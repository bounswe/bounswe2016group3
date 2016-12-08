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
public abstract class UserDAO {
    @SqlQuery("select * from users")
    abstract public List<User> getUsers();

    @GetGeneratedKeys
    @SqlUpdate("insert into users (email, password_hash, password_salt, "
            + "full_name, bio, user_type, diet_type, "
            + "secret_question, secret_answer_hash, secret_answer_salt,"
            + "avatar_url, location)"
            + "values (:email, :passwordHash, :passwordSalt, "
            + ":fullName, :bio, :userType, :dietType, "
            + ":secretQuestion, :secretAnswerHash, :secretAnswerSalt, "
            + ":avatarUrl, :location)")
    abstract public Long addUser(@BindBean User user);

    @SqlUpdate("update users set avatar_url = :url where id = :id")
    abstract public void updateAvatar(@Bind("id") Long id, @Bind("url") String url);
    
    @SqlUpdate("update users set password_hash = :passwordHash, password_salt = :passwordSalt,"
            + "full_name = :fullName, bio = :bio, user_type = :userType, diet_type = :dietType "
            + "where id = :id")
    abstract public void updateUser(@BindBean User user);

    @SqlQuery("select * from users where id = :id")
    abstract public User getUserById(@Bind("id") Long id);

    @SqlQuery("select * from users where email = :email")
    abstract public User getUserByEmail(@Bind("email") String email);
    
    @SqlUpdate("update users set banned = 1 where id = :id")
    abstract public void banUser(@Bind("id") Long id);
    
    @SqlQuery("select users.* from users join follow on users.id = follow.followee_id where follow.follower_id=:id")
    abstract public List<User> getFollowing(@Bind("id") Long id);
    
    @SqlQuery("select users.* from users join follow on users.id = follow.follower_id where follow.followee_id=:id")
    abstract public List<User> getFollowers(@Bind("id") Long id);
    
    @SqlQuery("select count(1) from follow where follower_id = :id1 and followee_id = :id2")
    abstract public Boolean follows(@Bind("id1") Long followerId, @Bind("id2") Long followeeId);
    
    @SqlQuery("select count(1) from users where id = :id")
    abstract public Boolean userExists(@Bind("id") Long id);
    
    @SqlQuery("select count(1) from users where email = :mail")
    abstract public Boolean userExistsByEmail(@Bind("mail") String mail);
    
    @SqlUpdate("insert into follow (follower_id, followee_id) values (:id1, :id2)")
    abstract public void followUser(@Bind("id1") Long folllower, @Bind("id2") Long followee);
    
    @SqlUpdate("delete from follow where follower_id = :id1 and followee_id = :id2")
    abstract public void unfollowUser(@Bind("id1") Long folllower, @Bind("id2") Long followee);
    
    @SqlQuery("select * from users where full_name like :pattern or email like :pattern")
	abstract protected List<User> _basicSearch(@Bind("pattern") String pattern);
	
	public List<User> basicSearch(String query) {
		StringBuilder b = new StringBuilder();
		b.append('%');
		b.append(query);
		b.append('%');
		
		return _basicSearch(b.toString());
	}
}