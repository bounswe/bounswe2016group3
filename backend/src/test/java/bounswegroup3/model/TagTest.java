package bounswegroup3.model;

import static io.dropwizard.testing.FixtureHelpers.fixture;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.dropwizard.jackson.Jackson;

public class TagTest {
	private static final ObjectMapper mapper = Jackson.newObjectMapper();

	@Test
	public void testDeserialize() throws Exception {
		Tag tag = new Tag(2l, -1l, "test", "test");
		Tag fixture = mapper.readValue(fixture("fixtures/tag.json"), Tag.class);
		
		assertThat(tag.getRelationType()).isEqualTo(fixture.getRelationType());
		assertThat(tag.getRelationId()).isEqualTo(fixture.getRelationId());
		assertThat(tag.getDisplayName()).isEqualTo(fixture.getDisplayName());
		assertThat(tag.getIdentifier()).isEqualTo(fixture.getIdentifier());
	}
	
	@Test
	public void testSerialize() throws Exception {
		Tag fixture = mapper.readValue(fixture("fixtures/tag.json"), Tag.class);
		assertThat(mapper.writeValueAsString(fixture)).isEqualTo(fixture("fixtures/tag_serialized.json"));
	}
}
