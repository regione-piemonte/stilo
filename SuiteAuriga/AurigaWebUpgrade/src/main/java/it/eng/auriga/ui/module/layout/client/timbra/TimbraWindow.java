/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.file.DownloadFile;

public class TimbraWindow extends Window {
	
	protected TimbraWindow window;
	private TimbraForm form;

	public TimbraWindow(String nomeEntita, boolean isJustWindow, final FileDaTimbrareBean pFileDaTimbrareBean) {

		window = this;
		
		setIsModal(true);
		setModalMaskOpacity(50);
		setAutoCenter(true);
		setWidth(560);		
		setHeight(180);
		setKeepInParentRect(true);
		setTitle("Opzioni timbro");		
		setShowModalMask(true);
		setShowCloseButton(true);
		setShowMaximizeButton(false);
		setShowMinimizeButton(false);
		setShowTitle(true);	
		setHeaderControls(HeaderControls.HEADER_LABEL, HeaderControls.CLOSE_BUTTON);
				
		form = new TimbraForm(pFileDaTimbrareBean);
		
		// CONFERMA button
		Button timbraButton = new Button("Timbra");
		timbraButton.setIcon("ok.png");
		timbraButton.setIconSize(16); 
		timbraButton.setAutoFit(false);
		timbraButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {

				if(form.validate()) {
					timbra();
					close();
				}
			}
		});
				
		// ANNULLA button
		Button annullaButton = new Button("Annulla");
		annullaButton.setIcon("annulla.png");
		annullaButton.setIconSize(16); 
		annullaButton.setAutoFit(false);
		annullaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {

				close();
			}
		});		
				
		HStack buttons = new HStack(5);
		buttons.setAlign(Alignment.CENTER);
		buttons.setPadding(5);
		buttons.addMember(timbraButton);		
		buttons.addMember(annullaButton);
		
		setAlign(Alignment.CENTER);
		setTop(50);
		
		VLayout layout = new VLayout();
		layout.setHeight100();
		layout.setWidth100();
				
		layout.addMember(form);		
		layout.addMember(buttons);
		
		addItem(layout);	
		
		addCloseClickHandler(new CloseClickHandler() {
			
			@Override
			public void onCloseClick(CloseClickEvent event) {
				
				close();
			}
		});		
		
		setHeaderIcon("file/timbra.gif");
		
	}
	
	public void close() {
		window.markForDestroy();
	}

	protected void timbra() {
		Record lRecord = form.getValuesAsRecord();
		if (lRecord != null){
			TimbroUtil.buildDatiSegnatura(lRecord);
//			invokeTimbra(lRecord);
		}
	}

//	protected void invokeTimbra(Record lRecord) {
//		Layout.showWaitPopup("Timbratura in corso: potrebbe richiedere qualche secondo. Attendere...");
//		GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("TimbraDatasource");
//		lGwtRestService.call(lRecord, new ServiceCallback<Record>() {
//			@Override
//			public void execute(Record object) {
//				Layout.hideWaitPopup();
//				if (object.getAttributeAsBoolean("result")){								
//					timbraCallBack(object);
//				} else {
//					Layout.addMessage(new MessageBean(object.getAttribute("error"), object.getAttribute("error"), MessageType.ERROR));
//				}
//			}
//		});
//	}
//	
//	protected void timbraCallBack(Record object) {
//		String uri = object.getAttribute("uri");
//		String display = "timbrato.pdf";
//		Record lRecord = new Record();
//		lRecord.setAttribute("displayFilename", display);
//		lRecord.setAttribute("uri", uri);
//		lRecord.setAttribute("sbustato", "false");
//		lRecord.setAttribute("remoteUri", false);					
//		DownloadFile.downloadFromRecord(lRecord, "FileToExtractBean");			
//	}

}