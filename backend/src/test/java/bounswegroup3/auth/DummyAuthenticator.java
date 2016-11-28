package bounswegroup3.auth;

import java.util.Optional;

import bounswegroup3.model.AccessToken;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;

public class DummyAuthenticator implements Authenticator<String, AccessToken> {
	@Override
	public Optional<AccessToken> authenticate(String token) throws AuthenticationException {
		// returns a dummy access token
		if(token.equals("nope")) {
			return Optional.empty();
		} else if(token.equals("noauth")) {
			return Optional.of(new AccessToken(null, 42l, null, null));
		} else {
			return Optional.of(new AccessToken(null, -1l, null, null));
		}
	}
	
}
