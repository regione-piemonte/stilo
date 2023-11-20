/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_sedute_org_coll.bean.DmpkSeduteOrgCollGetdiscussionesedutaBean;
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

public class GetdiscussionesedutaImpl  {
		
	private DmpkSeduteOrgCollGetdiscussionesedutaBean bean;
		  
	public void setBean(DmpkSeduteOrgCollGetdiscussionesedutaBean bean){
		this.bean = bean;
	}
	  
	  
	public void execute(Connection connection) throws SQLException {
	    CallableStatement call = null;			
		try{
			//Creo il Callbackstatement
			call = connection.prepareCall("{? = call DMPK_SEDUTE_ORG_COLL.GETDISCUSSIONESEDUTA(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");			
			SubjectBean subject =  SubjectUtil.subject.get();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.addStatement(subject.getUuidtransaction(), call);
			}
			//Registro i parametri di out
			call.registerOutParameter(1, Types.INTEGER);
			call.registerOutParameter(6, Types.VARCHAR);
			call.registerOutParameter(7, Types.INTEGER);
			call.registerOutParameter(8, Types.VARCHAR);
			call.registerOutParameter(9, Types.VARCHAR);
			call.registerOutParameter(10, Types.VARCHAR);
			call.registerOutParameter(11, Types.VARCHAR);
			call.registerOutParameter(12, Types.VARCHAR);
			call.registerOutParameter(13, Types.VARCHAR);
			call.registerOutParameter(14, Types.CLOB);
			call.registerOutParameter(15, Types.CLOB);
			call.registerOutParameter(16, Types.CLOB);
			call.registerOutParameter(17, Types.CLOB);
			call.registerOutParameter(18, Types.CLOB);
			call.registerOutParameter(19, Types.VARCHAR);
			call.registerOutParameter(20, Types.INTEGER);
			call.registerOutParameter(21, Types.VARCHAR);
			
			HibernateStoreUtil util = new HibernateStoreUtil();
			
			BeanWrapperImpl wrapperBean = BeanPropertyUtility.getBeanWrapper(bean);
			
			//Setto i valori della store procedure
			util.settingParameterOnStore(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codidconnectiontokenin",2,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"iduserlavoroin",3,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"organocollegialein",4,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"listacommissioniin",5,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"idsedutaio",6,Types.VARCHAR,connection); 	
			
			call.execute();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.removeStatement(subject.getUuidtransaction());
			}
			//Recupero i valori della chiamata
			util.settinParameterOnBean(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idsedutaio",6,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nrosedutaout",7,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"dataora1aconvocazioneout",8,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"luogo1aconvocazioneout",9,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"dataora2aconvocazioneout",10,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"luogo2aconvocazioneout",11,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"tiposessioneout",12,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"statosedutaout",13,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"odginfoout",14,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"argomentiodgout",15,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"info1aconvocazioneout",16,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"info2aconvocazioneout",17,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"esitixtipoargomentoout",18,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcontextout",19,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcodeout",20,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errmsgout",21,Types.VARCHAR); 
						
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