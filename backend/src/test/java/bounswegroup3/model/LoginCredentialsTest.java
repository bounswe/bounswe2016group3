package bounswegroup3.model;

import static io.dropwizard.testing.FixtureHelpers.fixture;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.dropwizard.jackson.Jackson;

public class LoginCredentialsTest {
	private static final ObjectMapper mapper = Jackson.newObjectMapper();
	
	@Test
	public void testDeserialize() throws Exception {
		LoginCredentials lc = new LoginCredentials("test", "test");
		LoginCredentials fixture = mapper.readValue(fixture("fixtures/login_credentials.json"), LoginCredentials.class);
		
		assertThat(lc.getEmail()).isEqualTo(fixture.getEmail());
		assertThat(lc.getPassword()).isEqualTo(fixture.getPassword());
	}
}
