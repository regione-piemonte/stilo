package it.eng.hsm.client.impl;

import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.soap.SOAPBinding;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import it.eng.hsm.client.bean.MessageBean;
import it.eng.hsm.client.bean.ResponseStatus;
import it.eng.hsm.client.bean.cert.CertBean;
import it.eng.hsm.client.bean.cert.CertResponseBean;
import it.eng.hsm.client.bean.otp.OtpResponseBean;
import it.eng.hsm.client.bean.sign.HashRequestBean;
import it.eng.hsm.client.bean.sign.HashResponseBean;
import it.eng.hsm.client.bean.sign.SignResponseBean;
import it.eng.hsm.client.config.ClientConfig;
import it.eng.hsm.client.config.HsmConfig;
import it.eng.hsm.client.config.PkBoxConfig;
import it.eng.hsm.client.config.ProxyConfig;
import it.eng.hsm.client.config.WSConfig;
import it.eng.hsm.client.exception.HsmClientConfigException;
import it.eng.hsm.client.exception.HsmClientSignatureException;
import it.eng.hsm.client.pkbox.common.generated.ByteArray;
import it.eng.hsm.client.pkbox.envelope.generated.EnvelopePortType;
import it.eng.hsm.client.pkbox.generated.PKBoxException_Exception;
import it.eng.hsm.client.pkbox.generated.UtilsPortType;
import it.eng.hsm.client.pkbox.otp.generated.NCFRWSException_Exception;
import it.eng.hsm.client.pkbox.otp.generated.OTPSenderWS;

public class HsmPkBox extends HsmImpl {

	private static Logger logger = Logger.getLogger(HsmPkBox.class);

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
		if (clientConfig == null || !(clientConfig instanceof PkBoxConfig)) {
			logger.error("Non e' stata specificata la configurazione per PkBox" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione Aruba non specificata");
		}
		
		final PkBoxConfig pkboxConfig = (PkBoxConfig) hsmConfig.getClientConfig();
		// Devo essere sicuro che user e password non siano null
		if (pkboxConfig.getUser() == null) {
			pkboxConfig.setUser("");
		}
		if (pkboxConfig.getPassword() == null) {
			pkboxConfig.setPassword("");
		}

		WSConfig wsOtpConfig = pkboxConfig.getWsOtpConfig();
		if (wsOtpConfig == null) {
			logger.error("Non e' stata specificata la configurazione per il ws di richiesta otp" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione WS di richiesta otp non specificata");
		}
		
		ProxyConfig proxyConfig = pkboxConfig.getProxyConfig();
		if (proxyConfig != null && proxyConfig.isUseProxy()) {
			initProxyConnection(proxyConfig.getProxyHost(), proxyConfig.getProxyPort(), proxyConfig.getProxyUser(), proxyConfig.getProxyPassword());
		} else {
			resetProxy();
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
			
			OTPSenderWS pkboxService = service.getPort(OTPSenderWS.class);
			SOAPBinding binding = (SOAPBinding) ((BindingProvider) pkboxService).getBinding();
			BindingProvider bindingProvider = (BindingProvider) pkboxService;
			
			Map<String, Object> req_ctx = ((BindingProvider)pkboxService).getRequestContext();
		    req_ctx.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, wsdlEndpoint);
		    
			Map<String, List<String>> headers = new HashMap<String, List<String>>();
			String authString = pkboxConfig.getUser() + ":" + pkboxConfig.getPassword();
		    //String authString = pkboxConfig.getUser() + ":" + pkboxConfig.getPassword() ;
		    String authorization =  com.itextpdf.text.pdf.codec.Base64.encodeBytes(authString.getBytes());
		    logger.debug("Basic " + authorization);
	       
		    headers.put("Authorization", Collections.singletonList("Basic " + authorization));
		    req_ctx.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
				
		    logger.debug("---- Invoco sendOtp con parametri: ");
		    logger.debug("user " + pkboxConfig.getUser() );
		    logger.debug("password " + pkboxConfig.getPassword() );
			
			Authenticator.setDefault(new Authenticator() {
			    @Override
			    protected PasswordAuthentication getPasswordAuthentication() {
			        return new PasswordAuthentication(
			        	pkboxConfig.getUser(),
			        	pkboxConfig.getPassword().toCharArray());
			    }
			});
		
			pkboxService.sendOtp();
			logger.debug("Otp innviato");
			MessageBean messageBean = new MessageBean();
			messageBean.setStatus(ResponseStatus.OK);
			otpResponseBean.setMessage(messageBean);
		} catch (NCFRWSException_Exception e) {
			logger.error(getHsmUserDescription(),e);
			MessageBean messageBean = new MessageBean();
			String messageString = "Errore";
			String messageCode = "Errore";
			messageBean.setDescription(messageString);
			messageBean.setCode("" + messageCode);
			messageBean.setStatus(ResponseStatus.KO);
			logger.error("Errore nella risposta per la richiesta otp - Codice errore: " + messageCode + " Descrizione errore: " + messageString + " " + getHsmUserDescription());
			otpResponseBean.setMessage(messageBean);
		} catch (MalformedURLException e) {
			logger.error(getHsmUserDescription(), e);
			throw new UnsupportedOperationException("Errore in fase di richiesta otp");
		}
		
		return otpResponseBean;
	}
	
	@Override
    public SignResponseBean firmaMultiplaHash(List<HashRequestBean> listaHashDaFirmare) throws HsmClientConfigException, HsmClientSignatureException, UnsupportedOperationException {

        logger.debug("Metodo di firma multipla hash - INIZIO");
        SignResponseBean signResponseBean = new SignResponseBean();
        URL url;
        HsmConfig hsmConfig = getHsmConfig();
        if (hsmConfig == null) {
            logger.error("Non e' stata specificata la configurazione" + " " + getHsmUserDescription());
            throw new HsmClientConfigException("Configurazione non specificata");
        }

        ClientConfig clientConfig = hsmConfig.getClientConfig();
        if (clientConfig == null || !(clientConfig instanceof PkBoxConfig)) {
            logger.error("Non e' stata specificata la configurazione per PkBox" + " " + getHsmUserDescription());
            throw new HsmClientConfigException("Configurazione Aruba non specificata");
        }

        PkBoxConfig pkBoxConfig = (PkBoxConfig) hsmConfig.getClientConfig();

        WSConfig wsSignConfig = pkBoxConfig.getWsSignConfig();
        if (wsSignConfig == null) {
            logger.error("Non e' stata specificata la configurazione per il ws di firma" + " " + getHsmUserDescription());
            throw new HsmClientConfigException("Configurazione WS di firma non specificata");
        }

        ProxyConfig proxyConfig = pkBoxConfig.getProxyConfig();
        if (proxyConfig != null && proxyConfig.isUseProxy()) {
            initProxyConnection(proxyConfig.getProxyHost(), proxyConfig.getProxyPort(), proxyConfig.getProxyUser(), proxyConfig.getProxyPassword());
        } else {
            resetProxy();
        }

        String wsdlEndpoint = wsSignConfig.getWsdlEndpoint();
        String wsdlInternalEndpoint = wsSignConfig.getWsdlInternalEndpoint();
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

            EnvelopePortType pkboxService = service.getPort(EnvelopePortType.class);
            SOAPBinding binding = (SOAPBinding) ((BindingProvider) pkboxService).getBinding();
            BindingProvider bindingProvider = (BindingProvider) pkboxService;
            bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, wsdlInternalEndpoint);

            byte[] keyID = null;
            String customerinfo = null;
            int encoding = 2;
            String signerPin = pkBoxConfig.getOtpPwd();
            String signer = pkBoxConfig.getAlias();
            String pin = pkBoxConfig.getPin();
            String environment = "default";

            List<ByteArray> digests = new ArrayList<ByteArray>();

            for (HashRequestBean hashDaFirmare : listaHashDaFirmare) {
                byte[] digest = Base64.decodeBase64(hashDaFirmare.getHash());
                logger.debug("digest" + digest);
                logger.debug("digest BASE64 " + hashDaFirmare.getHash());
                logger.debug("digest decodificato " + new String(digest));
                ByteArray b = new ByteArray();
                b.setBuffer(digest);
                digests.add(b);
            }

            logger.debug("---- Invoco il servizio sign con parametri ");
            logger.debug("wsdlEndpoint " + wsdlEndpoint );
            logger.debug("wsdlInternalEndpoint " + wsdlInternalEndpoint );
            logger.debug("serviceNS " + serviceNS );
            logger.debug("serviceName " + serviceName );
            logger.debug("url " + url );
            logger.debug("qname" + qname );
            logger.debug("environment " + environment );
            logger.debug("signer " + signer);
            logger.debug("pin " + pin);
            logger.debug("signerPin " + signerPin);
            //logger.debug("algorithm " + algorithm);
            logger.debug("encoding " + encoding);

           List<ByteArray> hashSignedList = null;
            try {
                hashSignedList = pkboxService.multisigndigest(environment, digests, customerinfo, signer, pin, signerPin, encoding, null);
                //byte[] hashSigned = pkboxService.signdigest(environment, digest, customerinfo, signer, pin, signerPin, encoding, null);
                logger.debug("Ricevuto lista hash firmate - hashSignedList " + hashSignedList);

            } catch (it.eng.hsm.client.pkbox.envelope.generated.PKBoxException_Exception e) {
                logger.error(getHsmUserDescription(), e);
                MessageBean messageBean = new MessageBean();
                messageBean.setDescription(e.getMessage());
                messageBean.setCode("Errore");
                messageBean.setStatus(ResponseStatus.KO);
                logger.error("Errore nella risposta di firma multipla hash - Descrizione errore: " + e.getMessage() + " " + getHsmUserDescription());
                signResponseBean.setMessage(messageBean); 
            }

            if( hashSignedList!=null ){
            	logger.debug("ciclo sulle impronte restituite " );
            	for (ByteArray hashSigned : hashSignedList){
                	logger.debug("hashSigned " + hashSigned);
                	HashResponseBean hashResponseBean = new HashResponseBean();
                    MessageBean messageBean = new MessageBean();
                    if( hashSigned!=null){
                    	logger.debug("setto stato ok e hashSigned.getBuffer() " + new String(hashSigned.getBuffer()) );
                    	messageBean.setStatus(ResponseStatus.OK);
                    	messageBean.setDescription("");
                        messageBean.setCode("");
                    	hashResponseBean.setMessage(messageBean);
                        hashResponseBean.setHashFirmata(hashSigned.getBuffer());
                    } else {
                    	logger.debug("setto stato ko  "  );
                    	messageBean.setStatus(ResponseStatus.KO);
                        messageBean.setDescription("Hash firmata nulla");
                        messageBean.setCode("Errore");
                        hashResponseBean.setMessage(messageBean);
                    }
                    signResponseBean.getHashResponseBean().add(hashResponseBean);
                }
            	setSignResponseMessage(signResponseBean);
            }

        }  catch (MalformedURLException e) {
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
		if (clientConfig == null || !(clientConfig instanceof PkBoxConfig)) {
			logger.error("Non e' stata specificata la configurazione per PkBox" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione Aruba non specificata");
		}

		PkBoxConfig pkBoxConfig = (PkBoxConfig) hsmConfig.getClientConfig();

		WSConfig wsCertConfig = pkBoxConfig.getWsCertConfig();
		if (wsCertConfig == null) {
			logger.error("Non e' stata specificata la configurazione per il ws di richiesta certificati" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione WS di richiesta certificati non specificata");
		}

		ProxyConfig proxyConfig = pkBoxConfig.getProxyConfig();
		if (proxyConfig != null && proxyConfig.isUseProxy()) {
			initProxyConnection(proxyConfig.getProxyHost(), proxyConfig.getProxyPort(), proxyConfig.getProxyUser(), proxyConfig.getProxyPassword());
		} else {
			resetProxy();
		}

		String wsdlEndpoint = wsCertConfig.getWsdlEndpoint();
		String wsdlInternalEndpoint = wsCertConfig.getWsdlInternalEndpoint();
		String serviceNS = wsCertConfig.getServiceNS();
		String serviceName = wsCertConfig.getServiceName();
		logger.debug("wsdlEndpoint: " + wsdlEndpoint + " serviceNS: " + serviceNS + " serviceName: " + serviceName);
		if (wsdlEndpoint == null || serviceNS == null || serviceName == null) {
			logger.error("Configurazione ws incompleta" + " " + getHsmUserDescription());
			throw new HsmClientConfigException("Configurazione ws incompleta");
		}
		
		logger.debug("Chiamo la url in https");
		try {
			url = new URL(wsdlEndpoint);
			QName qname = new QName(serviceNS, serviceName);
			Service service = Service.create(url, qname);
			
			UtilsPortType pkboxService = service.getPort(UtilsPortType.class);
			SOAPBinding binding = (SOAPBinding) ((BindingProvider) pkboxService).getBinding();
			BindingProvider bindingProvider = (BindingProvider) pkboxService;
			bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, wsdlInternalEndpoint);
				
			int encoding = 1;
			String environment = "default";
			logger.debug("---- Invoco exportCertificate con parametri: ");
			logger.debug("environment " + environment);
			logger.debug("userAlias " + pkBoxConfig.getAlias());
			logger.debug("encoding " + encoding );
			byte[] certBytes = pkboxService.exportCertificate(environment, pkBoxConfig.getAlias(), encoding);
			CertBean certBean = new CertBean();
			certBean.setCertId("C1");
			if (certBytes != null)
				certBean.setCertValue(certBytes);
			certBeanList.add(certBean);
			
			certResponseBean.setCertList(certBeanList);
			MessageBean messageBean = new MessageBean();
			messageBean.setStatus(ResponseStatus.OK);
			certResponseBean.setMessageBean(messageBean);
			
		} catch (PKBoxException_Exception e) {
			MessageBean messageBean = new MessageBean();
			messageBean.setDescription("Errore nella risposta di certificati utente");
			logger.error("Errore nella risposta di certificati utente" + " " + getHsmUserDescription());
			certResponseBean.setMessageBean(messageBean);
		} catch (MalformedURLException e) {
			logger.error(getHsmUserDescription(), e);
		}
		
		return certResponseBean;
	}
}
