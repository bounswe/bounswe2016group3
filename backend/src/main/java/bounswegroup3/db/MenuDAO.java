package bounswegroup3.db;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import bounswegroup3.mapper.MenuMapper;
import bounswegroup3.model.Menu;

@RegisterMapper(MenuMapper.class)
public interface MenuDAO {
    @GetGeneratedKeys
	@SqlUpdate("insert into menus (user_id, name) values (:userId, :name)")
	Long createMenu(@BindBean Menu menu);
	
	@SqlQuery("select * from menus where user_id=:id")
	List<Menu> menusByUser(@Bind("id") Long id);
	
	@SqlUpdate("delete from menus where id=:id")
	void deleteMenu(@Bind("id") Long id);

}
