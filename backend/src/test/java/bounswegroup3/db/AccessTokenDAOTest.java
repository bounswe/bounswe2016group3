package bounswegroup3.db;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import bounswegroup3.model.AccessToken;
import bounswegroup3.model.User;
import bounswegroup3.utils.H2JDBIRule;
import io.dropwizard.jackson.Jackson;

public class AccessTokenDAOTest {
	@Rule
	public H2JDBIRule db = new H2JDBIRule();
	
	private AccessTokenDAO dao;
	private ObjectMapper mapper;
	
	@Before
	public void setup() throws Exception {
		dao = db.getDbi().onDemand(AccessTokenDAO.class);
		mapper = Jackson.newObjectMapper();
		
		User u = mapper.readValue(fixture("fixtures/user.json"), User.class);
		db.getDbi().onDemand(UserDAO.class).addUser(u);
	}
	
	@After
	public void tearDown() throws Exception {
		// we don't need to delete the user
		// since all tables are dropped anyway
	}
	
	@Test
	public void testCrud() throws Exception {
		AccessToken tok = dao.generateToken(1l);
		tok = dao.getAccessToken(tok.getAccessToken());
		
		assertThat(tok.getUserId()).isEqualTo(1l);
		
		dao.updateLastAccessTime(tok);
		assertThat(tok.getLastAccessTime().isAfterNow());
		
		dao.deleteAccessToken(tok.getAccessToken());
		assertThat(dao.getAccessToken(tok.getAccessToken())).isNull();
	}
}
