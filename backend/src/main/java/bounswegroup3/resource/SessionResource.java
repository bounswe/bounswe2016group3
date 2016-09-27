package bounswegroup3.resource;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import bounswegroup3.auth.UnauthorizedException;
import bounswegroup3.db.AccessTokenDAO;
import bounswegroup3.db.UserDAO;
import bounswegroup3.model.AccessToken;
import bounswegroup3.model.LoginCredentials;
import bounswegroup3.model.User;
import io.dropwizard.auth.Auth;

@Path("/session")
@Produces(MediaType.APPLICATION_JSON)
public class SessionResource {
    private AccessTokenDAO accessTokenDAO;
    private UserDAO userDAO;

    public SessionResource(AccessTokenDAO accessTokenDAO, UserDAO userDAO) {
        super();
        this.accessTokenDAO = accessTokenDAO;
        this.userDAO = userDAO;
    }

    @POST
    @Path("/login")
    public AccessToken login(LoginCredentials credentials) {
        User user = userDAO.getUserByEmail(credentials.getEmail());

        if (user == null) {
            throw new UnauthorizedException();
        }

        System.out.println(user.getEmail());

        try {
            if (user.checkPassword(credentials.getPassword())) {

                return accessTokenDAO.generateToken(user.getId());
            } else {
                System.out.println("wrong password");
                throw new UnauthorizedException();
            }
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            System.out.println("exception");
            throw new UnauthorizedException();
        }
    }

    @POST
    @Path("/logout")
    public void logout(@Auth AccessToken token) {
        System.out.println("logging out");
        System.out.println(token.getAccessToken());
        System.out.println(token.getUserId());

        accessTokenDAO.deleteAccessToken(token.getAccessToken());
    }
}
