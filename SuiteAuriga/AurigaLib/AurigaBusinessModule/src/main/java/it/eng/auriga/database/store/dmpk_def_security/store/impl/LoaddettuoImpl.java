/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecurityLoaddettuoBean;
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

public class LoaddettuoImpl  {
		
	private DmpkDefSecurityLoaddettuoBean bean;
		  
	public void setBean(DmpkDefSecurityLoaddettuoBean bean){
		this.bean = bean;
	}
	  
	  
	public void execute(Connection connection) throws SQLException {
	    CallableStatement call = null;			
		try{
			//Creo il Callbackstatement
			call = connection.prepareCall("{? = call DMPK_DEF_SECURITY.LOADDETTUO(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");			
			SubjectBean subject =  SubjectUtil.subject.get();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.addStatement(subject.getUuidtransaction(), call);
			}
			//Registro i parametri di out
			call.registerOutParameter(1, Types.INTEGER);
			call.registerOutParameter(4, Types.DECIMAL);
			call.registerOutParameter(5, Types.VARCHAR);
			call.registerOutParameter(6, Types.VARCHAR);
			call.registerOutParameter(8, Types.DECIMAL);
			call.registerOutParameter(9, Types.DECIMAL);
			call.registerOutParameter(10, Types.DECIMAL);
			call.registerOutParameter(11, Types.VARCHAR);
			call.registerOutParameter(12, Types.VARCHAR);
			call.registerOutParameter(13, Types.VARCHAR);
			call.registerOutParameter(14, Types.VARCHAR);
			call.registerOutParameter(15, Types.DECIMAL);
			call.registerOutParameter(16, Types.VARCHAR);
			call.registerOutParameter(17, Types.VARCHAR);
			call.registerOutParameter(18, Types.DECIMAL);
			call.registerOutParameter(19, Types.VARCHAR);
			call.registerOutParameter(20, Types.CLOB);
			call.registerOutParameter(21, Types.CLOB);
			call.registerOutParameter(22, Types.INTEGER);
			call.registerOutParameter(23, Types.VARCHAR);
			call.registerOutParameter(24, Types.VARCHAR);
			call.registerOutParameter(25, Types.INTEGER);
			call.registerOutParameter(26, Types.CLOB);
			call.registerOutParameter(27, Types.CLOB);
			call.registerOutParameter(28, Types.VARCHAR);
			call.registerOutParameter(29, Types.CLOB);
			call.registerOutParameter(30, Types.INTEGER);
			call.registerOutParameter(31, Types.INTEGER);
			call.registerOutParameter(32, Types.VARCHAR);
			call.registerOutParameter(33, Types.INTEGER);
			call.registerOutParameter(34, Types.VARCHAR);
			call.registerOutParameter(36, Types.CLOB);
			
			HibernateStoreUtil util = new HibernateStoreUtil();
			
			BeanWrapperImpl wrapperBean = BeanPropertyUtility.getBeanWrapper(bean);
			
			//Setto i valori della store procedure
			util.settingParameterOnStore(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codidconnectiontokenin",2,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"iduserlavoroin",3,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"iduoio",4,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"nrilivelliuoio",5,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"denominazioneio",6,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"tsrifin",7,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"idorganigrammain",35,Types.DECIMAL,connection); 	
			
			call.execute();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.removeStatement(subject.getUuidtransaction());
			}
			//Recupero i valori della chiamata
			util.settinParameterOnBean(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"iduoio",4,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nrilivelliuoio",5,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"denominazioneio",6,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"iduosupout",8,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nrolivellogerarchicouosupout",9,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nrolivellogerarchicoout",10,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codtipoout",11,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"destipoout",12,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"dtistituzioneout",13,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"dtsoppressioneout",14,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nroposizioneout",15,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"competenzeout",16,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"storiaout",17,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idprofiloout",18,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nomeprofiloout",19,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"xmldatiprofiloout",20,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"xmlgruppiprivout",21,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgdichipaout",22,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"dtdichipaout",23,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"ciprovuoout",24,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flglockedout",25,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"xmlgruppiappout",26,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"xmlcontattiout",27,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"rowidout",28,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"attributiaddout",29,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"bachsizeout",30,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgmostraaltriattrout",31,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcontextout",32,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcodeout",33,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errmsgout",34,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"xmlpuntiprotocolloout",36,Types.CLOB); 
						
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