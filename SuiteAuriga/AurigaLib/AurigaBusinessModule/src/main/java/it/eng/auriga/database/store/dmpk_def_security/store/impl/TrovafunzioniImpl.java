/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecurityTrovafunzioniBean;
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

public class TrovafunzioniImpl  {
		
	private DmpkDefSecurityTrovafunzioniBean bean;
		  
	public void setBean(DmpkDefSecurityTrovafunzioniBean bean){
		this.bean = bean;
	}
	  
	  
	public void execute(Connection connection) throws SQLException {
	    CallableStatement call = null;			
		try{
			//Creo il Callbackstatement
			call = connection.prepareCall("{? = call DMPK_DEF_SECURITY.TROVAFUNZIONI(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");			
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
			call.registerOutParameter(10, Types.INTEGER);
			call.registerOutParameter(11, Types.INTEGER);
			call.registerOutParameter(12, Types.INTEGER);
			call.registerOutParameter(13, Types.INTEGER);
			call.registerOutParameter(14, Types.VARCHAR);
			call.registerOutParameter(15, Types.VARCHAR);
			call.registerOutParameter(16, Types.DECIMAL);
			call.registerOutParameter(17, Types.VARCHAR);
			call.registerOutParameter(18, Types.VARCHAR);
			call.registerOutParameter(20, Types.INTEGER);
			call.registerOutParameter(21, Types.INTEGER);
			call.registerOutParameter(24, Types.INTEGER);
			call.registerOutParameter(25, Types.INTEGER);
			call.registerOutParameter(26, Types.CLOB);
			call.registerOutParameter(27, Types.VARCHAR);
			call.registerOutParameter(28, Types.INTEGER);
			call.registerOutParameter(29, Types.VARCHAR);
			
			HibernateStoreUtil util = new HibernateStoreUtil();
			
			BeanWrapperImpl wrapperBean = BeanPropertyUtility.getBeanWrapper(bean);
			
			//Setto i valori della store procedure
			util.settingParameterOnStore(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codidconnectiontokenin",2,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"iduserlavoroin",3,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgpreimpostafiltroin",4,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codfunzp1io",5,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codfunzp2io",6,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codfunzp3io",7,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codfunzpnio",8,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"desfunzio",9,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"livellofunzio",10,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgconsottofunzioniio",11,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgsolodispio",12,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgsoloconopzstdio",13,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codapplownerio",14,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codistapplownerio",15,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgrestrapplownerio",16,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"colorderbyio",17,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgdescorderbyio",18,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgsenzapaginazionein",19,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"nropaginaio",20,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"bachsizeio",21,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"overflowlimitin",22,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgsenzatotin",23,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgstatoabilin",30,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgtpdestabilin",31,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"iddestabilin",32,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"opzioniabilin",33,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"rigatagnamein",34,Types.VARCHAR,connection); 	
			
			call.execute();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.removeStatement(subject.getUuidtransaction());
			}
			//Recupero i valori della chiamata
			util.settinParameterOnBean(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codfunzp1io",5,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codfunzp2io",6,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codfunzp3io",7,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codfunzpnio",8,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"desfunzio",9,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"livellofunzio",10,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgconsottofunzioniio",11,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgsolodispio",12,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgsoloconopzstdio",13,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codapplownerio",14,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codistapplownerio",15,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgrestrapplownerio",16,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"colorderbyio",17,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgdescorderbyio",18,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nropaginaio",20,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"bachsizeio",21,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nrototrecout",24,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nrorecinpaginaout",25,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"listaxmlout",26,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcontextout",27,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcodeout",28,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errmsgout",29,Types.VARCHAR); 
						
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