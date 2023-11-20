/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class DocumentiDaRifirmareWindow extends ModalWindow {

	private DocumentiDaRifirmareWindow _window;

	private DocumentiDaRifirmareLayout portletLayout;

	public DocumentiDaRifirmareWindow() {
		
		super("documenti_da_rifirmare", false);
		
		_window = this;
		
		setTitle("Lista documenti da rifirmare");
		
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
				
		portletLayout = new DocumentiDaRifirmareLayout();
		
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		
		setBody(portletLayout);

		setIcon("file/mini_sign.png");
				
	}	

}
