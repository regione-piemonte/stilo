/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.core.annotation.Operation;
import it.eng.core.annotation.Service;
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

@Service(name = "ConsultazioneAmcoImpl")
public class ConsultazioneAmcoImpl implements ConsultazioneAmco {
	
	private ConsultazioneAmcoMapper mapper;
	private ConsultazioneAmcoBase service;
	
	public ConsultazioneAmcoImpl() {
		this.mapper = ConsultazioneAmcoMapper.INSTANCE;
		this.service = new ConsultazioneAmcoBaseImpl();
	}

	@Operation(name = "ricercaContiCreditoDebito")
	public AmcoContiCreditoDebitoResponse ricercaContiCreditoDebito(AmcoContiCreditoDebitoRequest input) {
		ContiCreditoDebitoRequest request = mapper.inputRicercaContiCreditoDebito(input);
		
		ContiCreditoDebitoResponse output = service.ricercaContiCreditoDebito(request);
		
		AmcoContiCreditoDebitoResponse response = mapper.outputRicercaContiCreditoDebito(output);
		
		return response;
	}

	@Operation(name = "ricercaContiImputazione")
	public AmcoContiImputazioneResponse ricercaContiImputazione(AmcoContiImputazioneRequest input) {
		ContiImputazioneRequest request = mapper.inputRicercaContiImputazione(input);
		
		ContiImputazioneResponse output = service.ricercaContiImputazione(request);
		
		AmcoContiImputazioneResponse response = mapper.outputRicercaContiImputazione(output);
		
		return response;
	}

	@Operation(name = "ricercaTipiDocumento")
	public AmcoTipiDocumentoResponse ricercaTipiDocumento(AmcoTipiDocumentoRequest input) {
		TipiDocumentoRequest request = mapper.inputRicercaTipiDocumento(input);
		
		TipiDocumentoResponse output = service.ricercaTipiDocumento(request);
		
		AmcoTipiDocumentoResponse response = mapper.outputRicercaTipiDocumento(output);
		
		return response;
	}

	@Operation(name = "ricercaBusinessPartners")
	public AmcoBusinessPartnersResponse ricercaBusinessPartners(AmcoBusinessPartnersRequest input) {
		BusinessPartnersRequest request = mapper.inputRicercaBusinessPartners(input);
		
		BusinessPartnersResponse output = service.ricercaBusinessPartners(request);
		
		AmcoBusinessPartnersResponse response = mapper.outputRicercaBusinessPartners(output);
		
		return response;
	}
	
}
