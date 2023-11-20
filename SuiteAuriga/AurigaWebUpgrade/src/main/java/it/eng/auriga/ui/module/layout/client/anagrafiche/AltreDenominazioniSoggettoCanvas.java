/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.widgets.form.fields.FormItemCriteriaFunction;
import com.smartgwt.client.widgets.form.fields.FormItemFunctionContext;
import com.smartgwt.client.widgets.form.fields.HiddenItem;

public class AltreDenominazioniSoggettoCanvas extends ReplicableCanvas{

	private HiddenItem rowIdItem;	
	private SelectItem tipoItem;	
	private TextItem denominazioneItem;	
	
	private ReplicableCanvasForm mDynamicForm;
	
	@Override
	public void disegna() {

		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);

		rowIdItem = new HiddenItem("rowId");
		
		final GWTRestDataSource tipoAltraDenominazioneDS = new GWTRestDataSource("TipoAltraDenominazioneSoggettoDataSource", "key", FieldType.TEXT);
		
		tipoItem = new SelectItem("tipo", I18NUtil.getMessages().soggetti_detail_altreDenominazioni_tipoItem_title());
		tipoItem.setValueField("key");  
		tipoItem.setDisplayField("value");
		tipoItem.setOptionDataSource(tipoAltraDenominazioneDS); 		
		tipoItem.setWidth(200);			
		tipoItem.setRequired(true);
		tipoItem.setCachePickListResults(false);		
		tipoItem.setPickListFilterCriteriaFunction(new FormItemCriteriaFunction() {				
			@Override
			public Criteria getCriteria(FormItemFunctionContext itemContext) {
				if(rowIdItem.getValue() != null && !"".equals((String) rowIdItem.getValue())) {
					Criterion[] criterias = new Criterion[1];	
					criterias[0] = new Criterion("rowId", OperatorId.EQUALS, (String) rowIdItem.getValue());	
					return new AdvancedCriteria(OperatorId.AND, criterias);
				} 
				return null;
			}
		});
		
		// denominazione
		denominazioneItem = new TextItem("denominazione");
		denominazioneItem.setShowTitle(false);
		denominazioneItem.setWidth(200);	    
		denominazioneItem.setRequired(true);	
		denominazioneItem.setColSpan(1);
		
		mDynamicForm.setFields(rowIdItem, tipoItem, denominazioneItem);	
		
		mDynamicForm.setNumCols(10);		
		
		addChild(mDynamicForm);

	}
	
	public Record getFormValuesAsRecord() {
		return mDynamicForm.getValuesAsRecord();	
	}
		
	@Override
	public ReplicableCanvasForm[] getForm() {
		
		return new ReplicableCanvasForm[]{mDynamicForm};
	}
	
	@Override
	public void editRecord(Record record) {
		
		GWTRestDataSource tipoAltraDenominazioneDS = (GWTRestDataSource) tipoItem.getOptionDataSource();
		if(record.getAttribute("rowId") != null && !"".equals(record.getAttributeAsString("rowId"))) {
			tipoAltraDenominazioneDS.addParam("rowId", record.getAttributeAsString("rowId"));										
		} else {
			tipoAltraDenominazioneDS.addParam("rowId", null);										
		}
		tipoItem.setOptionDataSource(tipoAltraDenominazioneDS);	
		
		super.editRecord(record);
	}
	
}
