/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_amm_trasp.bean.DmpkAmmTraspGetdaticonttabellaBean;
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

public class GetdaticonttabellaImpl  {
		
	private DmpkAmmTraspGetdaticonttabellaBean bean;
		  
	public void setBean(DmpkAmmTraspGetdaticonttabellaBean bean){
		this.bean = bean;
	}
	  
	  
	public void execute(Connection connection) throws SQLException {
	    CallableStatement call = null;			
		try{
			//Creo il Callbackstatement
			call = connection.prepareCall("{? = call DMPK_AMM_TRASP.GETDATICONTTABELLA(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");			
			SubjectBean subject =  SubjectUtil.subject.get();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.addStatement(subject.getUuidtransaction(), call);
			}
			//Registro i parametri di out
			call.registerOutParameter(1, Types.INTEGER);
			call.registerOutParameter(4, Types.VARCHAR);
			call.registerOutParameter(5, Types.INTEGER);
			call.registerOutParameter(6, Types.CLOB);
			call.registerOutParameter(7, Types.CLOB);
			call.registerOutParameter(8, Types.INTEGER);
			call.registerOutParameter(9, Types.CLOB);
			call.registerOutParameter(11, Types.INTEGER);
			call.registerOutParameter(12, Types.CLOB);
			call.registerOutParameter(13, Types.INTEGER);
			call.registerOutParameter(14, Types.INTEGER);
			call.registerOutParameter(15, Types.INTEGER);
			call.registerOutParameter(16, Types.CLOB);
			call.registerOutParameter(17, Types.VARCHAR);
			call.registerOutParameter(18, Types.INTEGER);
			call.registerOutParameter(19, Types.VARCHAR);
			
			HibernateStoreUtil util = new HibernateStoreUtil();
			
			BeanWrapperImpl wrapperBean = BeanPropertyUtility.getBeanWrapper(bean);
			
			//Setto i valori della store procedure
			util.settingParameterOnStore(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codidconnectiontokenin",2,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"idcontenutosezin",3,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"nrorecxpaginain",10,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"nropaginaio",11,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgrecordfuoripubblin",20,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"filtriin",21,Types.CLOB,connection); 	
			
			call.execute();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.removeStatement(subject.getUuidtransaction());
			}
			//Recupero i valori della chiamata
			util.settinParameterOnBean(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"titoloout",4,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgpresentiinfodettout",5,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"infodettindettaglioout",6,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"infodettinsezout",7,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flginfodettugualiout",8,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"datiaggout",9,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nropaginaio",11,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"infostrutturatabout",12,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nrorectotaliout",13,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nropaginetotout",14,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nrorecinpaginaout",15,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"valorirectabout",16,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcontextout",17,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcodeout",18,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errmsgout",19,Types.VARCHAR); 
						
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