/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import it.eng.database.store.dmpk_blob.bean.DmpkBlobFinddocelettronicoBean;
import it.eng.storeutil.HibernateStoreUtil;

/**
 * @author Procedure Wrapper 1.0
 * Classe generata per la chiamata alla store procedure 
 */

public class FinddocelettronicoImpl  {
		
	private DmpkBlobFinddocelettronicoBean bean;
		  
	public void setBean(DmpkBlobFinddocelettronicoBean bean){
		this.bean = bean;
	}
	  
	  
	public void execute(Connection connection) throws SQLException {
	    CallableStatement call = null;			
		try{
			//Creo il Callbackstatement
			call = connection.prepareCall("{? = call DMPK_BLOB.FINDDOCELETTRONICO(?,?,?,?,?)}");			
			//Registro i parametri di out
			call.registerOutParameter(1, Types.INTEGER);
			call.registerOutParameter(3, Types.VARCHAR);
			call.registerOutParameter(4, Types.VARCHAR);
			call.registerOutParameter(5, Types.INTEGER);
			call.registerOutParameter(6, Types.VARCHAR);
			
			HibernateStoreUtil util = new HibernateStoreUtil();
			
			//Setto i valori della store procedure
			util.settingParameterOnStore(call,bean,"parametro_1",1,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,"idstoragein",2,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,"iddocelettronicoio",3,Types.VARCHAR,connection); 	
			
			call.execute();
			
			//Recupero i valori della chiamata
			util.settinParameterOnBean(call,bean,"parametro_1",1,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,"iddocelettronicoio",3,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,"errcontextout",4,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,"errcodeout",5,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,"errmsgout",6,Types.VARCHAR); 
						
		}catch(Exception e){
			throw new SQLException(e);
		}finally{
		 	try{if (call != null) call.close();} catch(Exception e){/**/}
		}
   }
}