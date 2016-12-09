package bounswegroup3.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

public class WikidataResponse {
	@SuppressWarnings("rawtypes")
	LinkedHashMap searchinfo;
	@SuppressWarnings("rawtypes")
	List<LinkedHashMap> search;
	
	public List<Tag> getResult() {
		return search.stream()
				.map(hm -> new Tag(-1l, -1l, (String)hm.get("id"), hm.get("title") + " - " + hm.get("description")))
				.collect(Collectors.toCollection(ArrayList::new));
	}
}
