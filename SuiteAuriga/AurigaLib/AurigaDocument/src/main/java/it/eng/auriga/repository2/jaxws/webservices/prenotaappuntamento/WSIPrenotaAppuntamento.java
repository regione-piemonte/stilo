/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import it.eng.auriga.repository2.jaxws.jaxbBean.prenotaappuntamento.RequestAggiornaRichiesta;
import it.eng.auriga.repository2.jaxws.jaxbBean.prenotaappuntamento.RequestVerificaRichiesta;
import it.eng.auriga.repository2.jaxws.jaxbBean.prenotaappuntamento.ResponseAggiornaRichiesta;
import it.eng.auriga.repository2.jaxws.jaxbBean.prenotaappuntamento.ResponseVerificaRichiesta;

/**
 * @author Mattia Zanetti
 *
 */
@WebService(targetNamespace = "http://prenotaappuntamento.webservices.repository2.auriga.eng.it")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface WSIPrenotaAppuntamento {

	/**
	 * 
	 * @param parameter
	 * @return returns it.eng.auriga.repository2.webservices.prenotaappuntamento.ResponseVerificaRichiesta
	 */
	@WebMethod(operationName = "VerificaRichiesta", action = "http://prenotaappuntamento.webservices.repository2.auriga.eng.it/VerificaRichiesta")
	@WebResult(name = "ResponseVerificaRichiesta", targetNamespace = "http://prenotaappuntamento.webservices.repository2.auriga.eng.it", partName = "parameter")
	public ResponseVerificaRichiesta verificaRichiesta(
			@WebParam(name = "RequestVerificaRichiesta", targetNamespace = "http://prenotaappuntamento.webservices.repository2.auriga.eng.it", partName = "parameter") RequestVerificaRichiesta parameter);

	/**
	 * 
	 * @param parameter
	 * @return returns it.eng.auriga.repository2.webservices.prenotaappuntamento.ResponseAggiornaRichiesta
	 */
	@WebMethod(operationName = "AggiornaRichiesta", action = "http://prenotaappuntamento.webservices.repository2.auriga.eng.it/AggiornaRichiesta")
	@WebResult(name = "ResponseAggiornaRichiesta", targetNamespace = "http://prenotaappuntamento.webservices.repository2.auriga.eng.it", partName = "parameter")
	public ResponseAggiornaRichiesta aggiornaRichiesta(
			@WebParam(name = "RequestAggiornaRichiesta", targetNamespace = "http://prenotaappuntamento.webservices.repository2.auriga.eng.it", partName = "parameter") RequestAggiornaRichiesta parameter);

}