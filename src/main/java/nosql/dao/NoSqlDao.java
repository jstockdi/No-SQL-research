package nosql.dao;

import nosql.model.Artist;
import nosql.model.Location;
import nosql.model.Song;

import com.google.common.base.Optional;

public interface NoSqlDao{
  void insertArtist(Artist artist);

  void updateLatLong(String artistId, Location location);

  void updateNewSimilarities(String target, String similar);

  void updateTerm(String artistId, String term);

  Optional<Artist> getArtist(String artistId);

  void insertSong(Song song);

}