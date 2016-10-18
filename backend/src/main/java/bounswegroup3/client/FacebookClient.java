package bounswegroup3.client;

import java.net.URI;
import java.util.HashMap;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.UriBuilder;

public class FacebookClient {
	private Client client;
	private Long appId;
	private String secret;
	private String redirectUrl;
	
	private String appToken;
	
	private static final URI callUrl = UriBuilder.fromUri("https://graph.facebook.com/debug_token").build();
	private static final URI appTokenUrl = UriBuilder.fromUri("https://graph.facebook.com/oauth/access_token").build();
	
	public FacebookClient(Client client, Long appId, String secret, String redirectUrl) {
		super();
		this.client = client;
		this.appId = appId;
		this.secret = secret;
		this.redirectUrl = redirectUrl;
		
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
	
	public Long getUserIdByToken(String token) {
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
