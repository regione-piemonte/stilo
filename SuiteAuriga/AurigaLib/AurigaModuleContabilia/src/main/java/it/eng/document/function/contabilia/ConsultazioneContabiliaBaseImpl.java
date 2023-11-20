/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.client.contabilia.documenti.ClientContabiliaDocumenti;
import it.eng.utility.client.contabilia.documenti.ClientSpringContabiliaDocumenti;
import it.eng.utility.client.contabilia.documenti.data.ElaboraAttiAmministrativiRequest;
import it.eng.utility.client.contabilia.documenti.data.OutputElaboraAttiAmministrativi;
import it.eng.utility.client.contabilia.movimenti.ClientContabiliaMovimenti;
import it.eng.utility.client.contabilia.movimenti.ClientSpringContabiliaMovimenti;
import it.eng.utility.client.contabilia.movimenti.data.OutputRicercaMovimentoGestione;
import it.eng.utility.client.contabilia.movimenti.data.RicercaMovimentoGestioneStiloRequest;
import it.eng.utility.client.contabilia.ricerche.ClientContabiliaRicerche;
import it.eng.utility.client.contabilia.ricerche.ClientSpringContabiliaRicerche;
import it.eng.utility.client.contabilia.ricerche.data.OutputRicercaAccertamento;
import it.eng.utility.client.contabilia.ricerche.data.OutputRicercaImpegno;
import it.eng.utility.client.contabilia.ricerche.data.RicercaAccertamentoRequest;
import it.eng.utility.client.contabilia.ricerche.data.RicercaImpegnoRequest;

public class ConsultazioneContabiliaBaseImpl implements ConsultazioneContabiliaBase {
	
	private ClientContabiliaRicerche clientContabiliaRicerche;
	private ClientContabiliaMovimenti clientContabiliaMovimenti;
	private ClientContabiliaDocumenti clientContabiliaDocumenti;
	
	public ConsultazioneContabiliaBaseImpl() {
		this.clientContabiliaRicerche = ClientSpringContabiliaRicerche.getClient();
		this.clientContabiliaMovimenti = ClientSpringContabiliaMovimenti.getClient();
		this.clientContabiliaDocumenti = ClientSpringContabiliaDocumenti.getClient();
	}
	
	@Override
	public OutputRicercaAccertamento ricercaAccertamento(RicercaAccertamentoRequest input) {
		final OutputRicercaAccertamento response = clientContabiliaRicerche.ricercaAccertamento(input);
		
		return response;
	}
	
	@Override
	public OutputRicercaImpegno ricercaImpegno(RicercaImpegnoRequest input) {
		final OutputRicercaImpegno response = clientContabiliaRicerche.ricercaImpegno(input);
		
		return response;
	}
	
	@Override
	public OutputRicercaMovimentoGestione ricercaMovimentoGestione(RicercaMovimentoGestioneStiloRequest input) {
		final OutputRicercaMovimentoGestione response = clientContabiliaMovimenti.ricercaMovimentoGestione(input);
		
		return response;
	}
	
	@Override
	public OutputElaboraAttiAmministrativi invioProposta(ElaboraAttiAmministrativiRequest input) {
		final OutputElaboraAttiAmministrativi response = clientContabiliaDocumenti.invioProposta(input);
		
		return response;
	}
	
	@Override
	public OutputElaboraAttiAmministrativi aggiornaProposta(ElaboraAttiAmministrativiRequest input) {
		final OutputElaboraAttiAmministrativi response = clientContabiliaDocumenti.aggiornaProposta(input);
		
		return response;
	}
	
	@Override
	public OutputElaboraAttiAmministrativi bloccoDatiProposta(ElaboraAttiAmministrativiRequest input) {
		final OutputElaboraAttiAmministrativi response = clientContabiliaDocumenti.bloccoDatiProposta(input);
		
		return response;
	}
	
	@Override
	public OutputElaboraAttiAmministrativi invioAttoDef(ElaboraAttiAmministrativiRequest input) {
		final OutputElaboraAttiAmministrativi response = clientContabiliaDocumenti.invioAttoDef(input);
		
		return response;
	}
	
	@Override
	public OutputElaboraAttiAmministrativi invioAttoDefEsec(ElaboraAttiAmministrativiRequest input) {
		final OutputElaboraAttiAmministrativi response = clientContabiliaDocumenti.invioAttoDefEsec(input);
		
		return response;
	}
	
	@Override
	public OutputElaboraAttiAmministrativi invioAttoEsec(ElaboraAttiAmministrativiRequest input) {
		final OutputElaboraAttiAmministrativi response = clientContabiliaDocumenti.invioAttoEsec(input);
		
		return response;
	}
	
	@Override
	public OutputElaboraAttiAmministrativi sbloccoDatiProposta(ElaboraAttiAmministrativiRequest input) {
		final OutputElaboraAttiAmministrativi response = clientContabiliaDocumenti.sbloccoDatiProposta(input);
		
		return response;
	}
	
	@Override
	public OutputElaboraAttiAmministrativi annullamentoProposta(ElaboraAttiAmministrativiRequest input) {
		final OutputElaboraAttiAmministrativi response = clientContabiliaDocumenti.annullamentoProposta(input);
		
		return response;
	}
	
	@Override
	public OutputElaboraAttiAmministrativi creaLiquidazione(ElaboraAttiAmministrativiRequest input) {
		final OutputElaboraAttiAmministrativi response = clientContabiliaDocumenti.creaLiquidazione(input);
		
		return response;
	}
	
}
