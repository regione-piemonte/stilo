package it.eng.utility.sicra.contabilita;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import javax.xml.rpc.ServiceException;

import org.apache.axis.AxisProperties;
import org.apache.log4j.Logger;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;

import it.eng.utility.data.Output;
import it.eng.utility.sicra.UtilSicra;
import it.eng.utility.sicra.contabilita.wsdl.CmnWSSGatewayServiceLocator;
import it.eng.utility.sicra.contabilita.wsdl.CmnWSSGatewaySoapBindingStub;
import it.eng.utility.sicra.contabilita.xsd.set_movimenti_atto.Richiesta;
import it.eng.utility.sicra.contabilita.xsd.set_movimenti_atto.Risultato;

public final class ClientSicra {

	private static final Logger logger = Logger.getLogger(ClientSicra.class);
	// ====================================
	private static final String AUTENTICAZIONE_SEMANTICA = "semanthic_switch_v2";
	private static final String NOME_SERVIZIO_RICERCA_VOCI_BILANCIO = "fde.stilo.ricerca_budget_bilancio";
	private static final String NOME_SERVIZIO_SET_MOVIMENTI_ATTO = "fde.stilo.inserimento_impegni";
	private static final String NOME_SERVIZIO_SET_ESECUTIVITA_ATTO = "fde.stilo.rendi_esecutivi_impegni";
	private static final String NOME_SERVIZIO_ARCHIVIA_ATTO = "fde.stilo.cancella_impegni_atto";
	private static final String NOME_SERVIZIO_AGGIORNA_RIF_ATTO_LIQUIDAZIONE = "fde.stilo.aggiorna_riferimenti_atto_liquidazione";
	private static final String NOME_SERVIZIO_RICERCA_ANAGRAFICA = "fde.stilo.ricerca_anagrafica";
	private static final String NOME_SERVIZIO_RICERCA_ATTO_LIQUIDAZIONE = "fde.stilo.ricerca_atti_liquidazione";
	private static final String NOME_SERVIZIO_RICERCA_ORDINATIVO_ATTO_LIQUIDAZIONE = "fde.stilo.ricerca_ordinativi_liquidazione";
	// ===============================================
	private static final String NAMESPACE_SERVIZIO_AGGIORNA_RIF_ATTO_LIQUIDAZIONE = "http://sicraweb/AggiornaRifAttoLiquidazione";
	private static final String NAMESPACE_SERVIZIO_RICERCA_ANAGRAFICA = "http://sicraweb/RicercaAnagrafica";
	private static final String NAMESPACE_SERVIZIO_RICERCA_ATTO_LIQUIDAZIONE = "http://sicraweb/RicercAttiLiquidazione";
	private static final String NAMESPACE_SERVIZIO_RICERCA_ORDINATIVO_ATTO_LIQUIDAZIONE = "http://sicraweb/RicercaOrdinativoAttoLiquidazione";
	// ===============================================
	//private static final int DEFAULT_TIMEOUT = 60000;
	private static final int DEFAULT_TIMEOUT = 300000;
	private static final Charset DEFAULT_XML_ENCODING = StandardCharsets.ISO_8859_1;
	private static final String OK = "OK";
	private static final String LOG_ERROR_MESSAGE = "Errore di comunicazione con Sicra";
	// =============================================
	private String proxyServer;
	private String proxyPort;
	private int timeout = DEFAULT_TIMEOUT;
	private String alias;
	private Charset xmlEncoding = DEFAULT_XML_ENCODING;
	// ==============================================
	private Marshaller marshallerRicercaVociBilancio;
	private Marshaller marshallerSetMovimentiAtto;
	private Marshaller marshallerSetEsecutivitaAtto;
	private Marshaller marshallerArchiviaAtto;
	private Marshaller marshallerAggiornaRifAttoLiquidazione;
	private Marshaller marshallerRicercaAnagrafica;
	private Marshaller marshallerRicercaAttoLiquidazione;
	private Marshaller marshallerRicercaOrdinativoAttoLiquidazione;
	private Unmarshaller unmarshallerRicercaVociBilancio;
	private Unmarshaller unmarshallerSetMovimentiAtto;
	private Unmarshaller unmarshallerSetEsecutivitaAtto;
	private Unmarshaller unmarshallerArchiviaAtto;
	private Unmarshaller unmarshallerAggiornaRifAttoLiquidazione;
	private Unmarshaller unmarshallerRicercaAnagrafica;
	private Unmarshaller unmarshallerRicercaAttoLiquidazione;
	private Unmarshaller unmarshallerRicercaOrdinativoAttoLiquidazione;
	private CmnWSSGatewayServiceLocator service;

	public ClientSicra() {
		// AxisProperties.setProperty(EngineConfigurationFactoryDefault.OPTION_CLIENT_CONFIG_FILE, "client-sicra-config.wsdd");
	}

	public Output<it.eng.utility.sicra.contabilita.xsd.ricerca_voci_bilancio.Risultato> ricercaVociBilancio(
			it.eng.utility.sicra.contabilita.xsd.ricerca_voci_bilancio.Richiesta richiesta) {
		return ricercaVociBilancio(alias, richiesta);
	}

	public Output<it.eng.utility.sicra.contabilita.xsd.set_movimenti_atto.Risultato> setMovimentiAtto(
			it.eng.utility.sicra.contabilita.xsd.set_movimenti_atto.Richiesta richiesta) {
		return setMovimentiAtto(alias, richiesta);
	}

	public Output<it.eng.utility.sicra.contabilita.xsd.set_esecutivita_atto.Risultato> setEsecutivitaAtto(
			it.eng.utility.sicra.contabilita.xsd.set_esecutivita_atto.Richiesta richiesta) {
		return setEsecutivitaAtto(alias, richiesta);
	}

	public Output<it.eng.utility.sicra.contabilita.xsd.set_movimenti_atto.Risultato> cancellazioneMovimentiAtto(
			it.eng.utility.sicra.contabilita.xsd.set_movimenti_atto.Richiesta richiesta) {
		return cancellazioneMovimentiAtto(alias, richiesta);
	}

	public Output<it.eng.utility.sicra.contabilita.xsd.set_movimenti_atto.Risultato> inserimentoMovimentiAtto(
			it.eng.utility.sicra.contabilita.xsd.set_movimenti_atto.Richiesta richiesta) {
		return inserimentoMovimentiAtto(alias, richiesta);
	}

	public Output<it.eng.utility.sicra.contabilita.xsd.set_movimenti_atto.Risultato> aggiornamentoMovimentiAtto(
			it.eng.utility.sicra.contabilita.xsd.set_movimenti_atto.Richiesta richiesta) {
		return aggiornamentoMovimentiAtto(alias, richiesta);
	}

	public Output<it.eng.utility.sicra.contabilita.xsd.archivia_atto.Risultato> archiviaAtto(
			it.eng.utility.sicra.contabilita.xsd.archivia_atto.Richiesta richiesta) {
		return archiviaAtto(alias, richiesta);
	}
	
	public Output<it.eng.utility.sicra.contabilita.xsd.aggiorna_rif_atto_liquidazione.Risultato> aggiornaRifAttoLiquidazione(
			it.eng.utility.sicra.contabilita.xsd.aggiorna_rif_atto_liquidazione.Richiesta richiesta) {
		return aggiornaRifAttoLiquidazione(alias, richiesta);
	}
	
	public Output<it.eng.utility.sicra.contabilita.xsd.ricerca_anagrafica.Risultato> ricercaAnagrafica(
			it.eng.utility.sicra.contabilita.xsd.ricerca_anagrafica.Richiesta richiesta) {
		return ricercaAnagrafica(alias, richiesta);
	}
	
	public Output<it.eng.utility.sicra.contabilita.xsd.ricerca_atto_liquidazione.Risultato> ricercaAttoLiquidazione(
			it.eng.utility.sicra.contabilita.xsd.ricerca_atto_liquidazione.Richiesta richiesta) {
		return ricercaAttoLiquidazione(alias, richiesta);
	}
	
	public Output<it.eng.utility.sicra.contabilita.xsd.ricerca_ordinativo_atto_liquidazione.Risultato> ricercaOrdinativoAttoLiquidazione(
			it.eng.utility.sicra.contabilita.xsd.ricerca_ordinativo_atto_liquidazione.Richiesta richiesta) {
		return ricercaOrdinativoAttoLiquidazione(alias, richiesta);
	}

	public Output<it.eng.utility.sicra.contabilita.xsd.ricerca_voci_bilancio.Risultato> ricercaVociBilancio(String alias,
			it.eng.utility.sicra.contabilita.xsd.ricerca_voci_bilancio.Richiesta richiesta) {
		logger.info("ricercaVociBilancio() start.");
		final long t0 = System.currentTimeMillis();
		final Output<it.eng.utility.sicra.contabilita.xsd.ricerca_voci_bilancio.Risultato> output = new Output<>();
		String fileRisposta = null;
		try {
			Integer flagCup = null;
			if (richiesta.getFlraggruppacup() != null) {
				flagCup = richiesta.getFlraggruppacup();
			}
			else {
				flagCup = 0;
			}
			richiesta.setFlraggruppacup(flagCup);
			
			logger.info("Flag per CUP: " + richiesta.getFlraggruppacup());
			
			// final String fileRichiesta = UtilSicra.toXMLCDATA(marshallerRicercaVociBilancio, richiesta);
			final String fileRichiesta = UtilSicra.toXML(marshallerRicercaVociBilancio, richiesta);
			logger.info("ricercaVociBilancio_RICHIESTA: \n" + fileRichiesta);
			final String[] args = new String[] { AUTENTICAZIONE_SEMANTICA, alias, NOME_SERVIZIO_RICERCA_VOCI_BILANCIO, fileRichiesta };
			fileRisposta = getStub().service(args);
			logger.debug("ricercaVociBilancio_RISPOSTA: \n" + fileRisposta);
		} catch (Exception e) {
			logger.error(LOG_ERROR_MESSAGE, e);
			UtilSicra.aggiornaEsito(output.getOutcome(), e);
			return output;
		}
		it.eng.utility.sicra.contabilita.xsd.ricerca_voci_bilancio.Risultato risposta = null;
		try {
			risposta = (it.eng.utility.sicra.contabilita.xsd.ricerca_voci_bilancio.Risultato) UtilSicra.toObject(unmarshallerRicercaVociBilancio, fileRisposta,
					xmlEncoding);
		} catch (Exception e) {
			final String msg = "Fallita deserializzazione della risposta XML";
			logger.error(msg, e);
			output.getOutcome().setMessaggio(msg);
			return output;
		}
		final it.eng.utility.sicra.contabilita.xsd.ricerca_voci_bilancio.Risultato.Esito esito = risposta.getEsito();
		if (esito != null) {
			output.getOutcome().setOk(it.eng.utility.sicra.contabilita.xsd.ricerca_voci_bilancio.Risultato.Esito.TipoEsito.OK == esito.getTipo());
			output.getOutcome().setMessaggio(esito.getDescrizione());
		}
		output.setData(risposta);
		final long t1 = System.currentTimeMillis();
		logger.info("ricercaVociBilancio() end: " + ((t1 - t0) / 1000) + " s");
		return output;
	}

	public Output<it.eng.utility.sicra.contabilita.xsd.set_movimenti_atto.Risultato> cancellazioneMovimentiAtto(String alias,
			it.eng.utility.sicra.contabilita.xsd.set_movimenti_atto.Richiesta richiesta) {
		logger.info("cancellazioneMovimentiAtto() start.");
		for (Richiesta.Impegno impegno : richiesta.getImpegno()) {
			impegno.getTestata().setAzione(ConstantSicra.AZIONE_CANCELLA_MOVIMENTO);
		}
		// jdk8: richiesta.getImpegno().forEach(impegno -> impegno.getTestata().setAzione(ConstantSicra.AZIONE_CANCELLA_MOVIMENTO));
		return setMovimentiAtto(alias, richiesta);
	}

	public Output<it.eng.utility.sicra.contabilita.xsd.set_movimenti_atto.Risultato> inserimentoMovimentiAtto(String alias,
			it.eng.utility.sicra.contabilita.xsd.set_movimenti_atto.Richiesta richiesta) {
		logger.info("inserimentoMovimentiAtto() start.");
		for (Richiesta.Impegno impegno : richiesta.getImpegno()) {
			impegno.getTestata().setAzione(ConstantSicra.AZIONE_CREA_MOVIMENTO);
		}
		// jdk8: richiesta.getImpegno().forEach(impegno -> impegno.getTestata().setAzione(ConstantSicra.AZIONE_CREA_MOVIMENTO));
		return setMovimentiAtto(alias, richiesta);
	}

	public Output<it.eng.utility.sicra.contabilita.xsd.set_movimenti_atto.Risultato> aggiornamentoMovimentiAtto(String alias,
			it.eng.utility.sicra.contabilita.xsd.set_movimenti_atto.Richiesta richiesta) {
		logger.info("aggiornamentoMovimentiAtto() start.");
		Output<it.eng.utility.sicra.contabilita.xsd.set_movimenti_atto.Risultato> output = cancellazioneMovimentiAtto(alias, richiesta);
		if (!output.getOutcome().isOk()) {
			return output;
		}
		if (Risultato.Messaggi.TipoMessaggi.OK != output.getData().getMessaggi().getTipo()) {
			return output;
		}
		return inserimentoMovimentiAtto(alias, richiesta);
	}

	public Output<it.eng.utility.sicra.contabilita.xsd.set_movimenti_atto.Risultato> setMovimentiAtto(String alias,
			it.eng.utility.sicra.contabilita.xsd.set_movimenti_atto.Richiesta richiesta) {
		logger.info("setMovimentiAtto() start.");
		final long t0 = System.currentTimeMillis();
		final Output<it.eng.utility.sicra.contabilita.xsd.set_movimenti_atto.Risultato> output = new Output<>();
		String fileRisposta = null;
		try {
			final String fileRichiesta = UtilSicra.toXML(marshallerSetMovimentiAtto, richiesta);
			logger.info("setMovimentiAtto_RICHIESTA: \n" + fileRichiesta);
			final String[] args = new String[] { AUTENTICAZIONE_SEMANTICA, alias, NOME_SERVIZIO_SET_MOVIMENTI_ATTO, fileRichiesta };
			fileRisposta = getStub().service(args);
			logger.info("setMovimentiAtto_RISPOSTA: \n" + fileRisposta);
		} catch (Exception e) {
			logger.error(LOG_ERROR_MESSAGE, e);
			UtilSicra.aggiornaEsito(output.getOutcome(), e);
			return output;
		}
		it.eng.utility.sicra.contabilita.xsd.set_movimenti_atto.Risultato risposta = null;
		try {
			risposta = (it.eng.utility.sicra.contabilita.xsd.set_movimenti_atto.Risultato) UtilSicra.toObject(unmarshallerSetMovimentiAtto, fileRisposta,
					xmlEncoding);
		} catch (Exception e) {
			final String msg = "Fallita deserializzazione della risposta XML";
			logger.error(msg, e);
			output.getOutcome().setMessaggio(msg);
			return output;
		}
		output.getOutcome().setOk(true);
		output.setData(risposta);
		final long t1 = System.currentTimeMillis();
		logger.info("setMovimentiAtto() end: " + ((t1 - t0) / 1000) + " s");
		return output;
	}

	public Output<it.eng.utility.sicra.contabilita.xsd.set_esecutivita_atto.Risultato> setEsecutivitaAtto(String alias,
			it.eng.utility.sicra.contabilita.xsd.set_esecutivita_atto.Richiesta richiesta) {
		logger.info("setEsecutivitaAtto() start.");
		final long t0 = System.currentTimeMillis();
		final Output<it.eng.utility.sicra.contabilita.xsd.set_esecutivita_atto.Risultato> output = new Output<>();
		String fileRisposta = null;
		try {
			final String fileRichiesta = UtilSicra.toXML(marshallerSetEsecutivitaAtto, richiesta);
			logger.info("setEsecutivitaAtto_RICHIESTA: \n" + fileRichiesta);
			final String[] args = new String[] { AUTENTICAZIONE_SEMANTICA, alias, NOME_SERVIZIO_SET_ESECUTIVITA_ATTO, fileRichiesta };
			fileRisposta = getStub().service(args);
			logger.info("setEsecutivitaAtto_RISPOSTA: \n" + fileRisposta);
		} catch (Exception e) {
			logger.error(LOG_ERROR_MESSAGE, e);
			UtilSicra.aggiornaEsito(output.getOutcome(), e);
			return output;
		}
		it.eng.utility.sicra.contabilita.xsd.set_esecutivita_atto.Risultato risposta = null;
		try {
			risposta = (it.eng.utility.sicra.contabilita.xsd.set_esecutivita_atto.Risultato) UtilSicra.toObject(unmarshallerSetEsecutivitaAtto, fileRisposta,
					xmlEncoding);
		} catch (Exception e) {
			final String msg = "Fallita deserializzazione della risposta XML";
			logger.error(msg, e);
			output.getOutcome().setMessaggio(msg);
			return output;
		}
		output.getOutcome().setOk(true);
		output.setData(risposta);
		final long t1 = System.currentTimeMillis();
		logger.info("setEsecutivitaAtto() end: " + ((t1 - t0) / 1000) + " s");
		return output;
	}
	
	public Output<it.eng.utility.sicra.contabilita.xsd.archivia_atto.Risultato> archiviaAtto(String alias,
			it.eng.utility.sicra.contabilita.xsd.archivia_atto.Richiesta richiesta) {
		logger.info("archiviaAtto() start.");
		final long t0 = System.currentTimeMillis();
		final Output<it.eng.utility.sicra.contabilita.xsd.archivia_atto.Risultato> output = new Output<>();
		String fileRisposta = null;
		try {
			final String fileRichiesta = UtilSicra.toXML(marshallerArchiviaAtto, richiesta);
			logger.info("archiviaAtto_RICHIESTA: \n" + fileRichiesta);
			final String[] args = new String[] { AUTENTICAZIONE_SEMANTICA, alias, NOME_SERVIZIO_ARCHIVIA_ATTO, fileRichiesta };
			fileRisposta = getStub().service(args);
			logger.info("archiviaAtto_RISPOSTA: \n" + fileRisposta);
		} catch (Exception e) {
			logger.error(LOG_ERROR_MESSAGE, e);
			UtilSicra.aggiornaEsito(output.getOutcome(), e);
			return output;
		}
		it.eng.utility.sicra.contabilita.xsd.archivia_atto.Risultato risposta = null;
		try {
			risposta = (it.eng.utility.sicra.contabilita.xsd.archivia_atto.Risultato) UtilSicra.toObject(unmarshallerArchiviaAtto, fileRisposta, xmlEncoding);
		} catch (Exception e) {
			final String msg = "Fallita deserializzazione della risposta XML";
			logger.error(msg, e);
			output.getOutcome().setMessaggio(msg);
			return output;
		}
		output.getOutcome().setOk(true);
		output.setData(risposta);
		final long t1 = System.currentTimeMillis();
		logger.info("archiviaAtto() end: " + ((t1 - t0) / 1000) + " s");
		return output;
	}
	
	public Output<it.eng.utility.sicra.contabilita.xsd.aggiorna_rif_atto_liquidazione.Risultato> aggiornaRifAttoLiquidazione(String alias,
			it.eng.utility.sicra.contabilita.xsd.aggiorna_rif_atto_liquidazione.Richiesta richiesta) {
		logger.info("aggiornaRifAttoLiquidazione() start.");
		final long t0 = System.currentTimeMillis();
		final Output<it.eng.utility.sicra.contabilita.xsd.aggiorna_rif_atto_liquidazione.Risultato> output = new Output<>();
		String fileRisposta = null;
		try {
			//final String fileRichiesta = UtilSicra.toXMLNoNameSpace(marshallerAggiornaRifAttoLiquidazione, richiesta, NAMESPACE_SERVIZIO_AGGIORNA_RIF_ATTO_LIQUIDAZIONE);
			final String fileRichiesta = UtilSicra.toXML(marshallerAggiornaRifAttoLiquidazione, richiesta);
			logger.info("aggiornaRifAttoLiquidazione_RICHIESTA: \n" + fileRichiesta);
			final String[] args = new String[] { AUTENTICAZIONE_SEMANTICA, alias, NOME_SERVIZIO_AGGIORNA_RIF_ATTO_LIQUIDAZIONE, fileRichiesta };
			fileRisposta = getStub().service(args);
			logger.info("aggiornaRifAttoLiquidazione_RISPOSTA: \n" + fileRisposta);
		} catch (Exception e) {
			logger.error(LOG_ERROR_MESSAGE, e);
			UtilSicra.aggiornaEsito(output.getOutcome(), e);
			return output;
		}
		it.eng.utility.sicra.contabilita.xsd.aggiorna_rif_atto_liquidazione.Risultato risposta = null;
		try {
			//risposta = (it.eng.utility.sicra.contabilita.xsd.aggiorna_rif_atto_liquidazione.Risultato) UtilSicra.toObjectNamespace(unmarshallerAggiornaRifAttoLiquidazione, fileRisposta,
			//		xmlEncoding, NAMESPACE_SERVIZIO_AGGIORNA_RIF_ATTO_LIQUIDAZIONE);
			risposta = (it.eng.utility.sicra.contabilita.xsd.aggiorna_rif_atto_liquidazione.Risultato) UtilSicra.toObject(unmarshallerAggiornaRifAttoLiquidazione, fileRisposta, xmlEncoding);
		} catch (Exception e) {
			final String msg = "Fallita deserializzazione della risposta XML";
			logger.error(msg, e);
			output.getOutcome().setMessaggio(msg);
			return output;
		}
		
		output.getOutcome().setOk(true);
		output.setData(risposta);
		final long t1 = System.currentTimeMillis();
		logger.info("aggiornaRifAttoLiquidazione() end: " + ((t1 - t0) / 1000) + " s");
		return output;
	}
	
	public Output<it.eng.utility.sicra.contabilita.xsd.ricerca_anagrafica.Risultato> ricercaAnagrafica(String alias,
			it.eng.utility.sicra.contabilita.xsd.ricerca_anagrafica.Richiesta richiesta) {
		logger.info("ricercaAnagrafica() start.");
		final long t0 = System.currentTimeMillis();
		final Output<it.eng.utility.sicra.contabilita.xsd.ricerca_anagrafica.Risultato> output = new Output<>();
		String fileRisposta = null;
		try {
			//final String fileRichiesta = UtilSicra.toXMLNoNameSpace(marshallerRicercaAnagrafica, richiesta, NAMESPACE_SERVIZIO_RICERCA_ANAGRAFICA);
			final String fileRichiesta = UtilSicra.toXML(marshallerRicercaAnagrafica, richiesta);
			logger.info("ricercaAnagrafica_RICHIESTA: \n" + fileRichiesta);
			final String[] args = new String[] { AUTENTICAZIONE_SEMANTICA, alias, NOME_SERVIZIO_RICERCA_ANAGRAFICA, fileRichiesta };
			fileRisposta = getStub().service(args);
			logger.info("ricercaAnagrafica_RISPOSTA: \n" + fileRisposta);
		} catch (Exception e) {
			logger.error(LOG_ERROR_MESSAGE, e);
			UtilSicra.aggiornaEsito(output.getOutcome(), e);
			return output;
		}
		it.eng.utility.sicra.contabilita.xsd.ricerca_anagrafica.Risultato risposta = null;
		try {
			//risposta = (it.eng.utility.sicra.contabilita.xsd.ricerca_anagrafica.Risultato) UtilSicra.toObjectNamespace(unmarshallerRicercaAnagrafica, fileRisposta,
			//		xmlEncoding, NAMESPACE_SERVIZIO_RICERCA_ANAGRAFICA);
			risposta = (it.eng.utility.sicra.contabilita.xsd.ricerca_anagrafica.Risultato) UtilSicra.toObject(unmarshallerRicercaAnagrafica, fileRisposta, xmlEncoding);
		} catch (Exception e) {
			//final String msg = "Fallita deserializzazione della risposta XML";
			logger.error(e.getMessage());
			//output.getOutcome().setMessaggio(msg);
			return output;
		}
		final it.eng.utility.sicra.contabilita.xsd.ricerca_anagrafica.Risultato.Esito esito = risposta.getEsito();
		if (esito != null) {
			output.getOutcome().setOk(it.eng.utility.sicra.contabilita.xsd.ricerca_anagrafica.Risultato.Esito.TipoEsito.OK == esito.getTipo());
			output.getOutcome().setMessaggio(esito.getDescrizione());
		}
		output.setData(risposta);
		final long t1 = System.currentTimeMillis();
		logger.info("ricercaAnagrafica() end: " + ((t1 - t0) / 1000) + " s");
		return output;
	}
	
	public Output<it.eng.utility.sicra.contabilita.xsd.ricerca_atto_liquidazione.Risultato> ricercaAttoLiquidazione(String alias,
			it.eng.utility.sicra.contabilita.xsd.ricerca_atto_liquidazione.Richiesta richiesta) {
		logger.info("ricercaAttoLiquidazione() start.");
		final long t0 = System.currentTimeMillis();
		final Output<it.eng.utility.sicra.contabilita.xsd.ricerca_atto_liquidazione.Risultato> output = new Output<>();
		String fileRisposta = null;
		try {
			//final String fileRichiesta = UtilSicra.toXMLNoNameSpace(marshallerRicercaAttoLiquidazione, richiesta, NAMESPACE_SERVIZIO_RICERCA_ATTO_LIQUIDAZIONE);
			final String fileRichiesta = UtilSicra.toXML(marshallerRicercaAttoLiquidazione, richiesta);
			logger.info("ricercaAttoLiquidazione_RICHIESTA: \n" + fileRichiesta);
			final String[] args = new String[] { AUTENTICAZIONE_SEMANTICA, alias, NOME_SERVIZIO_RICERCA_ATTO_LIQUIDAZIONE, fileRichiesta };
			fileRisposta = getStub().service(args);
			logger.info("ricercaAttoLiquidazione_RISPOSTA: \n" + fileRisposta);
		} catch (Exception e) {
			logger.error(LOG_ERROR_MESSAGE, e);
			UtilSicra.aggiornaEsito(output.getOutcome(), e);
			return output;
		}
		it.eng.utility.sicra.contabilita.xsd.ricerca_atto_liquidazione.Risultato risposta = null;
		try {
			//risposta = (it.eng.utility.sicra.contabilita.xsd.ricerca_atto_liquidazione.Risultato) UtilSicra.toObjectNamespace(unmarshallerRicercaAttoLiquidazione, fileRisposta,
			//		xmlEncoding, NAMESPACE_SERVIZIO_RICERCA_ATTO_LIQUIDAZIONE);
			risposta = (it.eng.utility.sicra.contabilita.xsd.ricerca_atto_liquidazione.Risultato) UtilSicra.toObject(unmarshallerRicercaAttoLiquidazione, fileRisposta, xmlEncoding);
		} catch (Exception e) {
			final String msg = "Fallita deserializzazione della risposta XML";
			logger.error(msg, e);
			output.getOutcome().setMessaggio(msg);
			return output;
		}
		final it.eng.utility.sicra.contabilita.xsd.ricerca_atto_liquidazione.Risultato.Esito esito = risposta.getEsito();
		if (esito != null) {
			output.getOutcome().setOk(it.eng.utility.sicra.contabilita.xsd.ricerca_atto_liquidazione.Risultato.Esito.TipoEsito.OK == esito.getTipo());
			output.getOutcome().setMessaggio(esito.getDescrizione());
		}
		output.setData(risposta);
		final long t1 = System.currentTimeMillis();
		logger.info("ricercaAttoLiquidazione() end: " + ((t1 - t0) / 1000) + " s");
		return output;
	}
	
	public Output<it.eng.utility.sicra.contabilita.xsd.ricerca_ordinativo_atto_liquidazione.Risultato> ricercaOrdinativoAttoLiquidazione(String alias,
			it.eng.utility.sicra.contabilita.xsd.ricerca_ordinativo_atto_liquidazione.Richiesta richiesta) {
		logger.info("ricercaOrdinativoAttoLiquidazione() start.");
		final long t0 = System.currentTimeMillis();
		final Output<it.eng.utility.sicra.contabilita.xsd.ricerca_ordinativo_atto_liquidazione.Risultato> output = new Output<>();
		String fileRisposta = null;
		try {
			//final String fileRichiesta = UtilSicra.toXMLNoNameSpace(marshallerRicercaOrdinativoAttoLiquidazione, richiesta, NAMESPACE_SERVIZIO_RICERCA_ORDINATIVO_ATTO_LIQUIDAZIONE);
			final String fileRichiesta = UtilSicra.toXML(marshallerRicercaOrdinativoAttoLiquidazione, richiesta);
			logger.info("ricercaOrdinativoAttoLiquidazione_RICHIESTA: \n" + fileRichiesta);
			final String[] args = new String[] { AUTENTICAZIONE_SEMANTICA, alias, NOME_SERVIZIO_RICERCA_ORDINATIVO_ATTO_LIQUIDAZIONE, fileRichiesta };
			fileRisposta = getStub().service(args);
			logger.info("ricercaOrdinativoAttoLiquidazione_RISPOSTA: \n" + fileRisposta);
		} catch (Exception e) {
			logger.error(LOG_ERROR_MESSAGE, e);
			UtilSicra.aggiornaEsito(output.getOutcome(), e);
			return output;
		}
		it.eng.utility.sicra.contabilita.xsd.ricerca_ordinativo_atto_liquidazione.Risultato risposta = null;
		try {
			//risposta = (it.eng.utility.sicra.contabilita.xsd.ricerca_ordinativo_atto_liquidazione.Risultato) UtilSicra.toObjectNamespace(unmarshallerRicercaOrdinativoAttoLiquidazione, fileRisposta,
			//		xmlEncoding, NAMESPACE_SERVIZIO_RICERCA_ORDINATIVO_ATTO_LIQUIDAZIONE);
			risposta = (it.eng.utility.sicra.contabilita.xsd.ricerca_ordinativo_atto_liquidazione.Risultato) UtilSicra.toObject(unmarshallerRicercaOrdinativoAttoLiquidazione, fileRisposta, xmlEncoding);
		} catch (Exception e) {
			final String msg = "Fallita deserializzazione della risposta XML";
			logger.error(msg, e);
			output.getOutcome().setMessaggio(msg);
			return output;
		}
		final it.eng.utility.sicra.contabilita.xsd.ricerca_ordinativo_atto_liquidazione.Risultato.Esito esito = risposta.getEsito();
		if (esito != null) {
			output.getOutcome().setOk(it.eng.utility.sicra.contabilita.xsd.ricerca_ordinativo_atto_liquidazione.Risultato.Esito.TipoEsito.OK == esito.getTipo());
			output.getOutcome().setMessaggio(esito.getDescrizione());
		}
		output.setData(risposta);
		final long t1 = System.currentTimeMillis();
		logger.info("ricercaOrdinativoAttoLiquidazione() end: " + ((t1 - t0) / 1000) + " s");
		return output;
	}
	
	public void setService(CmnWSSGatewayServiceLocator service) {
		this.service = service;
	}

	public void setProxyServer(String server) {
		this.proxyServer = server;
		AxisProperties.setProperty("http.proxyHost", server);
	}

	public void setProxyPort(String port) {
		this.proxyPort = port;
		AxisProperties.setProperty("http.proxyPort", port);
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public void setXmlEncoding(Charset xmlEncoding) {
		this.xmlEncoding = xmlEncoding;
	}

	public void setMarshallerRicercaVociBilancio(Marshaller marshallerRicercaVociBilancio) {
		this.marshallerRicercaVociBilancio = marshallerRicercaVociBilancio;
	}

	public void setMarshallerSetMovimentiAtto(Marshaller marshallerSetMovimentiAtto) {
		this.marshallerSetMovimentiAtto = marshallerSetMovimentiAtto;
	}

	public void setMarshallerSetEsecutivitaAtto(Marshaller marshallerSetEsecutivitaAtto) {
		this.marshallerSetEsecutivitaAtto = marshallerSetEsecutivitaAtto;
	}

	public void setMarshallerArchiviaAtto(Marshaller marshallerArchiviaAtto) {
		this.marshallerArchiviaAtto = marshallerArchiviaAtto;
	}

	public void setMarshallerAggiornaRifAttoLiquidazione(Marshaller marshallerAggiornaRifAttoLiquidazione) {
		this.marshallerAggiornaRifAttoLiquidazione = marshallerAggiornaRifAttoLiquidazione;
	}
	
	public void setMarshallerRicercaAnagrafica(Marshaller marshallerRicercaAnagrafica) {
		this.marshallerRicercaAnagrafica = marshallerRicercaAnagrafica;
	}

	public void setMarshallerRicercaAttoLiquidazione(Marshaller marshallerRicercaAttoLiquidazione) {
		this.marshallerRicercaAttoLiquidazione = marshallerRicercaAttoLiquidazione;
	}

	public void setMarshallerRicercaOrdinativoAttoLiquidazione(Marshaller marshallerRicercaOrdinativoAttoLiquidazione) {
		this.marshallerRicercaOrdinativoAttoLiquidazione = marshallerRicercaOrdinativoAttoLiquidazione;
	}

	public void setUnmarshallerRicercaVociBilancio(Unmarshaller unmarshallerRicercaVociBilancio) {
		this.unmarshallerRicercaVociBilancio = unmarshallerRicercaVociBilancio;
	}

	public void setUnmarshallerSetMovimentiAtto(Unmarshaller unmarshallerSetMovimentiAtto) {
		this.unmarshallerSetMovimentiAtto = unmarshallerSetMovimentiAtto;
	}

	public void setUnmarshallerSetEsecutivitaAtto(Unmarshaller unmarshallerSetEsecutivitaAtto) {
		this.unmarshallerSetEsecutivitaAtto = unmarshallerSetEsecutivitaAtto;
	}

	public void setUnmarshallerArchiviaAtto(Unmarshaller unmarshallerArchiviaAtto) {
		this.unmarshallerArchiviaAtto = unmarshallerArchiviaAtto;
	}

	public void setUnmarshallerAggiornaRifAttoLiquidazione(Unmarshaller unmarshallerAggiornaRifAttoLiquidazione) {
		this.unmarshallerAggiornaRifAttoLiquidazione = unmarshallerAggiornaRifAttoLiquidazione;
	}

	public void setUnmarshallerRicercaAnagrafica(Unmarshaller unmarshallerRicercaAnagrafica) {
		this.unmarshallerRicercaAnagrafica = unmarshallerRicercaAnagrafica;
	}

	public void setUnmarshallerRicercaAttoLiquidazione(Unmarshaller unmarshallerRicercaAttoLiquidazione) {
		this.unmarshallerRicercaAttoLiquidazione = unmarshallerRicercaAttoLiquidazione;
	}
	
	public void setUnmarshallerRicercaOrdinativoAttoLiquidazione(Unmarshaller unmarshallerRicercaOrdinativoAttoLiquidazione) {
		this.unmarshallerRicercaOrdinativoAttoLiquidazione = unmarshallerRicercaOrdinativoAttoLiquidazione;
	}

	private CmnWSSGatewaySoapBindingStub getStub() throws ServiceException {
		final CmnWSSGatewaySoapBindingStub stub = (CmnWSSGatewaySoapBindingStub) service.getCmnWSSGateway();
		stub.setTimeout(timeout);
		return stub;
	}

}// ClientSicra
