package nosql.dao;

import org.jcouchdb.db.Database;

import com.google.common.base.Optional;

import junit.framework.TestCase;
import nosql.FakeData;
import nosql.model.Artist;
import nosql.model.Location;
import nosql.model.Song;

public class CouchDaoTest extends TestCase {

  public void testInsertArtist() {
    final CouchDao dao = new CouchDao(CouchDao.createNewDatabase("nosql-db"));
    final Artist artist = FakeData.createArtist();

    dao.insertArtist(artist);

    final Optional<Artist> newArtist = dao.getArtist(artist.getArtistId());

    assertTrue(newArtist.isPresent());

    assertEquals(artist.getArtistId(), newArtist.get().getArtistId());
    assertEquals(artist.getArtistName(), newArtist.get().getArtistName());
    assertEquals(artist.getArtistMid(), newArtist.get().getArtistMid());
    assertEquals(artist.getTrackId(), newArtist.get().getTrackId());
  }

  public void testUpdateLocation() {
    final CouchDao dao = new CouchDao(CouchDao.createNewDatabase("nosql-db"));
    final Artist artist = FakeData.createArtist();

    dao.insertArtist(artist);

    final Location location = new Location(2423, 3434);

    dao.updateLatLong(artist.getArtistId(), location);

    final Optional<Artist> newArtist = dao.getArtist(artist.getArtistId());

    assertTrue(newArtist.isPresent());

    assertEquals(location.getLatitude(), newArtist.get().getLocation()
            .getLatitude());
    assertEquals(location.getLongitude(), newArtist.get().getLocation()
            .getLongitude());
  }

  public void testUpdateTerms() {
    final CouchDao dao = new CouchDao(CouchDao.createNewDatabase("nosql-db"));
    final Artist artist = FakeData.createArtist();

    dao.insertArtist(artist);

    final String term = "Test Term";

    dao.updateTerm(artist.getArtistId(), term);

    final Optional<Artist> newArtist = dao.getArtist(artist.getArtistId());

    assertTrue(newArtist.isPresent());

    assertTrue(newArtist.get().getTerms().contains(term));
  }
  
  public void testInsertSong(){
    final Database db = CouchDao.createNewDatabase("nosql-db");
    final CouchDao dao = new CouchDao(db);
    
    final Song song = FakeData.createSong();

    dao.insertSong(song);

    final Song newSong = db.getDocument(Song.class, song.getSongId());

    assertNotNull(newSong);
    
    assertEquals(song.getSongId(), newSong.getSongId());
    assertEquals(song.getArtistHotness(), newSong.getArtistHotness());
    assertEquals(song.getDuration(), newSong.getDuration());
    assertEquals(song.getFamiliarity(), newSong.getFamiliarity());
    assertEquals(song.getRelease(), newSong.getRelease());
    assertEquals(song.getTitle(), newSong.getTitle());
    assertEquals(song.getTrackId(), newSong.getTrackId());
    assertEquals(song.getYear(), newSong.getYear());
    
    
    assertEquals(song.getArtist().getArtistId(), newSong.getArtist().getArtistId());
    assertEquals(song.getArtist().getArtistName(), newSong.getArtist().getArtistName());
    assertEquals(song.getArtist().getArtistMid(), newSong.getArtist().getArtistMid());
    assertEquals(song.getArtist().getTrackId(), newSong.getArtist().getTrackId());
  }
}
