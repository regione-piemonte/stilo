/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.certverify.clientws.CertVerificationStatusType;
import it.eng.certverify.clientws.VerificationResultType;
import it.eng.certverify.clientws.VerificationResults;
import it.eng.certverify.clientws.VerificationTypes;

import java.util.List;

public class CertResponseUtils {

	public static boolean isVerificationOk(VerificationResults vr,VerificationTypes verification){
		boolean ret=false;
		if(vr!=null){
			List<VerificationResultType> results=vr.getVerificationResult();
			for (VerificationResultType verificationResultType : results) {
				if(verificationResultType.getVerificationID().equals(verification) && verificationResultType.getVerificationStatus().equals(CertVerificationStatusType.OK)){
					ret=true;
					break;
				}
			}
		}
		return ret;
	}
	
	public static String getError(VerificationResults vr,VerificationTypes verification){
		String ret=null;
		List<VerificationResultType> results=vr.getVerificationResult();
		for (VerificationResultType verificationResultType : results) {
			if(verificationResultType.getVerificationID().equals(verification)){
				ret=verificationResultType.getErrorMessage();
				break;
			}
		}
		return ret;
	}
	
	public static  List<String> getWarnings(VerificationResults vr,VerificationTypes verification){
		List<String> ret=null;
		List<VerificationResultType> results=vr.getVerificationResult();
		for (VerificationResultType verificationResultType : results) {
			if(verificationResultType.getVerificationID().equals(verification)){
				ret=verificationResultType.getWarnings().getWarnMesage();
				break;
			}
		}
		return ret;
	}
}
