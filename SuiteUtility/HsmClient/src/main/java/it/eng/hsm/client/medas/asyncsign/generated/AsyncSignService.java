
package it.eng.hsm.client.medas.asyncsign.generated;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "AsyncSignService", targetNamespace = "http://www.medas-solutions.it/ScrybaSignServer/", wsdlLocation = "file:/C:/wsdlMedas/asyncsign%20V2.2.wsdl")
public class AsyncSignService
    extends Service
{

    private final static URL ASYNCSIGNSERVICE_WSDL_LOCATION;
    private final static WebServiceException ASYNCSIGNSERVICE_EXCEPTION;
    private final static QName ASYNCSIGNSERVICE_QNAME = new QName("http://www.medas-solutions.it/ScrybaSignServer/", "AsyncSignService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("file:/C:/wsdlMedas/asyncsign%20V2.2.wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        ASYNCSIGNSERVICE_WSDL_LOCATION = url;
        ASYNCSIGNSERVICE_EXCEPTION = e;
    }

    public AsyncSignService() {
        super(__getWsdlLocation(), ASYNCSIGNSERVICE_QNAME);
    }

    public AsyncSignService(WebServiceFeature... features) {
        super(__getWsdlLocation(), ASYNCSIGNSERVICE_QNAME, features);
    }

    public AsyncSignService(URL wsdlLocation) {
        super(wsdlLocation, ASYNCSIGNSERVICE_QNAME);
    }

    public AsyncSignService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, ASYNCSIGNSERVICE_QNAME, features);
    }

    public AsyncSignService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public AsyncSignService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns ScrybaSignServerAsync
     */
    @WebEndpoint(name = "AsyncSignServicePort")
    public ScrybaSignServerAsync getAsyncSignServicePort() {
        return super.getPort(new QName("http://www.medas-solutions.it/ScrybaSignServer/", "AsyncSignServicePort"), ScrybaSignServerAsync.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ScrybaSignServerAsync
     */
    @WebEndpoint(name = "AsyncSignServicePort")
    public ScrybaSignServerAsync getAsyncSignServicePort(WebServiceFeature... features) {
        return super.getPort(new QName("http://www.medas-solutions.it/ScrybaSignServer/", "AsyncSignServicePort"), ScrybaSignServerAsync.class, features);
    }

    private static URL __getWsdlLocation() {
        if (ASYNCSIGNSERVICE_EXCEPTION!= null) {
            throw ASYNCSIGNSERVICE_EXCEPTION;
        }
        return ASYNCSIGNSERVICE_WSDL_LOCATION;
    }

}
