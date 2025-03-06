/**
 * CertificateReliabilityServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.eng.utility.cryptosigner.controller.impl.ws.service;

public class CertificateReliabilityServiceServiceLocator extends org.apache.axis.client.Service implements it.eng.utility.cryptosigner.controller.impl.ws.service.CertificateReliabilityServiceService {

    public CertificateReliabilityServiceServiceLocator() {
    }


    public CertificateReliabilityServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public CertificateReliabilityServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for CertificateReliabilityService
    private java.lang.String CertificateReliabilityService_address = "http://localhost:9080/cryptoSignerServices/services/CertificateReliabilityService";

    public java.lang.String getCertificateReliabilityServiceAddress() {
        return CertificateReliabilityService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String CertificateReliabilityServiceWSDDServiceName = "CertificateReliabilityService";

    public java.lang.String getCertificateReliabilityServiceWSDDServiceName() {
        return CertificateReliabilityServiceWSDDServiceName;
    }

    public void setCertificateReliabilityServiceWSDDServiceName(java.lang.String name) {
        CertificateReliabilityServiceWSDDServiceName = name;
    }

    public it.eng.utility.cryptosigner.controller.impl.ws.service.CertificateReliabilityService getCertificateReliabilityService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(CertificateReliabilityService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getCertificateReliabilityService(endpoint);
    }

    public it.eng.utility.cryptosigner.controller.impl.ws.service.CertificateReliabilityService getCertificateReliabilityService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            it.eng.utility.cryptosigner.controller.impl.ws.service.CertificateReliabilityServiceSoapBindingStub _stub = new it.eng.utility.cryptosigner.controller.impl.ws.service.CertificateReliabilityServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getCertificateReliabilityServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setCertificateReliabilityServiceEndpointAddress(java.lang.String address) {
        CertificateReliabilityService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (it.eng.utility.cryptosigner.controller.impl.ws.service.CertificateReliabilityService.class.isAssignableFrom(serviceEndpointInterface)) {
                it.eng.utility.cryptosigner.controller.impl.ws.service.CertificateReliabilityServiceSoapBindingStub _stub = new it.eng.utility.cryptosigner.controller.impl.ws.service.CertificateReliabilityServiceSoapBindingStub(new java.net.URL(CertificateReliabilityService_address), this);
                _stub.setPortName(getCertificateReliabilityServiceWSDDServiceName());
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
        if ("CertificateReliabilityService".equals(inputPortName)) {
            return getCertificateReliabilityService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://service.ws.impl.controller.crypto.eng.it", "CertificateReliabilityServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://service.ws.impl.controller.crypto.eng.it", "CertificateReliabilityService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("CertificateReliabilityService".equals(portName)) {
            setCertificateReliabilityServiceEndpointAddress(address);
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
