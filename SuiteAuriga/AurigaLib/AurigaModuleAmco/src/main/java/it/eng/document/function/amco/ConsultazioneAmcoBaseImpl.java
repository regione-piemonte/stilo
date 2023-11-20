/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.amco.client.ClientAmco;
import it.eng.utility.amco.client.ClientSpringAmco;
import it.finmatica.gsaws.BusinessPartnersRequest;
import it.finmatica.gsaws.BusinessPartnersResponse;
import it.finmatica.gsaws.ContiCreditoDebitoRequest;
import it.finmatica.gsaws.ContiCreditoDebitoResponse;
import it.finmatica.gsaws.ContiImputazioneRequest;
import it.finmatica.gsaws.ContiImputazioneResponse;
import it.finmatica.gsaws.TipiDocumentoRequest;
import it.finmatica.gsaws.TipiDocumentoResponse;

public class ConsultazioneAmcoBaseImpl implements ConsultazioneAmcoBase {
	
	private ClientAmco clientAmco;
	
	public ConsultazioneAmcoBaseImpl() {
		this.clientAmco = ClientSpringAmco.getClient();
	}

	@Override
	public ContiCreditoDebitoResponse ricercaContiCreditoDebito(ContiCreditoDebitoRequest input) {
		ContiCreditoDebitoResponse response = clientAmco.ricercaContiCreditoDebito(input);
		
		return response;
	}

	@Override
	public ContiImputazioneResponse ricercaContiImputazione(ContiImputazioneRequest input) {
		ContiImputazioneResponse response = clientAmco.ricercaContiImputazione(input);
		
		return response;
	}

	@Override
	public TipiDocumentoResponse ricercaTipiDocumento(TipiDocumentoRequest input) {
		TipiDocumentoResponse response = clientAmco.ricercaTipiDocumento(input);
		
		return response;
	}

	@Override
	public BusinessPartnersResponse ricercaBusinessPartners(BusinessPartnersRequest input) {
		BusinessPartnersResponse response = clientAmco.ricercaBusinessPartners(input);
		
		return response;
	}
	
}
