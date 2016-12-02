package bounswegroup3.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.joda.time.DateTime;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import bounswegroup3.constant.EventType;
import bounswegroup3.model.Event;

public class EventMapper implements ResultSetMapper<Event> {
	@Override
	public Event map(int row, ResultSet rs, StatementContext ctx) throws SQLException {
		
		Long id = rs.getLong("id");
		Long uid = rs.getLong("user_id");
		EventType type = EventType.values()[rs.getInt("event_type")];
		String url = rs.getString("url");
		String desc = rs.getString("description");
		DateTime date = new DateTime(rs.getTimestamp("date"));
		
		Event res = new Event(id, uid, type, url, desc, date);
		
		return res;
	}

}
