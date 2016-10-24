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
import bounswegroup3.client.NutritionixClient;
import bounswegroup3.db.CommentDAO;
import bounswegroup3.db.MealDAO;
import bounswegroup3.model.Comment;
import bounswegroup3.model.Meal;
import bounswegroup3.model.Tag;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;

public class MealResourceTest {
	private static MealDAO mealDao = mock(MealDAO.class);
	private static CommentDAO commentDao = mock(CommentDAO.class);
	private static NutritionixClient client = mock(NutritionixClient.class);
	
	@Rule
	public ResourceTestRule rule = registerAuth(new DummyAuthenticator())
		.addResource(new MealResource(mealDao, commentDao, client))
		.build();
	
	private Meal meal;
	private Comment comment;
	private Tag tag;
	
	private ObjectMapper mapper;
	
	private Meal invalidMeal;
	private Tag invalidTag;
	private Tag notExistsTag;
	
	@Before
	public void setup() throws Exception {
		mapper = Jackson.newObjectMapper();
		
		meal = mapper.readValue(fixture("fixtures/meal.json"), Meal.class);
		comment = mapper.readValue(fixture("fixtures/comment.json"), Comment.class);
		tag = new Tag(-1l, "test");
		
		ArrayList<Comment> comments = new ArrayList<Comment>();
		comments.add(comment);
		
		ArrayList<Meal> meals = new ArrayList<Meal>();
		meals.add(meal);
		
		ArrayList<String> tags = new ArrayList<String>();
		tags.add("tagged");
		
		when(mealDao.getMealById(any())).thenReturn(meal);
		when(mealDao.createMeal(any())).thenReturn(1l);
				
		invalidMeal = new Meal(42l, 42l, 42l, "", "", "", "");
		invalidTag = new Tag(42l, "test");
		notExistsTag = new Tag(32l, "tagged");
		
		when(mealDao.getMealById(eq(42l))).thenReturn(invalidMeal);
		
		when(mealDao.checkAte(any(), any())).thenReturn(false);
		when(mealDao.checkAte(any(), eq(42l))).thenReturn(true);
		
		when(mealDao.averageRating(any())).thenReturn(0.0f);
		when(mealDao.totalRatings(any())).thenReturn(0);
		when(mealDao.ratingByUser(any(), any())).thenReturn(0.0f);
		
		when(mealDao.getMealsByTag(any())).thenReturn(meals);
		when(mealDao.getTagsByMeal(any())).thenReturn(tags);
		
		when(commentDao.commentsByMeal(any())).thenReturn(comments);
		
		when(mealDao.getTagsByMeal(any())).thenReturn(tags);
	}
	
	@After
	public void tearDown() {
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
		verify(mealDao).createMeal(any());
	}
	
	@Test
	public void testCantCreateMeal() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/meal")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.header("Authorization", "Bearer test")
				.post(Entity.json(invalidMeal));
		
		assertThat(res.getStatusInfo().getStatusCode()).isEqualTo(304);
		verify(mealDao, never()).createMeal(any());
	}
	
	@Test
	public void testUpdateMeal() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/meal/update")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.header("Authorization", "Bearer test")
				.post(Entity.json(meal));
		
		LinkedHashMap read = mapper.readValue(res.readEntity(String.class), LinkedHashMap.class);
		
		assertThat(res.getStatusInfo().getStatusCode()).isEqualTo(200);
		assertThat(read.get("name")).isEqualTo(meal.getName());
		verify(mealDao).updateMeal(any());
	}
	
	@Test
	public void testCantUpdateMeal() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/meal/update")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.header("Authorization", "Bearer test")
				.post(Entity.json(invalidMeal));
		
		assertThat(res.getStatusInfo().getStatusCode()).isEqualTo(304);
		verify(mealDao, never()).updateMeal(any());
	}
	
	@Test
	public void testDeleteMeal() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/meal/1/delete")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.header("Authorization", "Bearer test")
				.post(Entity.json(""));
		
		assertThat(res.getStatusInfo().getStatusCode()).isEqualTo(200);
		verify(mealDao).deleteMeal(any());
	}
	
	@Test
	public void testCantDeleteMeal() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/meal/42/delete")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.header("Authorization", "Bearer test")
				.post(Entity.json(""));
		
		assertThat(res.getStatusInfo().getStatusCode()).isEqualTo(304);
		verify(mealDao, never()).deleteMeal(any());
	}
	
	@Test
	public void testCheckEat() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/meal/1/checkeat")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.header("Authorization", "Bearer test")
				.post(Entity.json(""));
		
		assertThat(res.getStatusInfo().getStatusCode()).isEqualTo(200);
		verify(mealDao).checkEat(any(), any());
	}
	
	@Test
	public void testCantCheckEat() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/meal/42/checkeat")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.header("Authorization", "Bearer test")
				.post(Entity.json(""));
		
		assertThat(res.getStatusInfo().getStatusCode()).isEqualTo(304);
		verify(mealDao, never()).checkEat(any(), any());
	}
	
	@Test
	public void testRateMeal() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/meal/1/rate/0.0")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.header("Authorization", "Bearer test")
				.post(Entity.json(""));
		
		assertThat(res.getStatusInfo().getStatusCode()).isBetween(200, 300);
		verify(mealDao).rateMeal(any(), any(), any());
	}
	
	@Test
	public void testRatings() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/meal/1/ratings")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.header("Authorization", "Bearer test")
				.get();
		
		LinkedHashMap read = mapper.readValue(res.readEntity(String.class), LinkedHashMap.class);
		
		assertThat(res.getStatusInfo().getStatusCode()).isEqualTo(200);
		assertThat(read.get("average")).isEqualTo(0.0);
	}
	
	@Test
	public void testMealsByTag() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/meal/byTag/test")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.header("Authorization", "Bearer test")
				.get();
		
		ArrayList<LinkedHashMap> read = mapper.readValue(res.readEntity(String.class), ArrayList.class);
		
		assertThat(res.getStatusInfo().getStatusCode()).isEqualTo(200);
		assertThat(read.get(0).get("name")).isEqualTo(meal.getName());
	}
	
	@Test
	public void testGetTags() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/meal/1/tags")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.header("Authorization", "Bearer test")
				.get();
		
		
		ArrayList<String> read = mapper.readValue(res.readEntity(String.class), ArrayList.class);
		
		assertThat(res.getStatusInfo().getStatusCode()).isEqualTo(200);
		assertThat(read.get(0)).isEqualTo("tagged");
	}
	
	@Test
	public void testCommentsByMeal() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/meal/1/comments")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.header("Authorization", "Bearer test")
				.get();
		
		ArrayList<LinkedHashMap> read = mapper.readValue(res.readEntity(String.class), ArrayList.class);
		
		assertThat(res.getStatusInfo().getStatusCode()).isEqualTo(200);
		assertThat(read.get(0).get("content")).isEqualTo(comment.getContent());
	}
	
	@Test
	public void testTagMeal() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/meal/tag")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.header("Authorization", "Bearer test")
				.post(Entity.json(tag));
		
		assertThat(res.getStatusInfo().getStatusCode()).isBetween(200, 300);
		verify(mealDao).tagMeal(any(), any());
	}
	
	@Test
	public void testCantTagMeal() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/meal/tag")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.header("Authorization", "Bearer test")
				.post(Entity.json(invalidTag));
		
		assertThat(res.getStatusInfo().getStatusCode()).isEqualTo(304);
		verify(mealDao, never()).tagMeal(any(), any());
	}
	
	@Test
	public void testAlreadyTagged() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/meal/tag")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.header("Authorization", "Bearer test")
				.post(Entity.json(notExistsTag));
		
		assertThat(res.getStatusInfo().getStatusCode()).isEqualTo(304);
		verify(mealDao, never()).tagMeal(any(), any());
	}
	
	@Test
	public void testUntagMeal() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/meal/untag")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.header("Authorization", "Bearer test")
				.post(Entity.json(notExistsTag));
		
		assertThat(res.getStatusInfo().getStatusCode()).isBetween(200, 300);
		verify(mealDao).untagMeal(any(), any());
	}
	
	@Test
	public void testCantUntagMeal() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/meal/untag")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.header("Authorization", "Bearer test")
				.post(Entity.json(invalidTag));
		
		assertThat(res.getStatusInfo().getStatusCode()).isEqualTo(304);
		verify(mealDao, never()).untagMeal(any(), any());
	}
	
	@Test
	public void testAlreadyUntagged() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/meal/untag")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.header("Authorization", "Bearer test")
				.post(Entity.json(tag));
		
		assertThat(res.getStatusInfo().getStatusCode()).isEqualTo(304);
		verify(mealDao, never()).untagMeal(any(), any());
	}
	
	@Test
	public void testGetNutrition() throws Exception {
		// TODO Test that
	}
}
