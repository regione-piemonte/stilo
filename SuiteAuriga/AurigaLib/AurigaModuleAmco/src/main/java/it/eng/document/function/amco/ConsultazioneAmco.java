/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.function.bean.AmcoBusinessPartnersRequest;
import it.eng.document.function.bean.AmcoBusinessPartnersResponse;
import it.eng.document.function.bean.AmcoContiCreditoDebitoRequest;
import it.eng.document.function.bean.AmcoContiCreditoDebitoResponse;
import it.eng.document.function.bean.AmcoContiImputazioneRequest;
import it.eng.document.function.bean.AmcoContiImputazioneResponse;
import it.eng.document.function.bean.AmcoTipiDocumentoRequest;
import it.eng.document.function.bean.AmcoTipiDocumentoResponse;

public interface ConsultazioneAmco {
	
	AmcoContiCreditoDebitoResponse ricercaContiCreditoDebito(AmcoContiCreditoDebitoRequest input);
	
	AmcoContiImputazioneResponse ricercaContiImputazione(AmcoContiImputazioneRequest input);
	
	AmcoTipiDocumentoResponse ricercaTipiDocumento(AmcoTipiDocumentoRequest input);
	
	AmcoBusinessPartnersResponse ricercaBusinessPartners(AmcoBusinessPartnersRequest input);
	
}
