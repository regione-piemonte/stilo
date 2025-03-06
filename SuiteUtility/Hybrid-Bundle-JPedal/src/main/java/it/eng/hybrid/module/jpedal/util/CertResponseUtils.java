package it.eng.hybrid.module.jpedal.util;

import it.eng.certverify.clientws.CertVerificationStatusType;
import it.eng.certverify.clientws.CertificateVerifierResponse;

import java.util.List;

public class CertResponseUtils {

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
		System.out.println("RET " + ret);
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
		System.out.println("RET " + ret);
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
}
