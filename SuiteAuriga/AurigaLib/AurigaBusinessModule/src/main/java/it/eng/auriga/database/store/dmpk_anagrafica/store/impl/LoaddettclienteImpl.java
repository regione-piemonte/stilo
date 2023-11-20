/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_anagrafica.bean.DmpkAnagraficaLoaddettclienteBean;
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

public class LoaddettclienteImpl  {
		
	private DmpkAnagraficaLoaddettclienteBean bean;
		  
	public void setBean(DmpkAnagraficaLoaddettclienteBean bean){
		this.bean = bean;
	}
	  
	  
	public void execute(Connection connection) throws SQLException {
	    CallableStatement call = null;			
		try{
			//Creo il Callbackstatement
			call = connection.prepareCall("{? = call DMPK_ANAGRAFICA.LOADDETTCLIENTE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");			
			SubjectBean subject =  SubjectUtil.subject.get();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.addStatement(subject.getUuidtransaction(), call);
			}
			//Registro i parametri di out
			call.registerOutParameter(1, Types.INTEGER);
			call.registerOutParameter(4, Types.DECIMAL);
			call.registerOutParameter(5, Types.DECIMAL);
			call.registerOutParameter(6, Types.DECIMAL);
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
			call.registerOutParameter(27, Types.VARCHAR);
			call.registerOutParameter(28, Types.DECIMAL);
			call.registerOutParameter(29, Types.VARCHAR);
			call.registerOutParameter(30, Types.VARCHAR);
			call.registerOutParameter(31, Types.VARCHAR);
			call.registerOutParameter(32, Types.VARCHAR);
			call.registerOutParameter(33, Types.DECIMAL);
			call.registerOutParameter(34, Types.VARCHAR);
			call.registerOutParameter(35, Types.CLOB);
			call.registerOutParameter(36, Types.CLOB);
			call.registerOutParameter(37, Types.CLOB);
			call.registerOutParameter(38, Types.CLOB);
			call.registerOutParameter(39, Types.CLOB);
			call.registerOutParameter(40, Types.CLOB);
			call.registerOutParameter(41, Types.INTEGER);
			call.registerOutParameter(42, Types.VARCHAR);
			call.registerOutParameter(43, Types.INTEGER);
			call.registerOutParameter(44, Types.VARCHAR);
			call.registerOutParameter(45, Types.INTEGER);
			
			HibernateStoreUtil util = new HibernateStoreUtil();
			
			BeanWrapperImpl wrapperBean = BeanPropertyUtility.getBeanWrapper(bean);
			
			//Setto i valori della store procedure
			util.settingParameterOnStore(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codidconnectiontokenin",2,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"iduserlavoroin",3,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"idclienteio",4,Types.DECIMAL,connection); 	
			
			call.execute();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.removeStatement(subject.getUuidtransaction());
			}
			//Recupero i valori della chiamata
			util.settinParameterOnBean(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idclienteio",4,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idsoggrubrout",5,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgpersonafisicaout",6,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"denomcognomeout",7,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nomeout",8,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"cfout",9,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"pivaout",10,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codtiposottotipoout",11,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codcondgiuridicaout",12,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"dtnascitaout",13,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codistatcomunenascout",14,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nomecomunenascout",15,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"targaprovnascout",16,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codistatstatonascout",17,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nomestatonascout",18,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"dtcessazioneout",19,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codcausalecessazioneout",20,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgsexout",21,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codistatstatocittout",22,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nomestatocittout",23,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"titoloout",24,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codindpaout",25,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codammipaout",26,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codaooipaout",27,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idsoggappout",28,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"ciprovsoggout",29,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"ciprovclienteout",30,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"usernameout",31,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codrapidoout",32,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flglockedout",33,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"rowidout",34,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"indirizziout",35,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"xmlaltredenominazioniout",36,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"xmlcontattiout",37,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"xmlgruppiappout",38,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"attributiaddout",39,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"baclientiout",40,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgmostraaltriattrout",41,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcontextout",42,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcodeout",43,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errmsgout",44,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgattivoout",45,Types.INTEGER); 
						
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