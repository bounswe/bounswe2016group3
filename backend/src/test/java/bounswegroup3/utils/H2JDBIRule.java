package bounswegroup3.utils;

import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;
import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;

import org.junit.rules.ExternalResource;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.MetricRegistry;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

public class H2JDBIRule extends ExternalResource {

    private DBI dbi;

    private Handle handle;

    private Liquibase liquibase;

    public DBI getDbi() {
        return dbi;
    }

    @Override
    protected void before() throws Throwable {
        Environment environment = new Environment("test-env", Jackson.newObjectMapper(), null, new MetricRegistry(), null);
        dbi = new DBIFactory().build(environment, getDataSourceFactory(), "test");
        handle = dbi.open();
        try {
        	migrateDatabase();
        } catch(Exception e) {
        	
        }
    }

    @Override
    protected void after() {
        try {
            liquibase.dropAll();
        } catch (Exception e) {
            throw new RuntimeException("failed clearing up Liquibase object", e);
        }
        handle.close();
    }

    private void migrateDatabase() throws Exception {
    	// we need to do that in order to get less ridiculous
    	// debug messages on the log
    	Logger logger = (Logger)LoggerFactory.getLogger("liquibase");
    	logger.setLevel(Level.WARN);
    	
    	logger = (Logger)LoggerFactory.getLogger("LiquibaseSchemaResolver");
    	logger.setLevel(Level.WARN);
    	
        liquibase = new Liquibase("migrations.xml", new ClassLoaderResourceAccessor(), new JdbcConnection(handle.getConnection()));
        liquibase.update("");
    }

    private DataSourceFactory getDataSourceFactory() {
        DataSourceFactory dataSourceFactory = new DataSourceFactory();
        dataSourceFactory.setDriverClass("org.h2.Driver");
        dataSourceFactory.setUrl("jdbc:h2:./target/h2db;MODE=PostgreSQL");
        dataSourceFactory.setUser("sa");
        dataSourceFactory.setPassword("sa");
        
        return dataSourceFactory;
    }
}