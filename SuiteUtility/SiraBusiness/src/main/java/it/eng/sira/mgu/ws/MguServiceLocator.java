/**
 * MguServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.eng.sira.mgu.ws;

public class MguServiceLocator extends org.apache.axis.client.Service implements it.eng.sira.mgu.ws.MguService {

    public MguServiceLocator() {
    }


    public MguServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public MguServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for MguServiceEndPointPort
    private java.lang.String MguServiceEndPointPort_address = "http://siranet.sardegnaambiente.it/mgu-ws/MguService";

    public java.lang.String getMguServiceEndPointPortAddress() {
        return MguServiceEndPointPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String MguServiceEndPointPortWSDDServiceName = "MguServiceEndPointPort";

    public java.lang.String getMguServiceEndPointPortWSDDServiceName() {
        return MguServiceEndPointPortWSDDServiceName;
    }

    public void setMguServiceEndPointPortWSDDServiceName(java.lang.String name) {
        MguServiceEndPointPortWSDDServiceName = name;
    }

    public it.eng.sira.mgu.ws.MguServiceEndPoint getMguServiceEndPointPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(MguServiceEndPointPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getMguServiceEndPointPort(endpoint);
    }

    public it.eng.sira.mgu.ws.MguServiceEndPoint getMguServiceEndPointPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            it.eng.sira.mgu.ws.MguServiceSoapBindingStub _stub = new it.eng.sira.mgu.ws.MguServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getMguServiceEndPointPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setMguServiceEndPointPortEndpointAddress(java.lang.String address) {
        MguServiceEndPointPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (it.eng.sira.mgu.ws.MguServiceEndPoint.class.isAssignableFrom(serviceEndpointInterface)) {
                it.eng.sira.mgu.ws.MguServiceSoapBindingStub _stub = new it.eng.sira.mgu.ws.MguServiceSoapBindingStub(new java.net.URL(MguServiceEndPointPort_address), this);
                _stub.setPortName(getMguServiceEndPointPortWSDDServiceName());
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
        if ("MguServiceEndPointPort".equals(inputPortName)) {
            return getMguServiceEndPointPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://ws.mgu.sira.eng.it/", "MguService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://ws.mgu.sira.eng.it/", "MguServiceEndPointPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("MguServiceEndPointPort".equals(portName)) {
            setMguServiceEndPointPortEndpointAddress(address);
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
