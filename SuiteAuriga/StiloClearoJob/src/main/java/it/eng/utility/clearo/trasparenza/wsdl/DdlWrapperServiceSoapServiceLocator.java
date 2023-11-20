/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
 * DdlWrapperServiceSoapServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.eng.utility.clearo.trasparenza.wsdl;

public class DdlWrapperServiceSoapServiceLocator extends org.apache.axis.client.Service implements it.eng.utility.clearo.trasparenza.wsdl.DdlWrapperServiceSoapService {

    public DdlWrapperServiceSoapServiceLocator() {
    }


    public DdlWrapperServiceSoapServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public DdlWrapperServiceSoapServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for Plugin_ddlWrapper_ddlWrapperService
    private java.lang.String Plugin_ddlWrapper_ddlWrapperService_address = "http://tst-api.trasparenza.odsp.csi.it/DDLWSHook-hook/api/secure/axis/Plugin_ddlWrapper_ddlWrapperService";

    public java.lang.String getPlugin_ddlWrapper_ddlWrapperServiceAddress() {
        return Plugin_ddlWrapper_ddlWrapperService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String Plugin_ddlWrapper_ddlWrapperServiceWSDDServiceName = "Plugin_ddlWrapper_ddlWrapperService";

    public java.lang.String getPlugin_ddlWrapper_ddlWrapperServiceWSDDServiceName() {
        return Plugin_ddlWrapper_ddlWrapperServiceWSDDServiceName;
    }

    public void setPlugin_ddlWrapper_ddlWrapperServiceWSDDServiceName(java.lang.String name) {
        Plugin_ddlWrapper_ddlWrapperServiceWSDDServiceName = name;
    }

    public it.eng.utility.clearo.trasparenza.wsdl.DdlWrapperServiceSoap getPlugin_ddlWrapper_ddlWrapperService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(Plugin_ddlWrapper_ddlWrapperService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getPlugin_ddlWrapper_ddlWrapperService(endpoint);
    }

    public it.eng.utility.clearo.trasparenza.wsdl.DdlWrapperServiceSoap getPlugin_ddlWrapper_ddlWrapperService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            it.eng.utility.clearo.trasparenza.wsdl.Plugin_ddlWrapper_ddlWrapperServiceSoapBindingStub _stub = new it.eng.utility.clearo.trasparenza.wsdl.Plugin_ddlWrapper_ddlWrapperServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getPlugin_ddlWrapper_ddlWrapperServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setPlugin_ddlWrapper_ddlWrapperServiceEndpointAddress(java.lang.String address) {
        Plugin_ddlWrapper_ddlWrapperService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (it.eng.utility.clearo.trasparenza.wsdl.DdlWrapperServiceSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                it.eng.utility.clearo.trasparenza.wsdl.Plugin_ddlWrapper_ddlWrapperServiceSoapBindingStub _stub = new it.eng.utility.clearo.trasparenza.wsdl.Plugin_ddlWrapper_ddlWrapperServiceSoapBindingStub(new java.net.URL(Plugin_ddlWrapper_ddlWrapperService_address), this);
                _stub.setPortName(getPlugin_ddlWrapper_ddlWrapperServiceWSDDServiceName());
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
        if ("Plugin_ddlWrapper_ddlWrapperService".equals(inputPortName)) {
            return getPlugin_ddlWrapper_ddlWrapperService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("urn:http.service.wrapper.ddlhook.energee3.csi.it", "ddlWrapperServiceSoapService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("urn:http.service.wrapper.ddlhook.energee3.csi.it", "Plugin_ddlWrapper_ddlWrapperService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("Plugin_ddlWrapper_ddlWrapperService".equals(portName)) {
            setPlugin_ddlWrapper_ddlWrapperServiceEndpointAddress(address);
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
