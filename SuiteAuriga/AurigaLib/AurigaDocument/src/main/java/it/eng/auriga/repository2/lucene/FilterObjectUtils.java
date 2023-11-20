/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.xml.bind.JAXBException;

import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.jaxb.variabili.Lista.Riga.Colonna;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.BooleanQuery.TooManyClauses;
import org.apache.lucene.search.Query;

public class FilterObjectUtils {

	private static Logger aLogger = Logger.getLogger(FilterObjectUtils.class);

	public static BooleanQuery getQuery(SearchArea searchType, String filterObjects) throws ParseException, LuceneOverflowException, JAXBException {
		BooleanQuery bq = null;
		// QueryUtils utils = new QueryUtils();
		if ((filterObjects != null) && (!filterObjects.equals(""))) {
			bq = new BooleanQuery();
			aLogger.debug("faccio unmarshal del filtro custom");
			// istanzio lo stringReader e vi associo la LISTA_STD
			java.io.StringReader sr = new java.io.StringReader(filterObjects);
			// Creao un oggetto di tipo lista
			
			Lista lsFilter = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
			
			aLogger.debug("filtro custom: " + lsFilter);
			// attenzione al filtro: qui il comportamento deve cambiare a
			// seconda della entita (ricerca rep, uo, tit o proc)
			if (lsFilter != null) {
				int clauses = 0;
				switch (searchType) {
				case SEARCH_TYPE_REPOSITORY_DOC_FOLDER: {
					// Leggo tutte le righe della lista che ha 2 colonne:
					// 1. Indica la tipologia dell'oggetto
					// 2. Identificativo dell'oggetto
					// Se la prima colonna (nro = 1) ha valore _DB_TIPO_UD, allora "alimento" una sotto-query
					// sull'attributo id_ud in cui metto in OR tutti i valori che troverà nella varie righe che avranno la prima colonna
					// uguale a U.
					// Se la prima colonna non ha valore _DB_TIPO_UD, allora "alimento" una sotto-query sull'attributo ci_obj in cui metto
					// in OR tutti i valori che trovera' nelle varie righe
					String tipoObj = "";
					String idObj = "";
					aLogger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> SEARCH TYPE : REPDOC <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
					for (int j = 0; j < lsFilter.getRiga().size(); j++) {
						// prendo la riga i-esima
						Riga r = lsFilter.getRiga().get(j);
						tipoObj = getContentColonnaNro(r, 1);
						idObj = getContentColonnaNro(r, 2);
						aLogger.debug("Oggetto " + (j + 1) + " -> tipo: " + tipoObj + "; id: " + idObj);
						// Se l'oggetto e' di tipo UNITA' DOCUMENTARIA non
						// cerco su CI_OBJ
						// ma cerco sul'attributo _FIELD_NAME_ID_UD
						if (tipoObj.equals(LuceneHelper._DB_TIPO_UD)) {
							String[] fields = new String[1];
							fields[0] = LuceneHelper._FIELD_NAME_ID_UD;
							MultiFieldQueryParser multiqueryIdUd = new MultiFieldQueryParser(LuceneHelper._LUCENE_VERSION, fields,
									new StandardAnalyzer(LuceneHelper._LUCENE_VERSION));
							Query qfn = multiqueryIdUd.parse(idObj);
							bq.add(qfn, BooleanClause.Occur.SHOULD);
						} else {
							String[] fields = new String[1];
							fields[0] = LuceneHelper._ID_OBJECT;
							MultiFieldQueryParser multiqueryIdObj = new MultiFieldQueryParser(LuceneHelper._LUCENE_VERSION, fields,
									new StandardAnalyzer(LuceneHelper._LUCENE_VERSION));
							Query qfn = multiqueryIdObj.parse(LuceneHelper._DB_TIPO_FOLDER + idObj);
							bq.add(qfn, BooleanClause.Occur.SHOULD);
						}
						// stampo la query finale sul log
						if (clauses == lsFilter.getRiga().size()) {
							aLogger.debug("Query finale con " + (++clauses) + "condizioni: " + bq.toString());
						}
						if (clauses == LuceneHelper._LUCENE_MAX_CLAUSE_COUNT) {
							throw new TooManyClauses();
						}
					}
					break;
				}
				case SEARCH_TYPE_UO: {
					// Leggo tutte le righe della lista:
					// - colonna nro 1 indica la tipologia dell'oggetto (UO, UT, SV)
					// - colonna nro 7 indica l'id dell'oggetto
					// Cerco su lucene tra gli oggetti che hanno ciObj concatenazione delle 2 colonne (tipo e id)
					aLogger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> SEARCH TYPE : UO <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
					bq = ricavaQueryUO(lsFilter);
					break;
				}
				case SEARCH_TYPE_TITOLARIO: {
					// Leggo tutte le righe della lista:
					// - colonna nro 2 indica l'id dell'oggetto
					// Cerco su lucene tra gli oggetti che hanno ciObj pari a CL+id
					aLogger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> SEARCH TYPE : TIT <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
					bq = ricavaQueryTitolario(lsFilter);
					break;
				}
				case SEARCH_TYPE_PROCESS: {
					// Leggo tutte le righe della lista:
					// - colonna nro 1 indica l'id del procedimento
					// per ognuna aggiungo una clausola per cercare P+id e una per cercare gli oggetti che hanno l'attributo LISTA_... che
					// CONTIENE id
					aLogger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> SEARCH TYPE : PROC <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
					bq = ricavaQueryProcess(lsFilter);
					break;
				}
				case SEARCH_TYPE_RUBRICA: {
					// Leggo tutte le righe della lista:
					// - colonna nro 1 indica l'id dell'oggetto ()
					// Cerco su lucene tra gli oggetti che hanno ciObj pari a RS+id
					aLogger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> SEARCH TYPE : RUBR <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
					bq = ricavaQueryRubrica(lsFilter);
					break;
				}
				}
			}
		}
		return bq;
	}

	private static BooleanQuery ricavaQueryProcess(Lista lsFilter) throws ParseException {
		BooleanQuery bq = new BooleanQuery();
		int clauses = 0;
		String idObj;
		for (int j = 0; j < lsFilter.getRiga().size(); j++) {
			// prendo la riga i-esima
			Riga r = lsFilter.getRiga().get(j);
			idObj = getContentColonnaNro(r, 1);
			aLogger.debug("Oggetto " + (j + 1) + " -> id: " + idObj);
			String[] fields = new String[1];
			fields[0] = LuceneHelper._ID_OBJECT;
			MultiFieldQueryParser multiquery = new MultiFieldQueryParser(LuceneHelper._LUCENE_VERSION, fields, new StandardAnalyzer(
					LuceneHelper._LUCENE_VERSION));
			Query qfn = multiquery.parse(LuceneHelper._ID_PROC_PREFIX + idObj);
			bq.add(qfn, BooleanClause.Occur.SHOULD);
			aLogger.debug("Query " + (++clauses) + ": " + bq.toString());
		}
		return bq;
	}

	private static BooleanQuery ricavaQueryTitolario(Lista lsFilter) throws ParseException {
		BooleanQuery bq = new BooleanQuery();
		int clauses = 0;
		String idObj;
		for (int j = 0; j < lsFilter.getRiga().size(); j++) {
			// prendo la riga i-esima
			Riga r = lsFilter.getRiga().get(j);
			idObj = getContentColonnaNro(r, 2);
			aLogger.debug("Oggetto " + (j + 1) + " -> id: " + idObj);
			String[] fields = new String[1];
			fields[0] = LuceneHelper._ID_OBJECT;
			MultiFieldQueryParser multiquery = new MultiFieldQueryParser(LuceneHelper._LUCENE_VERSION, fields, new StandardAnalyzer(
					LuceneHelper._LUCENE_VERSION));
			Query qfn = multiquery.parse(LuceneHelper._ID_CLASSIFICAZIONE_PREFIX + idObj);
			bq.add(qfn, BooleanClause.Occur.SHOULD);
			aLogger.debug("Query " + (++clauses) + ": " + bq.toString());
		}
		return bq;
	}

	private static BooleanQuery ricavaQueryRubrica(Lista lsFilter) throws ParseException {
		BooleanQuery bq = new BooleanQuery();
		int clauses = 0;
		String idObj;
		for (int j = 0; j < lsFilter.getRiga().size(); j++) {
			// prendo la riga i-esima
			Riga r = lsFilter.getRiga().get(j);
			idObj = getContentColonnaNro(r, 1);
			aLogger.debug("Oggetto " + (j + 1) + " -> id: " + idObj);
			String[] fields = new String[1];
			fields[0] = LuceneHelper._ID_OBJECT;
			MultiFieldQueryParser multiquery = new MultiFieldQueryParser(LuceneHelper._LUCENE_VERSION, fields, new StandardAnalyzer(
					LuceneHelper._LUCENE_VERSION));
			Query qfn = multiquery.parse(LuceneHelper._ID_RS_PREFIX + idObj);
			bq.add(qfn, BooleanClause.Occur.SHOULD);
			aLogger.debug("Query " + (++clauses) + ": " + bq.toString());
		}
		return bq;
	}

	/**
	 * metodo che ricava la query da effettuare
	 * 
	 * @param lsFilter
	 * @return
	 * @throws ParseException
	 */
	private static BooleanQuery ricavaQueryUO(Lista lsFilter) throws ParseException {
		BooleanQuery bq = new BooleanQuery();
		int clauses = 0;
		String tipoObj;
		String idObj;
		for (int j = 0; j < lsFilter.getRiga().size(); j++) {
			// prendo la riga i-esima
			Riga r = lsFilter.getRiga().get(j);
			tipoObj = getContentColonnaNro(r, 1);
			idObj = getContentColonnaNro(r, 7);
			aLogger.debug("Oggetto " + (j + 1) + " -> tipo: " + tipoObj + "; id: " + idObj);
			String[] fields = new String[1];
			fields[0] = LuceneHelper._ID_OBJECT;
			MultiFieldQueryParser multiquery = new MultiFieldQueryParser(LuceneHelper._LUCENE_VERSION, fields, new StandardAnalyzer(
					LuceneHelper._LUCENE_VERSION));
			Query qfn = multiquery.parse(tipoObj + idObj);
			bq.add(qfn, BooleanClause.Occur.SHOULD);

			aLogger.debug("Query " + (++clauses) + ": " + bq.toString());
		}
		return bq;
	}
	
	public static String getContentColonnaNro(Riga r, int nro) {
		
		if (r == null)
			return null;
		
		for (int i = 0; i < r.getColonna().size(); i++) {
			Colonna c = r.getColonna().get(i);
			if (c!=null && c.getNro().intValue() == nro) {
				return c.getContent();
			}
		}
		
		return null;
	}	

}
