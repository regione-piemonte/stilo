/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;

import com.ibm.icu.util.StringTokenizer;

@Entity
public class LuceneQueryCreate {

	private static final Log logger = LogFactory.getLog(LuceneQueryCreate.class.getName());

	/**
	 * Crea una query dalla hashmap dei valori
	 * 
	 * @param mapquery
	 * @return
	 * @throws ParseException
	 */

	public static Query createQuery(Map<String, String> mapquery, Occur occur) throws ParseException {
		Iterator<String> keys = mapquery.keySet().iterator();
		List<Query> querys = new ArrayList<Query>();// contenitore delle query per ogni attributo
		while (keys.hasNext()) {
			String key = keys.next();
			// scompongo la frase (stringa passata) in tutte le parole da cercare
			List<String> terms = new ArrayList<String>();
			StringTokenizer tokenizer = new StringTokenizer(mapquery.get(key), " ");
			while (tokenizer.hasMoreElements()) {
				String parola = tokenizer.nextToken();
				terms.add(parola);
			}

			BooleanQuery bq = new BooleanQuery();
			// ciclo su tutti i termini da ricercare
			for (String term : terms) {
				// se si vuole cercare in modo che tutti i termini passati (le parole nella frase)
				// devo essere presenti occur deve valere MUST altrimenti SHOULD
				if (StringUtils.endsWith(term, "*")) {
					PrefixQuery query = new PrefixQuery(new Term(key, StringUtils.removeEnd(term.toLowerCase(), "*")));
					bq.add(query, occur);
					querys.add(bq);
				} else {
					TermQuery query = new TermQuery(new Term(key, term.toLowerCase()));
					bq.add(query, occur);
					querys.add(bq);
				}
			}
		}

		if (querys.size() == 1) {
			logger.debug("single query:" + querys.get(0));
			return querys.get(0);
		} else {
			BooleanQuery booleanquery = new BooleanQuery();
			// ogni attributo indicizzato viene aggiunto in or
			for (Query term : querys) {
				booleanquery.add(term, Occur.SHOULD);
			}
			logger.debug("query:" + booleanquery);
			return booleanquery;
		}
	}

	/**
	 * Crea la query per recuperare tutti i documenti
	 * 
	 * @return
	 * @throws ParseException
	 */
	public static Query createQueryAllDoc() throws ParseException {
		return new MatchAllDocsQuery();
	}

}
