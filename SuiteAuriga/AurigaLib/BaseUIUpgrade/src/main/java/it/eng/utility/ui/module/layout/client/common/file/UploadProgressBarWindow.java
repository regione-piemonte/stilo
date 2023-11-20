/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.google.gwt.user.client.Timer;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.HTMLPane;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;

import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.OneCallGWTRestService;
import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;

public class UploadProgressBarWindow extends Window{
	
	final UploadProgressBarWindow _window;
	
	public boolean cancelUpload = false;
	private Timer timer;
	private boolean end;
	private boolean elaborate = false;
	private String smartId;
	
	public UploadProgressBarWindow(String smartId) {
		
		_window = this;
		this.smartId = smartId;
		setShowTitle(true);	
		setTitle(I18NUtil.getMessages().uploadProgressBarWindow_title());
		setIsModal(true);
		setModalMaskOpacity(50);
		setAutoCenter(true);
		setWidth(400);
		setHeight(110);
		setKeepInParentRect(true);
		setShowModalMask(true);
		setShowMaximizeButton(true);
		setShowMinimizeButton(true);
		HTMLPane lHtmlPane = new HTMLPane();
		lHtmlPane.setContents("<div style=\"width:100%; height:95%; text-align: center;\"> <p id=\"loaded_n_total\" style=\"padding-top:1em; margin-block-start: 0em;"
				+ "    margin-block-end: 0em; \"></p> <progress id=\"progressBar\" value=\"0\" max=\"100\" style=\"width:300px; position:relative;\"></progress>"
				+ "<button id=\"buttonCloseUploadProgressBarModal\" onclick=\"" + (Layout.isExternalPortlet?"document":"window.top") + ".closeUploadProgressBarWindow_" + smartId + "('test');\" style=\" display:none;\">Click me</button>"
				+ "<button id=\"buttonSetElaborateProgressBarModal\" onclick=\"" + (Layout.isExternalPortlet?"document":"window.top") + ".setElaborateUploadProgressBarWindow_" + smartId + "('test');\" style=\" display:none;\">Click me</button>"
				+ "</div>");
		addItem(lHtmlPane);
		
		addCloseClickHandler(new CloseClickHandler() {

			@Override
			public void onCloseClick(CloseClickEvent event) {
				GWTRestDataSource.printMessage(new MessageBean(I18NUtil.getMessages().progressBarWindow_interrupt_message(), I18NUtil.getMessages().progressBarWindow_interrupt_message(), MessageType.WARNING));
				if (!elaborate) {
					abortUpload();
				}
				setUploadInErrorAndDestroy();
			}
		});
	}
	
	@Override
	public void manageOnCloseClick() {
		if (!elaborate) {
			abortUpload();
		}
		setUploadInErrorAndDestroy();
	}
	
	private native void abortUpload() /*-{
		$wnd.abortUpload();
	}-*/;
	
	public void setElaborate() {
		elaborate = true;
		setTitle(I18NUtil.getMessages().progressBarWindow_title());
		inizializzaTimerVerificaFileElaborati();
	}
	
	public void destroyAfterError() {
		setUploadInErrorAndDestroy();
	}
	
	public void setElaborateFinish() {
		Record lRecord = new Record();
		lRecord.setAttribute("smartId", smartId);
		lRecord.setAttribute("finish", true);
		lRecord.setAttribute("uploadCancellato", false);
		OneCallGWTRestService<Record, Record> lOneCallUploadStatusDataSource = new OneCallGWTRestService<Record, Record>("UploadStatusDataSource");
		lOneCallUploadStatusDataSource.setShowPrompt(false);
		lOneCallUploadStatusDataSource.call(lRecord, new ServiceCallback<Record>() {
			
			@Override
			public void execute(Record object) {
			}		
		});		
	}

	private void setUploadInErrorAndDestroy() {
		cancelUpload = true;
		Record lRecord = new Record();
		lRecord.setAttribute("smartId", smartId);
		lRecord.setAttribute("finish", true);
		lRecord.setAttribute("uploadCancellato", true);
		OneCallGWTRestService<Record, Record> lOneCallUploadStatusDataSource = new OneCallGWTRestService<Record, Record>("UploadStatusDataSource");
		lOneCallUploadStatusDataSource.setShowPrompt(false);
		lOneCallUploadStatusDataSource.call(lRecord, new ServiceCallback<Record>() {
			
			@Override
			public void execute(Record object) {
				if(timer != null) {
					timer.cancel();
				}
				end = true;
				markForDestroy();
			}		
		});		
	}
	
	private native void settaValoreProgressBar(String title, String percent, boolean isExternalPortlet) /*-{
		if (isExternalPortlet){	
		   $doc.settaValoreProgressBar(title, percent);
	   } else {
	   	   $wnd.settaValoreProgressBar(title, percent);
	   }
	}-*/;

	protected void inizializzaTimerVerificaFileElaborati() {
		timer = new Timer() {  
			public void run() {  
				if (!end){
					Record lRecord = new Record();
					lRecord.setAttribute("smartId", smartId);
					OneCallGWTRestService<Record, Record> lOneCallUploadStatusDataSource = new OneCallGWTRestService<Record, Record>("UploadStatusDataSource");
					lOneCallUploadStatusDataSource.setShowPrompt(false);
					lOneCallUploadStatusDataSource.call(lRecord, new ServiceCallback<Record>() {
						
						@Override
						public void execute(Record object) {
							if (!end){
								Float numeroFileInElaborazione = object.getAttributeAsFloat("numFileInElaborazione");
								if (numeroFileInElaborazione <= 0) {
									settaValoreProgressBar("File in elaborazione...", "0", Layout.isExternalPortlet);
									schedule(50);
								} else {
									Float numeroFileTotali = object.getAttributeAsFloat("numFileTotali");
									String percentuale = "" + (numeroFileInElaborazione / numeroFileTotali) * 100;
									settaValoreProgressBar("File " + numeroFileInElaborazione + " di " + numeroFileTotali + " in elaborazione...", percentuale, Layout.isExternalPortlet);
									if (numeroFileInElaborazione < numeroFileTotali) {
										schedule(50);
									} else {
										cancel();
									}
								}
							}
						}			
					});
				} 
			}
		};
		timer.schedule(50);
	}
}
