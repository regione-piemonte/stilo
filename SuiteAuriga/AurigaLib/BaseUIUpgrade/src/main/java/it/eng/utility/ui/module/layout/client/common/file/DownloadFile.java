/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Window;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.util.JSONEncoder;

import it.eng.utility.ui.module.core.client.UserInterfaceFactory;
import it.eng.utility.ui.module.core.client.datasource.OneCallGWTRestService;
import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.Layout;

public class DownloadFile {

	public static void downloadFromUrl(String uri, String fileName){		
		if(isExternalPortlet()) {			
			Window.open(GWT.getHostPageBaseURL() + "springdispatcher/download?fromRecord=false&filename=" + encodeURL(fileName) + "&url=" + encodeURL(uri),"_blank","");	
		} else {
			Window.open(GWT.getHostPageBaseURL() + "springdispatcher/download?fromRecord=false&filename=" + encodeURL(fileName) + "&url=" + encodeURL(uri),"downloadTarget","");	
		}			
	}
	
	public static void downloadFromUrl(String uri, String fileName, Map<String, String> params){
		StringBuffer lStringBuffer = new StringBuffer();
		lStringBuffer.append("&filename=" + encodeURL(fileName) + "&url=" + encodeURL(uri));
		for (String lString : params.keySet()){
			lStringBuffer.append("&" + lString + "=" + encodeURL(params.get(lString)));
		}
		if(isExternalPortlet()) {			
			Window.open(GWT.getHostPageBaseURL() + "springdispatcher/download?fromRecord=false" + lStringBuffer.toString(),"_blank","");	
		} else {
			Window.open(GWT.getHostPageBaseURL() + "springdispatcher/download?fromRecord=false" + lStringBuffer.toString(),"downloadTarget","");	
		}			
	}
	
	public static void downloadFromRecord(final Record pRecord, final String recordType){
		
		// Verifico se la dimensionde del file è già disponibile
		long dimensioneFileMB = 0;
		try {
			dimensioneFileMB = pRecord.getAttributeAsRecord("infoFile") != null ? (pRecord.getAttributeAsRecord("infoFile").getAttributeAsLong("bytes")) / 1000000 : 0;
		}catch (Exception e) {
			dimensioneFileMB = 0;
		}
		if (dimensioneFileMB == 0) {
			// Non ho ricevuto la dimensione del file in ingresso, la ricavo da una chiamata DataSource
			pRecord.setAttribute("remote", pRecord.getAttributeAsBoolean("remoteUri"));
			new OneCallGWTRestService<Record, Record>("FileUtilityDatasource").executecustom("getDimensioneFile", pRecord, new DSCallback() {
				
				@Override
				public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
					long dimensioneFileCalcolataMB = 0;
					if (dsResponse.getStatus() == DSResponse.STATUS_SUCCESS) {
						Record record = dsResponse.getData()[0];
						
						try {
							long dimensioneFileCalcolataByte = record.getAttributeAsRecord("infoFile") != null ? record.getAttributeAsRecord("infoFile").getAttributeAsLong("bytes") : 0;
							dimensioneFileCalcolataMB = dimensioneFileCalcolataByte / 1000000;
						}catch (Exception e) {
							dimensioneFileCalcolataMB = 0;
						}
						downloadFromRecordConControlloDimensione(pRecord, recordType, dimensioneFileCalcolataMB);
					} else {
						downloadFromRecordConControlloDimensione(pRecord, recordType, dimensioneFileCalcolataMB);
					}
				}
			});
		} else {
			downloadFromRecordConControlloDimensione(pRecord, recordType, dimensioneFileMB);
		}
	}
	
	public static void	downloadFromRecordConControlloDimensione(final Record pRecord, final String recordType, long dimensioneFileMB) {
		int sogliaDownloadMB = UserInterfaceFactory.getParametroDB("DIMENSIONE_MAX_FILE_DOWNLOAD") != null ? Integer.parseInt(UserInterfaceFactory.getParametroDB("DIMENSIONE_MAX_FILE_DOWNLOAD")) : 0;
		if (sogliaDownloadMB > 0 && dimensioneFileMB > sogliaDownloadMB) {
			Layout.showConfirmDialogWithWarning("Attenzione!", I18NUtil.getMessages().download_alertDimensioneFile_message(sogliaDownloadMB + ""), "Ok", "Annulla", new BooleanCallback() {

				@Override
				public void execute(Boolean value) {
					if (value != null && value) {
						startDownloadFromRecord(pRecord, recordType);
					}
				}
			});
		} else {
			startDownloadFromRecord(pRecord, recordType);
		}
		
	}

	private static void startDownloadFromRecord(Record pRecord, String recordType) {
		// Tolgo dal record le buste e l'info di firma e marca, a volte mandano in errore la chiamata
		if (pRecord.getAttributeAsRecord("infoFile") != null) {
			pRecord.getAttributeAsRecord("infoFile").setAttribute("buste", (Record) null);
		}
		pRecord.setAttribute("infoFirmaMarca", (Record) null);
		if(isExternalPortlet()) {
			Window.open(GWT.getHostPageBaseURL() + "springdispatcher/download?fromRecord=true&recordType="+encodeURL(recordType)+"&record=" + encodeURL(JSON.encode(pRecord.getJsObj(), new JSONEncoder())),"_blank","");	
		} else {
			Window.open(GWT.getHostPageBaseURL() + "springdispatcher/download?fromRecord=true&recordType="+encodeURL(recordType)+"&record=" + encodeURL(JSON.encode(pRecord.getJsObj(), new JSONEncoder())),"downloadTarget","");	
		}
	}
	
	private static boolean isExternalPortlet() {
		String moduleBaseURL = GWT.getModuleBaseURL();
		String href = Window.Location.getHref();
		int endIndex = moduleBaseURL.lastIndexOf("/", moduleBaseURL.length()-2);
		String relativePath = href.substring(endIndex+1, href.length());
		return relativePath != null && !"".equals(relativePath) && relativePath.startsWith("portlet.jsp");
	}
	
	public static String encodeURL(String str) {
		if(str != null) {			
			return URL.encode(str.replaceAll("&", "%26"));
		}
		return null;
	}
	
}
