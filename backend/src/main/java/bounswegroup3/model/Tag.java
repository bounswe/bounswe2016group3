package bounswegroup3.model;

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
