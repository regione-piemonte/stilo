/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.smartgwt.client.types.ContentsType;
import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.HTMLFlow;

import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class HtmlEditorOrganigrammaFlowWindow extends ModalWindow {

	private HtmlEditorOrganigrammaFlowWindow instance;
	private HTMLFlow htmlFlow;
	private String iframeID;
	private String closeWindowFunctionName;
	
	public HtmlEditorOrganigrammaFlowWindow(String title, String icon, String contentsUrl){
		this("html_flow_editor_organigramma", title, icon, contentsUrl);
	}
	
	public HtmlEditorOrganigrammaFlowWindow(String nomeEntita, String title, String icon, String contentsUrl){
		
		super(nomeEntita, true);
		
		instance = this;
		
		String smartId = SC.generateID();		
		setID(smartId);
		iframeID = "iframe" + smartId;		
		
		String idCreated = nomeEntita + smartId + new Date().getTime();
		closeWindowFunctionName = idCreated + "closeWindow";		
		
		setTitle(title);
		
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);		
		
		htmlFlow = new HTMLFlow();
		htmlFlow.setHeight100();
		htmlFlow.setWidth100();		
		htmlFlow.setContentsURL(contentsUrl);
		htmlFlow.setContentsType(ContentsType.PAGE);
		htmlFlow.setEvalScriptBlocks(true);
		htmlFlow.setRedrawOnResize(false);
		
		setRedrawOnResize(false);
		setBody(htmlFlow);
		setIcon(icon);        			
		
//		setShowCloseButton(false);
		setShowMaximizeButton(false);
		setShowMinimizeButton(false);
		setHeaderControls(HeaderControls.HEADER_ICON, HeaderControls.HEADER_LABEL, HeaderControls.CLOSE_BUTTON);
		
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {

			@Override
			public void execute() {
				initCloseWindowFunction(instance, closeWindowFunctionName);
			}
		});
	}
	
	private native void initCloseWindowFunction(HtmlEditorOrganigrammaFlowWindow window, String functionName) /*-{
		$wnd[functionName] = function (paramJson) {
			if(paramJson) {
				if (paramJson.esitoAzione){
					window.@it.eng.auriga.ui.module.layout.client.HtmlEditorOrganigrammaFlowWindow::closeWindowAfterAzione(Ljava/lang/String;)(paramJson.esitoAzione);
				} else { 
					window.@it.eng.auriga.ui.module.layout.client.HtmlEditorOrganigrammaFlowWindow::closeWindow(Ljava/lang/String;)(paramJson.nroVersLavoro);
				}
			} else {
				window.@it.eng.auriga.ui.module.layout.client.HtmlEditorOrganigrammaFlowWindow::closeWindow(Ljava/lang/String;)("");
			} 
		};
	}-*/;

	public void closeWindowAfterAzione(String esitoAzione) {
		markForDestroy();
	}
	
	public void closeWindow(String nroVersLavoroSalvato) {
		markForDestroy();
	}
	
	public String getCloseWindowFunctionName() {
		return closeWindowFunctionName;
	}
	
	public String getiframeID() {
		return iframeID;
	}
	
	public void setHtmlFlowContentsURL(String contentsURL) {
		htmlFlow.setContentsURL(contentsURL);
	}
	
	public void setHtmlFlowContents(String contents) {
		htmlFlow.setContents(contents);
	}		
	
}