/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Fieldable;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexReader.FieldOption;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.Version;

import it.eng.gd.lucenemanager.bean.DocumentLuceneBean;
import it.eng.gd.lucenemanager.bean.SearchCategory;
import it.eng.gd.lucenemanager.config.LuceneIndexConfig;
import it.eng.gd.lucenemanager.util.DirectoryManager;

/**
 * Classe che implementa i metodi di lettura degli indici di lucene
 * 
 * @author jravagnan
 * 
 */
@Entity
public class LuceneIndexReader {

	public static final Version _LUCENE_VERSION = Version.LUCENE_35;

	public static final Logger log = Logger.getLogger(LuceneIndexReader.class);

	// categoria a cui fa riferimento l'indice
	private SearchCategory category;

	private String idDominio;

	private String dbName;

	private DirectoryManager dirManager;

	/**
	 * Effettua una ricerca fulltext data la query passata in ingresso
	 * 
	 * @param querystr
	 * @return
	 * @throws Exception
	 */
	public List<DocumentLuceneBean> searchDocuments(Query query, Filter filter) throws Exception {
		LuceneIndexConfig config = (LuceneIndexConfig) LuceneSpringAppContext.getContext().getBean(LuceneIndexConfig.CONFIG_NAME);
		ArrayList<DocumentLuceneBean> listDocumentLuceneBean = new ArrayList<DocumentLuceneBean>();
		Directory directory = null;
		IndexReader ireader = null;
		IndexSearcher searcher = null;
		try {
			directory = dirManager.getDirectory(getCategory(), getIdDominio(), getDbName());
			ireader = IndexReader.open(directory);
			searcher = new IndexSearcher(ireader);
			TopDocs top = null;
			if (filter != null) {
				top = searcher.search(query, filter, config.getMaxdocuments());
			} else {
				top = searcher.search(query, config.getMaxdocuments());
			}
			for (ScoreDoc doc : top.scoreDocs) {
				DocumentLuceneBean lucenebean = new DocumentLuceneBean(searcher.doc(doc.doc).getFieldable(LuceneIndexWriter.ID_OBJECT).stringValue(),
						doc.score);
				List<Fieldable> fields = searcher.doc(doc.doc).getFields();
				Map<String, String> mappa = new HashMap<String, String>();
				for (Fieldable field : fields) {
					mappa.put(field.name(), field.stringValue());
				}
				lucenebean.setMetadata(mappa);
				listDocumentLuceneBean.add(lucenebean);
			}

		} finally {
			if (searcher != null) {
				try {
					searcher.close();
				} catch (Exception e) {
					log.warn("Eccezione chiusura searcher");
				}
			}
			if (ireader != null) {
				try {
					ireader.close();
				} catch (Exception e) {
					log.warn("Eccezione chiusura ireader");
				}
			}
			if (directory != null) {
				try {
					directory.close();
				} catch (Exception e) {
					log.warn("Eccezione chiusura directory");
				}
			}
		}

		return listDocumentLuceneBean;
	}

	/**
	 * Metodo che verifica se esiste o meno l'indice con id in input
	 * 
	 * @param idObject
	 * @return
	 * @throws Exception
	 */

	public Boolean isDocumentIndexed(String idObject) throws Exception {
		Boolean result = false;
		Directory directory = null;
		IndexReader ireader = null;
		IndexSearcher searcher = null;
		try {
			directory = dirManager.getDirectory(getCategory(), getIdDominio(), getDbName());
			if (!IndexReader.indexExists(directory)) {
				return false;
			}
			BooleanQuery currentSearching = new BooleanQuery();
			Analyzer analyzer = new StandardAnalyzer(_LUCENE_VERSION);
			QueryParser parser = new QueryParser(_LUCENE_VERSION, LuceneIndexWriter.ID_OBJECT, analyzer);
			Query queryFieldName = parser.parse(idObject);
			currentSearching.add(queryFieldName, BooleanClause.Occur.MUST);
			ireader = IndexReader.open(directory);
			searcher = new IndexSearcher(ireader);
			TopDocs topdocs = searcher.search(currentSearching, 1);
			if (topdocs.totalHits > 0) {
				result = true;
			}
		} finally {
			if (searcher != null) {
				try {
					searcher.close();
				} catch (Exception e) {
					log.warn("Eccezione chiusura searcher");
				}
			}
			if (ireader != null) {
				try {
					ireader.close();
				} catch (Exception e) {
					log.warn("Eccezione chiusura ireader");
				}
			}
			if (directory != null) {
				try {
					directory.close();
				} catch (Exception e) {
					log.warn("Eccezione chiusura directory");
				}
			}
		}
		return result;
	}

	public Collection<String> retrieveIndexAttribute() throws Exception {

		Directory directory = null;
		IndexReader ireader = null;
		try {
			directory = dirManager.getDirectory(getCategory(), getIdDominio(), getDbName());
			ireader = IndexReader.open(directory);
			Collection<String> indexed = ireader.getFieldNames(FieldOption.INDEXED);
			return indexed;
		} finally {
			if (ireader != null) {
				try {
					ireader.close();
				} catch (Exception e) {
					log.warn("Eccezione chiusura ireader");
				}
			}
			if (directory != null) {
				try {
					directory.close();
				} catch (Exception e) {
					log.warn("Eccezione chiusura directory");
				}
			}
		}
	}

	public Query getQueryByObjId(String objid) throws Exception {
		BooleanQuery currentSearching = new BooleanQuery();
		QueryParser parser = new QueryParser(_LUCENE_VERSION, LuceneIndexWriter.ID_OBJECT, new StandardAnalyzer(_LUCENE_VERSION));
		Query queryFieldName = parser.parse(objid);
		currentSearching.add(queryFieldName, BooleanClause.Occur.MUST);
		return currentSearching;
	}

	/**
	 * Recupero il document dall'objid
	 * 
	 * @return
	 */
	public Map<String, Object> getFieldValuesByObjId(String objid) throws Exception {
		Map<String, Object> fields = null;
		Directory directory = null;
		IndexReader ireader = null;
		IndexSearcher searcher = null;
		try {
			directory = dirManager.getDirectory(getCategory(), getIdDominio(), getDbName());
			ireader = IndexReader.open(directory);
			searcher = new IndexSearcher(ireader);
			BooleanQuery currentSearching = new BooleanQuery();
			QueryParser parser = new QueryParser(_LUCENE_VERSION, LuceneIndexWriter.ID_OBJECT, new StandardAnalyzer(_LUCENE_VERSION));
			Query queryFieldName = parser.parse(objid);
			currentSearching.add(queryFieldName, BooleanClause.Occur.MUST);
			TopDocs topdocs = searcher.search(currentSearching, 100);
			if (topdocs.totalHits > 0) {
				fields = new HashMap<String, Object>();
				for (ScoreDoc sc : topdocs.scoreDocs) {
					Document doc = ireader.document(sc.doc);
					List<Fieldable> campi = doc.getFields();
					for (Fieldable fi : campi) {
						fields.put(fi.name(), fi.stringValue());
					}
				}
			}
		} finally {
			if (searcher != null) {
				try {
					searcher.close();
				} catch (Exception e) {
					log.warn("Eccezione chiusura searcher");
				}
			}
			if (ireader != null) {
				try {
					ireader.close();
				} catch (Exception e) {
					log.warn("Eccezione chiusura ireader");
				}
			}
			if (directory != null) {
				try {
					directory.close();
				} catch (Exception e) {
					log.warn("Eccezione chiusura directory");
				}
			}
		}

		return fields;
	}

	public SearchCategory getCategory() {
		return category;
	}

	public void setCategory(SearchCategory category) {
		this.category = category;
	}

	public String getIdDominio() {
		return idDominio;
	}

	public void setIdDominio(String idDominio) {
		this.idDominio = idDominio;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public DirectoryManager getDirManager() {
		return dirManager;
	}

	public void setDirManager(DirectoryManager dirManager) {
		this.dirManager = dirManager;
	}

}