package it.eng.hsm.client.impl;

import java.io.File;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.hsm.client.Hsm;
import it.eng.hsm.client.bean.MessageBean;
import it.eng.hsm.client.bean.ResponseStatus;
import it.eng.hsm.client.bean.cert.CertResponseBean;
import it.eng.hsm.client.bean.marca.FileMarcatoResponseBean;
import it.eng.hsm.client.bean.marca.MarcaRequestBean;
import it.eng.hsm.client.bean.marca.MarcaResponseBean;
import it.eng.hsm.client.bean.otp.OtpResponseBean;
import it.eng.hsm.client.bean.sign.CertificateVerifyResponseBean;
import it.eng.hsm.client.bean.sign.FileResponseBean;
import it.eng.hsm.client.bean.sign.HashRequestBean;
import it.eng.hsm.client.bean.sign.HashResponseBean;
import it.eng.hsm.client.bean.sign.SessionResponseBean;
import it.eng.hsm.client.bean.sign.SignResponseBean;
import it.eng.hsm.client.bean.sign.SignVerifyResponseBean;
import it.eng.hsm.client.config.ArubaConfig;
import it.eng.hsm.client.config.ClientConfig;
import it.eng.hsm.client.config.HsmConfig;
import it.eng.hsm.client.config.HsmType;
import it.eng.hsm.client.config.InfoCertConfig;
import it.eng.hsm.client.config.MarcaConfig;
import it.eng.hsm.client.config.MedasConfig;
import it.eng.hsm.client.config.PkBoxConfig;
import it.eng.hsm.client.config.ProxyConfig;
import it.eng.hsm.client.exception.HsmClientConfigException;
import it.eng.hsm.client.exception.HsmClientSignatureException;
import it.eng.hsm.client.exception.HsmClientSignatureVerifyException;
import it.eng.hsm.client.option.SignOption;
import it.eng.hsm.client.util.ProxyAuthenticator;
import it.eng.hsm.client.util.TsrGenerator;

public class HsmImpl implements Hsm {

	private static Logger logger = Logger.getLogger(HsmImpl.class);

	private HsmConfig hsmConfig;

	public HsmImpl() {

	}

	public static HsmImpl getNewInstance(HsmConfig hsmConfig) {
		HsmImpl instance = null;
		if (hsmConfig.getHsmType() != null) {
			if (hsmConfig.getHsmType().equals(HsmType.ARUBA))
				instance = new HsmAruba();
			if (hsmConfig.getHsmType().equals(HsmType.MEDAS))
				instance = new HsmMedas();
			if (hsmConfig.getHsmType().equals(HsmType.INFOCERT))
				instance = new HsmInfoCert();
			if (hsmConfig.getHsmType().equals(HsmType.PKBOX))
				instance = new HsmPkBox();
			if (hsmConfig.getHsmType().equals(HsmType.ITAGILE))
				instance = new HsmItagile();
			if (hsmConfig.getHsmType().equals(HsmType.UANATACA_CSI))
				instance = new HsmUanatacaCSI();
			if (hsmConfig.getHsmType().equals(HsmType.INFOCERT_CSI))
				instance = new HsmInfocertCSI();
			if (hsmConfig.getHsmType().equals(HsmType.LAMBDA_SERVICE))
				instance = new HsmLambdaService();
		} else
			instance = new HsmImpl();
		instance.setHsmConfig(hsmConfig);
		return instance;
	}

	@Override
	public SignResponseBean firmaCades(List<byte[]>  bytesFileDaFirmare, SignOption signOption)
			throws HsmClientConfigException, HsmClientSignatureException, UnsupportedOperationException {
		throw new UnsupportedOperationException("Funzionalità di firma Cades non supportata");
	}

	@Override
	public SignResponseBean firmaPades(List<byte[]>  bytesFileDaFirmare, SignOption signOption)
			throws HsmClientConfigException, HsmClientSignatureException, UnsupportedOperationException {
		throw new UnsupportedOperationException("Funzionalità di firma Pades non supportata");
	}

	@Override
	public SignResponseBean firmaXades(List<byte[]>  bytesFileDaFirmare, SignOption signOption)
			throws HsmClientConfigException, HsmClientSignatureException, UnsupportedOperationException {
		throw new UnsupportedOperationException("Funzionalità di firma Xades non supportata");
	}

	@Override
	public SignResponseBean firmaMultiplaHash(List<HashRequestBean> listaHashDaFirmare)
			throws HsmClientConfigException, HsmClientSignatureException, UnsupportedOperationException {
		throw new UnsupportedOperationException("Funzionalità di firma multipla hash non supportata");
	}

	@Override
	public OtpResponseBean richiediOTP() throws HsmClientConfigException, UnsupportedOperationException {
		throw new UnsupportedOperationException("Funzionalità di richiesta codice OTP non supportata");
	}

	@Override
	public CertResponseBean getUserCertificateList() throws HsmClientConfigException, UnsupportedOperationException {
		throw new UnsupportedOperationException("Funzionalità di richiesta lista certificati non supportata");
	}

	public CertResponseBean getUserCertificateById() throws HsmClientConfigException, UnsupportedOperationException {
		throw new UnsupportedOperationException("Funzionalità di richiesta lista certificati non supportata");
	}

	@Override
	public SignResponseBean firmaCadesParallela(List<byte[]> bytesFileDaFirmare, SignOption signOption)
			throws HsmClientConfigException, HsmClientSignatureException, UnsupportedOperationException {
		throw new UnsupportedOperationException("Funzionalità di fiurma Cades parallela non supportata");
	}

	@Override
	public MarcaResponseBean aggiungiMarca(File fileDaMarcare) throws HsmClientConfigException, UnsupportedOperationException {
		logger.debug("Metodo di aggiungiMarca - INIZIO");
		MarcaResponseBean marcaResponseBean = new MarcaResponseBean();
		URL url;
		HsmConfig hsmConfig = getHsmConfig();
		if (hsmConfig == null) {
			logger.error("Non e' stata specificata la configurazione");
			throw new HsmClientConfigException("Configurazione non specificata");
		}

		ClientConfig clientConfig = hsmConfig.getClientConfig();
		if (clientConfig == null || !(clientConfig instanceof ArubaConfig)) {
			logger.error("Non e' stata specificata la configurazione per Aruba");
			throw new HsmClientConfigException("Configurazione Aruba non specificata");
		}

		MarcaConfig marcaConfig = clientConfig.getMarcaConfig();
		if (marcaConfig == null) {
			logger.error("Non e' stata specificata la configurazione per il server di marca");
			throw new HsmClientConfigException("Configurazione server di marca non specificata");
		}

		String serviceUrl = marcaConfig.getServiceUrl();
		String serviceUser = marcaConfig.getServiceUser();
		String servicePwd = marcaConfig.getServicePassword();
		boolean useAuth = marcaConfig.isUseAuth();
		logger.debug("serviceUrl: " + serviceUrl + " serviceUser: " + serviceUser + " useAuth: " + useAuth);
		if (serviceUrl == null) {
			logger.error("Configurazione server marca incompleta");
			throw new HsmClientConfigException("Configurazione server marca incompleta");
		}

		ProxyConfig proxyConfig = clientConfig.getProxyConfig();
		TsrGenerator tsrGenerator = new TsrGenerator(serviceUrl, serviceUser, servicePwd, useAuth);
		tsrGenerator.setProxyConfig(proxyConfig);
		byte[] byteFirmati = null;
		MessageBean message = new MessageBean();
		try {
			byteFirmati = tsrGenerator.addMarca(fileDaMarcare);
			if (byteFirmati != null) {
				FileMarcatoResponseBean fileResponseBean = new FileMarcatoResponseBean();
				fileResponseBean.setFileMarcato(byteFirmati);
				marcaResponseBean.setFileMarcatoResponseBean(fileResponseBean);
				message.setStatus(ResponseStatus.OK);
				marcaResponseBean.setMessage(message);
			} else {
				message.setStatus(ResponseStatus.KO);
				marcaResponseBean.setMessage(message);
			}
		} catch (Exception e) {
			message.setStatus(ResponseStatus.KO);
			marcaResponseBean.setMessage(message);
		}

		return marcaResponseBean;
	}
	
	@Override
	public MarcaResponseBean aggiungiMarca(File fileDaMarcare, MarcaRequestBean marcaRequestBean) throws HsmClientConfigException, UnsupportedOperationException {
		return aggiungiMarca(fileDaMarcare);
	}

	public HsmConfig getHsmConfig() {
		return hsmConfig;
	}

	public void setHsmConfig(HsmConfig hsmConf) {
		hsmConfig = hsmConf;
	}

	@Override
	public SessionResponseBean apriSessioneFirmaMultipla() throws HsmClientConfigException, UnsupportedOperationException {
		throw new UnsupportedOperationException("Funzionalità di apertura sessione di firma non supportata");
	}

	@Override
	public SignResponseBean firmaMultiplaHashInSession(List<HashRequestBean> listaHashDaFirmare, String sessionId)
			throws HsmClientConfigException, HsmClientSignatureException, UnsupportedOperationException {
		throw new UnsupportedOperationException("Funzionalità di firma multipla hash in sessione non supportata");
	}

	@Override
	public MessageBean chiudiSessioneFirmaMultipla(String sessionId) throws HsmClientConfigException, UnsupportedOperationException {
		throw new UnsupportedOperationException("Funzionalità di chiusura sessione di firma non supportata");
	}

	protected void initProxyConnection(String proxyHost, String proxyPort, String proxyUser, String proxyPassword) {
		logger.debug("Imposto il proxy con parametri" + " proxyHost:" + proxyHost + " proxyPort: " + proxyPort + " proxyUser: " + proxyUser + " proxyPassword: "
				+ proxyPassword);

		System.setProperty("http.proxyHost", proxyHost);
		System.setProperty("http.proxyPort", proxyPort);
		System.setProperty("http.proxyUser", proxyUser);
		System.setProperty("http.proxyPassword", proxyPassword);

		System.setProperty("https.proxyHost", proxyHost);
		System.setProperty("https.proxyPort", proxyPort);
		System.setProperty("https.proxyUser", proxyUser);
		System.setProperty("https.proxyPassword", proxyPassword);

		if (proxyHost != null && !proxyHost.equals("")) {
			Proxy proxy = createProxy(Proxy.Type.HTTP, proxyHost, Integer.parseInt(proxyPort));
			if (proxyUser != null && !proxyUser.equals(""))
				Authenticator.setDefault(new ProxyAuthenticator(proxyUser, proxyPassword));
		}
	}
	
	protected void resetProxy() {
		System.clearProperty("http.proxyHost");
		System.clearProperty("http.proxyPort");
		System.clearProperty("http.proxyUser");
		System.clearProperty("http.proxyPassword");

		System.clearProperty("https.proxyHost");
		System.clearProperty("https.proxyPort");
		System.clearProperty("https.proxyUser");
		System.clearProperty("https.proxyPassword");
		
		try {
			Authenticator.setDefault(null);
		} catch(Exception e) {
			
		}
	}

	private static Proxy createProxy(Proxy.Type proxyType, String proxyHost, int proxyPort) {
		if (proxyType == null)
			return Proxy.NO_PROXY;
		else {
			return new Proxy(proxyType, new InetSocketAddress(proxyHost, proxyPort));
		}
	}

	@Override
	public SignVerifyResponseBean verificaFirmeCades(byte[] bytesFileDaVerificare)
			throws HsmClientConfigException, HsmClientSignatureVerifyException, UnsupportedOperationException {
		throw new UnsupportedOperationException("Funzionalità di verifica firma non supportata");
	}
	
	@Override
	public SignVerifyResponseBean verificaFirmePades(byte[] bytesFileDaVerificare)
			throws HsmClientConfigException, HsmClientSignatureVerifyException, UnsupportedOperationException {
		throw new UnsupportedOperationException("Funzionalità di verifica firma non supportata");
}
	@Override
	public SignVerifyResponseBean verificaFirmeXades(byte[] bytesFileDaVerificare)
			throws HsmClientConfigException, HsmClientSignatureVerifyException, UnsupportedOperationException {
		throw new UnsupportedOperationException("Funzionalità di verifica firma non supportata");
	}
	
	@Override
	public CertificateVerifyResponseBean verificaCertificato(List<byte[]> listaCertificati)
			throws HsmClientConfigException, HsmClientSignatureVerifyException, UnsupportedOperationException {
		throw new UnsupportedOperationException("Funzionalità di verifica firma non supportata");
	}
	
	@Override
	public CertificateVerifyResponseBean verificaCertificatoPerData(byte[] bytesFileDaVerificare, Date date)
			throws HsmClientConfigException, HsmClientSignatureVerifyException, UnsupportedOperationException {
		throw new UnsupportedOperationException("Funzionalità di verifica firma non supportata");
	}
	
	public String getHsmUserDescription() {
		ClientConfig clientConfig = getHsmConfig().getClientConfig();
		String userDescription = "(";
		try { 
			if (clientConfig != null && clientConfig instanceof ArubaConfig) {
				ArubaConfig arubaClientConfig = (ArubaConfig)clientConfig;
				String username = arubaClientConfig.getUser();
				String password = StringUtils.isNotBlank(arubaClientConfig.getPassword()) ?arubaClientConfig.getPassword().replaceAll(".", "*") : "<vuota>";
				String usernameDelegato = arubaClientConfig.getDelegatedUser();
				String passwordDelegato = StringUtils.isNotBlank(arubaClientConfig.getDelegatedPassword()) ? arubaClientConfig.getDelegatedPassword().replaceAll(".", "*") : "<vuota>";
				String dominioDelegato = arubaClientConfig.getDelegatedDomain();
				String isInDelega = arubaClientConfig.isUseDelegate() + "";
				userDescription += ", HsmUsername: " + username + ", HsmPassword: " + password + ", HsmUsernameDelegato: " + usernameDelegato + ", HsmPasswordDelegato: " + passwordDelegato + ", HsmDominio: " + dominioDelegato + ", HsmInDelega: " + isInDelega;
			}else if (clientConfig != null && clientConfig instanceof MedasConfig) {
				MedasConfig medasClientConfig = (MedasConfig)clientConfig;
				String username = medasClientConfig.getUser();
				String codiceFiscale = medasClientConfig.getCodiceFiscale();
				String password = StringUtils.isNotBlank(medasClientConfig.getPassword()) ? medasClientConfig.getPassword().replaceAll(".", "*") : "<vuota>";
				userDescription += ", HsmUsername: " + username + ", HsmCodiceFiscale: " + codiceFiscale + ", HsmPassword: " + password;
			}else if (clientConfig != null && clientConfig instanceof PkBoxConfig) {
				PkBoxConfig pkBoxClientConfig = (PkBoxConfig)clientConfig;
				String user = pkBoxClientConfig.getUser();
				String password = StringUtils.isNotBlank(pkBoxClientConfig.getPassword()) ? pkBoxClientConfig.getPassword().replaceAll(".", "*") : "<vuota>";
				String alias = pkBoxClientConfig.getAlias();
				String pin = StringUtils.isNotBlank(pkBoxClientConfig.getPin()) ? pkBoxClientConfig.getPin().replaceAll(".", "*") : "<vuota>";
				userDescription += ", HsmUser: " + user + ", HsmPassword: " + password + ", HsmAlias: " + alias + ", HsmPin: " + pin;
			}else if (clientConfig != null && clientConfig instanceof InfoCertConfig) {
				InfoCertConfig infocertClientConfig = (InfoCertConfig)clientConfig;
				String alias = infocertClientConfig.getAlias();
				userDescription += ", Hsmalias: " + alias;
			}
		} catch (Exception e) {
			userDescription = "Errore nel recupero delle informazioni del firmatario";
		}
		userDescription += ")";
		return userDescription;
	}
	
	protected void setSignResponseMessage(SignResponseBean signResponseBean) {
		MessageBean messageBean = new MessageBean();
		messageBean.setStatus(ResponseStatus.OK);
		if ((signResponseBean.getFileResponseBean() != null) ) {
			for (FileResponseBean fileResponseBean : signResponseBean.getFileResponseBean()) {
				if ((fileResponseBean.getMessage() != null) && (!fileResponseBean.getMessage().getStatus().equals(ResponseStatus.OK))) {
					messageBean.setStatus(fileResponseBean.getMessage().getStatus());
					messageBean.setCode(fileResponseBean.getMessage().getCode());
					messageBean.setDescription(fileResponseBean.getMessage().getDescription());
					break;
				}
			}
		} else if (signResponseBean.getHashResponseBean() != null) {
			for (HashResponseBean hashResponseBean : signResponseBean.getHashResponseBean()) {
				if ((hashResponseBean.getMessage() != null) && (!hashResponseBean.getMessage().getStatus().equals(ResponseStatus.OK))) {
					messageBean.setStatus(hashResponseBean.getMessage().getStatus());
					messageBean.setCode(hashResponseBean.getMessage().getCode());
					messageBean.setDescription(hashResponseBean.getMessage().getDescription());
					break;
				}
			}
		}
		signResponseBean.setMessage(messageBean);		
	}

}