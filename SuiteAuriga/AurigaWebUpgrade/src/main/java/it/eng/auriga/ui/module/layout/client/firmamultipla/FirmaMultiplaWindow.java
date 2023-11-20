/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.URL;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.DragResizeStopEvent;
import com.smartgwt.client.widgets.events.DragResizeStopHandler;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.FirmaUtility;
import it.eng.auriga.ui.module.layout.client.applet.AppletEng;
import it.eng.auriga.ui.module.layout.client.applet.WaitPopup;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.protocollazione.AppletWindow;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;

public abstract class FirmaMultiplaWindow extends AppletWindow {

	private AppletEng lAppletEngFirmaMultipla;
	
	protected FirmaMultiplaWindow _window;
	
	private Map<String, Record> nomiUnivoci;
	private String smartId;
	private String commonName;
	
	public abstract void firmaCallBack(Map<String, Record> files, String commonName);
	
	public Record[] getFilesAndUdDaFirmare() {
		return null;
	}
	
	public void firmaCallBackWithErrors(final Map<String, Record> files, final String commonName) {
		FirmaUtility.manageFirmaClientCallBackWithErrors(files, getFilesAndUdDaFirmare(), new ServiceCallback<Map<String, Record>>() {
			
			@Override
			public void execute(Map<String, Record> files) {
				firmaCallBack(files, commonName);
			}
		});				
	}
	
//	public FirmaMultiplaWindow(Map<String, Record> files){
//		this(files, "SignerAppletMulti", "SignerAppletMulti.jnlp", "SignerAppletMulti" + getSignerAppletMultiJarVersion() + ".jar","it.eng.client.applet.SmartCardApplet");
//	}
	
	public FirmaMultiplaWindow(Map<String, Record> files, String appletName, String jnplName, String appletJarName, String appletClass){
		
		super(I18NUtil.getMessages().protocollazione_firmaWindow_title(), appletJarName);
		
		_window = this;
		nomiUnivoci= files;
		smartId = SC.generateID();
		setID(smartId);
		getElement().setId(smartId); 
		getElement().setPropertyString("name", smartId); 
		getElement().setPropertyString("ID", smartId);
		String idCreated =  "appletEnd" + smartId + new Date().getTime();
		initCommonNameFunction(this, idCreated + "commonNameFunction");
		initCallBackAskForCloseFunction(this, idCreated + "callBackAskForCloseFunction");		
		
		Map<String, String> lMapParams = new HashMap<String, String>();
		lMapParams.put("readOnly", "false"); 
		lMapParams.put("sign.markEnabled", String.valueOf(AurigaLayout.getParametroDBAsBoolean("APPLET_SIGN_MARK_ENABLED")));
		lMapParams.put("fileInputProvider", "it.eng.client.applet.fileProvider.DirectUrlFilesListInputProvider");
		
		lMapParams.put("autoClosePostSign", "true"); 
		lMapParams.put("callBackAskForClose", idCreated + "callBackAskForCloseFunction"); 
		
		if(isFirmaCongiunta()) {
			lMapParams.put("sign.envelope.merge", "congiunta");		
			lMapParams.put("sign.envelope.merge.options", "congiunta");	
		}
		
		if(isActivityPanelHidden()) {
			lMapParams.put("preference.activityPanel.enabled", "false");	
		}
		
		int count = 0; 
		for (String lStrId : files.keySet()){
			lMapParams.put("directUrl" + count, GWT.getHostPageBaseURL() + "springdispatcher/download?fromRecord=false&url=" + URL.encode(files.get(lStrId).getAttribute("uri")));
			lMapParams.put("filename" + count, files.get(lStrId).getAttribute("nomeFile"));
			lMapParams.put("idFile" + count, files.get(lStrId).getAttribute("idFile"));
			lMapParams.put("firmaPresente" + count, files.get(lStrId).getAttributeAsRecord("infoFile").getAttribute("firmato"));
			lMapParams.put("firmaValida" + count, files.get(lStrId).getAttributeAsRecord("infoFile").getAttribute("firmaValida"));
			count++;
		}
		lMapParams.put("numFiles", files.size() + "");
		
		lMapParams.put("fileOutputProvider", "it.eng.client.applet.fileProvider.AurigaProxyFileOutputProvider");
		
		lMapParams.put("sign.envelope.type", "CAdES_BES");
		lMapParams.put("sign.envelope.type.options", "CAdES_BES");		
		lMapParams.put("testUrl", GWT.getHostPageBaseURL() + "springdispatcher/TestUploadServlet/");
		lMapParams.put("outputUrl", GWT.getHostPageBaseURL() + "springdispatcher/UploadMultiSignerApplet/");
		
		this.setRedrawOnResize(true);
		
		lAppletEngFirmaMultipla = new AppletEng(appletName, jnplName, lMapParams, 337, 510, true, true, this, new String[]{appletJarName}, appletClass){			
			@Override
			public void uploadFromServlet(String file) {
				uploadFirmaEndCallback(file);
			}
			@Override
			protected void uploadAfterDatasourceCall(Record object) {
				RecordList lRecordListFile = object.getAttributeAsRecordList("files");
				Map<String, Record> files = new HashMap<String, Record>();
				if (lRecordListFile != null) {
					for (int i = 0; i < lRecordListFile.getLength(); i++) {
						Record lRecordFile = lRecordListFile.get(i);						
						files.put(lRecordFile.getAttribute("idFile"), lRecordFile);
					}
				}
				firmaCallBackWithErrors(files, commonName);
			}
			@Override
			protected Record getRecordForAppletDataSource() {
				return getRecordFirmaForAppletDataSource();
			}
			
			@Override
			public void realCloseClick(Window pWindow) {
				pWindow.markForDestroy();
				if (uploadDone) {
					final WaitPopup loadWindow = new WaitPopup();
					loadWindow.setZIndex(getZIndex());
					loadWindow.show("Trasferimento file in corso");
					Record lRecord = getRecordForAppletDataSource();
					uploadAfterDatasourceCall(lRecord);
					loadWindow.hideFinal();
				}			
			}
		};
		addItem(lAppletEngFirmaMultipla);
				
		// adatta l'applet alla finestra che la contiene quando viene ridimensionata la finestra
		addDragResizeStopHandler(new DragResizeStopHandler() {			
			@Override
			public void onDragResizeStop(DragResizeStopEvent event) {
				markForRedraw();
				lAppletEngFirmaMultipla.setWidth100();
				lAppletEngFirmaMultipla.setHeight100();
				lAppletEngFirmaMultipla.markForRedraw();
				setAutoSize(true);
			}	
		});			
	}
	
	public boolean isFirmaCongiunta() {
		return AurigaLayout.getParametroDBAsBoolean("FIRMA_CONGIUNTA");
	}
	
	public boolean isActivityPanelHidden() {
		return AurigaLayout.getParametroDBAsBoolean("NASCONDI_PANNELLO_FIRMA");
	}

	private native void initCommonNameFunction(FirmaMultiplaWindow firmaMultiplaWindow, String functionName) /*-{
	   	$wnd[functionName] = function (value) {
 			firmaMultiplaWindow.@it.eng.auriga.ui.module.layout.client.firmamultipla.FirmaMultiplaWindow::commonNameFunction(Ljava/lang/String;)(value);
		};
	}-*/;
	
	public void commonNameFunction(String commonName){
		this.commonName = commonName;
	}

	private native void initCallBackAskForCloseFunction(FirmaMultiplaWindow firmaMultiplaWindow, String functionName) /*-{
   		$wnd[functionName] = function () {
			firmaMultiplaWindow.@it.eng.auriga.ui.module.layout.client.firmamultipla.FirmaMultiplaWindow::callBackAskForCloseFunction()();
		};
	}-*/;
	
	public void callBackAskForCloseFunction(){
		lAppletEngFirmaMultipla.realCloseClick(this);	
	}
	
	public void uploadFirmaEndCallback(String file){
		// Estraggo il nome + uri del file Firmato 
		String[] result = file.split("#");		
		String nome = URL.decodeQueryString(result[0]);		
		String uri = result[1];
		String idFile = result[2];
		Record lRecord = nomiUnivoci.get(idFile);
		if (lRecord == null) {
			lRecord = new Record();
		}
		lRecord.setAttribute("firmaEseguita", true);
		lRecord.setAttribute("uri", uri);
		lRecord.setAttribute("nomeFile", nome);
		lRecord.setAttribute("idFile", idFile);
		nomiUnivoci.put(idFile, lRecord);
	}

	protected Record getRecordFirmaForAppletDataSource() {
		RecordList files = new RecordList();
		if (nomiUnivoci != null) {
			for (String key : nomiUnivoci.keySet()) {
				files.add(nomiUnivoci.get(key));
			}
		}
		Record lRecord = new Record();
		lRecord.setAttribute("files", files);
		lRecord.setAttribute("provenienza", appletJarName);
		return lRecord;
	}
}
