/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Window;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.StampaEtichettaUtility.StampaEtichettaCallback;
import it.eng.auriga.ui.module.layout.client.applet.AppletEng;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.Layout;

public class StampaEtichettaAppletWindow extends AppletWindow {

	protected final AppletEng instanceApplet;
	protected Window instanceWindow;
	private String smartId;
	private StampaEtichettaCallback returnCallback;

	public StampaEtichettaAppletWindow(String title, String appletJarName, String nomeStampante, Record[] pRecordsEtichette, String numeroCopie, boolean attivaTimbraturaCartaceo, String nomePortaStampaPort, StampaEtichettaCallback returnCallback) {
		super(I18NUtil.getMessages().protocollazione_stampaEtichettaWindow_title(), "StampaEtichettaApplet" + getStampaEtichettaJarVersion() + ".jar");
		this.returnCallback = returnCallback;
		instanceWindow = this;
		Map<String, String> lMapParams = new HashMap<String, String>();
		smartId = SC.generateID();
		String idCreated = "appletEnd" + smartId + new Date().getTime();
		initCallBack(this, idCreated + "callbackClose");
		lMapParams.put("callbackClose", idCreated + "callbackClose");
		if (nomeStampante != null && !"".equals(nomeStampante)) {
			lMapParams.put("nomeStampante", nomeStampante);
		}
		if (attivaTimbraturaCartaceo) {
			lMapParams.put("tipoStampa", "PORT");
			lMapParams.put("nomePortaStampaPort", nomePortaStampaPort);
			lMapParams.put("numeroCopie", "1");
		} else {
			lMapParams.put("tipoStampa", "PDF");
			lMapParams.put("numeroCopie", numeroCopie);
		}
		lMapParams.put("idUtente", Layout.getUtenteLoggato().getIdUtente());
		lMapParams.put("idSchema", Layout.getUtenteLoggato().getSchema());
		lMapParams.put("idDominio", Layout.getUtenteLoggato().getDominio().split(":")[1]);
		if(AurigaLayout.getParametroDBAsBoolean("ATTIVA_BARCODE_ETICHETTA_SEPARATA")) {
			lMapParams.put("printCodeInSecondLabel", "true");
		}
		String barcodeEncoding = AurigaLayout.getParametroDB("BARCODE_TYPE_IN_ETICHETTA");
		if(barcodeEncoding != null && !"".equals(barcodeEncoding)) {
			lMapParams.put("barcodeEncoding", barcodeEncoding);
		}
		int i = 1;
		for (Record lRecord : pRecordsEtichette) {
			if(lRecord.getAttribute("testo") != null && !"".equals(lRecord.getAttribute("testo"))) {
				lMapParams.put("testo" + i, lRecord.getAttribute("testo"));	
			}
			if(lRecord.getAttribute("barcode") != null && !"".equals(lRecord.getAttribute("barcode"))) {
				lMapParams.put("barcode" + i, lRecord.getAttribute("barcode"));
			}
			if(lRecord.getAttribute("testoBarcode") != null && !"".equals(lRecord.getAttribute("testoBarcode"))) {
				lMapParams.put("testoBarcode" + i, lRecord.getAttribute("testoBarcode"));
			}
			if(lRecord.getAttribute("testoRepertorio") != null && !"".equals(lRecord.getAttribute("testoRepertorio"))) {
				lMapParams.put("testoRepertorio" + i, lRecord.getAttribute("testoRepertorio"));
			}
			if(lRecord.getAttribute("testoFaldone") != null && !"".equals(lRecord.getAttribute("testoFaldone"))) {
				lMapParams.put("testoFaldone" + i, lRecord.getAttribute("testoFaldone"));
			}
			if(lRecord.getAttribute("barcodeFaldone") != null && !"".equals(lRecord.getAttribute("barcodeFaldone"))) {
				lMapParams.put("barcodeFaldone" + i, lRecord.getAttribute("barcodeFaldone"));		
			}
			i++;
		}
		String contextPath = GWT.getHostPageBaseURL();
		lMapParams.put("propertiesServlet", contextPath + "springdispatcher/stampaEtichette");
		lMapParams.put("pdfServlet", contextPath + "springdispatcher/pdfProperties");

		AppletEng lAppletEng = new AppletEng("StampaEtichettaApplet", "StampaEtichettaApplet.jnlp", lMapParams, 200, 200, true, true, this, new String[] {
				this.appletJarName, "lib/barcode4j-fop-ext-2.0.jar" }, "it.eng.applet.StampaEtichettaAppletOcx") {

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

	private native void initCallBack(StampaEtichettaAppletWindow pStampaEtichettaAppletWindow, String functionName) /*-{
		$wnd[functionName] = function() {
			return pStampaEtichettaAppletWindow.@it.eng.auriga.ui.module.layout.client.protocollazione.StampaEtichettaAppletWindow::closeWindow()();
		}
	}-*/;

	public void doAfterClose() {
		if (returnCallback != null) {
			returnCallback.execute();
		}
	}

	public void closeWindow() {
		instanceApplet.markForDestroy();
		instanceWindow.markForDestroy();
		doAfterClose();
	}
}
