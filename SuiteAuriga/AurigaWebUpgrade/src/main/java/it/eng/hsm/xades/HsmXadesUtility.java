/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.hsm.xades;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.ui.module.layout.server.firmaHsm.bean.FirmaHsmBean;
import it.eng.auriga.ui.module.layout.server.firmaXades.bean.FirmaXadesBean;
import it.eng.hsm.HsmBaseUtility;
import it.eng.hsm.HsmClientFactory;
import it.eng.hsm.client.Hsm;
import it.eng.hsm.client.bean.MessageBean;
import it.eng.hsm.client.bean.ResponseStatus;
import it.eng.hsm.client.bean.sign.FileResponseBean;
import it.eng.hsm.client.bean.sign.SignResponseBean;
import it.eng.hsm.client.config.HsmConfig;
import it.eng.hsm.client.config.HsmType;
import it.eng.hsm.client.config.InfoCertConfig;
import it.eng.hsm.client.config.RestConfig;
import it.eng.hsm.client.exception.HsmClientConfigException;
import it.eng.hsm.client.exception.HsmClientSignatureException;
import it.eng.hsm.client.impl.HsmImpl;
import it.eng.hsm.client.option.SignOption;
import it.eng.utility.storageutil.exception.StorageException;

public class HsmXadesUtility extends HsmBaseUtility {
	
	private static Logger log = Logger.getLogger(HsmXadesUtility.class);
	
	public byte[] sigilloXades(byte[] bytesFileDaFirmare, FirmaXadesBean firmaXadesBean, HttpSession session) throws Exception {
		
		String providerSigillo = firmaXadesBean.getProvider();
		
		Hsm hsmClient = null;
		if (StringUtils.isBlank(providerSigillo)) {
			// Utilizzo il vecchio codice InfoCert per la retrocompatibilità con le vecchie configurazioni
			HsmConfig hsmConfig = new HsmConfig();
			hsmConfig.setHsmType(HsmType.INFOCERT);
			
			RestConfig restConfig = new RestConfig();
			restConfig.setUrlEndpoint(firmaXadesBean.getEndpoint());
			InfoCertConfig infoCertConfig = new InfoCertConfig();
			infoCertConfig.setAuto(false);
			infoCertConfig.setAlias(firmaXadesBean.getAlias());
			infoCertConfig.setPin(firmaXadesBean.getPin());
			infoCertConfig.setOtp(firmaXadesBean.getOtp());
			infoCertConfig.setRestConfig(restConfig);
			infoCertConfig.setRequireSignatureInSession(false);

			hsmConfig.setClientConfig(infoCertConfig);
			
			hsmClient = HsmImpl.getNewInstance(hsmConfig);
		} else {
			String tipoHsm = getTipoHsm(providerSigillo, false, session);
			String provider = firmaXadesBean.getProvider();
			String userid = firmaXadesBean.getUserid();
			String delegatedUserid = firmaXadesBean.getDelegatedUserid();
			String password = firmaXadesBean.getPassword();					
			String pin = firmaXadesBean.getPin();		
			String key = firmaXadesBean.getKey();	
			String secret = firmaXadesBean.getSecret();	
			FirmaHsmBean lFirmaHsmBean = new FirmaHsmBean();					
			// Setto firmatario ed eventuale delegante
			if (StringUtils.isNotBlank(delegatedUserid)) {
				lFirmaHsmBean.setUsername(delegatedUserid);
				lFirmaHsmBean.setUsernameDelegante(userid);
			} else {
				lFirmaHsmBean.setUsername(userid);
				lFirmaHsmBean.setUsernameDelegante("");
			}
			lFirmaHsmBean.setPassword(password);
			lFirmaHsmBean.setAuthPIN(pin);
			lFirmaHsmBean.setKey(key);
			lFirmaHsmBean.setSecret(secret);
			// Parametri per eventuale firma Medas
			// lFirmaHsmBean.setCodiceOtp(codiceOtp);
			// lFirmaHsmBean.setCertId(certId);
			// lFirmaHsmBean.setPotereDiFirma(potereDiFirma);
			// lFirmaHsmBean.setParametriHSMFromGui(parametriHSMFromGui);
			lFirmaHsmBean.setProviderHsmFromPreference(provider);
			lFirmaHsmBean.setHsmType(tipoHsm);
			lFirmaHsmBean.setSkipControlloCoerenzaCertificatoFirma(true);									
			lFirmaHsmBean.setParametriHSMFromGui(true);
			hsmClient = HsmClientFactory.getHsmClient(session, lFirmaHsmBean);
		}
		
		// Queste cambiano a seconda del provider?
		SignOption signOption = new SignOption();
		signOption.setSigillo(true);
		signOption.setDetached(false);
		signOption.setEnveloping(false);

		byte[] fileFirmato = null;
		if (bytesFileDaFirmare != null) {
			try {
				// Verifico se devo aprire una sessione di firma e in caso la apro (serve anche per creare e gestire eventuali token di autenticazione)
				apriSessioneFirmaSeRichiesto(session, hsmClient);
				List<byte[]> fileDaFirmare = new ArrayList<byte[]>();
				fileDaFirmare.add(bytesFileDaFirmare);
				SignResponseBean response = hsmClient.firmaXades(fileDaFirmare, signOption);
				MessageBean message = response.getMessage();
				if ((message != null) && ((message.getStatus() != null) && (!message.getStatus().equals(ResponseStatus.OK)))) {
					log.error("Errore: - " + message.getCode() + " " + message.getDescription());
					throw new Exception("Errore: - " + message.getCode() + " " + message.getDescription()); 
				}
				List<FileResponseBean> listFileResponseBean = response.getFileResponseBean();
				if (listFileResponseBean != null && !listFileResponseBean.isEmpty()) {
					fileFirmato = listFileResponseBean.get(0).getFileFirmato();
				}
				// Verifico se devo chiudere la sessione di firma eventualemte aperta (serve anche per revocare eventuali token di autenticazione)
				chiudiSessioneFirmaSeRichiesto(session, hsmClient);

			} catch (HsmClientConfigException e) {
				log.error("Error: " + e.getLocalizedMessage(), e);
			} catch (HsmClientSignatureException e) {
				log.error("Error: " + e.getLocalizedMessage(), e);
			} catch (UnsupportedOperationException e) {
				log.error("Error: " + e.getLocalizedMessage(), e);
			}
		} else {
			log.error("File non specificato");
		}
		return fileFirmato;
    }

	public FirmaHsmBean firmaXades(FirmaHsmBean bean) throws StorageException {
		// try {
		// URL url = new URL(wsdlEndpoint);
		// QName qname = new QName(serviceNS, serviceName);
		// Service service = Service.create(url, qname);
		// ArubaSignService arubaService = service.getPort(ArubaSignService.class);
		// SOAPBinding binding = (SOAPBinding) ((BindingProvider) arubaService).getBinding();
		//
		// SignRequestV2 signRequest = new SignRequestV2();
		// signRequest.setCertID("AS0");
		//
		// signRequest.setTransport( TypeTransport.BYNARYNET );
		//
		// StorageService storageService = StorageImplementation.getStorage();
		// File fileDaFirmare = storageService.getRealFile(bean.getListaFileDaFirmare().get(0).getUri());
		// byte[] bytesFileDaFirmare = getFileBytes( fileDaFirmare );
		//
		// signRequest.setBinaryinput( bytesFileDaFirmare );
		// signRequest.setRequiredmark(false);
		//
		// Auth identity = new Auth();
		// identity.setUser( getUser() );
		// //identity.setUserPWD( userPassword );
		//
		// identity.setDelegatedUser( getDelegatedUser() );
		// identity.setDelegatedPassword( getDelegatedPassword() );
		// identity.setDelegatedDomain("engineering");
		//
		// //identity.setTypeHSM("COSIGN");
		// identity.setTypeOtpAuth("engineering");
		// identity.setOtpPwd("dsign");
		// signRequest.setIdentity( identity );
		//
		// XmlSignatureParameter parameter = new XmlSignatureParameter();
		// parameter.setCanonicalizedType( CanonicalizedType.ALGO_ID_C_14_N_11_WITH_COMMENTS);
		// parameter.setType( XmlSignatureType.XMLENVELOPED );
		// SignReturnV2 response = arubaService.xmlsignature(signRequest, parameter );
		//
		// if( response!=null ){
		// if( response.getStatus()!=null && response.getStatus().equalsIgnoreCase("KO") ){
		//
		// HashMap<String , String> error = new HashMap<String, String>();
		// error.put(bean.getListaFileDaFirmare().get(0).getRealName(), response.getDescription());
		// bean.setErrorMessages(error);
		//
		// }
		//
		// }
		//
		// } catch (TypeOfTransportNotImplemented_Exception e) {
		// } catch (MalformedURLException e) {
		// }
		return bean;
	}

	public FirmaHsmBean firmaXadesMultipla(FirmaHsmBean bean) throws StorageException {
		// try {
		// URL url = new URL(wsdlEndpoint);
		// QName qname = new QName(serviceNS, serviceName);
		// Service service = Service.create(url, qname);
		// ArubaSignService arubaService = service.getPort(ArubaSignService.class);
		// SOAPBinding binding = (SOAPBinding) ((BindingProvider) arubaService).getBinding();
		//
		// SignRequestV2 signRequest = new SignRequestV2();
		// signRequest.setCertID("AS0");
		//
		// signRequest.setTransport( TypeTransport.BYNARYNET );
		//
		// StorageService storageService = StorageImplementation.getStorage();
		//
		// signRequest.setRequiredmark(false);
		//
		// Auth identity = new Auth();
		// identity.setUser( getUser() );
		// //identity.setUserPWD( userPassword );
		//
		// identity.setDelegatedUser( getDelegatedUser() );
		// identity.setDelegatedPassword( getDelegatedPassword() );
		// identity.setDelegatedDomain("engineering");
		//
		// //identity.setTypeHSM("COSIGN");
		// identity.setTypeOtpAuth("engineering");
		// identity.setOtpPwd("dsign");
		// signRequest.setIdentity( identity );
		//
		// HashMap<String , String> error = new HashMap<String, String>();
		// List<String> fileFirmati = new ArrayList<String>();
		//
		// XmlSignatureParameter parameter = new XmlSignatureParameter();
		// parameter.setCanonicalizedType( CanonicalizedType.ALGO_ID_C_14_N_11_WITH_COMMENTS);
		// parameter.setType( XmlSignatureType.XMLENVELOPED );
		//
		// for (int i=0; i < bean.getListaFileDaFirmare().size(); i++ ) {
		//
		// String uriFile = bean.getListaFileDaFirmare().get(i).getUri();
		//
		// File fileDaFirmare = storageService.getRealFile(uriFile);
		// byte[] bytesFileDaFirmare = getFileBytes( fileDaFirmare );
		//
		// signRequest.setBinaryinput(bytesFileDaFirmare);
		//
		// SignReturnV2 response = arubaService.xmlsignature(signRequest, parameter );
		//
		// if( response!=null ){
		// if( response.getStatus()!=null && response.getStatus().equalsIgnoreCase("KO") )
		//
		// error.put(bean.getListaFileDaFirmare().get(i).getRealName(), response.getDescription());
		//
		// else
		// fileFirmati.add(bean.getListaFileDaFirmare().get(i).getRealName());
		//
		// }
		//
		// }
		//
		// bean.setErrorMessages(error);
		// bean.setFileFirmati(fileFirmati);
		//
		// }
		// catch (TypeOfTransportNotImplemented_Exception e) {
		// }
		// catch (MalformedURLException e) {
		// }
		return bean;
	}

}
