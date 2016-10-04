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

    @SqlUpdate("update users set password_hash = :passwordHash, password_salt = :passwordSalt,"
            + "full_name = :fullName, bio = :bio, user_type = :userType, diet_type = :dietType,"
    		+ "avatar_url = :avatarUrl"
            + "where id = :id")
    void updateUser(@BindBean User user);

    @SqlQuery("select * from users where id = :id")
    User getUserById(@Bind("id") Long id);

    @SqlQuery("select * from users where email = :email")
    User getUserByEmail(@Bind("email") String email);
}