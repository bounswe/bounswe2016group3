package bounswegroup3.model;

import static io.dropwizard.testing.FixtureHelpers.fixture;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.dropwizard.jackson.Jackson;

public class FollowTest {
	private static final ObjectMapper mapper = Jackson.newObjectMapper();
	
	@Test
	public void testSerialize() throws Exception {
		Follow follow = new Follow(-1l, -1l);
		assertThat(mapper.writeValueAsString(follow)).isEqualTo(fixture("fixtures/follow_serialized.json"));
	}
}
