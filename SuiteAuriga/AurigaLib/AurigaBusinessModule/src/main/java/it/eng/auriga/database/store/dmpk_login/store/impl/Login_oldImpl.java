/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_login.bean.DmpkLoginLogin_oldBean;
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

public class Login_oldImpl  {
		
	private DmpkLoginLogin_oldBean bean;
		  
	public void setBean(DmpkLoginLogin_oldBean bean){
		this.bean = bean;
	}
	  
	  
	public void execute(Connection connection) throws SQLException {
	    CallableStatement call = null;			
		try{
			//Creo il Callbackstatement
			call = connection.prepareCall("{? = call DMPK_LOGIN.LOGIN_OLD(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");			
			SubjectBean subject =  SubjectUtil.subject.get();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.addStatement(subject.getUuidtransaction(), call);
			}
			//Registro i parametri di out
			call.registerOutParameter(1, Types.INTEGER);
			call.registerOutParameter(4, Types.INTEGER);
			call.registerOutParameter(5, Types.DECIMAL);
			call.registerOutParameter(8, Types.VARCHAR);
			call.registerOutParameter(9, Types.VARCHAR);
			call.registerOutParameter(10, Types.VARCHAR);
			call.registerOutParameter(11, Types.CLOB);
			call.registerOutParameter(12, Types.CLOB);
			call.registerOutParameter(13, Types.CLOB);
			call.registerOutParameter(16, Types.VARCHAR);
			call.registerOutParameter(17, Types.INTEGER);
			call.registerOutParameter(18, Types.VARCHAR);
			
			HibernateStoreUtil util = new HibernateStoreUtil();
			
			BeanWrapperImpl wrapperBean = BeanPropertyUtility.getBeanWrapper(bean);
			
			//Setto i valori della store procedure
			util.settingParameterOnStore(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"usernamein",2,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"passwordin",3,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgtpdominioautio",4,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"iddominioautio",5,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codapplicazioneestin",6,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codistanzaapplestin",7,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgrollbckfullin",14,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgautocommitin",15,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgnoctrlpasswordin",19,Types.INTEGER,connection); 	
			
			call.execute();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.removeStatement(subject.getUuidtransaction());
			}
			//Recupero i valori della chiamata
			util.settinParameterOnBean(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgtpdominioautio",4,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"iddominioautio",5,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codidconnectiontokenout",8,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"desuserout",9,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"desdominioout",10,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"parametriconfigout",11,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"menuout",12,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"listexmlout",13,Types.CLOB); 
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