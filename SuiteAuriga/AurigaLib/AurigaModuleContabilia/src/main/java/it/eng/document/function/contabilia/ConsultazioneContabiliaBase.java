/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.client.contabilia.documenti.data.ElaboraAttiAmministrativiRequest;
import it.eng.utility.client.contabilia.documenti.data.OutputElaboraAttiAmministrativi;
import it.eng.utility.client.contabilia.movimenti.data.OutputRicercaMovimentoGestione;
import it.eng.utility.client.contabilia.movimenti.data.RicercaMovimentoGestioneStiloRequest;
import it.eng.utility.client.contabilia.ricerche.data.OutputRicercaAccertamento;
import it.eng.utility.client.contabilia.ricerche.data.OutputRicercaImpegno;
import it.eng.utility.client.contabilia.ricerche.data.RicercaAccertamentoRequest;
import it.eng.utility.client.contabilia.ricerche.data.RicercaImpegnoRequest;

public interface ConsultazioneContabiliaBase {
	
	OutputRicercaAccertamento ricercaAccertamento(RicercaAccertamentoRequest input);
	
	OutputRicercaImpegno ricercaImpegno(RicercaImpegnoRequest input);
	
	OutputRicercaMovimentoGestione ricercaMovimentoGestione(RicercaMovimentoGestioneStiloRequest input);
	
	OutputElaboraAttiAmministrativi invioProposta(ElaboraAttiAmministrativiRequest input);
	
	OutputElaboraAttiAmministrativi aggiornaProposta(ElaboraAttiAmministrativiRequest input);
	
	OutputElaboraAttiAmministrativi bloccoDatiProposta(ElaboraAttiAmministrativiRequest input);
	
	OutputElaboraAttiAmministrativi invioAttoDef(ElaboraAttiAmministrativiRequest input);
	
	OutputElaboraAttiAmministrativi invioAttoDefEsec(ElaboraAttiAmministrativiRequest input);
	
	OutputElaboraAttiAmministrativi invioAttoEsec(ElaboraAttiAmministrativiRequest input);
	
	OutputElaboraAttiAmministrativi sbloccoDatiProposta(ElaboraAttiAmministrativiRequest input);
	
	OutputElaboraAttiAmministrativi annullamentoProposta(ElaboraAttiAmministrativiRequest input);
	
	OutputElaboraAttiAmministrativi creaLiquidazione(ElaboraAttiAmministrativiRequest input);
	
}
