/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


import java.util.List;

import org.apache.log4j.Logger;

import it.eng.core.annotation.Operation;
import it.eng.core.annotation.Service;
import it.eng.document.function.bean.acta.ActaInputGetClassificazioneEstesa;
import it.eng.document.function.bean.acta.ActaInputGetDestinatariSmistamento;
import it.eng.document.function.bean.acta.ActaInputGetFascicoloDossierInVoceTitolario;
import it.eng.document.function.bean.acta.ActaOutputGetClassificazioneEstesa;
import it.eng.document.function.bean.acta.ActaOutputGetDestinatariSmistamento;
import it.eng.document.function.bean.acta.ActaOutputGetFascicoloDossierInVoceTitolario;
import it.eng.utility.client.acta.ClientActa;
import it.eng.utility.client.acta.bean.NodoSmistamento;
import it.eng.utility.data.Output;

@Service(name = "EsportazioneDocActaImpl")
public class EsportazioneDocActaImpl implements EsportazioneDocActa {

	private static Logger logger = Logger.getLogger(EsportazioneDocActaImpl.class);
	private static final ClientActa client;
	private static final EsportazioneDocActaMapperImpl mapper;

	static {
		client = new ClientActa();
		try {
			client.init();
		} catch (Exception e) {
			logger.error("Fallita inizializzazione del client Acta.");
			throw new ExceptionInInitializerError(e);
		}
		mapper = new EsportazioneDocActaMapperImpl();
	}
	
	@Override
	@Operation(name = "getClassificazioneEstesa")
	public ActaOutputGetClassificazioneEstesa getClassificazioneEstesa(ActaInputGetClassificazioneEstesa input) {
		final Output<Boolean> out = client.verificaIndiceClassificazioneEstesa(input.getAooCode(), input.getStructurCode(), input.getIndiceClassificazioneEstesa());
		final ActaOutputGetClassificazioneEstesa output = new ActaOutputGetClassificazioneEstesa();
		output.setEsitoChiamata(mapper.outcomeToEsitoChiamata(out.getOutcome()));
		output.setPresenzaIndiceClassificazione(out.getData());
		return output;
	}
	
	@Override
	@Operation(name = "getFascicoloDossierInVoceTitolario")
	public ActaOutputGetFascicoloDossierInVoceTitolario getFascicoloDossierInVoceTitolario(ActaInputGetFascicoloDossierInVoceTitolario input) {
		final Output<String> out = client.ottieniFascicoloDossierInVoceTitolario(input.getAooCode(), 
				input.getStructurCode(), input.getDescrizioneVoceTitolario(), input.getCodiceFascicoloDossier(), 
				input.getSuffissoCodiceFascicoloDossier(), input.getNumeroSottofascicolo());
		final ActaOutputGetFascicoloDossierInVoceTitolario output = new ActaOutputGetFascicoloDossierInVoceTitolario();
		output.setEsitoChiamata(mapper.outcomeToEsitoChiamata(out.getOutcome()));
		output.setIdFascicoloDossier(out.getData());
		return output;
	}
	
	@Override
	@Operation(name = "getDestinatariSmistamento")
	public ActaOutputGetDestinatariSmistamento getDestinatariSmistamento(ActaInputGetDestinatariSmistamento input) {
		final Output<List<NodoSmistamento>> out = client.ottieniDestinatariSmistamento(input.getAooCode(), input.getStructurCode());
		final ActaOutputGetDestinatariSmistamento output = new ActaOutputGetDestinatariSmistamento();
		output.setEsitoChiamata(mapper.outcomeToEsitoChiamata(out.getOutcome()));
		output.setNodoSmistamento(mapper.nodiSmistamentoToActaNodiSmistamento(out.getData()));
		return output;
	}

}