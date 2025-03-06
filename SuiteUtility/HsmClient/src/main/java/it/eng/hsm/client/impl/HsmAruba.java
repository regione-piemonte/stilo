package it.eng.hsm.client.impl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import it.eng.hsm.client.aruba.generated.ArssReturn;
import it.eng.hsm.client.aruba.generated.ArubaSignService;
import it.eng.hsm.client.aruba.generated.Auth;
import it.eng.hsm.client.aruba.generated.CanonicalizedType;
import it.eng.hsm.client.aruba.generated.Cert;
import it.eng.hsm.client.aruba.generated.CredentialsType;
import it.eng.hsm.client.aruba.generated.PdfProfile;
import it.eng.hsm.client.aruba.generated.PdfSignApparence;
import it.eng.hsm.client.aruba.generated.SignHashRequest;
import it.eng.hsm.client.aruba.generated.SignHashReturn;
import it.eng.hsm.client.aruba.generated.SignRequestV2;
import it.eng.hsm.client.aruba.generated.SignReturnV2;
import it.eng.hsm.client.aruba.generated.TypeOfTransportNotImplemented_Exception;
import it.eng.hsm.client.aruba.generated.TypeTransport;
import it.eng.hsm.client.aruba.generated.UserCertList;
import it.eng.hsm.client.aruba.generated.XmlSignatureParameter;
import it.eng.hsm.client.aruba.generated.XmlSignatureType;
import it.eng.hsm.client.bean.MessageBean;
import it.eng.hsm.client.bean.ResponseStatus;
import it.eng.hsm.client.bean.cert.CertBean;
import it.eng.hsm.client.bean.cert.CertResponseBean;
import it.eng.hsm.client.bean.otp.OtpResponseBean;
import it.eng.hsm.client.bean.sign.FileResponseBean;
import it.eng.hsm.client.bean.sign.HashRequestBean;
import it.eng.hsm.client.bean.sign.HashResponseBean;
import it.eng.hsm.client.bean.sign.SessionResponseBean;
import it.eng.hsm.client.bean.sign.SignResponseBean;
import it.eng.hsm.client.config.ArubaConfig;
import it.eng.hsm.client.config.ClientConfig;
import it.eng.hsm.client.config.HsmConfig;
import it.eng.hsm.client.config.PadesConfig;
import it.eng.hsm.client.config.ProxyConfig;
import it.eng.hsm.client.config.WSConfig;
import it.eng.hsm.client.exception.HsmClientConfigException;
import it.eng.hsm.client.exception.HsmClientSignatureException;
import it.eng.hsm.client.option.SignOption;

public class HsmAruba extends HsmImpl {

	private Logger logger = Logger.getLogger(getClass());

	@Override
	public SignResponseBean firmaCades(List<byte[]> listaBytesFileDaFirmare, SignOption signOption)
			throws HsmClientConfigException, HsmClientSignatureException, UnsupportedOperationException {
		logger.debug("Metodo di firma file cades - INIZIO");
		SignResponseBean signResponseBean = new SignResponseBean();
		URL url;
		HsmConfig hsmConfig = getHsmConfig();
		if (hsmConfig == null) {
			logger.error("Non e' stata specificata la configurazione" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione non specificata");
		}

		ClientConfig clientConfig = hsmConfig.getClientConfig();
		if (clientConfig == null || !(clientConfig instanceof ArubaConfig)) {
			logger.error("Non e' stata specificata la configurazione per Aruba" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione Aruba non specificata");
		}

		ArubaConfig arubaConfig = (ArubaConfig) hsmConfig.getClientConfig();

		ProxyConfig proxyConfig = arubaConfig.getProxyConfig();
		if (proxyConfig != null && proxyConfig.isUseProxy()) {
			initProxyConnection(proxyConfig.getProxyHost(), proxyConfig.getProxyPort(), proxyConfig.getProxyUser(), proxyConfig.getProxyPassword());
		} else {
			resetProxy();
		}

		WSConfig wsSignConfig = arubaConfig.getWsSignConfig();
		if (wsSignConfig == null) {
			logger.error("Non e' stata specificata la configurazione per il ws di firma" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione WS di firma non specificata");
		}

		String wsdlEndpoint = wsSignConfig.getWsdlEndpoint();
		String serviceNS = wsSignConfig.getServiceNS();
		String serviceName = wsSignConfig.getServiceName();
		logger.debug("wsdlEndpoint: " + wsdlEndpoint + " serviceNS: " + serviceNS + " serviceName: " + serviceName);
		if (wsdlEndpoint == null || serviceNS == null || serviceName == null) {
			logger.error("Configurazione ws incompleta" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione ws incompleta");
		}

		try {
			url = new URL(wsdlEndpoint);
			QName qname = new QName(serviceNS, serviceName);
			Service service = Service.create(url, qname);
			ArubaSignService arubaService = service.getPort(ArubaSignService.class);
			SOAPBinding binding = (SOAPBinding) ((BindingProvider) arubaService).getBinding();
			SignRequestV2 signRequest = new SignRequestV2();
			signRequest.setCertID("AS0");

			signRequest.setTransport(TypeTransport.BYNARYNET);
			signRequest.setBinaryinput(listaBytesFileDaFirmare.get(0));
			signRequest.setRequiredmark(false);

			Auth identity = new Auth();
			logger.debug("User: " + arubaConfig.getUser() + " Password: ****** " + " TypeOtpAuth: " + arubaConfig.getTypeOtpAuth() + " OtpPwd: "
					+ arubaConfig.getOtpPwd());
			identity.setUser(arubaConfig.getUser());

			if (arubaConfig.isUseDelegate()) {
				logger.debug("User delegated: " + arubaConfig.getDelegatedUser() + " Password delegated: ****** " + " DelegatedDomain: "
						+ arubaConfig.getDelegatedDomain());
				identity.setDelegatedUser(arubaConfig.getDelegatedUser());
				identity.setDelegatedPassword(arubaConfig.getDelegatedPassword());
				identity.setDelegatedDomain(arubaConfig.getDelegatedDomain());
			} else {
				identity.setUserPWD(arubaConfig.getPassword());
			}

			identity.setTypeOtpAuth(arubaConfig.getTypeOtpAuth());
			identity.setOtpPwd(arubaConfig.getOtpPwd());
			signRequest.setIdentity(identity);

			SignReturnV2 response = arubaService.pkcs7SignV2(signRequest, false, true);

			if (response != null) {
				logger.debug("Response Status: " + response.getStatus());
				logger.debug("Response Return Code: " + response.getReturnCode());
				if (response.getStatus() != null && response.getStatus().equalsIgnoreCase("KO")) {
					MessageBean messageBean = new MessageBean();
					messageBean.setDescription(response.getDescription());
					messageBean.setCode(response.getReturnCode());
					messageBean.setStatus(ResponseStatus.KO);
					logger.error("Errore nella risposta di firma cades - Descrizione errore: " + response.getDescription() + " " + getHsmUserDescription());
					signResponseBean.setMessage(messageBean);
					for(int i=0;i<listaBytesFileDaFirmare.size();i++){
						FileResponseBean fileResponseBean = new FileResponseBean();
						fileResponseBean.setMessage(messageBean);
						signResponseBean.getFileResponseBean().add(fileResponseBean);
					}
				} else {
					byte[] fileOutput = response.getBinaryoutput();
					logger.debug("Ricevuto file firmato");
					MessageBean messageBean = new MessageBean();
					messageBean.setStatus(ResponseStatus.OK);
					FileResponseBean fileResponseBean = new FileResponseBean();
					fileResponseBean.setMessage(messageBean);
					fileResponseBean.setFileFirmato(fileOutput);
					signResponseBean.getFileResponseBean().add(fileResponseBean);
				}
			} else {
				MessageBean messageBean = new MessageBean();
				messageBean.setDescription("Errore nella risposta di firma cades ");
				logger.error("Errore nella risposta di firma cades" + " " + getHsmUserDescription());
				signResponseBean.setMessage(messageBean);
				for(int i=0;i<listaBytesFileDaFirmare.size();i++){
					FileResponseBean fileResponseBean = new FileResponseBean();
					fileResponseBean.setMessage(messageBean);
					signResponseBean.getFileResponseBean().add(fileResponseBean);
				}
			}
			setSignResponseMessage(signResponseBean);
			
		} catch (TypeOfTransportNotImplemented_Exception e) {
			logger.error(getHsmUserDescription(), e);
			throw new HsmClientSignatureException("Errore in fase di firma");
		} catch (MalformedURLException e) {
			logger.error(getHsmUserDescription(), e);
			throw new HsmClientSignatureException("Errore in fase di firma");
		}
		return signResponseBean;
	}

	@Override
	public SignResponseBean firmaCadesParallela(List<byte[]> listaBytesFileDaFirmare, SignOption signOption)
			throws HsmClientConfigException, HsmClientSignatureException, UnsupportedOperationException {
		logger.debug("Metodo di firma file cades parallela - INIZIO");
		SignResponseBean signResponseBean = new SignResponseBean();
		URL url;
		HsmConfig hsmConfig = getHsmConfig();
		if (hsmConfig == null) {
			logger.error("Non e' stata specificata la configurazione" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione non specificata");
		}

		ClientConfig clientConfig = hsmConfig.getClientConfig();
		if (clientConfig == null || !(clientConfig instanceof ArubaConfig)) {
			logger.error("Non e' stata specificata la configurazione per Aruba" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione Aruba non specificata");
		}

		ArubaConfig arubaConfig = (ArubaConfig) hsmConfig.getClientConfig();

		ProxyConfig proxyConfig = arubaConfig.getProxyConfig();
		if (proxyConfig != null && proxyConfig.isUseProxy()) {
			initProxyConnection(proxyConfig.getProxyHost(), proxyConfig.getProxyPort(), proxyConfig.getProxyUser(), proxyConfig.getProxyPassword());
		} else {
			resetProxy();
		}

		WSConfig wsSignConfig = arubaConfig.getWsSignConfig();
		if (wsSignConfig == null) {
			logger.error("Non e' stata specificata la configurazione per il ws di firma" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione WS di firma non specificata");
		}

		String wsdlEndpoint = wsSignConfig.getWsdlEndpoint();
		String serviceNS = wsSignConfig.getServiceNS();
		String serviceName = wsSignConfig.getServiceName();
		logger.debug("wsdlEndpoint: " + wsdlEndpoint + " serviceNS: " + serviceNS + " serviceName: " + serviceName);
		if (wsdlEndpoint == null || serviceNS == null || serviceName == null) {
			logger.error("Configurazione ws incompleta" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione ws incompleta");
		}

		try {
			url = new URL(wsdlEndpoint);
			QName qname = new QName(serviceNS, serviceName);
			Service service = Service.create(url, qname);
			ArubaSignService arubaService = service.getPort(ArubaSignService.class);
			SOAPBinding binding = (SOAPBinding) ((BindingProvider) arubaService).getBinding();
			SignRequestV2 signRequest = new SignRequestV2();
			signRequest.setCertID("AS0");

			signRequest.setTransport(TypeTransport.BYNARYNET);
			signRequest.setBinaryinput(listaBytesFileDaFirmare.get(0));
			signRequest.setRequiredmark(false);

			Auth identity = new Auth();
			logger.debug("User: " + arubaConfig.getUser() + " Password: ****** " + " TypeOtpAuth: " + arubaConfig.getTypeOtpAuth() + " OtpPwd: "
					+ arubaConfig.getOtpPwd());
			identity.setUser(arubaConfig.getUser());

			if (arubaConfig.isUseDelegate()) {
				logger.debug("User delegated: " + arubaConfig.getDelegatedUser() + " Password delegated: ****** " + " DelegatedDomain: "
						+ arubaConfig.getDelegatedDomain());
				identity.setDelegatedUser(arubaConfig.getDelegatedUser());
				identity.setDelegatedPassword(arubaConfig.getDelegatedPassword());
				identity.setDelegatedDomain(arubaConfig.getDelegatedDomain());
			} else {
				identity.setUserPWD(arubaConfig.getPassword());
			}

			identity.setTypeOtpAuth(arubaConfig.getTypeOtpAuth());
			identity.setOtpPwd(arubaConfig.getOtpPwd());
			signRequest.setIdentity(identity);

			SignReturnV2 response = arubaService.addpkcs7Sign(signRequest, false);

			if (response != null) {
				logger.debug("Response Status: " + response.getStatus());
				logger.debug("Response Return Code: " + response.getReturnCode());
				if (response.getStatus() != null && response.getStatus().equalsIgnoreCase("KO")) {
					MessageBean messageBean = new MessageBean();
					messageBean.setDescription(response.getDescription());
					messageBean.setCode(response.getReturnCode());
					messageBean.setStatus(ResponseStatus.KO);
					logger.error("Errore nella risposta di firma cades - Descrizione errore: " + response.getDescription() + " " + getHsmUserDescription());
					signResponseBean.setMessage(messageBean);
					for(int i=0;i<listaBytesFileDaFirmare.size();i++){
						FileResponseBean fileResponseBean = new FileResponseBean();
						fileResponseBean.setMessage(messageBean);
						signResponseBean.getFileResponseBean().add(fileResponseBean);
					}
				} else {
					byte[] fileOutput = response.getBinaryoutput();
					logger.debug("Ricevuto file firmato");
					MessageBean messageBean = new MessageBean();
					messageBean.setStatus(ResponseStatus.OK);
					FileResponseBean fileResponseBean = new FileResponseBean();
					fileResponseBean.setMessage(messageBean);
					fileResponseBean.setFileFirmato(fileOutput);
					signResponseBean.getFileResponseBean().add(fileResponseBean);
				}
			} else {
				MessageBean messageBean = new MessageBean();
				messageBean.setDescription("Errore nella risposta di firma cades ");
				messageBean.setStatus(ResponseStatus.KO);
				logger.error("Errore nella risposta di firma cades" + " " + getHsmUserDescription());
				signResponseBean.setMessage(messageBean);
				for(int i=0;i<listaBytesFileDaFirmare.size();i++){
					FileResponseBean fileResponseBean = new FileResponseBean();
					fileResponseBean.setMessage(messageBean);
					signResponseBean.getFileResponseBean().add(fileResponseBean);
				}
			}
			setSignResponseMessage(signResponseBean);
			
		} catch (TypeOfTransportNotImplemented_Exception e) {
			logger.error(getHsmUserDescription(), e);
			throw new HsmClientSignatureException("Errore in fase di firma");
		} catch (MalformedURLException e) {
			logger.error(getHsmUserDescription(), e);
			throw new HsmClientSignatureException("Errore in fase di firma");
		}
		return signResponseBean;
	}

	@Override
	public CertResponseBean getUserCertificateList() throws HsmClientConfigException, UnsupportedOperationException {
		logger.debug("Metodo di richiesta certificati utente - INIZIO");
		CertResponseBean certResponseBean = new CertResponseBean();
		List<CertBean> certBeanList = new ArrayList<CertBean>();

		URL url;
		HsmConfig hsmConfig = getHsmConfig();
		if (hsmConfig == null) {
			logger.error("Non e' stata specificata la configurazione" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione non specificata");
		}

		ClientConfig clientConfig = hsmConfig.getClientConfig();
		if (clientConfig == null || !(clientConfig instanceof ArubaConfig)) {
			logger.error("Non e' stata specificata la configurazione per Aruba" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione Aruba non specificata");
		}

		ArubaConfig arubaConfig = (ArubaConfig) hsmConfig.getClientConfig();

		WSConfig wsCertConfig = arubaConfig.getWsCertConfig();
		if (wsCertConfig == null) {
			logger.error("Non e' stata specificata la configurazione per il ws di richiesta certificati" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione WS di richiesta certificati non specificata");
		}

		ProxyConfig proxyConfig = arubaConfig.getProxyConfig();
		if (proxyConfig != null && proxyConfig.isUseProxy()) {
			initProxyConnection(proxyConfig.getProxyHost(), proxyConfig.getProxyPort(), proxyConfig.getProxyUser(), proxyConfig.getProxyPassword());
		} else {
			resetProxy();
		}


		String wsdlEndpoint = wsCertConfig.getWsdlEndpoint();
		String serviceNS = wsCertConfig.getServiceNS();
		String serviceName = wsCertConfig.getServiceName();
		logger.debug("wsdlEndpoint: " + wsdlEndpoint + " serviceNS: " + serviceNS + " serviceName: " + serviceName);
		if (wsdlEndpoint == null || serviceNS == null || serviceName == null) {
			logger.error("Configurazione ws incompleta" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione ws incompleta");
		}

		try {
			url = new URL(wsdlEndpoint);
			QName qname = new QName(serviceNS, serviceName);
			Service service = Service.create(url, qname);
			ArubaSignService arubaService = service.getPort(ArubaSignService.class);
			SOAPBinding binding = (SOAPBinding) ((BindingProvider) arubaService).getBinding();

			Auth identity = new Auth();
			identity.setUser(arubaConfig.getUser());
			logger.debug("User: " + arubaConfig.getUser() + " Password: ****** " + " TypeOtpAuth: " + arubaConfig.getTypeOtpAuth() + " OtpPwd: "
					+ arubaConfig.getOtpPwd());

			if (arubaConfig.isUseDelegate()) {
				logger.debug("User delegated: " + arubaConfig.getDelegatedUser() + " Password delegated: ****** " + " DelegatedDomain: "
						+ arubaConfig.getDelegatedDomain());
				identity.setDelegatedUser(arubaConfig.getDelegatedUser());
				identity.setDelegatedPassword(arubaConfig.getDelegatedPassword());
				identity.setDelegatedDomain(arubaConfig.getDelegatedDomain());
			} else {
				identity.setUserPWD(arubaConfig.getPassword());
			}
			// identity.setTypeHSM("COSIGN");
			identity.setTypeOtpAuth(arubaConfig.getTypeOtpAuth());
			// identity.setOtpPwd(arubaConfig.getOtpPwd());

			UserCertList response = arubaService.listCert(identity);

			if (response != null) {
				logger.debug("Response Status: " + response.getStatus());
				logger.debug("Response Description: " + response.getDescription());
				if (response.getStatus() != null && response.getStatus().contains("KO")) {
					MessageBean messageBean = new MessageBean();
					messageBean.setDescription(response.getDescription());
					messageBean.setStatus(ResponseStatus.KO);
					logger.error("Errore nella risposta di certificati utente - Descrizione errore: " + response.getDescription() + " " + getHsmUserDescription());
					certResponseBean.setMessageBean(messageBean);
					return certResponseBean;
				}

				List<Cert> certList1 = response.getApp1();
				for (Cert cert1 : certList1) {
					CertBean certBean = new CertBean();
					if (cert1.getId() != null)
						certBean.setCertId(cert1.getId());
					if (cert1.getValue() != null)
						certBean.setCertValue(cert1.getValue());
					certBeanList.add(certBean);
				}
				List<Cert> certList2 = response.getApp2();
				for (Cert cert2 : certList2) {
					CertBean certBean = new CertBean();
					if (cert2.getId() != null)
						certBean.setCertId(cert2.getId());
					if (cert2.getValue() != null)
						certBean.setCertValue(cert2.getValue());
					certBeanList.add(certBean);

				}
				certResponseBean.setCertList(certBeanList);
				MessageBean messageBean = new MessageBean();
				messageBean.setStatus(ResponseStatus.OK);
				certResponseBean.setMessageBean(messageBean);
			} else {
				MessageBean messageBean = new MessageBean();
				messageBean.setDescription("Errore nella risposta di certificati utente");
				logger.error("Errore nella risposta di certificati utente" + " " + getHsmUserDescription());
				certResponseBean.setMessageBean(messageBean);
			}
		} catch (MalformedURLException e) {
			logger.error(getHsmUserDescription(), e);
		}
		return certResponseBean;
	}

	@Override
	public SignResponseBean firmaPades(List<byte[]>  listaBytesFileDaFirmare, SignOption signOption)
			throws HsmClientConfigException, HsmClientSignatureException, UnsupportedOperationException {
		logger.debug("Metodo di firma file pades - INIZIO");
		SignResponseBean signResponseBean = new SignResponseBean();
		URL url;
		HsmConfig hsmConfig = getHsmConfig();
		if (hsmConfig == null) {
			logger.error("Non e' stata specificata la configurazione" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione non specificata");
		}

		ClientConfig clientConfig = hsmConfig.getClientConfig();
		if (clientConfig == null || !(clientConfig instanceof ArubaConfig)) {
			logger.error("Non e' stata specificata la configurazione per Aruba" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione Aruba non specificata");
		}

		ArubaConfig arubaConfig = (ArubaConfig) hsmConfig.getClientConfig();

		WSConfig wsSignConfig = arubaConfig.getWsSignConfig();
		if (wsSignConfig == null) {
			logger.error("Non e' stata specificata la configurazione per il ws di firma" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione WS di firma non specificata");
		}

		ProxyConfig proxyConfig = arubaConfig.getProxyConfig();
		if (proxyConfig != null && proxyConfig.isUseProxy()) {
			initProxyConnection(proxyConfig.getProxyHost(), proxyConfig.getProxyPort(), proxyConfig.getProxyUser(), proxyConfig.getProxyPassword());
		} else {
			resetProxy();
		}

		String wsdlEndpoint = wsSignConfig.getWsdlEndpoint();
		String serviceNS = wsSignConfig.getServiceNS();
		String serviceName = wsSignConfig.getServiceName();
		logger.debug("wsdlEndpoint: " + wsdlEndpoint + " serviceNS: " + serviceNS + " serviceName: " + serviceName);
		if (wsdlEndpoint == null || serviceNS == null || serviceName == null) {
			logger.error("Configurazione ws incompleta" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione ws incompleta");
		}

		PadesConfig padesConfig = arubaConfig.getPadesConfig();
		if (padesConfig == null) {
			logger.error("Non e' stata specificata la configurazione per la firma pades" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione Pades non specificata");
		}

		try {
			url = new URL(wsdlEndpoint);
			QName qname = new QName(serviceNS, serviceName);
			Service service = Service.create(url, qname);
			ArubaSignService arubaService = service.getPort(ArubaSignService.class);
			SOAPBinding binding = (SOAPBinding) ((BindingProvider) arubaService).getBinding();

			SignRequestV2 signRequest = new SignRequestV2();
			signRequest.setCertID("AS0");

			signRequest.setTransport(TypeTransport.BYNARYNET);
			signRequest.setBinaryinput(listaBytesFileDaFirmare.get(0));
			signRequest.setRequiredmark(false);

			Auth identity = new Auth();
			identity.setUser(arubaConfig.getUser());
			logger.debug("User: " + arubaConfig.getUser() + " Password: ****** " + " TypeOtpAuth: " + arubaConfig.getTypeOtpAuth() + " OtpPwd: "
					+ arubaConfig.getOtpPwd());

			if (arubaConfig.isUseDelegate()) {
				logger.debug("User delegated: " + arubaConfig.getDelegatedUser() + " Password delegated: ****** " + " DelegatedDomain: "
						+ arubaConfig.getDelegatedDomain());
				identity.setDelegatedUser(arubaConfig.getDelegatedUser());
				identity.setDelegatedPassword(arubaConfig.getDelegatedPassword());
				identity.setDelegatedDomain(arubaConfig.getDelegatedDomain());
			} else {
				identity.setUserPWD(arubaConfig.getPassword());
			}

			// identity.setTypeHSM("COSIGN");
			identity.setTypeOtpAuth(arubaConfig.getTypeOtpAuth());
			identity.setOtpPwd(arubaConfig.getOtpPwd());
			signRequest.setIdentity(identity);

			PdfSignApparence apparence = new PdfSignApparence();
			apparence.setTesto(padesConfig.getTesto());
			apparence.setPage(Integer.parseInt(padesConfig.getNumPagina()));
			if (padesConfig.getReason() != null)
				apparence.setReason(padesConfig.getReason());
			if (padesConfig.getLocation() != null)
				apparence.setLocation(padesConfig.getLocation());
			if (padesConfig.getLeftX() != null)
				apparence.setLeftx(Integer.parseInt(padesConfig.getLeftX()));
			if (padesConfig.getLeftY() != null)
				apparence.setLefty(Integer.parseInt(padesConfig.getLeftY()));
			if (padesConfig.getRightX() != null)
				apparence.setRightx(Integer.parseInt(padesConfig.getRightX()));
			if (padesConfig.getRightY() != null)
				apparence.setRighty(Integer.parseInt(padesConfig.getRightY()));
			PdfProfile pdfprofile = PdfProfile.PADESBES;
			SignReturnV2 response = arubaService.pdfsignatureV2(signRequest, apparence, pdfprofile, null);
			if (response != null) {
				logger.debug("Response Status: " + response.getStatus());
				logger.debug("Response Return Code: " + response.getReturnCode());
				if (response.getStatus() != null && response.getStatus().equalsIgnoreCase("KO")) {
					MessageBean messageBean = new MessageBean();
					messageBean.setDescription(response.getDescription());
					messageBean.setCode(response.getReturnCode());
					messageBean.setStatus(ResponseStatus.KO);
					logger.error("Errore nella risposta di firma pades - Descrizione errore: " + response.getDescription() + " " + getHsmUserDescription());
					signResponseBean.setMessage(messageBean);
					for(int i=0;i<listaBytesFileDaFirmare.size();i++){
						FileResponseBean fileResponseBean = new FileResponseBean();
						fileResponseBean.setMessage(messageBean);
						signResponseBean.getFileResponseBean().add(fileResponseBean);
					}
				} else {
					byte[] fileOutput = response.getBinaryoutput();
					logger.debug("Ricevuto file firmato");
					MessageBean messageBean = new MessageBean();
					messageBean.setStatus(ResponseStatus.OK);
					FileResponseBean fileResponseBean = new FileResponseBean();
					fileResponseBean.setMessage(messageBean);
					fileResponseBean.setFileFirmato(fileOutput);
					signResponseBean.getFileResponseBean().add(fileResponseBean);
				}
			} else {
				MessageBean messageBean = new MessageBean();
				messageBean.setDescription("Errore nella risposta di firma pades ");
				logger.error("Errore nella risposta di firma pades" + " " + getHsmUserDescription());
				messageBean.setStatus(ResponseStatus.KO);
				signResponseBean.setMessage(messageBean);
				for(int i=0;i<listaBytesFileDaFirmare.size();i++){
					FileResponseBean fileResponseBean = new FileResponseBean();
					fileResponseBean.setMessage(messageBean);
					signResponseBean.getFileResponseBean().add(fileResponseBean);
				}
			}
			setSignResponseMessage(signResponseBean);
			
		} catch (TypeOfTransportNotImplemented_Exception e) {
			logger.error(getHsmUserDescription(), e);
			throw new HsmClientSignatureException("Errore in fase di firma");
		} catch (MalformedURLException e) {
			logger.error(getHsmUserDescription(), e);
			throw new HsmClientSignatureException("Errore in fase di firma");
		}
		return signResponseBean;
	}

	@Override
	public SignResponseBean firmaXades(List<byte[]>  listaBytesFileDaFirmare, SignOption signOption)
			throws HsmClientConfigException, HsmClientSignatureException, UnsupportedOperationException {
		logger.debug("Metodo di firma file xades - INIZIO");
		SignResponseBean signResponseBean = new SignResponseBean();
		URL url;
		HsmConfig hsmConfig = getHsmConfig();
		if (hsmConfig == null) {
			logger.error("Non e' stata specificata la configurazione" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione non specificata");
		}

		ClientConfig clientConfig = hsmConfig.getClientConfig();
		if (clientConfig == null || !(clientConfig instanceof ArubaConfig)) {
			logger.error("Non e' stata specificata la configurazione per Aruba" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione Aruba non specificata");
		}

		ArubaConfig arubaConfig = (ArubaConfig) hsmConfig.getClientConfig();

		WSConfig wsSignConfig = arubaConfig.getWsSignConfig();
		if (wsSignConfig == null) {
			logger.error("Non e' stata specificata la configurazione per il ws di firma" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione WS di firma non specificata");
		}

		String wsdlEndpoint = wsSignConfig.getWsdlEndpoint();
		String serviceNS = wsSignConfig.getServiceNS();
		String serviceName = wsSignConfig.getServiceName();
		logger.debug("wsdlEndpoint: " + wsdlEndpoint + " serviceNS: " + serviceNS + " serviceName: " + serviceName);
		if (wsdlEndpoint == null || serviceNS == null || serviceName == null) {
			logger.error("Configurazione ws incompleta" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione ws incompleta");
		}

		try {
			url = new URL(wsdlEndpoint);
			QName qname = new QName(serviceNS, serviceName);
			Service service = Service.create(url, qname);
			ArubaSignService arubaService = service.getPort(ArubaSignService.class);
			SOAPBinding binding = (SOAPBinding) ((BindingProvider) arubaService).getBinding();

			SignRequestV2 signRequest = new SignRequestV2();
			signRequest.setCertID("AS0");

			signRequest.setTransport(TypeTransport.BYNARYNET);
			signRequest.setBinaryinput(listaBytesFileDaFirmare.get(0));
			signRequest.setRequiredmark(false);

			Auth identity = new Auth();
			identity.setUser(arubaConfig.getUser());
			logger.debug("User: " + arubaConfig.getUser() + " Password: ****** " + " TypeOtpAuth: " + arubaConfig.getTypeOtpAuth() + " OtpPwd: "
					+ arubaConfig.getOtpPwd());

			if (arubaConfig.isUseDelegate()) {
				logger.debug("User delegated: " + arubaConfig.getDelegatedUser() + " Password delegated: ****** " + " DelegatedDomain: "
						+ arubaConfig.getDelegatedDomain());
				identity.setDelegatedUser(arubaConfig.getDelegatedUser());
				identity.setDelegatedPassword(arubaConfig.getDelegatedPassword());
				identity.setDelegatedDomain(arubaConfig.getDelegatedDomain());
			} else {
				identity.setUserPWD(arubaConfig.getPassword());
			}

			// identity.setTypeHSM("COSIGN");
			identity.setTypeOtpAuth(arubaConfig.getTypeOtpAuth());
			identity.setOtpPwd(arubaConfig.getOtpPwd());
			signRequest.setIdentity(identity);

			XmlSignatureParameter parameter = new XmlSignatureParameter();
			parameter.setCanonicalizedType(CanonicalizedType.ALGO_ID_C_14_N_11_WITH_COMMENTS);
			parameter.setType(XmlSignatureType.XMLENVELOPED);
			SignReturnV2 response = arubaService.xmlsignature(signRequest, parameter);

			if (response != null) {
				logger.debug("Response Status: " + response.getStatus());
				logger.debug("Response Return Code: " + response.getReturnCode());
				if (response.getStatus() != null && response.getStatus().equalsIgnoreCase("KO")) {
					MessageBean messageBean = new MessageBean();
					messageBean.setDescription(response.getDescription());
					messageBean.setCode(response.getReturnCode());
					messageBean.setStatus(ResponseStatus.KO);
					logger.error("Errore nella risposta di firma xades - Descrizione errore: " + response.getDescription() + " " + getHsmUserDescription());
					signResponseBean.setMessage(messageBean);
					for(int i=0;i<listaBytesFileDaFirmare.size();i++){
						FileResponseBean fileResponseBean = new FileResponseBean();
						fileResponseBean.setMessage(messageBean);
						signResponseBean.getFileResponseBean().add(fileResponseBean);
					}
				} else {
					logger.debug("Ricevuto file firmato");
					byte[] fileOutput = response.getBinaryoutput();
					MessageBean messageBean = new MessageBean();
					messageBean.setStatus(ResponseStatus.OK);
					FileResponseBean fileResponseBean = new FileResponseBean();
					fileResponseBean.setMessage(messageBean);
					fileResponseBean.setFileFirmato(fileOutput);
					signResponseBean.getFileResponseBean().add(fileResponseBean);
				}
			} else {
				MessageBean messageBean = new MessageBean();
				messageBean.setDescription("Errore nella risposta di firma xades ");
				messageBean.setStatus(ResponseStatus.KO);
				logger.error("Errore nella risposta di firma xades" + " " + getHsmUserDescription());
				signResponseBean.setMessage(messageBean);
				for(int i=0;i<listaBytesFileDaFirmare.size();i++){
					FileResponseBean fileResponseBean = new FileResponseBean();
					fileResponseBean.setMessage(messageBean);
					signResponseBean.getFileResponseBean().add(fileResponseBean);
				}
			}
			setSignResponseMessage(signResponseBean);
			
		} catch (TypeOfTransportNotImplemented_Exception e) {
			logger.error(getHsmUserDescription(), e);
			throw new HsmClientSignatureException("Errore in fase di firma");
		} catch (MalformedURLException e) {
			logger.error(getHsmUserDescription(), e);
			throw new HsmClientSignatureException("Errore in fase di firma");
		}
		return signResponseBean;
	}

	@Override
	public SignResponseBean firmaMultiplaHash(List<HashRequestBean> listaHashDaFirmare)
			throws HsmClientConfigException, HsmClientSignatureException, UnsupportedOperationException {
		logger.debug("Metodo di firma multipla hash - INIZIO");
		SignResponseBean signResponseBean = new SignResponseBean();
		URL url;
		HsmConfig hsmConfig = getHsmConfig();
		if (hsmConfig == null) {
			logger.error("Non e' stata specificata la configurazione" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione non specificata");
		}

		ClientConfig clientConfig = hsmConfig.getClientConfig();
		if (clientConfig == null || !(clientConfig instanceof ArubaConfig)) {
			logger.error("Non e' stata specificata la configurazione per Aruba" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione Aruba non specificata");
		}

		ArubaConfig arubaConfig = (ArubaConfig) hsmConfig.getClientConfig();

		WSConfig wsSignConfig = arubaConfig.getWsSignConfig();
		if (wsSignConfig == null) {
			logger.error("Non e' stata specificata la configurazione per il ws di firma" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione WS di firma non specificata");
		}

		ProxyConfig proxyConfig = arubaConfig.getProxyConfig();
		if (proxyConfig != null && proxyConfig.isUseProxy()) {
			initProxyConnection(proxyConfig.getProxyHost(), proxyConfig.getProxyPort(), proxyConfig.getProxyUser(), proxyConfig.getProxyPassword());
		} else {
			resetProxy();
		}

		String wsdlEndpoint = wsSignConfig.getWsdlEndpoint();
		String serviceNS = wsSignConfig.getServiceNS();
		String serviceName = wsSignConfig.getServiceName();
		logger.debug("wsdlEndpoint: " + wsdlEndpoint + " serviceNS: " + serviceNS + " serviceName: " + serviceName);
		if (wsdlEndpoint == null || serviceNS == null || serviceName == null) {
			logger.error("Configurazione ws incompleta" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione ws incompleta");
		}

		try {
			url = new URL(wsdlEndpoint);
			QName qname = new QName(serviceNS, serviceName);
			Service service = Service.create(url, qname);
			ArubaSignService arubaService = service.getPort(ArubaSignService.class);
			SOAPBinding binding = (SOAPBinding) ((BindingProvider) arubaService).getBinding();

			SignHashRequest signRequest = new SignHashRequest();
			signRequest.setCertID("AS0");
			signRequest.setHashtype("SHA256");

			signRequest.setRequirecert(true);

			Auth identity = new Auth();
			identity.setUser(arubaConfig.getUser());
			logger.debug("User: " + arubaConfig.getUser() + " Password: ****** " + " TypeOtpAuth: " + arubaConfig.getTypeOtpAuth() + " OtpPwd: "
					+ arubaConfig.getOtpPwd());

			if (arubaConfig.isUseDelegate()) {
				logger.debug("User delegated: " + arubaConfig.getDelegatedUser() + " Password delegated: ****** " + " DelegatedDomain: "
						+ arubaConfig.getDelegatedDomain());
				identity.setDelegatedUser(arubaConfig.getDelegatedUser());
				identity.setDelegatedPassword(arubaConfig.getDelegatedPassword());
				identity.setDelegatedDomain(arubaConfig.getDelegatedDomain());
			} else {
				identity.setUserPWD(arubaConfig.getPassword());
			}
			// identity.setTypeHSM("COSIGN");
			identity.setTypeOtpAuth(arubaConfig.getTypeOtpAuth());
			identity.setOtpPwd(arubaConfig.getOtpPwd());
			signRequest.setIdentity(identity);

			for (HashRequestBean hashDaFirmare : listaHashDaFirmare) {
				signRequest.setHash(Base64.decodeBase64(hashDaFirmare.getHash()));
				SignHashReturn response = arubaService.signhash(signRequest);

				HashResponseBean hashResponseBean = new HashResponseBean();
				if (response != null) {
					logger.debug("Response Status: " + response.getStatus());
					logger.debug("Response Return Code: " + response.getReturnCode());
					if (response.getStatus() != null && response.getStatus().equalsIgnoreCase("KO")) {
						MessageBean messageBean = new MessageBean();
						messageBean.setDescription(response.getDescription());
						messageBean.setCode(response.getReturnCode());
						messageBean.setStatus(ResponseStatus.KO);
						logger.error("Errore nella risposta di firma multipla hash - Descrizione errore: " + response.getDescription() + " " + getHsmUserDescription());
						hashResponseBean.setMessage(messageBean);
					} else {
						logger.debug("Ricevuto hash firmata");
						// byte[] signature = response.getBinaryoutput();
						byte[] signature = response.getSignature();
						MessageBean messageBean = new MessageBean();
						messageBean.setStatus(ResponseStatus.OK);
						signResponseBean.setMessage(messageBean);
						hashResponseBean.setHashFirmata(signature);
					}
				} else {
					MessageBean messageBean = new MessageBean();
					messageBean.setDescription("Errore nella risposta di firma pades ");
					messageBean.setStatus(ResponseStatus.KO);
					logger.error("Errore nella risposta di firma pades" + " " + getHsmUserDescription());
					hashResponseBean.setMessage(messageBean);
				}
				signResponseBean.getHashResponseBean().add(hashResponseBean);
			}
			setSignResponseMessage(signResponseBean);
			
		} catch (MalformedURLException e) {
			logger.error(getHsmUserDescription(), e);
			throw new HsmClientSignatureException("Errore in fase di firma");
		}
		return signResponseBean;
	}

	@Override
	public OtpResponseBean richiediOTP() throws HsmClientConfigException, UnsupportedOperationException {
		//throw new UnsupportedOperationException("Funzionalità di richiesta codice OTP non supportata");
		
		OtpResponseBean otpResponseBean = new OtpResponseBean();
		logger.debug("Metodo di richiesta OTP - INIZIO");
		
		URL url;
		HsmConfig hsmConfig = getHsmConfig();
		if (hsmConfig == null) {
			logger.error("Non e' stata specificata la configurazione" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione non specificata");
		}

		ClientConfig clientConfig = hsmConfig.getClientConfig();
		if (clientConfig == null || !(clientConfig instanceof ArubaConfig)) {
			logger.error("Non e' stata specificata la configurazione per Aruba" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione Aruba non specificata");
		}

		ArubaConfig arubaConfig = (ArubaConfig) hsmConfig.getClientConfig();

		WSConfig wsSignConfig = arubaConfig.getWsSignConfig();
		if (wsSignConfig == null) {
			logger.error("Non e' stata specificata la configurazione per il ws di firma" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione WS di firma non specificata");
		}
		
		ProxyConfig proxyConfig = arubaConfig.getProxyConfig();
		if (proxyConfig != null && proxyConfig.isUseProxy()) {
			initProxyConnection(proxyConfig.getProxyHost(), proxyConfig.getProxyPort(), proxyConfig.getProxyUser(), proxyConfig.getProxyPassword());
		} else {
			resetProxy();
		}

		String wsdlEndpoint = wsSignConfig.getWsdlEndpoint();
		String serviceNS = wsSignConfig.getServiceNS();
		String serviceName = wsSignConfig.getServiceName();
		logger.debug("wsdlEndpoint: " + wsdlEndpoint + " serviceNS: " + serviceNS + " serviceName: " + serviceName);
		if (wsdlEndpoint == null || serviceNS == null || serviceName == null) {
			logger.error("Configurazione ws incompleta" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione ws incompleta");
		}

		try {
			url = new URL(wsdlEndpoint);
			QName qname = new QName(serviceNS, serviceName);
			Service service = Service.create(url, qname);
			ArubaSignService arubaService = service.getPort(ArubaSignService.class);
			SOAPBinding binding = (SOAPBinding) ((BindingProvider) arubaService).getBinding();

			Auth identity = new Auth();
			identity.setUser(arubaConfig.getUser());
			logger.debug("User: " + arubaConfig.getUser() + " Password: ****** " + " TypeOtpAuth: " + arubaConfig.getTypeOtpAuth() + " OtpPwd: "
					+ arubaConfig.getOtpPwd());

			if (arubaConfig.isUseDelegate()) {
				logger.debug("User delegated: " + arubaConfig.getDelegatedUser() + " Password delegated: ****** " + " DelegatedDomain: "
						+ arubaConfig.getDelegatedDomain());
				identity.setDelegatedUser(arubaConfig.getDelegatedUser());
				identity.setDelegatedPassword(arubaConfig.getDelegatedPassword());
				identity.setDelegatedDomain(arubaConfig.getDelegatedDomain());
			} else {
				identity.setUserPWD(arubaConfig.getPassword());
			}

			// identity.setTypeHSM("COSIGN");
			identity.setTypeOtpAuth(arubaConfig.getTypeOtpAuth());
			//identity.setOtpPwd(arubaConfig.getOtpPwd());

			
			CredentialsType credType = CredentialsType.SMS;
			ArssReturn response = arubaService.sendCredential(identity, credType );

			if (response != null) {
				logger.debug("Response Status: " + response.getStatus());
				logger.debug("Response Return Code: " + response.getReturnCode());
				if (response.getStatus() != null && response.getStatus().equalsIgnoreCase("OK")) {
					logger.debug("Otp innviato");
					MessageBean messageBean = new MessageBean();
					messageBean.setStatus(ResponseStatus.OK);
					otpResponseBean.setMessage(messageBean);
				} else {
					MessageBean messageBean = new MessageBean();
					String messageString = response.getDescription();
					String messageCode = response.getReturnCode();
					messageBean.setDescription(messageString);
					messageBean.setCode("" + messageCode);
					messageBean.setStatus(ResponseStatus.KO);
					logger.error("Errore nella risposta per la richiesta otp - Codice errore: " + messageCode + " Descrizione errore: " + messageString + " " + getHsmUserDescription());
					otpResponseBean.setMessage(messageBean);
				}
			} else {
				MessageBean messageBean = new MessageBean();
				String messageString = "Errore";
				String messageCode = "Errore";
				messageBean.setDescription(messageString);
				messageBean.setCode("" + messageCode);
				messageBean.setStatus(ResponseStatus.KO);
				logger.error("Errore nella risposta per la richiesta otp - Codice errore: " + messageCode + " Descrizione errore: " + messageString + " " + getHsmUserDescription());
				otpResponseBean.setMessage(messageBean);
			}
		/*} catch (TypeOfTransportNotImplemented_Exception e) {
			logger.error(getHsmUserDescription(), e);
			throw new HsmClientSignatureException("Errore in fase di firma");
		*/} catch (MalformedURLException e) {
			logger.error(getHsmUserDescription(), e);
			throw new UnsupportedOperationException("Errore in fase di richiesta otp");
		}
		return otpResponseBean;

		
	}

	@Override
	public SessionResponseBean apriSessioneFirmaMultipla() throws HsmClientConfigException, UnsupportedOperationException {
		logger.debug("Metodo di apertura sessione di firma - INIZIO");
		SessionResponseBean sessionResponseBean = new SessionResponseBean();

		URL url;
		HsmConfig hsmConfig = getHsmConfig();
		if (hsmConfig == null) {
			logger.error("Non e' stata specificata la configurazione" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione non specificata");
		}

		ClientConfig clientConfig = hsmConfig.getClientConfig();
		if (clientConfig == null || !(clientConfig instanceof ArubaConfig)) {
			logger.error("Non e' stata specificata la configurazione per Aruba" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione Aruba non specificata");
		}

		ArubaConfig arubaConfig = (ArubaConfig) hsmConfig.getClientConfig();

		WSConfig wsSignConfig = arubaConfig.getWsSignConfig();
		if (wsSignConfig == null) {
			logger.error("Non e' stata specificata la configurazione per il ws di firma" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione WS di firma non specificata");
		}

		String wsdlEndpoint = wsSignConfig.getWsdlEndpoint();
		String serviceNS = wsSignConfig.getServiceNS();
		String serviceName = wsSignConfig.getServiceName();
		logger.debug("wsdlEndpoint: " + wsdlEndpoint + " serviceNS: " + serviceNS + " serviceName: " + serviceName);
		if (wsdlEndpoint == null || serviceNS == null || serviceName == null) {
			logger.error("Configurazione ws incompleta" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione ws incompleta");
		}

		try {
			url = new URL(wsdlEndpoint);

			QName qname = new QName(serviceNS, serviceName);
			Service service = Service.create(url, qname);
			ArubaSignService arubaService = service.getPort(ArubaSignService.class);
			SOAPBinding binding = (SOAPBinding) ((BindingProvider) arubaService).getBinding();

			Auth identity = new Auth();
			identity.setUser(arubaConfig.getUser());
			logger.debug("User: " + arubaConfig.getUser() + " Password: ****** " + " TypeOtpAuth: " + arubaConfig.getTypeOtpAuth() + " OtpPwd: "
					+ arubaConfig.getOtpPwd());

			if (arubaConfig.isUseDelegate()) {
				logger.debug("User delegated: " + arubaConfig.getDelegatedUser() + " Password delegated: ****** " + " DelegatedDomain: "
						+ arubaConfig.getDelegatedDomain());
				identity.setDelegatedUser(arubaConfig.getDelegatedUser());
				identity.setDelegatedPassword(arubaConfig.getDelegatedPassword());
				identity.setDelegatedDomain(arubaConfig.getDelegatedDomain());
			} else {
				identity.setUserPWD(arubaConfig.getPassword());
			}
			// identity.setTypeHSM("COSIGN");
			identity.setTypeOtpAuth(arubaConfig.getTypeOtpAuth());
			identity.setOtpPwd(arubaConfig.getOtpPwd());

			String openSessionResponse = arubaService.opensession(identity);
			String sessionId = null;
			if (openSessionResponse != null && (openSessionResponse.equalsIgnoreCase("KO-0001") || openSessionResponse.equalsIgnoreCase("KO-0003")
					|| openSessionResponse.equalsIgnoreCase("KO-0004") || openSessionResponse.equalsIgnoreCase("KO-0009")
					|| openSessionResponse.equalsIgnoreCase("KO-0010"))) {
				String messageString = openSessionResponse;
				logger.error("Errore in fase di apertura sessione firma - Descrizione errore: " + messageString + " " + getHsmUserDescription());
				MessageBean messageBean = new MessageBean();
				messageBean.setDescription(messageString);
				messageBean.setStatus(ResponseStatus.KO);
				sessionResponseBean.setMessage(messageBean);
			} else {
				sessionId = openSessionResponse;
				logger.debug("Sessione di firma iniziata - SessionId: " + sessionId);
				sessionResponseBean.setSessionId(sessionId);
				MessageBean messageBean = new MessageBean();
				messageBean.setStatus(ResponseStatus.OK);
				sessionResponseBean.setMessage(messageBean);
			}

		} catch (MalformedURLException e) {
			logger.error(getHsmUserDescription(), e);
			throw new UnsupportedOperationException("Errore in fase di apertura sessione di firma ");
		}

		return sessionResponseBean;
	}

	@Override
	public MessageBean chiudiSessioneFirmaMultipla(String sessionId) throws HsmClientConfigException, UnsupportedOperationException {
		logger.debug("Metodo di chiusura sessione di firma - INIZIO");
		MessageBean messageBean = new MessageBean();

		URL url;
		HsmConfig hsmConfig = getHsmConfig();
		if (hsmConfig == null) {
			logger.error("Non e' stata specificata la configurazione" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione non specificata");
		}

		ClientConfig clientConfig = hsmConfig.getClientConfig();
		if (clientConfig == null || !(clientConfig instanceof ArubaConfig)) {
			logger.error("Non e' stata specificata la configurazione per Aruba" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione Aruba non specificata");
		}

		ArubaConfig arubaConfig = (ArubaConfig) hsmConfig.getClientConfig();

		WSConfig wsSignConfig = arubaConfig.getWsSignConfig();
		if (wsSignConfig == null) {
			logger.error("Non e' stata specificata la configurazione per il ws di firma" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione WS di firma non specificata");
		}

		String wsdlEndpoint = wsSignConfig.getWsdlEndpoint();
		String serviceNS = wsSignConfig.getServiceNS();
		String serviceName = wsSignConfig.getServiceName();
		logger.debug("wsdlEndpoint: " + wsdlEndpoint + " serviceNS: " + serviceNS + " serviceName: " + serviceName);
		if (wsdlEndpoint == null || serviceNS == null || serviceName == null) {
			logger.error("Configurazione ws incompleta" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione ws incompleta");
		}

		try {
			url = new URL(wsdlEndpoint);
			QName qname = new QName(serviceNS, serviceName);
			Service service = Service.create(url, qname);
			ArubaSignService arubaService = service.getPort(ArubaSignService.class);
			SOAPBinding binding = (SOAPBinding) ((BindingProvider) arubaService).getBinding();

			Auth identity = new Auth();
			identity.setUser(arubaConfig.getUser());
			logger.debug("User: " + arubaConfig.getUser() + " Password: ****** " + " TypeOtpAuth: " + arubaConfig.getTypeOtpAuth() + " OtpPwd: "
					+ arubaConfig.getOtpPwd());

			if (arubaConfig.isUseDelegate()) {
				logger.debug("User delegated: " + arubaConfig.getDelegatedUser() + " Password delegated: ****** " + " DelegatedDomain: "
						+ arubaConfig.getDelegatedDomain());
				identity.setDelegatedUser(arubaConfig.getDelegatedUser());
				identity.setDelegatedPassword(arubaConfig.getDelegatedPassword());
				identity.setDelegatedDomain(arubaConfig.getDelegatedDomain());
			} else {
				identity.setUserPWD(arubaConfig.getPassword());
			}
			// identity.setTypeHSM("COSIGN");
			identity.setTypeOtpAuth(arubaConfig.getTypeOtpAuth());
			// identity.setOtpPwd(arubaConfig.getOtpPwd());

			arubaService.closesession(identity, sessionId);
		} catch (MalformedURLException e) {
			logger.error(getHsmUserDescription(), e);
			throw new UnsupportedOperationException("Errore in fase di chiusura sessione di firma ");
		}
		return messageBean;

	}

	@Override
	public SignResponseBean firmaMultiplaHashInSession(List<HashRequestBean> listaHashDaFirmare, String sessionId)
			throws HsmClientConfigException, HsmClientSignatureException, UnsupportedOperationException {
		logger.debug("Metodo di firma multipla hash con sessione - INIZIO");
		SignResponseBean signResponseBean = new SignResponseBean();

		URL url;
		HsmConfig hsmConfig = getHsmConfig();
		if (hsmConfig == null) {
			logger.error("Non e' stata specificata la configurazione" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione non specificata");
		}

		ClientConfig clientConfig = hsmConfig.getClientConfig();
		if (clientConfig == null || !(clientConfig instanceof ArubaConfig)) {
			logger.error("Non e' stata specificata la configurazione per Aruba" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione Aruba non specificata");
		}

		ArubaConfig arubaConfig = (ArubaConfig) hsmConfig.getClientConfig();

		WSConfig wsSignConfig = arubaConfig.getWsSignConfig();
		if (wsSignConfig == null) {
			logger.error("Non e' stata specificata la configurazione per il ws di firma" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione WS di firma non specificata");
		}

		ProxyConfig proxyConfig = arubaConfig.getProxyConfig();
		if (proxyConfig != null && proxyConfig.isUseProxy()) {
			initProxyConnection(proxyConfig.getProxyHost(), proxyConfig.getProxyPort(), proxyConfig.getProxyUser(), proxyConfig.getProxyPassword());
		} else {
			resetProxy();
		}

		String wsdlEndpoint = wsSignConfig.getWsdlEndpoint();
		String serviceNS = wsSignConfig.getServiceNS();
		String serviceName = wsSignConfig.getServiceName();
		logger.debug("wsdlEndpoint: " + wsdlEndpoint + " serviceNS: " + serviceNS + " serviceName: " + serviceName);
		if (wsdlEndpoint == null || serviceNS == null || serviceName == null) {
			logger.error("Configurazione ws incompleta" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione ws incompleta");
		}

		try {
			url = new URL(wsdlEndpoint);
			QName qname = new QName(serviceNS, serviceName);
			Service service = Service.create(url, qname);
			ArubaSignService arubaService = service.getPort(ArubaSignService.class);
			SOAPBinding binding = (SOAPBinding) ((BindingProvider) arubaService).getBinding();

			SignHashRequest signRequest = new SignHashRequest();
			signRequest.setCertID("AS0");
			signRequest.setHashtype("SHA256");
			signRequest.setSessionId(sessionId);

			signRequest.setRequirecert(true);

			Auth identity = new Auth();
			identity.setUser(arubaConfig.getUser());
			logger.debug("User: " + arubaConfig.getUser() + " Password: ****** " + " TypeOtpAuth: " + arubaConfig.getTypeOtpAuth() + " OtpPwd: "
					+ arubaConfig.getOtpPwd());

			if (arubaConfig.isUseDelegate()) {
				logger.debug("User delegated: " + arubaConfig.getDelegatedUser() + " Password delegated: ****** " + " DelegatedDomain: "
						+ arubaConfig.getDelegatedDomain());
				identity.setDelegatedUser(arubaConfig.getDelegatedUser());
				identity.setDelegatedPassword(arubaConfig.getDelegatedPassword());
				identity.setDelegatedDomain(arubaConfig.getDelegatedDomain());
			} else {
				identity.setUserPWD(arubaConfig.getPassword());
			}
			// identity.setTypeHSM("COSIGN");
			identity.setTypeOtpAuth(arubaConfig.getTypeOtpAuth());
			// identity.setOtpPwd(arubaConfig.getOtpPwd());
			signRequest.setIdentity(identity);

			for (HashRequestBean hashDaFirmare : listaHashDaFirmare) {
				signRequest.setHash(Base64.decodeBase64(hashDaFirmare.getHash()));
				SignHashReturn response = arubaService.signhash(signRequest);

				HashResponseBean hashResponseBean = new HashResponseBean();
				if (response != null) {
					logger.debug("Response Status: " + response.getStatus());
					logger.debug("Response Return Code: " + response.getReturnCode());
					if (response.getStatus() != null && response.getStatus().equalsIgnoreCase("KO")) {
						MessageBean messageBean = new MessageBean();
						messageBean.setDescription(response.getDescription());
						messageBean.setCode(response.getReturnCode());
						messageBean.setStatus(ResponseStatus.KO);
						logger.error("Errore nella risposta di firma multipla hash - Descrizione errore: " + response.getDescription() + " " + getHsmUserDescription());
						hashResponseBean.setMessage(messageBean);
					} else {
						logger.debug("Ricevuto hash firmata");
						// byte[] signature = response.getBinaryoutput();
						byte[] signature = response.getSignature();
						MessageBean messageBean = new MessageBean();
						messageBean.setStatus(ResponseStatus.OK);
						signResponseBean.setMessage(messageBean);
						hashResponseBean.setHashFirmata(signature);
					}
				} else {
					MessageBean messageBean = new MessageBean();
					messageBean.setDescription("Errore nella risposta di firma pades ");
					messageBean.setStatus(ResponseStatus.KO);
					logger.error("Errore nella risposta di firma pades" + " " + getHsmUserDescription());
					hashResponseBean.setMessage(messageBean);
				}
				signResponseBean.getHashResponseBean().add(hashResponseBean);
			}
			setSignResponseMessage(signResponseBean);
			
		} catch (MalformedURLException e) {
			logger.error(getHsmUserDescription(), e);
			throw new HsmClientSignatureException("Errore in fase di firma");
		}
		return signResponseBean;
	}
}
