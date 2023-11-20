/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.eng.auriga.repository2.jaxws.webservices.aggiornaanagrafeclassidocclientela;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import it.eng.auriga.repository2.jaxws.jaxbBean.aggiornaanagrafeclassidocclientela.RequestAggiornaAnagrafeClassiDocClientela;
import it.eng.auriga.repository2.jaxws.jaxbBean.aggiornaanagrafeclassidocclientela.ResponseAggiornaAnagrafeClassiDocClientela;


@WebService(name = "AggiornaAnagrafeClassiDocClientelaPortType", targetNamespace = "http://aggiornaanagrafeclassidocclientela.webservices.repository2.auriga.eng.it")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface WSIAggiornaAnagrafeClassiDocClientela {


    /**
     * 
     * @param parameter
     * @return
     *     returns it.eng.auriga.repository2.jaxws.webservices.aggiornaanagrafeclassidocclientela.ResponseAggiornaAnagrafeClassiDocClientela
     */
    @WebMethod(operationName = "AggiornaAnagrafeClassiDocClientela", action = "http://aggiornaanagrafeclassidocclientela.webservices.repository2.auriga.eng.it/AggiornaAnagrafeClassiDocClientela")
    @WebResult(name = "ResponseAggiornaAnagrafeClassiDocClientela", targetNamespace = "http://aggiornaanagrafeclassidocclientela.webservices.repository2.auriga.eng.it", partName = "parameter")
    public ResponseAggiornaAnagrafeClassiDocClientela aggiornaAnagrafeClassiDocClientela (
        @WebParam(name = "RequestAggiornaAnagrafeClassiDocClientela", targetNamespace = "http://aggiornaanagrafeclassidocclientela.webservices.repository2.auriga.eng.it", partName = "parameter")
        RequestAggiornaAnagrafeClassiDocClientela parameter);

}
