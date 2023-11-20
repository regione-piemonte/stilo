/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreTrovarepositoryobjBean;
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

public class TrovarepositoryobjImpl  {
		
	private DmpkCoreTrovarepositoryobjBean bean;
		  
	public void setBean(DmpkCoreTrovarepositoryobjBean bean){
		this.bean = bean;
	}
	  
	  
	public void execute(Connection connection) throws SQLException {
	    CallableStatement call = null;			
		try{
			//Creo il Callbackstatement
			call = connection.prepareCall("{? = call DMPK_CORE.TROVAREPOSITORYOBJ(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");			
			SubjectBean subject =  SubjectUtil.subject.get();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.addStatement(subject.getUuidtransaction(), call);
			}
			//Registro i parametri di out
			call.registerOutParameter(1, Types.INTEGER);
			call.registerOutParameter(8, Types.VARCHAR);
			call.registerOutParameter(9, Types.VARCHAR);
			call.registerOutParameter(10, Types.DECIMAL);
			call.registerOutParameter(11, Types.INTEGER);
			call.registerOutParameter(12, Types.CLOB);
			call.registerOutParameter(13, Types.CLOB);
			call.registerOutParameter(14, Types.VARCHAR);
			call.registerOutParameter(15, Types.VARCHAR);
			call.registerOutParameter(17, Types.INTEGER);
			call.registerOutParameter(18, Types.INTEGER);
			call.registerOutParameter(21, Types.INTEGER);
			call.registerOutParameter(22, Types.INTEGER);
			call.registerOutParameter(25, Types.CLOB);
			call.registerOutParameter(26, Types.CLOB);
			call.registerOutParameter(27, Types.CLOB);
			call.registerOutParameter(28, Types.VARCHAR);
			call.registerOutParameter(29, Types.INTEGER);
			call.registerOutParameter(30, Types.VARCHAR);
			
			HibernateStoreUtil util = new HibernateStoreUtil();
			
			BeanWrapperImpl wrapperBean = BeanPropertyUtility.getBeanWrapper(bean);
			
			//Setto i valori della store procedure
			util.settingParameterOnStore(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codidconnectiontokenin",2,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"iduserlavoroin",3,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgpreimpostafiltroin",4,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"matchbyindexerin",5,Types.CLOB,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgindexeroverflowin",6,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flg2ndcallin",7,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgfmtestremiregio",8,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgudfolderio",9,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"cercainfolderio",10,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgcercainsubfolderio",11,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"criteriavanzatiio",12,Types.CLOB,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"criteripersonalizzatiio",13,Types.CLOB,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"colorderbyio",14,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgdescorderbyio",15,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgsenzapaginazionein",16,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"nropaginaio",17,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"bachsizeio",18,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"overflowlimitin",19,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgsenzatotin",20,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgbatchsearchin",23,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"coltoreturnin",24,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"percorsoricercaxmlio",26,Types.CLOB,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"finalitain",31,Types.VARCHAR,connection); 	
			
			call.execute();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.removeStatement(subject.getUuidtransaction());
			}
			//Recupero i valori della chiamata
			util.settinParameterOnBean(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgfmtestremiregio",8,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgudfolderio",9,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"cercainfolderio",10,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgcercainsubfolderio",11,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"criteriavanzatiio",12,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"criteripersonalizzatiio",13,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"colorderbyio",14,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgdescorderbyio",15,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nropaginaio",17,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"bachsizeio",18,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nrototrecout",21,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nrorecinpaginaout",22,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"resultout",25,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"percorsoricercaxmlio",26,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"dettaglicercainfolderout",27,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcontextout",28,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcodeout",29,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errmsgout",30,Types.VARCHAR); 
						
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