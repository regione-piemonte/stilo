/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecurityIureluouserBean;
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

public class IureluouserImpl  {
		
	private DmpkDefSecurityIureluouserBean bean;
		  
	public void setBean(DmpkDefSecurityIureluouserBean bean){
		this.bean = bean;
	}
	  
	  
	public void execute(Connection connection) throws SQLException {
	    CallableStatement call = null;			
		try{
			//Creo il Callbackstatement
			call = connection.prepareCall("{? = call DMPK_DEF_SECURITY.IURELUOUSER(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");			
			SubjectBean subject =  SubjectUtil.subject.get();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.addStatement(subject.getUuidtransaction(), call);
			}
			//Registro i parametri di out
			call.registerOutParameter(1, Types.INTEGER);
			call.registerOutParameter(4, Types.VARCHAR);
			call.registerOutParameter(17, Types.DECIMAL);
			call.registerOutParameter(19, Types.VARCHAR);
			call.registerOutParameter(31, Types.VARCHAR);
			call.registerOutParameter(37, Types.VARCHAR);
			call.registerOutParameter(38, Types.INTEGER);
			call.registerOutParameter(39, Types.VARCHAR);
			call.registerOutParameter(40, Types.INTEGER);
			call.registerOutParameter(41, Types.VARCHAR);
			
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
			util.settingParameterOnStore(call,bean,wrapperBean,"iduserin",8,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"desuserin",9,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"usernamein",10,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"dtiniziovldin",11,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"dtfinevldin",12,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgtiporelin",13,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flginclsottouoin",14,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flginclscrivvirtin",15,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgxscrivaniain",16,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"idscrivaniaio",17,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"intestazionescrivaniain",18,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"nuovaintestazionescrivaniaio",19,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"idruoloammin",20,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"desruoloammin",21,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"listaesclusioniuoppin",22,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgprimarioconruoloin",23,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"idprofiloin",24,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"nomeprofiloin",25,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"xmldatiprofiloin",26,Types.CLOB,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgmodgruppiprivin",27,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"xmlgruppiprivin",28,Types.CLOB,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgmodgruppiappin",29,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"xmlgruppiappin",30,Types.CLOB,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"attributiaddin",32,Types.CLOB,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgstoricizzadatiin",33,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgignorewarningin",34,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgrollbckfullin",35,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgautocommitin",36,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgignoresingolaappin",42,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgtipodestdocin",43,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"iduosvdestdocin",44,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"callingfuncin",45,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgaccessodoclimsvin",46,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgregistrazioneein",47,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgregistrazioneuiin",48,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flggestattiin",49,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgvispropattiiniterin",50,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flggestattiallin",51,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"listaidproctygestattiin",52,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgvispropattiallin",53,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"listaidproctyvispropattiin",54,Types.VARCHAR,connection); 	
			
			call.execute();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.removeStatement(subject.getUuidtransaction());
			}
			//Recupero i valori della chiamata
			util.settinParameterOnBean(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"cireluseruoio",4,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idscrivaniaio",17,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nuovaintestazionescrivaniaio",19,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"rowidscrivaniaout",31,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"warningmsgout",37,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgdatidastoricizzareout",38,Types.INTEGER); 
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