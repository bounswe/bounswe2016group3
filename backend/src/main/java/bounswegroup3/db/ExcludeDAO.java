package bounswegroup3.db;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

public abstract class ExcludeDAO {
	@SqlUpdate("delete from excludes where user_id = :id")
	public abstract void deleteUserExcludes(@Bind("id") Long id);
	
	@SqlUpdate("delete from includes where user_id = :id")
	public abstract void deleteUserIncludes(@Bind("id") Long id);
	
	@SqlQuery("select name from excludes where user_id = :id")
	public abstract List<String> getUserExcludes(@Bind("id") Long id);
	
	@SqlQuery("select name from includes where user_id = :id")
	public abstract List<String> getUserIncludes(@Bind("id") Long id);
	
	@SqlUpdate("insert into excludes (name, user_id) values (:id, :name)")
	public abstract void addExclude(@Bind("id") Long id, @Bind("name") String name);
	
	@SqlUpdate("insert into includes (name, user_id) values (:id, :name)")
	public abstract void addInclude(@Bind("id") Long id, @Bind("name") String name);
	
	public void updateIncludes(Long id, List<String> names) {
		deleteUserIncludes(id);
		
		for(String s : names) {
			addInclude(id, s);
		}
	}
	
	public void updateExcludes(Long id, List<String> names) {
		deleteUserExcludes(id);
		
		for(String s : names) {
			addExclude(id, s);
		}
	}
}
