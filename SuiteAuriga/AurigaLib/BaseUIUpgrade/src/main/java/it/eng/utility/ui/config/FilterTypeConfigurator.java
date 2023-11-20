/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.shared.bean.FilterTypeBean;

import java.util.Map;

public class FilterTypeConfigurator {

	private Map<String,FilterTypeBean> types;

	public void setTypes(Map<String,FilterTypeBean> types) {
		this.types = types;
	}

	public Map<String,FilterTypeBean> getTypes() {
		return types;
	}		
	
		
}
