/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailPreparaconfermaautomaticaBean;
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

public class PreparaconfermaautomaticaImpl  {
		
	private DmpkIntMgoEmailPreparaconfermaautomaticaBean bean;
		  
	public void setBean(DmpkIntMgoEmailPreparaconfermaautomaticaBean bean){
		this.bean = bean;
	}
	  
	  
	public void execute(Connection connection) throws SQLException {
	    CallableStatement call = null;			
		try{
			//Creo il Callbackstatement
			call = connection.prepareCall("{? = call DMPK_INT_MGO_EMAIL.PREPARACONFERMAAUTOMATICA(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");			
			SubjectBean subject =  SubjectUtil.subject.get();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.addStatement(subject.getUuidtransaction(), call);
			}
			//Registro i parametri di out
			call.registerOutParameter(1, Types.INTEGER);
			call.registerOutParameter(7, Types.VARCHAR);
			call.registerOutParameter(8, Types.VARCHAR);
			call.registerOutParameter(9, Types.VARCHAR);
			call.registerOutParameter(10, Types.VARCHAR);
			call.registerOutParameter(11, Types.VARCHAR);
			call.registerOutParameter(12, Types.CLOB);
			call.registerOutParameter(13, Types.VARCHAR);
			call.registerOutParameter(14, Types.INTEGER);
			call.registerOutParameter(15, Types.VARCHAR);
			
			HibernateStoreUtil util = new HibernateStoreUtil();
			
			BeanWrapperImpl wrapperBean = BeanPropertyUtility.getBeanWrapper(bean);
			
			//Setto i valori della store procedure
			util.settingParameterOnStore(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"idutentepecin",2,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"idemailin",3,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"idudin",4,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"xmlsegnaturain",5,Types.CLOB,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"versionedtdin",6,Types.VARCHAR,connection); 	
			
			call.execute();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.removeStatement(subject.getUuidtransaction());
			}
			//Recupero i valori della chiamata
			util.settinParameterOnBean(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"destinatarimailnotifout",7,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"destinatariccmailnotifout",8,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"subjectmailnotifout",9,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"bodymailnotifout",10,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgricevutacbsout",11,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"xmlconfermaout",12,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcontextout",13,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcodeout",14,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errmsgout",15,Types.VARCHAR); 
						
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