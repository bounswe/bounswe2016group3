package bounswegroup3.resource;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.*;

import static bounswegroup3.utils.TestUtils.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static io.dropwizard.testing.FixtureHelpers.fixture;

import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import com.fasterxml.jackson.databind.ObjectMapper;

import bounswegroup3.auth.DummyAuthenticator;
import bounswegroup3.auth.OAuthAuthorizer;
import bounswegroup3.db.MealDAO;
import bounswegroup3.db.MenuDAO;
import bounswegroup3.db.UserDAO;
import bounswegroup3.mail.Mailer;
import bounswegroup3.model.AccessToken;
import bounswegroup3.model.User;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.oauth.OAuthCredentialAuthFilter;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;

public class UserResourceTest {
	private static final UserDAO userDao = mock(UserDAO.class);
	private static final MenuDAO menuDao = mock(MenuDAO.class);
	private static final MealDAO mealDao = mock(MealDAO.class);
	private static final Mailer mailer = mock(Mailer.class);
	
	@Rule
	public ResourceTestRule rule = registerAuth()
		.addResource(new UserResource(userDao, menuDao, mealDao, mailer))
		.build();
	
	private User user;
	private ObjectMapper mapper;
	
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		mapper = Jackson.newObjectMapper();
		user = mapper.readValue(fixture("fixtures/user.json"), User.class);
		
		ArrayList<User> users = new ArrayList<User>();
		users.add(user);
		
		when(userDao.getUsers()).thenReturn(users);
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
		Response res = rule.getJerseyTest().target("/user").request(MediaType.APPLICATION_JSON_TYPE).get();
		
		ArrayList<LinkedHashMap<String,String>> read = mapper.readValue(res.readEntity(String.class), ArrayList.class);
		
		assertThat(read.size()).isEqualTo(1);
		
		// just controlling the email should be enough to check that we've got a correct response
		assertThat(read.get(0).get("email")).isEqualTo(user.getEmail());
	}
	
}
