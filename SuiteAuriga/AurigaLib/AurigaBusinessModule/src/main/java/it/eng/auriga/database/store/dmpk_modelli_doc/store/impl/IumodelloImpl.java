/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_modelli_doc.bean.DmpkModelliDocIumodelloBean;
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

public class IumodelloImpl  {
		
	private DmpkModelliDocIumodelloBean bean;
		  
	public void setBean(DmpkModelliDocIumodelloBean bean){
		this.bean = bean;
	}
	  
	  
	public void execute(Connection connection) throws SQLException {
	    CallableStatement call = null;			
		try{
			//Creo il Callbackstatement
			call = connection.prepareCall("{? = call DMPK_MODELLI_DOC.IUMODELLO(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");			
			SubjectBean subject =  SubjectUtil.subject.get();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.addStatement(subject.getUuidtransaction(), call);
			}
			//Registro i parametri di out
			call.registerOutParameter(1, Types.INTEGER);
			call.registerOutParameter(4, Types.DECIMAL);
			call.registerOutParameter(12, Types.DECIMAL);
			call.registerOutParameter(13, Types.INTEGER);
			call.registerOutParameter(14, Types.INTEGER);
			call.registerOutParameter(15, Types.INTEGER);
			call.registerOutParameter(19, Types.VARCHAR);
			call.registerOutParameter(20, Types.VARCHAR);
			call.registerOutParameter(21, Types.INTEGER);
			call.registerOutParameter(22, Types.VARCHAR);
			
			HibernateStoreUtil util = new HibernateStoreUtil();
			
			BeanWrapperImpl wrapperBean = BeanPropertyUtility.getBeanWrapper(bean);
			
			//Setto i valori della store procedure
			util.settingParameterOnStore(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codidconnectiontokenin",2,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"iduserlavoroin",3,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"idmodelloio",4,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"nomein",5,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"descrizionein",6,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"annotazioniin",7,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"ciprovmodelloin",8,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flglockedin",9,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"nroordinein",10,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"attributiaddin",11,Types.CLOB,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgignorewarningin",16,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgrollbckfullin",17,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgautocommitin",18,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"tipomodelloin",23,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"tipoenitaassociatain",24,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"identitaassociatain",25,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flggeneraformatopdfin",26,Types.DECIMAL,connection); 	
			
			call.execute();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.removeStatement(subject.getUuidtransaction());
			}
			//Recupero i valori della chiamata
			util.settinParameterOnBean(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idmodelloio",4,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"iddocout",12,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"iddocxmlout",13,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"iddocpdfout",14,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"iddochtmlout",15,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"warningmsgout",19,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcontextout",20,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcodeout",21,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errmsgout",22,Types.VARCHAR); 
						
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