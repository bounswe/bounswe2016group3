package bounswegroup3.db;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import bounswegroup3.model.Event;
import bounswegroup3.model.User;
import bounswegroup3.utils.H2JDBIRule;
import io.dropwizard.jackson.Jackson;

public class EventDAOTest {
	@Rule
	public H2JDBIRule db = new H2JDBIRule();
	
	private EventDAO dao;
	private ObjectMapper mapper;
	private Event evt;
	
	@Before
	public void setup() throws Exception {
		dao = db.getDbi().onDemand(EventDAO.class);
		mapper = Jackson.newObjectMapper();
		
		User u = mapper.readValue(fixture("fixtures/user.json"), User.class);
		db.getDbi().onDemand(UserDAO.class).addUser(u);
		
		evt = mapper.readValue(fixture("fixtures/event.json"), Event.class);
		evt.setUserId(1l);
		
		// follow self
		db.getDbi().onDemand(UserDAO.class).followUser(1l, 1l);
	}
	
	@After
	public void tearDown() throws Exception {
		
	}
	
	@Test
	public void testCreateEvent() throws Exception {
		dao.createEvent(evt);
		
		assertThat(evt.getId()).isEqualTo(1l);
	}
	
	@Test
	public void testHomepageEvents() throws Exception {
		List<Event> evs = dao.homepageEvents(1l);
		
		assertThat(evs.size()).isEqualTo(0);
		
		dao.createEvent(evt);
		// make sure that the two events have separate times
		Thread.sleep(1000);
		dao.createEvent(evt);
		
		evs = dao.homepageEvents(1l);
		
		assertThat(evs.size()).isEqualTo(2);
		assertThat(evs.get(1).getId()).isGreaterThan(evs.get(0).getId());
 	}
}
