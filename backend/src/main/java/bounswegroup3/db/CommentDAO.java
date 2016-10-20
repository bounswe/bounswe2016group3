package bounswegroup3.db;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import bounswegroup3.mapper.CommentMapper;
import bounswegroup3.model.Comment;

@RegisterMapper(CommentMapper.class)
public interface CommentDAO {
	@GetGeneratedKeys
	@SqlUpdate("insert into comments (meal_id, user_id, content, creation_time, update_time) "
			+ "values (:mealId, :userId, :content, :creationTime, :updateTime)")
    Long createComment(@BindBean Comment comment);
	
	@SqlQuery("select * from comments where meal_id=:id")
	List<Comment> commentsByMeal(@Bind("id") Long mealId);
	
	@SqlUpdate("update comments set meal_id = :mealId, user_id = :userId, "
			 + "content = :content, update_time = NOW()"
			 + "where id = :id")
	void updateComment(@BindBean Comment comment);
	
	@SqlQuery("select * from comments where user_id=:id")
	List<Comment> commentsByUser(@Bind("id") Long userId);

	@SqlQuery("select * from comments where id=:id")
	Comment getCommentById(@Bind("id") Long id);
	
	@SqlUpdate("delete from comments where id = :id")
	void deleteComment(@Bind("id") Long id);
}
