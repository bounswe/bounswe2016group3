package bounswegroup3.model;

import static io.dropwizard.testing.FixtureHelpers.fixture;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.dropwizard.jackson.Jackson;

public class AnswerCredentialsTest {
	private static final ObjectMapper mapper = Jackson.newObjectMapper();

	@Test
	public void testDeserialize() throws Exception {
		AnswerCredentials ac = new AnswerCredentials(-1l, "test ac");
		AnswerCredentials fixture = mapper.readValue(fixture("fixtures/answer_credentials.json"), AnswerCredentials.class);
		
		assertThat(ac.getUserId()).isEqualTo(fixture.getUserId());
		assertThat(ac.getAnswer()).isEqualTo(fixture.getAnswer());
	}
}
