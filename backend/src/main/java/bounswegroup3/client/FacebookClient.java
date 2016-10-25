package bounswegroup3.client;

import java.net.URI;
import java.util.HashMap;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.UriBuilder;

/**
 * The class is responsible for communicating with the Facebook Graph API in order
 * to connect via Facebook.
 */
public class FacebookClient {
	private Client client;
	private Long appId;
	private String secret;
	
	private String appToken;
	
	private static final URI callUrl = UriBuilder.fromUri("https://graph.facebook.com/debug_token").build();
	private static final URI appTokenUrl = UriBuilder.fromUri("https://graph.facebook.com/oauth/access_token").build();
	
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
	}
	
	private void getAppToken() {
		appToken = client.target(appTokenUrl)
					.queryParam("client_id", this.appId.toString())
					.queryParam("client_secret", this.secret)
					.queryParam("grant_type", "client_credentials")
					.request()
					.get(String.class);
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
}
