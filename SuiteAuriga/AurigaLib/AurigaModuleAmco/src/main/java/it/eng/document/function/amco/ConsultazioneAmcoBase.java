/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.finmatica.gsaws.BusinessPartnersRequest;
import it.finmatica.gsaws.BusinessPartnersResponse;
import it.finmatica.gsaws.ContiCreditoDebitoRequest;
import it.finmatica.gsaws.ContiCreditoDebitoResponse;
import it.finmatica.gsaws.ContiImputazioneRequest;
import it.finmatica.gsaws.ContiImputazioneResponse;
import it.finmatica.gsaws.TipiDocumentoRequest;
import it.finmatica.gsaws.TipiDocumentoResponse;

public interface ConsultazioneAmcoBase {
	
	ContiCreditoDebitoResponse ricercaContiCreditoDebito(ContiCreditoDebitoRequest input);
	
	ContiImputazioneResponse ricercaContiImputazione(ContiImputazioneRequest input);
	
	TipiDocumentoResponse ricercaTipiDocumento(TipiDocumentoRequest input);
	
	BusinessPartnersResponse ricercaBusinessPartners(BusinessPartnersRequest input);
	
}
