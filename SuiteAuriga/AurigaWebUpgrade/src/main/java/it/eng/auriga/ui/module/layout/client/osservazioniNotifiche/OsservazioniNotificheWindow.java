/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.widgets.layout.VLayout;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class OsservazioniNotificheWindow extends ModalWindow {

	private OsservazioniNotificheWindow _window;
	
	private VLayout portletLayout;
	private OsservazioniNotificheLayout listaOsservazioniNotificheLayout;
	private OsservazioneNotificaDetail nuovaOsservazioneNotificaLayout;
	
	public OsservazioniNotificheWindow(String idUdFolder, String flgUdFolder, String title) {
	
		super("osservazione_notifiche", true);
		
		setTitle(title);		
		
		_window = this;
					
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);		
		
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AurigaOsservazioniNotificheDataSource");
		lGwtRestDataSource.addParam("idUdFolder", idUdFolder);			
		lGwtRestDataSource.addParam("flgUdFolder", flgUdFolder);			
			
		listaOsservazioniNotificheLayout = new OsservazioniNotificheLayout(lGwtRestDataSource) {							
			@Override
			public boolean isForcedToAutoSearch() {

				return true;
			}				
		};
			
		((OsservazioniNotificheLayout)listaOsservazioniNotificheLayout).setLookup(false);
		listaOsservazioniNotificheLayout.setHeight(220);
		listaOsservazioniNotificheLayout.setWidth100();
		
		nuovaOsservazioneNotificaLayout = new OsservazioneNotificaDetail("osservazione_notifica",idUdFolder,flgUdFolder,listaOsservazioniNotificheLayout);
		nuovaOsservazioneNotificaLayout.setHeight(300);
		
		portletLayout = new VLayout();
		
		portletLayout.addMember(listaOsservazioniNotificheLayout);
		portletLayout.addMember(nuovaOsservazioneNotificaLayout);
		
		setBody(portletLayout);
        		
		setHeight(540);
		
        setIcon("protocollazione/operazioniEffettuate.png");
        
        show();        
	}
}
