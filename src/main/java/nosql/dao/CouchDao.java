package nosql.dao;

import nosql.model.Artist;
import nosql.model.Location;
import nosql.model.Song;

import org.jcouchdb.db.Database;

import com.google.common.base.Optional;

public class CouchDao implements NoSqlDao{
	private final Database db;
	
	public static Database createNewDatabase(String dbName){
		final Database db = new Database("localhost", 5984, dbName);
		
		if(db.getServer().listDatabases().contains(dbName)){
			db.getServer().deleteDatabase(dbName);
			db.getServer().createDatabase(dbName);
		}
		
		
		return db;
	}
	
	public CouchDao(Database db){
		this.db = db;
	}

	@Override
	public void insertArtist(Artist artist) {
		db.createDocument(artist);
	}

	@Override
	public void updateLatLong(String artistId, Location location) {
		final Optional<Artist> artist = getArtist(artistId);
		
		if(artist.isPresent()){
			artist.get().setLocation(location);
			db.updateDocument(artist.get());
		}
	}

	@Override
	public void updateNewSimilarities(String target, String similar) {
	  
	}

	@Override
	public void updateTerm(String artistId, String term) {
	  final Optional<Artist> artist = getArtist(artistId);
		
	  if(artist.isPresent()){
	    artist.get().getTerms().add(term);
	    db.updateDocument(artist.get());
	  }
	}

	@Override
	public Optional<Artist> getArtist(String artistId) {
		final Artist artist = db.getDocument(Artist.class, artistId);
		
		return Optional.fromNullable(artist);
	}

	@Override
	public void insertSong(Song song) {
		db.createDocument(song);
		
	}
	
}
