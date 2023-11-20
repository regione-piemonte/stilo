/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

import com.smartgwt.client.types.FieldType;

public class AttiAutorizzazioneAnnRegWindow extends ModalWindow {

	private AttiAutorizzazioneAnnRegWindow _window;

	private AttiAutorizzazioneAnnRegLayout portletLayout;

	public AttiAutorizzazioneAnnRegWindow() {
		
		super("atti_autorizzazione_ann_reg", false);
		
		_window = this;
		
		setTitle("Lista atti di autorizzazione");
		
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
		
		final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AttiAutorizzazioneAnnRegDatasource", true, "idAtto", FieldType.TEXT);
		lGwtRestDataSource.addParam("restringiAttiAutAnnReg", "tutti");
		
		portletLayout = new AttiAutorizzazioneAnnRegLayout(lGwtRestDataSource) {					
			@Override
			public void hideDetail(boolean reloadList) {
				
				super.hideDetail(reloadList);
				if(fullScreenDetail) {			
					_window.setTitle("Lista atti di autorizzazione"); 
				} 	
			}	
		};
		
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		
		setBody(portletLayout);

		setIcon("menu/atti_autorizzazione.png");
				
	}	

}
