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
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;

public class RespVistiConformitaCanvas extends ReplicableCanvas {
	
	private FilteredSelectItemWithDisplay respVistiConformitaItem;
	private HiddenItem respVistiConformitaFromLoadDettHiddenItem;
	private HiddenItem codUoRespVistiConformitaHiddenItem;
	private HiddenItem desRespVistiConformitaHiddenItem;
	private CheckboxItem flgRespVistiConformitaFirmatarioItem;
	private TextAreaItem motiviRespVistiConformitaItem;

	private ReplicableCanvasForm mDynamicForm;

	public RespVistiConformitaCanvas(ReplicableItem item) {
		super(item);
	}
	
	@Override
	public void disegna() {
		
		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);
		mDynamicForm.setNumCols(5);
		mDynamicForm.setColWidths(1, 1, 1, "*", "*");
		SelectGWTRestDataSource respVistiConformitaDS = new SelectGWTRestDataSource("LoadComboDirigenteDataSource", "idSv", FieldType.TEXT, new String[]{"codUo", "descrizione"}, true);
		if(((RespVistiConformitaItem)getItem()).getAltriParamLoadCombo() != null) {
			respVistiConformitaDS.addParam("altriParamLoadCombo", ((RespVistiConformitaItem)getItem()).getAltriParamLoadCombo());
		}
		
		respVistiConformitaItem = new FilteredSelectItemWithDisplay("respVistiConformita", respVistiConformitaDS) {
			
//			@Override
//			protected ListGrid builPickListProperties() {
//				ListGrid respVistiConformitaPickListProperties = super.builPickListProperties();				
//				respVistiConformitaPickListProperties.addFetchDataHandler(new FetchDataHandler() {
//
//					@Override
//					public void onFilterData(FetchDataEvent event) {
//						GWTRestDataSource respVistiConformitaDS = (GWTRestDataSource) respVistiConformitaItem.getOptionDataSource();
//						respVistiConformitaDS.addParam("idSv", (String) respVistiConformitaFromLoadDettHiddenItem.getValue());
//						respVistiConformitaItem.setOptionDataSource(respVistiConformitaDS);
//						respVistiConformitaItem.invalidateDisplayValueCache();
//					}
//				});
//				return respVistiConformitaPickListProperties;
//			}
			
			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);				
				mDynamicForm.setValue("codUoRespVistiConformita", record.getAttributeAsString("codUo"));
				mDynamicForm.setValue("desRespVistiConformita", record.getAttributeAsString("descrizione"));
			}

			@Override
			protected void clearSelect() {
				super.clearSelect();
				mDynamicForm.setValue("respVistiConformita", "");
				mDynamicForm.setValue("codUoRespVistiConformita", "");
				mDynamicForm.setValue("desRespVistiConformita", "");
			};

			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					mDynamicForm.setValue("respVistiConformita", "");
					mDynamicForm.setValue("codUoRespVistiConformita", "");
					mDynamicForm.setValue("desRespVistiConformita", "");
				}
			}
		};
		respVistiConformitaItem.setShowTitle(false);
		respVistiConformitaItem.setWidth(650);
		respVistiConformitaItem.setValueField("idSv");
		ListGridField codUoField = new ListGridField("codUo", "Cod. U.O.");
		codUoField.setWidth(120);		
		codUoField.setCanFilter(false);
		ListGridField descrizioneField = new ListGridField("descrizione", "Descrizione");
		respVistiConformitaItem.setPickListFields(codUoField, descrizioneField);	
		respVistiConformitaItem.setAllowEmptyValue(false);
		respVistiConformitaItem.setAutoFetchData(false);
		respVistiConformitaItem.setAlwaysFetchMissingValues(true);
		respVistiConformitaItem.setFetchMissingValues(true);
		respVistiConformitaItem.setRequired(true);
		
		respVistiConformitaFromLoadDettHiddenItem = new HiddenItem("respVistiConformitaFromLoadDett");
		codUoRespVistiConformitaHiddenItem = new HiddenItem("codUoRespVistiConformita");
		desRespVistiConformitaHiddenItem = new HiddenItem("desRespVistiConformita");
		
		flgRespVistiConformitaFirmatarioItem = new CheckboxItem("flgRespVistiConformitaFirmatario", I18NUtil.getMessages().nuovaPropostaAtto2_detail_flgFirmatario_title());
		flgRespVistiConformitaFirmatarioItem.setDefaultValue(false);
		flgRespVistiConformitaFirmatarioItem.setColSpan(1);
		flgRespVistiConformitaFirmatarioItem.setWidth("*");
		flgRespVistiConformitaFirmatarioItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return ((RespVistiConformitaItem)getItem()).showFlgFirmatario();
			}
		});
		
		motiviRespVistiConformitaItem = new TextAreaItem("motiviRespVistiConformita");
		motiviRespVistiConformitaItem.setShowTitle(false);
		motiviRespVistiConformitaItem.setHint("Motivo/i");
		motiviRespVistiConformitaItem.setShowHintInField(true);
		motiviRespVistiConformitaItem.setColSpan(20);
		motiviRespVistiConformitaItem.setStartRow(true);
		motiviRespVistiConformitaItem.setLength(4000);
		motiviRespVistiConformitaItem.setHeight(40);
		motiviRespVistiConformitaItem.setWidth(650);
		if(((RespVistiConformitaItem)getItem()).isRequiredMotivi()) {
			motiviRespVistiConformitaItem.setRequired(true);
		}
		motiviRespVistiConformitaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return ((RespVistiConformitaItem)getItem()).showMotivi();
			}
		});
				
		mDynamicForm.setFields(respVistiConformitaItem, respVistiConformitaFromLoadDettHiddenItem, codUoRespVistiConformitaHiddenItem, desRespVistiConformitaHiddenItem, flgRespVistiConformitaFirmatarioItem, motiviRespVistiConformitaItem);	
		
//		mDynamicForm.setNumCols(5);
		
		addChild(mDynamicForm);
		
	}

	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[]{mDynamicForm};
	}
	
	@Override
	public void editRecord(Record record) {
		
		manageLoadSelectInEditRecord(record, respVistiConformitaItem, "respVistiConformita", new String[]{"codUoRespVistiConformita", "desRespVistiConformita"}, "idSv");
		super.editRecord(record);					
	}	
	
}
