/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_anagrafica.bean.DmpkAnagraficaLoaddettdestinatariodocBean;
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

public class LoaddettdestinatariodocImpl  {
		
	private DmpkAnagraficaLoaddettdestinatariodocBean bean;
		  
	public void setBean(DmpkAnagraficaLoaddettdestinatariodocBean bean){
		this.bean = bean;
	}
	  
	  
	public void execute(Connection connection) throws SQLException {
	    CallableStatement call = null;			
		try{
			//Creo il Callbackstatement
			call = connection.prepareCall("{? = call DMPK_ANAGRAFICA.LOADDETTDESTINATARIODOC(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");			
			SubjectBean subject =  SubjectUtil.subject.get();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.addStatement(subject.getUuidtransaction(), call);
			}
			//Registro i parametri di out
			call.registerOutParameter(1, Types.INTEGER);
			call.registerOutParameter(4, Types.DECIMAL);
			call.registerOutParameter(7, Types.DECIMAL);
			call.registerOutParameter(8, Types.DECIMAL);
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
			call.registerOutParameter(28, Types.VARCHAR);
			call.registerOutParameter(29, Types.VARCHAR);
			call.registerOutParameter(30, Types.DECIMAL);
			call.registerOutParameter(31, Types.VARCHAR);
			call.registerOutParameter(32, Types.VARCHAR);
			call.registerOutParameter(33, Types.VARCHAR);
			call.registerOutParameter(34, Types.VARCHAR);
			call.registerOutParameter(35, Types.DECIMAL);
			call.registerOutParameter(36, Types.VARCHAR);
			call.registerOutParameter(37, Types.CLOB);
			call.registerOutParameter(38, Types.CLOB);
			call.registerOutParameter(39, Types.CLOB);
			call.registerOutParameter(40, Types.CLOB);
			call.registerOutParameter(41, Types.CLOB);
			call.registerOutParameter(42, Types.INTEGER);
			call.registerOutParameter(43, Types.VARCHAR);
			call.registerOutParameter(44, Types.VARCHAR);
			call.registerOutParameter(45, Types.VARCHAR);
			call.registerOutParameter(46, Types.VARCHAR);
			call.registerOutParameter(47, Types.VARCHAR);
			call.registerOutParameter(48, Types.VARCHAR);
			call.registerOutParameter(49, Types.DECIMAL);
			call.registerOutParameter(50, Types.VARCHAR);
			call.registerOutParameter(51, Types.VARCHAR);
			call.registerOutParameter(52, Types.VARCHAR);
			call.registerOutParameter(53, Types.VARCHAR);
			call.registerOutParameter(54, Types.CLOB);
			call.registerOutParameter(55, Types.VARCHAR);
			call.registerOutParameter(56, Types.INTEGER);
			call.registerOutParameter(57, Types.VARCHAR);
			call.registerOutParameter(58, Types.INTEGER);
			call.registerOutParameter(59, Types.CLOB);
			call.registerOutParameter(60, Types.CLOB);
			
			HibernateStoreUtil util = new HibernateStoreUtil();
			
			BeanWrapperImpl wrapperBean = BeanPropertyUtility.getBeanWrapper(bean);
			
			//Setto i valori della store procedure
			util.settingParameterOnStore(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codidconnectiontokenin",2,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"iduserlavoroin",3,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"iddestinatarioio",4,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codapplsocietain",5,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codistapplsocietain",6,Types.VARCHAR,connection); 	
			
			call.execute();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.removeStatement(subject.getUuidtransaction());
			}
			//Recupero i valori della chiamata
			util.settinParameterOnBean(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"iddestinatarioio",4,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idsoggrubrout",7,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgpersonafisicaout",8,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"denomcognomeout",9,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nomeout",10,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"cfout",11,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"pivaout",12,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codtiposottotipoout",13,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codcondgiuridicaout",14,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"dtnascitaout",15,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codistatcomunenascout",16,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nomecomunenascout",17,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"targaprovnascout",18,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codistatstatonascout",19,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nomestatonascout",20,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"dtcessazioneout",21,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codcausalecessazioneout",22,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgsexout",23,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codistatstatocittout",24,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nomestatocittout",25,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"titoloout",26,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codindpaout",27,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codammipaout",28,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codaooipaout",29,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idsoggappout",30,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"ciprovsoggout",31,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"ciprovdestdocout",32,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"usernameout",33,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codrapidoout",34,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flglockedout",35,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"rowidout",36,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"indirizziout",37,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"xmlaltredenominazioniout",38,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"xmlcontattiout",39,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"xmlgruppiappout",40,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"attributiaddout",41,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgmostraaltriattrout",42,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"indirizzoemailout",43,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"telefonoout",44,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codclienteout",45,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codcommittenteout",46,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"desclienteout",47,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"cfpivaclienteout",48,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idclienteout",49,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"canaleinvioout",50,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"modalitaregout",51,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"tsregistrazioneout",52,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"linguaout",53,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"utenzeassociateout",54,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcontextout",55,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcodeout",56,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errmsgout",57,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgattivoout",58,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"listadatixsocietaout",59,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"listadaticanalixclusterout",60,Types.CLOB); 
						
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