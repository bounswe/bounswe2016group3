package bounswegroup3.model;

import static io.dropwizard.testing.FixtureHelpers.fixture;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.dropwizard.jackson.Jackson;

public class RatingsTest {
	private static final ObjectMapper mapper = Jackson.newObjectMapper();

	@Test
	public void testSerialize() throws Exception {
		Ratings rate = new Ratings(0.0f , 0, 0.0f);
		
		assertThat(mapper.writeValueAsString(rate)).isEqualTo(fixture("fixtures/ratings_serialized.json"));
	}
}
