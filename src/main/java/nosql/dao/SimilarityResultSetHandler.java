package nosql.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import knzn.db.ResultSetHandler;

public class SimilarityResultSetHandler implements ResultSetHandler<Void> {

  private int count = 0;
  private final NoSqlDao noSqlDao;
  public SimilarityResultSetHandler(NoSqlDao noSqlDao) {
    this.noSqlDao = noSqlDao;
  }
  
  public Void handle(ResultSet resultSet) throws SQLException {
    
    String target = resultSet.getString("target");
    String similar = resultSet.getString("similar");
    noSqlDao.updateNewSimilarities(target,similar);
    
    count++;
    return null;
  }
  
  public int getCount() {
    return count;
  }

}
