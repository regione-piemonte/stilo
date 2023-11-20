/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_process_types.bean.DmpkProcessTypesLoaddetteventtypeBean;
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

public class LoaddetteventtypeImpl  {
		
	private DmpkProcessTypesLoaddetteventtypeBean bean;
		  
	public void setBean(DmpkProcessTypesLoaddetteventtypeBean bean){
		this.bean = bean;
	}
	  
	  
	public void execute(Connection connection) throws SQLException {
	    CallableStatement call = null;			
		try{
			//Creo il Callbackstatement
			call = connection.prepareCall("{? = call DMPK_PROCESS_TYPES.LOADDETTEVENTTYPE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");			
			SubjectBean subject =  SubjectUtil.subject.get();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.addStatement(subject.getUuidtransaction(), call);
			}
			//Registro i parametri di out
			call.registerOutParameter(1, Types.INTEGER);
			call.registerOutParameter(4, Types.DECIMAL);
			call.registerOutParameter(5, Types.VARCHAR);
			call.registerOutParameter(6, Types.VARCHAR);
			call.registerOutParameter(7, Types.INTEGER);
			call.registerOutParameter(8, Types.DECIMAL);
			call.registerOutParameter(9, Types.INTEGER);
			call.registerOutParameter(10, Types.DECIMAL);
			call.registerOutParameter(11, Types.VARCHAR);
			call.registerOutParameter(12, Types.VARCHAR);
			call.registerOutParameter(13, Types.VARCHAR);
			call.registerOutParameter(14, Types.VARCHAR);
			call.registerOutParameter(15, Types.INTEGER);
			call.registerOutParameter(16, Types.VARCHAR);
			call.registerOutParameter(17, Types.CLOB);
			call.registerOutParameter(18, Types.CLOB);
			call.registerOutParameter(19, Types.CLOB);
			call.registerOutParameter(20, Types.INTEGER);
			call.registerOutParameter(21, Types.INTEGER);
			call.registerOutParameter(22, Types.VARCHAR);
			call.registerOutParameter(23, Types.INTEGER);
			call.registerOutParameter(24, Types.VARCHAR);
			
			HibernateStoreUtil util = new HibernateStoreUtil();
			
			BeanWrapperImpl wrapperBean = BeanPropertyUtility.getBeanWrapper(bean);
			
			//Setto i valori della store procedure
			util.settingParameterOnStore(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codidconnectiontokenin",2,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"iduserlavoroin",3,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"ideventtypeio",4,Types.DECIMAL,connection); 	
			
			call.execute();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.removeStatement(subject.getUuidtransaction());
			}
			//Recupero i valori della chiamata
			util.settinParameterOnBean(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"ideventtypeio",4,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"deseventout",5,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"categoriaout",6,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgdurativoout",7,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"duratamaxout",8,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgvldxtuttiprocammout",9,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"iddoctypeout",10,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nomedoctypeout",11,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codtipodatareldocout",12,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"annotazioniout",13,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"ciproveventtypeout",14,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flglockedout",15,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"rowidout",16,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"xmlstoredfuncout",17,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"xmlattraddxevtdeltipoout",18,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"attributiaddout",19,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"bachsizeout",20,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgmostraaltriattrout",21,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcontextout",22,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcodeout",23,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errmsgout",24,Types.VARCHAR); 
						
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