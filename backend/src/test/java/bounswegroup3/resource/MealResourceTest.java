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
import bounswegroup3.db.CheckEatDAO;
import bounswegroup3.db.CommentDAO;
import bounswegroup3.db.MealDAO;
import bounswegroup3.db.RatingDAO;
import bounswegroup3.db.TagDAO;
import bounswegroup3.db.UserDAO;
import bounswegroup3.model.Comment;
import bounswegroup3.model.Meal;
import bounswegroup3.model.NutritionalInfo;
import bounswegroup3.model.Tag;
import bounswegroup3.model.User;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;

public class MealResourceTest {
	private static MealDAO mealDao = mock(MealDAO.class);
	private static CommentDAO commentDao = mock(CommentDAO.class);
	private static UserDAO userDao = mock(UserDAO.class);
	private static NutritionixClient client = mock(NutritionixClient.class);
	private static CheckEatDAO checkeatDao = mock(CheckEatDAO.class);
	private static TagDAO tagDao = mock(TagDAO.class);
	private static RatingDAO ratingDao = mock(RatingDAO.class);
	@Rule
	public ResourceTestRule rule = registerAuth(new DummyAuthenticator())
		.addResource(new MealResource(mealDao, commentDao, checkeatDao, userDao, tagDao, ratingDao, client))
		.build();
	
	private Meal meal;
	private Comment comment;
	private Tag tag;
	private NutritionalInfo nutrition;
	private User user;
	
	private ObjectMapper mapper;
	
	private Meal invalidMeal;
	private Tag invalidTag;
	private Tag notExistsTag;
	
	@Before
	public void setup() throws Exception {
		mapper = Jackson.newObjectMapper();
		
		meal = mapper.readValue(fixture("fixtures/meal.json"), Meal.class);
		comment = mapper.readValue(fixture("fixtures/comment.json"), Comment.class);
		tag = new Tag(2l, -1l, "test", "test");
		nutrition = mapper.readValue(fixture("fixtures/nutritional_info_serialized.json"), NutritionalInfo.class);
		user = mapper.readValue(fixture("fixtures/user.json"), User.class);

		ArrayList<Comment> comments = new ArrayList<Comment>();
		comments.add(comment);
		
		ArrayList<Meal> meals = new ArrayList<Meal>();
		meals.add(meal);
		
		ArrayList<String> tagsStr = new ArrayList<String>();
		tagsStr.add("tagged");
		
		ArrayList<Tag> tags = new ArrayList<Tag>();
		tags.add(new Tag(2l, -1l, "tagged", "tagged"));
		
		when(mealDao.getMealById(any())).thenReturn(meal);
		when(mealDao.createMeal(any())).thenReturn(1l);
				
		invalidMeal = new Meal(42l, 42l, 42l, "", "", "", "");
		invalidTag = new Tag(2l, 42l, "test", "test");
		notExistsTag = new Tag(2l, 32l, "test", "tagged");
		
		when(mealDao.getMealById(eq(42l))).thenReturn(invalidMeal);
		
		when(checkeatDao.checkAte(any(), any())).thenReturn(false);
		when(checkeatDao.checkAte(any(), eq(42l))).thenReturn(true);
		
		when(ratingDao.averageRating(any())).thenReturn(0.0f);
		when(ratingDao.totalRatings(any())).thenReturn(0);
		when(ratingDao.ratingByUser(any(), any())).thenReturn(0.0f);
		
		when(ratingDao.ratedByUser(any(), any())).thenReturn(false);
		when(ratingDao.ratedByUser(any(), eq(42l))).thenReturn(true);
		
		when(tagDao.getMealsByTag(any())).thenReturn(meals);
		when(tagDao.mealTaggedWith(any(), eq("tagged"))).thenReturn(true);
		when(tagDao.getTagsByMeal(any())).thenReturn(tags);
		
		when(commentDao.commentsByMeal(any())).thenReturn(comments);
		
		when(mealDao.basicSearch(any())).thenReturn(meals);
		when(mealDao.basicSearch(eq("nope"))).thenReturn(new ArrayList<Meal>());
		
		when(client.getNutrition(any())).thenReturn(nutrition);
		
		when(userDao.getUserById(any())).thenReturn(user);
	}
	
	@After
	public void tearDown() {
		reset(mealDao);
		reset(commentDao);
		reset(checkeatDao);
		reset(userDao);
		reset(tagDao);
		reset(ratingDao);
		reset(client);
	}

	@Test
	public void testMealById() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/meal/1")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.get();
		
		@SuppressWarnings("rawtypes")
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
		
		@SuppressWarnings("rawtypes")
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
	public void unauthorizedCreateMeal() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/meal")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.header("Authorization", "Bearer noauth")
				.post(Entity.json(meal));
		
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
		
		@SuppressWarnings("rawtypes")
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
		verify(checkeatDao).checkEat(any(), any());
	}

	@Test
	public void testCantCheckEat() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/meal/42/checkeat")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.header("Authorization", "Bearer test")
				.post(Entity.json(""));
		
		assertThat(res.getStatusInfo().getStatusCode()).isEqualTo(304);
		verify(checkeatDao, never()).checkEat(eq(-1l), eq(42l));
	}
	
	@Test
	public void testCheckAte() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/meal/1/checkate/1")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.get();

		Boolean read = mapper.readValue(res.readEntity(String.class), Boolean.class);
		
		assertThat(res.getStatusInfo().getStatusCode()).isEqualTo(200);
		assertThat(read).isEqualTo(false);
	}
	
	@Test
	public void testRateMeal() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/meal/1/rate/0.0")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.header("Authorization", "Bearer test")
				.post(Entity.json(""));
		
		assertThat(res.getStatusInfo().getStatusCode()).isBetween(200, 300);
		verify(ratingDao).rateMeal(any(), any(), any());
	}
	
	@Test
	public void testAlreadyRated() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/meal/42/rate/0.0")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.header("Authorization", "Bearer test")
				.post(Entity.json(""));
		
		assertThat(res.getStatusInfo().getStatusCode()).isEqualTo(304);
		verify(ratingDao, never()).rateMeal(any(), any(), any());
	}
	
	@Test
	public void testRatings() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/meal/1/ratings")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.header("Authorization", "Bearer test")
				.get();
		
		@SuppressWarnings("rawtypes")
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
		
		@SuppressWarnings({"rawtypes", "unchecked"})
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
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		ArrayList<LinkedHashMap> read = mapper.readValue(res.readEntity(String.class), ArrayList.class);
		
		assertThat(res.getStatusInfo().getStatusCode()).isEqualTo(200);
		assertThat(read.get(0).get("identifier")).isEqualTo("tagged");
	}
	
	@Test
	public void testCommentsByMeal() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/meal/1/comments")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.header("Authorization", "Bearer test")
				.get();
		
		@SuppressWarnings({"rawtypes", "unchecked"})
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
		verify(tagDao).tagMeal(any(), any(), any());
	}
	
	@Test
	public void testCantTagMeal() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/meal/tag")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.header("Authorization", "Bearer test")
				.post(Entity.json(invalidTag));
		
		assertThat(res.getStatusInfo().getStatusCode()).isEqualTo(304);
		verify(tagDao, never()).tagMeal(any(), any(), any());
	}
	
	@Test
	public void testAlreadyTagged() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/meal/tag")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.header("Authorization", "Bearer test")
				.post(Entity.json(notExistsTag));
		
		assertThat(res.getStatusInfo().getStatusCode()).isEqualTo(304);
		verify(tagDao, never()).tagMeal(any(), any(), any());
	}
	
	@Test
	public void testUntagMeal() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/meal/untag")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.header("Authorization", "Bearer test")
				.post(Entity.json(notExistsTag));
		
		assertThat(res.getStatusInfo().getStatusCode()).isBetween(200, 300);
		verify(tagDao).untagMeal(any(), any());
	}
	
	@Test
	public void testCantUntagMeal() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/meal/untag")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.header("Authorization", "Bearer test")
				.post(Entity.json(invalidTag));
		
		assertThat(res.getStatusInfo().getStatusCode()).isEqualTo(304);
		verify(tagDao, never()).untagMeal(any(), any());
	}
	
	@Test
	public void testAlreadyUntagged() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/meal/untag")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.header("Authorization", "Bearer test")
				.post(Entity.json(tag));
		
		assertThat(res.getStatusInfo().getStatusCode()).isEqualTo(304);
		verify(tagDao, never()).untagMeal(any(), any());
	}
	
	@Test
	public void testGetNutrition() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/meal/1/nutrition")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.get();
		
		@SuppressWarnings("rawtypes")
		LinkedHashMap read = mapper.readValue(res.readEntity(String.class), LinkedHashMap.class);
		
		assertThat(res.getStatusInfo().getStatusCode()).isEqualTo(200);
		assertThat(read.get("weight")).isEqualTo(nutrition.getWeight());
	}
	
	@Test
	public void testBasicSearch() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/meal/search/test")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.get();
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		ArrayList<LinkedHashMap> read = mapper.readValue(res.readEntity(String.class), ArrayList.class);
		
		assertThat(read.size()).isEqualTo(1);
	}
	
	@Test
	public void testCantSearch() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/meal/search/nope")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.get();
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		ArrayList<LinkedHashMap> read = mapper.readValue(res.readEntity(String.class), ArrayList.class);
		
		assertThat(read.size()).isEqualTo(0);
	}
}
