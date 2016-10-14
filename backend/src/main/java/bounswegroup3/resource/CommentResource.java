package bounswegroup3.resource;

import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
	Comment commentById(@PathParam("id") Long id){
		return commentDao.getCommentById(id);
	}
	
	@POST
	Comment createComment(@Auth AccessToken token, @Valid Comment comment){
		Long id = commentDao.createComment(comment);
		comment.setId(id);
		
		return comment;
	}
	
}
