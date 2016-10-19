package bounswegroup3.auth;

import bounswegroup3.model.AccessToken;
import io.dropwizard.auth.Authorizer;

public class OAuthAuthorizer implements Authorizer<AccessToken> {
	@Override
	public boolean authorize(AccessToken tok, String role) {
		return true;
	}
}
