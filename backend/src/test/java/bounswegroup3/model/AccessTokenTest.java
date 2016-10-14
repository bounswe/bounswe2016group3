package bounswegroup3.model;

import static io.dropwizard.testing.FixtureHelpers.fixture;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.dropwizard.jackson.Jackson;

public class AccessTokenTest {
	private static final ObjectMapper mapper = Jackson.newObjectMapper();
	
	@Test
	public void testSerialize() throws Exception {
		AccessToken token = new AccessToken(null, -1l, null, null);
		
		assertThat(mapper.writeValueAsString(token)).isEqualTo(fixture("fixtures/accesstoken_serialized.json"));
	}
}
