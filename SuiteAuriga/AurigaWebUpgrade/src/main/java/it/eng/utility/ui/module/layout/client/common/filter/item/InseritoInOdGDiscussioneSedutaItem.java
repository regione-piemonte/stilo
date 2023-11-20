/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.DateUtil;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;

import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.items.CustomItem;
import it.eng.utility.ui.module.layout.client.common.items.CustomItemFormField;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public class InseritoInOdGDiscussioneSedutaItem extends CustomItem {

	public InseritoInOdGDiscussioneSedutaItem(JavaScriptObject jsObject){
		super(jsObject);
	}
	
	public InseritoInOdGDiscussioneSedutaItem(){
		super();
	}
	
	public InseritoInOdGDiscussioneSedutaItem(final Map<String, String> property){
		super(property);
	}
		
	@Override
	public CustomItem buildObject(JavaScriptObject jsObj) {
		
		CustomItem lCustomItem = new InseritoInOdGDiscussioneSedutaItem(jsObj);
		return lCustomItem;
	}

	@Override
	public CustomItemFormField[] getFormFields() {

		CustomItemFormField organoCollegiale = new CustomItemFormField("organoCollegiale", this);
		organoCollegiale.setEditorType(new HiddenItem());
		
		CustomItemFormField data = new CustomItemFormField("data", this);
		data.setEditorType(new HiddenItem());
		
		CustomItemFormField esito = new CustomItemFormField("esito", this);
		esito.setEditorType(new HiddenItem());
		
		CustomItemFormField descrizione = new CustomItemFormField("descrizione", this);
		TextItem descrizioneEditorType = new TextItem();
		descrizioneEditorType.setWidth(350);
		descrizioneEditorType.setCanEdit(false);
		descrizione.setEditorType(descrizioneEditorType);
		descrizione.setEndRow(false);	
			
		CustomItemFormField editButton = new CustomItemFormField("modify", this);
		ImgButtonItem editButtonEditorType = new ImgButtonItem("editButton", "buttons/modify.png", I18NUtil.getMessages().modifyButton_prompt());
		editButtonEditorType.setAlign(Alignment.CENTER);
		editButtonEditorType.setAlwaysEnabled(true);
		editButtonEditorType.setIconWidth(16);
		editButtonEditorType.setIconHeight(16);
		editButtonEditorType.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				Record lRecord = new Record(_form.getValues());
				InseritoInOdgDiscussioneSedutaPopup lInseritoInOdgDiscussioneSedutaPopup = new InseritoInOdgDiscussioneSedutaPopup("Inserito in OdG/discussione di seduta collegiale", property, lRecord) {
					@Override
					public void onClickOkButton(Record values) {
						editRecord(values);						
					}					
				};
				lInseritoInOdgDiscussioneSedutaPopup.show();
			}
		});
		editButton.setEditorType(editButtonEditorType);

		CustomItemFormField clearButton = new CustomItemFormField("clear", this);
		ImgButtonItem clearButtonEditorType = new ImgButtonItem("clearButton", "buttons/clear.png", I18NUtil.getMessages().clearButton_title());
		clearButtonEditorType.setAlign(Alignment.CENTER);
		clearButtonEditorType.setAlwaysEnabled(true);
		clearButtonEditorType.setIconWidth(16);
		clearButtonEditorType.setIconHeight(16);
		clearButtonEditorType.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {				
				return _form.getValue("descrizione") != null && !"".equals((String) _form.getValue("descrizione"));
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

		return new CustomItemFormField[] { organoCollegiale, data, esito, descrizione, editButton, clearButton };
	}
	
	@Override
	protected void editRecord(Record record) {
		String descrizione = "Del " + record.getAttribute("desOrganoCollegiale");
		if(record.getAttributeAsDate("data") != null) {
			descrizione += " in data " + DateUtil.formatAsShortDate(record.getAttributeAsDate("data"));
		}
		if(record.getAttribute("esito") != null && !"".equals(record.getAttribute("esito"))) {
			descrizione +=  " con esito " + record.getAttribute("esito");
		}
		record.setAttribute("descrizione", descrizione);						
		super.editRecord(record);
		_instance.storeValue(record);
	}

}
