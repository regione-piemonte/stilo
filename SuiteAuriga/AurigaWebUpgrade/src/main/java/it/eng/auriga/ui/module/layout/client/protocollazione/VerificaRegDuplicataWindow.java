/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

import com.smartgwt.client.widgets.Canvas;

public class VerificaRegDuplicataWindow extends ModalWindow {

	VerificaRegDuplicataWindow _window;

	protected Canvas portletLayout;

	public VerificaRegDuplicataWindow(final GWTRestDataSource lGwtRestDataSource) {
		
		super("verifica_reg_duplicata", true);
		
		_window = this;
		
		setTitle("Lista possibili registrazioni duplicate");

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
		
		portletLayout = new VerificaRegDuplicataLayout(lGwtRestDataSource) {
			@Override
			public boolean isForcedToAutoSearch() {
				
				return true;
			}
			
			@Override
			public void hideDetail(boolean reloadList) {
				
				super.hideDetail(false); // il dettaglio è readonly perciò non serve ricaricare la lista ogni volta che lo chiudo
				if(fullScreenDetail) {			
					_window.setTitle("Lista possibili registrazioni duplicate"); 
				} 	
			}	
		};
		
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		
		setBody(portletLayout);

		setIcon("buttons/verificaDati.png");
		
	}

}
