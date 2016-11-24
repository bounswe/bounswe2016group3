package bounswegroup3.resource;

import static bounswegroup3.utils.TestUtils.registerAuth;
import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import bounswegroup3.auth.DummyAuthenticator;
import bounswegroup3.db.MealDAO;
import bounswegroup3.db.MenuDAO;
import bounswegroup3.db.UserDAO;
import bounswegroup3.model.Meal;
import bounswegroup3.model.Menu;
import bounswegroup3.model.User;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;

public class MenuResourceTest {
	private static MenuDAO menuDao = mock(MenuDAO.class);
	private static MealDAO mealDao = mock(MealDAO.class);
	private static UserDAO userDao = mock(UserDAO.class);
	
	@Rule
	public ResourceTestRule rule = registerAuth(new DummyAuthenticator())
		.addResource(new MenuResource(menuDao, mealDao, userDao))
		.build();
	
	private Menu menu;
	private Meal meal;
	private User user;
	private ObjectMapper mapper;
	
	@Before
	public void setup() throws Exception {
		mapper = Jackson.newObjectMapper();
		menu = mapper.readValue(fixture("fixtures/menu.json"), Menu.class);
		meal = mapper.readValue(fixture("fixtures/meal.json"), Meal.class);
		user = mapper.readValue(fixture("fixtures/user.json"), User.class);
		
		ArrayList<Meal> meals = new ArrayList<Meal>();
		meals.add(meal);
		
		when(mealDao.mealsByMenuId(any())).thenReturn(meals);
		when(menuDao.createMenu(any())).thenReturn(1l);
		when(menuDao.getMenuById(any())).thenReturn(menu);
		when(userDao.getUserById(any())).thenReturn(user);
	}
	
	@After
	public void tearDown() {
		reset(menuDao);
		reset(mealDao);
	}
	
	@Test
	public void testMenuById() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/menu/1")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.get();
		
		@SuppressWarnings("rawtypes")
		LinkedHashMap read = mapper.readValue(res.readEntity(String.class), LinkedHashMap.class);

		assertThat(res.getStatusInfo().getStatusCode()).isEqualTo(200);
		assertThat(read.get("name")).isEqualTo(menu.getName());
	}
	
	@Test
	public void testMealsByMenu() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/menu/1/meals")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.get();
		
		@SuppressWarnings({"rawtypes", "unchecked"})
		ArrayList<LinkedHashMap> read = mapper.readValue(res.readEntity(String.class), ArrayList.class);

		assertThat(res.getStatusInfo().getStatusCode()).isEqualTo(200);
		assertThat(read.size()).isEqualTo(1);
		assertThat(read.get(0).get("name")).isEqualTo(meal.getName());
	}
	
	@Test
	public void testCreateMeal() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/menu")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.header("Authorization", "Bearer test")
				.post(Entity.json(menu));
		
		@SuppressWarnings("rawtypes")
		LinkedHashMap read = mapper.readValue(res.readEntity(String.class), LinkedHashMap.class);
		
		assertThat(res.getStatusInfo().getStatusCode()).isEqualTo(200);
		assertThat(read.get("id")).isEqualTo(1);
	}
}
