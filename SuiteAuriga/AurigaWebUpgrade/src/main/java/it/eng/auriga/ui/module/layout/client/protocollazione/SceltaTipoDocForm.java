/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.DataArrivedEvent;
import com.smartgwt.client.widgets.form.fields.events.DataArrivedHandler;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;

public class SceltaTipoDocForm extends DynamicForm {

	private SceltaTipoDocPopup window;
	private DynamicForm instance;

	private SelectItem idTipoDocumento;
	private HiddenItem descTipoDocumento;
	private HiddenItem codCategoriaAltraNumerazione;
	private HiddenItem siglaAltraNumerazione;
	private HiddenItem flgTipoDocConVie;
	private HiddenItem flgOggettoNonObblig;
	private HiddenItem flgTipoProtModulo;
	
	public SceltaTipoDocForm(final boolean required, final String idTipoDocDefault, String descType, String categoriaReg, String siglaReg, 
			String finalita, final SceltaTipoDocPopup pWindow, final ServiceCallback<Record> callback) {

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

		GWTRestDataSource idTipoDocumentoDS = new GWTRestDataSource("LoadComboTipoDocumentoDataSource", "idTipoDocumento", FieldType.TEXT, true);
		idTipoDocumentoDS.addParam("categoriaReg", categoriaReg);
		idTipoDocumentoDS.addParam("siglaReg", siglaReg);
		idTipoDocumentoDS.addParam("isAttivaAccessibilita", ""+AurigaLayout.getIsAttivaAccessibilita());
		idTipoDocumentoDS.addParam("finalita", finalita);
		if(pWindow.isCompilazioneModulo()) {
			idTipoDocumentoDS.addParam("isCompilazioneModulo", "true");
		}

		idTipoDocumento = new SelectItem("idTipoDocumento") {

			@Override
			public void onOptionClick(Record record) {
				idTipoDocumento.setValue(record.getAttribute("idTipoDocumento"));
				descTipoDocumento.setValue(record.getAttribute("descTipoDocumento"));
				codCategoriaAltraNumerazione.setValue(record.getAttribute("codCategoriaAltraNumerazione"));
				siglaAltraNumerazione.setValue(record.getAttribute("siglaAltraNumerazione"));
				flgTipoDocConVie.setValue(record.getAttributeAsBoolean("flgTipoDocConVie"));
				flgOggettoNonObblig.setValue(record.getAttributeAsBoolean("flgOggettoNonObblig"));
				flgTipoProtModulo.setValue(record.getAttribute("flgTipoProtModulo"));
			}
		};
		idTipoDocumento.setShowTitle(false);
		idTipoDocumento.setWidth(350);
		idTipoDocumento.setPickListWidth(450);
		idTipoDocumento.setColSpan(2);
		idTipoDocumento.setAlign(Alignment.CENTER);
		idTipoDocumento.setValueField("idTipoDocumento");
		idTipoDocumento.setDisplayField("descTipoDocumento");
		if (AurigaLayout.getIsAttivaAccessibilita()) {
			idTipoDocumento.setCanFocus(true);		
		} 
		if(descType!=null) {
			idTipoDocumento.setValue(descType);
		}
		idTipoDocumento.setOptionDataSource(idTipoDocumentoDS);
		if(required) {
			idTipoDocumento.setRequired(true);
		} else {
			if (!AurigaLayout.getIsAttivaAccessibilita()) {
			idTipoDocumento.setAllowEmptyValue(true);
		}
		}
		idTipoDocumento.addDataArrivedHandler(new DataArrivedHandler() {

			@Override
			public void onDataArrived(DataArrivedEvent event) {
				// se non ci sono tipi documento o ce ne uno solo lo seleziono e chiudo la popup
				manageOnDataArrived(event.getData().toArray(), required, idTipoDocDefault, callback);
			}
		});

		descTipoDocumento = new HiddenItem("descTipoDocumento");
		codCategoriaAltraNumerazione = new HiddenItem("codCategoriaAltraNumerazione");
		siglaAltraNumerazione = new HiddenItem("siglaAltraNumerazione");
		flgTipoDocConVie = new HiddenItem("flgTipoDocConVie");
		flgOggettoNonObblig = new HiddenItem("flgOggettoNonObblig");
		flgTipoProtModulo = new HiddenItem("flgTipoProtModulo");
		
		ButtonItem okButton = new ButtonItem("okButton", "Ok");
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

		setFields(idTipoDocumento, descTipoDocumento, codCategoriaAltraNumerazione, siglaAltraNumerazione, flgTipoDocConVie, flgOggettoNonObblig, flgTipoProtModulo, okButton);

		idTipoDocumentoDS.fetchData(null, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				
				manageOnDataArrived(response.getData(), required, idTipoDocDefault, callback);
			}
		});

	}

	protected void manageOnDataArrived(Record[] data, boolean required, String idTipoDocDefault, ServiceCallback<Record> callback) {
		if (data.length == 0 || (required && data.length == 1)) {
			if (required && data.length == 1) {
				idTipoDocumento.setValue(data[0].getAttribute("idTipoDocumento"));
				descTipoDocumento.setValue(data[0].getAttribute("descTipoDocumento"));
				codCategoriaAltraNumerazione.setValue(data[0].getAttribute("codCategoriaAltraNumerazione"));
				siglaAltraNumerazione.setValue(data[0].getAttribute("siglaAltraNumerazione"));
				flgTipoDocConVie.setValue(data[0].getAttributeAsBoolean("flgTipoDocConVie"));
				flgOggettoNonObblig.setValue(data[0].getAttributeAsBoolean("flgOggettoNonObblig"));
				flgTipoProtModulo.setValue(data[0].getAttribute("flgTipoProtModulo"));
			}
			manageOnClick(callback);
		} else {
			if(idTipoDocDefault != null && !"".equals(idTipoDocDefault)) {
				for(int i = 0; i < data.length; i++) {
					if (idTipoDocDefault.equals(data[i].getAttribute("idTipoDocumento"))) {
						idTipoDocumento.setValue(data[i].getAttribute("idTipoDocumento"));
						descTipoDocumento.setValue(data[i].getAttribute("descTipoDocumento"));
						codCategoriaAltraNumerazione.setValue(data[i].getAttribute("codCategoriaAltraNumerazione"));
						siglaAltraNumerazione.setValue(data[i].getAttribute("siglaAltraNumerazione"));
						flgTipoDocConVie.setValue(data[i].getAttributeAsBoolean("flgTipoDocConVie"));
						flgOggettoNonObblig.setValue(data[i].getAttributeAsBoolean("flgOggettoNonObblig"));
						flgTipoProtModulo.setValue(data[i].getAttribute("flgTipoProtModulo"));
						break;
					}
				}
			}
			if ((!window.isDrawn()) || (!window.isVisible())) {
				window.show();
			}
		}
	}

	protected void manageOnClick(final ServiceCallback<Record> callback) {
		if (callback != null) {
			callback.execute(getValuesAsRecord());
		}
		window.markForDestroy();		
	}

}
