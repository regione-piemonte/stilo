/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_login.bean.DmpkLoginLoginBean;
import it.eng.auriga.database.store.dmpk_login.bean.DmpkLoginLoginconcredenzialiesterneBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.repository2.generic.VersionHandlerException;
import it.eng.auriga.repository2.lucene.LuceneHandler;
import it.eng.auriga.repository2.util.DBHelperSavePoint;
import it.eng.auriga.repository2.versionhandler.synchronous.SynchronousVersionHandler;

import java.math.BigDecimal;
import java.sql.Connection;
import org.apache.log4j.Logger;
import it.eng.auriga.database.store.dmpk_login.store.Login;
import it.eng.auriga.database.store.dmpk_login.store.Loginconcredenzialiesterne;

public class LoginWS extends GenericHelper{

	public LoginWS(Logger aLogger, Logger specialLogger,
			LuceneHandler luceneHandler
			) {	
		super(aLogger, specialLogger, luceneHandler);
		// TODO Auto-generated constructor stub
	}
	/*****************************************************************************************************
	 * LOGIN
	 *****************************************************************************************************/
	/**
	 * Metodo che consente di effettuare login mediante credenziali ESTERNE
	 * Restituisce un array di stringhe contenenti: [0] CodIdConnectionTokenOut
	 * [1] descrizione user [2] Id Dominio [3] descrizione Dominio [4] Tipo
	 * Dominio
	 * 
	 * @param con
	 *            Connection
	 * @param userId
	 *            String
	 * @param password
	 *            String
	 * @param extAppl
	 *            String
	 * @param istanzaAppl
	 *            String
	 * @return String[]
	 * @throws VersionHandlerException
	 */
	public String[] externalLogin(Connection conn, String userId, String password, String extAppl, String istanzaAppl, String dbSchema) throws VersionHandlerException {
		String[] result =  new String[4];
		
		try {
			
			aLogger.debug("Inizio externalLogin");
			aLogger.debug("UsernameIn: " + userId);
			aLogger.debug("PasswordIn: " + password);			
			aLogger.debug("CodApplicazioneIn: " + extAppl);
			aLogger.debug("CodIstanzaApplIn: " + istanzaAppl);
			aLogger.debug("dbSchemaIn: " + dbSchema);
			
			if (conn != null) 
				conn.setAutoCommit(false);
			
			//setto il savepoint
			DBHelperSavePoint.SetSavepoint(conn, "VHEXTERNALLOGIN");
						
			/************************************************************************************
	  	     * Chiamo il servizio di AurigaDocument  DmpkLoginLoginconcredenzialiesterne
	  	     ************************************************************************************/   
			
			WSLoginInBean loginInBean = new WSLoginInBean();
			loginInBean.setCodApplicazioneIn(extAppl);
			loginInBean.setCodIstanzaApplIn(istanzaAppl);
			loginInBean.setPasswordIn(password);
			loginInBean.setUsernameIn(userId);
			loginInBean.setDbSchemaIn(dbSchema);

			
			WSLoginOutBean outServizio =  new WSLoginOutBean();
			outServizio =  eseguiServizio("E", loginInBean);
			
			/*************************************************************
	  	     * Popolo il result
	  	     ************************************************************/   
			
			result[0] = outServizio.getCodIdConnectionTokenOut();
			result[1] = outServizio.getDesUserOut();
			result[2] = outServizio.getIdDominioOut();
			result[3] = outServizio.getDesDominioOut();
			
			aLogger.debug("Eseguita store per External Login");
			aLogger.debug("CodIdConnectionTokenOut: " + result[0]);
			aLogger.debug("DesUserOut: " + result[1]);			
			aLogger.debug("IdDominioOut: " + result[2]);
			aLogger.debug("DesDominioOut: " + result[3]);
			
			return result;
			
		} catch (Exception ex) {
			try {
				DBHelperSavePoint.RollbackToSavepoint(conn, "VHEXTERNALLOGIN");
			} catch (Exception e) {
			}
			aLogger.error("Errore IMPREVISTO External Login.\nException: " + ex.getMessage(), ex);			
			// rilancio l'eccezione trasformata
			throw new VersionHandlerException(SynchronousVersionHandler.IMPREVISTO_COD, SynchronousVersionHandler.IMPREVISTO_MSG + "(" + ex.getMessage() + ")");
		} finally {
			aLogger.info("Fine externalLogin");
		}
	}

	/**
	 * Metodo che consente di effettuare login mediante credenziali INTERNE
	 * Restituisce un array di stringhe contenenti: [0] CodIdConnectionTokenOut
	 * [1] descrizione user [2] Id Dominio [3] descrizione Dominio [4] Tipo
	 * Dominio
	 * 
	 * @param con
	 *            Connection
	 * @param userId
	 *            String
	 * @param password
	 *            String
	 * @param tipoDominio
	 *            String
	 * @param idDominio
	 *            String
	 * @return String[]
	 * @throws VersionHandlerException
	 */
	public String[] internalLogin(Connection conn, String userId, String password, String tipoDominio, String idDominio, String dbSchema) throws VersionHandlerException {

		String[] result =  new String[5];
		
		try {
			aLogger.info("Inizio internalLogin");
			aLogger.debug("UsernameIn: " + userId);
			aLogger.debug("PasswordIn: " + password);			
			aLogger.debug("TipoDominioIn: " + tipoDominio);
			aLogger.debug("IdDominioIn: " + idDominio);
			aLogger.debug("dbSchemaIn: " + dbSchema);
			
			
			
			if (conn != null) 
				conn.setAutoCommit(false);
			
			DBHelperSavePoint.SetSavepoint(conn, "VHINTERNALLOGIN");
						
			/*************************************************************
	  	     * Chiamo il servizio di AurigaDocument  DmpkLoginLogin
	  	     ************************************************************/   
			
			WSLoginInBean loginInBean = new WSLoginInBean();
			loginInBean.setPasswordIn(password);
			loginInBean.setUsernameIn(userId);
			loginInBean.setTipoDominioIn(tipoDominio);
			loginInBean.setDbSchemaIn(dbSchema);
			
			WSLoginOutBean outServizio =  new WSLoginOutBean();
			outServizio =  eseguiServizio("I", loginInBean);
			
			/********************loginInBean.setIdDominio*****************************************
	  	     * Popolo il result
	  	     ************************************************************/   
			
			result[0] = outServizio.getCodIdConnectionTokenOut();
			result[1] = outServizio.getDesUserOut();
			result[2] = outServizio.getIdDominioOut();
			result[3] = outServizio.getDesDominioOut();
			result[4] = outServizio.getFlgTpDominioAutOut();
			
			aLogger.debug("Eseguita store per Internal Login");
			aLogger.debug("CodIdConnectionTokenOut: " + result[0]);
			aLogger.debug("DesUserOut: " + result[1]);			
			aLogger.debug("IdDominioOut: " + result[2]);
			aLogger.debug("DesDominioOut: " + result[3]);
			aLogger.debug("FlgTpDominioAutOut: " + result[4]);
			
			return result;
		} 
		catch (Exception ex) {
			// rollback STE 22.10.07
			try {
				DBHelperSavePoint.RollbackToSavepoint(conn, "VHINTERNALLOGIN");
			} catch (Exception e) {
			}
			aLogger.error("Errore IMPREVISTO Internal Login.\nException: " + ex.getMessage(), ex);
			// rilancio l'eccezione trasformata
			throw new VersionHandlerException(SynchronousVersionHandler.IMPREVISTO_COD, SynchronousVersionHandler.IMPREVISTO_MSG + "(" + ex.getMessage() + ")");
		} finally {
			aLogger.info("Fine internalLogin");
		}
	}
		
	private WSLoginOutBean eseguiServizio(String tipoLogin, WSLoginInBean loginInBean) throws Exception {		 		 
		 String idDominioOut            = null;
		 String codIdConnectionTokenOut = null;
		 String desUserOut              = null;
		 String desDominioOut           = null;
		 String flgTpDominioAutOut      = null;
		 		 
		 try { 
			    WSLoginOutBean  result = new WSLoginOutBean();
			 
			 	// Login ESTERNA
			    if (tipoLogin.equalsIgnoreCase("E")){
			    	 aLogger.debug("Eseguo il WS DmpkLoginLoginconcredenzialiesterne.");
			    	 
			    	 AurigaLoginBean loginBean = new AurigaLoginBean();
			    	 loginBean.setSchema(loginInBean.getDbSchemaIn());
			    	 
			    	// Inizializzo l'INPUT    		
			    	 DmpkLoginLoginconcredenzialiesterneBean input = new DmpkLoginLoginconcredenzialiesterneBean();
			    	 input.setCodapplicazionein(loginInBean.getCodApplicazioneIn());
		    		 input.setCodistanzaapplin(loginInBean.getCodIstanzaApplIn());
		    		 input.setPasswordin(loginInBean.getPasswordIn());
		    		 input.setUsernamein(loginInBean.getUsernameIn());
		    		 
		    		// Eseguo il servizio
		    		 Loginconcredenzialiesterne service = new Loginconcredenzialiesterne();
		    		 StoreResultBean<DmpkLoginLoginconcredenzialiesterneBean> output = service.execute(loginBean, input);
		    		  
		    		 if (output.isInError()){
		    			  throw new Exception(output.getDefaultMessage());	
		    			}	
		    		 
		    		 // restituisco IdDominioOut
		    		 if (output.getResultBean().getIddominioout() != null){
		    			 idDominioOut = output.getResultBean().getIddominioout().toString();  
		    		  }
		    		 
		    		 // restituisco CodIdConnectionTokenOut
		    		 if (output.getResultBean().getCodidconnectiontokenout() != null){
		    			 codIdConnectionTokenOut = output.getResultBean().getCodidconnectiontokenout().toString();  
		    		  }
		    		 
		    		 // restituisco DesUserOut
		    		 if (output.getResultBean().getDesuserout() != null){
		    			 desUserOut = output.getResultBean().getDesuserout().toString();  
		    		  }
		    		 
		    		 // restituisco DesDominioOut
		    		 if (output.getResultBean().getDesdominioout()!= null){
		    			 desDominioOut = output.getResultBean().getDesdominioout().toString();  
		    		  }
		    		 
		    		 // popolo il bean di out
		    		 result.setIdDominioOut(idDominioOut);
		    		 result.setCodIdConnectionTokenOut(codIdConnectionTokenOut);
		    		 result.setDesUserOut(desUserOut);
	   	      		 result.setDesDominioOut(desDominioOut);
			    }
			    
			    // Login INTERNA
			    else if (tipoLogin.equalsIgnoreCase("I")){
			    	
			    	 aLogger.debug("Eseguo il WS DmpkLoginLogin.");
			    	 aLogger.debug("CodApplicazioneIn = " + loginInBean.getCodApplicazioneIn());
			    	 aLogger.debug("IstanzaApplIn = " + loginInBean.getCodIstanzaApplIn());
			    	 aLogger.debug("PasswordIn = " + loginInBean.getPasswordIn());
			    	 aLogger.debug("UsernameIn = " + loginInBean.getUsernameIn());
			    	 
			    	 if (loginInBean.getIdDominioIn()!= null)
			    		 aLogger.debug("UsernameIn = " + loginInBean.getUsernameIn());
			    	 
			    	 
			    	 AurigaLoginBean loginBean = new AurigaLoginBean();
			    	 loginBean.setSchema(loginInBean.getDbSchemaIn());
			    	 //loginBean.setIdUtente(loginInBean.getUsernameIn());
			    	 //loginBean.setPassword(loginInBean.getPasswordIn());
			    	 
			    	 
			    	 // Inizializzo l'INPUT    		
		    		 DmpkLoginLoginBean input = new DmpkLoginLoginBean();
		    		 input.setCodapplicazioneestin(loginInBean.getCodApplicazioneIn());
		    		 input.setCodistanzaapplestin(loginInBean.getCodIstanzaApplIn());
		    		 input.setPasswordin(loginInBean.getPasswordIn());
		    		 input.setUsernamein(loginInBean.getUsernameIn());
		    		 if (loginInBean.getIdDominioIn()!= null)
		    			 input.setIddominioautio(new BigDecimal(loginInBean.getIdDominioIn()));
		    		 
		    		 // Eseguo il servizio
		    		 Login service = new Login();
		    		 StoreResultBean<DmpkLoginLoginBean> output = service.execute(loginBean, input);
		    		  
		    		 if (output.isInError()){
		    			  throw new Exception(output.getDefaultMessage());	
		    			}	
		    		  
		    		 // restituisco IdDominioAutIO
		    		 if (output.getResultBean().getIddominioautio() != null){
		    			 idDominioOut = output.getResultBean().getIddominioautio().toString();  
		    		  }
		    		 
		    		 // restituisco CodIdConnectionTokenOut
		    		 if (output.getResultBean().getCodidconnectiontokenout() != null){
		    			 codIdConnectionTokenOut = output.getResultBean().getCodidconnectiontokenout().toString();  
		    		  }
		    		 
		    		 // restituisco DesUserOut
		    		 if (output.getResultBean().getDesuserout() != null){
		    			 desUserOut = output.getResultBean().getDesuserout().toString();  
		    		  }
		    		 
		    		 // restituisco DesDominioOut
		    		 if (output.getResultBean().getDesdominioout()!= null){
		    			 desDominioOut = output.getResultBean().getDesdominioout().toString();  
		    		  }
		    		 
		    		 // restituisco il Flgtpdominioautio()
		    		 if (output.getResultBean().getFlgtpdominioautio().toString() != null) {
		    			 flgTpDominioAutOut = output.getResultBean().getFlgtpdominioautio().toString();
		    		 }
		    		 
				 	 // popolo il bean di out
		    		 result.setIdDominioOut(idDominioOut);
		    		 result.setCodIdConnectionTokenOut(codIdConnectionTokenOut);
		    		 result.setDesUserOut(desUserOut);
	   	      		 result.setDesDominioOut(desDominioOut);
	   	      		 result.setFlgTpDominioAutOut(flgTpDominioAutOut);
	   	      	
	   	      	
			    }
			    
   	      		return result;
   	      	
		 }
		 catch (Exception e){
	 			throw new Exception(e.getMessage()); 			
	 		}
	 }
}
