/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_folder_types.bean.DmpkFolderTypesTrovafoldertypesBean;
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

public class TrovafoldertypesImpl  {
		
	private DmpkFolderTypesTrovafoldertypesBean bean;
		  
	public void setBean(DmpkFolderTypesTrovafoldertypesBean bean){
		this.bean = bean;
	}
	  
	  
	public void execute(Connection connection) throws SQLException {
	    CallableStatement call = null;			
		try{
			//Creo il Callbackstatement
			call = connection.prepareCall("{? = call DMPK_FOLDER_TYPES.TROVAFOLDERTYPES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");			
			SubjectBean subject =  SubjectUtil.subject.get();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.addStatement(subject.getUuidtransaction(), call);
			}
			//Registro i parametri di out
			call.registerOutParameter(1, Types.INTEGER);
			call.registerOutParameter(5, Types.DECIMAL);
			call.registerOutParameter(6, Types.VARCHAR);
			call.registerOutParameter(7, Types.DECIMAL);
			call.registerOutParameter(8, Types.VARCHAR);
			call.registerOutParameter(9, Types.VARCHAR);
			call.registerOutParameter(10, Types.INTEGER);
			call.registerOutParameter(11, Types.VARCHAR);
			call.registerOutParameter(12, Types.INTEGER);
			call.registerOutParameter(13, Types.DECIMAL);
			call.registerOutParameter(14, Types.VARCHAR);
			call.registerOutParameter(15, Types.VARCHAR);
			call.registerOutParameter(16, Types.INTEGER);
			call.registerOutParameter(17, Types.VARCHAR);
			call.registerOutParameter(18, Types.VARCHAR);
			call.registerOutParameter(19, Types.DECIMAL);
			call.registerOutParameter(20, Types.CLOB);
			call.registerOutParameter(21, Types.VARCHAR);
			call.registerOutParameter(22, Types.VARCHAR);
			call.registerOutParameter(24, Types.INTEGER);
			call.registerOutParameter(25, Types.INTEGER);
			call.registerOutParameter(28, Types.INTEGER);
			call.registerOutParameter(29, Types.INTEGER);
			call.registerOutParameter(30, Types.CLOB);
			call.registerOutParameter(31, Types.INTEGER);
			call.registerOutParameter(32, Types.INTEGER);
			call.registerOutParameter(33, Types.INTEGER);
			call.registerOutParameter(34, Types.INTEGER);
			call.registerOutParameter(35, Types.VARCHAR);
			call.registerOutParameter(36, Types.INTEGER);
			call.registerOutParameter(37, Types.VARCHAR);
			
			HibernateStoreUtil util = new HibernateStoreUtil();
			
			BeanWrapperImpl wrapperBean = BeanPropertyUtility.getBeanWrapper(bean);
			
			//Setto i valori della store procedure
			util.settingParameterOnStore(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codidconnectiontokenin",2,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"iduserlavoroin",3,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgpreimpostafiltroin",4,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"idfoldertypeio",5,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"nomeio",6,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"idfoldertypegenio",7,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"nomefoldertypegenio",8,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"ciprovfoldertypeio",9,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"periododiconservazionedaio",10,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codsupportoconservio",11,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgdascansionareio",12,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"idclassificazioneio",13,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"livelliclassificazioneio",14,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"desclassificazioneio",15,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgconfolderizzautoio",16,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codapplicazioneio",17,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codistapplicazioneio",18,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flginclannullatiio",19,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"altricriteriio",20,Types.CLOB,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"colorderbyio",21,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgdescorderbyio",22,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgsenzapaginazionein",23,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"nropaginaio",24,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"bachsizeio",25,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"overflowlimitin",26,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgsenzatotin",27,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgrichabilxvisualizzin",38,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgrichabilxgestin",39,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgrichabilxassegnin",40,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgstatoabilin",41,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgtpdestabilin",42,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"iddestabilin",43,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"opzioniabilin",44,Types.VARCHAR,connection); 	
			
			call.execute();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.removeStatement(subject.getUuidtransaction());
			}
			//Recupero i valori della chiamata
			util.settinParameterOnBean(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idfoldertypeio",5,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nomeio",6,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idfoldertypegenio",7,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nomefoldertypegenio",8,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"ciprovfoldertypeio",9,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"periododiconservazionedaio",10,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codsupportoconservio",11,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgdascansionareio",12,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idclassificazioneio",13,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"livelliclassificazioneio",14,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"desclassificazioneio",15,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgconfolderizzautoio",16,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codapplicazioneio",17,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codistapplicazioneio",18,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flginclannullatiio",19,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"altricriteriio",20,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"colorderbyio",21,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgdescorderbyio",22,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nropaginaio",24,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"bachsizeio",25,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nrototrecout",28,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nrorecinpaginaout",29,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"listaxmlout",30,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgabilinsout",31,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgabilupdout",32,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgabildelout",33,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgmostraaltriattrout",34,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcontextout",35,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcodeout",36,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errmsgout",37,Types.VARCHAR); 
						
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