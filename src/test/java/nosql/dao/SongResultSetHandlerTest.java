package nosql.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import nosql.mock.MockResultSet;
import nosql.model.Artist;
import nosql.model.Song;

import org.junit.Test;
import org.mockito.Mockito;

import com.google.common.base.Optional;

public class SongResultSetHandlerTest {
  
  @Test
  public void testHandler() throws SQLException {
    
    Artist artist = new Artist("2342",null,null,"Fake Artist");
    
    Song song = new Song();
    song.setArtist(artist);
    song.setTitle("Song Title");
    song.setArtistHotness(2.5d);
    song.setDuration(1223l);
    song.setFamiliarity(4234l);
    song.setRelease("My release");
    song.setSongId("234EA5");
    song.setTrackId("1AEDC5");
    song.setYear(1967);
    
    NoSqlDao mockDao = Mockito.mock(NoSqlDao.class);
    
    Mockito.when(mockDao.getArtist(artist.getArtistId())).thenReturn(Optional.of(artist));
    
    SongResultSetHandler resultSetHandler = new SongResultSetHandler(mockDao);
    
    MockResultSet mrs = new MockResultSet(Mockito.mock(ResultSet.class));
    mrs.mockString("artist_id", artist.getArtistId());
    mrs.mockString("title", song.getTitle());
    mrs.mockDouble("artist_hotttnesss", song.getArtistHotness());
    mrs.mockDouble("duration", song.getDuration());
    mrs.mockDouble("artist_familiarity",song.getFamiliarity());
    mrs.mockString("release",song.getRelease());
    mrs.mockString("song_id",song.getSongId());
    mrs.mockString("track_id", song.getTrackId());
    mrs.mockInt("year", song.getYear());
    
    
    resultSetHandler.handle(mrs.getResultSet());
    
    Mockito.verify(mockDao).insertSong(song);
    
  }
  
 

}
