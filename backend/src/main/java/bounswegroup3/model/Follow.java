package bounswegroup3.model;

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
