/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

import com.smartgwt.client.data.Record;

public class TipologieDocumentaliWindow  extends ModalWindow 
{
	
	private TipologieDocumentaliLayout portletLayout;
	private TipologieDocumentaliWindow _window;
	protected String windowTitle;
	
	protected String idPianoClassif;
	protected Boolean flgSoloAttive;
	protected String tsRif;

	public TipologieDocumentaliWindow(String title, final Record record) 
	{
		super("titolarioDocumentale", true);
		
		_window = this;
		
		windowTitle = title + " " + record.getAttributeAsString("codRapidoUo")  + " - " + record.getAttributeAsString("denominazioneEstesa");
		setTitle(windowTitle);
		
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
			
		portletLayout = new TipologieDocumentaliLayout(null,null,null,record)
		{
			@Override
			public boolean isForcedToAutoSearch() {
				
				return true;
			}
			
			@Override
			public void hideDetail(boolean reloadList) {
				
				super.hideDetail(reloadList);
				if(fullScreenDetail) {			
					_window.setTitle(windowTitle); 
				} 	
			}
		};
		
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		
		setBody(portletLayout);		
		
		String icona = "tipologieDocumentali.png";
		
		if (record.getAttributeAsString("icona")!=null)
			icona = record.getAttributeAsString("icona");
		
		
		setIcon("menu/"+icona);
	}

}
