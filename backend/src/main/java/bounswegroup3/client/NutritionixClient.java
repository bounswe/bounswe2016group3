package bounswegroup3.client;

import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.UriBuilder;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class NutritionixClient {
	private Client client;
	private String appId;
	private String secret;
	
	private LoadingCache<String, String> infos;
	private LoadingCache<String, String> searches;
	
	private static URI apiUrl = UriBuilder.fromUri("https://api.nutritionix.com").build();
	
	public NutritionixClient(Client client, String appId, String secret) {
		super();
		this.client = client;
		this.appId = appId;
		this.secret = secret;
		
		this.infos = CacheBuilder.newBuilder()
				.maximumSize(2000)
				.build(new CacheLoader<String,String>(){
					@Override
					public String load(String arg0) throws Exception {
						// TODO Auto-generated method stub
						return null;
					}
				});
		
		this.searches = CacheBuilder.newBuilder()
				.maximumSize(2000)
				.build(new CacheLoader<String,String>(){
					@Override
					public String load(String arg0) throws Exception {
						// TODO Auto-generated method stub
						return null;
					}
				});
	}
	
	
}
