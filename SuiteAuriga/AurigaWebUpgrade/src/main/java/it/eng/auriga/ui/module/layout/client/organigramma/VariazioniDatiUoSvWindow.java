/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.widgets.Canvas;

import it.eng.utility.ui.module.layout.client.portal.ModalWindow;


public class VariazioniDatiUoSvWindow extends ModalWindow {

	private VariazioniDatiUoSvWindow _window;
	
	private Canvas portletLayout;	
	
	public VariazioniDatiUoSvWindow(String idUoSv, String flgUoSv, final String estremi) {
	
		super("variazioni_dati_organigramma", true);
		
		if(flgUoSv != null && "SV".equals(flgUoSv)) {
			setTitle("Lista variazioni dati postazione "  + estremi);
		} else {
			setTitle(estremi);
		}
		
		_window = this;
					
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);		
		
		portletLayout = new VariazioniDatiUoSvLayout(this, idUoSv, flgUoSv) {				
			@Override
			public void showDetail() {
				super.showDetail();
				if(fullScreenDetail) {	
					String title = "";
					if(mode != null) {
						if(mode.equals("new")) {				
							title = getNewDetailTitle();
						} else if(mode.equals("edit")) {
							title = getEditDetailTitle();		
						} else if(mode.equals("view")) {
							title = getViewDetailTitle();
						}
					}
					_window.setTitle(title);											
				}
			}
			
			@Override
			public void hideDetail(boolean reloadList) {
				super.hideDetail(reloadList);
				if(fullScreenDetail) {		
					if(flgUoSv != null && "SV".equals(flgUoSv)) {
						_window.setTitle("Lista variazioni dati postazione "  + estremi);
					} else {
						_window.setTitle(estremi);
					}
				} 	
			}				
			
			@Override
			public boolean isForcedToAutoSearch() {
				return true;
			}				
		};
			
		((VariazioniDatiUoSvLayout)portletLayout).setLookup(false);
		
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		
		setBody(portletLayout);
        		
        setIcon("protocollazione/variazioni.png");
        
        show();   
	}
}