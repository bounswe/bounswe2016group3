package bounswegroup3.model;

import static io.dropwizard.testing.FixtureHelpers.fixture;

import static org.assertj.core.api.Assertions.assertThat;

import org.joda.time.DateTime;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.dropwizard.jackson.Jackson;

public class CommentTest {
	private static final ObjectMapper mapper = Jackson.newObjectMapper();
	
	@Test
	public void testDeserialize() throws Exception {
		Comment comment = new Comment(-1l, -1l, -1l, "test comment", DateTime.now(), DateTime.now());
		final Comment fixture = mapper.readValue(fixture("fixtures/comment.json"), Comment.class);
		
		assertThat(comment.getMealId()).isEqualTo(fixture.getMealId());
		assertThat(comment.getUserId()).isEqualTo(fixture.getUserId());
		assertThat(comment.getContent()).isEqualTo(fixture.getContent());
		// dates not checked, they tend to cause problems
	};
	
	@Test
	public void testSerialize() throws Exception {
		final Comment fixture = mapper.readValue(fixture("fixtures/comment.json"), Comment.class);
		assertThat(mapper.writeValueAsString(fixture)).isEqualTo(fixture("fixtures/comment_serialized.json"));
	}
	
}
