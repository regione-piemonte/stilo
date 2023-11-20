/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.events.FetchDataEvent;
import com.smartgwt.client.widgets.events.FetchDataHandler;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;
import it.eng.utility.ui.module.layout.client.common.items.FilteredSelectItemWithDisplay;

public class EstensoreCanvas extends ReplicableCanvas {
	
	private FilteredSelectItemWithDisplay estensoreItem;
	private HiddenItem estensoreFromLoadDettHiddenItem;
	private HiddenItem codUoEstensoreHiddenItem;
	private HiddenItem desEstensoreHiddenItem;
	
	private ReplicableCanvasForm mDynamicForm;

	public EstensoreCanvas(ReplicableItem item) {
		super(item);
	}

	@Override
	public void disegna() {
		
		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);
		
		SelectGWTRestDataSource estensoreDS = new SelectGWTRestDataSource("LoadComboEstensoreDataSource", "idSv", FieldType.TEXT, new String[]{"codUo", "descrizione"}, true);
		if(((EstensoreItem)getItem()).getAltriParamLoadCombo() != null) {
			estensoreDS.addParam("altriParamLoadCombo", ((EstensoreItem)getItem()).getAltriParamLoadCombo());
		}
		
		estensoreItem = new FilteredSelectItemWithDisplay("estensore", estensoreDS) {
			
			@Override
			protected ListGrid builPickListProperties() {
				ListGrid estensorePickListProperties = super.builPickListProperties();
				if(estensorePickListProperties == null) {
					estensorePickListProperties = new ListGrid();
				}
				estensorePickListProperties.addFetchDataHandler(new FetchDataHandler() {

					@Override
					public void onFilterData(FetchDataEvent event) {
						GWTRestDataSource estensoreDS = (GWTRestDataSource) estensoreItem.getOptionDataSource();
						estensoreDS.addParam("uoProponente", ((EstensoreItem)getItem()).getUoProponenteCorrente());
//						estensoreDS.addParam("idSv", (String) estensoreFromLoadDettHiddenItem.getValue());
						estensoreItem.setOptionDataSource(estensoreDS);
						estensoreItem.invalidateDisplayValueCache();
					}
				});
				return estensorePickListProperties;
			}
			
			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);
				mDynamicForm.setValue("codUoEstensore", record.getAttributeAsString("codUo"));
				mDynamicForm.setValue("desEstensore", record.getAttributeAsString("descrizione"));
			}

			@Override
			protected void clearSelect() {
				super.clearSelect();
				mDynamicForm.setValue("estensore", "");
				mDynamicForm.setValue("codUoEstensore", "");
				mDynamicForm.setValue("desEstensore", "");
			};

			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					mDynamicForm.setValue("estensore", "");
					mDynamicForm.setValue("codUoEstensore", "");
					mDynamicForm.setValue("desEstensore", "");
				}
			}
		};
		estensoreItem.setShowTitle(false);
		estensoreItem.setStartRow(true);
		estensoreItem.setWidth(650);
		estensoreItem.setValueField("idSv");
		ListGridField codUoField = new ListGridField("codUo", "Cod. U.O.");
		codUoField.setWidth(120);	
		codUoField.setCanFilter(false);
		ListGridField descrizioneField = new ListGridField("descrizione", "Descrizione");
		estensoreItem.setPickListFields(codUoField, descrizioneField);	
		estensoreItem.setAllowEmptyValue(false);
		estensoreItem.setAutoFetchData(false);
		estensoreItem.setAlwaysFetchMissingValues(true);
		estensoreItem.setFetchMissingValues(true);
		estensoreItem.setRequired(true);
		
		estensoreFromLoadDettHiddenItem = new HiddenItem("estensoreFromLoadDett");		
		codUoEstensoreHiddenItem = new HiddenItem("codUoEstensore");
		desEstensoreHiddenItem = new HiddenItem("desEstensore");
					
		mDynamicForm.setFields(estensoreItem, estensoreFromLoadDettHiddenItem, codUoEstensoreHiddenItem, desEstensoreHiddenItem);	
		
		mDynamicForm.setNumCols(5);
		
		addChild(mDynamicForm);
		
	}
	
	public void resetAfterChangedParams() {
		final String value = estensoreItem.getValueAsString();
		estensoreItem.fetchData(new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				RecordList data = response.getDataAsRecordList();
				if(value != null && !"".equals(value)) {
					boolean trovato = false;
					if (data.getLength() > 0) {
						for (int i = 0; i < data.getLength(); i++) {
							String idSv = data.get(i).getAttribute("idSv");
							if (value.equals(idSv)) {
								trovato = true;
								break;
							}
						}
					}
					if (!trovato) {
						mDynamicForm.setValue("estensore", "");
						mDynamicForm.setValue("codUoEstensore", "");
						mDynamicForm.setValue("desEstensore", "");
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
		
		manageLoadSelectInEditRecord(record, estensoreItem, "estensore", new String[]{"codUoEstensore", "desEstensore"}, "idSv");
		super.editRecord(record);				
	}
	
}