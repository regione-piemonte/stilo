/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.function.bean.ContabiliaElaboraAttiAmministrativiRequest;
import it.eng.document.function.bean.ContabiliaOutputElaboraAttiAmministrativi;
import it.eng.document.function.bean.ContabiliaOutputRicercaAccertamento;
import it.eng.document.function.bean.ContabiliaOutputRicercaImpegno;
import it.eng.document.function.bean.ContabiliaOutputRicercaMovimentoGestione;
import it.eng.document.function.bean.ContabiliaRicercaAccertamentoRequest;
import it.eng.document.function.bean.ContabiliaRicercaImpegnoRequest;
import it.eng.document.function.bean.ContabiliaRicercaMovimentoGestioneStiloRequest;

public interface ConsultazioneContabilia {
	
	ContabiliaOutputRicercaAccertamento ricercaAccertamento(ContabiliaRicercaAccertamentoRequest record);
	
	ContabiliaOutputRicercaImpegno ricercaImpegno(ContabiliaRicercaImpegnoRequest record);
	
	ContabiliaOutputRicercaMovimentoGestione ricercaMovimentoGestione(ContabiliaRicercaMovimentoGestioneStiloRequest record);
	
	ContabiliaOutputElaboraAttiAmministrativi invioProposta(ContabiliaElaboraAttiAmministrativiRequest record);
	
	ContabiliaOutputElaboraAttiAmministrativi aggiornaProposta(ContabiliaElaboraAttiAmministrativiRequest record);
	
	ContabiliaOutputElaboraAttiAmministrativi bloccoDatiProposta(ContabiliaElaboraAttiAmministrativiRequest record);
	
	ContabiliaOutputElaboraAttiAmministrativi invioAttoDef(ContabiliaElaboraAttiAmministrativiRequest record);
	
	ContabiliaOutputElaboraAttiAmministrativi invioAttoDefEsec(ContabiliaElaboraAttiAmministrativiRequest record);
	
	ContabiliaOutputElaboraAttiAmministrativi invioAttoEsec(ContabiliaElaboraAttiAmministrativiRequest record);
	
	ContabiliaOutputElaboraAttiAmministrativi sbloccoDatiProposta(ContabiliaElaboraAttiAmministrativiRequest record);
	
	ContabiliaOutputElaboraAttiAmministrativi annullamentoProposta(ContabiliaElaboraAttiAmministrativiRequest record);
	
	ContabiliaOutputElaboraAttiAmministrativi creaLiquidazione(ContabiliaElaboraAttiAmministrativiRequest record);
	
}
