package bounswegroup3.model;

import org.joda.time.DateTime;

public class CheckEat {
	private Long userId;
	private Long mealId;
	
	private DateTime creationDate;

	public CheckEat(Long userId, Long mealId, DateTime creationDate) {
		super();
		this.userId = userId;
		this.mealId = mealId;
		this.creationDate = creationDate;
	}
	
	public CheckEat() {
		 
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getMealId() {
		return mealId;
	}

	public void setMealId(Long mealId) {
		this.mealId = mealId;
	}

	public DateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(DateTime creationDate) {
		this.creationDate = creationDate;
	}
	
}
