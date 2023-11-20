/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import it.eng.auriga.database.store.dmpk_utility.bean.DmpkUtilityCtrlconnectiontokenBean;
import it.eng.auriga.database.store.dmpk_utility.store.Ctrlconnectiontoken;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.repository2.generic.VersionHandler;
import it.eng.auriga.repository2.generic.VersionHandlerException;
import it.eng.auriga.repository2.jaxws.webservices.util.BridgeSingleton;
import it.eng.auriga.repository2.util.DBHelperSavePoint;
import it.eng.core.business.HibernateUtil;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;

/**
 * Contiene la logica per effetturare una login al sistema auriga dall'esterno, esempio tramite WS
 * 
 * @author Antonio Peluso
 */

public class RESTAurigaService {

	static Connection mConnection = null;
	
	private static final Logger aLogger = Logger.getLogger(RESTAurigaService.class);
	
	
	public RESTAurigaService() {}
	
	
	public static AurigaLoginBean logIn(AurigaLoginBean authenticationBean, ServletContext context) {
		
		String token=null;
		String schemaDb=null;
		String idDominioAttr=null;
		String desDominio=null;
		String desUserAttr=null;
		String flgTpDominioAut=null;
		
		// connessione al db valida per tutte le operazioni del WS
		Connection con = null;

		Session session = null;
		
		String userName = authenticationBean.getUserid();
		String password = authenticationBean.getPassword();
		String codApplicazione = authenticationBean.getCodApplicazione();
		String istanzaApplicazione = authenticationBean.getIdApplicazione();
		
		aLogger.debug("Inizio della LogIn con le seguenti credenziali: applicazione esterna: " + codApplicazione + "; istanza: " + istanzaApplicazione + "; userName: " + userName + "; password: " + password);

		// verifica che codApplicazione,istanzaApplicazione siano valorizzati
		if (codApplicazione == null || codApplicazione.equals("") || istanzaApplicazione == null || istanzaApplicazione.equals("")) { 
			
			aLogger.error(AurigaRestServiceMessages.ERROR_ENTE_NON_VALIDO);
			throw new AurigaRestServiceException(AurigaRestServiceMessages.ERROR_ENTE_NON_VALIDO, Status.UNAUTHORIZED);
		}
		
		try {
			// ***************************************************************
			// ricavo l'id ente mediante la chiamata al bridge
			// ***************************************************************
			try {
				// Utilizziamo il BridgeSingleton per ricavare a quale ente collegarsi in
				// base a codApplicazione e istanzaApplicazione
				BridgeSingleton bs = (BridgeSingleton) getApplicationContext(context).getBean(AurigaRestServiceMessages._SPRING_BEAN_BRIDGESINGLETON);
				schemaDb = bs.getDBPoolAlias(codApplicazione, istanzaApplicazione);
				
			} catch (Exception ex) {
				// lancio un messaggio di errore imprevisto NON APPLICATIVO
				aLogger.error("Errore imprevisto: " + ex.getMessage(), ex);
				throw new AurigaRestServiceException(AurigaRestServiceMessages.ERROR_CHK_COD_ENTE, Status.INTERNAL_SERVER_ERROR);
			}

			SubjectBean subject = new SubjectBean();
			subject.setIdDominio(schemaDb);
			SubjectUtil.subject.set(subject);

/* 01/12/2022 ottavio - Eliminata la connessione al db, usata per verificare se e' attivo
			  
			// ***************************************************************
			// ricavo la connessione al db mediante l'id ente                *
			// ***************************************************************
			try {
				try {
					session = HibernateUtil.begin();
					session.doWork(new Work() {

						@Override
						public void execute(Connection paramConnection) throws SQLException {
							mConnection = paramConnection;
						}
					});
				} catch (Exception e) {
					throw e;
				}
				// ricavo la connessione dal pool
				con = mConnection;

				// fallita connessione al db
				if (con == null) {
					aLogger.error(AurigaRestServiceMessages.ERROR_CONNESSIONE_DB);
					throw new AurigaRestServiceException(AurigaRestServiceMessages.ERROR_CONNESSIONE_DB, Status.INTERNAL_SERVER_ERROR);
				}
			}catch(AurigaRestServiceException e) {
				throw e;
			}catch (Exception ex) {
				// lancio un messaggio di errore imprevisto NON APPLICATIVO
				aLogger.error("Errore nel ricavare la connessione: " + ex.getMessage(), ex);
				throw new AurigaRestServiceException(AurigaRestServiceMessages.ERROR_CONNESSIONE_DB, Status.INTERNAL_SERVER_ERROR);
			}

			*/
			
			// Nuova gestione della login: viene effettuata una volta tramite
			// un apposito web service. Questo restituisce, nell'xml, il token
			// da utilizzare successivamente.
			// Se la USERNAME e' vuota si assume che la login sia gia' stata effettuata
			// e la PASSWORD conterra' il token. Se invece USERNAME non e' vuota allora
			// sto effettuando la login.

			// controllo se la username e' null o vuota
			if (userName == null || userName.trim().equals("")) {
				
				aLogger.error(AurigaRestServiceMessages.ERROR_USERNAME_NON_VALIDO);
				throw new AurigaRestServiceException(AurigaRestServiceMessages.ERROR_USERNAME_NON_VALIDO, Status.UNAUTHORIZED);
				
			}
			// user name non vuota, utilizzo la externalLogin
			else {
				try {
					String res[] = null;

					aLogger.debug("Applicazione = " + codApplicazione + " Istanza = " + istanzaApplicazione);

					/*
					 * Comportamento: - Se codApplicazione != "AURIGA" effettuo una login esterna passando applicazione ed istanza ottenuta in ingresso - Se
					 * codApplicazione == "AURIGA" effettuo una login interna. tipoDominio e idDominio sono ricavati a partire dal valore in istanzaApplicazione
					 * secondo la seguente convenzione: + istanzaApplicazione == "" o null, tipoDominio e idDominio sono "" + istanzaApplicazione == "X" (X
					 * numerico) tipoDominio = "X" e idDominio = "" + istanzaApplicazione == "X:Y" (X e Y numerici) tipoDominio = "X" e idDominio = "Y"
					 */
					// gestione della procedura di login e
					// ottenimento del token di connessione

					// prendo istanza del version handler
					aLogger.debug("prendo istanza del version handler");
					VersionHandler vh = getVersionHandler(context);

					// discriminante tra login interna ed esterna
					if ((VersionHandler._CNOME_APPLICAZIONE).equals(codApplicazione)) {
						String tipoDominio = "";
						String idDominio = "";
						int columnPos = -1;

						// vediamo se dobbiamo fare il parsing di istanzaApplicazione
						if (istanzaApplicazione != null && !"".equals(istanzaApplicazione)) {
							columnPos = istanzaApplicazione.lastIndexOf(":");
							if (columnPos == -1) {
								// ho solo il tipoDominio, vediamo se e' un numerico
								try {
									if (!"".equals(istanzaApplicazione)) {
										Integer.parseInt(istanzaApplicazione);
									}
									tipoDominio = istanzaApplicazione;
									aLogger.debug("tipoDominio = " + tipoDominio);
								} catch (Exception e) {
									aLogger.error("Errore in fase di login: tipoDominio non numerico");
									throw new AurigaRestServiceException(AurigaRestServiceMessages.ERROR_CHK_INTERNAL_LOGIN_FALLITO, Status.UNAUTHORIZED);
								}
							} else {
								tipoDominio = istanzaApplicazione.substring(0, columnPos);
								idDominio = istanzaApplicazione.substring(columnPos + 1);
								aLogger.debug("tipoDominio = " + tipoDominio + " idDominio = " + idDominio);
								try {
									if (!"".equals(tipoDominio))
										Integer.parseInt(tipoDominio);

									if (!"".equals(idDominio))
										Integer.parseInt(idDominio);
								} catch (Exception e) {
									aLogger.error("Errore in fase di login: tipoDominio o idDominio non numerico");
									throw new AurigaRestServiceException(AurigaRestServiceMessages.ERROR_CHK_INTERNAL_LOGIN_FALLITO, Status.UNAUTHORIZED);
								}
							}
						}
						// login interna
						res = vh.internalLogin(con, userName, password, tipoDominio, idDominio, schemaDb);
						aLogger.debug("risultato internal login -> token: " + res[0] + "; desUserAttr: " + res[1] + "; idDominioAttr: " + res[2]
								+ "; desDominio: " + res[3] + "; flgTpDominioAut: " + res[4]);

						// Leggo out restituito dalla WSLogin
						token = res[0]; // CodIdConnectionTokenOut
						desUserAttr = res[1]; // DesUserOut
						idDominioAttr = res[2]; // IdDominioOut
						desDominio = res[3]; // DesDominioOut
						flgTpDominioAut = res[4]; // FlgTpDominioAutOut
					} else {
						//check applicazione-istanza
						aLogger.debug("effettuo la external login (applicazione esterna: " + codApplicazione + "; istanza: " + istanzaApplicazione + ")");
						if (istanzaApplicazione == null) {
							istanzaApplicazione = "";
						}
						// effettuo la external login
						res = vh.externalLogin(con, userName, password, codApplicazione, istanzaApplicazione, schemaDb);
						aLogger.debug("risultato external login -> token: " + res[0] + "; desUserAttr: " + res[1] + "; idDominioAttr: " + res[2]
								+ "; desDominio: " + res[3]);

						// Leggo out restituito dalla WSLogin
						token = res[0]; // CodIdConnectionTokenOut
						desUserAttr = res[1]; // DesUserOut
						idDominioAttr = res[2]; // IdDominioOut
						desDominio = res[3]; // DesDominioOut
					}
				} catch (VersionHandlerException vhe) {
					// Qualcosa e` andato storto!
					StringBuffer errMsg = new StringBuffer(AurigaRestServiceMessages.ERROR_LOGIN_FALLITO);
					errMsg.append("[");
					errMsg.append(vhe.getMessage());
					errMsg.append("] ");
					aLogger.error(errMsg.toString());
					throw new AurigaRestServiceException(errMsg.toString(), Status.UNAUTHORIZED);

				} catch (Exception e) {
					aLogger.error("Errore imprevisto in fase di login: " + e.getMessage(), e);
					throw new AurigaRestServiceException(AurigaRestServiceMessages.ERROR_CHK_LOGIN_FALLITO, Status.UNAUTHORIZED);
				}
			}
			
			AurigaLoginBean authenticationBeanOut = new AurigaLoginBean();
			authenticationBeanOut.setToken(token);
			authenticationBeanOut.setSchema(schemaDb);
			authenticationBeanOut.setDominio(idDominioAttr);
			
			return authenticationBeanOut;
			
		} finally {
			//il rilascio della connessione va fatto qualsiasi cosa succeda!
			try {
				if (con != null)
					con.close();
					
				if (session!=null)	
					HibernateUtil.release(session);
				
			} catch (Exception e) {
			}
			
		}

	}
	
	protected static AutowireCapableBeanFactory getApplicationContext(ServletContext context) {
		WebApplicationContext webApplicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
		return webApplicationContext.getAutowireCapableBeanFactory();
	}
	
	/*
	 * Chiamo il servizio DmpkUtilityCtrlconnectiontoken INPUT : token OUTPUT : IdUserOut IdDominioAutOut FlgTpDominioAutOut CodApplEsternaOut
	 * CodIstanzaApplEstOut
	 */
	public String[] getInfoDominioFromToken(Connection conn, String schemaDb, String tokenIn) throws Exception {

		String[] resOut = new String[5];

		if (tokenIn != null && !tokenIn.equalsIgnoreCase("")) {

			aLogger.debug("Inizio getInfoDominioFromToken");
			aLogger.debug("tokenIn : " + tokenIn);

			if (conn != null) 
				conn.setAutoCommit(false);

			// setto il savepoint
			DBHelperSavePoint.SetSavepoint(conn, "GETINFODOMINIOFROMTOKEN");

			/************************************************************************************
			 * Chiamo il servizio di AurigaDocument DmpkUtilityCtrlconnectiontoken
			 ************************************************************************************/

			AurigaLoginBean loginBean = new AurigaLoginBean();
			loginBean.setSchema(schemaDb);
			loginBean.setToken(tokenIn);

			// Inizializzo l'INPUT
			DmpkUtilityCtrlconnectiontokenBean input = new DmpkUtilityCtrlconnectiontokenBean();
			input.setCodidconnectiontokenin(tokenIn);

			// Eseguo il servizio
			Ctrlconnectiontoken service = new Ctrlconnectiontoken();
			StoreResultBean<DmpkUtilityCtrlconnectiontokenBean> output = service.execute(loginBean, input);

			if (output.isInError()) {
				throw new Exception(output.getDefaultMessage());
			}

			// restituisco IdUserOut
			if (output.getResultBean().getIduserout() != null) {
				resOut[0] = output.getResultBean().getIduserout().toString();
			}

			// restituisco IdDominioAutOut
			if (output.getResultBean().getIddominioautout() != null) {
				resOut[1] = output.getResultBean().getIddominioautout().toString();
			}

			// restituisco FlgTpDominioAutOut
			if (output.getResultBean().getFlgtpdominioautout() != null) {
				resOut[2] = output.getResultBean().getFlgtpdominioautout().toString();
			}

			// restituisco CodApplEsternaOut
			if (output.getResultBean().getCodapplesternaout() != null) {
				resOut[3] = output.getResultBean().getCodapplesternaout().toString();
			}

			// restituisco CodIstanzaApplEstOut
			if (output.getResultBean().getCodistanzaapplestout() != null) {
				resOut[4] = output.getResultBean().getCodistanzaapplestout().toString();
			}
		} else {
			try {
				DBHelperSavePoint.RollbackToSavepoint(conn, "GETINFODOMINIOFROMTOKEN");
			} catch (Exception e) {
			}
			aLogger.error("Errore. Il token non e' valorizzato. Impossibile ricavare le informazionid del dominio.");
			throw new AurigaRestServiceException(AurigaRestServiceMessages.ERROR_TOKEN_NON_VALIDO, Status.UNAUTHORIZED);
		}
		return resOut;
	}

	/**
	 * restituisce il version handler che e' stato configurato
	 * 
	 * @return VersionHandler
	 */
	protected static VersionHandler getVersionHandler(ServletContext context) {
		// reperisco le informazioni per referenziare il DocumentRepositorySingleton
		try {
			return (VersionHandler) getApplicationContext(context).getBean(AurigaRestServiceMessages._SPRING_BEAN_VERSIONHANDLER);
		} catch (Exception e) {
			aLogger.error(e.getMessage(), e);
			return null;
		}

	}
	
	public static final AurigaLoginBean createAuthenticationBean(HttpHeaders headers) {
		
		final String userId = getRequestHeader(headers, AurigaRestServiceMessages.HEADER_PARAM_USERNAME);
		final String password = getRequestHeader(headers, AurigaRestServiceMessages.HEADER_PARAM_PASSWORD);
		final String codApplicazione = getRequestHeader(headers, AurigaRestServiceMessages.HEADER_PARAM_APPLICAZIONE);
		final String idApplicazione = getRequestHeader(headers, AurigaRestServiceMessages.HEADER_PARAM_INSTANZA);
		
		final AurigaLoginBean authenticationBean = new AurigaLoginBean();
		authenticationBean.setUserid(userId);
		authenticationBean.setPassword(password);
		authenticationBean.setCodApplicazione(codApplicazione);
		authenticationBean.setIdApplicazione(idApplicazione);

		return authenticationBean;
	}
	
	private static final String getRequestHeader(HttpHeaders headers, String name) {
		String value = null;
		final List<String> values = headers.getRequestHeader(name);
		if (values != null && values.size() == 1) {
			value = values.get(0);
		} else if (values != null) {
			value = values.toString();
		}
		return value;
	}
	
}
