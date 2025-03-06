package it.eng.hybrid.module.scan;

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
import it.eng.hybrid.module.scan.preferences.PreferenceKeys;
import it.eng.hybrid.module.scan.preferences.PreferenceManager;
import it.eng.hybrid.module.scan.resources.Resources;
import it.eng.hybrid.module.scan.ui.ScanApplication;

public class ScanClientModule implements IClientHttpModule, IClientWebSocketModule {

	public final static Logger logger = Logger.getLogger(ScanClientModule.class);

	public static void activateBuiltin(IClientModuleManager moduleManager) throws Exception {
		Resources.start("ScanHybridBundle");
		moduleManager.registerModule(new ScanClientModule("ScanHybridBundle", "1.0.0"));
	}

	private String cookies;

	IClientModuleContainer container;
	String name;
	String version;

	private String callBackChiusura;
	private String callBackCancel;
	private String callBackChiusuraArg;

	public ScanClientModule(String name, String version) {
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

				if ("scan".equals(method)) {
					JSONObject options = parameters.getJSONObject(0);

					showAppl(parameters);
					logger.info("Dopo showAppl");

					if (getCallBackChiusura() != null) {
						jResult.put("callBackClose", getCallBackChiusura());
						jResult.put("callBackCloseArg", getCallBackChiusuraArg());
					}

				}
				logger.info("Rispondo al chiamante ed esco");
			} catch (Exception e1) {
				logger.error("Errore nel parsing JSON", e1);
				jResult.put("sParcheck", false);
			}

			// Rispondo
			return HttpModuleUtils.returnResource(jResult.toString(), HttpModuleUtils.MIMETYPE_JSON);
		}

		logger.info("Request uri:" + uri + " non gestito");

		return null;
	}

	private void showAppl(JSONArray parameters) {
		logger.info("Entro in showAppl");
		callBackChiusura = null;
		callBackCancel = null;

		// Se ci sono al massimo due istanze attive contemporaneamente del modulo allora apro quello nuovo
		final ScanApplication appl = new ScanApplication(this, parameters);

		appl.setTitle("Scansione documenti");
		appl.setResizable(false);
		appl.setSize(new Dimension(510, 356));
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		appl.setLocation((int) (dim.getWidth() - appl.getWidth()) / 2, 200);
		appl.setVisible(true);
		// appl.setAlwaysOnTop(true); //Impostando questo flag la finestra viene posta davanti a tutto
		appl.setFocusable(false);
		appl.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		appl.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				appl.forcedClose();

				logger.info("Chiudo la finestra");
			}
		});

		while (getCallBackCancel() == null && getCallBackChiusura() == null) {
			// logger.info("getCallBackChiusura ciclo");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		// Imposto il flag a true che indica che la scansione è avvenuta correttamente
		try {
			PreferenceManager.saveProp(PreferenceKeys.PROPERTY_CORRECTEXECUTION, true);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		logger.info("ScanHybridBundle e stato chiuso.");
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
		return "/scan";
	}

	@Override
	public String getModuleName() {
		return name;
	}

	@Override
	public String getModuleVersion() {
		return version;
	}

	public String getCallBackChiusura() {
		return callBackChiusura;
	}

	public String getCallBackCancel() {
		return callBackCancel;
	}

	public void setCallBackCancel(String callBackCancel) {
		this.callBackCancel = callBackCancel;
	}

	public void setCallBackChiusura(String callBackChiusura) {
		this.callBackChiusura = callBackChiusura;
	}

	public String getCallBackChiusuraArg() {
		return callBackChiusuraArg;
	}

	public void setCallBackChiusuraArg(String callBackChiusuraArg) {
		this.callBackChiusuraArg = callBackChiusuraArg;
	}

}
