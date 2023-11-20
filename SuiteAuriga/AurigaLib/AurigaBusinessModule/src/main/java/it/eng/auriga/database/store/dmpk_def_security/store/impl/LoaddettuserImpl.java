/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecurityLoaddettuserBean;
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

public class LoaddettuserImpl  {
		
	private DmpkDefSecurityLoaddettuserBean bean;
		  
	public void setBean(DmpkDefSecurityLoaddettuserBean bean){
		this.bean = bean;
	}
	  
	  
	public void execute(Connection connection) throws SQLException {
	    CallableStatement call = null;			
		try{
			//Creo il Callbackstatement
			call = connection.prepareCall("{? = call DMPK_DEF_SECURITY.LOADDETTUSER(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");			
			SubjectBean subject =  SubjectUtil.subject.get();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.addStatement(subject.getUuidtransaction(), call);
			}
			//Registro i parametri di out
			call.registerOutParameter(1, Types.INTEGER);
			call.registerOutParameter(4, Types.DECIMAL);
			call.registerOutParameter(5, Types.VARCHAR);
			call.registerOutParameter(6, Types.VARCHAR);
			call.registerOutParameter(7, Types.VARCHAR);
			call.registerOutParameter(8, Types.VARCHAR);
			call.registerOutParameter(9, Types.VARCHAR);
			call.registerOutParameter(10, Types.VARCHAR);
			call.registerOutParameter(11, Types.DECIMAL);
			call.registerOutParameter(12, Types.VARCHAR);
			call.registerOutParameter(13, Types.CLOB);
			call.registerOutParameter(14, Types.CLOB);
			call.registerOutParameter(15, Types.DECIMAL);
			call.registerOutParameter(16, Types.VARCHAR);
			call.registerOutParameter(17, Types.VARCHAR);
			call.registerOutParameter(18, Types.VARCHAR);
			call.registerOutParameter(19, Types.VARCHAR);
			call.registerOutParameter(20, Types.INTEGER);
			call.registerOutParameter(21, Types.VARCHAR);
			call.registerOutParameter(22, Types.INTEGER);
			call.registerOutParameter(23, Types.VARCHAR);
			call.registerOutParameter(24, Types.CLOB);
			call.registerOutParameter(25, Types.CLOB);
			call.registerOutParameter(26, Types.CLOB);
			call.registerOutParameter(27, Types.CLOB);
			call.registerOutParameter(28, Types.CLOB);
			call.registerOutParameter(29, Types.CLOB);
			call.registerOutParameter(30, Types.CLOB);
			call.registerOutParameter(31, Types.CLOB);
			call.registerOutParameter(32, Types.CLOB);
			call.registerOutParameter(33, Types.INTEGER);
			call.registerOutParameter(34, Types.INTEGER);
			call.registerOutParameter(35, Types.VARCHAR);
			call.registerOutParameter(36, Types.INTEGER);
			call.registerOutParameter(37, Types.VARCHAR);
			call.registerOutParameter(38, Types.VARCHAR);
			call.registerOutParameter(39, Types.VARCHAR);
			call.registerOutParameter(40, Types.INTEGER);
			
			HibernateStoreUtil util = new HibernateStoreUtil();
			
			BeanWrapperImpl wrapperBean = BeanPropertyUtility.getBeanWrapper(bean);
			
			//Setto i valori della store procedure
			util.settingParameterOnStore(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codidconnectiontokenin",2,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"iduserlavoroin",3,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"iduserio",4,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"desuserio",5,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"usernameio",6,Types.VARCHAR,connection); 	
			
			call.execute();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.removeStatement(subject.getUuidtransaction());
			}
			//Recupero i valori della chiamata
			util.settinParameterOnBean(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"iduserio",4,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"desuserio",5,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"usernameio",6,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"dtiniziovldout",7,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"dtfinevldout",8,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"dtinizioaccredindomout",9,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"dtfineaccredindomout",10,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idprofiloout",11,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nomeprofiloout",12,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"xmldatiprofiloout",13,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"xmlgruppiprivout",14,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idsoggrubricaout",15,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"titoloout",16,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"qualificaout",17,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nromatricolaout",18,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"emailout",19,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgsenzaaccessoalsistemaout",20,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"ciprovuserout",21,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flglockedout",22,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"rowidout",23,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"xmlapplestaccredout",24,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"xmlrelconuoout",25,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"visdocassinvauoxmlout",26,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"moddocassauoxmlout",27,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"xmluocaselleemailvisbout",28,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"xmldelegheout",29,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"xmlgruppiappout",30,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"indirizziout",31,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"attributiaddout",32,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"bachsizeout",33,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgmostraaltriattrout",34,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcontextout",35,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcodeout",36,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errmsgout",37,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codrapidoout",38,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codfiscaleout",39,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"accountdeflockedout",40,Types.INTEGER); 
						
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