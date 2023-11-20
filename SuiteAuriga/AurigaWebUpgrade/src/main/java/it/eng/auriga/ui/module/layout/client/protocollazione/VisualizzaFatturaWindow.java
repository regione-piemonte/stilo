/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.ContentsType;
import com.smartgwt.client.widgets.HTMLPane;

public class VisualizzaFatturaWindow extends ModalWindow {

	private VisualizzaFatturaWindow _window;
		
	public VisualizzaFatturaWindow(String title, Record record) {
		
		super("visualizzafattura", true);
		
		setTitle(title);  	
		
		_window = this;
					
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
			   
		HTMLPane lHTMLPane = new HTMLPane();
		lHTMLPane.setWidth100();
		lHTMLPane.setHeight100();
		lHTMLPane.setPadding(20);
		lHTMLPane.setContentsType(ContentsType.PAGE);
		lHTMLPane.setAllowCaching(false);
		lHTMLPane.setContents(record.getAttributeAsString("html"));
		
		setBody(lHTMLPane);
                
        setIcon("blank.png");
                        
	}	

}
