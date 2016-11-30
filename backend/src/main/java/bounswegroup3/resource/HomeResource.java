package bounswegroup3.resource;

import javax.ws.rs.Path;

import bounswegroup3.model.NutritionalInfo;

@Path("/home")
public class HomeResource {
	@Path("/lastweek")
	public NutritionalInfo lastWeekSummary() {
		return null;
	}
}
