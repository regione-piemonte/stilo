/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_repository_gui.bean.DmpkRepositoryGuiLoaddettudBean;
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

public class LoaddettudImpl  {
		
	private DmpkRepositoryGuiLoaddettudBean bean;
		  
	public void setBean(DmpkRepositoryGuiLoaddettudBean bean){
		this.bean = bean;
	}
	  
	  
	public void execute(Connection connection) throws SQLException {
	    CallableStatement call = null;			
		try{
			//Creo il Callbackstatement
			call = connection.prepareCall("{? = call DMPK_REPOSITORY_GUI.LOADDETTUD(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");			
			SubjectBean subject =  SubjectUtil.subject.get();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.addStatement(subject.getUuidtransaction(), call);
			}
			//Registro i parametri di out
			call.registerOutParameter(1, Types.INTEGER);
			call.registerOutParameter(4, Types.DECIMAL);
			call.registerOutParameter(5, Types.DECIMAL);
			call.registerOutParameter(6, Types.CLOB);
			call.registerOutParameter(7, Types.VARCHAR);
			call.registerOutParameter(8, Types.DECIMAL);
			call.registerOutParameter(9, Types.VARCHAR);
			call.registerOutParameter(10, Types.INTEGER);
			call.registerOutParameter(11, Types.INTEGER);
			call.registerOutParameter(12, Types.VARCHAR);
			call.registerOutParameter(13, Types.INTEGER);
			call.registerOutParameter(14, Types.INTEGER);
			call.registerOutParameter(15, Types.VARCHAR);
			call.registerOutParameter(16, Types.VARCHAR);
			call.registerOutParameter(17, Types.VARCHAR);
			call.registerOutParameter(18, Types.VARCHAR);
			call.registerOutParameter(19, Types.VARCHAR);
			call.registerOutParameter(20, Types.VARCHAR);
			call.registerOutParameter(21, Types.VARCHAR);
			call.registerOutParameter(22, Types.INTEGER);
			call.registerOutParameter(23, Types.VARCHAR);
			call.registerOutParameter(24, Types.CLOB);
			call.registerOutParameter(25, Types.CLOB);
			call.registerOutParameter(26, Types.CLOB);
			call.registerOutParameter(27, Types.CLOB);
			call.registerOutParameter(28, Types.VARCHAR);
			call.registerOutParameter(29, Types.VARCHAR);
			call.registerOutParameter(30, Types.CLOB);
			call.registerOutParameter(31, Types.VARCHAR);
			call.registerOutParameter(32, Types.VARCHAR);
			call.registerOutParameter(33, Types.VARCHAR);
			call.registerOutParameter(34, Types.VARCHAR);
			call.registerOutParameter(35, Types.VARCHAR);
			call.registerOutParameter(36, Types.DECIMAL);
			call.registerOutParameter(37, Types.INTEGER);
			call.registerOutParameter(38, Types.VARCHAR);
			call.registerOutParameter(39, Types.CLOB);
			call.registerOutParameter(40, Types.INTEGER);
			call.registerOutParameter(41, Types.INTEGER);
			call.registerOutParameter(42, Types.VARCHAR);
			call.registerOutParameter(43, Types.VARCHAR);
			call.registerOutParameter(44, Types.DECIMAL);
			call.registerOutParameter(45, Types.DECIMAL);
			call.registerOutParameter(46, Types.VARCHAR);
			call.registerOutParameter(47, Types.VARCHAR);
			call.registerOutParameter(48, Types.INTEGER);
			call.registerOutParameter(49, Types.VARCHAR);
			
			HibernateStoreUtil util = new HibernateStoreUtil();
			
			BeanWrapperImpl wrapperBean = BeanPropertyUtility.getBeanWrapper(bean);
			
			//Setto i valori della store procedure
			util.settingParameterOnStore(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codidconnectiontokenin",2,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"iduserlavoroin",3,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"idudio",4,Types.DECIMAL,connection); 	
			
			call.execute();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.removeStatement(subject.getUuidtransaction());
			}
			//Recupero i valori della chiamata
			util.settinParameterOnBean(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idudio",4,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"iddocprimarioout",5,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"listaregnumout",6,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"stringaregnumout",7,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idtipodocprimarioout",8,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nometipodocprimarioout",9,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nrolastvisibleverprimarioout",10,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nrolastverprimarioout",11,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"displayfilenameprimarioout",12,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgpubblfileprimarioout",13,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgdadigitfileprimarioout",14,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"notefileprimarioout",15,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"abilitazionifileprimarioout",16,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"desoggettoprimarioout",17,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgacqprodout",18,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"tsarrivostesuraout",19,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgtipoprovout",20,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"mittentiout",21,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgmovimentataout",22,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"estremisoggincaricoaout",23,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"destinatariassegnatariout",24,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"altrisoggettiesterniout",25,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"docnonprimariout",26,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"contenitoriout",27,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"parolechiaveprimarioout",28,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"estensoriout",29,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"listarelazionivsudout",30,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"codstatoout",31,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"desstatoout",32,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"noteudout",33,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"abilitazioniazioniout",34,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"infosulockout",35,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idprocessout",36,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgprocguidatodawfout",37,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"rowidout",38,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"attributiaddout",39,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgmostraaltriattrout",40,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nomeudcreatoinautomaticoout",41,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nomeunitadocout",42,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"statoconsprimarioout",43,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flginvioinconservazioneout",44,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgurgenzainvioinconservout",45,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idinconservazioneout",46,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcontextout",47,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcodeout",48,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errmsgout",49,Types.VARCHAR); 
						
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