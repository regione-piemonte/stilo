/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_modelli_doc.bean.DmpkModelliDocLoaddettmodelloBean;
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

public class LoaddettmodelloImpl  {
		
	private DmpkModelliDocLoaddettmodelloBean bean;
		  
	public void setBean(DmpkModelliDocLoaddettmodelloBean bean){
		this.bean = bean;
	}
	  
	  
	public void execute(Connection connection) throws SQLException {
	    CallableStatement call = null;			
		try{
			//Creo il Callbackstatement
			call = connection.prepareCall("{? = call DMPK_MODELLI_DOC.LOADDETTMODELLO(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");			
			SubjectBean subject =  SubjectUtil.subject.get();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.addStatement(subject.getUuidtransaction(), call);
			}
			//Registro i parametri di out
			call.registerOutParameter(1, Types.INTEGER);
			call.registerOutParameter(4, Types.DECIMAL);
			call.registerOutParameter(5, Types.VARCHAR);
			call.registerOutParameter(6, Types.VARCHAR);
			call.registerOutParameter(7, Types.DECIMAL);
			call.registerOutParameter(8, Types.INTEGER);
			call.registerOutParameter(9, Types.VARCHAR);
			call.registerOutParameter(10, Types.VARCHAR);
			call.registerOutParameter(11, Types.VARCHAR);
			call.registerOutParameter(12, Types.INTEGER);
			call.registerOutParameter(13, Types.INTEGER);
			call.registerOutParameter(14, Types.INTEGER);
			call.registerOutParameter(15, Types.INTEGER);
			call.registerOutParameter(16, Types.INTEGER);
			call.registerOutParameter(17, Types.INTEGER);
			call.registerOutParameter(18, Types.VARCHAR);
			call.registerOutParameter(19, Types.CLOB);
			call.registerOutParameter(20, Types.INTEGER);
			call.registerOutParameter(21, Types.VARCHAR);
			call.registerOutParameter(22, Types.VARCHAR);
			call.registerOutParameter(23, Types.DECIMAL);
			call.registerOutParameter(24, Types.VARCHAR);
			call.registerOutParameter(25, Types.VARCHAR);
			call.registerOutParameter(26, Types.INTEGER);
			call.registerOutParameter(27, Types.VARCHAR);
			call.registerOutParameter(28, Types.INTEGER);
			call.registerOutParameter(29, Types.VARCHAR);
			
			HibernateStoreUtil util = new HibernateStoreUtil();
			
			BeanWrapperImpl wrapperBean = BeanPropertyUtility.getBeanWrapper(bean);
			
			//Setto i valori della store procedure
			util.settingParameterOnStore(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codidconnectiontokenin",2,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"iduserlavoroin",3,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"idmodelloio",4,Types.DECIMAL,connection); 	
			
			call.execute();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.removeStatement(subject.getUuidtransaction());
			}
			//Recupero i valori della chiamata
			util.settinParameterOnBean(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idmodelloio",4,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nomeout",5,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"descrizioneout",6,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"iddocout",7,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nrovereldocout",8,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"displayfilenamevereldocout",9,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"annotazioniout",10,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"ciprovmodelloout",11,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flglockedout",12,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"iddocxmlout",13,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"iddocpdfout",14,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"iddochtmlout",15,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nroordineout",16,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgprofcompletaout",17,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"rowidout",18,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"attributiaddout",19,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgmostraaltriattrout",20,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"tipomodelloout",21,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"tipoenitaassociataout",22,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"identitaassociataout",23,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nomeentitaassociataout",24,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nometabellaentitaassout",25,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flggeneraformatopdfout",26,Types.INTEGER); 
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