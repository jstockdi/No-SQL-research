package nosql;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.sql.DataSource;

import knzn.db.Database;
import knzn.db.DatabaseImpl;
import knzn.db.ResultSetHandler;

import nosql.dao.NoSqlDao;
import nosql.dao.SimilarityResultSetHandler;
import nosql.dao.SongResultSetHandler;
import nosql.dao.TermsResultSetHandler;
import nosql.io.ArtistLineProcessor;
import nosql.io.ArtistLocationLineProcessor;

import org.apache.commons.dbcp.BasicDataSource;
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


  private static DataSource createDataSource(String url){
    BasicDataSource dataSource = new BasicDataSource();
    dataSource.setDriverClassName("org.sqlite.JDBC");
    dataSource.setUrl(url);
     return dataSource;
  }
  
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
    final String artistSimilarityUrl = "jdbc:sqlite:" + baseDir + "AdditionalFiles/subset_artist_similarity.db";
    final String artistTermsUrl = "jdbc:sqlite:" + baseDir + "AdditionalFiles/subset_artist_term.db";
    final String songUrl = "jdbc:sqlite:" + baseDir + "AdditionalFiles/subset_track_metadata.db";
    
    final Injector injector = Guice.createInjector(new ApplicationModule(datastore));
    final NoSqlDao noSqlDao = injector.getInstance(NoSqlDao.class);
    
    PopulateDatasource populateDatasource = new PopulateDatasource(noSqlDao);

    populateDatasource.loadArtists(artistFilename);
    
//    populateDatasource.loadArtistLocations(artistLocationsFilename);
    
//    populateDatasource.loadArtistSimilarities(artistSimilarityUrl);

//    populateDatasource.loadArtistTerms(artistTermsUrl);
    
    populateDatasource.loadSongs(songUrl);
  }
  
  private void loadSongs(String songUrl) {

    SongResultSetHandler resultSetHandler = new SongResultSetHandler(noSqlDao);
    String songQuery = "select * from songs";
    
    Stopwatch stopwatch = new Stopwatch().start();
    populateSqlite(songUrl, songQuery, resultSetHandler);
    int count = resultSetHandler.getCount();
    
    LOGGER.info("Loaded " + count + " songs in millis: " + stopwatch.elapsedMillis());
    LOGGER.info("Updates per second " + ((double)count / (double)stopwatch.elapsedMillis()*1000));
    
    
  }

  private void loadArtistTerms(String artistTermsUrl) {
    TermsResultSetHandler resultSetHandler = new TermsResultSetHandler(noSqlDao);
    String artistSimilarityQuery = "select artist_id, mbtag as term from artist_mbtag UNION select artist_id, term from artist_term";
    
    Stopwatch stopwatch = new Stopwatch().start();
    populateSqlite(artistTermsUrl, artistSimilarityQuery, resultSetHandler);
    int count = resultSetHandler.getCount();
    
    LOGGER.info("Loaded " + count + " terms in millis: " + stopwatch.elapsedMillis());
    LOGGER.info("Updates per second " + ((double)count / (double)stopwatch.elapsedMillis()*1000));
    
  }

  private void loadArtistSimilarities(String artistSimilarityUrl) {

    SimilarityResultSetHandler resultSetHandler = new SimilarityResultSetHandler(noSqlDao);
    String artistSimilarityQuery = "select * from similarity";
    
    Stopwatch stopwatch = new Stopwatch().start();
    populateSqlite(artistSimilarityUrl, artistSimilarityQuery, resultSetHandler);
    int count = resultSetHandler.getCount();
    
    LOGGER.info("Loaded " + count + " similarities in millis: " + stopwatch.elapsedMillis());
    LOGGER.info("Updates per second " + ((double)count / (double)stopwatch.elapsedMillis()*1000));
  }

  private <T> void populateSqlite(String jdbcUrl, String query,
          ResultSetHandler<T> resultSetHandler) {
    
    DataSource dataSource = createDataSource(jdbcUrl);
    Database database = new DatabaseImpl(dataSource);
    
    database.query(query, resultSetHandler);
    
    
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
  
  
  public <T> void populate(File dataFile, LineProcessor<T> callback) throws IOException {
    
    InputSupplier<InputStreamReader> inputSupplier = Files
            .newReaderSupplier(dataFile, Charsets.US_ASCII);

    CharStreams.readLines(inputSupplier, callback);

  }

}
