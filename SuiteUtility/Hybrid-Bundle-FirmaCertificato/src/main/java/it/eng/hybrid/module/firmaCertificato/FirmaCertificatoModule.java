package it.eng.hybrid.module.firmaCertificato;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
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
import it.eng.hybrid.module.firmaCertificato.resources.Resources;
import it.eng.hybrid.module.firmaCertificato.ui.FirmaCertificatoApplication;


public class FirmaCertificatoModule implements IClientHttpModule, IClientWebSocketModule {

	public final static Logger logger = Logger.getLogger(FirmaCertificatoModule.class);

	public static void activateBuiltin(IClientModuleManager moduleManager) throws Exception {
		Resources.start("FirmaCertificatoHybridBundle");
		moduleManager.registerModule(new FirmaCertificatoModule("FirmaCertificatoHybridBundle", "1.0.0"));
	}

	private String cookies;

	public static final String RESULT_OK = "OK";
	public static final String RESULT_ERROR = "ERROR";
	public static final String RESULT_CANCEL = "CANCEL";

	IClientModuleContainer container;
	String name;
	String version;

	private String callBackClose;
	private String callBackCancel;
	private List<String> commonNameResult;
	private String callBackFunction;
	private String callBackArgs;

	private String signStatus; // Ok, Error

	private String pin;

	public FirmaCertificatoModule(String name, String version) {
		this.name = name;
		this.version = version;
	}

	@Override
	public void initModule(IClientModuleContainer container) throws Exception {
		this.container = container;
	}

	@Override
	public Map<String, Object> processHttpRequest(Map<String, Object> request) throws Exception {

		callBackClose = null;
		callBackCancel = null;
		commonNameResult = null;
		signStatus = null;
		callBackFunction = null;
		callBackArgs = null;

		this.cookies = this.container.getParameter("cookie");

		String uri = (String) request.get(REQUEST_URI);
		logger.debug("Request uri:" + uri);
		if (uri.equals(getModuleUri() + "/stub")) {
			return HttpModuleUtils.returnResource(Resources.getInstance().getResource("stub.js"), HttpModuleUtils.MIMETYPE_JS);
		} else if (uri.startsWith(getModuleUri() + "/call/")) {
			String method = uri.substring(uri.lastIndexOf('/') + 1);
			logger.debug("function:" + method + " versione 16 marzo 2021");

			// Analizza il payload JSON
			String sParameters = (String) request.get(REQUEST_DATA);
			//			logger.debug("sParameters " + sParameters);

			JSONObject jResult = new JSONObject();
			JSONArray parameters;
			try {
				parameters = (sParameters != null && !"".equals(sParameters)) ? new JSONArray(sParameters) : null;
				jResult.put("sParcheck", true);
				
				if ("firmaCertificato".equals(method)) {
					JSONObject options = parameters.getJSONObject(0);

					showAppl(parameters);

					// Firma
					if (pin != null && !pin.equalsIgnoreCase("")) {

						try {

							//Per il ritorno alla funzione di upload con gli argomenti relativi
							if (callBackFunction != null) {
								jResult.put("callBackFunction", callBackFunction);
							}
							if (callBackArgs != null) {
								jResult.put("callBackArgs", callBackArgs);
							}
							//Per il ritorno quando si è eseguito l'upload
							if (getCallBackClose() != null) {
								jResult.put("callBackClose", callBackClose);
							}
							//Per il ritorno quando si annulla l'operazione
							if (getCallBackCancel() != null) {
								jResult.put("callBackCancel", callBackCancel);
							}
							//Status della firma
							if (signStatus != null) {
								jResult.put("status", signStatus);
							}
							if (commonNameResult != null && commonNameResult.size() > 0)
								jResult.put("commonNameFunction", commonNameResult.get(0));
							if (commonNameResult != null && commonNameResult.size() > 1)
								jResult.put("commonNameArg", commonNameResult.get(1));
						} catch (Exception e) {
							jResult.put("status", "ERROR");
							jResult.put("signResult", e.getMessage());
						}
					} else {
						if (getCallBackClose() != null) {
							jResult.put("callBackClose", getCallBackClose());
						}
						if (getCallBackCancel() != null) {
							jResult.put("callBackCancel", getCallBackCancel());
						}
						if (commonNameResult != null && commonNameResult.size() > 0)
							jResult.put("commonNameFunction", commonNameResult.get(0));
						if (commonNameResult != null && commonNameResult.size() > 1)
							jResult.put("commonNameArg", commonNameResult.get(1));

						if (callBackFunction != null)
							jResult.put("callBackFunction", callBackFunction);
						if (callBackArgs != null)
							jResult.put("callBackArgs", callBackArgs);
					}
				}
				
				logger.info("Rispondo al chiamante ed esco");
			} catch (Exception e1) {
				logger.error("Errore nel parsing JSON", e1);
				jResult.put("sParcheck", false);

			}
			// Rispondo
			logger.debug("jResult: " + jResult.toString());
			return HttpModuleUtils.returnResource(jResult.toString(), HttpModuleUtils.MIMETYPE_JSON);
		}

		logger.info("Request uri:" + uri + " non gestito");

		return null;
	}

	private void showAppl(JSONArray parameters) {
		logger.info("Entro in showAppl");
		final FirmaCertificatoApplication appl = new FirmaCertificatoApplication(this, parameters);

		appl.setTitle("Applicazione di firma");
		appl.setResizable(true);
		appl.setSize(new Dimension(370, 500));
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		appl.setLocation((int) (dim.getWidth() - appl.getWidth()) / 2, 200);
		appl.setAlwaysOnTop(true);
		appl.setFocusable(false);

		if (FirmaCertificatoApplication.DialogButton.CANCEL.equals(appl.getDialogResult())) {
			this.pin = null;
		}

		pin = new String(appl.getPin());

		// holdPin = pinDialog.isHoldPin();

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

		while (getCallBackCancel() == null && getCallBackClose() == null ) {
			// Si inserisce uno sleep altrimenti potrebbe non
			// riuscire ad accedere alla variabile e non terminare mai.
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
		return "/firmaCertificato";
	}

	@Override
	public String getModuleName() {
		return name;
	}

	@Override
	public String getModuleVersion() {
		return version;
	}

	public String getCallBackClose() {
		return callBackClose;
	}

	public String getCallBackCancel() {
		return callBackCancel;
	}

	public void setCallBackClose(String callBackClose) {
		this.callBackClose = callBackClose;
	}

	public void setCallBackCancel(String callBackCancel) {
		this.callBackCancel = callBackCancel;
	}

	public List<String> getCommonNameResult() {
		return commonNameResult;
	}

	public void setCommonNameResult(List<String> commonNameResult) {
		this.commonNameResult = commonNameResult;
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

	protected void signedOk() {
		// Se necessario invoco i javascript
		signStatus = RESULT_OK;
		logger.debug("signStatus:" + signStatus);

	}

	/**
	 * Javascript di esecuzione completa
	 */
	protected void signedComplete() {
	}

	protected void signedError(String message) {
		// Se necessario invoco i javascript
		signStatus = RESULT_ERROR;
		callBackClose = (message != null) ? message : "No Message";
		logger.debug("signStatus:" + signStatus);
	}

	protected void signedCancel() {
		// Se necessario invoco i javascript
		signStatus = RESULT_CANCEL;
		logger.debug("signStatus:" + signStatus);
	}
}
