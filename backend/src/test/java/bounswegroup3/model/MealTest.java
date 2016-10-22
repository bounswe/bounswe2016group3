package bounswegroup3.model;

import static io.dropwizard.testing.FixtureHelpers.fixture;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.dropwizard.jackson.Jackson;

public class MealTest {
	private static final ObjectMapper mapper = Jackson.newObjectMapper();
	
	@Test
	public void testDeserialize() throws Exception {
		Meal meal = new Meal(-1l, -1l, -1l, "test meal", "test meal", "http://icons.iconarchive.com/icons/dakirby309/windows-8-metro/256/Folders-OS-User-No-Frame-Metro-icon.png");
		Meal fixture = mapper.readValue(fixture("fixtures/meal.json"), Meal.class);
		
		assertThat(meal.getMenuId()).isEqualTo(fixture.getMenuId());
		assertThat(meal.getName()).isEqualTo(fixture.getName());
		assertThat(meal.getDescription()).isEqualTo(fixture.getDescription());
		assertThat(meal.getPhotoUrl()).isEqualTo(fixture.getPhotoUrl());
	}
	
	@Test
	public void testSerialize() throws Exception {
		Meal fixture = mapper.readValue(fixture("fixtures/meal.json"), Meal.class);
		assertThat(mapper.writeValueAsString(fixture)).isEqualTo(fixture("fixtures/meal_serialized.json"));
	}
}
