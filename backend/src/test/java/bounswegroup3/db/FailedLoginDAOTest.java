package bounswegroup3.db;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import bounswegroup3.model.User;
import bounswegroup3.utils.H2JDBIRule;
import io.dropwizard.jackson.Jackson;

public class FailedLoginDAOTest {
	@Rule
	public H2JDBIRule db = new H2JDBIRule();
	
	private FailedLoginDAO dao;
	private ObjectMapper mapper;
	
	@Before
	public void setup() throws Exception {
		mapper = Jackson.newObjectMapper();
		dao = db.getDbi().onDemand(FailedLoginDAO.class);
		
		User u = mapper.readValue(fixture("fixtures/user.json"), User.class);
		db.getDbi().onDemand(UserDAO.class).addUser(u);
	}
	
	@After
	public void tearDown() throws Exception {
		
	}
	
	@Test
	public void testAtttempts() throws Exception {
		/*assertThat(dao.attemptsInLastFiveMinutes(1l)).isEqualTo(0l);
		
		dao.addAttempt(1l);
		assertThat(dao.attemptsInLastFiveMinutes(1l)).isEqualTo(1l);*/
	}
}
