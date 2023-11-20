/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.client.common.file.DownloadFile;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.util.JSONEncoder;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;

public class PrintUtil {

	public void print(Record pRecordFileToPrint, String recordType){
		manageShowPreview(pRecordFileToPrint, recordType);
	}
	
	private Window window;
	private HTMLFlow flow;
	private Button printButton;
	private Button saveButton;
	private String uri;
	private Record record;
	private String recordType;
	
	protected void manageShowPreview(final Record pRecord, final String recordType) {
		this.record = pRecord;
		this.recordType = recordType;
		String idCreated =  "timbraPreview" + SC.generateID() + new Date().getTime();
		initAbilitaSalvaStampa(this, idCreated + "abilitaFunction");
		initWindow();
		String url = "page/showPdf.jsp";
		initFlow(url,  idCreated + "abilitaFunction");
		window.addItem(flow);
		initSaveButton();
		initPrintButton();
		HLayout lHLayout = new HLayout(5);
		lHLayout.setWidth100();
		lHLayout.setMembers(printButton, saveButton);
		window.addItem(lHLayout);
		window.setAlign(Alignment.CENTER);
		window.show();
		window.addCloseClickHandler(new CloseClickHandler() {
			@Override
			public void onCloseClick(CloseClickEvent event) {
				manageCloseClick();
			}
		});
	}

	private void initPrintButton() {
		printButton = new Button();
		printButton.setWidth(90);
		printButton.setTitle("Print");
		printButton.setVisible(false);
		printButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				manageClick(null, uri);
			}
		});
		
	}

	private void initSaveButton() {
		saveButton = new Button();
		saveButton.setWidth(90);
		saveButton.setTitle("Salva");
		saveButton.setDisabled(true);
		
		saveButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				manageSave();
			}
		});
	}

	private void initFlow(String url, String callback) {
		flow = new HTMLFlow();
		flow.setID("printPreview");
		flow.setWidth100();
		flow.setHeight100();
		flow.setContentsURL(url);
		Map<String, String> lMap = new HashMap<String, String>();
		String address = GWT.getHostPageBaseURL() + "springdispatcher/stream?fromRecord=true&recordType="+DownloadFile.encodeURL(recordType)+"&record=" + DownloadFile.encodeURL(JSON.encode(record.getJsObj(), new JSONEncoder()));
		lMap.put("address", address);
		lMap.put("callback", callback);
		flow.setContentsURLParams(lMap);
	}

	protected void initWindow() {
		window = new Window();
		window.setTitle("Preview");
		window.setWidth(800);
		window.setHeight(400);
		window.setAutoCenter(true);
	}
	
	public void abilitaSalvaStampa(){
		saveButton.setDisabled(false);
//		printButton.setDisabled(false);
	}

	private native void initAbilitaSalvaStampa(PrintUtil printUtil, String functionName) /*-{
	   $wnd[functionName] = function (value) {
    	printUtil.@it.eng.auriga.ui.module.layout.client.print.PrintUtil::abilitaSalvaStampa()();
	};
	}-*/;

	protected void manageSave() {
		DownloadFile.downloadFromRecord(record, recordType);
	}

	protected void manageCloseClick() {
		flow.markForDestroy();
		window.markForDestroy();
		
	}
	protected static native void manageClick(String print, String uri) /*-{
		try {
			$doc.getElementById('printablePage').contentWindow.print();
		} catch (err){
			$wnd.isc.say(err);
		}
	}-*/;

//	protected static native void manageClick(String print, String uri) /*-{
//		try {
//			var win = $wnd.open(print,'downloadTarget');
////			win.onload=function(){alert("loaded")};
////			win.addEventListener('load', function(){alert("loaded")}, false);
////			win.attachEvent('onload', function(){alert("loaded")}, false);
//		} catch (err){
//				alert(err);
//		}	
////		win.print();
////		try {
////			$doc.getElementById('printablePage').contentWindow.print();
////		} catch (err){
////			try {
////				var v = $wnd.open(uri,'downloadTarget');
////				v.print();
////			} catch (err){
////				alert(err);
////			}		
////		}	
//		
//	}-*/;
	
	
}
