/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.util.SC;

import it.eng.auriga.ui.module.layout.client.PrinterScannerUtility.PrinterScannerCallback;
import it.eng.auriga.ui.module.layout.client.applet.HybridEng;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.protocollazione.HybridWindow;

public class SelezionaStampanteHybridWindow extends HybridWindow {

	protected final HybridEng instanceApplet;
	protected HybridWindow instanceWindow;
	private String smartId;
	private PrinterScannerCallback returnCallback;
	private PrinterScannerCallback returnCallbackCancelSelectPrinter;

	public SelezionaStampanteHybridWindow(String nomeStampante,PrinterScannerCallback pReturnCallback, PrinterScannerCallback pReturnCallbackCancelSelectPrinter) {

		super(I18NUtil.getMessages().selezionaStampante_title(), "PrinterScanner.jar");

		instanceWindow = this;
		returnCallback = pReturnCallback;
		returnCallbackCancelSelectPrinter = pReturnCallbackCancelSelectPrinter;
		smartId = SC.generateID();

		String idCreated = "appletEnd" + smartId + new Date().getTime();
		initCallBack(this, idCreated + "callbackSelectPrinter");
		initCallBackCancelSelectPrinter(this, idCreated + "callbackCancelSelectPrinter");

		Map<String, String> lMapParams = new HashMap<String, String>();
		lMapParams.put("callbackSelectPrinter", idCreated + "callbackSelectPrinter");
		lMapParams.put("callbackCancelSelectPrinter", idCreated + "callbackCancelSelectPrinter");
		lMapParams.put("stampanteSelezionata", nomeStampante != null ? nomeStampante : "");

		HybridEng lAppletEng = new HybridEng("PrinterScanner", "PrinterScanner.jnlp", lMapParams, 400, 200, true, true, this,
				new String[] { "PrinterScanner.jar" }, "it.eng.applet.PrinterScannerApplet") {

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
		instanceApplet = lAppletEng;
	}

	public static native void callHybrid(JavaScriptObject lMapParams) /*-{
		$wnd.doPrinterScanner(lMapParams);
	}-*/;

	private native void initCallBack(SelezionaStampanteHybridWindow pSelezionaStampante, String functionName) /*-{
		$wnd[functionName] = function(value) {
			return pSelezionaStampante.@it.eng.auriga.ui.module.layout.client.stampante.SelezionaStampanteHybridWindow::selectAndCloseWindow(Ljava/lang/String;)(value);
		}
	}-*/;
	
	private native void initCallBackCancelSelectPrinter(SelezionaStampanteHybridWindow pSelezionaStampante, String functionName) /*-{
		$wnd[functionName] = function(value) {
			return pSelezionaStampante.@it.eng.auriga.ui.module.layout.client.stampante.SelezionaStampanteHybridWindow::cancelAndCloseWindow(Ljava/lang/String;)(value);
		}
	}-*/;

	public void selectAndCloseWindow(final String printerSelected) {
		if (returnCallback != null) {
			returnCallback.execute(printerSelected);
		}
		instanceApplet.markForDestroy();
	}
	
	public void cancelAndCloseWindow(final String printerSelected) {
		if (returnCallbackCancelSelectPrinter != null) {
			returnCallbackCancelSelectPrinter.execute(printerSelected);
		}
		instanceApplet.markForDestroy();
	}
	
}
