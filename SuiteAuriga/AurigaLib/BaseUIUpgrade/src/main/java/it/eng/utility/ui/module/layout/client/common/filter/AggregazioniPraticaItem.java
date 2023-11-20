/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.smartgwt.client.widgets.form.fields.SelectItem;

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.client.common.items.CustomItem;
import it.eng.utility.ui.module.layout.client.common.items.CustomItemFormField;

public class AggregazioniPraticaItem extends CustomItem {
	
	ConfigurableFilter mFilter;
	
	private CustomItemFormField aggregazioneField;
	private SelectItem aggregazioneItem;
	
	private GWTRestDataSource aggregazioneDS;
	
	public AggregazioniPraticaItem(JavaScriptObject jsObject){
		super(jsObject);
	}
	
	public AggregazioniPraticaItem(){
		super();
	}
		
	public AggregazioniPraticaItem(final Map<String, String> property){
		super(property);
	}
		
	@Override
	public CustomItem buildObject(JavaScriptObject jsObj) {
		
		CustomItem lCustomItem = new AggregazioniPraticaItem(jsObj);
		return lCustomItem;
	}
	
	@Override
	public CustomItemFormField[] getFormFields() {
		createAggregazioneField();
		
		return new CustomItemFormField[]{aggregazioneField};
	}
	
	public void createAggregazioneField(){
		
		aggregazioneItem = new SelectItem("aggregazioneSelect");	
		aggregazioneDS = new SelectGWTRestDataSource("SinapoliRicercaAggregazioneDatasource");
		aggregazioneItem.setOptionDataSource(aggregazioneDS);
		aggregazioneItem.setDisplayField("value");
		aggregazioneItem.setValueField("key");
		aggregazioneItem.setAllowEmptyValue(true);
		aggregazioneItem.setWidth(300);
		aggregazioneItem.setStartRow(true);
		aggregazioneItem.setAutoFetchData(false);
		aggregazioneField = new CustomItemFormField(aggregazioneItem, "", this);		
		aggregazioneField.setEditorType(aggregazioneItem);
		aggregazioneField.setVisible(true);
	}
	
	public void setFilter(ConfigurableFilter filter) {
		mFilter = filter;
	}
}
