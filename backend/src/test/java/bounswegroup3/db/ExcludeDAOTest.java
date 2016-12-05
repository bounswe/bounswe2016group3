package bounswegroup3.db;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;

import com.amazonaws.util.json.Jackson;
import com.fasterxml.jackson.databind.ObjectMapper;

import bounswegroup3.utils.H2JDBIRule;

public class ExcludeDAOTest {
	@Rule
	public H2JDBIRule db;
	
	private ExcludeDAO dao;

	private ObjectMapper mapper;
	
	@Before
	public void setup() {
		dao = db.getDbi().onDemand(ExcludeDAO.class);
		mapper = Jackson.getObjectMapper();
	}
	
	@After
	public void tearDown() {
		
	}
}
