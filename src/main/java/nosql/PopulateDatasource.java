package nosql;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import nosql.dao.NoSqlDao;
import nosql.model.Artist;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.google.common.io.Files;
import com.google.common.io.InputSupplier;
import com.google.common.io.LineProcessor;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class PopulateDatasource {

  private static final class ArtistLineProcessor
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

      Artist artist = new Artist();

      noSqlLoader.insertArtist(artist);

      return true;
    }
  }
  public static void main(String[] args) {

    final Datastore datastore;
    try {
      datastore = Datastore.valueOf(args[0]);
    } catch (Exception e) {
      throw new IllegalArgumentException(
              "Could not determine datastore from args");
    }
  }

  private final Datastore datastore;
  private NoSqlDao noSqlLoader;

  public PopulateDatasource(Datastore datastore) {
    this.datastore = datastore;
    
    Injector injector = Guice.createInjector(new ApplicationModule());
    noSqlLoader = injector.getInstance(NoSqlDao.class);
  }
  
  public void populateArtists(File artistFile) throws IOException{
    populate(artistFile, new ArtistLineProcessor(
            noSqlLoader));
  }

  //XXX Fix generic warnings
  private void populate(File dataFile, LineProcessor callback) throws IOException {
    
    InputSupplier<InputStreamReader> inputSupplier = Files
            .newReaderSupplier(dataFile, Charsets.US_ASCII);

    CharStreams.readLines(inputSupplier, callback);

  }

}
