package bounswegroup3.model;

/** 
 * Represents the total ratings on a meal. As in, average, total
 * and (if applicable) current user ratings. The ratings are floating 
 * point values between 0 and 1
 * <br>
 * <code>{"average":Float, "count":Integer, "currentUser":Float}</code>
 */
public class Ratings {
	private Float average;
	private Integer count;
	private Float currentUser;
	
	public Ratings(Float average, Integer count, Float currentUser) {
		super();
		this.average = average;
		this.count = count;
		this.currentUser = currentUser;
	}

	public Float getAverage() {
		return average;
	}

	public void setAverage(Float average) {
		this.average = average;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Float getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(Float currentUser) {
		this.currentUser = currentUser;
	}
	
}
