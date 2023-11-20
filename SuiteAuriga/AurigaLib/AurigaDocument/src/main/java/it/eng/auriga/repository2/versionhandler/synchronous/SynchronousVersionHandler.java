/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.function.bean.FindElenchiAlbiResultBean;
import it.eng.auriga.function.bean.FindRepositoryObjectBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.repository2.generic.ObjectHandlerException;
import it.eng.auriga.repository2.generic.VersionHandler;
import it.eng.auriga.repository2.generic.VersionHandlerException;
import it.eng.auriga.repository2.lucene.LuceneHandler;
import it.eng.auriga.repository2.lucene.LuceneParameterFilter;
import it.eng.auriga.repository2.versionhandler.synchronous.helpers.Find;
import it.eng.auriga.repository2.versionhandler.synchronous.helpers.LoginWS;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Properties;

import org.apache.log4j.Logger;

import eng.storefunction.StoreProcedure;

/**
 * 
 * <p>
 * Description: VersionHandler che lavora in modalita' sincrona. Gestisce un pool di ObjectHandler e usa quello specifico in base a quanto indicato nel
 * descrittore del file.
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class SynchronousVersionHandler implements VersionHandler {

	private Find find = null;

	private LoginWS login = null;

	// ***************** costanti di classe ***********************
	public static final String _HANDLERVERSION = "SynchronousVersionHandler 1.0 AURIGA";

	public static final String UNAVAILABLE_VERSION = "Impossibile determinare la versione di questo VersionHandler";

	public static final String _DEFAULT = "DEFAULT";

	public static final String _BACKUP = ".BACKUP";

	public static final String _HANDLER = ".HANDLER";

	public static final String _H_ALIAS = ".HANDLER.ALIAS";

	public static final String _OVERFLOW_LIMIT = "OVERFLOW_LIMIT";

	public static final String _DEFAULT_TEMP = "DEFAULT_TEMP";

	public static final String _MAX_ARCHIEVE_NUM_FILES = "maxFileInArchivio";

	public static final String _LOGGER_NAME = "versionhandler";

	public static final String _SPECIAL_LOGGER_NAME = "versionhandlerSpecialLogging";

	public static final int IMPREVISTO_COD = 0;

	public static final String IMPREVISTO_MSG = "Errore imprevisto";

	public static final int NO_DBMANAGER_DISPONIBILE_COD = -1;

	public static final String NO_DBMANAGER_DISPONIBILE_MSG = "Impossibile ottenere una connessione";

	public static final int DBMANAGER_CONN_NULL_COD = -2;

	public static final String DBMANAGER_CONN_NULL_MSG = "Connessione nulla";

	public static final int STORE_FUNC_NULL_COD = -3;

	public static final String STORE_FUNC_NULL_MSG = "Store function nulla";

	public static final int SOURCEFILE_NULL_COD = -4;

	public static final String SOURCEFILE_NULL_MSG = "Documento/Directory sorgente nullo o inesistente";

	public static final int TARGETFILE_DUPL_COD = -5;

	public static final String TARGETFILE_DUPL_MSG = "Risorsa logica gia' esistente nel repository";

	public static final int TARGETFILE_NULL_COD = -6;

	public static final String TARGETFILE_NULL_MSG = "File/Directory target nullo o inesistente";

	public static final int TARGETFILE_INVALID_COD = -7;

	public static final String TARGETFILE_INVALID_MSG = "File/Directory target non valido";

	// all'OCCHIO -----> non usare il codice 8 !!!!!

	public static final int TARGETFILE_EXISTS_COD = -9;

	public static final String TARGETFILE_EXISTS_MSG = "File target gia' esistente";

	public static final int NOT_LOCKED_DOC_COD = -10;

	public static final String NOT_LOCKED_DOC_MSG = "Documento non LOCKED prima di CHECK IN";

	public static final int OBJHANDLER_FAIL_COD = -11;

	public static final String OBJHANDLER_FAIL_MSG = "Errore nella chiamata a OBJECT HANDLER";

	public static final int INVALID_P7M_COD = -12;

	public static final String INVALID_P7M_MSG = "Impossibile sbustare il P7M: file corrotto o certificati non validi";

	public static final int NOT_IMPLEMENTED_COD = -13;

	public static final String NOT_IMPLEMENTED_MSG = "Funzionalita' non implementata";

	public static final int SOURCEFILE_FAIL_COD = -14;

	public static final String SOURCEFILE_FAIL_MSG = "Errore in creazione Directory";

	public static final int STEP_WITH_NO_FILE_COD = -15;

	public static final String STEP_WITH_NO_FILE_MSG = "Errore in Generic Checkin: documento elettronico obbligatorio";

	public static final int NULL_IDDOC_COD = -16;

	public static final String NULL_IDDOC_MSG = "ID Doc nullo";

	public static final int INVALID_VERSION_COD = -17;

	public static final String INVALID_VERSION_MSG = "Errore in Generic CheckIn: ultima versione diversa da quella indicata";

	public static final int INVALID_LOGICAL_NAME_COD = -18;

	public static final String INVALID_LOGICAL_NAME_MSG = "Logical name invalido";

	public static final int INVALID_SHA1_COD = -19;

	public static final String INVALID_SHA1_MSG = "";

	public static final int EMPTY_LOGICAL_NAME_COD = -20;

	public static final String EMPTY_LOGICAL_NAME_MSG = "Logical name vuoto";

	public static final int NULL_IDUD_COD = -21;

	public static final String NULL_IDUD_MSG = "ID UD nullo";

	public static final int INVALID_DELETE_TARGET_COD = -22;

	public static final String INVALID_DELETE_TARGET_MSG = "Errore in Generic Delete/Recover: target non valorizzato o erroneamente valorizzato";

	public static final int INVALID_TARGET_PROFILE_COD = -23;

	public static final String INVALID_TARGET_PROFILE_MSG = "Errore in getContainerProfile: target non valorizzato o erroneamente valorizzato";

	public static final int NULL_IDFOLDER_COD = -24;

	public static final String NULL_IDFOLDER_MSG = "ID Folder nullo";

	public static final int CONTAINER_OPERATION_ERROR_COD = -25;

	public static final String CONTAINER_OPERATION_ERROR_MSG = "Errore nell'effettuare operazione sul container";

	public static final int DOC_FISICO_NON_PRESENTE_ERROR_COD = -26;

	public static final String DOC_FISICO_NON_PRESENTE_ERROR_MSG = "Errore: documento fisico non presente";

	public static final int NULL_URI_COD = -27;

	public static final String NULL_URI_MSG = "Errore: URI nullo";

	public static final int INVALID_REMOVE_INNER_TYPE_COD = -28;

	public static final String INVALID_REMOVE_INNER_TYPE_MSG = "Errore: tipo di oggetto da rimuovere non valorizzato correttamente";

	public static final int INVALID_REMOVE_OUTER_TYPE_COD = -29;

	public static final String INVALID_REMOVE_OUTER_TYPE_MSG = "Errore: tipo di oggetto da cui rimuovere non valorizzato correttamente";

	public static final int NULL_IDWORKSPACE_COD = -30;

	public static final String NULL_IDWORKSPACE_MSG = "ID Workspace nullo";

	public static final int NULL_MODELLO_COD = -31;

	public static final String NULL_MODELLO_MSG = "Id modello e nome modello nulli";

	public static final int NO_DATA_FOUND_SEARCH_COD = -32;

	public static final String NO_DATA_FOUND_SEARCH_MSG = "Nessun risultato trovato";

	public static final int SEARCH_OVERFLOW_COD = -33;

	public static final String SEARCH_OVERFLOW_MSG = "I termini da ricercare (nel filtro \"Cerca\") sono troppo generici. Ricerca non consentita";;

	public static final int SEARCH_MANY_CLAUSES_COD = -43;

	public static final String SEARCH_MANY_CLAUSES_MSG = "I termini da ricercare (nel filtro \"Cerca\") sono troppo generici. Ricerca non consentita";

	public static final int INPUT_VALIDATION_ERR_COD = -34;

	public static final String INPUT_VALIDATION_ERR_MSG = "Errore in validazione parametro in ingresso";

	public static final int LUCENE_ERR_COD = -34;

	public static final String LUCENE_ERR_MSG = "Errore nella ricerca: ";

	public static final int LUCENE_HANDLER_COD = -35;

	public static final String LUCENE_HANDLER_MSG = "Errore durante operazione sul Lucene Handler";

	public static final int P7M_ERROR_COD = -36;

	public static final String P7M_ERROR_MSG = "Errore: file p7m corrotto";

	public static final int INVALID_MOVE_TYPE_COD = -37;

	public static final String INVALID_MOVE_TYPE_MSG = "Errore: tipo di oggetto da spostare (folder o unita' doc) non valorizzato correttamente";

	public static final int _SUB_FOLDER_CREATION_COD = -38;

	public static final String _SUB_FOLDER_CREATION_MSG = "Nessun folder di appartenenza presente sul container";

	public static final int INVALID_MOVE_TOFROM_TYPE_COD = -39;

	public static final String INVALID_MOVE_TOFROM_TYPE_MSG = "Errore: tipo di oggetto da/verso cui spostare erroneamente valorizzato";

	public static final int NULL_LISTAACL_COD = -40;

	public static final String NULL_LISTAACL_MSG = "Lista ACL nulla";

	public static final int NULL_OH_COD = -41;

	public static final String NULL_OH_MSG = "Richiesto utilizzo di un Object Handler non configurato";

	public static final int NULL_XMLACL_COD = -42;

	public static final String NULL_XMLACL_MSG = "Xml contenete informazioni sulle ACL non valorizzato";
	
	public static final int SEARCH_BUFFER_OVERFLOW_COD = -33;
	
	
	// costanti postfix per identificare le varriabili di un oggetto
	// SezioneCache
	// in INPUT/OUTPUT per i metodi degli object handler evoluti.
	public static final String _CONTAINER_PROFILE_PREFIX = "_!PROF";

	public static final String _CONTAINER_WARNING_PREFIX = "_!WARN";

	// Istanza di Lucene Handler per il V.H.
	protected LuceneHandler luceneHandler = null;

	public static final String _LISTA_VUOTA_STR = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?><Lista></Lista>";

	static Logger aLogger = Logger.getLogger(_LOGGER_NAME);

	static Logger specialLogger = Logger.getLogger(_SPECIAL_LOGGER_NAME);

	/*
	 * STE 22.10.07: effettuata la seguente modifica: le API del version Handler non sono più auto commitattanti, ma il commit deve essere eseguito
	 * dall'applicazione che le chiama. In caso di fallimento ciscuna api effettua una rollback fino al savepoint che aveva settato inizialmente. Attenzione
	 * alla gestione della transazione per non perdere i log su auriga.
	 */

	/*****************************************************************************************************
	 * COSTRUTTORI
	 *****************************************************************************************************/
	/**
	 * Costruttore di default. Le properties vengono prese dai rispettivi file con i nomi delle classi.
	 * 
	 * @throws VersionHandlerException
	 */
	public SynchronousVersionHandler() throws VersionHandlerException {
	}

	/**
	 * Costruttore a partire da oggetto properties, usato in caso di configurazione da ws
	 * 
	 * @throws VersionHandlerException
	 */
	protected SynchronousVersionHandler(Properties properties) throws VersionHandlerException {
	}

	/*****************************************************************************
	 * Getter per il Lucene Handler
	 ****************************************************************************/
	public LuceneHandler getLuceneHandler() {
		return luceneHandler;
	}

	public void setLuceneHandler(LuceneHandler luceneHandler) {
		this.luceneHandler = luceneHandler;
	}

	/**
	 * Metodo che restituisce la versione dell'Handler
	 * 
	 * @return
	 * @throws ObjectHandlerException
	 */
	public String getVersion() throws ObjectHandlerException {
		return _HANDLERVERSION;
	}

	/**
	 * Metodo wrapper per reperire l'oggetto StoreProcedure, associare la connessione e gestire eventuale errore.
	 * 
	 * @param con
	 *            Connection
	 * @param nomestored
	 *            String
	 * @param msg
	 *            String
	 * @return StoreProcedure
	 * @throws VersionHandlerException
	 */
	protected StoreProcedure getStoreProcedureFromRepositoryDefinitionSingleton(String nomestored, String msg) throws VersionHandlerException {
		StoreProcedure store = null;

		// ricavo l'interfaccia verso la stored specifica...
		store = SynchronousVersionHandlerRepositoryDefinitionSingleton.getInstance().getStoreProcedure(nomestored);
		if (store == null) {
			// lancio eccezione
			throw new VersionHandlerException(STORE_FUNC_NULL_COD, STORE_FUNC_NULL_MSG + msg);
		}

		return store;
	}

	/*****************************************************************************************************
	 * LOGIN
	 *****************************************************************************************************/

	/**
	 * Metodo che consente di effettuare login mediante credenziali ESTERNE Restituisce un array di stringhe contenenti: [0] CodIdConnectionTokenOut [1]
	 * descrizione user [2] Id Dominio [3] descrizione Dominio [4] Tipo Dominio
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
		if (login == null)
			login = new LoginWS(aLogger, specialLogger, luceneHandler);
		return login.externalLogin(conn, userId, password, extAppl, istanzaAppl, dbSchema);
	}

	/**
	 * Metodo che consente di effettuare login mediante credenziali INTERNE Restituisce un array di stringhe contenenti: [0] CodIdConnectionTokenOut [1]
	 * descrizione user [2] Id Dominio [3] descrizione Dominio [4] Tipo Dominio
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
		if (login == null)
			login = new LoginWS(aLogger, specialLogger, luceneHandler);
		return login.internalLogin(conn, userId, password, tipoDominio, idDominio,dbSchema);
	}

	/*****************************************************************************************************
	 * API DI RICERCA
	 *****************************************************************************************************/

	/**
       * 
       */
	public Object[] findRepositoryObject(Connection conn, String token, String userIdLavoro, String filtroFullText, String[] checkAttributes,
			String formatoEstremiReg, Integer searchAllTerms, String flgUdFolder, Long idFolderSearchIn, String flgSubfoderSearchIn, String advancedFilters,
			String customFilters, String colsOrderBy, String flgDescOrderBy, Integer flgSenzaPaginazione, Integer numPagina, Integer numRighePagina,
			Integer online, String colsToReturn, String percorsoRicerca, String flagTipoRicerca, String finalita, Integer overflowLimitIn)
			throws VersionHandlerException {
		if (find == null)
			find = new Find(aLogger, specialLogger, luceneHandler);
		return find.findRepositoryObject(conn, token, userIdLavoro, filtroFullText, checkAttributes, formatoEstremiReg, searchAllTerms, flgUdFolder,
				idFolderSearchIn, flgSubfoderSearchIn, advancedFilters, customFilters, colsOrderBy, flgDescOrderBy, flgSenzaPaginazione, numPagina,
				numRighePagina, online, colsToReturn, percorsoRicerca, flagTipoRicerca, finalita, null, overflowLimitIn);

	}
	
	public String getMatchbyindexerin(FindRepositoryObjectBean pBeanIn,AurigaLoginBean lAurigaLoginBean)
			throws VersionHandlerException {
		if (find == null)
			find = new Find(aLogger, specialLogger, luceneHandler);
		return find.getMatchbyindexerin(pBeanIn, lAurigaLoginBean);
	}

	public Object[] findProcessObject(Connection conn, String token, String userIdLavoro, String filtroFullText, String[] checkAttributes,
			String formatoEstremiReg, Integer searchAllTerms, String advancedFilters, String customFilters, String colsOrderBy, String flgDescOrderBy,
			Integer flgSenzaPaginazione, Integer numPagina, Integer numRighePagina, Integer online, String colsToReturn, String attoriToReturn,
			String attrCustomToReturn, String finalita) throws VersionHandlerException {
		if (find == null)
			find = new Find(aLogger, specialLogger, luceneHandler);
		return find.findProcessObject(conn, token, userIdLavoro, filtroFullText, checkAttributes, formatoEstremiReg, searchAllTerms, advancedFilters,
				customFilters, colsOrderBy, flgDescOrderBy, flgSenzaPaginazione, numPagina, numRighePagina, online, colsToReturn, attoriToReturn,
				attrCustomToReturn, finalita, null);
	}

	public Object[] findOrgStructureObject(Connection conn, String token, String userIdLavoro, String filtroFullText, String[] checkAttributes,
			Integer searchAllTerms, String[] flgObjectTypes, Integer idUoSearchIn, String flgSubUoSearchIn, String tsRiferimento, String advancedFilters,
			String customFilters, String colsOrderBy, String flgDescOrderBy, Integer flgSenzaPaginazione, Integer numPagina, Integer numRighePagina,
			Integer online, String colsToReturn, String percorsoRicerca, String finalita, String idUd, Integer overFlowLimitIn, String tyobjxfinalitain, String idobjxfinalitain, LuceneParameterFilter filter)
			throws VersionHandlerException {
		if (find == null)
			find = new Find(aLogger, specialLogger, luceneHandler);
		return find.findOrgStructureObject(conn, token, userIdLavoro, filtroFullText, checkAttributes, searchAllTerms, flgObjectTypes, idUoSearchIn,
				flgSubUoSearchIn, tsRiferimento, advancedFilters, customFilters, colsOrderBy, flgDescOrderBy, flgSenzaPaginazione, numPagina, numRighePagina,
				online, colsToReturn, percorsoRicerca, finalita, idUd, overFlowLimitIn, tyobjxfinalitain, idobjxfinalitain, filter);
	}

	public Object[] findTitolarioObject(Connection conn, String token, String userIdLavoro, String filtroFullText, String[] checkAttributes,
			String[] flgObjectTypes, Integer searchAllTerms, Integer idClSearchIO, String flgSubClSearchIn, String tsRiferimento, String advancedFilters,
			String customFilters, String colsOrderBy, String flgDescOrderBy, Integer flgSenzaPaginazione, Integer numPagina, Integer numRighePagina,
			Integer overFlowLimit, String flgSenzaTot, Integer online, String colsToReturn, String finalita, LuceneParameterFilter filter)
			throws VersionHandlerException {
		if (find == null)
			find = new Find(aLogger, specialLogger, luceneHandler);
		return find.findTitolarioObject(conn, token, userIdLavoro, filtroFullText, checkAttributes, flgObjectTypes, searchAllTerms, idClSearchIO,
				flgSubClSearchIn, tsRiferimento, advancedFilters, customFilters, colsOrderBy, flgDescOrderBy, flgSenzaPaginazione, numPagina, numRighePagina,
				overFlowLimit, flgSenzaTot, online, colsToReturn, finalita, filter);

	}

	public Object[] findRubricaObject(Connection conn, String token, String userIdLavoro, String filtroFullText, String[] checkAttributes,
			Integer searchAllTerms, String[] filterObjType, String matchByIndexerIn, String flgIndexerOverflowIn, String flg2ndCallIn, String denominazioneIO,
			Integer FlgInclAltreDenomIO, Integer FlgInclDenomStoricheIO, String CFIO, String PIVAIO, String FlgNotCodTipiSottoTipiIn,
			String FlgFisicaGiuridicaIn, String ListaCodTipiSottoTipiIO, String eMailIO, String CodRapidoIO, String CIProvSoggIO, Integer FlgSoloVldIO,
			String TsVldIO, String CodApplOwnerIO, String CodIstApplOwnerIO, Integer FlgRestrApplOwnerIO, Integer FlgCertificatiIO, Integer FlgInclAnnullatiIO,
			Integer IdSoggRubricaIO, Integer FlgInIndicePAIO, String CodAmmIPAIO, String CodAOOIPAIO, Integer IsSoggRubricaAppIO, Integer IdGruppoAppIO,
			String NomeGruppoAppIO, String StrInDenominazioneIn, String CriteriPersonalizzatiIO, String colsOrderBy, String flgDescOrderBy,
			Integer flgSenzaPaginazione, Integer numPagina, Integer numRighePagina, Integer OverFlowLimit, String FlgSenzaTot, Integer online,
			String colsToReturn, String finalita, String CodIstatComuneIndIn, String DesCittaIndIn, String restringiARubricaDiUOIn, String filtriAvanzatiIn,
			LuceneParameterFilter filter) throws VersionHandlerException {
		if (find == null)
			find = new Find(aLogger, specialLogger, luceneHandler);
		return find.findRubricaObject(conn, token, userIdLavoro, filtroFullText, checkAttributes, searchAllTerms, filterObjType, matchByIndexerIn,
				flgIndexerOverflowIn, flg2ndCallIn, denominazioneIO, FlgInclAltreDenomIO, FlgInclDenomStoricheIO, CFIO, PIVAIO, FlgNotCodTipiSottoTipiIn,
				FlgFisicaGiuridicaIn, ListaCodTipiSottoTipiIO, eMailIO, CodRapidoIO, CIProvSoggIO, FlgSoloVldIO, TsVldIO, CodApplOwnerIO, CodIstApplOwnerIO,
				FlgRestrApplOwnerIO, FlgCertificatiIO, FlgInclAnnullatiIO, IdSoggRubricaIO, FlgInIndicePAIO, CodAmmIPAIO, CodAOOIPAIO, IsSoggRubricaAppIO,
				IdGruppoAppIO, NomeGruppoAppIO, StrInDenominazioneIn, CriteriPersonalizzatiIO, colsOrderBy, flgDescOrderBy, flgSenzaPaginazione, numPagina,
				numRighePagina, OverFlowLimit, FlgSenzaTot, online, colsToReturn, finalita, CodIstatComuneIndIn, DesCittaIndIn, restringiARubricaDiUOIn,
				filtriAvanzatiIn, filter);

	}

	@Override
	public FindElenchiAlbiResultBean findElenchiAlbi(Connection conn, String token, String userIdLavoro, String filtroFullText, String[] checkAttributes,
			Integer searchAllTerms, String[] filterObjType, String matchByIndexer, String flgIndexerOverflow, String flg2ndCall, String criteriPersonalizzati,
			String advancedFilters, String colsOrderBy, String flgDescOrderBy, Integer flgSenzaPaginazione, Integer numPagina, Integer numRighePagina,
			Integer overFlowLimit, String flgSenzaTot, Integer online, String colsToReturn, String finalita, LuceneParameterFilter filter,
			BigDecimal idTipoElencoAlbo) throws VersionHandlerException {
		// TODO Auto-generated method stub
		if (find == null)
			find = new Find(aLogger, specialLogger, luceneHandler);
		return find.findElenchiAlbi(conn, token, userIdLavoro, filtroFullText, checkAttributes, searchAllTerms, filterObjType, matchByIndexer,
				flgIndexerOverflow, flg2ndCall, criteriPersonalizzati, advancedFilters, colsOrderBy, flgDescOrderBy, flgSenzaPaginazione, numPagina,
				numRighePagina, overFlowLimit, flgSenzaTot, online, colsToReturn, finalita, filter, idTipoElencoAlbo);
	}

}
