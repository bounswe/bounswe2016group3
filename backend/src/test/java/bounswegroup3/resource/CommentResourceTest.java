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

import com.fasterxml.jackson.databind.ObjectMapper;

import bounswegroup3.auth.DummyAuthenticator;
import bounswegroup3.db.CommentDAO;
import bounswegroup3.model.Comment;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;

public class CommentResourceTest {
	private static CommentDAO commentDao = mock(CommentDAO.class);
	
	@Rule
	public ResourceTestRule rule = registerAuth(new DummyAuthenticator())
		.addResource(new CommentResource(commentDao))
		.build();

	private Comment comment;
	
	private ObjectMapper mapper;
	
	@Before
	public void setup() throws Exception {
		mapper = Jackson.newObjectMapper();
		comment = mapper.readValue(fixture("fixtures/comment.json"), Comment.class);
	}
	
	@After
	public void tearDown() {
		reset(commentDao);
	}
	
	@Test
	public void testCommentById() throws Exception {
		
	}
	
	@Test
	public void testCreateComment() throws Exception {
		
	}
	
	@Test
	public void testUpdateComment() throws Exception {
		
	}
	
	@Test
	public void testDeleteComment() throws Exception {
		
	}
}
