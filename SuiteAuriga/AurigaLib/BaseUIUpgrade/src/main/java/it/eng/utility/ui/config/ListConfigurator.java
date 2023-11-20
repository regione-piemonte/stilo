/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


import it.eng.utility.ui.module.layout.shared.bean.ListaBean;

import java.util.Map;

public class ListConfigurator {
	
	private Map<String,ListaBean> liste;
	private boolean disableRecordComponent;
	private boolean disableSetFields;
	private boolean reloadDisabled;
	
	public Map<String,ListaBean> getListe() {
		return liste;
	}

	public void setListe(Map<String,ListaBean> liste) {
		this.liste = liste;
	}

	public boolean isDisableRecordComponent() {
		return disableRecordComponent;
	}

	public void setDisableRecordComponent(boolean disableRecordComponent) {
		this.disableRecordComponent = disableRecordComponent;
	}

	public boolean isDisableSetFields() {
		return disableSetFields;
	}

	public void setDisableSetFields(boolean disableSetFields) {
		this.disableSetFields = disableSetFields;
	}

	public boolean isReloadDisabled() {
		return reloadDisabled;
	}

	public void setReloadDisabled(boolean reloadDisabled) {
		this.reloadDisabled = reloadDisabled;
	}	
	
}

