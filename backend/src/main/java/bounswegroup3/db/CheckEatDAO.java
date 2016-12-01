package bounswegroup3.db;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import bounswegroup3.model.CheckEat;
import bounswegroup3.mapper.CheckEatMapper;

@RegisterMapper(CheckEatMapper.class)
public interface CheckEatDAO {
	@SqlQuery("select count(1) from checkeat where user_id = :uid and meal_id = :mid")
	public Boolean checkAte(@Bind("uid") Long userId, @Bind("mid") Long mealId);
	
	@SqlUpdate("insert into checkeat (user_id, meal_id, creation_date) values (:uid, :mid, NOW())")
	public void checkEat(@Bind("uid") Long userId, @Bind("mid") Long mealId);
	
	@SqlQuery("select * from checkeat where (now() - creation_date) < '1 week'::interval and user_id = :uid")
	public List<CheckEat> checkEatsFromLastWeek(@Bind("uid") Long userId);
}
