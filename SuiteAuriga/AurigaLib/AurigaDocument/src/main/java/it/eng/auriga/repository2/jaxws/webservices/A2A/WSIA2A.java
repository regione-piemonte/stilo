/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import it.eng.auriga.repository2.jaxws.webservices.A2A.bean.ResponseWSA2A;



/**
 * @author Antonio Peluso
 *
 */
@WebService(targetNamespace = "http://A2A.webservices.repository2.auriga.eng.it")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface WSIA2A {
	
	@WebMethod(operationName = "SendUtenti", action = "http://A2A.webservices.repository2.auriga.eng.it/SendUtenti")
	@WebResult(name = "serviceResponse", targetNamespace = "http://A2A.webservices.repository2.auriga.eng.it", partName = "parameter")
	public ResponseWSA2A sendUtenti(
			@WebParam(name = "ListaUtenti", targetNamespace = "http://A2A.webservices.repository2.auriga.eng.it", partName = "parameter") it.eng.auriga.repository2.jaxws.webservices.A2A.utente.bean.Lista parameter);
	
	@WebMethod(operationName = "SendNodiOrganigramma", action = "http://A2A.webservices.repository2.auriga.eng.it/SendNodiOrganigramma")
	@WebResult(name = "serviceResponse", targetNamespace = "http://A2A.webservices.repository2.auriga.eng.it", partName = "parameter")
	public ResponseWSA2A sendNodiOrganigramma(
			@WebParam(name = "ListaNodiOrganigramma", targetNamespace = "http://A2A.webservices.repository2.auriga.eng.it", partName = "parameter") it.eng.auriga.repository2.jaxws.webservices.A2A.nodiOrganigramma.bean.Lista parameter);
	
}