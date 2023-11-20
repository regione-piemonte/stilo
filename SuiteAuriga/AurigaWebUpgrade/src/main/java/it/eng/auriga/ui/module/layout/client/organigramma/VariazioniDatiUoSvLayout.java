/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;

public class VariazioniDatiUoSvLayout extends CustomLayout {
	
	protected VariazioniDatiUoSvWindow window;
	protected String idUoSv;
	protected String flgUoSv;
	
	public VariazioniDatiUoSvLayout(VariazioniDatiUoSvWindow window, String idUoSv, String flgUoSv) {
		
		super("variazioni_dati_organigramma", 
				getVariazioniDatiUoSvDataSource(idUoSv, flgUoSv),
				null,
				new VariazioniDatiUoSvList("variazioni_dati_organigramma", flgUoSv) ,
				new CustomDetail("variazioni_dati_organigramma")
		);
		
		this.window = window;
		this.idUoSv = idUoSv;
		this.flgUoSv = flgUoSv;

		multiselectButton.hide();	
		newButton.hide();		
		refreshListButton.hide();
		topListToolStripSeparator.hide();
		
		this.setLookup(false);
	}	
	
	public static GWTRestDataSource getVariazioniDatiUoSvDataSource(String idUoSv, String flgUoSv) {
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("VariazioniDatiUoSvDataSource");
		lGwtRestDataSource.addParam("idUoSv", idUoSv);			
		lGwtRestDataSource.addParam("flgUoSv", flgUoSv);
		return lGwtRestDataSource;
	}
			
	@Override
	public boolean getDefaultDetailAuto() {
		return false;
	}
		
	@Override
	public String getViewDetailTitle() {			
		Record record = new Record(detail.getValuesManager().getValues());		
		if(flgUoSv != null && "SV".equals(flgUoSv)) {			
			String estremiUOApp = ""; //TODO devo mettere gli estremi della UO di appartenenza (codRapidoUo - descrUoSvUt)
			if(estremiUOApp != null && !"".equals(estremiUOApp)) {
				return "Variazioni della postazione " + record.getAttributeAsString("intestazione") + " nella UO " + estremiUOApp;
			} else {
				return "Variazioni della postazione " + record.getAttributeAsString("intestazione");
			}			
		} else {			
			String estremiUO = null;
			if (record.getAttributeAsString("livelloCorrente") != null && !"".equals(record.getAttributeAsString("livelloCorrente"))) {
				estremiUO = record.getAttributeAsString("livelloCorrente") + "." + record.getAttributeAsString("livello") + " - "
						+ record.getAttributeAsString("denominazioneEstesa");
			} else {
				estremiUO = record.getAttributeAsString("livello") + " - " + record.getAttributeAsString("denominazioneEstesa");
			}
			return "Variazioni della UO " + estremiUO;	
		}					
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
	
	public VariazioniDatiUoSvWindow getWindow() {
		return window;
	}
	
}