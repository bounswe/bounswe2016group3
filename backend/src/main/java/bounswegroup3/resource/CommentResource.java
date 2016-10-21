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
	
	@GET
	@Path("/{id}")
	public Comment commentById(@PathParam("id") Long id){
		return commentDao.getCommentById(id);
	}
	
	@POST
	public Comment createComment(@Auth AccessToken token, @Valid Comment comment){
		Long id = commentDao.createComment(comment);
		comment.setId(id);
		
		return comment;
	}
	
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
