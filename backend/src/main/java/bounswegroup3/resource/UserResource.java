package bounswegroup3.resource;

import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.google.common.io.CharStreams;

import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import bounswegroup3.model.AccessToken;
import bounswegroup3.model.User;
import io.dropwizard.auth.Auth;
import bounswegroup3.db.UserDAO;
import bounswegroup3.mail.Mailer;
import java.util.List;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
    private UserDAO dao;
    private Mailer mailer;
    
    public UserResource(UserDAO dao, Mailer mailer) {
        this.dao = dao;
        this.mailer = mailer;
    }

    @GET
    public List<User> getUsers() {
        return dao.getUsers();
    }

    @POST
    public User addUser(@Valid User user) {
        Long id = dao.addUser(user);

        user.setId(id);
        
        mailer.sendMail(user.getEmail(), "Welcome", "Welcome");
        
        return user;
    }

    @POST
    @Path("/update")
    public User updateUser(@Auth AccessToken token, User user) {
        if (token.getUserId() == user.getId()) {
            dao.updateUser(user);
        }

        return user;
    }

    @GET
    @Path("/{id}")
    public User getUser(@PathParam("id") Long id) {
        return dao.getUserById(id);
    }
    
    
    @POST
    @Path("/byEmail")
    public User getUser(String email){
    	return dao.getUserByEmail(email);
    }
}