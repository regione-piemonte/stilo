/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.repository2.generic.VersionHandlerException;
import it.eng.auriga.repository2.lucene.LuceneHandler;
import it.eng.auriga.repository2.util.DBHelperSavePoint;
import it.eng.auriga.repository2.versionhandler.synchronous.SynchronousVersionHandler;

import java.sql.Connection;

import org.apache.log4j.Logger;

import eng.storefunction.StoreProcedure;
import eng.storefunction.StoreProcedureException;

public class Login extends GenericHelper{

	public Login(Logger aLogger, Logger specialLogger,
			LuceneHandler luceneHandler) {
		super(aLogger, specialLogger, luceneHandler);
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
	public String[] externalLogin(Connection conn, String userId,
			String password, String extAppl, String istanzaAppl)
			throws VersionHandlerException {

		StoreProcedure store = null;
		int errCode = 0;
		String errMsg = "";

		try {
			aLogger.info("Inizio externalLogin");
			if (conn != null) 
				conn.setAutoCommit(false);
			
			DBHelperSavePoint.SetSavepoint(conn, "VHEXTERNALLOGIN");
			// ricavo l'interfaccia verso la stored specifica e imposto la
			// connessione
			store = getStoreProcedureFromRepositoryDefinitionSingleton(
					"LoginConCredenzialiEsterne", "External Login");

			// Impostiamo i valori per la store procedure....
			setStoreProcedureParametersValues(conn, store, new Object[] {
			// userId
					"UsernameIn", userId,
					// password
					"PasswordIn", password,
					// IdDominioAutIO
					"IdDominioOut", "",
					// CodApplicazioneIn
					"CodApplicazioneIn", extAppl,
					// CodIstanzaApplIn
					"CodIstanzaApplIn", istanzaAppl });

			aLogger.debug("Eseguo store per External Login");
			aLogger.debug("UsernameIn: " + userId);
			aLogger.debug("PasswordIn: " + password);			
			aLogger.debug("CodApplicazioneIn: " + extAppl);
			aLogger.debug("CodIstanzaApplIn: " + istanzaAppl);
			
			store.execute();						
			// commit
			// STE 22.10.07 conn.commit();			
			aLogger.debug("Eseguita store per External Login");
			
			// leggo parametri di out
			return getStoreProcedureParametersValues(store, new String[] {
					"CodIdConnectionTokenOut", // [0] CodIdConnectionTokenOut
					"DesUserOut", // [1] descrizione user
					"IdDominioOut", // [2] Id Dominio
					"DesDominioOut", // [3] descrizione Dominio
			});

		} catch (VersionHandlerException ex1) {
			// rollback STE 22.10.07
			try {
				DBHelperSavePoint.RollbackToSavepoint(conn, "VHEXTERNALLOGIN");
			} catch (Exception e) {
			}
			// log
			aLogger.error("Errore in External Login.\nErrcode:"
					+ ex1.getCodice() + "\nErrMsg: " + ex1.getMessage(), ex1);
			// rilancio l'eccezione
			throw ex1;
		} catch (StoreProcedureException ex0) {
			// rollback STE 22.10.07
			try {
				DBHelperSavePoint.RollbackToSavepoint(conn, "VHEXTERNALLOGIN");
			} catch (Exception e) {
			}
			// Qualcosa è andato storto nella storefunc!!!
			String err[] = getInfoFromStoreProcedureException(store, ex0);
			errCode = Integer.parseInt(err[0]);
			errMsg = err[1];
			// log
			aLogger.error("Errore SQL External Login.\nException: " + errMsg,
					ex0);
			// lancio l'eccezione
			throw new VersionHandlerException(errCode, errMsg);
		} catch (Exception ex) {
			// rollback STE 22.10.07
			try {
				DBHelperSavePoint.RollbackToSavepoint(conn, "VHEXTERNALLOGIN");
			} catch (Exception e) {
			}
			// log
			aLogger.error("Errore IMPREVISTO External Login.\nException: "
					+ ex.getMessage(), ex);
			// rilancio l'eccezione trasformata
			throw new VersionHandlerException(SynchronousVersionHandler.IMPREVISTO_COD, SynchronousVersionHandler.IMPREVISTO_MSG
					+ "(" + ex.getMessage() + ")");
		} finally {
			// STE 22.10.07 try {DBHelperSavePoint.RollbackToSavepoint(conn,
			// "VHEXTERNALLOGIN");}catch (Exception e){}
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
	public String[] internalLogin(Connection conn, String userId,
			String password, String tipoDominio, String idDominio)
			throws VersionHandlerException {

		StoreProcedure store = null;
		int errCode = 0;
		String errMsg = "";

		try {
			aLogger.info("Inizio internalLogin");

			if (conn != null) 
				conn.setAutoCommit(false);
			
			DBHelperSavePoint.SetSavepoint(conn, "VHINTERNALLOGIN");
			// ricavo l'interfaccia verso la stored specifica e imposto la
			// connessione
			store = getStoreProcedureFromRepositoryDefinitionSingleton("Login",
					"Internal Login");

			// Impostiamo i valori per la store procedure....
			setStoreProcedureParametersValues(conn, store, new Object[] {
			// userId
					"UsernameIn", userId,
					// password
					"PasswordIn", password,
					// FlgTpDominioAutIO
					"FlgTpDominioAutIO", tipoDominio,
					// IdDominioAutIO
					"IdDominioAutIO", idDominio,
					// CodApplicazioneEstIn
					"CodApplicazioneEstIn", "",
					// CodIstanzaApplEstIn
					"CodIstanzaApplEstIn", "" });

			aLogger.debug("Eseguo store per Internal Login");
			store.execute();
			// commit
			// STE 22.10.07 conn.commit();

			// leggo parametri di out
			return getStoreProcedureParametersValues(store, new String[] {
					"CodIdConnectionTokenOut", // [0] CodIdConnectionTokenOut
					"DesUserOut", // [1] descrizione user
					"IdDominioAutIO", // [2] Id Dominio
					"DesDominioOut", // [3] descrizione Dominio
					"FlgTpDominioAutIO" // [4] Tipo Dominio
			});

		} catch (VersionHandlerException ex1) {
			// rollback STE 22.10.07
			try {
				DBHelperSavePoint.RollbackToSavepoint(conn, "VHINTERNALLOGIN");
			} catch (Exception e) {
			}
			// log
			aLogger.error("Errore in Internal Login.\nErrcode:"
					+ ex1.getCodice() + "\nErrMsg: " + ex1.getMessage(), ex1);
			// rilancio l'eccezione
			throw ex1;
		} catch (StoreProcedureException ex0) {
			// rollback STE 22.10.07
			try {
				DBHelperSavePoint.RollbackToSavepoint(conn, "VHINTERNALLOGIN");
			} catch (Exception e) {
			}
			// Qualcosa è andato storto nella storefunc!!!
			String err[] = getInfoFromStoreProcedureException(store, ex0);
			errCode = Integer.parseInt(err[0]);
			errMsg = err[1];
			// log
			aLogger.error("Errore SQL Internal Login.\nException: " + errMsg,
					ex0);
			// lancio l'eccezione
			throw new VersionHandlerException(errCode, errMsg);
		} catch (Exception ex) {
			// rollback STE 22.10.07
			try {
				DBHelperSavePoint.RollbackToSavepoint(conn, "VHINTERNALLOGIN");
			} catch (Exception e) {
			}
			// log
			aLogger.error("Errore IMPREVISTO Internal Login.\nException: "
					+ ex.getMessage(), ex);
			// rilancio l'eccezione trasformata
			throw new VersionHandlerException(SynchronousVersionHandler.IMPREVISTO_COD, SynchronousVersionHandler.IMPREVISTO_MSG
					+ "(" + ex.getMessage() + ")");
		} finally {
			// STE 22.10.07 try {DBHelperSavePoint.RollbackToSavepoint(conn,
			// "VHINTERNALLOGIN");}catch (Exception e){}
			aLogger.info("Fine internalLogin");
		}

	}
}
