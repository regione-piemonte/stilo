/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_process_types.bean.DmpkProcessTypesLoaddettprocesstypeBean;
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

public class LoaddettprocesstypeImpl  {
		
	private DmpkProcessTypesLoaddettprocesstypeBean bean;
		  
	public void setBean(DmpkProcessTypesLoaddettprocesstypeBean bean){
		this.bean = bean;
	}
	  
	  
	public void execute(Connection connection) throws SQLException {
	    CallableStatement call = null;			
		try{
			//Creo il Callbackstatement
			call = connection.prepareCall("{? = call DMPK_PROCESS_TYPES.LOADDETTPROCESSTYPE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");			
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
			call.registerOutParameter(9, Types.DECIMAL);
			call.registerOutParameter(10, Types.VARCHAR);
			call.registerOutParameter(11, Types.INTEGER);
			call.registerOutParameter(12, Types.VARCHAR);
			call.registerOutParameter(13, Types.DECIMAL);
			call.registerOutParameter(14, Types.VARCHAR);
			call.registerOutParameter(15, Types.INTEGER);
			call.registerOutParameter(16, Types.DECIMAL);
			call.registerOutParameter(17, Types.INTEGER);
			call.registerOutParameter(18, Types.DECIMAL);
			call.registerOutParameter(19, Types.INTEGER);
			call.registerOutParameter(20, Types.INTEGER);
			call.registerOutParameter(21, Types.DECIMAL);
			call.registerOutParameter(22, Types.VARCHAR);
			call.registerOutParameter(23, Types.DECIMAL);
			call.registerOutParameter(24, Types.VARCHAR);
			call.registerOutParameter(25, Types.VARCHAR);
			call.registerOutParameter(26, Types.DECIMAL);
			call.registerOutParameter(27, Types.VARCHAR);
			call.registerOutParameter(28, Types.INTEGER);
			call.registerOutParameter(29, Types.DECIMAL);
			call.registerOutParameter(30, Types.VARCHAR);
			call.registerOutParameter(31, Types.VARCHAR);
			call.registerOutParameter(32, Types.INTEGER);
			call.registerOutParameter(33, Types.INTEGER);
			call.registerOutParameter(34, Types.INTEGER);
			call.registerOutParameter(35, Types.VARCHAR);
			call.registerOutParameter(36, Types.INTEGER);
			call.registerOutParameter(37, Types.VARCHAR);
			call.registerOutParameter(38, Types.CLOB);
			call.registerOutParameter(39, Types.DECIMAL);
			call.registerOutParameter(40, Types.VARCHAR);
			call.registerOutParameter(41, Types.DECIMAL);
			call.registerOutParameter(42, Types.VARCHAR);
			call.registerOutParameter(43, Types.VARCHAR);
			call.registerOutParameter(44, Types.CLOB);
			call.registerOutParameter(45, Types.INTEGER);
			call.registerOutParameter(46, Types.INTEGER);
			call.registerOutParameter(47, Types.VARCHAR);
			call.registerOutParameter(48, Types.INTEGER);
			call.registerOutParameter(49, Types.VARCHAR);
			
			HibernateStoreUtil util = new HibernateStoreUtil();
			
			BeanWrapperImpl wrapperBean = BeanPropertyUtility.getBeanWrapper(bean);
			
			//Setto i valori della store procedure
			util.settingParameterOnStore(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codidconnectiontokenin",2,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"iduserlavoroin",3,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"idprocesstypeio",4,Types.DECIMAL,connection); 	
			
			call.execute();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.removeStatement(subject.getUuidtransaction());
			}
			//Recupero i valori della chiamata
			util.settinParameterOnBean(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idprocesstypeio",4,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nomeout",5,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"descrizioneout",6,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"dtiniziovldout",7,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"dtfinevldout",8,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idprocesstypegenout",9,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nomeprocesstypegenout",10,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgprocammout",11,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgtipoiniziativaout",12,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"ggdurataprevistaout",13,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"basenormativaout",14,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgsospendibileout",15,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nromaxsospensioniout",16,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flginterrompibileout",17,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nromaxggxinterrout",18,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgpartiesterneout",19,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgsilenzioassensoout",20,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"ggsilenzioassensoout",21,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgfascsfout",22,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idclassificazioneout",23,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"livelliclassificazioneout",24,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"desclassificazioneout",25,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idfoldertypeout",26,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nomefoldertypeout",27,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgconservpermout",28,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"periodoconservout",29,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codsupportoconservout",30,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"annotazioniout",31,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgrichabilxvisualizzout",32,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgrichabilxgestout",33,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgrichabilxassegnout",34,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"ciprovprocesstypeout",35,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flglockedout",36,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"rowidout",37,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"xmlflussiwfout",38,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"iduserrespout",39,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"desuserrespout",40,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"iduocompetenteout",41,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"livelliuocompetenteout",42,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"desuocompetenteout",43,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"attributiaddout",44,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"bachsizeout",45,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgmostraaltriattrout",46,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcontextout",47,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcodeout",48,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errmsgout",49,Types.VARCHAR); 
						
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