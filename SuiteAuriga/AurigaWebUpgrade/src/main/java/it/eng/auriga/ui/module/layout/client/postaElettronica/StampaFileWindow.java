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

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.PrinterScannerUtility.PrinterScannerCallback;
import it.eng.auriga.ui.module.layout.client.applet.AppletEng;
import it.eng.auriga.ui.module.layout.client.protocollazione.AppletWindow;
import it.eng.utility.ui.module.layout.client.Layout;

/**
 * 
 * @author DANCRIST
 *
 */

public abstract class StampaFileWindow extends AppletWindow {

	protected Window instanceWindow;
	protected StampaFileWindow _window;
	private String smartId;
	private PrinterScannerCallback returnCallback;
	private AppletEng lAppletEng;

	public abstract void closeCallBack();

	public StampaFileWindow(Record pInBean) {
		super("Stampa file", "StampaFileApplet" + getStampaFileAppletJarVersion() + ".jar");

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
		lMapParams.put("fileInputProvider", "it.eng.applet.stampaFile.inputFileProvider.DirectUrlFilesListInputProvider");
		lMapParams.put("outputProvider", "it.eng.applet.stampaFile.outputProvider.OutputProviderImpl");
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

		lAppletEng = new AppletEng("StampaFileApplet", "StampaFileApplet.jnlp", lMapParams, 1, 1, true, true, this, new String[] {appletJarName},
				"it.eng.applet.stampaFile.StampaFileApplet") {

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

			@Override
			public void defaultCloseClick(Window pWindow) {
				pWindow.markForDestroy();
			}

			@Override
			public void realCloseClick(Window pWindow) {
				pWindow.markForDestroy();

				closeCallBack();

			};

		};
		addItem(lAppletEng);

	}

	private native void initCallBack(StampaFileWindow stampaFileWindow, String functionName) /*-{
		$wnd[functionName] = function(value) {
			return stampaFileWindow.@it.eng.auriga.ui.module.layout.client.postaElettronica.StampaFileWindow::selectAndCloseWindow(Ljava/lang/String;)(value);
		}
	}-*/;

	public void selectAndCloseWindow(final String printerSelected) {
		lAppletEng.realCloseClick(_window);
	}

}
