/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.client.monitoraggioRegistrazioniMultipleUscita;

import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.form.fields.SelectItem;

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;

public class MonitoraggioRegistrazioniMultipleUscitaLayout extends CustomLayout{
	
	public MonitoraggioRegistrazioniMultipleUscitaLayout() {
		this(null, null, null);
	}

	public MonitoraggioRegistrazioniMultipleUscitaLayout(String finalita, Boolean flgSelezioneSingola, Boolean showOnlyDetail) {
		
		super("monitoraggio_registrazioni_multiple_uscita",
				new GWTRestDataSource("MonitoraggioRegistrazioniMultipleUscitaDataSource", "idRegistrazioniMultipleUscita", FieldType.TEXT), 
				new MonitoraggioRegistrazioniMultipleUscitaFilter("monitoraggio_registrazioni_multiple_uscita") {
					
					@Override
					protected SelectItem createSelectField() {	
						SelectItem selectField = super.createSelectField();
						selectField.setWidth(300);
						return selectField;
					}
				},
				new MonitoraggioRegistrazioniMultipleUscitaList("monitoraggioRegistrazioniMultipleUscita"),
				new CustomDetail("monitoraggioRegistrazioniMultipleUscita"), 
				finalita, flgSelezioneSingola, showOnlyDetail);

		multiselectButton.hide();

		newButton.hide();		
	}
	
}
