package nosql;

import nosql.model.Artist;
import nosql.model.Song;

public class FakeData {

  public static Artist createArtist(){
    return new Artist("123","3242","ERR42","Fresh Beat Band");
  }

  public static Song createSong() {
    Song song = new Song();
    song.setArtist(createArtist());
    song.setArtistHotness(10d);
    song.setDuration(20l);
    song.setFamiliarity(100l);
    song.setRelease("release");
    song.setSongId("3EAER");
    song.setTitle("Important Song");
    song.setTrackId("2EA6F");
    song.setYear(1933);
    return song;
  }

}
