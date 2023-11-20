/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_process_types.bean.DmpkProcessTypesTrovaprocesstypesBean;
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

public class TrovaprocesstypesImpl  {
		
	private DmpkProcessTypesTrovaprocesstypesBean bean;
		  
	public void setBean(DmpkProcessTypesTrovaprocesstypesBean bean){
		this.bean = bean;
	}
	  
	  
	public void execute(Connection connection) throws SQLException {
	    CallableStatement call = null;			
		try{
			//Creo il Callbackstatement
			call = connection.prepareCall("{? = call DMPK_PROCESS_TYPES.TROVAPROCESSTYPES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");			
			SubjectBean subject =  SubjectUtil.subject.get();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.addStatement(subject.getUuidtransaction(), call);
			}
			//Registro i parametri di out
			call.registerOutParameter(1, Types.INTEGER);
			call.registerOutParameter(5, Types.DECIMAL);
			call.registerOutParameter(6, Types.VARCHAR);
			call.registerOutParameter(7, Types.VARCHAR);
			call.registerOutParameter(8, Types.INTEGER);
			call.registerOutParameter(9, Types.DECIMAL);
			call.registerOutParameter(10, Types.VARCHAR);
			call.registerOutParameter(11, Types.VARCHAR);
			call.registerOutParameter(12, Types.VARCHAR);
			call.registerOutParameter(13, Types.DECIMAL);
			call.registerOutParameter(14, Types.VARCHAR);
			call.registerOutParameter(15, Types.VARCHAR);
			call.registerOutParameter(16, Types.DECIMAL);
			call.registerOutParameter(17, Types.VARCHAR);
			call.registerOutParameter(18, Types.DECIMAL);
			call.registerOutParameter(19, Types.VARCHAR);
			call.registerOutParameter(20, Types.VARCHAR);
			call.registerOutParameter(21, Types.VARCHAR);
			call.registerOutParameter(22, Types.DECIMAL);
			call.registerOutParameter(23, Types.DECIMAL);
			call.registerOutParameter(24, Types.DECIMAL);
			call.registerOutParameter(25, Types.CLOB);
			call.registerOutParameter(26, Types.VARCHAR);
			call.registerOutParameter(27, Types.VARCHAR);
			call.registerOutParameter(29, Types.INTEGER);
			call.registerOutParameter(30, Types.INTEGER);
			call.registerOutParameter(33, Types.INTEGER);
			call.registerOutParameter(34, Types.INTEGER);
			call.registerOutParameter(35, Types.CLOB);
			call.registerOutParameter(36, Types.INTEGER);
			call.registerOutParameter(37, Types.INTEGER);
			call.registerOutParameter(38, Types.INTEGER);
			call.registerOutParameter(39, Types.INTEGER);
			call.registerOutParameter(40, Types.VARCHAR);
			call.registerOutParameter(41, Types.INTEGER);
			call.registerOutParameter(42, Types.VARCHAR);
			
			HibernateStoreUtil util = new HibernateStoreUtil();
			
			BeanWrapperImpl wrapperBean = BeanPropertyUtility.getBeanWrapper(bean);
			
			//Setto i valori della store procedure
			util.settingParameterOnStore(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codidconnectiontokenin",2,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"iduserlavoroin",3,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgpreimpostafiltroin",4,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"idprocesstypeio",5,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"nomeio",6,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"descrizioneio",7,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgprocammio",8,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"idprocesstypegenio",9,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"nomeprocesstypegenio",10,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgtipoiniziativaio",11,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"ciprovprocesstypeio",12,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"iduorespio",13,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"livelliuorespio",14,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"desuorespio",15,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"iduserrespio",16,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"desuserrespio",17,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgsolovldio",18,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"tsvldio",19,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codapplicazioneio",20,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codistapplicazioneio",21,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flginclannullatiio",22,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"ggduratadaio",23,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"ggdurataaio",24,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"altricriteriio",25,Types.CLOB,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"colorderbyio",26,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgdescorderbyio",27,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgsenzapaginazionein",28,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"nropaginaio",29,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"bachsizeio",30,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"overflowlimitin",31,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgsenzatotin",32,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgrichabilxvisualizzin",43,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgrichabilxgestin",44,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgrichabilxassegnin",45,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgstatoabilin",46,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgtpdestabilin",47,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"iddestabilin",48,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"opzioniabilin",49,Types.VARCHAR,connection); 	
			
			call.execute();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.removeStatement(subject.getUuidtransaction());
			}
			//Recupero i valori della chiamata
			util.settinParameterOnBean(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idprocesstypeio",5,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nomeio",6,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"descrizioneio",7,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgprocammio",8,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idprocesstypegenio",9,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nomeprocesstypegenio",10,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgtipoiniziativaio",11,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"ciprovprocesstypeio",12,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"iduorespio",13,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"livelliuorespio",14,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"desuorespio",15,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"iduserrespio",16,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"desuserrespio",17,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgsolovldio",18,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"tsvldio",19,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codapplicazioneio",20,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codistapplicazioneio",21,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flginclannullatiio",22,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"ggduratadaio",23,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"ggdurataaio",24,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"altricriteriio",25,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"colorderbyio",26,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgdescorderbyio",27,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nropaginaio",29,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"bachsizeio",30,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nrototrecout",33,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nrorecinpaginaout",34,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"listaxmlout",35,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgabilinsout",36,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgabilupdout",37,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgabildelout",38,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgmostraaltriattrout",39,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcontextout",40,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcodeout",41,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errmsgout",42,Types.VARCHAR); 
						
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