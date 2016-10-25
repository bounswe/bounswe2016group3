package bounswegroup3.resource;

import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import bounswegroup3.db.MealDAO;
import bounswegroup3.db.MenuDAO;
import bounswegroup3.model.AccessToken;
import bounswegroup3.model.Meal;
import bounswegroup3.model.Menu;
import io.dropwizard.auth.Auth;

@Path("/menu")
@Produces(MediaType.APPLICATION_JSON)
public class MenuResource {
	private MenuDAO menuDao;
	private MealDAO mealDao;
	
	public MenuResource(MenuDAO menuDao, MealDAO mealDao){
		this.menuDao = menuDao;
		this.mealDao = mealDao;
	}
	
	/**
	 * <code>GET /api/menu/:id</code>
	 * <br>
	 * Gets the menu with the given id
	 * @param id
	 * @return A Menu object
	 */
	@GET
	@Path("/{id}")
	public Menu menuById(@PathParam("id") Long id){
		return menuDao.getMenuById(id);
	}
	
	/**
	 * <code>GET /api/menu/:id</code>
	 * <br>
	 * Fetches the meals of the menu with the given id
	 * @param id
	 * @return A list of Meal objects
	 */
	@GET
	@Path("/{id}/meals")
	public List<Meal> mealsByMenu(@PathParam("id") Long id){
		return mealDao.mealsByMenuId(id);
	}
	
	/**
	 * <code>POST /api/menu</code>
	 * <br>
	 * Creates a new menu. If you're not allowed to create that menu,
	 * a 304 response is returned. Otherwise, a 200 response is returned
	 * @param token Requires authentication
	 * @param menu The Menu object - including the user id
	 * @return The created Menu object, if successful
	 */
	@POST
	public Response createMenu(@Auth AccessToken token, @Valid Menu menu){
		if(token.getUserId()==menu.getUserId()){
			Long id = menuDao.createMenu(menu);
			menu.setId(id);
			
			return Response.ok(menu).build();
		} else {
			return Response.notModified().build();
		}
		
	}
}
