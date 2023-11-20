/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.apache.log4j.Logger;

import it.eng.core.annotation.Operation;
import it.eng.core.annotation.Service;
import it.eng.document.function.bean.ContabiliaElaboraAttiAmministrativiRequest;
import it.eng.document.function.bean.ContabiliaOutputElaboraAttiAmministrativi;
import it.eng.document.function.bean.ContabiliaOutputRicercaAccertamento;
import it.eng.document.function.bean.ContabiliaOutputRicercaImpegno;
import it.eng.document.function.bean.ContabiliaOutputRicercaMovimentoGestione;
import it.eng.document.function.bean.ContabiliaRicercaAccertamentoRequest;
import it.eng.document.function.bean.ContabiliaRicercaImpegnoRequest;
import it.eng.document.function.bean.ContabiliaRicercaMovimentoGestioneStiloRequest;
import it.eng.utility.client.contabilia.documenti.data.ElaboraAttiAmministrativiRequest;
import it.eng.utility.client.contabilia.documenti.data.OutputElaboraAttiAmministrativi;
import it.eng.utility.client.contabilia.movimenti.data.OutputRicercaMovimentoGestione;
import it.eng.utility.client.contabilia.movimenti.data.RicercaMovimentoGestioneStiloRequest;
import it.eng.utility.client.contabilia.ricerche.data.OutputRicercaAccertamento;
import it.eng.utility.client.contabilia.ricerche.data.OutputRicercaImpegno;
import it.eng.utility.client.contabilia.ricerche.data.RicercaAccertamentoRequest;
import it.eng.utility.client.contabilia.ricerche.data.RicercaImpegnoRequest;

@Service(name = "ConsultazioneContabiliaImpl")
public class ConsultazioneContabiliaImpl implements ConsultazioneContabilia {
	
	private static Logger logger = Logger.getLogger(ConsultazioneContabiliaImpl.class);
	private ConsultazioneContabiliaMapper mapper;
	private ConsultazioneContabiliaBase service;
	
	public ConsultazioneContabiliaImpl() {
		this.mapper = ConsultazioneContabiliaMapper.INSTANCE;
		this.service = new ConsultazioneContabiliaBaseImpl();
	}
	
	@Operation(name = "ricercaAccertamento")
	public ContabiliaOutputRicercaAccertamento ricercaAccertamento(ContabiliaRicercaAccertamentoRequest record) {
		// mapping per parametri input da bean Auriga a bean WS
		RicercaAccertamentoRequest input = mapper.fromContabiliaRicercaAccertamento(record);
		
		// chiamata WS ricerca accertamento
		OutputRicercaAccertamento output = service.ricercaAccertamento(input);
		
		// mapping response da bean WS a bean Auriga
		ContabiliaOutputRicercaAccertamento response = mapper.outputRicercaAccertamentoToContabiliaOutputRicercaAccertamento(output);
		
		logger.info("Chiamata servizio WS ricercaAccertamento: " + response.isOk() + " - " + response.getMessaggio());
		
		return response;
	}
	
	@Operation(name = "ricercaImpegno")
	public ContabiliaOutputRicercaImpegno ricercaImpegno(ContabiliaRicercaImpegnoRequest record) {
		// mapping per parametri input da bean Auriga a bean WS
		RicercaImpegnoRequest input = mapper.fromContabiliaRicercaImpegno(record);
		
		// chiamata WS ricerca impegno
		OutputRicercaImpegno output = service.ricercaImpegno(input);
		
		// mapping response da bean WS a bean Auriga
		ContabiliaOutputRicercaImpegno response = mapper.outputRicercaImpegnoToContabiliaOutputRicercaImpegno(output);
		
		logger.info("Chiamata servizio WS ricercaImpegno: " + response.isOk() + " - " + response.getMessaggio());
		
		return response;
	}
	
	@Operation(name = "ricercaMovimentoGestione")
	public ContabiliaOutputRicercaMovimentoGestione ricercaMovimentoGestione(ContabiliaRicercaMovimentoGestioneStiloRequest record) {
		// mapping per parametri input da bean Auriga a bean WS
		RicercaMovimentoGestioneStiloRequest input = mapper.fromContabiliaRicercaMovimentoGestione(record);
		
		// chiamata WS ricerca movimento gestione
		OutputRicercaMovimentoGestione output = service.ricercaMovimentoGestione(input);
		
		// mapping response da bean WS a bean Auriga
		ContabiliaOutputRicercaMovimentoGestione response = mapper.outputRicercaMovimentoGestioneToContabiliaOutputRicercaMovimentoGestione(output);
		
		logger.info("Chiamata servizio WS ricercaMovimentoGestione esito: " + response.isOk() + 
					" - messaggio: " + response.getMessaggio() + 
					" - totaleRisultati: " + response.getTotaleRisultati());
		
		return response;
	}
	
	@Operation(name = "invioProposta")
	public ContabiliaOutputElaboraAttiAmministrativi invioProposta(ContabiliaElaboraAttiAmministrativiRequest record) {
		// mapping per parametri input da bean Auriga a bean WS
		ElaboraAttiAmministrativiRequest input = mapper.fromContabiliaElaboraAttiAmministrativi(record);
		
		// chiamata WS elabora atti amministrativi
		OutputElaboraAttiAmministrativi output = service.invioProposta(input);
		
		// mapping response da bean WS a bean Auriga
		ContabiliaOutputElaboraAttiAmministrativi response = mapper.outputElaboraAttiAmministrativiToContabiliaOutputElaboraAttiAmministrativi(output);
		
		logger.info("Chiamata servizio WS invioProposta - esito: " + response.getEntry().isResponseElaborazione() + 
					" - " + response.getEntry().getEsitoElaborazione() + 
					" - codice: " + response.getEntry().getCodiceElaborazione() +
					" - messaggio: " + response.getEntry().getMessaggioElaborazione());
		
		return response;
	}
	
	@Operation(name = "aggiornaProposta")
	public ContabiliaOutputElaboraAttiAmministrativi aggiornaProposta(ContabiliaElaboraAttiAmministrativiRequest record) {
		// mapping per parametri input da bean Auriga a bean WS
		ElaboraAttiAmministrativiRequest input = mapper.fromContabiliaElaboraAttiAmministrativi(record);
		
		// chiamata WS elabora atti amministrativi
		OutputElaboraAttiAmministrativi output = service.aggiornaProposta(input);
		
		// mapping response da bean WS a bean Auriga
		ContabiliaOutputElaboraAttiAmministrativi response = mapper.outputElaboraAttiAmministrativiToContabiliaOutputElaboraAttiAmministrativi(output);
		
		logger.info("Chiamata servizio WS aggiornaProposta: - esito: " + response.getEntry().isResponseElaborazione() + 
					" - " + response.getEntry().getEsitoElaborazione() + 
					" - codice: " + response.getEntry().getCodiceElaborazione() +
					" - messaggio: " + response.getEntry().getMessaggioElaborazione());
		
		return response;
	}
	
	@Operation(name = "bloccoDatiProposta")
	public ContabiliaOutputElaboraAttiAmministrativi bloccoDatiProposta(ContabiliaElaboraAttiAmministrativiRequest record) {
		// mapping per parametri input da bean Auriga a bean WS
		ElaboraAttiAmministrativiRequest input = mapper.fromContabiliaElaboraAttiAmministrativi(record);
		
		// chiamata WS elabora atti amministrativi
		OutputElaboraAttiAmministrativi output = service.bloccoDatiProposta(input);
		
		// mapping response da bean WS a bean Auriga
		ContabiliaOutputElaboraAttiAmministrativi response = mapper.outputElaboraAttiAmministrativiToContabiliaOutputElaboraAttiAmministrativi(output);
		
		logger.info("Chiamata servizio WS bloccoDatiProposta: - esito: " + response.getEntry().isResponseElaborazione() + 
					" - " + response.getEntry().getEsitoElaborazione() + 
					" - codice: " + response.getEntry().getCodiceElaborazione() +
					" - messaggio: " + response.getEntry().getMessaggioElaborazione());
		
		return response;
	}
	
	@Operation(name = "invioAttoDef")
	public ContabiliaOutputElaboraAttiAmministrativi invioAttoDef(ContabiliaElaboraAttiAmministrativiRequest record) {
		// mapping per parametri input da bean Auriga a bean WS
		ElaboraAttiAmministrativiRequest input = mapper.fromContabiliaElaboraAttiAmministrativi(record);
		
		// chiamata WS elabora atti amministrativi
		OutputElaboraAttiAmministrativi output = service.invioAttoDef(input);
		
		// mapping response da bean WS a bean Auriga
		ContabiliaOutputElaboraAttiAmministrativi response = mapper.outputElaboraAttiAmministrativiToContabiliaOutputElaboraAttiAmministrativi(output);
		
		logger.info("Chiamata servizio WS invioAttoDef: - esito: " + response.getEntry().isResponseElaborazione() + 
					" - " + response.getEntry().getEsitoElaborazione() + 
					" - codice: " + response.getEntry().getCodiceElaborazione() +
					" - messaggio: " + response.getEntry().getMessaggioElaborazione());
		
		return response;
	}
	
	@Operation(name = "invioAttoDefEsec")
	public ContabiliaOutputElaboraAttiAmministrativi invioAttoDefEsec(ContabiliaElaboraAttiAmministrativiRequest record) {
		// mapping per parametri input da bean Auriga a bean WS
		ElaboraAttiAmministrativiRequest input = mapper.fromContabiliaElaboraAttiAmministrativi(record);
		
		// chiamata WS elabora atti amministrativi
		OutputElaboraAttiAmministrativi output = service.invioAttoDefEsec(input);
		
		// mapping response da bean WS a bean Auriga
		ContabiliaOutputElaboraAttiAmministrativi response = mapper.outputElaboraAttiAmministrativiToContabiliaOutputElaboraAttiAmministrativi(output);
		
		logger.info("Chiamata servizio WS invioAttoDefEsec: - esito: " + response.getEntry().isResponseElaborazione() + 
					" - " + response.getEntry().getEsitoElaborazione() + 
					" - codice: " + response.getEntry().getCodiceElaborazione() +
					" - messaggio: " + response.getEntry().getMessaggioElaborazione());
		
		return response;
	}
	
	@Operation(name = "invioAttoEsec")
	public ContabiliaOutputElaboraAttiAmministrativi invioAttoEsec(ContabiliaElaboraAttiAmministrativiRequest record) {
		// mapping per parametri input da bean Auriga a bean WS
		ElaboraAttiAmministrativiRequest input = mapper.fromContabiliaElaboraAttiAmministrativi(record);
		
		// chiamata WS elabora atti amministrativi
		OutputElaboraAttiAmministrativi output = service.invioAttoEsec(input);
		
		// mapping response da bean WS a bean Auriga
		ContabiliaOutputElaboraAttiAmministrativi response = mapper.outputElaboraAttiAmministrativiToContabiliaOutputElaboraAttiAmministrativi(output);
		
		logger.info("Chiamata servizio WS invioAttoEsec: - esito: " + response.getEntry().isResponseElaborazione() + 
					" - " + response.getEntry().getEsitoElaborazione() + 
					" - codice: " + response.getEntry().getCodiceElaborazione() +
					" - messaggio: " + response.getEntry().getMessaggioElaborazione());
		
		return response;
	}
	
	@Operation(name = "sbloccoDatiProposta")
	public ContabiliaOutputElaboraAttiAmministrativi sbloccoDatiProposta(ContabiliaElaboraAttiAmministrativiRequest record) {
		// mapping per parametri input da bean Auriga a bean WS
		ElaboraAttiAmministrativiRequest input = mapper.fromContabiliaElaboraAttiAmministrativi(record);
		
		// chiamata WS elabora atti amministrativi
		OutputElaboraAttiAmministrativi output = service.sbloccoDatiProposta(input);
		
		// mapping response da bean WS a bean Auriga
		ContabiliaOutputElaboraAttiAmministrativi response = mapper.outputElaboraAttiAmministrativiToContabiliaOutputElaboraAttiAmministrativi(output);
		
		logger.info("Chiamata servizio WS sbloccoDatiProposta: - esito: " + response.getEntry().isResponseElaborazione() + 
					" - " + response.getEntry().getEsitoElaborazione() + 
					" - codice: " + response.getEntry().getCodiceElaborazione() +
					" - messaggio: " + response.getEntry().getMessaggioElaborazione());
		
		return response;
	}
	
	@Operation(name = "annullamentoProposta")
	public ContabiliaOutputElaboraAttiAmministrativi annullamentoProposta(ContabiliaElaboraAttiAmministrativiRequest record) {
		// mapping per parametri input da bean Auriga a bean WS
		ElaboraAttiAmministrativiRequest input = mapper.fromContabiliaElaboraAttiAmministrativi(record);
		
		// chiamata WS elabora atti amministrativi
		OutputElaboraAttiAmministrativi output = service.annullamentoProposta(input);
		
		// mapping response da bean WS a bean Auriga
		ContabiliaOutputElaboraAttiAmministrativi response = mapper.outputElaboraAttiAmministrativiToContabiliaOutputElaboraAttiAmministrativi(output);
		
		logger.info("Chiamata servizio WS annullamentoProposta: - esito: " + response.getEntry().isResponseElaborazione() + 
					" - " + response.getEntry().getEsitoElaborazione() + 
					" - codice: " + response.getEntry().getCodiceElaborazione() +
					" - messaggio: " + response.getEntry().getMessaggioElaborazione());
		
		return response;
	}
	
	@Operation(name = "creaLiquidazione")
	public ContabiliaOutputElaboraAttiAmministrativi creaLiquidazione(ContabiliaElaboraAttiAmministrativiRequest record) {
		// mapping per parametri input da bean Auriga a bean WS
		ElaboraAttiAmministrativiRequest input = mapper.fromContabiliaElaboraAttiAmministrativi(record);
		
		// chiamata WS elabora atti amministrativi
		OutputElaboraAttiAmministrativi output = service.creaLiquidazione(input);
		
		// mapping response da bean WS a bean Auriga
		ContabiliaOutputElaboraAttiAmministrativi response = mapper.outputElaboraAttiAmministrativiToContabiliaOutputElaboraAttiAmministrativi(output);
		
		logger.info("Chiamata servizio WS creaLiquidazione: - esito: " + response.getEntry().isResponseElaborazione() + 
					" - " + response.getEntry().getEsitoElaborazione() + 
					" - codice: " + response.getEntry().getCodiceElaborazione() +
					" - messaggio: " + response.getEntry().getMessaggioElaborazione());
		
		return response;
	}

}
