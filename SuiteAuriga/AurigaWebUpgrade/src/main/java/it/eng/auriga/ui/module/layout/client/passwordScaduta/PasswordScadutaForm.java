/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.i18n.I18NUtil;

import com.google.gwt.core.client.JavaScriptObject;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FormItemType;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;

public class PasswordScadutaForm extends DynamicForm{

	private PasswordScadutaWindow selectionWindow;
	private DynamicForm form;

	public PasswordScadutaForm(PasswordScadutaWindow pSchemaSelectionWindow){
		form = this;
		selectionWindow = pSchemaSelectionWindow;
		setKeepInParentRect(true);
		setWidth100();
		setHeight100();
		setNumCols(2);
		setColWidths(120,120);
		setCellPadding(5);
		FormItem lPasswordFormItem = new FormItem();
		lPasswordFormItem.setName("oldPassword");
		lPasswordFormItem.setType(FormItemType.PASSWORD_ITEM.getValue());
		lPasswordFormItem.setTitle("Digita la vecchia password");  
		lPasswordFormItem.setWidth(120);

		FormItem lNewPasswordFormItem = new FormItem();
		lNewPasswordFormItem.setName("newPassword");
		lNewPasswordFormItem.setType(FormItemType.PASSWORD_ITEM.getValue());
		lNewPasswordFormItem.setTitle("Digita la nuova password");  
		lNewPasswordFormItem.setWidth(120);

		FormItem lConfirmPasswordFormItem = new FormItem();
		lConfirmPasswordFormItem.setName("confermaPassword");
		lConfirmPasswordFormItem.setType(FormItemType.PASSWORD_ITEM.getValue());
		lConfirmPasswordFormItem.setTitle("Ripeti la nuova password");  
		lConfirmPasswordFormItem.setWidth(120);

		setDataSource(new GWTRestDataSource("AurigaCambioPasswordDataSource"));
		ButtonItem lButtonItem = new ButtonItem();
		lButtonItem.setName("OK");
		lButtonItem.setTitle(I18NUtil.getMessages().loginButton_title());		
		lButtonItem.setColSpan(2);
		lButtonItem.setWidth(100);
		lButtonItem.setTop(20);
		lButtonItem.setAlign(Alignment.CENTER);
		lButtonItem.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				form.saveData(new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						JavaScriptObject lJavaScriptObject = (JavaScriptObject)rawData;
						Record lRecord = new Record(lJavaScriptObject);
						if (lRecord.getAttributeAsBoolean("changeOk")){
							selectionWindow.passwordOk();
						}
					}
				});				
			}
		});
		setFields(lPasswordFormItem, lNewPasswordFormItem, lConfirmPasswordFormItem, lButtonItem);
		setAlign(Alignment.CENTER);
		setTop(50);
	}
}
