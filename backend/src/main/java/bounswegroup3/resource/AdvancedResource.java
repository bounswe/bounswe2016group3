package bounswegroup3.resource;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import bounswegroup3.db.MealDAO;
import bounswegroup3.model.AccessToken;
import bounswegroup3.model.AdvancedSearchOptions;
import bounswegroup3.model.Meal;
import io.dropwizard.auth.Auth;

@Path("/advanced")
@Produces(MediaType.APPLICATION_JSON)
public class AdvancedResource {
	
	private MealDAO mealDao;
	
	public AdvancedResource(MealDAO dao) {
		this.mealDao = dao;
	}
	
	@POST
	@Path("/search")
	public List<Meal> advancedSearch(@Auth AccessToken token) {
		return mealDao.advancedSearch(token.getUserId());
	}
	
	@GET
	@Path("/{id}/recommend")
	public List<Meal> recommendations(@PathParam("id") Long mealId) {
		return mealDao.recommend(mealId);
	}
}
