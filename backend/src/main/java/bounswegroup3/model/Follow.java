package bounswegroup3.model;

/**
 * Represents a many-to-many directed follow relationship
 * between two users. We call those two users the follower
 * and the followee
 * <br>
 * <code>{"followerId":Integer, "followeeId":Integer}</code>
 */
public class Follow {
	Long followerId;
	Long followeeId;
	
	public Follow(Long followerId, Long followeeId) {
		super();
		this.followerId = followerId;
		this.followeeId = followeeId;
	}
	
	public Follow(){
		
	}
	
	public Long getFollowerId() {
		return followerId;
	}
	
	public void setFollowerId(Long followerId) {
		this.followerId = followerId;
	}
	
	public Long getFolloweeId() {
		return followeeId;
	}
	
	public void setFolloweeId(Long followeeId) {
		this.followeeId = followeeId;
	}
	
}
