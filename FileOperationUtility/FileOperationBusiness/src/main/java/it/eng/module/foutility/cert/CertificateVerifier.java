/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.module.foutility.beans.generated.VerificationRequest;
import it.eng.module.foutility.beans.generated.VerificationResults;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.jws.soap.SOAPBinding.Style;

@WebService(targetNamespace="verify.cryptoutil.eng.it")
@SOAPBinding(style = Style.DOCUMENT, parameterStyle = ParameterStyle.BARE)
public interface CertificateVerifier {
	//se vuoi cambiare i nomi dei param devi aggiungre le annoation relative
	// @WebResult(name="VerificationResults")
	//public VerificationResults check(@WebParam(name="input")VerificationRequest input);
	@WebResult(name = "CertificateVerifierResponse", targetNamespace = "verify.cryptoutil.eng.it") 
	public VerificationResults check( @WebParam(name = "certificateVerifierRequest",partName="certificateVerifierRequest", 
            targetNamespace = "verify.cryptoutil.eng.it")VerificationRequest verificationRequest);
}
