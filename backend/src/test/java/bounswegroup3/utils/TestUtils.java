package bounswegroup3.utils;

import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;

import bounswegroup3.auth.DummyAuthenticator;
import bounswegroup3.auth.OAuthAuthorizer;
import bounswegroup3.model.AccessToken;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.oauth.OAuthCredentialAuthFilter;
import io.dropwizard.testing.junit.ResourceTestRule;
import io.dropwizard.testing.junit.ResourceTestRule.Builder;

public class TestUtils {
	public static Builder registerAuth(){
		return ResourceTestRule.builder()
		.setTestContainerFactory(new GrizzlyWebTestContainerFactory())
		.addProvider(new AuthDynamicFeature(new OAuthCredentialAuthFilter.Builder<AccessToken>()
				.setAuthenticator(new DummyAuthenticator())
				.setAuthorizer(new OAuthAuthorizer())
				.setRealm("eatalyze")
				.setPrefix("Bearer")
				.buildAuthFilter()))
		.addProvider(RolesAllowedDynamicFeature.class)
		.addProvider(new AuthValueFactoryProvider.Binder<>(AccessToken.class));
	}
}
