/**
 * CertificateRevocationServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.eng.utility.cryptosigner.controller.impl.ws.service;

public class CertificateRevocationServiceServiceLocator extends org.apache.axis.client.Service implements it.eng.utility.cryptosigner.controller.impl.ws.service.CertificateRevocationServiceService {

    public CertificateRevocationServiceServiceLocator() {
    }


    public CertificateRevocationServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public CertificateRevocationServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for CertificateRevocationService
    private java.lang.String CertificateRevocationService_address = "http://localhost:9080/cryptoSignerServices/services/CertificateRevocationService";

    public java.lang.String getCertificateRevocationServiceAddress() {
        return CertificateRevocationService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String CertificateRevocationServiceWSDDServiceName = "CertificateRevocationService";

    public java.lang.String getCertificateRevocationServiceWSDDServiceName() {
        return CertificateRevocationServiceWSDDServiceName;
    }

    public void setCertificateRevocationServiceWSDDServiceName(java.lang.String name) {
        CertificateRevocationServiceWSDDServiceName = name;
    }

    public it.eng.utility.cryptosigner.controller.impl.ws.service.CertificateRevocationService getCertificateRevocationService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(CertificateRevocationService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getCertificateRevocationService(endpoint);
    }

    public it.eng.utility.cryptosigner.controller.impl.ws.service.CertificateRevocationService getCertificateRevocationService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            it.eng.utility.cryptosigner.controller.impl.ws.service.CertificateRevocationServiceSoapBindingStub _stub = new it.eng.utility.cryptosigner.controller.impl.ws.service.CertificateRevocationServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getCertificateRevocationServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setCertificateRevocationServiceEndpointAddress(java.lang.String address) {
        CertificateRevocationService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (it.eng.utility.cryptosigner.controller.impl.ws.service.CertificateRevocationService.class.isAssignableFrom(serviceEndpointInterface)) {
                it.eng.utility.cryptosigner.controller.impl.ws.service.CertificateRevocationServiceSoapBindingStub _stub = new it.eng.utility.cryptosigner.controller.impl.ws.service.CertificateRevocationServiceSoapBindingStub(new java.net.URL(CertificateRevocationService_address), this);
                _stub.setPortName(getCertificateRevocationServiceWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("CertificateRevocationService".equals(inputPortName)) {
            return getCertificateRevocationService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://service.ws.impl.controller.crypto.eng.it", "CertificateRevocationServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://service.ws.impl.controller.crypto.eng.it", "CertificateRevocationService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("CertificateRevocationService".equals(portName)) {
            setCertificateRevocationServiceEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
