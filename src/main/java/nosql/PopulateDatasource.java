package nosql;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import nosql.dao.NoSqlDao;
import nosql.io.ArtistLineProcessor;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.google.common.io.Files;
import com.google.common.io.InputSupplier;
import com.google.common.io.LineProcessor;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class PopulateDatasource {

  public static void main(String[] args) {
    final Datastore datastore;
    try {
      datastore = Datastore.valueOf(args[0]);
    } catch (Exception e) {
      throw new IllegalArgumentException(
              "Could not determine datastore from args");
    }
    
    final Injector injector = Guice.createInjector(new ApplicationModule());
    final NoSqlDao noSqlLoader = injector.getInstance(NoSqlDao.class);
  }

  private final NoSqlDao noSqlLoader;
  

  public PopulateDatasource(NoSqlDao noSqlLoader) {
    this.noSqlLoader = noSqlLoader;
  }
  
  public void populateArtists(File artistFile) throws IOException{
    populate(artistFile, new ArtistLineProcessor(noSqlLoader));
  }

  //XXX Fix generic warnings
  private void populate(File dataFile, LineProcessor callback) throws IOException {
    
    InputSupplier<InputStreamReader> inputSupplier = Files
            .newReaderSupplier(dataFile, Charsets.US_ASCII);

    CharStreams.readLines(inputSupplier, callback);

  }

}
