/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.LinkedHashMap;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;

import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;

/**
 * 
 * @author DANCRIST
 *
 */

public class SceltaCommissioneForm extends DynamicForm {

	private SceltaCommissionePopup window;
	private SelectItem commissioneItem;

	public SceltaCommissioneForm(Record[] listaCommissioni, String listaIdCommissioniConvocateSalvate,
			final SceltaCommissionePopup pWindow, final ServiceCallback<Record> callback) {

		window = pWindow;

		setKeepInParentRect(true);
		setWidth100();
		setHeight100();
		setNumCols(2);
		setColWidths(200, 200);
		setCellPadding(5);
		setAlign(Alignment.CENTER);
		setTop(50);

		GWTRestDataSource gruppiRepertorioDS = new GWTRestDataSource("LoadComboConvocazioneCommissioneSource", "key", FieldType.TEXT);
		
		commissioneItem = new SelectItem("listaCommissioni", "Commissione");
		commissioneItem.setValueField("key");
		commissioneItem.setDisplayField("value");
		commissioneItem.setShowTitle(false);
		commissioneItem.setWidth(300);
		commissioneItem.setColSpan(2);
		commissioneItem.setAutoFetchData(false);
		commissioneItem.setAlign(Alignment.CENTER);
		commissioneItem.setRequired(true);
		commissioneItem.setMultiple(true);
		LinkedHashMap<String, String> valueMapCommissioni = getCommissioniMap(listaCommissioni);
		if(valueMapCommissioni != null && !valueMapCommissioni.isEmpty()) {
			commissioneItem.setValueMap(valueMapCommissioni);
		} else {
			commissioneItem.setOptionDataSource(gruppiRepertorioDS);
		}
		if(listaIdCommissioniConvocateSalvate != null &&
				!"".equalsIgnoreCase(listaIdCommissioniConvocateSalvate)) {
			commissioneItem.setDefaultValues(listaIdCommissioniConvocateSalvate.split(";"));
		}

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

		setFields(commissioneItem, okButton);

	}
	
	private LinkedHashMap<String, String> getCommissioniMap(Record[] listaCommissioni) {
		LinkedHashMap<String, String> repertoriMap = new LinkedHashMap<String, String>();
		if(listaCommissioni != null) {
			for(Record recordItem : listaCommissioni) {
				repertoriMap.put(recordItem.getAttribute("key"), recordItem.getAttribute("value"));
			}
		}
		return repertoriMap;
	}

	protected void manageOnClick(final ServiceCallback<Record> callback) {
		if (callback != null) {
			Record record = new Record();
			record.setAttribute("listaCommissioni", commissioneItem.getValueAsString());
			
			final RecordList listaCommissioniSelezionate = new RecordList();
			for(int i = 0; i < commissioneItem.getSelectedRecords().length; i++) {
				listaCommissioniSelezionate.add(commissioneItem.getSelectedRecords()[i]);
			}
			record.setAttribute("listaCommissioniSelezionate", listaCommissioniSelezionate);
			
			callback.execute(record);
		}
		window.markForDestroy();		
	}
}