package nosql.mock;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.mockito.Mockito;

public class MockResultSet{
  private final ResultSet resultSet;

  public MockResultSet(ResultSet resultSet) {
    this.resultSet = resultSet;
  }
  
  public void mockString(String columnTitle, String value) throws SQLException{
    Mockito.when(resultSet.getString(columnTitle)).thenReturn(value);
  }

  public void mockDouble(String columnTitle, double value) throws SQLException {
    Mockito.when(resultSet.getDouble(columnTitle)).thenReturn(value);
  }

  public void mockLong(String columnTitle, long value) throws SQLException {
    Mockito.when(resultSet.getLong(columnTitle)).thenReturn(value);      
  }
  
  public void mockInt(String columnTitle, int value) throws SQLException {
    Mockito.when(resultSet.getInt(columnTitle)).thenReturn(value);      
  }
  
  public ResultSet getResultSet() {
    return resultSet;
  }

}