package nosql;

import nosql.model.Artist;

public class FakeData {

  public static Artist createArtist(){
    return new Artist("123","3242","ERR42","Fresh Beat Band");
  }

}
