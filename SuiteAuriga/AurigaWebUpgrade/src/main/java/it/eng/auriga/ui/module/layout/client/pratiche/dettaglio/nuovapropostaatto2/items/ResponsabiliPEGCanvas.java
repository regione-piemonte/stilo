/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.grid.ListGridField;

import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;
import it.eng.utility.ui.module.layout.client.common.items.FilteredSelectItemWithDisplay;

public class ResponsabiliPEGCanvas extends ReplicableCanvas {
	
	private FilteredSelectItemWithDisplay responsabilePEGItem;
	private HiddenItem responsabilePEGFromLoadDettHiddenItem;
	private HiddenItem codUoResponsabilePEGHiddenItem;
	private HiddenItem desResponsabilePEGHiddenItem;
	
	private ReplicableCanvasForm mDynamicForm;

	public ResponsabiliPEGCanvas(ReplicableItem item) {
		super(item);
	}

	@Override
	public void disegna() {
		
		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);
		
		SelectGWTRestDataSource responsabilePEGDS = new SelectGWTRestDataSource("LoadComboResponsabiliPEGDataSource", "idSv", FieldType.TEXT, new String[]{"codUo", "descrizione"}, true);
		if(((ResponsabiliPEGItem)getItem()).getAltriParamLoadCombo() != null) {
			responsabilePEGDS.addParam("altriParamLoadCombo", ((ResponsabiliPEGItem)getItem()).getAltriParamLoadCombo());
		}
		
		responsabilePEGItem = new FilteredSelectItemWithDisplay("responsabilePEG", responsabilePEGDS) {

//			@Override
//			protected ListGrid builPickListProperties() {
//				ListGrid responsabilePEGPickListProperties = super.builPickListProperties();				
//				responsabilePEGPickListProperties.addFetchDataHandler(new FetchDataHandler() {
//
//					@Override
//					public void onFilterData(FetchDataEvent event) {
//						GWTRestDataSource responsabilePEGDS = (GWTRestDataSource) responsabilePEGItem.getOptionDataSource();
//						responsabilePEGDS.addParam("idSv", (String) responsabilePEGFromLoadDettHiddenItem.getValue());
//						responsabilePEGItem.setOptionDataSource(responsabilePEGDS);
//						responsabilePEGItem.invalidateDisplayValueCache();
//					}
//				});
//				return responsabilePEGPickListProperties;
//			}
			
			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);
				mDynamicForm.setValue("codUoResponsabilePEG", record.getAttributeAsString("codUo"));
				mDynamicForm.setValue("desResponsabilePEG", record.getAttributeAsString("descrizione"));
			}

			@Override
			protected void clearSelect() {
				super.clearSelect();
				mDynamicForm.setValue("responsabilePEG", "");
				mDynamicForm.setValue("codUoResponsabilePEG", "");
				mDynamicForm.setValue("desResponsabilePEG", "");
			};

			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					mDynamicForm.setValue("responsabilePEG", "");
					mDynamicForm.setValue("codUoResponsabilePEG", "");
					mDynamicForm.setValue("desResponsabilePEG", "");
				}
			}
		};
		responsabilePEGItem.setShowTitle(false);
		responsabilePEGItem.setStartRow(true);
		responsabilePEGItem.setWidth(650);
		responsabilePEGItem.setValueField("idSv");
		ListGridField codUoField = new ListGridField("codUo", "Cod. U.O.");
		codUoField.setWidth(120);	
		codUoField.setCanFilter(false);
		ListGridField descrizioneField = new ListGridField("descrizione", "Descrizione");
		responsabilePEGItem.setPickListFields(codUoField, descrizioneField);	
		responsabilePEGItem.setAllowEmptyValue(false);
		responsabilePEGItem.setAutoFetchData(false);
		responsabilePEGItem.setAlwaysFetchMissingValues(true);
		responsabilePEGItem.setFetchMissingValues(true);
		responsabilePEGItem.setRequired(true);
		
		responsabilePEGFromLoadDettHiddenItem = new HiddenItem("responsabilePEGFromLoadDett");
		codUoResponsabilePEGHiddenItem = new HiddenItem("codUoResponsabilePEG");
		desResponsabilePEGHiddenItem = new HiddenItem("desResponsabilePEG");
					
		mDynamicForm.setFields(responsabilePEGItem, responsabilePEGFromLoadDettHiddenItem, codUoResponsabilePEGHiddenItem, desResponsabilePEGHiddenItem);	
		
		mDynamicForm.setNumCols(5);
		
		addChild(mDynamicForm);
		
	}

	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[]{mDynamicForm};
	}
	
	@Override
	public void editRecord(Record record) {
		
		manageLoadSelectInEditRecord(record, responsabilePEGItem, "responsabilePEG", new String[]{"codUoResponsabilePEG", "desResponsabilePEG"}, "idSv");
		super.editRecord(record);				
	}
	
}
