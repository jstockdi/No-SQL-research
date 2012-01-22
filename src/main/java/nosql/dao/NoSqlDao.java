package nosql.dao;

import nosql.model.Artist;
import nosql.model.Location;

public interface NoSqlDao{
  void insertArtist(Artist artist);

  void updateLatLong(String artistId, Location location);

  void updateNewSimilarities(String target, String similar);
}