/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.i18n.I18NUtil;

public class LoginWindow extends Window{
	
	private Index index;
	private DynamicForm form; 
	
	public LoginWindow(Index pIndex){
		index = pIndex;
		setIsModal(true);
		setModalMaskOpacity(50);
		setAutoCenter(true);
		setWidth(350);		
		setHeight(125);
		setKeepInParentRect(true);
		setTitle(I18NUtil.getMessages().loginWindow_title());
		setShowModalMask(true);
		setShowCloseButton(false);
		setShowMaximizeButton(false);
		setShowMinimizeButton(false);
		form = new LoginForm(this);
		GWTRestDataSource lLoginFormDataSource = new GWTRestDataSource("LoginFormDataSource");
		lLoginFormDataSource.setParentCanvas(this);
		form.setDataSource(lLoginFormDataSource);
		form.setUseAllDataSourceFields(true);
		form.setCanSubmit(true);
		addItem(form);		
		setShowTitle(true);		
	}

	public DynamicForm getForm() {
		return form;
	}

	public void setForm(DynamicForm form) {
		this.form = form;
	}

	public Index getIndex() {
		return index;
	}
	
}
