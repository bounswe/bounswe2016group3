package bounswegroup3.model;

public class AnswerCredentials {
	private Long userId;
	private String answer;
	
	public AnswerCredentials(Long userId, String answer) {
		super();
		this.userId = userId;
		this.answer = answer;
	}
	
	public Long getUserId() {
		return userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public String getAnswer() {
		return answer;
	}
	
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	
}
