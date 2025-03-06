package it.eng.hybrid.module.jpedal;

import it.eng.areas.hybrid.module.IClientHttpModule;
import it.eng.areas.hybrid.module.IClientModuleContainer;
import it.eng.areas.hybrid.module.IClientModuleManager;
import it.eng.areas.hybrid.module.IClientWebSocketModule;
import it.eng.areas.hybrid.module.util.HttpModuleUtils;
import it.eng.areas.hybrid.module.util.json.JSONArray;
import it.eng.areas.hybrid.module.util.json.JSONObject;
import it.eng.hybrid.module.jpedal.resources.Resources;
import it.eng.hybrid.module.jpedal.ui.JPedalApplication;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Map;

import org.apache.log4j.Logger;

public class JPedalClientModule implements IClientHttpModule, IClientWebSocketModule {

	public final static Logger logger = Logger.getLogger(JPedalClientModule.class);

	public static void activateBuiltin(IClientModuleManager moduleManager) throws Exception {
		Resources.start("JPedalHybridBundle");
		moduleManager.registerModule(new JPedalClientModule("JPedalHybridBundle", "1.0.0"));
	}

	private String cookies;

	IClientModuleContainer container;
	String name;
	String version;

	public JPedalClientModule(String name, String version) {
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

		String uri = (String)request.get(REQUEST_URI);
		logger.debug("Request uri:" + uri);
		if (uri.equals(getModuleUri() + "/stub")) {
			return HttpModuleUtils.returnResource(Resources .getInstance().getResource("stub.js"), HttpModuleUtils.MIMETYPE_JS);
		} else if (uri.startsWith(getModuleUri() +"/call/")) {
			String method = uri.substring(uri.lastIndexOf('/') + 1);
			logger.debug("function:"+method);

			//Analizza il payload JSON
			String sParameters = (String)request.get(REQUEST_DATA);
			logger.debug("sParameters " + sParameters );

			JSONObject jResult = new JSONObject();
			JSONArray parameters;
			try {
				parameters = (sParameters != null && !"".equals(sParameters)) ? new JSONArray(sParameters) : null;
				jResult.put("sParcheck", true);

				if ("jpedal".equals(method)) {
					JSONObject options = parameters.getJSONObject(0);
					showAppl(parameters);
				}

			} catch (Exception e1) {
				logger.error("Errore nel parsing JSON", e1);
				jResult.put("sParcheck", false);
			}

			//Rispondo
			return HttpModuleUtils.returnResource(jResult.toString(), HttpModuleUtils.MIMETYPE_JSON);
		}

		logger.info("Request uri:" + uri+" non gestito");

		return null;
	}

	private void showAppl(JSONArray parameters) {
		JPedalApplication appl = new JPedalApplication(this, parameters);

		appl.setResizable(true);
		appl.setSize(new Dimension(700, 500));
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		appl.setLocation((int)(dim.getWidth() - appl.getWidth()) / 2 , 200);
		appl.setVisible(true);
		appl.setAlwaysOnTop(true);
		appl.setFocusable(false);

	}



	@Override
	public void onMessage(String message) throws Exception {
		logger.debug("Messaggio "+message);
	}

	@Override
	public void onClose(int code, String reason, boolean initiatedByRemote) {
		logger.debug("Chiuso canale ws "+reason);
	}

	@Override
	public void onException(Exception e) {
		logger.debug("Errore su canale ws " + e.getMessage());
	}

	@Override
	public String getModuleUri() {
		return "/jpedal";
	}

	@Override
	public String getModuleName() {
		return name;
	}

	@Override
	public String getModuleVersion() {
		return version;
	}

}
