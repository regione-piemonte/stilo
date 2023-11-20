/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_folder_types.bean.DmpkFolderTypesLoaddettfoldertypeBean;
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

public class LoaddettfoldertypeImpl  {
		
	private DmpkFolderTypesLoaddettfoldertypeBean bean;
		  
	public void setBean(DmpkFolderTypesLoaddettfoldertypeBean bean){
		this.bean = bean;
	}
	  
	  
	public void execute(Connection connection) throws SQLException {
	    CallableStatement call = null;			
		try{
			//Creo il Callbackstatement
			call = connection.prepareCall("{? = call DMPK_FOLDER_TYPES.LOADDETTFOLDERTYPE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");			
			SubjectBean subject =  SubjectUtil.subject.get();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.addStatement(subject.getUuidtransaction(), call);
			}
			//Registro i parametri di out
			call.registerOutParameter(1, Types.INTEGER);
			call.registerOutParameter(4, Types.DECIMAL);
			call.registerOutParameter(5, Types.VARCHAR);
			call.registerOutParameter(6, Types.DECIMAL);
			call.registerOutParameter(7, Types.VARCHAR);
			call.registerOutParameter(8, Types.INTEGER);
			call.registerOutParameter(9, Types.INTEGER);
			call.registerOutParameter(10, Types.DECIMAL);
			call.registerOutParameter(11, Types.VARCHAR);
			call.registerOutParameter(12, Types.DECIMAL);
			call.registerOutParameter(13, Types.VARCHAR);
			call.registerOutParameter(14, Types.VARCHAR);
			call.registerOutParameter(15, Types.VARCHAR);
			call.registerOutParameter(16, Types.INTEGER);
			call.registerOutParameter(17, Types.INTEGER);
			call.registerOutParameter(18, Types.INTEGER);
			call.registerOutParameter(19, Types.VARCHAR);
			call.registerOutParameter(20, Types.VARCHAR);
			call.registerOutParameter(21, Types.VARCHAR);
			call.registerOutParameter(22, Types.VARCHAR);
			call.registerOutParameter(23, Types.DECIMAL);
			call.registerOutParameter(24, Types.VARCHAR);
			call.registerOutParameter(25, Types.INTEGER);
			call.registerOutParameter(26, Types.VARCHAR);
			call.registerOutParameter(27, Types.CLOB);
			call.registerOutParameter(28, Types.CLOB);
			call.registerOutParameter(29, Types.INTEGER);
			call.registerOutParameter(30, Types.INTEGER);
			call.registerOutParameter(31, Types.VARCHAR);
			call.registerOutParameter(32, Types.INTEGER);
			call.registerOutParameter(33, Types.VARCHAR);
			
			HibernateStoreUtil util = new HibernateStoreUtil();
			
			BeanWrapperImpl wrapperBean = BeanPropertyUtility.getBeanWrapper(bean);
			
			//Setto i valori della store procedure
			util.settingParameterOnStore(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codidconnectiontokenin",2,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"iduserlavoroin",3,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"idfoldertypeio",4,Types.DECIMAL,connection); 	
			
			call.execute();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.removeStatement(subject.getUuidtransaction());
			}
			//Recupero i valori della chiamata
			util.settinParameterOnBean(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idfoldertypeio",4,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nomeout",5,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idfoldertypegenout",6,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nomefoldertypegenout",7,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgdascansionareout",8,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgconservpermout",9,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"periodoconservout",10,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codsupportoconservout",11,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idclassificazioneout",12,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"livelliclassificazioneout",13,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"desclassificazioneout",14,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"annotazioniout",15,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgrichabilxvisualizzout",16,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgrichabilxgestout",17,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgrichabilxassegnout",18,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"ciprovfoldertypeout",19,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"templatenomeout",20,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"templatetimbroout",21,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"templatecodeout",22,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idprocesstypeout",23,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nomeprocesstypeout",24,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flglockedout",25,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"rowidout",26,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"xmlattraddxfolderdeltipoout",27,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"attributiaddout",28,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"bachsizeout",29,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgmostraaltriattrout",30,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcontextout",31,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcodeout",32,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errmsgout",33,Types.VARCHAR); 
						
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