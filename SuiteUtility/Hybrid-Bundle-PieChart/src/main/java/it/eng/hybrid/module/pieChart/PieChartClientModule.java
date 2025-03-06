package it.eng.hybrid.module.pieChart;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;

import org.apache.log4j.Logger;

import it.eng.areas.hybrid.module.IClientHttpModule;
import it.eng.areas.hybrid.module.IClientModuleContainer;
import it.eng.areas.hybrid.module.IClientModuleManager;
import it.eng.areas.hybrid.module.IClientWebSocketModule;
import it.eng.areas.hybrid.module.util.HttpModuleUtils;
import it.eng.areas.hybrid.module.util.json.JSONArray;
import it.eng.areas.hybrid.module.util.json.JSONObject;
import it.eng.hybrid.module.pieChart.resources.Resources;
import it.eng.hybrid.module.pieChart.ui.PieChartApplication;

public class PieChartClientModule implements IClientHttpModule, IClientWebSocketModule {

	public final static Logger logger = Logger.getLogger(PieChartClientModule.class);

	private String callBackFunction;
	private String callBackArgs;

	public static void activateBuiltin(IClientModuleManager moduleManager) throws Exception {
		Resources.start("PieChartClientModule");
		moduleManager.registerModule(new PieChartClientModule("PieChartClientModule", "1.0.0"));
	}

	private String cookies;

	IClientModuleContainer container;
	String name;
	String version;

	public PieChartClientModule(String name, String version) {
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

				if ("pieChart".equals(method)) {
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
		callBackFunction = null;
		callBackArgs = null;
		
		final PieChartApplication appl = new PieChartApplication(this, parameters);

		appl.setResizable(true);
		appl.setSize(new Dimension(800, 500));
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		appl.setLocation((int) (dim.getWidth() - appl.getWidth()) / 2, 200);
		appl.setVisible(true);
		appl.setAlwaysOnTop(true);
		appl.setFocusable(false);
		appl.setTitle("Report");
		appl.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				appl.forcedClose();
				logger.info("Chiudo la finestra");
			}
		});

		while (getCallBackFunction() == null) {// logger.debug("Ciclo nel while");
			// Si inserisce uno sleep altrimenti potrebbe non
			// riuscire ad accedere alla variabile e non terminare mai.
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// Il modulo termina
		appl.dispose();
		logger.debug("PieChartHybridModule terminato");
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
		return "/pieChart";
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
