
package com.opentext.livelink.service.memberservice;

import java.net.URL;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.10
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "MemberService", targetNamespace = "urn:MemberService.service.livelink.opentext.com")
public class MemberService_Service
    extends Service
{

    private final static QName MEMBERSERVICE_QNAME = new QName("urn:MemberService.service.livelink.opentext.com", "MemberService");

    public MemberService_Service(URL wsdlLocation) {
        super(wsdlLocation, MEMBERSERVICE_QNAME);
    }

    public MemberService_Service(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, MEMBERSERVICE_QNAME, features);
    }

    public MemberService_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public MemberService_Service(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns MemberService
     */
    @WebEndpoint(name = "BasicHttpBinding_MemberService")
    public MemberService getBasicHttpBindingMemberService(String endpoint) {
    	MemberService port = super.getPort(new QName("urn:MemberService.service.livelink.opentext.com", "BasicHttpBinding_MemberService"), MemberService.class);
    	Map<String, Object> requestContext = ((BindingProvider) port).getRequestContext();
    	requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpoint);
    	return port;
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns MemberService
     */
    @WebEndpoint(name = "BasicHttpBinding_MemberService")
    public MemberService getBasicHttpBindingMemberService(String endpoint,WebServiceFeature... features) {
    	MemberService port = super.getPort(new QName("urn:MemberService.service.livelink.opentext.com", "BasicHttpBinding_MemberService"), MemberService.class, features);
    	Map<String, Object> requestContext = ((BindingProvider) port).getRequestContext();
    	requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpoint);
    	return port;
    }

}
