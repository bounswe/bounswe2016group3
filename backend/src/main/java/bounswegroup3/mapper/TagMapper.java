package bounswegroup3.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import bounswegroup3.model.Tag;

public class TagMapper implements ResultSetMapper<Tag> {

	@Override
	public Tag map(int row, ResultSet rs, StatementContext ctx) throws SQLException {
		Long type = rs.getLong("relation_type");
		Long relId = rs.getLong("relation_id");
		String dn = rs.getString("display_name");
		String id = rs.getString("identifier");
		
		return new Tag(type, relId, dn, id);
	}

}
