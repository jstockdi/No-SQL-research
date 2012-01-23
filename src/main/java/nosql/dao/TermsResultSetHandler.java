package nosql.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import knzn.db.ResultSetHandler;

public class TermsResultSetHandler implements ResultSetHandler<Void> {

  private int count = 0;
  private final NoSqlDao noSqlDao;
  
  public TermsResultSetHandler(NoSqlDao noSqlDao) {
    this.noSqlDao = noSqlDao;
  }
  
  public Void handle(ResultSet resultSet) throws SQLException {
    
    String artistId = resultSet.getString("artist_id");
    String term = resultSet.getString("term");
    noSqlDao.updateTerm(artistId, term);
    
    count++;
    return null;
  }
  
  public int getCount() {
    return count;
  }

}
