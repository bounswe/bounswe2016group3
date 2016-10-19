package bounswegroup3.auth;

import java.util.Optional;

import bounswegroup3.model.AccessToken;
import bounswegroup3.model.User;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;

public class DummyAuthenticator implements Authenticator<String, AccessToken> {
	@Override
	public Optional<AccessToken> authenticate(String token) throws AuthenticationException {
		// returns a dummy access token
		return Optional.of(new AccessToken(null, -1l, null, null));
	}
	
}
