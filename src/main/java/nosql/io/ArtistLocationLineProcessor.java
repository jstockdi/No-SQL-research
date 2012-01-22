package nosql.io;

import java.io.IOException;
import java.util.Iterator;

import nosql.dao.NoSqlDao;
import nosql.model.Location;

import com.google.common.base.Splitter;
import com.google.common.io.LineProcessor;

public final class ArtistLocationLineProcessor
        implements
          LineProcessor<Integer> {

  private int count = 0;
  
  private final NoSqlDao noSqlLoader;
  public ArtistLocationLineProcessor(NoSqlDao noSqlLoader) {
    this.noSqlLoader = noSqlLoader;
  }

  public Integer getResult() {
    return count;
  }

  public boolean processLine(String line) throws IOException {

    Iterator<String> iterator = Splitter.on("<SEP>").split(line).iterator();
    
    String artistId = iterator.next();
    double latitude = Double.parseDouble(iterator.next());
    double longitude = Double.parseDouble(iterator.next());

    
    noSqlLoader.updateLatLong(artistId, new Location(latitude, longitude));

    count++;
    return true;
  }
}