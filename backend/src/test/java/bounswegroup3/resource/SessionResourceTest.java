package bounswegroup3.resource;

import static bounswegroup3.utils.TestUtils.registerAuth;
import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.UUID;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import bounswegroup3.client.FacebookClient;
import bounswegroup3.db.AccessTokenDAO;
import bounswegroup3.db.FailedLoginDAO;
import bounswegroup3.db.UserDAO;
import bounswegroup3.model.AccessToken;
import bounswegroup3.model.LoginCredentials;
import bounswegroup3.model.User;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;

public class SessionResourceTest {
	private static AccessTokenDAO accessTokenDao = mock(AccessTokenDAO.class);
	private static UserDAO userDao = mock(UserDAO.class);
	private static FailedLoginDAO failedLoginDao = mock(FailedLoginDAO.class);
	private static FacebookClient client = mock(FacebookClient.class);
	
	@Rule
	public ResourceTestRule rule = registerAuth()
		.addResource(new SessionResource(accessTokenDao, userDao, failedLoginDao, client))
		.build();
	private User user;
	private LoginCredentials creds;
	private ObjectMapper mapper;
	
	@Before
	public void setup() throws Exception {
		mapper = Jackson.newObjectMapper();

		user = mapper.readValue(fixture("fixtures/user.json"), User.class);
		creds = new LoginCredentials("test@deneme.com", "123456");		
		
		ArrayList<User> users = new ArrayList<User>();
		users.add(user);
		
		when(userDao.getUserByEmail(anyString())).thenReturn(user);
		
		when(failedLoginDao.attemptsInLastFiveMinutes(anyLong())).thenReturn(0l);
	}
	
	@After
	public void tearDown() {
		reset(accessTokenDao);
		reset(userDao);
		reset(failedLoginDao);
	}
	
	@Test
	public void testLoginAndLogout() {
		Response token = rule.getJerseyTest()
				.target("/session/login")
				.request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.json(creds));
		
		verify(userDao).getUserByEmail(anyString());
		verify(failedLoginDao).attemptsInLastFiveMinutes(anyLong());
		 
		// the login method has a couple more external dependencies
		// we need to mock those as well
		//System.out.println(token.readEntity(String.class));
		/*
		resources.client()
			.target("session/logout")
			.request().accept(MediaType.APPLICATION_JSON)
			.header("Authorization", "Bearer " + token)
			.post(Entity.json(null));
		
		verify(accessTokenDao).deleteAccessToken((UUID)anyObject());
		*/
	}
	
	@Test
	public void testUnsuccessfulLogin() {
		
	}
	
	@Test
	public void testTooManyAttempts() {
		
	}
}
