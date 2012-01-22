package nosql;

import nosql.dao.MongoDao;
import nosql.dao.NoSqlDao;

import com.google.code.morphia.Datastore;
import com.google.inject.AbstractModule;
import com.google.inject.Provider;

public class ApplicationModule extends AbstractModule {

  private final DatastoreEnum datastore;

  public ApplicationModule(DatastoreEnum datastore) {
    this.datastore = datastore;
  }

  
  @SuppressWarnings("unchecked")
  @Override
  protected void configure() {
    
    bind(NoSqlDao.class).to(datastore.getDaoClass());
    
    bind(Datastore.class).toProvider(new Provider<Datastore>() {
      public Datastore get() {
        return MongoDao.createDatastore("million");
      }
    });
  }
}
