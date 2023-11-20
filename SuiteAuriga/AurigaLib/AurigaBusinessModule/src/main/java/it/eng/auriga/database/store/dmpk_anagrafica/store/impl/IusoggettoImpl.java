/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_anagrafica.bean.DmpkAnagraficaIusoggettoBean;
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

public class IusoggettoImpl  {
		
	private DmpkAnagraficaIusoggettoBean bean;
		  
	public void setBean(DmpkAnagraficaIusoggettoBean bean){
		this.bean = bean;
	}
	  
	  
	public void execute(Connection connection) throws SQLException {
	    CallableStatement call = null;			
		try{
			//Creo il Callbackstatement
			call = connection.prepareCall("{? = call DMPK_ANAGRAFICA.IUSOGGETTO(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");			
			SubjectBean subject =  SubjectUtil.subject.get();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.addStatement(subject.getUuidtransaction(), call);
			}
			//Registro i parametri di out
			call.registerOutParameter(1, Types.INTEGER);
			call.registerOutParameter(4, Types.DECIMAL);
			call.registerOutParameter(44, Types.VARCHAR);
			call.registerOutParameter(45, Types.VARCHAR);
			call.registerOutParameter(46, Types.INTEGER);
			call.registerOutParameter(47, Types.VARCHAR);
			
			HibernateStoreUtil util = new HibernateStoreUtil();
			
			BeanWrapperImpl wrapperBean = BeanPropertyUtility.getBeanWrapper(bean);
			
			//Setto i valori della store procedure
			util.settingParameterOnStore(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codidconnectiontokenin",2,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"iduserlavoroin",3,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"idsoggrubrio",4,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgpersonafisicain",5,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"denomcognomein",6,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"nomein",7,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"cfin",8,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"pivain",9,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codtiposottotipoin",10,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codcondgiuridicain",11,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"dtnascitain",12,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codistatcomunenascin",13,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"nomecomunenascin",14,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codistatstatonascin",15,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"nomestatonascin",16,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"dtcessazionein",17,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codcausalecessazionein",18,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgsexin",19,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codistatstatocittin",20,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"nomestatocittin",21,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"titoloin",22,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codindpain",23,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codammipain",24,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codaooipain",25,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"idsoggappin",26,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"ciprovsoggin",27,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"usernamein",28,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"passwordin",29,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codrapidoin",30,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flglockedin",31,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgmodindirizziin",32,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"xmlindirizziin",33,Types.CLOB,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgmodaltredenomin",34,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"xmlaltredenominazioniin",35,Types.CLOB,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgmodcontattiin",36,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"xmlcontattiin",37,Types.CLOB,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgmodgruppiappin",38,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"xmlgruppiappin",39,Types.CLOB,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"attributiaddin",40,Types.CLOB,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgignorewarningin",41,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgrollbckfullin",42,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgautocommitin",43,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"iduoin",48,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgvisibsottouoin",49,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flggestsottouoin",50,Types.INTEGER,connection); 	
			
			call.execute();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.removeStatement(subject.getUuidtransaction());
			}
			//Recupero i valori della chiamata
			util.settinParameterOnBean(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idsoggrubrio",4,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"warningmsgout",44,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcontextout",45,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcodeout",46,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errmsgout",47,Types.VARCHAR); 
						
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