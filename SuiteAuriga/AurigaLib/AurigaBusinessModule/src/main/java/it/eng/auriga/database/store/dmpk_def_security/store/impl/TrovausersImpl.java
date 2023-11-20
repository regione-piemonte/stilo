/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecurityTrovausersBean;
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

public class TrovausersImpl  {
		
	private DmpkDefSecurityTrovausersBean bean;
		  
	public void setBean(DmpkDefSecurityTrovausersBean bean){
		this.bean = bean;
	}
	  
	  
	public void execute(Connection connection) throws SQLException {
	    CallableStatement call = null;			
		try{
			//Creo il Callbackstatement
			call = connection.prepareCall("{? = call DMPK_DEF_SECURITY.TROVAUSERS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");			
			SubjectBean subject =  SubjectUtil.subject.get();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.addStatement(subject.getUuidtransaction(), call);
			}
			//Registro i parametri di out
			call.registerOutParameter(1, Types.INTEGER);
			call.registerOutParameter(5, Types.VARCHAR);
			call.registerOutParameter(6, Types.VARCHAR);
			call.registerOutParameter(7, Types.VARCHAR);
			call.registerOutParameter(8, Types.VARCHAR);
			call.registerOutParameter(9, Types.VARCHAR);
			call.registerOutParameter(10, Types.VARCHAR);
			call.registerOutParameter(11, Types.INTEGER);
			call.registerOutParameter(12, Types.VARCHAR);
			call.registerOutParameter(13, Types.VARCHAR);
			call.registerOutParameter(14, Types.VARCHAR);
			call.registerOutParameter(15, Types.VARCHAR);
			call.registerOutParameter(16, Types.DECIMAL);
			call.registerOutParameter(17, Types.VARCHAR);
			call.registerOutParameter(18, Types.DECIMAL);
			call.registerOutParameter(19, Types.VARCHAR);
			call.registerOutParameter(20, Types.VARCHAR);
			call.registerOutParameter(21, Types.VARCHAR);
			call.registerOutParameter(22, Types.INTEGER);
			call.registerOutParameter(23, Types.DECIMAL);
			call.registerOutParameter(24, Types.VARCHAR);
			call.registerOutParameter(25, Types.DECIMAL);
			call.registerOutParameter(26, Types.VARCHAR);
			call.registerOutParameter(27, Types.INTEGER);
			call.registerOutParameter(28, Types.CLOB);
			call.registerOutParameter(29, Types.VARCHAR);
			call.registerOutParameter(30, Types.VARCHAR);
			call.registerOutParameter(32, Types.INTEGER);
			call.registerOutParameter(33, Types.INTEGER);
			call.registerOutParameter(36, Types.INTEGER);
			call.registerOutParameter(37, Types.INTEGER);
			call.registerOutParameter(38, Types.CLOB);
			call.registerOutParameter(39, Types.INTEGER);
			call.registerOutParameter(40, Types.INTEGER);
			call.registerOutParameter(41, Types.INTEGER);
			call.registerOutParameter(42, Types.INTEGER);
			call.registerOutParameter(43, Types.VARCHAR);
			call.registerOutParameter(44, Types.INTEGER);
			call.registerOutParameter(45, Types.VARCHAR);
			
			HibernateStoreUtil util = new HibernateStoreUtil();
			
			BeanWrapperImpl wrapperBean = BeanPropertyUtility.getBeanWrapper(bean);
			
			//Setto i valori della store procedure
			util.settingParameterOnStore(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codidconnectiontokenin",2,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"iduserlavoroin",3,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgpreimpostafiltroin",4,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"iduserio",5,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"usernameio",6,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"descrizioneio",7,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"qualificaio",8,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"matricolaio",9,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"ciprovuserio",10,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgaccreditatiindomio",11,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codapplaccredio",12,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codistapplaccredio",13,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codapplnoaccredio",14,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codistapplnoaccredio",15,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"idruoloammio",16,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"desruoloammio",17,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"iduoconrelvsuserio",18,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"livelliuoconrelvsuserio",19,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"desuoconrelvsuserio",20,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgtiporelconuoio",21,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flginclsottouoio",22,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"idgruppoappio",23,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"nomegruppoappio",24,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgsolovldio",25,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"tsvldio",26,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgconaccessoalsistemaio",27,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"altricriteriio",28,Types.CLOB,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"colorderbyio",29,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgdescorderbyio",30,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgsenzapaginazionein",31,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"nropaginaio",32,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"bachsizeio",33,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"overflowlimitin",34,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgsenzatotin",35,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"emailin",46,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"idprofiloin",47,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"rigatagnamein",48,Types.VARCHAR,connection); 	
			
			call.execute();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.removeStatement(subject.getUuidtransaction());
			}
			//Recupero i valori della chiamata
			util.settinParameterOnBean(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"iduserio",5,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"usernameio",6,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"descrizioneio",7,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"qualificaio",8,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"matricolaio",9,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"ciprovuserio",10,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgaccreditatiindomio",11,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codapplaccredio",12,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codistapplaccredio",13,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codapplnoaccredio",14,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codistapplnoaccredio",15,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idruoloammio",16,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"desruoloammio",17,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"iduoconrelvsuserio",18,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"livelliuoconrelvsuserio",19,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"desuoconrelvsuserio",20,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgtiporelconuoio",21,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flginclsottouoio",22,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idgruppoappio",23,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nomegruppoappio",24,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgsolovldio",25,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"tsvldio",26,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgconaccessoalsistemaio",27,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"altricriteriio",28,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"colorderbyio",29,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgdescorderbyio",30,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nropaginaio",32,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"bachsizeio",33,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nrototrecout",36,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nrorecinpaginaout",37,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"listaxmlout",38,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgabilinsout",39,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgabilupdout",40,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgabildelout",41,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgmostraaltriattrout",42,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcontextout",43,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcodeout",44,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errmsgout",45,Types.VARCHAR); 
						
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