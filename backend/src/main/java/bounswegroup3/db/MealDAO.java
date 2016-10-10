package bounswegroup3.db;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import bounswegroup3.mapper.MealMapper;
import bounswegroup3.model.Meal;

@RegisterMapper(MealMapper.class)
public interface MealDAO {
    @GetGeneratedKeys
	@SqlUpdate("insert into meals (menu_id, name, description, photo_url) values (:menuId, :name, :description, :photoUrl)")
    Long createMeal(@BindBean Meal meal);
    
	@SqlQuery("select * from meals where menu_id=:id")
	List<Meal> mealsByMenuId(@Bind("id") Long menuId);
	
	@SqlQuery("select * from meals join menus on meals.menu_id = menus.id where menus.user_id = :id")
	List<Meal> mealsByUserId(@Bind("id") Long userId);
	
	@SqlUpdate("delete from meals where id=:id")
	void deleteMeal(@Bind("id") Long id);
}
