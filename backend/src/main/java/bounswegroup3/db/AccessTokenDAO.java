package bounswegroup3.db;

import org.joda.time.DateTime;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import bounswegroup3.mapper.AccessTokenMapper;
import bounswegroup3.model.AccessToken;

import java.util.UUID;

public abstract class AccessTokenDAO {
    @SqlUpdate("insert into tokens(access_token, user_id, creation_time, last_access_time) "
            + "values(:accessToken, :userId, :creationTime, :lastAccessTime)")
    abstract protected void insertToken(@BindBean AccessToken accessToken);

    @SqlUpdate("update tokens set last_access_time = :lastAccessTime")
    abstract protected void _updateLastAccessTime(@BindBean AccessToken accessToken);

    @Mapper(AccessTokenMapper.class)
    @SqlQuery("select * from tokens where access_token = :accessToken")
    abstract public AccessToken getAccessToken(@Bind("accessToken") UUID accessToken);

    @SqlUpdate("delete from tokens where access_token = :accessToken")
    abstract public void deleteAccessToken(@Bind("accessToken") UUID accessToken);

    @SqlUpdate("delete from tokens where now() - last_access_time >= :diff")
    abstract public void deleteOlderThan(@Bind("diff") Long diff);
    
    public AccessToken generateToken(Long userId) {
        AccessToken token = new AccessToken(UUID.randomUUID(), userId, new DateTime(),
                new DateTime());
        insertToken(token);
        return token;
    }

    public void updateLastAccessTime(AccessToken accessToken) {
        accessToken.setLastAccessTime(new DateTime());
        _updateLastAccessTime(accessToken);
    }

}
