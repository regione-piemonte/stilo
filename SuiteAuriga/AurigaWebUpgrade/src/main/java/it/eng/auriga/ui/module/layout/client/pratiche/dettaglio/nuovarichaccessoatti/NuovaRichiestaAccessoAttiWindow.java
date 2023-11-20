/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import it.eng.utility.ui.module.layout.client.portal.ModalWindow;


public class NuovaRichiestaAccessoAttiWindow extends ModalWindow {

	private NuovaRichiestaAccessoAttiWindow _window;
	private NuovaRichiestaAccessoAttiDetail portletLayout;
	
	public NuovaRichiestaAccessoAttiWindow(Map<String, Object> initialValues) {
	
		super("nuova_richiesta_accesso_atti", true);
		
		setTitle("Dettaglio richiesta accesso atto");  	
		
		_window = this;
		
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
		   
		portletLayout = new NuovaRichiestaAccessoAttiDetail("nuova_richiesta_accesso_atti");
		portletLayout.editNewRecord(initialValues);
		portletLayout.newMode();
			
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		
		setBody(portletLayout);
    
       	setIcon("blank.png");          
	}
	
}