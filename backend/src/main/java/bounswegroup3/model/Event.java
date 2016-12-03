package bounswegroup3.model;

import org.joda.time.DateTime;

import bounswegroup3.constant.EventType;
import bounswegroup3.db.EventDAO;

public class Event {
	private Long id;
	
	private Long userId;
	
	private EventType type;
	private String url;
	private String description;
	
	private DateTime date;

	public Event() {
		this.id = -1l;
		this.userId = -1l;
	}
	
	public Event(Long id, Long userId, EventType type, String url, String description, DateTime date) {
		super();
		this.id = id;
		this.userId = userId;
		this.type = type;
		this.url = url;
		this.description = description;
		this.date = date;
	}

	public static void create(EventDAO dao, Long userId, EventType type, String url) {
		dao.createEvent(new Event(-1l, userId, type, url, "test description", new DateTime()));
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public int getType() {
		return type.ordinal();
	}

	public void setType(int type) {
		this.type = EventType.values()[type];
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public DateTime getDate() {
		return date;
	}

	public void setDate(DateTime date) {
		this.date = date;
	}
}
