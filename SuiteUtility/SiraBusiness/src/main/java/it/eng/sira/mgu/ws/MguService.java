/**
 * MguService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.eng.sira.mgu.ws;

public interface MguService extends javax.xml.rpc.Service {
    public java.lang.String getMguServiceEndPointPortAddress();

    public it.eng.sira.mgu.ws.MguServiceEndPoint getMguServiceEndPointPort() throws javax.xml.rpc.ServiceException;

    public it.eng.sira.mgu.ws.MguServiceEndPoint getMguServiceEndPointPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
