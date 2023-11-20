/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecurityMovepostazioneutenteBean;
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

public class MovepostazioneutenteImpl  {
		
	private DmpkDefSecurityMovepostazioneutenteBean bean;
		  
	public void setBean(DmpkDefSecurityMovepostazioneutenteBean bean){
		this.bean = bean;
	}
	  
	  
	public void execute(Connection connection) throws SQLException {
	    CallableStatement call = null;			
		try{
			//Creo il Callbackstatement
			call = connection.prepareCall("{? = call DMPK_DEF_SECURITY.MOVEPOSTAZIONEUTENTE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");			
			SubjectBean subject =  SubjectUtil.subject.get();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.addStatement(subject.getUuidtransaction(), call);
			}
			//Registro i parametri di out
			call.registerOutParameter(1, Types.INTEGER);
			call.registerOutParameter(4, Types.VARCHAR);
			call.registerOutParameter(13, Types.VARCHAR);
			call.registerOutParameter(21, Types.VARCHAR);
			call.registerOutParameter(22, Types.INTEGER);
			call.registerOutParameter(23, Types.VARCHAR);
			
			HibernateStoreUtil util = new HibernateStoreUtil();
			
			BeanWrapperImpl wrapperBean = BeanPropertyUtility.getBeanWrapper(bean);
			
			//Setto i valori della store procedure
			util.settingParameterOnStore(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codidconnectiontokenin",2,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"iduserlavoroin",3,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"cireluseruoio",4,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"iduoin",5,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"nrilivelliuoin",6,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"denominazioneuoin",7,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"dtiniziovldin",8,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"dtfinevldin",9,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgtiporelin",10,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flginclsottouoin",11,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flginclscrivvirtin",12,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"nuovaintestazionescrivaniaio",13,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"idruoloammin",14,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"desruoloammin",15,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"listaesclusioniuoppin",16,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgprimarioconruoloin",17,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"attributiaddin",18,Types.CLOB,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgrollbckfullin",19,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgautocommitin",20,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgaccessodoclimsvin",24,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgregistrazioneein",25,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgregistrazioneuiin",26,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flggestattiin",27,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgvispropattiiniterin",28,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flggestattiallin",29,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"listaidproctygestattiin",30,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgvispropattiallin",31,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"listaidproctyvispropattiin",32,Types.VARCHAR,connection); 	
			
			call.execute();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.removeStatement(subject.getUuidtransaction());
			}
			//Recupero i valori della chiamata
			util.settinParameterOnBean(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"cireluseruoio",4,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nuovaintestazionescrivaniaio",13,Types.VARCHAR); 
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