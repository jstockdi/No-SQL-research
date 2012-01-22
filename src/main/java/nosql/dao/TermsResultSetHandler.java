package nosql.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import knzn.db.ResultSetHandler;

public class TermsResultSetHandler implements ResultSetHandler<Void> {

  private int count = 0;
  private final NoSqlDao noSqlDao;
  private final String termColumnName;
  
  public TermsResultSetHandler(NoSqlDao noSqlDao, String termColumnName) {
    this.noSqlDao = noSqlDao;
    this.termColumnName = termColumnName;
  }
  
  public Void handle(ResultSet resultSet) throws SQLException {
    
    String artistId = resultSet.getString("artist_id");
    String term = resultSet.getString(termColumnName);
    noSqlDao.updateTerm(artistId, term);
    
    count++;
    return null;
  }
  
  public int getCount() {
    return count;
  }

}
