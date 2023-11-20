/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.google.gwt.event.shared.EventHandler;

public interface ChangeCanEditHandler extends EventHandler {
	
	void onChangeCanEdit(ChangeCanEditEvent event);
	
}
