package bounswegroup3.db;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import bounswegroup3.model.User;
import bounswegroup3.utils.H2JDBIRule;
import io.dropwizard.jackson.Jackson;

public class UserDAOTest {
	@Rule
	public H2JDBIRule db = new H2JDBIRule();
	
	private UserDAO dao;
	private ObjectMapper mapper;
	
	@Before
	public void setup() throws Exception {
		mapper = Jackson.newObjectMapper();
		dao = db.getDbi().onDemand(UserDAO.class);
		
		
	}
	
	@After
	public void tearDown() throws Exception {
		
	}
	
	@Test
	public void testCrud() throws Exception {
		User u = mapper.readValue(fixture("fixtures/user.json"), User.class);
		Long id = dao.addUser(u);
		assertThat(id).isEqualTo(1l);
		
	    u = dao.getUserById(1l);
	    assertThat(u.getFullName()).isEqualTo("test deneme");
	    
	    u = dao.getUserByEmail("test@deneme.com");
	    assertThat(u.getFullName()).isEqualTo("test deneme");
	    
	    u.setBio("test bio");
	    dao.updateUser(u);
	    
	    u = dao.getUserById(1l);
	    assertThat(u.getBio()).isEqualTo("test bio");
	    
	    assertThat(dao.userExists(1l));
	    
	    dao.banUser(1l);
	    u = dao.getUserById(1l);
	    assertThat(u.getIsBanned());
	}
	
	@Test
	public void testFollow() throws Exception {
		User u = mapper.readValue(fixture("fixtures/user.json"), User.class);
		
		Long id = dao.addUser(u);
		assertThat(id).isEqualTo(1l);
		
		id = dao.addUser(u);
		assertThat(id).isEqualTo(2l);
		
		assertThat(!dao.follows(1l, 2l));
		
		assertThat(dao.getFollowers(1l).size()).isEqualTo(0);
		assertThat(dao.getFollowing(1l).size()).isEqualTo(0);
		
		dao.followUser(1l, 2l);
		
		assertThat(dao.follows(1l, 2l));
		
		assertThat(dao.getFollowing(1l).size()).isEqualTo(1);
		assertThat(dao.getFollowers(2l).size()).isEqualTo(1);
		
		assertThat(dao.getFollowing(1l).get(0).getId()).isEqualTo(2l);
		assertThat(dao.getFollowers(2l).get(0).getId()).isEqualTo(1l);
		
		dao.unfollowUser(1l, 2l);
		
		assertThat(!dao.follows(1l, 2l));
		
		assertThat(dao.getFollowers(1l).size()).isEqualTo(0);
		assertThat(dao.getFollowing(1l).size()).isEqualTo(0);
	}
}
