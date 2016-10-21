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

import bounswegroup3.auth.UnauthorizedException;
import bounswegroup3.db.CommentDAO;
import bounswegroup3.db.MealDAO;
import bounswegroup3.db.MenuDAO;
import bounswegroup3.model.AccessToken;
import bounswegroup3.model.Comment;
import bounswegroup3.model.Meal;
import bounswegroup3.model.Menu;
import bounswegroup3.model.Ratings;
import bounswegroup3.model.Tag;
import io.dropwizard.auth.Auth;

@Path("/meal")
@Produces(MediaType.APPLICATION_JSON)
public class MealResource {
	private MenuDAO menuDao;
	private MealDAO mealDao;
	private CommentDAO commentDao;
	
	public MealResource(MenuDAO menuDao, MealDAO mealDao, CommentDAO commentDao) {
		this.menuDao = menuDao;
		this.mealDao = mealDao;
		this.commentDao = commentDao;
	}
	
	@GET
	@Path("/{id}")
	public Meal getMealById(@PathParam("id") Long id) {
		return mealDao.getMealById(id);
	}
	
	@POST
	public Response createMeal(@Auth AccessToken token, @Valid Meal meal) {
		Menu menu = menuDao.getMenuById(meal.getMenuId());
		if(menu.getUserId()  == token.getUserId()) {
			Long id = mealDao.createMeal(meal);
			meal.setId(id);
		} else {
			return Response.notModified().build();
		}
		
		return Response.ok(meal).build();
	}
	
	@POST
	@Path("/update")
	public Response updateMeal(@Auth AccessToken token, @Valid Meal meal) {
		Menu menu = menuDao.getMenuById(meal.getMenuId());
		
		if(token.getUserId() == menu.getUserId()){
			mealDao.updateMeal(meal);
			return Response.ok(meal).build();
		} else {
			return Response.notModified().build();
		}
	}
	
	@POST
	@Path("/delete/{id}")
	public Response deleteMeal(@Auth AccessToken token, @PathParam("id") Long id) {
		if(menuDao.getMenuById(mealDao.getMealById(id).getMenuId()).getUserId() == token.getUserId()){
			mealDao.deleteMeal(id);
			return Response.ok().build();
		} else {
			return Response.notModified().build();
		}
	}
	
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
	
	@POST
	@Path("/{id}/rate/{rating}")
	public void rateMeal(@Auth AccessToken token, @PathParam("id") Long id, @PathParam("rating") Float rating) {
		mealDao.rateMeal(token.getUserId(), id, rating);
	}
	
	@GET
	@Path("/{id}/ratings")
	public Ratings getRatings(@Auth AccessToken token, @PathParam("id") Long id) {
		return new Ratings(mealDao.averageRating(id), mealDao.totalRatings(id), mealDao.ratingByUser(token.getUserId(), id));
	}
	
	@GET
	@Path("/byTag/{tag}")
	public List<Meal> mealsByTag(@PathParam("tag") String tag) {
		return mealDao.getMealsByTag(tag);
	}
	
	@GET
	@Path("/{id}/tags")
	public List<String> tagsByMeal(@PathParam("id") Long id) {
		return mealDao.getTagsByMeal(id);
	}
	
	@GET
	@Path("/{id}/comments")
	public List<Comment> commentsByMeal(@PathParam("id") Long id) {
		return commentDao.commentsByMeal(id);
	}
	
	@POST
	@Path("/tag")
	public Response tagMeal(@Auth AccessToken token, Tag tag) {
		Meal meal = mealDao.getMealById(tag.getMealId());
		Menu menu = menuDao.getMenuById(meal.getMenuId());
		
		if(token.getUserId() == menu.getUserId()){
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
	
	@POST
	@Path("/untag")
	public Response untagMeal(@Auth AccessToken token, Tag tag) {
		Meal meal = mealDao.getMealById(tag.getMealId());
		Menu menu = menuDao.getMenuById(meal.getMenuId());
		
		if(token.getUserId() == menu.getUserId()){
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
}
