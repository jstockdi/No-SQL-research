package nosql.model;

import static org.junit.Assert.*;

import nosql.FakeData;

import org.junit.Before;
import org.junit.Test;

public class ArtistTest {

  private Artist expected;
  private Artist actual;
  private Artist notEqual;

  @Before
  public void setup(){
    expected = FakeData.createArtist();
    actual = FakeData.createArtist();
    notEqual = new Artist("e3", "232", "W3", "My Name");
  }
  @Test
  public void testHashCode() {
    assertTrue(expected.hashCode() == actual.hashCode());
  }

  @Test
  public void testEquals() {
    assertTrue(expected.equals(actual));
  }

  @Test
  public void testNotEquals() {
    assertFalse(notEqual.equals(actual));
  }
  
  @Test
  public void testInEqualHashcode() {
    assertFalse(notEqual.hashCode() == actual.hashCode());
  }
}

