package nosql;

import nosql.dao.MongoDao;
import nosql.dao.NoSqlDao;

import com.google.inject.AbstractModule;

public class ApplicationModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(NoSqlDao.class).to(MongoDao.class);
  }

}
