package bounswegroup3.mapper;

import bounswegroup3.constant.DietType;
import bounswegroup3.constant.UserType;
import bounswegroup3.model.User;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;

public class UserMapper implements ResultSetMapper<User> {
    public User map(int row, ResultSet rs, StatementContext ctx) throws SQLException {
        final Long id = rs.getLong("id");
        final String email = rs.getString("email");
        final String passwordHash = rs.getString("password_hash");
        final String passwordSalt = rs.getString("password_salt");
        final String fullName = rs.getString("full_name");
        final String bio = rs.getString("bio");
        final UserType ut = UserType.values()[rs.getInt("user_type")];
        final DietType dt = DietType.values()[rs.getInt("diet_type")];

        return new User(id, email, passwordHash, passwordSalt, fullName, bio, ut, dt);
    }
}