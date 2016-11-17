package bounswegroup3;

import javax.ws.rs.client.Client;

import org.glassfish.jersey.client.JerseyClientBuilder;
import org.junit.ClassRule;
import org.junit.Test;

import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;

public class IntegrationTest {
	/*@ClassRule
	public static final DropwizardAppRule<AppConfig> rule =
		new DropwizardAppRule<AppConfig>(App.class, ResourceHelpers.resourceFilePath("conf.yml"));
	
	public static final Client client = JerseyClientBuilder.createClient();
	*/
	@Test
	public void dummyTest() {
		// TODO actually write that
	}
}
