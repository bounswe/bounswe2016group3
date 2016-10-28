package bounswegroup3.db;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

public interface FailedLoginDAO {
	@SqlQuery("select count(*) from failed_logins where "
			+ "user_id = :id and (now() - attempt_time)::int <= 300")
	public Long attemptsInLastFiveMinutes(@Bind("id") Long id);
	
	@SqlUpdate("insert into failed_logins (user_id, attempt_time) "
			+ "values (:id, now())")
	public void addAttempt(@Bind("id") Long id);
}
