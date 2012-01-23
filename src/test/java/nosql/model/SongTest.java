package nosql.model;

import static org.junit.Assert.*;

import nosql.FakeData;

import org.junit.Before;
import org.junit.Test;

public class SongTest {
  
  private Song actual;
  private Song expected;
  private Song notEqual;

  @Before
  public void setup(){
    actual = FakeData.createSong();
    expected = FakeData.createSong();
    
    notEqual = FakeData.createSong();
    notEqual.setTitle("Not my title");
    
  }

  @Test
  public void testHashCode() {
    assertTrue(expected.hashCode() == actual.hashCode());
  }

  @Test
  public void testEqualsObject() {
    assertTrue(expected.equals(actual));
  }
  
  @Test
  public void testNotHashCode(){
    assertFalse(expected.hashCode() == notEqual.hashCode());
  }
  
  @Test
  public void testNotEquals(){
    assertFalse(expected.equals(notEqual));
  }

}
