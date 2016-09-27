package bounswegroup3.auth;

import java.util.UUID;

import com.google.common.base.Optional;

import bounswegroup3.db.AccessTokenDAO;
import bounswegroup3.model.AccessToken;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;

public class OAuthAuthenticator implements Authenticator<String, AccessToken> {

    private AccessTokenDAO accessTokenDao;

    @Override
    public Optional<AccessToken> authenticate(String token) throws AuthenticationException {
        UUID tokenUUID;
        try {
            tokenUUID = UUID.fromString(token);
        } catch (IllegalArgumentException e) {
            return Optional.absent();
        }

        AccessToken accessToken = accessTokenDao.getAccessToken(tokenUUID);

        if (accessToken == null) {
            return Optional.absent();
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
