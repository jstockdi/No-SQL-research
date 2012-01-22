package nosql.model;

import java.util.List;
import java.util.Set;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Index;
import com.google.code.morphia.annotations.Reference;
import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

@Index(background=false, unique=true, value="artistId")
public class Artist {

  @Id
  private ObjectId objectId;
  
  private String artistId;
  private String artistMid;
  private String trackId;
  private String artistName;
  
  @Reference
  private List<Artist> similar = Lists.newArrayList();  //XXX once working w/ morphia, try to change list to Iterables or something more generic.
  
  private Set<String> terms = Sets.newHashSet();
  
  @Embedded
  private Location location;
  
  public Artist(String artistId, String artistMid, String trackId,
          String artistName) {
    super();
    this.artistId = artistId;
    this.artistMid = artistMid;
    this.trackId = trackId;
    this.artistName = artistName;
  }
  
  
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Artist) {
      final Artist other = (Artist) obj;
      return Objects.equal(getArtistId(), other.getArtistId())
              && Objects.equal(getArtistMid(), other.getArtistMid())
              && Objects.equal(getTrackId(), other.getTrackId())
              && Objects.equal(getArtistName(), other.getArtistName())
              && Objects.equal(getLocation(), other.getLocation());
    } else {
      return false;
    }
  }
  public String getArtistId() {
    return artistId;
  }
  public String getArtistMid() {
    return artistMid;
  }
  public String getArtistName() {
    return artistName;
  }

  public ObjectId getObjectId() {
    return objectId;
  }

  public String getTrackId() {
    return trackId;
  }
  
  public List<Artist> getSimilar() {
    return similar;
  }
  
  //XXX Add similar to hashcode and equals
  @Override
  public int hashCode() {
    return Objects.hashCode(getArtistId(), getArtistMid(), getTrackId(),
            getArtistName(), getLocation());
  }
  @Override
  public String toString() {
    return Objects.toStringHelper(this)
            .add("artistId", getArtistId())
            .add("artistMid", getArtistMid())
            .add("trackId", getTrackId())
            .add("name", getArtistName())
            .add("location", getLocation())
            .toString();
  }
  
  public Location getLocation() {
    return location;
  }
}
