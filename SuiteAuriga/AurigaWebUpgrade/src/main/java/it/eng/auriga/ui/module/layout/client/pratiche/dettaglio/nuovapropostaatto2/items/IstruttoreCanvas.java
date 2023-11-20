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

public class IstruttoreCanvas extends ReplicableCanvas {
	
	private FilteredSelectItemWithDisplay istruttoreItem;
	private HiddenItem istruttoreFromLoadDettHiddenItem;
	private HiddenItem codUoIstruttoreHiddenItem;
	private HiddenItem desIstruttoreHiddenItem;
	
	private ReplicableCanvasForm mDynamicForm;

	public IstruttoreCanvas(ReplicableItem item) {
		super(item);
	}

	@Override
	public void disegna() {
		
		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);
		
		SelectGWTRestDataSource istruttoreDS = new SelectGWTRestDataSource("LoadComboIstruttoreDataSource", "idSv", FieldType.TEXT, new String[]{"codUo", "descrizione"}, true);
		if(((IstruttoreItem)getItem()).getAltriParamLoadCombo() != null) {
			istruttoreDS.addParam("altriParamLoadCombo", ((IstruttoreItem)getItem()).getAltriParamLoadCombo());
		}
		
		istruttoreItem = new FilteredSelectItemWithDisplay("istruttore", istruttoreDS) {
			
			@Override
			protected ListGrid builPickListProperties() {
				ListGrid istruttorePickListProperties = super.builPickListProperties();
				if(istruttorePickListProperties == null) {
					istruttorePickListProperties = new ListGrid();
				}
				istruttorePickListProperties.addFetchDataHandler(new FetchDataHandler() {

					@Override
					public void onFilterData(FetchDataEvent event) {
						GWTRestDataSource istruttoreDS = (GWTRestDataSource) istruttoreItem.getOptionDataSource();
						istruttoreDS.addParam("uoProponente", ((IstruttoreItem)getItem()).getUoProponenteCorrente());
//						istruttoreDS.addParam("idSv", (String) istruttoreFromLoadDettHiddenItem.getValue());
						istruttoreItem.setOptionDataSource(istruttoreDS);
						istruttoreItem.invalidateDisplayValueCache();
					}
				});
				return istruttorePickListProperties;
			}
			
			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);
				mDynamicForm.setValue("codUoIstruttore", record.getAttributeAsString("codUo"));
				mDynamicForm.setValue("desIstruttore", record.getAttributeAsString("descrizione"));
			}

			@Override
			protected void clearSelect() {
				super.clearSelect();
				mDynamicForm.setValue("istruttore", "");
				mDynamicForm.setValue("codUoIstruttore", "");
				mDynamicForm.setValue("desIstruttore", "");
			};

			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					mDynamicForm.setValue("istruttore", "");
					mDynamicForm.setValue("codUoIstruttore", "");
					mDynamicForm.setValue("desIstruttore", "");
				}
			}
		};
		istruttoreItem.setShowTitle(false);
		istruttoreItem.setStartRow(true);
		istruttoreItem.setWidth(650);
		istruttoreItem.setValueField("idSv");
		ListGridField codUoField = new ListGridField("codUo", "Cod. U.O.");
		codUoField.setWidth(120);	
		codUoField.setCanFilter(false);
		ListGridField descrizioneField = new ListGridField("descrizione", "Descrizione");
		istruttoreItem.setPickListFields(codUoField, descrizioneField);	
		istruttoreItem.setAllowEmptyValue(false);
		istruttoreItem.setAutoFetchData(false);
		istruttoreItem.setAlwaysFetchMissingValues(true);
		istruttoreItem.setFetchMissingValues(true);
		istruttoreItem.setRequired(true);
		
		istruttoreFromLoadDettHiddenItem = new HiddenItem("istruttoreFromLoadDett");		
		codUoIstruttoreHiddenItem = new HiddenItem("codUoIstruttore");
		desIstruttoreHiddenItem = new HiddenItem("desIstruttore");
					
		mDynamicForm.setFields(istruttoreItem, istruttoreFromLoadDettHiddenItem, codUoIstruttoreHiddenItem, desIstruttoreHiddenItem);	
		
		mDynamicForm.setNumCols(5);
		
		addChild(mDynamicForm);
		
	}
	
	public void resetAfterChangedParams() {
		final String value = istruttoreItem.getValueAsString();
		istruttoreItem.fetchData(new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {				
				RecordList data = response.getDataAsRecordList();
				if(((IstruttoreItem)getItem()).selectUniqueValueAfterChangedParams() && data.getLength() == 1) {
					if(value == null || "".equals(value) || !value.equals(data.get(0).getAttributeAsString("idSv"))) {	
						mDynamicForm.setValue("istruttore", data.get(0).getAttribute("idSv"));
						mDynamicForm.setValue("codUoIstruttore", data.get(0).getAttribute("codUo"));																								
						mDynamicForm.setValue("desIstruttore", data.get(0).getAttribute("descrizione"));
					}
				} else if(value != null && !"".equals(value)) {
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
						mDynamicForm.setValue("istruttore", "");
						mDynamicForm.setValue("codUoIstruttore", "");
						mDynamicForm.setValue("desIstruttore", "");
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
		
		manageLoadSelectInEditRecord(record, istruttoreItem, "istruttore", new String[]{"codUoIstruttore", "desIstruttore"}, "idSv");
		super.editRecord(record);				
	}
	
}