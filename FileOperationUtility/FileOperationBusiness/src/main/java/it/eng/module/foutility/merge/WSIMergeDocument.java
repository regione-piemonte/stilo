/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.module.foutility.beans.merge.request.MergeDocumentRequest;
import it.eng.module.foutility.beans.merge.response.MergeDocumentResponse;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService(targetNamespace="http://mergedocument.webservices.jaxws.eng.it/")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use=SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface WSIMergeDocument {

	@WebMethod
	@WebResult(partName = "mergeDocumentResponse", name = "mergeDocumentResponse")
	public MergeDocumentResponse merge(
			@WebParam(partName = "mergeDocumentRequest", name = "mergeDocumentRequest") MergeDocumentRequest mergeDocumentRequest);
	
}
