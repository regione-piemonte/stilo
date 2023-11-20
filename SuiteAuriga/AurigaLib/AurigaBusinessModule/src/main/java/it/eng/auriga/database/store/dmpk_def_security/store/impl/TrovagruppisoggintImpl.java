/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecurityTrovagruppisoggintBean;
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

public class TrovagruppisoggintImpl  {
		
	private DmpkDefSecurityTrovagruppisoggintBean bean;
		  
	public void setBean(DmpkDefSecurityTrovagruppisoggintBean bean){
		this.bean = bean;
	}
	  
	  
	public void execute(Connection connection) throws SQLException {
	    CallableStatement call = null;			
		try{
			//Creo il Callbackstatement
			call = connection.prepareCall("{? = call DMPK_DEF_SECURITY.TROVAGRUPPISOGGINT(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");			
			SubjectBean subject =  SubjectUtil.subject.get();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.addStatement(subject.getUuidtransaction(), call);
			}
			//Registro i parametri di out
			call.registerOutParameter(1, Types.INTEGER);
			call.registerOutParameter(5, Types.DECIMAL);
			call.registerOutParameter(6, Types.VARCHAR);
			call.registerOutParameter(7, Types.INTEGER);
			call.registerOutParameter(8, Types.VARCHAR);
			call.registerOutParameter(9, Types.DECIMAL);
			call.registerOutParameter(10, Types.VARCHAR);
			call.registerOutParameter(11, Types.VARCHAR);
			call.registerOutParameter(12, Types.VARCHAR);
			call.registerOutParameter(13, Types.DECIMAL);
			call.registerOutParameter(14, Types.VARCHAR);
			call.registerOutParameter(15, Types.DECIMAL);
			call.registerOutParameter(16, Types.VARCHAR);
			call.registerOutParameter(17, Types.VARCHAR);
			call.registerOutParameter(18, Types.VARCHAR);
			call.registerOutParameter(19, Types.VARCHAR);
			call.registerOutParameter(20, Types.INTEGER);
			call.registerOutParameter(21, Types.CLOB);
			call.registerOutParameter(22, Types.VARCHAR);
			call.registerOutParameter(23, Types.VARCHAR);
			call.registerOutParameter(25, Types.INTEGER);
			call.registerOutParameter(26, Types.INTEGER);
			call.registerOutParameter(29, Types.INTEGER);
			call.registerOutParameter(30, Types.INTEGER);
			call.registerOutParameter(31, Types.CLOB);
			call.registerOutParameter(32, Types.INTEGER);
			call.registerOutParameter(33, Types.INTEGER);
			call.registerOutParameter(34, Types.INTEGER);
			call.registerOutParameter(35, Types.INTEGER);
			call.registerOutParameter(36, Types.VARCHAR);
			call.registerOutParameter(37, Types.INTEGER);
			call.registerOutParameter(38, Types.VARCHAR);
			
			HibernateStoreUtil util = new HibernateStoreUtil();
			
			BeanWrapperImpl wrapperBean = BeanPropertyUtility.getBeanWrapper(bean);
			
			//Setto i valori della store procedure
			util.settingParameterOnStore(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codidconnectiontokenin",2,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"iduserlavoroin",3,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgpreimpostafiltroin",4,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"idgruppoio",5,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"nomeio",6,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgdidefinaclio",7,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"ciprovgruppoio",8,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgincludinonvldio",9,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"tsvldio",10,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codapplownerio",11,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codistapplownerio",12,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgrestrapplownerio",13,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgtpobjappartenenteio",14,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"idobjappartenenteio",15,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"desobjappartenenteio",16,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"livelliuoappartenenteio",17,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"desuoappscrivaniatoio",18,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"desuserscrivaniatoio",19,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgancheappartindirettaio",20,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"altricriteriio",21,Types.CLOB,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"colorderbyio",22,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgdescorderbyio",23,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgsenzapaginazionein",24,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"nropaginaio",25,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"bachsizeio",26,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"overflowlimitin",27,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgsenzatotin",28,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"rigatagnamein",39,Types.VARCHAR,connection); 	
			
			call.execute();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.removeStatement(subject.getUuidtransaction());
			}
			//Recupero i valori della chiamata
			util.settinParameterOnBean(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idgruppoio",5,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nomeio",6,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgdidefinaclio",7,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"ciprovgruppoio",8,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgincludinonvldio",9,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"tsvldio",10,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codapplownerio",11,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codistapplownerio",12,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgrestrapplownerio",13,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgtpobjappartenenteio",14,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idobjappartenenteio",15,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"desobjappartenenteio",16,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"livelliuoappartenenteio",17,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"desuoappscrivaniatoio",18,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"desuserscrivaniatoio",19,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgancheappartindirettaio",20,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"altricriteriio",21,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"colorderbyio",22,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgdescorderbyio",23,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nropaginaio",25,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"bachsizeio",26,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nrototrecout",29,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nrorecinpaginaout",30,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"listaxmlout",31,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgabilinsout",32,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgabilupdout",33,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgabildelout",34,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgmostraaltriattrout",35,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcontextout",36,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcodeout",37,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errmsgout",38,Types.VARCHAR); 
						
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