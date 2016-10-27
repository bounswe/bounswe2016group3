/**
 * The package contains the backend API for the Eatalyze project
 * by Group 3 for the course CMPE451 on fall 2016 semester.
 * <br>
 * You need java 1.8 to be able to run the project as well as
 * a postgres database with the access info given in conf.yml
 * <br>
 * In order to run the project, after creating the database
 * (we're assumming the resulting executable file is backend.jar
 * and the configuration file is called conf.yml),
 * run <code>java -jar backend.jar db migrate conf.yml</code>
 * first. This command will create the required database schema
 * by applying the migrations.
 * <br>
 * Then, run <code>java -jar backend.jar server conf.yml</code>
 * which will start a web server serving on the port specified in
 * the configuration file.
 * <br>
 * After running the backend, all API calls are made in the /api/
 * subdirectory. You also need to authenticate, you'll need an
 * access token from the application. To get an access token, call the 
 * session/login api call with your email and password. You'll then get 
 * a temporary access token from that call. For every other call,
 * pass an Authorization header with the value "Bearer $token" in order to 
 * authenticate into the service as a user. Note that tokens older than 30 
 * days are automatically deleted. When you're done with a token, i.e.
 * logging out, you can call the session/logout api call to have the token
 * you're using be invalidated.
 */
package bounswegroup3;
