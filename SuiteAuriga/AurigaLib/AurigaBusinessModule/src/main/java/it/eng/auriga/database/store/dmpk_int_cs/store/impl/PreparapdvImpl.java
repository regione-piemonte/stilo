/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_int_cs.bean.DmpkIntCsPreparapdvBean;
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

public class PreparapdvImpl  {
		
	private DmpkIntCsPreparapdvBean bean;
		  
	public void setBean(DmpkIntCsPreparapdvBean bean){
		this.bean = bean;
	}
	  
	  
	public void execute(Connection connection) throws SQLException {
	    CallableStatement call = null;			
		try{
			//Creo il Callbackstatement
			call = connection.prepareCall("{? = call DMPK_INT_CS.PREPARAPDV(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");			
			SubjectBean subject =  SubjectUtil.subject.get();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.addStatement(subject.getUuidtransaction(), call);
			}
			//Registro i parametri di out
			call.registerOutParameter(1, Types.INTEGER);
			call.registerOutParameter(2, Types.VARCHAR);
			call.registerOutParameter(3, Types.DECIMAL);
			call.registerOutParameter(4, Types.VARCHAR);
			call.registerOutParameter(5, Types.VARCHAR);
			call.registerOutParameter(6, Types.VARCHAR);
			call.registerOutParameter(7, Types.VARCHAR);
			call.registerOutParameter(8, Types.CLOB);
			call.registerOutParameter(9, Types.CLOB);
			call.registerOutParameter(10, Types.CLOB);
			call.registerOutParameter(11, Types.INTEGER);
			call.registerOutParameter(12, Types.VARCHAR);
			call.registerOutParameter(13, Types.VARCHAR);
			call.registerOutParameter(14, Types.INTEGER);
			call.registerOutParameter(15, Types.VARCHAR);
			
			HibernateStoreUtil util = new HibernateStoreUtil();
			
			BeanWrapperImpl wrapperBean = BeanPropertyUtility.getBeanWrapper(bean);
			
			//Setto i valori della store procedure
			util.settingParameterOnStore(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codpbproduttorein",16,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"idsistconservazionein",17,Types.VARCHAR,connection); 	
			
			call.execute();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.removeStatement(subject.getUuidtransaction());
			}
			//Recupero i valori della chiamata
			util.settinParameterOnBean(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"identrypointversout",2,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idspaooout",3,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codapplicazioneout",4,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codistapplicazioneout",5,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idsistconservazioneout",6,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"dettentrypointversout",7,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"xmldatigenpdvout",8,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"xmlindicepdvout",9,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"listafilepdvout",10,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nroitemout",11,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"iditemgroupout",12,Types.VARCHAR); 
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