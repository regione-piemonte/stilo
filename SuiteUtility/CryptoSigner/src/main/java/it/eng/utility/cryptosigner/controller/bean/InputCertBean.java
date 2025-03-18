/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.security.cert.X509Certificate;
import java.util.Date;

/**
 * bean di input per analizeffetuare controlli su un certificato x509
 * @author Russo
 *
 */
public class InputCertBean extends InputBean{
	 //certificato di input da controllare
	 X509Certificate certificate = null;
	 Date				referenceDate;
 
	 
	public X509Certificate getCertificate() {
		return certificate;
	}
	public void setCertificate(X509Certificate certificate) {
		this.certificate = certificate;
	}
	public Date getReferenceDate() {
		return referenceDate;
	}
	public void setReferenceDate(Date referenceDate) {
		this.referenceDate = referenceDate;
	}
	 
	 
}
