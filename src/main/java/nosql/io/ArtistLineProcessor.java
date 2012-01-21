package nosql.io;

import java.io.IOException;
import java.util.Iterator;

import nosql.dao.NoSqlDao;
import nosql.model.Artist;

import com.google.common.base.Splitter;
import com.google.common.io.LineProcessor;

public final class ArtistLineProcessor
        implements
          LineProcessor<Artist> {

  private final NoSqlDao noSqlLoader;
  public ArtistLineProcessor(NoSqlDao noSqlLoader) {
    this.noSqlLoader = noSqlLoader;
  }

  public Artist getResult() {
    return null;
  }

  public boolean processLine(String line) throws IOException {

    Iterator<String> iterator = Splitter.on("<SEP>").split(line).iterator();
    
    String artistId = iterator.next();
    String artistMid = iterator.next();
    String trackId = iterator.next();
    String artistName = iterator.next();
    
    Artist artist = new Artist(artistId, artistMid, trackId, artistName);

    noSqlLoader.insertArtist(artist);

    return true;
  }
}