package it.eng.utility.cryptosigner.utils;

import java.security.cert.X509CRL;
import java.security.cert.X509CRLEntry;
import java.security.cert.X509Certificate;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.eng.utility.cryptosigner.controller.impl.cert.CertMessage;

/**
 * Utilita' per le crl
 *
 */
public class CrlUtility {
	
	static Logger log = LogManager.getLogger(CrlUtility.class);
	
	public static boolean checkCRL( X509Certificate certificate, X509CRL crl, Date date) {
		X509CRLEntry crlEntry = crl.getRevokedCertificate(certificate);

		// il certificato e stato revocato
		if (crlEntry != null) {
			if (date != null && crlEntry.getRevocationDate().before(date)) {
				log.debug(MessageHelper.getMessage(CertMessage.CERT_CA_CRL_REVOKED_BEFORE, crlEntry.getRevocationDate(), date));
				return false;
			} else if (date == null) {
				log.debug(MessageHelper.getMessage(CertMessage.CERT_CA_CRL_REVOKED_AFTER, crlEntry.getRevocationDate()));
				return false;
			}
		} else {
			//log.debug("certificato non presente nella CRL");
		}
		return true;
	}
	
	public static Date getDataRevoca(X509Certificate certificate, X509CRL crl){
		X509CRLEntry crlEntry = crl.getRevokedCertificate(certificate);

		Date dataRevoca = null;
		// il certificato e stato revocato
		if (crlEntry != null) {
			dataRevoca = crlEntry.getRevocationDate();
		}
		return dataRevoca;
	}
	
}
