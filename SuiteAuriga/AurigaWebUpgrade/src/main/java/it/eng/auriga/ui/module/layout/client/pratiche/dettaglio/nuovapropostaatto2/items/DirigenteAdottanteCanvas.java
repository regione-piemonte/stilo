/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.events.FetchDataEvent;
import com.smartgwt.client.widgets.events.FetchDataHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.FilteredSelectItemWithDisplay;

public class DirigenteAdottanteCanvas extends ReplicableCanvas {
	
	protected FilteredSelectItemWithDisplay dirigenteAdottanteItem;
	protected HiddenItem dirigenteAdottanteFromLoadDettHiddenItem;
	protected HiddenItem codUoDirigenteAdottanteHiddenItem;
	protected HiddenItem desDirigenteAdottanteHiddenItem;
	protected CheckboxItem flgAdottanteAncheRdPItem;
	protected CheckboxItem flgAdottanteAncheRUPItem;
	
	private ReplicableCanvasForm mDynamicForm;
	
	public DirigenteAdottanteCanvas(ReplicableItem item) {
		super(item);
	}

	@Override
	public void disegna() {
		
		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);
		
		SelectGWTRestDataSource dirigenteAdottanteDS = new SelectGWTRestDataSource("LoadComboDirigenteAdottanteDataSource", "idSv", FieldType.TEXT, new String[]{"codUo", "descrizione"}, true);
		if(((DirigenteAdottanteItem)getItem()).getIdUdAttoDaAnn() != null) {
			dirigenteAdottanteDS.addParam("idUdAttoDaAnn", ((DirigenteAdottanteItem)getItem()).getIdUdAttoDaAnn());
		}
		if(((DirigenteAdottanteItem)getItem()).getAltriParamLoadCombo() != null) {
			dirigenteAdottanteDS.addParam("altriParamLoadCombo", ((DirigenteAdottanteItem)getItem()).getAltriParamLoadCombo());
		}
		
		dirigenteAdottanteItem = new FilteredSelectItemWithDisplay("dirigenteAdottante", dirigenteAdottanteDS) {
			
			@Override
			protected ListGrid builPickListProperties() {
				ListGrid dirigenteAdottantePickListProperties = super.builPickListProperties();
				if(dirigenteAdottantePickListProperties == null) {
					dirigenteAdottantePickListProperties = new ListGrid();
				}
				if(((DirigenteAdottanteItem)getItem()).getIdUdAttoDaAnn() != null) {
					dirigenteAdottantePickListProperties.setShowFilterEditor(false);					
				}
				dirigenteAdottantePickListProperties.addFetchDataHandler(new FetchDataHandler() {

					@Override
					public void onFilterData(FetchDataEvent event) {
						GWTRestDataSource dirigenteAdottanteDS = (GWTRestDataSource) dirigenteAdottanteItem.getOptionDataSource();
						dirigenteAdottanteDS.addParam("uoProponente", ((DirigenteAdottanteItem)getItem()).getUoProponenteCorrente());
//						dirigenteAdottanteDS.addParam("idSv", (String) dirigenteAdottanteFromLoadDettHiddenItem.getValue());
						dirigenteAdottanteItem.setOptionDataSource(dirigenteAdottanteDS);
						dirigenteAdottanteItem.invalidateDisplayValueCache();
					}
				});
				return dirigenteAdottantePickListProperties;
			}
			
			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);
				mDynamicForm.setValue("codUoDirigenteAdottante", record.getAttributeAsString("codUo"));
				mDynamicForm.setValue("desDirigenteAdottante", record.getAttributeAsString("descrizione"));
			}

			@Override
			protected void clearSelect() {
				super.clearSelect();
				mDynamicForm.setValue("dirigenteAdottante", "");
				mDynamicForm.setValue("codUoDirigenteAdottante", "");
				mDynamicForm.setValue("desDirigenteAdottante", "");
			};

			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					mDynamicForm.setValue("dirigenteAdottante", "");
					mDynamicForm.setValue("codUoDirigenteAdottante", "");
					mDynamicForm.setValue("desDirigenteAdottante", "");
				}
			}
		};
		dirigenteAdottanteItem.setShowTitle(false);
		dirigenteAdottanteItem.setStartRow(true);
		dirigenteAdottanteItem.setWidth(650);
		dirigenteAdottanteItem.setValueField("idSv");
		ListGridField codUoField = new ListGridField("codUo", "Cod. U.O.");
		codUoField.setWidth(120);	
		codUoField.setCanFilter(false);
		ListGridField descrizioneField = new ListGridField("descrizione", "Descrizione");
		dirigenteAdottanteItem.setPickListFields(codUoField, descrizioneField);
		dirigenteAdottanteItem.setAllowEmptyValue(false);
		dirigenteAdottanteItem.setAutoFetchData(false);
		dirigenteAdottanteItem.setAlwaysFetchMissingValues(true);
		dirigenteAdottanteItem.setFetchMissingValues(true);
		dirigenteAdottanteItem.setEmptyPickListMessage(I18NUtil.getMessages().nuovaPropostaAtto2_detail_selectDependsFromUOProponentePickList_emptyMessage());
		dirigenteAdottanteItem.setRequired(true);		

		dirigenteAdottanteFromLoadDettHiddenItem = new HiddenItem("dirigenteAdottanteFromLoadDett");		
		codUoDirigenteAdottanteHiddenItem = new HiddenItem("codUoDirigenteAdottante");
		desDirigenteAdottanteHiddenItem = new HiddenItem("desDirigenteAdottante");

		flgAdottanteAncheRdPItem = new CheckboxItem("flgAdottanteAncheRdP", I18NUtil.getMessages().nuovaPropostaAtto2_detail_flgAncheRdP_title());
		flgAdottanteAncheRdPItem.setDefaultValue(false);
		flgAdottanteAncheRdPItem.setColSpan(1);
		flgAdottanteAncheRdPItem.setWidth("*");
		flgAdottanteAncheRdPItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				Boolean value = (Boolean) event.getValue();
				((DirigenteAdottanteItem)getItem()).manageOnChangedFlgAdottanteAncheRdP(value != null && value);
			}
		});
		flgAdottanteAncheRdPItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(AurigaLayout.getParametroDBAsBoolean("FORZA_RDP_UGUALE_ADOTTANTE")) {
					mDynamicForm.setValue("flgAdottanteAncheRdP", true);
					flgAdottanteAncheRdPItem.setCanEdit(false);
				}
				return ((DirigenteAdottanteItem)getItem()).showFlgAncheRdP() && ((DirigenteAdottanteItem)getItem()).getIdUdAttoDaAnn() == null;
			}
		});
		
		flgAdottanteAncheRUPItem = new CheckboxItem("flgAdottanteAncheRUP", I18NUtil.getMessages().nuovaPropostaAtto2_detail_flgAncheRUP_title());
		flgAdottanteAncheRUPItem.setDefaultValue(false);
		flgAdottanteAncheRUPItem.setColSpan(1);
		flgAdottanteAncheRUPItem.setWidth("*");
		flgAdottanteAncheRUPItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				Boolean value = (Boolean) event.getValue();
				((DirigenteAdottanteItem)getItem()).manageOnChangedFlgAdottanteAncheRUP(value != null && value);
			}
		});
		flgAdottanteAncheRUPItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(AurigaLayout.getParametroDBAsBoolean("FORZA_RUP_UGUALE_ADOTTANTE")) {
					mDynamicForm.setValue("flgAdottanteAncheRUP", true);
					flgAdottanteAncheRUPItem.setCanEdit(false);
				}
				return ((DirigenteAdottanteItem)getItem()).showFlgAncheRUP() && ((DirigenteAdottanteItem)getItem()).getIdUdAttoDaAnn() == null;
			}
		});
		
		mDynamicForm.setFields(dirigenteAdottanteItem, dirigenteAdottanteFromLoadDettHiddenItem, codUoDirigenteAdottanteHiddenItem, desDirigenteAdottanteHiddenItem, flgAdottanteAncheRdPItem, flgAdottanteAncheRUPItem);	
		
		mDynamicForm.setNumCols(5);
		
		addChild(mDynamicForm);
		
	}
	
	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[]{mDynamicForm};
	}
	
	public void resetAfterChangedParams() {
		final String value = dirigenteAdottanteItem.getValueAsString();
		dirigenteAdottanteItem.fetchData(new DSCallback() {

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
						mDynamicForm.setValue("dirigenteAdottante", "");
						mDynamicForm.setValue("codUoDirigenteAdottante", "");
						mDynamicForm.setValue("desDirigenteAdottante", "");						
					}
				}
			}
		});
	}
	
	@Override
	public void editRecord(Record record) {
		manageLoadSelectInEditRecord(record, dirigenteAdottanteItem, "dirigenteAdottanteFromLoadDett", new String[]{"codUoDirigenteAdottante", "desDirigenteAdottante"}, "idSv");
		super.editRecord(record);					
	}	
	
}
