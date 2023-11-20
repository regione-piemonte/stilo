/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.grid.ListGrid;

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;

public class ValoriDizionarioCanvas extends ReplicableCanvas {
		
	private ReplicableCanvasForm mDynamicForm;
	
	private SelectItem keyItem;
	private HiddenItem valueItem;	

	public ValoriDizionarioCanvas(ReplicableItem item) {
		super(item);
	}

	@Override
	public void disegna() {
		
		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);
		
		GWTRestDataSource parereCircoscrizioniDS = new GWTRestDataSource("LoadComboValoriDizionarioDataSource", "key", FieldType.TEXT, true);
		if(((ValoriDizionarioItem)getItem()).getAltriParamLoadCombo() != null) {
			parereCircoscrizioniDS.addParam("altriParamLoadCombo", ((ValoriDizionarioItem)getItem()).getAltriParamLoadCombo());
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
				return pickListProperties;
			}
		};
//		keyItem.setTitleOrientation(TitleOrientation.TOP);
		keyItem.setShowTitle(false);
		keyItem.setWidth(500);
		keyItem.setColSpan(20);		
		keyItem.setStartRow(true);		
		keyItem.setValueField("key");
		keyItem.setDisplayField("value");
		keyItem.setOptionDataSource(parereCircoscrizioniDS);
		keyItem.setAllowEmptyValue(true);	
		keyItem.setRequired(true);
		
		valueItem = new HiddenItem("value");
					
		mDynamicForm.setFields(keyItem, valueItem);	
		
		mDynamicForm.setNumCols(5);
		
		addChild(mDynamicForm);
		
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