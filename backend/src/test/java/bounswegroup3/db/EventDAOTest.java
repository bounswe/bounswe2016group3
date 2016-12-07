package bounswegroup3.db;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;

import com.fasterxml.jackson.databind.ObjectMapper;

import bounswegroup3.utils.H2JDBIRule;
import io.dropwizard.jackson.Jackson;

public class EventDAOTest {
	@Rule
	public H2JDBIRule db = new H2JDBIRule();
	
	private EventDAO dao;
	private ObjectMapper mapper;
	
	@Before
	public void setup() throws Exception {
		dao = db.getDbi().onDemand(EventDAO.class);
		mapper = Jackson.newObjectMapper();
	}
	
	@After
	public void tearDown() {
		
	}
}
