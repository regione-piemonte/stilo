/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_collaboration.bean.DmpkCollaborationNotificaBean;
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

public class NotificaImpl  {
		
	private DmpkCollaborationNotificaBean bean;
		  
	public void setBean(DmpkCollaborationNotificaBean bean){
		this.bean = bean;
	}
	  
	  
	public void execute(Connection connection) throws SQLException {
	    CallableStatement call = null;			
		try{
			//Creo il Callbackstatement
			call = connection.prepareCall("{? = call DMPK_COLLABORATION.NOTIFICA(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");			
			SubjectBean subject =  SubjectUtil.subject.get();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.addStatement(subject.getUuidtransaction(), call);
			}
			//Registro i parametri di out
			call.registerOutParameter(1, Types.INTEGER);
			call.registerOutParameter(21, Types.VARCHAR);
			call.registerOutParameter(22, Types.VARCHAR);
			call.registerOutParameter(23, Types.VARCHAR);
			call.registerOutParameter(25, Types.VARCHAR);
			call.registerOutParameter(26, Types.VARCHAR);
			call.registerOutParameter(27, Types.CLOB);
			call.registerOutParameter(31, Types.VARCHAR);
			call.registerOutParameter(32, Types.VARCHAR);
			call.registerOutParameter(33, Types.INTEGER);
			call.registerOutParameter(34, Types.VARCHAR);
			
			HibernateStoreUtil util = new HibernateStoreUtil();
			
			BeanWrapperImpl wrapperBean = BeanPropertyUtility.getBeanWrapper(bean);
			
			//Setto i valori della store procedure
			util.settingParameterOnStore(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codidconnectiontokenin",2,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"iduserlavoroin",3,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgtypeobjtonotifin",4,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"idobjtonotifin",5,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"recipientsxmlin",6,Types.CLOB,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"sendertypein",7,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"senderidin",8,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codmotivonotifin",9,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"motivonotifin",10,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"messaggionotifin",11,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"livelloprioritain",12,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"richconfermapresavisin",13,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgemailnotifpresavisin",14,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"indemailnotifpresavisin",15,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"notnopresavisentroggin",16,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgemailnopresavisin",17,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"indemailnopresavisin",18,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"tsdecorrenzanotifin",19,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgnotificaemailnotifin",20,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"indxnotifemailnotifio",21,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgnotificasmsnotifin",24,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"nricellxnotifsmsnotifio",25,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgignorewarningin",28,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgrollbckfullin",29,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgautocommitin",30,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgcallbyguiin",35,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flginviofascicoloin",36,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flginvioudcollegatein",37,Types.INTEGER,connection); 	
			
			call.execute();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.removeStatement(subject.getUuidtransaction());
			}
			//Recupero i valori della chiamata
			util.settinParameterOnBean(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"indxnotifemailnotifio",21,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"oggemailout",22,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"bodyemailout",23,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nricellxnotifsmsnotifio",25,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"testosmsout",26,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"urixmlout",27,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"warningmsgout",31,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcontextout",32,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcodeout",33,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errmsgout",34,Types.VARCHAR); 
						
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