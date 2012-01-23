package nosql.model;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.utils.IndexDirection;
import com.google.common.base.Objects;

@Embedded
public class Location {

  @Indexed(IndexDirection.GEO2D)
  private final double[] loc;

  public Location(double latitude, double longitude) {
    loc = new double[]{latitude, longitude};
  }

  public double getLatitude() {
    return loc[0];
  }

  public double getLongitude() {
    return loc[1];
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
