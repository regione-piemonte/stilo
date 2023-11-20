/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;


public abstract class LookupTipoFascicoliAggregatiPopup extends ModalWindow {

	private LookupTipoFascicoliAggregatiPopup _window;
	
	private TipoFascicoliAggregatiLayout portletLayout;
	
	public LookupTipoFascicoliAggregatiPopup(String finalita, Boolean flgSelezioneSingola) {
		
		super("lookup_tipo_fascicoli_aggregati", true);
		
		setTitle(getWindowTitle());  	
		
		_window = this;
			
		portletLayout = new TipoFascicoliAggregatiLayout(finalita, flgSelezioneSingola) {
			
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
					_window.setTitle(getWindowTitle()); 
				} 	
			}
			
			@Override
			public boolean showNewButton(){
				return getShowNewButton();
			}	
			
			@Override
			public boolean isForcedToAutoSearch() {	
				return true;
			}
		};
		
		portletLayout.setLookup(true);
		
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		
		setBody(portletLayout);
                
        setIcon("menu/tipo_fascicoli_aggr.png");
         
	}
	
	public String getWindowTitle() {
		return I18NUtil.getMessages().tipofascicoliaggr_lookupTipoFascicoliAggregatiPopup_title();
	}
	
	@Override
	public void manageOnCloseClick() {
		if(getIsModal()) {
			markForDestroy();
		} else {
			Layout.removePortlet(getNomeEntita());
		}	
		
		int size = portletLayout.getMultiLookupGridSize();
		
		if (size>0)
			afterManageOnCloseClick(true);
		else
			afterManageOnCloseClick(false);
	}
	
	public abstract void manageLookupBack(Record record);	
	public abstract void manageMultiLookupBack(Record record);
	public abstract void manageMultiLookupUndo(Record record);	
	public abstract boolean getShowNewButton();
	public abstract void afterManageOnCloseClick(boolean flgApriCambiaPrivilegiSubProfiliPopup);	
}