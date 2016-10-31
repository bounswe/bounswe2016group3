package bounswegroup3.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.HashMap;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import bounswegroup3.model.FacebookUser;
import io.dropwizard.jackson.Jackson;

/**
 * The class is responsible for communicating with the Facebook Graph API in order
 * to connect via Facebook.
 */
public class FacebookClient implements ServiceClient {
	private Client client;
	private Long appId;
	private String secret;
	
	private String appToken;

	private ObjectMapper mapper;
	
	private static final URI callUrl = UriBuilder.fromUri("https://graph.facebook.com/debug_token").build();
	private static final URI appTokenUrl = UriBuilder.fromUri("https://graph.facebook.com/oauth/access_token").build();
	private static final URI graphUrl = UriBuilder.fromUri("https://graph.facebook.com/v2.8").build();
	private static final String userFields = "name,about,email,picture";
	
	/**
	 * Facebook API provides us with an App ID and a secret. It also requires an App Token
	 * to be able to check the validity of a user token. The constructor sets the required fields
	 * and then gets an app token from Facebook API
	 * @param client the HTTPClient object needed to connect to an external service
	 * @param appId the public half of Facebook's authentication scheme 
	 * @param secret the private half of it
	 */
	public FacebookClient(Client client, Long appId, String secret) {
		super();
		this.client = client;
		this.appId = appId;
		this.secret = secret;
		
		getAppToken();
		
		mapper = Jackson.newObjectMapper();
	}
	
	private void getAppToken() {
		appToken = client.target(appTokenUrl)
					.queryParam("client_id", this.appId.toString())
					.queryParam("client_secret", this.secret)
					.queryParam("grant_type", "client_credentials")
					.request()
					.get(String.class);
		
		appToken = appToken.substring(13, appToken.length());
	}
	
	/**
	 * Checks if the given token is a valid one for this application.
	 * @param token The Facebook token to check validity of
	 * @return If the token is valid, the Facebook User IDd which it belongs
	 * to. If not, 0 is returned.
	 */
	public Long getUserIdByToken(String token) {
		@SuppressWarnings("unchecked")
		HashMap<String, HashMap<String, String>> res = client.target(callUrl)
			.queryParam("input_token", token)
			.queryParam("access_token", this.appToken)
			.request()
			.get(HashMap.class);
		
		if(res.get("data").get("is_valid").equals("true") && res.get("data").get("app_id").equals(this.appId.toString())) {	
			return Long.parseLong(res.get("data").get("user_id"));
		} else {
			return 0l;
		}
	}
	
	public boolean checkValidity() {
		@SuppressWarnings("unchecked")
		HashMap<String, String> res = client.target(graphUrl.resolve(appId.toString()))
				.queryParam("access_token", appToken)
				.request()
				.get(HashMap.class);
		
		return res.containsKey("id") && res.get("id").equals(appId.toString());
	}

	@SuppressWarnings("unchecked")
	public FacebookUser getPersonalInfo(Long userId, String token) {
		HashMap<String, String> res = client.target(graphUrl.resolve("/"+userId.toString()))
				.queryParam("access_token", token)
				.queryParam("fields", userFields)
				.request()
				.get(HashMap.class);
		
		try {
			String pic = ((HashMap<String,String>)mapper.readValue(res.get("picture"), HashMap.class).get("data")).get("url");
			
			FacebookUser ret = new FacebookUser(new Long(res.get("id")), res.get("email"), res.get("name"), res.get("about"), pic);
			return ret;
		} catch (IOException e) {
			e.printStackTrace();
			
			return null;
		}
	}
	
	public InputStream downloadImage(String url){
		return client.target(url)
		.request()
		.get(InputStream.class);	
	}
}
