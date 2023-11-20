/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class RegoleProtocollazioneAutomaticaCaselleWindow extends ModalWindow {

	private RegoleProtocollazioneAutomaticaCaselleWindow _window;
	
	private RegoleProtocollazioneAutomaticaCaselleLayout portletLayout;
	
	public RegoleProtocollazioneAutomaticaCaselleWindow() {
		super("regole_protocollazione_automatica_caselle_pec_peo", false);
		
		_window = this;
		
		setTitle("Regole di protocollazione automatica su caselle PEC/PEO");
		
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
		
		portletLayout = new RegoleProtocollazioneAutomaticaCaselleLayout() {
			@Override
			public void hideDetail(boolean reloadList) {
				
				super.hideDetail(reloadList);
				if(fullScreenDetail) {			
					_window.setTitle("Regole di protocollazione automatica su caselle PEC/PEO"); 
				} 	
			}	
		};
		
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		
		setBody(portletLayout);
	}
}
