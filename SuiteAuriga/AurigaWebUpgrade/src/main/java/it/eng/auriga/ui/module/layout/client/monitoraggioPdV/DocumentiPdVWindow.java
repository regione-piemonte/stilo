/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.widgets.Canvas;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class DocumentiPdVWindow extends ModalWindow {

	private DocumentiPdVWindow window;
	private Canvas body;

	public DocumentiPdVWindow(final String idPdV) {
		
		super("documentiPdV", true);
		
		window = this;
		
		setTitle(I18NUtil.getMessages().monitoraggioPdV_list_documentiPdVButtonField_title(idPdV));
		
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
		
		body = new DocumentiPdVLayout(idPdV) {
			
			@Override
			public boolean isForcedToAutoSearch() {
				return true;
			}
			
			@Override
			public void hideDetail(boolean reloadList) {
				
				super.hideDetail(reloadList);
				
				if(fullScreenDetail) {			
					window.setTitle(I18NUtil.getMessages().monitoraggioPdV_list_documentiPdVButtonField_title(idPdV)); 
				} 	
			}
		};
		
		body.setHeight100();
		body.setWidth100();
		
		setBody(body);		
		
		setIcon("menu/documentiPdV.png");
			
	}	

}
