/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import org.apache.log4j.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.smartgwt.client.types.ContentsType;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.HTMLFlow;

import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class ViewerPdfWindow extends ModalWindow {
	
	private static Logger logger = Logger.getLogger(ViewerPdfWindow.class);

	private ViewerPdfWindow instance;
	private String smartId;
	protected HTMLFlow htmlFlow;

	public ViewerPdfWindow(String title, String icon, String contentsUrl) {
		super("viewerPdf", true);
		
		setTitle(title);
		
		instance = this;
		
		smartId = SC.generateID();
		
		setID(smartId);
		
		String idCreated = nomeEntita + smartId + new Date().getTime();
		final String saveSelectionName = idCreated + "saveSelection";
		contentsUrl = contentsUrl + "&saveSelectionName=" + saveSelectionName;
		
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


		@Override
		public void closePortlet() {
			super.closePortlet();
			String url = GWT.getHostPageBaseURL() + "springdispatcher/preview/getSelection";
			RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET, url);
			try {
				requestBuilder.sendRequest(null, new RequestCallback() {
					
					@Override
					public void onResponseReceived(Request request, Response response) {
						
						// TODO : IMPLEMENTARE COSA FARE QUANDO LA CHIAMATA ALLA SERVLET
						// CHE RECUPERA LA SELEZIONE VA A BUON FINE 
						saveSelection("SELEZIONE SALVATA");
						
					}
					
					@Override
					public void onError(Request request, Throwable exception) {
						// TODO : IMPLEMENTARE COSA FARE QUANDO LA CHIAMATA ALLA SERVLET
						// CHE RECUPERA LA SELEZIONE NON VA A BUON FINE 
						SC.say("HAISBAGLIATOTUTTO");
						
					}
				});
			} catch (RequestException e) {
				logger.error(e);
			}
		
					
		}
	
	// TODO : METODO CHIAMATO NEL CASO IN CUI IL RECUPERO DELLA SELZIONE VADA A BUON FINE
	public void saveSelection (String selection) {
		SC.say(selection);
	}
}
