/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Record;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public abstract class LookupTitolarioCogitoPopup extends ModalWindow {

	private LookupTitolarioCogitoPopup _window;
	
	private TitolarioCogitoLayout portletLayout;
	
	public LookupTitolarioCogitoPopup(final Record filterValues, boolean flgSelezioneSingola) {
		
		super("titolarioCogito", true);
		
		setTitle(I18NUtil.getMessages().titolarioCogito_lookupTitolarioCogitoPopup_title());  	
		
		_window = this;
			
		if(filterValues != null) {
			settingsMenu.removeItem(separatorMenuItem);
			settingsMenu.removeItem(autoSearchMenuItem);
		}
				
		portletLayout = new TitolarioCogitoLayout(getFinalita(), flgSelezioneSingola, null, getInfoPrimarioEAllegati() , getUoLavoro()) {
			
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
					_window.setTitle(I18NUtil.getMessages().titolarioCogito_lookupTitolarioCogitoPopup_title()); 
				} 	
			}
			
			@Override
			public void setCriteriaAndFirstSearch(AdvancedCriteria criteria, boolean autoSearch) {
			}
			
			@Override
			public void setDefaultCriteriaAndFirstSearch(boolean autosearch) {
			}
		};
			
		portletLayout.setLookup(true);
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		setBody(portletLayout);
        setIcon("lookup/lampadina.png");
        portletLayout.doSearch();
	}
	
	public String getFinalita() {
		return null;
	};
 
	public abstract void manageLookupBack(Record record);	
	public abstract void manageMultiLookupBack(Record record);
	public abstract void manageMultiLookupUndo(Record record);
	public abstract Record getInfoPrimarioEAllegati();
	public abstract String getUoLavoro();	
}
