package bounswegroup3.model;

import org.joda.time.DateTime;

import bounswegroup3.constant.EventType;

public class Event {
	private Long id;
	
	private Long userId;
	
	private EventType type;
	private String url;
	private String description;
	
	private DateTime date;

	public Event() {
		
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

	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
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
