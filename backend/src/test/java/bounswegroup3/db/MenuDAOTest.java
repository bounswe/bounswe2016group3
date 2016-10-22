package bounswegroup3.db;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import bounswegroup3.utils.H2JDBIRule;
import io.dropwizard.jackson.Jackson;

public class MenuDAOTest {
	@Rule
	public H2JDBIRule db = new H2JDBIRule();
	
	private MenuDAO dao;
	private ObjectMapper mapper;
	
	@Before
	public void setup() throws Exception {
		mapper = Jackson.newObjectMapper();
		dao = db.getDbi().onDemand(MenuDAO.class);
	}
	
	@After
	public void tearDown() throws Exception {
		
	}
	
	@Test
	public void testCrud() throws Exception {
		
	}
}
