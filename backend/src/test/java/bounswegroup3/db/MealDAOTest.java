package bounswegroup3.db;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import bounswegroup3.model.Meal;
import bounswegroup3.model.Menu;
import bounswegroup3.model.User;
import bounswegroup3.utils.H2JDBIRule;
import io.dropwizard.jackson.Jackson;

public class MealDAOTest {
	@Rule
	public H2JDBIRule db = new H2JDBIRule();
	
	private MealDAO dao;
	private ObjectMapper mapper;
	
	@Before
	public void setup() throws Exception {
		mapper = Jackson.newObjectMapper();
		dao = db.getDbi().onDemand(MealDAO.class);
		
		User u = mapper.readValue(fixture("fixtures/user.json"), User.class);
		db.getDbi().onDemand(UserDAO.class).addUser(u);
		
		Menu m = mapper.readValue(fixture("fixtures/menu.json"), Menu.class);
		m.setUserId(1l);
		db.getDbi().onDemand(MenuDAO.class).createMenu(m);
	}
	
	@After
	public void tearDown() throws Exception {
		
	}
	
	@Test
	public void testCrud() throws Exception {
		Meal m = mapper.readValue(fixture("fixtures/meal.json"), Meal.class);
		m.setMenuId(1l);
		m.setUserId(1l);
		
		Long id = dao.createMeal(m);
		assertThat(id).isEqualTo(1l);
		
		m = dao.getMealById(1l);
		assertThat(m.getName()).isEqualTo("test meal");
		
		ArrayList<Meal> ms = new ArrayList<Meal>(dao.mealsByMenuId(1l));
		assertThat(ms.size()).isEqualTo(1);
		assertThat(ms.get(0).getName()).isEqualTo("test meal");
		
		ms = new ArrayList<Meal>(dao.mealsByUserId(1l));
		assertThat(ms.size()).isEqualTo(1);
		assertThat(ms.get(0).getName()).isEqualTo("test meal");
		
		m.setName("update test");
		dao.updateMeal(m);
		
		m = dao.getMealById(1l);
		assertThat(m.getName()).isEqualTo("update test");
		
		dao.deleteMeal(1l);
		assertThat(dao.getMealById(1l)).isNull();
	}
	
	@Test
	public void testSearch() throws Exception {
		ArrayList<Meal> res = new ArrayList<Meal>(dao.basicSearch("test"));
		
		assertThat(res.isEmpty());
		
		Meal m = mapper.readValue(fixture("fixtures/meal.json"), Meal.class);
		m.setMenuId(1l);
		m.setUserId(1l);
		
		dao.createMeal(m);
		
		res = new ArrayList<Meal>(dao.basicSearch("test"));
		
		assertThat(res.size()).isEqualTo(1);
	}
}
