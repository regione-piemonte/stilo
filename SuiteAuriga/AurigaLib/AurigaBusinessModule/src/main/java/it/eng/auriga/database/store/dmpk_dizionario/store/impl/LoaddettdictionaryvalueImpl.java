/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_dizionario.bean.DmpkDizionarioLoaddettdictionaryvalueBean;
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

public class LoaddettdictionaryvalueImpl  {
		
	private DmpkDizionarioLoaddettdictionaryvalueBean bean;
		  
	public void setBean(DmpkDizionarioLoaddettdictionaryvalueBean bean){
		this.bean = bean;
	}
	  
	  
	public void execute(Connection connection) throws SQLException {
	    CallableStatement call = null;			
		try{
			//Creo il Callbackstatement
			call = connection.prepareCall("{? = call DMPK_DIZIONARIO.LOADDETTDICTIONARYVALUE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");			
			SubjectBean subject =  SubjectUtil.subject.get();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.addStatement(subject.getUuidtransaction(), call);
			}
			//Registro i parametri di out
			call.registerOutParameter(1, Types.INTEGER);
			call.registerOutParameter(4, Types.VARCHAR);
			call.registerOutParameter(5, Types.CLOB);
			call.registerOutParameter(6, Types.VARCHAR);
			call.registerOutParameter(7, Types.INTEGER);
			call.registerOutParameter(8, Types.VARCHAR);
			call.registerOutParameter(9, Types.VARCHAR);
			call.registerOutParameter(10, Types.VARCHAR);
			call.registerOutParameter(11, Types.VARCHAR);
			call.registerOutParameter(12, Types.VARCHAR);
			call.registerOutParameter(13, Types.INTEGER);
			call.registerOutParameter(14, Types.INTEGER);
			call.registerOutParameter(15, Types.INTEGER);
			call.registerOutParameter(16, Types.VARCHAR);
			call.registerOutParameter(17, Types.INTEGER);
			call.registerOutParameter(18, Types.VARCHAR);
			call.registerOutParameter(19, Types.DECIMAL);
			call.registerOutParameter(20, Types.VARCHAR);
			call.registerOutParameter(21, Types.VARCHAR);
			call.registerOutParameter(22, Types.INTEGER);
			call.registerOutParameter(23, Types.INTEGER);
			call.registerOutParameter(24, Types.INTEGER);
			call.registerOutParameter(25, Types.INTEGER);
			
			HibernateStoreUtil util = new HibernateStoreUtil();
			
			BeanWrapperImpl wrapperBean = BeanPropertyUtility.getBeanWrapper(bean);
			
			//Setto i valori della store procedure
			util.settingParameterOnStore(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codidconnectiontokenin",2,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"iduserlavoroin",3,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"dictionaryentryio",4,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"valueio",5,Types.CLOB,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codvalueio",6,Types.VARCHAR,connection); 	
			
			call.execute();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.removeStatement(subject.getUuidtransaction());
			}
			//Recupero i valori della chiamata
			util.settinParameterOnBean(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"dictionaryentryio",4,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"valueio",5,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codvalueio",6,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgcodobbligatorioout",7,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"meaningout",8,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"valuegenvincoloout",9,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"dictentryvincoloout",10,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"dtiniziovldout",11,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"dtfinevldout",12,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flglockedout",13,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgvaluereferencedout",14,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgcodvalreferencedout",15,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcontextout",16,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcodeout",17,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errmsgout",18,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"iduoout",19,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"livelliuoout",20,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"desuoout",21,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgvisibsottouoout",22,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flggestsottouoout",23,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgabilmodificaout",24,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgabileliminazioneout",25,Types.INTEGER); 
						
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