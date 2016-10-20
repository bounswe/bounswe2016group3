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
import bounswegroup3.db.CommentDAO;
import bounswegroup3.db.MealDAO;
import bounswegroup3.db.MenuDAO;
import bounswegroup3.model.Comment;
import bounswegroup3.model.Meal;
import bounswegroup3.model.Menu;
import bounswegroup3.model.Ratings;
import bounswegroup3.model.Tag;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;

public class MealResourceTest {
	private static MenuDAO menuDao = mock(MenuDAO.class);
	private static MealDAO mealDao = mock(MealDAO.class);
	private static CommentDAO commentDao = mock(CommentDAO.class);
	
	@Rule
	public ResourceTestRule rule = registerAuth(new DummyAuthenticator())
		.addResource(new MealResource(menuDao, mealDao, commentDao))
		.build();
	
	private Menu menu;
	private Meal meal;
	private Comment comment;
	private Ratings ratings;
	private Tag tag;
	
	private ObjectMapper mapper;
	
	@Before
	public void setup() throws Exception {
		mapper = Jackson.newObjectMapper();
		
		menu = mapper.readValue(fixture("fixtures/menu.json"), Menu.class);
		meal = mapper.readValue(fixture("fixtures/meal.json"), Meal.class);
		comment = mapper.readValue(fixture("fixtures/comment.json"), Comment.class);
		ratings = new Ratings(0.0f, 0, 0.0f);
		tag = mapper.readValue(fixture("fixtures/tag.json"), Tag.class);
		
		ArrayList<Comment> comments = new ArrayList<Comment>();
		comments.add(comment);
		
		when(mealDao.getMealById(any())).thenReturn(meal);
		when(mealDao.createMeal(any())).thenReturn(1l);
	}
	
	@After
	public void tearDown() {
		reset(menuDao);
		reset(mealDao);
		reset(commentDao);
	}

	@Test
	public void testMealById() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/meal/1")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.get();
		
		LinkedHashMap read = mapper.readValue(res.readEntity(String.class), LinkedHashMap.class);
		
		assertThat(res.getStatusInfo().getStatusCode()).isEqualTo(200);
		assertThat(read.get("name")).isEqualTo(meal.getName());
	}
	
	@Test
	public void testCreateMeal() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/meal")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.header("Authorization", "Bearer test")
				.post(Entity.json(meal));

		LinkedHashMap read = mapper.readValue(res.readEntity(String.class), LinkedHashMap.class);
		
		assertThat(res.getStatusInfo().getStatusCode()).isEqualTo(200);
		assertThat(read.get("id")).isEqualTo(1);
	}
	
	@Test
	public void testCantCreateMeal() throws Exception {
		
	}
	
	@Test
	public void testUpdateMeal() throws Exception {
		
	}
	
	@Test
	public void testDeleteMeal() throws Exception {
		
	}
	
	@Test
	public void testCheckEat() throws Exception {
		
	}
	
	@Test
	public void testRateMeal() throws Exception {
		
	}
	
	@Test
	public void testRatings() throws Exception {
		
	}
	
	@Test
	public void testMealsByTag() throws Exception {
		
	}
	
	@Test
	public void testGetTags() throws Exception {
		
	}
	
	@Test
	public void testCommentsByMeal() throws Exception {
		
	}
	
	@Test
	public void testTagMeal() throws Exception {
		
	}
	
	@Test
	public void testCantTagMeal() throws Exception {
		
	}
	
	@Test
	public void testAlreadyTagged() throws Exception {
		
	}
	
	@Test
	public void testUntagMeal() throws Exception {
		
	}
	
	@Test
	public void testCantUntagMeal() throws Exception {
		
	}
	
	@Test
	public void testAlreadyUntagged() throws Exception {
		
	}
}
