package bounswegroup3.resource;

import javax.ws.rs.core.MediaType;

import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import bounswegroup3.model.AccessToken;
import bounswegroup3.model.AnswerCredentials;
import bounswegroup3.model.User;
import io.dropwizard.auth.Auth;
import bounswegroup3.constant.UserType;
import bounswegroup3.db.UserDAO;
import bounswegroup3.mail.Mailer;
import bounswegroup3.mail.Template;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
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
        
        Template tpl = new Template("welcome.st");
        tpl.add("name", user.getFullName());
        
        mailer.sendMail(user.getEmail(), "Welcome", tpl.render());
        
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
    
    @POST
    @Path("/ban/{id}")
    public void banUser(@Auth AccessToken token, @PathParam("id") Long id){
    	if(UserType.values()[dao.getUserById(token.getUserId()).getUserType()] == UserType.ADMIN){
    		dao.banUser(id);
    	}
    }
    
    @POST
    @Path("/resetPassword")
    public void resetPassword(@Auth AccessToken token, AnswerCredentials answer){
    	User user = dao.getUserById(token.getUserId());
    	try {
			if(user!=null && user.checkSecretAnswer(answer.getAnswer())){
			    Template tpl = new Template("password.st");
			    String res = User.generatePassword();
			    tpl.add("pwd", res);
			    mailer.sendMail(user.getEmail(), "Generated Mail", tpl.render());
			    user.setPassword(res);
			}
		} catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}