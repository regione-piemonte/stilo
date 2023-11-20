/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_titolario.bean.DmpkTitolarioLoaddettclassificaBean;
import it.eng.storeutil.HibernateStoreUtil;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

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

public class LoaddettclassificaImpl  {
		
	private DmpkTitolarioLoaddettclassificaBean bean;
		  
	public void setBean(DmpkTitolarioLoaddettclassificaBean bean){
		this.bean = bean;
	}
	  
	  
	public void execute(Connection connection) throws SQLException {
	    CallableStatement call = null;			
		try{
			//Creo il Callbackstatement
			call = connection.prepareCall("{? = call DMPK_TITOLARIO.LOADDETTCLASSIFICA(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");			
			SubjectBean subject =  SubjectUtil.subject.get();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.addStatement(subject.getUuidtransaction(), call);
			}
			//Registro i parametri di out
			call.registerOutParameter(1, Types.INTEGER);
			call.registerOutParameter(5, Types.DECIMAL);
			call.registerOutParameter(6, Types.DECIMAL);
			call.registerOutParameter(7, Types.VARCHAR);
			call.registerOutParameter(8, Types.VARCHAR);
			call.registerOutParameter(9, Types.VARCHAR);
			call.registerOutParameter(10, Types.VARCHAR);
			call.registerOutParameter(11, Types.VARCHAR);
			call.registerOutParameter(12, Types.VARCHAR);
			call.registerOutParameter(13, Types.DECIMAL);
			call.registerOutParameter(14, Types.VARCHAR);
			call.registerOutParameter(15, Types.VARCHAR);
			call.registerOutParameter(16, Types.INTEGER);
			call.registerOutParameter(17, Types.INTEGER);
			call.registerOutParameter(18, Types.INTEGER);
			call.registerOutParameter(19, Types.INTEGER);
			call.registerOutParameter(20, Types.INTEGER);
			call.registerOutParameter(21, Types.INTEGER);
			call.registerOutParameter(22, Types.INTEGER);
			call.registerOutParameter(23, Types.INTEGER);
			call.registerOutParameter(24, Types.INTEGER);
			call.registerOutParameter(25, Types.INTEGER);
			call.registerOutParameter(26, Types.VARCHAR);
			call.registerOutParameter(27, Types.CLOB);
			call.registerOutParameter(28, Types.VARCHAR);
			call.registerOutParameter(29, Types.CLOB);
			call.registerOutParameter(30, Types.INTEGER);
			call.registerOutParameter(31, Types.VARCHAR);
			call.registerOutParameter(32, Types.INTEGER);
			call.registerOutParameter(33, Types.VARCHAR);
			
			HibernateStoreUtil util = new HibernateStoreUtil();
			
			BeanWrapperImpl wrapperBean = BeanPropertyUtility.getBeanWrapper(bean);
			
			//Setto i valori della store procedure
			util.settingParameterOnStore(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codidconnectiontokenin",2,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"iduserlavoroin",3,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"idclassificazionein",4,Types.DECIMAL,connection); 	
			
			call.execute();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.removeStatement(subject.getUuidtransaction());
			}
			//Recupero i valori della chiamata
			util.settinParameterOnBean(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idpianoclassifout",5,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idclassificazionesupout",6,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nrilivelliclassifsupout",7,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nrolivelloout",8,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"desclassificazioneout",9,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"dtiniziovldout",10,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"dtfinevldout",11,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"parolechiaveout",12,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nroposizioneout",13,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codsupportoconservout",14,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"dessupportoconservout",15,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgconservpermanenteout",16,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"periodoconservazionout",17,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgnumcontinuaout",18,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgfolderizzaxannoout",19,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgrichabilxvisout",20,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgrichabilxgestout",21,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgrichabilxfirmaout",22,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgrichabilxassegnout",23,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgrichabilxaperfascout",24,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgclassifinibitaout",25,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"provciclassifout",26,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"xmltemplatexfascout",27,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"rowidout",28,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"attributiaddout",29,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgmostraaltriattrout",30,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcontextout",31,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcodeout",32,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errmsgout",33,Types.VARCHAR); 
						
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