/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;

public class ConsiglieriCanvas extends ReplicableCanvas {
	
	private SelectItem consigliereItem;
	private HiddenItem consigliereFromLoadDettHiddenItem;
	private HiddenItem desConsigliereHiddenItem;
	private CheckboxItem flgConsigliereFirmatarioItem;
	private CheckboxItem flgConsigliereFirmaInSostSindacoItem;
	
	private ReplicableCanvasForm mDynamicForm;

	public ConsiglieriCanvas(ReplicableItem item) {
		super(item);
	}

	@Override
	public void disegna() {
		
		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);
		
		GWTRestDataSource consigliereDS = new GWTRestDataSource("LoadComboConsiglieriDataSource", "key", FieldType.TEXT);
		if(((ConsiglieriItem)getItem()).getAltriParamLoadCombo() != null) {
			consigliereDS.addParam("altriParamLoadCombo", ((ConsiglieriItem)getItem()).getAltriParamLoadCombo());
		}
		
		consigliereItem = new SelectItem("consigliere") {
			
//			@Override
//			protected ListGrid builPickListProperties() {
//				ListGrid consiglierePickListProperties = super.builPickListProperties();
//				if(consiglierePickListProperties == null) {
//					consiglierePickListProperties = new ListGrid();
//				}
//				consiglierePickListProperties.addFetchDataHandler(new FetchDataHandler() {
//
//					@Override
//					public void onFilterData(FetchDataEvent event) {
//						GWTRestDataSource consigliereDS = (GWTRestDataSource) consigliereItem.getOptionDataSource();
//						consigliereDS.addParam("key", (String) consigliereFromLoadDettHiddenItem.getValue());												
//						consigliereItem.setOptionDataSource(consigliereDS);
//						consigliereItem.invalidateDisplayValueCache();
//					}
//				});
//				return consiglierePickListProperties;
//			}
			
			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);
				mDynamicForm.setValue("desConsigliere", record.getAttributeAsString("descrizione"));
			}

			@Override
			protected void clearSelect() {
				super.clearSelect();
				mDynamicForm.setValue("consigliere", "");
				mDynamicForm.setValue("desConsigliere", "");
			};

			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					mDynamicForm.setValue("consigliere", "");
					mDynamicForm.setValue("desConsigliere", "");
				}
			}
		};
		consigliereItem.setOptionDataSource(consigliereDS);
		consigliereItem.setShowTitle(false);
		consigliereItem.setStartRow(true);
		consigliereItem.setWidth(650);
		consigliereItem.setValueField("key");
		consigliereItem.setDisplayField("value");
		consigliereItem.setAllowEmptyValue(false);
		consigliereItem.setClearable(true);
		consigliereItem.setAutoFetchData(false);
		consigliereItem.setAlwaysFetchMissingValues(true);
		consigliereItem.setFetchMissingValues(true);
		consigliereItem.setRequired(true);
		consigliereItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {						
				mDynamicForm.markForRedraw();
			}
		});
		
		consigliereFromLoadDettHiddenItem = new HiddenItem("consigliereFromLoadDett");
		desConsigliereHiddenItem = new HiddenItem("desConsigliere");
		
		flgConsigliereFirmatarioItem = new CheckboxItem("flgConsigliereFirmatario", I18NUtil.getMessages().nuovaPropostaAtto2_detail_flgFirmatario_title());
		flgConsigliereFirmatarioItem.setDefaultValue(false);
		flgConsigliereFirmatarioItem.setColSpan(1);
		flgConsigliereFirmatarioItem.setWidth("*");
		flgConsigliereFirmatarioItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return ((ConsiglieriItem)getItem()).showFlgFirmatario();
			}
		});
		
		flgConsigliereFirmaInSostSindacoItem = new CheckboxItem("flgConsigliereFirmaInSostSindaco", ((ConsiglieriItem)getItem()).getTitleFlgFirmaInSostSindaco());
		flgConsigliereFirmaInSostSindacoItem.setDefaultValue(((ConsiglieriItem)getItem()).getDefaultValueAsBooleanFlgFirmaInSostSindaco());
		flgConsigliereFirmaInSostSindacoItem.setColSpan(1);
		flgConsigliereFirmaInSostSindacoItem.setWidth("*");		
		flgConsigliereFirmaInSostSindacoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(consigliereItem.getDisplayValue() != null && (consigliereItem.getDisplayValue().toUpperCase().contains("VICESINDAC") || consigliereItem.getDisplayValue().toUpperCase().contains("VICE-SINDAC"))) {					
					return ((ConsiglieriItem)getItem()).showFlgFirmaInSostSindaco();
				} else {
					// siccome non riesco a replicare il controllo lato server della presenza o meno del check flgFirmaInSostSindaco (non mi arriva il valore di desConsigliere lato dataSource) quando non è visibile lo resetto a false
					mDynamicForm.setValue("flgConsigliereFirmaInSostSindaco", false);
				}
				return false;
			}
		});
					
		mDynamicForm.setFields(consigliereItem, consigliereFromLoadDettHiddenItem, desConsigliereHiddenItem, flgConsigliereFirmatarioItem, flgConsigliereFirmaInSostSindacoItem);	
		
		mDynamicForm.setNumCols(5);
		
		addChild(mDynamicForm);
		
	}

	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[]{mDynamicForm};
	}
	
	@Override
	public void editRecord(Record record) {
		
		manageLoadSelectInEditRecord(record, consigliereItem, "consigliere", new String[]{"desConsigliere"}, "key");
		super.editRecord(record);				
	}
	
	@Override
	public void setCanEdit(Boolean canEdit) {
		super.setCanEdit(canEdit);
		flgConsigliereFirmaInSostSindacoItem.setCanEdit(((ConsiglieriItem)getItem()).isEditableFlgFirmaInSostSindaco() ? canEdit : false);
	}
	
}
