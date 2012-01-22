package nosql;

import static org.mockito.Mockito.verify;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.mockito.Mockito;

import com.google.common.io.LineProcessor;

public class PopulateDatasourceTest {

 
  @Test
  public void testPopulateArtists() throws IOException {
    
    PopulateDatasource populateDatasource = new PopulateDatasource();
    File dataFile = new File("src/test/resources/head_unique_artists.txt");
    LineProcessor<String> callback = Mockito.mock(LineProcessor.class);
    Mockito.when(callback.processLine("AR009211187B989185<SEP>9dfe78a6-6d91-454e-9b95-9d7722cbc476<SEP>TRAWSCW12903CD6C7E<SEP>Carroll Thompson")).thenReturn(true);
    Mockito.when(callback.processLine("AR00A6H1187FB5402A<SEP>312c14d9-7897-4608-944a-c5b1c76ae682<SEP>TRAKWGL12903CB8529<SEP>The Meatmen")).thenReturn(true);
    
    populateDatasource.populate(dataFile, callback);

    verify(callback).processLine("AR009211187B989185<SEP>9dfe78a6-6d91-454e-9b95-9d7722cbc476<SEP>TRAWSCW12903CD6C7E<SEP>Carroll Thompson");
    verify(callback).processLine("AR00A6H1187FB5402A<SEP>312c14d9-7897-4608-944a-c5b1c76ae682<SEP>TRAKWGL12903CB8529<SEP>The Meatmen");
  }

}
