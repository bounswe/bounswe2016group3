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
    
    public DataSourceFactory getDatabase() {
        return database;
    }

    public String getBearerRealm() {
        return bearerRealm;
    }
}