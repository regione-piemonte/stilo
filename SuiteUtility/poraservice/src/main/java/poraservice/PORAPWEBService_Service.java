// 
// Decompiled by Procyon v0.5.36
// 

package poraservice;

import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceFeature;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import java.net.URL;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.Service;

@WebServiceClient(name = "PORAPWEBService", wsdlLocation = "http:///172.31.134.6:8080/PWEBServices/PORAPWEBService.svc?wsdl", targetNamespace = "http://poraservice/")
public class PORAPWEBService_Service extends Service
{
    public static final URL WSDL_LOCATION;
    public static final QName SERVICE;
    public static final QName BasicHttpBindingPORAPWEBService;
    
    static {
        SERVICE = new QName("http://poraservice/", "PORAPWEBService");
        BasicHttpBindingPORAPWEBService = new QName("http://poraservice/", "BasicHttpBinding_PORAPWEBService");
        URL url = null;
        try {
            url = new URL("http:///172.31.134.6:8080/PWEBServices/PORAPWEBService.svc?wsdl");
        }
        catch (MalformedURLException e) {
            Logger.getLogger(PORAPWEBService_Service.class.getName()).log(Level.INFO, "Can not initialize the default wsdl from {0}", "http:///172.31.134.6:8080/PWEBServices/PORAPWEBService.svc?wsdl");
        }
        WSDL_LOCATION = url;
    }
    
    public PORAPWEBService_Service(final URL wsdlLocation) {
        super(wsdlLocation, PORAPWEBService_Service.SERVICE);
    }
    
    public PORAPWEBService_Service(final URL wsdlLocation, final QName serviceName) {
        super(wsdlLocation, serviceName);
    }
    
    public PORAPWEBService_Service() {
        super(PORAPWEBService_Service.WSDL_LOCATION, PORAPWEBService_Service.SERVICE);
    }
    
    public PORAPWEBService_Service(final WebServiceFeature... features) {
        super(PORAPWEBService_Service.WSDL_LOCATION, PORAPWEBService_Service.SERVICE, features);
    }
    
    public PORAPWEBService_Service(final URL wsdlLocation, final WebServiceFeature... features) {
        super(wsdlLocation, PORAPWEBService_Service.SERVICE, features);
    }
    
    public PORAPWEBService_Service(final URL wsdlLocation, final QName serviceName, final WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }
    
    @WebEndpoint(name = "BasicHttpBinding_PORAPWEBService")
    public PORAPWEBService getBasicHttpBindingPORAPWEBService() {
        return (PORAPWEBService)super.getPort(PORAPWEBService_Service.BasicHttpBindingPORAPWEBService, (Class)PORAPWEBService.class);
    }
    
    @WebEndpoint(name = "BasicHttpBinding_PORAPWEBService")
    public PORAPWEBService getBasicHttpBindingPORAPWEBService(final WebServiceFeature... features) {
        return (PORAPWEBService)super.getPort(PORAPWEBService_Service.BasicHttpBindingPORAPWEBService, (Class)PORAPWEBService.class, features);
    }
}
