/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_tipi_reg_num_ud.bean.DmpkTipiRegNumUdLoaddetttiporegnumBean;
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

public class LoaddetttiporegnumImpl  {
		
	private DmpkTipiRegNumUdLoaddetttiporegnumBean bean;
		  
	public void setBean(DmpkTipiRegNumUdLoaddetttiporegnumBean bean){
		this.bean = bean;
	}
	  
	  
	public void execute(Connection connection) throws SQLException {
	    CallableStatement call = null;			
		try{
			//Creo il Callbackstatement
			call = connection.prepareCall("{? = call DMPK_TIPI_REG_NUM_UD.LOADDETTTIPOREGNUM(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");			
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
			call.registerOutParameter(8, Types.VARCHAR);
			call.registerOutParameter(9, Types.VARCHAR);
			call.registerOutParameter(10, Types.INTEGER);
			call.registerOutParameter(11, Types.VARCHAR);
			call.registerOutParameter(12, Types.VARCHAR);
			call.registerOutParameter(13, Types.INTEGER);
			call.registerOutParameter(14, Types.INTEGER);
			call.registerOutParameter(15, Types.INTEGER);
			call.registerOutParameter(16, Types.INTEGER);
			call.registerOutParameter(17, Types.INTEGER);
			call.registerOutParameter(18, Types.INTEGER);
			call.registerOutParameter(19, Types.VARCHAR);
			call.registerOutParameter(20, Types.INTEGER);
			call.registerOutParameter(21, Types.INTEGER);
			call.registerOutParameter(22, Types.VARCHAR);
			call.registerOutParameter(23, Types.VARCHAR);
			call.registerOutParameter(24, Types.DECIMAL);
			call.registerOutParameter(25, Types.DECIMAL);
			call.registerOutParameter(26, Types.DECIMAL);
			call.registerOutParameter(27, Types.VARCHAR);
			call.registerOutParameter(28, Types.VARCHAR);
			call.registerOutParameter(29, Types.VARCHAR);
			call.registerOutParameter(30, Types.CLOB);
			call.registerOutParameter(31, Types.CLOB);
			call.registerOutParameter(32, Types.CLOB);
			call.registerOutParameter(33, Types.INTEGER);
			call.registerOutParameter(34, Types.VARCHAR);
			call.registerOutParameter(35, Types.INTEGER);
			call.registerOutParameter(36, Types.VARCHAR);
			
			HibernateStoreUtil util = new HibernateStoreUtil();
			
			BeanWrapperImpl wrapperBean = BeanPropertyUtility.getBeanWrapper(bean);
			
			//Setto i valori della store procedure
			util.settingParameterOnStore(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codidconnectiontokenin",2,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"iduserlavoroin",3,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"idtiporennumio",4,Types.DECIMAL,connection); 	
			
			call.execute();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.removeStatement(subject.getUuidtransaction());
			}
			//Recupero i valori della chiamata
			util.settinParameterOnBean(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idtiporennumio",4,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codcategoriaout",5,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"siglaout",6,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"descrizioneout",7,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"dtiniziovldout",8,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"dtfinevldout",9,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flginternaout",10,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgstatoregistroout",11,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"tsiniziostatoregistroout",12,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"periodicitadianniout",13,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"annoinizionumerazioneout",14,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgrichabilxvisualizzout",15,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgrichabilxgestout",16,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgrichabilxassegnout",17,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgrichabilxfirmaout",18,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"ciprovtiporegnumout",19,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flglockedout",20,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgbuchiammessiout",21,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"gruppoappout",22,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"restrizioniversoregout",23,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgctrabiluomittout",24,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nroinizialeout",25,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"ultimonrogenearatoout",26,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"dataultnrogeneratoout",27,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"rowidout",28,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgammescxtipidocout",29,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"xmltipidocammescout",30,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"xmlattraddxdocdeltipoout",31,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"attributiaddout",32,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgmostraaltriattrout",33,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcontextout",34,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcodeout",35,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errmsgout",36,Types.VARCHAR); 
						
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