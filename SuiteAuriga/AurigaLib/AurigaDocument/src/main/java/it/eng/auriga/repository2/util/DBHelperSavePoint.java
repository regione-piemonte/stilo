/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.apache.log4j.Logger;
import eng.storefunction.StoreProcedureException;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class DBHelperSavePoint {
	
	public static void SetSavepoint(Connection conn, String SavepointName) throws StoreProcedureException
	{
		
		if (conn!=null && SavepointName !=null){
			Logger aLogger = Logger.getLogger(DBHelperSavePoint.class.getName());
			//Set savepoint
			CallableStatement stmt = null;
	        try{
				String sql = "{call DMPK_UTILITY.SetSavepoint(?)}";
				stmt = null;
				stmt = (CallableStatement)conn.prepareCall(sql);
		        //Set parametro
				stmt.setString(1, SavepointName);
		        stmt.execute();
				aLogger.debug("SetSavepoint " + SavepointName);
	        }catch(SQLException se){
	        	throw new StoreProcedureException(se.getErrorCode(), se.getMessage());
	        }finally{
	        	if (stmt != null) try { stmt.close(); }	catch (Exception x){}
	        }	
		}
	}
	
	public static void RollbackToSavepoint(Connection conn, String SavepointName) throws StoreProcedureException
	{
		if (conn!=null && SavepointName !=null){
			Logger aLogger = Logger.getLogger(DBHelperSavePoint.class.getName());
			//Set savepoint
			CallableStatement stmt = null;
	        try{
				String sql = "{call DMPK_UTILITY.RollbackToSavepoint(?)}";
				stmt = null;
				stmt = (CallableStatement)conn.prepareCall(sql);
		        //Set parametro
				stmt.setString(1, SavepointName);
		        stmt.execute();
				aLogger.debug("RollbackToSavepoint " + SavepointName);
	        }catch(SQLException se){
	        	throw new StoreProcedureException(se.getErrorCode(), se.getMessage());
	        }finally{
	        	if (stmt != null) try { stmt.close(); }	catch (Exception x){}
	        }	
		}
	}
}
