package bounswegroup3.db;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import bounswegroup3.model.Menu;
import bounswegroup3.model.User;
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
		
		User u = mapper.readValue(fixture("fixtures/user.json"), User.class);
		db.getDbi().onDemand(UserDAO.class).addUser(u);
	}
	
	@After
	public void tearDown() throws Exception {
		
	}
	
	@Test
	public void testCrud() throws Exception {
		Menu m = mapper.readValue(fixture("fixtures/menu.json"), Menu.class);
		m.setUserId(1l);
		
		Long id = dao.createMenu(m);
		assertThat(id).isEqualTo(1l);
		
		m = dao.getMenuById(1l);
		assertThat(m.getName()).isEqualTo("test menu");
		
		ArrayList<Menu> ms = new ArrayList<Menu>(dao.menusByUser(1l));
		assertThat(ms.size()).isEqualTo(1);
		assertThat(ms.get(0).getName()).isEqualTo("test menu");
		
		dao.deleteMenu(1l);
		assertThat(dao.getMenuById(1l)).isNull();
	}
}
