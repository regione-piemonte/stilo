/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;

import it.eng.utility.ui.module.core.client.UserInterfaceFactory;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.client.common.items.CustomItem;
import it.eng.utility.ui.module.layout.client.common.items.CustomItemFormField;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;
import it.eng.utility.ui.module.layout.shared.bean.FilterLookupType;

public class StringaRicercaEstesaCaseInsensitive1WithLookupItem extends CustomItem {

	protected DataSourceField _field;
	protected ConfigurableFilter _filter;
	protected Map<String, String> property = new HashMap<>();
	
	public StringaRicercaEstesaCaseInsensitive1WithLookupItem(){
		super();
	}
	
	public StringaRicercaEstesaCaseInsensitive1WithLookupItem(JavaScriptObject jsObject){
		super(jsObject);
	}	
		
	public StringaRicercaEstesaCaseInsensitive1WithLookupItem(DataSourceField field, ConfigurableFilter filter, Map<String, String> property){
		super();
		setFilter(filter);
		setField(field);
		setProperty(property);		
	}
	
	public StringaRicercaEstesaCaseInsensitive1WithLookupItem(JavaScriptObject jsObj, DataSourceField field, ConfigurableFilter filter, Map<String, String> property) {
		super(jsObj);
		setFilter(filter);
		setField(field);	
		setProperty(property);
	}
		
	@Override
	public CustomItem buildObject(JavaScriptObject jsObj) {
		
		CustomItem lCustomItem = new StringaRicercaEstesaCaseInsensitive1WithLookupItem(jsObj, getField(), getFilter(), getProperty());
		return lCustomItem;
	}

	@Override
	public CustomItemFormField[] getFormFields() {

		final FilterLookupType lookupType = FilterLookupType.valueOf(property.get("lookupType"));
		
		CustomItemFormField id = new CustomItemFormField("id", this);
		CustomItemFormField descrizione = new CustomItemFormField("descrizione", this);
		
		if (isLookupFieldWithId()){
			
			descrizione.setEditorType(new HiddenItem());
			
			TextItem idEditorType = new TextItem();
			idEditorType.setWidth(200);
			id.setEditorType(idEditorType);
			id.setEndRow(false);
			
		} else {
			
			id.setEditorType(new HiddenItem());
			
			TextItem descrizioneEditorType = new TextItem();
			descrizioneEditorType.setWidth(200);
			descrizione.setEditorType(descrizioneEditorType);
			descrizione.setEndRow(false);
			
		}			
			
		CustomItemFormField detailButton = new CustomItemFormField("detail", this);
		ImgButtonItem detailButtonEditorType = new ImgButtonItem("detailButton", "buttons/detail.png", I18NUtil.getMessages().detailButton_prompt());
		detailButtonEditorType.setAlign(Alignment.CENTER);
		detailButtonEditorType.setAlwaysEnabled(true);
		detailButtonEditorType.setIconWidth(16);
		detailButtonEditorType.setIconHeight(16);
		detailButtonEditorType.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return _form.getValue("id") != null && !"".equals((String) _form.getValue("id")) && isAbilToShowDetail();
			}
		});
		detailButtonEditorType.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				UserInterfaceFactory.onClickLookupFilterDetailButton(lookupType, (String) _form.getValue("id"), (String) _form.getValue("descrizione"));				
			}
		});
		detailButton.setEditorType(detailButtonEditorType);

		CustomItemFormField clearButton = new CustomItemFormField("clear", this);
		ImgButtonItem clearButtonEditorType = new ImgButtonItem("clearButton", "buttons/clear.png", I18NUtil.getMessages().clearButton_title());
		clearButtonEditorType.setAlign(Alignment.CENTER);
		clearButtonEditorType.setAlwaysEnabled(true);
		clearButtonEditorType.setIconWidth(16);
		clearButtonEditorType.setIconHeight(16);
		clearButtonEditorType.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return _form.getValue("id") != null && !"".equals((String) _form.getValue("id"));
			}
		});
		clearButtonEditorType.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				_form.clearValues();
				_instance.storeValue(new Record(_form.getValues()));
			}
		});
		clearButton.setEditorType(clearButtonEditorType);

		CustomItemFormField lookupButton = new CustomItemFormField("lookup", this);
		ImgButtonItem lookupButtonEditorType = new ImgButtonItem("lookupButton", "buttons/lookup.png", I18NUtil.getMessages().selezionaButton_prompt());
		lookupButtonEditorType.setAlign(Alignment.CENTER);
		lookupButtonEditorType.setAlwaysEnabled(true);
		lookupButtonEditorType.setIconWidth(16);
		lookupButtonEditorType.setIconHeight(16);
		lookupButtonEditorType.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				UserInterfaceFactory.onClickLookupFilterLookupButton(lookupType, new ServiceCallback<Record>() {

					@Override
					public void execute(Record object) {
						_form.clearValues();
						_form.setValue("id", object.getAttribute("id"));
						_form.setValue("descrizione", object.getAttribute("descrizione"));
						_instance.storeValue(new Record(_form.getValues()));
						if (isLookupFieldWithId() && object.getAttribute("id") != null && !object.getAttribute("id").equalsIgnoreCase("")) {
							setEqualOperator();							
						} else if (object.getAttribute("descrizione") != null && !object.getAttribute("descrizione").equalsIgnoreCase("")) {
							setEqualOperator();
						}						
					}
				});
			}
		});
		lookupButton.setEditorType(lookupButtonEditorType);
		
		return new CustomItemFormField[] { id, descrizione, detailButton, clearButton, lookupButton };
	}
	
	protected boolean isLookupFieldWithId() {
		return getLookupField() != null && "id".equalsIgnoreCase(getLookupField());
	}
	
	protected void setEqualOperator() {
		if(getFilter() != null) {
			AdvancedCriteria advancedCrit =  getFilter().getCriteria(true);
			for (int i = 0; i < advancedCrit.getCriteria().length; i++) {
				if (advancedCrit.getCriteria()[i].getFieldName().equals(getField().getName())) {
					Criterion newCriterion = new Criterion();
					newCriterion = advancedCrit.getCriteria()[i];
					newCriterion.setAttribute("value", getValue());
					newCriterion.setOperator(OperatorId.IEQUALS);
					advancedCrit.getCriteria()[i] = newCriterion;
					getFilter().setCriteria(advancedCrit);
					break;
				}
			}
		}
	}
	
	@Override
	public void onChangeFilter(String clauseOperator) {
		super.onChangeFilter(clauseOperator);
		_form.markForRedraw();
	}

	public String getLookupField(){
		return property.get("lookupField") != null && !"".equals(property.get("lookupField")) ? (property.get("lookupField")) : null;
	}

	public boolean isAbilToShowDetail() {
		return property.get("showLookupDetail") != null && !"".equals(property.get("showLookupDetail")) ? Boolean.valueOf(property.get("showLookupDetail"))	: false;
	}
	
	public DataSourceField getField() {
		return _field;
	}

	public void setField(DataSourceField field) {
		this._field = field;
	}

	public ConfigurableFilter getFilter() {
		return _filter;
	}

	public void setFilter(ConfigurableFilter filter) {
		this._filter = filter;
	}
	
	public Map<String, String> getProperty() {
		return property;
	}

	public void setProperty(Map<String, String> property) {
		this.property = property;
	}
	
}
