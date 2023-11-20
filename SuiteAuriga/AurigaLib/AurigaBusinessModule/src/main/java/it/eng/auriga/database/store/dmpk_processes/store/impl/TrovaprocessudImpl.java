/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_processes.bean.DmpkProcessesTrovaprocessudBean;
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

public class TrovaprocessudImpl  {
		
	private DmpkProcessesTrovaprocessudBean bean;
		  
	public void setBean(DmpkProcessesTrovaprocessudBean bean){
		this.bean = bean;
	}
	  
	  
	public void execute(Connection connection) throws SQLException {
	    CallableStatement call = null;			
		try{
			//Creo il Callbackstatement
			call = connection.prepareCall("{? = call DMPK_PROCESSES.TROVAPROCESSUD(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");			
			SubjectBean subject =  SubjectUtil.subject.get();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.addStatement(subject.getUuidtransaction(), call);
			}
			//Registro i parametri di out
			call.registerOutParameter(1, Types.INTEGER);
			call.registerOutParameter(7, Types.VARCHAR);
			call.registerOutParameter(9, Types.VARCHAR);
			call.registerOutParameter(10, Types.VARCHAR);
			call.registerOutParameter(11, Types.VARCHAR);
			call.registerOutParameter(12, Types.VARCHAR);
			call.registerOutParameter(13, Types.VARCHAR);
			call.registerOutParameter(14, Types.VARCHAR);
			call.registerOutParameter(15, Types.INTEGER);
			call.registerOutParameter(16, Types.INTEGER);
			call.registerOutParameter(17, Types.VARCHAR);
			call.registerOutParameter(18, Types.DECIMAL);
			call.registerOutParameter(19, Types.DECIMAL);
			call.registerOutParameter(20, Types.VARCHAR);
			call.registerOutParameter(21, Types.VARCHAR);
			call.registerOutParameter(23, Types.INTEGER);
			call.registerOutParameter(24, Types.INTEGER);
			call.registerOutParameter(27, Types.INTEGER);
			call.registerOutParameter(28, Types.INTEGER);
			call.registerOutParameter(29, Types.CLOB);
			call.registerOutParameter(30, Types.INTEGER);
			call.registerOutParameter(31, Types.INTEGER);
			call.registerOutParameter(32, Types.INTEGER);
			call.registerOutParameter(33, Types.VARCHAR);
			call.registerOutParameter(34, Types.INTEGER);
			call.registerOutParameter(35, Types.VARCHAR);
			
			HibernateStoreUtil util = new HibernateStoreUtil();
			
			BeanWrapperImpl wrapperBean = BeanPropertyUtility.getBeanWrapper(bean);
			
			//Setto i valori della store procedure
			util.settingParameterOnStore(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codidconnectiontokenin",2,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"iduserlavoroin",3,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"idprocessin",4,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgtpopersuudin",5,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"activitynamein",6,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgfmtestremiregio",7,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgpreimpostafiltroin",8,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"condsutipodocio",9,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgacqprodio",10,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codruoloinprocio",11,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"desruoloinprocio",12,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codstatoinprocio",13,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"desstatoinprocio",14,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"annoregio",15,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"nroregio",16,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"oggettodocio",17,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"idudio",18,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"iddocio",19,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"colorderbyio",20,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgdescorderbyio",21,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgsenzapaginazionein",22,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"nropaginaio",23,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"bachsizeio",24,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"overflowlimitin",25,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgsenzatotin",26,Types.INTEGER,connection); 	
			
			call.execute();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.removeStatement(subject.getUuidtransaction());
			}
			//Recupero i valori della chiamata
			util.settinParameterOnBean(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgfmtestremiregio",7,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"condsutipodocio",9,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgacqprodio",10,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codruoloinprocio",11,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"desruoloinprocio",12,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codstatoinprocio",13,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"desstatoinprocio",14,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"annoregio",15,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nroregio",16,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"oggettodocio",17,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idudio",18,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"iddocio",19,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"colorderbyio",20,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgdescorderbyio",21,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nropaginaio",23,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"bachsizeio",24,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nrototrecout",27,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nrorecinpaginaout",28,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"listaxmlout",29,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgabilinsout",30,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgabilupdout",31,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgabildelout",32,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcontextout",33,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcodeout",34,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errmsgout",35,Types.VARCHAR); 
						
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