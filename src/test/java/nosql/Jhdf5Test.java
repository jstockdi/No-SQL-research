package nosql;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import ch.systemsx.cisd.hdf5.HDF5DataBlock;
import ch.systemsx.cisd.hdf5.HDF5DataSetInformation;
import ch.systemsx.cisd.hdf5.HDF5Factory;
import ch.systemsx.cisd.hdf5.HDF5LinkInformation;
import ch.systemsx.cisd.hdf5.IHDF5Reader;
import ch.systemsx.cisd.hdf5.IHDF5SimpleReader;
import ch.systemsx.cisd.hdf5.IHDF5SimpleWriter;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

public class Jhdf5Test {

  public static void browse(IHDF5Reader reader,
          List<HDF5LinkInformation> members, String prefix) {
    for (HDF5LinkInformation info : members) {
      System.out.println(prefix + info.getPath() + ":" + info.getType());
      switch (info.getType()) {
        case DATASET :
          HDF5DataSetInformation dsInfo = reader.getDataSetInformation(info
                  .getPath());
          System.out.println(prefix + "     " + dsInfo);
          break;
        case SOFT_LINK :
          System.out.println(prefix + "     -> "
                  + info.tryGetSymbolicLinkTarget());
          break;
        case GROUP :
          browse(reader,
                  reader.getGroupMemberInformation(info.getPath(), true),
                  prefix + "  ");
          break;
        default :
          break;
      }
    }
  }

  @Test
  public void testHf5Lib() {

    float[] expected = new float[1001];
    for (int i = 0; i < expected.length; i++) {
      expected[i] = (float) i;
    }

    String dataFile = "target/farray.h5";
    IHDF5SimpleWriter writer = HDF5Factory.open(dataFile);
    writer.writeFloatArray("mydata", expected);
    writer.close();

    IHDF5SimpleReader reader = HDF5Factory.openForReading(dataFile);
    float[] actualdata = reader.readFloatArray("mydata");
    reader.close();

    Assert.assertEquals(expected[1000], actualdata[1000]);
  }

  @Test
  public void testHf5Browse() {

    String dataFile = "src/test/resources/TRBIJRN128F425F3DD.h5";
    IHDF5Reader reader = HDF5Factory.openForReading(dataFile);
    browse(reader, reader.getGroupMemberInformation("/", true), "");

    
    //Read bars_confidence
    HDF5DataSetInformation dataSetInfo = reader.getDataSetInformation("/analysis/bars_confidence");
    System.out.println("ele: " + dataSetInfo.getNumberOfElements());
    System.out.println("typeInfo: " + dataSetInfo.getTypeInformation());
    System.out.println("sl: " + dataSetInfo.getStorageLayout());
    
    System.out.println("bc: " + dataSetInfo);
    
    Iterable<HDF5DataBlock<float[]>> blocks = reader.getFloatArrayNaturalBlocks("/analysis/bars_confidence");
    
    System.out.println(Iterables.size(blocks));
    float[] data = blocks.iterator().next().getData();
    for(float floatD : data){
      System.out.println(floatD);
    }
    
    
    reader.close();

  }

}
