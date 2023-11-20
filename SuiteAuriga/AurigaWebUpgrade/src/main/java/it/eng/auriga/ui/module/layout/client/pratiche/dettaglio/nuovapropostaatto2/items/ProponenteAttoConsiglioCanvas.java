/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.form.fields.HiddenItem;

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;

public class ProponenteAttoConsiglioCanvas extends ReplicableCanvas {
	
	private SelectItem proponenteAttoConsiglioItem;
	private HiddenItem proponenteAttoConsiglioFromLoadDettHiddenItem;
	private HiddenItem desProponenteAttoConsiglioHiddenItem;
	
	private ReplicableCanvasForm mDynamicForm;

	public ProponenteAttoConsiglioCanvas(ReplicableItem item) {
		super(item);
	}

	@Override
	public void disegna() {
		
		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);
		
		GWTRestDataSource proponenteAttoConsiglioDS = new GWTRestDataSource("LoadComboProponenteAttoConsiglioDataSource", "key", FieldType.TEXT);
		if(((ProponenteAttoConsiglioItem)getItem()).getAltriParamLoadCombo() != null) {
			proponenteAttoConsiglioDS.addParam("altriParamLoadCombo", ((ProponenteAttoConsiglioItem)getItem()).getAltriParamLoadCombo());
		}
		
		proponenteAttoConsiglioItem = new SelectItem("proponenteAttoConsiglio") {
			
//			@Override
//			protected ListGrid builPickListProperties() {
//				ListGrid proponenteAttoConsiglioPickListProperties = super.builPickListProperties();
//				if(proponenteAttoConsiglioPickListProperties == null) {
//					proponenteAttoConsiglioPickListProperties = new ListGrid();
//				}
//				proponenteAttoConsiglioPickListProperties.addFetchDataHandler(new FetchDataHandler() {
//
//					@Override
//					public void onFilterData(FetchDataEvent event) {
//						GWTRestDataSource proponenteAttoConsiglioDS = (GWTRestDataSource) proponenteAttoConsiglioItem.getOptionDataSource();
//						proponenteAttoConsiglioDS.addParam("key", (String) proponenteAttoConsiglioFromLoadDettHiddenItem.getValue());												
//						proponenteAttoConsiglioItem.setOptionDataSource(proponenteAttoConsiglioDS);
//						proponenteAttoConsiglioItem.invalidateDisplayValueCache();
//					}
//				});
//				return proponenteAttoConsiglioPickListProperties;
//			}
			
			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);
				mDynamicForm.setValue("desProponenteAttoConsiglio", record.getAttributeAsString("descrizione"));
			}

			@Override
			protected void clearSelect() {
				super.clearSelect();
				mDynamicForm.setValue("proponenteAttoConsiglio", "");
				mDynamicForm.setValue("desProponenteAttoConsiglio", "");
			};

			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					mDynamicForm.setValue("proponenteAttoConsiglio", "");
					mDynamicForm.setValue("desProponenteAttoConsiglio", "");
				}
			}
		};
		proponenteAttoConsiglioItem.setOptionDataSource(proponenteAttoConsiglioDS);
		proponenteAttoConsiglioItem.setShowTitle(false);
		proponenteAttoConsiglioItem.setStartRow(true);
		proponenteAttoConsiglioItem.setWidth(650);
		proponenteAttoConsiglioItem.setValueField("key");
		proponenteAttoConsiglioItem.setDisplayField("value");
		proponenteAttoConsiglioItem.setAllowEmptyValue(false);
		proponenteAttoConsiglioItem.setClearable(true);
		proponenteAttoConsiglioItem.setAutoFetchData(false);
		proponenteAttoConsiglioItem.setAlwaysFetchMissingValues(true);
		proponenteAttoConsiglioItem.setFetchMissingValues(true);
		proponenteAttoConsiglioItem.setRequired(true);
		
		proponenteAttoConsiglioFromLoadDettHiddenItem = new HiddenItem("proponenteAttoConsiglioFromLoadDett");
		desProponenteAttoConsiglioHiddenItem = new HiddenItem("desProponenteAttoConsiglio");
		
		mDynamicForm.setFields(proponenteAttoConsiglioItem, proponenteAttoConsiglioFromLoadDettHiddenItem, desProponenteAttoConsiglioHiddenItem);	
		
		mDynamicForm.setNumCols(5);
		
		addChild(mDynamicForm);
		
	}

	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[]{mDynamicForm};
	}
	
	@Override
	public void editRecord(Record record) {
		
		manageLoadSelectInEditRecord(record, proponenteAttoConsiglioItem, "proponenteAttoConsiglio", new String[]{"desProponenteAttoConsiglio"}, "key");
		super.editRecord(record);				
	}
	
}
