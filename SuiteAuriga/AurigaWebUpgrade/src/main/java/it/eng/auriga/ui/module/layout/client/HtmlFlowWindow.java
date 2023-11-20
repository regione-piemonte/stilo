/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

import com.smartgwt.client.types.ContentsType;
import com.smartgwt.client.widgets.HTMLFlow;

public class HtmlFlowWindow extends ModalWindow {

	public HtmlFlowWindow(String title, String icon, String contentsUrl){
		this("html_flow", title, icon, contentsUrl);
	}
	
	public HtmlFlowWindow(String nomeEntita, String title, String icon, String contentsUrl){
		
		super(nomeEntita, true);
		
		setTitle(title);
		
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);		
		
		HTMLFlow htmlFlow = new HTMLFlow();
		htmlFlow.setHeight100();
		htmlFlow.setWidth100();		
		htmlFlow.setContentsURL(contentsUrl);
		htmlFlow.setContentsType(ContentsType.PAGE);
		
		setBody(htmlFlow);
		
		setIcon(icon);
        
	}
}