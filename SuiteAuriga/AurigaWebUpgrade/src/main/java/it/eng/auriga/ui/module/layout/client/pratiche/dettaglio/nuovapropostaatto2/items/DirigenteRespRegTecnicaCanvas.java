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

public class DirigenteRespRegTecnicaCanvas extends ReplicableCanvas {
	
	protected FilteredSelectItemWithDisplay dirigenteRespRegTecnicaItem;
	protected HiddenItem dirigenteRespRegTecnicaFromLoadDettHiddenItem;
	protected HiddenItem codUoDirigenteRespRegTecnicaHiddenItem;
	protected HiddenItem desDirigenteRespRegTecnicaHiddenItem;
	protected CheckboxItem flgDirRespRegTecnicaAncheRdPItem;
	protected CheckboxItem flgDirRespRegTecnicaAncheRUPItem;
		
	private ReplicableCanvasForm mDynamicForm;
	
	public DirigenteRespRegTecnicaCanvas(ReplicableItem item) {
		super(item);
	}

	@Override
	public void disegna() {
		
		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);
		
		SelectGWTRestDataSource dirigenteRespRegTecnicaDS = new SelectGWTRestDataSource("LoadComboDirigenteAdottanteDataSource", "idSv", FieldType.TEXT, new String[]{"codUo", "descrizione"}, true);
		if(((DirigenteRespRegTecnicaItem)getItem()).getIdUdAttoDaAnn() != null) {
			dirigenteRespRegTecnicaDS.addParam("idUdAttoDaAnn", ((DirigenteRespRegTecnicaItem)getItem()).getIdUdAttoDaAnn());
		}
		if(((DirigenteRespRegTecnicaItem)getItem()).getAltriParamLoadCombo() != null) {
			dirigenteRespRegTecnicaDS.addParam("altriParamLoadCombo", ((DirigenteRespRegTecnicaItem)getItem()).getAltriParamLoadCombo());
		}
		
		dirigenteRespRegTecnicaItem = new FilteredSelectItemWithDisplay("dirigenteRespRegTecnica", dirigenteRespRegTecnicaDS) {
			
			@Override
			protected ListGrid builPickListProperties() {
				ListGrid dirigenteRespRegTecnicaPickListProperties = super.builPickListProperties();
				if(dirigenteRespRegTecnicaPickListProperties == null) {
					dirigenteRespRegTecnicaPickListProperties = new ListGrid();
				}
				if(((DirigenteRespRegTecnicaItem)getItem()).getIdUdAttoDaAnn() != null) {
					dirigenteRespRegTecnicaPickListProperties.setShowFilterEditor(false);					
				}
				dirigenteRespRegTecnicaPickListProperties.addFetchDataHandler(new FetchDataHandler() {

					@Override
					public void onFilterData(FetchDataEvent event) {
						GWTRestDataSource dirigenteRespRegTecnicaDS = (GWTRestDataSource) dirigenteRespRegTecnicaItem.getOptionDataSource();
						dirigenteRespRegTecnicaDS.addParam("uoProponente", ((DirigenteRespRegTecnicaItem)getItem()).getUoProponenteCorrente());
//						dirigenteRespRegTecnicaDS.addParam("idSv", (String) dirigenteRespRegTecnicaFromLoadDettHiddenItem.getValue());
						dirigenteRespRegTecnicaItem.setOptionDataSource(dirigenteRespRegTecnicaDS);
						dirigenteRespRegTecnicaItem.invalidateDisplayValueCache();
					}
				});
				return dirigenteRespRegTecnicaPickListProperties;
			}
			
			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);
				mDynamicForm.setValue("codUoDirigenteRespRegTecnica", record.getAttributeAsString("codUo"));
				mDynamicForm.setValue("desDirigenteRespRegTecnica", record.getAttributeAsString("descrizione"));		
			}

			@Override
			protected void clearSelect() {
				super.clearSelect();
				mDynamicForm.setValue("dirigenteRespRegTecnica", "");
				mDynamicForm.setValue("codUoDirigenteRespRegTecnica", "");
				mDynamicForm.setValue("desDirigenteRespRegTecnica", "");				
			};

			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					mDynamicForm.setValue("dirigenteRespRegTecnica", "");
					mDynamicForm.setValue("codUoDirigenteRespRegTecnica", "");
					mDynamicForm.setValue("desDirigenteRespRegTecnica", "");		
				}
			}
		};
		dirigenteRespRegTecnicaItem.setShowTitle(false);
		dirigenteRespRegTecnicaItem.setStartRow(true);
		dirigenteRespRegTecnicaItem.setWidth(650);
		dirigenteRespRegTecnicaItem.setValueField("idSv");
		ListGridField codUoField = new ListGridField("codUo", "Cod. U.O.");
		codUoField.setWidth(120);	
		codUoField.setCanFilter(false);
		ListGridField descrizioneField = new ListGridField("descrizione", "Descrizione");
		dirigenteRespRegTecnicaItem.setPickListFields(codUoField, descrizioneField);
		dirigenteRespRegTecnicaItem.setAllowEmptyValue(false);
		dirigenteRespRegTecnicaItem.setAutoFetchData(false);
		dirigenteRespRegTecnicaItem.setAlwaysFetchMissingValues(true);
		dirigenteRespRegTecnicaItem.setFetchMissingValues(true);
		dirigenteRespRegTecnicaItem.setEmptyPickListMessage(I18NUtil.getMessages().nuovaPropostaAtto2_detail_selectDependsFromUOProponentePickList_emptyMessage());
		dirigenteRespRegTecnicaItem.setRequired(true);		

		dirigenteRespRegTecnicaFromLoadDettHiddenItem = new HiddenItem("dirigenteRespRegTecnicaFromLoadDett");		
		codUoDirigenteRespRegTecnicaHiddenItem = new HiddenItem("codUoDirigenteRespRegTecnica");
		desDirigenteRespRegTecnicaHiddenItem = new HiddenItem("desDirigenteRespRegTecnica");

		flgDirRespRegTecnicaAncheRdPItem = new CheckboxItem("flgDirRespRegTecnicaAncheRdP", I18NUtil.getMessages().nuovaPropostaAtto2_detail_flgAncheRdP_title());
		flgDirRespRegTecnicaAncheRdPItem.setDefaultValue(false);
		flgDirRespRegTecnicaAncheRdPItem.setColSpan(1);
		flgDirRespRegTecnicaAncheRdPItem.setWidth("*");
		flgDirRespRegTecnicaAncheRdPItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				Boolean value = (Boolean) event.getValue();
				((DirigenteRespRegTecnicaItem)getItem()).manageOnChangedFlgDirRespRegTecnicaAncheRdP(value != null && value);
			}
		});
		flgDirRespRegTecnicaAncheRdPItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(AurigaLayout.getParametroDBAsBoolean("FORZA_RDP_UGUALE_DIR")) {
					mDynamicForm.setValue("flgDirRespRegTecnicaAncheRdP", true);
					flgDirRespRegTecnicaAncheRdPItem.setCanEdit(false);
				}
				return ((DirigenteRespRegTecnicaItem)getItem()).showFlgAncheRdP() && ((DirigenteRespRegTecnicaItem)getItem()).getIdUdAttoDaAnn() == null;
			}
		});
		
		flgDirRespRegTecnicaAncheRUPItem = new CheckboxItem("flgDirRespRegTecnicaAncheRUP", I18NUtil.getMessages().nuovaPropostaAtto2_detail_flgAncheRUP_title());
		flgDirRespRegTecnicaAncheRUPItem.setDefaultValue(false);
		flgDirRespRegTecnicaAncheRUPItem.setColSpan(1);
		flgDirRespRegTecnicaAncheRUPItem.setWidth("*");
		flgDirRespRegTecnicaAncheRUPItem.addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				Boolean value = (Boolean) event.getValue();
				((DirigenteRespRegTecnicaItem)getItem()).manageOnChangedFlgDirRespRegTecnicaAncheRUP(value != null && value);
			}
		});
		flgDirRespRegTecnicaAncheRUPItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(AurigaLayout.getParametroDBAsBoolean("FORZA_RUP_UGUALE_DIR")) {
					mDynamicForm.setValue("flgDirRespRegTecnicaAncheRUP", true);
					flgDirRespRegTecnicaAncheRUPItem.setCanEdit(false);
				}
				return ((DirigenteRespRegTecnicaItem)getItem()).showFlgAncheRUP() && ((DirigenteRespRegTecnicaItem)getItem()).getIdUdAttoDaAnn() == null;
			}
		});
		
		mDynamicForm.setFields(dirigenteRespRegTecnicaItem, dirigenteRespRegTecnicaFromLoadDettHiddenItem, codUoDirigenteRespRegTecnicaHiddenItem, desDirigenteRespRegTecnicaHiddenItem, flgDirRespRegTecnicaAncheRdPItem, flgDirRespRegTecnicaAncheRUPItem);	
		
		mDynamicForm.setNumCols(5);
		
		addChild(mDynamicForm);
		
	}
	
	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[]{mDynamicForm};
	}
	
	public void resetAfterChangedParams() {
		final String value = dirigenteRespRegTecnicaItem.getValueAsString();
		dirigenteRespRegTecnicaItem.fetchData(new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				RecordList data = response.getDataAsRecordList();
				if(((DirigenteRespRegTecnicaItem)getItem()).selectUniqueValueAfterChangedParams() && data.getLength() == 1) {
					if(value == null || "".equals(value) || !value.equals(data.get(0).getAttributeAsString("idSv"))) {	
						mDynamicForm.setValue("dirigenteRespRegTecnica", data.get(0).getAttribute("idSv"));
						mDynamicForm.setValue("codUoDirigenteRespRegTecnica", data.get(0).getAttribute("codUo"));												
						mDynamicForm.setValue("desDirigenteRespRegTecnica", data.get(0).getAttribute("descrizione"));
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
						mDynamicForm.setValue("dirigenteRespRegTecnica", "");
						mDynamicForm.setValue("codUoDirigenteRespRegTecnica", "");
						mDynamicForm.setValue("desDirigenteRespRegTecnica", "");		
					}
				}
			}
		});
	}
		
	@Override
	public void editRecord(Record record) {
		manageLoadSelectInEditRecord(record, dirigenteRespRegTecnicaItem, "dirigenteRespRegTecnicaFromLoadDett", new String[]{"codUoDirigenteRespRegTecnica", "desDirigenteRespRegTecnica"}, "idSv");
		super.editRecord(record);					
	}	
	
}
