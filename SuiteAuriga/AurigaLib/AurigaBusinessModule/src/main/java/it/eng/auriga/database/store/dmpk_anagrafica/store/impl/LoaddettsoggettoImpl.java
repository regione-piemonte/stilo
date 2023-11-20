/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_anagrafica.bean.DmpkAnagraficaLoaddettsoggettoBean;
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

public class LoaddettsoggettoImpl  {
		
	private DmpkAnagraficaLoaddettsoggettoBean bean;
		  
	public void setBean(DmpkAnagraficaLoaddettsoggettoBean bean){
		this.bean = bean;
	}
	  
	  
	public void execute(Connection connection) throws SQLException {
	    CallableStatement call = null;			
		try{
			//Creo il Callbackstatement
			call = connection.prepareCall("{? = call DMPK_ANAGRAFICA.LOADDETTSOGGETTO(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");			
			SubjectBean subject =  SubjectUtil.subject.get();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.addStatement(subject.getUuidtransaction(), call);
			}
			//Registro i parametri di out
			call.registerOutParameter(1, Types.INTEGER);
			call.registerOutParameter(4, Types.DECIMAL);
			call.registerOutParameter(5, Types.DECIMAL);
			call.registerOutParameter(6, Types.VARCHAR);
			call.registerOutParameter(7, Types.VARCHAR);
			call.registerOutParameter(8, Types.VARCHAR);
			call.registerOutParameter(9, Types.VARCHAR);
			call.registerOutParameter(10, Types.VARCHAR);
			call.registerOutParameter(11, Types.VARCHAR);
			call.registerOutParameter(12, Types.VARCHAR);
			call.registerOutParameter(13, Types.VARCHAR);
			call.registerOutParameter(14, Types.VARCHAR);
			call.registerOutParameter(15, Types.VARCHAR);
			call.registerOutParameter(16, Types.VARCHAR);
			call.registerOutParameter(17, Types.VARCHAR);
			call.registerOutParameter(18, Types.VARCHAR);
			call.registerOutParameter(19, Types.VARCHAR);
			call.registerOutParameter(20, Types.VARCHAR);
			call.registerOutParameter(21, Types.VARCHAR);
			call.registerOutParameter(22, Types.VARCHAR);
			call.registerOutParameter(23, Types.VARCHAR);
			call.registerOutParameter(24, Types.VARCHAR);
			call.registerOutParameter(25, Types.VARCHAR);
			call.registerOutParameter(26, Types.VARCHAR);
			call.registerOutParameter(27, Types.DECIMAL);
			call.registerOutParameter(28, Types.VARCHAR);
			call.registerOutParameter(29, Types.VARCHAR);
			call.registerOutParameter(30, Types.VARCHAR);
			call.registerOutParameter(31, Types.DECIMAL);
			call.registerOutParameter(32, Types.VARCHAR);
			call.registerOutParameter(33, Types.CLOB);
			call.registerOutParameter(34, Types.CLOB);
			call.registerOutParameter(35, Types.CLOB);
			call.registerOutParameter(36, Types.CLOB);
			call.registerOutParameter(37, Types.CLOB);
			call.registerOutParameter(38, Types.INTEGER);
			call.registerOutParameter(39, Types.VARCHAR);
			call.registerOutParameter(40, Types.INTEGER);
			call.registerOutParameter(41, Types.VARCHAR);
			call.registerOutParameter(43, Types.INTEGER);
			call.registerOutParameter(44, Types.INTEGER);
			call.registerOutParameter(45, Types.DECIMAL);
			call.registerOutParameter(46, Types.VARCHAR);
			call.registerOutParameter(47, Types.VARCHAR);
			call.registerOutParameter(48, Types.INTEGER);
			call.registerOutParameter(49, Types.INTEGER);
			
			HibernateStoreUtil util = new HibernateStoreUtil();
			
			BeanWrapperImpl wrapperBean = BeanPropertyUtility.getBeanWrapper(bean);
			
			//Setto i valori della store procedure
			util.settingParameterOnStore(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codidconnectiontokenin",2,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"iduserlavoroin",3,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"idsoggrubrio",4,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"finalitain",42,Types.VARCHAR,connection); 	
			
			call.execute();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.removeStatement(subject.getUuidtransaction());
			}
			//Recupero i valori della chiamata
			util.settinParameterOnBean(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idsoggrubrio",4,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgpersonafisicaout",5,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"denomcognomeout",6,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nomeout",7,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"cfout",8,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"pivaout",9,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codtiposottotipoout",10,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codcondgiuridicaout",11,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"dtnascitaout",12,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codistatcomunenascout",13,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nomecomunenascout",14,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"targaprovnascout",15,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codistatstatonascout",16,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nomestatonascout",17,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"dtcessazioneout",18,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codcausalecessazioneout",19,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgsexout",20,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codistatstatocittout",21,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nomestatocittout",22,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"titoloout",23,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codindpaout",24,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codammipaout",25,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codaooipaout",26,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idsoggappout",27,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"ciprovsoggout",28,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"usernameout",29,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codrapidoout",30,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flglockedout",31,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"rowidout",32,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"indirizziout",33,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"xmlaltredenominazioniout",34,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"xmlcontattiout",35,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"xmlgruppiappout",36,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"attributiaddout",37,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgmostraaltriattrout",38,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcontextout",39,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcodeout",40,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errmsgout",41,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgselezionabileout",43,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgattivoout",44,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"iduoout",45,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"livelliuoout",46,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"desuoout",47,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgvisibsottouoout",48,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flggestsottouoout",49,Types.INTEGER); 
						
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