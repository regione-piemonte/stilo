/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.LinkedHashMap;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.DataArrivedEvent;
import com.smartgwt.client.widgets.form.fields.events.DataArrivedHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;

public class SceltaRepertorioForm extends DynamicForm {

	private SceltaRepertorioPopup window;
	private DynamicForm instance;

	private SelectItem repertorioItem;

	public SceltaRepertorioForm(final String flgTipoProv, final String repertorioDefault, Record[] listaRepertori, 
					final SceltaRepertorioPopup pWindow, final ServiceCallback<Record> callback) {

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

		GWTRestDataSource gruppiRepertorioDS = new GWTRestDataSource("LoadComboGruppiRepertorioSource", "key", FieldType.TEXT);
		gruppiRepertorioDS.addParam("flgTipoProv", flgTipoProv != null && !"".equalsIgnoreCase(flgTipoProv) ? flgTipoProv : "I");
		gruppiRepertorioDS.addParam("isAttivaAccessibilita", ""+AurigaLayout.getIsAttivaAccessibilita());

		repertorioItem = new SelectItem("repertorio", I18NUtil.getMessages().protocollazione_detail_repertorioItem_title());
		repertorioItem.setValueField("key");
		repertorioItem.setDisplayField("value");
		repertorioItem.setShowTitle(false);
		repertorioItem.setWidth(300);
		repertorioItem.setColSpan(2);
		repertorioItem.setAutoFetchData(false);
		repertorioItem.setAlign(Alignment.CENTER);
		if (AurigaLayout.getIsAttivaAccessibilita()) {
			repertorioItem.setValidators(new CustomValidator() {
				
				@Override
				protected boolean condition(Object value) {
					// TODO Auto-generated method stub
					return value.equals("") || value.equals(I18NUtil.getMessages().protocollazione_select_repertorio_tipologia_empty_value()) ? false : true;
				}
			});
		} else {
		repertorioItem.setRequired(true);
		}
		LinkedHashMap<String, String> valueMapRepertori = getRepertoriMap(listaRepertori);
		if(valueMapRepertori != null && !valueMapRepertori.isEmpty()) {
			repertorioItem.setValueMap(valueMapRepertori);
		} else {
			repertorioItem.setOptionDataSource(gruppiRepertorioDS);
		}
		repertorioItem.addDataArrivedHandler(new DataArrivedHandler() {

			@Override
			public void onDataArrived(DataArrivedEvent event) {
				// se non ci sono tipi documento o ce ne uno solo lo seleziono e chiudo la popup
				manageOnDataArrived(event.getData().toArray(), repertorioDefault, callback);
			}
		});

		ButtonItem okButton = new ButtonItem("okButton", "Ok");
		okButton.setColSpan(2);
		okButton.setWidth(100);
		okButton.setTop(20);
		okButton.setAlign(Alignment.CENTER);
		okButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (validate()) {
					manageOnClick(callback);
				}
			}
		});

		setFields(repertorioItem, okButton);

		if(valueMapRepertori != null && !valueMapRepertori.isEmpty()) {			
			repertorioItem.setValue(AurigaLayout.getIsAttivaAccessibilita() ? I18NUtil.getMessages().protocollazione_select_repertorio_tipologia_empty_value() : repertorioDefault);
			window.show();
		} else {
			gruppiRepertorioDS.fetchData(null, new DSCallback() {
				
				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
	
					manageOnDataArrived(response.getData(), repertorioDefault, callback);
				}
			});
		}
		
	}

	protected void manageOnDataArrived(Record[] data, String repertorioDefault, ServiceCallback<Record> callback) {
		if (data.length <= 1) {
			if (data.length == 1) {
				repertorioItem.setValue(data[0].getAttribute("key"));
			}
			manageOnClick(callback);
		} else {
			if(repertorioDefault != null && !"".equals(repertorioDefault)) {
				for(int i = 0; i < data.length; i++) {
					if (repertorioDefault.equals(data[i].getAttribute("key"))) {
						repertorioItem.setValue(data[i].getAttribute("key"));
						break;
					}
				}
			}
			window.show();
		}
	}
	
	private LinkedHashMap<String, String> getRepertoriMap(Record[] listaRepertori) {
		LinkedHashMap<String, String> repertoriMap = new LinkedHashMap<String, String>();
		if(listaRepertori != null) {
			for(Record recordItem : listaRepertori) {
				repertoriMap.put(recordItem.getAttribute("key"), recordItem.getAttribute("value"));
			}
		}
		return repertoriMap;
	}

	protected void manageOnClick(final ServiceCallback<Record> callback) {
		if (callback != null) {
			callback.execute(getValuesAsRecord());
		}
		window.markForDestroy();		
	}

}