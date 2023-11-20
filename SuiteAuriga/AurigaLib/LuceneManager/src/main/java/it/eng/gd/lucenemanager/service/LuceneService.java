/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.Query;

import it.eng.gd.lucenemanager.LuceneIndexReader;
import it.eng.gd.lucenemanager.LuceneIndexWriter;
import it.eng.gd.lucenemanager.bean.DocumentLuceneBean;
import it.eng.gd.lucenemanager.bean.SearchCategory;
import it.eng.gd.lucenemanager.exception.LuceneManagerException;

/**
 * servizio per la ricerca full text
 * 
 * @author jravagnan
 * 
 */
public class LuceneService {

	private static final String JOLLY_CHAR = "*";

	private Logger log = Logger.getLogger(LuceneService.class);

	private LuceneIndexReader reader;

	private LuceneIndexWriter writer;

	private SearchCategory category;

	private String idDominio;

	private String dbName;

	/**
	 * metodo di ricerca full text
	 * 
	 * @param searchType
	 * @param fieldName
	 * @param searchValue
	 * @param overflowLimit
	 * @param filter
	 * @return
	 * @throws LuceneManagerException
	 */
	public List<DocumentLuceneBean> searchFullText(SEARCH_TYPE searchType, String[] fieldName, String searchValue, BigDecimal overflowLimit, Filter filter)
			throws LuceneManagerException {
		log.debug("searchFullText con tipo ricerca " + searchType.toString() + " istanziato");
		List<DocumentLuceneBean> result = null;
		try {

			if (searchValue != null) {
				String searchValueNew = "";
				StringTokenizer st = new StringTokenizer(searchValue);
				while (st.hasMoreTokens()) {
					searchValueNew += st.nextToken() + JOLLY_CHAR + " ";
				}
				searchValue = searchValueNew.trim();
			}

			MultiFieldQueryParser mfqp = null;
			String nToken = null;
			BooleanQuery currentSearching = new BooleanQuery();
			List<DocumentLuceneBean> total = new ArrayList<DocumentLuceneBean>();

			switch (searchType) {
			case SEARCH_AT_LEAST_ONE_TERM:
				// Costruzione della query sui fieldName passati
				mfqp = new MultiFieldQueryParser(LuceneIndexReader._LUCENE_VERSION, fieldName, new StandardAnalyzer(LuceneIndexReader._LUCENE_VERSION));
				mfqp.setDefaultOperator(MultiFieldQueryParser.OR_OPERATOR);
				// Creazione effettiva della query
				Query queryFieldName = mfqp.parse(searchValue);
				currentSearching.add(queryFieldName, BooleanClause.Occur.MUST);
				total = reader.searchDocuments(currentSearching, filter);
				// RIPULISCO LA LISTA DELLE CHIAVI DOPPIE
				Set<DocumentLuceneBean> mySet = new HashSet<DocumentLuceneBean>(total);
				result = new ArrayList<DocumentLuceneBean>(mySet);
				break;
			case SEARCH_ALL_TERMS:
				StringTokenizer st = new StringTokenizer(searchValue);
				List<BooleanQuery> searching = new ArrayList<BooleanQuery>();
				while (st.hasMoreTokens()) {
					nToken = st.nextToken();
					mfqp = new MultiFieldQueryParser(LuceneIndexReader._LUCENE_VERSION, fieldName, new StandardAnalyzer(LuceneIndexReader._LUCENE_VERSION));
					queryFieldName = mfqp.parse(nToken);
					BooleanQuery tokenSearching = new BooleanQuery();
					tokenSearching.add(queryFieldName, BooleanClause.Occur.MUST);
					searching.add(tokenSearching);
				}
				// IL RISULTATO CORRETTO E' QUELLO CHE CERCA TUTTE LE PAROLE ALL'INTERNO DI TUTTI I CAMPI
				// cerco di ciclare sulle singole condizioni e successivamente di fare l'intersezione degli insiemi
				Iterator<BooleanQuery> bqIter = searching.iterator();
				List<List<DocumentLuceneBean>> listaDocOk = new ArrayList<List<DocumentLuceneBean>>();
				Map<String, DocumentLuceneBean> mappaCompleta = new HashMap<String, DocumentLuceneBean>();
				List<String> listaRef = null;
				// ciascun token inserito puo' avere piu' risultati su vari campi
				// li raccolgo tutti e inserisco i documenti comprensivi di chiave
				// all'interno della mappa completa
				while (bqIter.hasNext()) {
					BooleanQuery bq = bqIter.next();
					List<DocumentLuceneBean> parziale = reader.searchDocuments(bq, filter);
					for (DocumentLuceneBean document : parziale) {
						String id = document.getObjectid();
						if (!mappaCompleta.keySet().contains(id)) {
							mappaCompleta.put(id, document);
						}
					}
					// aggiorno anche la lista dei documenti che rispondono alle
					// parole di ricerca inserite
					listaDocOk.add(parziale);
				}
				listaRef = new ArrayList<String>();
				List<String> toCompare = new ArrayList<String>();
				// // mi creo una lista con degli id di riferimento
				// // su cui verificare le intersezioni
				for (int d = 0; d < listaDocOk.size(); d++) {
					if (d == 0) {
						for (int i = 0; i < listaDocOk.get(d).size(); i++) {
							listaRef.add(listaDocOk.get(d).get(i).getObjectid());
						}
					} else {
						for (int i = 0; i < listaDocOk.get(d).size(); i++) {
							toCompare.add(listaDocOk.get(d).get(i).getObjectid());
						}

						listaRef = ListUtilities.intersect(listaRef, toCompare);
					}
				}
				// inserisco nei documenti quelli presenti nella mappa completa
				// che hanno verificato le condizioni di intersezione

				for (String key : listaRef) {
					total.add(mappaCompleta.get(key));
				}
				// RIPULISCO LA LISTA DELLE CHIAVI DOPPIE
				Set<DocumentLuceneBean> newSet = new HashSet<DocumentLuceneBean>(total);
				result = new ArrayList<DocumentLuceneBean>(newSet);
				break;

			}
		} catch (Exception e) {
			log.error("Impossibile effettuare la ricerca", e);
			throw new LuceneManagerException("Impossibile effettuare la ricerca", e);
		}
		// GESTISCO l'OVERFLOW
		if (result != null) {
			if (result.size() > overflowLimit.intValue()) {
				log.error("Numero di risultati eccessivi, aumentare la soglia o effettuare ricerca pi� stringente");
				throw new LuceneManagerException("Numero di risultati eccessivi, aumentare la soglia o effettuare ricerca pi� stringente");
			}
		}
		return result;
	}

	/**
	 * indicizza un documento partendo dal semplice file il mimetype se lo ricava tramite webservice di fileoperarion e considera il filename come quello del
	 * file passato
	 * 
	 * @param fileToAdd
	 * @param id
	 * @param indexmetadata
	 * @param unindexmetadata
	 * @throws Exception
	 */
	public void indexDocument(File fileToAdd, String id, Map<String, Object> indexmetadata, Map<String, Object> unindexmetadata, String uri, boolean ocr)
			throws Exception {
		writer.addDocument(fileToAdd, id, indexmetadata, unindexmetadata, uri, ocr);
	}

	/**
	 * indicizza un documento partendo dal semplice file il mimetype se lo ricava tramite webservice di fileoperarion e considera il filename come quello del
	 * file passato, prevede un writer già presente
	 * 
	 * @param fileToAdd
	 * @param id
	 * @param indexmetadata
	 * @param unindexmetadata
	 * @throws Exception
	 */
	public void indexDocument(File fileToAdd, String id, Map<String, Object> indexmetadata, Map<String, Object> unindexmetadata, String uri, boolean ocr,
			IndexWriter iwriter) throws Exception {
		writer.addDocument(fileToAdd, id, indexmetadata, unindexmetadata, uri, ocr, iwriter);
	}

	/**
	 * indicizza un documento partendo dal semplice file il mimetype se lo ricava tramite webservice di fileoperarion e considera il filename come quello del
	 * file passato
	 * 
	 * @param fileToAdd
	 * @param id
	 * @param indexmetadata
	 * @param unindexmetadata
	 * @throws Exception
	 */
	public void indexDocument(File fileToAdd, String id, Map<String, Object> indexmetadata, Map<String, Object> unindexmetadata, String uri) throws Exception {
		writer.addDocument(fileToAdd, id, indexmetadata, unindexmetadata, uri);
	}

	/**
	 * aggiorna un documento già indicizzato
	 * 
	 * @param fileToUpdate
	 * @param id
	 * @param indexmetadata
	 * @param unindexmetadata
	 * @throws Exception
	 */
	public void updateIndexedDocument(String id, Map<String, Object> indexmetadata, Map<String, Object> unindexmetadata) throws Exception {
		writer.updateDocument(id, indexmetadata, unindexmetadata);
	}

	/**
	 * aggiorna un documento già indicizzato
	 * 
	 * @param fileToUpdate
	 * @param id
	 * @param indexmetadata
	 * @param unindexmetadata
	 * @throws Exception
	 */
	public void updateIndexedDocument(File fileToUpdate, String id, Map<String, Object> indexmetadata, Map<String, Object> unindexmetadata, boolean OCR)
			throws Exception {
		writer.updateDocument(fileToUpdate, id, indexmetadata, unindexmetadata, OCR, null);
	}

	/**
	 * aggiorna un documento già indicizzato
	 * 
	 * @param fileToUpdate
	 * @param id
	 * @param indexmetadata
	 * @param unindexmetadata
	 * @throws Exception
	 */
	public void updateIndexedDocument(File fileToUpdate, String id, Map<String, Object> indexmetadata, Map<String, Object> unindexmetadata, boolean OCR,
			IndexWriter iwriter) throws Exception {
		writer.updateDocument(fileToUpdate, id, indexmetadata, unindexmetadata, OCR, iwriter);
	}

	/**
	 * restituisce un writer per la gestione manuale dell'indice
	 * 
	 * @return
	 * @throws Exception
	 */
	public IndexWriter getIndexWriter() throws Exception {
		return writer.getWriter();
	}

	/**
	 * cancella un documento indicizzato
	 * 
	 * @param objectId
	 * @throws Exception
	 */
	public void deleteDocument(String objectId) throws Exception {
		writer.delete(objectId, null);
	}

	/**
	 * cancella un documento indicizzato
	 * 
	 * @param objectId
	 * @throws Exception
	 */
	public void deleteDocument(String objectId, IndexWriter iwriter) throws Exception {
		writer.delete(objectId, iwriter);
	}

	/**
	 * verifica se un documento è indicizzato
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public boolean isDocumentIndexed(String id) throws Exception {
		return reader.isDocumentIndexed(id);
	}

	/**
	 * restituisce gli attributi indicizzati
	 * 
	 * @return
	 * @throws Exception
	 */
	public Collection<String> retrieveIndexedAttributes() throws Exception {
		return reader.retrieveIndexAttribute();
	}

	public enum SEARCH_TYPE {
		SEARCH_ALL_TERMS, SEARCH_AT_LEAST_ONE_TERM
	}

	public LuceneIndexReader getReader() {
		return reader;
	}

	public void setReader(LuceneIndexReader reader) {
		this.reader = reader;
	}

	public LuceneIndexWriter getWriter() {
		return writer;
	}

	public void setWriter(LuceneIndexWriter writer) {
		this.writer = writer;
	}

	public SearchCategory getCategory() {
		return category;
	}

	public void setCategory(SearchCategory category) {
		this.category = category;
		reader.setCategory(category);
		writer.setCategory(category);
	}

	public String getIdDominio() {
		return idDominio;
	}

	public void setIdDominio(String idDominio) {
		this.idDominio = idDominio;
		reader.setIdDominio(idDominio);
		writer.setIdDominio(idDominio);
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
		reader.setDbName(dbName);
		writer.setDbName(dbName);
	}

}
