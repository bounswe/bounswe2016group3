package bounswegroup3.resource;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.*;

import java.util.ArrayList;

import javax.ws.rs.core.MediaType;

import static io.dropwizard.testing.FixtureHelpers.fixture;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import bounswegroup3.db.MealDAO;
import bounswegroup3.db.MenuDAO;
import bounswegroup3.db.UserDAO;
import bounswegroup3.mail.Mailer;
import bounswegroup3.model.User;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;

public class UserResourceTest {
	private static final UserDAO userDao = mock(UserDAO.class);
	private static final MenuDAO menuDao = mock(MenuDAO.class);
	private static final MealDAO mealDao = mock(MealDAO.class);
	private static final Mailer mailer = mock(Mailer.class);
	
	/*
	@ClassRule
	public static final ResourceTestRule resources = ResourceTestRule.builder()
		.addResource(new UserResource(userDao, menuDao, mealDao, mailer))
		.build();
	*/
	
	private User user;
	private ObjectMapper mapper;
	
	@Before
	public void setup() throws Exception {
		mapper = Jackson.newObjectMapper();
		user = mapper.readValue(fixture("fixtures/user.json"), User.class);
		
		ArrayList<User> users = new ArrayList<User>();
		users.add(user);
		
		when(userDao.getUsers()).thenReturn(users);
		
		reset(userDao);
		reset(menuDao);
		reset(mealDao);
		reset(mailer);
	}
	
	
	@Test
	public void testAllUsers() throws Exception {
		/* This is commented out until we find how to fix that
		 * and so is the static variable resources
	    ArrayList<User> res = resources.client().target("/user/").request().accept(MediaType.APPLICATION_JSON).get(ArrayList.class);
		assertThat(res.size()).isEqualTo(1);
		assertThat(res.get(0)).isEqualTo(user);*/
	}
	
}
