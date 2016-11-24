package bounswegroup3.resource;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import bounswegroup3.client.NutritionixClient;
import bounswegroup3.constant.UserType;
import bounswegroup3.db.CommentDAO;
import bounswegroup3.db.MealDAO;
import bounswegroup3.db.UserDAO;
import bounswegroup3.model.AccessToken;
import bounswegroup3.model.Comment;
import bounswegroup3.model.Meal;
import bounswegroup3.model.NutritionalInfo;
import bounswegroup3.model.Ratings;
import bounswegroup3.model.Tag;
import io.dropwizard.auth.Auth;

@Path("/meal")
@Produces(MediaType.APPLICATION_JSON)
public class MealResource {
	private MealDAO mealDao;
	private CommentDAO commentDao;
	private NutritionixClient client;
	private UserDAO userDao;
	
	public MealResource(MealDAO mealDao, CommentDAO commentDao, UserDAO userDao, NutritionixClient client) {
		this.mealDao = mealDao;
		this.commentDao = commentDao;
		this.userDao = userDao;
		this.client = client;
	}
	
	/**
	 * <code>GET /api/meal/:id</code>
	 * <br>
	 * Gets a meal by its id
	 * @param id
	 * @return A meal object
	 */
	@GET
	@Path("/{id}")
	public Meal getMealById(@PathParam("id") Long id) {
		return mealDao.getMealById(id);
	}
	
	/**
	 * <code>POST /api/meal</code>
	 * <br>
	 * Creates a new meal. Fails if you have don't have the same user id with the meal.
	 * Returns 200 on success and 304 on failure.
	 * @param token Requires authentication
	 * @param meal The meal you're trying to create, including its user id
	 * @return The created meal - including its id, if successful
	 */
	@POST
	public Response createMeal(@Auth AccessToken token, @Valid Meal meal) {
		if(meal.getUserId()  == token.getUserId()) {
			if(UserType.values()[userDao.getUserById(token.getUserId()).getUserType()]==UserType.FOOD_SERVER ||
					UserType.values()[userDao.getUserById(token.getUserId()).getUserType()]==UserType.ADMIN) {
				Long id = mealDao.createMeal(meal);
				meal.setId(id);

				return Response.ok(meal).build();
			} else {
				return Response.notModified().build();
			}
		} else {
			return Response.notModified().build();
		}
	}
	
	/**
	 * <code>POST /api/meal/update</code>
	 * <br>
	 * Update your own meal. Fails if you have no write accesss to the comment
	 * Returns 200 on success and 304 on failure
	 * @param token Requires authentication
	 * @param meal The meal we're trying to update - including the unchanged properties
	 * @return The modified meal, if successful.
	 */
	@POST
	@Path("/update")
	public Response updateMeal(@Auth AccessToken token, @Valid Meal meal) {	
		if(token.getUserId() == meal.getUserId()){
			mealDao.updateMeal(meal);
			return Response.ok(meal).build();
		} else {
			return Response.notModified().build();
		}
	}
	
	/**
	 * <code>POST /api/meal/:id/delete</code>
	 * <br>
	 * Deletes the specified meal. Returns 200 on success and 304 on failure.
	 * @param token Requires authentication
	 */
	@POST
	@Path("/{id}/delete")
	public Response deleteMeal(@Auth AccessToken token, @PathParam("id") Long id) {
		if(mealDao.getMealById(id).getUserId() == token.getUserId()){
			mealDao.deleteMeal(id);
			return Response.ok().build();
		} else {
			return Response.notModified().build();
		}
	}
	
	/**
	 * <code>POST /api/meal/:id/checkeat</code>
	 * <br>
	 * Check-eats the meal with the given id. You check-eat a meal to specify that you've eaten it.
	 * Fails if you've already marked as eaten the meal. Return
	 * @param token Requires authentication
	 */
	@POST
	@Path("/{id}/checkeat")
	public Response checkEat(@Auth AccessToken token, @PathParam("id") Long id) {
		if(!mealDao.checkAte(token.getUserId(), id)) {
			mealDao.checkEat(token.getUserId(), id);
			return Response.ok().build();
		} else {
			return Response.notModified().build();
		}
	}
	/**
	 * <code>POST /api/meal/:id/rate/:rating</code>
	 * <br>
	 * Rates the meal with the specified id. The rating is a floating point 
	 * number between 0 and 1
	 * @param token Requires authentication
	 */
	@POST
	@Path("/{id}/rate/{rating}")
	public Response rateMeal(@Auth AccessToken token, @PathParam("id") Long id, @PathParam("rating") Float rating) {
		if(mealDao.ratedByUser(token.getUserId(), id)) {
			return Response.notModified().build();
		}
		
		mealDao.rateMeal(token.getUserId(), id, rating);
		return Response.ok().build();
	}
	
	/**
	 * <code>POST /api/meal/:id/ratings</code>
	 * <br>
	 * Gets the average and total ratings associated with a meal.
	 * @param token Requires authentication
	 * @return A Ratings object
	 */
	@GET
	@Path("/{id}/ratings")
	public Ratings getRatings(@Auth AccessToken token, @PathParam("id") Long id) {
		return new Ratings(mealDao.averageRating(id), mealDao.totalRatings(id), mealDao.ratingByUser(token.getUserId(), id));
	}
	
	/**
	 * <code>GET /api/meal/byTag/:tag</code>
	 * <br>
	 * Fetches a list of meals tagged with the specified tag
	 * @return A list of Meal objects
	 */
	@GET
	@Path("/byTag/{tag}")
	public List<Meal> mealsByTag(@PathParam("tag") String tag) {
		return mealDao.getMealsByTag(tag);
	}
	
	/**
	 * <code>GET /api/meal/:id/tags</code>
	 * <br>
	 * Fetches all the tags the specified meal is tagged with
	 * @return A list of Strings
	 */
	@GET
	@Path("/{id}/tags")
	public List<String> tagsByMeal(@PathParam("id") Long id) {
		return mealDao.getTagsByMeal(id);
	}
	
	/**
	 * <code>GET /api/meal/:id/comments</code>
	 * <br>
	 * Fetches all the comments made on the specified meal
	 * @return A list of Comment objects
	 */
	@GET
	@Path("/{id}/comments")
	public List<Comment> commentsByMeal(@PathParam("id") Long id) {
		return commentDao.commentsByMeal(id);
	}
	
	/**
	 * <code>POST /api/meal/tag</code>
	 * <br>
	 * The call fails if the meal is already tagged in the specified way.
	 * Returns 200 on success and 304 on failure
	 * @param token Requires authentication
	 * @param tag A tag object, containing the meal id and tag string
	 */
	@POST
	@Path("/tag")
	public Response tagMeal(@Auth AccessToken token, Tag tag) {
		Meal meal = mealDao.getMealById(tag.getMealId());
		
		if(token.getUserId() == meal.getUserId()){
			ArrayList<String> tags = new ArrayList<String>(mealDao.getTagsByMeal(tag.getMealId()));
						
			if(!tags.contains(tag.getTag())){
				mealDao.tagMeal(tag.getMealId(), tag.getTag());
				return Response.ok().build();
			} else {
				return Response.notModified().build();
			}
		} else {
			return Response.notModified().build();
		}
	}
	
	/**
	 * <code>POST /api/meal/untag</code>
	 * <br>
	 * The call fails if the meal is not already tagged in the specified way.
	 * Returns 200 on success and 304 on failure
	 * @param token Requires authentication
	 * @param tag A tag object, containing the meal id and tag string
	 */
	@POST
	@Path("/untag")
	public Response untagMeal(@Auth AccessToken token, Tag tag) {
		Meal meal = mealDao.getMealById(tag.getMealId());
		
		if(token.getUserId() == meal.getUserId()){
			ArrayList<String> tags = new ArrayList<String>(mealDao.getTagsByMeal(tag.getMealId()));
			if(tags.contains(tag.getTag())){
				mealDao.untagMeal(tag.getMealId(), tag.getTag());
				return Response.ok().build();
			} else {
				return Response.notModified().build();
			}
		} else {
			return Response.notModified().build(); 
		}
	}
	
	/**
	 * <code>GET /api/meal/:id/nutrition</code>
	 * Fetches the nutritional info of the given meal from the external service
	 * @return a NutritionalInfo object
	 */
	@GET
	@Path("/{id}/nutrition")
	public NutritionalInfo getNutrition(@PathParam("id") Long id) {
		return client.getNutrition(mealDao.getMealById(id).getIngredients());
	}
	
	/**
	 * <code>GET /api/meal/search/:query</code>
	 * Performs a basic search on meals only based on the given query. User 
	 * preferences and such aren't taken into account
	 * @return A list of the matching Meal objects
	 */
	@GET
	@Path("/search/{query}")
	public List<Meal> basicSearch(@PathParam("query") String query) {
		return mealDao.basicSearch(query);
	}
}
