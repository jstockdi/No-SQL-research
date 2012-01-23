package nosql.dao;

import nosql.model.Artist;
import nosql.model.Location;
import nosql.model.Song;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;
import com.google.common.base.Optional;
import com.google.common.collect.Iterables;
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
    LOGGER.debug("Creating artist: {}", artist);
    ds.save(artist);
  }


  public void updateLatLong(String artistId, Location location) {
    
    final Query<Artist> query = ds.createQuery(Artist.class).field("artistId").equal(artistId);
    final UpdateOperations<Artist> updateOperations = ds.createUpdateOperations(Artist.class).set("location", location);
    
    ds.update(query, updateOperations);
    
  }


  /*
   *  assumes the similar array does not include the similar
   */
  public void updateNewSimilarities(String target, String similar) {

    final Query<Artist> query = ds.createQuery(Artist.class).field("artistId").equal(target);
    final UpdateOperations<Artist> updateOperations = 
            ds.createUpdateOperations(Artist.class).add("similar", similar); 
    
    ds.update(query, updateOperations);
    
  }


  @Override
  public void updateTerm(String artistId, String term) {
    final Query<Artist> query = ds.createQuery(Artist.class).field("artistId").equal(artistId);
    final UpdateOperations<Artist> updateOperations = 
            ds.createUpdateOperations(Artist.class).add("terms", term); 
    
    ds.update(query, updateOperations);
  }

  @Override
  public Optional<Artist> getArtist(String artistId) {
    Artist artist = Iterables.getFirst(ds.find(Artist.class)
            .filter("artistId =", artistId)
            .fetch(), null);
    
    return Optional.fromNullable(artist);
  }

  @Override
  public void insertSong(Song song) {
    LOGGER.debug("Creating song: {}", song);
    ds.save(song);
  }
}
