/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.utility.ui.module.layout.client.common;

import com.google.gwt.event.shared.EventHandler;

public interface ChangeValueMapHandler extends EventHandler {
	
	void onChangeValueMap(ChangeValueMapEvent event);
	
}
