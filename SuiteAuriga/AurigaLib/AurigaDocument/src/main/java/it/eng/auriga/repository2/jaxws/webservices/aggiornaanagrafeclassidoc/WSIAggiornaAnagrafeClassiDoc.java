/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.eng.auriga.repository2.jaxws.webservices.aggiornaanagrafeclassidoc;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import it.eng.auriga.repository2.jaxws.jaxbBean.aggiornaanagrafeclassidoc.RequestAggiornaAnagrafeClassiDoc;
import it.eng.auriga.repository2.jaxws.jaxbBean.aggiornaanagrafeclassidoc.ResponseAggiornaAnagrafeClassiDoc;


@WebService(name = "AggiornaAnagrafeClassiDocPortType", targetNamespace = "http://aggiornaanagrafeclassidoc.webservices.repository2.auriga.eng.it")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface WSIAggiornaAnagrafeClassiDoc {


    /**
     * 
     * @param parameter
     * @return
     *     returns it.eng.auriga.repository2.jaxws.webservices.aggiornaanagrafeclassidoc.ResponseAggiornaAnagrafeClassiDoc
     */
    @WebMethod(operationName = "AggiornaAnagrafeClassiDoc", action = "http://aggiornaanagrafeclassidoc.webservices.repository2.auriga.eng.it/AggiornaAnagrafeClassiDoc")
    @WebResult(name = "ResponseAggiornaAnagrafeClassiDoc", targetNamespace = "http://aggiornaanagrafeclassidoc.webservices.repository2.auriga.eng.it", partName = "parameter")
    public ResponseAggiornaAnagrafeClassiDoc aggiornaAnagrafeClassiDoc(
        @WebParam(name = "RequestAggiornaAnagrafeClassiDoc", targetNamespace = "http://aggiornaanagrafeclassidoc.webservices.repository2.auriga.eng.it", partName = "parameter")
        RequestAggiornaAnagrafeClassiDoc parameter);

}
