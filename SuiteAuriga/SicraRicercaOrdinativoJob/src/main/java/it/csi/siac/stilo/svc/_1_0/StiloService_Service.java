/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 3.2.5
 * 2020-05-26T19:01:17.538+02:00
 * Generated source version: 3.2.5
 *
 */
@WebServiceClient(name = "StiloService",
                  wsdlLocation = "classpath:StiloService.wsdl",
                  targetNamespace = "http://siac.csi.it/stilo/svc/1.0")
public class StiloService_Service extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://siac.csi.it/stilo/svc/1.0", "StiloService");
    public final static QName StiloServicePort = new QName("http://siac.csi.it/stilo/svc/1.0", "StiloServicePort");
    static {
        URL url = StiloService_Service.class.getClassLoader().getResource("StiloService.wsdl");
        if (url == null) {
            java.util.logging.Logger.getLogger(StiloService_Service.class.getName())
                .log(java.util.logging.Level.INFO,
                     "Can not initialize the default wsdl from {0}", "classpath:StiloService.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public StiloService_Service(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public StiloService_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public StiloService_Service() {
        super(WSDL_LOCATION, SERVICE);
    }

    public StiloService_Service(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public StiloService_Service(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public StiloService_Service(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }




    /**
     *
     * @return
     *     returns StiloService
     */
    @WebEndpoint(name = "StiloServicePort")
    public StiloService getStiloServicePort() {
        return super.getPort(StiloServicePort, StiloService.class);
    }

    /**
     *
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns StiloService
     */
    @WebEndpoint(name = "StiloServicePort")
    public StiloService getStiloServicePort(WebServiceFeature... features) {
        return super.getPort(StiloServicePort, StiloService.class, features);
    }

}