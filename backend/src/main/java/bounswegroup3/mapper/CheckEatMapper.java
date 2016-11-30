package bounswegroup3.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.joda.time.DateTime;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import bounswegroup3.model.CheckEat;

public class CheckEatMapper implements ResultSetMapper<CheckEat> {
	@Override
	public CheckEat map(int row, ResultSet rs, StatementContext ctx) throws SQLException {
		Long uid = rs.getLong("user_id");
		Long mid = rs.getLong("meal_id");
		DateTime date = new DateTime(rs.getTimestamp("creation_date"));
		
		CheckEat res = new CheckEat(uid, mid, date);
		return res;
	}
}
