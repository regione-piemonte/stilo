/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
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

public class UfficioGareAcquistiCanvas extends ReplicableCanvas {
	
	private SelectItem ufficioGareAcquistiItem;
	private HiddenItem desUfficioGareAcquistiHiddenItem;
	
	private ReplicableCanvasForm mDynamicForm;

	private String start;

	public UfficioGareAcquistiCanvas(ReplicableItem item) {
		super(item);
	}

	@Override
	public void disegna() {
		
		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);
		
		GWTRestDataSource ufficioGareAcquistiDS = ((UfficioGareAcquistiItem)getItem()).getUfficioGareAcquistiDS();
		
		ufficioGareAcquistiItem = new SelectItem("ufficioGareAcquisti") {
			
			@Override
			protected ListGrid builPickListProperties() {
				ListGrid ufficioGareAcquistiPickListProperties = super.builPickListProperties();
				if(ufficioGareAcquistiPickListProperties == null) {
					ufficioGareAcquistiPickListProperties = new ListGrid();
				}
				ufficioGareAcquistiPickListProperties.addFetchDataHandler(new FetchDataHandler() {

					@Override
					public void onFilterData(FetchDataEvent event) {
						
						start = DateTimeFormat.getFormat("HH:mm:ss").format(new Date());
						
//						GWTRestDataSource ufficioGareAcquistiDS = (GWTRestDataSource) ufficioGareAcquistiItem.getOptionDataSource();
//						ufficioGareAcquistiDS.addParam("uoProponente", ((UfficioGareAcquistiItem)getItem()).getUoProponenteCorrente());												
//						ufficioGareAcquistiItem.setOptionDataSource(ufficioGareAcquistiDS);
//						ufficioGareAcquistiItem.invalidateDisplayValueCache();
					}
				});
				return ufficioGareAcquistiPickListProperties;
			}
			
			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);
				mDynamicForm.setValue("desUfficioGareAcquisti", record.getAttributeAsString("descrizione"));
			}

			@Override
			protected void clearSelect() {
				super.clearSelect();
				mDynamicForm.setValue("ufficioGareAcquisti", "");
				mDynamicForm.setValue("desUfficioGareAcquisti", "");
			};

			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					mDynamicForm.setValue("ufficioGareAcquisti", "");
					mDynamicForm.setValue("desUfficioGareAcquisti", "");
				}
			}
		};
		ufficioGareAcquistiItem.setOptionDataSource(ufficioGareAcquistiDS);
		ufficioGareAcquistiItem.setShowTitle(false);
		ufficioGareAcquistiItem.setStartRow(true);
		ufficioGareAcquistiItem.setWidth(650);
		ufficioGareAcquistiItem.setValueField("key");
		ufficioGareAcquistiItem.setDisplayField("value");
		ufficioGareAcquistiItem.setAllowEmptyValue(false);
		ufficioGareAcquistiItem.setClearable(true);
		// WORKAROUND PER IL PROBLEMA RELATIVO AL METODO resetAfterChangedParams CHE, A SEGUITO DELLA FETCH, NON ENTRA NELLA CALLBACK
		if(((UfficioGareAcquistiItem)getItem()).getFlgAbilitaAutoFetchDataSelectOrganigramma()) {
			ufficioGareAcquistiItem.setAutoFetchData(true);
		} else {			
			ufficioGareAcquistiItem.setAutoFetchData(false);
		}
		ufficioGareAcquistiItem.setAlwaysFetchMissingValues(true);
		ufficioGareAcquistiItem.setFetchMissingValues(true);
		ufficioGareAcquistiItem.setRequired(true);
		ufficioGareAcquistiItem.addDataArrivedHandler(new DataArrivedHandler() {

			@Override
			public void onDataArrived(DataArrivedEvent event) {
				
				GWT.log("loadCombo() listaUfficioGareAcquisti started at " + start + " ended at " + DateTimeFormat.getFormat("HH:mm:ss").format(new Date()));												
			}
		});
		
		desUfficioGareAcquistiHiddenItem = new HiddenItem("desUfficioGareAcquisti");
					
		mDynamicForm.setFields(ufficioGareAcquistiItem, desUfficioGareAcquistiHiddenItem);	
		
		mDynamicForm.setNumCols(5);
		
		addChild(mDynamicForm);
		
	}
	
	public void resetAfterChangedParams() {
			
		final String value = ufficioGareAcquistiItem.getValueAsString();
		ufficioGareAcquistiItem.fetchData(new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {

				RecordList data = response.getDataAsRecordList();
				if(((UfficioGareAcquistiItem)getItem()).selectUniqueValueAfterChangedParams() && data.getLength() == 1) {
					if(value == null || "".equals(value) || !value.equals(data.get(0).getAttributeAsString("key"))) {	
						mDynamicForm.setValue("ufficioGareAcquisti", data.get(0).getAttribute("key"));
						mDynamicForm.setValue("desUfficioGareAcquisti", data.get(0).getAttribute("value"));
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
						mDynamicForm.setValue("ufficioGareAcquisti", "");
						mDynamicForm.setValue("desUfficioGareAcquisti", "");
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
		
		manageLoadSelectInEditRecord(record, ufficioGareAcquistiItem, "ufficioGareAcquisti", new String[]{"desUfficioGareAcquisti"}, "key");
		super.editRecord(record);				
	}
	
}
