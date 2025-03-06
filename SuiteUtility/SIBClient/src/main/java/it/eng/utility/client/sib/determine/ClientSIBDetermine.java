package it.eng.utility.client.sib.determine;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.datamanagement.sib.webservice.integrazioneauriga.service.Esito;
import it.datamanagement.sib.webservice.integrazioneauriga.service.InputDettagliContabiliV0;
import it.datamanagement.sib.webservice.integrazioneauriga.service.IntegrazioneAurigaV0;
import it.datamanagement.sib.webservice.integrazioneauriga.service.OutputAggiornaAttoV0;
import it.datamanagement.sib.webservice.integrazioneauriga.service.OutputCreaPropostaV0;
import it.datamanagement.sib.webservice.integrazioneauriga.service.OutputDettagliContabiliV0;
import it.datamanagement.sib.webservice.integrazioneauriga.service.RecordAggiornaAttoV0;
import it.datamanagement.sib.webservice.integrazioneauriga.service.RecordCreaPropostaV0;
import it.datamanagement.sib.webservice.integrazioneauriga.service.RecordDettagliContabiliV0;
import it.datamanagement.sib.webservice.integrazioneauriga.service.RispostaCreaPropostaV0;
import it.eng.utility.client.sib.CredenzialiAccessoSIB;
import it.eng.utility.client.sib.Util;
import it.eng.utility.client.sib.determine.data.OutputAggiornaAtto;
import it.eng.utility.client.sib.determine.data.OutputCreaProposta;
import it.eng.utility.client.sib.determine.data.OutputElencoDettagliContabili;

public final class ClientSIBDetermine {

	public static final String BEAN_ID = "clientSIBDetermine";
	private static final Logger logger = LoggerFactory.getLogger(ClientSIBDetermine.class);
	private IntegrazioneAurigaV0 proxy;
	private CredenzialiAccessoSIB credentials;
	private int nroMaxChiamateElDettCont = 100;

	public OutputAggiornaAtto aggiornaAtto(final RecordAggiornaAttoV0 input) {
		if (credentials != null) {
			input.setEnte(credentials.getEnte());
			input.setCodiceUtente(credentials.getUtente());
			input.setPassword(credentials.getPassword());
		}
		final OutputAggiornaAtto output = new OutputAggiornaAtto();
		OutputAggiornaAttoV0 rispostaAggiornaAtto = null;
		try {
			rispostaAggiornaAtto = proxy.aggiornaAtto(input);
		} catch (Exception e) {
			Util.aggiornaEsito(output, e);
			logger.error("Errore di comunicazione con SIB", e);
			return output;
		}
		final Esito esito = rispostaAggiornaAtto.getEsito();
		output.setMessaggio(esito.getMessaggio());
		switch (esito.getEsito()) {
		case 0:
			final List<RecordAggiornaAttoV0> outputAggiornaAtto = rispostaAggiornaAtto.getRecordAggiornaAtto();
			output.setList(outputAggiornaAtto);
			output.setOk(true);
			break;
		default:
			final String messaggio = esito.getMessaggio();
			logger.warn("Errore restituito da SIB: " + messaggio);
			output.setOk(false);
			break;
		}
		return output;
	}// aggiornaAtto

	public OutputCreaProposta creaProposta(final RecordCreaPropostaV0 input) {
		if (credentials != null) {
			input.setEnte(credentials.getEnte());
			input.setCodiceUtente(credentials.getUtente());
			input.setPassword(credentials.getPassword());
		}
		final OutputCreaProposta output = new OutputCreaProposta();
		RispostaCreaPropostaV0 rispostaCreaProposta = null;
		try {
			rispostaCreaProposta = proxy.creaProposta(input);
		} catch (Exception e) {
			Util.aggiornaEsito(output, e);
			logger.error("Errore di comunicazione con SIB", e);
			return output;
		}
		final Esito esito = rispostaCreaProposta.getEsito();
		output.setMessaggio(esito.getMessaggio());
		switch (esito.getEsito()) {
		case 0:
			final OutputCreaPropostaV0 outputCreaProposta = rispostaCreaProposta.getOutputCreaProposta();
			final Long idSib = outputCreaProposta.getIdSib();
			output.setIdPropostaDetermina(idSib);
			output.setOk(true);
			break;
		default:
			final String messaggio = esito.getMessaggio();
			logger.warn("Errore restituito da SIB: " + messaggio);
			output.setOk(false);
			break;
		}
		return output;
	}// creaProposta

	public OutputElencoDettagliContabili elencoDettagliContabili_Completo(final InputDettagliContabiliV0 input) {
		if (credentials != null) {
			input.setEnte(credentials.getEnte());
			input.setCodiceUtente(credentials.getUtente());
			input.setPassword(credentials.getPassword());
		}
		final OutputElencoDettagliContabili output = new OutputElencoDettagliContabili();
		List<RecordDettagliContabiliV0> listOut = new ArrayList<RecordDettagliContabiliV0>();
		output.setList(listOut);
		boolean flagContinue = false;
		int count = 0;
		do {
			count++;
			if (count > nroMaxChiamateElDettCont) {
				logger.error("Interrompo per superamento limite di chiamate effettuate impostato a " + nroMaxChiamateElDettCont);
				flagContinue = false;
				break;
			}
			input.setNumeroBloccoInformazioni(count - 1);
			final OutputDettagliContabiliV0 rispostaElencoDettagliContabili = proxy.elencoDettagliContabili(input);
			final Esito esito = rispostaElencoDettagliContabili.getEsito();
			output.setMessaggio(esito.getMessaggio());
			switch (esito.getEsito()) {
			case 0:
				flagContinue = esito.getAltreInformazioni() > 0;
				final List<RecordDettagliContabiliV0> recordDettagliContabili = rispostaElencoDettagliContabili.getRecordDettagliContabili();
				listOut.addAll(recordDettagliContabili);
				output.setOk(true);
				break;
			default:
				flagContinue = false;
				final String messaggio = esito.getMessaggio();
				logger.warn("Errore riscontrato alla chiamata " + count + ": " + messaggio);
				output.setOk(false);
				break;
			}
		} while (flagContinue);

		logger.warn("Chiamate effettive effettuate: " + count);

		return output;
	}// elencoDettagliContabili_Completo

	public OutputElencoDettagliContabili elencoDettagliContabili(final InputDettagliContabiliV0 input) {
		if (credentials != null) {
			input.setEnte(credentials.getEnte());
			input.setCodiceUtente(credentials.getUtente());
			input.setPassword(credentials.getPassword());
		}
		final OutputElencoDettagliContabili output = new OutputElencoDettagliContabili();
		OutputDettagliContabiliV0 rispostaElencoDettagliContabili = null;
		try {
			rispostaElencoDettagliContabili = proxy.elencoDettagliContabiliCompleto(input);
		} catch (Exception e) {
			Util.aggiornaEsito(output, e);
			logger.error("Errore di comunicazione con SIB", e);
			return output;
		}
		final Esito esito = rispostaElencoDettagliContabili.getEsito();
		output.setMessaggio(esito.getMessaggio());
		switch (esito.getEsito()) {
		case 0:
			final List<RecordDettagliContabiliV0> recordDettagliContabili = rispostaElencoDettagliContabili.getRecordDettagliContabili();
			output.setList(recordDettagliContabili);
			output.setOk(true);
			break;
		default:
			final String messaggio = esito.getMessaggio();
			logger.warn("Errore restituito da SIB: " + messaggio);
			output.setOk(false);
			break;
		}
		return output;
	}// elencoDettagliContabili

	public IntegrazioneAurigaV0 getProxy() {
		return proxy;
	}

	public void setProxy(IntegrazioneAurigaV0 proxy) {
		this.proxy = proxy;
	}

	public CredenzialiAccessoSIB getCredentials() {
		return credentials;
	}

	public void setCredentials(CredenzialiAccessoSIB credentials) {
		this.credentials = credentials;
	}

	public void setNroMaxChiamateElDettCont(int nroMaxChiamateElDettCont) {
		this.nroMaxChiamateElDettCont = nroMaxChiamateElDettCont;
	}

}// ClientSIBDetermine
