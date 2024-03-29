package nosql.io;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.IOException;

import nosql.dao.NoSqlDao;
import nosql.model.Artist;

import org.junit.Test;

import com.google.common.base.Joiner;
import com.google.common.io.LineProcessor;

public class ArtistLineProcessorTest {

  private static final String ARTIST_NAME = "NOFX";
  private static final String TRACK_ID = "TRAYABW128F4235888";
  private static final String ARTIST_MID = "dcaa4f81-bfb7-44eb-8594-4e74f004b6e4";
  private static final String ARTIST_ID = "AR06EB01187FB40150";
  private static final String FIELD_SEPARATOR = "<SEP>";
  @Test
  public void test() throws IOException {
    Artist expectedArtist = new Artist(ARTIST_ID, ARTIST_MID, TRACK_ID, ARTIST_NAME);
    NoSqlDao mockDao = mock(NoSqlDao.class);

    LineProcessor<Integer> lineProcessor = new ArtistLineProcessor(mockDao);

    String line = Joiner.on(FIELD_SEPARATOR).join(ARTIST_ID, ARTIST_MID, TRACK_ID, ARTIST_NAME);
    lineProcessor.processLine(line);
    verify(mockDao).insertArtist(expectedArtist);
  }
}
