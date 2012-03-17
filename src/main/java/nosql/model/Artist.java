package nosql.model;

import java.util.Set;

import org.bson.types.ObjectId;
import org.svenson.JSONProperty;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Index;
import com.google.code.morphia.annotations.Reference;
import com.google.common.base.Objects;
import com.google.common.collect.Sets;

@Index(background = false, unique = true, value = "artistId")
public class Artist {

  @Id
  private ObjectId objectId;

  private String artistId;
  private String artistMid;
  private String trackId;
  private String artistName;
  private String revision;

  @Reference
  private Set<Artist> similar = Sets.newHashSet(); // XXX once working w/
                                                   // morphia, try to change
                                                   // list to Iterables or
                                                   // something more generic.

  private Set<String> terms = Sets.newHashSet();

  @Embedded
  private Location location;

  public Artist() {
  }

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
              && Objects.equal(getLocation(), other.getLocation())
              && Objects.equal(getSimilar(), other.getSimilar())
              && Objects.equal(getTerms(), other.getTerms());
    } else {
      return false;
    }
  }
  @JSONProperty(value = "_id", ignoreIfNull = false)
  public String getArtistId() {
    return artistId;
  }

  @JSONProperty(value = "artistMid", ignoreIfNull = true)
  public String getArtistMid() {
    return artistMid;
  }

  @JSONProperty(value = "artistName", ignoreIfNull = true)
  public String getArtistName() {
    return artistName;
  }

  @JSONProperty(ignore = true)
  public ObjectId getObjectId() {
    return objectId;
  }

  @JSONProperty(value = "trackId", ignoreIfNull = true)
  public String getTrackId() {
    return trackId;
  }

  @JSONProperty(value = "_rev", ignoreIfNull = true)
  public String getRevision() {
    return revision;
  }

  @JSONProperty(value = "similar", ignoreIfNull = true)
  public Set<Artist> getSimilar() {
    return similar;
  }

  @JSONProperty(value = "terms", ignoreIfNull = true)
  public Set<String> getTerms() {
    return terms;
  }

  public void setRevision(String revision) {
    this.revision = revision;
  }

  public void setArtistName(String artistName) {
    this.artistName = artistName;
  }

  public void setArtistId(String artistId) {
    this.artistId = artistId;
  }

  public void setLocation(Location location) {
    this.location = location;
  }

  public void setArtistMid(String artistMid) {
    this.artistMid = artistMid;
  }

  public void setObjectId(ObjectId objectId) {
    this.objectId = objectId;
  }

  public void setSimilar(Set<Artist> similar) {
    this.similar = similar;
  }

  public void setTerms(Set<String> terms) {
    this.terms = terms;
  }

  public void setTrackId(String trackId) {
    this.trackId = trackId;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(getArtistId(), getArtistMid(), getTrackId(),
            getArtistName(), getLocation(), getSimilar(), getTerms());
  }
  @Override
  public String toString() {
    return Objects.toStringHelper(this).add("artistId", getArtistId())
            .add("artistMid", getArtistMid()).add("trackId", getTrackId())
            .add("name", getArtistName()).add("location", getLocation())
            .add("terms", getTerms()).toString();
  }

  @JSONProperty(value = "location", ignoreIfNull = true)
  public Location getLocation() {
    return location;
  }
}
