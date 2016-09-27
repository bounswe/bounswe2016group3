package bounswegroup3.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.joda.time.DateTime;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import bounswegroup3.model.AccessToken;

public class AccessTokenMapper implements ResultSetMapper<AccessToken> {

    @Override
    public AccessToken map(int row, ResultSet rs, StatementContext ctx) throws SQLException {
        UUID accessToken = UUID.fromString(rs.getString("access_token"));
        Long userId = rs.getLong("user_id");
        DateTime creationTime = new DateTime(rs.getTimestamp("creation_time"));
        DateTime lastAccessTime = new DateTime(rs.getTimestamp("last_access_time"));

        return new AccessToken(accessToken, userId, creationTime, lastAccessTime);
    }

}