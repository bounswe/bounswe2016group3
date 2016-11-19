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
	
	@SqlQuery("select meals.* from meals join tags on meals.id = tags.meal_id where tags.tag = :tag")
	abstract public List<Meal> getMealsByTag(@Bind("tag") String tag);
	
	@SqlQuery("select tags.tag from meals join tags on meals.id = tags.meal_id where tags.meal_id = :id")
	abstract public List<String> getTagsByMeal(@Bind("id") Long id);
	
	@SqlUpdate("insert into tags (meal_id, tag) values (:id, :tag)")
	abstract public void tagMeal(@Bind("id") Long id, @Bind("tag") String tag);
	
	@SqlUpdate("delete from tags where meal_id = :id and tag = :tag")
	abstract public void untagMeal(@Bind("id") Long id, @Bind("tag") String tag);
	
	@SqlQuery("select count(1) from checkeat where user_id = :uid and meal_id = :mid")
	abstract public Boolean checkAte(@Bind("uid") Long userId, @Bind("mid") Long mealId);
	
	@SqlUpdate("insert into checkeat (user_id, meal_id) values (:uid, :mid)")
	abstract public void checkEat(@Bind("uid") Long userId, @Bind("mid") Long mealId);
	
	@SqlQuery("select avg(rating) from rating where meal_id = :mid")
	abstract public Float averageRating(@Bind("mid") Long mealId);
	
	@SqlQuery("select count(*) from rating where meal_id = :mid")
	abstract public Integer totalRatings(@Bind("mid") Long mealId);
	
	@SqlQuery("select rating from rating where meal_id = :mid and user_id = :uid")
	abstract public Float ratingByUser(@Bind("uid") Long userId, @Bind("mid") Long mealId);
	
	@SqlQuery("select count(*) from rating where meal_id = :mid and user_id = :uid")
	abstract public Boolean ratedByUser(@Bind("uid") Long userId, @Bind("mid") Long mealId);

	@SqlUpdate("insert into rating (user_id, meal_id, rating) values (:uid, :mid, :rating)")
	abstract public void rateMeal(@Bind("uid")Long userId, @Bind("mid")Long id, @Bind("rating")Float rating);
	
	@SqlQuery("select * from meals where name like :pattern or description like :pattern")
	abstract protected List<Meal> _basicSearch(@Bind("pattern") String pattern);
	
	public List<Meal> basicSearch(String query) {
		StringBuilder b = new StringBuilder();
		b.append('%');
		b.append(query);
		b.append('%');
		
		return _basicSearch(b.toString());
	}
}
