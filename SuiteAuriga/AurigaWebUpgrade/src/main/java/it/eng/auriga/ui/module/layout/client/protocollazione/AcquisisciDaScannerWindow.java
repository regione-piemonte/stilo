/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.util.JSON;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Window;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.ScanUtility.ScanCallback;
import it.eng.auriga.ui.module.layout.client.applet.AppletEng;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.Layout;

public class AcquisisciDaScannerWindow extends AppletWindow {

	private String displayFilenamePdf;
	private String uriPdf;
	private String displayFilenameTif;
	private String uriTif;
	private boolean imageSaved;
	private ScanCallback returnCallback;

	// private String functionStartName;
	// public abstract void uploadCallBack(String filePdf, String uriPdf, String fileTif, String uriTif, String record);

	private final AppletEng instanceApplet;
	private Window instanceWindow;
	private String smartId;
	private boolean richiestaChiusura;

	public AcquisisciDaScannerWindow(ScanCallback returnCallback) {
		super(I18NUtil.getMessages().protocollazione_scansioneWindow_title(), "ScanApplet" + getScanAppletJarVersion() + ".jar");
		this.returnCallback = returnCallback;
		richiestaChiusura = false;
		Map<String, String> lMapParams = new HashMap<String, String>();
		String contextPath = GWT.getHostPageBaseURL();
		boolean enablePDFA = AurigaLayout.getParametroDBAsBoolean("SCANSIONE_ABILITA_PDFA");
		boolean enablePannImpEmbedded = AurigaLayout.getParametroDBAsBoolean("SCANSIONE_ABILITA_PANN_IMPOSTAZIONI_EMBEDDED");
		lMapParams.put("contextPath", contextPath);
		lMapParams.put("servletUpload", "springdispatcher/UploadDaScannerServlet/");
		lMapParams.put("abilitaPdfA", enablePDFA + "");
		lMapParams.put("abilitaPannImpEmbedded", enablePannImpEmbedded + "");
		smartId = SC.generateID();
		String idCreated = "appletEnd" + smartId + new Date().getTime();
		lMapParams.put("idSelected", idCreated);
		initCallBackAskForClose(this, idCreated + "callbackAskForClose");
		lMapParams.put("callbackAskForClose", idCreated + "callbackAskForClose");
		initCallBackRichiestaChiusura(this, idCreated + "callbackRichiestaChiusura");
		lMapParams.put("callbackRichiestaChiusura", idCreated + "callbackRichiestaChiusura");
		initCallBackAnnullaChiusura(this, idCreated + "callbackAnnullaChiusura");
		lMapParams.put("callbackAnnullaChiusura", idCreated + "callbackAnnullaChiusura");
		lMapParams.put("testUrl", GWT.getHostPageBaseURL() + "springdispatcher/TestUploadServlet/");
		// Leggo la risoluzione di default dal contesto
		int defaultScannerResolution = Layout.getGenericConfig().getScannerDefaultResolution();
		lMapParams.put("risoluzioneScanner", defaultScannerResolution + "");
		String compressionQuality = AurigaLayout.getParametroDB("SCANSIONE_COMPRESSION_QUALITY");
		compressionQuality = compressionQuality != null && !"".equalsIgnoreCase(compressionQuality) ? compressionQuality : "0.7";
		lMapParams.put("compressionQuality", compressionQuality);
		
		AppletEng lAppletEng = new AppletEng("ScanApplet", "ScanApplet.jnlp", lMapParams, 490, 320, true, true, this, new String[] {appletJarName}, "it.eng.utility.scanner.twain.applet.TwainApplet") {

			@Override
			public void uploadFromServlet(String file) {
				uploadScannerEndCallback(file);

			}

			@Override
			protected void uploadAfterDatasourceCall(Record object) {
				Record lRecord = object.getAttributeAsRecord("mimeTypeFirmaBean");
				String record = JSON.encode(lRecord.getJsObj());
				scanCallback(displayFilenamePdf, uriPdf, displayFilenameTif, uriTif, record);
			}

			@Override
			protected Record getRecordForAppletDataSource() {
				return getRecordScannerForAppletDataSource();
			}

			@Override
			public void defaultCloseClick(Window pWindow) {
				instanceWindow = pWindow;
				richiestaChiusura = true;
			}
		};
		instanceApplet = lAppletEng;
		instanceWindow = this;
		addItem(lAppletEng);
	}

	private native void initCallBackAnnullaChiusura(AcquisisciDaScannerWindow pAcquisisciDaScannerWindow, String functionName) /*-{
		$wnd[functionName] = function() {
			return pAcquisisciDaScannerWindow.@it.eng.auriga.ui.module.layout.client.protocollazione.AcquisisciDaScannerWindow::annullaChiusura()();
		}
	}-*/;

	public boolean annullaChiusura() {
		richiestaChiusura = false;
		return richiestaChiusura;
	}

	public boolean canBeClosed() {
		return richiestaChiusura;
	}

	private native void initCallBackRichiestaChiusura(AcquisisciDaScannerWindow pAcquisisciDaScannerWindow, String functionName) /*-{
		$wnd[functionName] = function() {
			return pAcquisisciDaScannerWindow.@it.eng.auriga.ui.module.layout.client.protocollazione.AcquisisciDaScannerWindow::canBeClosed()();
		}
	}-*/;

	public final native void askForClose() /*-{
		$wnd.SignerApplet.askForClose();
	}-*/;

	public final native boolean verifyAppletStatus() /*-{
		var result;
		try {
			result = $wnd.SignerApplet.showConfirmOnClose();
		} catch (err) {
			alert(err);
			return true;
		}
		return result;
	}-*/;

	private native void initCallBackAskForClose(AcquisisciDaScannerWindow pAcquisisciDaScannerWindow, String functionName) /*-{
		$wnd[functionName] = function() {
			return pAcquisisciDaScannerWindow.@it.eng.auriga.ui.module.layout.client.protocollazione.AcquisisciDaScannerWindow::callbackAskForClose()();
		};
	}-*/;

	public void callbackAskForClose() {
		instanceApplet.realCloseClick(instanceWindow);
	}

	public void uploadScannerEndCallback(String file) {
		
		// Estraggo le info del file PDF e del file TIFF
		String[] pdfAndTif = file.split("---");
		// Estraggo il nome + uri del file PDF
		String[] result = pdfAndTif[0].split("#");
		displayFilenamePdf = result[0];
		uriPdf = result[1];
		// Estraggo il nome + uri del file TIFF
		String[] resultTif = pdfAndTif[1].split("#");
		displayFilenameTif = resultTif[0];
		uriTif = resultTif[1];
	}

	protected Record getRecordScannerForAppletDataSource() {
		Record lRecord = new Record();
		lRecord.setAttribute("uri", uriPdf);
		lRecord.setAttribute("fileName", displayFilenamePdf);
		lRecord.setAttribute("uriTif", uriTif);
		lRecord.setAttribute("fileNameTif", displayFilenameTif);
		lRecord.setAttribute("provenienza", appletJarName);
		return lRecord;
	}

	public void scanCallback(String filePdf, final String uriPdf, String fileTif, String uriTif, String record) {
		if (returnCallback != null) {
			returnCallback.execute(filePdf, uriPdf, fileTif, uriTif, record);
		}
	}

}
