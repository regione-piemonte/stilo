/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.FieldType;

import it.eng.auriga.ui.module.layout.client.attiAutorizzazione.AttiAutorizzazioneAnnRegLayout;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public abstract class LookupAttiAutorizzazioneAnnRegPopup extends ModalWindow {

	private LookupAttiAutorizzazioneAnnRegPopup _window;

	private AttiAutorizzazioneAnnRegLayout portletLayout;

	public LookupAttiAutorizzazioneAnnRegPopup(final RecordList listaRegDaAnnullare) {
		
		super("atti_autorizzazione_ann_reg", true);
		
		_window = this;
		
		setTitle("Atti di autorizzazione all'annullamento di registrazione");
		
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
		
		final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AttiAutorizzazioneAnnRegDatasource", true, "idAtto", FieldType.TEXT);
		lGwtRestDataSource.addParam("restringiAttiAutAnnReg", "aperti");
		
		portletLayout = new AttiAutorizzazioneAnnRegLayout(lGwtRestDataSource) {
			
			@Override
			public void lookupBack(Record selectedRecord) {
				
				manageLookupBack(selectedRecord);		
				_window.markForDestroy();
			}
			
			@Override
			public void hideDetail(boolean reloadList) {
				
				super.hideDetail(reloadList);
				if(fullScreenDetail) {			
					_window.setTitle("Atti di autorizzazione all'annullamento di registrazione"); 
				} 	
			}	
		};
		portletLayout.setLookup(true);
		
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		
		setBody(portletLayout);
		
		setIcon("menu/atti_autorizzazione.png");
		
	}	
	
	public abstract void manageLookupBack(Record selectedRecord);

}
