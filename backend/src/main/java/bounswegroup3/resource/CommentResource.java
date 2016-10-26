package bounswegroup3.resource;

import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.joda.time.DateTime;

import bounswegroup3.db.CommentDAO;
import bounswegroup3.model.AccessToken;
import bounswegroup3.model.Comment;
import io.dropwizard.auth.Auth;

@Path("/comment")
@Produces(MediaType.APPLICATION_JSON)
public class CommentResource {
	private CommentDAO commentDao;

	public CommentResource(CommentDAO commentDao) {
		this.commentDao = commentDao;
	}
	
	/**
	 * <code>GET /api/comment/:id</code>
	 * <br>
	 * Gets a comment by its id
	 * @param id
	 * @return A Comment object
	 */
	@GET
	@Path("/{id}")
	public Comment commentById(@PathParam("id") Long id){
		return commentDao.getCommentById(id);
	}
	
	/**
	 * <code>POST /api/comment</code>
	 * <br>
	 * Creates a new comment. If you're not allowed to create that comment,
	 * a 304 response is returned. Otherwise, a 200 response is returned
	 * @param token Requires Authentication
	 * @param comment The comment to post. Needs to contain the user id as well
	 * @return The created comment including its id, if successful
	 */
	@POST
	public Comment createComment(@Auth AccessToken token, @Valid Comment comment){
		comment.setCreationTime(DateTime.now());
		comment.setUpdateTime(DateTime.now());
		Long id = commentDao.createComment(comment);
		comment.setId(id);
		
		return comment;
	}
	
	/**
	 * <code>POST /api/comment/update</code>
	 * <br>
	 * Updates an existing comment. If you don't have write access to the specified comment,
	 * a 304 response is returned. Otherwise, 200 as usual
	 * @param token Requires authentication
	 * @param comment The comment to change. Needs to contain all the fields, including the
	 * non-modified ones.
	 * @return The updated comment, if successful.
	 */
	@POST
	@Path("/update")
	public Response updateComment(@Auth AccessToken token, @Valid Comment comment) {
		if(comment.getUserId() == token.getUserId()) {
			comment.setUpdateTime(DateTime.now());
			commentDao.updateComment(comment);
			return Response.ok(comment).build();
		} else {
			return Response.notModified().build();
		}
	}
	
	/**
	 * <code>POST /api/comment/:id/delete</code>
	 * <br>
	 * Deletes the specified comment. Returns 304 if you have no write access to the token,
	 * 200 if it could be deleted
	 * @param token Requires authentication
	 * @param id
	 */
	@POST
	@Path("/{id}/delete")
	public Response deleteComment(@Auth AccessToken token, @PathParam("id") Long id) {
		Comment comment = commentDao.getCommentById(id);
		if(comment.getUserId() == token.getUserId()) {
			commentDao.deleteComment(id);
			return Response.ok().build();
		} else {
			return Response.notModified().build();
		}
	}
}
