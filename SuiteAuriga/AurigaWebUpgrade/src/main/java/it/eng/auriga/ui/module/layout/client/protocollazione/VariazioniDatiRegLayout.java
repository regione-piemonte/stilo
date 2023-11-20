/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;

public class VariazioniDatiRegLayout extends CustomLayout {	
	
	private String tsAnnDati;		

	public VariazioniDatiRegLayout(GWTRestDataSource pGWTRestDataSource) {
		super("variazioni_dati_reg", 
				pGWTRestDataSource,
				null,
				new VariazioniDatiRegList("variazioni_dati_reg") ,
				new CustomDetail("variazioni_dati_reg")
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
	public String getViewDetailTitle() {		
		return "Dati e file della registrazione prima della variazione del " + tsAnnDati;		
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
	
	public String getTsAnnDati() {
		return tsAnnDati;
	}

	public void setTsAnnDati(String tsAnnDati) {
		this.tsAnnDati = tsAnnDati;
	}
	
}
