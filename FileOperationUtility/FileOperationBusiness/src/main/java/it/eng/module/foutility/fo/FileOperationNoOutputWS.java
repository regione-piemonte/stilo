/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.module.foutility.beans.generated.FileOperation;
import it.eng.module.foutility.beans.generated.Response;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.jws.soap.SOAPBinding.Style;
import javax.xml.ws.soap.MTOM;

//import com.sun.xml.internal.ws.developer.StreamingAttachment;

@WebService(targetNamespace="it.eng.fileoperation.ws")
@MTOM(enabled=true ,threshold=0)
//@StreamingAttachment(parseEagerly=true, memoryThreshold=4000000L)
@SOAPBinding(style = Style.DOCUMENT, use=SOAPBinding.Use.LITERAL, parameterStyle = ParameterStyle.BARE)
public interface FileOperationNoOutputWS {
	@WebResult(name = "FileOperationResponse", targetNamespace = "it.eng.fileoperation.ws", partName = "FileOperationResponse")
	public Response execute( @WebParam(name = "FileOperationRequest",partName="FileOperationRequest", 
            targetNamespace = "it.eng.fileoperation.ws") FileOperation input);

}
