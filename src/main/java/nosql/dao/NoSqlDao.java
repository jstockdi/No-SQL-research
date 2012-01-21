package nosql.dao;

import java.util.Iterator;

import nosql.model.Artist;

public interface NoSqlDao{
  void insertArtist(Artist artist);
}