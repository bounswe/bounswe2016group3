package bounswegroup3.client;

import java.net.URI;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import bounswegroup3.model.Tag;
import bounswegroup3.model.WikidataResponse;
import io.dropwizard.jackson.Jackson;

public class WikidataClient implements ServiceClient {
	private static final URI AUTOCOMPLETE_URL = UriBuilder.fromUri("https://www.wikidata.org/w/api.php").build();
	private static final URI SPARQL_URL = UriBuilder.fromUri("https://query.wikidata.org/sparql").build();
	
	private Client client;

	public WikidataClient(Client client) {
		super();
		this.client = client;
	}
	
	public List<Tag> autocomplete(String text) throws Exception {
		String res = client.target(AUTOCOMPLETE_URL)
			.queryParam("action", "wbsearchentities")
			.queryParam("search", text)
			.queryParam("format", "json")
			.queryParam("language", "en")
			.queryParam("uselang", "en")
			.request()
			.get(String.class);
		
		ObjectMapper mapper = Jackson.newObjectMapper();
		return mapper.readValue(res, WikidataResponse.class).getResult();
	}

	@Override
	public boolean checkValidity() {
		return true;
	}
}
