package it.eng.client.applet.util;

import it.eng.certverify.clientws.CertificateVerifier;
import it.eng.certverify.clientws.CertificateVerifierRequest;
import it.eng.certverify.clientws.CertificateVerifierResponse;
import it.eng.certverify.clientws.VerificationInfo;
import it.eng.certverify.clientws.VerificationTypes;
import it.eng.common.LogWriter;
import it.eng.common.bean.ValidationInfos;

import java.net.URL;
import java.security.cert.X509Certificate;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;


public class WSClientUtils {

	public static ValidationInfos checkCertificate(X509Certificate certificate,boolean caCheck,boolean crlCheck) throws Exception {
		
		LogWriter.writeLog("Metodo di verifica del certificato");
		String wsEndpoint = null;
		String namespace = null;
		String serviceName = null;
		try {
			wsEndpoint = PreferenceManager.getString( PreferenceKeys.PROPERTY_VERIFYCERT_WSENDPOINT );
			LogWriter.writeLog( "Proprietà " + PreferenceKeys.PROPERTY_VERIFYCERT_WSENDPOINT + ": " + wsEndpoint );
			namespace = PreferenceManager.getString( PreferenceKeys.PROPERTY_VERIFYCERT_NAMESPACE );
			LogWriter.writeLog( "Proprietà " + PreferenceKeys.PROPERTY_VERIFYCERT_NAMESPACE + ": " + namespace );
			serviceName = PreferenceManager.getString( PreferenceKeys.PROPERTY_VERIFYCERT_SERVICE );
			LogWriter.writeLog( "Proprietà " + PreferenceKeys.PROPERTY_VERIFYCERT_SERVICE + ": " + serviceName );
		} catch (Exception e) {
			LogWriter.writeLog("Errore nel recupero delle proprietà di configurazione del servizio di verifica certificato", e );
			throw new Exception("Parametri non configurati");
		}
		
		ValidationInfos ret = new ValidationInfos();
		if( wsEndpoint!=null && namespace!=null && serviceName!=null ){
			try{
				LogWriter.writeLog("Controllo ca richiesto? " + caCheck );
				LogWriter.writeLog("Controllo crl richiesto? " + crlCheck );
				if( caCheck || crlCheck ){
					//effettua chiamata
					URL url = new URL( wsEndpoint );
					QName qname = new QName( namespace, serviceName );
	
					Service service = Service.create(url, qname);
					CertificateVerifier verifier = service.getPort(CertificateVerifier.class);
					CertificateVerifierRequest request= new CertificateVerifierRequest();
					request.setX509Cert(certificate.getEncoded() );
					VerificationInfo vinfo= new VerificationInfo();
					vinfo.setVerificationID(VerificationTypes.CERTIFICATE_EXPIRATION);
					request.getVerificationInfo().add(vinfo);
					if(caCheck){
						VerificationInfo vinfoca= new VerificationInfo();
						vinfoca.setVerificationID(VerificationTypes.CA_VERIFY);
						request.getVerificationInfo().add(vinfoca);
					}
					if(crlCheck){
						VerificationInfo vinfocrl= new VerificationInfo();
						vinfocrl.setVerificationID(VerificationTypes.CRL_VERIFY);
						request.getVerificationInfo().add(vinfocrl);
					}
					CertificateVerifierResponse result = verifier.check( request );
					
					LogWriter.writeLog("esito verifica " + CertificateUtils.isVerificationOk(result , VerificationTypes.CERTIFICATE_EXPIRATION ) );
					if(!CertificateUtils.isVerificationOk( result, VerificationTypes.CERTIFICATE_EXPIRATION)){
						String error=CertificateUtils.getError(result, VerificationTypes.CERTIFICATE_EXPIRATION );
						if(error!=null){
							ret.addError(error);
						} 
					}
					if(caCheck){
						LogWriter.writeLog("esito verifica ca " + CertificateUtils.isVerificationOk( result, VerificationTypes.CA_VERIFY ) );
						if(!CertificateUtils.isVerificationOk(result, VerificationTypes.CA_VERIFY)){
							String error=CertificateUtils.getError(result, VerificationTypes.CA_VERIFY);
							if(error!=null){
								ret.addError(error);
							} 
						}
					}
					if(crlCheck){
						LogWriter.writeLog("esito verifica crl " + CertificateUtils.isVerificationOk( result, VerificationTypes.CRL_VERIFY ) );
						if(!CertificateUtils.isVerificationOk(result, VerificationTypes.CRL_VERIFY)){
							String error=CertificateUtils.getError(result, VerificationTypes.CRL_VERIFY);
							if(error!=null){
								ret.addError(error);
							}
						}
					}
				}
			} catch (Exception e){
				LogWriter.writeLog("Errore", e);
			}
		} else {
			throw new Exception("Parametri non configurati");
		}
		return ret;
	}
	
	
	
}
