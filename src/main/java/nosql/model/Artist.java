package nosql.model;

import com.google.common.base.Objects;

public class Artist {

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
  public String getArtistId() {
    return artistId;
  }
  public String getArtistMid() {
    return artistMid;
  }
  public String getTrackId() {
    return trackId;
  }
  public String getArtistName() {
    return artistName;
  }
  
  @Override
  public int hashCode() {
    return Objects.hashCode(getArtistId(), getArtistMid(), getTrackId(), getArtistName());
  }

  @Override
  public boolean equals(Object obj) {
    if(obj instanceof Artist){
      final Artist other = (Artist) obj;
      return Objects.equal(getArtistId(), other.getArtistId())
          && Objects.equal(getArtistMid(), other.getArtistMid())
          && Objects.equal(getTrackId(), other.getTrackId())
          && Objects.equal(getArtistName(), other.getArtistName());
  } else{
      return false;
  }
  }
}
