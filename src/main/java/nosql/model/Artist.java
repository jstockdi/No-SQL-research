package nosql.model;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Id;
import com.google.common.base.Objects;

public class Artist {

  @Id
  private ObjectId objectId;
  
  private final String artistId;
  private final String artistMid;
  private final String trackId;
  private final String artistName;

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
              && Objects.equal(getArtistName(), other.getArtistName());
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
  
  @Override
  public int hashCode() {
    return Objects.hashCode(getArtistId(), getArtistMid(), getTrackId(),
            getArtistName());
  }
  @Override
  public String toString() {
    return Objects.toStringHelper(this)
            .add("artistId", getArtistId())
            .add("artistMid", getArtistMid())
            .add("trackId", getTrackId())
            .add("name", getArtistName())
            .toString();
  }
}
