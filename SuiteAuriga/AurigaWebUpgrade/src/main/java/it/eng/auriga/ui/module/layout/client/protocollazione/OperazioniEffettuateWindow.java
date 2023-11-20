/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.widgets.Canvas;

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;


public class OperazioniEffettuateWindow extends ModalWindow {

	private OperazioniEffettuateWindow _window;
	
	private Canvas portletLayout;
	
	public OperazioniEffettuateWindow(String idUdFolder, String flgUdFolder, String title) {
		super("operazioni_effettuate", true);
		
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("OperazioniEffettuateDataSource");
		lGwtRestDataSource.addParam("operazione", "ud_fascicoli");			
		lGwtRestDataSource.addParam("idUdFolder", idUdFolder);			
		lGwtRestDataSource.addParam("flgUdFolder", flgUdFolder);
		
		new OperazioniEffettuateWindow(lGwtRestDataSource, title);
	}
	
	public OperazioniEffettuateWindow(String idSeduta, String title) {
		super("operazioni_effettuate", true);
		
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("OperazioniEffettuateDataSource");
		lGwtRestDataSource.addParam("operazione", "convocazione_discussioneSeduta");			
		lGwtRestDataSource.addParam("idSeduta", idSeduta);	
		
		new OperazioniEffettuateWindow(lGwtRestDataSource, title);
	}
		
	
	public OperazioniEffettuateWindow(GWTRestDataSource lGwtRestDataSource, String title) {
	
		super("operazioni_effettuate", true);
		
		setTitle(title);		
		
		_window = this;
					
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);				
			
		portletLayout = new OperazioniEffettuateLayout(lGwtRestDataSource) {							
			@Override
			public boolean isForcedToAutoSearch() {

				return true;
			}				
		};
			
		((OperazioniEffettuateLayout)portletLayout).setLookup(false);
		
		portletLayout.setHeight100();
		portletLayout.setWidth100();
	
		setBody(portletLayout);
        		
        setIcon("protocollazione/operazioniEffettuate.png");
        
        show();
        
	}

}