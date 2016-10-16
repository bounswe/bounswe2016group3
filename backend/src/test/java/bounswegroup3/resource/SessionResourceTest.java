package bounswegroup3.resource;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.UUID;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import org.apache.http.client.HttpClient;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.research.ws.wadl.Response;

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
	private static HttpClient httpClient = mock(HttpClient.class);
	@ClassRule
	public static final ResourceTestRule resources = ResourceTestRule.builder()
		.addResource(new SessionResource(accessTokenDao, userDao, failedLoginDao, httpClient))
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
		
		reset(accessTokenDao);
		reset(userDao);
		reset(failedLoginDao);
	}
	
	@Test
	public void testLoginAndLogout() {
		/*
		String token = resources.client()
				.target("/session/login")
				.request().accept(MediaType.APPLICATION_JSON)
				.post(Entity.json(creds),AccessToken.class)
				.getAccessToken()
				.toString();
		
		verify(userDao).getUserByEmail(anyString());
		verify(failedLoginDao).attemptsInLastFiveMinutes(anyLong());
		
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
