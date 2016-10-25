package bounswegroup3.auth;

import java.util.Optional;
import java.util.UUID;

import bounswegroup3.db.AccessTokenDAO;
import bounswegroup3.model.AccessToken;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;

public class OAuthAuthenticator implements Authenticator<String, AccessToken> {

    private AccessTokenDAO accessTokenDao;

    /**
     * Gets a token from the Authorization header, checks if it matches
     * a token in the database, and returns true if it does.
     */
    @Override
    public Optional<AccessToken> authenticate(String token) throws AuthenticationException {
        UUID tokenUUID;
        try {
            tokenUUID = UUID.fromString(token);
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }

        AccessToken accessToken = accessTokenDao.getAccessToken(tokenUUID);

        if (accessToken == null) {
            return Optional.empty();
        } else {
            accessTokenDao.updateLastAccessTime(accessToken);
            return Optional.of(accessToken);
        }
    }

    public OAuthAuthenticator(AccessTokenDAO accessTokenDao) {
        super();
        this.accessTokenDao = accessTokenDao;
    }
}
