package bounswegroup3.db;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import bounswegroup3.mapper.MealMapper;
import bounswegroup3.model.Meal;

public interface TagDAO {
	@Mapper(MealMapper.class)
	@SqlQuery("select meals.* from meals join tags on meals.id = tags.relation_id where tags.identifier = :tag and tags.relation_type = 2")
	List<Meal> getMealsByTag(@Bind("tag") String tag);
	
	@SqlQuery("select tags.identifier from meals join tags on meals.id = tags.relation_id where tags.relation_id = :id and tags.relation_type = 2")
	List<String> getTagsByMeal(@Bind("id") Long id);
	
	@SqlUpdate("insert into tags (relation_type, relation_id, display_name, identifier) values (2, :id, :dn, :tag)")
	void tagMeal(@Bind("id") Long id, @Bind("dn") String displayName, @Bind("tag") String tag);
	
	@SqlUpdate("delete from tags where relation_type = 2 and relation_id = :id and identifier = :tag")
	void untagMeal(@Bind("id") Long id, @Bind("tag") String tag);
}
