package bounswegroup3.db;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.amazonaws.util.json.Jackson;
import com.fasterxml.jackson.databind.ObjectMapper;

import bounswegroup3.model.Meal;
import bounswegroup3.model.Menu;
import bounswegroup3.model.User;
import bounswegroup3.utils.H2JDBIRule;

public class CheckEatDAOTest {
	@Rule
	public H2JDBIRule db = new H2JDBIRule();
	
	private ObjectMapper mapper;
	private CheckEatDAO dao;
	
	@Before
	public void setUp() throws Exception {
		mapper = Jackson.getObjectMapper();
		dao = db.getDbi().onDemand(CheckEatDAO.class);
		
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
	
	@Test
	public void testCheckEat() throws Exception {
		assertThat(!dao.checkAte(1l, 1l));
		
		dao.checkEat(1l, 1l);
		
		assertThat(dao.checkAte(1l, 1l));
	}
	
	//TODO test the last week method
	
}
