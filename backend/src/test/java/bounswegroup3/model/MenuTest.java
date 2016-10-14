package bounswegroup3.model;

import static io.dropwizard.testing.FixtureHelpers.fixture;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.dropwizard.jackson.Jackson;

public class MenuTest {
	private static final ObjectMapper mapper = Jackson.newObjectMapper();
	
	@Test
	public void testDeserialize() throws Exception {
		Menu menu = new Menu(-1l, -1l, "test menu");
		Menu fixture = mapper.readValue(fixture("fixtures/menu.json"), Menu.class);
		
		assertThat(menu.getUserId()).isEqualTo(fixture.getUserId());
		assertThat(menu.getName()).isEqualTo(fixture.getName());
	}
	
	@Test
	public void testSerialize() throws Exception {
		Menu fixture = mapper.readValue(fixture("fixtures/menu.json"), Menu.class);
		assertThat(mapper.writeValueAsString(fixture)).isEqualTo(fixture("fixtures/menu_serialized.json"));
	}
	
}
