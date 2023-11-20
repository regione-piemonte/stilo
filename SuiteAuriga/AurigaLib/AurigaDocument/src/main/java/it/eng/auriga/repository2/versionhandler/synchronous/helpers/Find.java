/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreTrovarepositoryobjBean;
import it.eng.auriga.database.store.dmpk_elenchi_albi.bean.DmpkElenchiAlbiTrovacontenutielencoalboBean;
import it.eng.auriga.database.store.dmpk_elenchi_albi.store.impl.TrovacontenutielencoalboImpl;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.function.GenericFunction;
import it.eng.auriga.function.GenericFunctionBean;
import it.eng.auriga.function.bean.FindElenchiAlbiResultBean;
import it.eng.auriga.function.bean.FindRepositoryObjectBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.repository2.generic.ObjectHandlerException;
import it.eng.auriga.repository2.generic.VersionHandlerException;
import it.eng.auriga.repository2.lucene.LuceneException;
import it.eng.auriga.repository2.lucene.LuceneHandler;
import it.eng.auriga.repository2.lucene.LuceneHandlerException;
import it.eng.auriga.repository2.lucene.LuceneHelper;
import it.eng.auriga.repository2.lucene.LuceneNoDataFoundException;
import it.eng.auriga.repository2.lucene.LuceneOverflowException;
import it.eng.auriga.repository2.lucene.LuceneParameterFilter;
import it.eng.auriga.repository2.lucene.LuceneTooManyValuesException;
import it.eng.auriga.repository2.lucene.SearchType;
import it.eng.auriga.repository2.util.DBHelperSavePoint;
import it.eng.auriga.repository2.versionhandler.synchronous.SynchronousVersionHandler;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.jaxb.variabili.SezioneCache;
import it.eng.storeutil.AnalyzeResult;

import java.io.StringReader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.lucene.search.BooleanQuery.TooManyClauses;

import eng.storefunction.StoreProcedure;
import eng.storefunction.StoreProcedureException;

public class Find extends GenericHelper {

	public static final String _OBJECT_CATEGORY_REP_DOC = "REP_DOC";

	public static final String _OBJECT_CATEGORY_DEF_CTX_CL = "DEF_CTX_CL";

	public static final String _OBJECT_CATEGORY_DEF_CTX_SO = "DEF_CTX_SO";

	public static final String _OBJECT_CATEGORY_TOPOGRAFICO = "TOPOGRAFICO";

	public static final String _OBJECT_CATEGORY_RUBRICA = "RUBRICA";

	/*****************************************************************************************************
	 * API DI RICERCA mo
	 ***********************************************************************/

	public Find(Logger aLogger, Logger specialLogger, LuceneHandler luceneHandler) {
		super(aLogger, specialLogger, luceneHandler);
	}
    /*
     *  Filtro ricerca Lucene da Utilizzare con i ws
     */
    
	public String getMatchbyindexerin (FindRepositoryObjectBean pBeanIn,AurigaLoginBean lAurigaLoginBean) throws VersionHandlerException
	{
		
		if (pBeanIn.getFiltroFullText() != null && !"".equals(pBeanIn.getFiltroFullText()) && (luceneHandler != null)) {
			it.eng.auriga.function.Find fin;
			GenericFunctionBean gen = null;
			StoreProcedure store = null;
			String tmp[] = null;
			String schemaName = null;

			try {
				try {
					fin = new it.eng.auriga.function.Find();
					gen = fin.initWS(lAurigaLoginBean);
				} catch (Exception e1) {
					aLogger.error("Exception: " + e1.getMessage());
				}
				try {
					gen.getmConnection().setAutoCommit(false);
					try {
						DBHelperSavePoint.SetSavepoint(gen.getmConnection(), "VHFINDREPOSITORYOBJ");
					} catch (StoreProcedureException e) {
						aLogger.error("Exception: " + e.getMessage());
					}

					// prima di procedere ricavo il nome dello schema dalla connessione
					store = getStoreProcedureFromRepositoryDefinitionSingleton("GetConnSchema", "GetConnSchema");

					// Impostiamo i valori per la store procedure....
					try {
						setStoreProcedureParametersValues(gen.getmConnection(), store, new Object[] {});
					} catch (ObjectHandlerException e) {
						aLogger.error("Exception: " + e.getMessage());
					} catch (Exception e) {
						aLogger.error("Exception: " + e.getMessage());
					}

					aLogger.debug("Eseguo store GetConnSchema");
					try {
						store.execute();
					} catch (StoreProcedureException e) {
						aLogger.error("Exception: " + e.getMessage());
					}

					// leggo parametri di out
					tmp = getStoreProcedureParametersValues(store, new String[] { "SchemaOut" });
					schemaName = tmp[0];
				} catch (SQLException e2) {
					aLogger.error("Exception: " + e2.getMessage());
				}

				aLogger.debug("Effettuo la ricerca full text");
				String checkAttributes[] = new String[0];
				checkAttributes = pBeanIn.getCheckAttributes();
				aLogger.info("checkAttributes: " + checkAttributes.toString());
				Integer searchAllTerms = pBeanIn.getSearchAllTerms();
				String flagTipoRicerca = pBeanIn.getFlagTipoRicerca();
				aLogger.info("flagTipoRicerca: " + flagTipoRicerca);
				String advancedFilters = pBeanIn.getAdvancedFilters();
				aLogger.info("flagTipoRicerca: " + flagTipoRicerca);
				final int _RESULT_OUT_POSITION = 0;
				aLogger.info("flagTipoRicerca: " + flagTipoRicerca);
				aLogger.info("schemaName: " + schemaName);

				aLogger.info("gen.getToken(): " + gen.getToken());
				LuceneParameterFilter filter = null;
				// ricavo l'idDominioAut dal token
				String[] sTmp = getApplIstanzaFromToken(gen.getmConnection(), gen.getToken());
				String lista = null;
				// sTmp[2] = "1";//test

				String filtroFullText = replaceConfiguredWildCards(gen.getmConnection(), sTmp[2],
						pBeanIn.getFiltroFullText());
				aLogger.info("filtroFullText: " + filtroFullText);
				aLogger.info("sTmp[2]: " + sTmp[2]);
				String[] campiProtetti = null;
				try {
					// mi ricavo i campi protetti da passare alla ricerca di Lucene
                    try {
						campiProtetti = getPrivacyFieldList(gen.getmConnection(), gen.getToken());
					} catch (Exception e) {
						aLogger.error("Exception: " + e.getMessage());
					}
					// mi creo il bean di login da usare per il LuceneHandler
					// AurigaLoginBean login = getLoginBean(gen.getToken(), userIdLavoro);
					// chiamo l'api del Lucene Helper
					try {
						lista = luceneHandler.searchFullTextRepository(sTmp[2], // id dominio
								schemaName,
								// schemaName,// nome schema
								_OBJECT_CATEGORY_REP_DOC, checkAttributes, // attributi su cui cerco
								filtroFullText, // valore da cercare
								(searchAllTerms == null || searchAllTerms == 0)
										? SearchType.TYPE_SEARCH_AT_LEAST_ONE_TERM
										: SearchType.TYPE_SEARCH_ALL_TERMS,
								null, ("B".equals(flagTipoRicerca)) ? LuceneHelper._ID_DOC_PREFIX : null,
								campiProtetti[0], filter, lAurigaLoginBean);
					} catch (LuceneHandlerException e) {
						// TODO Auto-generated catch block
						aLogger.error("Exception: " + e.getMessage());
					}

					// se NON abbiamo sforato la soglia chiamo la store con:
					// - MatchByIndexerIn con gli oggetti trovati
					// - FlgIndexerOverflowIn null
					// - Flg2ndCallIn null
					aLogger.debug("risultato ricerca lucene: " + lista);
					aLogger.debug("Incrocio i risultati con il db ed effettuo la store");

					return lista;

				} catch (LuceneNoDataFoundException lndfe) {
					aLogger.error("nessun risultato trovato");
					return null;
				} catch (LuceneTooManyValuesException ltmve) {
					aLogger.error("troppi risultati: restringere i criteri di ricerca");
					throw new VersionHandlerException(SynchronousVersionHandler.SEARCH_OVERFLOW_COD,
							SynchronousVersionHandler.SEARCH_OVERFLOW_MSG);
				} catch (TooManyClauses tmc) {
					aLogger.error("troppi risultati: restringere i criteri di ricerca");
					throw new VersionHandlerException(SynchronousVersionHandler.SEARCH_MANY_CLAUSES_COD,
							SynchronousVersionHandler.SEARCH_MANY_CLAUSES_MSG);
				} catch (LuceneOverflowException loe) {
					// se non ci sono filtri ulteriori mando un'eccezione di overflow

					if (((advancedFilters.trim())
							.equals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" + "<SezioneCache>"
									+ "	<Variabile>" + "		<Nome>@Registrazioni</Nome>" + "		<Lista/>"
									+ "	</Variabile>" + "	<Variabile>" + "		<Nome>InteresseCessato</Nome>"
									+ "		<ValoreSemplice>I</ValoreSemplice>" + "	</Variabile>" + "</SezioneCache>"))
							&& pBeanIn.getIdFolderSearchIn() == null) {
						aLogger.error("trovato numero record sopra il massimo consentito");
						throw new VersionHandlerException(SynchronousVersionHandler.SEARCH_OVERFLOW_COD,
								SynchronousVersionHandler.SEARCH_OVERFLOW_MSG);
					}
					aLogger.error("sono andato in errore di overflow con lucene, per cui chiamo subito la store");
					// ABBIAMO SFORATO!!!!!!
					// chiamo la store
					// - null
					// - FlgIndexerOverflowIn 1
					// - Flg2ndCallIn null
					// recupero la store

					try {
						aLogger.debug(
								"riprovo a fare la ricerca con lucene limitandomi al risultato della store precedente inserendo il custom filter: ");
						// ottengo i campi privati per la ricerca di Lucene
						String[] privacyFields = null;
						try {
							privacyFields = getPrivacyFieldList(gen.getmConnection(), gen.getToken());
						} catch (Exception e1) {
							aLogger.error("Exception: " + e1.getMessage());
						}
						// mi creo il bean di login da usare per il LuceneHandler
						// AurigaLoginBean login = getLoginBean(token, userIdLavoro);
						// chiamo l'api del Lucene Helper
						String listaLucene2 = null;
						try {
							listaLucene2 = luceneHandler.searchFullTextRepository(sTmp[2], // id dominio
									schemaName, // nome schema
									_OBJECT_CATEGORY_REP_DOC, checkAttributes, // attributi su cui cerco
									filtroFullText, // valore da cercare
									(searchAllTerms == null || searchAllTerms == 0)
											? SearchType.TYPE_SEARCH_AT_LEAST_ONE_TERM
											: SearchType.TYPE_SEARCH_ALL_TERMS,
									null,
									// res[_RESULT_OUT_POSITION], // risultato
									// della
									// store
									// (lista
									// std)
									("B".equals(flagTipoRicerca)) ? LuceneHelper._ID_DOC_PREFIX : null,
									privacyFields[0], filter, lAurigaLoginBean);
						} catch (LuceneHandlerException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						aLogger.debug("risultati ricerca lucene secondo giro : " + listaLucene2);
						// - MatchByIndexerIn con gli oggetti trovati
						// - FlgIndexerOverflowIn null
						// - Flg2ndCallIn 1

						return listaLucene2;

					} catch (LuceneOverflowException e) {
						aLogger.error("trovato numero record sopra il massimo consentito");
						throw new VersionHandlerException(SynchronousVersionHandler.SEARCH_OVERFLOW_COD,
								SynchronousVersionHandler.SEARCH_OVERFLOW_MSG);
					} catch (LuceneNoDataFoundException e) {
						aLogger.error("nessun risultato trovato");
						return null;
						// throw new
						// VersionHandlerException(NO_DATA_FOUND_SEARCH_COD,NO_DATA_FOUND_SEARCH_MSG);
					} catch (LuceneException le) {
						aLogger.error("Errore nella ricerca tramite Lucene", le);
						throw new VersionHandlerException(SynchronousVersionHandler.LUCENE_ERR_COD,
								SynchronousVersionHandler.LUCENE_ERR_MSG + le.getMessage());
					}

				} catch (LuceneException le) {
					aLogger.error("Errore nella ricerca tramite Lucene", le);
					throw new VersionHandlerException(SynchronousVersionHandler.LUCENE_ERR_COD,
							SynchronousVersionHandler.LUCENE_ERR_MSG + le.getMessage());
				}finally
				{
					try {
						if(gen.getmConnection()!=null)
						{	
						 gen.getmConnection().close();
						} 
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						aLogger.error("gen.getmConnection()"+e.getMessage());
					}
				}

			} finally {
				try {
					if(gen.getmConnection()!=null)
					{	
					 gen.getmConnection().close();
					} 
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					aLogger.error("gen.getmConnection()"+e.getMessage());
				}

			}

		}// Chiudo if
		return null;
	}
	
	
	/**
       * 
       */
	public Object[] findRepositoryObject(Connection conn, String token, String userIdLavoro, String filtroFullText, String[] checkAttributes,
			String formatoEstremiReg, Integer searchAllTerms, String flgUdFolder, Long idFolderSearchIn, String flgSubfoderSearchIn, String advancedFilters,
			String customFilters, String colsOrderBy, String flgDescOrderBy, Integer flgSenzaPaginazione, Integer numPagina, Integer numRighePagina,
			Integer online, String colsToReturn, String percorsoRicerca, String flagTipoRicerca, String finalita, LuceneParameterFilter filter,
			Integer overFlowLimitIn) throws VersionHandlerException {

		Object ret[] = null;
		final Object emptyRes[] = new Object[] { SynchronousVersionHandler._LISTA_VUOTA_STR, "0", "0", SynchronousVersionHandler._LISTA_VUOTA_STR,
				SynchronousVersionHandler._LISTA_VUOTA_STR };
		// StoreProcedure store = null;
		String res[] = null;
		String privacyres[] = null;
		int numRecordOutputStore = 0;
		String checkAttrUsed[] = null;

		final int _RESULT_OUT_POSITION = 0;
		final int _NUM_REC_OUT_POSITION = 1;
		StoreProcedure store = null;
		String tmp[] = null;
		String schemaName = null;

		try {
			// log
			aLogger.info("Inizio findRepositoryObject");
			// autocommit e savepoint
			if (conn != null) 
				conn.setAutoCommit(false);
			
			DBHelperSavePoint.SetSavepoint(conn, "VHFINDREPOSITORYOBJ");

			// prima di procedere ricavo il nome dello schema dalla connessione
			store = getStoreProcedureFromRepositoryDefinitionSingleton("GetConnSchema", "GetConnSchema");

			// Impostiamo i valori per la store procedure....
			setStoreProcedureParametersValues(conn, store, new Object[] {});

			aLogger.debug("Eseguo store GetConnSchema");
			store.execute();

			// leggo parametri di out
			tmp = getStoreProcedureParametersValues(store, new String[] { "SchemaOut" });
			schemaName = tmp[0];

			// log
			aLogger.debug("Nome schema = " + schemaName);

			// controlli preliminari in input: validazione di eventuali xml per
			// criteri avanzati e custom

			// criteri avanzati ----> sezione cache
			if (advancedFilters != null && !"".equals(advancedFilters)) {
				try {
					aLogger.debug("Inserisco i filtri avanzati fatti in forma di sezione cache");
					// istanzio lo stringReader e vi associo la SezioneCache
					StringReader sr = new java.io.StringReader(advancedFilters);
					SezioneCache sc = (SezioneCache) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
				} catch (Exception e) {
					// log
					aLogger.error(e.getMessage(), e);
					// rilancio l'eccezione
					throw new VersionHandlerException(SynchronousVersionHandler.INPUT_VALIDATION_ERR_COD, SynchronousVersionHandler.INPUT_VALIDATION_ERR_MSG
							+ ": advanced filtes [" + e.getMessage() + "]");
				}
			}

			// criteri custom ----> lista std
			if (customFilters != null && !"".equals(customFilters)) {
				try {
					aLogger.debug("Inserisco i filtri custom");
					// istanzio lo stringReader e vi associo la SezioneCache
					StringReader sr = new java.io.StringReader(customFilters);
					Lista ls = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
				} catch (Exception e) {
					// log
					aLogger.error(e.getMessage(), e);
					// rilancio l'eccezione
					throw new VersionHandlerException(SynchronousVersionHandler.INPUT_VALIDATION_ERR_COD, SynchronousVersionHandler.INPUT_VALIDATION_ERR_MSG
							+ ": custom filtes [" + e.getMessage() + "]");
				}
			}

			// prima operazione: strip dei valori nulli da checkattributes
			// conto gli elementi..
			int numAttr = 0;
			for (int i = 0; i < checkAttributes.length; i++)
				if (checkAttributes[i] != null && !checkAttributes[i].equals(""))
					numAttr++;

			// istanzio checkAttrUsed
			checkAttrUsed = new String[numAttr];
			int j = 0;
			for (int i = 0; i < checkAttributes.length; i++) {
				if (checkAttributes[i] != null && !checkAttributes[i].equals("")) {
					checkAttrUsed[j] = checkAttributes[i];
					j++;
				}
			}

			// DIFFERENZIAZIONE DEL CASO 'BOZZE'
			// - il filtro per restringere a folder o unità doc. andrà passato
			// fisso a U
			// - nei filtri avanzati la variabile "NomeWorkspaceApp" va
			// valorizzata a <BOZZE>
			if ("B".equals(flagTipoRicerca)) {
				flgUdFolder = "U";
				// la seconda operazione viene fatta lato interfaccia
			}

			/*
			 * FILTRO VALORIZZATO
			 */
			// - se è valorizzato interrogo Lucene
			// - se Lucene non è configurato non eseguo la ricerca full-text ma
			// chiamo dirett. la stored
			if (filtroFullText != null && !"".equals(filtroFullText) && (luceneHandler != null)) {
				aLogger.debug("Effettuo la ricerca full text");
				if (numAttr == 0) {
					throw new VersionHandlerException("Non e' stato selezionato alcun attributo su cui effettuare la ricerca");
				}

				// ricavo l'idDominioAut dal token
				String[] sTmp = getApplIstanzaFromToken(conn, token);
				String lista = null;
				// sTmp[2] = "1";//test

				filtroFullText = replaceConfiguredWildCards(conn, sTmp[2], filtroFullText);
				try {
					// mi ricavo i campi protetti da passare alla ricerca di Lucene
					String[] campiProtetti = getPrivacyFieldList(conn, token);
					// mi creo il bean di login da usare per il LuceneHandler
					AurigaLoginBean login = getLoginBean(token, userIdLavoro);
					// chiamo l'api del Lucene Helper
					lista = luceneHandler.searchFullTextRepository(
							sTmp[2], // id dominio
							schemaName,// nome schema
							_OBJECT_CATEGORY_REP_DOC,
							checkAttrUsed, // attributi su cui cerco
							filtroFullText, // valore da cercare
							(searchAllTerms == null || searchAllTerms == 0) ? SearchType.TYPE_SEARCH_AT_LEAST_ONE_TERM : SearchType.TYPE_SEARCH_ALL_TERMS,
							null, ("B".equals(flagTipoRicerca)) ? LuceneHelper._ID_DOC_PREFIX : null, campiProtetti[0], filter, login);

					// se NON abbiamo sforato la soglia chiamo la store con:
					// - MatchByIndexerIn con gli oggetti trovati
					// - FlgIndexerOverflowIn null
					// - Flg2ndCallIn null
					aLogger.debug("risultato ricerca lucene: " + lista);
					aLogger.debug("Incrocio i risultati con il db ed effettuo la store");
					res = doDatabaseSearch(conn, token, userIdLavoro, filtroFullText, checkAttrUsed, formatoEstremiReg, searchAllTerms, flgUdFolder,
							idFolderSearchIn, flgSubfoderSearchIn, advancedFilters, customFilters, colsOrderBy, flgDescOrderBy, flgSenzaPaginazione, numPagina,
							numRighePagina, online, colsToReturn, percorsoRicerca, lista,// matchByIndexerIn
							"",// flgIndexerOverflowIn
							"",// flg2ndCallIn,
							flagTipoRicerca, finalita, overFlowLimitIn);

					aLogger.debug("risultati ricerca store: " + res);
					// completo l'array da ritornare con i parametri dalla
					// stringa
					ret = new Object[res.length];

					for (int i = 0; i < res.length; i++)
						ret[i] = res[i];

					// vediamo quanti sono i record in out
					numRecordOutputStore = (res[_NUM_REC_OUT_POSITION] == null) ? 0 : (new Integer(res[_NUM_REC_OUT_POSITION])).intValue();

					// la store non ha prodotto risultati
					// if (numRecordOutputStore == 0) {
					// return ret;
					// //throw new
					// VersionHandlerException(NO_DATA_FOUND_SEARCH_COD,NO_DATA_FOUND_SEARCH_MSG);
					// }

					// --------- >tutto OK
					return ret;

				} catch (LuceneNoDataFoundException lndfe) {
					aLogger.debug("nessun risultato trovato");
					return emptyRes;
					// throw new
					// VersionHandlerException(NO_DATA_FOUND_SEARCH_COD,NO_DATA_FOUND_SEARCH_MSG);
				} catch (LuceneTooManyValuesException ltmve) {
					aLogger.debug("troppi risultati: restringere i criteri di ricerca");
					throw new VersionHandlerException(SynchronousVersionHandler.SEARCH_OVERFLOW_COD, SynchronousVersionHandler.SEARCH_OVERFLOW_MSG);
				} catch (TooManyClauses tmc) {
					aLogger.debug("troppi risultati: restringere i criteri di ricerca");
					throw new VersionHandlerException(SynchronousVersionHandler.SEARCH_MANY_CLAUSES_COD, SynchronousVersionHandler.SEARCH_MANY_CLAUSES_MSG);
				} catch (LuceneOverflowException loe) {
					// se non ci sono filtri ulteriori mando un'eccezione di overflow

					if (((advancedFilters.trim()).equals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" + "<SezioneCache>" + "	<Variabile>"
							+ "		<Nome>@Registrazioni</Nome>" + "		<Lista/>" + "	</Variabile>" + "	<Variabile>" + "		<Nome>InteresseCessato</Nome>"
							+ "		<ValoreSemplice>I</ValoreSemplice>" + "	</Variabile>" + "</SezioneCache>"))
							&& idFolderSearchIn == null) {
						aLogger.debug("trovato numero record sopra il massimo consentito");
						throw new VersionHandlerException(SynchronousVersionHandler.SEARCH_OVERFLOW_COD, SynchronousVersionHandler.SEARCH_OVERFLOW_MSG);
					}
					aLogger.debug("sono andato in errore di overflow con lucene, per cui chiamo subito la store");
					// ABBIAMO SFORATO!!!!!!
					// chiamo la store
					// - null
					// - FlgIndexerOverflowIn 1
					// - Flg2ndCallIn null
					// recupero la store
					res = doDatabaseSearch(conn, token, userIdLavoro, filtroFullText, checkAttrUsed, formatoEstremiReg, searchAllTerms, flgUdFolder,
							idFolderSearchIn, flgSubfoderSearchIn, advancedFilters, customFilters, colsOrderBy, flgDescOrderBy, flgSenzaPaginazione, numPagina,
							numRighePagina, online, colsToReturn, percorsoRicerca, "",// matchByIndexerIn
							"1",// flgIndexerOverflowIn
							"",// flg2ndCallIn,
							flagTipoRicerca, finalita, overFlowLimitIn);
					aLogger.debug("risultati ricerca store: " + res);
					// completo l'array da ritornare con i parametri dalla
					// stringa
					ret = new Object[res.length];
					for (int i = 0; i < res.length; i++)
						ret[i] = res[i];

					// vediamo quanti sono i record in out
					numRecordOutputStore = (res[_NUM_REC_OUT_POSITION] == null) ? 0 : (new Integer(res[_NUM_REC_OUT_POSITION])).intValue();

					// la store non ha prodotto risultati
					if (numRecordOutputStore == 0) {
						return ret;
						// throw new
						// VersionHandlerException(NO_DATA_FOUND_SEARCH_COD,NO_DATA_FOUND_SEARCH_MSG);
					}

					try {
						aLogger.debug("riprovo a fare la ricerca con lucene limitandomi al risultato della store precedente inserendo il custom filter: "
								+ res[_RESULT_OUT_POSITION]);
						// ottengo i campi privati per la ricerca di Lucene
						String[] privacyFields = getPrivacyFieldList(conn, token);
						// mi creo il bean di login da usare per il LuceneHandler
						AurigaLoginBean login = getLoginBean(token, userIdLavoro);
						// chiamo l'api del Lucene Helper
						String listaLucene2 = luceneHandler.searchFullTextRepository(
								sTmp[2], // id dominio
								schemaName, // nome schema
								_OBJECT_CATEGORY_REP_DOC,
								checkAttrUsed, // attributi su cui cerco
								filtroFullText, // valore da cercare
								(searchAllTerms == null || searchAllTerms == 0) ? SearchType.TYPE_SEARCH_AT_LEAST_ONE_TERM : SearchType.TYPE_SEARCH_ALL_TERMS,
								res[_RESULT_OUT_POSITION], // risultato
															// della
															// store
															// (lista
															// std)
								("B".equals(flagTipoRicerca)) ? LuceneHelper._ID_DOC_PREFIX : null, privacyFields[0], filter, login);
						aLogger.debug("risultati ricerca lucene secondo giro : " + listaLucene2);
						// - MatchByIndexerIn con gli oggetti trovati
						// - FlgIndexerOverflowIn null
						// - Flg2ndCallIn 1
						res = doDatabaseSearch(conn, token, userIdLavoro, filtroFullText, checkAttrUsed, formatoEstremiReg, searchAllTerms, flgUdFolder,
								idFolderSearchIn, flgSubfoderSearchIn, advancedFilters, customFilters, colsOrderBy, flgDescOrderBy, flgSenzaPaginazione,
								numPagina, numRighePagina, online, colsToReturn, percorsoRicerca, listaLucene2,// matchByIndexerIn
								"",// flgIndexerOverflowIn
								"1",// flg2ndCallIn,
								flagTipoRicerca, finalita, overFlowLimitIn);
						aLogger.debug("risultati ricerca store secondo giro : " + res);
						// completo l'array da ritornare con i parametri dalla
						// stringa
						ret = new Object[res.length];
						for (int i = 0; i < res.length; i++)
							ret[i] = res[i];

						// vediamo quanti sono i record in out
						numRecordOutputStore = (res[_NUM_REC_OUT_POSITION] == null) ? 0 : (new Integer(res[_NUM_REC_OUT_POSITION])).intValue();
						// la store non ha prodotto risultati
						// if (numRecordOutputStore == 0) {
						// throw new
						// VersionHandlerException(NO_DATA_FOUND_SEARCH_COD,NO_DATA_FOUND_SEARCH_MSG);
						// }

						// tutto ok
						return ret;

					} catch (LuceneOverflowException e) {
						aLogger.debug("trovato numero record sopra il massimo consentito");
						throw new VersionHandlerException(SynchronousVersionHandler.SEARCH_OVERFLOW_COD, SynchronousVersionHandler.SEARCH_OVERFLOW_MSG);
					} catch (LuceneNoDataFoundException e) {
						aLogger.debug("nessun risultato trovato");
						return emptyRes;
						// throw new
						// VersionHandlerException(NO_DATA_FOUND_SEARCH_COD,NO_DATA_FOUND_SEARCH_MSG);
					} catch (LuceneException le) {
						aLogger.error("Errore nella ricerca tramite Lucene", le);
						throw new VersionHandlerException(SynchronousVersionHandler.LUCENE_ERR_COD, SynchronousVersionHandler.LUCENE_ERR_MSG + le.getMessage());
					}

				} catch (LuceneException le) {
					aLogger.error("Errore nella ricerca tramite Lucene", le);
					throw new VersionHandlerException(SynchronousVersionHandler.LUCENE_ERR_COD, SynchronousVersionHandler.LUCENE_ERR_MSG + le.getMessage());
				}

			}
			/*
			 * FILTRO NON VALORIZZATO = semplice chiamata alla store
			 */
			else {

				res = doDatabaseSearch(conn, token, userIdLavoro, filtroFullText, checkAttrUsed, formatoEstremiReg, searchAllTerms, flgUdFolder,
						idFolderSearchIn, flgSubfoderSearchIn, advancedFilters, customFilters, colsOrderBy, flgDescOrderBy, flgSenzaPaginazione, numPagina,
						numRighePagina, online, colsToReturn, percorsoRicerca, "",// matchByIndexerIn
						"",// flgIndexerOverflowIn
						"",// flg2ndCallIn,
						flagTipoRicerca, finalita, overFlowLimitIn);

				// completo l'array da ritornare con i parametri dalla stringa
				ret = new Object[res.length];
				for (int i = 0; i < res.length; i++)
					ret[i] = res[i];

				// vediamo quanti sono i record in out
				numRecordOutputStore = (res[_NUM_REC_OUT_POSITION] == null) ? 0 : (new Integer(res[_NUM_REC_OUT_POSITION])).intValue();
				// la store non ha prodotto risultati
				// if (numRecordOutputStore == 0) {
				// throw new
				// VersionHandlerException(NO_DATA_FOUND_SEARCH_COD,NO_DATA_FOUND_SEARCH_MSG);
				// }

				// tutto ok
				return ret;
			}

		} catch (LuceneHandlerException ex2) {
			// ROLLBACK
			try {
				DBHelperSavePoint.RollbackToSavepoint(conn, "VHFINDREPOSITORYOBJ");
			} catch (Exception e) {
			}
			// log
			aLogger.error("Errore in FIND REPOSITORY OBJECT. ErrMsg: " + ex2.getMessage(), ex2);
			// rilancio l'eccezione
			throw new VersionHandlerException(SynchronousVersionHandler.LUCENE_HANDLER_COD, SynchronousVersionHandler.LUCENE_HANDLER_MSG + "("
					+ ex2.getMessage() + ")");

		} catch (VersionHandlerException ex1) {
			// ROLLBACK
			try {
				DBHelperSavePoint.RollbackToSavepoint(conn, "VHFINDREPOSITORYOBJ");
			} catch (Exception e) {
			}
			// log
			aLogger.error("Errore in FIND REPOSITORY OBJECT.\nErrcode:" + ex1.getCodice() + "\nErrMsg: " + ex1.getMessage(), ex1);
			// rilancio l'eccezione
			throw ex1;
		}
		// ************ eccezione IMPREVISTA *************
		catch (Exception ex) {
			// ROLLBACK
			try {
				DBHelperSavePoint.RollbackToSavepoint(conn, "VHFINDREPOSITORYOBJ");
			} catch (Exception e) {
			}
			// log
			aLogger.debug("Errore GENERICO in FIND REPOSITORY OBJECT.\nException: " + ex.getMessage(), ex);
			// rilancio l'eccezione trasformata
			throw new VersionHandlerException(SynchronousVersionHandler.IMPREVISTO_COD, SynchronousVersionHandler.IMPREVISTO_MSG + "(" + ex.getMessage() + ")");
		} finally {
			aLogger.info("Fine findRepositoryObject");
		}
	}

	private AurigaLoginBean getLoginBean(String token, String userIdLavoro) {
		AurigaLoginBean login = new AurigaLoginBean();
		login.setIdUserLavoro(userIdLavoro);
		login.setToken(token);
		return login;
	}

	/*
	 * Metodo privato che effettua una riconversione delle wildcard configurate x l'applicativo in quella di lucene x la ricerca fulltext
	 */
	private String replaceConfiguredWildCards(Connection con, String idDominio, String fulltext) {

		ResultSet rs = null;
		Statement stmt = null;
		String ret = fulltext;

		if (fulltext == null)
			return null;

		try {
			aLogger.info("Inizio replaceConfiguredWildCards...");

			if (idDominio == null || "".equals(idDominio.trim())) {
				idDominio = "NULL";
			}

			String sql = "select DMFN_TRASF_STR_X_EXT_FT_SEARCH(" + idDominio + ", '" + fulltext.replaceAll("'", "''") + "') from dual";
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
				ret = rs.getString(1);
			return ret;
		} catch (Exception e) {
			aLogger.warn(e.getMessage(), e);
			return fulltext;
		} finally {
			try {
				stmt.close();
			} catch (Exception e) {
			}
			try {
				rs.close();
			} catch (Exception e) {
			}
			aLogger.info("Fine replaceConfiguredWildCards...");
		}

	}

	/**
	 * chiamata alla store procedure che restituisce la lista dei campi 'sensibili' per la privacy
	 * 
	 * @param conn
	 * @param token
	 * @return
	 * @throws Exception
	 */
	private String[] getPrivacyFieldList(Connection conn, String token) throws Exception {
		StoreProcedure store = null;
		String res[] = null;
		int errCode = 0;
		String errMsg = "";
		final String TIPO_COMBO = "PROTECTED_ATTRIBUTES";
		try {
			store = getStoreProcedureFromRepositoryDefinitionSingleton("dmfn_Load_Combo", "dmfn_Load_Combo");

			setStoreProcedureParametersValues(conn, store, new Object[] { "CodIdConnectionToken", token, // token di connessione
					"TipoComboIn", TIPO_COMBO });
			// log
			aLogger.debug("Eseguo store dmfn_Load_Combo");
			// esecuzione della store proc
			store.execute();
			res = getStoreProcedureParametersValues(store, new String[] { "ListaXMLOut" });
		} catch (StoreProcedureException e) {
			String err[] = getInfoFromStoreProcedureException(store, e);
			errCode = Integer.parseInt(err[0]);
			errMsg = err[1];
			aLogger.error("Errore STORE in FIND REPOSITORY OBJECT.\nErrcode:" + errCode + "\nErrMsg: " + errMsg, e);
			throw new VersionHandlerException(errCode, errMsg);
		} finally {
			aLogger.info("Fine getPrivacyFieldList");
		}

		return res;
	}

	private String[] doDatabaseSearch(Connection conn, String token, String userIdLavoro, String filtroFullText, String[] checkAttributes,
			String formatoEstremiReg, Integer searchAllTerms, String flgUdFolder, Long idFolderSearchIn, String flgSubfoderSearchIn, String advancedFilters,
			String customFilters, String colsOrderBy, String flgDescOrderBy, Integer flgSenzaPaginazione, Integer numPagina, Integer numRighePagina,
			Integer online, String colsToReturn, String percorsoRicerca, String matchByIndexerIn, String flgIndexerOverflowIn, String flg2ndCallIn,
			String flagTipoRicerca, String finalita, Integer overFlowLimitIn) throws Exception {

		StoreProcedure store = null;
		String res[] = null;
		Object ret[] = null;
		int errCode = 0;
		String errMsg = "";

		try {
			aLogger.info("Inizio doDatabaseSearch");
			// if ("B".equals(flagTipoRicerca)) {
			// store =
			// getStoreProcedureFromRepositoryDefinitionSingleton("TrovaUDInBozze",
			// "TrovaUDInBozze");
			//
			// // Impostiamo i valori per la store procedure....
			// setStoreProcedureParametersValues(conn, store, new Object[] {
			// "CodIdConnectionTokenIn", token, //token di connessione
			// "IdUserLavoroIn", userIdLavoro, // user id lavoro
			// //TODO come settare FlgPreimpostaFiltroIn????
			// "MatchByIndexerIn", matchByIndexerIn,
			// "FlgIndexerOverflowIn", flgIndexerOverflowIn,
			// "Flg2ndCallIn", flg2ndCallIn,
			// "FlgFmtEstremiRegIO",
			// (formatoEstremiReg==null)?"":formatoEstremiReg,
			// "CriteriAvanzatiIO", (advancedFilters==null)?"":advancedFilters,
			// "CriteriPersonalizzatiIO",
			// (customFilters==null)?"":customFilters,
			// "ColOrderByIO", (colsOrderBy==null)?"":colsOrderBy,
			// "FlgDescOrderByIO", (flgDescOrderBy==null)?"0":flgDescOrderBy,
			// "FlgSenzaPaginazioneIn",
			// (flgSenzaPaginazione==null)?"0":flgSenzaPaginazione.toString(),
			// "NroPaginaIO", (numPagina==null)?"":numPagina.toString(),
			// "BachSizeIO",
			// (numRighePagina==null)?"":numRighePagina.toString(),
			// "FlgBatchSearchIn", (online==null)?"0":online.toString(),
			// "ColToReturnIn", (colsToReturn==null)?"":colsToReturn
			// });
			//
			// //log
			// aLogger.debug("Eseguo store TrovaRepositoryObj");
			// //esecuzione della store proc
			// store.execute();
			//
			// // leggo parametri di out
			// res = getStoreProcedureParametersValues( store, new String[] {
			// "ResultOut",
			// "NroTotRecOut",
			// "NroRecInPaginaOut"
			// });
			//
			// }
			// else {
			store = getStoreProcedureFromRepositoryDefinitionSingleton("TrovaRepositoryObj", "TrovaRepositoryObj");

			String overflowLimitValue = overFlowLimitIn != null ? overFlowLimitIn.toString() : null;

			// Impostiamo i valori per la store procedure....
			setStoreProcedureParametersValues(conn, store, new Object[] {
					"CodIdConnectionTokenIn",
					token, // token di connessione
					"IdUserLavoroIn",
					userIdLavoro, // user id lavoro
					// TODO come settare FlgPreimpostaFiltroIn????
					"MatchByIndexerIn", matchByIndexerIn, "FlgIndexerOverflowIn", flgIndexerOverflowIn, "Flg2ndCallIn", flg2ndCallIn, "FlgFmtEstremiRegIO",
					(formatoEstremiReg == null) ? "" : formatoEstremiReg, "FlgUDFolderIO", (flgUdFolder == null) ? "" : flgUdFolder, "CercaInFolderIO",
					(idFolderSearchIn == null) ? "" : idFolderSearchIn.toString(), "FlgCercaInSubFolderIO",
					(flgSubfoderSearchIn == null) ? "" : flgSubfoderSearchIn, "CriteriAvanzatiIO", (advancedFilters == null) ? "" : advancedFilters,
					"CriteriPersonalizzatiIO", (customFilters == null) ? "" : customFilters, "ColOrderByIO", (colsOrderBy == null) ? "" : colsOrderBy,
					"FlgDescOrderByIO", (flgDescOrderBy == null) ? "0" : flgDescOrderBy, "FlgSenzaPaginazioneIn",
					(flgSenzaPaginazione == null) ? "0" : flgSenzaPaginazione.toString(), "NroPaginaIO", (numPagina == null) ? "" : numPagina.toString(),
					"BachSizeIO", (numRighePagina == null) ? "" : numRighePagina.toString(), "OverFlowLimitIn", overflowLimitValue, "FlgBatchSearchIn",
					(online == null) ? "0" : online.toString(), "ColToReturnIn", (colsToReturn == null) ? "" : colsToReturn, "PercorsoRicercaXMLIO",
					(percorsoRicerca == null) ? "" : percorsoRicerca, "FinalitaIn", (finalita == null) ? "" : finalita });

			// log
			aLogger.debug("Eseguo store TrovaRepositoryObj");
			// esecuzione della store proc
			store.execute();

			// leggo parametri di out
			res = getStoreProcedureParametersValues(store, new String[] { "ResultOut", "NroTotRecOut", "NroRecInPaginaOut", "PercorsoRicercaXMLIO",
					"DettagliCercaInFolderOut", "ErrorMessage", "BachSizeIO", "NroPaginaIO", "ErrorCode" });
			// }

			return res;

		} catch (StoreProcedureException ex0) {
			// ROLLBACK
			try {
				DBHelperSavePoint.RollbackToSavepoint(conn, "VHFINDREPOSITORYOBJ");
			} catch (Exception e) {
			}
			// Qualcosa è andato storto!!!!
			String err[] = getInfoFromStoreProcedureException(store, ex0);
			errCode = Integer.parseInt(err[0]);
			errMsg = err[1];
			
			if (errCode == 1011) {
				aLogger.debug("Errore 1011 di OVERFLOW in FIND REPOSITORY OBJECT.\nErrcode:" + errCode + "\nErrMsg: " + errMsg, ex0);
				throw new VersionHandlerException(SynchronousVersionHandler.SEARCH_OVERFLOW_COD, SynchronousVersionHandler.SEARCH_OVERFLOW_MSG);
			} else {
				aLogger.error("Errore STORE in FIND REPOSITORY OBJECT.\nErrcode:" + errCode + "\nErrMsg: " + errMsg, ex0);
				throw new VersionHandlerException(errCode, errMsg);
			}
		} finally {
			aLogger.info("Fine doDatabaseSearch");
		}

	}

	public Object[] findProcessObject(Connection conn, String token, String userIdLavoro, String filtroFullText, String[] checkAttributes,
			String formatoEstremiReg, Integer searchAllTerms, String advancedFilters, String customFilters, String colsOrderBy, String flgDescOrderBy,
			Integer flgSenzaPaginazione, Integer numPagina, Integer numRighePagina, Integer online, String colsToReturn, String attoriToReturn,
			String attrCustomToReturn, String finalita, LuceneParameterFilter filter) throws VersionHandlerException {

		Object ret[] = null;
		final Object emptyRes[] = new Object[] { SynchronousVersionHandler._LISTA_VUOTA_STR, "0", "0", SynchronousVersionHandler._LISTA_VUOTA_STR,
				SynchronousVersionHandler._LISTA_VUOTA_STR };
		// StoreProcedure store = null;
		String res[] = null;
		int numRecordOutputStore = 0;
		String checkAttrUsed[] = null;

		final int _RESULT_OUT_POSITION = 0;
		final int _NUM_REC_OUT_POSITION = 1;
		StoreProcedure store = null;
		String tmp[] = null;
		String schemaName = null;

		try {
			// log
			aLogger.info("Inizio findProcessObject");
			// autocommit e savepoint
			if (conn != null) 
				conn.setAutoCommit(false);
			
			DBHelperSavePoint.SetSavepoint(conn, "VHFINDPROCOBJ");

			// prima di procedere ricavo il nome dello schema dalla connessione
			store = getStoreProcedureFromRepositoryDefinitionSingleton("GetConnSchema", "GetConnSchema");

			// Impostiamo i valori per la store procedure....
			setStoreProcedureParametersValues(conn, store, new Object[] {});

			aLogger.debug("Eseguo store GetConnSchema");
			store.execute();

			// leggo parametri di out
			tmp = getStoreProcedureParametersValues(store, new String[] { "SchemaOut" });
			schemaName = tmp[0];

			// log
			aLogger.debug("Nome schema = " + schemaName);

			// controlli preliminari in input: validazione di eventuali xml per
			// criteri avanzati e custom

			// criteri avanzati ----> sezione cache
			if (advancedFilters != null && !"".equals(advancedFilters)) {
				try {
					// istanzio lo stringReader e vi associo la SezioneCache
					StringReader sr = new java.io.StringReader(advancedFilters);
					SezioneCache sc = (SezioneCache) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
				} catch (Exception e) {
					// log
					aLogger.error(e.getMessage(), e);
					// rilancio l'eccezione
					throw new VersionHandlerException(SynchronousVersionHandler.INPUT_VALIDATION_ERR_COD, SynchronousVersionHandler.INPUT_VALIDATION_ERR_MSG
							+ ": advanced filtes [" + e.getMessage() + "]");
				}
			}

			// criteri custom ----> lista std
			if (customFilters != null && !"".equals(customFilters)) {
				try {
					// istanzio lo stringReader e vi associo la SezioneCache
					StringReader sr = new java.io.StringReader(customFilters);
					Lista ls = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
				} catch (Exception e) {
					// log
					aLogger.error(e.getMessage(), e);
					// rilancio l'eccezione
					throw new VersionHandlerException(SynchronousVersionHandler.INPUT_VALIDATION_ERR_COD, SynchronousVersionHandler.INPUT_VALIDATION_ERR_MSG
							+ ": custom filtes [" + e.getMessage() + "]");
				}
			}

			// prima operazione: strip dei valori nulli da checkattributes
			// conto gli elementi..
			int numAttr = 0;
			for (int i = 0; i < checkAttributes.length; i++)
				if (checkAttributes[i] != null && !checkAttributes[i].equals(""))
					numAttr++;

			// istanzio checkAttrUsed
			checkAttrUsed = new String[numAttr];
			int j = 0;
			for (int i = 0; i < checkAttributes.length; i++) {
				if (checkAttributes[i] != null && !checkAttributes[i].equals("")) {
					checkAttrUsed[j] = checkAttributes[i];
					j++;
				}
			}

			/*
			 * FILTRO VALORIZZATO
			 */
			// - se è valorizzato interrogo Lucene
			// - se Lucene non è configurato non eseguo la ricerca full-text ma
			// chiamo dirett. la stored
			if (filtroFullText != null && !"".equals(filtroFullText) && (luceneHandler != null)) {

				if (numAttr == 0) {
					throw new VersionHandlerException("Non e' stato selezionato alcun attributo su cui effettuare la ricerca");
				}

				// ricavo l'idDominioAut dal token
				String[] sTmp = getApplIstanzaFromToken(conn, token);
				String lista = null;
				// sTmp[2] = "1";//test
				try {
					// ottengo i campi privati per la ricerca di Lucene
					String[] privacyFields = getPrivacyFieldList(conn, token);
					filtroFullText = replaceConfiguredWildCards(conn, sTmp[2], filtroFullText);
					// creo il bean di Login
					AurigaLoginBean login = getLoginBean(token, userIdLavoro);
					// chiamo l'api del Lucene Helper
					lista = luceneHandler.searchFullTextProcess(
							sTmp[2], // id dominio
							schemaName,// nome schema
							_OBJECT_CATEGORY_DEF_CTX_CL,
							checkAttrUsed, // attributi su cui cerco
							filtroFullText, // valore da cercare
							(searchAllTerms == null || searchAllTerms == 0) ? SearchType.TYPE_SEARCH_AT_LEAST_ONE_TERM : SearchType.TYPE_SEARCH_ALL_TERMS,
							null, null, privacyFields[0], filter, login);

					// se NON abbiamo sforato la soglia chiamo la store con:
					// - MatchByIndexerIn con gli oggetti trovati
					// - FlgIndexerOverflowIn null
					// - Flg2ndCallIn null

					res = doDatabaseSearchForProcess(conn, token, userIdLavoro, filtroFullText, checkAttrUsed, formatoEstremiReg, searchAllTerms,
							advancedFilters, customFilters, colsOrderBy, flgDescOrderBy, flgSenzaPaginazione, numPagina, numRighePagina, online, colsToReturn,
							attoriToReturn, attrCustomToReturn, lista,// matchByIndexerIn
							"",// flgIndexerOverflowIn
							"",// flg2ndCallIn,
							finalita);

					// completo l'array da ritornare con i parametri dalla
					// stringa
					ret = new Object[res.length];

					for (int i = 0; i < res.length; i++)
						ret[i] = res[i];

					// vediamo quanti sono i record in out
					numRecordOutputStore = (res[_NUM_REC_OUT_POSITION] == null) ? 0 : (new Integer(res[_NUM_REC_OUT_POSITION])).intValue();

					// la store non ha prodotto risultati
					// if (numRecordOutputStore == 0) {
					// return ret;
					// //throw new
					// VersionHandlerException(NO_DATA_FOUND_SEARCH_COD,NO_DATA_FOUND_SEARCH_MSG);
					// }

					// --------- >tutto OK
					return ret;

				} catch (LuceneNoDataFoundException lndfe) {
					aLogger.debug("nessun risultato trovato");
					return emptyRes;
					// throw new
					// VersionHandlerException(NO_DATA_FOUND_SEARCH_COD,NO_DATA_FOUND_SEARCH_MSG);
				} catch (LuceneTooManyValuesException ltmve) {
					aLogger.debug("troppi risultati: restringere i criteri di ricerca");
					throw new VersionHandlerException(SynchronousVersionHandler.SEARCH_OVERFLOW_COD, SynchronousVersionHandler.SEARCH_OVERFLOW_MSG);
				} catch (TooManyClauses tmc) {
					aLogger.debug("troppi risultati: restringere i criteri di ricerca");
					throw new VersionHandlerException(SynchronousVersionHandler.SEARCH_MANY_CLAUSES_COD, SynchronousVersionHandler.SEARCH_MANY_CLAUSES_MSG);
				} catch (LuceneOverflowException loe) {

					// ABBIAMO SFORATO!!!!!!
					// chiamo la store
					// - null
					// - FlgIndexerOverflowIn 1
					// - Flg2ndCallIn null
					// recupero la store
					res = doDatabaseSearchForProcess(conn, token, userIdLavoro, filtroFullText, checkAttrUsed, formatoEstremiReg, searchAllTerms,
							advancedFilters, customFilters, colsOrderBy, flgDescOrderBy, flgSenzaPaginazione, numPagina, numRighePagina, online, colsToReturn,
							attoriToReturn, attrCustomToReturn, "",// matchByIndexerIn
							"1",// flgIndexerOverflowIn
							"",// flg2ndCallIn,
							finalita);

					// completo l'array da ritornare con i parametri dalla
					// stringa
					ret = new Object[res.length];
					for (int i = 0; i < res.length; i++)
						ret[i] = res[i];

					// vediamo quanti sono i record in out
					numRecordOutputStore = (res[_NUM_REC_OUT_POSITION] == null) ? 0 : (new Integer(res[_NUM_REC_OUT_POSITION])).intValue();

					// la store non ha prodotto risultati
					if (numRecordOutputStore == 0) {
						return ret;
						// throw new
						// VersionHandlerException(NO_DATA_FOUND_SEARCH_COD,NO_DATA_FOUND_SEARCH_MSG);
					}

					try {
						// ottengo i campi privati per la ricerca di Lucene
						String[] privacyFields = getPrivacyFieldList(conn, token);
						// creo il bean di Login
						AurigaLoginBean login = getLoginBean(token, userIdLavoro);
						// chiamo l'api del Lucene Helper
						String listaLucene2 = luceneHandler.searchFullTextProcess(
								sTmp[2], // id dominio
								schemaName, // nome schema
								_OBJECT_CATEGORY_DEF_CTX_CL,
								checkAttrUsed, // attributi su cui cerco
								filtroFullText, // valore da cercare
								(searchAllTerms == null || searchAllTerms == 0) ? SearchType.TYPE_SEARCH_AT_LEAST_ONE_TERM : SearchType.TYPE_SEARCH_ALL_TERMS,
								res[_RESULT_OUT_POSITION], // risultato
															// della
															// store
															// (lista
															// std)
								null, privacyFields[0], filter, login);

						// - MatchByIndexerIn con gli oggetti trovati
						// - FlgIndexerOverflowIn null
						// - Flg2ndCallIn 1
						res = doDatabaseSearchForProcess(conn, token, userIdLavoro, filtroFullText, checkAttrUsed, formatoEstremiReg, searchAllTerms,
								advancedFilters, customFilters, colsOrderBy, flgDescOrderBy, flgSenzaPaginazione, numPagina, numRighePagina, online,
								colsToReturn, attoriToReturn, attrCustomToReturn, listaLucene2,// matchByIndexerIn
								"",// flgIndexerOverflowIn
								"1",// flg2ndCallIn,
								finalita);

						// completo l'array da ritornare con i parametri dalla
						// stringa
						ret = new Object[res.length];
						for (int i = 0; i < res.length; i++)
							ret[i] = res[i];

						// vediamo quanti sono i record in out
						numRecordOutputStore = (res[_NUM_REC_OUT_POSITION] == null) ? 0 : (new Integer(res[_NUM_REC_OUT_POSITION])).intValue();
						// la store non ha prodotto risultati
						// if (numRecordOutputStore == 0) {
						// throw new
						// VersionHandlerException(NO_DATA_FOUND_SEARCH_COD,NO_DATA_FOUND_SEARCH_MSG);
						// }

						// tutto ok
						return ret;

					} catch (LuceneOverflowException e) {
						aLogger.debug("trovato numero record sopra il massimo consentito");
						throw new VersionHandlerException(SynchronousVersionHandler.SEARCH_OVERFLOW_COD, SynchronousVersionHandler.SEARCH_OVERFLOW_MSG);
					} catch (LuceneNoDataFoundException e) {
						aLogger.debug("nessun risultato trovato");
						return emptyRes;
						// throw new
						// VersionHandlerException(NO_DATA_FOUND_SEARCH_COD,NO_DATA_FOUND_SEARCH_MSG);
					} catch (LuceneException le) {
						aLogger.error("Errore nella ricerca tramite Lucene", le);
						throw new VersionHandlerException(SynchronousVersionHandler.LUCENE_ERR_COD, SynchronousVersionHandler.LUCENE_ERR_MSG + le.getMessage());
					}

				} catch (LuceneException le) {
					aLogger.error("Errore nella ricerca tramite Lucene", le);
					throw new VersionHandlerException(SynchronousVersionHandler.LUCENE_ERR_COD, SynchronousVersionHandler.LUCENE_ERR_MSG + le.getMessage());
				}

			}
			/*
			 * FILTRO NON VALORIZZATO = semplice chiamata alla store
			 */
			else {

				res = doDatabaseSearchForProcess(conn, token, userIdLavoro, filtroFullText, checkAttrUsed, formatoEstremiReg, searchAllTerms, advancedFilters,
						customFilters, colsOrderBy, flgDescOrderBy, flgSenzaPaginazione, numPagina, numRighePagina, online, colsToReturn, attoriToReturn,
						attrCustomToReturn, "",// matchByIndexerIn
						"",// flgIndexerOverflowIn
						"",// flg2ndCallIn,
						finalita);

				// completo l'array da ritornare con i parametri dalla stringa
				ret = new Object[res.length];
				for (int i = 0; i < res.length; i++)
					ret[i] = res[i];

				// vediamo quanti sono i record in out
				numRecordOutputStore = (res[_NUM_REC_OUT_POSITION] == null) ? 0 : (new Integer(res[_NUM_REC_OUT_POSITION])).intValue();
				// la store non ha prodotto risultati
				// if (numRecordOutputStore == 0) {
				// throw new
				// VersionHandlerException(NO_DATA_FOUND_SEARCH_COD,NO_DATA_FOUND_SEARCH_MSG);
				// }

				// tutto ok
				return ret;
			}

		} catch (LuceneHandlerException ex2) {
			// ROLLBACK
			try {
				DBHelperSavePoint.RollbackToSavepoint(conn, "VHFINDPROCOBJ");
			} catch (Exception e) {
			}
			// log
			aLogger.error("Errore in FIND PROC OBJECT. ErrMsg: " + ex2.getMessage(), ex2);
			// rilancio l'eccezione
			throw new VersionHandlerException(SynchronousVersionHandler.LUCENE_HANDLER_COD, SynchronousVersionHandler.LUCENE_HANDLER_MSG + "("
					+ ex2.getMessage() + ")");

		} catch (VersionHandlerException ex1) {
			// ROLLBACK
			try {
				DBHelperSavePoint.RollbackToSavepoint(conn, "VHFINDPROCOBJ");
			} catch (Exception e) {
			}
			// log
			aLogger.error("Errore in FIND PROC OBJECT.\nErrcode:" + ex1.getCodice() + "\nErrMsg: " + ex1.getMessage(), ex1);
			// rilancio l'eccezione
			throw ex1;
		}
		// ************ eccezione IMPREVISTA *************
		catch (Exception ex) {
			// ROLLBACK
			try {
				DBHelperSavePoint.RollbackToSavepoint(conn, "VHFINDPROCOBJ");
			} catch (Exception e) {
			}
			// log
			aLogger.debug("Errore GENERICO in FIND PROC OBJECT.\nException: " + ex.getMessage(), ex);
			// rilancio l'eccezione trasformata
			throw new VersionHandlerException(SynchronousVersionHandler.IMPREVISTO_COD, SynchronousVersionHandler.IMPREVISTO_MSG + "(" + ex.getMessage() + ")");
		} finally {
			aLogger.info("Fine findProcessObject");
		}
	}

	private String[] doDatabaseSearchForProcess(Connection conn, String token, String userIdLavoro, String filtroFullText, String[] checkAttributes,
			String formatoEstremiReg, Integer searchAllTerms, String advancedFilters, String customFilters, String colsOrderBy, String flgDescOrderBy,
			Integer flgSenzaPaginazione, Integer numPagina, Integer numRighePagina, Integer online, String colsToReturn, String attoriToReturn,
			String attrCustomToReturn, String matchByIndexerIn, String flgIndexerOverflowIn, String flg2ndCallIn, String finalita) throws Exception {

		StoreProcedure store = null;
		String res[] = null;
		Object ret[] = null;
		int errCode = 0;
		String errMsg = "";

		try {
			aLogger.info("Inizio doDatabaseSearchForProcess");
			store = getStoreProcedureFromRepositoryDefinitionSingleton("TrovaProcessi", "TrovaProcessi");

			// Impostiamo i valori per la store procedure....
			setStoreProcedureParametersValues(conn, store, new Object[] {
					"CodIdConnectionTokenIn",
					token, // token di connessione
					"IdUserLavoroIn",
					userIdLavoro, // user id lavoro
					// TODO come settare FlgPreimpostaFiltroIn????
					"MatchByIndexerIn", matchByIndexerIn, "FlgIndexerOverflowIn", flgIndexerOverflowIn, "Flg2ndCallIn", flg2ndCallIn, "FlgFmtEstremiRegIO",
					(formatoEstremiReg == null) ? "" : formatoEstremiReg, "CriteriAvanzatiIO", (advancedFilters == null) ? "" : advancedFilters,
					"CriteriPersonalizzatiIO", (customFilters == null) ? "" : customFilters, "ColOrderByIO", (colsOrderBy == null) ? "" : colsOrderBy,
					"FlgDescOrderByIO", (flgDescOrderBy == null) ? "0" : flgDescOrderBy, "FlgSenzaPaginazioneIn",
					(flgSenzaPaginazione == null) ? "0" : flgSenzaPaginazione.toString(), "NroPaginaIO", (numPagina == null) ? "" : numPagina.toString(),
					"BachSizeIO", (numRighePagina == null) ? "" : numRighePagina.toString(), "FlgBatchSearchIn", (online == null) ? "0" : online.toString(),
					"ColToReturnIn", (colsToReturn == null) ? "" : colsToReturn, "AttoriToReturnIn", (attoriToReturn == null) ? "" : attoriToReturn,
					"AttrCustomToReturnIn", (attrCustomToReturn == null) ? "" : attrCustomToReturn, "FinalitaIn", (finalita == null) ? "" : finalita });

			// log
			aLogger.debug("Eseguo store TrovaProcessi");
			// esecuzione della store proc
			store.execute();

			// leggo parametri di out
			res = getStoreProcedureParametersValues(store, new String[] { "ResultOut", "NroTotRecOut", "NroRecInPaginaOut", "ErrorMessage" });

			return res;

		} catch (StoreProcedureException ex0) {
			// ROLLBACK
			try {
				DBHelperSavePoint.RollbackToSavepoint(conn, "VHFINDPROCOBJ");
			} catch (Exception e) {
			}
			// Qualcosa è andato storto!!!!
			String err[] = getInfoFromStoreProcedureException(store, ex0);
			errCode = Integer.parseInt(err[0]);
			errMsg = err[1];

			if (errCode == 1011) {
				aLogger.debug("Errore 1011 di OVERFLOW in FIND PROC OBJECT.\nErrcode:" + errCode + "\nErrMsg: " + errMsg, ex0);
				throw new VersionHandlerException(SynchronousVersionHandler.SEARCH_OVERFLOW_COD, SynchronousVersionHandler.SEARCH_OVERFLOW_MSG);
			} else {
				aLogger.error("Errore STORE in FIND PROC OBJECT.\nErrcode:" + errCode + "\nErrMsg: " + errMsg, ex0);
				throw new VersionHandlerException(errCode, errMsg);
			}
		} finally {
			aLogger.info("Fine doDatabaseSearchForProcess");
		}

	}

	public Object[] findOrgStructureObject(Connection conn, String token, String userIdLavoro, String filtroFullText, String[] checkAttributes,
			Integer searchAllTerms, String[] flgObjectTypes, Integer idUoSearchIn, String flgSubUoSearchIn, String tsRiferimento, String advancedFilters,
			String customFilters, String colsOrderBy, String flgDescOrderBy, Integer flgSenzaPaginazione, Integer numPagina, Integer numRighePagina,
			Integer online, String colsToReturn, String percorsoRicerca, String finalita, String idUd, Integer overFlowLimitIn, String tyobjxfinalitain, String idobjxfinalitain, 
			LuceneParameterFilter filter)
			throws VersionHandlerException {

		Object ret[] = null;
		final Object emptyRes[] = new Object[] { SynchronousVersionHandler._LISTA_VUOTA_STR, "0", "0", SynchronousVersionHandler._LISTA_VUOTA_STR,
				SynchronousVersionHandler._LISTA_VUOTA_STR };
		
		String res[] = null;
		int numRecordOutputStore = 0;
		String checkAttrUsed[] = null;

		final int _RESULT_OUT_POSITION = 0;
		final int _NUM_REC_OUT_POSITION = 1;
		StoreProcedure store = null;
		String tmp[] = null;
		String schemaName = null;
		String idDominio = "";
		Statement stmt = null;
		ResultSet rs = null;

		try {
			// log
			aLogger.info("Inizio findOrgStructureObject");
			// autocommit e savepoint
			if (conn != null) 
				conn.setAutoCommit(false);
			
			DBHelperSavePoint.SetSavepoint(conn, "VHFINDORGSTRUCTUREOBJ");

			// prima di procedere ricavo il nome dello schema dalla connessione
			store = getStoreProcedureFromRepositoryDefinitionSingleton("GetConnSchema", "GetConnSchema");

			// Impostiamo i valori per la store procedure....
			setStoreProcedureParametersValues(conn, store, new Object[] {});

			aLogger.debug("Eseguo store GetConnSchema");
			store.execute();

			// leggo parametri di out
			tmp = getStoreProcedureParametersValues(store, new String[] { "SchemaOut" });
			schemaName = tmp[0];

			// log
			aLogger.debug("Nome schema = " + schemaName);

			// controlli preliminari in input: validazione di eventuali xml per
			// criteri avanzati e custom

			// criteri avanzati ----> sezione cache
			if (advancedFilters != null && !"".equals(advancedFilters)) {
				try {
					// istanzio lo stringReader e vi associo la SezioneCache
					StringReader sr = new java.io.StringReader(advancedFilters);
					SezioneCache sc = (SezioneCache) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
				} catch (Exception e) {
					// log
					aLogger.error(e.getMessage(), e);
					// rilancio l'eccezione
					throw new VersionHandlerException(SynchronousVersionHandler.INPUT_VALIDATION_ERR_COD, SynchronousVersionHandler.INPUT_VALIDATION_ERR_MSG
							+ ": advanced filtes [" + e.getMessage() + "]");
				}
			}

			// criteri custom ----> lista std
			if (customFilters != null && !"".equals(customFilters)) {
				try {
					// istanzio lo stringReader e vi associo la SezioneCache
					StringReader sr = new java.io.StringReader(customFilters);
					Lista ls = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
				} catch (Exception e) {
					// log
					aLogger.error(e.getMessage(), e);
					// rilancio l'eccezione
					throw new VersionHandlerException(SynchronousVersionHandler.INPUT_VALIDATION_ERR_COD, SynchronousVersionHandler.INPUT_VALIDATION_ERR_MSG
							+ ": custom filtes [" + e.getMessage() + "]");
				}
			}

			// prima operazione: strip dei valori nulli da checkattributes
			// conto gli elementi..
			int numAttr = 0;
			for (int i = 0; i < checkAttributes.length; i++)
				if (checkAttributes[i] != null && !checkAttributes[i].equals(""))
					numAttr++;

			// istanzio checkAttrUsed
			checkAttrUsed = new String[numAttr];
			int j = 0;
			for (int i = 0; i < checkAttributes.length; i++) {
				if (checkAttributes[i] != null && !checkAttributes[i].equals("")) {
					checkAttrUsed[j] = checkAttributes[i];
					j++;
				}
			}

			/*
			 * FILTRO VALORIZZATO
			 */
			// - se è valorizzato interrogo Lucene
			// - se Lucene non è configurato non eseguo la ricerca full-text ma chiamo dirett. la stored
			if (filtroFullText != null && !"".equals(filtroFullText) && (luceneHandler != null)) {

				if (numAttr == 0) {
					throw new VersionHandlerException("Non e' stato selezionato alcun attributo su cui effettuare la ricerca");
				}

				// Recupero l'infomazione che mi dice se gli indici sono folderizzati per dominio
				String flgDividi = "";
				String sqlDividi = "select dmpk_utility.GetValConfigParamNumber('" + token + "', 'DIVIDI_INDICI_SO_X_DOMINIO') from dual";
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sqlDividi);

				try {
					if (rs.next()) {
						flgDividi = rs.getString(1);
					}
				} finally {
					try {
						stmt.close();
					} catch (Exception e) {
					}
					try {
						rs.close();
					} catch (Exception e) {
					}
				}
				if (flgDividi == null)
					flgDividi = "";

				// ricavo l'idDominioAut dal token
				String[] sTmp = getApplIstanzaFromToken(conn, token);
				idDominio = sTmp[2];

				// Recupero il idSpAoo padre
				String idSpAooFather = "";
				String sql = "select nvl(dmpk_utility.GetSoggProdDiAppAOO(" + idDominio + "),'') from dual";
				
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);

				try {
					if (rs.next()) {
						idSpAooFather = rs.getString(1);
					}
				} finally {
					try {
						stmt.close();
					} catch (Exception e) {
					}
					try {
						rs.close();
					} catch (Exception e) {
					}
				}
				if (idSpAooFather == null)
					idSpAooFather = "";

				// Aggiungo il filtro sulle IdSpAoo
				HashMap idSpAooFilter = new HashMap();
				String[] idSpAooValues = null; // TODO: chiamare st.pr. per
												// farsi dare i valori
				if (!idSpAooFather.equals("")) {
					idSpAooValues = new String[] { idDominio, idSpAooFather, LuceneHelper._ID_SP_AOO_SCHEMA };
				} else {
					idSpAooValues = new String[] { idDominio, LuceneHelper._ID_SP_AOO_SCHEMA };
				}
				idSpAooFilter.put(LuceneHelper._FIELD_NAME_ID_SP_AOO, idSpAooValues);

				String lista = null;
				// sTmp[2] = "1";//test
				try {
					filtroFullText = replaceConfiguredWildCards(conn, idDominio, filtroFullText);
					// chiamo l'api del Lucene Helper
					List flgObjectTypesLucene = new ArrayList();
					for (int i = 0; i < flgObjectTypes.length; i++) {
						flgObjectTypesLucene.add(flgObjectTypes[i]);
						if (flgObjectTypesLucene.contains("SV") && !flgObjectTypesLucene.contains("UT")) {
							flgObjectTypesLucene.add("UT");
						}
					}
					// ottengo i campi privati per la ricerca di Lucene
					String[] privacyFields = getPrivacyFieldList(conn, token);
					// creo il bean di Login
					AurigaLoginBean login = getLoginBean(token, userIdLavoro);
					lista = luceneHandler.searchFullTextUnitaOrg(
							flgDividi.equals("1") ? idDominio : "", // id
																	// dominio
																	// solo
																	// se
																	// gli
																	// indici
																	// sono
																	// folderizzati
																	// per
																	// dominio
							schemaName,// nome schema
							conn, _OBJECT_CATEGORY_DEF_CTX_SO,
							checkAttrUsed, // attributi su cui cerco
							filtroFullText, // valore da cercare
							(searchAllTerms == null || searchAllTerms == 0) ? SearchType.TYPE_SEARCH_AT_LEAST_ONE_TERM : SearchType.TYPE_SEARCH_ALL_TERMS,
							null, (String[]) flgObjectTypesLucene.toArray(new String[0]), idSpAooFilter, privacyFields[0], filter, login);

					// se NON abbiamo sforato la soglia chiamo la store con:
					// - MatchByIndexerIn con gli oggetti trovati
					// - FlgIndexerOverflowIn null
					// - Flg2ndCallIn null

					res = doDatabaseSearchForOrgStructure(conn, token, userIdLavoro, filtroFullText, checkAttrUsed, searchAllTerms, flgObjectTypes,
							idUoSearchIn, flgSubUoSearchIn, tsRiferimento, advancedFilters, customFilters, colsOrderBy, flgDescOrderBy, flgSenzaPaginazione,
							numPagina, numRighePagina, online, colsToReturn, percorsoRicerca, lista,// matchByIndexerIn
							"",// flgIndexerOverflowIn
							"",// flg2ndCallIn,
							finalita, idUd, overFlowLimitIn, tyobjxfinalitain, idobjxfinalitain);

					// completo l'array da ritornare con i parametri dalla
					// stringa
					ret = new Object[res.length];

					for (int i = 0; i < res.length; i++)
						ret[i] = res[i];

					// vediamo quanti sono i record in out
					numRecordOutputStore = (res[_NUM_REC_OUT_POSITION] == null) ? 0 : (new Integer(res[_NUM_REC_OUT_POSITION])).intValue();

					// la store non ha prodotto risultati
					// if (numRecordOutputStore == 0) {
					// return ret;
					// //throw new
					// VersionHandlerException(NO_DATA_FOUND_SEARCH_COD,NO_DATA_FOUND_SEARCH_MSG);
					// }

					// --------- >tutto OK
					return ret;

				} catch (LuceneNoDataFoundException lndfe) {
					aLogger.debug("nessun risultato trovato");
					return emptyRes;
					// throw new
					// VersionHandlerException(NO_DATA_FOUND_SEARCH_COD,NO_DATA_FOUND_SEARCH_MSG);
				} catch (LuceneTooManyValuesException ltmve) {
					aLogger.debug("troppi risultati: restringere i criteri di ricerca");
					throw new VersionHandlerException(SynchronousVersionHandler.SEARCH_OVERFLOW_COD, SynchronousVersionHandler.SEARCH_OVERFLOW_MSG);
				} catch (TooManyClauses tmc) {
					aLogger.debug("troppi risultati: restringere i criteri di ricerca");
					throw new VersionHandlerException(SynchronousVersionHandler.SEARCH_MANY_CLAUSES_COD, SynchronousVersionHandler.SEARCH_MANY_CLAUSES_MSG);
				} catch (LuceneOverflowException loe) {

					// ABBIAMO SFORATO!!!!!!
					// chiamo la store
					// - null
					// - FlgIndexerOverflowIn 1
					// - Flg2ndCallIn null
					// recupero la store
					res = doDatabaseSearchForOrgStructure(conn, token, userIdLavoro, filtroFullText, checkAttrUsed, searchAllTerms, flgObjectTypes,
							idUoSearchIn, flgSubUoSearchIn, tsRiferimento, advancedFilters, customFilters, colsOrderBy, flgDescOrderBy, flgSenzaPaginazione,
							numPagina, numRighePagina, online, colsToReturn, percorsoRicerca, "",// matchByIndexerIn
							"1",// flgIndexerOverflowIn
							"",// flg2ndCallIn,
							finalita, idUd, overFlowLimitIn,
							tyobjxfinalitain, idobjxfinalitain);

					// completo l'array da ritornare con i parametri dalla
					// stringa
					ret = new Object[res.length];
					for (int i = 0; i < res.length; i++)
						ret[i] = res[i];

					// vediamo quanti sono i record in out
					numRecordOutputStore = (res[_NUM_REC_OUT_POSITION] == null) ? 0 : (new Integer(res[_NUM_REC_OUT_POSITION])).intValue();

					// la store non ha prodotto risultati
					if (numRecordOutputStore == 0) {
						return ret;
						// throw new
						// VersionHandlerException(NO_DATA_FOUND_SEARCH_COD,NO_DATA_FOUND_SEARCH_MSG);
					}

					try {

						List flgObjectTypesLucene = new ArrayList();
						for (int i = 0; i < flgObjectTypes.length; i++) {
							flgObjectTypesLucene.add(flgObjectTypes[i]);
							if (flgObjectTypesLucene.contains("SV") && !flgObjectTypesLucene.contains("UT")) {
								flgObjectTypesLucene.add("UT");
							}
						}
						// ottengo i campi privati per la ricerca di Lucene
						String[] privacyFields = getPrivacyFieldList(conn, token);
						// creo il bean di Login
						AurigaLoginBean login = getLoginBean(token, userIdLavoro);
						// chiamo l'api del Lucene Helper
						String listaLucene2 = luceneHandler.searchFullTextUnitaOrg(
								flgDividi.equals("1") ? idDominio : "", // id
																		// dominio
																		// solo
																		// se
																		// gli
																		// indici
																		// sono
																		// folderizzati
																		// per
																		// dominio
								schemaName, // nome schema
								conn, _OBJECT_CATEGORY_DEF_CTX_CL,
								checkAttrUsed, // attributi su cui cerco
								filtroFullText, // valore da cercare
								(searchAllTerms == null || searchAllTerms == 0) ? SearchType.TYPE_SEARCH_AT_LEAST_ONE_TERM : SearchType.TYPE_SEARCH_ALL_TERMS,
								res[_RESULT_OUT_POSITION], // risultato
															// della
															// store
															// (lista
															// std)
								(String[]) flgObjectTypesLucene.toArray(new String[0]), idSpAooFilter, privacyFields[0], filter, login);

						// - MatchByIndexerIn con gli oggetti trovati
						// - FlgIndexerOverflowIn null
						// - Flg2ndCallIn 1
						res = doDatabaseSearchForOrgStructure(conn, token, userIdLavoro, filtroFullText, checkAttrUsed, searchAllTerms, flgObjectTypes,
								idUoSearchIn, flgSubUoSearchIn, tsRiferimento, advancedFilters, customFilters, colsOrderBy, flgDescOrderBy,
								flgSenzaPaginazione, numPagina, numRighePagina, online, colsToReturn, percorsoRicerca, listaLucene2,// matchByIndexerIn
								"",// flgIndexerOverflowIn
								"1",// flg2ndCallIn,
								finalita, idUd, overFlowLimitIn,
								tyobjxfinalitain, idobjxfinalitain);

						// completo l'array da ritornare con i parametri dalla
						// stringa
						ret = new Object[res.length];
						for (int i = 0; i < res.length; i++)
							ret[i] = res[i];

						// vediamo quanti sono i record in out
						numRecordOutputStore = (res[_NUM_REC_OUT_POSITION] == null) ? 0 : (new Integer(res[_NUM_REC_OUT_POSITION])).intValue();
						// la store non ha prodotto risultati
						// if (numRecordOutputStore == 0) {
						// throw new
						// VersionHandlerException(NO_DATA_FOUND_SEARCH_COD,NO_DATA_FOUND_SEARCH_MSG);
						// }

						// tutto ok
						return ret;

					} catch (LuceneOverflowException e) {
						aLogger.debug("trovato numero record sopra il massimo consentito");
						throw new VersionHandlerException(SynchronousVersionHandler.SEARCH_OVERFLOW_COD, SynchronousVersionHandler.SEARCH_OVERFLOW_MSG);
					} catch (LuceneNoDataFoundException e) {
						aLogger.debug("nessun risultato trovato");
						return emptyRes;
						// throw new
						// VersionHandlerException(NO_DATA_FOUND_SEARCH_COD,NO_DATA_FOUND_SEARCH_MSG);
					} catch (LuceneException le) {
						aLogger.error("Errore nella ricerca tramite Lucene", le);
						throw new VersionHandlerException(SynchronousVersionHandler.LUCENE_ERR_COD, SynchronousVersionHandler.LUCENE_ERR_MSG + le.getMessage());
					}

				} catch (LuceneException le) {
					aLogger.error("Errore nella ricerca tramite Lucene", le);
					throw new VersionHandlerException(SynchronousVersionHandler.LUCENE_ERR_COD, SynchronousVersionHandler.LUCENE_ERR_MSG + le.getMessage());
				}

			}
			/*
			 * FILTRO NON VALORIZZATO = semplice chiamata alla store
			 */
			else {

				res = doDatabaseSearchForOrgStructure(conn, token, userIdLavoro, filtroFullText, checkAttrUsed, searchAllTerms, flgObjectTypes, idUoSearchIn,
						flgSubUoSearchIn, tsRiferimento, advancedFilters, customFilters, colsOrderBy, flgDescOrderBy, flgSenzaPaginazione, numPagina,
						numRighePagina, online, colsToReturn, percorsoRicerca, "",// matchByIndexerIn
						"",// flgIndexerOverflowIn
						"",// flg2ndCallIn,
						finalita, idUd, overFlowLimitIn,
						tyobjxfinalitain, idobjxfinalitain);

				// completo l'array da ritornare con i parametri dalla stringa
				ret = new Object[res.length];
				for (int i = 0; i < res.length; i++)
					ret[i] = res[i];

				// vediamo quanti sono i record in out
				numRecordOutputStore = (res[_NUM_REC_OUT_POSITION] == null) ? 0 : (new Integer(res[_NUM_REC_OUT_POSITION])).intValue();
				// la store non ha prodotto risultati
				// if (numRecordOutputStore == 0) {
				// throw new
				// VersionHandlerException(NO_DATA_FOUND_SEARCH_COD,NO_DATA_FOUND_SEARCH_MSG);
				// }

				// tutto ok
				return ret;
			}

		} catch (LuceneHandlerException ex2) {
			// ROLLBACK
			try {
				DBHelperSavePoint.RollbackToSavepoint(conn, "VHFINDORGSTRUCTUREOBJ");
			} catch (Exception e) {
			}
			// log
			aLogger.error("Errore in FIND REPOSITORY OBJECT. ErrMsg: " + ex2.getMessage(), ex2);
			// rilancio l'eccezione
			throw new VersionHandlerException(SynchronousVersionHandler.LUCENE_HANDLER_COD, SynchronousVersionHandler.LUCENE_HANDLER_MSG + "("
					+ ex2.getMessage() + ")");

		} catch (VersionHandlerException ex1) {
			// ROLLBACK
			try {
				DBHelperSavePoint.RollbackToSavepoint(conn, "VHFINDORGSTRUCTUREOBJ");
			} catch (Exception e) {
			}
			// log
			aLogger.error("Errore in FIND REPOSITORY OBJECT.\nErrcode:" + ex1.getCodice() + "\nErrMsg: " + ex1.getMessage(), ex1);
			// rilancio l'eccezione
			throw ex1;
		}
		// ************ eccezione IMPREVISTA *************
		catch (Exception ex) {
			// ROLLBACK
			try {
				DBHelperSavePoint.RollbackToSavepoint(conn, "VHFINDORGSTRUCTUREOBJ");
			} catch (Exception e) {
			}
			// log
			aLogger.debug("Errore GENERICO in FIND REPOSITORY OBJECT.\nException: " + ex.getMessage(), ex);
			// rilancio l'eccezione trasformata
			throw new VersionHandlerException(SynchronousVersionHandler.IMPREVISTO_COD, SynchronousVersionHandler.IMPREVISTO_MSG + "(" + ex.getMessage() + ")");
		} finally {
			aLogger.info("Fine findRepositoryObject");
		}
	}

	private String[] doDatabaseSearchForOrgStructure(Connection conn, String token, String userIdLavoro, String filtroFullText, String[] checkAttributes,
			Integer searchAllTerms, String[] flgObjectTypes, Integer idUoSearchIn, String flgSubUoSearchIn, String tsRiferimento, String advancedFilters,
			String customFilters, String colsOrderBy, String flgDescOrderBy, Integer flgSenzaPaginazione, Integer numPagina, Integer numRighePagina,
			Integer online, String colsToReturn, String percorsoRicerca, String matchByIndexerIn, String flgIndexerOverflowIn, String flg2ndCallIn,
			String finalita, String idUd, Integer overFlowLimitIn, String tyobjxfinalitain, String idobjxfinalitain) throws Exception {

		StoreProcedure store = null;
		String res[] = null;
		Object ret[] = null;
		int errCode = 0;
		String errMsg = "";
		String strObjTypes = "";

		try {
			aLogger.info("Inizio doDatabaseSearchForOrgStructure");

			if (flgObjectTypes != null) {
				for (int i = 0; i < flgObjectTypes.length; i++) {
					if (i > 0)
						strObjTypes += ";";
					strObjTypes += flgObjectTypes[i];
				}
			}

			store = getStoreProcedureFromRepositoryDefinitionSingleton("TrovaStruttOrgObj", "TrovaStruttOrgObj");
			
			String overflowLimitValue = overFlowLimitIn != null ? overFlowLimitIn.toString() : null;

			// Impostiamo i valori per la store procedure....
			setStoreProcedureParametersValues(
					conn,
					store,
					new Object[] {
							"CodIdConnectionTokenIn", token, // token di connessione
							"IdUserLavoroIn", userIdLavoro, // user id lavoro
							"MatchByIndexerIn", matchByIndexerIn,
							"FlgIndexerOverflowIn", flgIndexerOverflowIn,
							"Flg2ndCallIn", flg2ndCallIn,
							"FlgObjTypeIO", strObjTypes,
							"CercaInUOIO", (idUoSearchIn == null) ? "" : idUoSearchIn.toString(),
							"FlgCercaInSubUOIO", (flgSubUoSearchIn == null) ? "" : flgSubUoSearchIn,
							"TsRifIn", (tsRiferimento == null) ? "" : tsRiferimento,
							"CriteriAvanzatiIO", (advancedFilters == null) ? "" : advancedFilters, 
							"CriteriPersonalizzatiIO",(customFilters == null) ? "" : customFilters,
							"ColOrderByIO", (colsOrderBy == null) ? "" : colsOrderBy, 
							"FlgDescOrderByIO",(flgDescOrderBy == null) ? "0" : flgDescOrderBy, 
							"FlgSenzaPaginazioneIn",(flgSenzaPaginazione == null) ? "0" : flgSenzaPaginazione.toString(), 
							"NroPaginaIO",(numPagina == null) ? "" : numPagina.toString(), 
							"BachSizeIO", (numRighePagina == null) ? "" : numRighePagina.toString(),
							"OverFlowLimitIn", overflowLimitValue, 
							"FlgBatchSearchIn", (online == null) ? "0" : online.toString(), 
							"ColToReturnIn", (colsToReturn == null) ? "" : colsToReturn,
							"PercorsoRicercaXMLIO", (percorsoRicerca == null) ? "" : percorsoRicerca,
							"FinalitaIn", (finalita == null) ? "" : finalita,							
							//"TyObjxFinalitaIn", (idUd == null) ? "" : "U", 
							//"IdObjxFinalitaIn", (idUd == null) ? "" : idUd 									
							"TyObjxFinalitaIn",  (tyobjxfinalitain !=null) ? tyobjxfinalitain : ((idUd == null) ? "" : "U"),  
							"IdObjxFinalitaIn",  (idobjxfinalitain !=null) ? idobjxfinalitain : ((idUd == null) ? "" : idUd)
				 });

			// log
			aLogger.debug("Eseguo store TrovaRepositoryObj");
			// esecuzione della store proc
			store.execute();

			// leggo parametri di out
			res = getStoreProcedureParametersValues(store, new String[] { "ResultOut", "NroTotRecOut", "NroRecInPaginaOut", "PercorsoRicercaXMLIO",
					"DettagliCercaInFolderOut", "ErrorMessage", "BachSizeIO"});

			return res;

		} catch (StoreProcedureException ex0) {
			// ROLLBACK
			try {
				DBHelperSavePoint.RollbackToSavepoint(conn, "VHFINDORGSTRUCTUREOBJ");
			} catch (Exception e) {
			}
			// Qualcosa è andato storto!!!!
			String err[] = getInfoFromStoreProcedureException(store, ex0);
			errCode = Integer.parseInt(err[0]);
			errMsg = err[1];

			if (errCode == 1011) {
				aLogger.debug("Errore 1011 di OVERFLOW in FIND UO OBJECT.\nErrcode:" + errCode + "\nErrMsg: " + errMsg, ex0);
				throw new VersionHandlerException(SynchronousVersionHandler.SEARCH_OVERFLOW_COD, SynchronousVersionHandler.SEARCH_OVERFLOW_MSG);
			} else {
				aLogger.error("Errore STORE in FIND UO OBJECT.\nErrcode:" + errCode + "\nErrMsg: " + errMsg, ex0);
				throw new VersionHandlerException(errCode, errMsg);
			}
		} finally {
			aLogger.info("Fine doDatabaseSearchForOrgStructure");
		}

	}

	public Object[] findTitolarioObject(Connection conn, String token, String userIdLavoro, String filtroFullText, String[] checkAttributes,
			String[] flgObjectTypes, Integer searchAllTerms, Integer idClSearchIO, String flgSubClSearchIn, String tsRiferimento, String advancedFilters,
			String customFilters, String colsOrderBy, String flgDescOrderBy, Integer flgSenzaPaginazione, Integer numPagina, Integer numRighePagina,
			Integer overFlowLimit, String flgSenzaTot, Integer online, String colsToReturn, String finalita, LuceneParameterFilter filter)
			throws VersionHandlerException {

		Object ret[] = null;
		final Object emptyRes[] = new Object[] { SynchronousVersionHandler._LISTA_VUOTA_STR, "0", "0", SynchronousVersionHandler._LISTA_VUOTA_STR,
				SynchronousVersionHandler._LISTA_VUOTA_STR };
		// StoreProcedure store = null;
		String res[] = null;
		int numRecordOutputStore = 0;
		String checkAttrUsed[] = null;

		final int _RESULT_OUT_POSITION = 0;
		final int _NUM_REC_OUT_POSITION = 1;
		StoreProcedure store = null;
		String tmp[] = null;
		String schemaName = null;
		String idDominio = "";
		Statement stmt = null;
		ResultSet rs = null;

		try {
			// log
			aLogger.info("Inizio findTitolarioObject");

			// autocommit e savepoint
			if (conn != null) 
				conn.setAutoCommit(false);
			
			DBHelperSavePoint.SetSavepoint(conn, "VHFINDTITOBJ");

			// prima di procedere ricavo il nome dello schema dalla connessione
			store = getStoreProcedureFromRepositoryDefinitionSingleton("GetConnSchema", "GetConnSchema");

			// Impostiamo i valori per la store procedure....
			setStoreProcedureParametersValues(conn, store, new Object[] {});

			aLogger.debug("Eseguo store GetConnSchema");
			store.execute();

			// leggo parametri di out
			tmp = getStoreProcedureParametersValues(store, new String[] { "SchemaOut" });
			schemaName = tmp[0];

			// log
			aLogger.debug("Nome schema = " + schemaName);

			// controlli preliminari in input: validazione di eventuali xml per
			// criteri avanzati e custom

			// criteri avanzati ----> sezione cache
			if (advancedFilters != null && !"".equals(advancedFilters)) {
				try {
					// istanzio lo stringReader e vi associo la SezioneCache
					StringReader sr = new java.io.StringReader(advancedFilters);
					SezioneCache sc = (SezioneCache) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
				} catch (Exception e) {
					// log
					aLogger.error(e.getMessage(), e);
					// rilancio l'eccezione
					throw new VersionHandlerException(SynchronousVersionHandler.INPUT_VALIDATION_ERR_COD, SynchronousVersionHandler.INPUT_VALIDATION_ERR_MSG
							+ ": advanced filtes [" + e.getMessage() + "]");
				}
			}

			// criteri custom ----> lista std
			if (customFilters != null && !"".equals(customFilters)) {
				try {
					// istanzio lo stringReader e vi associo la SezioneCache
					StringReader sr = new java.io.StringReader(customFilters);
					Lista ls = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
				} catch (Exception e) {
					// log
					aLogger.error(e.getMessage(), e);
					// rilancio l'eccezione
					throw new VersionHandlerException(SynchronousVersionHandler.INPUT_VALIDATION_ERR_COD, SynchronousVersionHandler.INPUT_VALIDATION_ERR_MSG
							+ ": custom filtes [" + e.getMessage() + "]");
				}
			}

			// prima operazione: strip dei valori nulli da checkattributes
			// conto gli elementi..
			int numAttr = 0;
			for (int i = 0; i < checkAttributes.length; i++)
				if (checkAttributes[i] != null && !checkAttributes[i].equals(""))
					numAttr++;

			// istanzio checkAttrUsed
			checkAttrUsed = new String[numAttr];
			int j = 0;
			for (int i = 0; i < checkAttributes.length; i++) {
				if (checkAttributes[i] != null && !checkAttributes[i].equals("")) {
					checkAttrUsed[j] = checkAttributes[i];
					j++;
				}
			}

			/*
			 * FILTRO VALORIZZATO
			 */
			// - se è valorizzato interrogo Lucene
			// - se Lucene non è configurato non eseguo la ricerca full-text ma
			// chiamo dirett. la stored
			if (filtroFullText != null && !"".equals(filtroFullText) && (luceneHandler != null)) {

				if (numAttr == 0) {
					throw new VersionHandlerException("Non e' stato selezionato alcun attributo su cui effettuare la ricerca");
				}

				// Recupero l'infomazione che mi dice se gli indici sono
				// folderizzati per dominio
				String flgDividi = "";
				String sqlDividi = "select dmpk_utility.GetValConfigParamNumber('" + token + "', 'DIVIDI_INDICI_SO_X_DOMINIO') from dual";
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sqlDividi);

				try {
					if (rs.next()) {
						flgDividi = rs.getString(1);
					}
				} finally {
					try {
						stmt.close();
					} catch (Exception e) {
					}
					try {
						rs.close();
					} catch (Exception e) {
					}
				}
				if (flgDividi == null)
					flgDividi = "";

				// ricavo l'idDominioAut dal token
				String[] sTmp = getApplIstanzaFromToken(conn, token);
				idDominio = sTmp[2];

				// Recupero il idSpAoo padre
				String idSpAooFather = "";
				String sql = "select nvl(dmpk_utility.GetSoggProdDiAppAOO(" + idDominio + "),'') from dual";
				;
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);

				try {
					if (rs.next()) {
						idSpAooFather = rs.getString(1);
					}
				} finally {
					try {
						stmt.close();
					} catch (Exception e) {
					}
					try {
						rs.close();
					} catch (Exception e) {
					}
				}
				if (idSpAooFather == null)
					idSpAooFather = "";

				// Aggiungo il filtro sulle IdSpAoo
				HashMap idSpAooFilter = new HashMap();
				String[] idSpAooValues = null; // TODO: chiamare st.pr. per
												// farsi dare i valori
				if (!idSpAooFather.equals("")) {
					idSpAooValues = new String[] { idDominio, idSpAooFather, LuceneHelper._ID_SP_AOO_SCHEMA };
				} else {
					idSpAooValues = new String[] { idDominio, LuceneHelper._ID_SP_AOO_SCHEMA };
				}
				idSpAooFilter.put(LuceneHelper._FIELD_NAME_ID_SP_AOO, idSpAooValues);

				String lista = null;
				// sTmp[2] = "1";//test
				try {
					// ottengo i campi privati per la ricerca di Lucene
					String[] privacyFields = getPrivacyFieldList(conn, token);
					// creo il bean di Login
					AurigaLoginBean login = getLoginBean(token, userIdLavoro);
					filtroFullText = replaceConfiguredWildCards(conn, idDominio, filtroFullText);
					// chiamo l'api del Lucene Helper
					lista = luceneHandler.searchFullTextTitolario(
							flgDividi.equals("1") ? idDominio : "", // id dominio solo se gli indici sono folderizzati per dominio
							schemaName,// nome schema
							conn, // connessione
							_OBJECT_CATEGORY_DEF_CTX_CL,
							checkAttrUsed, // attributi su cui cerco
							filtroFullText, // valore da cercare
							(searchAllTerms == null || searchAllTerms == 0) ? SearchType.TYPE_SEARCH_AT_LEAST_ONE_TERM : SearchType.TYPE_SEARCH_ALL_TERMS,
							null, flgObjectTypes, idSpAooFilter, privacyFields[0], filter, login);

					// se NON abbiamo sforato la soglia chiamo la store con:
					// - MatchByIndexerIn con gli oggetti trovati
					// - FlgIndexerOverflowIn null
					// - Flg2ndCallIn null

					res = doDatabaseSearchForTitolario(conn, token, userIdLavoro,
							lista,// matchByIndexerIn,
							"",// flgIndexerOverflowIn flgIndexerOverflowIn,
							"",// flg2ndCallIn,flg2ndCallIn,
							idClSearchIO, flgSubClSearchIn, tsRiferimento, advancedFilters, customFilters, colsOrderBy, flgDescOrderBy, flgSenzaPaginazione,
							numPagina, numRighePagina, overFlowLimit, flgSenzaTot, online, colsToReturn, finalita

					);

					// completo l'array da ritornare con i parametri dalla
					// stringa
					ret = new Object[res.length];

					for (int i = 0; i < res.length; i++)
						ret[i] = res[i];

					// vediamo quanti sono i record in out
					numRecordOutputStore = (res[_NUM_REC_OUT_POSITION] == null) ? 0 : (new Integer(res[_NUM_REC_OUT_POSITION])).intValue();

					// la store non ha prodotto risultati
					// if (numRecordOutputStore == 0) {
					// return ret;
					// //throw new
					// VersionHandlerException(NO_DATA_FOUND_SEARCH_COD,NO_DATA_FOUND_SEARCH_MSG);
					// }

					// --------- >tutto OK
					return ret;

				} catch (LuceneNoDataFoundException lndfe) {
					aLogger.debug("nessun risultato trovato");
					return emptyRes;
					// throw new
					// VersionHandlerException(NO_DATA_FOUND_SEARCH_COD,NO_DATA_FOUND_SEARCH_MSG);
				} catch (LuceneTooManyValuesException ltmve) {
					aLogger.debug("troppi risultati: restringere i criteri di ricerca");
					throw new VersionHandlerException(SynchronousVersionHandler.SEARCH_OVERFLOW_COD, SynchronousVersionHandler.SEARCH_OVERFLOW_MSG);
				} catch (TooManyClauses tmc) {
					aLogger.debug("troppi risultati: restringere i criteri di ricerca");
					throw new VersionHandlerException(SynchronousVersionHandler.SEARCH_MANY_CLAUSES_COD, SynchronousVersionHandler.SEARCH_MANY_CLAUSES_MSG);
				} catch (LuceneOverflowException loe) {

					// ABBIAMO SFORATO!!!!!!
					// chiamo la store
					// - null
					// - FlgIndexerOverflowIn 1
					// - Flg2ndCallIn null
					// recupero la store
					res = doDatabaseSearchForTitolario(conn, token, userIdLavoro,
							"",// matchByIndexerIn matchByIndexerIn,
							"1",// flgIndexerOverflowIn flgIndexerOverflowIn,
							"",// flg2ndCallIn, flg2ndCallIn,
							idClSearchIO, flgSubClSearchIn, tsRiferimento, advancedFilters, customFilters, colsOrderBy, flgDescOrderBy, flgSenzaPaginazione,
							numPagina, numRighePagina, overFlowLimit, flgSenzaTot, online, colsToReturn, finalita

					);

					// completo l'array da ritornare con i parametri dalla
					// stringa
					ret = new Object[res.length];
					for (int i = 0; i < res.length; i++)
						ret[i] = res[i];

					// vediamo quanti sono i record in out
					numRecordOutputStore = (res[_NUM_REC_OUT_POSITION] == null) ? 0 : (new Integer(res[_NUM_REC_OUT_POSITION])).intValue();

					// la store non ha prodotto risultati
					if (numRecordOutputStore == 0) {
						return ret;
						// throw new
						// VersionHandlerException(NO_DATA_FOUND_SEARCH_COD,NO_DATA_FOUND_SEARCH_MSG);
					}

					try {
						// ottengo i campi privati per la ricerca di Lucene
						String[] privacyFields = getPrivacyFieldList(conn, token);
						// creo il bean di Login
						AurigaLoginBean login = getLoginBean(token, userIdLavoro);
						// chiamo l'api del Lucene Helper
						String listaLucene2 = luceneHandler.searchFullTextTitolario(
								flgDividi.equals("1") ? idDominio : "", // id
																		// dominio
																		// solo
																		// se
																		// gli
																		// indici
																		// sono
																		// folderizzati
																		// per
																		// dominio
								schemaName, // nome schema
								conn, // connessione
								_OBJECT_CATEGORY_DEF_CTX_CL,
								checkAttrUsed, // attributi su cui cerco
								filtroFullText, // valore da cercare
								(searchAllTerms == null || searchAllTerms == 0) ? SearchType.TYPE_SEARCH_AT_LEAST_ONE_TERM : SearchType.TYPE_SEARCH_ALL_TERMS,
								res[_RESULT_OUT_POSITION], // risultato
															// della
															// store
															// (lista
															// std)
								flgObjectTypes, idSpAooFilter, privacyFields[0], filter, login);

						// - MatchByIndexerIn con gli oggetti trovati
						// - FlgIndexerOverflowIn null
						// - Flg2ndCallIn 1
						res = doDatabaseSearchForTitolario(conn, token, userIdLavoro, listaLucene2,
								"",// flgIndexerOverflowIn flgIndexerOverflowIn,
								"1",// flg2ndCallIn, flg2ndCallIn,
								idClSearchIO, flgSubClSearchIn, tsRiferimento, advancedFilters, customFilters, colsOrderBy, flgDescOrderBy,
								flgSenzaPaginazione, numPagina, numRighePagina, overFlowLimit, flgSenzaTot, online, colsToReturn, finalita);

						// completo l'array da ritornare con i parametri dalla
						// stringa
						ret = new Object[res.length];
						for (int i = 0; i < res.length; i++)
							ret[i] = res[i];

						// vediamo quanti sono i record in out
						numRecordOutputStore = (res[_NUM_REC_OUT_POSITION] == null) ? 0 : (new Integer(res[_NUM_REC_OUT_POSITION])).intValue();
						// la store non ha prodotto risultati
						// if (numRecordOutputStore == 0) {
						// throw new
						// VersionHandlerException(NO_DATA_FOUND_SEARCH_COD,NO_DATA_FOUND_SEARCH_MSG);
						// }

						// tutto ok
						return ret;

					} catch (LuceneOverflowException e) {
						aLogger.debug("trovato numero record sopra il massimo consentito");
						throw new VersionHandlerException(SynchronousVersionHandler.SEARCH_OVERFLOW_COD, SynchronousVersionHandler.SEARCH_OVERFLOW_MSG);
					} catch (LuceneNoDataFoundException e) {
						aLogger.debug("nessun risultato trovato");
						return emptyRes;
						// throw new
						// VersionHandlerException(NO_DATA_FOUND_SEARCH_COD,NO_DATA_FOUND_SEARCH_MSG);
					} catch (LuceneException le) {
						aLogger.error("Errore nella ricerca tramite Lucene", le);
						throw new VersionHandlerException(SynchronousVersionHandler.LUCENE_ERR_COD, SynchronousVersionHandler.LUCENE_ERR_MSG + le.getMessage());
					}

				} catch (LuceneException le) {
					aLogger.error("Errore nella ricerca tramite Lucene", le);
					throw new VersionHandlerException(SynchronousVersionHandler.LUCENE_ERR_COD, SynchronousVersionHandler.LUCENE_ERR_MSG + le.getMessage());
				}

			}
			/*
			 * FILTRO NON VALORIZZATO = semplice chiamata alla store
			 */
			else {

				res = doDatabaseSearchForTitolario(conn, token, userIdLavoro,
						"",// matchByIndexerIn matchByIndexerIn,
						"",// flgIndexerOverflowIn flgIndexerOverflowIn,
						"",// flg2ndCallIn, flg2ndCallIn,
						idClSearchIO, flgSubClSearchIn, tsRiferimento, advancedFilters, customFilters, colsOrderBy, flgDescOrderBy, flgSenzaPaginazione,
						numPagina, numRighePagina, overFlowLimit, flgSenzaTot, online, colsToReturn, finalita);

				// completo l'array da ritornare con i parametri dalla stringa
				ret = new Object[res.length];
				for (int i = 0; i < res.length; i++)
					ret[i] = res[i];

				// vediamo quanti sono i record in out
				numRecordOutputStore = (res[_NUM_REC_OUT_POSITION] == null) ? 0 : (new Integer(res[_NUM_REC_OUT_POSITION])).intValue();
				// la store non ha prodotto risultati
				// if (numRecordOutputStore == 0) {
				// throw new
				// VersionHandlerException(NO_DATA_FOUND_SEARCH_COD,NO_DATA_FOUND_SEARCH_MSG);
				// }

				// tutto ok
				return ret;
			}
		} catch (LuceneHandlerException ex2) {
			// ROLLBACK
			try {
				DBHelperSavePoint.RollbackToSavepoint(conn, "VHFINDTITOBJ");
			} catch (Exception e) {
			}
			// log
			aLogger.error("Errore in FIND REPOSITORY OBJECT. ErrMsg: " + ex2.getMessage(), ex2);
			// rilancio l'eccezione
			throw new VersionHandlerException(SynchronousVersionHandler.LUCENE_HANDLER_COD, SynchronousVersionHandler.LUCENE_HANDLER_MSG + "("
					+ ex2.getMessage() + ")");

		} catch (VersionHandlerException ex1) {
			// ROLLBACK
			try {
				DBHelperSavePoint.RollbackToSavepoint(conn, "VHFINDTITOBJ");
			} catch (Exception e) {
			}
			// log
			aLogger.error("Errore in FIND REPOSITORY OBJECT.\nErrcode:" + ex1.getCodice() + "\nErrMsg: " + ex1.getMessage(), ex1);
			// rilancio l'eccezione
			throw ex1;
		}
		// ************ eccezione IMPREVISTA *************
		catch (Exception ex) {
			// ROLLBACK
			try {
				DBHelperSavePoint.RollbackToSavepoint(conn, "VHFINDTITOBJ");
			} catch (Exception e) {
			}
			// log
			aLogger.debug("Errore GENERICO in FIND REPOSITORY OBJECT.\nException: " + ex.getMessage(), ex);
			// rilancio l'eccezione trasformata
			throw new VersionHandlerException(SynchronousVersionHandler.IMPREVISTO_COD, SynchronousVersionHandler.IMPREVISTO_MSG + "(" + ex.getMessage() + ")");
		} finally {
			aLogger.info("Fine findTitolarioObject");
		}
	}

	private String[] doDatabaseSearchForTitolario(Connection conn, String token, String userIdLavoro, String matchByIndexerIn, String flgIndexerOverflowIn,
			String flg2ndCallIn, Integer idClSearchIO, String flgSubClSearchIn, String tsRiferimento, String advancedFilters, String customFilters,
			String colsOrderBy, String flgDescOrderBy, Integer flgSenzaPaginazione, Integer numPagina, Integer numRighePagina, Integer OverFlowLimit,
			String FlgSenzaTot, Integer online, String colsToReturn, String finalita

	) throws Exception {

		StoreProcedure store = null;
		String res[] = null;
		Object ret[] = null;
		int errCode = 0;
		String errMsg = "";
		String strObjTypes = "";

		try {

			aLogger.info("Inizio doDatabaseSearchForTitolario");

			store = getStoreProcedureFromRepositoryDefinitionSingleton("TrovaTitolario", "TrovaTitolario");
			
			String overflowLimitValue = OverFlowLimit != null ? OverFlowLimit.toString() : null;

			// Impostiamo i valori per la store procedure....
			setStoreProcedureParametersValues(
					conn,
					store,
					new Object[] {
							"CodIdConnectionTokenIn",token, // token di connessione
							"IdUserLavoroIn",userIdLavoro, // user id lavoro
							"MatchByIndexerIn", matchByIndexerIn, 
							"FlgIndexerOverflowIn", flgIndexerOverflowIn,
							"Flg2ndCallIn", flg2ndCallIn, 
							"CercaInCLIO",(idClSearchIO == null) ? "" : idClSearchIO.toString(), 
							"FlgCercaInSubCLIO", (flgSubClSearchIn == null) ? "" : flgSubClSearchIn,
							"TsRifIn", (tsRiferimento == null) ? "" : tsRiferimento, 
							"CriteriAvanzatiIO", (advancedFilters == null) ? "" : advancedFilters,
							"CriteriPersonalizzatiIO", (customFilters == null) ? "" : customFilters,
							"ColOrderByIO", (colsOrderBy == null) ? "" : colsOrderBy,
							"FlgDescOrderByIO", (flgDescOrderBy == null) ? "0" : flgDescOrderBy, 
							"FlgSenzaPaginazioneIn",(flgSenzaPaginazione == null) ? "0" : flgSenzaPaginazione.toString(),
							"NroPaginaIO",(numPagina == null) ? "" : numPagina.toString(), 
							"BachSizeIO", (numRighePagina == null) ? "" : numRighePagina.toString(),
							"OverFlowLimitIn", overflowLimitValue, 
							"FlgSenzaTotIn",(FlgSenzaTot == null) ? "0" : FlgSenzaTot, 
							"FlgBatchSearchIn", (online == null) ? "0" : online.toString(),
							"ColToReturnIn",(colsToReturn == null) ? "" : colsToReturn,
							"FinalitaIn", (finalita == null) ? "" : finalita });

			// log
			aLogger.debug("Eseguo store TrovaTitolarioObj");
			// esecuzione della store proc
			store.execute();

			// leggo parametri di out
			res = getStoreProcedureParametersValues(store, new String[] { "ResultOut", "NroTotRecOut", "NroRecInPaginaOut", "PercorsoRicercaXMLIO",
					"DettagliCercaInFolderOut", "ErrorMessage", "BachSizeIO" });

			return res;

		} catch (StoreProcedureException ex0) {
			// ROLLBACK
			try {
				DBHelperSavePoint.RollbackToSavepoint(conn, "VHFINDTITOBJ");
			} catch (Exception e) {
			}
			// Qualcosa è andato storto!!!!
			String err[] = getInfoFromStoreProcedureException(store, ex0);
			errCode = Integer.parseInt(err[0]);
			errMsg = err[1];

			if (errCode == 1011) {
				aLogger.debug("Errore 1011 di OVERFLOW in FIND TITOLARIO OBJECT.\nErrcode:" + errCode + "\nErrMsg: " + errMsg, ex0);
				throw new VersionHandlerException(SynchronousVersionHandler.SEARCH_OVERFLOW_COD, SynchronousVersionHandler.SEARCH_OVERFLOW_MSG);
			} else {
				aLogger.error("Errore STORE in FIND TITOLARIO OBJECT.\nErrcode:" + errCode + "\nErrMsg: " + errMsg, ex0);
				throw new VersionHandlerException(errCode, errMsg);
			}
		} finally {
			aLogger.info("Fine doDatabaseSearchForTitolario");
		}

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

		Object ret[] = null;
		final Object emptyRes[] = new Object[] { SynchronousVersionHandler._LISTA_VUOTA_STR, "0", "0", SynchronousVersionHandler._LISTA_VUOTA_STR,
				SynchronousVersionHandler._LISTA_VUOTA_STR };
		// StoreProcedure store = null;
		String res[] = null;
		int numRecordOutputStore = 0;
		String checkAttrUsed[] = null;

		final int _RESULT_OUT_POSITION = 0;
		final int _NUM_REC_OUT_POSITION = 1;
		StoreProcedure store = null;
		String tmp[] = null;
		String schemaName = null;
		String idDominio = "";
		Statement stmt = null;
		ResultSet rs = null;

		try {
			// log
			aLogger.info("Inizio findRubricaObject");

			// autocommit e savepoint
			if (conn != null) 
				conn.setAutoCommit(false);
			
			DBHelperSavePoint.SetSavepoint(conn, "VHFINDRUBOBJ");

			// prima di procedere ricavo il nome dello schema dalla connessione
			store = getStoreProcedureFromRepositoryDefinitionSingleton("GetConnSchema", "GetConnSchema");

			// Impostiamo i valori per la store procedure....
			setStoreProcedureParametersValues(conn, store, new Object[] {});

			aLogger.debug("Eseguo store GetConnSchema");
			store.execute();

			// leggo parametri di out
			tmp = getStoreProcedureParametersValues(store, new String[] { "SchemaOut" });
			schemaName = tmp[0];

			// log
			aLogger.debug("Nome schema = " + schemaName);

			// controlli preliminari in input: validazione di eventuali xml per
			// criteri avanzati e custom

			// criteri custom ----> lista std
			if (CriteriPersonalizzatiIO != null && !"".equals(CriteriPersonalizzatiIO)) {
				try {
					// istanzio lo stringReader e vi associo la SezioneCache
					StringReader sr = new java.io.StringReader(CriteriPersonalizzatiIO);
					Lista ls = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
				} catch (Exception e) {
					// log
					aLogger.error(e.getMessage(), e);
					// rilancio l'eccezione
					throw new VersionHandlerException(SynchronousVersionHandler.INPUT_VALIDATION_ERR_COD, SynchronousVersionHandler.INPUT_VALIDATION_ERR_MSG
							+ ": custom filtes [" + e.getMessage() + "]");
				}
			}

			// prima operazione: strip dei valori nulli da checkattributes
			// conto gli elementi..
			int numAttr = 0;
			for (int i = 0; i < checkAttributes.length; i++)
				if (checkAttributes[i] != null && !checkAttributes[i].equals(""))
					numAttr++;

			// istanzio checkAttrUsed
			checkAttrUsed = new String[numAttr];
			int j = 0;
			for (int i = 0; i < checkAttributes.length; i++) {
				if (checkAttributes[i] != null && !checkAttributes[i].equals("")) {
					checkAttrUsed[j] = checkAttributes[i];
					j++;
				}
			}

			/*
			 * FILTRO VALORIZZATO
			 */
			// - se è valorizzato interrogo Lucene
			// - se Lucene non è configurato non eseguo la ricerca full-text ma
			// chiamo dirett. la stored
			if (filtroFullText != null && !"".equals(filtroFullText) && (luceneHandler != null)) {

				if (numAttr == 0) {
					throw new VersionHandlerException("Non e' stato selezionato alcun attributo su cui effettuare la ricerca");
				}

				// Recupero l'infomazione che mi dice se gli indici sono
				// folderizzati per dominio
				String flgDividi = "";
				String sqlDividi = "select dmpk_utility.GetValConfigParamNumber('" + token + "', 'DIVIDI_INDICI_SO_X_DOMINIO') from dual";
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sqlDividi);

				try {
					if (rs.next()) {
						flgDividi = rs.getString(1);
					}
				} finally {
					try {
						stmt.close();
					} catch (Exception e) {
					}
					try {
						rs.close();
					} catch (Exception e) {
					}
				}
				if (flgDividi == null)
					flgDividi = "";

				// ricavo l'idDominioAut dal token
				String[] sTmp = getApplIstanzaFromToken(conn, token);
				idDominio = sTmp[2];

				// Recupero il idSpAoo padre
				String idSpAooFather = "";
				String sql = "select nvl(dmpk_utility.GetSoggProdDiAppAOO(" + idDominio + "),'') from dual";
				;
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);

				try {
					if (rs.next()) {
						idSpAooFather = rs.getString(1);
					}
				} finally {
					try {
						stmt.close();
					} catch (Exception e) {
					}
					try {
						rs.close();
					} catch (Exception e) {
					}
				}
				if (idSpAooFather == null)
					idSpAooFather = "";

				// Aggiungo il filtro sulle IdSpAoo
				HashMap idSpAooFilter = new HashMap();
				String[] idSpAooValues = null; // TODO: chiamare st.pr. per
												// farsi dare i valori
				if (!idSpAooFather.equals("")) {
					idSpAooValues = new String[] { idDominio, idSpAooFather, LuceneHelper._ID_SP_AOO_SCHEMA };
				} else {
					idSpAooValues = new String[] { idDominio, LuceneHelper._ID_SP_AOO_SCHEMA };
				}
				idSpAooFilter.put(LuceneHelper._FIELD_NAME_ID_SP_AOO, idSpAooValues);

				String lista = null;
				// sTmp[2] = "1";//test
				try {
					// ottengo i campi privati per la ricerca di Lucene
					String[] privacyFields = getPrivacyFieldList(conn, token);
					// creo il bean di Login
					AurigaLoginBean login = getLoginBean(token, userIdLavoro);
					filtroFullText = replaceConfiguredWildCards(conn, idDominio, filtroFullText);
					// chiamo l'api del Lucene Helper
					lista = luceneHandler.searchFullTextRubrica(
							flgDividi.equals("1") ? idDominio : "", // id
																	// dominio
																	// solo
																	// se
																	// gli
																	// indici
																	// sono
																	// folderizzati
																	// per
																	// dominio
							schemaName,// nome schema
							_OBJECT_CATEGORY_RUBRICA,
							checkAttrUsed, // attributi su cui cerco
							filtroFullText, // valore da cercare
							(searchAllTerms == null || searchAllTerms == 0) ? SearchType.TYPE_SEARCH_AT_LEAST_ONE_TERM : SearchType.TYPE_SEARCH_ALL_TERMS,
							null, filterObjType, idSpAooFilter, privacyFields[0], filter, login);

					// se NON abbiamo sforato la soglia chiamo la store con:
					// - MatchByIndexerIn con gli oggetti trovati
					// - FlgIndexerOverflowIn null
					// - Flg2ndCallIn null

					res = doDatabaseSearchForRubrica(
							conn,
							token,
							userIdLavoro,
							lista,// matchByIndexerIn,
							"",// flgIndexerOverflowIn flgIndexerOverflowIn,
							"",// flg2ndCallIn,flg2ndCallIn,
							denominazioneIO, FlgInclAltreDenomIO, FlgInclDenomStoricheIO, CFIO, PIVAIO, FlgFisicaGiuridicaIn, FlgNotCodTipiSottoTipiIn,
							ListaCodTipiSottoTipiIO, eMailIO, CodRapidoIO, CIProvSoggIO, FlgSoloVldIO, TsVldIO, CodApplOwnerIO, CodIstApplOwnerIO,
							FlgRestrApplOwnerIO, FlgCertificatiIO, FlgInclAnnullatiIO, IdSoggRubricaIO, FlgInIndicePAIO, CodAmmIPAIO, CodAOOIPAIO,
							IsSoggRubricaAppIO, IdGruppoAppIO, NomeGruppoAppIO, StrInDenominazioneIn, CriteriPersonalizzatiIO, colsOrderBy, flgDescOrderBy,
							flgSenzaPaginazione, numPagina, numRighePagina, OverFlowLimit, FlgSenzaTot, online, colsToReturn, finalita, CodIstatComuneIndIn,
							DesCittaIndIn, restringiARubricaDiUOIn, filtriAvanzatiIn);

					// completo l'array da ritornare con i parametri dalla
					// stringa
					ret = new Object[res.length];

					for (int i = 0; i < res.length; i++)
						ret[i] = res[i];

					// vediamo quanti sono i record in out
					numRecordOutputStore = (res[_NUM_REC_OUT_POSITION] == null) ? 0 : (new Integer(res[_NUM_REC_OUT_POSITION])).intValue();

					// la store non ha prodotto risultati
					// if (numRecordOutputStore == 0) {
					// return ret;
					// //throw new
					// VersionHandlerException(NO_DATA_FOUND_SEARCH_COD,NO_DATA_FOUND_SEARCH_MSG);
					// }

					// --------- >tutto OK
					return ret;

				} catch (LuceneNoDataFoundException lndfe) {
					aLogger.debug("nessun risultato trovato");
					return emptyRes;
					// throw new
					// VersionHandlerException(NO_DATA_FOUND_SEARCH_COD,NO_DATA_FOUND_SEARCH_MSG);
				} catch (LuceneTooManyValuesException ltmve) {
					aLogger.debug("troppi risultati: restringere i criteri di ricerca");
					throw new VersionHandlerException(SynchronousVersionHandler.SEARCH_OVERFLOW_COD, SynchronousVersionHandler.SEARCH_OVERFLOW_MSG);
				} catch (TooManyClauses tmc) {
					aLogger.debug("troppi risultati: restringere i criteri di ricerca");
					throw new VersionHandlerException(SynchronousVersionHandler.SEARCH_MANY_CLAUSES_COD, SynchronousVersionHandler.SEARCH_MANY_CLAUSES_MSG);
				} catch (LuceneOverflowException loe) {

					// ABBIAMO SFORATO!!!!!!
					// chiamo la store
					// - null
					// - FlgIndexerOverflowIn 1
					// - Flg2ndCallIn null
					// recupero la store
					res = doDatabaseSearchForRubrica(
							conn,
							token,
							userIdLavoro,
							"",// matchByIndexerIn matchByIndexerIn,
							"1",// flgIndexerOverflowIn flgIndexerOverflowIn,
							"",// flg2ndCallIn, flg2ndCallIn,
							denominazioneIO, FlgInclAltreDenomIO, FlgInclDenomStoricheIO, CFIO, PIVAIO, FlgFisicaGiuridicaIn, FlgNotCodTipiSottoTipiIn,
							ListaCodTipiSottoTipiIO, eMailIO, CodRapidoIO, CIProvSoggIO, FlgSoloVldIO, TsVldIO, CodApplOwnerIO, CodIstApplOwnerIO,
							FlgRestrApplOwnerIO, FlgCertificatiIO, FlgInclAnnullatiIO, IdSoggRubricaIO, FlgInIndicePAIO, CodAmmIPAIO, CodAOOIPAIO,
							IsSoggRubricaAppIO, IdGruppoAppIO, NomeGruppoAppIO, StrInDenominazioneIn, CriteriPersonalizzatiIO, colsOrderBy, flgDescOrderBy,
							flgSenzaPaginazione, numPagina, numRighePagina, OverFlowLimit, FlgSenzaTot, online, colsToReturn, finalita, CodIstatComuneIndIn,
							DesCittaIndIn, restringiARubricaDiUOIn, filtriAvanzatiIn);

					// completo l'array da ritornare con i parametri dalla
					// stringa
					ret = new Object[res.length];
					for (int i = 0; i < res.length; i++)
						ret[i] = res[i];

					// vediamo quanti sono i record in out
					numRecordOutputStore = (res[_NUM_REC_OUT_POSITION] == null) ? 0 : (new Integer(res[_NUM_REC_OUT_POSITION])).intValue();

					// la store non ha prodotto risultati
					if (numRecordOutputStore == 0) {
						return ret;
						// throw new
						// VersionHandlerException(NO_DATA_FOUND_SEARCH_COD,NO_DATA_FOUND_SEARCH_MSG);
					}

					try {
						// ottengo i campi privati per la ricerca di Lucene
						String[] privacyFields = getPrivacyFieldList(conn, token);
						// creo il bean di Login
						AurigaLoginBean login = getLoginBean(token, userIdLavoro);
						// chiamo l'api del Lucene Helper
						String listaLucene2 = luceneHandler.searchFullTextRubrica(
								flgDividi.equals("1") ? idDominio : "", // id
																		// dominio
																		// solo
																		// se
																		// gli
																		// indici
																		// sono
																		// folderizzati
																		// per
																		// dominio
								schemaName, // nome schema
								_OBJECT_CATEGORY_RUBRICA,
								checkAttrUsed, // attributi su cui cerco
								filtroFullText, // valore da cercare
								(searchAllTerms == null || searchAllTerms == 0) ? SearchType.TYPE_SEARCH_AT_LEAST_ONE_TERM : SearchType.TYPE_SEARCH_ALL_TERMS,
								res[_RESULT_OUT_POSITION], // risultato
															// della
															// store
															// (lista
															// std)
								filterObjType, idSpAooFilter, privacyFields[0], filter, login);

						// - MatchByIndexerIn con gli oggetti trovati
						// - FlgIndexerOverflowIn null
						// - Flg2ndCallIn 1
						res = doDatabaseSearchForRubrica(
								conn,
								token,
								userIdLavoro,
								listaLucene2,
								"",// flgIndexerOverflowIn flgIndexerOverflowIn,
								"1",// flg2ndCallIn, flg2ndCallIn,
								denominazioneIO, FlgInclAltreDenomIO, FlgInclDenomStoricheIO, CFIO, PIVAIO, FlgFisicaGiuridicaIn, FlgNotCodTipiSottoTipiIn,
								ListaCodTipiSottoTipiIO, eMailIO, CodRapidoIO, CIProvSoggIO, FlgSoloVldIO, TsVldIO, CodApplOwnerIO, CodIstApplOwnerIO,
								FlgRestrApplOwnerIO, FlgCertificatiIO, FlgInclAnnullatiIO, IdSoggRubricaIO, FlgInIndicePAIO, CodAmmIPAIO, CodAOOIPAIO,
								IsSoggRubricaAppIO, IdGruppoAppIO, NomeGruppoAppIO, StrInDenominazioneIn, CriteriPersonalizzatiIO, colsOrderBy, flgDescOrderBy,
								flgSenzaPaginazione, numPagina, numRighePagina, OverFlowLimit, FlgSenzaTot, online, colsToReturn, finalita,
								CodIstatComuneIndIn, DesCittaIndIn, restringiARubricaDiUOIn, filtriAvanzatiIn);

						// completo l'array da ritornare con i parametri dalla
						// stringa
						ret = new Object[res.length];
						for (int i = 0; i < res.length; i++)
							ret[i] = res[i];

						// vediamo quanti sono i record in out
						numRecordOutputStore = (res[_NUM_REC_OUT_POSITION] == null) ? 0 : (new Integer(res[_NUM_REC_OUT_POSITION])).intValue();
						// la store non ha prodotto risultati
						// if (numRecordOutputStore == 0) {
						// throw new
						// VersionHandlerException(NO_DATA_FOUND_SEARCH_COD,NO_DATA_FOUND_SEARCH_MSG);
						// }

						// tutto ok
						return ret;

					} catch (LuceneOverflowException e) {
						aLogger.debug("trovato numero record sopra il massimo consentito");
						throw new VersionHandlerException(SynchronousVersionHandler.SEARCH_OVERFLOW_COD, SynchronousVersionHandler.SEARCH_OVERFLOW_MSG);
					} catch (LuceneNoDataFoundException e) {
						aLogger.debug("nessun risultato trovato");
						return emptyRes;
						// throw new
						// VersionHandlerException(NO_DATA_FOUND_SEARCH_COD,NO_DATA_FOUND_SEARCH_MSG);
					} catch (LuceneException le) {
						aLogger.error("Errore nella ricerca tramite Lucene", le);
						throw new VersionHandlerException(SynchronousVersionHandler.LUCENE_ERR_COD, SynchronousVersionHandler.LUCENE_ERR_MSG + le.getMessage());
					}

				} catch (LuceneException le) {
					aLogger.error("Errore nella ricerca tramite Lucene", le);
					throw new VersionHandlerException(SynchronousVersionHandler.LUCENE_ERR_COD, SynchronousVersionHandler.LUCENE_ERR_MSG + le.getMessage());
				}

			}
			/*
			 * FILTRO NON VALORIZZATO = semplice chiamata alla store
			 */
			else {

				res = doDatabaseSearchForRubrica(
						conn,
						token,
						userIdLavoro,
						"",// matchByIndexerIn matchByIndexerIn,
						"",// flgIndexerOverflowIn flgIndexerOverflowIn,
						"",// flg2ndCallIn, flg2ndCallIn,
						denominazioneIO, FlgInclAltreDenomIO, FlgInclDenomStoricheIO, CFIO, PIVAIO, FlgFisicaGiuridicaIn, FlgNotCodTipiSottoTipiIn,
						ListaCodTipiSottoTipiIO, eMailIO, CodRapidoIO, CIProvSoggIO, FlgSoloVldIO, TsVldIO, CodApplOwnerIO, CodIstApplOwnerIO,
						FlgRestrApplOwnerIO, FlgCertificatiIO, FlgInclAnnullatiIO, IdSoggRubricaIO, FlgInIndicePAIO, CodAmmIPAIO, CodAOOIPAIO,
						IsSoggRubricaAppIO, IdGruppoAppIO, NomeGruppoAppIO, StrInDenominazioneIn, CriteriPersonalizzatiIO, colsOrderBy, flgDescOrderBy,
						flgSenzaPaginazione, numPagina, numRighePagina, OverFlowLimit, FlgSenzaTot, online, colsToReturn, finalita, CodIstatComuneIndIn,
						DesCittaIndIn, restringiARubricaDiUOIn, filtriAvanzatiIn);

				// completo l'array da ritornare con i parametri dalla stringa
				ret = new Object[res.length];
				for (int i = 0; i < res.length; i++)
					ret[i] = res[i];

				// vediamo quanti sono i record in out
				numRecordOutputStore = (res[_NUM_REC_OUT_POSITION] == null) ? 0 : (new Integer(res[_NUM_REC_OUT_POSITION])).intValue();
				// la store non ha prodotto risultati
				// if (numRecordOutputStore == 0) {
				// throw new
				// VersionHandlerException(NO_DATA_FOUND_SEARCH_COD,NO_DATA_FOUND_SEARCH_MSG);
				// }

				// tutto ok
				return ret;
			}
		} catch (LuceneHandlerException ex2) {
			// ROLLBACK
			try {
				DBHelperSavePoint.RollbackToSavepoint(conn, "VHFINDRUBOBJ");
			} catch (Exception e) {
			}
			// log
			aLogger.error("Errore in FIND RUBRICA OBJECT. ErrMsg: " + ex2.getMessage(), ex2);
			// rilancio l'eccezione
			throw new VersionHandlerException(SynchronousVersionHandler.LUCENE_HANDLER_COD, SynchronousVersionHandler.LUCENE_HANDLER_MSG + "("
					+ ex2.getMessage() + ")");

		} catch (VersionHandlerException ex1) {
			// ROLLBACK
			try {
				DBHelperSavePoint.RollbackToSavepoint(conn, "VHFINDRUBOBJ");
			} catch (Exception e) {
			}
			// log
			aLogger.error("Errore in FIND RUBRICA OBJECT.\nErrcode:" + ex1.getCodice() + "\nErrMsg: " + ex1.getMessage(), ex1);
			// rilancio l'eccezione
			throw ex1;
		}
		// ************ eccezione IMPREVISTA *************
		catch (Exception ex) {
			// ROLLBACK
			try {
				DBHelperSavePoint.RollbackToSavepoint(conn, "VHFINDRUBOBJ");
			} catch (Exception e) {
			}
			// log
			aLogger.debug("Errore GENERICO in FIND RUBRICA OBJECT.\nException: " + ex.getMessage(), ex);
			// rilancio l'eccezione trasformata
			throw new VersionHandlerException(SynchronousVersionHandler.IMPREVISTO_COD, SynchronousVersionHandler.IMPREVISTO_MSG + "(" + ex.getMessage() + ")");
		} finally {
			aLogger.info("Fine findRubricaObject");
		}
	}

	private String[] doDatabaseSearchForRubrica(Connection conn, String token, String userIdLavoro, String matchByIndexerIn, String flgIndexerOverflowIn,
			String flg2ndCallIn, String denominazioneIO, Integer FlgInclAltreDenomIO, Integer FlgInclDenomStoricheIO, String CFIO, String PIVAIO,
			String FlgFisicaGiuridicaIn, String FlgNotCodTipiSottoTipiIn, String ListaCodTipiSottoTipiIO, String eMailIO, String CodRapidoIO,
			String CIProvSoggIO, Integer FlgSoloVldIO, String TsVldIO, String CodApplOwnerIO, String CodIstApplOwnerIO, Integer FlgRestrApplOwnerIO,
			Integer FlgCertificatiIO, Integer FlgInclAnnullatiIO, Integer IdSoggRubricaIO, Integer FlgInIndicePAIO, String CodAmmIPAIO, String CodAOOIPAIO,
			Integer IsSoggRubricaAppIO, Integer IdGruppoAppIO, String NomeGruppoAppIO, String StrInDenominazioneIn, String CriteriPersonalizzatiIO,
			String colsOrderBy, String flgDescOrderBy, Integer flgSenzaPaginazione, Integer numPagina, Integer numRighePagina, Integer OverFlowLimit,
			String FlgSenzaTot, Integer online, String colsToReturn, String finalita, String CodIstatComuneIndIn, String DesCittaIndIn,
			String restringiARubricaDiUOIn, String filtriAvanzatiIn) throws Exception {

		StoreProcedure store = null;
		String res[] = null;
		int errCode = 0;
		String errMsg = "";
		try {

			aLogger.info("Inizio doDatabaseSearchForRubrica");

			store = getStoreProcedureFromRepositoryDefinitionSingleton("TrovaSoggetti", "TrovaSoggetti");

			// Impostiamo i valori per la store procedure....
			setStoreProcedureParametersValues(
					conn,
					store,
					new Object[] {
							"CodIdConnectionTokenIn",
							token, // token di connessione
							"IdUserLavoroIn",
							userIdLavoro, // user id lavoro
							"MatchByIndexerIn", matchByIndexerIn, "FlgIndexerOverflowIn", flgIndexerOverflowIn, "Flg2ndCallIn", flg2ndCallIn,
							"DenominazioneIO", (denominazioneIO == null) ? "" : denominazioneIO, "FlgInclAltreDenomIO",
							(FlgInclAltreDenomIO == null) ? "" : FlgInclAltreDenomIO.toString(), "FlgInclDenomStoricheIO",
							(FlgInclDenomStoricheIO == null) ? "" : FlgInclDenomStoricheIO.toString(), "CFIO", (CFIO == null) ? "" : CFIO, "PIVAIO",
							(PIVAIO == null) ? "" : PIVAIO, "FlgFisicaGiuridicaIn", (FlgFisicaGiuridicaIn == null) ? "" : FlgFisicaGiuridicaIn,
							"FlgNotCodTipiSottoTipiIn", (FlgNotCodTipiSottoTipiIn == null) ? "0" : FlgNotCodTipiSottoTipiIn, "ListaCodTipiSottoTipiIO",
							(ListaCodTipiSottoTipiIO == null) ? "" : ListaCodTipiSottoTipiIO, "eMailIO", (eMailIO == null) ? "" : eMailIO, "CodRapidoIO",
							(CodRapidoIO == null) ? "" : CodRapidoIO, "CIProvSoggIO", (CIProvSoggIO == null) ? "" : CIProvSoggIO, "FlgSoloVldIO",
							(FlgSoloVldIO == null) ? "" : FlgSoloVldIO.toString(), "TsVldIO", (TsVldIO == null) ? "" : TsVldIO, "CodApplOwnerIO",
							(CodApplOwnerIO == null) ? "" : CodApplOwnerIO, "CodIstApplOwnerIO", (CodIstApplOwnerIO == null) ? "" : CodIstApplOwnerIO,
							"FlgRestrApplOwnerIO", (FlgRestrApplOwnerIO == null) ? "" : FlgRestrApplOwnerIO.toString(), "FlgCertificatiIO",
							(FlgCertificatiIO == null) ? "" : FlgCertificatiIO.toString(), "FlgInclAnnullatiIO",
							(FlgInclAnnullatiIO == null) ? "" : FlgInclAnnullatiIO.toString(), "IdSoggRubricaIO",
							(IdSoggRubricaIO == null) ? "" : IdSoggRubricaIO.toString(), "FlgInIndicePAIO",
							(FlgInIndicePAIO == null) ? "" : FlgInIndicePAIO.toString(), "CodAmmIPAIO", (CodAmmIPAIO == null) ? "" : CodAmmIPAIO,
							"CodAOOIPAIO", (CodAOOIPAIO == null) ? "" : CodAOOIPAIO, "IsSoggRubricaAppIO",
							(IsSoggRubricaAppIO == null) ? "" : IsSoggRubricaAppIO.toString(), "IdGruppoAppIO",
							(IdGruppoAppIO == null) ? "" : IdGruppoAppIO.toString(), "NomeGruppoAppIO", (NomeGruppoAppIO == null) ? "" : NomeGruppoAppIO,
							"StrInDenominazioneIn", (StrInDenominazioneIn == null) ? "" : StrInDenominazioneIn, "CriteriPersonalizzatiIO",
							(CriteriPersonalizzatiIO == null) ? "" : CriteriPersonalizzatiIO, "ColOrderByIO", (colsOrderBy == null) ? "" : colsOrderBy,
							"FlgDescOrderByIO", (flgDescOrderBy == null) ? "0" : flgDescOrderBy, "FlgSenzaPaginazioneIn",
							(flgSenzaPaginazione == null) ? "0" : flgSenzaPaginazione.toString(), "NroPaginaIO",
							(numPagina == null) ? "" : numPagina.toString(), "BachSizeIO", (numRighePagina == null) ? "" : numRighePagina.toString(),
							"OverFlowLimitIn", (OverFlowLimit == null) ? "" : OverFlowLimit.toString(), "FlgSenzaTotIn",
							(FlgSenzaTot == null) ? "0" : FlgSenzaTot, "FlgBatchSearchIn", (online == null) ? "0" : online.toString(), "ColToReturnIn",
							(colsToReturn == null) ? "" : colsToReturn, "FinalitaIn", (finalita == null) ? "" : finalita, "CodIstatComuneIndIn",
							(CodIstatComuneIndIn == null) ? "" : CodIstatComuneIndIn, "DesCittaIndIn", (DesCittaIndIn == null) ? "" : DesCittaIndIn,
							"RestringiARubricaDiUOIn", (restringiARubricaDiUOIn == null) ? "" : restringiARubricaDiUOIn, "FiltriAvanzatiIn",
							(filtriAvanzatiIn == null) ? "" : filtriAvanzatiIn });

			// log
			aLogger.debug("Eseguo store TrovaRubrica");
			// esecuzione della store proc
			store.execute();

			// leggo parametri di out
			res = getStoreProcedureParametersValues(store, new String[] { "ListaXMLOut", "NroTotRecOut", "NroRecInPaginaOut", "FlgAbilInsOut",
					"FlgMostraAltriAttrOut", "ErrorMessage" , "BachSizeIO" });

			return res;

		} catch (StoreProcedureException ex0) {
			// ROLLBACK
			try {
				DBHelperSavePoint.RollbackToSavepoint(conn, "VHFINDRUBOBJ");
			} catch (Exception e) {
			}
			// Qualcosa è andato storto!!!!
			String err[] = getInfoFromStoreProcedureException(store, ex0);
			errCode = Integer.parseInt(err[0]);
			errMsg = err[1];

			if (errCode == 1011) {
				aLogger.debug("Errore 1011 di OVERFLOW in FIND RUBRICA OBJECT.\nErrcode:" + errCode + "\nErrMsg: " + errMsg, ex0);
				throw new VersionHandlerException(SynchronousVersionHandler.SEARCH_OVERFLOW_COD, SynchronousVersionHandler.SEARCH_OVERFLOW_MSG);
			} else {
				aLogger.error("Errore STORE in FIND RUBRICA OBJECT.\nErrcode:" + errCode + "\nErrMsg: " + errMsg, ex0);
				throw new VersionHandlerException(errCode, errMsg);
			}
		} finally {
			aLogger.info("Fine doDatabaseSearchForRubrica");
		}

	}

	public FindElenchiAlbiResultBean findElenchiAlbi(Connection conn, String token, String userIdLavoro, String filtroFullText, String[] checkAttributes,
			Integer searchAllTerms, String[] filterObjType, String matchByIndexer, String flgIndexerOverflow, String flg2ndCall, String criteriPersonalizzati,
			String advancedFilters, String colsOrderBy, String flgDescOrderBy, Integer flgSenzaPaginazione, Integer numPagina, Integer numRighePagina,
			Integer overFlowLimit, String flgSenzaTot, Integer online, String colsToReturn, String finalita, LuceneParameterFilter filter,
			BigDecimal idTipoElencoAlbo) throws VersionHandlerException {

		FindElenchiAlbiResultBean ret = null;
		try {

			aLogger.info("Inizio findElenchiAlbi");

			DmpkElenchiAlbiTrovacontenutielencoalboBean bean = new DmpkElenchiAlbiTrovacontenutielencoalboBean();
			bean.setCodidconnectiontokenin(token);
			bean.setColorderbyio(colsOrderBy);
			bean.setBachsizeio((numRighePagina == null) ? 0 : numRighePagina);
			bean.setColtoreturnin(colsToReturn);
			bean.setCriteriavanzatiio((advancedFilters == null) ? "" : advancedFilters);
			bean.setCriteripersonalizzatiio((criteriPersonalizzati == null) ? "" : criteriPersonalizzati);
			bean.setFinalitain(finalita);
			bean.setFlg2ndcallin(flg2ndCall != null ? new Integer(flg2ndCall) : null);
			bean.setFlgbatchsearchin((online == null) ? 0 : online);
			bean.setFlgdescorderbyio((flgDescOrderBy == null) ? "0" : flgDescOrderBy);
			bean.setFlgindexeroverflowin(flgIndexerOverflow != null ? new Integer(flgIndexerOverflow) : null);
			bean.setFlgsenzapaginazionein((flgSenzaPaginazione == null) ? 0 : flgSenzaPaginazione);
			bean.setFlgsenzatotin((flgSenzaTot == null) ? 0 : new Integer(flgSenzaTot));
			bean.setIdelencoalbotypein(idTipoElencoAlbo);
			bean.setIduserlavoroin(userIdLavoro != null ? new BigDecimal(userIdLavoro) : null);
			bean.setMatchbyindexerin(matchByIndexer);
			bean.setNropaginaio((numPagina == null) ? 0 : numPagina);
			bean.setOverflowlimitin((overFlowLimit == null) ? 0 : overFlowLimit);

			TrovacontenutielencoalboImpl storeAlbi = new TrovacontenutielencoalboImpl();
			storeAlbi.setBean(bean);
			storeAlbi.execute(conn);
			StoreResultBean<DmpkElenchiAlbiTrovacontenutielencoalboBean> result = new StoreResultBean<DmpkElenchiAlbiTrovacontenutielencoalboBean>();
			AnalyzeResult.analyze(bean, result);
			result.setResultBean(bean);
			if (result.isInError()) {
				aLogger.error("Errore in STORE DMPK_ELENCHI_ALBI.TrovaContenutiElencoAlbo.\nErrCode: " + result.getErrorCode() + "\nErrMsg: "
						+ result.getDefaultMessage());
				aLogger.debug(result.getDefaultMessage());
				aLogger.debug(result.getErrorContext());
				aLogger.debug(result.getErrorCode());
				throw new Exception(result.getErrorCode() + result.getDefaultMessage());
			}

			ret = new FindElenchiAlbiResultBean();
			ret.setErrCode(result.getResultBean().getErrcodeout());
			ret.setErrContext(result.getResultBean().getErrcontextout());
			ret.setErrMsg(result.getResultBean().getErrmsgout());
			ret.setNroRecInPagina(result.getResultBean().getNrorecinpaginaout());
			ret.setNroTotRec(result.getResultBean().getNrototrecout());
			ret.setResult(result.getResultBean().getResultout());

			return ret;

		} catch (Exception e) {

			aLogger.error("Errore in FIND ELENCHI ALBI.\nErrMsg: " + e.getMessage(), e);
			throw new VersionHandlerException(e.getMessage());

		} finally {
			aLogger.info("Fine findElenchiAlbi");
		}

	}

}
