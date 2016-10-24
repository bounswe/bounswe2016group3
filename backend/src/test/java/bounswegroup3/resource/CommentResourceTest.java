package bounswegroup3.resource;

import static bounswegroup3.utils.TestUtils.registerAuth;
import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.*;

import java.util.LinkedHashMap;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import bounswegroup3.auth.DummyAuthenticator;
import bounswegroup3.db.CommentDAO;
import bounswegroup3.model.Comment;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;

public class CommentResourceTest {
	private static CommentDAO commentDao = mock(CommentDAO.class);
	
	@Rule
	public ResourceTestRule rule = registerAuth(new DummyAuthenticator())
		.addResource(new CommentResource(commentDao))
		.build();

	private Comment comment;
	private Comment invalidComment;
	
	
	private ObjectMapper mapper;
	
	@Before
	public void setup() throws Exception {
		mapper = Jackson.newObjectMapper();
		comment = mapper.readValue(fixture("fixtures/comment.json"), Comment.class);
		
		invalidComment = new Comment(42l, 42l, 42l, "", null, null);
		
		when(commentDao.getCommentById(any())).thenReturn(comment);
		when(commentDao.getCommentById(eq(42l))).thenReturn(invalidComment);
		
		when(commentDao.createComment(any())).thenReturn(1l);
	}
	
	@After
	public void tearDown() {
		reset(commentDao);
	}
	
	@Test
	public void testCommentById() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/comment/1")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.get();
		
		@SuppressWarnings("rawtypes")
		LinkedHashMap read = mapper.readValue(res.readEntity(String.class), LinkedHashMap.class);
		
		assertThat(res.getStatusInfo().getStatusCode()).isEqualTo(200);
		assertThat(read.get("content")).isEqualTo(comment.getContent());
	}
	
	@Test
	public void testCreateComment() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/comment")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.header("Authorization", "Bearer test")
				.post(Entity.json(comment));
		
		@SuppressWarnings("rawtypes")
		LinkedHashMap read = mapper.readValue(res.readEntity(String.class), LinkedHashMap.class);
		
		assertThat(res.getStatusInfo().getStatusCode()).isEqualTo(200);
		assertThat(read.get("id")).isEqualTo(1);
		verify(commentDao).createComment(any());
	}
	
	@Test
	public void testUpdateComment() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/comment/update")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.header("Authorization", "Bearer test")
				.post(Entity.json(comment));
		
		assertThat(res.getStatusInfo().getStatusCode()).isEqualTo(200);
		verify(commentDao).updateComment(any());
	}
	
	@Test
	public void cantUpdateComment() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/comment/update")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.header("Authorization", "Bearer test")
				.post(Entity.json(invalidComment));
		
		assertThat(res.getStatusInfo().getStatusCode()).isEqualTo(304);
		verify(commentDao, never()).updateComment(any());
	}
	
	@Test
	public void testDeleteComment() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/comment/1/delete")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.header("Authorization", "Bearer test")
				.post(Entity.json(""));
		
		assertThat(res.getStatusInfo().getStatusCode()).isEqualTo(200);
		verify(commentDao).deleteComment(any());
	}
	
	@Test
	public void cantDeleteComment() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/comment/42/delete")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.header("Authorization", "Bearer test")
				.post(Entity.json(""));
		
		assertThat(res.getStatusInfo().getStatusCode()).isEqualTo(304);
		verify(commentDao, never()).deleteComment(any());
	}
}
