/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

import com.smartgwt.client.data.Record;

public class RegistriNumerazioneWindow  extends ModalWindow 
{
	
	private RegistriNumerazioneLayout portletLayout;
	private RegistriNumerazioneWindow _window;
	protected String windowTitle;
	
	protected String idPianoClassif;
	protected Boolean flgSoloAttive;
	protected String tsRif;

	public RegistriNumerazioneWindow(String title, final Record record) 
	{
		super("registri_numerazione", true);
		
		_window = this;
		
		windowTitle = title + " " + record.getAttributeAsString("codRapidoUo")  + " - " + record.getAttributeAsString("denominazioneEstesa");
		setTitle(windowTitle);
		
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
			
		portletLayout = new RegistriNumerazioneLayout(null,null,null,record)
		{
			@Override
			public boolean isForcedToAutoSearch() {
				
				return true;
			}
			
			@Override
			public void hideDetail(boolean reloadList) {
				// TODO Auto-generated method stub
				super.hideDetail(reloadList);
				if(fullScreenDetail) {			
					_window.setTitle(windowTitle); 
				} 	
			}
		};
		
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		
		setBody(portletLayout);		
		
		setIcon("menu/registri_numerazione.png");
	}

}
