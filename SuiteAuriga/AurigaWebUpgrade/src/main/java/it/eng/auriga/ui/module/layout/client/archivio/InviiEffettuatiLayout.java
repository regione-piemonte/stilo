/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;

public class InviiEffettuatiLayout extends CustomLayout {

	public InviiEffettuatiLayout(GWTRestDataSource pGWTRestDataSource, Record record) {
		super("invii_effettuati", 
				pGWTRestDataSource,
				new ConfigurableFilter("invii_effettuati"),
				new InviiEffettuatiList("invii_effettuati",record),
				new CustomDetail("invii_effettuati")
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
	
	@Override
	public void newMode() {
		super.newMode();
		altreOpButton.hide();
	}

	@Override
	public void viewMode() {
		super.viewMode();		
		deleteButton.hide();
		editButton.hide();
		altreOpButton.hide();		
	}

	@Override
	public void editMode(boolean fromViewMode) {
		super.editMode(fromViewMode);
		altreOpButton.hide();		
	}

}