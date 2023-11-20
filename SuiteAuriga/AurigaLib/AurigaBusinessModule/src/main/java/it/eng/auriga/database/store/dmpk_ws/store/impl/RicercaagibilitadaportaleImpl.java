/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_ws.bean.DmpkWsRicercaagibilitadaportaleBean;
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

public class RicercaagibilitadaportaleImpl  {
		
	private DmpkWsRicercaagibilitadaportaleBean bean;
		  
	public void setBean(DmpkWsRicercaagibilitadaportaleBean bean){
		this.bean = bean;
	}
	  
	  
	public void execute(Connection connection) throws SQLException {
	    CallableStatement call = null;			
		try{
			//Creo il Callbackstatement
			call = connection.prepareCall("{? = call DMPK_WS.RICERCAAGIBILITADAPORTALE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");			
			SubjectBean subject =  SubjectUtil.subject.get();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.addStatement(subject.getUuidtransaction(), call);
			}
			//Registro i parametri di out
			call.registerOutParameter(1, Types.INTEGER);
			call.registerOutParameter(3, Types.VARCHAR);
			call.registerOutParameter(4, Types.DECIMAL);
			call.registerOutParameter(5, Types.DECIMAL);
			call.registerOutParameter(6, Types.DECIMAL);
			call.registerOutParameter(7, Types.VARCHAR);
			call.registerOutParameter(8, Types.CLOB);
			call.registerOutParameter(9, Types.CLOB);
			call.registerOutParameter(10, Types.VARCHAR);
			call.registerOutParameter(11, Types.DECIMAL);
			call.registerOutParameter(12, Types.DECIMAL);
			call.registerOutParameter(13, Types.VARCHAR);
			call.registerOutParameter(14, Types.VARCHAR);
			call.registerOutParameter(15, Types.VARCHAR);
			call.registerOutParameter(16, Types.VARCHAR);
			call.registerOutParameter(17, Types.CLOB);
			call.registerOutParameter(18, Types.INTEGER);
			call.registerOutParameter(19, Types.DECIMAL);
			call.registerOutParameter(20, Types.VARCHAR);
			call.registerOutParameter(21, Types.VARCHAR);
			call.registerOutParameter(22, Types.VARCHAR);
			call.registerOutParameter(23, Types.CLOB);
			call.registerOutParameter(24, Types.VARCHAR);
			call.registerOutParameter(25, Types.VARCHAR);
			call.registerOutParameter(26, Types.VARCHAR);
			call.registerOutParameter(27, Types.INTEGER);
			call.registerOutParameter(28, Types.VARCHAR);
			
			HibernateStoreUtil util = new HibernateStoreUtil();
			
			BeanWrapperImpl wrapperBean = BeanPropertyUtility.getBeanWrapper(bean);
			
			//Setto i valori della store procedure
			util.settingParameterOnStore(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"xmlrequestin",2,Types.CLOB,connection); 	
			
			call.execute();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.removeStatement(subject.getUuidtransaction());
			}
			//Recupero i valori della chiamata
			util.settinParameterOnBean(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"conntokenout",3,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"iddominioout",4,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idudrichiestaout",5,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"iddocrichiestaout",6,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"estremiprorichout",7,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"listaagibilitaout",8,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"listafileout",9,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"modalitafilexportaleout",10,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idudrispostaout",11,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"iddocrispostaout",12,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"estremiprorispostaout",13,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idutenteinviomailout",14,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"accountmittmailtosendout",15,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"subjectmailtosendout",16,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"bodymailtosendout",17,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"fileallegatimailout",18,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idtemplaterispostaout",19,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"uritemplaterispostaout",20,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nometemplaterispostaout",21,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"tipotemplateout",22,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"xmldatixmodelloout",23,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"contenutobarcodeout",24,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"testoinchiarobarcodeout",25,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcontextout",26,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcodeout",27,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errmsgout",28,Types.VARCHAR); 
						
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