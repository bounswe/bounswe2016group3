package bounswegroup3.model;

import static io.dropwizard.testing.FixtureHelpers.fixture;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import bounswegroup3.constant.DietType;
import bounswegroup3.constant.UserType;
import io.dropwizard.jackson.Jackson;

public class UserTest {
	private static final ObjectMapper mapper = Jackson.newObjectMapper();
	
	@Test
	public void testDeserialize() throws Exception{
		final User user = new User(-1l, "test@deneme.com", "", "", "test deneme", 
				"bio", UserType.ADMIN, DietType.OMNIVORE, "test", "", "",
				"http://icons.iconarchive.com/icons/dakirby309/windows-8-metro/256/Folders-OS-User-No-Frame-Metro-icon.png",
				false);
		
		final User fixture = mapper.readValue(fixture("fixtures/user.json"), User.class);
		
		assertThat(fixture.getEmail()).isEqualTo(user.getEmail());
		assertThat(fixture.getFullName()).isEqualTo(user.getFullName());
		assertThat(fixture.getBio()).isEqualTo(user.getBio());
		assertThat(fixture.getUserType()).isEqualTo(user.getUserType());
		assertThat(fixture.getDietType()).isEqualTo(user.getDietType());
		assertThat(fixture.getAvatarUrl()).isEqualTo(user.getAvatarUrl());
		assertThat(fixture.getIsBanned()).isEqualTo(user.getIsBanned());
	}
	
	@Test
	public void testSerialize() throws Exception {
		final User fixture = mapper.readValue(fixture("fixtures/user.json"), User.class);
		assertThat(mapper.writeValueAsString(fixture)).isEqualTo(fixture("fixtures/user_serialized.json"));
	}
	
	@Test
	public void testLogin() throws Exception {
		final User fixture = mapper.readValue(fixture("fixtures/user.json"), User.class);
		assertThat(fixture.checkPassword("123456")).isEqualTo(true);
		assertThat(fixture.checkPassword("nope")).isEqualTo(false);
	}
	
	@Test
	public void testSecretAnswer() throws Exception {
		final User fixture = mapper.readValue(fixture("fixtures/user.json"), User.class);
		assertThat(fixture.checkSecretAnswer("deneme")).isEqualTo(true);
		assertThat(fixture.checkSecretAnswer("nope")).isEqualTo(false);
	}
}
