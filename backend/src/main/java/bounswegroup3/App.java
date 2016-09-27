package bounswegroup3;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.jdbi.OptionalContainerFactory;
import io.dropwizard.auth.AuthFactory;
import io.dropwizard.auth.oauth.OAuthFactory;

import org.eclipse.jetty.servlets.CrossOriginFilter;

import java.util.EnumSet;
import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration.Dynamic;

import org.skife.jdbi.v2.DBI;

import bounswegroup3.auth.OAuthAuthenticator;
import bounswegroup3.db.AccessTokenDAO;
import bounswegroup3.db.UserDAO;
import bounswegroup3.model.AccessToken;
import bounswegroup3.resource.UserResource;

class App extends Application<AppConfig> {
	public static void main(String[] args) throws Exception {
		new App().run(args);
	}
	

    @Override
    public String getName() {
        return "temporary name";
    }

    @Override
    public void initialize(Bootstrap<AppConfig> bootstrap) {
        bootstrap.addBundle(new AssetsBundle("/assets/", "/", "index.html"));
        

        bootstrap.addBundle(new MigrationsBundle<AppConfig>() {
            @Override
            public DataSourceFactory getDataSourceFactory(AppConfig config) {
                return config.getDatabase();
            }
        });
    }
    
	@Override
	public void run(AppConfig conf, Environment env) throws Exception {
		configureCors(env);

        // Connect to db
        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(env, conf.getDatabase(), "postgresql");

        jdbi.registerContainerFactory(new OptionalContainerFactory());
        
        final AccessTokenDAO accessTokenDAO = jdbi.onDemand(AccessTokenDAO.class);
        final UserDAO userDAO = jdbi.onDemand(UserDAO.class);

        final UserResource userResource = new UserResource(userDAO);
        
        env.jersey()
                .register(AuthFactory.binder(
                        new OAuthFactory<AccessToken>(new OAuthAuthenticator(accessTokenDAO),
                                conf.getBearerRealm(), AccessToken.class)));


        env.jersey().register(userResource);
	}
	
    private void configureCors(Environment environment) {
        Dynamic filter = environment.servlets().addFilter("CORS", CrossOriginFilter.class);
        filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM,
                "GET,PUT,POST,DELETE,OPTIONS");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
        filter.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*");
        filter.setInitParameter("allowedHeaders",
                "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin");
        filter.setInitParameter("allowCredentials", "true");
    }
}