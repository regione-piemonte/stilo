/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;

public class OperazioniEffettuateLayout extends CustomLayout {	
	
	public OperazioniEffettuateLayout(GWTRestDataSource pGWTRestDataSource) {
		super("operazioni_effettuate", 
				pGWTRestDataSource,
				null,
				new OperazioniEffettuateList("operazioni_effettuate") ,
				new CustomDetail("operazioni_effettuate")
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
