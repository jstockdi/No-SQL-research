package nosql.mongo;

import java.util.List;
import java.util.logging.Logger;

import junit.framework.Assert;
import nosql.mongo.model.Address;
import nosql.mongo.model.Hotel;

import org.bson.types.ObjectId;
import org.junit.Test;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.mongodb.Mongo;

/***
 * Requires a local MongoDB running. 
 */
public class MongoTest {

  private static Hotel createHotel(){
    Hotel hotel = new Hotel();
    hotel.setName("My Hotel");
    hotel.setStars(4);

    Address address = new Address();
    address.setStreet("123 Some street");
    address.setCity("Some city");
    address.setPostCode("123 456");
    address.setCountry("Some country");

    //set address
    hotel.setAddress(address);
    
    return hotel;
  }
  
  private static final Datastore ds = createDatastore();
  private static final Logger LOGGER = Logger.getLogger(MongoTest.class.getName());
  
  @Test
  public void testCrud() {
    
    ds.delete(ds.createQuery(Hotel.class));  //delete all previous records
    
    Hotel expected = createHotel();
    Assert.assertNull(expected.getHotelId());
    
    ds.save(expected);

    ObjectId hotelId = expected.getHotelId();
    LOGGER.info( "ObjectId: " + hotelId);

    Hotel actual = ds.get(Hotel.class, hotelId);
    Assert.assertEquals(expected.getName(), actual.getName());
    
    List<Hotel> fourStarHotels = ds.find(Hotel.class, "stars >=", 4).asList();
    Assert.assertEquals(1, fourStarHotels.size());

  }

  private static Datastore createDatastore() {
    Datastore ds = null;
    try {
      Mongo mongo = new Mongo("127.0.0.1"); //Should only be one instance per JVM of these
      Morphia morphia = new Morphia();
      morphia
        .map(Hotel.class)
        .map(Address.class);
      ds = morphia.createDatastore(mongo, "test_ds");
      
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return ds;
  }
}
