/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.sinadoc.database.store.dmpk_load_combo_sinapoli.bean.DmpkLoadComboSinapoliDmfn_load_combo_sinapoliBean;
import it.eng.storeutil.HibernateStoreUtil;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import oracle.jdbc.OracleTypes;

import org.springframework.beans.BeanWrapperImpl;
import it.eng.utility.springBeanWrapper.BeanPropertyUtility;

/**
 * @author Procedure Wrapper 1.0
 * Classe generata per la chiamata alla store procedure 
 */

public class Dmfn_load_combo_sinapoliImpl  {
		
	private DmpkLoadComboSinapoliDmfn_load_combo_sinapoliBean bean;
		  
	public void setBean(DmpkLoadComboSinapoliDmfn_load_combo_sinapoliBean bean){
		this.bean = bean;
	}
	  
	  
	public void execute(Connection connection) throws SQLException {
	    try{
			//Creo il Callbackstatement
			CallableStatement call = connection.prepareCall("{? = call DMPK_LOAD_COMBO_SINAPOLI.DMFN_LOAD_COMBO_SINAPOLI(?,?,?,?,?,?,?,?,?,?)}");			
			
			//Registro i parametri di out
									call.registerOutParameter(1, Types.INTEGER);
																																																call.registerOutParameter(8, Types.CLOB);
												call.registerOutParameter(9, Types.VARCHAR);
												call.registerOutParameter(10, Types.INTEGER);
												call.registerOutParameter(11, Types.VARCHAR);
									
			
			HibernateStoreUtil util = new HibernateStoreUtil();
			
			BeanWrapperImpl wrapperBean = BeanPropertyUtility.getBeanWrapper(bean);
			
			//Setto i valori della store procedure
									util.settingParameterOnStore(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER,connection); 	
												util.settingParameterOnStore(call,bean,wrapperBean,"codidconnectiontokenin",2,Types.VARCHAR,connection); 	
												util.settingParameterOnStore(call,bean,wrapperBean,"tipocomboin",3,Types.VARCHAR,connection); 	
												util.settingParameterOnStore(call,bean,wrapperBean,"flgsolovldin",4,Types.DECIMAL,connection); 	
												util.settingParameterOnStore(call,bean,wrapperBean,"tsvldin",5,Types.VARCHAR,connection); 	
												util.settingParameterOnStore(call,bean,wrapperBean,"pkrecin",6,Types.VARCHAR,connection); 	
												util.settingParameterOnStore(call,bean,wrapperBean,"altriparametriin",7,Types.VARCHAR,connection); 	
																																	
			call.execute();
			
			//Recupero i valori della chiamata
									util.settinParameterOnBean(call,bean,wrapperBean,"parametro_1",1,Types.INTEGER); 
																																																util.settinParameterOnBean(call,bean,wrapperBean,"listaxmlout",8,Types.CLOB); 
												util.settinParameterOnBean(call,bean,wrapperBean,"errcontextout",9,Types.VARCHAR); 
												util.settinParameterOnBean(call,bean,wrapperBean,"errcodeout",10,Types.INTEGER); 
												util.settinParameterOnBean(call,bean,wrapperBean,"errmsgout",11,Types.VARCHAR); 
												
		}catch(Exception e){
			throw new SQLException(e);
		}
   }
}