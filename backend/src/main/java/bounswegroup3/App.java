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
import bounswegroup3.db.CommentDAO;
import bounswegroup3.db.FailedLoginDAO;
import bounswegroup3.db.MealDAO;
import bounswegroup3.db.MenuDAO;
import bounswegroup3.db.UserDAO;
import bounswegroup3.mail.Mailer;
import bounswegroup3.model.AccessToken;
import bounswegroup3.resource.CommentResource;
import bounswegroup3.resource.MealResource;
import bounswegroup3.resource.MenuResource;
import bounswegroup3.resource.SessionResource;
import bounswegroup3.resource.UserResource;

class App extends Application<AppConfig> {
	public static void main(String[] args) throws Exception {
		new App().run(args);
	}
	

    @Override
    public String getName() {
        return "Eatalyze";
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
        final FailedLoginDAO failedLoginDAO = jdbi.onDemand(FailedLoginDAO.class);
        final MenuDAO menuDao = jdbi.onDemand(MenuDAO.class);
        final MealDAO mealDao = jdbi.onDemand(MealDAO.class);
        final CommentDAO commentDao = jdbi.onDemand(CommentDAO.class);
        
        final Mailer mailer = new Mailer(conf.getMailjetKey(), conf.getMailjetSecret(), getName(), conf.getMailAddress());
        
        final UserResource userResource = new UserResource(userDAO, menuDao, mealDao, mailer);
        final SessionResource sessionResource = new SessionResource(accessTokenDAO, userDAO, failedLoginDAO);
        final MenuResource menuResource = new MenuResource(menuDao, mealDao);
        final MealResource mealResource = new MealResource(menuDao, mealDao, commentDao);
        final CommentResource commentResource = new CommentResource(commentDao);
        
        env.jersey()
                .register(AuthFactory.binder(
                        new OAuthFactory<AccessToken>(new OAuthAuthenticator(accessTokenDAO),
                                conf.getBearerRealm(), AccessToken.class)));


        env.jersey().register(userResource);
        env.jersey().register(sessionResource);
        env.jersey().register(menuResource);
        env.jersey().register(mealResource);
        env.jersey().register(commentResource);
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