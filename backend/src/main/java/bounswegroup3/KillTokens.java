package bounswegroup3;

import java.io.PrintWriter;

import com.google.common.collect.ImmutableMultimap;

import bounswegroup3.db.AccessTokenDAO;
import io.dropwizard.servlets.tasks.Task;

public class KillTokens extends Task {
	private AccessTokenDAO dao;
	
	/**
	 * Simply sets up the task with a name and a DAO
	 * @param dao The object providing access to the data
	 */
	public KillTokens(AccessTokenDAO dao) {
		super("kill_expired_tokens");
		this.dao = dao;
	}

	/**
	 * Removes every token older than 30 days
	 */
	@Override
	public void execute(ImmutableMultimap<String, String> arg0, PrintWriter arg1) throws Exception {
		// 30 days in seconds
		dao.deleteOlderThan(new Long(30*24*60*60));
	}

}
