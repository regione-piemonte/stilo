/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.widgets.Canvas;

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

/**
 * 
 * @author dbe4235
 *
 */

public class CronologiaOperazioniWindow extends ModalWindow {

	private CronologiaOperazioniWindow _window;
	
	private Canvas portletLayout;
	
	public CronologiaOperazioniWindow(String idContenutoSezione) {
	
		super("cronologia_operazioni", true);
		
		setTitle("Cronologia operazioni");		
		
		_window = this;
					
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);	
		
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("CronologiaOperazioniDataSource");
		lGwtRestDataSource.addParam("idContenutoSezione", idContenutoSezione);			
		
		
		portletLayout = new CronologiaOperazioniLayout(lGwtRestDataSource) {
			
			@Override
			public boolean isForcedToAutoSearch() {

				return true;
			}				
		};
			
		((CronologiaOperazioniLayout)portletLayout).setLookup(false);
		
		portletLayout.setHeight100();
		portletLayout.setWidth100();
	
		setBody(portletLayout);
        		
        setIcon("protocollazione/operazioniEffettuate.png");
        
        show();
        
	}

}