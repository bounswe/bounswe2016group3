package bounswegroup3.auth;

import bounswegroup3.model.AccessToken;
import io.dropwizard.auth.Authorizer;

public class OAuthAuthorizer implements Authorizer<AccessToken> {
	
	/**
	 * Currently doesn't even look at the role and returns true.
	 */
	@Override
	public boolean authorize(AccessToken tok, String role) {
		return true;
	}
}
