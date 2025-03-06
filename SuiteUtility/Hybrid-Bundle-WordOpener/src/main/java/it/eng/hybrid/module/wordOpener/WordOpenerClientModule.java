package it.eng.hybrid.module.wordOpener;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import it.eng.areas.hybrid.module.IClientHttpModule;
import it.eng.areas.hybrid.module.IClientModuleContainer;
import it.eng.areas.hybrid.module.IClientModuleManager;
import it.eng.areas.hybrid.module.IClientWebSocketModule;
import it.eng.areas.hybrid.module.util.HttpModuleUtils;
import it.eng.areas.hybrid.module.util.json.JSONArray;
import it.eng.areas.hybrid.module.util.json.JSONObject;
import it.eng.hybrid.module.wordOpener.resources.Resources;
import it.eng.hybrid.module.wordOpener.ui.WordOpenerApplication;

public class WordOpenerClientModule implements IClientHttpModule, IClientWebSocketModule {

	public final static Logger logger = Logger.getLogger(WordOpenerClientModule.class);

	public static void activateBuiltin(IClientModuleManager moduleManager) throws Exception {
		Resources.start("WordOpenerHybridBundle");
		moduleManager.registerModule(new WordOpenerClientModule("WordOpenerHybridBundle", "1.0.0"));
	}

	private String cookies;

	IClientModuleContainer container;
	String name;
	String version;
	private String result;
	private List<String> results;

	public WordOpenerClientModule(String name, String version) {
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

		results = null;

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

				if ("wordOpener".equals(method)) {
					JSONObject options = parameters.getJSONObject(0);

					showAppl(parameters);

					if (getResult() != null) {
						jResult.put("signResult", getResult());
					}
					if (results != null && results.size() > 0)
						jResult.put("resultFunction", results.get(0));
					if (results != null && results.size() > 1)
						jResult.put("resultArg", results.get(1));
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
		logger.debug("Dentro showAppl");
		WordOpenerApplication appl = new WordOpenerApplication(this, parameters);

		appl.setResizable(false);
		appl.setTitle("Editor");
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
		return "/wordOpener";
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

	public List<String> getResults() {
		return results;
	}

	public void setResults(List<String> results) {
		this.results = results;
	}

}
