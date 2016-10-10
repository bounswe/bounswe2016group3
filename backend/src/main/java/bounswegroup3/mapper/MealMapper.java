package bounswegroup3.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import bounswegroup3.model.Meal;

public class MealMapper implements ResultSetMapper<Meal>{
	@Override
	public Meal map(int row, ResultSet rs, StatementContext ctx) throws SQLException {
		final Long id = rs.getLong("id");
		final Long menuId = rs.getLong("menu_id");
		final String name = rs.getString("name");
		final String description = rs.getString("description");
		final String photoUrl = rs.getString("photo_url");

		return new Meal(id, menuId, name, description, photoUrl);
	}
}
