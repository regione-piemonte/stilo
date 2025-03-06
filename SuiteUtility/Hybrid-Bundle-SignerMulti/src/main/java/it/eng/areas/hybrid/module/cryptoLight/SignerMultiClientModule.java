package it.eng.areas.hybrid.module.cryptoLight;

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
import it.eng.areas.hybrid.module.cryptoLight.resources.Resources;
import it.eng.areas.hybrid.module.cryptoLight.ui.SignApplication;
import it.eng.areas.hybrid.module.util.HttpModuleUtils;
import it.eng.areas.hybrid.module.util.json.JSONArray;
import it.eng.areas.hybrid.module.util.json.JSONObject;

public class SignerMultiClientModule implements IClientHttpModule, IClientWebSocketModule {

	public final static Logger logger = Logger.getLogger(SignerMultiClientModule.class);

	/**
	 * ATTENZIONE OSGi non riesce ad accedere alla classe del provider! Questa libreria va caricata come jar NON OSGi
	 * 
	 * @author GioBo
	 *
	 */
	public static void activateBuiltin(IClientModuleManager moduleManager) throws Exception {
		Resources.start("SignerMultiHybridBundle");
		moduleManager.registerModule(new SignerMultiClientModule("SignerMultiHybridBundle", "1.0.0"));
	}

	public static final String RESULT_OK = "OK";
	public static final String RESULT_ERROR = "ERROR";
	public static final String RESULT_CANCEL = "CANCEL";

	private String cookies;

	private String pin;

	private String callBackClose;
	private String callBackCancel;
	private List<String> commonNameResult;
	private String callBackFunction;
	private String callBackArgs;

	private String[] signResults;

	private String signStatus; // Ok, Error

	IClientModuleContainer container;
	String name;
	String version;

	public SignerMultiClientModule(String name, String version) {
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
			logger.debug("sParameters " + sParameters);

			JSONObject jResult = new JSONObject();
			JSONArray parameters;
			try {
				parameters = (sParameters != null && !"".equals(sParameters)) ? new JSONArray(sParameters) : null;
				jResult.put("sParcheck", true);

				if ("sign".equals(method)) {
					JSONObject options = parameters.getJSONObject(0);

					if (options.has("sourceType")) {
						logger.info("options.has(sourceType) ");
						String optionSourceType = options.getString("sourceType");
						logger.info("optionSourceType " + optionSourceType);
					}

					if (options.has("pin")) {
						logger.info("options.has(pin) ");
						pin = options.getString("pin");

					} else {
						// mostro la finestra di firma
						this.showSignAppl(parameters);
					}

					// Firma
					if (pin != null && !pin.equalsIgnoreCase("")) {
						try {
							jResult.put("callBackClose", callBackClose);
							jResult.put("callBackCancel", callBackCancel);
							jResult.put("status", signStatus);
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
						if (commonNameResult != null && commonNameResult.size() > 0) {
							jResult.put("commonNameFunction", commonNameResult.get(0));
						}
						if (commonNameResult != null && commonNameResult.size() > 1) {
							jResult.put("commonNameArg", commonNameResult.get(1));
						}
						if (callBackFunction != null) {
							jResult.put("callBackFunction", callBackFunction);
						}
						if (callBackArgs != null) {
							jResult.put("callBackArgs", callBackArgs);
						}
					}
				}
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

	// private boolean internalSign(String pin) {
	// logger.info("internalSign " + pin);
	// try {
	//
	// if (!internalAccessToSmartCard(pin)) {
	// signedCancel();
	// return false;//Accesso cancellato
	// }
	//
	//
	// if (sources == null) {
	// //Caso di firma singola (default)
	// signChallenge();
	// signedOk();
	// } else {
	// //Caso di firma multipla
	// String[] sourceItem = sources.split(",");
	// String[] signerClassItem = null;
	// if (signerClasses != null) {
	// signerClassItem = signerClasses.split(",");
	// }
	//
	// this.signResults = new String[sourceItem.length];
	//
	// for (int idx = 0; idx < sourceItem.length; idx++) {
	// this.source = sourceItem[idx];
	// if (signerClassItem != null) {
	// this.signerClass = signerClassItem[idx];
	// }
	// signChallenge();
	// this.signResults[idx] = this.signResult;
	// signedOk();
	//
	// }
	// }
	//
	// signedComplete();
	//
	// return true;
	// } catch (Exception e) {
	// logger.error(e,e);
	// signedError(e.toString());
	// return false;
	// }
	// }

	/**
	 * 
	 * @param pin
	 * @return true accesso riuscito, false cancellato
	 * @throws Exception
	 *             errore in accesso
	 */
	// private boolean internalAccessToSmartCard(String pin) throws Exception {
	// if (keyStore == null) {
	// logger.info("Accesso al keystore tramite pkcs#11");
	// logger.info("Modello dati (32/64) "+System.getProperty("sun.arch.data.model"));
	// if (dlls == null) {
	// //Carico le dll di default
	// dlls = "";
	// Properties dllConfig = new Properties();
	// dllConfig.load(Resources.class.getResourceAsStream("dll.properties"));
	// for (int i = 0; i < 99; i++) {
	// if (dlls.length() > 0) {
	// dlls += ",";
	// }
	// String dllPath = dllConfig.getProperty("provider.dll."+i);
	// if (dllPath != null) {
	// dlls += dllPath;
	// }
	// }
	// }
	//
	// String[] dll = dlls.split(",");
	// for (String checkDll : dll) {
	// while (true) {
	// try {
	// loadKeyStore(checkDll, pin);
	// return true;
	// } catch (Exception e) {
	// logger.error(e,e);
	// if (e instanceof LoginException || e.getCause() instanceof LoginException) {
	// JOptionPane.showMessageDialog(null, "Errore nell'accesso della SmartCard. Il PIN potrebbe essere errato","Attenzione", JOptionPane.WARNING_MESSAGE);
	// pin = null;
	// } else
	// //cambio DLL
	// break;
	// }
	// }
	// }
	//
	//
	// throw new Exception("Impossibile accedere alla smartcard");
	//
	// }
	//
	// return true;
	// }

	/**
	 * @return
	 */
	private void showSignAppl(JSONArray parameters) {

		final SignApplication signApplication = new SignApplication(this, /* null, */ parameters);

		// signApplication.setModal(true);
		signApplication.setTitle("Firma digitale");
		signApplication.setResizable(true);
		signApplication.setSize(new Dimension(365, 556));
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		signApplication.setLocation((int) (dim.getWidth() - signApplication.getWidth()) / 2, 200);
		signApplication.setAlwaysOnTop(true);
		signApplication.setFocusable(false);
		
		signApplication.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				signApplication.forcedClose();
				logger.info("Chiudo la finestra");
			}
			
			@Override
            public void windowIconified(WindowEvent we) {
            	signApplication.setState(JFrame.NORMAL);
            }
		});
		
		signApplication.setVisible(true);

		if (SignApplication.DialogButton.CANCEL.equals(signApplication.getDialogResult())) {
			this.pin = null;
		}

		pin = new String(signApplication.getPin());
		// holdPin = pinDialog.isHoldPin();

		while (getCallBackCancel() == null && getCallBackClose() == null) {
			// Si inserisce uno sleep altrimenti potrebbe non
			// riuscire ad accedere alla variabile e non terminare mai.
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				logger.error(e);
			}
		}

		logger.info("TERMINE SIGNHYBRIDMODULE ");

	}

	// public String accessToSmartCardNoDialog(final String pin) {
	// return java.security.AccessController.doPrivileged(new PrivilegedAction<String>() {
	//
	// public String run() {
	// try {
	// if (internalAccessToSmartCard(pin)) {
	// return RESULT_OK;
	// } else {
	// return RESULT_CANCEL;
	// }
	// } catch (Exception e) {
	// logger.error(e,e);
	// signedError(e.getMessage());
	// return RESULT_ERROR;
	// }
	// }
	// });
	// }

	// public String accessToSmartCard() {
	// return accessToSmartCardNoDialog(null);
	// }

	// public String exportCertificate(final String type) {
	// return java.security.AccessController.doPrivileged(new PrivilegedAction<String>() {
	//
	// public String run() {
	// try {
	// String aliasId = getAliasId(type);
	// Certificate certificate = keyStore.getCertificate(aliasId);
	//
	// signStatus = RESULT_OK;
	// signResult = Base64Utils.base64Encode(certificate.getEncoded());
	// } catch (Exception e) {
	// logger.error(e,e);
	// signedError(e.getMessage());
	// }
	// return signResult;
	// }
	// });
	// }
	//
	// protected void loadKeyStore(String dll, String pin) throws Exception {
	// logger.info("Carico Keystore tramite "+dll+"...");
	// keyStore = SmartCardUtils.loadKeyStoreFromSmartCard(dll,pin, container.getClass().getClassLoader()); //
	// logger.info("Caricato Keystore");
	//
	// logger.info("Certificati nel keystore:");
	// Enumeration<String> aliases = keyStore.aliases();
	// while (aliases.hasMoreElements()) {
	// logger.info(" - "+aliases.nextElement());
	// }
	// }

	// protected String getAliasId(String certificateType) throws Exception {
	// String aliasId = null;
	// if (CERTIFICATE_IDENTITY.equalsIgnoreCase(certificateType)) {
	// aliasId = SmartCardUtils.getIdentityCertificateAlias(keyStore);
	// logger.info("Certificato di Identificazione:"+aliasId);
	// } else if (CERTIFICATE_SIGNATURE.equalsIgnoreCase(certificateType)) {
	// aliasId = SmartCardUtils.getSignatureCertificateAlias(keyStore);
	// logger.info("Certificato di Firma qualificata:"+aliasId);
	// } else
	// throw new IllegalArgumentException(certificateType+": tipo non corretto. Ammessi "+CERTIFICATE_IDENTITY+", "+CERTIFICATE_SIGNATURE);
	//
	// return aliasId;
	// }

	// protected void signChallenge() throws Exception {
	// //Carico la classe di firma
	// SignerInterface signerInterface = new RawSigner();
	// if (SIGNER_RAW_SHA1_WITH_RSA.equalsIgnoreCase(signerClass)) {
	// logger.debug("Firma RAW con SHA1");
	// signerInterface = new RawSigner();
	// } else if (SIGNER_RAW_SHA256_WITH_RSA.equalsIgnoreCase(signerClass)) {
	// logger.debug("Firma RAW con SHA256");
	// signerInterface = new RawSigner(RawSigner.SHA256_RSA_SIGNATURE);
	// } else if (SIGNER_RAW_NONE_WITH_RSA.equalsIgnoreCase(signerClass)) {
	// logger.debug("Firma RAW senza calcolo digest");
	// signerInterface = new RawSigner(RawSigner.RSA_SIGNATURE);
	// } else {
	// logger.debug("Firma con classe "+signerClass);
	// Class<?> clz = Class.forName(signerClass);
	// signerInterface = (SignerInterface) clz.newInstance();
	// }
	//
	//
	// String aliasId = getAliasId(certificateType);
	// if (aliasId == null) {
	// throw new IllegalStateException("certificato di tipo "+certificateType+" non trovato");
	// }
	//
	// PrivateKey privateKey = (PrivateKey) keyStore.getKey(aliasId, null);
	// Certificate[] certificationChain = keyStore.getCertificateChain(aliasId);
	//
	// if (certificationChain.length > 0) {
	// logger.debug(aliasId+":\n" + certificationChain[0]);
	// } else {
	// logger.debug("Certifcato non presente");
	// }
	//
	//
	// logger.debug(privateKey.getAlgorithm());
	//
	// byte[] document = loadDocument(certificationChain[0]);
	//
	// logger.debug("Firmo:\n"+Base64Utils.base64Encode(document));
	//
	//
	// ByteArrayOutputStream dest = new ByteArrayOutputStream();
	// signerInterface.generate(new ByteArrayInputStream(document), dest, privateKey, certificationChain);
	//
	// rawSignedData = dest.toByteArray();
	// signResult = Base64Utils.base64Encode(rawSignedData);
	//
	// logger.debug("Risultato:\n"+signResult);
	//
	// }

	// private byte[] loadDocument(Certificate certificate) throws Exception {
	// if (source == null)
	// throw new IllegalArgumentException("No Document");
	//
	// if (SOURCE_CHALLENGE.equalsIgnoreCase(sourceType)) {
	// return source.getBytes();
	// } else if (SOURCE_DIGEST.equalsIgnoreCase(sourceType)) {
	// return Base64Utils.base64Decode(source);
	// } else if (SOURCE_URL_POST.equalsIgnoreCase(sourceType)) {
	// // Construct data
	// String certificate64 = Base64Utils.base64Encode(certificate.getEncoded());
	// String data = "certificate=" + URLEncoder.encode(certificate64,"UTF-8");
	//
	// String sourceUrl = source;
	// // Codice per rimuovere la sessione dalla url
	// // if (source.toUpperCase().contains(";JSESSIONID=")) {
	// // sourceUrl = source.substring(0, source.toUpperCase().indexOf(";JSESSIONID="));
	// // }
	//
	//
	// // Send data
	// URL url = new URL(sourceUrl);
	// URLConnection conn = url.openConnection();
	//
	// //Creo un cookie per la sessione
	// String cookies = getCookies();
	// String jsession = this.container.getParameter(IClientModuleContainer.PARAMETER_SERVERSESSION);
	// if (cookies != null) {
	// cookies = "JSESSIONID="+jsession + ";" + cookies;
	// } else {
	// cookies = "JSESSIONID="+jsession;
	// }
	// conn.setRequestProperty("Cookie", cookies);
	// logger.debug("Post a "+sourceUrl+" con cookie:" + cookies);
	//
	//
	// conn.setDoOutput(true);
	// OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
	// wr.write(data);
	// wr.flush();
	//
	// // Get the response
	// ByteArrayOutputStream document = new ByteArrayOutputStream();
	//
	// InputStream inputStream = conn.getInputStream();
	//
	// int len = 0;
	// byte[] buffer = new byte[4096];
	//
	// while ((len = inputStream.read(buffer)) > 0) {
	// document.write(buffer, 0, len);
	// }
	//
	// inputStream.close();
	// document.close();
	//
	// return document.toByteArray();
	//
	// }
	// throw new IllegalArgumentException("Tipo sorgente '"+sourceType+"' non riconosciuto");
	// }
	
	private String getCookies() {
		return cookies;
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
		return "/cryptoLight";
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

}
