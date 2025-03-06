/**
 * CertificateRevocationService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.eng.utility.cryptosigner.controller.impl.ws.service;

import java.util.Calendar;

public interface CertificateRevocationService extends java.rmi.Remote {
    public it.eng.utility.cryptosigner.controller.bean.ValidationInfos checkCertificateRevocationStatus(byte[] certificate, Calendar referenceCal) throws java.rmi.RemoteException, it.eng.utility.cryptosigner.exception.WSCryptoSignerException;
    public it.eng.utility.cryptosigner.controller.bean.ValidationInfos checkCertificateAndIssuerRevocationStatus(byte[] certificate, byte[] issuerCertificate, Calendar referenceCal) throws java.rmi.RemoteException, it.eng.utility.cryptosigner.exception.WSCryptoSignerException;
}
