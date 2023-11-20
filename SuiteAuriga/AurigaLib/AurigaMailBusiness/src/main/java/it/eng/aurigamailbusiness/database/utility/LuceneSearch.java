/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.aurigamailbusiness.database.utility.bean.lucene.LuceneBeanResult;
import it.eng.aurigamailbusiness.database.utility.bean.search.LuceneSearchOption;
import it.eng.gd.lucenemanager.bean.DocumentLuceneBean;
import it.eng.gd.lucenemanager.bean.SearchCategory;
import it.eng.gd.lucenemanager.exception.LuceneManagerException;
import it.eng.gd.lucenemanager.service.LuceneService;
import it.eng.gd.lucenemanager.service.LuceneService.SEARCH_TYPE;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class LuceneSearch {

	private static final String DEFAULT_MAX_NUM_RECORD_SEARCH_RESULT = "1000";

	private LuceneService service;

	private static final String SPACE = " ";

	/**
	 * Esegue la ricerca su Lucene
	 * 
	 * @param terms
	 *            termini di ricerca
	 * @param folders
	 *            folder di ricerca
	 * @param flag
	 *            flag che indica se una o più parole
	 * @param attributi
	 *            attributi indicizzati
	 * @param numMax
	 *            numero massimo dei risultati
	 * @return
	 * @throws LuceneManagerException
	 */
	public List<LuceneBeanResult> search(List<String> terms, List<String> folders, LuceneSearchOption flag, List<String> attributi,
			BigDecimal numMax) throws LuceneManagerException {
		List<LuceneBeanResult> ret = new ArrayList<LuceneBeanResult>();
		// imposto la categoria sulla quale andare a fare la ricerca
		service.setCategory(SearchCategory.EMAIL);
		SEARCH_TYPE st = null;
		switch (flag) {
		case JUST_ONE_WORD:
			st = SEARCH_TYPE.SEARCH_AT_LEAST_ONE_TERM;
			break;
		case ALL_WORD:
			st = SEARCH_TYPE.SEARCH_ALL_TERMS;
			break;
		}
		String[] fieldName = attributi.toArray(new String[attributi.size()]);
		String searchValue = "";
		for (String term : terms) {
			searchValue = searchValue + term + SPACE;
		}
		if (numMax == null) {
			numMax = new BigDecimal(DEFAULT_MAX_NUM_RECORD_SEARCH_RESULT);
		}
		List<DocumentLuceneBean> listaRis = service.searchFullText(st, fieldName, searchValue, numMax, null);
		// valuto se effettuare o meno un filtro sulle caselle di interesse
		// TODO: gestire le caselle in modo da matchare solo
		// i risultati relativi alle caselle
		// for (DocumentLuceneBean doc : listaRis) {
		// if (folders != null && folders.size() != 0 && doc.getMetadata().get(LuceneConstants.ID_CASELLA) != null) {
		// if (folders.contains(doc.getMetadata().get(LuceneConstants.ID_CASELLA))) {
		// ret.add(createLuceneBeanResult(doc));
		// }
		// } else {
		// ret.add(createLuceneBeanResult(doc));
		// }
		// }
		for (DocumentLuceneBean doc : listaRis) {
			ret.add(createLuceneBeanResult(doc));
		}
		return ret;
	}
	
	public LuceneBeanResult createLuceneBeanResult(DocumentLuceneBean doc) {
		LuceneBeanResult luceneBeanResult = new LuceneBeanResult();
		luceneBeanResult.setIdEmail(doc.getObjectid());
		luceneBeanResult.setScore(doc.getScore());		
		return luceneBeanResult;
	}

	public LuceneService getService() {
		return service;
	}

	public void setService(LuceneService service) {
		this.service = service;
	}

}
