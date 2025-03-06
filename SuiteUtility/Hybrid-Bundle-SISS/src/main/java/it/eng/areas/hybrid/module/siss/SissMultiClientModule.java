package it.eng.areas.hybrid.module.siss;

import it.eng.areas.hybrid.module.IClientHttpModule;
import it.eng.areas.hybrid.module.IClientModuleContainer;
import it.eng.areas.hybrid.module.IClientModuleManager;
import it.eng.areas.hybrid.module.IClientWebSocketModule;
import it.eng.areas.hybrid.module.siss.resources.Resources;
import it.eng.areas.hybrid.module.siss.ui.SissApplication;
import it.eng.areas.hybrid.module.util.HttpModuleUtils;
import it.eng.areas.hybrid.module.util.json.JSONArray;
import it.eng.areas.hybrid.module.util.json.JSONObject;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Map;

import org.apache.log4j.Logger;

public class SissMultiClientModule implements IClientHttpModule, IClientWebSocketModule {

	public final static Logger logger = Logger.getLogger(SissMultiClientModule.class);

	public static void activateBuiltin(IClientModuleManager moduleManager) throws Exception {
		Resources.start("SissMultiHybridBundle");
		moduleManager.registerModule(new SissMultiClientModule("SissMultiHybridBundle", "1.0.0"));
	}



	public  static final String RESULT_OK = "OK";
	public static final String RESULT_ERROR = "ERROR";
	public static final String RESULT_CANCEL = "CANCEL";

	private String cookies;

	private String pin;

	private String signResult;

	private String[] signResults;

	private String signStatus; //Ok, Error

	IClientModuleContainer container;
	String name;
	String version;

	public SissMultiClientModule(String name, String version) {
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
			return HttpModuleUtils.returnResource(Resources.getInstance().getResource("stub.js"), HttpModuleUtils.MIMETYPE_JS);
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

				if ("siss".equals(method)) {
					JSONObject options = parameters.getJSONObject(0);


					if (options.has("sourceType")) {
						logger.info("options.has(sourceType) " );
						String optionSourceType = options.getString("sourceType");
						logger.info("optionSourceType " + optionSourceType );
					}

					if (options.has("pin")) {
						logger.info("options.has(pin) " );
						pin = options.getString("pin");

					} else {
						//mostro la finestra di firma
						this.showSignAppl( parameters );
					}




					//Firma
					if (pin != null && !pin.equalsIgnoreCase("")) {
						try {
							//						if (internalSign(pin)) {
							//							//Prendo il/i risultati
							//							if (this.sources != null) {
							//								JSONArray jSignResults = new JSONArray();
							//								for (int idx = 0; idx < signResults.length; idx++) {
							//									jSignResults.put(signResults[idx]);
							//								}
							//								jResult.put("signResults", jSignResults);
							//
							//							} else {
							//								jResult.put("signResult", signResult);
							//							}
							//
							//							jResult.put("result", "");
							//						} else {
							jResult.put("signResult", signResult);
							//						}
							jResult.put("status", signStatus);

						} catch (Exception e) {
							jResult.put("status", "ERROR");
							jResult.put("signResult", e.getMessage());
						}
					} else {
						if( getSignResult()!=null ){
							jResult.put("signResult", getSignResult());
						} else {
							//jResult.put("status", "CANCEL");
							//jResult.put("signResult", "Cancellato dall' utente");
						}
					}


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





	/**
	 * @return
	 */
	private void showSignAppl(JSONArray parameters) {

		SissApplication signApplication = new SissApplication(this, parameters);

		//signApplication.setModal(true);
		signApplication.setResizable(true);
		signApplication.setSize(new Dimension(365, 256));
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		signApplication.setLocation((int)(dim.getWidth() - signApplication.getWidth()) / 2 , 200);
		//signApplication.setAlwaysOnTop(true);
		signApplication.setVisible(true);
		signApplication.setAlwaysOnTop(true);
		signApplication.setFocusable(false);



		while( getSignResult()==null){

		}
	}

	private String getCookies() {
		return cookies;
	}

	protected void signedOk() {
		//Se necessario invoco i javascript
		signStatus = RESULT_OK;
		logger.debug("signStatus:" + signStatus);

	}

	/**
	 * Javascript di esecuzione completa
	 */
	protected void signedComplete() {
	}


	protected void signedError(String message) {
		//Se necessario invoco i javascript
		signStatus = RESULT_ERROR;
		signResult = (message != null) ? message : "No Message";
		logger.debug("signStatus:" + signStatus);
	}

	protected void signedCancel() {
		//Se necessario invoco i javascript
		signStatus = RESULT_CANCEL;
		logger.debug("signStatus:" + signStatus);
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
		return "/siss";
	}

	@Override
	public String getModuleName() {
		return name;
	}

	@Override
	public String getModuleVersion() {
		return version;
	}

	public String getSignResult() {
		return signResult;
	}

	public void setSignResult(String signResult) {
		this.signResult = signResult;
	}



}
