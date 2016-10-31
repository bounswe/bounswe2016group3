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
import bounswegroup3.client.AmazonClient;
import bounswegroup3.client.FacebookClient;
import bounswegroup3.db.AccessTokenDAO;
import bounswegroup3.db.FailedLoginDAO;
import bounswegroup3.db.UserDAO;
import bounswegroup3.model.FacebookUser;
import bounswegroup3.model.LoginCredentials;
import bounswegroup3.model.User;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;

public class SessionResourceTest {
	private static AccessTokenDAO accessTokenDao = mock(AccessTokenDAO.class);
	private static UserDAO userDao = mock(UserDAO.class);
	private static FailedLoginDAO failedLoginDao = mock(FailedLoginDAO.class);
	private static FacebookClient client = mock(FacebookClient.class);
	private static AmazonClient s3 = mock(AmazonClient.class);
	
	@Rule
	public ResourceTestRule rule = registerAuth(new DummyAuthenticator())
		.addResource(new SessionResource(accessTokenDao, userDao, failedLoginDao, client, s3))
		.build();
	private User user;
	private FacebookUser fbUser;
	private LoginCredentials creds;
	private ObjectMapper mapper;
	
	@Before
	public void setup() throws Exception {
		mapper = Jackson.newObjectMapper();

		user = mapper.readValue(fixture("fixtures/user.json"), User.class);
		fbUser = new FacebookUser(42l, "test@deneme.com", "", "", "");
		creds = new LoginCredentials("test@deneme.com", "123456");		
		
		FacebookUser invalidFbUser = new FacebookUser(42l, "not@exists.com", "", "", "");
		
		ArrayList<User> users = new ArrayList<User>();
		users.add(user);
		
		User tooManyLoginsUser = new User();
		tooManyLoginsUser.setId(42l);
		tooManyLoginsUser.setPassword("123456");
		
		when(userDao.getUserByEmail(any())).thenReturn(user);
		when(userDao.getUserByEmail(eq("toomany@logins.com"))).thenReturn(tooManyLoginsUser);
		when(userDao.getUserByEmail(eq("not@exists.com"))).thenReturn(null);
		
		when(userDao.getUserById(any())).thenReturn(user);
		
		when(failedLoginDao.attemptsInLastFiveMinutes(any())).thenReturn(0l);
		when(failedLoginDao.attemptsInLastFiveMinutes(eq(42l))).thenReturn(6l);
		
		when(client.getUserIdByToken(any())).thenReturn(1l);
		when(client.getUserIdByToken(eq("notexists"))).thenReturn(2l);
		when(client.getUserIdByToken(eq("nope"))).thenReturn(0l);
		
		when(client.getPersonalInfo(any(), any())).thenReturn(fbUser);
		when(client.getPersonalInfo(eq(2l), any())).thenReturn(invalidFbUser);
	}
	
	@After
	public void tearDown() {
		reset(accessTokenDao);
		reset(userDao);
		reset(failedLoginDao);
	}
	
	@Test
	public void testLogin() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/session/login")
				.request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.json(creds));
		
		assertThat(res.getStatusInfo().getStatusCode()).isBetween(200, 300);
		
		verify(userDao).getUserByEmail(any());
		verify(failedLoginDao).attemptsInLastFiveMinutes(any());
	}
	
	@Test
	public void testUnsuccessfulLogin() throws Exception {
		LoginCredentials tmp = new LoginCredentials("test", "nope");
		Response res = rule.getJerseyTest()
				.target("/session/login")
				.request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.json(tmp));
		
		assertThat(res.getStatusInfo().getStatusCode()).isEqualTo(401);
		verify(failedLoginDao).addAttempt(any());
	}
	
	@Test
	public void testTooManyAttempts() throws Exception {
		LoginCredentials tmp = new LoginCredentials("toomany@logins.com", "123456");
		
		Response res = rule.getJerseyTest()
				.target("/session/login")
				.request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.json(tmp));
		
		assertThat(res.getStatusInfo().getStatusCode()).isEqualTo(401);
	}
	
	@Test
	public void testCurrentUser() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/session/currentUser")
				.request().accept(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer test")
				.get();
		
		@SuppressWarnings("rawtypes")
		LinkedHashMap read = mapper.readValue(res.readEntity(String.class), LinkedHashMap.class);
		
		assertThat(res.getStatusInfo().getStatusCode()).isEqualTo(200);
		assertThat(read.get("email")).isEqualTo(user.getEmail());
	}
	
	@Test
	public void testLogout() throws Exception {
		rule.getJerseyTest()
			.target("/session/logout")
			.request().accept(MediaType.APPLICATION_JSON)
			.header("Authorization", "Bearer test")
			.post(Entity.json(null));
	
		verify(accessTokenDao).deleteAccessToken(any());
	}
	
	@Test
	public void testFacebookLoginNewUser() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/session/fbLogin")
				.request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.json("notexists"));
		
		assertThat(res.getStatusInfo().getStatusCode()).isBetween(200, 300);
		
		verify(userDao).addUser(any());
		verify(accessTokenDao).generateToken(any());
		
		verify(client).downloadImage(any());
		verify(s3).uploadFile(any());
	}
	
	@Test
	public void testFacebookLoginExistingUser() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/session/fbLogin")
				.request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.json("test"));
		
		assertThat(res.getStatusInfo().getStatusCode()).isBetween(200, 300);
		
		verify(userDao, never()).addUser(any());
		verify(accessTokenDao).generateToken(any());
		
		verify(client, never()).downloadImage(any());
		verify(s3, never()).uploadFile(any());
	}
	
	@Test
	public void testFacebookLoginFail() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/session/fbLogin")
				.request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.json("nope"));
		
		assertThat(res.getStatusInfo().getStatusCode()).isBetween(400, 500);
		verify(accessTokenDao, never()).generateToken(any());
	}
}
