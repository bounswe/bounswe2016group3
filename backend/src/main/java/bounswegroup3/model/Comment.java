package bounswegroup3.model;

import org.joda.time.DateTime;

/**
 * Represents a comment made on a meal. The contents are markdown strings,
 * to be rendered clientside. The dates are as always, unix timestamps
 * {"id":Integer, "mealId":Integer, "userId": Integer, "content": String, "creationTime":Integer, "updateTime":Integer}
 */
public class Comment {
	private Long id;
	private Long mealId;
	private Long userId;
	private String content;
	private DateTime creationTime;
	private DateTime updateTime;
	
	public Comment(){
		this.id = -1l;
	}
	
	public Comment(Long id, Long mealId, Long userId, String content, DateTime creationTime, DateTime updateTime) {
		super();
		this.id = id;
		this.mealId = mealId;
		this.userId = userId;
		this.content = content;
		this.creationTime = creationTime;
		this.updateTime = updateTime;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getMealId() {
		return mealId;
	}
	
	public void setMealId(Long mealId) {
		this.mealId = mealId;
	}
	
	public Long getUserId() {
		return userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public DateTime getCreationTime() {
		return creationTime;
	}
	
	public void setCreationTime(DateTime creationTime) {
		this.creationTime = creationTime;
	}
	
	public DateTime getUpdateTime() {
		return updateTime;
	}
	
	public void setUpdateTime(DateTime updateTime) {
		this.updateTime = updateTime;
	}
}
