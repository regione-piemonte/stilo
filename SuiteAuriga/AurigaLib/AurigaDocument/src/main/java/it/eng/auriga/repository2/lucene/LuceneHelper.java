/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.database.store.dmpk_def_security.bean.DmpkDefSecurityGetidorganigrammaspaooBean;
import it.eng.auriga.database.store.dmpk_def_security.store.impl.GetidorganigrammaspaooImpl;
import it.eng.auriga.database.store.dmpk_titolario.bean.DmpkTitolarioGetidpianoclassifspaooBean;
import it.eng.auriga.database.store.dmpk_titolario.store.impl.GetidpianoclassifspaooImpl;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.repository2.model.EnhancedDocument;
import it.eng.gd.lucenemanager.config.DbConfig;
import it.eng.gd.lucenemanager.util.EnhancedJdbcDirectory;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.jaxb.variabili.Lista.Riga.Colonna;
import it.eng.spring.utility.SpringAppContext;
import it.eng.storeutil.AnalyzeResult;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;

import oracle.jdbc.pool.OracleDataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.BooleanQuery.TooManyClauses;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.jdbc.dialect.OracleDialect;
import org.apache.lucene.util.Version;

public class LuceneHelper {
	public static final String _ID_FOLDER_PREFIX = "F";

	public static final String _ID_DOC_PREFIX = "D";

	public static final String _ID_PROC_PREFIX = "P";

	public static final String _ID_UO_PREFIX = "UO";

	public static final String _ID_RS_PREFIX = "RS";

	public static final String _ID_UTENTE_PREFIX = "UT";

	public static final String _ID_SCRIVANIA_PREFIX = "SV";

	public static final String _ID_CLASSIFICAZIONE_PREFIX = "CL";

	public static final String _VERSION_SEP = "V";

	public static final String _DELETED_DOC = "#DELETED";

	public static final String _DB_TIPO_FOLDER = "F";

	public static final String _DB_TIPO_UD = "U";

	public static final String _FIELD_NAME_CI_OBJ = "ci_obj";

	public static String _ID_OBJECT = "OBJECT_ID_KEY";

	public static final String _FIELD_NAME_ID_USER = "id_user";

	public static final String _FIELD_NAME_ID_SP_AOO = "ID_SP_AOO";

	public static final String _FIELD_NAME_ID_PIANO_CLASSIF = "ID_PIANO_CLASSIF";

	public static final String _FIELD_NAME_ID_ORGANIGRAMMA = "ID_ORGANIGRAMMA";

	public static final String _FIELD_NAME_ID_UD = "ID_UD";

	public static final String _FIELD_NAME_PROC_LIST = "LISTA_ID_PROCESSI_COLLEGATI";

	public static final String _ID_SP_AOO_SCHEMA = "SCHEMA";

	public static final Version _LUCENE_VERSION = Version.LUCENE_35;

	public static final Integer TERM_MINIMUM_LENGTH = 4;

	public static final int _LUCENE_MAX_CLAUSE_COUNT = Integer.parseInt(System.getProperty("org.apache.lucene.maxClauseCount",
			Integer.toString(BooleanQuery.getMaxClauseCount())));

	private static final String JOLLY_CHAR = "*";

	private static Logger aLogger = Logger.getLogger(LuceneHelper.class.getName());

	/**
	 * Restituisce il risultato della ricerca effettuata su Lucene (in formato LISTA_STD)
	 * 
	 * 
	 * @param path
	 *            Percorso dove è presente l'indice di Lucene
	 * @param fieldName
	 *            Array di stringhe con i nomi degli attributi tra cui cercare il testo presente in searchValue
	 * @param searchValue
	 *            Testo da cercare
	 * @param overflowLimit
	 *            Identifica il valore massimo accettabile di risultati (in caso contrario viene lanciata una eccezione)
	 * @return Xml rappresentante la LISTA_STD
	 * @throws Lancia
	 *             un eccezione di tipo LuceneException con il testo dell'errore e con codice - LuceneException._ERR_SEARCH_OVERFLOW se il
	 *             numero di record trovati è superiore a overflowLimit - LuceneException._ERR_SEARCH_NO_FOUND se la ricarca non ha avuto
	 *             risultati - LuceneException._ERR_GENERIC se si è verificato un errore generico
	 */
	public static String searchFullText(SearchArea searchType, String path, String[] fieldName, String searchValue, SearchType typeSearch,
			int overflowLimit, String privacyfield, LuceneParameterFilter parFilter, AurigaLoginBean login, Connection conn)
			throws LuceneException {
		String filterObjType = null;
		return searchFullText(searchType, path, fieldName, searchValue, typeSearch, overflowLimit, null, filterObjType, privacyfield,
				parFilter, login, conn);
	}

	/**
	 * Restituisce il risultato della ricerca effettuata su Lucene (in formato LISTA_STD)
	 * 
	 * @param path
	 *            Percorso dove è presente l'indice di Lucene
	 * @param fieldName
	 *            Array di stringhe con i nomi degli attributi tra cui cercare il testo presente in searchValue
	 * @param searchValue
	 *            Testo da cercare
	 * @param overflowLimit
	 *            Identifica il valore massimo oltre il quale viene lanciata una LuceneException con codice LuceneException._ERR_SEARCH_OVERFLOW
	 * @param filterObjects
	 *            Insieme di oggetti (in formato LISTA_STD) su cui fare la ricerca
	 * @param filterObjType
	 *            Identifica il tipo di oggetti lucene su cui si vuole ricercare D o F per doc o folder. ATTENZIONE: il filtro viene applicato sui risultati in
	 *            uscita da Lucene e viene comunque eseguita una ricerca non filtrata su lucene
	 * @return Xml rappresentante la LISTA_STD
	 * @throws Lancia
	 *             un eccezione di tipo LuceneException con il testo dell'errore e con codice - LuceneException._ERR_SEARCH_OVERFLOW se il numero di record
	 *             trovati è superiore a overflowLimit - LuceneException._ERR_SEARCH_NO_FOUND se la ricarca non ha avuto risultati -
	 *             LuceneException._ERR_GENERIC se si è verificato un errore generico
	 */
	public static String searchFullText(SearchArea searchType, String path, String[] fieldName, String searchValue, SearchType typeSearch,
			int overflowLimit, String filterObjects, String filterObjType, String privacyFields, LuceneParameterFilter parFilter,
			AurigaLoginBean login, Connection conn) throws LuceneException {
		String[] filterObjTypeAr = { filterObjType };
		return searchFullText(searchType, path, fieldName, searchValue, typeSearch, overflowLimit, filterObjects, filterObjTypeAr, null,
				privacyFields, parFilter, login, conn);
	}

	/**
	 * Restituisce il risultato della ricerca effettuata su Lucene (in formato LISTA_STD)
	 * 
	 * @param path
	 *            Percorso dove è presente l'indice di Lucene
	 * @param fieldName
	 *            Array di stringhe con i nomi degli attributi tra cui cercare il testo presente in searchValue
	 * @param searchValue
	 *            Testo da cercare
	 * @param overflowLimit
	 *            Identifica il valore massimo oltre il quale viene lanciata una LuceneException con codice LuceneException._ERR_SEARCH_OVERFLOW
	 * @param filterObjects
	 *            Insieme di oggetti (in formato LISTA_STD) su cui fare la ricerca
	 * @param filterObjType
	 *            Identifica un array di tipi di oggetti lucene su cui si vuole ricercare (per esempio, D per doc, F per folder) ATTENZIONE: il filtro viene
	 *            applicato sui risultati in uscita da Lucene e viene comunque eseguita una ricerca non filtrata su lucene
	 * @return Xml rappresentante la LISTA_STD
	 * @throws Lancia
	 *             un eccezione di tipo LuceneException con il testo dell'errore e con codice - LuceneException._ERR_SEARCH_OVERFLOW se il numero di record
	 *             trovati è superiore a overflowLimit - LuceneException._ERR_SEARCH_NO_FOUND se la ricarca non ha avuto risultati -
	 *             LuceneException._ERR_GENERIC se si è verificato un errore generico
	 */
	public static String searchFullText(SearchArea searchType, String path, String[] fieldName, String searchValue, SearchType typeSearch,
			int overflowLimit, String filterObjects, String[] filterObjType, HashMap customFilter, String privacyFields,
			LuceneParameterFilter parFilter, AurigaLoginBean login, Connection conn) throws LuceneException {
		aLogger.debug("la ricerca viene fatta con il seguente filterObject: " + filterObjects);
		String res = "";
		IndexSearcher is = null;
		HashMap hmUdObj = null;
		HashMap hmUtObj = null;
		HashMap hmProcObj = null;
		HashMap hmFolderObj = null;

		Float maxScore = new Float("0");
		int i = 0;
		Long timePartenza = System.currentTimeMillis();
		List<String> terms = new ArrayList<String>();
		List<LuceneWordObject> parole = new ArrayList<LuceneWordObject>();
		try {
			try {
				is = getSearcher(path);
			} catch (LuceneNoSegmentsFileFoundException lnsffe) {
				// Non ci sono indici, non ritorno alcun risultato
				throw new LuceneNoDataFoundException("La ricerca non ha avuto alcun risultato");
			}
			// non aggiungo più il carattere jolly

			// if (searchValue != null) {
			// String searchValueNew = "";
			// StringTokenizer st = new StringTokenizer(searchValue);
			//
			// while (st.hasMoreTokens()) {
			// String nToken = st.nextToken();
			// terms.add(nToken + JOLLY_CHAR);
			// // verifico che non ci sia il carattere jolly ad inizio parola (raj)
			// if (nToken.startsWith(JOLLY_CHAR)) {
			// throw new LuceneException("Il carattere jolly non puo' essere usato ad inizio parola");
			// }
			// searchValueNew += nToken + JOLLY_CHAR + " ";
			// }
			// searchValue = searchValueNew.trim();
			// }

			aLogger.debug("verifico la frequenza dei termini di ricerca senza carattere jolly");
			Long timePreFreq = System.currentTimeMillis();
			IndexReader reader = is.getIndexReader();
			boolean isOverflow = false;
			StringTokenizer st = new StringTokenizer(searchValue);
			while (st.hasMoreTokens()) {
				String nToken = st.nextToken();
				if (nToken.startsWith(JOLLY_CHAR)) {
					throw new LuceneException(
							"Nel filtro \"Cerca\" non e' ammesso specificare la fine di parole (termini con * o %  iniziale )");
				}
				if (nToken.contains(JOLLY_CHAR) && nToken.substring(0, nToken.indexOf(JOLLY_CHAR)).length() < TERM_MINIMUM_LENGTH) {
					throw new LuceneException(
							"Nel filtro \"Cerca\" non e' ammesso specificare meno di 4 caratteri prima del carattere wildcard (* o %) per ogni singola parola");
				}
				terms.add(nToken);
			}
			List<String> terminiJolly = new ArrayList<String>();
			// scorro i termini per verificare le occorrenze e capire se i criteri inseriti
			// sono sufficientemente restrittivi
			for (String termine : terms) {
				if (!termine.contains(JOLLY_CHAR)) {
					int count = 0;
					for (String field : fieldName) {
						// count += reader.docFreq(new Term(field, termine.substring(0, termine.length() - 1)));
						count += reader.docFreq(new Term(field, termine.toLowerCase()));
					}
					aLogger.debug("frequenza: " + termine + " :" + count);
					// se non ci sono occorrenze è inutile continuare
					if (count == 0) {
						throw new LuceneNoDataFoundException("La ricerca non ha avuto alcun risultato");
					}
					LuceneWordObject object = new LuceneWordObject();
					if (count > overflowLimit) {
						object.setOverflow(true);
						isOverflow = true;
					} else {
						object.setOverflow(false);
					}
					object.setCount(count);
					object.setTerm(termine);
					parole.add(object);
				} else {
					terminiJolly.add(termine);
				}
			}
			aLogger.debug("tempo per verificare le frequenze: " + (System.currentTimeMillis() - timePreFreq));
			aLogger.debug("ordino la lista di parole secondo occorrenze");
			Collections.sort(parole);
			// se almeno una parola ha avuto overflow verifico se tutte
			// le parole inserite sono in overflow e nel caso mando eccezione
			if (isOverflow && (filterObjects == null || filterObjects == "")) {
				if (typeSearch == SearchType.TYPE_SEARCH_AT_LEAST_ONE_TERM) {
					aLogger.debug("I termini inseriti presentano eccessive occorrenze. Restringere i criteri di ricerca");
					throw new LuceneOverflowException(
							"I termini inseriti presentano eccessive occorrenze. Restringere i criteri di ricerca");
				} else {
					for (LuceneWordObject termine : parole) {
						if (!termine.getOverflow()) {
							isOverflow = false;
						}
					}
				}
			}
			// se è ancora in overflow lancio eccezione
			if (isOverflow && (filterObjects == null || filterObjects == "")) {
				aLogger.debug("I termini inseriti presentano eccessive occorrenze. Restringere i criteri di ricerca");
				throw new LuceneOverflowException("I termini inseriti presentano eccessive occorrenze. Restringere i criteri di ricerca");
			}
			// aggiungo anche le parole jolly
			for (String tj : terminiJolly) {
				LuceneWordObject word = new LuceneWordObject();
				word.setTerm(tj);
				parole.add(word);
			}
			Query termQueryFilter = null;
			Query queryFieldName = null;
			// Query queryFieldName = null;
			BooleanQuery queryFilter = null;
			BooleanQuery queryCustomFilter = null;
			BooleanQuery currentSearching = new BooleanQuery();
			MultiFieldQueryParser mfqp = null;
			if (filterObjects != null && filterObjects != "") {
				queryFilter = FilterObjectUtils.getQuery(searchType, filterObjects);
			}
			TopDocs topdocs = null;
			HashMap<String, EnhancedDocument> documents = new HashMap<String, EnhancedDocument>();
			Map<String, List<String>> relazioniCiObjIdUd = new HashMap<String, List<String>>();
			Long timePostSearcher = System.currentTimeMillis();
			aLogger.debug("tempo inizializzazione: " + (timePostSearcher - timePartenza));
			// Costruzione della query sui fieldName passati
			// Set del tipo di operatore da usare
			switch (typeSearch) {
			case TYPE_SEARCH_AT_LEAST_ONE_TERM: {
				aLogger.debug("ricerca almeno un termine");
				// Costruzione della query sui fieldName passati
				mfqp = new MultiFieldQueryParser(_LUCENE_VERSION, fieldName, new StandardAnalyzer(_LUCENE_VERSION));
				// I termini devono essere presenti in almeno uno dei fieldName
				// Deve esserci almeno un termine tra i risulati
				mfqp.setDefaultOperator(mfqp.OR_OPERATOR);
				// Creazione effettiva della query
				queryFieldName = mfqp.parse(searchValue);
				currentSearching.add(queryFieldName, BooleanClause.Occur.MUST);
				break;
			}
			case TYPE_SEARCH_ALL_TERMS: {
				aLogger.debug("ricerca per tutti i termini");
				// Devono esserci tutti i termini tra i risulati
				// creo una query per ciascun termine partendo da quella con meno occorrenze
				// dato che ho pre-ordinato la lista
				// StringTokenizer st = new StringTokenizer(searchValue);
				List<BooleanQuery> searching = new ArrayList<BooleanQuery>();
				// while (st.hasMoreTokens()) {
				for (LuceneWordObject token : parole) {
					// nToken = st.nextToken();
					mfqp = new MultiFieldQueryParser(_LUCENE_VERSION, fieldName, new StandardAnalyzer(_LUCENE_VERSION));
					queryFieldName = mfqp.parse(token.getTerm());
					BooleanQuery tokenSearching = new BooleanQuery();
					tokenSearching.add(queryFieldName, BooleanClause.Occur.MUST);
					searching.add(tokenSearching);
				}
				/*** INIZIO MODIFICA RAJ ***/
				// IL RISULTATO CORRETTO E' QUELLO CHE CERCA TUTTE LE PAROLE
				// ALL'INTERNO DI TUTTI I CAMPI
				// cerco di ciclare sulle singole condizioni e successivamente
				// di fare l'intersezione degli insiemi
				// Iterator<BooleanQuery> bqIter = searching.iterator();
				List<TopDocs> listaDocOk = new ArrayList<TopDocs>();
				Map<String, EnhancedDocument> mappaCompleta = new HashMap<String, EnhancedDocument>();
				List<String> chiaviFascicolo = null;
				List<String> listaRef = null;
				// ciascun token inserito può avere più risultati su vari campi
				// li raccolgo tutti e inserisco i documenti comprensivi di
				// chiave
				// all'interno della mappa completa
				// while (bqIter.hasNext()) {
				for (BooleanQuery bq : searching) {
					BooleanQuery nq = null;
					BooleanQuery queryFascicoli = null;
					// BooleanQuery bq = bqIter.next();
					if (mappaCompleta.keySet().size() != 0) {
						nq = new BooleanQuery();
						nq.setMinimumNumberShouldMatch(1);
						if (searchType == SearchArea.SEARCH_TYPE_REPOSITORY_DOC_FOLDER) {
							Set<String> chiavi = relazioniCiObjIdUd.keySet();
							String[] fields = new String[1];
							fields[0] = _FIELD_NAME_ID_UD;
							for (String key : chiavi) {
								MultiFieldQueryParser multiquery = new MultiFieldQueryParser(_LUCENE_VERSION, fields, new StandardAnalyzer(
										_LUCENE_VERSION));
								Query qfn = multiquery.parse(key);
								nq.add(qfn, BooleanClause.Occur.SHOULD);
							}
							aLogger.debug("query di affinamento sulle chiavi: " + nq.toString());
							if (chiaviFascicolo != null && chiaviFascicolo.size() != 0) {
								queryFascicoli = new BooleanQuery();
								queryFascicoli.setMinimumNumberShouldMatch(1);
								for (String chiaveFascicolo : chiaviFascicolo) {
									fields[0] = _ID_OBJECT;
									MultiFieldQueryParser multiquery = new MultiFieldQueryParser(_LUCENE_VERSION, fields,
											new StandardAnalyzer(_LUCENE_VERSION));
									Query qfnFascicoli = multiquery.parse(chiaveFascicolo);
									queryFascicoli.add(qfnFascicoli, BooleanClause.Occur.SHOULD);
								}
								aLogger.debug("query di affinamento sulle chiavi: " + queryFascicoli.toString());
							}
						} else {
							Set<String> chiavi = mappaCompleta.keySet();
							String[] fields = new String[1];
							fields[0] = _ID_OBJECT;
							for (String key : chiavi) {
								MultiFieldQueryParser multiquery = new MultiFieldQueryParser(_LUCENE_VERSION, fields, new StandardAnalyzer(
										_LUCENE_VERSION));
								Query qfn = multiquery.parse(key);
								nq.add(qfn, BooleanClause.Occur.SHOULD);
							}
							aLogger.debug("query di affinamento sulle chiavi: " + nq.toString());
						}
					}
					BooleanQuery mainQuery = new BooleanQuery();
					mainQuery.add(bq, BooleanClause.Occur.MUST);
					if (nq != null) {
						if (queryFascicoli == null) {
							mainQuery.add(nq, BooleanClause.Occur.MUST);
						} else {
							Query fascicoliOrDoc = Query.mergeBooleanQueries(nq, queryFascicoli);
							mainQuery.add(fascicoliOrDoc, BooleanClause.Occur.MUST);
						}
					}
					// se il queryfilter è valorizzato devo ciclare su tutte le query presenti e inserirle solo nella prima delle query da
					// effettuare
					// in modo da restringere il campo
					if (queryFilter != null && (mappaCompleta == null || mappaCompleta.keySet().size() == 0)) {
						aLogger.debug("query sugli oggetti filterObject: " + queryFilter.toString());
						mainQuery.add(queryFilter, BooleanClause.Occur.MUST);
						TopDocs parziale = is.search(mainQuery, null, Integer.MAX_VALUE);
						aLogger.debug("Risultati parziali con filterObject valorizzato : " + parziale.totalHits + " per la query: "
								+ mainQuery.toString());
						listaDocOk = new ArrayList<TopDocs>();
						mappaCompleta = new HashMap<String, EnhancedDocument>();
						// devo filtrare anche i fascicoli
						chiaviFascicolo = new ArrayList<String>();
						relazioniCiObjIdUd = new HashMap<String, List<String>>();
						if (parziale.totalHits != 0) {
							for (i = 0; i < parziale.totalHits; i++) {
								String ciObj = null;
								int numeroDocumento = parziale.scoreDocs[i].doc;
								float score = parziale.scoreDocs[i].score;
								EnhancedDocument edoc = enhanceDoc(numeroDocumento, score, is, bq, privacyFields);
								ciObj = getId(edoc.getDoc(), searchType);
								if (searchType == SearchArea.SEARCH_TYPE_REPOSITORY_DOC_FOLDER) {
									String idUd = edoc.getDoc().get(_FIELD_NAME_ID_UD);
									if (idUd != null) {
										if (relazioniCiObjIdUd.get(idUd) != null && !relazioniCiObjIdUd.get(idUd).contains(ciObj)) {
											relazioniCiObjIdUd.get(idUd).add(ciObj);
										} else {
											List<String> ciObjs = new ArrayList<String>();
											ciObjs.add(ciObj);
											relazioniCiObjIdUd.put(idUd, ciObjs);
										}
									} else {
										if (!chiaviFascicolo.contains(ciObj)) {
											chiaviFascicolo.add(ciObj);
										}
									}
								}
								// devo anche verificare la situazione dei dati sensibili
								// per cui se un parola è stata trovata senza dati sensibili
								// ed una invece con dati sensibili
								// è corretto che quel documento venga cmq visualizzato
								if (!mappaCompleta.keySet().contains(ciObj)) {
									mappaCompleta.put(ciObj, edoc);
								} else {
									if (edoc.isPrivacyDamaging()) {
										mappaCompleta.put(ciObj, edoc);
									}
								}
							}
							// eseguo le logiche per ottenere lo score massimo
							Float scoreMassimo = parziale.getMaxScore();
							if (scoreMassimo > maxScore)
								maxScore = scoreMassimo;

							listaDocOk.add(parziale);

						} else {
							break;
						}
					} else {
						// se non c'è nessun altro filtro eseguo la procedura normale
						TopDocs parziale = is.search(mainQuery, null, Integer.MAX_VALUE);
						aLogger.debug("Risultati parziali no filterObject: " + parziale.totalHits + " per la query: "
								+ mainQuery.toString());
						listaDocOk = new ArrayList<TopDocs>();
						mappaCompleta = new HashMap<String, EnhancedDocument>();
						// devo filtrare anche i fascicoli
						chiaviFascicolo = new ArrayList<String>();
						relazioniCiObjIdUd = new HashMap<String, List<String>>();
						if (parziale.totalHits != 0) {
							for (i = 0; i < parziale.totalHits; i++) {
								String ciObj = null;
								int numeroDocumento = parziale.scoreDocs[i].doc;
								float score = parziale.scoreDocs[i].score;
								EnhancedDocument edoc = enhanceDoc(numeroDocumento, score, is, bq, privacyFields);
								ciObj = getId(edoc.getDoc(), searchType);
								if (searchType == SearchArea.SEARCH_TYPE_REPOSITORY_DOC_FOLDER) {
									String idUd = edoc.getDoc().get(_FIELD_NAME_ID_UD);
									if (idUd != null) {
										if (relazioniCiObjIdUd.get(idUd) != null && !relazioniCiObjIdUd.get(idUd).contains(ciObj)) {
											relazioniCiObjIdUd.get(idUd).add(ciObj);
										} else {
											List<String> ciObjs = new ArrayList<String>();
											ciObjs.add(ciObj);
											relazioniCiObjIdUd.put(idUd, ciObjs);
										}
									} else {
										if (!chiaviFascicolo.contains(ciObj)) {
											chiaviFascicolo.add(ciObj);
										}
									}
								}
								// devo anche verificare la situazione dei dati sensibili
								// per cui se un parola è stata trovata senza dati sensibili
								// ed una invece con dati sensibili
								// è corretto che quel documento venga cmq visualizzato
								if (!mappaCompleta.keySet().contains(ciObj)) {
									mappaCompleta.put(ciObj, edoc);
								} else {
									if (edoc.isPrivacyDamaging()) {
										mappaCompleta.put(ciObj, edoc);
									}
								}
							}
							// eseguo le logiche per ottenere lo score massimo
							Float scoreMassimo = parziale.getMaxScore();
							if (scoreMassimo > maxScore)
								maxScore = scoreMassimo;

							listaDocOk.add(parziale);

						} else {
							break;
						}
					}
				}
				listaRef = new ArrayList<String>();
				for (int d = 0; d < listaDocOk.size(); d++) {
					for (i = 0; i < listaDocOk.get(d).totalHits; i++) {
						Document doc = is.doc(listaDocOk.get(d).scoreDocs[i].doc);
						if (searchType != SearchArea.SEARCH_TYPE_REPOSITORY_DOC_FOLDER) {
							listaRef.add(getId(doc, searchType));
						} else {
							if (doc.get(_FIELD_NAME_ID_UD) != null) {
								listaRef.add(doc.get(_FIELD_NAME_ID_UD));
							} else {
								listaRef.add(getId(doc, searchType));
							}
						}
					}
				}
				for (String key : listaRef) {
					if (searchType != SearchArea.SEARCH_TYPE_REPOSITORY_DOC_FOLDER) {
						if (!documents.keySet().contains(key))
							documents.put(key, mappaCompleta.get(key));
					} else {
						if (relazioniCiObjIdUd.get(key) != null) {
							for (String chiaveOggetto : relazioniCiObjIdUd.get(key)) {
								if (!documents.keySet().contains(chiaveOggetto))
									documents.put(chiaveOggetto, mappaCompleta.get(chiaveOggetto));
							}
						} else {
							if (!documents.keySet().contains(key))
								documents.put(key, mappaCompleta.get(key));
						}
					}
				}
				break;
				/*** FINE MODIFICA RAJ ***/
			}
			}
			// Faccio questa operazione solo se la ricerca non è per tutte le
			// parole
			// in cui le logiche sono diverse a causa delle intersezione dei
			// risultati (raj)
			if (typeSearch != SearchType.TYPE_SEARCH_ALL_TERMS) {
				// se esiste il queryfilter devo unire i risultati di più elaborazioni
				if (queryFilter != null) {
					currentSearching.add(queryFilter, BooleanClause.Occur.MUST);
					TopDocs td = is.search(currentSearching, null, Integer.MAX_VALUE);
					if (td.totalHits > 0) {
						if (td.getMaxScore() > maxScore) {
							maxScore = td.getMaxScore();
						}
					}
					for (i = 0; i < td.totalHits; i++) {
						int numeroDocumento = td.scoreDocs[i].doc;
						float score = td.scoreDocs[i].score;
						EnhancedDocument edoc = enhanceDoc(numeroDocumento, score, is, currentSearching, privacyFields);
						String ciObj = getId(edoc.getDoc(), searchType);
						if (!documents.keySet().contains(ciObj)) {
							documents.put(ciObj, edoc);
						}
					}
				} else {
					topdocs = is.search(currentSearching, null, Integer.MAX_VALUE);
					if (topdocs.totalHits > 0) {
						maxScore = topdocs.getMaxScore();
					}
					for (i = 0; i < topdocs.totalHits; i++) {
						int numeroDocumento = topdocs.scoreDocs[i].doc;
						float score = topdocs.scoreDocs[i].score;
						EnhancedDocument edoc = enhanceDoc(numeroDocumento, score, is, currentSearching, privacyFields);
						String ciObj = getId(edoc.getDoc(), searchType);
						if (!documents.keySet().contains(ciObj)) {
							documents.put(ciObj, edoc);
						}
					}
				}
			}
			currentSearching = new BooleanQuery();
			Long timePostRicerca = System.currentTimeMillis();
			aLogger.debug("tempo ricerca: " + (timePostRicerca - timePostSearcher));
			// Se viene passato anche un elenco (in formato LISTA_STD) di
			// oggetti su cui fare un ulteriore filtro,
			// e' necessario creare una ulteriore sotto-query creando degli OR
			// sugli attributi ci_obj i id_ud a seconda
			// che il tipo di oggetto sia un folder o sia una unita'
			// documentaria
			// C'e' la possibilita' di aggiungere delle ulteriori condizioni
			// L'HashMap customFilter e' fatta in questo modo:
			// - key: stringa con il nome dell'attributo oggetto della
			// condizione
			// - valore: array di stringhe con i valori con cui creare la OR
			// condition sul nome dell'attributo
			if (customFilter != null) {
				Set keyNames = customFilter.keySet();
				if (keyNames != null) {
					Iterator itNames = customFilter.keySet().iterator();
					String attName = "";
					String[] attValues = null;
					while ((itNames != null) && (itNames.hasNext())) {
						attName = (String) itNames.next();
						attValues = (String[]) customFilter.get(attName);
						i = 0;
						queryCustomFilter = new BooleanQuery();
						while ((attValues != null) && (i < attValues.length)) {
							termQueryFilter = new TermQuery(new Term(attName, attValues[i]));
							queryCustomFilter.add(termQueryFilter, BooleanClause.Occur.SHOULD);
							i++;
						}
						currentSearching.add(queryCustomFilter, BooleanClause.Occur.MUST);

					}
					topdocs = is.search(currentSearching, null, Integer.MAX_VALUE);
					for (i = 0; i < topdocs.totalHits; i++) {
						int numeroDocumento = topdocs.scoreDocs[i].doc;
						float score = topdocs.scoreDocs[i].score;
						EnhancedDocument edoc = enhanceDoc(numeroDocumento, score, is, currentSearching, privacyFields);
						String ciObj = getId(edoc.getDoc(), searchType);
						if (!documents.keySet().contains(ciObj)) {
							documents.remove(ciObj);
						}
					}
					currentSearching = new BooleanQuery();
				}

			}
			Long timePostFiltroCustom = System.currentTimeMillis();
			aLogger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Total hits: " + documents.keySet().size()
					+ " <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

			if (documents.keySet().size() == 0) {
				throw new LuceneNoDataFoundException("La ricerca non ha avuto alcun risultato");
			}
			// Costruzione lista d'uscita (con conteggio oggetti Folder+UD)
			int countForVerify = 0;
			hmUdObj = new HashMap();
			hmUtObj = new HashMap();
			hmProcObj = new HashMap();
			hmFolderObj = new HashMap();
			Lista lst = new Lista();
			String ciObj = "";
			String docIdUd = "";
			String docIdUt = "";

			// prima operazione: strip dei valori nulli da filterObjType
			// conto gli elementi..
			int numAttr = 0;
			for (int k = 0; k < filterObjType.length; k++)
				if (filterObjType[k] != null && !filterObjType[k].equals(""))
					numAttr++;
			// strippiamo i valori nulli da filterObjType
			String filterObjTypeUsed[] = new String[numAttr];
			int j = 0;
			for (int k = 0; k < filterObjType.length; k++) {
				if (filterObjType[k] != null && !filterObjType[k].equals("")) {
					filterObjTypeUsed[j] = filterObjType[j];
					j++;
				}
			}
			// DOPO TUTTI I FILTRI A MONTE CREO LA LISTA D'USCITA
			// A SECONDA DEL PARAMETRO parFilter DECIDO QUALI RISULTATI TENERE
			// E QUALI INVECE SCARTARE

			// ciclo sui risultati per scremare quelli che non sono voluti
			// e faccio il conteggio di quanto trovato
			for (EnhancedDocument tempDoc : documents.values()) {
				ciObj = getId(tempDoc.getDoc(), searchType);
				docIdUd = "";
				docIdUt = "";
				Riga rg = null;
				// se non ho ci_obj scarto a priori
				if (ciObj == null)
					continue;
				// modifica raj
				// Mi ricavo dal documento arricchito lo score da inserire
				// nella colonna 3
				String score = getNormalizedScore(tempDoc.getScore(), String.valueOf(maxScore));
				String privacy = "0";
				if (tempDoc.isPrivacyDamaging())
					privacy = "1";
				// se sto filtrando su tipi di oggetti quello che ho trovato non
				// matcha scarto
				if (filterObjTypeUsed != null) {
					if (!verifyTypeObjInFilterObjType(ciObj, filterObjTypeUsed))
						continue;
				}

				// ricerca nel repository doc
				// se e' un folder va sempre bene
				/**************************** ricerca sul repository doc **************************/
				switch (searchType) {
				case SEARCH_TYPE_REPOSITORY_DOC_FOLDER: {
					if (ciObj.startsWith(_ID_FOLDER_PREFIX)) {
						if (!hmFolderObj.containsKey(ciObj)) {
							hmFolderObj.put(ciObj, "1");
							countForVerify++;
						} else
							continue; // l'avevo già inserito nella lista,
										// scarto
					}
					// se e' un doc prendo l'id ud e guardo se gia l'avevo
					// conteggiata
					else if (ciObj.startsWith(_ID_DOC_PREFIX)) {
						docIdUd = tempDoc.getDoc().get(_FIELD_NAME_ID_UD);
						if (docIdUd == null || "".equals(docIdUd.trim())) {
							continue;
						}
						if (!hmUdObj.containsKey(docIdUd)) {
							hmUdObj.put(docIdUd, "1");
							countForVerify++;
						}
					} else
						continue;// non so cosa sia, scarto

					rg = new Riga();
					Colonna col1 = new Colonna();
					col1.setNro(BigInteger.valueOf(1));
					col1.setContent(ciObj);
					Colonna col2 = new Colonna();
					col2.setNro(BigInteger.valueOf(2));
					col2.setContent(docIdUd);
					// aggiungo le nuove colonne (raj)
					Colonna col3 = new Colonna();
					col3.setNro(BigInteger.valueOf(3));
					col3.setContent(privacy);
					Colonna col4 = new Colonna();
					col4.setNro(BigInteger.valueOf(4));
					col4.setContent(score);
					rg.getColonna().add(col1);
					rg.getColonna().add(col2);
					rg.getColonna().add(col3);
					rg.getColonna().add(col4);
					break;
				}
				/**************************** ricerca sulle UO **************************/
				case SEARCH_TYPE_UO: {
					// se c'è un ulteriore filtro verifichiamo se l'id Organigramma del
					// documento trovato è contenuto in quelli passati dal bean filtrante
					if (parFilter != null) {
						String idOrganigramma = tempDoc.getDoc().get(_FIELD_NAME_ID_ORGANIGRAMMA);
						if (!StringUtils.isBlank(idOrganigramma)) {
							List<String> idOrgs = new ArrayList<String>();
							// se il filtro contiene già l'id dell'organigramma ricavo direttamente i valori
							if (parFilter.getType() == FilterType.ID_ORGANIGRAMMA) {
								idOrgs = parFilter.getValues();
							} else {
								// se il filtro contiene l'idSpAOO richiamo la store per avere l'idOrganigramma
								for (String value : parFilter.getValues()) {
									DmpkDefSecurityGetidorganigrammaspaooBean bean = new DmpkDefSecurityGetidorganigrammaspaooBean();
									bean.setIdspaooin(new BigDecimal(value.trim()));
									GetidorganigrammaspaooImpl store = new GetidorganigrammaspaooImpl();
									store.setBean(bean);
									store.execute(conn);
									StoreResultBean<DmpkDefSecurityGetidorganigrammaspaooBean> result = new StoreResultBean<DmpkDefSecurityGetidorganigrammaspaooBean>();
									AnalyzeResult.analyze(bean, result);
									result.setResultBean(bean);
									if (result.isInError()) {
										aLogger.debug("Andata in errore ");
										aLogger.debug(result.getDefaultMessage());
										aLogger.debug(result.getErrorContext());
										aLogger.debug(result.getErrorCode());
										throw new LuceneException(result.getDefaultMessage() + result.getErrorCode());
									}
									String id = String.valueOf(result.getResultBean().getParametro_1());
									idOrgs.add(id);
								}
							}
							if (!idOrgs.contains(idOrganigramma))
								continue;
							else
								countForVerify++;
						} else
							countForVerify++;
					} else
						countForVerify++;
					rg = new Riga();
					Colonna col1 = new Colonna();
					col1.setNro(BigInteger.valueOf(1));
					col1.setContent(ciObj);
					// aggiungo le nuove colonne (raj)
					Colonna col3 = new Colonna();
					col3.setNro(BigInteger.valueOf(3));
					col3.setContent(privacy);
					Colonna col4 = new Colonna();
					col4.setNro(BigInteger.valueOf(4));
					col4.setContent(score);
					rg.getColonna().add(col1);
					rg.getColonna().add(col3);
					rg.getColonna().add(col4);
					break;
				}
				/**************************** ricerca sul TITOLARIO **************************/
				case SEARCH_TYPE_TITOLARIO: {
					if (ciObj.startsWith(_ID_CLASSIFICAZIONE_PREFIX)) {
						// se c'è un ulteriore filtro verifichiamo se l'id Piano Classificazione del
						// documento trovato è contenuto in quelli passati dal bean filtrante
						if (parFilter != null) {
							String idClassif = tempDoc.getDoc().get(_FIELD_NAME_ID_PIANO_CLASSIF);
							if (!StringUtils.isBlank(idClassif)) {
								List<String> pianiClassif = new ArrayList<String>();
								// se il filtro contiene già l'id del piano ricavo direttamente i valori
								if (parFilter.getType() == FilterType.ID_PIANO_CLASSIF) {
									pianiClassif = parFilter.getValues();
								} else {
									// se il filtro contiene l'idSpAOO richiamo la store per avere il piano di classificazione
									for (String value : parFilter.getValues()) {
										DmpkTitolarioGetidpianoclassifspaooBean bean = new DmpkTitolarioGetidpianoclassifspaooBean();
										bean.setIdspaooin(new BigDecimal(value.trim()));
										GetidpianoclassifspaooImpl store = new GetidpianoclassifspaooImpl();
										store.setBean(bean);
										store.execute(conn);
										StoreResultBean<DmpkTitolarioGetidpianoclassifspaooBean> result = new StoreResultBean<DmpkTitolarioGetidpianoclassifspaooBean>();
										AnalyzeResult.analyze(bean, result);
										result.setResultBean(bean);
										if (result.isInError()) {
											aLogger.debug("Andata in errore ");
											aLogger.debug(result.getDefaultMessage());
											aLogger.debug(result.getErrorContext());
											aLogger.debug(result.getErrorCode());
											throw new LuceneException(result.getDefaultMessage() + result.getErrorCode());
										}
										String id = String.valueOf(result.getResultBean().getParametro_1());
										pianiClassif.add(id);
									}
								}
								if (!pianiClassif.contains(idClassif))
									continue;
								else
									countForVerify++;
							} else
								countForVerify++;
						} else
							countForVerify++;
					} else
						continue; // non so cosa sia, scarto
					rg = new Riga();
					Colonna col1 = new Colonna();
					col1.setNro(BigInteger.valueOf(1));
					col1.setContent(ciObj);
					// aggiungo le nuove colonne (raj)
					Colonna col3 = new Colonna();
					col3.setNro(BigInteger.valueOf(3));
					col3.setContent(privacy);
					Colonna col4 = new Colonna();
					col4.setNro(BigInteger.valueOf(4));
					col4.setContent(score);
					rg.getColonna().add(col1);
					rg.getColonna().add(col3);
					rg.getColonna().add(col4);
					break;
				}
				/**************************** ricerca sui PROCESSI **************************/
				case SEARCH_TYPE_PROCESS: {
					// caso 1: ho trovato nei metadati del proc, ho ci_Obj =
					// P<nro>
					String listaProcessi = null;
					String idUd = null;
					if (ciObj.startsWith(_ID_PROC_PREFIX) || ciObj.startsWith(_ID_DOC_PREFIX) || ciObj.startsWith(_ID_FOLDER_PREFIX)) {
						// prendo la lista
						listaProcessi = tempDoc.getDoc().get(_FIELD_NAME_PROC_LIST);
						// la splitto nei vari valori
						if (listaProcessi != null) {
							String str[] = listaProcessi.split(";");
							for (int k = 0; k < str.length; k++) {
								String key = str[k].trim();
								if (!hmProcObj.containsKey(key)) {
									hmProcObj.put(key, "1");
									countForVerify++;
								}
							}
							if (ciObj.startsWith(_ID_DOC_PREFIX)) {
								idUd = tempDoc.getDoc().get(_FIELD_NAME_ID_UD);
							}
						} else
							continue;// scarto se non c'e' l'attributo
										// listaProcessi

					} else
						continue;// non so cosa sia, scarto

					rg = new Riga();
					Colonna col1 = new Colonna();
					col1.setNro(BigInteger.valueOf(1));
					col1.setContent(ciObj);
					rg.getColonna().add(col1);

					Colonna col2 = new Colonna();
					col2.setNro(BigInteger.valueOf(2));
					col2.setContent(listaProcessi);
					rg.getColonna().add(col2);

					if (idUd != null) {
						Colonna col3 = new Colonna();
						col3.setNro(BigInteger.valueOf(3));
						col3.setContent(idUd);
						rg.getColonna().add(col3);
					}
					break;
				}
				/**************************** ricerca sulla RUBRICA **************************/
				case SEARCH_TYPE_RUBRICA: {
					// se c'è un ulteriore filtro verifichiamo se l'idSpAOO del
					// documento trovato è contenuto in quelli passati dal bean filtrante
					aLogger.debug("Inserimento ulteriore filtro per filtrare su idSPAoo");
					if (parFilter != null) {
						String idSpAoo = tempDoc.getDoc().get(_FIELD_NAME_ID_SP_AOO);
						aLogger.debug("id documento: " + getId(tempDoc.getDoc(), searchType) + " con idSpaoo: " + idSpAoo);
						if (!StringUtils.isBlank(idSpAoo)) {
							List<String> ids = parFilter.getValues();
							if (idSpAoo != null && !ids.contains(idSpAoo)) {
								aLogger.debug("scarto il documento");
								continue;
							} else {
								aLogger.debug("inserisco il documento");
								countForVerify++;
							}
						} else {
							countForVerify++;
							aLogger.debug("inserisco il documento");
						}
					} else {
						countForVerify++;
						aLogger.debug("inserisco il documento");
					}
					rg = new Riga();
					Colonna col1 = new Colonna();
					col1.setNro(BigInteger.valueOf(1));
					col1.setContent(ciObj);
					// aggiungo le nuove colonne (raj)
					Colonna col3 = new Colonna();
					col3.setNro(BigInteger.valueOf(3));
					col3.setContent(privacy);
					Colonna col4 = new Colonna();
					col4.setNro(BigInteger.valueOf(4));
					col4.setContent(score);
					rg.getColonna().add(col1);
					rg.getColonna().add(col3);
					rg.getColonna().add(col4);
					break;
				}
				}
				if (rg != null)
					lst.getRiga().add(rg);
			}

			Long timePostCreazioneLista = System.currentTimeMillis();
			// caso in cui avevo trovato risultati, ma questi sono stati tutti
			// scartati
			if (countForVerify == 0) {
				throw new LuceneNoDataFoundException("La ricerca non ha avuto alcun risultato");
			}

			// Verifica del numero di oggetti Folder + UD
			if ((overflowLimit != -1) && (countForVerify > overflowLimit)) {
				throw new LuceneOverflowException("Superato il limite di oggetti consentiti (" + overflowLimit + ")");
			}

			// Costruisco l'xml conforsme a LISTA_STD
			Writer wr = null;
			wr = new StringWriter();

			SingletonJAXBContext.getInstance().createMarshaller().marshal(lst, wr);

			// sezione cache in stringa
			res = wr.toString();

			return res;
		} catch (LuceneException le) {
			throw le;
		} catch (TooManyClauses tmc) {
			throw new LuceneTooManyValuesException("I termini da ricercare (nel filtro \"Cerca\") sono troppo generici. Ricerca non consentita");
		} catch (Exception e) {
			aLogger.error("Errore generico in searchFullText", e);
			throw new LuceneException("Errore generico in searchFullText: " + e.getMessage());
		} finally {
			if (is != null)
				try {
					is.close();
				} catch (Exception ex) {
				}
			System.gc();
		}
	}

	/**
	 * ritorna il valore normalizzato dello score
	 * 
	 * @param score
	 * @param maxScore
	 * @return
	 */
	private static String getNormalizedScore(String score, String maxScore) {
		String normValue = "0";
		float normScore = new Float(score) / new Float(maxScore);
		if (normScore <= 0.2) {
			normValue = "1";
		} else {
			if (normScore > 0.2 && normScore <= 0.4) {
				normValue = "2";
			} else {
				if (normScore > 0.4 && normScore <= 0.6) {
					normValue = "3";
				} else {
					if (normScore > 0.6 && normScore <= 0.8) {
						normValue = "4";
					} else {
						if (normScore > 0.8 && normScore <= 1) {
							normValue = "5";
						}
					}
				}
			}
		}
		return normValue;
	}

	public static boolean verifyTypeObjInFilterObjType(String ciObj, String[] filterObjType) {
		boolean res = false;
		int i = 0;

		if (filterObjType.length == 0)
			return true;

		while ((i < filterObjType.length) && (!res)) {
			if (filterObjType[i] != null && !"".equals(filterObjType[i]) && ciObj.startsWith(filterObjType[i])) {
				res = true;
				break;
			}
			i++;
		}

		return res;
	}

	// Verifica l'esistenza in Lucene di un documento con un particolare ciObj
	public static boolean isDocumentInLucene(String path, String ciObj) throws LuceneException {
		boolean res = false;
		IndexSearcher is = null;

		try {
			is = getSearcher(path);
			Term t = new Term(_FIELD_NAME_CI_OBJ, ciObj);
			Query query = new TermQuery(t);

			/*
			 * IndexSearcher is = getSearcher(path); QueryParser qp=new
			 * QueryParser(_FIELD_NAME_CI_OBJ, new StandardAnalyzer()); Query
			 * query = qp.parse(ciObj); Hits hits = is.search(query);
			 */

			// DEPRECATED
			/*
			 * Hits hits = is.search(query); if (hits.length()>0) res = true;
			 */
			TopDocs topdocs = is.search(query, null, 1);
			if (topdocs.totalHits > 0)
				res = true;

		} catch (LuceneNoSegmentsFileFoundException lnsffe) {
			res = false;
		} catch (Exception e) {
			aLogger.error("Errore generico in isDocumentInLucene", e);
			throw new LuceneException("Errore in isDocumentInLucene: " + e.getMessage());
		} finally {
			if (is != null)
				try {
					is.close();
				} catch (Exception ex) {
				}
			System.gc();
		}

		return res;
	}

	public static void addLuceneDocument(String path, String ciObj, Properties attributes) throws LuceneException {
		IndexWriter writer = null;

		try {
			writer = getWriter(path);
			Document doc = new Document();

			// Prima di tutto aggiungo il ci_obj
			doc.add(new Field(_FIELD_NAME_CI_OBJ, ciObj, Field.Store.YES, Field.Index.NOT_ANALYZED));
			Enumeration enu = attributes.keys();
			String key = "";
			String value = "";
			Object valueObj = null;
			while (enu.hasMoreElements()) {
				key = (String) enu.nextElement();
				valueObj = attributes.get(key);
				if (valueObj instanceof String) {
					value = (String) valueObj;
					if (key.equals(_FIELD_NAME_ID_UD) || key.equals(_FIELD_NAME_ID_SP_AOO)) {
						doc.add(new Field(key, value, Field.Store.YES, Field.Index.NOT_ANALYZED));
					} else {
						doc.add(new Field(key, value, Field.Store.YES, Field.Index.ANALYZED));
					}
				} else if (valueObj instanceof Reader) {
					doc.add(new Field(key, (Reader) valueObj));
				}
			}

			writer.addDocument(doc);
		} catch (Exception ex) {
			aLogger.error("Errore generico in addLuceneDocument", ex);
			throw new LuceneException(ex.getMessage());
		} finally {
			closeWriter(writer);
		}
	}

	// Modifica un documento generico a lucene
	public static void modifyLuceneDocument(String path, String ciObj, Properties attributes) throws LuceneException {
		IndexWriter writer = null;
		Document doc = null;

		try {
			doc = getDocumentByCiObj(path, ciObj);

			if (doc != null) {
				// Cancellazione del documento
				deleteLuceneDocument(path, ciObj);
			} else {
				doc = new Document();
			}

			writer = getWriter(path);

			// Nuovo inserimento
			Enumeration enu = attributes.keys();
			String key = "";
			String value = "";
			Object valueObj = null;
			while (enu.hasMoreElements()) {
				// Valuto il nome del campo
				key = (String) enu.nextElement();
				// Valuto il valore del campo
				valueObj = attributes.get(key);
				// Rimuovo il campo se presente nel documento
				doc.removeField(key);
				// se il campo era da rimuovere sono aposto e vado oltre
				if (valueObj instanceof String && _DELETED_DOC.equals((String) valueObj))
					continue;

				// Aggiungo il campo al documento
				if (valueObj instanceof String) {
					value = (String) valueObj;
					if (key.equals(_FIELD_NAME_ID_UD) || key.equals(_FIELD_NAME_ID_SP_AOO)) {
						doc.add(new Field(key, value, Field.Store.YES, Field.Index.NOT_ANALYZED));
					} else {
						doc.add(new Field(key, value, Field.Store.YES, Field.Index.ANALYZED));
					}
				} else if (valueObj instanceof Reader) {
					doc.add(new Field(key, (Reader) valueObj));
				}
			}

			writer.addDocument(doc);
		} catch (LuceneException le) {
			throw le;
		} catch (Exception ex) {
			aLogger.error("Errore modifyLuceneDocument", ex);
			throw new LuceneException(ex.getMessage());
		} finally {
			closeWriter(writer);
		}
	}

	// Elimina un documento da lucene
	public static void deleteLuceneDocument(String path, String ciObj) throws LuceneException {
		IndexReader reader = null;
		// IndexSearcher seracher = null;
		try {

			// Cancellazione
			reader = getReader(path);
			int deleted = reader.deleteDocuments(new Term(_FIELD_NAME_CI_OBJ, ciObj));

		} catch (LuceneException le) {
			throw le;
		} catch (Exception ex) {
			aLogger.error("Errore deleteLuceneDocument", ex);
			throw new LuceneException(ex.getMessage());
		} finally {
			if (reader != null)
				try {
					reader.close();
				} catch (Exception ex) {
				}
			System.gc();
		}
	}

	/**
	 * restituisce per un determinato indexSearcher gli attributi indicizzati
	 * 
	 * @param path
	 * @return
	 * @throws LuceneException
	 */
	public static Collection<String> retrieveIndexedFields(String path) throws LuceneException {
		IndexSearcher is = getSearcher(path);
		Collection<String> indexedFields = is.getIndexReader().getFieldNames(IndexReader.FieldOption.INDEXED);
		for (String field : indexedFields)
			aLogger.debug("campo indicizzato: " + field);
		return indexedFields;
	}

	/**
	 * restituisce per un determinato indexSearcher gli attributi non indicizzati
	 * 
	 * @param path
	 * @return
	 * @throws LuceneException
	 */
	public static Collection<String> retrieveUnindexedFields(String path) throws LuceneException {
		IndexSearcher is = getSearcher(path);
		Collection<String> indexedFields = is.getIndexReader().getFieldNames(IndexReader.FieldOption.UNINDEXED);
		for (String field : indexedFields)
			aLogger.debug("campo non indicizzato: " + field);
		return indexedFields;
	}

	/**************************************************************************/
	/**************************** METODI PRIVATI ******************************/

	/**
	 * metodo per arricchire il documento di Lucene con i dati dello score e dei campi 'sensibili'
	 * 
	 * @throws IOException
	 * @throws CorruptIndexException
	 ************************************************************************/

	private static EnhancedDocument enhanceDoc(int numeroDocumento, float score, IndexSearcher is, BooleanQuery bq, String privacyFields)
			throws CorruptIndexException, IOException {
		Document doc = is.doc(numeroDocumento);
		// ricavo i valori dei campi coinvolti nella ricerca di Lucene
		// tramite un parsing dell'explain dell'indexer
		List<String> fieldNameInvolved = findFieldnames(is.explain(bq, numeroDocumento).toString());
		Boolean privacyActivated = true;
		// verifico se i campi coinvolti nella ricerca sono campi 'sensibili'
		// se tutti i campi in cui il termine è stato trovato sono sensibili allora il risultato è sensibile
		// altrimenti no
		for (String field : fieldNameInvolved) {
			if (!privacyFields.contains(field)) {
				privacyActivated = false;
				break;
			}
		}
		// in funzione dei precedenti valori arrichisco il documento con valori
		// relativi allo score
		// e relativi alla privacy dei campi stessi
		return new EnhancedDocument(doc, Float.toString(score), privacyActivated);
	}

	// Restituisce un particolare documento in Lucene a aprtire dal suo ciObj
	private static Document getDocumentByCiObj(String path, String ciObj) throws LuceneException {
		Document res = null;
		IndexSearcher is = null;

		try {
			is = getSearcher(path);
			Term t = new Term(_FIELD_NAME_CI_OBJ, ciObj);
			Query query = new TermQuery(t);

			TopDocs topdocs = is.search(query, null, 1);
			if (topdocs.totalHits > 0) {
				res = is.doc(topdocs.scoreDocs[0].doc);
			}
		} catch (LuceneNoSegmentsFileFoundException lnsffe) {
			res = null;
		} catch (Exception e) {
			aLogger.error("Errore in isDocumentInLucene", e);
			throw new LuceneException("Errore in isDocumentInLucene: " + e.getMessage());
		} finally {
			if (is != null)
				try {
					is.close();
				} catch (Exception ex) {
				}
			System.gc();
		}

		return res;
	}

	// Restituisce l'oggetto IndexWriter
	private static IndexWriter getWriter(String path) throws LuceneException {
		IndexWriter writer = null;
		Directory fsDir = null;
		IndexWriterConfig config = new IndexWriterConfig(_LUCENE_VERSION, new StandardAnalyzer(_LUCENE_VERSION));
		config.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
		try {
			writer = new IndexWriter(fsDir, config);

		} catch (Exception e) {
			aLogger.error("Errore getWriter", e);
			throw new LuceneException(e.getMessage());
		} finally {
			System.gc();
		}
		return writer;

	}

	// Restituisce l'oggetto IndexSearcher
	private static IndexSearcher getSearcher(String path) throws LuceneException {
		IndexSearcher searcher = null;
		try {
			searcher = new IndexSearcher(getReader(path));
		} catch (Exception e) {
			throw new LuceneException("Errore in getSearcher: " + e.getMessage());
		} finally {
			System.gc();
		}
		return searcher;
	}

	// Restituisce l'oggetto IndexReader
	private static IndexReader getReader(String path) throws Exception {
		IndexReader reader = null;
		if (path.contains(File.separator)) {
			File f = null;
			Directory fsDir = null;

			try {
				f = new File(path);
				if (f.exists() && f.isDirectory()) {
					fsDir = FSDirectory.open(f);
					reader = IndexReader.open(fsDir, true);
				}

			} catch (Exception e) {
				throw new LuceneException("Errore in getReader: " + e.getMessage());
			} finally {
				System.gc();
			}
		} else {
			DbConfig config = (DbConfig) SpringAppContext.getContext().getBean("dbConfig");
			if (config.getType().equalsIgnoreCase("ORACLE")) {
				OracleDataSource dataSource = new OracleDataSource();
				dataSource.setDriverType("thin");
				dataSource.setServerName(config.getServerName());
				dataSource.setPortNumber(Integer.parseInt(config.getPortNumber()));
				if(StringUtils.isNotBlank(config.getDatabaseName())) {
					dataSource.setDatabaseName(config.getDatabaseName()); // Oracle SID
				}
				if(StringUtils.isNotBlank(config.getServiceName())) {
					dataSource.setServiceName(config.getServiceName()); // ServiceName
				}
				dataSource.setUser(config.getUsername());
				dataSource.setPassword(config.getPassword());
				EnhancedJdbcDirectory directory = new EnhancedJdbcDirectory(dataSource, new OracleDialect(), path);
				reader = IndexReader.open(directory);
			} else {
				aLogger.error("Tipo db per lettura indici: " + config.getType() + " non gestito!!!");
				throw new LuceneException("Tipo db per lettura indici: " + config.getType() + " non gestito!!!");
			}
		}
		return reader;
	}

	private static List<String> findFieldnames(String in) {
		List<String> out = new ArrayList<String>();
		Scanner scanner = new Scanner(in);
		while (scanner.hasNext()) {
			String line = scanner.next();
			if (line.contains("ConstantScore")) {
				out.add(line.substring((line.indexOf("ConstantScore(")) + 14, line.indexOf(":")));
			} else if (line.contains("fieldNorm")) {
				out.add(line.substring((line.indexOf("fieldNorm(field=")) + 16, line.indexOf(",")));
			}
		}
		return out;
	}

	// close writer
	private static void closeWriter(IndexWriter writer) {
		if (writer != null)
			try {
				writer.close();
			} catch (Exception e) {
			}
		System.gc();
	}

	/**
	 * ritorna l'id a seconda dell'area di ricerca
	 * 
	 * @param doc
	 * @param area
	 * @return
	 */
	private static String getId(Document doc, SearchArea area) {
		if (area == SearchArea.SEARCH_TYPE_PROCESS) {
			return doc.get(_FIELD_NAME_CI_OBJ);
		} else {
			return doc.get(_ID_OBJECT);
		}
	}

	private static String getIdLabel(SearchArea area) {
		if (area == SearchArea.SEARCH_TYPE_PROCESS) {
			return _FIELD_NAME_CI_OBJ;
		} else {
			return _ID_OBJECT;
		}
	}

	/*******************************************************
	 * metodo main di test
	 * 
	 * @throws LuceneException
	 * @throws IOException
	 * @throws ParseException
	 */

	public static void main(String argv[]) throws LuceneException, IOException, ParseException {
		IndexWriter writer = null;
		// String path = "C:\\temp\\repository\\lucene\\path_test_2\\";
		String path = "\\\\172.27.1.139\\c$\\lucene\\index\\REP_DOC\\AURI_OWNER_1_2\\";
		IndexSearcher searcher = getSearcher(path);
		IndexReader reader = searcher.getIndexReader();
		List<String> fieldName = new ArrayList<String>();
		Collection<String> campi = retrieveIndexedFields(path);
		for (String ca : campi)
			fieldName.add(ca);
		List<String> terms = new ArrayList<String>();
		terms.add("FUNZIONAAA");
		for (String termine : terms) {
			int count = 0;
			for (String field : fieldName) {
				// count += reader.docFreq(new Term(field, termine.substring(0, termine.length() - 1)));
				count += reader.docFreq(new Term(field, termine.toLowerCase()));
			}
			aLogger.debug("frequenza: " + termine + " :" + count);
		}
		// Directory fsDir = null;
		// File f = new File(path);
		// if (f.exists() && f.isDirectory()) {
		// fsDir = FSDirectory.open(f);
		// // }
		// CheckIndex check = new CheckIndex(fsDir);
		// Status status = check.checkIndex();
		// check.fixIndex(status);
		// String term = "comune";
		// 21950, 1317109, 1310609, 1342727, 22651, 39897, 1150703, 1175132, 1232298, 1312744, 1294533, 1299356, 1159053, 1266021
		// List<String> chiavi = new ArrayList<String>();
		// String key1 = "rs5396";
		// String key2 = "rs5571";
		// chiavi.add(key1);
		// chiavi.add(key2);
		// BooleanQuery nq = new BooleanQuery();
		// nq.setMinimumNumberShouldMatch(1);
		// for (String key : chiavi) {
		// TermQuery termQuery = new TermQuery(new Term(_ID_OBJECT, key));
		// nq.add(termQuery, BooleanClause.Occur.SHOULD);
		// }
		// TopDocs parziale = searcher.search(nq, null, Integer.MAX_VALUE);
		// BooleanQuery currentSearching = new BooleanQuery();
		// BooleanQuery mainQuery = new BooleanQuery();
		// // currentSearching.setMinimumNumberShouldM;atch(1);
		// for (String key : chiavi) {
		// String[] fields = new String[1];
		// fields[0] = _ID_OBJECT;
		// MultiFieldQueryParser mfqp = new MultiFieldQueryParser(_LUCENE_VERSION, fields, new StandardAnalyzer(_LUCENE_VERSION));
		// Query queryFieldName = mfqp.parse(key);
		// currentSearching.add(queryFieldName, BooleanClause.Occur.SHOULD);
		// }
		// TopDocs parziale = searcher.search(currentSearching, null, Integer.MAX_VALUE);
		// aLogger.debug(parziale.totalHits);
		// String[] fields2 = new String[2];
		// fields2[0] = "DENOMINAZIONE_PRINCIPALE";
		// fields2[1] = "EMAIL";
		// BooleanQuery searchq = new BooleanQuery();
		// MultiFieldQueryParser mfqp2 = new MultiFieldQueryParser(_LUCENE_VERSION, fields2, new StandardAnalyzer(_LUCENE_VERSION));
		// Query queryFieldName = mfqp2.parse("bis");
		// searchq.add(queryFieldName, BooleanClause.Occur.MUST);
		// TopDocs parziale2 = searcher.search(searchq, null, Integer.MAX_VALUE);
		// aLogger.debug(parziale2.totalHits);
		// mainQuery.add(currentSearching, BooleanClause.Occur.MUST);
		// mainQuery.add(searchq, BooleanClause.Occur.MUST);
		// TopDocs parziale3 = searcher.search(mainQuery, null, Integer.MAX_VALUE);
		// aLogger.debug(parziale3.totalHits);
		// Collection<String> campi = retrieveIndexedFields(path);
		// for (String ca : campi)
		// aLogger.debug(ca);
		// TermDocs termDocs = reader.termDocs(new Term("DENOMINAZIONE_PRINCIPALE", "corte*"));
		// aLogger.debug(reader.docFreq(new Term("DENOMINAZIONE_PRINCIPALE", "protocollo")));
		// int count = 0;
		// while (termDocs.next()) {
		// count += termDocs.freq();
		// }
		// aLogger.debug(count);
	}

}
