/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.repository2.jaxws.jaxbBean.service.request.ServiceRequest;
import it.eng.auriga.repository2.jaxws.jaxbBean.service.response.ServiceResponse;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;


/**
 * @author Ottavio Passalacqua
 *
 */
@WebService(targetNamespace = "http://getdatiinviimailud.webservices.repository2.auriga.eng.it")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use=SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface WSIGetDatiInviiMailUD{
	@WebMethod
	@WebResult(partName = "serviceResponse", name = "serviceResponse")
	public ServiceResponse serviceOperation(
			@WebParam(partName = "service", name = "service") ServiceRequest serviceRequest);
}