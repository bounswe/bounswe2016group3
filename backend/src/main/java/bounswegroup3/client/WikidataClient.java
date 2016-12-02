package bounswegroup3.client;

import javax.ws.rs.client.Client;

public class WikidataClient implements ServiceClient {
	private static final String AUTOCOMPLETE_URL = "https://www.wikidata.org/w/api.php";
	private static final String SPARQL_URL = "https://query.wikidata.org/sparql";
	
	private Client client;

	public WikidataClient(Client client) {
		super();
		this.client = client;
	}
	
	public void autocomplete() {
		//TODO implement that
	}

	@Override
	public boolean checkValidity() {
		// TODO Auto-generated method stub
		return true;
	}
}
