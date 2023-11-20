/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_spatial.bean.DmpkSpatialTrovastatinazionaliBean;
import it.eng.storeutil.HibernateStoreUtil;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import oracle.jdbc.OracleTypes;

import it.eng.core.business.HibernateUtil;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;

import org.springframework.beans.BeanWrapperImpl;
import it.eng.utility.springBeanWrapper.BeanPropertyUtility;



import org.apache.commons.lang3.StringUtils;

/**
 * @author Procedure Wrapper 1.0
 * Classe generata per la chiamata alla store procedure 
 */

public class TrovastatinazionaliImpl  {
		
	private DmpkSpatialTrovastatinazionaliBean bean;
		  
	public void setBean(DmpkSpatialTrovastatinazionaliBean bean){
		this.bean = bean;
	}
	  
	  
	public void execute(Connection connection) throws SQLException {
	    CallableStatement call = null;			
		try{
			//Creo il Callbackstatement
			call = connection.prepareCall("{? = call DMPK_SPATIAL.TROVASTATINAZIONALI(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");			
			SubjectBean subject =  SubjectUtil.subject.get();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.addStatement(subject.getUuidtransaction(), call);
			}
			//Registro i parametri di out
			call.registerOutParameter(1, Types.INTEGER);
			call.registerOutParameter(6, Types.VARCHAR);
			call.registerOutParameter(7, Types.VARCHAR);
			call.registerOutParameter(9, Types.INTEGER);
			call.registerOutParameter(10, Types.INTEGER);
			call.registerOutParameter(13, Types.INTEGER);
			call.registerOutParameter(14, Types.INTEGER);
			call.registerOutParameter(15, Types.CLOB);
			call.registerOutParameter(16, Types.VARCHAR);
			call.registerOutParameter(17, Types.INTEGER);
			call.registerOutParameter(18, Types.VARCHAR);
			
			HibernateStoreUtil util = new HibernateStoreUtil();
			
			BeanWrapperImpl wrapperBean = BeanPropertyUtility.getBeanWrapper(bean);
			
			//Setto i valori della store procedure
			util.settingParameterOnStore(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codidconnectiontokenin",2,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"strinnomein",3,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"strincodistatin",4,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"tsvldin",5,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"colorderbyio",6,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgdescorderbyio",7,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgsenzapaginazionein",8,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"nropaginaio",9,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"bachsizeio",10,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"overflowlimitin",11,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgsenzatotin",12,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"strincittadinanzain",19,Types.VARCHAR,connection); 	
			
			call.execute();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.removeStatement(subject.getUuidtransaction());
			}
			//Recupero i valori della chiamata
			util.settinParameterOnBean(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"colorderbyio",6,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgdescorderbyio",7,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nropaginaio",9,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"bachsizeio",10,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nrototrecout",13,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nrorecinpaginaout",14,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"listaxmlout",15,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcontextout",16,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcodeout",17,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errmsgout",18,Types.VARCHAR); 
						
		}catch(Exception e){
			if (e instanceof SQLException){
				SQLException pSqlException = (SQLException)e;
				if (pSqlException.getErrorCode()==1013 && pSqlException.getSQLState().equals("72000")){
					throw new SQLException("Chiusura forzata");
				}
			} throw new SQLException(e);
		}finally{
		 	try{call.close();} catch(Exception e){}
		}
   }
}