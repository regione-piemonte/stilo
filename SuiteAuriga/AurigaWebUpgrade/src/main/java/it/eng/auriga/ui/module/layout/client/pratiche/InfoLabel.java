/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Visibility;
import com.smartgwt.client.widgets.Label;

public class InfoLabel extends Label {

	public InfoLabel() {
		
		this(null, null);
		
	}	
	
	public InfoLabel(String titolo, String testo) {
		
		setAutoHeight();
		setWidth100();
		setAlign(Alignment.CENTER);  
		setVisibility(Visibility.HIDDEN);		
		setContenuto(titolo, testo);				
	}
	
	public void setContenuto(String titolo, String testo) {
		if((titolo != null && !"".equals(titolo)) || (testo != null && !"".equals(testo))) {
			setVisible(true);
			String contents = null;
			if(titolo != null && !"".equals(titolo)) {
				contents = "<span style='font-weight:bold;font-size: 22px;font-style: normal;'>" + titolo + "</span>";
			}
			if(testo != null && !"".equals(testo)) {
				if(contents != null) {
					contents += "<br/><br/>";
				}
				contents += testo;
			}			
			setContents(
				"<div style='height: 200px;margin-left: 20px;margin-right: 20px;'>" +
				"<p style='background-color: #d1edf9;box-shadow: 5px 5px 10px #888888;font-size:16px;font-style: italic;font-family: Arial,Verdana,sans-serif;padding: 25px;padding-left: 25px;text-align: justify;'>" + contents + "</p>" +
				"</div>"
			);
		} else {
			setVisible(false);
		}
	}
	
}
