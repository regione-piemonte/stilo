/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;

public class AttiAutorizzazioneAnnRegLayout extends CustomLayout {
	
	public AttiAutorizzazioneAnnRegLayout() {
		this(null);
	}

	public AttiAutorizzazioneAnnRegLayout(GWTRestDataSource pGWTRestDataSource) {
		
		super("atti_autorizzazione_ann_reg", 
				pGWTRestDataSource != null ? pGWTRestDataSource : new GWTRestDataSource("AttiAutorizzazioneAnnRegDatasource", true, "idAtto", FieldType.TEXT),
				new ConfigurableFilter("atti_autorizzazione_ann_reg"),
				new AttiAutorizzazioneAnnRegList("atti_autorizzazione_ann_reg"),
				new AttiAutorizzazioneAnnRegDetail("atti_autorizzazione_ann_reg")
		);
		
		multiselectButton.hide();
		refreshListButton.hide();
		topListToolStripSeparator.hide();		
	}	
	
	@Override
	public boolean isForcedToAutoSearch() {
		return true;
	}
	
	public static boolean isRecordAbilToMod(boolean isAttoChiuso) {
		return !isAttoChiuso; 
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
		altreOpButton.hide();
	}

	@Override
	public void editMode(boolean fromViewMode) {
		super.editMode(fromViewMode);
		altreOpButton.hide();		
	}



	@Override
	protected GWTRestDataSource createNroRecordDatasource() {		
		GWTRestDataSource dataSource = (GWTRestDataSource) getList().getDataSource();
		dataSource.setForceToShowPrompt(false);
		return dataSource;
	}

	@Override
	protected Record[] extractRecords(String[] fields) {
		// Se sono in overflow i dati verranno recuperati con il metodo asincrono,
		// altrimenti utilizzo quelli nella lista a GUI
		if (overflow){
			return new Record[0];
		}else{
			return super.extractRecords(fields);
		}
	}
}