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
public abstract class MealDAO {
    @GetGeneratedKeys
	@SqlUpdate("insert into meals (menu_id, user_id, name, description, ingredients, photo_url) values (:menuId, :userId, :name, :description, :ingredients, :photoUrl)")
    abstract public Long createMeal(@BindBean Meal meal);
    
	@SqlQuery("select * from meals where menu_id=:id")
	abstract public List<Meal> mealsByMenuId(@Bind("id") Long menuId);
	
	@SqlQuery("select * from meals where meals.user_id = :id")
	abstract public List<Meal> mealsByUserId(@Bind("id") Long userId);
	
	@SqlUpdate("update meals set menu_id = :menuId, user_id = :userId, "
			 + "name = :name, description = :description, photo_url = :photoUrl "
			 + "where id = :id")
	abstract public void updateMeal(@BindBean Meal meal);
	
	@SqlUpdate("delete from meals where id=:id")
	abstract public void deleteMeal(@Bind("id") Long id);

	@SqlQuery("select * from meals where id=:id")
	abstract public Meal getMealById(@Bind("id") Long id);
	
	@SqlQuery("select * from meals where name like :pattern or description like :pattern")
	abstract protected List<Meal> _basicSearch(@Bind("pattern") String pattern);
	
	public List<Meal> basicSearch(String query) {
		StringBuilder b = new StringBuilder();
		b.append('%');
		b.append(query);
		b.append('%');
		
		return _basicSearch(b.toString());
	}
	
	@SqlQuery("select meals.* from meals join checkeat on meals.id = checkeat.meal_id where checkeat.user_id = :uid")
	abstract public List<Meal> mealsEaten(@Bind("uid") Long id);
}
