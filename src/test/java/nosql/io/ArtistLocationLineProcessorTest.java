package nosql.io;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

import nosql.dao.NoSqlDao;
import nosql.model.Location;

import org.junit.Test;

import com.google.common.base.Joiner;

public class ArtistLocationLineProcessorTest {

  private static final String TRACK_ID = "TRAHDNJ128F4241432";
  private static final String ARTIST_NAME = "Slipknot";
  private static final double LONG = -93.61566;
  private static final double LAT = 41.58979;
  private static final String ARTIST_ID = "AR0KBXO1187B996460";
  private static final String FIELD_SEPARATOR = "<SEP>";

  @Test
  public void testProcessLine() throws IOException {
    NoSqlDao mockDao = mock(NoSqlDao.class);
    ArtistLocationLineProcessor lineProcessor = new ArtistLocationLineProcessor(
            mockDao);

    String line = Joiner.on(FIELD_SEPARATOR).join(ARTIST_ID, LAT, LONG,
            TRACK_ID, ARTIST_NAME);
    lineProcessor.processLine(line);
    
    verify(mockDao).updateLatLong(ARTIST_ID, new Location(LAT, LONG));
    
  }
}
