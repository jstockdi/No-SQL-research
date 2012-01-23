package nosql.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

import knzn.db.ResultSetHandler;
import nosql.model.Artist;
import nosql.model.Song;

public class SongResultSetHandler implements ResultSetHandler<Void> {

  private static final Logger LOGGER = LoggerFactory.getLogger(SongResultSetHandler.class);
  private int count = 0;
  private final NoSqlDao noSqlDao;
  
  public SongResultSetHandler(NoSqlDao noSqlDao) {
    this.noSqlDao = noSqlDao;
  }
  
  public Void handle(ResultSet resultSet) throws SQLException {
    
    String artistId = resultSet.getString("artist_id");
    String trackId = resultSet.getString("track_id");
    String title = resultSet.getString("title");
    String songId = resultSet.getString("song_id");
    String release = resultSet.getString("release");
    double duration = resultSet.getDouble("duration");
    double familiarity = resultSet.getDouble("artist_familiarity");
    double hotttnesss = resultSet.getDouble("artist_hotttnesss");
    int year = resultSet.getInt("year");
    
    Song song = new Song();
    
    Optional<Artist> artist = noSqlDao.getArtist(artistId);
    if(artist.isPresent()){
      song.setArtist(artist.get());
    }else{
      LOGGER.warn("Artist not found for: " + artistId);
    }
    
    song.setArtistHotness(hotttnesss);
    song.setDuration(duration);
    song.setFamiliarity(familiarity);
    song.setRelease(release);
    song.setSongId(songId);
    song.setTitle(title);
    song.setTrackId(trackId);
    song.setYear(year);
    
    noSqlDao.insertSong(song);
    
    count++;
    return null;
  }
  
  public int getCount() {
    return count;
  }

}
