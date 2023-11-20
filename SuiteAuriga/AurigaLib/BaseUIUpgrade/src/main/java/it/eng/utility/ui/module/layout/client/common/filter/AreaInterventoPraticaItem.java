/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.FormItemCriteriaFunction;
import com.smartgwt.client.widgets.form.fields.FormItemFunctionContext;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.client.common.items.CustomItem;
import it.eng.utility.ui.module.layout.client.common.items.CustomItemFormField;

public class AreaInterventoPraticaItem extends CustomItem {

	private ConfigurableFilter mFilter;
	
	private CustomItemFormField provinciaField;
	private SelectItem provinciaItem;	
	
	private CustomItemFormField comuneField;
	private SelectItem comuneItem;
	
	
	private GWTRestDataSource comuneDS;
	private GWTRestDataSource provinciaDS;

	public AreaInterventoPraticaItem(JavaScriptObject jsObject){
		super(jsObject);
	}
	
	public AreaInterventoPraticaItem(){
		super();
	}
		
	public AreaInterventoPraticaItem(final Map<String, String> property){
		super(property);
	}
		
	@Override
	public CustomItem buildObject(JavaScriptObject jsObj) {
		
		CustomItem lCustomItem = new AreaInterventoPraticaItem(jsObj);
		return lCustomItem;
	}

	@Override
	public CustomItemFormField[] getFormFields() {
		createProvincia();
		createComune();
		return new CustomItemFormField[]{provinciaField, comuneField };
	}

	protected void createProvincia() {
		provinciaItem = new SelectItem("provinciaSelect");	
		provinciaDS = new SelectGWTRestDataSource("SinapoliProvincePuntoIndagineDatasource");
		provinciaItem.setOptionDataSource(provinciaDS);	
		provinciaItem.setDisplayField("descrProv");
		provinciaItem.setValueField("istatprov");
		provinciaItem.setAllowEmptyValue(true);
		provinciaItem.setWidth(300);	
		provinciaItem.setAutoFetchData(false);
		//provinciaItem.setFetchMissingValues(true);	
		
		provinciaField = new CustomItemFormField(provinciaItem, "Provincia", this);		
		provinciaField.setEditorType(provinciaItem);		
		provinciaField.setVisible(true);
		
		
		provinciaField.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				String idProvincia = "";				
				if(event.getItem().getValue()!=null){
					idProvincia = (String)event.getForm().getValueAsString("provinciaSelect");
					if (comuneField.isVisible()){
						comuneField.clearValue();
					} else {
						comuneField.show();
					}		
				}
				else{
					comuneField.clearValue();
					comuneField.hide();
				}								
				comuneDS = (GWTRestDataSource) comuneItem.getOptionDataSource();
				comuneDS.addParam("provinciaSelected", idProvincia);
				comuneItem.setOptionDataSource(comuneDS);	
			}
		});		
	}
	
	protected void createComune() {
		comuneItem = new SelectItem("comuneSelect");	
		comuneDS = new SelectGWTRestDataSource("SinapoliComuniPuntoIndagineDatasource");
		comuneItem.setOptionDataSource(comuneDS);
		comuneItem.setDisplayField("descr");
		comuneItem.setValueField("idcomune");
		comuneItem.setAllowEmptyValue(true);
		comuneItem.setWidth(300);
		comuneItem.setStartRow(false);
		comuneItem.setAutoFetchData(true);
		comuneItem.setFetchMissingValues(true);	
							
		comuneItem.setPickListFilterCriteriaFunction(new FormItemCriteriaFunction() {
			@Override
			public Criteria getCriteria(FormItemFunctionContext itemContext) {
				
				Criteria lAdvancedCriteria = new Criteria();
				String idProvincia = itemContext.getFormItem().getForm().getValueAsString("provinciaSelect");
				lAdvancedCriteria.addCriteria(new Criteria("istatProv",    idProvincia));
				return lAdvancedCriteria.asAdvancedCriteria();
			}
		});
		
		comuneItem.setShowIfCondition(new FormItemIfFunction() {
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return form.getValue("provinciaSelect")!=null && !form.getValueAsString("provinciaSelect").trim().equals("");
			}
		});
		comuneField = new CustomItemFormField(comuneItem, "Comune", this);		
		comuneField.setEditorType(comuneItem);
		comuneField.setVisible(false);
	}

	public void setFilter(ConfigurableFilter filter) {
		mFilter = filter;
	}	
	
}