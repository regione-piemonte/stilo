package eng.database.modal;

import java.sql.*;
import eng.database.exception.*;


public final class DataBaseConnection
{
	public static String urlDb;
	public static String userDb;	
	public static String passwdDb;		
	
	private static Connection con = null;
    
	private DataBaseConnection() throws EngSqlNoApplException
	{
	   try
	   {
          new oracle.jdbc.driver.OracleDriver();
//          con = DriverManager.getConnection("jdbc:oracle:thin:@128.11.10.4:1521:orcl","pron","onpr");
          con = DriverManager.getConnection(urlDb,userDb,passwdDb);
		  con.setAutoCommit(false);
	   }catch(SQLException e){
	   	throw new ConnectionFailed("Connection Faild."+ urlDb +" USER="+userDb);
	   }
	}
	
	public static final Connection getDataBaseConnection() 
	      throws EngSqlNoApplException
	{
      try
      {	
		if (con == null || (con != null && con.isClosed())) 
			new DataBaseConnection();
      }catch(SQLException sqle){}
		return con;
	}

    public static void setUrlDb(String url){
	   urlDb = url;
    }
    public static void setUserDb(String user){
       userDb = user;
    }
    public static void setPasswdDb(String passwd){
       passwdDb = passwd;
    }
	
}
