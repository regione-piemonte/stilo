/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import it.eng.auriga.ui.module.layout.server.firmaHsm.bean.FirmaHsmBean;
import it.eng.auriga.ui.module.layout.server.firmaXades.bean.FirmaXadesBean;
import it.eng.hsm.HsmBaseUtility;
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
	
	public byte[] sigilloXades(byte[] bytesFileDaFirmare, FirmaXadesBean FirmaXadesBean) throws Exception {
		HsmType hsmType = HsmType.INFOCERT;

		SignOption signOption = new SignOption();
		signOption.setSigillo(true);
		signOption.setDetached(false);
		signOption.setEnveloping(false);
		HsmConfig hsmConfig = new HsmConfig();
		hsmConfig.setHsmType(hsmType);

		if (hsmType.equals(HsmType.INFOCERT)) {
			
			RestConfig restConfig = new RestConfig();
			restConfig.setUrlEndpoint(FirmaXadesBean.getEndpoint());
			InfoCertConfig infoCertConfig = new InfoCertConfig();
			infoCertConfig.setAuto(false);
			infoCertConfig.setAlias(FirmaXadesBean.getAlias());
			infoCertConfig.setPin(FirmaXadesBean.getPin());
			infoCertConfig.setOtp(FirmaXadesBean.getOtp());
			infoCertConfig.setRestConfig(restConfig);

			hsmConfig.setClientConfig(infoCertConfig);
		}

		Hsm hsm = HsmImpl.getNewInstance(hsmConfig);
		byte[] fileFirmato = null;
		if (bytesFileDaFirmare != null) {
			try {
				List<byte[]> fileDaFirmare = new ArrayList<byte[]>();
				fileDaFirmare.add(bytesFileDaFirmare);
				SignResponseBean response = hsm.firmaXades(fileDaFirmare, signOption);
				MessageBean message = response.getMessage();
				if ((message != null) && ((message.getStatus() != null) && (!message.getStatus().equals(ResponseStatus.OK)))) {
					log.error("Errore: - " + message.getCode() + " " + message.getDescription());
					throw new Exception("Errore: - " + message.getCode() + " " + message.getDescription()); 
				}
				List<FileResponseBean> listFileResponseBean = response.getFileResponseBean();
				if (listFileResponseBean != null && !listFileResponseBean.isEmpty()) {
					fileFirmato = listFileResponseBean.get(0).getFileFirmato();
				}

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
