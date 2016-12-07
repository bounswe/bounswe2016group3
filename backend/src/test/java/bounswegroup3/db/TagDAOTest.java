package bounswegroup3.db;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;

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

public class TagDAOTest {
	@Rule
	public H2JDBIRule db = new H2JDBIRule();
	
	private TagDAO dao;
	private ObjectMapper mapper;
	
	@Before
	public void setup() throws Exception {
		mapper = Jackson.newObjectMapper();
		dao = db.getDbi().onDemand(TagDAO.class);
		
		User u = mapper.readValue(fixture("fixtures/user.json"), User.class);
		db.getDbi().onDemand(UserDAO.class).addUser(u);
		
		Menu mn = mapper.readValue(fixture("fixtures/menu.json"), Menu.class);
		mn.setUserId(1l);
		db.getDbi().onDemand(MenuDAO.class).createMenu(mn);
		
		Meal m = mapper.readValue(fixture("fixtures/meal.json"), Meal.class);
		m.setMenuId(1l);
		m.setUserId(1l);
		db.getDbi().onDemand(MealDAO.class).createMeal(m);
	}
	
	@After
	public void tearDown() throws Exception {
		
	}
	
	@Test
	public void testTags() throws Exception {
		assertThat(dao.getTagsByMeal(1l).size()).isEqualTo(0);
		assertThat(dao.getMealsByTag("test").size()).isEqualTo(0);
		
		dao.tagMeal(1l, "test", "test");
		
		assertThat(dao.getTagsByMeal(1l).size()).isEqualTo(1);
		assertThat(dao.getMealsByTag("test").size()).isEqualTo(1);
		
		assertThat(dao.getTagsByMeal(1l).get(0).getIdentifier()).isEqualTo("test");
		assertThat(dao.getMealsByTag("test").get(0).getName()).isEqualTo("test meal");
		
		dao.untagMeal(1l, "test");
		
		assertThat(dao.getTagsByMeal(1l).size()).isEqualTo(0);
		assertThat(dao.getMealsByTag("test").size()).isEqualTo(0);
	}
	
	@Test
	public void testMealTaggedWith() {
		assertThat(dao.mealTaggedWith(1l, "test")).isEqualTo(false);
		
		dao.tagMeal(1l, "test", "test");
		
		assertThat(dao.mealTaggedWith(1l, "test")).isEqualTo(true);				
	}
}
