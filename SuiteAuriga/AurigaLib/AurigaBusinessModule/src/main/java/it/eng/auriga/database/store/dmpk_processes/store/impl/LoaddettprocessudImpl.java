/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_processes.bean.DmpkProcessesLoaddettprocessudBean;
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

public class LoaddettprocessudImpl  {
		
	private DmpkProcessesLoaddettprocessudBean bean;
		  
	public void setBean(DmpkProcessesLoaddettprocessudBean bean){
		this.bean = bean;
	}
	  
	  
	public void execute(Connection connection) throws SQLException {
	    CallableStatement call = null;			
		try{
			//Creo il Callbackstatement
			call = connection.prepareCall("{? = call DMPK_PROCESSES.LOADDETTPROCESSUD(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");			
			SubjectBean subject =  SubjectUtil.subject.get();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.addStatement(subject.getUuidtransaction(), call);
			}
			//Registro i parametri di out
			call.registerOutParameter(1, Types.INTEGER);
			call.registerOutParameter(5, Types.DECIMAL);
			call.registerOutParameter(6, Types.DECIMAL);
			call.registerOutParameter(7, Types.VARCHAR);
			call.registerOutParameter(8, Types.INTEGER);
			call.registerOutParameter(9, Types.INTEGER);
			call.registerOutParameter(10, Types.VARCHAR);
			call.registerOutParameter(11, Types.INTEGER);
			call.registerOutParameter(12, Types.DECIMAL);
			call.registerOutParameter(13, Types.VARCHAR);
			call.registerOutParameter(14, Types.INTEGER);
			call.registerOutParameter(15, Types.VARCHAR);
			call.registerOutParameter(16, Types.VARCHAR);
			call.registerOutParameter(17, Types.VARCHAR);
			call.registerOutParameter(18, Types.VARCHAR);
			call.registerOutParameter(19, Types.VARCHAR);
			call.registerOutParameter(20, Types.VARCHAR);
			call.registerOutParameter(21, Types.CLOB);
			call.registerOutParameter(22, Types.INTEGER);
			call.registerOutParameter(23, Types.VARCHAR);
			call.registerOutParameter(24, Types.VARCHAR);
			call.registerOutParameter(25, Types.INTEGER);
			call.registerOutParameter(26, Types.VARCHAR);
			
			HibernateStoreUtil util = new HibernateStoreUtil();
			
			BeanWrapperImpl wrapperBean = BeanPropertyUtility.getBeanWrapper(bean);
			
			//Setto i valori della store procedure
			util.settingParameterOnStore(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codidconnectiontokenin",2,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"iduserlavoroin",3,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"idprocessin",4,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"idudio",5,Types.DECIMAL,connection); 	
			
			call.execute();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.removeStatement(subject.getUuidtransaction());
			}
			//Recupero i valori della chiamata
			util.settinParameterOnBean(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idudio",5,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"iddocprimarioout",6,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"siglaprotout",7,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"annoprotout",8,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nroprotout",9,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"dtprotout",10,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgprotrichiestaout",11,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idtipodocprimarioout",12,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nometipodocprimarioout",13,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nrolastverprimarioout",14,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"displayfilenameprimarioout",15,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"oggettoout",16,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"mittdestout",17,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgmittdestout",18,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"estremiprocessout",19,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"rowidout",20,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"attributiaddout",21,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgmostraaltriattrout",22,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgtipoprovout",23,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcontextout",24,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcodeout",25,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errmsgout",26,Types.VARCHAR); 
						
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