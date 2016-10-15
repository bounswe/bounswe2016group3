package bounswegroup3.resource;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.google.common.io.CharStreams;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import bounswegroup3.model.AccessToken;
import bounswegroup3.model.AnswerCredentials;
import bounswegroup3.model.Follow;
import bounswegroup3.model.Meal;
import bounswegroup3.model.Menu;
import bounswegroup3.model.User;
import io.dropwizard.auth.Auth;
import bounswegroup3.constant.UserType;
import bounswegroup3.db.MealDAO;
import bounswegroup3.db.MenuDAO;
import bounswegroup3.db.UserDAO;
import bounswegroup3.mail.Mailer;
import bounswegroup3.mail.Template;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
    private UserDAO dao;
    private MenuDAO menuDao;
    private MealDAO mealDao;
    private Mailer mailer;
    
    public UserResource(UserDAO dao, MenuDAO menuDao, MealDAO mealDao, Mailer mailer) {
        this.dao = dao;
        this.menuDao = menuDao;
        this.mealDao = mealDao;
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
        
        Template tpl = new Template("/welcome.st");
        tpl.add("name", user.getFullName());
        
        mailer.sendMail(user.getEmail(), "Welcome", tpl.render());
        
        return user;
    }

    @POST
    @Path("/update")
    public User updateUser(@Auth AccessToken token, @Valid User user) {
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
    public Response banUser(@Auth AccessToken token, @PathParam("id") Long id){
    	if(UserType.values()[dao.getUserById(token.getUserId()).getUserType()] == UserType.ADMIN){
    		dao.banUser(id);
    		return Response.ok().build();
    	}
    	return Response.notModified().build();
    }
    
    @POST
    @Path("/resetPassword")
    public Response resetPassword(AnswerCredentials answer){
    	User user = dao.getUserById(answer.getUserId());
    	try {
			if(user!=null && user.checkSecretAnswer(answer.getAnswer())){
			    Template tpl = new Template("/password.st");
			    String res = User.generatePassword();
			    tpl.add("pwd", res);
			    mailer.sendMail(user.getEmail(), "Generated Mail", tpl.render());
			    user.setPassword(res);
			    return Response.ok().build();
			}
			return Response.notModified().build();
		} catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.serverError().build();
		}
    }
    
    @POST
    @Path("/follow/{id}")
    public Response followUser(@Auth AccessToken token, @PathParam("id") Long id){
    	if(dao.follows(token.getUserId(), id) || !dao.userExists(id)){
    		return Response.notModified().build();
    	}
    	
    	dao.followUser(token.getUserId(), id);
    	return Response.ok(new Follow(token.getUserId(), id)).build();
    }
    
    @POST
    @Path("/unfollow/{id}")
    public Response unfollowUser(@Auth AccessToken token, @PathParam("id") Long id){
    	if(!dao.follows(token.getUserId(), id)){
    		return Response.notModified().build();
    	}
    	
    	dao.unfollowUser(token.getUserId(), id);
    	return Response.ok().build();
    }
    
    @GET
    @Path("/followers/{id}")
    public Response getFollowers(@PathParam("id") Long id){
    	if(!dao.userExists(id)){
    		return Response.noContent().build();
    	}
    	
    	return Response.ok(dao.getFollowers(id)).build();
    }
    
    @GET
    @Path("/following/{id}")
    public Response getFollowing(@PathParam("id") Long id){
    	if(!dao.userExists(id)){
    		return Response.noContent().build();
    	}
    	
    	return Response.ok(dao.getFollowing(id)).build();
    }
    
    @GET
    @Path("/menus/{id}")
    public List<Menu> menusByUser(@PathParam("id") Long id){
    	return menuDao.menusByUser(id);
    }
    
    @GET
    @Path("/meals/{id}")
    public List<Meal> mealsByUser(@PathParam("id") Long id){
    	return mealDao.mealsByUserId(id);
    }
    

    @POST
    @Path("/avatar")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public void avatarUpload(@Auth AccessToken token, @FormDataParam("file") InputStream file, @FormDataParam("file") FormDataContentDisposition contentDispositionHeader){
    	System.out.println(contentDispositionHeader.getFileName());
        
        try {
            System.out.println(CharStreams.toString(new InputStreamReader(file)));
        } catch (IOException e) {
            System.out.println("wat??");

            e.printStackTrace();
        }
    }
}