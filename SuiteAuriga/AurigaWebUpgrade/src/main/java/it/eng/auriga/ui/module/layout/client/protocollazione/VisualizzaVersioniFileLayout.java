/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;

import com.smartgwt.client.data.Record;

public class VisualizzaVersioniFileLayout extends CustomLayout {

	public VisualizzaVersioniFileLayout(GWTRestDataSource pGWTRestDataSource, Record recordProtocollo) {
	      this(pGWTRestDataSource, recordProtocollo, true);
	};
	
	
	public VisualizzaVersioniFileLayout(GWTRestDataSource pGWTRestDataSource, Record recordProtocollo, boolean abilAttestatoConformitaButton) {
		super("visualizza_versioni_file", 
				pGWTRestDataSource, 
				null, 
				new VisualizzaVersioniFileList("visualizza_versioni_file", recordProtocollo, abilAttestatoConformitaButton), 
				new CustomDetail("visualizza_versioni_file")
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
