package bounswegroup3.model;

/**
 * Represents a secret answer for a password reset operation
 * The API never returns this object but the password reset call
 * requires this as an input
 * <br>
 * <code>{"userId": Integer, "answer": String}</code>
 */
public class AnswerCredentials {
	private Long userId;
	private String answer;
	
	public AnswerCredentials(Long userId, String answer) {
		super();
		this.userId = userId;
		this.answer = answer;
	}
	
	public AnswerCredentials() {
		
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
