package bounswegroup3.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.joda.time.DateTime;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import bounswegroup3.model.Comment;

public class CommentMapper implements ResultSetMapper<Comment> {
	@Override
	public Comment map(int row, ResultSet rs, StatementContext ctx) throws SQLException {
		final Long id = rs.getLong("id");
		final Long mealId = rs.getLong("meal_id");
		final Long userId = rs.getLong("user_id");
		final String content = rs.getString("content");
		final DateTime creationTime = new DateTime(rs.getTimestamp("creation_time"));
		final DateTime updateTime = new DateTime(rs.getTimestamp("update_time"));
		
		return new Comment(id,mealId,userId,content,creationTime,updateTime);

	}
}
