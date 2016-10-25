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
    
    /**
     * @return the HTTP client configuration such as max number of connections,
     * time to live of those connections, usingf compression etc.
     */
    public JerseyClientConfiguration getHttpClient() {
		return httpClient;
	}

	public void setHttpClient(JerseyClientConfiguration httpClient) {
		this.httpClient = httpClient;
	}

	/**
	 * The app uses a number of external Services and these services generally
	 * provide us wih a appId/key pair. This is a plain object used for storing
	 * those keys
	 * @return the AppKeyConfiguration object that contains said keys
	 */
	public AppKeyConfiguration getAppKeys() {
		return appKeys;
	}

	public void setAppKeys(AppKeyConfiguration appKeys) {
		this.appKeys = appKeys;
	}

	private String mailAddress;
    
	/**
	 * @return The database connection, its name, credentials etc.
	 */
    public DataSourceFactory getDatabase() {
        return database;
    }

    /**
     * @return The Bearer realm used when dealing with OAuth etc.
     */
    public String getBearerRealm() {
        return bearerRealm;
    }
    
    /**
     * @return The mail address used when sending e-mails from the app.
     * Needs to be set to the same one as the Mailjet account
     */
	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	/**
	 * @return The name of the app
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return The root URL of the app being served
	 */
	public String getAppRoot() {
		return appRoot;
	}

	public void setAppRoot(String appRoot) {
		this.appRoot = appRoot;
	}
}