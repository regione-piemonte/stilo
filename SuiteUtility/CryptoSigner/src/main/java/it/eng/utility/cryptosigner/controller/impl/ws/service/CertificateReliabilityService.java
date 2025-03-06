/**
 * CertificateReliabilityService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.eng.utility.cryptosigner.controller.impl.ws.service;

public interface CertificateReliabilityService extends java.rmi.Remote {
    public byte[] checkReliableCertificate(byte[] principal, java.util.Calendar referenceDate) throws java.rmi.RemoteException, it.eng.utility.cryptosigner.exception.WSCryptoSignerException;
}
