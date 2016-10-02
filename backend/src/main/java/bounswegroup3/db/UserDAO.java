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
            + "full_name, bio, user_type, diet_type) "
            + "values (:email, :passwordHash, :passwordSalt, "
            + ":fullName, :bio, :userType, :dietType)")
    Long addUser(@BindBean User user);

    @SqlUpdate("update users set password_hash = :passwordHash, password_salt = :passwordSalt,"
            + "full_name = :fullName, bio = :bio, user_type = :userType, diet_type = :dietType,"
    		+ "secret_question = :secretQuestion, secret_answer_hash = :secretAnswerHash"
            + "secret_answer_salt = :secretAnswerSalt"
            + "where id = :id")
    void updateUser(@BindBean User user);

    @SqlQuery("select * from users where id = :id")
    User getUserById(@Bind("id") Long id);

    @SqlQuery("select * from users where email = :email")
    User getUserByEmail(@Bind("email") String email);
}