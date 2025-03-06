package it.eng.hybrid.module.stampaEtichette;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;

import javax.swing.JFrame;

import org.apache.log4j.Logger;

import it.eng.areas.hybrid.module.IClientHttpModule;
import it.eng.areas.hybrid.module.IClientModuleContainer;
import it.eng.areas.hybrid.module.IClientModuleManager;
import it.eng.areas.hybrid.module.IClientWebSocketModule;
import it.eng.areas.hybrid.module.util.HttpModuleUtils;
import it.eng.areas.hybrid.module.util.json.JSONArray;
import it.eng.areas.hybrid.module.util.json.JSONObject;
import it.eng.hybrid.module.stampaEtichette.resources.Resources;
import it.eng.hybrid.module.stampaEtichette.ui.StampaEtichetteApplication;

public class StampaEtichetteClientModule implements IClientHttpModule, IClientWebSocketModule {

	public final static Logger logger = Logger.getLogger(StampaEtichetteClientModule.class);

	/**
	 * ATTENZIONE OSGi non riesce ad accedere alla classe del provider! Questa libreria va caricata come jar NON OSGi
	 * 
	 * @author GioBo
	 *
	 */
	public static void activateBuiltin(IClientModuleManager moduleManager) throws Exception {
		Resources.start("StampaEtichetteHybridBundle");
		moduleManager.registerModule(new StampaEtichetteClientModule("StampaEtichetteHybridBundle", "1.0.0"));
	}

	private String cookies;

	IClientModuleContainer container;
	String name;
	String version;
	private String result;

	public StampaEtichetteClientModule(String name, String version) {
		this.name = name;
		this.version = version;
	}

	@Override
	public void initModule(IClientModuleContainer container) throws Exception {
		this.container = container;
	}

	@Override
	public Map<String, Object> processHttpRequest(Map<String, Object> request) throws Exception {
		this.cookies = this.container.getParameter("cookie");

		String uri = (String) request.get(REQUEST_URI);
		logger.debug("Request uri:" + uri);
		if (uri.equals(getModuleUri() + "/stub")) {
			return HttpModuleUtils.returnResource(Resources.getInstance().getResource("stub.js"), HttpModuleUtils.MIMETYPE_JS);
		} else if (uri.startsWith(getModuleUri() + "/call/")) {
			String method = uri.substring(uri.lastIndexOf('/') + 1);
			logger.debug("function:" + method);

			// Analizza il payload JSON
			String sParameters = (String) request.get(REQUEST_DATA);
			logger.debug("sParameters " + sParameters);


			JSONObject jResult = new JSONObject();
			JSONArray parameters;
			try {
				parameters = (sParameters != null && !"".equals(sParameters)) ? new JSONArray(sParameters) : null;
				jResult.put("sParcheck", true);

				if ("stampaEtichette".equals(method)) {
					JSONObject options = parameters.getJSONObject(0);

					/*
					 * Per fare in modo che se aperto il modulo due volte in successione non arrivi direttamente al termine del modulo e non invii gi� i dati in
					 * risposta imposto il valore di result a null
					 */
					setResult(null);

					showAppl(parameters);

					if (getResult() != null) {
						jResult.put("signResult", getResult());
					}

				}
			} catch (Exception e1) {
				logger.error(e1);
				jResult.put("sParcheck", false);
			}

			// Rispondo
			return HttpModuleUtils.returnResource(jResult.toString(), HttpModuleUtils.MIMETYPE_JSON);
		}

		logger.info("Request uri:" + uri + " non gestito");

		return null;
	}

	private void showAppl(JSONArray parameters) {
		setResult(null); // Imposto il result a null

		logger.debug("Entro in showAppl");

		final StampaEtichetteApplication appl = new StampaEtichetteApplication(this, parameters);

		appl.setTitle("Stampa etichette");
		appl.setResizable(false);
		appl.setSize(new Dimension(363, 150));
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		appl.setLocation((int) (dim.getWidth() - appl.getWidth()) / 2, 200);
		appl.setVisible(true);
		appl.setAlwaysOnTop(true);
		appl.setFocusable(false);
		appl.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		appl.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				appl.forcedClose();
				logger.info("Chiudo la finestra");
			}
		});
		
		appl.eseguiStampa();

		while (getResult() == null) {
			// logger.info("getResult");
			// Si inserisce uno sleep altrimenti potrebbe non
			// riuscire ad accedere alla variabile e non terminare mai.
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// Attesa per lasciare tempo alla finestra di venire visualizzata
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		appl.checkAndClose();

		logger.debug("StampaEtichetteHybridModule e' stato chiuso");
	}

	@Override
	public void onMessage(String message) throws Exception {
		logger.debug("Messaggio " + message);
	}

	@Override
	public void onClose(int code, String reason, boolean initiatedByRemote) {
		logger.debug("Chiuso canale ws " + reason);
	}

	@Override
	public void onException(Exception e) {
		logger.debug("Errore su canale ws " + e.getMessage());
	}

	@Override
	public String getModuleUri() {
		return "/stampaEtichette";
	}

	@Override
	public String getModuleName() {
		return name;
	}

	@Override
	public String getModuleVersion() {
		return version;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
