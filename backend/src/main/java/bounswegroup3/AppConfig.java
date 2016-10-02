package bounswegroup3;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class AppConfig extends Configuration {
    @Valid
    @NotNull
    private DataSourceFactory database = new DataSourceFactory();

    @Valid
    @NotNull
    @NotEmpty
    private String bearerRealm;
    
    private String mailjetKey;
    private String mailjetSecret;
    
    private String mailAddress;
    
    public DataSourceFactory getDatabase() {
        return database;
    }

    public String getBearerRealm() {
        return bearerRealm;
    }

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

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}
}