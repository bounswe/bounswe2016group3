package bounswegroup3;

public class AppKeyConfiguration {
	private String mailjetKey;
    private String mailjetSecret;
    
    private Long fbAppId;
    private String fbAppSecret;
    
    private String nutritionixAppId;
    private String nutritionixKey;

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

	public String getFbAppSecret() {
		return fbAppSecret;
	}

	public void setFbAppSecret(String fbAppSecret) {
		this.fbAppSecret = fbAppSecret;
	}

	public String getNutritionixAppId() {
		return nutritionixAppId;
	}

	public void setNutritionixAppId(String nutritionixAppId) {
		this.nutritionixAppId = nutritionixAppId;
	}

	public String getNutritionixKey() {
		return nutritionixKey;
	}

	public void setNutritionixKey(String nutritionixKey) {
		this.nutritionixKey = nutritionixKey;
	}
}
