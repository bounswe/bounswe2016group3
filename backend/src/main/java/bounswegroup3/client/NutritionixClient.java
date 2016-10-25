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
	private LoadingCache<String, String> searches;
	
	private static URI searchUrl = UriBuilder.fromUri("https://trackapi.nutritionix.com/v2/natural/nutrients").build();
	
	/**
	 * Nutritionix API also needs two authentication factors provided by the service.
	 * This class is responsible for connecting to the service and
	 * getting nutritional data from it. There is a simple cache between this
	 * class and the external resource, both to speed things and stay within the API
	 * call limits of the free tier (Hope it won't be too difficult).
	 * @param client the HTTPClient object needed to connect to an external service
	 * @param appId the public half of Nutritionix's authentication scheme 
	 * @param secret the private half of it
	 */
	public NutritionixClient(Client client, String appId, String secret) {
		super();
		
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
	
	/**
	 * Connects to the Nutritionix API (if needed) to fetch a response
	 * for each ingredient and then sums the nutritional info values
	 * received from the external service.
	 * @param ingredients The list of ingredients, in a natural-language form
	 * @return The total of nutritional values of all ingredients
	 */
	public NutritionalInfo getNutrition(String ingredients) {
		try {
			return (new NutritionalInfoMapper()).map(this.searches.getUnchecked(ingredients));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
