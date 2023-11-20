/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.Canvas;

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

/**
 * 
 * @author DANCRIST
 *
 */

public class MembriGruppoUdWindow extends ModalWindow {
	
	private MembriGruppoUdWindow _window;
	
	private Canvas portletLayout;	
	
	public MembriGruppoUdWindow(Record record) {
		
		super("membri_gruppo_ud", true);
		
		/**
		 *  D: Destinatari
		 *  A: Assegnatari
		 *  DIC: Destinatari invio in conoscenza
		 */
		String titoloGruppo = null;
		String soggettoGruppo = "";
		String tipologiaMembro = record.getAttributeAsString("tipologiaMembro");
		if(tipologiaMembro != null && !"".equals(tipologiaMembro)){
			if("D".equalsIgnoreCase(tipologiaMembro)){
				soggettoGruppo += "Destinatari ricompresi in ";
			} else if("A".equalsIgnoreCase(tipologiaMembro)){
				soggettoGruppo += "Assegnatari ricompresi in ";
			} else if("DIC".equalsIgnoreCase(tipologiaMembro)){
				soggettoGruppo += "Destinatari di invio in conoscenza ricompresi in ";
			}
			
			titoloGruppo = soggettoGruppo + record.getAttribute("nomeGruppo");
		} else {
			titoloGruppo = record != null && record.getAttribute("nomeGruppo") != null && !"".equals(record.getAttribute("nomeGruppo")) ?
						"Membri del gruppo " + record.getAttribute("nomeGruppo") : "Membri del gruppo";
		}
		
		setTitle(titoloGruppo);		
		
		_window = this;
					
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);		
		
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("MembriGruppoUdDataSource");
		lGwtRestDataSource.addParam("idUd", record.getAttributeAsString("idUd"));			
		lGwtRestDataSource.addParam("gruppo", record.getAttributeAsString("gruppo"));
		lGwtRestDataSource.addParam("nomeGruppo", record.getAttributeAsString("nomeGruppo"));
		lGwtRestDataSource.addParam("flgAssCondDest", record.getAttributeAsString("flgAssCondDest"));
			
		portletLayout = new MembriGruppoUdLayout(record.getAttributeAsString("flgAssCondDest"), lGwtRestDataSource) {	
			
			@Override
			public boolean isForcedToAutoSearch() {
				return true;
			}				
		};
			
		((MembriGruppoUdLayout)portletLayout).setLookup(false);
		
		portletLayout.setHeight100();
		portletLayout.setWidth100();
	
		setBody(portletLayout);
        		
        setIcon("menu/gruppi_soggetti.png");
        
        show();
	}

}