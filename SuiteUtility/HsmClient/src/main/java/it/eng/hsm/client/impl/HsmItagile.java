package it.eng.hsm.client.impl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import it.eng.hsm.client.bean.MessageBean;
import it.eng.hsm.client.bean.ResponseStatus;
import it.eng.hsm.client.bean.cert.CertBean;
import it.eng.hsm.client.bean.cert.CertResponseBean;
import it.eng.hsm.client.bean.otp.OtpResponseBean;
import it.eng.hsm.client.bean.sign.CertificateBean;
import it.eng.hsm.client.bean.sign.CertificateVerifyBean;
import it.eng.hsm.client.bean.sign.CertificateVerifyResponseBean;
import it.eng.hsm.client.bean.sign.FileResponseBean;
import it.eng.hsm.client.bean.sign.HashRequestBean;
import it.eng.hsm.client.bean.sign.HashResponseBean;
import it.eng.hsm.client.bean.sign.InformazioniFirma;
import it.eng.hsm.client.bean.sign.MarcaBean;
import it.eng.hsm.client.bean.sign.SessionResponseBean;
import it.eng.hsm.client.bean.sign.SignResponseBean;
import it.eng.hsm.client.bean.sign.SignVerifyResponseBean;
import it.eng.hsm.client.bean.sign.SignatureBean;
import it.eng.hsm.client.config.ClientConfig;
import it.eng.hsm.client.config.HsmConfig;
import it.eng.hsm.client.config.ItagileConfig;
import it.eng.hsm.client.config.PadesConfig;
import it.eng.hsm.client.config.ProxyConfig;
import it.eng.hsm.client.config.WSConfig;
import it.eng.hsm.client.exception.HsmClientConfigException;
import it.eng.hsm.client.exception.HsmClientSignatureException;
import it.eng.hsm.client.exception.HsmClientSignatureVerifyException;
import it.eng.hsm.client.option.SignOption;
import it.itagile.firmaremota.ws.CertificateStatus;
import it.itagile.firmaremota.ws.RemoteSignatureCredentials;
import it.itagile.firmaremota.ws.RemoteSignatureXadesParams;
import it.itagile.firmaremota.ws.Signature;
import it.itagile.firmaremota.ws.SignatureDocumentInfo;
import it.itagile.firmaremota.ws.SignatureFlags;
import it.itagile.firmaremota.ws.impl.RemoteSignature;
import it.itagile.firmaremota.ws.impl.RemoteSignatureExceptionException;

public class HsmItagile extends HsmImpl {

	private Logger logger = Logger.getLogger(getClass());

	@Override
	public SignResponseBean firmaCades(List<byte[]>  listaBytesFileDaFirmare, SignOption signOption)
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
		if (clientConfig == null || !(clientConfig instanceof ItagileConfig)) {
			logger.error("Non e' stata specificata la configurazione per Itagile" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione Itagile non specificata");
		}

		ItagileConfig itagileConfig = (ItagileConfig) hsmConfig.getClientConfig();

		ProxyConfig proxyConfig = itagileConfig.getProxyConfig();
		if (proxyConfig != null && proxyConfig.isUseProxy()) {
			initProxyConnection(proxyConfig.getProxyHost(), proxyConfig.getProxyPort(), proxyConfig.getProxyUser(), proxyConfig.getProxyPassword());
		} else {
			resetProxy();
		}

		WSConfig wsSignConfig = itagileConfig.getWsSignConfig();
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
			
			RemoteSignature itagileService = service.getPort(RemoteSignature.class);
			SOAPBinding binding = (SOAPBinding) ((BindingProvider) itagileService).getBinding();
			
			RemoteSignatureCredentials userCred = new RemoteSignatureCredentials();
			logger.debug("User: " + itagileConfig.getUser() + " Password: ****** " + " TypeOtpAuth: " + itagileConfig.getTypeOtpAuth() + " OtpPwd: "
					+ itagileConfig.getOtpPwd());
			userCred.setUserid(itagileConfig.getUser());
			userCred.setPassword(itagileConfig.getPassword());
			if( itagileConfig.getOtpPwd()!=null )
				userCred.setExtAuth(itagileConfig.getOtpPwd());
			
			SignatureFlags sigFlags = new SignatureFlags();
			sigFlags.setCadesDetached(false);
			sigFlags.setTimestamp(false);
			
			byte[] fileOutput = itagileService.signCAdES(userCred, listaBytesFileDaFirmare.get(0), "SHA256", null, sigFlags);

			if (fileOutput != null) {
				logger.debug("Ricevuto file firmato");
				MessageBean messageBean = new MessageBean();
				messageBean.setStatus(ResponseStatus.OK);
				FileResponseBean fileResponseBean = new FileResponseBean();
				fileResponseBean.setMessage(messageBean);
				fileResponseBean.setFileFirmato(fileOutput);
				signResponseBean.getFileResponseBean().add(fileResponseBean);
			} else {
				MessageBean messageBean = new MessageBean();
				messageBean.setDescription("Errore nella risposta di firma cades ");
				messageBean.setStatus(ResponseStatus.KO);
				messageBean.setCode("");
				logger.error("Errore nella risposta di firma cades" + " " + getHsmUserDescription());
				signResponseBean.setMessage(messageBean);
				for(int i=0;i<listaBytesFileDaFirmare.size();i++){
					FileResponseBean fileResponseBean = new FileResponseBean();
					fileResponseBean.setMessage(messageBean);
					signResponseBean.getFileResponseBean().add(fileResponseBean);
				}
			}
			setSignResponseMessage(signResponseBean);
			
		} catch (MalformedURLException e) {
			logger.error(getHsmUserDescription(), e);
			throw new HsmClientSignatureException("Errore in fase di firma");
		} catch (Exception e){
			MessageBean messageBean = new MessageBean();
			messageBean.setDescription(e.getMessage());
			messageBean.setCode("");
			messageBean.setStatus(ResponseStatus.KO);
			logger.error("Errore nella risposta di firma cades - Descrizione errore: " + e.getMessage() + " " + getHsmUserDescription());
			signResponseBean.setMessage(messageBean);
			for(int i=0;i<listaBytesFileDaFirmare.size();i++){
				FileResponseBean fileResponseBean = new FileResponseBean();
				fileResponseBean.setMessage(messageBean);
				signResponseBean.getFileResponseBean().add(fileResponseBean);
			}
		}
		return signResponseBean;
	}

	@Override
	public SignResponseBean firmaCadesParallela(List<byte[]> listaBytesFileDaFirmare, SignOption signOption)
			throws HsmClientConfigException, HsmClientSignatureException, UnsupportedOperationException {
		throw new UnsupportedOperationException("Funzionalità di firma Cades parallela non supportata");
	
		/*logger.debug("Metodo di firma file cades parallela - INIZIO");
		SignResponseBean signResponseBean = new SignResponseBean();
		URL url;
		HsmConfig hsmConfig = getHsmConfig();
		if (hsmConfig == null) {
			logger.error("Non e' stata specificata la configurazione" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione non specificata");
		}

		ClientConfig clientConfig = hsmConfig.getClientConfig();
		if (clientConfig == null || !(clientConfig instanceof ItagileConfig)) {
			logger.error("Non e' stata specificata la configurazione per Itagile" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione Aruba non specificata");
		}

		ItagileConfig itagileConfig = (ItagileConfig) hsmConfig.getClientConfig();

		ProxyConfig proxyConfig = itagileConfig.getProxyConfig();
		if (proxyConfig != null && proxyConfig.isUseProxy()) {
			initProxyConnection(proxyConfig.getProxyHost(), proxyConfig.getProxyPort(), proxyConfig.getProxyUser(), proxyConfig.getProxyPassword());
		} else {
			resetProxy();
		}

		WSConfig wsSignConfig = itagileConfig.getWsSignConfig();
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
			signRequest.setBinaryinput(bytesFileDaFirmare);
			signRequest.setRequiredmark(false);

			RemoteSignatureCredentials userCred = new RemoteSignatureCredentials();
			logger.debug("User: " + itagileConfig.getUser() + " Password: ****** " + " TypeOtpAuth: " + itagileConfig.getTypeOtpAuth() + " OtpPwd: "
					+ itagileConfig.getOtpPwd());
			userCred.setUserid(itagileConfig.getUser());
			userCred.setPassword(itagileConfig.getPassword());
			if( itagileConfig.getOtpPwd()!=null )
				userCred.setExtAuth(itagileConfig.getOtpPwd());

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
					signResponseBean.getFileResponseBean().setMessageBean(messageBean);
				} else {
					byte[] fileOutput = response.getBinaryoutput();
					logger.debug("Ricevuto file firmato");
					MessageBean messageBean = new MessageBean();
					messageBean.setStatus(ResponseStatus.OK);
					signResponseBean.getFileResponseBean().setMessageBean(messageBean);
					signResponseBean.getFileResponseBean().setFileFirmato(fileOutput);
				}
			} else {
				MessageBean messageBean = new MessageBean();
				messageBean.setDescription("Errore nella risposta di firma cades ");
				messageBean.setStatus(ResponseStatus.KO);
				logger.error("Errore nella risposta di firma cades" + " " + getHsmUserDescription());
				signResponseBean.getFileResponseBean().setMessageBean(messageBean);
			}
			setSignResponseMessage(signResponseBean);
			
		} catch (TypeOfTransportNotImplemented_Exception e) {
			logger.error(getHsmUserDescription(), e);
			throw new HsmClientSignatureException("Errore in fase di firma");
		} catch (MalformedURLException e) {
			logger.error(getHsmUserDescription(), e);
			throw new HsmClientSignatureException("Errore in fase di firma");
		}
		return signResponseBean;*/
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
		if (clientConfig == null || !(clientConfig instanceof ItagileConfig )) {
			logger.error("Non e' stata specificata la configurazione per Itagile" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione Aruba non specificata");
		}

		ItagileConfig itagileConfig = (ItagileConfig) hsmConfig.getClientConfig();

		WSConfig wsCertConfig = itagileConfig.getWsCertConfig();
		if (wsCertConfig == null) {
			logger.error("Non e' stata specificata la configurazione per il ws di richiesta certificati" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione WS di richiesta certificati non specificata");
		}

		ProxyConfig proxyConfig = itagileConfig.getProxyConfig();
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
			RemoteSignature itagileService = service.getPort(RemoteSignature.class);
			SOAPBinding binding = (SOAPBinding) ((BindingProvider) itagileService).getBinding();

			RemoteSignatureCredentials userCred = new RemoteSignatureCredentials();
			logger.debug("User: " + itagileConfig.getUser() + " Password: ****** " + " TypeOtpAuth: " + itagileConfig.getTypeOtpAuth() + " OtpPwd: "
					+ itagileConfig.getOtpPwd());
			userCred.setUserid(itagileConfig.getUser());
			userCred.setPassword(itagileConfig.getPassword());
			if( itagileConfig.getOtpPwd()!=null )
				userCred.setExtAuth(itagileConfig.getOtpPwd());

			List<byte[]> listCerts = null;
			try {
				listCerts = itagileService.getCertificates(userCred);
			} catch (RemoteSignatureExceptionException e) {
				logger.error(getHsmUserDescription(), e);
			}

			if (listCerts != null) {
				for(byte[] byteCert : listCerts){
					CertBean certBean = new CertBean();
					//if (cert1.getId() != null)
					//	certBean.setCertId(cert1.getId());
					if (byteCert != null)
						certBean.setCertValue(byteCert);
					certBeanList.add(certBean);
				}
				/*if (response.getStatus() != null && response.getStatus().contains("KO")) {
					MessageBean messageBean = new MessageBean();
					messageBean.setDescription(response.getDescription());
					messageBean.setStatus(ResponseStatus.KO);
					logger.error("Errore nella risposta di certificati utente - Descrizione errore: " + response.getDescription() + " " + getHsmUserDescription());
					certResponseBean.setMessageBean(messageBean);
					return certResponseBean;
				}*/
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
		if (clientConfig == null || !(clientConfig instanceof ItagileConfig)) {
			logger.error("Non e' stata specificata la configurazione per Itagile" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione Itagile non specificata");
		}

		ItagileConfig itagileConfig = (ItagileConfig) hsmConfig.getClientConfig();

		WSConfig wsSignConfig = itagileConfig.getWsSignConfig();
		if (wsSignConfig == null) {
			logger.error("Non e' stata specificata la configurazione per il ws di firma" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione WS di firma non specificata");
		}

		ProxyConfig proxyConfig = itagileConfig.getProxyConfig();
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

		PadesConfig padesConfig = itagileConfig.getPadesConfig();
		if (padesConfig == null) {
			logger.error("Non e' stata specificata la configurazione per la firma pades" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione Pades non specificata");
		}
		
		try {
			url = new URL(wsdlEndpoint);
			QName qname = new QName(serviceNS, serviceName);
			Service service = Service.create(url, qname);
			
			RemoteSignature itagileService = service.getPort(RemoteSignature.class);
			SOAPBinding binding = (SOAPBinding) ((BindingProvider) itagileService).getBinding();
			
			RemoteSignatureCredentials userCred = new RemoteSignatureCredentials();
			logger.debug("User: " + itagileConfig.getUser() + " Password: ****** " + " TypeOtpAuth: " + itagileConfig.getTypeOtpAuth() + " OtpPwd: "
					+ itagileConfig.getOtpPwd());
			userCred.setUserid(itagileConfig.getUser());
			userCred.setPassword(itagileConfig.getPassword());
			if( itagileConfig.getOtpPwd()!=null )
				userCred.setExtAuth(itagileConfig.getOtpPwd());
			
			SignatureFlags sigFlags = new SignatureFlags();
			sigFlags.setTimestamp(false);
			sigFlags.setGraphicalSignature(true);
			
		
			String text = padesConfig.getTesto();
			int page = Integer.parseInt(padesConfig.getNumPagina());
			String reason = null;
			if (padesConfig.getReason() != null)
				reason = padesConfig.getReason();
			String location = null;
			if (padesConfig.getLocation() != null)
				location = padesConfig.getLocation();
			int x = 0;
			if (padesConfig.getLeftX() != null)
				x = Integer.parseInt(padesConfig.getLeftX());
			int y = 0;
			if (padesConfig.getLeftY() != null)
				y = Integer.parseInt(padesConfig.getLeftY());
			int width = 0;
			if (padesConfig.getRightX() != null)
				width = (Integer.parseInt(padesConfig.getRightX()) - Integer.parseInt(padesConfig.getLeftX()));
			int height = 0;
			if (padesConfig.getRightY() != null)
				height = (Integer.parseInt(padesConfig.getRightY()) - Integer.parseInt(padesConfig.getLeftY()));
			String userName = null;
			String fieldName = null;
			int fontSize = 12;
			String dateFormat = "dd/MM/YYYY";
			
			byte[] fileOutput = itagileService.signPDF(userCred, listaBytesFileDaFirmare.get(0), false, "SHA256", null, sigFlags, 
					 fieldName, page, x, y, width, height, userName, reason, location, dateFormat, text, fontSize);
			 
			if (fileOutput != null) {
				logger.debug("Ricevuto file firmato");
				MessageBean messageBean = new MessageBean();
				messageBean.setStatus(ResponseStatus.OK);
				FileResponseBean fileResponseBean = new FileResponseBean();
				fileResponseBean.setMessage(messageBean);
				fileResponseBean.setFileFirmato(fileOutput);
				signResponseBean.getFileResponseBean().add(fileResponseBean);
			} else {
				MessageBean messageBean = new MessageBean();
				messageBean.setDescription("Errore nella risposta di firma pades ");
				logger.error("Errore nella risposta di firma pades" + " " + getHsmUserDescription());
				messageBean.setStatus(ResponseStatus.KO);
				for(int i=0;i<listaBytesFileDaFirmare.size();i++){
					FileResponseBean fileResponseBean = new FileResponseBean();
					fileResponseBean.setMessage(messageBean);
					signResponseBean.getFileResponseBean().add(fileResponseBean);
				}
			}
			setSignResponseMessage(signResponseBean);
			
		} catch (MalformedURLException e) {
			logger.error(getHsmUserDescription(), e);
			throw new HsmClientSignatureException("Errore in fase di firma");
		} catch (Exception e){
			MessageBean messageBean = new MessageBean();
			messageBean.setDescription(e.getMessage());
			messageBean.setCode("");
			messageBean.setStatus(ResponseStatus.KO);
			logger.error("Errore nella risposta di firma pades - Descrizione errore: " + e.getMessage() + " " + getHsmUserDescription());
			for(int i=0;i<listaBytesFileDaFirmare.size();i++){
				FileResponseBean fileResponseBean = new FileResponseBean();
				fileResponseBean.setMessage(messageBean);
				signResponseBean.getFileResponseBean().add(fileResponseBean);
			}
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
		if (clientConfig == null || !(clientConfig instanceof ItagileConfig)) {
			logger.error("Non e' stata specificata la configurazione per Itagile" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione Itagile non specificata");
		}

		ItagileConfig itagileConfig = (ItagileConfig) hsmConfig.getClientConfig();

		WSConfig wsSignConfig = itagileConfig.getWsSignConfig();
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

			RemoteSignature itagileService = service.getPort(RemoteSignature.class);
			SOAPBinding binding = (SOAPBinding) ((BindingProvider) itagileService).getBinding();
			
			RemoteSignatureCredentials userCred = new RemoteSignatureCredentials();
			logger.debug("User: " + itagileConfig.getUser() + " Password: ****** " + " TypeOtpAuth: " + itagileConfig.getTypeOtpAuth() + " OtpPwd: "
					+ itagileConfig.getOtpPwd());
			userCred.setUserid(itagileConfig.getUser());
			userCred.setPassword(itagileConfig.getPassword());
			if( itagileConfig.getOtpPwd()!=null )
				userCred.setExtAuth(itagileConfig.getOtpPwd());
			
			RemoteSignatureXadesParams params = new RemoteSignatureXadesParams();

			byte[] fileOutput = itagileService.signXAdES(userCred, listaBytesFileDaFirmare.get(0), "SHA256", null, params);

			if (fileOutput != null) {
				logger.debug("Ricevuto file firmato");
				MessageBean messageBean = new MessageBean();
				messageBean.setStatus(ResponseStatus.OK);
				FileResponseBean fileResponseBean = new FileResponseBean();
				fileResponseBean.setMessage(messageBean);
				fileResponseBean.setFileFirmato(fileOutput);
				signResponseBean.getFileResponseBean().add(fileResponseBean);
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
			
		} catch (MalformedURLException e) {
			logger.error(getHsmUserDescription(), e);
			throw new HsmClientSignatureException("Errore in fase di firma");
		} catch (RemoteSignatureExceptionException e) {
			logger.error(getHsmUserDescription(), e);
			MessageBean messageBean = new MessageBean();
			messageBean.setDescription("Errore nella risposta di firma xades ");
			messageBean.setCode("");
			messageBean.setStatus(ResponseStatus.KO);
			logger.error("Errore nella risposta di firma xades" + " " + getHsmUserDescription());
			signResponseBean.setMessage(messageBean);
			for(int i=0;i<listaBytesFileDaFirmare.size();i++){
				FileResponseBean fileResponseBean = new FileResponseBean();
				fileResponseBean.setMessage(messageBean);
				signResponseBean.getFileResponseBean().add(fileResponseBean);
			}
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
		if (clientConfig == null || !(clientConfig instanceof ItagileConfig)) {
			logger.error("Non e' stata specificata la configurazione per Itagile" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione Aruba non specificata");
		}

		ItagileConfig itagileConfig = (ItagileConfig) hsmConfig.getClientConfig();

		WSConfig wsSignConfig = itagileConfig.getWsSignConfig();
		if (wsSignConfig == null) {
			logger.error("Non e' stata specificata la configurazione per il ws di firma" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione WS di firma non specificata");
		}

		ProxyConfig proxyConfig = itagileConfig.getProxyConfig();
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
			
			RemoteSignature itagileService = service.getPort(RemoteSignature.class);
			SOAPBinding binding = (SOAPBinding) ((BindingProvider) itagileService).getBinding();

			RemoteSignatureCredentials userCred = new RemoteSignatureCredentials();
			logger.debug("User: " + itagileConfig.getUser() + " Password: ****** " + " TypeOtpAuth: " + itagileConfig.getTypeOtpAuth() + " OtpPwd: "
					+ itagileConfig.getOtpPwd());
			userCred.setUserid(itagileConfig.getUser());
			userCred.setPassword(itagileConfig.getPassword());
			if( itagileConfig.getOtpPwd()!=null )
				userCred.setExtAuth(itagileConfig.getOtpPwd());

			for (HashRequestBean hashDaFirmare : listaHashDaFirmare) {
				HashResponseBean hashResponseBean = new HashResponseBean();
				byte[] signature = null;
				try {
					signature = itagileService.signPKCS1(userCred, Base64.decodeBase64(hashDaFirmare.getHash()), "SHA256", null);
				} catch (RemoteSignatureExceptionException e) {
					logger.error(getHsmUserDescription(),e);
					MessageBean messageBean = new MessageBean();
					messageBean.setDescription(e.getMessage());
					messageBean.setCode("");
					messageBean.setStatus(ResponseStatus.KO);
					logger.error("Errore nella risposta di firma multipla hash - Descrizione errore: " + e.getMessage() + " " + getHsmUserDescription());
					hashResponseBean.setMessage(messageBean);
				}

				if (signature != null) {
					logger.debug("Ricevuto hash firmata");
						// byte[] signature = response.getBinaryoutput();
						MessageBean messageBean = new MessageBean();
						messageBean.setStatus(ResponseStatus.OK);
						signResponseBean.setMessage(messageBean);
						hashResponseBean.setHashFirmata(signature);
					} else {
						MessageBean messageBean = new MessageBean();
						messageBean.setDescription("Errore nella risposta di firma ");
						messageBean.setStatus(ResponseStatus.KO);
						logger.error("Errore nella risposta di firma" + " " + getHsmUserDescription());
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
		throw new UnsupportedOperationException("Funzionalita'à di richiesta codice OTP non supportata");
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
		if (clientConfig == null || !(clientConfig instanceof ItagileConfig)) {
			logger.error("Non e' stata specificata la configurazione per Aruba" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione Aruba non specificata");
		}

		ItagileConfig itagileConfig = (ItagileConfig) hsmConfig.getClientConfig();

		WSConfig wsSignConfig = itagileConfig.getWsSignConfig();
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

			RemoteSignature itagileService = service.getPort(RemoteSignature.class);
			SOAPBinding binding = (SOAPBinding) ((BindingProvider) itagileService).getBinding();

			RemoteSignatureCredentials userCred = new RemoteSignatureCredentials();
			logger.debug("User: " + itagileConfig.getUser() + " Password: ****** " + " TypeOtpAuth: " + itagileConfig.getTypeOtpAuth() + " OtpPwd: "
					+ itagileConfig.getOtpPwd());
			userCred.setUserid(itagileConfig.getUser());
			userCred.setPassword(itagileConfig.getPassword());
			if( itagileConfig.getOtpPwd()!=null )
				userCred.setExtAuth(itagileConfig.getOtpPwd());

			String openSessionResponse = itagileService.openSignatureSession(userCred);
			String sessionId = null;
			if (openSessionResponse != null ) {
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
		} catch (RemoteSignatureExceptionException e) {
			String messageString = e.getMessage();
			logger.error("Errore in fase di apertura sessione firma - Descrizione errore: " + messageString + " " + getHsmUserDescription());
			MessageBean messageBean = new MessageBean();
			messageBean.setDescription(messageString);
			messageBean.setStatus(ResponseStatus.KO);
			sessionResponseBean.setMessage(messageBean);
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
		if (clientConfig == null || !(clientConfig instanceof ItagileConfig)) {
			logger.error("Non e' stata specificata la configurazione per Itagile" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione Itagile non specificata");
		}

		ItagileConfig itagileConfig = (ItagileConfig) hsmConfig.getClientConfig();

		WSConfig wsSignConfig = itagileConfig.getWsSignConfig();
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
			RemoteSignature itagileService = service.getPort(RemoteSignature.class);
			SOAPBinding binding = (SOAPBinding) ((BindingProvider) itagileService).getBinding();

			RemoteSignatureCredentials userCred = new RemoteSignatureCredentials();
			logger.debug("User: " + itagileConfig.getUser() + " Password: ****** " + " TypeOtpAuth: " + itagileConfig.getTypeOtpAuth() + " OtpPwd: "
					+ itagileConfig.getOtpPwd());
			userCred.setUserid(itagileConfig.getUser());
			userCred.setPassword(itagileConfig.getPassword());
			if( itagileConfig.getOtpPwd()!=null )
				userCred.setExtAuth(itagileConfig.getOtpPwd());

			itagileService.closeSignatureSession(userCred, sessionId);
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
		if (clientConfig == null || !(clientConfig instanceof ItagileConfig)) {
			logger.error("Non e' stata specificata la configurazione per Itagile" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione Itagile non specificata");
		}

		ItagileConfig itagileConfig = (ItagileConfig) hsmConfig.getClientConfig();

		WSConfig wsSignConfig = itagileConfig.getWsSignConfig();
		if (wsSignConfig == null) {
			logger.error("Non e' stata specificata la configurazione per il ws di firma" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione WS di firma non specificata");
		}

		ProxyConfig proxyConfig = itagileConfig.getProxyConfig();
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

			RemoteSignature itagileService = service.getPort(RemoteSignature.class);
			SOAPBinding binding = (SOAPBinding) ((BindingProvider) itagileService).getBinding();

			RemoteSignatureCredentials userCred = new RemoteSignatureCredentials();
			logger.debug("User: " + itagileConfig.getUser() + " Password: ****** " + " TypeOtpAuth: " + itagileConfig.getTypeOtpAuth() + " OtpPwd: "
					+ itagileConfig.getOtpPwd());
			userCred.setUserid(itagileConfig.getUser());
			userCred.setPassword(itagileConfig.getPassword());
			if( sessionId!=null )
				userCred.setExtAuth(sessionId);
			
			for (HashRequestBean hashDaFirmare : listaHashDaFirmare) {
				byte[] signature = null;
				HashResponseBean hashResponseBean = new HashResponseBean();
				try {
					signature = itagileService.signPKCS1(userCred, Base64.decodeBase64(hashDaFirmare.getHash()), "SHA256", null);
				}	catch (RemoteSignatureExceptionException e) {
						logger.error(getHsmUserDescription(), e);
						MessageBean messageBean = new MessageBean();
						messageBean.setDescription(e.getMessage());
						messageBean.setCode("");
						messageBean.setStatus(ResponseStatus.KO);
						logger.error("Errore nella risposta di firma multipla hash - Descrizione errore: " + e.getMessage() + " " + getHsmUserDescription());
						hashResponseBean.setMessage(messageBean);
					}

				if (signature != null) {
					logger.debug("Ricevuto hash firmata");
					MessageBean messageBean = new MessageBean();
					messageBean.setStatus(ResponseStatus.OK);
					signResponseBean.setMessage(messageBean);
					hashResponseBean.setHashFirmata(signature);
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
	public SignVerifyResponseBean verificaFirmeCades(byte[] bytesFileDaVerificare)
			throws HsmClientConfigException, HsmClientSignatureVerifyException, UnsupportedOperationException {
		
		logger.debug("Metodo di verifica file CAdeS - INIZIO");
		SignVerifyResponseBean signVerifyResponseBean = new SignVerifyResponseBean();
		
		HsmConfig hsmConfig = getHsmConfig();
		if (hsmConfig == null) {
			logger.error("Non e' stata specificata la configurazione" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione non specificata");
		}
		
		ItagileConfig itagileConfig = (ItagileConfig) hsmConfig.getClientConfig();

		WSConfig wsSignConfig = itagileConfig.getWsSignConfig();
		if (wsSignConfig == null) {
			logger.error("Non e' stata specificata la configurazione per il ws di verifica" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione WS di verifica non specificata");
		}
		
		String wsdlEndpoint = wsSignConfig.getWsdlEndpoint();
		String serviceNS = wsSignConfig.getServiceNS();
		String serviceName = wsSignConfig.getServiceName();
		logger.debug("wsdlEndpoint: " + wsdlEndpoint + " serviceNS: " + serviceNS + " serviceName: " + serviceName);
		if (wsdlEndpoint == null || serviceNS == null || serviceName == null) {
			logger.error("Configurazione ws incompleta" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione ws incompleta");
		}
		
		URL url;
		try {
			url = new URL(wsdlEndpoint);
			QName qname = new QName(serviceNS, serviceName);
			Service service = Service.create(url, qname);
			
			RemoteSignature itagileService = service.getPort(RemoteSignature.class);
			SOAPBinding binding = (SOAPBinding) ((BindingProvider) itagileService).getBinding();
			
			SignatureDocumentInfo signatureDocumentInfo = null;
			try {
				signatureDocumentInfo = itagileService.documentP7MInfo(bytesFileDaVerificare,true,true,false,true);
			} catch (RemoteSignatureExceptionException e) {
				logger.error(getHsmUserDescription(), e);
				MessageBean messageBean = new MessageBean();
				messageBean.setDescription(e.getMessage());
				messageBean.setCode(e.getMessage());
				messageBean.setStatus(ResponseStatus.KO);
				logger.error("Errore nella risposta di verifica firma CAdeS - Descrizione errore: " + e.getMessage() + " " + getHsmUserDescription());
				signVerifyResponseBean.setMessage(messageBean);
			}
			
			if (signatureDocumentInfo != null) {
				logger.debug("Ricevuta verifica file");
				MessageBean messageBean = new MessageBean();
				messageBean.setStatus(ResponseStatus.OK);
				signVerifyResponseBean.setMessage(messageBean);
				
				signVerifyResponseBean.setContent(signatureDocumentInfo.getContent());
				
				List<SignatureBean> listaSignatureBean = new ArrayList<SignatureBean>();
					
				List<Signature> listSegnature = signatureDocumentInfo.getSignatures().getSignatures().getItem();
				for(Signature itemSign : listSegnature) {
					
					SignatureBean signatureBean = getSignatureBean(itemSign);
					signatureBean.setTipoFirma("CAdes");
					listaSignatureBean.add(signatureBean);
				}
				
				signVerifyResponseBean.setSignatureBean(listaSignatureBean);

			} else {
				MessageBean messageBean = new MessageBean();
				messageBean.setDescription("Errore nella risposta di verifica CAdeS");
				messageBean.setStatus(ResponseStatus.KO);
				logger.error("Errore nella risposta di verifica CAdeS" + " " + getHsmUserDescription());
				signVerifyResponseBean.setMessage(messageBean);
			}

		} catch (MalformedURLException e) {
			logger.error("Errore in fase di verifica CAdeS" + " " + getHsmUserDescription(), e);
			throw new HsmClientSignatureVerifyException("Errore in fase di verifica CAdeS");
		} 
		
		return signVerifyResponseBean;
	}
	
	@Override
	public SignVerifyResponseBean verificaFirmePades(byte[] bytesFileDaVerificare)
			throws HsmClientConfigException, HsmClientSignatureVerifyException, UnsupportedOperationException {
		
		logger.debug("Metodo di verifica file PAdeS - INIZIO");
		SignVerifyResponseBean signVerifyResponseBean = new SignVerifyResponseBean();
		
		HsmConfig hsmConfig = getHsmConfig();
		if (hsmConfig == null) {
			logger.error("Non e' stata specificata la configurazione" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione non specificata");
		}
		
		ItagileConfig itagileConfig = (ItagileConfig) hsmConfig.getClientConfig();

		WSConfig wsSignConfig = itagileConfig.getWsSignConfig();
		if (wsSignConfig == null) {
			logger.error("Non e' stata specificata la configurazione per il ws di verifica" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione WS di verifica non specificata");
		}
		
		String wsdlEndpoint = wsSignConfig.getWsdlEndpoint();
		String serviceNS = wsSignConfig.getServiceNS();
		String serviceName = wsSignConfig.getServiceName();
		logger.debug("wsdlEndpoint: " + wsdlEndpoint + " serviceNS: " + serviceNS + " serviceName: " + serviceName);
		if (wsdlEndpoint == null || serviceNS == null || serviceName == null) {
			logger.error("Configurazione ws incompleta" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione ws incompleta");
		}
		
		URL url;
		try {
			url = new URL(wsdlEndpoint);
			QName qname = new QName(serviceNS, serviceName);
			Service service = Service.create(url, qname);
			
			RemoteSignature itagileService = service.getPort(RemoteSignature.class);
			SOAPBinding binding = (SOAPBinding) ((BindingProvider) itagileService).getBinding();
			
			SignatureDocumentInfo signatureDocumentInfo = null;
			try {
				signatureDocumentInfo = itagileService.documentPdfInfo(bytesFileDaVerificare,true,true,true);
			} catch (RemoteSignatureExceptionException e) {
				logger.error("Errore nella risposta di verifica firma PAdeS" + " " + getHsmUserDescription(), e);
				MessageBean messageBean = new MessageBean();
				messageBean.setDescription(e.getMessage());
				messageBean.setCode(e.getMessage());
				messageBean.setStatus(ResponseStatus.KO);
				logger.error("Errore nella risposta di verifica firma PAdeS - Descrizione errore: " + e.getMessage() + " " + getHsmUserDescription());
				signVerifyResponseBean.setMessage(messageBean);
			}
			
			if (signatureDocumentInfo != null) {
				logger.debug("Ricevuta verifica file");
				MessageBean messageBean = new MessageBean();
				messageBean.setStatus(ResponseStatus.OK);
				signVerifyResponseBean.setMessage(messageBean);
				
				signVerifyResponseBean.setContent(signatureDocumentInfo.getContent());
				
				List<SignatureBean> listaSignatureBean = new ArrayList<SignatureBean>();
				
				List<Signature> listSegnature = signatureDocumentInfo.getSignatures().getSignatures().getItem();
				for(Signature itemSign : listSegnature) {
					
					SignatureBean signatureBean = getSignatureBean(itemSign);
					signatureBean.setTipoFirma("PAdes");
					listaSignatureBean.add(signatureBean);
				}
				
				signVerifyResponseBean.setSignatureBean(listaSignatureBean);

			} else {
				MessageBean messageBean = new MessageBean();
				messageBean.setDescription("Errore nella risposta di verifica PAdeS");
				messageBean.setStatus(ResponseStatus.KO);
				logger.error("Errore nella risposta di verifica PAdeS" + " " + getHsmUserDescription());
				signVerifyResponseBean.setMessage(messageBean);
			}

		} catch (MalformedURLException e) {
			logger.error("Errore in fase di verifica PAdeS" + " " + getHsmUserDescription(), e);
			throw new HsmClientSignatureVerifyException("Errore in fase di verifica PAdeS");
		} 
		
		return signVerifyResponseBean;
	}
	
	@Override
	public SignVerifyResponseBean verificaFirmeXades(byte[] bytesFileDaVerificare)
			throws HsmClientConfigException, HsmClientSignatureVerifyException, UnsupportedOperationException {
		
		logger.debug("Metodo di verifica file XAdeS - INIZIO");
		SignVerifyResponseBean signVerifyResponseBean = new SignVerifyResponseBean();
		
		HsmConfig hsmConfig = getHsmConfig();
		if (hsmConfig == null) {
			logger.error("Non e' stata specificata la configurazione" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione non specificata");
		}
		
		ItagileConfig itagileConfig = (ItagileConfig) hsmConfig.getClientConfig();

		WSConfig wsSignConfig = itagileConfig.getWsSignConfig();
		if (wsSignConfig == null) {
			logger.error("Non e' stata specificata la configurazione per il ws di verifica" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione WS di verifica non specificata");
		}
		
		String wsdlEndpoint = wsSignConfig.getWsdlEndpoint();
		String serviceNS = wsSignConfig.getServiceNS();
		String serviceName = wsSignConfig.getServiceName();
		logger.debug("wsdlEndpoint: " + wsdlEndpoint + " serviceNS: " + serviceNS + " serviceName: " + serviceName);
		if (wsdlEndpoint == null || serviceNS == null || serviceName == null) {
			logger.error("Configurazione ws incompleta" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione ws incompleta");
		}
		
		URL url;
		try {
			url = new URL(wsdlEndpoint);
			QName qname = new QName(serviceNS, serviceName);
			Service service = Service.create(url, qname);
			
			RemoteSignature itagileService = service.getPort(RemoteSignature.class);
			SOAPBinding binding = (SOAPBinding) ((BindingProvider) itagileService).getBinding();
			
			SignatureDocumentInfo signatureDocumentInfo = null;
			try {
				signatureDocumentInfo = itagileService.documentXadesInfo(bytesFileDaVerificare,true,true,true);
			} catch (RemoteSignatureExceptionException e) {
				logger.error("Errore nella risposta di verifica firma xades" + " " + getHsmUserDescription(), e);
				MessageBean messageBean = new MessageBean();
				messageBean.setDescription(e.getMessage());
				messageBean.setCode(e.getMessage());
				messageBean.setStatus(ResponseStatus.KO);
				logger.error("Errore nella risposta di verifica firma xades - Descrizione errore: " + e.getMessage() + " " + getHsmUserDescription());
				signVerifyResponseBean.setMessage(messageBean);
			}
			
			if (signatureDocumentInfo != null) {
				logger.debug("Ricevuta verifica file");
				MessageBean messageBean = new MessageBean();
				messageBean.setStatus(ResponseStatus.OK);
				signVerifyResponseBean.setMessage(messageBean);
				
				signVerifyResponseBean.setContent(signatureDocumentInfo.getContent());
				
				List<SignatureBean> listaSignatureBean = new ArrayList<SignatureBean>();
				
				List<Signature> listSegnature = signatureDocumentInfo.getSignatures().getSignatures().getItem();
				for(Signature itemSign : listSegnature) {
					
					SignatureBean signatureBean = getSignatureBean(itemSign);			
					signatureBean.setTipoFirma("XAdes");
					listaSignatureBean.add(signatureBean);
				}
				
				signVerifyResponseBean.setSignatureBean(listaSignatureBean);

			} else {
				MessageBean messageBean = new MessageBean();
				messageBean.setDescription("Errore nella risposta di verifica XAdeS");
				messageBean.setStatus(ResponseStatus.KO);
				logger.error("Errore nella risposta di verifica XAdeS" + " " + getHsmUserDescription());
				signVerifyResponseBean.setMessage(messageBean);
			}

		} catch (MalformedURLException e) {
			logger.error("Errore in fase di verifica XAdeS" + " " + getHsmUserDescription(), e);
			throw new HsmClientSignatureVerifyException("Errore in fase di verifica XAdeS");
		} 
		
		return signVerifyResponseBean;
	}

	private SignatureBean getSignatureBean(Signature itemSign) {
		
		SignatureBean signatureBean = new SignatureBean();
		
		System.out.println("itemSign.getGivenName " + itemSign.getGivenName() + " " + itemSign.getSurName() );
		
		System.out.println("itemSign.getP7mLevel " + itemSign.getP7MLevel());
		System.out.println("itemSign.getP7mPath " + itemSign.getP7MPath());
		System.out.println("itemSign.getsignType " + itemSign.getSignType());
		
		signatureBean.setAlgoritmoDigest(itemSign.getDigestAlg());
		signatureBean.setTimestamp(itemSign.getSignTime() != null ? itemSign.getSignTime().toString() : null);
		signatureBean.setTipo(itemSign.getSignType());
		
		InformazioniFirma informazioniFirma = new InformazioniFirma();
		informazioniFirma.setName(itemSign.getSignatureField() != null ? itemSign.getSignatureField().getName() : null);
		informazioniFirma.setSigned(itemSign.getSignatureField() != null && itemSign.getSignatureField().isSigned() != null
				&& itemSign.getSignatureField().isSigned() ? true : false);
		informazioniFirma.setPage(itemSign.getSignatureField() != null ? itemSign.getSignatureField().getPage() : null);
		informazioniFirma.setPageH(itemSign.getSignatureField() != null ? itemSign.getSignatureField().getPageH() : null);
		informazioniFirma.setPosition(itemSign.getSignatureField() != null && itemSign.getSignatureField().getPosition() != null &&
				itemSign.getSignatureField().getPosition().getItem() != null ? itemSign.getSignatureField().getPosition().getItem() : null);
		informazioniFirma.setVisible(itemSign.getSignatureField() != null && itemSign.getSignatureField().isVisible() != null
				&& itemSign.getSignatureField().isVisible() ? true : false);
		informazioniFirma.setSigner(itemSign.getSignatureField() != null ? itemSign.getSignatureField().getSigner() : null);
		informazioniFirma.setLocation(itemSign.getSignatureField() != null ? itemSign.getSignatureField().getLocation() : null);
		informazioniFirma.setReason(itemSign.getSignatureField() != null ? itemSign.getSignatureField().getReason() : null);
		signatureBean.setInformazioni(informazioniFirma);
		
		signatureBean.setIsValidaCompleta(itemSign.isValid() != null && itemSign.isValid() ? true : false);
		signatureBean.setIsValida(itemSign.isValidSign() != null && itemSign.isValidSign() ? true : false);
		signatureBean.setSignErrCode(itemSign.getSignErrCode());
		signatureBean.setP7mLevel(itemSign.getP7MLevel());
		
		CertificateBean certificateBean = new CertificateBean();
		certificateBean.setNomeFirmatario(itemSign.getGivenName());
		certificateBean.setCognomeFirmatario(itemSign.getSurName());
		certificateBean.setIdentificativoFirmatario(itemSign.getFiscalCode());
		certificateBean.setOrganizzazione(itemSign.getOrganization());
		certificateBean.setUnitaOrganizzativa(itemSign.getOrgUnit());
		certificateBean.setIdentificativo(itemSign.getCertID());
		certificateBean.setTipo(itemSign.getCertType());
		certificateBean.setSeriale(itemSign.getCertSerial());
		certificateBean.setTipoUtilizzo(itemSign.getCertKeyUsage());
		certificateBean.setTrustServiceProvider(itemSign.getTrustSp());
		certificateBean.setTsInizioValidita(itemSign.getCertDateFrom() != null ? itemSign.getCertDateFrom().toString() : null);
		certificateBean.setTsFineValidita(itemSign.getCertDateTo() != null ? itemSign.getCertDateTo().toString() : null);
		certificateBean.setIsValido(itemSign.isValidCert() != null && itemSign.isValidCert() ? true : false);
		certificateBean.setIsCAValida(itemSign.isValidTrust() != null && itemSign.isValidTrust() ? true : false);
		certificateBean.setCertErrCode(itemSign.getCertErrCode());
		certificateBean.setTrustErrCode(itemSign.getTrustErrCode());
		certificateBean.setX509(itemSign.getX509());
		signatureBean.setCertificato(certificateBean);
		
		MarcaBean marcaBean = new MarcaBean();
		marcaBean.setTsAuthority(itemSign.getTsAuthority());
		marcaBean.setTsLenght(itemSign.getTsLenght() != null ? itemSign.getTsLenght().toString() : null);
		marcaBean.setIsPresente(itemSign.isValidTimestamp() != null && itemSign.isValidTimestamp() ? true : false);
		marcaBean.setIsValida(itemSign.isValidTimestamp() != null && itemSign.isValidTimestamp() ? true : false);
		signatureBean.setMarca(marcaBean);
		
		return signatureBean;
	}
	
	@Override
	public CertificateVerifyResponseBean verificaCertificato(List<byte[]> listaCertificati)
			throws HsmClientConfigException, HsmClientSignatureVerifyException, UnsupportedOperationException {
		
		logger.debug("Metodo di verifica certificato - INIZIO");
		CertificateVerifyResponseBean certificateVerifyResponseBean = new CertificateVerifyResponseBean();
		
		HsmConfig hsmConfig = getHsmConfig();
		if (hsmConfig == null) {
			logger.error("Non e' stata specificata la configurazione" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione non specificata");
		}
		
		ItagileConfig itagileConfig = (ItagileConfig) hsmConfig.getClientConfig();

		WSConfig wsSignConfig = itagileConfig.getWsSignConfig();
		if (wsSignConfig == null) {
			logger.error("Non e' stata specificata la configurazione per il ws di verifica certificato" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione WS di verifica certificato non specificata");
		}
		
		String wsdlEndpoint = wsSignConfig.getWsdlEndpoint();
		String serviceNS = wsSignConfig.getServiceNS();
		String serviceName = wsSignConfig.getServiceName();
		logger.debug("wsdlEndpoint: " + wsdlEndpoint + " serviceNS: " + serviceNS + " serviceName: " + serviceName);
		if (wsdlEndpoint == null || serviceNS == null || serviceName == null) {
			logger.error("Configurazione ws incompleta" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione ws incompleta");
		}
		
		URL url;
		try {
			url = new URL(wsdlEndpoint);
			QName qname = new QName(serviceNS, serviceName);
			Service service = Service.create(url, qname);
			
			RemoteSignature itagileService = service.getPort(RemoteSignature.class);
			SOAPBinding binding = (SOAPBinding) ((BindingProvider) itagileService).getBinding();
			
			List<CertificateStatus> listaCertificatiItagile = null;
			try {
				listaCertificatiItagile = itagileService.verifyCertificate(listaCertificati);
			} catch (RemoteSignatureExceptionException e) {
				logger.error(getHsmUserDescription(), e);
				MessageBean messageBean = new MessageBean();
				messageBean.setDescription(e.getMessage());
				messageBean.setCode("");
				messageBean.setStatus(ResponseStatus.KO);
				logger.error("Errore nella risposta di verifica certificato - Descrizione errore: " + e.getMessage() + " " + getHsmUserDescription());
				certificateVerifyResponseBean.setMessage(messageBean);
			}
			
			List<CertificateVerifyBean> listaCertificatiOutput = new ArrayList<CertificateVerifyBean>();
			if (listaCertificatiItagile != null) {
				logger.debug("Ricevuta verifica file");
				
				for(CertificateStatus certificatoItem : listaCertificatiItagile) {
					
					CertificateVerifyBean certificateItagileBean = new CertificateVerifyBean();
					certificateItagileBean.setCertificate(certificatoItem.getCertificate());
					certificateItagileBean.setCheckTime(String.valueOf(certificatoItem.getCheckTime()));
					certificateItagileBean.setInvalidCertificateCode(certificatoItem.getInvalidCertificateCode());
					certificateItagileBean.setInvalidCertificateMessage(certificatoItem.getInvalidCertificateMessage());
					certificateItagileBean.setTrustedIdentity(certificatoItem.isTrustedIdentity());
					certificateItagileBean.setUntrustedIdentityCode(certificatoItem.getUntrustedIdentityCode());
					certificateItagileBean.setUntrustedIdentityMessage(certificatoItem.getUntrustedIdentityMessage());
					certificateItagileBean.setValidCertificate(certificatoItem.isValidCertificate());
					
					listaCertificatiOutput.add(certificateItagileBean);
				}
				
				certificateVerifyResponseBean.setListaCertificatiItagile(listaCertificatiOutput);
				
			} else {
				MessageBean messageBean = new MessageBean();
				messageBean.setDescription("Errore nella risposta di verifica certificato");
				messageBean.setStatus(ResponseStatus.KO);
				logger.error("Errore nella risposta di verifica certificato" + " " + getHsmUserDescription());
				certificateVerifyResponseBean.setMessage(messageBean);
			}

		} catch (MalformedURLException e) {
			logger.error(getHsmUserDescription(), e);
			throw new HsmClientSignatureVerifyException("Errore in fase di verifica certificato");
		} 
		
		return certificateVerifyResponseBean;
	}
	
	@Override
	public CertificateVerifyResponseBean verificaCertificatoPerData(byte[] bytesFileDaVerificare, Date date)
			throws HsmClientConfigException, HsmClientSignatureVerifyException, UnsupportedOperationException {
		
		logger.debug("Metodo di verifica certificato con data - INIZIO");
		CertificateVerifyResponseBean certificateVerifyResponseBean = new CertificateVerifyResponseBean();
		
		HsmConfig hsmConfig = getHsmConfig();
		if (hsmConfig == null) {
			logger.error("Non e' stata specificata la configurazione" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione non specificata");
		}
		
		ItagileConfig itagileConfig = (ItagileConfig) hsmConfig.getClientConfig();

		WSConfig wsSignConfig = itagileConfig.getWsSignConfig();
		if (wsSignConfig == null) {
			logger.error("Non e' stata specificata la configurazione per il ws di verifica certificato con data" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione WS di verifica certificato con data non specificata");
		}
		
		String wsdlEndpoint = wsSignConfig.getWsdlEndpoint();
		String serviceNS = wsSignConfig.getServiceNS();
		String serviceName = wsSignConfig.getServiceName();
		logger.debug("wsdlEndpoint: " + wsdlEndpoint + " serviceNS: " + serviceNS + " serviceName: " + serviceName);
		if (wsdlEndpoint == null || serviceNS == null || serviceName == null) {
			logger.error("Configurazione ws incompleta" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione ws incompleta");
		}
		
		URL url;
		try {
			url = new URL(wsdlEndpoint);
			QName qname = new QName(serviceNS, serviceName);
			Service service = Service.create(url, qname);
			
			RemoteSignature itagileService = service.getPort(RemoteSignature.class);
			SOAPBinding binding = (SOAPBinding) ((BindingProvider) itagileService).getBinding();
			
			CertificateStatus certificateStatusItagile = null;
			try {
				XMLGregorianCalendar xmlDate = null;
			    try {
				    GregorianCalendar gc = new GregorianCalendar();
				    gc.setTime(date);
				    xmlDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
			    } catch(Exception e){
			      	logger.error("Errore nella conversione della data in input" + " " + getHsmUserDescription(), e);
			    }
			    certificateStatusItagile = itagileService.verifyCertificateAtTime(bytesFileDaVerificare,xmlDate);
			} catch (RemoteSignatureExceptionException e) {
				logger.error(getHsmUserDescription(), e);
				MessageBean messageBean = new MessageBean();
				messageBean.setDescription(e.getMessage());
				messageBean.setCode("");
				messageBean.setStatus(ResponseStatus.KO);
				logger.error("Errore nella risposta di verifica certificato con data - Descrizione errore: " + e.getMessage() + " " + getHsmUserDescription());
				certificateVerifyResponseBean.setMessage(messageBean);
			}
			
			if (certificateStatusItagile != null) {
				logger.debug("Ricevuta verifica file");
				
				CertificateVerifyBean certificateItagileBean = new CertificateVerifyBean();
				certificateItagileBean.setCertificate(certificateStatusItagile.getCertificate());
				certificateItagileBean.setCheckTime(String.valueOf(certificateStatusItagile.getCheckTime()));
				certificateItagileBean.setInvalidCertificateCode(certificateStatusItagile.getInvalidCertificateCode());
				certificateItagileBean.setInvalidCertificateMessage(certificateStatusItagile.getInvalidCertificateMessage());
				certificateItagileBean.setTrustedIdentity(certificateStatusItagile.isTrustedIdentity());
				certificateItagileBean.setUntrustedIdentityCode(certificateStatusItagile.getUntrustedIdentityCode());
				certificateItagileBean.setUntrustedIdentityMessage(certificateStatusItagile.getUntrustedIdentityMessage());
				certificateItagileBean.setValidCertificate(certificateStatusItagile.isValidCertificate());
				
				certificateVerifyResponseBean.setCertificateItagile(certificateItagileBean);
				
			} else {
				MessageBean messageBean = new MessageBean();
				messageBean.setDescription("Errore nella risposta di verifica certificato con data");
				messageBean.setStatus(ResponseStatus.KO);
				logger.error("Errore nella risposta di verifica certificato con data" + " " + getHsmUserDescription());
				certificateVerifyResponseBean.setMessage(messageBean);
			}

		} catch (MalformedURLException e) {
			logger.error(getHsmUserDescription(), e);
			throw new HsmClientSignatureVerifyException("Errore in fase di verifica certificato con data");
		} 
		
		return certificateVerifyResponseBean;
	}
}