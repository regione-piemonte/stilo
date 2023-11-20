/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
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
import com.smartgwt.client.widgets.form.fields.events.DataArrivedEvent;
import com.smartgwt.client.widgets.form.fields.events.DataArrivedHandler;
import com.smartgwt.client.widgets.grid.ListGrid;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;

public class AssessoriCanvas extends ReplicableCanvas {
	
	private SelectItem assessoreItem;
	private HiddenItem assessoreFromLoadDettHiddenItem;
	private HiddenItem desAssessoreHiddenItem;
	private CheckboxItem flgAssessoreFirmatarioItem;
	
	private ReplicableCanvasForm mDynamicForm;

	private String start;

	public AssessoriCanvas(ReplicableItem item) {
		super(item);
	}

	@Override
	public void disegna() {
		
		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);
		
		GWTRestDataSource assessoreDS = new GWTRestDataSource("LoadComboAssessoriDataSource", "key", FieldType.TEXT);
		if(((AssessoriItem)getItem()).getAltriParamLoadCombo() != null) {
			assessoreDS.addParam("altriParamLoadCombo", ((AssessoriItem)getItem()).getAltriParamLoadCombo());
		}
		
		assessoreItem = new SelectItem("assessore") {
			
			@Override
			protected ListGrid builPickListProperties() {
				ListGrid assessorePickListProperties = super.builPickListProperties();
				if(assessorePickListProperties == null) {
					assessorePickListProperties = new ListGrid();
				}
				assessorePickListProperties.addFetchDataHandler(new FetchDataHandler() {

					@Override
					public void onFilterData(FetchDataEvent event) {
						
						start = DateTimeFormat.getFormat("HH:mm:ss").format(new Date());
						
						GWTRestDataSource assessoreDS = (GWTRestDataSource) assessoreItem.getOptionDataSource();
						assessoreDS.addParam("uoProponente", ((AssessoriItem)getItem()).getUoProponenteCorrente());
//						assessoreDS.addParam("key", (String) assessoreFromLoadDettHiddenItem.getValue());						
						assessoreItem.setOptionDataSource(assessoreDS);
						assessoreItem.invalidateDisplayValueCache();
					}
				});
				return assessorePickListProperties;
			}
			
			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);
				mDynamicForm.setValue("desAssessore", record.getAttributeAsString("value"));
			}

			@Override
			protected void clearSelect() {
				super.clearSelect();
				mDynamicForm.setValue("assessore", "");
				mDynamicForm.setValue("desAssessore", "");
			};

			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					mDynamicForm.setValue("assessore", "");
					mDynamicForm.setValue("desAssessore", "");
				}
			}
		};
		assessoreItem.setOptionDataSource(assessoreDS);
		assessoreItem.setShowTitle(false);
		assessoreItem.setStartRow(true);
		assessoreItem.setWidth(650);
		assessoreItem.setValueField("key");
		assessoreItem.setDisplayField("value");				
		assessoreItem.setAllowEmptyValue(false);
		assessoreItem.setClearable(true);
		// WORKAROUND PER IL PROBLEMA RELATIVO AL METODO resetAfterChangedParams CHE, A SEGUITO DELLA FETCH, NON ENTRA NELLA CALLBACK		
		if(((AssessoriItem)getItem()).getFlgAbilitaAutoFetchDataSelectOrganigramma()) {
			assessoreItem.setAutoFetchData(true);
		} else {
			assessoreItem.setAutoFetchData(false);
		}
		assessoreItem.setAlwaysFetchMissingValues(true);
		assessoreItem.setFetchMissingValues(true);
		assessoreItem.setRequired(true);
		assessoreItem.addDataArrivedHandler(new DataArrivedHandler() {

			@Override
			public void onDataArrived(DataArrivedEvent event) {
				
				GWT.log("loadCombo() listaAssessori started at " + start + " ended at " + DateTimeFormat.getFormat("HH:mm:ss").format(new Date()));												
			}
		});
		
		assessoreFromLoadDettHiddenItem = new HiddenItem("assessoreFromLoadDett");
		desAssessoreHiddenItem = new HiddenItem("desAssessore");
					
		flgAssessoreFirmatarioItem = new CheckboxItem("flgAssessoreFirmatario", I18NUtil.getMessages().nuovaPropostaAtto2_detail_flgFirmatario_title());
		flgAssessoreFirmatarioItem.setDefaultValue(false);
		flgAssessoreFirmatarioItem.setColSpan(1);
		flgAssessoreFirmatarioItem.setWidth("*");
		flgAssessoreFirmatarioItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return ((AssessoriItem)getItem()).showFlgFirmatario();
			}
		});					
		
		mDynamicForm.setFields(assessoreItem, assessoreFromLoadDettHiddenItem, desAssessoreHiddenItem, flgAssessoreFirmatarioItem);	
		
		mDynamicForm.setNumCols(5);
		
		addChild(mDynamicForm);
		
	}
	
	public void resetAfterChangedParams() {
		
		final String value = assessoreItem.getValueAsString();
		assessoreItem.fetchData(new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				
				RecordList data = response.getDataAsRecordList();
				if(((AssessoriItem)getItem()).selectUniqueValueAfterChangedParams() && data.getLength() == 1) {
					if(value == null || "".equals(value) || !value.equals(data.get(0).getAttributeAsString("key"))) {	
						mDynamicForm.setValue("assessore", data.get(0).getAttribute("key"));
						mDynamicForm.setValue("desAssessore", data.get(0).getAttribute("value"));
					}
				} else if(value != null && !"".equals(value)) {
					boolean trovato = false;
					if (data.getLength() > 0) {
						for (int i = 0; i < data.getLength(); i++) {
							String key = data.get(i).getAttribute("key");
							if (value.equals(key)) {
								trovato = true;
								break;
							}
						}
					}
					if (!trovato) {
						mDynamicForm.setValue("assessore", "");
						mDynamicForm.setValue("desAssessore", "");
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
		
		manageLoadSelectInEditRecord(record, assessoreItem, "assessore", new String[]{"desAssessore"}, "key");
		super.editRecord(record);				
	}
	
}
