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

public class CoordinatoriCompCircCanvas extends ReplicableCanvas {
	
	private SelectItem coordinatoreCompCircItem;
	private HiddenItem coordinatoreCompCircFromLoadDettHiddenItem;
	private HiddenItem desCoordinatoreCompCircHiddenItem;
	
	private ReplicableCanvasForm mDynamicForm;

	private String start;

	public CoordinatoriCompCircCanvas(ReplicableItem item) {
		super(item);
	}

	@Override
	public void disegna() {
		
		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);
		
		GWTRestDataSource coordinatoreCompCircDS = new GWTRestDataSource("LoadComboCoordinatoriCompCircDataSource", "key", FieldType.TEXT);
		if(((CoordinatoriCompCircItem)getItem()).getAltriParamLoadCombo() != null) {
			coordinatoreCompCircDS.addParam("altriParamLoadCombo", ((CoordinatoriCompCircItem)getItem()).getAltriParamLoadCombo());
		}
		
		coordinatoreCompCircItem = new SelectItem("coordinatoreCompCirc") {
			
			@Override
			protected ListGrid builPickListProperties() {
				ListGrid coordinatoreCompCircPickListProperties = super.builPickListProperties();
				if(coordinatoreCompCircPickListProperties == null) {
					coordinatoreCompCircPickListProperties = new ListGrid();
				}
				coordinatoreCompCircPickListProperties.addFetchDataHandler(new FetchDataHandler() {

					@Override
					public void onFilterData(FetchDataEvent event) {
						
						start = DateTimeFormat.getFormat("HH:mm:ss").format(new Date());
						
						GWTRestDataSource coordinatoreCompCircDS = (GWTRestDataSource) coordinatoreCompCircItem.getOptionDataSource();
						coordinatoreCompCircDS.addParam("uoProponente", ((CoordinatoriCompCircItem)getItem()).getUoProponenteCorrente());						
//						coordinatoreCompCircDS.addParam("key", (String) coordinatoreCompCircFromLoadDettHiddenItem.getValue());												
						coordinatoreCompCircItem.setOptionDataSource(coordinatoreCompCircDS);
						coordinatoreCompCircItem.invalidateDisplayValueCache();
					}
				});
				return coordinatoreCompCircPickListProperties;				
			}
			
			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);
				mDynamicForm.setValue("desCoordinatoreCompCirc", record.getAttributeAsString("value"));
			}

			@Override
			protected void clearSelect() {
				super.clearSelect();
				mDynamicForm.setValue("coordinatoreCompCirc", "");
				mDynamicForm.setValue("desCoordinatoreCompCirc", "");
			};

			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					mDynamicForm.setValue("coordinatoreCompCirc", "");
					mDynamicForm.setValue("desCoordinatoreCompCirc", "");
				}
			}
		};
		coordinatoreCompCircItem.setOptionDataSource(coordinatoreCompCircDS);
		coordinatoreCompCircItem.setShowTitle(false);
		coordinatoreCompCircItem.setStartRow(true);
		coordinatoreCompCircItem.setWidth(650);
		coordinatoreCompCircItem.setValueField("key");
		coordinatoreCompCircItem.setDisplayField("value");
		coordinatoreCompCircItem.setAllowEmptyValue(false);
		coordinatoreCompCircItem.setClearable(true);
		// WORKAROUND PER IL PROBLEMA RELATIVO AL METODO resetAfterChangedParams CHE, A SEGUITO DELLA FETCH, NON ENTRA NELLA CALLBACK
		if(((CoordinatoriCompCircItem)getItem()).getFlgAbilitaAutoFetchDataSelectOrganigramma()) {
			coordinatoreCompCircItem.setAutoFetchData(true);
		} else {
			coordinatoreCompCircItem.setAutoFetchData(false);
		}
		coordinatoreCompCircItem.setAlwaysFetchMissingValues(true);
		coordinatoreCompCircItem.setFetchMissingValues(true);
		coordinatoreCompCircItem.setRequired(true);
		coordinatoreCompCircItem.addDataArrivedHandler(new DataArrivedHandler() {

			@Override
			public void onDataArrived(DataArrivedEvent event) {
				
				GWT.log("loadCombo() listaCoordinatoreCompCirc started at " + start + " ended at " + DateTimeFormat.getFormat("HH:mm:ss").format(new Date()));																
			}
		});
		
		coordinatoreCompCircFromLoadDettHiddenItem = new HiddenItem("coordinatoreCompCircFromLoadDett");
		desCoordinatoreCompCircHiddenItem = new HiddenItem("desCoordinatoreCompCirc");
		
		mDynamicForm.setFields(coordinatoreCompCircItem, coordinatoreCompCircFromLoadDettHiddenItem, desCoordinatoreCompCircHiddenItem);	
		
		mDynamicForm.setNumCols(5);
		
		addChild(mDynamicForm);
		
	}
	
	public void resetAfterChangedParams() {
		
		final String value = coordinatoreCompCircItem.getValueAsString();
		coordinatoreCompCircItem.fetchData(new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				
				RecordList data = response.getDataAsRecordList();
				if(((CoordinatoriCompCircItem)getItem()).selectUniqueValueAfterChangedParams() && data.getLength() == 1) {
					if(value == null || "".equals(value) || !value.equals(data.get(0).getAttributeAsString("key"))) {	
						mDynamicForm.setValue("coordinatoreCompCirc", data.get(0).getAttribute("key"));
						mDynamicForm.setValue("desCoordinatoreCompCirc", data.get(0).getAttribute("value"));
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
						mDynamicForm.setValue("coordinatoreCompCirc", "");
						mDynamicForm.setValue("desCoordinatoreCompCirc", "");					
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
		
		manageLoadSelectInEditRecord(record, coordinatoreCompCircItem, "coordinatoreCompCirc", new String[]{"desCoordinatoreCompCirc"}, "key");
		super.editRecord(record);				
	}
	
}
