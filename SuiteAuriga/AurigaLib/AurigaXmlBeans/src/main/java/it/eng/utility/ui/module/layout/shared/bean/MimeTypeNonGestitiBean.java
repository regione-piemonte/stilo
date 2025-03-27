/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.utility.ui.module.layout.shared.bean;

import java.util.List;
import java.util.Map;

public class MimeTypeNonGestitiBean {
	
	public Map<String, List<String>> mimeTypeMap;

	public Map<String, List<String>> getMimeTypeMap() {
		return mimeTypeMap;
	}

	public void setMimeTypeMap(Map<String, List<String>> mimeTypeMap) {
		this.mimeTypeMap = mimeTypeMap;
	}
	
}
