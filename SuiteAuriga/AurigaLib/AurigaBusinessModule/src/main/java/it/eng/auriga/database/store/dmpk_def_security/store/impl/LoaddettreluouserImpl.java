/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecurityLoaddettreluouserBean;
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

public class LoaddettreluouserImpl  {
		
	private DmpkDefSecurityLoaddettreluouserBean bean;
		  
	public void setBean(DmpkDefSecurityLoaddettreluouserBean bean){
		this.bean = bean;
	}
	  
	  
	public void execute(Connection connection) throws SQLException {
	    CallableStatement call = null;			
		try{
			//Creo il Callbackstatement
			call = connection.prepareCall("{? = call DMPK_DEF_SECURITY.LOADDETTRELUOUSER(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");			
			SubjectBean subject =  SubjectUtil.subject.get();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.addStatement(subject.getUuidtransaction(), call);
			}
			//Registro i parametri di out
			call.registerOutParameter(1, Types.INTEGER);
			call.registerOutParameter(4, Types.DECIMAL);
			call.registerOutParameter(5, Types.VARCHAR);
			call.registerOutParameter(6, Types.VARCHAR);
			call.registerOutParameter(7, Types.DECIMAL);
			call.registerOutParameter(8, Types.VARCHAR);
			call.registerOutParameter(9, Types.VARCHAR);
			call.registerOutParameter(10, Types.VARCHAR);
			call.registerOutParameter(11, Types.VARCHAR);
			call.registerOutParameter(12, Types.VARCHAR);
			call.registerOutParameter(13, Types.INTEGER);
			call.registerOutParameter(14, Types.INTEGER);
			call.registerOutParameter(15, Types.DECIMAL);
			call.registerOutParameter(16, Types.VARCHAR);
			call.registerOutParameter(17, Types.DECIMAL);
			call.registerOutParameter(18, Types.VARCHAR);
			call.registerOutParameter(19, Types.INTEGER);
			call.registerOutParameter(20, Types.DECIMAL);
			call.registerOutParameter(21, Types.VARCHAR);
			call.registerOutParameter(22, Types.CLOB);
			call.registerOutParameter(23, Types.CLOB);
			call.registerOutParameter(24, Types.CLOB);
			call.registerOutParameter(25, Types.INTEGER);
			call.registerOutParameter(26, Types.CLOB);
			call.registerOutParameter(27, Types.VARCHAR);
			call.registerOutParameter(28, Types.CLOB);
			call.registerOutParameter(29, Types.INTEGER);
			call.registerOutParameter(30, Types.INTEGER);
			call.registerOutParameter(31, Types.INTEGER);
			call.registerOutParameter(32, Types.VARCHAR);
			call.registerOutParameter(33, Types.DECIMAL);
			call.registerOutParameter(34, Types.VARCHAR);
			call.registerOutParameter(35, Types.VARCHAR);
			call.registerOutParameter(36, Types.VARCHAR);
			call.registerOutParameter(37, Types.VARCHAR);
			call.registerOutParameter(38, Types.VARCHAR);
			call.registerOutParameter(39, Types.DECIMAL);
			call.registerOutParameter(40, Types.VARCHAR);
			call.registerOutParameter(41, Types.DECIMAL);
			call.registerOutParameter(42, Types.VARCHAR);
			call.registerOutParameter(43, Types.INTEGER);
			call.registerOutParameter(44, Types.INTEGER);
			call.registerOutParameter(45, Types.INTEGER);
			call.registerOutParameter(46, Types.INTEGER);
			call.registerOutParameter(47, Types.INTEGER);
			call.registerOutParameter(48, Types.INTEGER);
			call.registerOutParameter(49, Types.VARCHAR);
			call.registerOutParameter(50, Types.INTEGER);
			call.registerOutParameter(51, Types.VARCHAR);
			call.registerOutParameter(52, Types.VARCHAR);
			call.registerOutParameter(53, Types.INTEGER);
			call.registerOutParameter(54, Types.VARCHAR);
			
			HibernateStoreUtil util = new HibernateStoreUtil();
			
			BeanWrapperImpl wrapperBean = BeanPropertyUtility.getBeanWrapper(bean);
			
			//Setto i valori della store procedure
			util.settingParameterOnStore(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"codidconnectiontokenin",2,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"iduserlavoroin",3,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"iduoio",4,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"nrilivelliuoio",5,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"denominazioneuoio",6,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"iduserio",7,Types.DECIMAL,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"desuserio",8,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"usernameio",9,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"flgtiporelio",10,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"dtiniziovldio",11,Types.VARCHAR,connection); 	
			util.settingParameterOnStore(call,bean,wrapperBean,"idruoloammio",17,Types.DECIMAL,connection); 	
			
			call.execute();
			if (StringUtils.isNotEmpty(subject.getUuidtransaction())){
				HibernateUtil.removeStatement(subject.getUuidtransaction());
			}
			//Recupero i valori della chiamata
			util.settinParameterOnBean(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"iduoio",4,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nrilivelliuoio",5,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"denominazioneuoio",6,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"iduserio",7,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"desuserio",8,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"usernameio",9,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgtiporelio",10,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"dtiniziovldio",11,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"dtfinevldout",12,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flginclsottouoout",13,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flginclscrivvirtout",14,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idscrivaniaout",15,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"intestazionescrivaniaout",16,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idruoloammio",17,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"desruoloammout",18,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgprimarioconruoloout",19,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"idprofiloout",20,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nomeprofiloout",21,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"xmldatiprofiloout",22,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"xmlgruppiprivout",23,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"xmlgruppiappout",24,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flguoppout",25,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"xmluoppout",26,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"rowidout",27,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"attributiaddout",28,Types.CLOB); 
			util.settinParameterOnBean(call,bean,wrapperBean,"bachsizeout",29,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgmostraaltriattrout",30,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgpresentidocfascout",31,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgtipodestdocout",32,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"iduosvdestdocfascout",33,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"livelliuodestdocfascout",34,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"desuodestdocfascout",35,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"statospostdocfascout",36,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"tsiniziospostdocfascout",37,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"tsfinespostdocfascout",38,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nrodocassout",39,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"tsrilevnrodocassout",40,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"nrofascassout",41,Types.DECIMAL); 
			util.settinParameterOnBean(call,bean,wrapperBean,"tsrilevnrofascassout",42,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgaccessodoclimsvout",43,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgregistrazioneeout",44,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgregistrazioneuiout",45,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flggestattiout",46,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgvispropattiiniterout",47,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flggestattiallout",48,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"listaidproctygestattiout",49,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"flgvispropattiallout",50,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"listaidproctyvispropattiout",51,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcontextout",52,Types.VARCHAR); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errcodeout",53,Types.INTEGER); 
			util.settinParameterOnBean(call,bean,wrapperBean,"errmsgout",54,Types.VARCHAR); 
						
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