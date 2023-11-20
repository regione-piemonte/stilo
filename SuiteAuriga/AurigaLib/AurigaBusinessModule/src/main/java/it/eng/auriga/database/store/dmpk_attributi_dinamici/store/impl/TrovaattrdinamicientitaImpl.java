/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_attributi_dinamici.bean.DmpkAttributiDinamiciTrovaattrdinamicientitaBean;
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

public class TrovaattrdinamicientitaImpl  {
		
	private DmpkAttributiDinamiciTrovaattrdinamicientitaBean bean;
		  
	public void setBean(DmpkAttributiDinamiciTrovaattrdinamicientitaBean bean){
		this.bean = bean;
	}
	  
	  
	public void execute(Connection connection) throws SQLException {
	    CallableStatement call = null;			
		try{
			//Creo il Callbackstatement
			call = connection.prepareCall("{? = call DMPK_ATTRIBUTI_DINAMICI.TROVAATTRDINAMICIENTITA(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");			
			SubjectBean subject =  SubjectUtil.subject.get();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.addStatement(subject.getUuidtransaction(), call);
			}
			//Registro i parametri di out
			call.registerOutParameter(1, Types.INTEGER);
			call.registerOutParameter(5, Types.VARCHAR);
			call.registerOutParameter(6, Types.DECIMAL);
			call.registerOutParameter(7, Types.VARCHAR);
			call.registerOutParameter(8, Types.VARCHAR);
			call.registerOutParameter(9, Types.VARCHAR);
			call.registerOutParameter(11, Types.INTEGER);
			call.registerOutParameter(12, Types.INTEGER);
			call.registerOutParameter(15, Types.INTEGER);
			call.registerOutParameter(16, Types.INTEGER);
			call.registerOutParameter(17, Types.CLOB);
			call.registerOutParameter(18, Types.INTEGER);
			call.registerOutParameter(19, Types.INTEGER);
			call.registerOutParameter(20, Types.INTEGER);
			call.registerOutParameter(21, Types.VARCHAR);
			call.registerOutParameter(22, Types.INTEGER);
			call.registerOutParameter(23, Types.VARCHAR);
			
			HibernateStoreUtil util = new HibernateStoreUtil();
			
			BeanWrapperImpl wrapperBean = BeanPropertyUtility.getBeanWrapper(bean);
			
			//Setto i valori della store procedure
			util.settingParameterOnStore(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codidconnectiontokenin",2,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"iduserlavoroin",3,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgpreimpostafiltroin",4,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"nometabellaconentitaio",5,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgsolovldio",6,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"tsvldio",7,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"colorderbyio",8,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgdescorderbyio",9,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgsenzapaginazionein",10,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"nropaginaio",11,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"bachsizeio",12,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"overflowlimitin",13,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgsenzatotin",14,Types.INTEGER,connection); 	
			
			call.execute();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.removeStatement(subject.getUuidtransaction());
			}
			//Recupero i valori della chiamata
			util.settinParameterOnBean(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nometabellaconentitaio",5,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgsolovldio",6,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"tsvldio",7,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"colorderbyio",8,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgdescorderbyio",9,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nropaginaio",11,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"bachsizeio",12,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nrototrecout",15,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nrorecinpaginaout",16,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"listaxmlout",17,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgabilinsout",18,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgabilupdout",19,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgabildelout",20,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcontextout",21,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcodeout",22,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errmsgout",23,Types.VARCHAR); 
						
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