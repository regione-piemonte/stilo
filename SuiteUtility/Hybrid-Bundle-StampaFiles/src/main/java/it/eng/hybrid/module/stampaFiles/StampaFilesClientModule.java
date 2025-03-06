package it.eng.hybrid.module.stampaFiles;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Map;

import org.apache.log4j.Logger;

import it.eng.areas.hybrid.module.IClientHttpModule;
import it.eng.areas.hybrid.module.IClientModuleContainer;
import it.eng.areas.hybrid.module.IClientModuleManager;
import it.eng.areas.hybrid.module.IClientWebSocketModule;
import it.eng.areas.hybrid.module.util.HttpModuleUtils;
import it.eng.areas.hybrid.module.util.json.JSONArray;
import it.eng.areas.hybrid.module.util.json.JSONObject;
import it.eng.hybrid.module.stampaFiles.resources.Resources;
import it.eng.hybrid.module.stampaFiles.ui.StampaFileApplication;

public class StampaFilesClientModule implements IClientHttpModule, IClientWebSocketModule {

	public final static Logger logger = Logger.getLogger(StampaFilesClientModule.class);

	private String callBackFunction;
	private String callBackArgs;

	public static void activateBuiltin(IClientModuleManager moduleManager) throws Exception {
		Resources.start("StampaFilesHybridBundle");
		moduleManager.registerModule(new StampaFilesClientModule("StampaFilesHybridBundle", "1.0.0"));
	}

	private String cookies;

	IClientModuleContainer container;
	String name;
	String version;

	public StampaFilesClientModule(String name, String version) {
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

		// Porto a null i valori delle callback
		setCallBackFunction(null);
		setCallBackArgs(null);

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

				if ("stampaFiles".equals(method)) {
					JSONObject options = parameters.getJSONObject(0);
					showAppl(parameters);

					if (callBackFunction != null)
						jResult.put("callBackFunction", callBackFunction);
					if (callBackArgs != null)
						jResult.put("callBackArgs", callBackArgs);

				}
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
		StampaFileApplication appl = new StampaFileApplication(this, parameters);

		appl.setResizable(true);
		appl.setSize(new Dimension(365, 256));
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		appl.setLocation((int) (dim.getWidth() - appl.getWidth()) / 2, 200);
		appl.setVisible(false);
		appl.setAlwaysOnTop(true);
		appl.setFocusable(false);

		logger.debug("Inizio a ciclare");
		while (getCallBackFunction() == null) {

			logger.debug("Ciclo ");
			// Sleep per evitare che le risorse vengano continuamente occupate
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		logger.debug("Chiudo la finestra");
		// Una volta arrivati qui chiudo la finestra
		appl.dispose();
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
		return "/stampaFiles";
	}

	@Override
	public String getModuleName() {
		return name;
	}

	@Override
	public String getModuleVersion() {
		return version;
	}

	public String getCallBackFunction() {
		return callBackFunction;
	}

	public void setCallBackFunction(String callBackFunction) {
		this.callBackFunction = callBackFunction;
	}

	public String getCallBackArgs() {
		return callBackArgs;
	}

	public void setCallBackArgs(String callBackArgs) {
		this.callBackArgs = callBackArgs;
	}

}
