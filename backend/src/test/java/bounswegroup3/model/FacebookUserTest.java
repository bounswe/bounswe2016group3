package bounswegroup3.model;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.dropwizard.jackson.Jackson;

public class FacebookUserTest {
	private static final ObjectMapper mapper = Jackson.newObjectMapper();
	
	@Test
	public void testSerialize() throws Exception {
		FacebookUser user = new FacebookUser(1l, "test@deneme.com", "test deneme", "", "");
		assertThat(mapper.writeValueAsString(user)).isEqualTo(fixture("fixtures/facebook_user_serialized.json"));
	}
}
