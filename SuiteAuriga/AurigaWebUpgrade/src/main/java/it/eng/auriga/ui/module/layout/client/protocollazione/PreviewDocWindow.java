/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.URL;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.widgets.events.ResizedEvent;
import com.smartgwt.client.widgets.events.ResizedHandler;

import it.eng.auriga.ui.module.layout.client.applet.AppletEng;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.file.DownloadFile;

public abstract class PreviewDocWindow extends AppletWindow {
	
	private String displayFilenamePdf;
	private String uriPdf;
	private boolean firstDraw;
	
	public PreviewDocWindow(String pUri, String pDisplay, Boolean remoteUri){
		this(pUri, pDisplay, remoteUri, false, null);
	}
	
	public PreviewDocWindow(String pUri, String pDisplay, Boolean remoteUri, boolean timbroEnabled, Record lOpzioniTimbro){
		super(I18NUtil.getMessages().protocollazione_previewDocWindow_title(), "JPedalApplet" + getJPedalAppletJarVersion() + ".jar");
		setShowMaximizeButton(true);
		firstDraw = true;
		Map<String, String> lMapParams = new HashMap<String, String>();
		lMapParams.put("readOnly", "false");
				lMapParams.put("fileInputProvider", "org.jpedal.examples.viewer.fileProvider.DirectUrlFileInputProvider");
				String url = null;
		if (remoteUri!=null && remoteUri){
			url = "fromRecord=false&recordType="+DownloadFile.encodeURL("RemoteExtractor")+"&url="+URL.encode(pUri);
		} else {
			url="fromRecord=false&url="+URL.encode(pUri);
		}
		lMapParams.put("directUrl", GWT.getHostPageBaseURL() + "springdispatcher/download?"+url);
		lMapParams.put("filename", pDisplay);
		lMapParams.put("fileOutputProvider", "org.jpedal.examples.viewer.fileProvider.AurigaProxyFileOutputProvider");
		lMapParams.put("mark.enabled", "true");
		lMapParams.put("sign.enabled", "true");
		lMapParams.put("testUrl", GWT.getHostPageBaseURL() + "springdispatcher/TestUploadServlet/");
		//se è un protocollo
		if(timbroEnabled) {
			lMapParams.put("timbro.enabled", "true");
			lMapParams.put("TimbroPreference", "true");	
			lMapParams.put("timbro.testo", lOpzioniTimbro.getAttribute("testo"));	
			String posizioneTestoInChiaro = lOpzioniTimbro.getAttribute("posizioneTestoInChiaro");
			lMapParams.put("timbro.posizioneTestoInChiaro", posizioneTestoInChiaro != null && !"".equals(posizioneTestoInChiaro) ? posizioneTestoInChiaro : "--");	
			lMapParams.put("timbro.testoIntestazione", lOpzioniTimbro.getAttribute("testoIntestazione"));
			String posizioneIntestazione = lOpzioniTimbro.getAttribute("posizioneIntestazione");
			lMapParams.put("timbro.posizioneIntestazione", posizioneIntestazione != null && !"".equals(posizioneIntestazione) ? posizioneIntestazione : "--");					
			lMapParams.put("timbro.codifica", lOpzioniTimbro.getAttribute("codifica"));		
			lMapParams.put("timbro.timbroSingolo", lOpzioniTimbro.getAttribute("timbroSingolo"));
			lMapParams.put("timbro.moreLines", lOpzioniTimbro.getAttribute("moreLines"));			
		} else {
			lMapParams.put("timbro.enabled", "false");
			lMapParams.put("TimbroPreference", "false");	
		}
		lMapParams.put("tipoApplet", "X");
		lMapParams.put("saveAsOutputUrl", GWT.getHostPageBaseURL() + "springdispatcher/UploadPerSaveAs/");
		lMapParams.put("outputUrl", GWT.getHostPageBaseURL() + "springdispatcher/UploadDaJPedalFirmato/");
		final AppletEng lAppletEngJPedal = new AppletEng("JPedal", "JPedal.jnlp", lMapParams, 940, 570, true, true, this, new String[]{appletJarName}, "org.jpedal.examples.viewer.AppletViewer"){
			@Override
			public void uploadFromServlet(String file) {
				uploadJPedalEndCallback(file);
			}
			@Override
			protected void uploadAfterDatasourceCall(Record object) {
				Record lRecord = object.getAttributeAsRecord("mimeTypeFirmaBean");
				String record = JSON.encode(lRecord.getJsObj());
				uploadCallBack(displayFilenamePdf, uriPdf, record);
			}
			@Override
			protected Record getRecordForAppletDataSource() {
				return getRecordJpedalForAppletDataSource();
			}
			
		};
		addItem(lAppletEngJPedal);
		addResizedHandler(new ResizedHandler() {			
			@Override
			public void onResized(ResizedEvent event) {
				//setAppletHTML();
				markForRedraw();
				if (firstDraw) {
					firstDraw = !firstDraw; 
				} else {
					lAppletEngJPedal.setWidth100();
					lAppletEngJPedal.setHeight100();
					lAppletEngJPedal.markForRedraw();
				}
				setAutoSize(true);
			}
		});
	}	
	
	public void uploadJPedalEndCallback(String file){
		// Estraggo il nome + uri del file PDF 
		String[] result = file.split("#");			
		displayFilenamePdf = result[0];
		uriPdf = result[1];	
	}
	
	protected Record getRecordJpedalForAppletDataSource() {
		Record lRecord = new Record();
		lRecord.setAttribute("uri", uriPdf);
		lRecord.setAttribute("fileName", displayFilenamePdf);
		lRecord.setAttribute("provenienza", appletJarName);
		return lRecord;
	}
	
	public abstract void uploadCallBack(String filePdf, String uriPdf, String record);
	
}
