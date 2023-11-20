/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Visibility;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.DataArrivedEvent;
import com.smartgwt.client.widgets.form.fields.events.DataArrivedHandler;

public class SelezionaTipoElencoAlboForm extends DynamicForm {

	private SelezionaTipoElencoAlboWindow window;
	private DynamicForm instance;
	private HiddenItem abilInsModDel;
	private HiddenItem nomeTipo;
	private SelectItem idTipo;

	public SelezionaTipoElencoAlboForm(SelezionaTipoElencoAlboWindow pWindow) {

		instance = this;
		window = pWindow;
		setKeepInParentRect(true);
		setWidth100();
		setHeight100();
		setNumCols(2);
		setColWidths(200, 200);
		setCellPadding(5);
		setAlign(Alignment.CENTER);
		setTop(50);

		idTipo = new SelectItem("idTipo") {

			@Override
			public void onOptionClick(Record record) {
				
				nomeTipo.setValue(record.getAttribute("nomeTipo"));
				abilInsModDel.setValue(record.getAttribute("abilInsModDel") != null ? "1".equals(record.getAttribute("abilInsModDel")) : null);
			}
		};
		idTipo.setShowTitle(false);
		idTipo.setRequired(true);
		idTipo.setWidth(300);
		idTipo.setColSpan(2);
		idTipo.setAlign(Alignment.CENTER);
		idTipo.setValueField("idTipo");
		idTipo.setDisplayField("nomeTipo");
		idTipo.setOptionDataSource(new GWTRestDataSource("TipiElenchiAlbiDataSource"));
		idTipo.addDataArrivedHandler(new DataArrivedHandler() {

			@Override
			public void onDataArrived(DataArrivedEvent event) {
				
				if (event.getData().getLength() > 0) {
					Record record = event.getData().get(0);
					idTipo.setValue(record.getAttribute("idTipo"));
					nomeTipo.setValue(record.getAttribute("nomeTipo"));
					abilInsModDel.setValue(record.getAttribute("abilInsModDel") != null ? "1".equals(record.getAttribute("abilInsModDel")) : null);
				}
			}
		});

		nomeTipo = new HiddenItem("nomeTipo");

		abilInsModDel = new HiddenItem("abilInsModDel");

		ButtonItem okButton = new ButtonItem("okButton", "Ok");
		okButton.setColSpan(2);
		okButton.setWidth(100);
		okButton.setTop(20);
		okButton.setAlign(Alignment.CENTER);
		okButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				manageOnClick();
			}
		});

		setFields(idTipo, nomeTipo, abilInsModDel, okButton);

	}

	protected void manageOnClick() {

		final String idTipo = getValueAsString("idTipo");
		final String nomeTipo = getValueAsString("nomeTipo");
		final boolean abilInsModDel = getValue("abilInsModDel") != null ? (Boolean) getValue("abilInsModDel") : true;

		GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("AttributiDinamiciDatasource");
		Record record = new Record();
		record.setAttribute("nomeTabella", "DMT_ELENCHI_ALBI_CONTENTS");
		record.setAttribute("tipoEntita", idTipo);
		lGwtRestService.call(record, new ServiceCallback<Record>() {

			@Override
			public void execute(Record object) {
				
				final String portletTitle = "Elenchi e albi - " + nomeTipo;
				final String idTipoForPref = idTipo;
				ElenchiAlbiLayout body = new ElenchiAlbiLayout(idTipo, nomeTipo, object.getAttributeAsRecordList("attributiAdd"), object
						.getAttributeAsMap("mappaDettAttrLista"), object.getAttributeAsMap("mappaValoriAttrLista"), object
						.getAttributeAsMap("mappaVariazioniAttrLista"), object.getAttributeAsMap("mappaDocumenti"), abilInsModDel) {

					@Override
					public String getBaseTitle() {
						
						return portletTitle;
					}

					@Override
					public String getIdTipoForPref() {
						
						return idTipoForPref;
					}
				};
				body.setVisibility(Visibility.HIDDEN);
				Layout.addPortlet("elenchi_albi." + idTipo, portletTitle, "menu/elenchiAlbi.png", body);
				window.markForDestroy();

			}
		});

	}

}
