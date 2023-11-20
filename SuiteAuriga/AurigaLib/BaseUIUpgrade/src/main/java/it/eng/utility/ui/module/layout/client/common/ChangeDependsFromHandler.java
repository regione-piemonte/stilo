/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.google.gwt.event.shared.EventHandler;

public interface ChangeDependsFromHandler extends EventHandler {
	
	void onChangeDependsFrom(ChangeDependsFromEvent event);
	
}
