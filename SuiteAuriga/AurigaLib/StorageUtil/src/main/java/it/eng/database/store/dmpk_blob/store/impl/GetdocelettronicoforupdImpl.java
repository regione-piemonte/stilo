/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import it.eng.database.store.dmpk_blob.bean.DmpkBlobGetdocelettronicoforupdBean;
import it.eng.storeutil.HibernateStoreUtil;

/**
 * @author Procedure Wrapper 1.0
 * Classe generata per la chiamata alla store procedure 
 */

public class GetdocelettronicoforupdImpl  {
		
	private DmpkBlobGetdocelettronicoforupdBean bean;
		  
	public void setBean(DmpkBlobGetdocelettronicoforupdBean bean){
		this.bean = bean;
	}
	  
	  
	public void execute(Connection connection) throws SQLException {
	    CallableStatement call = null;			
		try{
			//Creo il Callbackstatement
			call = connection.prepareCall("{? = call DMPK_BLOB.GETDOCELETTRONICOFORUPD(?,?,?,?,?,?,?,?)}");			
			//Registro i parametri di out
			call.registerOutParameter(1, Types.INTEGER);
			call.registerOutParameter(4, Types.BLOB);
			call.registerOutParameter(7, Types.VARCHAR);
			call.registerOutParameter(8, Types.INTEGER);
			call.registerOutParameter(9, Types.VARCHAR);
			
			HibernateStoreUtil util = new HibernateStoreUtil();
			
			//Setto i valori della store procedure
			util.settingParameterOnStore(call,bean,"parametro_1",1,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,"idstoragein",2,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,"iddocelettronicoin",3,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,"flgrollbckfullin",5,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,"flgautocommitin",6,Types.INTEGER,connection); 	
			
			call.execute();
			
			//Recupero i valori della chiamata
			util.settinParameterOnBean(call,bean,"parametro_1",1,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,"documentoout",4,Types.BLOB); 
			util.settinParameterOnBean(call,bean,"errcontextout",7,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,"errcodeout",8,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,"errmsgout",9,Types.VARCHAR); 
						
		}catch(Exception e){
			throw new SQLException(e);
		}finally{
		 	try{if (call != null) call.close();} catch(Exception e){/**/}
		}
   }
}