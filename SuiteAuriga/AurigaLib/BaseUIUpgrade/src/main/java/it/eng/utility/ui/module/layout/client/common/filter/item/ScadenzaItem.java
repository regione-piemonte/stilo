/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FilterClause;
import com.smartgwt.client.widgets.form.fields.FormItemCriteriaFunction;
import com.smartgwt.client.widgets.form.fields.FormItemFunctionContext;
import com.smartgwt.client.widgets.form.fields.events.DataArrivedEvent;
import com.smartgwt.client.widgets.layout.VStack;

import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.CustomItem;
import it.eng.utility.ui.module.layout.client.common.items.CustomItemFormField;
import it.eng.utility.ui.module.layout.client.common.items.NumericItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;

public class ScadenzaItem extends CustomItem {

	public ScadenzaItem(JavaScriptObject jsObject){
		super(jsObject);
	}
	
	public ScadenzaItem(){
		super();
	}
		
	public ScadenzaItem(final Map<String, String> property){
		super(property);
	}
		
	@Override
	public CustomItem buildObject(JavaScriptObject jsObj) {
		
		CustomItem lCustomItem = new ScadenzaItem(jsObj);
		return lCustomItem;
	}
	
	@Override
	public CustomItemFormField[] getFormFields() {
//		CustomItemFormField tipoScadenza = new CustomItemFormField("tipoScadenza", this);
		SelectItem lSelectItem = new SelectItem();
		SelectGWTRestDataSource lSelectGWTRestDataSource = new SelectGWTRestDataSource("LoadTipoScadenza");
		lSelectGWTRestDataSource.addParam("isFromFilter", "true");
		lSelectGWTRestDataSource.setCacheAllData(false);
		lSelectGWTRestDataSource.setAutoCacheAllData(false);
		lSelectItem.setOptionDataSource(lSelectGWTRestDataSource);
		lSelectItem.setCachePickListResults(false);
		lSelectItem.setDisplayField("value");
		lSelectItem.setValueField("key");
		lSelectItem.setWidth(150);
		lSelectItem.setAutoFetchData(false);
		
		lSelectItem.setPickListFilterCriteriaFunction(new FormItemCriteriaFunction(){
			@Override
			public Criteria getCriteria(FormItemFunctionContext itemContext) {
				FilterClause lFilterClause = (FilterClause) itemContext.getFormItem().getForm().getCanvasItem().getForm().getParentElement();
				VStack lVStack = (VStack) lFilterClause.getParentElement();
				FilterClause lFilterClauseIdProcess = (FilterClause) lVStack.getMember(0);
				DynamicForm lDynamicForm = (DynamicForm) lFilterClauseIdProcess.getMember(1);
				String idProcessType = (String) lDynamicForm.getFields()[2].getValue();
				return new Criteria("idProcessType", idProcessType).asAdvancedCriteria();
			}
		});
		CustomItemFormField tipoScadenza = new CustomItemFormField(lSelectItem, null, this);
		tipoScadenza.setName("tipoScadenza");
		tipoScadenza.setWidth(150);
		
//		tipoScadenza.setEditorType(lSelectItem);
		CustomItemFormField entroGiorni = new CustomItemFormField("entroGiorni", this);
		NumericItem lNumericItemEntroGiorni = new NumericItem();
		lNumericItemEntroGiorni.setWidth(50);
		entroGiorni.setEditorType(lNumericItemEntroGiorni);
		entroGiorni.setShowTitle(true);
		entroGiorni.setTitle("entro giorni");
		CustomItemFormField trascorsaDaGiorni = new CustomItemFormField("trascorsaDaGiorni", this);
		NumericItem lNumericItemTrascorsaDaGiorni = new NumericItem();
		lNumericItemTrascorsaDaGiorni.setWidth(50);
		trascorsaDaGiorni.setEditorType(lNumericItemTrascorsaDaGiorni);
		trascorsaDaGiorni.setShowTitle(true);
		trascorsaDaGiorni.setTitle("o trascorsa da giorni");
		CustomItemFormField soloSeTermineScadenzaNonAvvenuto = new CustomItemFormField("soloSeTermineScadenzaNonAvvenuto", this);
		CheckboxItem lCheckboxItem = new CheckboxItem();
		lCheckboxItem.setTitle("solo se termine scadenza non avvenuto");
		lCheckboxItem.setWidth(20);
		soloSeTermineScadenzaNonAvvenuto.setEditorType(lCheckboxItem);
		return new CustomItemFormField[]{tipoScadenza, entroGiorni, trascorsaDaGiorni, soloSeTermineScadenzaNonAvvenuto};
	}

	protected void manageData(DataArrivedEvent event) {
//		System.out.println(event.getData().get(0).getAttributeAsString("value"));
//		System.out.println(event.getData().get(0).getAttributeAsString("key"));
		
	}

	protected void disegna(Object value) {
		_form = getFormToDraw();
		_form.setFields(getFormFields());
		setDefaultValues(value);
		if (value != null) {		
			_form.editRecord(new Record((JavaScriptObject) value));
		}
		_instance.storeValue(new Record(_form.getValues()));
		setCanvas(_form);
		setShouldSaveValue(true);	


	}
	
	protected DynamicForm getFormToDraw(){
		DynamicForm lFormToDraw = new DynamicForm();
		lFormToDraw.setNumCols(6);
		lFormToDraw.setWidth(750);
		lFormToDraw.setColWidths(120,
				60,50,100,50,150);
		lFormToDraw.setStopOnError(true);
		return lFormToDraw;
	}

}
