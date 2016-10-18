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
	
	@GET
	@Path("/{id}/comments")
	public List<Comment> getCommentsByMeal(@PathParam("id") Long id) {
		return commentDao.commentsByMeal(id);
	}
	
	@POST
	public Meal createMeal(@Auth AccessToken token, @Valid Meal meal) {
		Long id = mealDao.createMeal(meal);
		meal.setId(id);
		
		return meal;
	}
	
	@POST
	@Path("/update/{id}")
	public Meal updateMeal(@Auth AccessToken token, @Valid Meal meal) {
		if(meal.getId() == token.getUserId()){
			mealDao.updateMeal(meal);
		}
		
		return meal;
	}
	
	@POST
	@Path("/delete/{id}")
	public void deleteMeal(@Auth AccessToken token, @PathParam("id") Long id) {
		if(menuDao.getMenuById(mealDao.getMealById(id).getMenuId()).getUserId() == token.getUserId()){
			mealDao.deleteMeal(id);
		}
	}
	
	@POST
	@Path("/{id}/checkeat")
	public void checkEat(@Auth AccessToken token, @PathParam("id") Long id) {
		if(!mealDao.checkAte(token.getUserId(), id)) {
			mealDao.checkEat(token.getUserId(), id);
		}
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
	
	@POST
	@Path("/tag")
	public void tagMeal(@Auth AccessToken token, Tag tag) {
		Meal meal = mealDao.getMealById(tag.getMealId());
		Menu menu = menuDao.getMenuById(meal.getMenuId());
		
		if(token.getUserId() == menu.getUserId()){
			ArrayList<String> tags = new ArrayList<String>(mealDao.getTagsByMeal(tag.getMealId()));
			if(!tags.contains(tag.getTag())){
				mealDao.tagMeal(tag.getMealId(), tag.getTag());
			}
		}
	}
	
	@POST
	@Path("/untag")
	public void untagMeal(@Auth AccessToken token, Tag tag) {
		Meal meal = mealDao.getMealById(tag.getMealId());
		Menu menu = menuDao.getMenuById(meal.getMenuId());
		
		if(token.getUserId() == menu.getUserId()){
			ArrayList<String> tags = new ArrayList<String>(mealDao.getTagsByMeal(tag.getMealId()));
			if(tags.contains(tag.getTag())){
				mealDao.untagMeal(tag.getMealId(), tag.getTag());
			}
		}
	}
}
