/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_attributi_dinamici.bean.DmpkAttributiDinamiciLoaddettattributoBean;
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

public class LoaddettattributoImpl  {
		
	private DmpkAttributiDinamiciLoaddettattributoBean bean;
		  
	public void setBean(DmpkAttributiDinamiciLoaddettattributoBean bean){
		this.bean = bean;
	}
	  
	  
	public void execute(Connection connection) throws SQLException {
	    CallableStatement call = null;			
		try{
			//Creo il Callbackstatement
			call = connection.prepareCall("{? = call DMPK_ATTRIBUTI_DINAMICI.LOADDETTATTRIBUTO(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");			
			SubjectBean subject =  SubjectUtil.subject.get();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.addStatement(subject.getUuidtransaction(), call);
			}
			//Registro i parametri di out
			call.registerOutParameter(1, Types.INTEGER);
			call.registerOutParameter(4, Types.VARCHAR);
			call.registerOutParameter(5, Types.VARCHAR);
			call.registerOutParameter(6, Types.VARCHAR);
			call.registerOutParameter(7, Types.VARCHAR);
			call.registerOutParameter(8, Types.VARCHAR);
			call.registerOutParameter(9, Types.VARCHAR);
			call.registerOutParameter(10, Types.VARCHAR);
			call.registerOutParameter(11, Types.DECIMAL);
			call.registerOutParameter(12, Types.DECIMAL);
			call.registerOutParameter(13, Types.DECIMAL);
			call.registerOutParameter(14, Types.DECIMAL);
			call.registerOutParameter(15, Types.DECIMAL);
			call.registerOutParameter(16, Types.VARCHAR);
			call.registerOutParameter(17, Types.VARCHAR);
			call.registerOutParameter(18, Types.VARCHAR);
			call.registerOutParameter(19, Types.VARCHAR);
			call.registerOutParameter(20, Types.DECIMAL);
			call.registerOutParameter(21, Types.DECIMAL);
			call.registerOutParameter(22, Types.VARCHAR);
			call.registerOutParameter(23, Types.VARCHAR);
			call.registerOutParameter(24, Types.VARCHAR);
			call.registerOutParameter(25, Types.CLOB);
			call.registerOutParameter(26, Types.DECIMAL);
			call.registerOutParameter(27, Types.DECIMAL);
			call.registerOutParameter(28, Types.DECIMAL);
			call.registerOutParameter(29, Types.VARCHAR);
			call.registerOutParameter(30, Types.CLOB);
			call.registerOutParameter(31, Types.CLOB);
			call.registerOutParameter(32, Types.VARCHAR);
			call.registerOutParameter(33, Types.VARCHAR);
			call.registerOutParameter(34, Types.DECIMAL);
			call.registerOutParameter(35, Types.VARCHAR);
			call.registerOutParameter(36, Types.VARCHAR);
			call.registerOutParameter(37, Types.INTEGER);
			call.registerOutParameter(38, Types.VARCHAR);
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
			util.settingParameterOnStore(call,bean,wrapperBean,"nomeio",4,Types.VARCHAR,connection); 	
			
			call.execute();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.removeStatement(subject.getUuidtransaction());
			}
			//Recupero i valori della chiamata
			util.settinParameterOnBean(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nomeio",4,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"labelout",5,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"tipoout",6,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"subtipoout",7,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"descrizioneout",8,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nomeattrappout",9,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"labelattrappout",10,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nroordinattrappout",11,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nrorigainattrappout",12,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgobbliginattrappout",13,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nrocifrecaratteriout",14,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"precisionedecimaleout",15,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"formatonumericoout",16,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"valoreminout",17,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"valoremaxout",18,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"restrizionisulcaseout",19,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"larghguiout",20,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"altezzaguiout",21,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"valoredefaultout",22,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"querypervaloripossibiliout",23,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"urlwsvaloripossibiliout",24,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"xmlinwsvaloripossibiliout",25,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgdaindicizzareout",26,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgprotectedout",27,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgvaloriunivociout",28,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"regularexprout",29,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"xmlvaloripossibiliout",30,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"xmlaclout",31,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nomeidlookupout",32,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"deslookupout",33,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nrocollookupout",34,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"descollookupout",35,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"filtroinlookupout",36,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgsolovaldalookupout",37,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"rowidout",38,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgutilizzatoout",39,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"bachsizeout",40,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgmostraaclout",41,Types.INTEGER); 
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