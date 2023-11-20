/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.LinkedHashMap;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;

import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;

/**
 * 
 * @author DANCRIST
 *
 */

public class SceltaSedutaOrganoCollegialeForm extends DynamicForm {

	private SceltaSedutaOrganoCollegialePopup window;
	private SelectItem sedutaItem;
	private HiddenItem storicoItem;

	public SceltaSedutaOrganoCollegialeForm(Record[] listaSedute, final SceltaSedutaOrganoCollegialePopup pWindow, final ServiceCallback<Record> callback) {

		window = pWindow;

		setKeepInParentRect(true);
		setWidth100();
		setHeight100();
		setNumCols(2);
		setColWidths(200, 200);
		setCellPadding(5);
		setAlign(Alignment.CENTER);
		setTop(50);
		
		storicoItem = new HiddenItem("storico");		
		
		sedutaItem = new SelectItem("idSeduta", "Sedute") {
        	@Override
        	public void onOptionClick(Record record) {
        				
        		storicoItem.setValue(record.getAttribute("storico"));
        	}
        	@Override
			public void setValue(String value) {
				if (value == null || "".equals(value)) {
					sedutaItem.setValue("");
					storicoItem.setValue("");
				}				
			}

			@Override
			protected void clearSelect() {
				sedutaItem.setValue("");
				storicoItem.setValue("");
			};
        };  
		sedutaItem.setShowTitle(false);
		sedutaItem.setWidth(300);
		sedutaItem.setColSpan(2);
		sedutaItem.setAutoFetchData(false);
		sedutaItem.setAlign(Alignment.CENTER);
		sedutaItem.setRequired(true);
		sedutaItem.setMultiple(false);
		sedutaItem.setAllowEmptyValue(false);
		sedutaItem.setValueField("key");
		sedutaItem.setDisplayField("value");
		
		StoricoSedutaDS storicoSedutaDS = new StoricoSedutaDS();     
		storicoSedutaDS.setTestData(listaSedute);
		sedutaItem.setOptionDataSource(storicoSedutaDS);  

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

		setFields(storicoItem,sedutaItem, okButton);

	}
	
	private LinkedHashMap<String, String> getSeduteMap(Record[] listaSedute) {
		LinkedHashMap<String, String> seduteMap = new LinkedHashMap<String, String>();
		if(listaSedute != null) {
			for(Record recordItem : listaSedute) {
				seduteMap.put(recordItem.getAttribute("key"), recordItem.getAttribute("value"));
			}
		}
		return seduteMap;
	}

	protected void manageOnClick(final ServiceCallback<Record> callback) {
		if (callback != null) {
			Record record = new Record();
			record.setAttribute("idSeduta", sedutaItem.getValueAsString());
			record.setAttribute("storico",  (String) storicoItem.getValue());
			callback.execute(record);
		}
		window.markForDestroy();		
	}
	
	public class StoricoSedutaDS extends DataSource {

		public StoricoSedutaDS() {

			DataSourceTextField key = new DataSourceTextField("key");
			key.setPrimaryKey(true);
			key.setHidden(true);
			
			DataSourceTextField value = new DataSourceTextField("value");
			
			DataSourceTextField storico = new DataSourceTextField("storico");
			storico.setHidden(true);

			setFields(key, value, storico);
			
			setClientOnly(true);   
			
		}

	}
}