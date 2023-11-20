/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
 * AlboPretorioLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.eng.albopretorio.ws;

public class AlboPretorioLocator extends org.apache.axis.client.Service implements it.eng.albopretorio.ws.AlboPretorio {

    public AlboPretorioLocator() {
    }


    public AlboPretorioLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public AlboPretorioLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for AlboPretorioSoap12
    private java.lang.String AlboPretorioSoap12_address = "http://infraced185.ced.comune.milano.local/AlboPretorioProtocolloWS/AlboPretorio.asmx";

    public java.lang.String getAlboPretorioSoap12Address() {
        return AlboPretorioSoap12_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String AlboPretorioSoap12WSDDServiceName = "AlboPretorioSoap12";

    public java.lang.String getAlboPretorioSoap12WSDDServiceName() {
        return AlboPretorioSoap12WSDDServiceName;
    }

    public void setAlboPretorioSoap12WSDDServiceName(java.lang.String name) {
        AlboPretorioSoap12WSDDServiceName = name;
    }

    public it.eng.albopretorio.ws.AlboPretorioSoap_PortType getAlboPretorioSoap12() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(AlboPretorioSoap12_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getAlboPretorioSoap12(endpoint);
    }

    public it.eng.albopretorio.ws.AlboPretorioSoap_PortType getAlboPretorioSoap12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            it.eng.albopretorio.ws.AlboPretorioSoap12Stub _stub = new it.eng.albopretorio.ws.AlboPretorioSoap12Stub(portAddress, this);
            _stub.setPortName(getAlboPretorioSoap12WSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setAlboPretorioSoap12EndpointAddress(java.lang.String address) {
        AlboPretorioSoap12_address = address;
    }


    // Use to get a proxy class for AlboPretorioSoap
    private java.lang.String AlboPretorioSoap_address = "http://infraced185.ced.comune.milano.local/AlboPretorioProtocolloWS/AlboPretorio.asmx";

    public java.lang.String getAlboPretorioSoapAddress() {
        return AlboPretorioSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String AlboPretorioSoapWSDDServiceName = "AlboPretorioSoap";

    public java.lang.String getAlboPretorioSoapWSDDServiceName() {
        return AlboPretorioSoapWSDDServiceName;
    }

    public void setAlboPretorioSoapWSDDServiceName(java.lang.String name) {
        AlboPretorioSoapWSDDServiceName = name;
    }

    public it.eng.albopretorio.ws.AlboPretorioSoap_PortType getAlboPretorioSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(AlboPretorioSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getAlboPretorioSoap(endpoint);
    }

    public it.eng.albopretorio.ws.AlboPretorioSoap_PortType getAlboPretorioSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            it.eng.albopretorio.ws.AlboPretorioSoap_BindingStub _stub = new it.eng.albopretorio.ws.AlboPretorioSoap_BindingStub(portAddress, this);
            _stub.setPortName(getAlboPretorioSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setAlboPretorioSoapEndpointAddress(java.lang.String address) {
        AlboPretorioSoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     * This service has multiple ports for a given interface;
     * the proxy implementation returned may be indeterminate.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (it.eng.albopretorio.ws.AlboPretorioSoap_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                it.eng.albopretorio.ws.AlboPretorioSoap12Stub _stub = new it.eng.albopretorio.ws.AlboPretorioSoap12Stub(new java.net.URL(AlboPretorioSoap12_address), this);
                _stub.setPortName(getAlboPretorioSoap12WSDDServiceName());
                return _stub;
            }
            if (it.eng.albopretorio.ws.AlboPretorioSoap_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                it.eng.albopretorio.ws.AlboPretorioSoap_BindingStub _stub = new it.eng.albopretorio.ws.AlboPretorioSoap_BindingStub(new java.net.URL(AlboPretorioSoap_address), this);
                _stub.setPortName(getAlboPretorioSoapWSDDServiceName());
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
        if ("AlboPretorioSoap12".equals(inputPortName)) {
            return getAlboPretorioSoap12();
        }
        else if ("AlboPretorioSoap".equals(inputPortName)) {
            return getAlboPretorioSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://it.intersail/wse", "AlboPretorio");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://it.intersail/wse", "AlboPretorioSoap12"));
            ports.add(new javax.xml.namespace.QName("http://it.intersail/wse", "AlboPretorioSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("AlboPretorioSoap12".equals(portName)) {
            setAlboPretorioSoap12EndpointAddress(address);
        }
        else 
if ("AlboPretorioSoap".equals(portName)) {
            setAlboPretorioSoapEndpointAddress(address);
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
