package bounswegroup3;

import io.dropwizard.Configuration;
import io.dropwizard.client.JerseyClientConfiguration;
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
    
    @Valid
    @NotNull
    @NotEmpty
    private String name;
    
    @Valid
    @NotNull
    @NotEmpty
    private String appRoot;
    
    @Valid
    @NotNull
    private JerseyClientConfiguration httpClient = new JerseyClientConfiguration();
 
    private AppKeyConfiguration appKeys = new AppKeyConfiguration();
    
    public JerseyClientConfiguration getHttpClient() {
		return httpClient;
	}

	public void setHttpClient(JerseyClientConfiguration httpClient) {
		this.httpClient = httpClient;
	}

	public AppKeyConfiguration getAppKeys() {
		return appKeys;
	}

	public void setAppKeys(AppKeyConfiguration appKeys) {
		this.appKeys = appKeys;
	}

	private String mailAddress;
    
    public DataSourceFactory getDatabase() {
        return database;
    }

    public String getBearerRealm() {
        return bearerRealm;
    }
    
	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAppRoot() {
		return appRoot;
	}

	public void setAppRoot(String appRoot) {
		this.appRoot = appRoot;
	}
}