/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_doc_types.bean.DmpkDocTypesLoaddettdoctypeBean;
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

public class LoaddettdoctypeImpl  {
		
	private DmpkDocTypesLoaddettdoctypeBean bean;
		  
	public void setBean(DmpkDocTypesLoaddettdoctypeBean bean){
		this.bean = bean;
	}
	  
	  
	public void execute(Connection connection) throws SQLException {
	    CallableStatement call = null;			
		try{
			//Creo il Callbackstatement
			call = connection.prepareCall("{? = call DMPK_DOC_TYPES.LOADDETTDOCTYPE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");			
			SubjectBean subject =  SubjectUtil.subject.get();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.addStatement(subject.getUuidtransaction(), call);
			}
			//Registro i parametri di out
			call.registerOutParameter(1, Types.INTEGER);
			call.registerOutParameter(4, Types.DECIMAL);
			call.registerOutParameter(5, Types.VARCHAR);
			call.registerOutParameter(6, Types.VARCHAR);
			call.registerOutParameter(7, Types.DECIMAL);
			call.registerOutParameter(8, Types.VARCHAR);
			call.registerOutParameter(9, Types.INTEGER);
			call.registerOutParameter(10, Types.VARCHAR);
			call.registerOutParameter(11, Types.VARCHAR);
			call.registerOutParameter(12, Types.VARCHAR);
			call.registerOutParameter(13, Types.VARCHAR);
			call.registerOutParameter(14, Types.INTEGER);
			call.registerOutParameter(15, Types.INTEGER);
			call.registerOutParameter(16, Types.DECIMAL);
			call.registerOutParameter(17, Types.VARCHAR);
			call.registerOutParameter(18, Types.DECIMAL);
			call.registerOutParameter(19, Types.VARCHAR);
			call.registerOutParameter(20, Types.VARCHAR);
			call.registerOutParameter(21, Types.DECIMAL);
			call.registerOutParameter(22, Types.VARCHAR);
			call.registerOutParameter(23, Types.VARCHAR);
			call.registerOutParameter(24, Types.VARCHAR);
			call.registerOutParameter(25, Types.VARCHAR);
			call.registerOutParameter(26, Types.INTEGER);
			call.registerOutParameter(27, Types.INTEGER);
			call.registerOutParameter(28, Types.INTEGER);
			call.registerOutParameter(29, Types.INTEGER);
			call.registerOutParameter(30, Types.VARCHAR);
			call.registerOutParameter(31, Types.VARCHAR);
			call.registerOutParameter(32, Types.VARCHAR);
			call.registerOutParameter(33, Types.DECIMAL);
			call.registerOutParameter(34, Types.VARCHAR);
			call.registerOutParameter(35, Types.INTEGER);
			call.registerOutParameter(36, Types.VARCHAR);
			call.registerOutParameter(37, Types.CLOB);
			call.registerOutParameter(38, Types.CLOB);
			call.registerOutParameter(39, Types.CLOB);
			call.registerOutParameter(40, Types.CLOB);
			call.registerOutParameter(41, Types.VARCHAR);
			call.registerOutParameter(42, Types.INTEGER);
			call.registerOutParameter(43, Types.INTEGER);
			call.registerOutParameter(44, Types.VARCHAR);
			call.registerOutParameter(45, Types.INTEGER);
			call.registerOutParameter(46, Types.VARCHAR);
			
			HibernateStoreUtil util = new HibernateStoreUtil();
			
			BeanWrapperImpl wrapperBean = BeanPropertyUtility.getBeanWrapper(bean);
			
			//Setto i valori della store procedure
			util.settingParameterOnStore(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codidconnectiontokenin",2,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"iduserlavoroin",3,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"iddoctypeio",4,Types.DECIMAL,connection); 	
			
			call.execute();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.removeStatement(subject.getUuidtransaction());
			}
			//Recupero i valori della chiamata
			util.settinParameterOnBean(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"iddoctypeio",4,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nomeout",5,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"descrizioneout",6,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"iddoctypegenout",7,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nomedoctypegenout",8,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgallegatoout",9,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codnaturaout",10,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgtipoprovout",11,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codsupportoorigout",12,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgtipocartaceoout",13,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgdascansionareout",14,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgconservpermout",15,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"periodoconservout",16,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codsupportoconservout",17,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idclassificazioneout",18,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"livelliclassificazioneout",19,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"desclassificazioneout",20,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idformatoelout",21,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nomeformatoelout",22,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"specaccessibilitaout",23,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"specriproducibilitaout",24,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"annotazioniout",25,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgrichabilxvisualizzout",26,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgrichabilxgestout",27,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgrichabilxassegnout",28,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgrichabilxfirmaout",29,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"ciprovdoctypeout",30,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"templatenomeudout",31,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"templatetimbroudout",32,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idprocesstypeout",33,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nomeprocesstypeout",34,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flglockedout",35,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"rowidout",36,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"xmlmodelliout",37,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"xmlattraddxdocdeltipoout",38,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"attributiaddout",39,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"abilitazionipubblout",40,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgrichfirmadigitaleout",41,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"bachsizeout",42,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgmostraaltriattrout",43,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcontextout",44,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcodeout",45,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errmsgout",46,Types.VARCHAR); 
						
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