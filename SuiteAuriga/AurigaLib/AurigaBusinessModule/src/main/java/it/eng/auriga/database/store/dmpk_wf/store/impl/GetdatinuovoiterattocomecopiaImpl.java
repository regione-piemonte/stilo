/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_wf.bean.DmpkWfGetdatinuovoiterattocomecopiaBean;
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

public class GetdatinuovoiterattocomecopiaImpl  {
		
	private DmpkWfGetdatinuovoiterattocomecopiaBean bean;
		  
	public void setBean(DmpkWfGetdatinuovoiterattocomecopiaBean bean){
		this.bean = bean;
	}
	  
	  
	public void execute(Connection connection) throws SQLException {
	    CallableStatement call = null;			
		try{
			//Creo il Callbackstatement
			call = connection.prepareCall("{? = call DMPK_WF.GETDATINUOVOITERATTOCOMECOPIA(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");			
			SubjectBean subject =  SubjectUtil.subject.get();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.addStatement(subject.getUuidtransaction(), call);
			}
			//Registro i parametri di out
			call.registerOutParameter(1, Types.INTEGER);
			call.registerOutParameter(9, Types.DECIMAL);
			call.registerOutParameter(10, Types.DECIMAL);
			call.registerOutParameter(11, Types.DECIMAL);
			call.registerOutParameter(12, Types.DECIMAL);
			call.registerOutParameter(13, Types.VARCHAR);
			call.registerOutParameter(14, Types.VARCHAR);
			call.registerOutParameter(15, Types.DECIMAL);
			call.registerOutParameter(16, Types.DECIMAL);
			call.registerOutParameter(17, Types.VARCHAR);
			call.registerOutParameter(18, Types.INTEGER);
			call.registerOutParameter(19, Types.VARCHAR);
			
			HibernateStoreUtil util = new HibernateStoreUtil();
			
			BeanWrapperImpl wrapperBean = BeanPropertyUtility.getBeanWrapper(bean);
			
			//Setto i valori della store procedure
			util.settingParameterOnStore(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codidconnectiontokenin",2,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"iduserlavoroin",3,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"idproctypedaavviarein",4,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgtiponumerazionein",5,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"siglanumerazionein",6,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"annonumerazionein",7,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"numeroin",8,Types.DECIMAL,connection); 	
			
			call.execute();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.removeStatement(subject.getUuidtransaction());
			}
			//Recupero i valori della chiamata
			util.settinParameterOnBean(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"iduddacopiareout",9,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idprocessdacopiareout",10,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgdoctypediversoout",11,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"iddoctypedacopiareout",12,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codcatregattodacopiareout",13,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"siglaregattodacopiareout",14,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"annoattodacopiareout",15,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"numattodacopiareout",16,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcontextout",17,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcodeout",18,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errmsgout",19,Types.VARCHAR); 
						
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