/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.Styles;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.i18n.I18NUtil;

import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;

public class AurigaLoginWindow extends Window{
	
	private AurigaIndex index;
	private AurigaLoginForm form; 
	private Boolean showResetPasswordLogin;

	// Cacco Federico 02-12-2015
	// Mantis 0000027
	// Devo tenere traccia se la login è dovuta allo scadere della sessione
	public AurigaLoginWindow(AurigaIndex pIndex){		
		index = pIndex;		
		setShowResetPasswordLogin(  (index.showResetPasswordLogin!=null && !index.showResetPasswordLogin.equalsIgnoreCase("") ? index.showResetPasswordLogin.equalsIgnoreCase("true") : false  )  );		
		setIsModal(true);
		setModalMaskOpacity(50);
		setAutoCenter(true);
		setWidth(370);				
		if (isShowResetPasswordLogin()) {
			setHeight(180);
		} else {
			setHeight(140);
		}		
		setKeepInParentRect(true);
		setTitle(I18NUtil.getMessages().loginWindow_title());
		setShowModalMask(true);
		setModalMaskStyle(Styles.loginModalMask);
		setShowCloseButton(false);
		setShowMaximizeButton(false);
		setShowMinimizeButton(false);
		
		form = new AurigaLoginForm(this);
		GWTRestDataSource datasource = new GWTRestDataSource("LoginFormDataSource");
		form.setDataSource(datasource);
		form.setUseAllDataSourceFields(true);
		form.setCanSubmit(true);
		addItem(form);		
		setShowTitle(true);		
	}

	public DynamicForm getForm() {
		return form;
	}

	public void setForm(AurigaLoginForm form) {
		this.form = form;
	}

	public AurigaIndex getIndex() {
		return index;
	}

	public boolean isShowResetPasswordLogin() {
		return showResetPasswordLogin;
	}

	public void setShowResetPasswordLogin(boolean showResetPasswordLogin) {
		this.showResetPasswordLogin = showResetPasswordLogin;
	}
	
	public void setIgnoraControlloLingua(boolean ignoraControlloLingua){
		form.setIgnoraControlloLingua(ignoraControlloLingua);
	}
	
	public boolean isIgnoraControlloLingua(){
		return form.isIgnoraControlloLingua();
	}
	
}