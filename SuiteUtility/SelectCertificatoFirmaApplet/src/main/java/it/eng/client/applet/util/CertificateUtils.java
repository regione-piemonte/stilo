package it.eng.client.applet.util;

import it.eng.certverify.clientws.CertVerificationStatusType;
import it.eng.certverify.clientws.CertificateVerifierResponse;
import it.eng.client.applet.i18N.MessageKeys;
import it.eng.client.applet.i18N.Messages;
import it.eng.common.LogWriter;
import it.eng.common.bean.ValidationInfos;

import java.security.cert.X509Certificate;
import java.util.List;

import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;

public class CertificateUtils {

	public static boolean isVerificationOk(CertificateVerifierResponse vr, it.eng.certverify.clientws.VerificationTypes verification) throws Exception{
				
		boolean ret=false;
		List<it.eng.certverify.clientws.VerificationResultType> results=vr.getVerificationResult();
		for (it.eng.certverify.clientws.VerificationResultType verificationResultType : results) {
			//System.out.println("verificationResultType" + verificationResultType);
			if(verificationResultType.getVerificationID().equals(verification) && verificationResultType.getVerificationStatus().equals(CertVerificationStatusType.OK)){
				ret=true;
				break;
			}
		}
		return ret;
	}
	
	public static String getError( CertificateVerifierResponse checkReturn, it.eng.certverify.clientws.VerificationTypes verification) throws Exception{
				String ret=null;
		List<it.eng.certverify.clientws.VerificationResultType> results=checkReturn.getVerificationResult();
		for (it.eng.certverify.clientws.VerificationResultType verificationResultType : results) {
			if(verificationResultType.getVerificationID().equals(verification)){
				ret=verificationResultType.getErrorMessage();
				break;
			}
		}
		return ret;
	}
	
	public static  List<String> getWarnings(CertificateVerifierResponse checkReturn, it.eng.certverify.clientws.VerificationTypes verification) throws Exception{
		List<String> ret=null;
		List<it.eng.certverify.clientws.VerificationResultType> results=checkReturn.getVerificationResult();
		for (it.eng.certverify.clientws.VerificationResultType verificationResultType : results) {
			if(verificationResultType.getVerificationID().equals(verification)){
				ret=verificationResultType.getWarnings().getWarnMesage();
				break;
			}
		}
		return ret;
	}
	
	public static ValidationInfos checkCertificate(X509Certificate certificate ) throws Exception{
		LogWriter.writeLog("Inizio verifica sul certificato " + certificate );
		
		if( certificate!=null ){
			ValidationInfos ret = new ValidationInfos();
			
			String cognomeNomeCertificato = PreferenceManager.getString( PreferenceKeys.PROPERTY_COGNOMENOME_CERTIFICATO );
			if( cognomeNomeCertificato!=null ){
				X500Name x500name = new JcaX509CertificateHolder(certificate).getSubject();
				if(x500name!=null ){
					RDN[] cns = x500name.getRDNs( BCStyle.CN );
					if( cns!=null && cns.length>0){
						RDN cn = cns[0];
						if( cn!=null && cn.getFirst()!=null && cn.getFirst().getValue()!=null 
								&& !IETFUtils.valueToString(cn.getFirst().getValue()).equalsIgnoreCase( cognomeNomeCertificato ) ){
							ret.addError(Messages.getMessage(MessageKeys.MSG_FIRMAMARCA_ERROR_INVALIDSIGNINGCERTNAME, IETFUtils.valueToString(cn.getFirst().getValue())));
							return ret;
						}
					}
				}
			}
		
			String codiceFiscaleCertificato = PreferenceManager.getString( PreferenceKeys.PROPERTY_CODICEFISCALE_CERTIFICATO );
			if( codiceFiscaleCertificato!=null ){
				X500Name x500name = new JcaX509CertificateHolder(certificate).getSubject();
				if(x500name!=null ){
					RDN[] serialNumbers = x500name.getRDNs( BCStyle.SERIALNUMBER );
					if( serialNumbers!=null && serialNumbers.length>0){
						RDN serialNumberRDN = serialNumbers[0];
						if( serialNumberRDN!=null && serialNumberRDN.getFirst()!=null && serialNumberRDN.getFirst().getValue()!=null){ 
							String serialNumber = IETFUtils.valueToString(serialNumberRDN.getFirst().getValue() );
							if( serialNumber!=null){	
								serialNumber = serialNumber.substring( serialNumber.indexOf(":"), serialNumber.length() );
								if( !serialNumber.equalsIgnoreCase( codiceFiscaleCertificato ) ){
									ret.addError(Messages.getMessage(MessageKeys.MSG_FIRMAMARCA_ERROR_INVALIDSIGNINGCERTCF, serialNumber ) );
									return ret;
								}
							}
						}
					}
				}
			}
		
			boolean caCheckCertificate = PreferenceManager.getBoolean( PreferenceKeys.PROPERTY_SIGN_CACHECKCERTIFICATEENABLED );
			boolean crlCheckCertificate = PreferenceManager.getBoolean( PreferenceKeys.PROPERTY_SIGN_CRLCHECKCERTIFICATEENABLED );
			if((caCheckCertificate || crlCheckCertificate) ){
				ret = WSClientUtils.checkCertificate(certificate, caCheckCertificate, crlCheckCertificate);
			}
			return ret;
		}
		
		return null;
	}
}
