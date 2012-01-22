package nosql;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import nosql.dao.NoSqlDao;
import nosql.io.ArtistLineProcessor;
import nosql.io.ArtistLocationLineProcessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Charsets;
import com.google.common.base.Stopwatch;
import com.google.common.io.CharStreams;
import com.google.common.io.Files;
import com.google.common.io.InputSupplier;
import com.google.common.io.LineProcessor;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class PopulateDatasource {

  private final NoSqlDao noSqlDao;


  public static void main(String[] args) throws IOException {
    
    final DatastoreEnum datastore;
    try {
      datastore = DatastoreEnum.valueOf(args[0]);
    } catch (Exception e) {
      throw new IllegalArgumentException(
              "Could not determine datastore from args");
    }
    
    final String baseDir = args[1];
    
    LOGGER.info("Using baseDir: " + baseDir);
    LOGGER.info("Using datastore: " + datastore);
    
    final String artistFilename = baseDir + "AdditionalFiles/subset_unique_artists.txt";
    final String artistLocationsFilename = baseDir + "AdditionalFiles/subset_artist_location.txt";
    
    final Injector injector = Guice.createInjector(new ApplicationModule(datastore));
    final NoSqlDao noSqlDao = injector.getInstance(NoSqlDao.class);
    
    PopulateDatasource populateDatasource = new PopulateDatasource(noSqlDao);

    populateDatasource.loadArtists(artistFilename);
    
    populateDatasource.loadArtistLocations(artistLocationsFilename);
    

  }
  
  private void loadArtistLocations(String artistLocationsFilename) throws IOException {
    Stopwatch stopwatch = new Stopwatch().start();
    LineProcessor<Integer> artistLocationProcessor = new ArtistLocationLineProcessor(noSqlDao);
    populate(new File(artistLocationsFilename), artistLocationProcessor);
    
    int count = artistLocationProcessor.getResult();
    LOGGER.info("Updated " + count + " artists in millis: " + stopwatch.elapsedMillis());
    LOGGER.info("Updates per second " + ((double)count / (double)stopwatch.elapsedMillis()*1000));
    
  }

  public PopulateDatasource(NoSqlDao noSqlDao) {
    this.noSqlDao = noSqlDao;
  }


  private void loadArtists(String artistFilename) throws IOException {
    
    Stopwatch stopwatch = new Stopwatch().start();
    LineProcessor<Integer> artistLineProcessor = new ArtistLineProcessor(noSqlDao);
    populate(new File(artistFilename), artistLineProcessor);
    
    int count = artistLineProcessor.getResult();
    LOGGER.info("Loaded " + count + " artists in millis: " + stopwatch.elapsedMillis());
    LOGGER.info("Inserts per second " + ((double)count / (double)stopwatch.elapsedMillis()*1000));
    
  }


  private static final Logger LOGGER = LoggerFactory.getLogger(PopulateDatasource.class);
  
  
  //XXX Fix generic warnings
  public void populate(File dataFile, LineProcessor callback) throws IOException {
    
    InputSupplier<InputStreamReader> inputSupplier = Files
            .newReaderSupplier(dataFile, Charsets.US_ASCII);

    CharStreams.readLines(inputSupplier, callback);

  }

}
