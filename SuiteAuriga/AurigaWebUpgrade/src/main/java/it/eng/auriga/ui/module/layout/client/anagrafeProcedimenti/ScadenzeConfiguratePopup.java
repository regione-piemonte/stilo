/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

import com.smartgwt.client.widgets.Canvas;

public class ScadenzeConfiguratePopup extends ModalWindow{
	
	private ScadenzeConfiguratePopup _window;
	protected Canvas portletLayout;

	public ScadenzeConfiguratePopup(final GWTRestDataSource lGwtRestDataSource, String idProcessType, final String nomeProcessType) {
		super("scadenze_configurate",true);
		
		_window=this;
		
		setTitle("Scadenze Configurate");
		
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
		portletLayout = new ScadenzeConfigurateLayout(lGwtRestDataSource, idProcessType) {
			@Override
			public boolean isForcedToAutoSearch() {
				return true;
			}
			@Override
			public void hideDetail(boolean reloadList) {
				super.hideDetail(reloadList);
				if(fullScreenDetail) {			
					_window.setTitle("Scadenze configurate per procedimento " + nomeProcessType); 
				} 	
			}	
		};
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		setBody(portletLayout);		

		setIcon("buttons/scadenze.png");
		
	}

}
