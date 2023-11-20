/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.module.foutility.beans.generated.VerificationResultType;
import it.eng.module.foutility.beans.generated.VerificationResults;
import it.eng.module.foutility.beans.generated.VerificationStatusType;
import it.eng.module.foutility.beans.generated.VerificationTypes;

import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

public class CertResponseUtils {

	public static boolean isVerificationOk(VerificationResults vr,VerificationTypes verification){
		boolean ret=false;
		List<VerificationResultType> results=vr.getVerificationResult();
		for (VerificationResultType verificationResultType : results) {
			if(verificationResultType.getVerificationID().equals(verification) && verificationResultType.getVerificationStatus().equals(VerificationStatusType.OK)){
				ret=true;
				break;
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
	
	@Deprecated
	public static void printResult(VerificationResults vres){
		try{ 
			JAXBContext jc = JAXBContext.newInstance( vres.getClass());
			Marshaller m = jc.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m.marshal(vres, System.out);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
