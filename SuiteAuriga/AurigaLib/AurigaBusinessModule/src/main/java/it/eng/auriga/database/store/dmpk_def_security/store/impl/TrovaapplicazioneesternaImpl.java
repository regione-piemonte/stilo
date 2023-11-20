/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecurityTrovaapplicazioneesternaBean;
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

public class TrovaapplicazioneesternaImpl  {
		
	private DmpkDefSecurityTrovaapplicazioneesternaBean bean;
		  
	public void setBean(DmpkDefSecurityTrovaapplicazioneesternaBean bean){
		this.bean = bean;
	}
	  
	  
	public void execute(Connection connection) throws SQLException {
	    CallableStatement call = null;			
		try{
			//Creo il Callbackstatement
			call = connection.prepareCall("{? = call DMPK_DEF_SECURITY.TROVAAPPLICAZIONEESTERNA(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");			
			SubjectBean subject =  SubjectUtil.subject.get();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.addStatement(subject.getUuidtransaction(), call);
			}
			//Registro i parametri di out
			call.registerOutParameter(1, Types.INTEGER);
			call.registerOutParameter(5, Types.VARCHAR);
			call.registerOutParameter(6, Types.VARCHAR);
			call.registerOutParameter(7, Types.VARCHAR);
			call.registerOutParameter(8, Types.DECIMAL);
			call.registerOutParameter(9, Types.INTEGER);
			call.registerOutParameter(10, Types.DECIMAL);
			call.registerOutParameter(11, Types.CLOB);
			call.registerOutParameter(12, Types.VARCHAR);
			call.registerOutParameter(13, Types.VARCHAR);
			call.registerOutParameter(15, Types.INTEGER);
			call.registerOutParameter(16, Types.INTEGER);
			call.registerOutParameter(19, Types.INTEGER);
			call.registerOutParameter(20, Types.INTEGER);
			call.registerOutParameter(21, Types.CLOB);
			call.registerOutParameter(22, Types.INTEGER);
			call.registerOutParameter(23, Types.INTEGER);
			call.registerOutParameter(24, Types.INTEGER);
			call.registerOutParameter(25, Types.VARCHAR);
			call.registerOutParameter(26, Types.INTEGER);
			call.registerOutParameter(27, Types.VARCHAR);
			
			HibernateStoreUtil util = new HibernateStoreUtil();
			
			BeanWrapperImpl wrapperBean = BeanPropertyUtility.getBeanWrapper(bean);
			
			//Setto i valori della store procedure
			util.settingParameterOnStore(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codidconnectiontokenin",2,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"iduserlavoroin",3,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgpreimpostafiltroin",4,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codapplicazioneio",5,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codistanzaapplicazioneio",6,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"descrizioneapplistanzaio",7,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"idspaooio",8,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgusacredenzialiproprieio",9,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flginclannullatiio",10,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"criterisuattrdinio",11,Types.CLOB,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"colorderbyio",12,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgdescorderbyio",13,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgsenzapaginazionein",14,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"nropaginaio",15,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"bachsizeio",16,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"overflowlimitin",17,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgsenzatotin",18,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"rigatagnamein",28,Types.VARCHAR,connection); 	
			
			call.execute();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.removeStatement(subject.getUuidtransaction());
			}
			//Recupero i valori della chiamata
			util.settinParameterOnBean(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codapplicazioneio",5,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codistanzaapplicazioneio",6,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"descrizioneapplistanzaio",7,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idspaooio",8,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgusacredenzialiproprieio",9,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flginclannullatiio",10,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"criterisuattrdinio",11,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"colorderbyio",12,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgdescorderbyio",13,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nropaginaio",15,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"bachsizeio",16,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nrototrecout",19,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nrorecinpaginaout",20,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"listaxmlout",21,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgabilinsout",22,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgabilupdout",23,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgabildelout",24,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcontextout",25,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcodeout",26,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errmsgout",27,Types.VARCHAR); 
						
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