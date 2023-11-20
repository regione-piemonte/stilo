/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_wf.bean.DmpkWfTrovalistalavoroBean;
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

public class TrovalistalavoroImpl  {
		
	private DmpkWfTrovalistalavoroBean bean;
		  
	public void setBean(DmpkWfTrovalistalavoroBean bean){
		this.bean = bean;
	}
	  
	  
	public void execute(Connection connection) throws SQLException {
	    CallableStatement call = null;			
		try{
			//Creo il Callbackstatement
			call = connection.prepareCall("{? = call DMPK_WF.TROVALISTALAVORO(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");			
			SubjectBean subject =  SubjectUtil.subject.get();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.addStatement(subject.getUuidtransaction(), call);
			}
			//Registro i parametri di out
			call.registerOutParameter(1, Types.INTEGER);
			call.registerOutParameter(5, Types.VARCHAR);
			call.registerOutParameter(6, Types.VARCHAR);
			call.registerOutParameter(7, Types.DECIMAL);
			call.registerOutParameter(8, Types.VARCHAR);
			call.registerOutParameter(9, Types.VARCHAR);
			call.registerOutParameter(10, Types.VARCHAR);
			call.registerOutParameter(11, Types.VARCHAR);
			call.registerOutParameter(12, Types.VARCHAR);
			call.registerOutParameter(13, Types.VARCHAR);
			call.registerOutParameter(14, Types.VARCHAR);
			call.registerOutParameter(15, Types.VARCHAR);
			call.registerOutParameter(16, Types.VARCHAR);
			call.registerOutParameter(17, Types.INTEGER);
			call.registerOutParameter(18, Types.VARCHAR);
			call.registerOutParameter(19, Types.VARCHAR);
			call.registerOutParameter(20, Types.CLOB);
			call.registerOutParameter(21, Types.CLOB);
			call.registerOutParameter(22, Types.VARCHAR);
			call.registerOutParameter(23, Types.INTEGER);
			call.registerOutParameter(24, Types.INTEGER);
			call.registerOutParameter(25, Types.INTEGER);
			call.registerOutParameter(26, Types.VARCHAR);
			call.registerOutParameter(27, Types.CLOB);
			call.registerOutParameter(28, Types.CLOB);
			call.registerOutParameter(29, Types.INTEGER);
			call.registerOutParameter(30, Types.DECIMAL);
			call.registerOutParameter(31, Types.VARCHAR);
			call.registerOutParameter(32, Types.CLOB);
			call.registerOutParameter(33, Types.CLOB);
			call.registerOutParameter(34, Types.CLOB);
			call.registerOutParameter(35, Types.VARCHAR);
			call.registerOutParameter(36, Types.VARCHAR);
			call.registerOutParameter(38, Types.INTEGER);
			call.registerOutParameter(39, Types.INTEGER);
			call.registerOutParameter(42, Types.INTEGER);
			call.registerOutParameter(43, Types.INTEGER);
			call.registerOutParameter(44, Types.CLOB);
			call.registerOutParameter(45, Types.VARCHAR);
			call.registerOutParameter(46, Types.INTEGER);
			call.registerOutParameter(47, Types.VARCHAR);
			
			HibernateStoreUtil util = new HibernateStoreUtil();
			
			BeanWrapperImpl wrapperBean = BeanPropertyUtility.getBeanWrapper(bean);
			
			//Setto i valori della store procedure
			util.settingParameterOnStore(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codidconnectiontokenin",2,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"iduserlavoroin",3,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgpreimpostafiltroin",4,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codapplicazioneio",5,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codistanzaapplio",6,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"idprocesstypeio",7,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"nomeprocesstypeio",8,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"citypeflussowfio",9,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"ciprovprocessio",10,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"oggettoio",11,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"dtavviodaio",12,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"dtavvioaio",13,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"dtdecorrenzadaio",14,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"dtdecorrenzaaio",15,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgstatoprocio",16,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgavviouserlavio",17,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"wfnamefasecorrio",18,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"wfnameattesegio",19,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"criteriitersvoltoio",20,Types.CLOB,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"criteriiternonsvoltoio",21,Types.CLOB,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"tpscadio",22,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"scadentronggio",23,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"scaddaminnggio",24,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgnoscadconevtfinio",25,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"noteprocio",26,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"attributiprocio",27,Types.CLOB,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"soggettiintio",28,Types.CLOB,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgassuserlavio",29,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"iduserassio",30,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"desuserassio",31,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"soggettiestio",32,Types.CLOB,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"udprocio",33,Types.CLOB,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"folderprocio",34,Types.CLOB,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"colorderbyio",35,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgdescorderbyio",36,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgsenzapaginazionein",37,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"nropaginaio",38,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"bachsizeio",39,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"overflowlimitin",40,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgsenzatotin",41,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"criteriavanzatiin",48,Types.CLOB,connection); 	
			
			call.execute();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.removeStatement(subject.getUuidtransaction());
			}
			//Recupero i valori della chiamata
			util.settinParameterOnBean(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codapplicazioneio",5,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codistanzaapplio",6,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idprocesstypeio",7,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nomeprocesstypeio",8,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"citypeflussowfio",9,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"ciprovprocessio",10,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"oggettoio",11,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"dtavviodaio",12,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"dtavvioaio",13,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"dtdecorrenzadaio",14,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"dtdecorrenzaaio",15,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgstatoprocio",16,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgavviouserlavio",17,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"wfnamefasecorrio",18,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"wfnameattesegio",19,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"criteriitersvoltoio",20,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"criteriiternonsvoltoio",21,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"tpscadio",22,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"scadentronggio",23,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"scaddaminnggio",24,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgnoscadconevtfinio",25,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"noteprocio",26,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"attributiprocio",27,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"soggettiintio",28,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgassuserlavio",29,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"iduserassio",30,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"desuserassio",31,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"soggettiestio",32,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"udprocio",33,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"folderprocio",34,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"colorderbyio",35,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgdescorderbyio",36,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nropaginaio",38,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"bachsizeio",39,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nrototrecout",42,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nrorecinpaginaout",43,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"listaxmlout",44,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcontextout",45,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcodeout",46,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errmsgout",47,Types.VARCHAR); 
						
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