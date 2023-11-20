/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class AurigaRestServiceMessages {
	// Errore 1
		public static final int ERR_XML_NON_VALIDO = 1;
		public static final String ERROR_XML_NON_VALIDO = "Xml non valido: \n";
		// Errore 51
		public static final String ERROR_CHK_XML_NON_VALIDO = "Errore imprevisto nella verifica della validita' della stringa XML di input: \n";
		
		public static final String ERROR_SERVICE = "Si e' verificato un errore durante l'esecuzione del servizio";

		// Errore 3
		public static final int ERR_ENTE_NON_VALIDO = 3;
		public static final String ERROR_ENTE_NON_VALIDO = "Codice applicazione  e/o Istanza applicazione non valorizzati o nulli";
		public static final String ERROR_USERNAME_NON_VALIDO = "Username non valorizzato o nullo";
		public static final String ERROR_TOKEN_NON_VALIDO = "Errore. Il token non e' valorizzato. Impossibile ricavare le informazionid del dominio.";
		// Errore 53
		public static final String ERROR_CHK_COD_ENTE = "Errore imprevisto nel reperimento della stringa di connessione al db (BRIDGE)";

		// Errore 4
		public static final int ERR_LOGIN_FALLITO = 4;
		// Il codice ritornato non ? il 4, viene aggiunto quello che "ha da dire" la stored di login.
		public static final String ERROR_LOGIN_FALLITO = "Login fallito:";
		// 54 errore inatteso in fase di login
		public static final String ERROR_CHK_LOGIN_FALLITO = "Errore imprevisto durante l'autenticazione dell'utente";

		// Errore 6
		public static final int ERR_CONNESSIONE_DB = 6;
		public static final String ERROR_CONNESSIONE_DB = "Fallito reperimento della connessione al database";
		// Errore 56
		public static final String ERROR_CHK_CONNESSIONE_DB = "Errore imprevisto nel reperimento della connessione al database";

		// Errore 7
		public static final int ERR_STOREFUNCT_MANCANTE = 7;
		// Errore 57
		public static final String ERROR_CHK_STOREFUNCT_MANCANTE = "Errore imprevisto: Manca la storefunction Login";

		// Errore 8
		public static final int ERR_XML_ALTERATO = 8;
		public static final String ERROR_XML_ALTERATO = "Il DIGEST SHA-1 della stringa XML di input non coincide con quello fornito in input";
		// Errore 58
		public static final String ERROR_CHK_XML = "Errore imprevisto nella verifica del DIGEST SHA-1 della stringa XML di input";

		// Errore 9
		public static final int ERR_CREDENZIALI_LOGIN_INVALIDE = 9;
		public static final String ERROR_XML_CREDENZIALI_LOGIN_INVALIDE = "Errore: le credenziali di login non sono valide";
		
		// Errore 59
		public static final String ERROR_CHK_CREDENZIALI_LOGIN = "Errore imprevisto nel controllo delle credenziali di login";

		// Errore 10
		public static final int ERR_ATTACH_ALLEGATO = 10;
		// Errore 60
		public static final String ERROR_CHK_ATTACH_ALLEGATO = "Errore imprevisto nell'allegare l'attachment alla risposta";

		// Errore 11
		public static final int ERR_INTERNAL_LOGIN_FALLITO = 11;
		// Il codice ritornato non ? il 11, viene aggiunto quello che "ha da dire" la stored di login.
		public static final String ERROR_INTERNAL_LOGIN_FALLITO = "Login interno fallito:";
		// Errore 61 : errore inatteso in fase di login
		public static final String ERROR_CHK_INTERNAL_LOGIN_FALLITO = "Errore durante l'autenticazione dell'utente: controllare il tipo e l'id dominio";

		
		//Parametri Autenticazione
		public static final String HEADER_PARAM_USERNAME = "username";
		public static final String HEADER_PARAM_PASSWORD = "password";
		public static final String HEADER_PARAM_APPLICAZIONE = "applicazione";
		public static final String HEADER_PARAM_INSTANZA = "istanza";
		
		
		// costanti per codifica esito
		public static final int SUCCESSO = 1;
		public static final int FALLIMENTO = 0;
		protected static final int DELTA_INATTESO = 50;

		public static final int ERR_ERRORE_APPLICATIVO = 100;
		public static final String ERROR_ERRORE_APPLICATIVO = "Errore generico imprevisto";
		
		public static String COLUMN_SEPARATOR = "|*|";
		public static final String NO_CHK_XML = "_NoVerificaXml_";

		// per il DIME dovrebbe essere 3 mentre per ikl MIME dovrebbe essere 2
		public static int SEND_TYPE_MIME = 2;
		public static int SEND_TYPE_DIME = 3;

		// String pathTmpUpload=null;

		public static final String _SPRING_BEAN_VERSIONHANDLER = "VersionHandler";
		public static final String _SPRING_BEAN_FAXHANDLER = "FaxHandler";
		public static final String _SPRING_BEAN_BRIDGESINGLETON = "BridgeSingleton";
		public static final String _SPRING_BEAN_WSCONFIGUER = "WebServiceConfigurer";
		public static final String _SPRING_BEAN_REPOSITORYADVANCED = "RepositoryAdvanced";
}
