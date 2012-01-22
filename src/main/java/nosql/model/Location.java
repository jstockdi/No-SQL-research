package nosql.model;

import com.google.common.base.Objects;

public class Location {

  private final double latitude;
  private final double longitude;

  public Location(double latitude, double longitude) {
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public double getLatitude() {
    return latitude;
  }

  public double getLongitude() {
    return longitude;
  }
  
  @Override
  public int hashCode() {
    return Objects.hashCode(getLatitude(), getLongitude());
  }
  
  @Override
  public boolean equals(Object obj) {
    if(obj instanceof Location){
      Location other = (Location) obj;
      return Objects.equal(getLatitude(), other.getLatitude())
              && Objects.equal(getLongitude(), other.getLongitude());  
    }else{
      return false;
    }
    
  }

}
