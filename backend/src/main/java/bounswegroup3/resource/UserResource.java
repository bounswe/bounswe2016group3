package bounswegroup3.resource;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import bounswegroup3.model.AccessToken;
import bounswegroup3.model.AnswerCredentials;
import bounswegroup3.model.Comment;
import bounswegroup3.model.Follow;
import bounswegroup3.model.Meal;
import bounswegroup3.model.Menu;
import bounswegroup3.model.User;
import io.dropwizard.auth.Auth;
import bounswegroup3.client.AmazonClient;
import bounswegroup3.constant.UserType;
import bounswegroup3.db.CommentDAO;
import bounswegroup3.db.ExcludeDAO;
import bounswegroup3.db.MealDAO;
import bounswegroup3.db.MenuDAO;
import bounswegroup3.db.UserDAO;
import bounswegroup3.mail.Mailer;
import bounswegroup3.mail.Template;

import java.io.InputStream;
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
    private AmazonClient s3;
    private ExcludeDAO excludeDao;
    private CommentDAO commentDao;
    
    public UserResource(UserDAO dao, MenuDAO menuDao, MealDAO mealDao, ExcludeDAO excludeDao, CommentDAO commentDao, Mailer mailer, AmazonClient s3) {
        this.dao = dao;
        this.menuDao = menuDao;
        this.mealDao = mealDao;
        this.excludeDao = excludeDao;
        this.commentDao = commentDao;
        this.mailer = mailer;
        this.s3 = s3;
    }

    /**
     * <code>GET /api/user</code>
     * <br>
     * @return A list of all the users
     */
    @GET
    public List<User> getUsers() {
        return dao.getUsers();
    }

    /**
     * <code>POST /api/user</code>
     * <br>
     * Creates a new user object(i.e. does a signup operation), you need to
     * include the password and the secret answer in plaintext as well.
     * No need to include the id or the avatar url in the request though,
     * they are ignored.
     * @param user The User object to be created
     * @return The created user object, including its id column
     */
    @POST
    public Response addUser(@Valid User user) {    	
        if(dao.userExistsByEmail(user.getEmail())) {
        	return Response.notModified().build();
        }

    	Long id = dao.addUser(user);
        
        user.setId(id);
        
        Template tpl = new Template("/welcome.st");
        tpl.add("name", user.getFullName());
        
        mailer.sendMail(user.getEmail(), "Welcome", tpl.render());
        
        return Response.ok(user).build();
    }

    /**
     * <code>POST /api/user/update</code>
     * <br>
     * Updates an existing User profile. It fails if you aren't allowed to modify that user
     * @param token Requires authentication
     * @param user The user to modify, including the non-modified fields
     * @return The updated user, if successful
     */
    @POST
    @Path("/update")
    public Response updateUser(@Auth AccessToken token, @Valid User user) {
        if (token.getUserId() == user.getId()) {
            dao.updateUser(user);
            return Response.ok(user).build();
        } else {
        	return Response.notModified().build();
        }
    }

    /**
     * <code>GET /api/user/:id</code>
     * <br>
     * Gets a user by its id
     * @return A user object
     */
    @GET
    @Path("/{id}")
    public User getUser(@PathParam("id") Long id) {
        return dao.getUserById(id);
    }
    
    /**
     * <code>POST /api/user/byEmail</code>
     * <br>
     * Gets a user by its email address
     * @return A user object
     */
    @POST
    @Path("/byEmail")
    public User getUserByEmail(String email){
    	return dao.getUserByEmail(email);
    }
    
    /**
     * <code>POST /api/user/:id/ban</code>
     * <br>
     * Bans a user from posting if you are an administrator.
     * Returns 204 on success and 304 on failure
     */
    @POST
    @Path("/{id}/ban")
    public Response banUser(@Auth AccessToken token, @PathParam("id") Long id){
    	if(UserType.values()[dao.getUserById(token.getUserId()).getUserType()] == UserType.ADMIN){
    		dao.banUser(id);
    		return Response.ok().build();
    	}
    	return Response.notModified().build();
    }
    
    /**
     * <code>POST /api/user/resetPassword</code>
     * <br>
     * Takes an AnswerCredentials object as input, and checks if that matches 
     * the secret answer hash of the user. If it succeeds, a new password for the
     * User is generated, it is sent to the user's email address, and a 200
     * response is returned. On failure, the call returns a 304.
     * @param answer an AnswerCredentials object
     */
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
			e.printStackTrace();
			return Response.serverError().build();
		}
    }
    
    /**
     * <code>POST /api/user/:id/follow</code>
     * <br>
     * Follow another user. If that is not possible (nonexistent user or someone 
     * you're already following) the call fails. A 200 response is returned on 
     * success and a 304 on failure
     * @param token Authentication required
     * @return A Follow object, if successful
     */
    @POST
    @Path("/{id}/follow")
    public Response followUser(@Auth AccessToken token, @PathParam("id") Long id){
    	if(dao.follows(token.getUserId(), id) || !dao.userExists(id)){
    		return Response.notModified().build();
    	}
    	
    	dao.followUser(token.getUserId(), id);
    	return Response.ok(new Follow(token.getUserId(), id)).build();
    }
    
    /**
     * <code>POST /api/user/:id/unfollow</code>
     * <br>
     * Unfollow another user. If that is not possible (nonexistent user or someone 
     * you're not already following) the call fails. A 204 response is returned on 
     * success and a 304 on failure
     * @param token Authentication required
     */
    @POST
    @Path("/{id}/unfollow")
    public Response unfollowUser(@Auth AccessToken token, @PathParam("id") Long id){
    	if(!dao.follows(token.getUserId(), id)){
    		return Response.notModified().build();
    	}
    	
    	dao.unfollowUser(token.getUserId(), id);
    	return Response.ok().build();
    }
    
    /**
     * <code>GET /api/user/:id/followers</code>
     * <br>
     * Fetches all the users that follow the specified user.
     * @return A list of User objects
     */
    @GET
    @Path("/{id}/followers")
    public Response getFollowers(@PathParam("id") Long id){
    	if(!dao.userExists(id)){
    		return Response.noContent().build();
    	}
    	
    	return Response.ok(dao.getFollowers(id)).build();
    }
    
    /**
     * <code>GET /api/user/:id/following</code>
     * <br>
     * Fetches all the users that are followed by the specified user.
     * @return A list of User objects
     */
    @GET
    @Path("/{id}/following")
    public Response getFollowing(@PathParam("id") Long id){
    	if(!dao.userExists(id)){
    		return Response.noContent().build();
    	}
    	
    	return Response.ok(dao.getFollowing(id)).build();
    }
    
    /**
     * <code>GET /api/user/:id/menus</code>
     * <br>
     * Fetches a list of menus created by the specified user
     * @return A list of Menu objects
     */
    @GET
    @Path("/{id}/menus")
    public List<Menu> menusByUser(@PathParam("id") Long id){
    	return menuDao.menusByUser(id);
    }
    
    /**
     * <code>GET /api/user/:id/meals</code>
     * <br>
     * Fetches a list of meals created by the specified user
     * @return A list of Meal objects
     */
    @GET
    @Path("/{id}/meals")
    public List<Meal> mealsByUser(@PathParam("id") Long id){
    	return mealDao.mealsByUserId(id);
    }
    
    @GET
	@Path("/{id}/comments")
	public List<Comment> commentsByUser(@PathParam("id") Long id) {
    	return commentDao.commentsByUser(id);
    }
    
    /**
     * <code>POST /api/user/avatar</code>
     * <br>
     * This one's a bit different than the other API calls.
     * In the header, you need to set content-type to <code>multipart/form-data</code>
     * instead of <code>application/json</code> but the Authorization header
     * should remain the same. And then, in the post body, instead of a JSON
     * object as a raw string, send regular form-encoded input with the parameter
     * "file" equal to the file that you want to upload. 
     * <br>
     * The call will get the file you've sent, upload that to the Amazon S3 bucket,
     * make the uploaded file publicly available, and set the current user's avatar
     * URL to the public URL of the newly uploaded file.
     * <br>
     * Returns a 204 response on success, only fails when the Authentication is wrong.
     * @param token Requires authorization.
     * @param file Uploaded file
     * @param contentDispositionHeader Uploaded file metadata
     */
    @POST
    @Path("/avatar")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response avatarUpload(@Auth AccessToken token, 
    		@FormDataParam("file") InputStream file, 
    		@FormDataParam("file") FormDataContentDisposition contentDispositionHeader){

    	if(dao.userExists(token.getUserId())) {
        	String url = s3.uploadFile(file);
        	
    		dao.updateAvatar(token.getUserId(), url);
    		return Response.ok().build();
    	} else {
    		return Response.noContent().build();
    	}
    }
    
    /**
     * <code>POST /api/user/search/:query</code>
     * <br>
     * Does a basic search (as in, only searches by email and username) 
     * in registered users.
     * @param query The string to be searched for 
     * @return A list of matching users
     */
    @GET
    @Path("/search/{query}")
    public List<User> basicSearch(@PathParam("query") String query) {
    	return dao.basicSearch(query);
    }
    
    /**
     * <code>GET /api/user/:id/include</code>
     * <br>
     * Retrieves the include list of a user.
     * @param id the user id to get includes for
     * @return A list of ingredients marked as include
     */
    @GET
    @Path("/{id}/include")
    public List<String> getIncludes(@PathParam("id") Long id) {
    	return excludeDao.getUserIncludes(id);
    }
    
    /**
     * <code>GET /api/user/:id/exclude
     * <br>
     * Retrieves the exclude list of a user.
     * @param id the user id to get excludes for
     * @return A list of ingredients marked as exclude
     */
    @GET
    @Path("/{id}/exclude")
    public List<String> getExcludes(@PathParam("id") Long id) {
    	return excludeDao.getUserExcludes(id);
    }
    
    /**
     * <code>POST /api/user/:id/include</code>
     * <br>
     * Sets the include list of a user. Requires authentication
     * @param id the user id to set includes for
     * @param xs A List of Strings, to be set as the user's includes
     */
    @POST
    @Path("/{id}/include")
    public Response updateIncludes(@Auth AccessToken token, @PathParam("id") Long id, List<String> xs) {
    	if(token.getUserId() == id) {
    		excludeDao.updateIncludes(id, xs);
    		return Response.ok().build();
    	} else {
    		return Response.notModified().build();
    	}
    }
    
    /**
     * <code>POST /api/user/:id/exclude</code>
     * <br>
     * Sets the exclude list of a user. Requires authentication
     * @param id the user id to set excludes for
     * @param xs A List of Strings, to be set as the user's excludes
     */
    @POST
    @Path("/{id}/exclude")
    public Response updateExcludes(@Auth AccessToken token, @PathParam("id") Long id, List<String> xs) {
    	if(token.getUserId() == id) {
    		excludeDao.updateExcludes(id, xs);
    		return Response.ok().build();
    	} else {
    		return Response.notModified().build();
    	}
    }
}