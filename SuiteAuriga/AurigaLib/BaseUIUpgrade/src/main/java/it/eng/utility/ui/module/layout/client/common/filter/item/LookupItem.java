/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;

import it.eng.utility.ui.module.core.client.UserInterfaceFactory;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.items.CustomItem;
import it.eng.utility.ui.module.layout.client.common.items.CustomItemFormField;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;
import it.eng.utility.ui.module.layout.shared.bean.FilterLookupType;

public class LookupItem extends CustomItem {

	public LookupItem(JavaScriptObject jsObject){
		super(jsObject);
	}
	
	public LookupItem(){
		super();
	}
		
	public LookupItem(final Map<String, String> property){
		super(property);
	}
		
	@Override
	public CustomItem buildObject(JavaScriptObject jsObj) {
		
		CustomItem lCustomItem = new LookupItem(jsObj);
		return lCustomItem;
	}

	@Override
	public CustomItemFormField[] getFormFields() {

		final FilterLookupType lookupType = FilterLookupType.valueOf(property.get("lookupType"));
		
		CustomItemFormField id = new CustomItemFormField("id", this);
		CustomItemFormField descrizione = new CustomItemFormField("descrizione", this);
		
		if (getLookupField() != null && "id".equalsIgnoreCase(getLookupField())){
			descrizione.setEditorType(new HiddenItem());
			
			TextItem idEditorType = new TextItem();
			idEditorType.setWidth(200);
			idEditorType.setCanEdit(false);
			id.setEditorType(idEditorType);
			id.setEndRow(false);
		} else {
			id.setEditorType(new HiddenItem());
			
			TextItem descrizioneEditorType = new TextItem();
			descrizioneEditorType.setWidth(200);
			descrizioneEditorType.setCanEdit(false);
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
					}
				});
			}
		});
		lookupButton.setEditorType(lookupButtonEditorType);

		return new CustomItemFormField[] { id, descrizione, detailButton, clearButton, lookupButton };
	}

	public String getLookupField(){
		return property.get("lookupField") != null && !"".equals(property.get("lookupField")) ? (property.get("lookupField")) : null;
	}

	public boolean isAbilToShowDetail() {
		return property.get("showLookupDetail") != null && !"".equals(property.get("showLookupDetail")) ? Boolean.valueOf(property.get("showLookupDetail"))	: false;
	}
}
