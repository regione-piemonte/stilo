/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_titolario.bean.DmpkTitolarioIuclassificaBean;
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

public class IuclassificaImpl  {
		
	private DmpkTitolarioIuclassificaBean bean;
		  
	public void setBean(DmpkTitolarioIuclassificaBean bean){
		this.bean = bean;
	}
	  
	  
	public void execute(Connection connection) throws SQLException {
	    CallableStatement call = null;			
		try{
			//Creo il Callbackstatement
			call = connection.prepareCall("{? = call DMPK_TITOLARIO.IUCLASSIFICA(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");			
			SubjectBean subject =  SubjectUtil.subject.get();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.addStatement(subject.getUuidtransaction(), call);
			}
			//Registro i parametri di out
			call.registerOutParameter(1, Types.INTEGER);
			call.registerOutParameter(5, Types.DECIMAL);
			call.registerOutParameter(29, Types.VARCHAR);
			call.registerOutParameter(34, Types.INTEGER);
			call.registerOutParameter(38, Types.VARCHAR);
			call.registerOutParameter(39, Types.VARCHAR);
			call.registerOutParameter(40, Types.INTEGER);
			call.registerOutParameter(41, Types.VARCHAR);
			
			HibernateStoreUtil util = new HibernateStoreUtil();
			
			BeanWrapperImpl wrapperBean = BeanPropertyUtility.getBeanWrapper(bean);
			
			//Setto i valori della store procedure
			util.settingParameterOnStore(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codidconnectiontokenin",2,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"iduserlavoroin",3,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"idpianoclassifin",4,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"idclassificazioneio",5,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"idclassificazionesupin",6,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"nrilivelliclassifsupin",7,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"nrolivelloin",8,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"desclassificazionein",9,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"dtiniziovldin",10,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"dtfinevldin",11,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"parolechiavein",12,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"nroposizionein",13,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codsupportoconservin",14,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"dessupportoconservin",15,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgconservpermanentein",16,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"periodoconservazionein",17,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgnumcontinuain",18,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgfolderizzaxannoin",19,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgrichabilxvisin",20,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgrichabilxgestin",21,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgrichabilxfirmain",22,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgrichabilxassegnin",23,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgrichabilxaperfascin",24,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgclassifinibitain",25,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"provciclassifin",26,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgmodtemplatexfascin",27,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"xmltemplatexfascin",28,Types.CLOB,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"attributiaddin",30,Types.CLOB,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgstoricizzadatiin",31,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"tsvariazionedatiin",32,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"motivivariazionein",33,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgignorewarningin",35,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgrollbckfullin",36,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgautocommitin",37,Types.INTEGER,connection); 	
			
			call.execute();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.removeStatement(subject.getUuidtransaction());
			}
			//Recupero i valori della chiamata
			util.settinParameterOnBean(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idclassificazioneio",5,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"rowidout",29,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgdatidastoricizzareout",34,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"warningmsgout",38,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcontextout",39,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcodeout",40,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errmsgout",41,Types.VARCHAR); 
						
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