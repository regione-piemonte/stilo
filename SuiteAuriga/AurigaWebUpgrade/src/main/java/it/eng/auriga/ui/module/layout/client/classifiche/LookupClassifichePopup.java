/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public abstract class LookupClassifichePopup extends ModalWindow {

	private LookupClassifichePopup _window;
	
	private ClassificheLayout portletLayout;
	
	public LookupClassifichePopup(String finalita, Boolean flgSelezioneSingola, Boolean showOnlyDetail, Record record) {
		
		super("lookup_classifiche", true);
		
		setTitle(getWindowTitle());  	
		
		_window = this;
			
		portletLayout = new ClassificheLayout(finalita, flgSelezioneSingola, showOnlyDetail, record) {
			
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
			public boolean isForcedToAutoSearch() {	
				return true;
			}
		};
		
		portletLayout.setLookup(true);
		
		portletLayout.setMultiselect(false);
		
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		
		setBody(portletLayout);
                
        setIcon("menu/titolario.png");
         
	}
	
	public String getWindowTitle() {
		return I18NUtil.getMessages().titolario_lookupClassifichePopup_title();
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
	public abstract void afterManageOnCloseClick(boolean flgApriCambiaPrivilegiSubProfiliPopup);	
}
