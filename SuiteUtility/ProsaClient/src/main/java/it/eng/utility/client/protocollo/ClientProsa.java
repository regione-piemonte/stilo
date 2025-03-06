package it.eng.utility.client.protocollo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.xml.rpc.ServiceException;

import org.apache.axis.AxisProperties;
import org.apache.log4j.Logger;

import it.eng.utility.client.prosa.Allegato;
import it.eng.utility.client.prosa.Assegnazione;
import it.eng.utility.client.prosa.DatiProtocollo;
import it.eng.utility.client.prosa.DocumentoProtocollato;
import it.eng.utility.client.prosa.ImmagineDocumentale;
import it.eng.utility.client.prosa.ProtocolloWebServiceServiceLocator;
import it.eng.utility.client.prosa.ProtocolloWebServiceSoapBindingStub;
import it.eng.utility.client.prosa.Ricerca;
import it.eng.utility.client.prosa.VoceTitolario;
import it.eng.utility.client.prosa.WSAuthentication;
import it.eng.utility.client.prosa.WSEsito;
import it.eng.utility.client.protocollo.data.Output;

public final class ClientProsa {

	private static final Logger logger = Logger.getLogger(ClientProsa.class);
	private static final String OK = "000";
	private static final int DEFAULT_TIMEOUT = 60000;
	private static final String CONFIG_FILE = "client-prosa.properties";
	private static final String KEY_ENDPOINT = "prosa.endpoint";
	private static final String KEY_TIMEOUT = "prosa.timeout";
	private static final String KEY_APPLICAZIONE = "prosa.applicazione";
	private static final String KEY_ENTE = "prosa.ente";
	private static final String KEY_AOO = "prosa.aoo";
	private static final String KEY_USERNAME = "prosa.username";
	private static final String KEY_PASSWORD = "prosa.password";
	private static final String KEY_PROXY_SERVER = "prosa.proxy.server";
	private static final String KEY_PROXY_PORT = "prosa.proxy.port";
	private static final String LOG_ERROR_MESSAGE = "Errore di comunicazione con Prosa";
	private ProtocolloWebServiceServiceLocator service;
	private WSAuthentication wsAuthentication;
	private Properties properties;
	private int timeout = DEFAULT_TIMEOUT;

	public void init() throws IOException {
		properties = retrieveProperties(CONFIG_FILE);
		if (logger.isDebugEnabled())
			logger.debug("CLIENT CONFIGURATION\n" + properties);
		service = createService(properties.getProperty(KEY_ENDPOINT));
		wsAuthentication = createWSAuthentication(properties);
		setProxy(properties.getProperty(KEY_PROXY_SERVER), properties.getProperty(KEY_PROXY_PORT));
		timeout = getTimeout();
	}

	public Output<DocumentoProtocollato> protocolla(DocumentoProtocollato documentoProtocollato) {
		logger.info("protocolla() start.");
		final long t0 = System.currentTimeMillis();
		final Output<DocumentoProtocollato> output = new Output<>();
		DocumentoProtocollato resp = null;
		try {
			resp = getStub().protocolla(wsAuthentication, documentoProtocollato);
		} catch (Exception e) {
			logger.error(LOG_ERROR_MESSAGE, e);
			Util.aggiornaEsito(output, e);
			return output;
		}
		final WSEsito esito = resp.getEsito();
		if (esito != null) {
			output.setOk(OK.equals(esito.getCodiceEsito()));
			output.setMessaggio(esito.getDescrizioneEsito());
		}
		output.setResult(resp);
		final long t1 = System.currentTimeMillis();
		logger.info("protocolla() end: " + ((t1 - t0) / 1000) + " s");
		return output;
	}

	public Output<Allegato> inserisciAllegato(Allegato allegato) {
		logger.info("inserisciAllegato() start.");
		final long t0 = System.currentTimeMillis();
		final Output<Allegato> output = new Output<>();
		Allegato resp = null;
		try {
			resp = getStub().inserisciAllegato(wsAuthentication, allegato);
		} catch (Exception e) {
			logger.error(LOG_ERROR_MESSAGE, e);
			Util.aggiornaEsito(output, e);
			return output;
		}
		final WSEsito esito = resp.getEsito();
		if (esito != null) {
			output.setOk(OK.equals(esito.getCodiceEsito()));
			output.setMessaggio(esito.getDescrizioneEsito());
		}
		output.setResult(resp);
		final long t1 = System.currentTimeMillis();
		logger.info("inserisciAllegato() end: " + ((t1 - t0) / 1000) + " s");
		return output;
	}

	public Output<Allegato[]> getAllegati(long idProfilo) {
		logger.info("getAllegati() start.");
		final long t0 = System.currentTimeMillis();
		final Output<Allegato[]> output = new Output<>();
		Allegato[] resp = null;
		try {
			resp = getStub().getAllegati(wsAuthentication, idProfilo);
		} catch (Exception e) {
			logger.error(LOG_ERROR_MESSAGE, e);
			Util.aggiornaEsito(output, e);
			return output;
		}
		if (resp != null && resp.length > 0) {
			final WSEsito esito0 = resp[0].getEsito();
			if (esito0 != null) {
				output.setOk(OK.equals(esito0.getCodiceEsito()));
				output.setMessaggio(esito0.getDescrizioneEsito());
			}
		}
		output.setResult(resp);
		final long t1 = System.currentTimeMillis();
		logger.info("getAllegati() end: " + ((t1 - t0) / 1000) + " s");
		return output;
	}

	public Output<Allegato> getAllegato(long idAllegato) {
		logger.info("getAllegato() start.");
		final long t0 = System.currentTimeMillis();
		final Output<Allegato> output = new Output<>();
		Allegato resp = null;
		try {
			resp = getStub().getAllegato(wsAuthentication, idAllegato);
		} catch (Exception e) {
			logger.error(LOG_ERROR_MESSAGE, e);
			Util.aggiornaEsito(output, e);
			return output;
		}
		final WSEsito esito = resp.getEsito();
		if (esito != null) {
			output.setOk(OK.equals(esito.getCodiceEsito()));
			output.setMessaggio(esito.getDescrizioneEsito());
		}
		output.setResult(resp);
		final long t1 = System.currentTimeMillis();
		logger.info("getAllegato() end: " + ((t1 - t0) / 1000) + " s");
		return output;
	}

	public Output<byte[]> getContenutoAllegato(long idAllegato) {
		logger.info("getContenutoAllegato() start.");
		final long t0 = System.currentTimeMillis();
		final Output<byte[]> output = new Output<>();
		byte[] resp = null;
		try {
			resp = getStub().getContenutoAllegato(wsAuthentication, idAllegato);
		} catch (Exception e) {
			logger.error(LOG_ERROR_MESSAGE, e);
			Util.aggiornaEsito(output, e);
			return output;
		}
		output.setOk(resp != null);
		output.setResult(resp);
		final long t1 = System.currentTimeMillis();
		logger.info("getContenutoAllegato() end: " + ((t1 - t0) / 1000) + " s");
		return output;
	}

	public Output<Allegato> rimuoviAllegato(long idAllegato) {
		logger.info("rimuoviAllegato() start.");
		final long t0 = System.currentTimeMillis();
		final Output<Allegato> output = new Output<>();
		Allegato resp = null;
		try {
			resp = getStub().rimuoviAllegato(wsAuthentication, idAllegato);
		} catch (Exception e) {
			logger.error(LOG_ERROR_MESSAGE, e);
			Util.aggiornaEsito(output, e);
			return output;
		}
		final WSEsito esito = resp.getEsito();
		if (esito != null) {
			output.setOk(OK.equals(esito.getCodiceEsito()));
			output.setMessaggio(esito.getDescrizioneEsito());
		}
		output.setResult(resp);
		final long t1 = System.currentTimeMillis();
		logger.info("rimuoviAllegato() end: " + ((t1 - t0) / 1000) + " s");
		return output;
	}

	public Output<ImmagineDocumentale> inserisciContenuto(ImmagineDocumentale imgDoc, boolean timbro) {
		logger.info("inserisciContenuto() start.");
		final long t0 = System.currentTimeMillis();
		final Output<ImmagineDocumentale> output = new Output<>();
		ImmagineDocumentale resp = null;
		try {
			resp = getStub().inserisciContenuto(wsAuthentication, imgDoc, timbro);
		} catch (Exception e) {
			logger.error(LOG_ERROR_MESSAGE, e);
			Util.aggiornaEsito(output, e);
			return output;
		}
		final WSEsito esito = resp.getEsito();
		if (esito != null) {
			output.setOk(OK.equals(esito.getCodiceEsito()));
			output.setMessaggio(esito.getDescrizioneEsito());
		}
		output.setResult(resp);
		final long t1 = System.currentTimeMillis();
		logger.info("inserisciContenuto() end: " + ((t1 - t0) / 1000) + " s");
		return output;
	}

	public Output<ImmagineDocumentale> recuperaContenuto(long idDoc) {
		logger.info("recuperaContenuto() start.");
		final long t0 = System.currentTimeMillis();
		final Output<ImmagineDocumentale> output = new Output<>();
		ImmagineDocumentale resp = null;
		try {
			resp = getStub().recuperaContenuto(wsAuthentication, idDoc);
		} catch (Exception e) {
			logger.error(LOG_ERROR_MESSAGE, e);
			Util.aggiornaEsito(output, e);
			return output;
		}
		final WSEsito esito = resp.getEsito();
		if (esito != null) {
			output.setOk(OK.equals(esito.getCodiceEsito()));
			output.setMessaggio(esito.getDescrizioneEsito());
		}
		output.setResult(resp);
		final long t1 = System.currentTimeMillis();
		logger.info("recuperaContenuto() end: " + ((t1 - t0) / 1000) + " s");
		return output;
	}

	public Output<byte[]> getContenutoDocumento(long idDoc) {
		logger.info("getContenutoDocumento() start.");
		final long t0 = System.currentTimeMillis();
		final Output<byte[]> output = new Output<>();
		byte[] resp = null;
		try {
			resp = getStub().getContenutoDocumento(wsAuthentication, idDoc);
		} catch (Exception e) {
			logger.error(LOG_ERROR_MESSAGE, e);
			Util.aggiornaEsito(output, e);
			return output;
		}
		output.setOk(true);
		output.setResult(resp);
		final long t1 = System.currentTimeMillis();
		logger.info("getContenutoDocumento() end: " + ((t1 - t0) / 1000) + " s");
		return output;
	}

	public Output<DocumentoProtocollato[]> ricercaProtocolli(Ricerca ricerca) {
		logger.info("ricercaProtocolli() start.");
		final long t0 = System.currentTimeMillis();
		final Output<DocumentoProtocollato[]> output = new Output<>();
		DocumentoProtocollato[] resp = null;
		try {
			resp = getStub().ricercaProtocolli(wsAuthentication, ricerca);
		} catch (Exception e) {
			logger.error(LOG_ERROR_MESSAGE, e);
			Util.aggiornaEsito(output, e);
			return output;
		}
		if (resp != null && resp.length > 0) {
			final WSEsito esito0 = resp[0].getEsito();
			if (esito0 != null) {
				output.setOk(OK.equals(esito0.getCodiceEsito()));
				output.setMessaggio(esito0.getDescrizioneEsito());
			}
		}
		output.setResult(resp);
		final long t1 = System.currentTimeMillis();
		logger.info("ricercaProtocolli() end: " + ((t1 - t0) / 1000) + " s");
		return output;
	}

	public Output<VoceTitolario[]> ricercaTitolarioPerCodiceDescrizione(VoceTitolario voce) {
		logger.info("ricercaTitolarioPerCodiceDescrizione() start.");
		final long t0 = System.currentTimeMillis();
		final Output<VoceTitolario[]> output = new Output<>();
		VoceTitolario[] resp = null;
		try {
			resp = getStub().ricercaTitolarioPerCodiceDescrizione(wsAuthentication, voce);
		} catch (Exception e) {
			logger.error(LOG_ERROR_MESSAGE, e);
			Util.aggiornaEsito(output, e);
			return output;
		}
		if (resp != null && resp.length > 0) {
			final WSEsito esito0 = resp[0].getEsito();
			if (esito0 != null) {
				output.setOk(OK.equals(esito0.getCodiceEsito()));
				output.setMessaggio(esito0.getDescrizioneEsito());
			}
		}
		output.setResult(resp);
		final long t1 = System.currentTimeMillis();
		logger.info("ricercaTitolarioPerCodiceDescrizione() end: " + ((t1 - t0) / 1000) + " s");
		return output;
	}

	public Output<Assegnazione> assegna(Assegnazione assegnazione) {
		logger.info("assegna() start.");
		final long t0 = System.currentTimeMillis();
		final Output<Assegnazione> output = new Output<>();
		Assegnazione resp = null;
		try {
			resp = getStub().assegna(wsAuthentication, assegnazione);
		} catch (Exception e) {
			logger.error(LOG_ERROR_MESSAGE, e);
			Util.aggiornaEsito(output, e);
			return output;
		}
		final WSEsito esito = resp.getEsito();
		if (esito != null) {
			output.setOk(OK.equals(esito.getCodiceEsito()));
			output.setMessaggio(esito.getDescrizioneEsito());
		}
		output.setResult(resp);
		final long t1 = System.currentTimeMillis();
		logger.info("assegna() end: " + ((t1 - t0) / 1000) + " s");
		return output;
	}

	public Output<WSEsito> testLogin() {
		logger.info("testLogin() start.");
		final long t0 = System.currentTimeMillis();
		final Output<WSEsito> output = new Output<>();
		WSEsito resp = null;
		try {
			resp = getStub().testLogin(wsAuthentication);
		} catch (Exception e) {
			logger.error(LOG_ERROR_MESSAGE, e);
			Util.aggiornaEsito(output, e);
			return output;
		}
		output.setOk(OK.equals(resp.getCodiceEsito()));
		output.setMessaggio(resp.getDescrizioneEsito());
		output.setResult(resp);
		final long t1 = System.currentTimeMillis();
		logger.info("testLogin() end: " + ((t1 - t0) / 1000) + " s");
		return output;
	}

	public Output<WSEsito> testStato() {
		logger.info("testStato() start.");
		final long t0 = System.currentTimeMillis();
		final Output<WSEsito> output = new Output<>();
		WSEsito resp = null;
		try {
			resp = getStub().testStato(wsAuthentication);
		} catch (Exception e) {
			logger.error(LOG_ERROR_MESSAGE, e);
			Util.aggiornaEsito(output, e);
			return output;
		}
		output.setOk(OK.equals(resp.getCodiceEsito()));
		output.setMessaggio(resp.getDescrizioneEsito());
		output.setResult(resp);
		final long t1 = System.currentTimeMillis();
		logger.info("testStato() end: " + ((t1 - t0) / 1000) + " s");
		return output;
	}

	public Output<WSEsito> inserisciContenutoDaDocumentale(Long idProfiloDa, Long idProfiloA, boolean stampaTimbro) {
		logger.info("inserisciContenutoDaDocumentale() start.");
		final long t0 = System.currentTimeMillis();
		final Output<WSEsito> output = new Output<>();
		WSEsito resp = null;
		try {
			resp = getStub().inserisciContenutoDaDocumentale(wsAuthentication, idProfiloDa, idProfiloA, stampaTimbro);
		} catch (Exception e) {
			logger.error(LOG_ERROR_MESSAGE, e);
			Util.aggiornaEsito(output, e);
			return output;
		}
		output.setOk(OK.equals(resp.getCodiceEsito()));
		output.setMessaggio(resp.getDescrizioneEsito());
		output.setResult(resp);
		final long t1 = System.currentTimeMillis();
		logger.info("inserisciContenutoDaDocumentale() end: " + ((t1 - t0) / 1000) + " s");
		return output;
	}

	public Output<WSEsito> inserisciAllegatoZip(Allegato allegato) {
		logger.info("inserisciAllegatoZip() start.");
		final long t0 = System.currentTimeMillis();
		final Output<WSEsito> output = new Output<>();
		WSEsito resp = null;
		try {
			resp = getStub().inserisciAllegatoZip(wsAuthentication, allegato);
		} catch (Exception e) {
			logger.error(LOG_ERROR_MESSAGE, e);
			Util.aggiornaEsito(output, e);
			return output;
		}
		output.setOk(OK.equals(resp.getCodiceEsito()));
		output.setMessaggio(resp.getDescrizioneEsito());
		output.setResult(resp);
		final long t1 = System.currentTimeMillis();
		logger.info("inserisciAllegatoZip() end: " + ((t1 - t0) / 1000) + " s");
		return output;
	}

	public Output<WSEsito> inserisciAllegatoDaFascicolo(Long idProfilo, Allegato allegato) {
		logger.info("inserisciAllegatoDaFascicolo() start.");
		final long t0 = System.currentTimeMillis();
		final Output<WSEsito> output = new Output<>();
		WSEsito resp = null;
		try {
			resp = getStub().inserisciAllegatoDaFascicolo(wsAuthentication, idProfilo, allegato);
		} catch (Exception e) {
			logger.error(LOG_ERROR_MESSAGE, e);
			Util.aggiornaEsito(output, e);
			return output;
		}
		output.setOk(OK.equals(resp.getCodiceEsito()));
		output.setMessaggio(resp.getDescrizioneEsito());
		output.setResult(resp);
		final long t1 = System.currentTimeMillis();
		logger.info("inserisciAllegatoDaFascicolo() end: " + ((t1 - t0) / 1000) + " s");
		return output;
	}

	public Output<WSEsito> protocollaProfilo(DatiProtocollo datiProtocollo) {
		logger.info("protocollaProfilo() start.");
		final long t0 = System.currentTimeMillis();
		final Output<WSEsito> output = new Output<>();
		WSEsito resp = null;
		try {
			resp = getStub().protocollaProfilo(wsAuthentication, datiProtocollo);
		} catch (Exception e) {
			logger.error(LOG_ERROR_MESSAGE, e);
			Util.aggiornaEsito(output, e);
			return output;
		}
		output.setOk(OK.equals(resp.getCodiceEsito()));
		output.setMessaggio(resp.getDescrizioneEsito());
		output.setResult(resp);
		final long t1 = System.currentTimeMillis();
		logger.info("protocollaProfilo() end: " + ((t1 - t0) / 1000) + " s");
		return output;
	}

	public void setService(ProtocolloWebServiceServiceLocator service) {
		this.service = service;
	}

	public void setWsAuthentication(WSAuthentication wsAuthentication) {
		this.wsAuthentication = wsAuthentication;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public void setProxy(String server, String port) {
		AxisProperties.setProperty("http.proxyHost", server);
		AxisProperties.setProperty("http.proxyPort", port);
	}

	private Properties retrieveProperties(String fileName) throws IOException {
		// try (InputStream input = new FileInputStream("application.properties")) {
		try (InputStream input = getClass().getClassLoader().getResourceAsStream(fileName)) {
			if (input == null) {
				throw new FileNotFoundException("File di configurazione '" + fileName + "' non recuperato.");
			}
			Properties properties = new Properties();
			properties.load(input);
			return properties;
		}
	}

	private ProtocolloWebServiceServiceLocator createService(String address) {
		final ProtocolloWebServiceServiceLocator sl = new ProtocolloWebServiceServiceLocator();
		sl.setProtocolloWebServiceEndpointAddress(address);
		// sl.setProtocolloWebServiceWSDDServiceName(name);
		return sl;
	}

	private WSAuthentication createWSAuthentication(Properties prop) {
		final WSAuthentication auth = new WSAuthentication();
		auth.setApplicazione(prop.getProperty(KEY_APPLICAZIONE));
		auth.setEnte(prop.getProperty(KEY_ENTE));
		auth.setAoo(prop.getProperty(KEY_AOO));
		auth.setUsername(prop.getProperty(KEY_USERNAME));
		auth.setPassword(prop.getProperty(KEY_PASSWORD));
		return auth;
	}

	private ProtocolloWebServiceSoapBindingStub getStub() throws ServiceException {
		final ProtocolloWebServiceSoapBindingStub stub = (ProtocolloWebServiceSoapBindingStub) service.getProtocolloWebService();
		stub.setTimeout(timeout);
		return stub;
	}

	private int getTimeout() {
		int timeout = DEFAULT_TIMEOUT;
		if (properties != null) {
			timeout = Integer.parseInt(properties.getProperty(KEY_TIMEOUT, String.valueOf(DEFAULT_TIMEOUT)));
		}
		return timeout;
	}
}
