/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.Map;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.rpc.RPCCallback;
import com.smartgwt.client.rpc.RPCManager;
import com.smartgwt.client.rpc.RPCRequest;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.CharacterCasing;
import com.smartgwt.client.types.FormItemType;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;

import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;

public class LoginForm extends DynamicForm{

	private LoginWindow loginWindow;
	private DynamicForm form;

	public LoginForm(LoginWindow pLoginWindow){
		form = this;
		loginWindow = pLoginWindow;
		setKeepInParentRect(true);
		setWidth100();
		setHeight100();
		setNumCols(2);
		setColWidths(80,120);
		setCellPadding(5);
//		setProperty("rowSpan", 10);
		TextItem lUsernameFormItem = new TextItem();
		lUsernameFormItem.setName("j_username");
		lUsernameFormItem.setCharacterCasing(CharacterCasing.UPPER);
		lUsernameFormItem.setTitle(I18NUtil.getMessages().usernameItem_title());
		lUsernameFormItem.setWidth(200);
		lUsernameFormItem.addKeyPressHandler(new KeyPressHandler() {			
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals("Enter")){
					form.focusInItem("j_password");					
				}
			}
		});

		FormItem lPasswordFormItem = new FormItem();
		lPasswordFormItem.setName("j_password");
		lPasswordFormItem.setType(FormItemType.PASSWORD_ITEM.getValue());
		lPasswordFormItem.setTitle(I18NUtil.getMessages().passwordItem_title());  
		lPasswordFormItem.setWidth(200);
		lPasswordFormItem.addKeyPressHandler(new KeyPressHandler() {			
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals("Enter")){
					provideLoginAction();					
				}
			}
		});

		ButtonItem lButtonItem = new ButtonItem();
		lButtonItem.setName("login");
		lButtonItem.setTitle(I18NUtil.getMessages().loginButton_title());		
		lButtonItem.setColSpan(2);
		lButtonItem.setWidth(100);
		lButtonItem.setTop(20);
		lButtonItem.setAlign(Alignment.CENTER);
		lButtonItem.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				provideLoginAction();				
			}

		});

		setFields(new FormItem[]{lUsernameFormItem, lPasswordFormItem, lButtonItem});
		setAlign(Alignment.CENTER);
		setTop(50);
	}

	private void provideLoginAction() {
		RPCRequest request = new RPCRequest();
		request.setContainsCredentials(true);
		request.setActionURL("j_security_check");
		request.setUseSimpleHttp(true);
		request.setShowPrompt(false);
		Map params = new HashMap();
		// adjust parameter names to match your authentication system
		String username = (String) getValue("j_username");
		String password = getValue("j_password")!=null ? (String) getValue("j_password") : "";
		
		// Pulisco username e password dallo script injection
		username = GWTRestDataSource.clearFromScript(username);
		password = GWTRestDataSource.clearFromScript(password);
		
		params.put("j_username", username);
		params.put("j_password", password);
		request.setParams(params);
		RPCManager.sendRequest(request,new RPCCallback(){
			public void execute(RPCResponse response, Object rawData, RPCRequest request) {
				if (response.getStatus() == RPCResponse.STATUS_SUCCESS) {
					loginWindow.getIndex().createSessionLoginInfo(new ServiceCallback<Record>() {						
						@Override
						public void execute(Record object) {
							// get rid of login window
							RPCManager.resendTransaction();	
							try {	                	 
								if (loginWindow.getIndex().getLayout().getConfigured())
									loginWindow.getIndex().getLayout().aggiornaUtente();	                	 	                	 
							} catch(Exception e) {};
							loginWindow.hide();
						}
					});													
				} else { 
					loginWindow.getIndex().getLayout().addMessage(new MessageBean(I18NUtil.getMessages().loginError_message(), "", MessageType.ERROR));	                
				}
			}
		});
	}
}
