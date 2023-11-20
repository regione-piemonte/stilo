/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_registrazionedoc.bean.DmpkRegistrazionedocStampadestinatarispedBean;
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

public class StampadestinatarispedImpl  {
		
	private DmpkRegistrazionedocStampadestinatarispedBean bean;
		  
	public void setBean(DmpkRegistrazionedocStampadestinatarispedBean bean){
		this.bean = bean;
	}
	  
	  
	public void execute(Connection connection) throws SQLException {
	    CallableStatement call = null;			
		try{
			//Creo il Callbackstatement
			call = connection.prepareCall("{? = call DMPK_REGISTRAZIONEDOC.STAMPADESTINATARISPED(?,?,?,?,?,?,?,?,?,?,?,?)}");			
			SubjectBean subject =  SubjectUtil.subject.get();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.addStatement(subject.getUuidtransaction(), call);
			}
			//Registro i parametri di out
			call.registerOutParameter(1, Types.INTEGER);
			call.registerOutParameter(6, Types.VARCHAR);
			call.registerOutParameter(7, Types.VARCHAR);
			call.registerOutParameter(8, Types.VARCHAR);
			call.registerOutParameter(9, Types.CLOB);
			call.registerOutParameter(10, Types.INTEGER);
			call.registerOutParameter(11, Types.VARCHAR);
			call.registerOutParameter(12, Types.INTEGER);
			call.registerOutParameter(13, Types.VARCHAR);
			
			HibernateStoreUtil util = new HibernateStoreUtil();
			
			BeanWrapperImpl wrapperBean = BeanPropertyUtility.getBeanWrapper(bean);
			
			//Setto i valori della store procedure
			util.settingParameterOnStore(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codidconnectiontokenin",2,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"iduserlavoroin",3,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"listaidudin",4,Types.CLOB,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"listacodmezziin",5,Types.CLOB,connection); 	
			
			call.execute();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.removeStatement(subject.getUuidtransaction());
			}
			//Recupero i valori della chiamata
			util.settinParameterOnBean(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"header1out",6,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"header2out",7,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"header3out",8,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"resultout",9,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nrototrecordout",10,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcontextout",11,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcodeout",12,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errmsgout",13,Types.VARCHAR); 
						
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