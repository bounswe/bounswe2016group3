package bounswegroup3.resource;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import bounswegroup3.auth.UnauthorizedException;
import bounswegroup3.client.FacebookClient;
import bounswegroup3.db.AccessTokenDAO;
import bounswegroup3.db.FailedLoginDAO;
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
    private FailedLoginDAO failedLoginDAO;
    private FacebookClient client;
    
    public SessionResource(AccessTokenDAO accessTokenDAO, UserDAO userDAO, FailedLoginDAO failedLoginDAO, FacebookClient client) {
        super();
        this.accessTokenDAO = accessTokenDAO;
        this.userDAO = userDAO;
        this.failedLoginDAO = failedLoginDAO;
        this.client = client;
    }

    /**
     * <code>POSTT /api/session/login</code>
     * <br>
     * Gets a LoginCredentials object and then returns an answer token if the attempt is successful. 
     * If it is not, a 401 response is returned
     * @param credentials The login credentials
     * @return An access token object, if successful
     */
    @POST
    @Path("/login")
    public AccessToken login(LoginCredentials credentials) {
        User user = userDAO.getUserByEmail(credentials.getEmail());

        if (user == null) {
            throw new UnauthorizedException();
        }

        if(failedLoginDAO.attemptsInLastFiveMinutes(user.getId()) >= 5){
        	failedLoginDAO.addAttempt(user.getId());
        	throw new UnauthorizedException();
        }

        try {
            if (user.checkPassword(credentials.getPassword())) {
                return accessTokenDAO.generateToken(user.getId());
            } else {
            	failedLoginDAO.addAttempt(user.getId());
                throw new UnauthorizedException();
            }
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
        	failedLoginDAO.addAttempt(user.getId());
            throw new UnauthorizedException();
        }
    }

    /**
     * <code>POST /api/session/logout</code>
     * <br>
     * Takes an access token, and deletes it, effectively ending the session.
     * If the token is already invalid, no action is taken and a 403 response is returned.
     * @param token Requires authentication (input is the token)
     */
    @POST
    @Path("/logout")
    public void logout(@Auth AccessToken token) {
        accessTokenDAO.deleteAccessToken(token.getAccessToken());
    }
    
    /**
     * <code>POST /api/session/currentUser</code>
     * <br>
     * Returns the User pointed by the access token if it is a valid one
     * @param token Authentication required
     * @return The current user according to the token, if successful
     */
    @GET
    @Path("/currentUser")
    public User currentUser(@Auth AccessToken token) {
    	return userDAO.getUserById(token.getUserId());
    }
    
    /**
     * <code>POST /api/session/fbLogin</code>
     * <br>
     * Takes a facebook access token, checks through facebook 
     * if it is a valid one, and returns an application access token if successful
     * @param fbToken The access token given by Facebook login
     * @return An AccessToken object if successful
     */
    @POST
    @Path("/fbLogin")
    public AccessToken fbLogin(String fbToken) {
    	Long res = client.getUserIdByToken(fbToken);
    	
    	if(res != 0) {
    		return accessTokenDAO.generateToken(res);
    	} else {
    		throw new UnauthorizedException();
    	}
    }
}
