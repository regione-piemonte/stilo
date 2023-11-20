/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class LibroFirmaMassivaWindow extends ModalWindow {

	private LibroFirmaMassivaWindow _window;

	private LibroFirmaMassivaLayout portletLayout;

	public LibroFirmaMassivaWindow() {
		
		super("libro_firma", false);
		
		_window = this;
		
		setTitle("Lista documenti da rifirmare");
		
//		settingsMenu.removeItem(separatorMenuItem);
//		settingsMenu.removeItem(autoSearchMenuItem);
				
		portletLayout = new LibroFirmaMassivaLayout();
		
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		
		setBody(portletLayout);

		setIcon("file/mini_sign.png");
				
	}	

}
