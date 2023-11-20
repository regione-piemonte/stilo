/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_ws.bean.DmpkWsNotificadocfolderBean;
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

public class NotificadocfolderImpl  {
		
	private DmpkWsNotificadocfolderBean bean;
		  
	public void setBean(DmpkWsNotificadocfolderBean bean){
		this.bean = bean;
	}
	  
	  
	public void execute(Connection connection) throws SQLException {
	    CallableStatement call = null;			
		try{
			//Creo il Callbackstatement
			call = connection.prepareCall("{? = call DMPK_WS.NOTIFICADOCFOLDER(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");			
			SubjectBean subject =  SubjectUtil.subject.get();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.addStatement(subject.getUuidtransaction(), call);
			}
			//Registro i parametri di out
			call.registerOutParameter(1, Types.INTEGER);
			call.registerOutParameter(4, Types.VARCHAR);
			call.registerOutParameter(5, Types.DECIMAL);
			call.registerOutParameter(6, Types.CLOB);
			call.registerOutParameter(7, Types.VARCHAR);
			call.registerOutParameter(8, Types.DECIMAL);
			call.registerOutParameter(9, Types.VARCHAR);
			call.registerOutParameter(10, Types.VARCHAR);
			call.registerOutParameter(11, Types.VARCHAR);
			call.registerOutParameter(12, Types.DECIMAL);
			call.registerOutParameter(13, Types.INTEGER);
			call.registerOutParameter(14, Types.INTEGER);
			call.registerOutParameter(15, Types.VARCHAR);
			call.registerOutParameter(16, Types.INTEGER);
			call.registerOutParameter(17, Types.INTEGER);
			call.registerOutParameter(18, Types.VARCHAR);
			call.registerOutParameter(19, Types.VARCHAR);
			call.registerOutParameter(20, Types.INTEGER);
			call.registerOutParameter(21, Types.VARCHAR);
			call.registerOutParameter(22, Types.VARCHAR);
			call.registerOutParameter(23, Types.VARCHAR);
			call.registerOutParameter(24, Types.INTEGER);
			call.registerOutParameter(25, Types.VARCHAR);
			call.registerOutParameter(26, Types.VARCHAR);
			call.registerOutParameter(29, Types.VARCHAR);
			call.registerOutParameter(30, Types.INTEGER);
			call.registerOutParameter(31, Types.VARCHAR);
			
			HibernateStoreUtil util = new HibernateStoreUtil();
			
			BeanWrapperImpl wrapperBean = BeanPropertyUtility.getBeanWrapper(bean);
			
			//Setto i valori della store procedure
			util.settingParameterOnStore(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codidconnectiontokenin",2,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"xmlin",3,Types.CLOB,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgrollbckfullin",27,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgautocommitin",28,Types.INTEGER,connection); 	
			
			call.execute();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.removeStatement(subject.getUuidtransaction());
			}
			//Recupero i valori della chiamata
			util.settinParameterOnBean(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgtypeobjtonotifout",4,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idobjtonotifout",5,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"recipientsxmlout",6,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"sendertypeout",7,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"senderidout",8,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codmotivonotifout",9,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"motivonotifout",10,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"messaggionotifout",11,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"livelloprioritaout",12,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"richconfermapresavisout",13,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgemailnotifpresavisout",14,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"indemailnotifpresavisout",15,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"notnopresavisentroggout",16,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgemailnopresavisout",17,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"indemailnopresavisout",18,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"tsdecorrenzanotifout",19,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgnotificaemailnotifout",20,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"indxnotifemailnotifout",21,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"oggemailout",22,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"bodyemailout",23,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgnotificasmsnotifout",24,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nricellxnotifsmsnotifout",25,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"testosmsout",26,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcontextout",29,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcodeout",30,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errmsgout",31,Types.VARCHAR); 
						
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