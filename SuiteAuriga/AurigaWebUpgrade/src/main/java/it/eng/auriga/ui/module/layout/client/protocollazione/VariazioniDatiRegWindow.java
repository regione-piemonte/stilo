/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

import com.smartgwt.client.widgets.Canvas;


public class VariazioniDatiRegWindow extends ModalWindow {

	private VariazioniDatiRegWindow _window;
	
	private Canvas portletLayout;	
	
	public VariazioniDatiRegWindow(String idUd, final String estremi) {
	
		super("variazioni_dati_reg", true);
		
		setTitle(I18NUtil.getMessages().variazionidatireg_window_title(estremi));  	
		
		_window = this;
					
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);		
		
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("VariazioniDatiRegDataSource");
		lGwtRestDataSource.addParam("idUd", idUd);			
			
		portletLayout = new VariazioniDatiRegLayout(lGwtRestDataSource) {				
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
					_window.setTitle(I18NUtil.getMessages().variazionidatireg_window_title(estremi)); 
				} 	
			}				
			
			@Override
			public boolean isForcedToAutoSearch() {
				
				return true;
			}				
		};
			
		((VariazioniDatiRegLayout)portletLayout).setLookup(false);
		
		portletLayout.setHeight100();
		portletLayout.setWidth100();
	
		setBody(portletLayout);
        		
        setIcon("protocollazione/variazioni.png");
        
        show();
        
	}

}
