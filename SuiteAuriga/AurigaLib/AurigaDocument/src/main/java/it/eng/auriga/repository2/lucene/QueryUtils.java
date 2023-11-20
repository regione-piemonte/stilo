/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;

/**
 * classe di utilità che serve a splittare una query nel caso le clausole siano più delle consentite
 * 
 * @author jravagnan
 * 
 */

public class QueryUtils {

	private static int MAX_NUMBER = LuceneHelper._LUCENE_MAX_CLAUSE_COUNT;

	public List<BooleanQuery> splitQuery(BooleanQuery queryIn) {
		List<BooleanQuery> listaQuery = new ArrayList<BooleanQuery>();
		if (queryIn.getClauses().length > MAX_NUMBER) {
			Integer group = getgroup(queryIn);
			BooleanClause[] clauses = queryIn.getClauses();
			for (int i = 0; i < group; i++) {
				BooleanQuery newQuery = new BooleanQuery();
				List<BooleanClause> lista = split(clauses, i);
				for (BooleanClause clausola : lista) {
					newQuery.add(clausola);
				}
				listaQuery.add(newQuery);
			}
		} else {
			listaQuery.add(queryIn);
		}
		return listaQuery;
	}

	private Integer getgroup(BooleanQuery queryIn) {
		int clausesNumber = queryIn.getClauses().length;
		int group = clausesNumber / MAX_NUMBER;
		if (clausesNumber % MAX_NUMBER != 0) {
			group++;
		}
		return group;
	}

	private List<BooleanClause> split(BooleanClause[] clauses, int group) {
		return Arrays.asList(ArrayUtils.subarray(clauses, MAX_NUMBER * group, (MAX_NUMBER * (group + 1))));
	}

}
