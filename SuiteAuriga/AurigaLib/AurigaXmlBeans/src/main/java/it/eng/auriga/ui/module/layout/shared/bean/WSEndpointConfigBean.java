/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.shared.bean;

import java.util.HashMap;

public class WSEndpointConfigBean {

	private HashMap<String, String> endpoints;

	public HashMap<String, String> getEndpoints() {
		return endpoints;
	}

	public void setEndpoints(HashMap<String, String> endpoints) {
		this.endpoints = endpoints;
	}
	
}
