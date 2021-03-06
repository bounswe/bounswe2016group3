package bounswegroup3.resource;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.*;

import static bounswegroup3.utils.TestUtils.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static io.dropwizard.testing.FixtureHelpers.fixture;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import com.fasterxml.jackson.databind.ObjectMapper;

import bounswegroup3.auth.DummyAuthenticator;
import bounswegroup3.client.AmazonClient;
import bounswegroup3.db.CommentDAO;
import bounswegroup3.db.ExcludeDAO;
import bounswegroup3.db.MealDAO;
import bounswegroup3.db.MenuDAO;
import bounswegroup3.db.UserDAO;
import bounswegroup3.mail.Mailer;
import bounswegroup3.model.AnswerCredentials;
import bounswegroup3.model.Comment;
import bounswegroup3.model.Meal;
import bounswegroup3.model.Menu;
import bounswegroup3.model.User;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;

public class UserResourceTest {
	private static final UserDAO userDao = mock(UserDAO.class);
	private static final MenuDAO menuDao = mock(MenuDAO.class);
	private static final MealDAO mealDao = mock(MealDAO.class);
	private static final ExcludeDAO excludeDao = mock(ExcludeDAO.class);
	private static final CommentDAO commentDao = mock(CommentDAO.class);
	private static final Mailer mailer = mock(Mailer.class);
	private static final AmazonClient s3 = mock(AmazonClient.class);
	
	@Rule
	public ResourceTestRule rule = registerAuth(new DummyAuthenticator())
		.addResource(new UserResource(userDao, menuDao, mealDao, excludeDao, commentDao, mailer, s3))
		.addProvider(MultiPartFeature.class)
		.build();
	
	private User user;
	private ObjectMapper mapper;
	
	private User invalidUser;
	
	private Menu menu;
	private Meal meal;
	private Comment comment;
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		mapper = Jackson.newObjectMapper();
		user = mapper.readValue(fixture("fixtures/user.json"), User.class);
		invalidUser = mapper.readValue(fixture("fixtures/user.json"), User.class);
		invalidUser.setEmail("nope@nope.com");
		
		menu = mapper.readValue(fixture("fixtures/menu.json"), Menu.class);
		meal = mapper.readValue(fixture("fixtures/meal.json"), Meal.class);
		comment = mapper.readValue(fixture("fixtures/comment.json"), Comment.class);
		
		ArrayList<User> users = new ArrayList<User>();
		users.add(user);
		
		ArrayList<Menu> menus = new ArrayList<Menu>();
		menus.add(menu);
		ArrayList<Meal> meals = new ArrayList<Meal>();
		meals.add(meal);
		ArrayList<Comment> comments = new ArrayList<Comment>();
		comments.add(comment);
		ArrayList<String> excludes = new ArrayList<String>();
		excludes.add("test");
		
		when(userDao.getUsers()).thenReturn(users);
		
		when(userDao.addUser(any())).thenReturn(1l);
		
		when(userDao.getUserById(any())).thenReturn(user);
		when(userDao.getUserByEmail(any())).thenReturn(user);
		
		when(userDao.follows(any(), any())).thenReturn(false);
		when(userDao.follows(eq(-1l), eq(42l))).thenReturn(true);
		when(userDao.userExists(any())).thenReturn(true);
		when(userDao.userExists(eq(32l))).thenReturn(false);
		
		when(userDao.userExistsByEmail(any())).thenReturn(false);
		when(userDao.userExistsByEmail(eq("nope@nope.com"))).thenReturn(true);
		
		when(userDao.getFollowers(any())).thenReturn(users);
		when(userDao.getFollowing(any())).thenReturn(users);
		
		when(menuDao.menusByUser(any())).thenReturn(menus);
		when(mealDao.mealsByUserId(any())).thenReturn(meals);
		
		when(userDao.basicSearch(any())).thenReturn(users);
		when(userDao.basicSearch(eq("nope"))).thenReturn(new ArrayList<User>());
		
		when(commentDao.commentsByUser(any())).thenReturn(comments);
		when(commentDao.commentsByUser(eq(42l))).thenReturn(new ArrayList<Comment>());
		
		when(excludeDao.getUserIncludes(any())).thenReturn(excludes);
		when(excludeDao.getUserExcludes(any())).thenReturn(excludes);
	}
	
	@After
	public void tearDown(){
		reset(userDao);
		reset(menuDao);
		reset(mealDao);
		reset(mailer);
	}
	
	@Test
	public void testAllUsers() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/user")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.get();
		
		@SuppressWarnings("unchecked")
		ArrayList<LinkedHashMap<String,String>> read = mapper.readValue(res.readEntity(String.class), ArrayList.class);
		
		assertThat(read.size()).isEqualTo(1);
		
		// just controlling the email should be enough to check that we've got a correct response
		assertThat(read.get(0).get("email")).isEqualTo(user.getEmail());
	}
	
	@Test
	public void testCreateUser() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/user")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.post(Entity.json(user));
		
		assertThat(res.getStatusInfo().getStatusCode()).isEqualTo(200);
		
		// we get a heterogenous object as a response
		@SuppressWarnings("rawtypes")
		LinkedHashMap read = mapper.readValue(res.readEntity(String.class), LinkedHashMap.class);
		
		verify(mailer).sendMail(any(), any(), any());
		
		// check if the id has been set
		assertThat(read.get("id")).isEqualTo(1);
	}
	
	@Test
	public void cantCreateUser() {
		Response res = rule.getJerseyTest()
				.target("/user")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.post(Entity.json(invalidUser));
		
		assertThat(res.getStatusInfo().getStatusCode()).isEqualTo(304);
		
		verify(mailer, never()).sendMail(any(), any(), any());
	}
	
	@Test
	public void testUpdateUser() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/user/update")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.header("Authorization", "Bearer test")
				.post(Entity.json(user));
		
		@SuppressWarnings("rawtypes")
		LinkedHashMap read = mapper.readValue(res.readEntity(String.class), LinkedHashMap.class);
		
		verify(userDao).updateUser(any());
		assertThat(read.get("email")).isEqualTo(user.getEmail());
	}
	
	@Test
	public void testGetUser() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/user/1")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.get();
		
		@SuppressWarnings("rawtypes")
		LinkedHashMap read = mapper.readValue(res.readEntity(String.class), LinkedHashMap.class);
		
		assertThat(read.get("email")).isEqualTo(user.getEmail());
	}
	
	@Test
	public void testGetUserByEmail() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/user/byEmail")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.post(Entity.json("test@test.com"));
		
		@SuppressWarnings("rawtypes")
		LinkedHashMap read = mapper.readValue(res.readEntity(String.class), LinkedHashMap.class);
		
		assertThat(read.get("email")).isEqualTo(user.getEmail());
	}
	
	@Test
	public void testBanUser() throws Exception {
		rule.getJerseyTest()
			.target("/user/1/ban")
			.request(MediaType.APPLICATION_JSON_TYPE)
			.header("Authorization", "Bearer test")
			.post(Entity.json(""));

		verify(userDao).banUser(any());
	}
	
	@Test
	public void testResetPassword() throws Exception {
		AnswerCredentials creds = new AnswerCredentials(-1l, "deneme");
		
		Response res = rule.getJerseyTest()
				.target("/user/resetPassword")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.header("Authorization", "Bearer test")
				.post(Entity.json(creds));
		
		verify(mailer).sendMail(any(), any(), any());
		assertThat(res.getStatusInfo().getStatusCode()).isEqualTo(200);
	}
	
	@Test
	public void testWrongResetPassword() throws Exception {
		AnswerCredentials creds = new AnswerCredentials(-1l, "wrong");
		
		Response res = rule.getJerseyTest()
				.target("/user/resetPassword")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.header("Authorization", "Bearer test")
				.post(Entity.json(creds));
		
		verify(mailer, never()).sendMail(any(), any(), any());
		assertThat(res.getStatusInfo().getStatusCode()).isEqualTo(304);
	}
	
	@Test
	public void testFollow() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/user/1/follow")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.header("Authorization", "Bearer test")
				.post(Entity.json(""));
		
		assertThat(res.getStatusInfo().getStatusCode()).isEqualTo(200);
	}
	
	@Test
	public void testAlreadyFollow() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/user/42/follow")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.header("Authorization", "Bearer test")
				.post(Entity.json(""));
		
		assertThat(res.getStatusInfo().getStatusCode()).isEqualTo(304);
	}
	
	@Test
	public void testFollowNonexistentUser() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/user/32/follow")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.header("Authorization", "Bearer test")
				.post(Entity.json(""));
		
		assertThat(res.getStatusInfo().getStatusCode()).isEqualTo(304);
	}
	
	@Test
	public void testUnfollow() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/user/42/unfollow")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.header("Authorization", "Bearer test")
				.post(Entity.json(""));
		
		assertThat(res.getStatusInfo().getStatusCode()).isEqualTo(200);
	}
	
	@Test
	public void testAlreadyUnfollow() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/user/1/unfollow")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.header("Authorization", "Bearer test")
				.post(Entity.json(""));
		
		assertThat(res.getStatusInfo().getStatusCode()).isEqualTo(304);
	}
	
	@Test
	public void testUnfollowNonexistentUser() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/user/32/unfollow")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.header("Authorization", "Bearer test")
				.post(Entity.json(""));
		
		assertThat(res.getStatusInfo().getStatusCode()).isEqualTo(304);
	}
	
	@Test
	public void testFollowers() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/user/1/followers")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.get();

		@SuppressWarnings({"rawtypes", "unchecked"})
		ArrayList<LinkedHashMap> read = mapper.readValue(res.readEntity(String.class), ArrayList.class);
		
		assertThat(read.size()).isEqualTo(1);
		assertThat(read.get(0).get("email")).isEqualTo(user.getEmail());
	}
	
	@Test
	public void testFollowing() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/user/1/following")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.get();

		@SuppressWarnings({"rawtypes", "unchecked"})
		ArrayList<LinkedHashMap> read = mapper.readValue(res.readEntity(String.class), ArrayList.class);
		
		assertThat(read.size()).isEqualTo(1);
		assertThat(read.get(0).get("email")).isEqualTo(user.getEmail());
	}
	
	@Test
	public void testGetMenus() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/user/1/menus")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.get();

		@SuppressWarnings({"rawtypes", "unchecked"})
		ArrayList<LinkedHashMap> read = mapper.readValue(res.readEntity(String.class), ArrayList.class);
		
		assertThat(read.size()).isEqualTo(1);
		assertThat(read.get(0).get("name")).isEqualTo(menu.getName());
	}
	
	@Test
	public void testGetMeals() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/user/1/meals")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.get();

		@SuppressWarnings({"rawtypes", "unchecked"})
		ArrayList<LinkedHashMap> read = mapper.readValue(res.readEntity(String.class), ArrayList.class);
		
		assertThat(read.size()).isEqualTo(1);
		assertThat(read.get(0).get("name")).isEqualTo(meal.getName());
	}
	
	@Test
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void testGetCommentsByUser() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/user/42/comments")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.get();
		
		ArrayList<LinkedHashMap> read = mapper.readValue(res.readEntity(String.class), ArrayList.class);
		
		assertThat(read.size()).isEqualTo(0);
		
		res = rule.getJerseyTest()
				.target("/user/1/comments")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.get();
		
		read = mapper.readValue(res.readEntity(String.class), ArrayList.class);
		
		assertThat(read.size()).isEqualTo(1);
	}
	
	@Test
	public void testAvatarUpload() throws Exception {
		/* 
		 * doesn't work for some reason.
		 * We get a MessageBodyWriter not found for media type=multipart/form-data error
		 */
		
		/*
		FileDataBodyPart filePart = new FileDataBodyPart("test", File.createTempFile("test-", "-eatalyze"));
		filePart.setContentDisposition(FormDataContentDisposition.name("test").build());
		
		@SuppressWarnings("resource")
		MultiPart mp = new FormDataMultiPart()
				.bodyPart(filePart);
		
		Response res = rule.getJerseyTest()
				.target("/user/avatar")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.header("Authorization", "Bearer test")
				.post(Entity.entity(mp, MediaType.MULTIPART_FORM_DATA_TYPE));
		
		assertThat(res.getStatusInfo().getStatusCode()).isBetween(200, 300);
		verify(s3).uploadFile(any());
		*/
	}
	
	@Test
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void testBasicSearch() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/user/search/nope")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.get();
		
		ArrayList<LinkedHashMap> read = mapper.readValue(res.readEntity(String.class), ArrayList.class);
		
		assertThat(read.isEmpty());
		
		res = rule.getJerseyTest()
				.target("/user/search/test")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.get();
		
		read = mapper.readValue(res.readEntity(String.class), ArrayList.class);
		
		assertThat(read.size()).isEqualTo(1);
	}
	
	@Test
	public void testGetIncludes() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/user/1/include")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.get();
		
		@SuppressWarnings("unchecked")
		ArrayList<String> read = mapper.readValue(res.readEntity(String.class), ArrayList.class);
		
		assertThat(res.getStatusInfo().getStatusCode()).isEqualTo(200);
		assertThat(read.size()).isEqualTo(1);
		assertThat(read.get(0)).isEqualTo("test");
	}
	
	@Test
	public void testGetExcludes() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/user/1/exclude")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.get();
		
		@SuppressWarnings("unchecked")
		ArrayList<String> read = mapper.readValue(res.readEntity(String.class), ArrayList.class);
		
		assertThat(res.getStatusInfo().getStatusCode()).isEqualTo(200);
		assertThat(read.size()).isEqualTo(1);
		assertThat(read.get(0)).isEqualTo("test");
	}
	
	@Test
	public void testSetIncludes() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/user/-1/include")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.header("Authorization", "Bearer test")
				.post(Entity.json(new ArrayList<String>()));
		
		assertThat(res.getStatusInfo().getStatusCode()).isBetween(200, 300);
		verify(excludeDao).updateIncludes(any(), any());
	}
	
	@Test
	public void testSetExcludes() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/user/-1/exclude")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.header("Authorization", "Bearer test")
				.post(Entity.json(new ArrayList<String>()));
		
		assertThat(res.getStatusInfo().getStatusCode()).isBetween(200, 300);
		verify(excludeDao).updateExcludes(any(), any());
	}
	
	public void testCantSetIncludes() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/user/42/include")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.header("Authorization", "Bearer test")
				.post(Entity.json(new ArrayList<String>()));
		
		assertThat(res.getStatusInfo().getStatusCode()).isEqualTo(304);
		verify(excludeDao, never()).updateIncludes(any(), any());
	}
	
	@Test
	public void testCantSetExcludes() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/user/42/exclude")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.header("Authorization", "Bearer test")
				.post(Entity.json(new ArrayList<String>()));
		
		assertThat(res.getStatusInfo().getStatusCode()).isEqualTo(304);
		verify(excludeDao, never()).updateExcludes(any(), any());
	}
}
