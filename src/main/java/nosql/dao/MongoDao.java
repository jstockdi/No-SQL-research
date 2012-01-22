package nosql.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nosql.model.Artist;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.google.inject.Inject;
import com.mongodb.Mongo;

public class MongoDao implements NoSqlDao {

  private static final Logger LOGGER = LoggerFactory.getLogger(MongoDao.class);
  
  private final Datastore ds;

  public static Datastore createDatastore(String datastoreName) {
    Datastore ds = null;
    try {
      Mongo mongo = new Mongo("127.0.0.1"); //Should only be one instance per JVM of these
      Morphia morphia = new Morphia();
      morphia
        .map(Artist.class);
      
      LOGGER.info("Creating datastore: " + datastoreName);
      ds = morphia.createDatastore(mongo, datastoreName);
      
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return ds;
  }
  

  @Inject
  public MongoDao(Datastore ds) {
    this.ds = ds;
  }
  
  public void insertArtist(Artist artist) {
    LOGGER.debug("Creating artist: " + artist);
    ds.save(artist);
  }
}
