/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import it.eng.utility.ui.module.layout.shared.bean.FilterSpecBean;

public class FilterSpecConfigurator {

	private Map<String, FilterSpecBean> liste;

	public Map<String, FilterSpecBean> getListe() {
		return liste;
	}

	public void setListe(Map<String, FilterSpecBean> liste) {
		this.liste = liste;
	}

}