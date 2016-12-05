package bounswegroup3.db;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.amazonaws.util.json.Jackson;
import com.fasterxml.jackson.databind.ObjectMapper;

import bounswegroup3.model.User;
import bounswegroup3.utils.H2JDBIRule;

public class ExcludeDAOTest {
	@Rule
	public H2JDBIRule db = new H2JDBIRule();
	
	private ExcludeDAO dao;

	private ObjectMapper mapper;
	
	private ArrayList<String> data;
	
	@Before
	public void setup() throws Exception {
		dao = db.getDbi().onDemand(ExcludeDAO.class);
		
		mapper = Jackson.getObjectMapper();
		
		User u = mapper.readValue(fixture("fixtures/user.json"), User.class);
		db.getDbi().onDemand(UserDAO.class).addUser(u);
		
		data = new ArrayList<String>();
		data.add("test");
	}
	
	@After
	public void tearDown() {
		
	}
	
	@Test
	public void testIncludes() throws Exception {
		List<String> res = dao.getUserIncludes(1l);
		
		assertThat(res.isEmpty());
		
		dao.updateIncludes(1l, data);
		
		res = dao.getUserIncludes(1l);
		
		assertThat(res.size()).isEqualTo(1);
		assertThat(res.get(0)).isEqualTo("test");
	}
	
	@Test
	public void testExcludes() throws Exception {
		List<String> res = dao.getUserExcludes(1l);
		
		assertThat(res.isEmpty());
		
		dao.updateExcludes(1l, data);
		
		res = dao.getUserExcludes(1l);
		
		assertThat(res.size()).isEqualTo(1);
		assertThat(res.get(0)).isEqualTo("test");
	}
}
