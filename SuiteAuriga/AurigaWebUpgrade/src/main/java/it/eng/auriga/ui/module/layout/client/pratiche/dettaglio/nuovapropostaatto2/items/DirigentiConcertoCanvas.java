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

public class DirigentiConcertoCanvas extends ReplicableCanvas {
	
	private FilteredSelectItemWithDisplay dirigenteConcertoItem;
	private HiddenItem dirigenteConcertoFromLoadDettHiddenItem;
	private HiddenItem codUoDirigenteConcertoHiddenItem;
	private HiddenItem desDirigenteConcertoHiddenItem;
	private CheckboxItem flgDirigenteConcertoFirmatarioItem;

	private ReplicableCanvasForm mDynamicForm;

	public DirigentiConcertoCanvas(ReplicableItem item) {
		super(item);
	}
	
	@Override
	public void disegna() {
		
		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);
		
		SelectGWTRestDataSource dirigenteConcertoDS = new SelectGWTRestDataSource("LoadComboDirigenteDataSource", "idSv", FieldType.TEXT, new String[]{"codUo", "descrizione"}, true);
		if(((DirigentiConcertoItem)getItem()).getAltriParamLoadCombo() != null) {
			dirigenteConcertoDS.addParam("altriParamLoadCombo", ((DirigentiConcertoItem)getItem()).getAltriParamLoadCombo());
		}
		
		dirigenteConcertoItem = new FilteredSelectItemWithDisplay("dirigenteConcerto", dirigenteConcertoDS) {

//			@Override
//			protected ListGrid builPickListProperties() {
//				ListGrid dirigenteConcertoPickListProperties = super.builPickListProperties();				
//				dirigenteConcertoPickListProperties.addFetchDataHandler(new FetchDataHandler() {
//
//					@Override
//					public void onFilterData(FetchDataEvent event) {
//						GWTRestDataSource dirigenteConcertoDS = (GWTRestDataSource) dirigenteConcertoItem.getOptionDataSource();
//						dirigenteConcertoDS.addParam("idSv", (String) dirigenteConcertoFromLoadDettHiddenItem.getValue());
//						dirigenteConcertoItem.setOptionDataSource(dirigenteConcertoDS);
//						dirigenteConcertoItem.invalidateDisplayValueCache();
//					}
//				});
//				return dirigenteConcertoPickListProperties;
//			}
			
			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);				
				mDynamicForm.setValue("codUoDirigenteConcerto", record.getAttributeAsString("codUo"));
				mDynamicForm.setValue("desDirigenteConcerto", record.getAttributeAsString("descrizione"));
			}

			@Override
			protected void clearSelect() {
				super.clearSelect();
				mDynamicForm.setValue("dirigenteConcerto", "");
				mDynamicForm.setValue("codUoDirigenteConcerto", "");
				mDynamicForm.setValue("desDirigenteConcerto", "");
			};

			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					mDynamicForm.setValue("dirigenteConcerto", "");
					mDynamicForm.setValue("codUoDirigenteConcerto", "");
					mDynamicForm.setValue("desDirigenteConcerto", "");
				}
			}
		};
		dirigenteConcertoItem.setShowTitle(false);
		dirigenteConcertoItem.setWidth(650);
		dirigenteConcertoItem.setValueField("idSv");
		ListGridField codUoField = new ListGridField("codUo", "Cod. U.O.");
		codUoField.setWidth(120);		
		codUoField.setCanFilter(false);
		ListGridField descrizioneField = new ListGridField("descrizione", "Descrizione");
		dirigenteConcertoItem.setPickListFields(codUoField, descrizioneField);	
		dirigenteConcertoItem.setAllowEmptyValue(false);
		dirigenteConcertoItem.setAutoFetchData(false);
		dirigenteConcertoItem.setAlwaysFetchMissingValues(true);
		dirigenteConcertoItem.setFetchMissingValues(true);
		dirigenteConcertoItem.setRequired(true);
					
		dirigenteConcertoFromLoadDettHiddenItem = new HiddenItem("dirigenteConcertoFromLoadDett");		
		codUoDirigenteConcertoHiddenItem = new HiddenItem("codUoDirigenteConcerto");
		desDirigenteConcertoHiddenItem = new HiddenItem("desDirigenteConcerto");
		
		flgDirigenteConcertoFirmatarioItem = new CheckboxItem("flgDirigenteConcertoFirmatario", I18NUtil.getMessages().nuovaPropostaAtto2_detail_flgFirmatario_title());
		flgDirigenteConcertoFirmatarioItem.setDefaultValue(false);
		flgDirigenteConcertoFirmatarioItem.setColSpan(1);
		flgDirigenteConcertoFirmatarioItem.setWidth("*");
		flgDirigenteConcertoFirmatarioItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return ((DirigentiConcertoItem)getItem()).showFlgFirmatario();
			}
		});
			
		mDynamicForm.setFields(dirigenteConcertoItem, dirigenteConcertoFromLoadDettHiddenItem, codUoDirigenteConcertoHiddenItem, desDirigenteConcertoHiddenItem, flgDirigenteConcertoFirmatarioItem);				
		
		mDynamicForm.setNumCols(5);
		
		addChild(mDynamicForm);
		
	}

	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[]{mDynamicForm};
	}
	
	@Override
	public void editRecord(Record record) {
		
		manageLoadSelectInEditRecord(record, dirigenteConcertoItem, "dirigenteConcerto", new String[]{"codUoDirigenteConcerto", "desDirigenteConcerto"}, "idSv");
		super.editRecord(record);					
	}	
	
}
