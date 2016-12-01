package bounswegroup3.model;

import static io.dropwizard.testing.FixtureHelpers.fixture;

import static org.assertj.core.api.Assertions.assertThat;

import org.joda.time.DateTime;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.dropwizard.jackson.Jackson;

public class CheckEatTest {
	private static final ObjectMapper mapper = Jackson.newObjectMapper();
	
	@Test
	public void testDeserialize() throws Exception {
		CheckEat checkeat = new CheckEat(-1l, -1l, DateTime.now());
		CheckEat fixture = mapper.readValue(fixture("fixtures/checkeat.json"), CheckEat.class);
		
		assertThat(checkeat.getUserId()).isEqualTo(fixture.getUserId());
		assertThat(checkeat.getMealId()).isEqualTo(fixture.getMealId());
	}
	
	@Test 
	public void testSerialize() throws Exception {
		CheckEat fixture = mapper.readValue(fixture("fixtures/checkeat.json"), CheckEat.class);
		assertThat(mapper.writeValueAsString(fixture)).isEqualTo(fixture("fixtures/checkeat_serialized.json"));
	}
}
