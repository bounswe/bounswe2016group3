package bounswegroup3.resource;

import static bounswegroup3.utils.TestUtils.registerAuth;
import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import bounswegroup3.auth.DummyAuthenticator;
import bounswegroup3.db.MealDAO;
import bounswegroup3.db.MenuDAO;
import io.dropwizard.testing.junit.ResourceTestRule;

public class MenuResourceTest {
	private static MenuDAO menuDao = mock(MenuDAO.class);
	private static MealDAO mealDao = mock(MealDAO.class);
	
	@Rule
	public ResourceTestRule rule = registerAuth(new DummyAuthenticator())
		.addResource(new MenuResource(menuDao, mealDao))
		.build();
	
	@Before
	public void setup() {
		
	}
	
	@After
	public void tearDown() {
		reset(menuDao);
		reset(mealDao);
	}
	
	@Test
	public void testMenuById() {
		
	}
}
