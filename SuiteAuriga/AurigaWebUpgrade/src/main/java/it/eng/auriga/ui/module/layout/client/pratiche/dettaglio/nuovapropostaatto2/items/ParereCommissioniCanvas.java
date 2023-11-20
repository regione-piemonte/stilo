/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.events.FetchDataEvent;
import com.smartgwt.client.widgets.events.FetchDataHandler;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.DataArrivedEvent;
import com.smartgwt.client.widgets.form.fields.events.DataArrivedHandler;
import com.smartgwt.client.widgets.grid.ListGrid;

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;

public class ParereCommissioniCanvas extends ReplicableCanvas {
		
	private ReplicableCanvasForm mDynamicForm;
	
	private SelectItem keyItem;
	private HiddenItem valueItem;	

	private String start;

	public ParereCommissioniCanvas(ReplicableItem item) {
		super(item);
	}

	@Override
	public void disegna() {
		
		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);
		
		GWTRestDataSource parereCircoscrizioniDS = new GWTRestDataSource("LoadComboGenericaDataSource", "key", FieldType.TEXT, true);
		parereCircoscrizioniDS.addParam("tipoCombo", "COMMISSIONI_X_CONVOCAZIONE");
		if(((ParereCommissioniItem)getItem()).getAltriParamLoadCombo() != null) {
			parereCircoscrizioniDS.addParam("altriParamLoadCombo", ((ParereCommissioniItem)getItem()).getAltriParamLoadCombo());
		}
		 		
		keyItem = new SelectItem("key") {
			
			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);	
				mDynamicForm.setValue("value", record.getAttributeAsString("value"));
			}			
			
			@Override
			protected void clearSelect() {
				super.clearSelect();
				mDynamicForm.setValue("key", "");	
				mDynamicForm.setValue("value", "");	
			};		
			
			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					mDynamicForm.setValue("key", "");
					mDynamicForm.setValue("value", "");
				}
			}

			@Override
			protected ListGrid builPickListProperties() {
				ListGrid pickListProperties = super.builPickListProperties();	
				pickListProperties.setShowHeader(false);				
				pickListProperties.addFetchDataHandler(new FetchDataHandler() {

					@Override
					public void onFilterData(FetchDataEvent event) {
						
						start = DateTimeFormat.getFormat("HH:mm:ss").format(new Date());
						
						GWTRestDataSource parereCircoscrizioniDS = (GWTRestDataSource) keyItem.getOptionDataSource();		
						parereCircoscrizioniDS.addParam("uoProponente", ((ParereCommissioniItem)getItem()).getUoProponenteCorrente());
						keyItem.setOptionDataSource(parereCircoscrizioniDS);
						keyItem.invalidateDisplayValueCache();
					}
				});
				return pickListProperties;
			}
		};
		keyItem.setOptionDataSource(parereCircoscrizioniDS);
//		keyItem.setTitleOrientation(TitleOrientation.TOP);
		keyItem.setShowTitle(false);
		keyItem.setStartRow(true);		
		keyItem.setWidth(500);
		keyItem.setColSpan(20);		
		keyItem.setValueField("key");
		keyItem.setDisplayField("value");
		keyItem.setAllowEmptyValue(false);
		keyItem.setClearable(true);
		// WORKAROUND PER IL PROBLEMA RELATIVO AL METODO resetAfterChangedParams CHE, A SEGUITO DELLA FETCH, NON ENTRA NELLA CALLBACK
		if(((ParereCommissioniItem)getItem()).getFlgAbilitaAutoFetchDataSelectOrganigramma()) {
			keyItem.setAutoFetchData(true);			
		} else {
			keyItem.setAutoFetchData(false);
		}
		keyItem.setAlwaysFetchMissingValues(true);
		keyItem.setFetchMissingValues(true);
		keyItem.setRequired(true);
		keyItem.addDataArrivedHandler(new DataArrivedHandler() {

			@Override
			public void onDataArrived(DataArrivedEvent event) {
				
				GWT.log("loadCombo() listaParereCommissioni started at " + start + " ended at " + DateTimeFormat.getFormat("HH:mm:ss").format(new Date()));												
			}
		});
		
		valueItem = new HiddenItem("value");
					
		mDynamicForm.setFields(keyItem, valueItem);	
		
		mDynamicForm.setNumCols(5);
		
		addChild(mDynamicForm);
		
	}
	
	public void resetAfterChangedParams() {
		
		final String value = keyItem.getValueAsString();
		keyItem.fetchData(new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				
				RecordList data = response.getDataAsRecordList();
				if(((ParereCommissioniItem)getItem()).selectUniqueValueAfterChangedParams() && data.getLength() == 1) {
					if(value == null || "".equals(value) || !value.equals(data.get(0).getAttributeAsString("key"))) {	
						mDynamicForm.setValue("key", data.get(0).getAttribute("key"));
						mDynamicForm.setValue("value", data.get(0).getAttribute("value"));
					}
				} else if(value != null && !"".equals(value)) {
					boolean trovato = false;
					if (data.getLength() > 0) {
						for (int i = 0; i < data.getLength(); i++) {
							String key = data.get(i).getAttribute("key");
							if (value.equals(key)) {
								trovato = true;
								break;
							}
						}
					}
					if (!trovato) {
						mDynamicForm.setValue("key", "");
						mDynamicForm.setValue("value", "");					
					}
				}
			}
		});
	}

	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[]{mDynamicForm};
	}
	
	@Override
	public void editRecord(Record record) {
		
		manageLoadSelectInEditRecord(record, keyItem, "key", new String[]{"value"}, "key");
		super.editRecord(record);				
	}
	
}