/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
 * Window utilizzata per la chiamata dell'applet SelectCertificatoFirmaApplet In questa classe vengono settati i parametri richiesti dall'applet come input e
 * implementato il meccanismo di callback che, una volta ottenuta risposta, rimanda i parametri ottenuti alla classe chiamante
 */

package it.eng.auriga.ui.module.layout.client.firmamultiplahash;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.util.SC;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.applet.HybridEng;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.protocollazione.HybridWindow;

public abstract class SelectCertificatoFirmaHybridWindow extends HybridWindow {

	private HybridEng appletSelectCertificato;
	protected SelectCertificatoFirmaHybridWindow _window;
	private String smartId;
	private String commonName;

	private String pin = "";
	private String firmatario = "";
	private String alias = "";
	private String metodoFirma = "";

	public abstract void letturaCertificatoCallBack(String pin, String firmatario, String alias, String metodoFirma);

	public SelectCertificatoFirmaHybridWindow() {

		super(I18NUtil.getMessages().protocollazione_firmaWindow_title(), "selectCertificatoFirmaApplet" + getSignerAppletMultiJarVersion() + ".jar");

		_window = this;
		smartId = SC.generateID();

		/*
		 * Inizializzazione del nome delle funzioni di callback Viene utilizzato un id in modo che esse siano univoche
		 */
		String idCreated = "appletEnd" + smartId + new Date().getTime();
		initCallBackAskForCloseFunction(this, idCreated + "callBackAskForCloseFunction");

		// Creazione della mappa dei parametri che verranno passati come input all'applet
		Map<String, String> lMapParams = new HashMap<String, String>();

		// Passo alla mappa i valori delle callback che devono essere chiamate
		lMapParams.put("callBackAskForClose", idCreated + "callBackAskForCloseFunction");

		// Indico che voglio che l'applet dopo la ricerca si chiuda automaticamente
		lMapParams.put("autoClosePostSearch", "true");
		
		//Per abilitare o meno la marca
		lMapParams.put("sign.markEnabled", String.valueOf(isAppletSignMarkEnabled()));

		lMapParams.put("outputProvider", "it.eng.hybrid.module.selectCertificati.response.FirmatarioOutputProvider");

		// Parametro che viene testato per eseguire il test sulla rete
		lMapParams.put("testUrl", GWT.getHostPageBaseURL() + "springdispatcher/TestUploadServlet/");
		
		// Parametro per disabilitare il controllo della scadenza del certificato
		String disabilitaControlloCertificatoScaduto = AurigaLayout.getParametroDBAsBoolean("DISABILITA_CONTROLLO_CERTIFICATO_SCADUTO_FIRMA_CLIENT") + "";
		lMapParams.put("disabilitaControlloCertificatoScaduto", disabilitaControlloCertificatoScaduto);
		
		// Indica se è abilitata la verifica di controllo coerenza del certificato di firma
		String attivaCtrlUserVsCertifFirmaDig = AurigaLayout.getParametroDBAsBoolean("ATTIVA_CTRL_USER_VS_CERTIF_FIRMA_DIG") + "";
		lMapParams.put("ctrlUserVsCertifFirmaDigAttivato", attivaCtrlUserVsCertifFirmaDig);

		/*
		 * it.eng.client.applet.SelectFirmatariApplet è dove si trova il metodo init()
		 * LE DIMENSIONI DEL MODULO DEVONO ESSERE MODIFICATE ALL'INTERNO DEL MODULO, NON DA QUI 
		 */
		appletSelectCertificato = new HybridEng("selectCertificatoFirmaApplet", "selectCertificatoFirmaApplet.jnlp", lMapParams, 427, 300, true, true, this,
				new String[] { "selectCertificatoFirmaApplet" + getSignerAppletMultiJarVersion() + ".jar" }, "it.eng.client.applet.SelectFirmatariApplet") {

			/*
			 * I seguenti metodi sono stati aggiunti perchè corrispondono a dei metodi astratti in AppletEng e quindi necessitano di un implementazione ma, in
			 * questa situazione, non sono utilizzati
			 */
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

			/**
			 * Viene chiamata una volta che si ritorna dall'esecuzione dell'applet
			 */
			@Override
			public void realCloseClick(HybridWindow pWindow) {

				if ((!"".equals(pin)) && (!"".equals(firmatario)) && (!"".equals(alias)) && (!"".equals(metodoFirma))) {
					// Se i tre parametri che ci interessano non sono nulli

					// Richiamo la callback nella classe chiamante
					letturaCertificatoCallBack(pin, firmatario, alias, metodoFirma);

				}
			}

			@Override
			protected void startHybrid(JavaScriptObject jsParams) {
				callHybrid(jsParams);
			}

			@Override
			public void uploadFunction(String fileList) {
			}

		};
	}

	/**
	 * doSelectCertificate è una funzione js definita all'interno di index.jsp
	 * 
	 * @param lMapParams
	 */
	public static native void callHybrid(JavaScriptObject lMapParams) /*-{
		$wnd.doSelectCertificate(lMapParams);
	}-*/;

	/**
	 * Definizione nativa della funzione che dovrà essere richiamata una volta terminata l'esecuzione pin, firmatario e alias definiti nella firma di function e
	 * in quella di callBackAskForCloseFunction sono i parametri che saranno ritornati dall'applet
	 * 
	 * @param selectCertificatoFirmaWindow
	 * @param functionName
	 */
	private native void initCallBackAskForCloseFunction(SelectCertificatoFirmaHybridWindow selectCertificatoFirmaHybridWindow, String functionName) /*-{
		$wnd[functionName] = function(args) {
			selectCertificatoFirmaHybridWindow.@it.eng.auriga.ui.module.layout.client.firmamultiplahash.SelectCertificatoFirmaHybridWindow::callBackAskForCloseFunction(Ljava/lang/String;)(args);
		};
	}-*/;

	/**
	 * Definizione della callback che viene chiamata una volta che viene terminata l'esecuzione dell'applet. Viene chiamata una volta che è stata eseguita la
	 * ricerca del certificato e si deve procedere alla terminazione dell'applet e alla chiamata della callback
	 */
	public void callBackAskForCloseFunction(String args) {

		/*
		 * Nel modulo Hybrid viene ritornato tramite la callback una stringa di parametri concatenata da dei delimitatori. Dev'essere quindi spezzata e
		 * determinati i vari valori
		 */
		this.pin = args.split("#@#")[0];
		this.firmatario = args.split("#@#")[1];
		this.alias = args.split("#@#")[2];
		this.metodoFirma = args.split("#@#")[3];

		appletSelectCertificato.realCloseClick(this);
	}
	
	public boolean isAppletSignMarkEnabled() {
		return AurigaLayout.getParametroDBAsBoolean("APPLET_SIGN_MARK_ENABLED");
	}
}
