/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_processes.bean.DmpkProcessesLoaddetteventocustomBean;
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

public class LoaddetteventocustomImpl  {
		
	private DmpkProcessesLoaddetteventocustomBean bean;
		  
	public void setBean(DmpkProcessesLoaddetteventocustomBean bean){
		this.bean = bean;
	}
	  
	  
	public void execute(Connection connection) throws SQLException {
	    CallableStatement call = null;			
		try{
			//Creo il Callbackstatement
			call = connection.prepareCall("{? = call DMPK_PROCESSES.LOADDETTEVENTOCUSTOM(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");			
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
			call.registerOutParameter(8, Types.DECIMAL);
			call.registerOutParameter(9, Types.VARCHAR);
			call.registerOutParameter(10, Types.VARCHAR);
			call.registerOutParameter(11, Types.VARCHAR);
			call.registerOutParameter(12, Types.DECIMAL);
			call.registerOutParameter(13, Types.VARCHAR);
			call.registerOutParameter(14, Types.DECIMAL);
			call.registerOutParameter(15, Types.VARCHAR);
			call.registerOutParameter(16, Types.DECIMAL);
			call.registerOutParameter(17, Types.VARCHAR);
			call.registerOutParameter(18, Types.VARCHAR);
			call.registerOutParameter(19, Types.DECIMAL);
			call.registerOutParameter(20, Types.VARCHAR);
			call.registerOutParameter(21, Types.DECIMAL);
			call.registerOutParameter(22, Types.VARCHAR);
			call.registerOutParameter(23, Types.DECIMAL);
			call.registerOutParameter(24, Types.VARCHAR);
			call.registerOutParameter(25, Types.CLOB);
			call.registerOutParameter(26, Types.VARCHAR);
			call.registerOutParameter(27, Types.VARCHAR);
			call.registerOutParameter(28, Types.DECIMAL);
			call.registerOutParameter(29, Types.DECIMAL);
			call.registerOutParameter(30, Types.INTEGER);
			call.registerOutParameter(31, Types.VARCHAR);
			call.registerOutParameter(32, Types.VARCHAR);
			call.registerOutParameter(33, Types.DECIMAL);
			call.registerOutParameter(34, Types.DECIMAL);
			call.registerOutParameter(35, Types.VARCHAR);
			call.registerOutParameter(36, Types.CLOB);
			call.registerOutParameter(37, Types.INTEGER);
			call.registerOutParameter(38, Types.INTEGER);
			call.registerOutParameter(39, Types.VARCHAR);
			call.registerOutParameter(40, Types.INTEGER);
			call.registerOutParameter(41, Types.VARCHAR);
			
			HibernateStoreUtil util = new HibernateStoreUtil();
			
			BeanWrapperImpl wrapperBean = BeanPropertyUtility.getBeanWrapper(bean);
			
			//Setto i valori della store procedure
			util.settingParameterOnStore(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codidconnectiontokenin",2,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"iduserlavoroin",3,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"ideventoio",4,Types.DECIMAL,connection); 	
			
			call.execute();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.removeStatement(subject.getUuidtransaction());
			}
			//Recupero i valori della chiamata
			util.settinParameterOnBean(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"ideventoio",4,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idprocessout",5,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"desprocesstypeout",6,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"ciprovprocessout",7,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idtipoeventoout",8,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"destipoeventoout",9,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"deseventoout",10,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"tsinizioeventoout",11,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"iduserinianomediout",12,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"desuserinianomediout",13,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"iduserinidaout",14,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"desuserinidaout",15,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"ideventoinidaout",16,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"estremieventoinidaout",17,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"tscompleventoout",18,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idusercomplanomediout",19,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"desusercomplanomediout",20,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idusercompldaout",21,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"desusercompldaout",22,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"ideventoenddaout",23,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"estremieventoenddaout",24,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"eventiderivatiout",25,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"desesitoout",26,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"messaggioout",27,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idudassociataout",28,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"iddocassociatoout",29,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nrolastverdocassout",30,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"displayfilenameout",31,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"estremiudassociataout",32,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"iddoctypeassociatoout",33,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"duratamaxeventoout",34,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"rowidout",35,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"attributiaddout",36,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgmostraaltriattrout",37,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"bachsizeout",38,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcontextout",39,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcodeout",40,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errmsgout",41,Types.VARCHAR); 
						
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