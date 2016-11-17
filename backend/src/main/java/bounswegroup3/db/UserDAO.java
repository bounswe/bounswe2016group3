package bounswegroup3.db;

import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import bounswegroup3.model.User;
import bounswegroup3.mapper.UserMapper;
import java.util.List;

@RegisterMapper(UserMapper.class)
public interface UserDAO {
    @SqlQuery("select * from users")
    List<User> getUsers();

    @GetGeneratedKeys
    @SqlUpdate("insert into users (email, password_hash, password_salt, "
            + "full_name, bio, user_type, diet_type, "
            + "secret_question, secret_answer_hash, secret_answer_salt,"
            + "avatar_url) "
            + "values (:email, :passwordHash, :passwordSalt, "
            + ":fullName, :bio, :userType, :dietType, "
            + ":secretQuestion, :secretAnswerHash, :secretAnswerSalt, :avatarUrl)")
    Long addUser(@BindBean User user);

    @SqlUpdate("update users set avatar_url = :url where id = :id")
    void updateAvatar(@Bind("id") Long id, @Bind("url") String url);
    
    @SqlUpdate("update users set password_hash = :passwordHash, password_salt = :passwordSalt,"
            + "full_name = :fullName, bio = :bio, user_type = :userType, diet_type = :dietType "
            + "where id = :id")
    void updateUser(@BindBean User user);

    @SqlQuery("select * from users where id = :id")
    User getUserById(@Bind("id") Long id);

    @SqlQuery("select * from users where email = :email")
    User getUserByEmail(@Bind("email") String email);
    
    @SqlUpdate("update users set banned = 1 where id = :id")
    void banUser(@Bind("id") Long id);
    
    @SqlQuery("select users.* from users join follow on users.id = follow.followee_id where follow.follower_id=:id")
    List<User> getFollowing(@Bind("id") Long id);
    
    @SqlQuery("select users.* from users join follow on users.id = follow.follower_id where follow.followee_id=:id")
    List<User> getFollowers(@Bind("id") Long id);
    
    @SqlQuery("select count(1) from follow where follower_id = :id1 and followee_id = :id2")
    Boolean follows(@Bind("id1") Long followerId, @Bind("id2") Long followeeId);
    
    @SqlQuery("select count(1) from users where id = :id")
    Boolean userExists(@Bind("id") Long id);
    
    @SqlQuery("select count(1) from users where email = :mail")
    Boolean userExistsByEmail(@Bind("mail") String mail);
    
    @SqlUpdate("insert into follow (follower_id, followee_id) values (:id1, :id2)")
    void followUser(@Bind("id1") Long folllower, @Bind("id2") Long followee);
    
    @SqlUpdate("delete from follow where follower_id = :id1 and followee_id = :id2")
    void unfollowUser(@Bind("id1") Long folllower, @Bind("id2") Long followee);
}