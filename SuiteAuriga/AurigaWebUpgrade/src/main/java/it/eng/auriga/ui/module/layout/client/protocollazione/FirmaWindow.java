/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.URL;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.util.JSON;

import it.eng.auriga.ui.module.layout.client.applet.AppletEng;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;

public abstract class FirmaWindow extends AppletWindow {

	protected FirmaWindow _window;
	public abstract void firmaCallBack(String nomeFileFirmato, String uriFileFirmato, String record);
	
	private String nomeFileFirmato;
	private String uriFileFirmato;
	
	public FirmaWindow(String uri, String display, InfoFileRecord pInfoFileRecord){
		super(I18NUtil.getMessages().protocollazione_firmaWindow_title(), "SignerApplet.jar");
		_window = this;
		Map<String, String> lMapParams = new HashMap<String, String>();
		lMapParams.put("readOnly", "false");
		lMapParams.put("fileInputProvider", "it.eng.client.applet.fileProvider.DirectUrlFileInputProvider");
		lMapParams.put("directUrl", GWT.getHostPageBaseURL() + "springdispatcher/download?fromRecord=false&url=" + URL.encode(uri));
		lMapParams.put("filename", display);
		lMapParams.put("fileOutputProvider", "it.eng.client.applet.fileProvider.AurigaProxyFileOutputProvider");
		String tipoFirmaDefault = null;
		String tipiFirma = null;
		if (pInfoFileRecord.isFirmato()){
			if (pInfoFileRecord.getTipoFirma().startsWith("CAdES")){
				tipoFirmaDefault = "CAdES_BES";
				tipiFirma = "CAdES_BES";
			} else {
				tipoFirmaDefault = "PDF";
				tipiFirma = "CAdES_BES, PDF";
			}
		} else if (pInfoFileRecord.getMimetype().equals("application/pdf") || pInfoFileRecord.isConvertibile()){
			tipoFirmaDefault = "PDF";
			tipiFirma = "CAdES_BES, PDF";
		} else {
			tipoFirmaDefault = "CAdES_BES";
			tipiFirma = "CAdES_BES";
		}
		lMapParams.put("envelope.defaultType", tipoFirmaDefault);
		lMapParams.put("envelope.options", tipiFirma);
		lMapParams.put("testUrl", GWT.getHostPageBaseURL() + "springdispatcher/TestUploadServlet/");
		lMapParams.put("outputUrl", GWT.getHostPageBaseURL() + "springdispatcher/UploadSignerApplet/");
		final AppletEng lAppletEng = new AppletEng("SignerApplet", "SignerApplet.jnlp", 
				lMapParams, 337, 510, true, true, this, new String[]{"SignerApplet.jar"}, "it.eng.client.applet.SmartCardApplet"){
			@Override
			public void uploadFromServlet(String file) {
				uploadFirmaEndCallback(file);
			}
			@Override
			protected void uploadAfterDatasourceCall(Record object) {
				Record lRecord = object.getAttributeAsRecord("mimeTypeFirmaBean");
				String record = JSON.encode(lRecord.getJsObj());
				firmaCallBack(nomeFileFirmato, uriFileFirmato, record);
			}
			@Override
			protected Record getRecordForAppletDataSource() {
				return getRecordFirmaForAppletDataSource();
			}
		};
		addItem(lAppletEng);
	}

	public void uploadFirmaEndCallback(String file){
		// Estraggo il nome + uri del file Firmato 
		String[] result = file.split("#");			
		nomeFileFirmato = result[0];
		uriFileFirmato = result[1];			
	}

	protected Record getRecordFirmaForAppletDataSource() {
		Record lRecord = new Record();
		lRecord.setAttribute("uri", uriFileFirmato);
		lRecord.setAttribute("fileName", nomeFileFirmato);
		lRecord.setAttribute("provenienza", appletJarName);
		return lRecord;
	}

}
