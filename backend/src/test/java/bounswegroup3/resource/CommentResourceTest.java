package bounswegroup3.resource;

import static bounswegroup3.utils.TestUtils.registerAuth;
import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import bounswegroup3.auth.DummyAuthenticator;
import bounswegroup3.db.CommentDAO;
import io.dropwizard.testing.junit.ResourceTestRule;

public class CommentResourceTest {
	private static CommentDAO commentDao = mock(CommentDAO.class);
	
	@Rule
	public ResourceTestRule rule = registerAuth(new DummyAuthenticator())
		.addResource(new CommentResource(commentDao))
		.build();
	
	@Before
	public void setup() {
		
	}
	
	@After
	public void tearDown() {
		reset(commentDao);
	}
	
	@Test
	public void testCommentById() {
		
	}
}
