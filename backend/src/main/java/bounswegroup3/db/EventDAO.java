package bounswegroup3.db;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import bounswegroup3.mapper.EventMapper;
import bounswegroup3.model.Event;

@RegisterMapper(EventMapper.class)
public abstract class EventDAO {
	@GetGeneratedKeys
	@SqlUpdate("insert into events (user_id, event_type, url, description, date) values (:userId, :type, :url, :description, NOW())")
	abstract protected Long _createEvent(@BindBean Event event);

	@SqlQuery("select events.* from events join follow where follow.follower_id = :uid and events.user_id = follow.followee_id order by date limit 100")
	abstract public List<Event> homepageEvents(@Bind("uid") Long userId);
	
	public void createEvent(Event event) {
		Long id = _createEvent(event);
		event.setId(id);
	}
}
