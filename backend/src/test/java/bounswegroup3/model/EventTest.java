package bounswegroup3.model;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;

import org.joda.time.DateTime;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import bounswegroup3.constant.EventType;
import io.dropwizard.jackson.Jackson;

public class EventTest {
	private static final ObjectMapper mapper = Jackson.newObjectMapper();
	
	@Test
	public void testDeserialize() throws Exception {
		Event event = new Event(-1l, -1l, EventType.ADD_MEAL, "test url", "test desc", DateTime.now());
		final Event fixture = mapper.readValue(fixture("fixtures/event.json"), Event.class);
		
		assertThat(event.getUserId()).isEqualTo(fixture.getUserId());
		assertThat(event.getType()).isEqualTo(fixture.getType());
		assertThat(event.getUrl()).isEqualTo(fixture.getUrl());
		assertThat(event.getDescription()).isEqualTo(fixture.getDescription());
		
		// dates not checked, they tend to cause problems
	};
	
	@Test
	public void testSerialize() throws Exception {
		final Event fixture = mapper.readValue(fixture("fixtures/event.json"), Event.class);
		assertThat(mapper.writeValueAsString(fixture)).isEqualTo(fixture("fixtures/event_serialized.json"));
	}
}
