/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Window;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.PrinterScannerUtility.PrinterScannerCallback;
import it.eng.auriga.ui.module.layout.client.applet.AppletEng;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.protocollazione.AppletWindow;

public class SelezionaStampante extends AppletWindow {

	protected final AppletEng instanceApplet;
	protected Window instanceWindow;
	private String smartId;
	private PrinterScannerCallback returnCallback;
	private PrinterScannerCallback returnCallbackCancelSelectPrinter;

	public SelezionaStampante(String nomeStampante, PrinterScannerCallback pReturnCallback, PrinterScannerCallback pReturnCallbackCancelSelectPrinter) {

		super(I18NUtil.getMessages().selezionaStampante_title(), "PrinterScanner" + getPrinterScannerAppletJarVersion() + ".jar");

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
		
		int defaultHeight = 200;
		int defaultWidth = 400;
		String strHeight = AurigaLayout.getParametroDB("APPLET_PRINTER_SCANNER_HEIGHT").toString();
		int height = strHeight != null && !"".equals(strHeight) ? Integer.valueOf(strHeight) : defaultHeight;
		String strWidth = AurigaLayout.getParametroDB("APPLET_PRINTER_SCANNER_WIDTH").toString();
		int width = strWidth != null && !"".equals(strWidth) ? Integer.valueOf(strWidth) : defaultWidth;

		AppletEng lAppletEng = new AppletEng("PrinterScanner", "PrinterScanner.jnlp", lMapParams, width, height, true, true, this,
				new String[] {appletJarName}, "it.eng.applet.PrinterScannerApplet") {

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
		};
		instanceApplet = lAppletEng;
		addItem(lAppletEng);
	}

	private native void initCallBack(SelezionaStampante pSelezionaStampante, String functionName) /*-{
		$wnd[functionName] = function(value) {
			return pSelezionaStampante.@it.eng.auriga.ui.module.layout.client.stampante.SelezionaStampante::selectAndCloseWindow(Ljava/lang/String;)(value);
		}
	}-*/;
	
	private native void initCallBackCancelSelectPrinter(SelezionaStampante pSelezionaStampante, String functionName) /*-{
		$wnd[functionName] = function(value) {
			return pSelezionaStampante.@it.eng.auriga.ui.module.layout.client.stampante.SelezionaStampante::cancelAndCloseWindow(Ljava/lang/String;)(value);
		}
	}-*/;

	public void selectAndCloseWindow(final String printerSelected) {
		if (returnCallback != null) {
			returnCallback.execute(printerSelected);
		}
		instanceApplet.markForDestroy();
		instanceWindow.markForDestroy();
	}
	
	public void cancelAndCloseWindow(final String printerSelected) {
		if (returnCallbackCancelSelectPrinter != null) {
			returnCallbackCancelSelectPrinter.execute(printerSelected);
		}
		instanceApplet.markForDestroy();
	}
}
