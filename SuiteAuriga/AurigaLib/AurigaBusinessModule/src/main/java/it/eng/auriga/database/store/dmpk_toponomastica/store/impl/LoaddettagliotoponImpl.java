/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_toponomastica.bean.DmpkToponomasticaLoaddettagliotoponBean;
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

public class LoaddettagliotoponImpl  {
		
	private DmpkToponomasticaLoaddettagliotoponBean bean;
		  
	public void setBean(DmpkToponomasticaLoaddettagliotoponBean bean){
		this.bean = bean;
	}
	  
	  
	public void execute(Connection connection) throws SQLException {
	    CallableStatement call = null;			
		try{
			//Creo il Callbackstatement
			call = connection.prepareCall("{? = call DMPK_TOPONOMASTICA.LOADDETTAGLIOTOPON(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");			
			SubjectBean subject =  SubjectUtil.subject.get();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.addStatement(subject.getUuidtransaction(), call);
			}
			//Registro i parametri di out
			call.registerOutParameter(1, Types.DECIMAL);
			call.registerOutParameter(3, Types.VARCHAR);
			call.registerOutParameter(4, Types.VARCHAR);
			call.registerOutParameter(5, Types.VARCHAR);
			call.registerOutParameter(6, Types.VARCHAR);
			call.registerOutParameter(7, Types.VARCHAR);
			call.registerOutParameter(8, Types.VARCHAR);
			call.registerOutParameter(9, Types.VARCHAR);
			call.registerOutParameter(10, Types.VARCHAR);
			call.registerOutParameter(11, Types.DECIMAL);
			call.registerOutParameter(12, Types.VARCHAR);
			call.registerOutParameter(13, Types.VARCHAR);
			call.registerOutParameter(14, Types.VARCHAR);
			call.registerOutParameter(15, Types.VARCHAR);
			call.registerOutParameter(16, Types.VARCHAR);
			call.registerOutParameter(17, Types.VARCHAR);
			call.registerOutParameter(18, Types.DECIMAL);
			call.registerOutParameter(19, Types.DECIMAL);
			call.registerOutParameter(20, Types.DECIMAL);
			call.registerOutParameter(21, Types.VARCHAR);
			call.registerOutParameter(22, Types.INTEGER);
			call.registerOutParameter(23, Types.VARCHAR);
			
			HibernateStoreUtil util = new HibernateStoreUtil();
			
			BeanWrapperImpl wrapperBean = BeanPropertyUtility.getBeanWrapper(bean);
			
			//Setto i valori della store procedure
			util.settingParameterOnStore(call,bean,wrapperBean,"parametro_1",1,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codidconnectiontokenin",2,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"citoponomasticoinout",3,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"cicivicoinout",4,Types.VARCHAR,connection); 	
			
			call.execute();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.removeStatement(subject.getUuidtransaction());
			}
			//Recupero i valori della chiamata
			util.settinParameterOnBean(call,bean,wrapperBean,"parametro_1",1,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"citoponomasticoinout",3,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"cicivicoinout",4,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nometoponout",5,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"capout",6,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"ciquartiereout",7,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nomequartiereout",8,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"dtiniziovldout",9,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"dtfinevldout",10,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"ciidspaooout",11,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"prvcitoponout",12,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"civicoout",13,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"capcivout",14,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"ciquartierecivout",15,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nomequartierecivout",16,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"localitacivout",17,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"coordxout",18,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"coordyout",19,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"coordzout",20,Types.DECIMAL); 
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