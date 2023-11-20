/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecurityNavigastrutturaorgtreeBean;
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

public class NavigastrutturaorgtreeImpl  {
		
	private DmpkDefSecurityNavigastrutturaorgtreeBean bean;
		  
	public void setBean(DmpkDefSecurityNavigastrutturaorgtreeBean bean){
		this.bean = bean;
	}
	  
	  
	public void execute(Connection connection) throws SQLException {
	    CallableStatement call = null;			
		try{
			//Creo il Callbackstatement
			call = connection.prepareCall("{? = call DMPK_DEF_SECURITY.NAVIGASTRUTTURAORGTREE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");			
			SubjectBean subject =  SubjectUtil.subject.get();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.addStatement(subject.getUuidtransaction(), call);
			}
			//Registro i parametri di out
			call.registerOutParameter(1, Types.INTEGER);
			call.registerOutParameter(8, Types.VARCHAR);
			call.registerOutParameter(9, Types.DECIMAL);
			call.registerOutParameter(12, Types.CLOB);
			call.registerOutParameter(13, Types.CLOB);
			call.registerOutParameter(14, Types.CLOB);
			call.registerOutParameter(15, Types.VARCHAR);
			call.registerOutParameter(16, Types.INTEGER);
			call.registerOutParameter(17, Types.VARCHAR);
			
			HibernateStoreUtil util = new HibernateStoreUtil();
			
			BeanWrapperImpl wrapperBean = BeanPropertyUtility.getBeanWrapper(bean);
			
			//Setto i valori della store procedure
			util.settingParameterOnStore(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codidconnectiontokenin",2,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"iduserlavoroin",3,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgincludiutentiin",4,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"tiporelutenticonuoin",5,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"tsrifin",6,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgsoloattivein",7,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"idrootnodeio",8,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"idorganigrammaio",9,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"idnodetoexplimplin",10,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgexplodenodein",11,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"finalitain",18,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgnodatirootnodein",19,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"tyobjxfinalitain",20,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"idobjxfinalitain",21,Types.VARCHAR,connection); 	
			
			call.execute();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.removeStatement(subject.getUuidtransaction());
			}
			//Recupero i valori della chiamata
			util.settinParameterOnBean(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idrootnodeio",8,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idorganigrammaio",9,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"treexmlout",12,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"percorsorootnodexmlout",13,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"dettaglirootnodeout",14,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcontextout",15,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcodeout",16,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errmsgout",17,Types.VARCHAR); 
						
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