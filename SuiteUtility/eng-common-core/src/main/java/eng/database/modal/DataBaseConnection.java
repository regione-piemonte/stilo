// 
// Decompiled by Procyon v0.5.36
// 

package eng.database.modal;

import eng.database.exception.EngSqlNoApplException;
import java.sql.SQLException;
import eng.database.exception.ConnectionFailed;
import java.sql.DriverManager;
import oracle.jdbc.driver.OracleDriver;
import java.sql.Connection;

public final class DataBaseConnection
{
    public static String urlDb;
    public static String userDb;
    public static String passwdDb;
    private static Connection con;
    
    private DataBaseConnection() throws EngSqlNoApplException {
        try {
            new OracleDriver();
            (DataBaseConnection.con = DriverManager.getConnection(DataBaseConnection.urlDb, DataBaseConnection.userDb, DataBaseConnection.passwdDb)).setAutoCommit(false);
        }
        catch (SQLException e) {
            throw new ConnectionFailed("Connection Faild." + DataBaseConnection.urlDb + " USER=" + DataBaseConnection.userDb);
        }
    }
    
    public static final Connection getDataBaseConnection() throws EngSqlNoApplException {
        try {
            if (DataBaseConnection.con == null || (DataBaseConnection.con != null && DataBaseConnection.con.isClosed())) {
                new DataBaseConnection();
            }
        }
        catch (SQLException ex) {}
        return DataBaseConnection.con;
    }
    
    public static void setUrlDb(final String url) {
        DataBaseConnection.urlDb = url;
    }
    
    public static void setUserDb(final String user) {
        DataBaseConnection.userDb = user;
    }
    
    public static void setPasswdDb(final String passwd) {
        DataBaseConnection.passwdDb = passwd;
    }
    
    static {
        DataBaseConnection.con = null;
    }
}
