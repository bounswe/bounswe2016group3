package bounswegroup3;

public class AppKeyConfiguration {
	private String mailjetKey;
    private String mailjetSecret;
    
    private Long fbAppId;

	public String getMailjetKey() {
		return mailjetKey;
	}

	public void setMailjetKey(String mailjetKey) {
		this.mailjetKey = mailjetKey;
	}

	public String getMailjetSecret() {
		return mailjetSecret;
	}

	public void setMailjetSecret(String mailjetSecret) {
		this.mailjetSecret = mailjetSecret;
	}

	public Long getFbAppId() {
		return fbAppId;
	}

	public void setFbAppId(Long fbAppId) {
		this.fbAppId = fbAppId;
	}
}
