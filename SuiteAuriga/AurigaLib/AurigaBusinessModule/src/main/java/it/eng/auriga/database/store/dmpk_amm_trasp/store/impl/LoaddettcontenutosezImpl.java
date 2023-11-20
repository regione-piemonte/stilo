/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_amm_trasp.bean.DmpkAmmTraspLoaddettcontenutosezBean;
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

public class LoaddettcontenutosezImpl  {
		
	private DmpkAmmTraspLoaddettcontenutosezBean bean;
		  
	public void setBean(DmpkAmmTraspLoaddettcontenutosezBean bean){
		this.bean = bean;
	}
	  
	  
	public void execute(Connection connection) throws SQLException {
	    CallableStatement call = null;			
		try{
			//Creo il Callbackstatement
			call = connection.prepareCall("{? = call DMPK_AMM_TRASP.LOADDETTCONTENUTOSEZ(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");			
			SubjectBean subject =  SubjectUtil.subject.get();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.addStatement(subject.getUuidtransaction(), call);
			}
			//Registro i parametri di out
			call.registerOutParameter(1, Types.INTEGER);
			call.registerOutParameter(5, Types.DECIMAL);
			call.registerOutParameter(6, Types.VARCHAR);
			call.registerOutParameter(7, Types.INTEGER);
			call.registerOutParameter(8, Types.VARCHAR);
			call.registerOutParameter(9, Types.CLOB);
			call.registerOutParameter(10, Types.CLOB);
			call.registerOutParameter(11, Types.INTEGER);
			call.registerOutParameter(12, Types.INTEGER);
			call.registerOutParameter(13, Types.CLOB);
			call.registerOutParameter(14, Types.INTEGER);
			call.registerOutParameter(15, Types.INTEGER);
			call.registerOutParameter(16, Types.INTEGER);
			call.registerOutParameter(17, Types.VARCHAR);
			call.registerOutParameter(18, Types.VARCHAR);
			call.registerOutParameter(19, Types.DECIMAL);
			call.registerOutParameter(20, Types.VARCHAR);
			call.registerOutParameter(21, Types.DECIMAL);
			call.registerOutParameter(22, Types.DECIMAL);
			call.registerOutParameter(23, Types.DECIMAL);
			call.registerOutParameter(24, Types.CLOB);
			call.registerOutParameter(25, Types.CLOB);
			call.registerOutParameter(26, Types.CLOB);
			call.registerOutParameter(27, Types.DECIMAL);
			call.registerOutParameter(28, Types.DECIMAL);
			call.registerOutParameter(29, Types.VARCHAR);
			call.registerOutParameter(30, Types.VARCHAR);
			call.registerOutParameter(31, Types.VARCHAR);
			call.registerOutParameter(32, Types.VARCHAR);
			call.registerOutParameter(33, Types.INTEGER);
			call.registerOutParameter(34, Types.VARCHAR);
			call.registerOutParameter(35, Types.INTEGER);
			call.registerOutParameter(36, Types.VARCHAR);
			
			HibernateStoreUtil util = new HibernateStoreUtil();
			
			BeanWrapperImpl wrapperBean = BeanPropertyUtility.getBeanWrapper(bean);
			
			//Setto i valori della store procedure
			util.settingParameterOnStore(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codidconnectiontokenin",2,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"iduserlavoroin",3,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"idcontenutosezin",4,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"idsezioneio",5,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"tipocontenutoio",6,Types.VARCHAR,connection); 	
			
			call.execute();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.removeStatement(subject.getUuidtransaction());
			}
			//Recupero i valori della chiamata
			util.settinParameterOnBean(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idsezioneio",5,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"tipocontenutoio",6,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nroordineinsezout",7,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"titoloout",8,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"testohtmlsezout",9,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"testohtmlindettaglioout",10,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgtestihtmlugualiout",11,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgmostradatiaggout",12,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"datiaggout",13,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgexportopendataout",14,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgpaginadedicataout",15,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idudout",16,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codcategoriaregout",17,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"siglaregistroregout",18,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"annoregout",19,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"dataregout",20,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nroregout",21,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"iddocprimarioout",22,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nroverfileprimarioout",23,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"datifileprimarioout",24,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"datifileallegatiout",25,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"infostrutturatabout",26,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nrorecpubblintabellaout",27,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nrorecdapubblicareout",28,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"dtpubbldalout",29,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"dtpubblalout",30,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"statorichpubblout",31,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"motivorigettoout",32,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgabilauotorizzrichout",33,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcontextout",34,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcodeout",35,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errmsgout",36,Types.VARCHAR); 
						
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