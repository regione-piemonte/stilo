/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.client.pratiche.dettaglio;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGridField;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.items.FilteredSelectItemWithDisplay;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;

public class CollegaComeIstanzaConcorrenteForm extends DynamicForm {

	private CollegaComeIstanzaConcorrentePopup window;
	private DynamicForm instance;

	private SelectItem idUdDaCollegareItem;
	
	public CollegaComeIstanzaConcorrenteForm(final String idUd, final CollegaComeIstanzaConcorrentePopup pWindow, final ServiceCallback<Record> callback) {

		instance = this;

		window = pWindow;
		setCanFocus(AurigaLayout.getIsAttivaAccessibilita());

		setKeepInParentRect(true);
		setWidth100();
		setHeight100();
		setNumCols(2);
		setColWidths(200, 200);
		setCellPadding(5);
		setAlign(Alignment.CENTER);
		setTop(50);

		SelectGWTRestDataSource idUdDaCollegareDS = new SelectGWTRestDataSource("LoadComboIstanzeConcSUAADSPXConcorrDataSource", "idUd", FieldType.TEXT, new String[] { "protocolloIstanza", "codPratica" }, true);
		idUdDaCollegareDS.addParam("idUdDaCollegare", idUd);

		idUdDaCollegareItem = new FilteredSelectItemWithDisplay("idUdDaCollegare", idUdDaCollegareDS);
		idUdDaCollegareItem.setAutoFetchData(false);
		idUdDaCollegareItem.setFetchMissingValues(true);
		idUdDaCollegareItem.setTitle("Seleziona");
		idUdDaCollegareItem.setWidth(450);
		idUdDaCollegareItem.setPickListWidth(450);
		idUdDaCollegareItem.setColSpan(2);
		idUdDaCollegareItem.setAlign(Alignment.CENTER);
		idUdDaCollegareItem.setValueField("idUd");
		idUdDaCollegareItem.setRequired(true);
		ListGridField idUdField = new ListGridField("idUd");
		idUdField.setHidden(true);
		ListGridField protocolloIstanzaField = new ListGridField("protocolloIstanza", "N° protocollo");
		ListGridField codPraticaField = new ListGridField("codPratica", "Cod. pratica SUA");
		ListGridField nroPubblicazioneField = new ListGridField("nroPubblicazione", "N° pubblicazione");
		idUdDaCollegareItem.setPickListFields(idUdField, protocolloIstanzaField, codPraticaField, nroPubblicazioneField);
		
		ButtonItem okButton = new ButtonItem("okButton", "Collega");
		okButton.setColSpan(2);
		okButton.setWidth(100);
		okButton.setTop(20);
		okButton.setAlign(Alignment.CENTER);
		okButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if(validate()) {
					manageOnClick(callback);
				}
			}
		});

		setFields(idUdDaCollegareItem, okButton);
	}

	protected void manageOnClick(final ServiceCallback<Record> callback) {
		if (callback != null) {
			callback.execute(getValuesAsRecord());
		}
		window.markForDestroy();		
	}

}
