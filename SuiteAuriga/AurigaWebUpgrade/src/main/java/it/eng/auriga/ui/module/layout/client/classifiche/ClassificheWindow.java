/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;

import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class ClassificheWindow extends ModalWindow {

	private ClassificheWindow _window;
	protected ClassificheLayout portletLayout;
	
	protected String idPianoClassif;
	protected Boolean flgSoloAttive;
	protected String tsRif;
	
	protected String windowTitle;
		
	public ClassificheWindow(String title, final Record record) {
		super("classifiche", true);
		
		_window = this;
		
		windowTitle = title + " " + record.getAttributeAsString("codRapidoUo")  + " - " + record.getAttributeAsString("denominazioneEstesa");
		setTitle(windowTitle);
		
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
			
		portletLayout = new ClassificheLayout(null, null, false, record) {
			
			@Override
			public boolean isForcedToAutoSearch() {	
				return true;
			}
			
			@Override
			public void hideDetail(boolean reloadList) {
				
				super.hideDetail(reloadList);
				if(fullScreenDetail) {			
					_window.setTitle(windowTitle); 
				} 	
			}
		};
		
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		
		setBody(portletLayout);		
		
		setIcon("menu/titolario.png");
	}
		
}