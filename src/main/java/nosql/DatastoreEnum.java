package nosql;

import nosql.dao.MongoDao;

@SuppressWarnings("rawtypes")
public enum DatastoreEnum{
  MongoDB(MongoDao.class);
  
  private final Class daoClass;

  private DatastoreEnum(Class daoClass) {
    this.daoClass = daoClass;
  }
  
  public Class getDaoClass() {
    return daoClass;
  }
  
}