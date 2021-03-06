package bounswegroup3.resource;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import bounswegroup3.client.NutritionixClient;
import bounswegroup3.db.CheckEatDAO;
import bounswegroup3.db.EventDAO;
import bounswegroup3.db.MealDAO;
import bounswegroup3.model.AccessToken;
import bounswegroup3.model.CheckEat;
import bounswegroup3.model.Event;
import bounswegroup3.model.NutritionalInfo;
import io.dropwizard.auth.Auth;

@Path("/home")
@Produces(MediaType.APPLICATION_JSON)
public class HomeResource {
	private CheckEatDAO checkeatDao;
	private MealDAO mealDao;
	private EventDAO eventDao;
	private NutritionixClient client;
	
	public HomeResource(CheckEatDAO checkeatDao, MealDAO mealDao, EventDAO eventDao,NutritionixClient client) {
		this.checkeatDao = checkeatDao;
		this.mealDao = mealDao;
		this.eventDao = eventDao;
		this.client = client;
	}
	
	@GET
	public List<Event> homepage(@Auth AccessToken token) {
		return eventDao.homepageEvents(token.getUserId());
	}
	
	@GET
	@Path("/lastweek")
	public NutritionalInfo lastWeekSummary(@Auth AccessToken token) {
		List<CheckEat> ces = checkeatDao.checkEatsFromLastWeek(token.getUserId());
		
		NutritionalInfo res = new NutritionalInfo();
		for(CheckEat ce : ces) {
			NutritionalInfo ni = client.getNutrition(mealDao.getMealById(ce.getMealId()).getIngredients());
			
			if(ni == null) {
				continue;
			}
			
			res.setCalories(res.getCalories()+ni.getCalories());
			res.setCholesterol(res.getCholesterol()+ni.getCholesterol());
			res.setDietaryFiber(res.getDietaryFiber()+ni.getDietaryFiber());
			res.setPhosphorus(res.getPhosphorus()+ni.getPhosphorus());
			res.setPotassium(res.getPotassium()+ni.getPotassium());
			res.setProtein(res.getProtein()+ni.getProtein());
			res.setSaturatedFat(res.getSaturatedFat()+ni.getSaturatedFat());
			res.setSodium(res.getSodium()+ni.getSodium());
			res.setSugars(res.getSugars()+ni.getSugars());
			res.setTotalCarbohydrate(res.getTotalCarbohydrate()+ni.getTotalCarbohydrate());
			res.setTotalFat(res.getTotalFat()+ni.getTotalFat());
			res.setWeight(res.getWeight()+ni.getWeight());
		}
		
		return res;
	}
	
	@GET
	@Path("/lastweek/meals")
	public List<CheckEat> lastWeekCheckEats(@Auth AccessToken token) {
		return checkeatDao.checkEatsFromLastWeek(token.getUserId());
	}
}
