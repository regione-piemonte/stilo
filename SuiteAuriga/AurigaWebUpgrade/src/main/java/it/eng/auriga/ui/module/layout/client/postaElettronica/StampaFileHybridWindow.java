/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.http.client.URL;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.util.SC;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.applet.HybridEng;
import it.eng.auriga.ui.module.layout.client.postaElettronica.StampaFileUtility.StampaFileCallback;
import it.eng.auriga.ui.module.layout.client.protocollazione.HybridWindow;
import it.eng.utility.ui.module.layout.client.Layout;

/**
 * 
 * @author DANCRIST
 *
 */

public class StampaFileHybridWindow extends HybridWindow {

	private HybridEng lAppletHybrid;
	protected HybridWindow instanceWindow;
	private String smartId;
	protected StampaFileHybridWindow _window;
	private StampaFileCallback returnCallback;

	public StampaFileHybridWindow(Record pInBean) {
		super("Stampa file", "StampaFilesHybridBundle.jar");

		instanceWindow = this;
		_window = this;
		smartId = SC.generateID();

		String idCreated = "appletEnd" + smartId + new Date().getTime();
		initCallBack(this, idCreated + "selectAndCloseWindow");

		Map<String, String> lMapParams = new HashMap<String, String>();

		RecordList recordListFiles = pInBean.getAttributeAsRecordList("listaAllegati");
		if (recordListFiles != null && recordListFiles.getLength() > 0 && !recordListFiles.isEmpty()) {
			for (int i = 0; i < recordListFiles.getLength(); i++) {
				Record item = recordListFiles.get(i);
				if (item != null) {

					String url = GWT.getHostPageBaseURL() + "springdispatcher/download?fromRecord=false&url=" + URL.encode(item.getAttributeAsString("uri"));

					lMapParams.put("directUrl" + i, url);
					lMapParams.put("fileName" + i, item.getAttributeAsString("nomeFile"));
				}
			}
			lMapParams.put("numFiles", String.valueOf(recordListFiles.getLength()));
		}

		lMapParams.put("testUrl", GWT.getHostPageBaseURL() + "springdispatcher/TestUploadServlet/");
		lMapParams.put("fileInputProvider", "it.eng.hybrid.module.stampaFiles.inputFileProvider.DirectUrlFilesListInputProvider");
		lMapParams.put("outputProvider", "it.eng.hybrid.module.stampaFiles.outputProvider.OutputProviderImpl");
		lMapParams.put("skipSceltaStampante", AurigaLayout.getImpostazioneStampaAsBoolean("skipPrinterStandardSelection") ? "true" : "false");
		if (AurigaLayout.getImpostazioneStampa("stampanteStandard") != null && !"".equals(AurigaLayout.getImpostazioneStampa("stampanteStandard"))) {
			lMapParams.put("stampanteSelezionata", AurigaLayout.getImpostazioneStampa("stampanteStandard"));
		}
		lMapParams.put("autoClosePostPrint", "true");
		lMapParams.put("callBackAskForClose", idCreated + "selectAndCloseWindow");
		lMapParams.put("skipUserInterface", "true");
		
		lMapParams.put("idUtente", Layout.getUtenteLoggato().getIdUtente());
		lMapParams.put("idSchema", Layout.getUtenteLoggato().getSchema());
		lMapParams.put("idDominio", Layout.getUtenteLoggato().getDominio().split(":")[1]);
		
		String contextPath = GWT.getHostPageBaseURL();
		lMapParams.put("pdfServlet", contextPath + "springdispatcher/pdfProperties");

		lAppletHybrid = new HybridEng("StampaFilesHybridBundle", "StampaFilesHybridBundle.jnlp", lMapParams, 400, 200, true, true, this,
				new String[] { "StampaFilesHybridBundle.jar" }, "it.eng.hybrid.module.stampaFiles") {

			@Override
			public void uploadFromServlet(String file) {

			}

			@Override
			protected void uploadAfterDatasourceCall(Record object) {

			}

			@Override
			protected Record getRecordForAppletDataSource() {
				return null;
			}

			// @Override
			// public void defaultCloseClick(Window pWindow) {
			// pWindow.markForDestroy();
			// }

			@Override
			protected void startHybrid(JavaScriptObject jsParams) {
				callHybrid(jsParams);
			}
		};
	}

	public static native void callHybrid(JavaScriptObject lMapParams) /*-{
		$wnd.doPrinterFiles(lMapParams);
	}-*/;

	private native void initCallBack(StampaFileHybridWindow stampaFileHybridWindow, String functionName) /*-{
		$wnd[functionName] = function(value) {
			return stampaFileHybridWindow.@it.eng.auriga.ui.module.layout.client.postaElettronica.StampaFileHybridWindow::selectAndCloseWindow(Ljava/lang/String;)(value);
		}
	}-*/;

	public void selectAndCloseWindow(final String printerSelected) {
		lAppletHybrid.realCloseClick(_window);
	}

}
