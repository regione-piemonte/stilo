/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;

public class OsservazioniNotificheLayout extends CustomLayout {	
	
	public OsservazioniNotificheLayout(GWTRestDataSource pGWTRestDataSource) {
		super("osservazioni__notifiche", 
				pGWTRestDataSource,
				null,
				new OsservazioniNotificheList("osservazioni__notifiche"),
				new CustomDetail("osservazioni__notifiche")
		);

		multiselectButton.hide();	
		newButton.hide();		
		refreshListButton.hide();
		topListToolStripSeparator.hide();
		
		this.setLookup(false);
	}
	
	@Override
	public boolean getDefaultDetailAuto() {
		return false;
	}
	
}