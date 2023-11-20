/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_anagrafica.bean.DmpkAnagraficaTrovasoggettiBean;
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

public class TrovasoggettiImpl  {
		
	private DmpkAnagraficaTrovasoggettiBean bean;
		  
	public void setBean(DmpkAnagraficaTrovasoggettiBean bean){
		this.bean = bean;
	}
	  
	  
	public void execute(Connection connection) throws SQLException {
	    CallableStatement call = null;			
		try{
			//Creo il Callbackstatement
			call = connection.prepareCall("{? = call DMPK_ANAGRAFICA.TROVASOGGETTI(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");			
			SubjectBean subject =  SubjectUtil.subject.get();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.addStatement(subject.getUuidtransaction(), call);
			}
			//Registro i parametri di out
			call.registerOutParameter(1, Types.INTEGER);
			call.registerOutParameter(7, Types.VARCHAR);
			call.registerOutParameter(8, Types.INTEGER);
			call.registerOutParameter(9, Types.INTEGER);
			call.registerOutParameter(10, Types.VARCHAR);
			call.registerOutParameter(11, Types.VARCHAR);
			call.registerOutParameter(14, Types.VARCHAR);
			call.registerOutParameter(15, Types.VARCHAR);
			call.registerOutParameter(16, Types.VARCHAR);
			call.registerOutParameter(17, Types.VARCHAR);
			call.registerOutParameter(18, Types.INTEGER);
			call.registerOutParameter(19, Types.VARCHAR);
			call.registerOutParameter(20, Types.VARCHAR);
			call.registerOutParameter(21, Types.VARCHAR);
			call.registerOutParameter(22, Types.INTEGER);
			call.registerOutParameter(23, Types.INTEGER);
			call.registerOutParameter(24, Types.INTEGER);
			call.registerOutParameter(25, Types.DECIMAL);
			call.registerOutParameter(26, Types.INTEGER);
			call.registerOutParameter(27, Types.VARCHAR);
			call.registerOutParameter(28, Types.VARCHAR);
			call.registerOutParameter(29, Types.DECIMAL);
			call.registerOutParameter(30, Types.DECIMAL);
			call.registerOutParameter(31, Types.VARCHAR);
			call.registerOutParameter(33, Types.CLOB);
			call.registerOutParameter(34, Types.VARCHAR);
			call.registerOutParameter(35, Types.VARCHAR);
			call.registerOutParameter(37, Types.INTEGER);
			call.registerOutParameter(38, Types.INTEGER);
			call.registerOutParameter(41, Types.INTEGER);
			call.registerOutParameter(42, Types.INTEGER);
			call.registerOutParameter(45, Types.CLOB);
			call.registerOutParameter(46, Types.INTEGER);
			call.registerOutParameter(47, Types.INTEGER);
			call.registerOutParameter(48, Types.VARCHAR);
			call.registerOutParameter(49, Types.INTEGER);
			call.registerOutParameter(50, Types.VARCHAR);
			
			HibernateStoreUtil util = new HibernateStoreUtil();
			
			BeanWrapperImpl wrapperBean = BeanPropertyUtility.getBeanWrapper(bean);
			
			//Setto i valori della store procedure
			util.settingParameterOnStore(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codidconnectiontokenin",2,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"iduserlavoroin",3,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"matchbyindexerin",4,Types.CLOB,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgindexeroverflowin",5,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flg2ndcallin",6,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"denominazioneio",7,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flginclaltredenomio",8,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgincldenomstoricheio",9,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"cfio",10,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"pivaio",11,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgfisicagiuridicain",12,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgnotcodtipisottotipiin",13,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"listacodtipisottotipiio",14,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"emailio",15,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codrapidoio",16,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"ciprovsoggio",17,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgsolovldio",18,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"tsvldio",19,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codapplownerio",20,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codistapplownerio",21,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgrestrapplownerio",22,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgcertificatiio",23,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flginclannullatiio",24,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"idsoggrubricaio",25,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flginindicepaio",26,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codammipaio",27,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codaooipaio",28,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"issoggrubricaappio",29,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"idgruppoappio",30,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"nomegruppoappio",31,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"strindenominazionein",32,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"criteripersonalizzatiio",33,Types.CLOB,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"colorderbyio",34,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgdescorderbyio",35,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgsenzapaginazionein",36,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"nropaginaio",37,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"bachsizeio",38,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"overflowlimitin",39,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgsenzatotin",40,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgbatchsearchin",43,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"coltoreturnin",44,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"finalitain",51,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codistatcomuneindin",52,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"descittaindin",53,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"restringiarubricadiuoin",54,Types.CLOB,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"filtriavanzatiin",55,Types.CLOB,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codindpain",56,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"rigatagnamein",57,Types.VARCHAR,connection); 	
			
			call.execute();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.removeStatement(subject.getUuidtransaction());
			}
			//Recupero i valori della chiamata
			util.settinParameterOnBean(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"denominazioneio",7,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flginclaltredenomio",8,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgincldenomstoricheio",9,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"cfio",10,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"pivaio",11,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"listacodtipisottotipiio",14,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"emailio",15,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codrapidoio",16,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"ciprovsoggio",17,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgsolovldio",18,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"tsvldio",19,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codapplownerio",20,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codistapplownerio",21,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgrestrapplownerio",22,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgcertificatiio",23,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flginclannullatiio",24,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idsoggrubricaio",25,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flginindicepaio",26,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codammipaio",27,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codaooipaio",28,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"issoggrubricaappio",29,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idgruppoappio",30,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nomegruppoappio",31,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"criteripersonalizzatiio",33,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"colorderbyio",34,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgdescorderbyio",35,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nropaginaio",37,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"bachsizeio",38,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nrototrecout",41,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nrorecinpaginaout",42,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"listaxmlout",45,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgabilinsout",46,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgmostraaltriattrout",47,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcontextout",48,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcodeout",49,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errmsgout",50,Types.VARCHAR); 
						
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