package nosql.model;

import org.bson.types.ObjectId;

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


  public Artist getArtist() {
    return artist;
  }
  
  public double getArtistHotness() {
    return artistHotness;
  }
  public double getDuration() {
    return duration;
  }
  public double getFamiliarity() {
    return familiarity;
  }
  public ObjectId getObjectId() {
    return objectId;
  }
  
  public String getRelease() {
    return release;
  }
  
  public String getSongId() {
    return songId;
  }
  public String getTitle() {
    return title;
  }
  public String getTrackId() {
    return trackId;
  }
  public int getYear() {
    return year;
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
