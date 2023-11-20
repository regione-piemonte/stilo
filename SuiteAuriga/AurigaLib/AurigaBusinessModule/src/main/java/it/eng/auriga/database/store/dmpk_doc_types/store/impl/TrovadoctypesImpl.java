/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_doc_types.bean.DmpkDocTypesTrovadoctypesBean;
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

public class TrovadoctypesImpl  {
		
	private DmpkDocTypesTrovadoctypesBean bean;
		  
	public void setBean(DmpkDocTypesTrovadoctypesBean bean){
		this.bean = bean;
	}
	  
	  
	public void execute(Connection connection) throws SQLException {
	    CallableStatement call = null;			
		try{
			//Creo il Callbackstatement
			call = connection.prepareCall("{? = call DMPK_DOC_TYPES.TROVADOCTYPES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");			
			SubjectBean subject =  SubjectUtil.subject.get();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.addStatement(subject.getUuidtransaction(), call);
			}
			//Registro i parametri di out
			call.registerOutParameter(1, Types.INTEGER);
			call.registerOutParameter(5, Types.DECIMAL);
			call.registerOutParameter(6, Types.VARCHAR);
			call.registerOutParameter(7, Types.VARCHAR);
			call.registerOutParameter(8, Types.DECIMAL);
			call.registerOutParameter(9, Types.VARCHAR);
			call.registerOutParameter(10, Types.INTEGER);
			call.registerOutParameter(11, Types.VARCHAR);
			call.registerOutParameter(12, Types.VARCHAR);
			call.registerOutParameter(13, Types.VARCHAR);
			call.registerOutParameter(14, Types.VARCHAR);
			call.registerOutParameter(15, Types.INTEGER);
			call.registerOutParameter(16, Types.VARCHAR);
			call.registerOutParameter(17, Types.INTEGER);
			call.registerOutParameter(18, Types.DECIMAL);
			call.registerOutParameter(19, Types.VARCHAR);
			call.registerOutParameter(20, Types.VARCHAR);
			call.registerOutParameter(21, Types.DECIMAL);
			call.registerOutParameter(22, Types.VARCHAR);
			call.registerOutParameter(23, Types.VARCHAR);
			call.registerOutParameter(24, Types.INTEGER);
			call.registerOutParameter(25, Types.INTEGER);
			call.registerOutParameter(26, Types.DECIMAL);
			call.registerOutParameter(27, Types.CLOB);
			call.registerOutParameter(28, Types.VARCHAR);
			call.registerOutParameter(29, Types.VARCHAR);
			call.registerOutParameter(31, Types.INTEGER);
			call.registerOutParameter(32, Types.INTEGER);
			call.registerOutParameter(35, Types.INTEGER);
			call.registerOutParameter(36, Types.INTEGER);
			call.registerOutParameter(37, Types.CLOB);
			call.registerOutParameter(38, Types.INTEGER);
			call.registerOutParameter(39, Types.INTEGER);
			call.registerOutParameter(40, Types.INTEGER);
			call.registerOutParameter(41, Types.INTEGER);
			call.registerOutParameter(42, Types.VARCHAR);
			call.registerOutParameter(43, Types.INTEGER);
			call.registerOutParameter(44, Types.VARCHAR);
			
			HibernateStoreUtil util = new HibernateStoreUtil();
			
			BeanWrapperImpl wrapperBean = BeanPropertyUtility.getBeanWrapper(bean);
			
			//Setto i valori della store procedure
			util.settingParameterOnStore(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codidconnectiontokenin",2,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"iduserlavoroin",3,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgpreimpostafiltroin",4,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"iddoctypeio",5,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"nomeio",6,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"descrizioneio",7,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"iddoctypegenio",8,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"nomedoctypegenio",9,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgallegatoio",10,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"ciprovdoctypeio",11,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codnaturaio",12,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgtipoprovio",13,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codsupportoorigio",14,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"periododiconservazionedaio",15,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codsupportoconservio",16,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgdascansionareio",17,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"idclassificazioneio",18,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"livelliclassificazioneio",19,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"desclassificazioneio",20,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"idformatoelio",21,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"nomeformatoelio",22,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"estensioneformatoelio",23,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgconfolderizzautoio",24,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgconmodelloio",25,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flginclannullatiio",26,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"altricriteriio",27,Types.CLOB,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"colorderbyio",28,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgdescorderbyio",29,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgsenzapaginazionein",30,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"nropaginaio",31,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"bachsizeio",32,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"overflowlimitin",33,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgsenzatotin",34,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgrichabilxvisualizzin",45,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgrichabilxgestin",46,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgrichabilxassegnin",47,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgrichabilxfirmain",48,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgstatoabilin",49,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgtpdestabilin",50,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"iddestabilin",51,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"opzioniabilin",52,Types.VARCHAR,connection); 	
			
			call.execute();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.removeStatement(subject.getUuidtransaction());
			}
			//Recupero i valori della chiamata
			util.settinParameterOnBean(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"iddoctypeio",5,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nomeio",6,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"descrizioneio",7,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"iddoctypegenio",8,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nomedoctypegenio",9,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgallegatoio",10,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"ciprovdoctypeio",11,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codnaturaio",12,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgtipoprovio",13,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codsupportoorigio",14,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"periododiconservazionedaio",15,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codsupportoconservio",16,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgdascansionareio",17,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idclassificazioneio",18,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"livelliclassificazioneio",19,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"desclassificazioneio",20,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idformatoelio",21,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nomeformatoelio",22,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"estensioneformatoelio",23,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgconfolderizzautoio",24,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgconmodelloio",25,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flginclannullatiio",26,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"altricriteriio",27,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"colorderbyio",28,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgdescorderbyio",29,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nropaginaio",31,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"bachsizeio",32,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nrototrecout",35,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nrorecinpaginaout",36,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"listaxmlout",37,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgabilinsout",38,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgabilupdout",39,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgabildelout",40,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgmostraaltriattrout",41,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcontextout",42,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcodeout",43,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errmsgout",44,Types.VARCHAR); 
						
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