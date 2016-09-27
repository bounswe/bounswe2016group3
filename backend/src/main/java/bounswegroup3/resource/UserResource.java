package bounswegroup3.resource;

import javax.ws.rs.core.MediaType;


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

import java.util.List;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
    private UserDAO dao;

    public UserResource(UserDAO dao) {
        this.dao = dao;
    }

    @GET
    public List<User> getUsers() {
        return dao.getUsers();
    }

    @POST
    public User addUser(@Valid User user) {
        Long id = dao.addUser(user);

        user.setId(id);
        
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
}