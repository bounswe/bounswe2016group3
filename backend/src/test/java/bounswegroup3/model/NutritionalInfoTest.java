package bounswegroup3.model;

import static io.dropwizard.testing.FixtureHelpers.fixture;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.dropwizard.jackson.Jackson;

public class NutritionalInfoTest {
	private static final ObjectMapper mapper = Jackson.newObjectMapper();
	
	@Test
	public void testSerialize() throws Exception {
		NutritionalInfo ni = new NutritionalInfo(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
		
		assertThat(fixture("fixtures/nutritional_info_serialized.json")).isEqualTo(mapper.writeValueAsString(ni));
	}
}
