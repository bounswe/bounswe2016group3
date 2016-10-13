package bounswegroup3.resource;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import bounswegroup3.db.CommentDAO;

@Path("/comment")
@Produces(MediaType.APPLICATION_JSON)
public class CommentResource {
	private CommentDAO commentDao;

	public CommentResource(CommentDAO commentDao) {
		this.commentDao = commentDao;
	}
	
}
