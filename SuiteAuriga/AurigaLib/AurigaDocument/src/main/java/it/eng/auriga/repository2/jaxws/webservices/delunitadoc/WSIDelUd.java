/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
 * WSDelUd.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package it.eng.auriga.repository2.jaxws.webservices.delunitadoc;


import it.eng.auriga.repository2.jaxws.jaxbBean.service.request.ServiceRequest;
import it.eng.auriga.repository2.jaxws.jaxbBean.service.response.ServiceResponse;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService(targetNamespace = "http://delunitadoc.webservices.repository2.auriga.eng.it")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use=SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface WSIDelUd{
	@WebMethod
	@WebResult(partName = "serviceResponse", name = "serviceResponse")
	public ServiceResponse serviceOperation(
			@WebParam(partName = "service", name = "service") ServiceRequest serviceRequest);}
