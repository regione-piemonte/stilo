package it.eng.utility.cryptosigner.controller.impl.cert;

import it.eng.utility.cryptosigner.utils.MessageConstants;

/**
 * messaggi relativi ai certificati
 * @author Russo
 *
 */
public interface CertMessage extends MessageConstants {
	String CERT_INPUTEMPTY="CERT.INPUTEMPTY";
	String CERT_EXPIRED="CERT.EXPIRED";
	String CERT_NOTFOUND="CERT.NOTFOUND";
	String CERT_NOTVALID="CERT.NOTVALID";
	String CERT_ASSOCIATION_ISSUER="CERT.ASSOCIATION_ISSUER";
	String CERT_NONREPUDIATION="CERT.NONREPUDIATION";
	String CERT_REVOKED_AFTER="CERT.REVOKED_AFTER";
	String CERT_REVOKED_BEFORE="CERT.REVOKED_BEFORE";
	String CERT_SERIALIZATION_ERROR="CERT.SERIALIZATION_ERROR";
	String CERT_NO_CERTIFICATE_WARNING="CERT.NO_CERTIFICATE_WARNING";
	
	// 
	String CERT_CRL_NOT_DOWNLOADABLE="CERT.CRL_NOT_DOWNLOADABLE";
	String CERT_CRL_NOT_FOUND="CERT.CRL_NOT_FOUND";
	String CERT_CRL_REVOKED_BEFORE="CERT.CRL_REVOKED_BEFORE";
	String CERT_CRL_REVOKED_AFTER="CERT.CRL_REVOKED_AFTER";
	String CERT_CRL_VALIDATION_DATE="CERT.CRL_VALIDATION_DATE";
	String CERT_CRL_ISSUER_CERTIFICATE="CERT.CRL_ISSUER_CERTIFICATE";
	String CERT_CRL_ISSUER_CERTIFICATE_VALIDATION="CERT.CRL_ISSUER_CERTIFICATE_VALIDATION";
	String CERT_CRL_WITHOUT_CERTIFICATE="CERT.CRL_WITHOUT_CERTIFICATE";
	String CERT_CRL_CERTIFICATE_EXPIRED="CERT.CRL_CERTIFICATE_EXPIRED";
	String CERT_CRL_VERIFICATION_ISSUER_CERTIFICATE_WARNING="CERT.CRL_VERIFICATION_ISSUER_CERTIFICATE_WARNING";
	String CERT_CRL_VALIDATION_WARNING="CERT.CRL_VALIDATION_WARNING";		
	String CERT_CRL_NO_ISSUER_CERTIFICATE_WARNING="CERT.CRL_NO_ISSUER_CERTIFICATE_WARNING";
	
	String CERT_CA_CRL_REVOKED_BEFORE="CERT.CA_CRL_REVOKED_BEFORE";
	String CERT_CA_CRL_REVOKED_AFTER="CERT.CA_CRL_REVOKED_AFTER";
	String CERT_CA_CRL_NOT_EXECUTED="CERT.CA_CRL_NOT_EXECUTED";
	String CERT_CA_ERROR_FROM_STORAGE="CERT.CA_ERROR_FROM_STORAGE";
	String CERT_CA_NOT_PRESENT="CERT.CA_NOT_PRESENT";
	String CERT_CA_EXPIRED="CERT.CA_EXPIRED";
	String CERT_CA_NOT_VALID_YET="CERT.CA_NOT_VALID_YET";
	String CERT_CA_REVOKED="CERT.CA_REVOKED";
	String CERT_UPDATE_LIST_CA_WARNING="CERT.UPDATE_LIST_CA_WARNING";	
}
