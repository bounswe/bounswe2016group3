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

public class RatingDAOTest {
	@Rule
	public H2JDBIRule db = new H2JDBIRule();
	
	private RatingDAO dao;
	private ObjectMapper mapper;
	
	@Before
	public void setup() throws Exception {
		mapper = Jackson.newObjectMapper();
		dao = db.getDbi().onDemand(RatingDAO.class);
		
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
	public void testRating() throws Exception {
		assertThat(dao.totalRatings(1l)).isEqualTo(0);
		
		dao.rateMeal(1l, 1l, 1.0f);
		
		assertThat(dao.averageRating(1l)).isEqualTo(1.0f);
		assertThat(dao.ratedByUser(1l, 1l));
		assertThat(dao.ratingByUser(1l, 1l)).isEqualTo(1.0f);
		assertThat(dao.totalRatings(1l)).isEqualTo(1);
	}
}
