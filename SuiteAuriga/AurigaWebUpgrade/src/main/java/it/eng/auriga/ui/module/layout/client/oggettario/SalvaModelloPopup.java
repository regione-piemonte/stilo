/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;

import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public abstract class SalvaModelloPopup extends ModalWindow {

	private SalvaModelloPopup _window;
	
	private OggettarioLayout portletLayout;
	
	public SalvaModelloPopup(String flgTipoProv, final String codRapidoOggetto, final String oggetto) {
		
		super("oggettario.detail", true);
		
		_window = this;
		
		setTitle("Compila dati modello oggetto");  	
		
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
				
		portletLayout = new OggettarioLayout(flgTipoProv, true) {
			
			@Override
			public void lookupBack(Record record) {

				manageLookupBack(record);
				_window.markForDestroy();
			}
		};		
				
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		
		setBody(portletLayout);
	
		portletLayout.newMode();
		
		((OggettarioDetail)portletLayout.getDetail()).nomeItem.setValue(codRapidoOggetto);		
		((OggettarioDetail)portletLayout.getDetail()).oggettoItem.setValue(oggetto);		
		       
        setIcon("menu/oggettario.png");                     
        
	}
	
	@Override
	public void manageOnCloseClick() {
		if(getIsModal()) {
			markForDestroy();
		} else {
			Layout.removePortlet(getNomeEntita());
		}	
	}
	
	public abstract void manageLookupBack(Record record);
	
}