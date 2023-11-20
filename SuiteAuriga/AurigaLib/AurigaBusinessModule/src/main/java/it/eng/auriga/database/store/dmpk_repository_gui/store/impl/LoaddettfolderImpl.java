/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_repository_gui.bean.DmpkRepositoryGuiLoaddettfolderBean;
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

public class LoaddettfolderImpl  {
		
	private DmpkRepositoryGuiLoaddettfolderBean bean;
		  
	public void setBean(DmpkRepositoryGuiLoaddettfolderBean bean){
		this.bean = bean;
	}
	  
	  
	public void execute(Connection connection) throws SQLException {
	    CallableStatement call = null;			
		try{
			//Creo il Callbackstatement
			call = connection.prepareCall("{? = call DMPK_REPOSITORY_GUI.LOADDETTFOLDER(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");			
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
			call.registerOutParameter(8, Types.DECIMAL);
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
			call.registerOutParameter(20, Types.INTEGER);
			call.registerOutParameter(21, Types.VARCHAR);
			call.registerOutParameter(22, Types.CLOB);
			call.registerOutParameter(23, Types.CLOB);
			call.registerOutParameter(24, Types.CLOB);
			call.registerOutParameter(25, Types.VARCHAR);
			call.registerOutParameter(26, Types.VARCHAR);
			call.registerOutParameter(27, Types.VARCHAR);
			call.registerOutParameter(28, Types.VARCHAR);
			call.registerOutParameter(29, Types.VARCHAR);
			call.registerOutParameter(30, Types.VARCHAR);
			call.registerOutParameter(31, Types.DECIMAL);
			call.registerOutParameter(32, Types.INTEGER);
			call.registerOutParameter(33, Types.VARCHAR);
			call.registerOutParameter(34, Types.CLOB);
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
			util.settingParameterOnStore(call,bean,wrapperBean,"idfolderio",4,Types.DECIMAL,connection); 	
			
			call.execute();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.removeStatement(subject.getUuidtransaction());
			}
			//Recupero i valori della chiamata
			util.settinParameterOnBean(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idfolderio",4,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgparticolaritafolderout",5,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nomefolderout",6,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"dtaperturaout",7,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idclassificazioneout",8,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"livelliclassificazioneout",9,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"desclassificazioneout",10,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"annoaperturaout",11,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nroprogrfascout",12,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nroprogrsottofascout",13,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nroprogrinsertoout",14,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idtipofolderout",15,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nometipofolderout",16,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nrosecondarioout",17,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"oggettoout",18,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"dtchiusuraout",19,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgmovimentatoout",20,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"estremisoggincaricoaout",21,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"soggettiesterniout",22,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"contenitoriout",23,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"listarelazionivsfolderout",24,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"parolechiaveout",25,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codstatoout",26,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"desstatoout",27,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"noteout",28,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"abilitazioniazioniout",29,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"infosulockout",30,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idprocessout",31,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgprocguidatodawfout",32,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"rowidout",33,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"attributiaddout",34,Types.CLOB); 
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