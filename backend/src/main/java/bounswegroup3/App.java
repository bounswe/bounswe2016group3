package bounswegroup3;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.jdbi.OptionalContainerFactory;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.oauth.OAuthCredentialAuthFilter;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;

import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import java.util.EnumSet;
import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration.Dynamic;
import javax.ws.rs.client.Client;

import org.skife.jdbi.v2.DBI;

import bounswegroup3.auth.OAuthAuthenticator;
import bounswegroup3.auth.OAuthAuthorizer;
import bounswegroup3.client.FacebookClient;
import bounswegroup3.client.AmazonClient;
import bounswegroup3.client.ClientHealthCheck;
import bounswegroup3.client.NutritionixClient;
import bounswegroup3.client.WikidataClient;
import bounswegroup3.db.AccessTokenDAO;
import bounswegroup3.db.CheckEatDAO;
import bounswegroup3.db.CommentDAO;
import bounswegroup3.db.EventDAO;
import bounswegroup3.db.ExcludeDAO;
import bounswegroup3.db.FailedLoginDAO;
import bounswegroup3.db.MealDAO;
import bounswegroup3.db.MenuDAO;
import bounswegroup3.db.RatingDAO;
import bounswegroup3.db.TagDAO;
import bounswegroup3.db.UserDAO;
import bounswegroup3.mail.Mailer;
import bounswegroup3.model.AccessToken;
import bounswegroup3.resource.CommentResource;
import bounswegroup3.resource.HomeResource;
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
        
        bootstrap.setConfigurationSourceProvider(
        		new SubstitutingSourceProvider(
        				bootstrap.getConfigurationSourceProvider(), 
        				new EnvironmentVariableSubstitutor()));
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
        final ExcludeDAO excludeDao = jdbi.onDemand(ExcludeDAO.class);
        final CheckEatDAO checkeatDao = jdbi.onDemand(CheckEatDAO.class);
        final EventDAO eventDao = jdbi.onDemand(EventDAO.class);
        final TagDAO tagDao = jdbi.onDemand(TagDAO.class);
        final RatingDAO ratingDao = jdbi.onDemand(RatingDAO.class);
        
        final Mailer mailer = new Mailer(conf.getAppKeys().getMailjetKey(), conf.getAppKeys().getMailjetSecret(), getName(), conf.getMailAddress());
        
        final Client httpClient = new JerseyClientBuilder(env).using(conf.getHttpClient()).build(getName());
        final FacebookClient fbClient = new FacebookClient(httpClient, 
        		conf.getAppKeys().getFbAppId(), 
        		conf.getAppKeys().getFbAppSecret());
        
        final NutritionixClient nutritionixClient = new NutritionixClient(httpClient, 
        		conf.getAppKeys().getNutritionixAppId(), 
        		conf.getAppKeys().getNutritionixKey());
        
        final AmazonClient amazonClient = new AmazonClient(
        		conf.getAppKeys().getAmazonBucket(), 
        		conf.getAppKeys().getAmazonKey(),
        		conf.getAppKeys().getAmazonSecret());
        final WikidataClient wikidataClient = new WikidataClient(httpClient);
        
        final UserResource userResource = new UserResource(userDAO, menuDao, mealDao, excludeDao, commentDao, mailer, amazonClient);
        final SessionResource sessionResource = new SessionResource(accessTokenDAO, userDAO, failedLoginDAO, fbClient, amazonClient);
        final MenuResource menuResource = new MenuResource(menuDao, mealDao, userDAO);
        final MealResource mealResource = new MealResource(mealDao, commentDao, checkeatDao, userDAO, tagDao, ratingDao, nutritionixClient);
        final CommentResource commentResource = new CommentResource(commentDao);
        final HomeResource homeResource = new HomeResource(checkeatDao, mealDao, eventDao, nutritionixClient);
        
        final KillTokens killTokens = new KillTokens(accessTokenDAO);
        
        env.admin().addTask(killTokens);
        
        env.healthChecks().register("facebook", new ClientHealthCheck("Facebook", fbClient));
        env.healthChecks().register("nutritionix", new ClientHealthCheck("Nutritionix", nutritionixClient));
        env.healthChecks().register("mailjet", new ClientHealthCheck("Mailjet", mailer));
        env.healthChecks().register("amazon", new ClientHealthCheck("Amazon S3", amazonClient));
        env.healthChecks().register("wikidata", new ClientHealthCheck("Wikidata", wikidataClient));
        
        env.jersey()
        	.register(new AuthDynamicFeature(
        			new OAuthCredentialAuthFilter.Builder<AccessToken>()
        			.setAuthenticator(new OAuthAuthenticator(accessTokenDAO))
        			.setAuthorizer(new OAuthAuthorizer())
        			.setRealm(conf.getBearerRealm())
        			.setPrefix("Bearer")
        			.buildAuthFilter()));

        env.jersey().register(RolesAllowedDynamicFeature.class);
        env.jersey().register(new AuthValueFactoryProvider.Binder<>(AccessToken.class));
        
        env.jersey().register(MultiPartFeature.class);
        
        env.jersey().register(userResource);
        env.jersey().register(sessionResource);
        env.jersey().register(menuResource);
        env.jersey().register(mealResource);
        env.jersey().register(commentResource);
        env.jersey().register(homeResource);
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