/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.grid.ListGridField;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.FilteredSelectItemWithDisplay;

public class AltriDirRespRegTecnicaCanvas extends ReplicableCanvas {
	
	private FilteredSelectItemWithDisplay dirigenteRespRegTecnicaItem;
	private HiddenItem dirigenteRespRegTecnicaFromLoadDettHiddenItem;
	private HiddenItem codUoDirigenteRespRegTecnicaHiddenItem;
	private HiddenItem desDirigenteRespRegTecnicaHiddenItem;
	private CheckboxItem flgDirigenteRespRegTecnicaFirmatarioItem;

	private ReplicableCanvasForm mDynamicForm;

	public AltriDirRespRegTecnicaCanvas(ReplicableItem item) {
		super(item);
	}
	
	@Override
	public void disegna() {
		
		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);
		
		SelectGWTRestDataSource dirigenteRespRegTecnicaDS = new SelectGWTRestDataSource("LoadComboDirigenteDataSource", "idSv", FieldType.TEXT, new String[]{"codUo", "descrizione"}, true);
		if(((AltriDirRespRegTecnicaItem)getItem()).getAltriParamLoadCombo() != null) {
			dirigenteRespRegTecnicaDS.addParam("altriParamLoadCombo", ((AltriDirRespRegTecnicaItem)getItem()).getAltriParamLoadCombo());
		}
		
		dirigenteRespRegTecnicaItem = new FilteredSelectItemWithDisplay("dirigenteRespRegTecnica", dirigenteRespRegTecnicaDS) {
			
//			@Override
//			protected ListGrid builPickListProperties() {
//				ListGrid dirigenteRespRegTecnicaPickListProperties = super.builPickListProperties();				
//				dirigenteRespRegTecnicaPickListProperties.addFetchDataHandler(new FetchDataHandler() {
//
//					@Override
//					public void onFilterData(FetchDataEvent event) {
//						GWTRestDataSource dirigenteRespRegTecnicaDS = (GWTRestDataSource) dirigenteRespRegTecnicaItem.getOptionDataSource();
//						dirigenteRespRegTecnicaDS.addParam("idSv", (String) dirigenteRespRegTecnicaFromLoadDettHiddenItem.getValue());
//						dirigenteRespRegTecnicaItem.setOptionDataSource(dirigenteRespRegTecnicaDS);
//						dirigenteRespRegTecnicaItem.invalidateDisplayValueCache();
//					}
//				});
//				return dirigenteRespRegTecnicaPickListProperties;
//			}
			
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
		dirigenteRespRegTecnicaItem.setRequired(true);
				
		dirigenteRespRegTecnicaFromLoadDettHiddenItem = new HiddenItem("dirigenteRespRegTecnicaFromLoadDett");
		codUoDirigenteRespRegTecnicaHiddenItem = new HiddenItem("codUoDirigenteRespRegTecnica");
		desDirigenteRespRegTecnicaHiddenItem = new HiddenItem("desDirigenteRespRegTecnica");
		
		flgDirigenteRespRegTecnicaFirmatarioItem = new CheckboxItem("flgDirigenteRespRegTecnicaFirmatario", I18NUtil.getMessages().nuovaPropostaAtto2_detail_flgFirmatario_title());
		flgDirigenteRespRegTecnicaFirmatarioItem.setDefaultValue(false);
		flgDirigenteRespRegTecnicaFirmatarioItem.setColSpan(1);
		flgDirigenteRespRegTecnicaFirmatarioItem.setWidth("*");
		flgDirigenteRespRegTecnicaFirmatarioItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return ((AltriDirRespRegTecnicaItem)getItem()).showFlgFirmatario();
			}
		});
				
		mDynamicForm.setFields(dirigenteRespRegTecnicaItem, dirigenteRespRegTecnicaFromLoadDettHiddenItem, codUoDirigenteRespRegTecnicaHiddenItem, desDirigenteRespRegTecnicaHiddenItem, flgDirigenteRespRegTecnicaFirmatarioItem);	
		
		mDynamicForm.setNumCols(5);
		
		addChild(mDynamicForm);
		
	}

	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[]{mDynamicForm};
	}
	
	@Override
	public void editRecord(Record record) {
		
		manageLoadSelectInEditRecord(record, dirigenteRespRegTecnicaItem, "dirigenteRespRegTecnica", new String[]{"codUoDirigenteRespRegTecnica", "desDirigenteRespRegTecnica"}, "idSv");
		super.editRecord(record);					
	}	
	
}
