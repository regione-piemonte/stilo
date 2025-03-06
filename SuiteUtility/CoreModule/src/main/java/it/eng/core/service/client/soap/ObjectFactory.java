
package it.eng.core.service.client.soap;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.eng.core.service.client.soap package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Serviceoperationinvoke_QNAME = new QName("http://server.service.core.eng.it/", "serviceoperationinvoke");
    private final static QName _ServiceoperationinvokeResponse_QNAME = new QName("http://server.service.core.eng.it/", "serviceoperationinvokeResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.eng.core.service.client.soap
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ServiceoperationinvokeResponse }
     * 
     */
    public ServiceoperationinvokeResponse createServiceoperationinvokeResponse() {
        return new ServiceoperationinvokeResponse();
    }

    /**
     * Create an instance of {@link Serviceoperationinvoke }
     * 
     */
    public Serviceoperationinvoke createServiceoperationinvoke() {
        return new Serviceoperationinvoke();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Serviceoperationinvoke }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.service.core.eng.it/", name = "serviceoperationinvoke")
    public JAXBElement<Serviceoperationinvoke> createServiceoperationinvoke(Serviceoperationinvoke value) {
        return new JAXBElement<Serviceoperationinvoke>(_Serviceoperationinvoke_QNAME, Serviceoperationinvoke.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ServiceoperationinvokeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.service.core.eng.it/", name = "serviceoperationinvokeResponse")
    public JAXBElement<ServiceoperationinvokeResponse> createServiceoperationinvokeResponse(ServiceoperationinvokeResponse value) {
        return new JAXBElement<ServiceoperationinvokeResponse>(_ServiceoperationinvokeResponse_QNAME, ServiceoperationinvokeResponse.class, null, value);
    }

}
