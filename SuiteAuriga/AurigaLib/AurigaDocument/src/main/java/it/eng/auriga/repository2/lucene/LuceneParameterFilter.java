/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

/**
 * classe di filtro ulteriore per i risultati di Lucene
 * 
 * @author jravagnan
 * 
 */
public class LuceneParameterFilter {

	private List<String> values;

	private FilterType type;

	public List<String> getValues() {
		return values;
	}

	public void setValues(List<String> values) {
		this.values = values;
	}

	public FilterType getType() {
		return type;
	}

	public void setType(FilterType type) {
		this.type = type;
	}

}
