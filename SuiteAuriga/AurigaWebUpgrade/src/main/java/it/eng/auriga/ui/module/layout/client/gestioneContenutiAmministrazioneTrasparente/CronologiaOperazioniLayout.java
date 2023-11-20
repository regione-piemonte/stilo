/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;

/**
 * 
 * @author dbe4235
 *
 */

public class CronologiaOperazioniLayout extends CustomLayout {	
	
	public CronologiaOperazioniLayout(GWTRestDataSource pGWTRestDataSource) {
		super("cronologia_operazioni", 
				pGWTRestDataSource,
				null,
				new CronologiaOperazioniList("cronologia_operazioni"),
				new CustomDetail("cronologia_operazioni")
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