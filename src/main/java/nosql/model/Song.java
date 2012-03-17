package nosql.model;

import org.bson.types.ObjectId;
import org.svenson.JSONProperty;

import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Index;
import com.google.code.morphia.annotations.Reference;
import com.google.common.base.Objects;

@Index(background=false, unique=true, value="trackId")
public class Song {

  @Id
  private ObjectId objectId;
  private String trackId;
  private String title;
  private String songId;
  private String release;

  @Reference 
  private Artist artist;
  private double duration;
  private double familiarity;
  private int year;
  private double artistHotness;
  private String revision;



  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Song) {
      final Song other = (Song) obj;
      return 
              Objects.equal(getArtist(), other.getArtist())
              && Objects.equal(getArtistHotness(), other.getArtistHotness())
              && Objects.equal(getDuration(), other.getDuration())
              && Objects.equal(getFamiliarity(), other.getFamiliarity())
              && Objects.equal(getRelease(), other.getRelease())
              && Objects.equal(getSongId(), other.getSongId())
              && Objects.equal(getTitle(), other.getTitle())
              && Objects.equal(getTrackId(), other.getTrackId())
              && Objects.equal(getYear(), other.getYear());
              
    } else {
      return false;
    }
  }

  @JSONProperty(value = "artist", ignoreIfNull = true)
  public Artist getArtist() {
    return artist;
  }
  
  @JSONProperty(value = "artistHotness", ignoreIfNull = true)
  public double getArtistHotness() {
    return artistHotness;
  }
  
  @JSONProperty(value = "duration", ignoreIfNull = true)
  public double getDuration() {
    return duration;
  }
  
  @JSONProperty(value = "familiarity", ignoreIfNull = true)
  public double getFamiliarity() {
    return familiarity;
  }
  @JSONProperty(ignore = true)
  public ObjectId getObjectId() {
    return objectId;
  }
  
  @JSONProperty(value = "release", ignoreIfNull = true)
  public String getRelease() {
    return release;
  }
  
  @JSONProperty(value = "_id", ignoreIfNull = false)
  public String getSongId() {
    return songId;
  }
  
  @JSONProperty(value = "title", ignoreIfNull = true)
  public String getTitle() {
    return title;
  }
  
  @JSONProperty(value = "trackId", ignoreIfNull = true)
  public String getTrackId() {
    return trackId;
  }
  
  @JSONProperty(value = "year", ignoreIfNull = true)
  public int getYear() {
    return year;
  }
  
  @JSONProperty(value = "_rev", ignoreIfNull = true)
  public String getRevision() {
    return revision;
  }
  
  @Override
  public int hashCode() {
    return Objects.hashCode(
            getArtist(), 
            getArtistHotness(),
            getDuration(), 
            getFamiliarity(), 
            getRelease(), 
            getSongId(),
            getTitle(), 
            getTrackId(), 
            getYear());
  }

  
  public void setArtist(Artist artist) {
    this.artist = artist;
  }
  
  
  public void setArtistHotness(double artistHotness) {
    this.artistHotness = artistHotness;
  }
  public void setDuration(double duration) {
    this.duration = duration;
  }
  public void setFamiliarity(double familiarity) {
    this.familiarity = familiarity;
  }

  public void setRelease(String release) {
    this.release = release;
  }

  public void setSongId(String songId) {
    this.songId = songId;
  }
  
  public void setTitle(String title) {
    this.title = title;
  }
  public void setTrackId(String trackId) {
    this.trackId = trackId;
  }
  

  public void setYear(int year) {
    this.year = year;
  }
  
  public void setRevision(String revision) {
    this.revision = revision;
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(this)
            
            .add("artistHotness", getArtistHotness())
            .add("duration", getDuration())
            .add("trackId", getTrackId())
            .add("title", getTitle())
            .add("artist", getArtist())
            
            .toString();
  }
}
