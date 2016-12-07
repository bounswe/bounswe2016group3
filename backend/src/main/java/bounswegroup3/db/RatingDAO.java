package bounswegroup3.db;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

public interface RatingDAO {
	@SqlQuery("select avg(rating) from rating where meal_id = :mid")
	Float averageRating(@Bind("mid") Long mealId);
	
	@SqlQuery("select count(*) from rating where meal_id = :mid")
	Integer totalRatings(@Bind("mid") Long mealId);
	
	@SqlQuery("select rating from rating where meal_id = :mid and user_id = :uid")
	Float ratingByUser(@Bind("uid") Long userId, @Bind("mid") Long mealId);
	
	@SqlQuery("select count(*) from rating where meal_id = :mid and user_id = :uid")
	Boolean ratedByUser(@Bind("uid") Long userId, @Bind("mid") Long mealId);

	@SqlUpdate("insert into rating (user_id, meal_id, rating) values (:uid, :mid, :rating)")
	void rateMeal(@Bind("uid")Long userId, @Bind("mid")Long id, @Bind("rating")Float rating);
	
}
