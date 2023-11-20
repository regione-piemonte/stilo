/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.Canvas;

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class InviiEffettuatiWindow extends ModalWindow {

	protected InviiEffettuatiWindow _window;
	protected String windowTitle;

	protected Canvas portletLayout;
	
	public InviiEffettuatiWindow(final GWTRestDataSource lGwtRestDataSource) {
		this(lGwtRestDataSource, null, null);
	}
	
	public InviiEffettuatiWindow(final GWTRestDataSource lGwtRestDataSource, final Record record) {
		this(lGwtRestDataSource, record, null);
	}

	public InviiEffettuatiWindow(final GWTRestDataSource lGwtRestDataSource, final Record record, String pTitle) {
		
		super("invii_effettuati", true);
		
		_window = this;
		
		windowTitle = pTitle != null && !"".equals(pTitle) ? pTitle : "Modifica/annulla ass./invii cc";
		
		setTitle(windowTitle);
		
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
		
		portletLayout = new InviiEffettuatiLayout(lGwtRestDataSource,record) {
			
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
		
		setIcon("archivio/inviiEffettuati.png");
	}	

}