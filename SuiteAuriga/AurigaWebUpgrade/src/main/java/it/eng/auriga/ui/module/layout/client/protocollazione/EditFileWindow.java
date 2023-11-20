/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.URL;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.util.JSON;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.OpenEditorUtility.OpenEditorCallback;
import it.eng.auriga.ui.module.layout.client.applet.AppletEng;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.file.DownloadFile;

public class EditFileWindow extends AppletWindow {

	protected EditFileWindow _window;

	private String nomeFile;
	private String uriFile;
	private AppletEng mAppletEng;

	private OpenEditorCallback returnCallback;

	public EditFileWindow(String display, String uri, Boolean remoteUri, String estensione, String impronta, OpenEditorCallback returnCallback) {
		super(I18NUtil.getMessages().protocollazione_editWindow_title(), "EditApplet" + getEditAppletJarVersion() + ".jar");
		this.returnCallback = returnCallback;
		_window = this;
		Map<String, String> lMapParams = new HashMap<String, String>();
		lMapParams.put("fileName", display);
		String url = null;
		if (remoteUri != null && remoteUri) {
			url = "fromRecord=false&recordType=" + DownloadFile.encodeURL("RemoteExtractor") + "&url=" + URL.encode(uri);
		} else {
			url = "fromRecord=false&url=" + URL.encode(uri);
		}
		lMapParams.put("fileUrl", GWT.getHostPageBaseURL() + "springdispatcher/download?" + url);
		lMapParams.put("outputUrl", GWT.getHostPageBaseURL() + "springdispatcher/UploadEditApplet/");
		lMapParams.put("testUrl", GWT.getHostPageBaseURL() + "springdispatcher/TestUploadServlet/");
		lMapParams.put("tipoFile", estensione);
		lMapParams.put("impronta", impronta);
		lMapParams.put("tipoImpronta", AurigaLayout.getAlgoritmoImpronta());
		lMapParams.put("tipoEncoding", AurigaLayout.getEncoding());
		final AppletEng lAppletEng = new AppletEng("EditApplet", "SignerApplet.jnlp", lMapParams, 100, 100, false, false, this,
				new String[] {appletJarName}, "it.eng.wordOpener.applet.WordOpenerApplet") {

			@Override
			public void uploadFromServlet(String file) {
				uploadFirmaEndCallback(file);
			}

			@Override
			protected void uploadAfterDatasourceCall(Record object) {
				Record lRecord = object.getAttributeAsRecord("mimeTypeFirmaBean");
				String record = JSON.encode(lRecord.getJsObj());
				firmaCallBack(nomeFile, uriFile, record);
			}

			@Override
			protected Record getRecordForAppletDataSource() {
				return getRecordFirmaForAppletDataSource();
			}

			@Override
			public void cancelUpload() {
				super.cancelUpload();
				nothingToSave();
			}
		};
		mAppletEng = lAppletEng;
		addItem(lAppletEng);
	}

	protected void nothingToSave() {
		mAppletEng.defaultCloseClick(this);
	}

	public void uploadFirmaEndCallback(String file) {
		// Estraggo il nome + uri del file Firmato
		String[] result = file.split("#");
		nomeFile = result[0];
		uriFile = result[1];
		mAppletEng.uploadDone = true;
		mAppletEng.defaultCloseClick(this);
	}

	protected Record getRecordFirmaForAppletDataSource() {
		Record lRecord = new Record();
		lRecord.setAttribute("uri", uriFile);
		lRecord.setAttribute("fileName", nomeFile);
		lRecord.setAttribute("provenienza", appletJarName);
		return lRecord;
	}

	public void firmaCallBack(String nomeFileFirmato, String uriFileFirmato, String record) {
		if (returnCallback != null) {
			returnCallback.execute(nomeFileFirmato, uriFileFirmato, record);
		}
	}
}
