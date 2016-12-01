package bounswegroup3.db;

import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import bounswegroup3.mapper.EventMapper;

@RegisterMapper(EventMapper.class)
public interface EventDAO {

}
