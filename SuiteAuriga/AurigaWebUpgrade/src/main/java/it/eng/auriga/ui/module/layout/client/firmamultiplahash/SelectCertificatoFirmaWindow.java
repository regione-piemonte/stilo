/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
 * Window utilizzata per la chiamata dell'applet SelectCertificatoFirmaApplet In questa classe vengono settati i parametri richiesti dall'applet come input e
 * implementato il meccanismo di callback che, una volta ottenuta risposta, rimanda i parametri ottenuti alla classe chiamante
 */

package it.eng.auriga.ui.module.layout.client.firmamultiplahash;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.DragResizeStopEvent;
import com.smartgwt.client.widgets.events.DragResizeStopHandler;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.applet.AppletEng;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.protocollazione.AppletWindow;

public abstract class SelectCertificatoFirmaWindow extends AppletWindow {

	private AppletEng appletSelectCertificato;
	protected SelectCertificatoFirmaWindow _window;
	private String smartId;
	private String commonName;
	private boolean skipCtrlBustaFirm = false;

	private String pin = "";
	private String firmatario = "";
	private String alias = "";
	private String metodoFirma = "";

	public abstract void letturaCertificatoCallBack(String pin, String firmatario, String alias, String metodoFirma);

	public SelectCertificatoFirmaWindow() {

		super(I18NUtil.getMessages().protocollazione_firmaWindow_title(), "SelectCertificatoFirmaApplet" + getSelCertificatoFirmaAppletJarVersion() + ".jar");

		_window = this;
		smartId = SC.generateID();

		setID(smartId);

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

		lMapParams.put("outputProvider", "it.eng.client.applet.response.FirmatarioOutputProvider");

		// Per il test della connessione della rete
		lMapParams.put("testUrl", GWT.getHostPageBaseURL() + "springdispatcher/TestUploadServlet/");
		
		// Parametro per disabilitare il controllo della scadenza del certificato
		String disabilitaControlloCertificatoScaduto = AurigaLayout.getParametroDBAsBoolean("DISABILITA_CONTROLLO_CERTIFICATO_SCADUTO_FIRMA_CLIENT") + "";
		lMapParams.put("disabilitaControlloCertificatoScaduto", disabilitaControlloCertificatoScaduto);
		
		// Indica se è abilitata la verifica di controllo coerenza del certificato di firma
		String attivaCtrlUserVsCertifFirmaDig = AurigaLayout.getParametroDBAsBoolean("ATTIVA_CTRL_USER_VS_CERTIF_FIRMA_DIG") + "";
		lMapParams.put("ctrlUserVsCertifFirmaDigAttivato", attivaCtrlUserVsCertifFirmaDig);

		this.setRedrawOnResize(true);

		/*
		 * it.eng.client.applet.SelectFirmatariApplet è dove si trova il metodo init()
		 */
		appletSelectCertificato = new AppletEng("SelectCertificatoFirmaApplet", "SelectCertificatoFirmaApplet.jnlp", lMapParams, 467, 350, true, true, this,
				new String[] {appletJarName}, "it.eng.client.applet.SelectFirmatariApplet") {

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
			public void realCloseClick(Window pWindow) {
				pWindow.markForDestroy(); // Mette in coda la finestra che deve essere distrutta

				if ((pin != null && !"".equals(pin)) && (firmatario != null && !"".equals(firmatario)) 
						&& (alias != null && !"".equals(alias)) && (metodoFirma != null && !"".equals(metodoFirma))) {
					// Se i tre parametri che ci interessano non sono nulli

					// Richiamo la callback nella classe chiamante
					letturaCertificatoCallBack(pin, firmatario, alias, metodoFirma);
				}
			}
		};

		/*
		 * Aggiungo il contenuto dell'applet all'interno della finestra che è stata creata. Se questa riga non fosse presente l'applet non avrebbe un
		 * contenitore
		 */
		addItem(appletSelectCertificato);

		// Adatta l'applet alla finestra che la contiene quando viene ridimensionata la finestra
		addDragResizeStopHandler(new DragResizeStopHandler() {

			@Override
			public void onDragResizeStop(DragResizeStopEvent event) {
				markForRedraw();
				appletSelectCertificato.setWidth100();
				appletSelectCertificato.setHeight100();
				appletSelectCertificato.markForRedraw();
				setAutoSize(true);
			}
		});
	}

	/**
	 * Definizione nativa della funzione che dovrà essere richiamata una volta terminata l'esecuzione pin, firmatario e alias definiti nella firma di function e
	 * in quella di callBackAskForCloseFunction sono i parametri che saranno ritornati dall'applet
	 * 
	 * @param selectCertificatoFirmaWindow
	 * @param functionName
	 */
	private native void initCallBackAskForCloseFunction(SelectCertificatoFirmaWindow selectCertificatoFirmaWindow, String functionName) /*-{
		//ATTENZIONE: MODIFICARE ANCHE I PARAMETRI DI FUNCTION NELLA RIGA SEGUENTE SE NECESSARIO
		$wnd[functionName] = function(pin, firmatario, alias, metodoFirma) {
			selectCertificatoFirmaWindow.@it.eng.auriga.ui.module.layout.client.firmamultiplahash.SelectCertificatoFirmaWindow::callBackAskForCloseFunction(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)(pin, firmatario, alias, metodoFirma);
		};
	}-*/;

	/**
	 * Definizione della callback che viene chiamata una volta che viene terminata l'esecuzione dell'applet. Viene chiamata una volta che è stata eseguita la
	 * ricerca del certificato e si deve procedere alla terminazione dell'applet e alla chiamata della callback
	 */
	public void callBackAskForCloseFunction(String pin, String firmatario, String alias, String metodoFirma) {

		// Assegno il valore alle variabili locali
		this.pin = pin;
		this.firmatario = firmatario;
		this.alias = alias;
		this.metodoFirma = metodoFirma;

		appletSelectCertificato.realCloseClick(this);
	}
	
	public boolean isAppletSignMarkEnabled() {
		return AurigaLayout.getParametroDBAsBoolean("APPLET_SIGN_MARK_ENABLED");
	}
}
