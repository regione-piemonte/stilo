/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_collaboration.bean.DmpkCollaborationInvioBean;
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

public class InvioImpl  {
		
	private DmpkCollaborationInvioBean bean;
		  
	public void setBean(DmpkCollaborationInvioBean bean){
		this.bean = bean;
	}
	  
	  
	public void execute(Connection connection) throws SQLException {
	    CallableStatement call = null;			
		try{
			//Creo il Callbackstatement
			call = connection.prepareCall("{? = call DMPK_COLLABORATION.INVIO(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");			
			SubjectBean subject =  SubjectUtil.subject.get();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.addStatement(subject.getUuidtransaction(), call);
			}
			//Registro i parametri di out
			call.registerOutParameter(1, Types.INTEGER);
			call.registerOutParameter(18, Types.VARCHAR);
			call.registerOutParameter(19, Types.VARCHAR);
			call.registerOutParameter(20, Types.VARCHAR);
			call.registerOutParameter(22, Types.VARCHAR);
			call.registerOutParameter(23, Types.VARCHAR);
			call.registerOutParameter(25, Types.CLOB);
			call.registerOutParameter(26, Types.CLOB);
			call.registerOutParameter(30, Types.VARCHAR);
			call.registerOutParameter(31, Types.VARCHAR);
			call.registerOutParameter(32, Types.INTEGER);
			call.registerOutParameter(33, Types.VARCHAR);
			
			HibernateStoreUtil util = new HibernateStoreUtil();
			
			BeanWrapperImpl wrapperBean = BeanPropertyUtility.getBeanWrapper(bean);
			
			//Setto i valori della store procedure
			util.settingParameterOnStore(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codidconnectiontokenin",2,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"iduserlavoroin",3,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgtypeobjtosendin",4,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"idobjtosendin",5,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgsendcontenutiin",6,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"recipientsxmlin",7,Types.CLOB,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"sendertypein",8,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"senderidin",9,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codmotivoinvioin",10,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"motivoinvioin",11,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"messaggioinvioin",12,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"livelloprioritain",13,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"richconfermapresavisin",14,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"richconfermaaccettazin",15,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"tsdecorrenzaassegnazin",16,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgnotificaemailinvioin",17,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"indxnotifemailinvioio",18,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgnotificasmsinvioin",21,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"nricellxnotifsmsinvioio",22,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"altrenotificherichxmlin",24,Types.CLOB,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgignorewarningin",27,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgrollbckfullin",28,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgautocommitin",29,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgcallbyguiin",34,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgmantienicopiaudin",35,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgforceinvioin",36,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgskipctrlcartaceoin",37,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flginviofascicoloin",38,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flginvioudcollegatein",39,Types.INTEGER,connection); 	
			
			call.execute();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.removeStatement(subject.getUuidtransaction());
			}
			//Recupero i valori della chiamata
			util.settinParameterOnBean(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"indxnotifemailinvioio",18,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"oggemailout",19,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"bodyemailout",20,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nricellxnotifsmsinvioio",22,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"testosmsout",23,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"copieudinviatexmlout",25,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"urixmlout",26,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"warningmsgout",30,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcontextout",31,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcodeout",32,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errmsgout",33,Types.VARCHAR); 
						
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