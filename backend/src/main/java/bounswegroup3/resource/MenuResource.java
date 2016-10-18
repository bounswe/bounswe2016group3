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

import bounswegroup3.db.MealDAO;
import bounswegroup3.db.MenuDAO;
import bounswegroup3.model.AccessToken;
import bounswegroup3.model.Meal;
import bounswegroup3.model.Menu;
import bounswegroup3.model.Tag;
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
	
	@GET
	@Path("/{id}")
	public Menu menuById(@PathParam("id") Long id){
		return menuDao.getMenuById(id);
	}
	
	@GET
	@Path("byUser/{id}")
	public List<Menu> menusByUser(@PathParam("id") Long id){
		return menuDao.menusByUser(id);
	}
	
	@GET
	@Path("/{id}/meals")
	public List<Meal> mealsByMenu(@PathParam("id") Long id){
		return mealDao.mealsByMenuId(id);
	}
	
	@POST
	public Menu createMenu(@Auth AccessToken token, @Valid Menu menu){
		Long id = menuDao.createMenu(menu);
		menu.setId(id);
		
		return menu;
	}
}
