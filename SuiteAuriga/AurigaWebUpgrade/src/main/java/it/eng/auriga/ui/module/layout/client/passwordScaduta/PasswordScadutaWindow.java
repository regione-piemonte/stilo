/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.AurigaLoginForm;

import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;

public class PasswordScadutaWindow extends Window{
	
	private DynamicForm form; 
	private AurigaLoginForm login;
	
	public PasswordScadutaWindow(AurigaLoginForm form2){
		
		setIsModal(true);
		
		login = form2;
		
		setModalMaskOpacity(50);
		setAutoCenter(true);
		setWidth(350);		
		setHeight(180);
		setKeepInParentRect(true);
		setTitle("Richiesta cambio password");
		setShowModalMask(true);
		setShowCloseButton(false);
		setShowMaximizeButton(false);
		setShowMinimizeButton(false);
		
		form = new PasswordScadutaForm(this);
		
		addItem(form);		
		
		setShowTitle(true);	
		
	}
	
	public void passwordOk(){
		this.markForDestroy();
		login.passowrdOk();
	}

}
