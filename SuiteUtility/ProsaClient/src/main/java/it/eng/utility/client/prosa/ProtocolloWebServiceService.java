/**
 * ProtocolloWebServiceService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 09, 2010 (01:02:43 CEST) WSDL2Java emitter.
 */

package it.eng.utility.client.prosa;

public interface ProtocolloWebServiceService extends javax.xml.rpc.Service {
    public java.lang.String getProtocolloWebServiceAddress();

    public it.eng.utility.client.prosa.ProtocolloWebService_PortType getProtocolloWebService() throws javax.xml.rpc.ServiceException;

    public it.eng.utility.client.prosa.ProtocolloWebService_PortType getProtocolloWebService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
