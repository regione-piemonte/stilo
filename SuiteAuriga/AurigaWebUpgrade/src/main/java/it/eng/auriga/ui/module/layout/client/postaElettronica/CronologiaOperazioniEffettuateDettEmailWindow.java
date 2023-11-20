/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

import com.smartgwt.client.widgets.Canvas;


public class CronologiaOperazioniEffettuateDettEmailWindow extends ModalWindow {

	private CronologiaOperazioniEffettuateDettEmailWindow _window;
	
	private Canvas portletLayout;	
	
	public CronologiaOperazioniEffettuateDettEmailWindow(String idEmail,String title) {
	
		super("cronologia_operazioni_effettuate", true);
		
		setTitle(title);		
		
		_window = this;
					
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);		
		
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("CronologiaOperazioniEffettuateDettEmailDataSource");
		lGwtRestDataSource.addParam("idEmail", idEmail);
		
		portletLayout = new CronologiaOperazioniEffettuateDettEmailLayout(lGwtRestDataSource) {							
			@Override
			public boolean isForcedToAutoSearch() {
				return true;
			}				
		};
			
		((CronologiaOperazioniEffettuateDettEmailLayout)portletLayout).setLookup(false);
		
		portletLayout.setHeight100();
		portletLayout.setWidth100();
	
		setBody(portletLayout);
        		
        setIcon("protocollazione/operazioniEffettuate.png");
        
        show();
        
	}

}
