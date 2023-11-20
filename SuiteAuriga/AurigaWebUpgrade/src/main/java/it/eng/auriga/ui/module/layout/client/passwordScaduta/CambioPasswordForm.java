/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;

import com.google.gwt.core.client.JavaScriptObject;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FormItemType;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.PasswordItem;

public class CambioPasswordForm extends DynamicForm{
	
	private CambioPasswordWindow cambioPasswordWindow;
	private CambioPasswordForm form;
	
	public CambioPasswordForm(CambioPasswordWindow pCambioPasswordWindow) {
		
		form = this;
		cambioPasswordWindow = pCambioPasswordWindow;
		
		GWTRestDataSource datasource = new GWTRestDataSource("AurigaCambioPasswordDataSource");
		form.setDataSource(datasource);
		form.setUseAllDataSourceFields(true);
		form.setCanSubmit(true);
		form.setWrapItemTitles(false);
		
		setKeepInParentRect(true);
		setWidth100();
		setHeight100();
		setNumCols(4);
		setColWidths(300,200, 300, 200);
		setCellPadding(5);
		
		// OLD PASSWORD
		PasswordItem lOldPasswordItem = new PasswordItem();
		lOldPasswordItem.setName("oldPassword");
		lOldPasswordItem.setType(FormItemType.PASSWORD_ITEM.getValue());	
		lOldPasswordItem.setLength(30);
		lOldPasswordItem.setTitle(I18NUtil.getMessages().cambioPassword_oldPasswordItem_title());  
		lOldPasswordItem.setWidth(250);
		lOldPasswordItem.setColSpan(4);
		
		// NEW PASSWORD
		PasswordItem lNewPasswordFormItem = new PasswordItem();
		lNewPasswordFormItem.setName("newPassword");
		lNewPasswordFormItem.setType(FormItemType.PASSWORD_ITEM.getValue());
		lNewPasswordFormItem.setLength(30);
		lNewPasswordFormItem.setTitle(I18NUtil.getMessages().cambioPassword_newPasswordItem_title());  
		lNewPasswordFormItem.setWidth(250);
		lNewPasswordFormItem.setColSpan(4);
		lNewPasswordFormItem.setRequired(true);
		
		// CONFERMA PASSWORD
		PasswordItem lConfirmPasswordFormItem = new PasswordItem();
		lConfirmPasswordFormItem.setName("confermaPassword");
		lConfirmPasswordFormItem.setType(FormItemType.PASSWORD_ITEM.getValue());
		lConfirmPasswordFormItem.setLength(30);
		lConfirmPasswordFormItem.setTitle(I18NUtil.getMessages().cambioPassword_confermaPasswordItem_title());
		lConfirmPasswordFormItem.setWidth(250);
		lConfirmPasswordFormItem.setColSpan(4);
		lConfirmPasswordFormItem.setRequired(true);
		
		setFields(lOldPasswordItem, lNewPasswordFormItem, lConfirmPasswordFormItem);
		setAlign(Alignment.CENTER);
	}
	
	public void cambioPasswordAction(String formCall) {
		
		final String lformCall = formCall;
		
		saveData(new DSCallback() {
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {				
				if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Layout.addMessage(new MessageBean(I18NUtil.getMessages().cambioPassword_esitoValidazione_OK_value(), "", MessageType.INFO));
					
					if (lformCall.equalsIgnoreCase("menu")){
						cambioPasswordWindow.markForDestroy();	
					}
					else if (lformCall.equalsIgnoreCase("login")){
						JavaScriptObject lJavaScriptObject = (JavaScriptObject)rawData;
						Record lRecord = new Record(lJavaScriptObject);
						if (lRecord.getAttributeAsBoolean("changeOk")){
							cambioPasswordWindow.passwordOk();
						}	
					}
				}	
			}
		});
	}
}
