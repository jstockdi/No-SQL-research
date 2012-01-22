package nosql.dao.mongo.model;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Id;


public class Hotel {

  @Id 
  private ObjectId hotelId;
  
  public ObjectId getHotelId() {
    return hotelId;
  }

  public void setHotelId(ObjectId hotelId) {
    this.hotelId = hotelId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getStars() {
    return stars;
  }

  public void setStars(int stars) {
    this.stars = stars;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  private String name;
  private int stars;
  
  @Embedded
  private Address address;

}