/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.utility.ui.module.layout.shared.bean;

import java.util.List;
import java.util.Map;

public class AppletParameterConfigurator {

	private Map<String, List<AppletParameterBean>> appletConfiguration;

	public void setAppletConfiguration(Map<String, List<AppletParameterBean>> appletConfiguration) {
		this.appletConfiguration = appletConfiguration;
	}

	public Map<String, List<AppletParameterBean>> getAppletConfiguration() {
		return appletConfiguration;
	}
}
