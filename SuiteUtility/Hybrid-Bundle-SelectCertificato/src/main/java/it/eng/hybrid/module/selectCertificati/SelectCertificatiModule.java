package it.eng.hybrid.module.selectCertificati;

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
import it.eng.hybrid.module.selectCertificati.resources.Resources;
import it.eng.hybrid.module.selectCertificati.ui.SelectCertificatiApplication;

public class SelectCertificatiModule implements IClientHttpModule, IClientWebSocketModule {

	public final static Logger logger = Logger.getLogger(SelectCertificatiModule.class);

	public static void activateBuiltin(IClientModuleManager moduleManager) throws Exception {
		Resources.start("SelectCertificatoHybridBundle");
		moduleManager.registerModule(new SelectCertificatiModule("SelectCertificatoHybridBundle", "1.0.0"));
	}

	private String cookies;

	IClientModuleContainer container;
	String name;
	String version;

	private String callBackChiusura;
	private String callBackChiusuraArg;
	private String callBackCancel;

	public SelectCertificatiModule(String name, String version) {
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

		/*
		 * Inizializzo i valori che devono essere ritornati in modo tale che, agli avvii successivi al primo non vada direttamente al termine dell'esecuzione
		 */
		setCallBackChiusura(null);
		setCallBackChiusuraArg(null);
		setCallBackCancel(null);

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

				if ("selectCertificati".equals(method)) {
					JSONObject options = parameters.getJSONObject(0);

					showAppl(parameters);

					if (getCallBackChiusura() != null) {
						jResult.put("callBackClose", getCallBackChiusura());
					}
					if (getCallBackCancel() != null) {
						jResult.put("callBackCancel", getCallBackCancel());
					}
					if (getCallBackChiusuraArg() != null) {
						jResult.put("callBackArgs", getCallBackChiusuraArg());
					}		
				}
				// logger.info("Rispondo al chiamante ed esco: " + jResult.toString());
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
		final SelectCertificatiApplication appl = new SelectCertificatiApplication(this, parameters);

		appl.setTitle("Selezione certificati");
		appl.setResizable(true);
		appl.setSize(new Dimension(350, 396));
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		appl.setLocation((int) (dim.getWidth() - appl.getWidth()) / 2, 200);
		appl.setAlwaysOnTop(true);
		appl.setFocusable(false);

		appl.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				appl.forcedClose();
				logger.info("Chiudo la finestra");
			}
			
			@Override
            public void windowIconified(WindowEvent we) {
				appl.setState(JFrame.NORMAL);
            }
		});
		
		appl.setVisible(true);

		while (getCallBackChiusura() == null && getCallBackCancel() == null) {
			// Sleep per evitare che le risorse vengano continuamente occupate
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				logger.error(e);
			}
		}
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
		return "/selectCertificati";
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

	public void setCallBackChiusura(String callBackChiusura) {
		this.callBackChiusura = callBackChiusura;
	}

	public String getCallBackCancel() {
		return callBackCancel;
	}

	public void setCallBackCancel(String callBackCancel) {
		this.callBackCancel = callBackCancel;
	}

	public String getCallBackChiusuraArg() {
		return callBackChiusuraArg;
	}

	public void setCallBackChiusuraArg(String callBackChiusuraArg) {
		this.callBackChiusuraArg = callBackChiusuraArg;
	}

}
