/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import it.eng.document.function.bean.AmcoBusinessPartnersRequest;
import it.eng.document.function.bean.AmcoBusinessPartnersResponse;
import it.eng.document.function.bean.AmcoContiCreditoDebitoRequest;
import it.eng.document.function.bean.AmcoContiCreditoDebitoResponse;
import it.eng.document.function.bean.AmcoContiImputazioneRequest;
import it.eng.document.function.bean.AmcoContiImputazioneResponse;
import it.eng.document.function.bean.AmcoTipiDocumentoRequest;
import it.eng.document.function.bean.AmcoTipiDocumentoResponse;
import it.finmatica.gsaws.BusinessPartnersRequest;
import it.finmatica.gsaws.BusinessPartnersResponse;
import it.finmatica.gsaws.ContiCreditoDebitoRequest;
import it.finmatica.gsaws.ContiCreditoDebitoResponse;
import it.finmatica.gsaws.ContiImputazioneRequest;
import it.finmatica.gsaws.ContiImputazioneResponse;
import it.finmatica.gsaws.TipiDocumentoRequest;
import it.finmatica.gsaws.TipiDocumentoResponse;

//@Mapper /* (componentModel = "spring") */
public interface ConsultazioneAmcoMapper {
	
	ConsultazioneAmcoMapper INSTANCE = Mappers.getMapper(ConsultazioneAmcoMapper.class);
	
	/*
	 * Mapping da bean Auriga a bean client ricercaContiCreditoDebito (parametri in input)
	 */
	ContiCreditoDebitoRequest inputRicercaContiCreditoDebito(AmcoContiCreditoDebitoRequest source);
	
	/*
	 * Mapping da bean Auriga a bean client ricercaContiImputazione (parametri in input)
	 */
	ContiImputazioneRequest inputRicercaContiImputazione(AmcoContiImputazioneRequest source);
	
	/*
	 * Mapping da bean Auriga a bean client ricercaTipiDocumento (parametri in input)
	 */
	TipiDocumentoRequest inputRicercaTipiDocumento(AmcoTipiDocumentoRequest source);
	
	/*
	 * Mapping da bean Auriga a bean client ricercaBusinessPartners (parametri in input)
	 */
	BusinessPartnersRequest inputRicercaBusinessPartners(AmcoBusinessPartnersRequest source);
	
	/*
	 * Mapping response client in bean Auriga per ricercaContiCreditoDebito
	 */
	AmcoContiCreditoDebitoResponse outputRicercaContiCreditoDebito(ContiCreditoDebitoResponse source);
	
	/*
	 * Mapping response client in bean Auriga per ricercaContiImputazione
	 */
	AmcoContiImputazioneResponse outputRicercaContiImputazione(ContiImputazioneResponse source);
	
	/*
	 * Mapping response client in bean Auriga per ricercaTipiDocumento
	 */
	AmcoTipiDocumentoResponse outputRicercaTipiDocumento(TipiDocumentoResponse source);
	
	/*
	 * Mapping response client in bean Auriga per ricercaBusinessPartners
	 */
	AmcoBusinessPartnersResponse outputRicercaBusinessPartners(BusinessPartnersResponse source);
	
}
 