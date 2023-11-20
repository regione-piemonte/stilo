/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.AurigaLoginForm;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;


public class CambioPasswordWindow extends Window{
	
	protected CambioPasswordWindow window;
	private CambioPasswordForm form; 	
	
	private AurigaLoginForm loginForm;
	
	public CambioPasswordWindow(AurigaLoginForm loginForm) {
		this(loginForm, "menu");
	}
	
	
	public CambioPasswordWindow(AurigaLoginForm pLoginForm, String pCallFrom){
		
		final String callFrom = pCallFrom;
		
		window = this;
		loginForm = pLoginForm;
		
		setIsModal(true);
		setModalMaskOpacity(50);
		setAutoCenter(true);
		setWidth(480);		
		setHeight(170);
		setKeepInParentRect(true);
		setTitle(I18NUtil.getMessages().cambioPasswordWindow_title());		
		setShowModalMask(true);
		setShowCloseButton(false);
		setShowMaximizeButton(false);
		setShowMinimizeButton(false);
		setShowTitle(true);	
		setHeaderControls(HeaderControls.HEADER_LABEL, HeaderControls.CLOSE_BUTTON);
		
		form = new CambioPasswordForm(this);
		
		// CONFERMA button
		Button confermaButton = new Button(I18NUtil.getMessages().cambioPassword_confermaButton_title());
		confermaButton.setIcon("ok.png");
		confermaButton.setIconSize(16); 
		confermaButton.setAutoFit(false);
		confermaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				form.cambioPasswordAction(callFrom);	
			}
		});
				
		// ANNULLA button
		Button annullaButton = new Button(I18NUtil.getMessages().cambioPassword_annullaButton_title());
		annullaButton.setIcon("annulla.png");
		annullaButton.setIconSize(16); 
		annullaButton.setAutoFit(false);
		if (callFrom.equalsIgnoreCase("menu")){			
			annullaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {			
				@Override
				public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
					if (window != null){
						window.markForDestroy();
					}
				}
			});		
		}		
		
		if (callFrom.equalsIgnoreCase("login")){
			annullaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {			
				@Override
				public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
					logout();
				}
			});	
		}
		HStack buttons = new HStack(5);
		buttons.setAlign(Alignment.CENTER);
		buttons.setPadding(5);
		buttons.addMember(confermaButton);		
		buttons.addMember(annullaButton);
		
		setAlign(Alignment.CENTER);
		setTop(50);
		
		VLayout layout = new VLayout();
		layout.setHeight100();
		layout.setWidth100();
				
		layout.addMember(form);		
		layout.addMember(buttons);
		
		addItem(layout);		
		
		setHeaderIcon("menu/cambiopwd.png");
		
	}

	public DynamicForm getForm() {
		return form;
	}

	public void setForm(CambioPasswordForm form) {
		this.form = form;
	}
	
	
	public void logout(){
		this.markForDestroy();
		loginForm.logout();	
	}
	
	public void passwordOk(){
		this.markForDestroy();
		loginForm.passowrdOk();
	}
	
	
}
