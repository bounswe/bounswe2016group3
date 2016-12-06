package bounswegroup3.resource;

import static bounswegroup3.utils.TestUtils.registerAuth;
import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.amazonaws.util.json.Jackson;
import com.fasterxml.jackson.databind.ObjectMapper;

import bounswegroup3.auth.DummyAuthenticator;
import bounswegroup3.client.NutritionixClient;
import bounswegroup3.db.CheckEatDAO;
import bounswegroup3.db.EventDAO;
import bounswegroup3.db.MealDAO;
import bounswegroup3.model.CheckEat;
import bounswegroup3.model.Event;
import bounswegroup3.model.Meal;
import bounswegroup3.model.NutritionalInfo;
import io.dropwizard.testing.junit.ResourceTestRule;

public class HomeResourceTest {
	private static CheckEatDAO checkeatDao = mock(CheckEatDAO.class);
	private static MealDAO mealDao = mock(MealDAO.class);
	private static EventDAO eventDao = mock(EventDAO.class);
	private static NutritionixClient client = mock(NutritionixClient.class);
	
	@Rule
	public ResourceTestRule rule = registerAuth(new DummyAuthenticator())
		.addResource(new HomeResource(checkeatDao, mealDao, eventDao, client))
		.build();
	
	private ObjectMapper mapper;
	
	private Event evt;
	private Meal meal;
	private NutritionalInfo ni;
	
	@Before
	public void setup() throws Exception{
		mapper = Jackson.getObjectMapper();
		
		ArrayList<CheckEat> ces = new ArrayList<CheckEat>();
		ces.add(new CheckEat(1l, 1l, new DateTime()));
		ces.add(new CheckEat(1l, 1l, new DateTime()));
		
		when(checkeatDao.checkEatsFromLastWeek(any())).thenReturn(ces);
		
		evt = mapper.readValue(fixture("fixtures/event.json"), Event.class);
		ArrayList<Event> evs = new ArrayList<Event>();
		evs.add(evt);
		evs.add(evt);
		
		when(eventDao.homepageEvents(any())).thenReturn(evs);
		
		meal = mapper.readValue(fixture("fixtures/meal.json"), Meal.class);
		when(mealDao.getMealById(any())).thenReturn(meal);
		
		ni = mapper.readValue(fixture("fixtures/nutritional_info_serialized.json"), NutritionalInfo.class);
		ni.setCalories(10.0);
		
		when(client.getNutrition(any())).thenReturn(ni);
	}
	
	@After
	public void tearDown() {
		reset(checkeatDao);
		reset(mealDao);
		reset(eventDao);
		reset(client);
	}
	
	@Test
	public void testHomepage() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/home")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.header("Authorization", "Bearer test")
				.get();

		@SuppressWarnings({ "rawtypes", "unchecked" })
		ArrayList<LinkedHashMap> read = mapper.readValue(res.readEntity(String.class), ArrayList.class);
		
		assertThat(res.getStatusInfo().getStatusCode()).isEqualTo(200);
		assertThat(read.size()).isEqualTo(2);
		assertThat(read.get(0).get("description")).isEqualTo(evt.getDescription());
	}
	
	@Test
	public void testLastWeek() throws Exception {
		Response res = rule.getJerseyTest()
				.target("/home/lastweek")
				.request(MediaType.APPLICATION_JSON_TYPE)
				.header("Authorization", "Bearer test")
				.get();

		@SuppressWarnings("rawtypes")
		LinkedHashMap read = mapper.readValue(res.readEntity(String.class), LinkedHashMap.class);
		
		assertThat(res.getStatusInfo().getStatusCode()).isEqualTo(200);
		assertThat(read.get("calories")).isEqualTo(2*ni.getCalories());
	}
}
