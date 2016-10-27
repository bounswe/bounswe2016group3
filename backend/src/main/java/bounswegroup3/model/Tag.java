package bounswegroup3.model;

/**
 * Represents a semantic tag on a meal.
 * <br>
 * <code>{"mealId":Integer, "tag":String}</code>
 */
public class Tag {
	private Long mealId;
	private String tag;
	
	public Tag() {
		
	}
	
	public Tag(Long mealId, String tag) {
		super();
		this.mealId = mealId;
		this.tag = tag;
	}

	public Long getMealId() {
		return mealId;
	}
	
	public void setMealId(Long mealId) {
		this.mealId = mealId;
	}
	
	public String getTag() {
		return tag;
	}
	
	public void setTag(String tag) {
		this.tag = tag;
	}
}
