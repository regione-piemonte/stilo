/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import it.eng.utility.ui.module.layout.shared.bean.FilterBean;

public class FilterConfigurator {

	private String attributiDataSource;
	private Map<String, FilterBean> liste;
	private Boolean isOrdinableFilter;

	public String getAttributiDataSource() {
		return attributiDataSource;
	}

	public void setAttributiDataSource(String attributiDataSource) {
		this.attributiDataSource = attributiDataSource;
	}

	public void setListe(Map<String, FilterBean> liste) {
		this.liste = liste;
	}

	public Map<String, FilterBean> getListe() {
		return liste;
	}

	public Boolean getIsOrdinableFilter() {
		return isOrdinableFilter;
	}

	public void setIsOrdinableFilter(Boolean isOrdinableFilter) {
		this.isOrdinableFilter = isOrdinableFilter;
	}

}
