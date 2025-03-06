package it.eng.hsm.client.impl;

import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

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
import it.eng.hsm.client.config.ClientConfig;
import it.eng.hsm.client.config.HsmConfig;
import it.eng.hsm.client.config.MedasConfig;
import it.eng.hsm.client.config.PadesConfig;
import it.eng.hsm.client.config.SignMode;
import it.eng.hsm.client.config.WSConfig;
import it.eng.hsm.client.exception.HsmClientConfigException;
import it.eng.hsm.client.exception.HsmClientSignatureException;
import it.eng.hsm.client.medas.syncsign.generated.CloseSignSessionReq;
import it.eng.hsm.client.medas.syncsign.generated.CloseSignSessionResp;
import it.eng.hsm.client.medas.syncsign.generated.OpenSignSessionReq;
import it.eng.hsm.client.medas.syncsign.generated.OpenSignSessionResp;
import it.eng.hsm.client.medas.syncsign.generated.ScrybaSignServerSync;
import it.eng.hsm.client.medas.syncsign.generated.SignDocReq;
import it.eng.hsm.client.medas.syncsign.generated.SignDocResp;
import it.eng.hsm.client.medas.syncsign.generated.SignOneDocReq;
import it.eng.hsm.client.medas.syncsign.generated.SignOneDocResp;
import it.eng.hsm.client.medas.syncsign.generated.TypeArssPadesProperties;
import it.eng.hsm.client.medas.syncsign.generated.TypeArssPadesPropertiesApparence;
import it.eng.hsm.client.medas.syncsign.generated.TypeCertListReq;
import it.eng.hsm.client.medas.syncsign.generated.TypeCertListResp;
import it.eng.hsm.client.medas.syncsign.generated.TypeCloseSignSessionReq;
import it.eng.hsm.client.medas.syncsign.generated.TypeCloseSignSessionResp;
import it.eng.hsm.client.medas.syncsign.generated.TypeCredentials;
import it.eng.hsm.client.medas.syncsign.generated.TypeDespatchOtpReq;
import it.eng.hsm.client.medas.syncsign.generated.TypeDespatchOtpResp;
import it.eng.hsm.client.medas.syncsign.generated.TypeDocTypeList;
import it.eng.hsm.client.medas.syncsign.generated.TypeDocument;
import it.eng.hsm.client.medas.syncsign.generated.TypeDsPadesProperties;
import it.eng.hsm.client.medas.syncsign.generated.TypeDsPadesPropertiesApparence;
import it.eng.hsm.client.medas.syncsign.generated.TypeMessage;
import it.eng.hsm.client.medas.syncsign.generated.TypeOTPtypeList;
import it.eng.hsm.client.medas.syncsign.generated.TypeOpenSignSessionReq;
import it.eng.hsm.client.medas.syncsign.generated.TypeOpenSignSessionResp;
import it.eng.hsm.client.medas.syncsign.generated.TypePadesProperties;
import it.eng.hsm.client.medas.syncsign.generated.TypeRawCertificate;
import it.eng.hsm.client.medas.syncsign.generated.TypeSignDocReq;
import it.eng.hsm.client.medas.syncsign.generated.TypeSignDocResp;
import it.eng.hsm.client.medas.syncsign.generated.TypeSignOneDocReq;
import it.eng.hsm.client.medas.syncsign.generated.TypeSignOneDocResp;
import it.eng.hsm.client.medas.syncsign.generated.TypeSignProperties;
import it.eng.hsm.client.medas.syncsign.generated.TypeUser;
import it.eng.hsm.client.medas.utils.generated.CertListReq;
import it.eng.hsm.client.medas.utils.generated.CertListResp;
import it.eng.hsm.client.medas.utils.generated.DespatchOtpReq;
import it.eng.hsm.client.medas.utils.generated.DespatchOtpResp;
import it.eng.hsm.client.medas.utils.generated.GetUserInfo3Req;
import it.eng.hsm.client.medas.utils.generated.GetUserInfo3Resp;
import it.eng.hsm.client.medas.utils.generated.ScrybaSignServerUtils;
import it.eng.hsm.client.medas.utils.generated.TypeCert3;
import it.eng.hsm.client.medas.utils.generated.TypeCertificateList2;
import it.eng.hsm.client.medas.utils.generated.TypeGetUserInfo3Req;
import it.eng.hsm.client.medas.utils.generated.TypeGetUserInfo3Resp;
import it.eng.hsm.client.medas.utils.generated.TypeSignaturePower2;
import it.eng.hsm.client.medas.utils.generated.TypeSignaturePowerList2;
import it.eng.hsm.client.medas.utils.generated.TypeSigningUser3;
import it.eng.hsm.client.option.ClientOption;
import it.eng.hsm.client.option.MedasOption;
import it.eng.hsm.client.option.SignOption;

public class HsmMedas extends HsmImpl {

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
		if (clientConfig == null || !(clientConfig instanceof MedasConfig)) {
			logger.error("Non e' stata specificata la configurazione per Medas" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione Medas non specificata");
		}

		MedasConfig medasConfig = (MedasConfig) hsmConfig.getClientConfig();

		WSConfig wsSignConfig = medasConfig.getWsSignConfig();
		if (wsSignConfig == null) {
			logger.error("Non e' stata specificata la configurazione per il ws di firma" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione WS di firma non specificata");
		}

		String wsdlEndpoint = wsSignConfig.getWsdlEndpoint();
		String serviceNS = wsSignConfig.getServiceNS();
		String serviceName = wsSignConfig.getServiceName();
		logger.debug("wsdlEndpoint: " + wsdlEndpoint + " serviceNS: " + serviceNS + " serviceName: " + serviceName);
		if (wsdlEndpoint == null || serviceNS == null || serviceName == null) {
			logger.error("Configurazione ws incompleta");
			throw new HsmClientConfigException("Configurazione ws incompleta");
		}

		try {
			logger.debug("User: " + medasConfig.getUser() + " Codice fiscale: " + medasConfig.getCodiceFiscale() + " Password: ****** " + " Otp: "
					+ medasConfig.getOtp());
			logger.debug("ProcessId: " + medasConfig.getProcessId());

			url = new URL(wsdlEndpoint);
			QName qname = new QName(serviceNS, serviceName);
			Service service = Service.create(url, qname);
			ScrybaSignServerSync syncService = service.getPort(ScrybaSignServerSync.class);

			String docType = null;
			if (signOption != null) {
				ClientOption clientOption = signOption.getClientOption();
				if (clientOption != null && clientOption instanceof MedasOption) {
					docType = ((MedasOption) clientOption).getDocType();
				}
			}

			SignOneDocReq signOneDocRequest = new SignOneDocReq();
			TypeSignOneDocReq signOneDocReq = new TypeSignOneDocReq();

			TypeDocument document = new TypeDocument();
			// stringa codificata che rappresenta il doc
			String fileDaFirmare = Base64.encodeBase64String(listaBytesFileDaFirmare.get(0));
			document.setDocBin(fileDaFirmare);
			if (docType != null)
				document.setDocType(docType);
			signOneDocReq.setDocument(document);

			TypeCredentials credentials = new TypeCredentials();
			credentials.setPassword(medasConfig.getPassword());
			credentials.setOtp(medasConfig.getOtp());
			signOneDocReq.setCredentials(credentials);

			TypeUser user = new TypeUser();
			if (medasConfig.getUser() != null)
				user.setUsername(medasConfig.getUser());
			if (medasConfig.getCodiceFiscale() != null)
				user.setSsn(medasConfig.getCodiceFiscale());
			signOneDocReq.setUser(user);

			TypeSignProperties typeSignProperties = new TypeSignProperties();
			typeSignProperties.setSignMode(SignMode.CADES.getType());
			typeSignProperties.setParallel(false);
			typeSignProperties.setProcessId(medasConfig.getProcessId());
			signOneDocReq.setSignProperties(typeSignProperties);

			signOneDocRequest.setSignOneDocReq(signOneDocReq);
			SignOneDocResp signOneDocResponse = syncService.signOneDoc(signOneDocRequest);
			TypeSignOneDocResp signOneDocResp = signOneDocResponse.getSignOneDocResp();
			TypeMessage message = signOneDocResp.getMessage();
			BigInteger messageCode = message.getCode();
			if (messageCode != null && !messageCode.equals(new BigInteger("0"))) {
				String messageString = message.getMessage();
				MessageBean messageBean = new MessageBean();
				messageBean.setDescription(messageString);
				messageBean.setCode("" + messageCode);
				messageBean.setStatus(ResponseStatus.KO);
				logger.error("Errore nella risposta di firma cades - Codice errore: " + messageCode + " Descrizione errore: " + messageString + " " + getHsmUserDescription());
				signResponseBean.setMessage(messageBean);
				for(int i=0;i<listaBytesFileDaFirmare.size();i++){
					FileResponseBean fileResponseBean = new FileResponseBean();
					fileResponseBean.setMessage(messageBean);
					signResponseBean.getFileResponseBean().add(fileResponseBean);
				}
			} else {
				String responseDoc = signOneDocResp.getSignedDocBin();
				MessageBean messageBean = new MessageBean();
				messageBean.setStatus(ResponseStatus.OK);
				FileResponseBean fileResponseBean = new FileResponseBean();
				fileResponseBean.setMessage(messageBean);
				fileResponseBean.setFileFirmato(Base64.decodeBase64(responseDoc.getBytes()));
				signResponseBean.getFileResponseBean().add(fileResponseBean);
			}
			setSignResponseMessage(signResponseBean);
			
		} catch (MalformedURLException e) {
			logger.error(getHsmUserDescription(), e);
			throw new HsmClientSignatureException("Errore in fase di firma cades");
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
		if (clientConfig == null || !(clientConfig instanceof MedasConfig)) {
			logger.error("Non e' stata specificata la configurazione per Medas" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione Medas non specificata");
		}

		MedasConfig medasConfig = (MedasConfig) hsmConfig.getClientConfig();

		WSConfig wsSignConfig = medasConfig.getWsSignConfig();
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
			ScrybaSignServerSync syncService = service.getPort(ScrybaSignServerSync.class);

			logger.debug("User: " + medasConfig.getUser() + " Codice fiscale: " + medasConfig.getCodiceFiscale() + "  Password: ****** " + " Otp: "
					+ medasConfig.getOtp());
			logger.debug("ProcessId" + medasConfig.getProcessId());

			SignOneDocReq signOneDocRequest = new SignOneDocReq();
			TypeSignOneDocReq signOneDocReq = new TypeSignOneDocReq();

			String docType = null;
			if (signOption != null) {
				ClientOption clientOption = signOption.getClientOption();
				if (clientOption != null && clientOption instanceof MedasOption) {
					docType = ((MedasOption) clientOption).getDocType();
				}
			}

			TypeDocument document = new TypeDocument();
			// stringa codificata che rappresenta il doc
			String fileDaFirmare = Base64.encodeBase64String(listaBytesFileDaFirmare.get(0));
			document.setDocBin(fileDaFirmare);
			if (docType != null)
				document.setDocType(docType);
			signOneDocReq.setDocument(document);

			TypeCredentials credentials = new TypeCredentials();
			credentials.setPassword(medasConfig.getPassword());
			credentials.setOtp(medasConfig.getOtp());
			signOneDocReq.setCredentials(credentials);

			TypeUser user = new TypeUser();
			if (medasConfig.getUser() != null)
				user.setUsername(medasConfig.getUser());
			if (medasConfig.getCodiceFiscale() != null)
				user.setSsn(medasConfig.getCodiceFiscale());
			signOneDocReq.setUser(user);

			TypeSignProperties typeSignProperties = new TypeSignProperties();
			typeSignProperties.setSignMode(SignMode.CADES.getType());
			typeSignProperties.setParallel(true);
			typeSignProperties.setProcessId(medasConfig.getProcessId());
			signOneDocReq.setSignProperties(typeSignProperties);

			signOneDocRequest.setSignOneDocReq(signOneDocReq);
			SignOneDocResp signOneDocResponse = syncService.signOneDoc(signOneDocRequest);
			TypeSignOneDocResp signOneDocResp = signOneDocResponse.getSignOneDocResp();
			TypeMessage message = signOneDocResp.getMessage();
			BigInteger messageCode = message.getCode();
			if (messageCode != null && !messageCode.equals(new BigInteger("0"))) {
				String messageString = message.getMessage();
				MessageBean messageBean = new MessageBean();
				messageBean.setDescription(messageString);
				messageBean.setStatus(ResponseStatus.KO);
				logger.error("Errore nella risposta di firma cades - Codice errore: " + messageCode + " Descrizione errore: " + messageString + " " + getHsmUserDescription());
				signResponseBean.setMessage(messageBean);
				for(int i=0;i<listaBytesFileDaFirmare.size();i++){
					FileResponseBean fileResponseBean = new FileResponseBean();
					fileResponseBean.setMessage(messageBean);
					signResponseBean.getFileResponseBean().add(fileResponseBean);
				}
			} else {
				String responseDoc = signOneDocResp.getSignedDocBin();
				MessageBean messageBean = new MessageBean();
				messageBean.setStatus(ResponseStatus.OK);
				FileResponseBean fileResponseBean = new FileResponseBean();
				fileResponseBean.setMessage(messageBean);
				fileResponseBean.setFileFirmato(responseDoc.getBytes());
				signResponseBean.getFileResponseBean().add(fileResponseBean);
			}
			setSignResponseMessage(signResponseBean);
			
		} catch (MalformedURLException e) {
			logger.error(getHsmUserDescription(), e);
			throw new HsmClientSignatureException("Errore in fase di firma cades");
		}

		return signResponseBean;
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
		if (clientConfig == null || !(clientConfig instanceof MedasConfig)) {
			logger.error("Non e' stata specificata la configurazione per Medas" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione Medas non specificata");
		}

		MedasConfig medasConfig = (MedasConfig) hsmConfig.getClientConfig();

		WSConfig wsSignConfig = medasConfig.getWsSignConfig();
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

		PadesConfig padesConfig = medasConfig.getPadesConfig();
		if (padesConfig == null) {
			logger.error("Non e' stata specificata la configurazione per la firma pades" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione Pades non specificata");
		}

		try {
			url = new URL(wsdlEndpoint);
			QName qname = new QName(serviceNS, serviceName);
			Service service = Service.create(url, qname);
			ScrybaSignServerSync syncService = service.getPort(ScrybaSignServerSync.class);

			SignOneDocReq signOneDocRequest = new SignOneDocReq();
			TypeSignOneDocReq signOneDocReq = new TypeSignOneDocReq();

			String docType = null;
			if (signOption != null) {
				ClientOption clientOption = signOption.getClientOption();
				if (clientOption != null && clientOption instanceof MedasOption) {
					docType = ((MedasOption) clientOption).getDocType();
				}
			}

			logger.debug("User: " + medasConfig.getUser() + " Codice fiscale: " + medasConfig.getCodiceFiscale() + " Password: ****** " + " Otp: "
					+ medasConfig.getOtp());
			logger.debug("ProcessId" + medasConfig.getProcessId());

			TypeDocument document = new TypeDocument();
			String fileDaFirmare = Base64.encodeBase64String(listaBytesFileDaFirmare.get(0));
			document.setDocBin(fileDaFirmare);
			if (docType != null)
				document.setDocType(docType);
			signOneDocReq.setDocument(document);

			TypeCredentials credentials = new TypeCredentials();
			credentials.setPassword(medasConfig.getPassword());
			credentials.setOtp(medasConfig.getOtp());
			signOneDocReq.setCredentials(credentials);

			TypeUser user = new TypeUser();
			if (medasConfig.getUser() != null)
				user.setUsername(medasConfig.getUser());
			if (medasConfig.getCodiceFiscale() != null)
				user.setSsn(medasConfig.getCodiceFiscale());
			signOneDocReq.setUser(user);

			TypeSignProperties typeSignProperties = new TypeSignProperties();
			typeSignProperties.setSignMode(SignMode.PADES.getType());
			typeSignProperties.setParallel(false);
			typeSignProperties.setProcessId(medasConfig.getProcessId());

			TypePadesProperties typePadesProperties = new TypePadesProperties();
			typePadesProperties.setPage(padesConfig.getNumPagina());
			typePadesProperties.setLeftx(padesConfig.getLeftX());
			typePadesProperties.setLefty(padesConfig.getLeftY());
			typePadesProperties.setRightx(padesConfig.getRightX());
			typePadesProperties.setRighty(padesConfig.getRightY());
			if (medasConfig.isPadesArss()) {
				TypeArssPadesProperties arssPadesProp = new TypeArssPadesProperties();
				arssPadesProp.setLocation(padesConfig.getLocation());
				arssPadesProp.setReason(padesConfig.getReason());
				TypeArssPadesPropertiesApparence arssPadesPropertiesApparence = new TypeArssPadesPropertiesApparence();
				arssPadesPropertiesApparence.setText(padesConfig.getTesto());
				arssPadesProp.setArssPadesPropertiesApparence(arssPadesPropertiesApparence);
				typePadesProperties.setArssPadesProperties(arssPadesProp);
			} else if (medasConfig.isPadesDs()) {
				TypeDsPadesProperties dsPadesProp = new TypeDsPadesProperties();
				TypeDsPadesPropertiesApparence dsPadesPropertiesApparence = new TypeDsPadesPropertiesApparence();
				dsPadesPropertiesApparence.setReasonText(padesConfig.getReason());
				dsPadesProp.setDsPadesPropertiesApparence(dsPadesPropertiesApparence);
				typePadesProperties.setDsPadesProperties(dsPadesProp);
			}

			typeSignProperties.setPadesProperties(typePadesProperties);

			signOneDocReq.setSignProperties(typeSignProperties);

			signOneDocRequest.setSignOneDocReq(signOneDocReq);
			SignOneDocResp signOneDocResponse = syncService.signOneDoc(signOneDocRequest);
			TypeSignOneDocResp signOneDocResp = signOneDocResponse.getSignOneDocResp();
			TypeMessage message = signOneDocResp.getMessage();
			BigInteger messageCode = message.getCode();
			if (messageCode != null && !messageCode.equals(new BigInteger("0"))) {
				String messageString = message.getMessage();
				MessageBean messageBean = new MessageBean();
				messageBean.setDescription(messageString);
				messageBean.setStatus(ResponseStatus.KO);
				logger.error("Errore nella risposta per la richiesta di firma pades - Codice errore: " + messageCode + " Descrizione errore: " + messageString + " " + getHsmUserDescription());
				signResponseBean.setMessage(messageBean);
				for(int i=0;i<listaBytesFileDaFirmare.size();i++){
					FileResponseBean fileResponseBean = new FileResponseBean();
					fileResponseBean.setMessage(messageBean);
					signResponseBean.getFileResponseBean().add(fileResponseBean);
				}
			} else {
				String responseDoc = signOneDocResp.getSignedDocBin();
				MessageBean messageBean = new MessageBean();
				messageBean.setStatus(ResponseStatus.OK);
				FileResponseBean fileResponseBean = new FileResponseBean();
				fileResponseBean.setMessage(messageBean);
				fileResponseBean.setFileFirmato(Base64.decodeBase64(responseDoc.getBytes()));
				signResponseBean.getFileResponseBean().add(fileResponseBean);
			}
			setSignResponseMessage(signResponseBean);
			
		} catch (MalformedURLException e) {
			logger.error(getHsmUserDescription(), e);
			throw new HsmClientSignatureException("Errore in fase di firma pades");
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
		if (clientConfig == null || !(clientConfig instanceof MedasConfig)) {
			logger.error("Non e' stata specificata la configurazione per Medas" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione Medas non specificata");
		}

		MedasConfig medasConfig = (MedasConfig) hsmConfig.getClientConfig();

		WSConfig wsSignConfig = medasConfig.getWsSignConfig();
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
		
		String certId = medasConfig.getCertificateId();
		logger.debug("Id Certificato " + certId);
//		if ( certId == null ) {
//			logger.error("Non e' stato specificato l'id del certificato" + " " + getHsmUserDescription());
//			throw new HsmClientConfigException("Non e' stato specificato l'id del certificato");
//		}
		
		String certSerialNumber = medasConfig.getCertificateSerialNumber();
		logger.debug("SerialNumber Certificato " + certSerialNumber);
		
		String certificateSerialNumber = null;
		if( StringUtils.isBlank(certSerialNumber)){
			CertResponseBean responseCertId = getUserCertificateById();
			MessageBean responseMessageBean = responseCertId.getMessageBean();
			if( responseMessageBean==null || responseMessageBean.getStatus().equals( ResponseStatus.KO ) ){
				MessageBean messageBean = new MessageBean();
				if( responseMessageBean!=null && responseMessageBean.getDescription()!=null )
					messageBean.setDescription(responseMessageBean.getDescription());
				if( responseMessageBean!=null && responseMessageBean.getCode()!=null )
					messageBean.setCode("" + responseMessageBean.getCode());
				messageBean.setStatus(ResponseStatus.KO);
				signResponseBean.setMessage(messageBean);
				return signResponseBean;
			}
	
			List<CertBean> certList = responseCertId.getCertList();
			if( certList!=null && certList.size()>0)
				certificateSerialNumber = certList.get(0).getSerialNumber();
		}
		
		if( certificateSerialNumber==null ){
			MessageBean messageBean = new MessageBean();
			messageBean.setDescription("Non e' stato recuperato il serialNumber del certificato");
			messageBean.setStatus(ResponseStatus.KO);
			signResponseBean.setMessage(messageBean);
			return signResponseBean;
		}

		try {
			url = new URL(wsdlEndpoint);
			QName qname = new QName(serviceNS, serviceName);
			Service service = Service.create(url, qname);
			ScrybaSignServerSync syncService = service.getPort(ScrybaSignServerSync.class);

			OpenSignSessionReq openSignSessionRequest = new OpenSignSessionReq();
			TypeOpenSignSessionReq typeOpenSignSessionReq = new TypeOpenSignSessionReq();

			logger.debug("User: " + medasConfig.getUser() + " Codice fiscale: " + medasConfig.getCodiceFiscale() + " Password: ****** " + " Otp: "
					+ medasConfig.getOtp());

			TypeUser user = new TypeUser();
			if (medasConfig.getUser() != null)
				user.setUsername(medasConfig.getUser());
			if (medasConfig.getCodiceFiscale() != null)
				user.setSsn(medasConfig.getCodiceFiscale());
			typeOpenSignSessionReq.setUser(user);

			TypeCredentials credentials = new TypeCredentials();
			credentials.setPassword(medasConfig.getPassword());
			credentials.setOtp(medasConfig.getOtp());
			typeOpenSignSessionReq.setCredentials(credentials);

			if( medasConfig.getCertificateId()!=null )
				typeOpenSignSessionReq.setCertificateId( medasConfig.getCertificateId() );
			if( medasConfig.getSignaturePower()!=null )
				typeOpenSignSessionReq.setCertificateId( medasConfig.getSignaturePower() );
			
			openSignSessionRequest.setOpenSignSessionReq(typeOpenSignSessionReq);
			OpenSignSessionResp openSignSessionResponse = syncService.openSignSession(openSignSessionRequest);
			TypeOpenSignSessionResp openSignSessionResp = openSignSessionResponse.getOpenSignSessionResp();
			String sessionId = null;
			TypeMessage message = openSignSessionResp.getMessage();
			BigInteger messageCode = message.getCode();
			if (messageCode != null && !messageCode.equals(new BigInteger("0"))) {
				String messageString = message.getMessage();
				logger.error("Errore in fase di apertura sessione firma - Codice errore: " + messageCode + " Descrizione errore: " + messageString + " " + getHsmUserDescription());
				MessageBean messageBean = new MessageBean();
				messageBean.setDescription(messageString);
				messageBean.setStatus(ResponseStatus.KO);
				signResponseBean.setMessage(messageBean);
				return signResponseBean;
			} else {
				sessionId = openSignSessionResp.getSessionId();
				logger.debug("Sessione di firma iniziata - SessionId: " + sessionId);
			}

			SignDocReq signDocRequest = new SignDocReq();
			TypeSignDocReq signDocReq = new TypeSignDocReq();

			TypeSignProperties typeSignProperties = new TypeSignProperties();
			typeSignProperties.setSignMode(SignMode.HASH.getType());
			typeSignProperties.setParallel(false);
			typeSignProperties.setProcessId(medasConfig.getProcessId());
			logger.debug("ProcessId: " + medasConfig.getProcessId());
			logger.debug("CertificateSerialNumber: " + certificateSerialNumber);
			typeSignProperties.setCertificateSerialNumber( certificateSerialNumber );
			signDocReq.setSignProperties(typeSignProperties);
			signDocReq.setSessionId(sessionId);

			for (HashRequestBean hashDaFirmare : listaHashDaFirmare) {
				HashResponseBean hashResponseBean = new HashResponseBean();
				TypeDocument document = new TypeDocument();
				document.setDocHash(hashDaFirmare.getHash());
				String docType = null;
				if (hashDaFirmare.getSignOption() != null) {
					ClientOption clientOption = hashDaFirmare.getSignOption().getClientOption();
					if (clientOption != null && clientOption instanceof MedasOption) {
						docType = ((MedasOption) clientOption).getDocType();
						if (docType != null)
							document.setDocType(docType);
					}
				}

				signDocReq.setDocument(document);

				signDocRequest.setSignDocReq(signDocReq);
				SignDocResp signDocResponse = syncService.signDoc(signDocRequest);
				TypeSignDocResp signDocResp = signDocResponse.getSignDocResp();
				message = signDocResp.getMessage();
				messageCode = message.getCode();
				if (messageCode != null && !messageCode.equals(new BigInteger("0"))) {
					String messageString = message.getMessage();
					logger.error("Errore in fase di firma hash - Codice errore: " + messageCode + "Descrizione errore: " + messageString + " " + getHsmUserDescription());
					MessageBean messageBean = new MessageBean();
					messageBean.setDescription(messageString);
					messageBean.setCode("" + messageCode);
					messageBean.setStatus(ResponseStatus.KO);
					hashResponseBean.setMessage(messageBean);
				} else {
					String signedDocHash = signDocResp.getSignedDocHash();
					logger.debug("Ricevuta hash Firmata " + signedDocHash);
					MessageBean messageBean = new MessageBean();
					messageBean.setStatus(ResponseStatus.OK);
					hashResponseBean.setMessage(messageBean);
					if (signedDocHash != null)
						hashResponseBean.setHashFirmata(Base64.decodeBase64(signedDocHash.getBytes()));
				}
				signResponseBean.getHashResponseBean().add(hashResponseBean);
			}
			setSignResponseMessage(signResponseBean);

			CloseSignSessionReq closeSignSessionRequest = new CloseSignSessionReq();
			TypeCloseSignSessionReq typeCloseSignSessionReq = new TypeCloseSignSessionReq();
			typeCloseSignSessionReq.setSessionId(sessionId);
			closeSignSessionRequest.setCloseSignSessionReq(typeCloseSignSessionReq);
			CloseSignSessionResp closeSignSessionResponse = syncService.closeSignSession(closeSignSessionRequest);
			TypeCloseSignSessionResp closeSignSessionResp = closeSignSessionResponse.getCloseSignSessionResp();
			message = closeSignSessionResp.getMessage();
			messageCode = message.getCode();
			if (messageCode != null && !messageCode.equals(new BigInteger("0"))) {
				String messageString = message.getMessage();
				logger.error("Errore in fase di chiusura sessione firma - Codice errore: " + messageCode + " Descrizione errore: : " + messageString + " " + getHsmUserDescription());
				MessageBean messageBean = new MessageBean();
				messageBean.setDescription(messageString);
				messageBean.setCode("" + messageCode);
				messageBean.setStatus(ResponseStatus.KO);
				signResponseBean.setMessage(messageBean);
				return signResponseBean;
			} else {
				logger.debug("Sessione di firma terminata");
			}

		} catch (MalformedURLException e) {
			logger.error(getHsmUserDescription(), e);
			throw new HsmClientSignatureException("Errore in fase di firma multipla hash");
		}

		return signResponseBean;
	}

	@Override
	public OtpResponseBean richiediOTP() throws HsmClientConfigException, UnsupportedOperationException {
		OtpResponseBean otpResponseBean = new OtpResponseBean();
		logger.debug("Metodo di richiesta OTP - INIZIO");
		URL url;
		HsmConfig hsmConfig = getHsmConfig();
		if (hsmConfig == null) {
			logger.error("Non e' stata specificata la configurazione" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione non specificata");
		}

		ClientConfig clientConfig = hsmConfig.getClientConfig();
		if (clientConfig == null || !(clientConfig instanceof MedasConfig)) {
			logger.error("Non e' stata specificata la configurazione per Medas" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione Medas non specificata");
		}

		MedasConfig medasConfig = (MedasConfig) hsmConfig.getClientConfig();

		WSConfig wsOtpConfig = medasConfig.getWsOtpConfig();
		if (wsOtpConfig == null) {
			logger.error("Non e' stata specificata la configurazione per il ws di richiesta otp" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione WS di richiesta otp non specificata");
		}

		String wsdlEndpoint = wsOtpConfig.getWsdlEndpoint();
		String serviceNS = wsOtpConfig.getServiceNS();
		String serviceName = wsOtpConfig.getServiceName();
		logger.debug("wsdlEndpoint: " + wsdlEndpoint + " serviceNS: " + serviceNS + " serviceName: " + serviceName);
		if (wsdlEndpoint == null || serviceNS == null || serviceName == null) {
			logger.error("Configurazione ws incompleta" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione ws incompleta");
		}

		try {
			url = new URL(wsdlEndpoint);
			QName qname = new QName(serviceNS, serviceName);
			Service service = Service.create(url, qname);
			ScrybaSignServerUtils utilService = service.getPort(ScrybaSignServerUtils.class);

			logger.debug("User: " + medasConfig.getUser() + " Codice fiscale: " + medasConfig.getCodiceFiscale() + " Password: ****** ");
			// "SMS", "ARUBACALL"
			logger.debug("TypeOtp: " + medasConfig.getTypeOtp());

			DespatchOtpReq despatchOtpRequest = new DespatchOtpReq();
			TypeDespatchOtpReq typeDespatchOtpReq = new TypeDespatchOtpReq();
			TypeUser user = new TypeUser();
			if (medasConfig.getUser() != null)
				user.setUsername(medasConfig.getUser());
			if (medasConfig.getCodiceFiscale() != null)
				user.setSsn(medasConfig.getCodiceFiscale());
			typeDespatchOtpReq.setUser(user);

			TypeCredentials credentials = new TypeCredentials();
			credentials.setPassword(medasConfig.getPassword());
			credentials.setType(medasConfig.getTypeOtp().getValue());
			typeDespatchOtpReq.setCredentials(credentials);

			if (medasConfig.getCertificateId() != null) {
				logger.debug("CertificateId: " + medasConfig.getCertificateId());
				typeDespatchOtpReq.setCertificateId(medasConfig.getCertificateId());
			}

			despatchOtpRequest.setDespatchOtpReq(typeDespatchOtpReq);
			DespatchOtpResp despatchOtpResponse = utilService.despatchOtp(despatchOtpRequest);
			TypeDespatchOtpResp despatchOtpResp = despatchOtpResponse.getDespatchOtpResp();
			TypeMessage message = despatchOtpResp.getMessage();
			BigInteger messageCode = message.getCode();
			if (messageCode != null && !messageCode.equals(new BigInteger("0"))) {
				String messageString = message.getMessage();
				MessageBean messageBean = new MessageBean();
				messageBean.setDescription(messageString);
				messageBean.setCode("" + messageCode);
				messageBean.setStatus(ResponseStatus.KO);
				logger.error("Errore nella risposta per la richiesta otp - Codice errore: " + messageCode + " Descrizione errore: " + messageString + " " + getHsmUserDescription());
				otpResponseBean.setMessage(messageBean);
			} else {
				logger.debug("Otp innviato");
				MessageBean messageBean = new MessageBean();
				messageBean.setStatus(ResponseStatus.OK);
				otpResponseBean.setMessage(messageBean);
			}

		} catch (MalformedURLException e) {
			logger.error(getHsmUserDescription(), e);
			throw new UnsupportedOperationException("Errore in fase di richiesta otp");
		}

		return otpResponseBean;
	}

	@Override
	public CertResponseBean getUserCertificateList() throws HsmClientConfigException, UnsupportedOperationException {
		logger.debug("Metodo di rihiesta certificati utenti - INIZIO");
		CertResponseBean certResponseBean = new CertResponseBean();
		List<CertBean> certBeanList = new ArrayList<CertBean>();

		URL url;
		HsmConfig hsmConfig = getHsmConfig();
		if (hsmConfig == null) {
			logger.error("Non e' stata specificata la configurazione" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione non specificata");
		}

		ClientConfig clientConfig = hsmConfig.getClientConfig();
		if (clientConfig == null || !(clientConfig instanceof MedasConfig)) {
			logger.error("Non e' stata specificata la configurazione per Medas" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione Medas non specificata");
		}

		MedasConfig medasConfig = (MedasConfig) hsmConfig.getClientConfig();

		WSConfig wsCertConfig = medasConfig.getWsCertConfig();
		if (wsCertConfig == null) {
			logger.error("Non e' stata specificata la configurazione per il ws di richiesta certificati" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione WS di richiesta certificati non specificata");
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
			ScrybaSignServerUtils utilService = service.getPort(ScrybaSignServerUtils.class);
			
			GetUserInfo3Req getUserInfo3Request = new GetUserInfo3Req();
			TypeGetUserInfo3Req typeGetUserInfo3Req = new TypeGetUserInfo3Req();
			logger.debug("User: " + medasConfig.getUser() + " Codice fiscale: " + medasConfig.getCodiceFiscale() + " ProcessId: " + medasConfig.getProcessId() );
			if (medasConfig.getUser() != null)
				typeGetUserInfo3Req.setUsername(medasConfig.getUser());
			if (medasConfig.getCodiceFiscale() != null)
				typeGetUserInfo3Req.setSsn(medasConfig.getCodiceFiscale());
			if(medasConfig.getProcessId() != null)
				typeGetUserInfo3Req.setProcessId( medasConfig.getProcessId() );
			
			TypeDocTypeList typeDocTypeList = new TypeDocTypeList();
			logger.info("Aggiungo Doctype " + medasConfig.getCertificateDocType() );
			if( medasConfig.getCertificateDocType()!=null ){
				typeDocTypeList.getDoctype().add( medasConfig.getCertificateDocType() );
			}
			typeGetUserInfo3Req.setDocTypeList( typeDocTypeList  );
			
			getUserInfo3Request.setGetUserInfo3Req(typeGetUserInfo3Req );
			GetUserInfo3Resp getUserInfo3Response = utilService.getUserInfo3(getUserInfo3Request);
			TypeGetUserInfo3Resp getUserInfo3Resp = getUserInfo3Response.getGetUserInfo3Resp();
			TypeMessage message = getUserInfo3Resp.getMessage();
			BigInteger messageCode = message.getCode();
			if( messageCode!=null && !messageCode.equals(new BigInteger("0")) ){
				String messageString = message.getMessage();
				MessageBean messageBean = new MessageBean();
				messageBean.setDescription( messageString );
				logger.error("Errore nella risposta per la richiesta properties utenti - Descrizione errore: " + messageString + " " + getHsmUserDescription());
				certResponseBean.setMessageBean( messageBean );
			} else {
				TypeSigningUser3 signerUser = getUserInfo3Resp.getSignerUser();
				//TypeUser us = signerUser.getUser();
				//logger.debug("ssn " + us.getSsn());
				//logger.debug("usrname " + us.getUsername() );

				TypeSignaturePowerList2 typeSigPowerList = signerUser.getSignaturePowerList();
				List<TypeSignaturePower2> sigPowerList = typeSigPowerList.getSignaturePower();
				for(TypeSignaturePower2 sigPower : sigPowerList ){
					String codicePotereFirma = sigPower.getCode();
					logger.debug("codicePotereFirma " + codicePotereFirma);
					TypeCertificateList2 certificateList = sigPower.getCertificateList();
					List<TypeCert3> listCert = certificateList.getCertificate();
					logger.debug(listCert);
					for(TypeCert3 cert : listCert){
						CertBean certBean = new CertBean();
						logger.debug("Recuperato certificato con id " + cert.getId());
						TypeOTPtypeList otpList = cert.getOTPtypeList();
						if( otpList!=null ){
							certBean.setOtps(otpList.getOTPtype());
						}
						certBean.setDescrizione(cert.getDescription());
						logger.info("id " + cert.getId() + " " + cert.getDescription() + " " + cert.getValidFrom() + " " + cert.getValidTo() + " " + cert.getCertificationAuthority() );
						certBean.setCertId( cert.getId() );
						certBean.setSignaturePower(codicePotereFirma);
						certBeanList.add(certBean);
					}

					//					 TypeProfile profile = sigPower.getProfile();
					//					 TypeProcessList typeProcessList = profile.getProcessList();
					//					 List<TypeProcess2> processList = typeProcessList.getProcess();
					//					 for(TypeProcess2 process: processList){
					//						 logger.debug("PROCESS ------ " + process.getCode() + " " + process.getDescription());
					//						 TypeDocTypeList2 typeDocTypeList = process.getDocTypeList();
					//						 List<TypeDocumentType> docTypeList = typeDocTypeList.getDocType();
					//						 for(TypeDocumentType docType: docTypeList){
					//						 logger.debug("DOCTYPE ------ " + docType.getCode() + " " + docType.getDescription() );
					//						 }
					//						 logger.debug("-------------");
					//					 }
				}
				certResponseBean.setCertList(certBeanList);
				MessageBean messageBean = new MessageBean();
				messageBean.setStatus(ResponseStatus.OK);
				certResponseBean.setMessageBean(messageBean);
			}
			
		} catch (MalformedURLException e) {
			logger.error(getHsmUserDescription(), e);
			throw new UnsupportedOperationException("Errore in fase di richiesta certificati utenti");
		}

		return certResponseBean;
	}
	
	public CertResponseBean getUserCertificateById() throws HsmClientConfigException, UnsupportedOperationException {
		logger.debug("Metodo di richiesta certificato utente in base all'id  - INIZIO");
		CertResponseBean certResponseBean = new CertResponseBean();
		List<CertBean> certBeanList = new ArrayList<CertBean>();

		URL url;
		HsmConfig hsmConfig = getHsmConfig();
		if (hsmConfig == null) {
			logger.error("Non e' stata specificata la configurazione" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione non specificata");
		}

		ClientConfig clientConfig = hsmConfig.getClientConfig();
		if (clientConfig == null || !(clientConfig instanceof MedasConfig)) {
			logger.error("Non e' stata specificata la configurazione per Medas" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione Medas non specificata");
		}

		MedasConfig medasConfig = (MedasConfig) hsmConfig.getClientConfig();

		WSConfig wsCertConfig = medasConfig.getWsCertConfig();
		if (wsCertConfig == null) {
			logger.error("Non e' stata specificata la configurazione per il ws di richiesta certificati" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione WS di richiesta certificati non specificata");
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
			ScrybaSignServerUtils utilService = service.getPort(ScrybaSignServerUtils.class);

			CertListReq certListRequest = new CertListReq();
			TypeCertListReq typeCertListReq = new TypeCertListReq();

			logger.debug("User: " + medasConfig.getUser() + " Codice fiscale: " + medasConfig.getCodiceFiscale() + " Password: ****** ");

			TypeUser user = new TypeUser();
			if (medasConfig.getUser() != null)
				user.setUsername(medasConfig.getUser());
			if (medasConfig.getCodiceFiscale() != null)
				user.setSsn(medasConfig.getCodiceFiscale());
			typeCertListReq.setUser(user);

			TypeCredentials credentials = new TypeCredentials();
			credentials.setPassword(medasConfig.getPassword());
			typeCertListReq.setCredentials(credentials);
			
			if( medasConfig.getCertificateId()!=null )
				typeCertListReq.setCertificateId( medasConfig.getCertificateId() );

			certListRequest.setCertListReq(typeCertListReq);
			
			CertListResp certListResponse = utilService.certList(certListRequest);
			TypeCertListResp certListResp = certListResponse.getCertListResp();
			TypeMessage message = certListResp.getMessage();
			BigInteger messageCode = message.getCode();
			if (messageCode != null && !messageCode.equals(new BigInteger("0"))) {
				String messageString = message.getMessage();
				MessageBean messageBean = new MessageBean();
				messageBean.setDescription(messageString);
				messageBean.setCode("" + messageCode);
				messageBean.setStatus(ResponseStatus.KO);
				logger.error("Errore nella risposta per la richiesta certificato utente in base all'id  - Codice errore: " + messageCode + " Descrizione errore: " + messageString + " " + getHsmUserDescription());
				certResponseBean.setMessageBean(messageBean);
			} else {
				List<TypeRawCertificate> rawCertificates = certListResp.getRawCertificates();
				for (TypeRawCertificate rawCertificate : rawCertificates) {
					String serialNumber = rawCertificate.getSerialNumber();
					String cert = rawCertificate.getCertificate();
					CertBean certBean = new CertBean();
					logger.debug("Recuperato certificato con numero serie " + serialNumber);
					certBean.setSerialNumber(serialNumber);
					certBean.setCertValue(Base64.decodeBase64(cert.getBytes()));
					certBeanList.add(certBean);
				}
				certResponseBean.setCertList(certBeanList);
				MessageBean messageBean = new MessageBean();
				messageBean.setStatus(ResponseStatus.OK);
				certResponseBean.setMessageBean(messageBean);
			}

		} catch (MalformedURLException e) {
			logger.error(getHsmUserDescription(), e);
			throw new UnsupportedOperationException("Errore in fase di richiesta certificati utenti");
		}

		return certResponseBean;
	}

	@Override
	public SignResponseBean firmaXades(List<byte[]>  listaBytesFileDaFirmare, SignOption signOption)
			throws HsmClientConfigException, HsmClientSignatureException, UnsupportedOperationException {
		throw new UnsupportedOperationException("Funzionalità di firma Xades non supportata");
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
		if (clientConfig == null || !(clientConfig instanceof MedasConfig)) {
			logger.error("Non e' stata specificata la configurazione per Medas" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione Medas non specificata");
		}

		MedasConfig medasConfig = (MedasConfig) hsmConfig.getClientConfig();

		WSConfig wsSignConfig = medasConfig.getWsSignConfig();
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
			ScrybaSignServerSync syncService = service.getPort(ScrybaSignServerSync.class);

			OpenSignSessionReq openSignSessionRequest = new OpenSignSessionReq();
			TypeOpenSignSessionReq typeOpenSignSessionReq = new TypeOpenSignSessionReq();

			logger.debug("User: " + medasConfig.getUser() + " Codice fiscale: " + medasConfig.getCodiceFiscale() + " Password: ****** " + " Otp: "
					+ medasConfig.getOtp());

			TypeUser user = new TypeUser();
			if (medasConfig.getUser() != null)
				user.setUsername(medasConfig.getUser());
			if (medasConfig.getCodiceFiscale() != null){
				user.setSsn(medasConfig.getCodiceFiscale());
			}
			typeOpenSignSessionReq.setUser(user);
			
			if( medasConfig.getCertificateId()!=null ){
				typeOpenSignSessionReq.setCertificateId( medasConfig.getCertificateId() );
				logger.debug("Certificate Id: " + medasConfig.getCertificateId());
			}
			if( medasConfig.getSignaturePower()!=null ){
				typeOpenSignSessionReq.setSignaturePowerId( medasConfig.getSignaturePower() );
				logger.debug("Signature power Id: " + medasConfig.getSignaturePower());
			}

			TypeCredentials credentials = new TypeCredentials();
			credentials.setPassword(medasConfig.getPassword());
			credentials.setOtp(medasConfig.getOtp());
			typeOpenSignSessionReq.setCredentials(credentials);

			openSignSessionRequest.setOpenSignSessionReq(typeOpenSignSessionReq);
			OpenSignSessionResp openSignSessionResponse = syncService.openSignSession(openSignSessionRequest);
			TypeOpenSignSessionResp openSignSessionResp = openSignSessionResponse.getOpenSignSessionResp();
			String sessionId = null;
			TypeMessage message = openSignSessionResp.getMessage();
			BigInteger messageCode = message.getCode();
			if (messageCode != null && !messageCode.equals(new BigInteger("0"))) {
				String messageString = message.getMessage();
				logger.error("Errore in fase di apertura sessione firma - Codice errore: " + messageCode + " Descrizione errore: " + messageString + " " + getHsmUserDescription());
				MessageBean messageBean = new MessageBean();
				messageBean.setDescription(messageString);
				messageBean.setStatus(ResponseStatus.KO);
				sessionResponseBean.setMessage(messageBean);
			} else {
				sessionId = openSignSessionResp.getSessionId();
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
		if (clientConfig == null || !(clientConfig instanceof MedasConfig)) {
			logger.error("Non e' stata specificata la configurazione per Medas" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione Medas non specificata");
		}

		MedasConfig medasConfig = (MedasConfig) hsmConfig.getClientConfig();

		WSConfig wsSignConfig = medasConfig.getWsSignConfig();
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
		
		String certId = medasConfig.getCertificateId();
		logger.debug("Id Certificato " + certId);
//		if ( certId == null ) {
//			logger.error("Non e' stato specificato l'id del certificato" + " " + getHsmUserDescription());
//			throw new HsmClientConfigException("Non e' stato specificato l'id del certificato");
//		}
		
		String certificateSerialNumber = medasConfig.getCertificateSerialNumber();
		logger.debug("SerialNumber Certificato " + certificateSerialNumber);
		
		if( StringUtils.isBlank(certificateSerialNumber)){
			CertResponseBean responseCertId = getUserCertificateById();
			MessageBean responseMessageBean = responseCertId.getMessageBean();
			if( responseMessageBean==null || responseMessageBean.getStatus().equals( ResponseStatus.KO ) ){
				MessageBean messageBean = new MessageBean();
				if( responseMessageBean!=null && responseMessageBean.getDescription()!=null )
					messageBean.setDescription(responseMessageBean.getDescription());
				if( responseMessageBean!=null && responseMessageBean.getCode()!=null )
					messageBean.setCode("" + responseMessageBean.getCode());
				messageBean.setStatus(ResponseStatus.KO);
				signResponseBean.setMessage(messageBean);
				return signResponseBean;
			}
	
			List<CertBean> certList = responseCertId.getCertList();
			if( certList!=null && certList.size()>0)
				certificateSerialNumber = certList.get(0).getSerialNumber();
		}
		
		if( certificateSerialNumber==null ){
			MessageBean messageBean = new MessageBean();
			messageBean.setDescription("Non e' stato recuperato il serialNumber del certificato");
			messageBean.setStatus(ResponseStatus.KO);
			signResponseBean.setMessage(messageBean);
			return signResponseBean;
		}
		try {
			url = new URL(wsdlEndpoint);
			QName qname = new QName(serviceNS, serviceName);
			Service service = Service.create(url, qname);
			ScrybaSignServerSync syncService = service.getPort(ScrybaSignServerSync.class);

			SignDocReq signDocRequest = new SignDocReq();
			TypeSignDocReq signDocReq = new TypeSignDocReq();

			TypeSignProperties typeSignProperties = new TypeSignProperties();
			typeSignProperties.setSignMode(SignMode.HASH.getType());
			typeSignProperties.setParallel(false);
			typeSignProperties.setProcessId(medasConfig.getProcessId());
			logger.debug("ProcessId: " + medasConfig.getProcessId());
			logger.debug("CertificateSerialNumber: " +certificateSerialNumber);
			typeSignProperties.setCertificateSerialNumber(certificateSerialNumber);
				
			signDocReq.setSignProperties(typeSignProperties);
			signDocReq.setSessionId(sessionId);

			for (HashRequestBean hashDaFirmare : listaHashDaFirmare) {
				HashResponseBean hashResponseBean = new HashResponseBean();
				TypeDocument document = new TypeDocument();
				document.setDocHash(hashDaFirmare.getHash());
				String docType = null;
				if (hashDaFirmare.getSignOption() != null) {
					ClientOption clientOption = hashDaFirmare.getSignOption().getClientOption();
					if (clientOption != null && clientOption instanceof MedasOption) {
						docType = ((MedasOption) clientOption).getDocType();
						if (docType != null)
							document.setDocType(docType);
					}
				}

				signDocReq.setDocument(document);

				signDocRequest.setSignDocReq(signDocReq);
				SignDocResp signDocResponse = syncService.signDoc(signDocRequest);
				TypeSignDocResp signDocResp = signDocResponse.getSignDocResp();
				TypeMessage message = signDocResp.getMessage();
				BigInteger messageCode = message.getCode();
				if (messageCode != null && !messageCode.equals(new BigInteger("0"))) {
					String messageString = message.getMessage();
					logger.error("Errore in fase di firma hash - Codice errore: " + messageCode + "Descrizione errore: " + messageString + " " + getHsmUserDescription());
					MessageBean messageBean = new MessageBean();
					messageBean.setDescription(messageString);
					messageBean.setCode("" + messageCode);
					messageBean.setStatus(ResponseStatus.KO);
					hashResponseBean.setMessage(messageBean);
				} else {
					String signedDocHash = signDocResp.getSignedDocHash();
					logger.debug("Ricevuta hash Firmata " + signedDocHash);
					MessageBean messageBean = new MessageBean();
					messageBean.setStatus(ResponseStatus.OK);
					hashResponseBean.setMessage(messageBean);
					if (signedDocHash != null)
						hashResponseBean.setHashFirmata(Base64.decodeBase64(signedDocHash.getBytes()));
				}
				signResponseBean.getHashResponseBean().add(hashResponseBean);
			}
			setSignResponseMessage(signResponseBean);
			
		} catch (MalformedURLException e) {
			logger.error(getHsmUserDescription(), e);
			throw new HsmClientSignatureException("Errore in fase di firma multipla hash con sessione");
		}

		return signResponseBean;
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
		if (clientConfig == null || !(clientConfig instanceof MedasConfig)) {
			logger.error("Non e' stata specificata la configurazione per Medas" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione Medas non specificata");
		}

		MedasConfig medasConfig = (MedasConfig) hsmConfig.getClientConfig();

		WSConfig wsSignConfig = medasConfig.getWsSignConfig();
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
			ScrybaSignServerSync syncService = service.getPort(ScrybaSignServerSync.class);

			CloseSignSessionReq closeSignSessionRequest = new CloseSignSessionReq();
			TypeCloseSignSessionReq typeCloseSignSessionReq = new TypeCloseSignSessionReq();
			typeCloseSignSessionReq.setSessionId(sessionId);
			closeSignSessionRequest.setCloseSignSessionReq(typeCloseSignSessionReq);
			CloseSignSessionResp closeSignSessionResponse = syncService.closeSignSession(closeSignSessionRequest);
			TypeCloseSignSessionResp closeSignSessionResp = closeSignSessionResponse.getCloseSignSessionResp();
			TypeMessage message = closeSignSessionResp.getMessage();
			BigInteger messageCode = message.getCode();
			if (messageCode != null && !messageCode.equals(new BigInteger("0"))) {
				String messageString = message.getMessage();
				logger.error("Errore in fase di chiusura sessione firma - Codice errore: " + messageCode + " Descrizione errore: : " + messageString + " " + getHsmUserDescription());
				messageBean = new MessageBean();
				messageBean.setDescription(messageString);
				messageBean.setCode("" + messageCode);
				messageBean.setStatus(ResponseStatus.KO);
			} else {
				logger.debug("Sessione di firma terminata");
				messageBean = new MessageBean();
				messageBean.setStatus(ResponseStatus.OK);
			}

		} catch (MalformedURLException e) {
			logger.error(getHsmUserDescription(), e);
			throw new UnsupportedOperationException("Errore in fase di chiusura sessione di firma ");
		}
		return messageBean;

	}

}
