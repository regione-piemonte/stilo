/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.form.fields.HiddenItem;

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;

public class UtenteRifAttoConsiglioCanvas extends ReplicableCanvas {
	
	private SelectItem utenteRifAttoConsiglioItem;
	private HiddenItem utenteRifAttoConsiglioFromLoadDettHiddenItem;
	private HiddenItem desUtenteRifAttoConsiglioHiddenItem;
	
	private ReplicableCanvasForm mDynamicForm;

	public UtenteRifAttoConsiglioCanvas(ReplicableItem item) {
		super(item);
	}

	@Override
	public void disegna() {
		
		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);
		
		GWTRestDataSource utenteRifAttoConsiglioDS = new GWTRestDataSource("LoadComboUtenteRifAttoConsiglioDataSource", "key", FieldType.TEXT);
		if(((UtenteRifAttoConsiglioItem)getItem()).getAltriParamLoadCombo() != null) {
			utenteRifAttoConsiglioDS.addParam("altriParamLoadCombo", ((UtenteRifAttoConsiglioItem)getItem()).getAltriParamLoadCombo());
		}
		
		utenteRifAttoConsiglioItem = new SelectItem("utenteRifAttoConsiglio") {
			
//			@Override
//			protected ListGrid builPickListProperties() {
//				ListGrid utenteRifAttoConsiglioPickListProperties = super.builPickListProperties();
//				if(utenteRifAttoConsiglioPickListProperties == null) {
//					utenteRifAttoConsiglioPickListProperties = new ListGrid();
//				}
//				utenteRifAttoConsiglioPickListProperties.addFetchDataHandler(new FetchDataHandler() {
//
//					@Override
//					public void onFilterData(FetchDataEvent event) {
//						GWTRestDataSource utenteRifAttoConsiglioDS = (GWTRestDataSource) utenteRifAttoConsiglioItem.getOptionDataSource();
//						utenteRifAttoConsiglioDS.addParam("key", (String) utenteRifAttoConsiglioFromLoadDettHiddenItem.getValue());												
//						utenteRifAttoConsiglioItem.setOptionDataSource(utenteRifAttoConsiglioDS);
//						utenteRifAttoConsiglioItem.invalidateDisplayValueCache();
//					}
//				});
//				return utenteRifAttoConsiglioPickListProperties;
//			}
			
			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);
				mDynamicForm.setValue("desUtenteRifAttoConsiglio", record.getAttributeAsString("descrizione"));
			}

			@Override
			protected void clearSelect() {
				super.clearSelect();
				mDynamicForm.setValue("utenteRifAttoConsiglio", "");
				mDynamicForm.setValue("desUtenteRifAttoConsiglio", "");
			};

			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					mDynamicForm.setValue("utenteRifAttoConsiglio", "");
					mDynamicForm.setValue("desUtenteRifAttoConsiglio", "");
				}
			}
		};
		utenteRifAttoConsiglioItem.setOptionDataSource(utenteRifAttoConsiglioDS);
		utenteRifAttoConsiglioItem.setShowTitle(false);
		utenteRifAttoConsiglioItem.setStartRow(true);
		utenteRifAttoConsiglioItem.setWidth(650);
		utenteRifAttoConsiglioItem.setValueField("key");
		utenteRifAttoConsiglioItem.setDisplayField("value");
		utenteRifAttoConsiglioItem.setAllowEmptyValue(false);
		utenteRifAttoConsiglioItem.setClearable(true);
		utenteRifAttoConsiglioItem.setAutoFetchData(false);
		utenteRifAttoConsiglioItem.setAlwaysFetchMissingValues(true);
		utenteRifAttoConsiglioItem.setFetchMissingValues(true);
		utenteRifAttoConsiglioItem.setRequired(true);
		
		utenteRifAttoConsiglioFromLoadDettHiddenItem = new HiddenItem("utenteRifAttoConsiglioFromLoadDett");
		desUtenteRifAttoConsiglioHiddenItem = new HiddenItem("desUtenteRifAttoConsiglio");
					
		mDynamicForm.setFields(utenteRifAttoConsiglioItem, utenteRifAttoConsiglioFromLoadDettHiddenItem, desUtenteRifAttoConsiglioHiddenItem);	
		
		mDynamicForm.setNumCols(5);
		
		addChild(mDynamicForm);
		
	}

	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[]{mDynamicForm};
	}
	
	@Override
	public void editRecord(Record record) {
		
		manageLoadSelectInEditRecord(record, utenteRifAttoConsiglioItem, "utenteRifAttoConsiglio", new String[]{"desUtenteRifAttoConsiglio"}, "key");
		super.editRecord(record);				
	}
	
}
