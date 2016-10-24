package bounswegroup3.client;

import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import bounswegroup3.mapper.NutritionalInfoMapper;
import bounswegroup3.model.NutritionalInfo;

public class NutritionixClient {
	private Client client;
	private String appId;
	private String secret;
	
	private LoadingCache<String, String> searches;
	
	private static URI searchUrl = UriBuilder.fromUri("https://trackapi.nutritionix.com/v2/natural/nutrients").build();
	
	public NutritionixClient(Client client, String appId, String secret) {
		super();
		this.client = client;
		this.appId = appId;
		this.secret = secret;
		
		this.searches = CacheBuilder.newBuilder()
				.maximumSize(2000)
				.build(new CacheLoader<String,String>(){
					@Override
					public String load(String query) throws Exception {
						String postBody = "{ \"query\": \"" + query + "\"}";
						
						Response res = client.target(searchUrl)
								.request()
								.header("Content-Type", "application/json")
								.header("x-app-id", appId)
								.header("x-app-key", secret)
								.header("x-remote-user-id", 0)
								.post(Entity.entity(postBody, MediaType.APPLICATION_JSON_TYPE));
							
							return res.readEntity(String.class);
					}
				});
	}
	
	public NutritionalInfo getNutrition(String ingredients) {
		return (new NutritionalInfoMapper()).map(this.searches.getUnchecked(ingredients));
	}
	
	
}
