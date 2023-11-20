/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public abstract class LookupGruppiPopup extends ModalWindow {

	private LookupGruppiPopup _window;
	
	private GruppiSoggettiLayout portletLayout;
	
	public LookupGruppiPopup(final Record filterValues, String finalita, boolean flgSelezioneSingola) {
		
		super("gruppi_soggetti", true);
		
		setTitle(I18NUtil.getMessages().gruppisoggetti_lookupGruppiPopup_title());  	
		
		_window = this;
		
		if(filterValues != null) {
			settingsMenu.removeItem(separatorMenuItem);
			settingsMenu.removeItem(autoSearchMenuItem);
		}
				
		portletLayout = new GruppiSoggettiLayout(finalita, flgSelezioneSingola) {
			
			@Override
			public void lookupBack(Record selectedRecord) {
				
				manageLookupBack(selectedRecord);
				_window.markForDestroy();	
			}
			
			@Override
			public void multiLookupBack(Record record) {
								
				manageMultiLookupBack(record);
			}
			
			@Override
			public void multiLookupUndo(Record record) {
								
				manageMultiLookupUndo(record);
			}
			
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
					_window.setTitle(I18NUtil.getMessages().gruppisoggetti_lookupGruppiPopup_title()); 
				} 	
			}			
		};
		
		portletLayout.setLookup(true);
		
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		
		setBody(portletLayout);
         
        setIcon("menu/gruppi_soggetti.png");
                  
	}

	public abstract void manageLookupBack(Record record);	
	public abstract void manageMultiLookupBack(Record record);
	public abstract void manageMultiLookupUndo(Record record);
	
}
