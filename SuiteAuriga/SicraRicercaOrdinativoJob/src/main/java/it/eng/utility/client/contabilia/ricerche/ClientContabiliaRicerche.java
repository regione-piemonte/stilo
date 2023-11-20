/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.xml.ws.BindingProvider;

import org.apache.log4j.Logger;

import it.csi.siac.integ.data._1.Accertamento;
import it.csi.siac.integ.data._1.Impegno;
import it.csi.siac.ricerche.svc._1.RicercaAccertamento;
import it.csi.siac.ricerche.svc._1.RicercaAccertamentoResponse;
import it.csi.siac.ricerche.svc._1.RicercaImpegno;
import it.csi.siac.ricerche.svc._1.RicercaImpegnoResponse;
import it.csi.siac.ricerche.svc._1_0.RicercaService;
import it.eng.utility.client.contabilia.CustomPropertyPlaceholderConfigurer;
import it.eng.utility.client.contabilia.Util;
import it.eng.utility.client.contabilia.ricerche.data.OutputRicercaAccertamento;
import it.eng.utility.client.contabilia.ricerche.data.OutputRicercaImpegno;
import it.eng.utility.client.contabilia.ricerche.data.PropertyRicercheWS;
import it.eng.utility.client.contabilia.ricerche.data.RicercaAccertamentoRequest;
import it.eng.utility.client.contabilia.ricerche.data.RicercaImpegnoRequest;

public class ClientContabiliaRicerche {
	
	public static final String BEAN_ID = "clientContabiliaRicerche";
	private static final Logger logger = Logger.getLogger(ClientContabiliaRicerche.class);
	private RicercaService proxy;
	private CustomPropertyPlaceholderConfigurer property;
	private Integer numElementiPagina;
	private String contabiliaAccertamentoFruitore;
	private String contabiliaImpegnoFruitore;
	private String contabiliaAccertamentoEnte;
	private String contabiliaImpegnoEnte;
	
	/*
	 * Metodo che richiama il servizio WS ricercaAccertamento
	 */
	public OutputRicercaAccertamento ricercaAccertamento(final RicercaAccertamentoRequest input) {
		OutputRicercaAccertamento result = new OutputRicercaAccertamento();
		RicercaAccertamentoResponse response = null;
		
		try {
			// parametri di input
			PropertyRicercheWS prop = setParamInputAccertamento(input);
			RicercaAccertamento param = prop.getAccertamento();
			
			// controllo parametri di input obbligatori
			boolean checkInput = ValidazioneRequestRicerche.checkInputRicercaAccertamento(param);
			
			logger.info("Chiamata WS ricercaAccertamento parametri" + 
					" - ente: " + param.getCodiceEnte() +
					" - fruitore: " + param.getCodiceFruitore() +
					" - annoBilancio: " + param.getAnnoBilancio() +
					" - codiceTipoStruttura: " + param.getCodiceTipoProvvedimento() +
					" - codiceStruttura:" + param.getCodiceStruttura() +
					" - annoProvvedimento: " + param.getAnnoProvvedimento() +
					" - codiceTipoProvvedimento: " + param.getCodiceTipoProvvedimento() +
					" - numeroProvvedimento: " + param.getNumeroProvvedimento());
			
			if (checkInput) {
				// valorizzazione endpoint da file di configurazione
				setEndpointProxy(prop.getEndpoint());
				
				// chiamata al servizio ricercaAccertamento
				RicercaAccertamentoResponse responseAccertamento = proxy.ricercaAccertamento(param);
				logger.info("Chiamata servizio ricercaAccertamento pagina: 1");
				
				List<Accertamento> accertamentoList = new ArrayList<Accertamento>();
				accertamentoList.addAll(responseAccertamento.getAccertamenti());
				
				// calcolo numero di pagine
				Integer totRisultati = 0;
				Integer numPagine = 0;
				if (responseAccertamento.getTotaleRisultati() != null) {
					totRisultati = responseAccertamento.getTotaleRisultati();
					
					if (totRisultati % numElementiPagina == 0) {
						numPagine = totRisultati / numElementiPagina;
					} else {
						numPagine = totRisultati / numElementiPagina + 1;
					}
				}
				
				logger.info("Risultati trovati: " + totRisultati + " - totale numero pagine: " + numPagine);
				
				if (numPagine > 1) {
					// per ottenere tutti i risultati occorre una chiamata per ogni pagina
					for (int i=2; i<=numPagine; i++) {
						input.setNumeroPagina(i);
						
						// chiamata al servizio ricercaAccertamento
						RicercaAccertamentoResponse responseAccertamentoPag= proxy.ricercaAccertamento(param);
						accertamentoList.addAll(responseAccertamentoPag.getAccertamenti());
						
						logger.info("Chiamata servizio ricercaAccertamento pagina: " + i);
					}
				}
				
				response = new RicercaAccertamentoResponse();
				response.setTotaleRisultati(responseAccertamento.getTotaleRisultati());
				response.setEnte(responseAccertamento.getEnte());
				response.getErrori().addAll(responseAccertamento.getErrori());
				response.setEsito(responseAccertamento.getEsito());
				response.getMessaggi().addAll(responseAccertamento.getMessaggi());
				response.getAccertamenti().addAll(accertamentoList);
				
				result.setOk(true);
			}
			else {
				result.setOk(false);
				result.setMessaggio("Parametri obbligatori in input mancanti");
			}
		} catch (Exception e) {
			Util.aggiornaEsito(result, e);
			
			final String msg = "Fallita chiamata ricercaAccertamento";
			logger.error(e.getMessage());
			
			result.setOk(false);
			result.setMessaggio(msg);
		}
		
		result.setEntry(response);
		return result;
	}
	
	/*
	 * Metodo che richiama il servizio WS ricercaImpegno
	 */
	public OutputRicercaImpegno ricercaImpegno(final RicercaImpegnoRequest input) {
		OutputRicercaImpegno result = new OutputRicercaImpegno();
		RicercaImpegnoResponse response = null;
		
		try {
			// parametri di input
			PropertyRicercheWS prop = setParamInputImpegno(input);
			RicercaImpegno param = prop.getImpegno();
			
			// controllo parametri di input obbligatori
			boolean checkInput = ValidazioneRequestRicerche.checkInputRicercaImpegno(param);
			
			logger.info("Chiamata WS ricercaImpegno parametri" + 
					" - ente: " + param.getCodiceEnte() +
					" - fruitore: " + param.getCodiceFruitore() +
					" - annoBilancio: " + param.getAnnoBilancio() +
					" - codiceTipoStruttura: " + param.getCodiceTipoProvvedimento() +
					" - codiceStruttura:" + param.getCodiceStruttura() +
					" - annoProvvedimento: " + param.getAnnoProvvedimento() +
					" - codiceTipoProvvedimento: " + param.getCodiceTipoProvvedimento() +
					" - numeroProvvedimento: " + param.getNumeroProvvedimento());
			
			if (checkInput) {
				// valorizzazione endpoint da file di configurazione
				setEndpointProxy(prop.getEndpoint());
				
				// chiamata al servizio ricercaImpegno
				RicercaImpegnoResponse responseImpegno = proxy.ricercaImpegno(param);
				logger.info("Chiamata servizio ricercaImpegno pagina: 1");
				
				List<Impegno> impegnoList = new ArrayList<Impegno>();
				impegnoList.addAll(responseImpegno.getImpegni());
				
				// calcolo numero di pagine
				Integer totRisultati = 0;
				Integer numPagine = 0;
				if (responseImpegno.getTotaleRisultati() != null) {
					totRisultati = responseImpegno.getTotaleRisultati();
					if (totRisultati % numElementiPagina == 0) {
						numPagine = totRisultati / numElementiPagina;
					} else {
						numPagine = totRisultati / numElementiPagina + 1;
					}
				}
				
				logger.info("Risultati trovati: " + totRisultati + " - totale numero pagine: " + numPagine);
				
				if (numPagine > 1) {
					// per ottenere tutti i risultati occorre una chiamata per ogni pagina
					for (int i=2; i<=numPagine; i++) {
						input.setNumeroPagina(i);
						
						// chiamata al servizio ricercaImpegno
						RicercaImpegnoResponse responseImpegnoPag= proxy.ricercaImpegno(param);
						impegnoList.addAll(responseImpegnoPag.getImpegni());
						
						logger.info("Chiamata servizio ricercaImpegno pagina: " + i);
					}
				}
				
				response = new RicercaImpegnoResponse();
				response.setTotaleRisultati(responseImpegno.getTotaleRisultati());
				response.setEnte(responseImpegno.getEnte());
				response.getErrori().addAll(responseImpegno.getErrori());
				response.setEsito(responseImpegno.getEsito());
				response.getMessaggi().addAll(responseImpegno.getMessaggi());
				response.getImpegni().addAll(impegnoList);
				
				result.setOk(true);
			}
			else {
				result.setOk(false);
				result.setMessaggio("Parametri obbligatori in input mancanti");
			}
		} catch (Exception e) {
			Util.aggiornaEsito(result, e);
			
			final String msg = "Fallita chiamata ricercaImpegno";
			logger.error(msg, e);
			
			result.setOk(false);
			result.setMessaggio(e.getMessage());
		}
		
		result.setEntry(response);
		return result;
	}
	
	public RicercaService getProxy() {
		return proxy;
	}
	
	public void setProxy(RicercaService proxy) {
		this.proxy = proxy;
	}
	
	public CustomPropertyPlaceholderConfigurer getProperty() {
		return property;
	}

	public void setProperty(CustomPropertyPlaceholderConfigurer property) {
		this.property = property;
	}
	
	public Integer getNumElementiPagina() {
		return numElementiPagina;
	}
	
	public void setNumElementiPagina(Integer numElementiPagina) {
		this.numElementiPagina = numElementiPagina;
	}
	
	public String getContabiliaAccertamentoFruitore() {
		return contabiliaAccertamentoFruitore;
	}
	
	public void setContabiliaAccertamentoFruitore(String contabiliaAccertamentoFruitore) {
		this.contabiliaAccertamentoFruitore = contabiliaAccertamentoFruitore;
	}
	
	public String getContabiliaImpegnoFruitore() {
		return contabiliaImpegnoFruitore;
	}
	
	public void setContabiliaImpegnoFruitore(String contabiliaImpegnoFruitore) {
		this.contabiliaImpegnoFruitore = contabiliaImpegnoFruitore;
	}
	
	public String getContabiliaAccertamentoEnte() {
		return contabiliaAccertamentoEnte;
	}
	
	public void setContabiliaAccertamentoEnte(String contabiliaAccertamentoEnte) {
		this.contabiliaAccertamentoEnte = contabiliaAccertamentoEnte;
	}
	
	public String getContabiliaImpegnoEnte() {
		return contabiliaImpegnoEnte;
	}
	
	public void setContabiliaImpegnoEnte(String contabiliaImpegnoEnte) {
		this.contabiliaImpegnoEnte = contabiliaImpegnoEnte;
	}
	
	
	/*
	 * Metodo che mappa tutte le properties del file di configurazione
	 */
	private Map<String, String> getProperties() {
		Map<String, String> properties = property.getResolvedProps();
		
		return properties;
	}
	
	/*
	 * Metodo per restituire i parametri di input da inviare al servizio WS ricercaAccertameto
	 */
	private PropertyRicercheWS setParamInputAccertamento(RicercaAccertamentoRequest input) {
		PropertyRicercheWS result = new PropertyRicercheWS();
		
		RicercaAccertamento accertamento = new RicercaAccertamento();
		accertamento.setAnnoAccertamento(input.getAnnoAccertamento());
		accertamento.setNumeroAccertamento(input.getNumeroAccertamento());
		accertamento.setAnnoProvvedimento(input.getAnnoProvvedimento());
		accertamento.setCodiceStruttura(input.getCodiceStruttura());
		accertamento.setCodiceTipoProvvedimento(input.getCodiceTipoProvvedimento());
		accertamento.setCodiceTipoStruttura(input.getCodiceTipoStruttura());
		accertamento.setNumeroProvvedimento(input.getNumeroProvvedimento());
		accertamento.setNumeroElementiPerPagina(numElementiPagina);
		accertamento.setNumeroPagina(1);
		
		if (input.getAnnoBilancio() != null) {
			accertamento.setAnnoBilancio(input.getAnnoBilancio());
		}
		else {
			accertamento.setAnnoBilancio(Calendar.getInstance().get(Calendar.YEAR));
		}
		
		// fruitore ed ente di default
		accertamento.setCodiceFruitore(contabiliaAccertamentoFruitore);
		accertamento.setCodiceEnte(contabiliaAccertamentoEnte);
		String endpointWs = null;
		
		if (input.getIdSpAoo() != null) {
			String idSpAoo = input.getIdSpAoo();
			
			// acquisizione properties da file di configurazione
			Map<String, String> properties = getProperties();
			
			if (properties != null) {
				// fruitore specializzato
				if (properties.containsKey("contabilia.fruitore." + idSpAoo)) {
					accertamento.setCodiceFruitore(properties.get("contabilia.fruitore." + idSpAoo));
				}
				
				// ente specializzato
				if (properties.containsKey("contabilia.ente." + idSpAoo)) {
					accertamento.setCodiceEnte(properties.get("contabilia.ente." + idSpAoo));
				}
				
				// endpoint specializzato
				if (properties.containsKey("contabilia.ricerca.endpoint." + idSpAoo)) {
					endpointWs = properties.get("contabilia.ricerca.endpoint." + idSpAoo);
				}
			}
		}
		result.setAccertamento(accertamento);
		result.setEndpoint(endpointWs);
		
		return result;
	}
	
	/*
	 * Metodo per restituire i parametri di input da inviare al servizio WS ricercaImpegno
	 */
	private PropertyRicercheWS setParamInputImpegno(RicercaImpegnoRequest input) {
		PropertyRicercheWS result = new PropertyRicercheWS();
		
		RicercaImpegno impegno = new RicercaImpegno();
		impegno.setAnnoImpegno(input.getAnnoImpegno());
		impegno.setNumeroImpegno(input.getNumeroImpegno());
		impegno.setAnnoProvvedimento(input.getAnnoProvvedimento());
		impegno.setCodiceStruttura(input.getCodiceStruttura());
		impegno.setCodiceTipoProvvedimento(input.getCodiceTipoProvvedimento());
		impegno.setCodiceTipoStruttura(input.getCodiceTipoStruttura());
		impegno.setNumeroProvvedimento(input.getNumeroProvvedimento());
		impegno.setNumeroElementiPerPagina(numElementiPagina);
		impegno.setNumeroPagina(1);
		
		if (input.getAnnoBilancio() != null) {
			impegno.setAnnoBilancio(input.getAnnoBilancio());
		}
		else {
			impegno.setAnnoBilancio(Calendar.getInstance().get(Calendar.YEAR));
		}
		
		// fruitore ed ente di default
		impegno.setCodiceFruitore(contabiliaImpegnoFruitore);
		impegno.setCodiceEnte(contabiliaImpegnoEnte);
		String endpointWs = null;
		
		if (input.getIdSpAoo() != null) {
			String idSpAoo = input.getIdSpAoo();
			
			// acquisizione properties da file di configurazione
			Map<String, String> properties = getProperties();
			
			if (properties != null) {
				// fruitore specializzato
				if (properties.containsKey("contabilia.fruitore." + idSpAoo)) {
					impegno.setCodiceFruitore(properties.get("contabilia.fruitore." + idSpAoo));
				}
				
				// ente specializzato
				if (properties.containsKey("contabilia.ente." + idSpAoo)) {
					impegno.setCodiceEnte(properties.get("contabilia.ente." + idSpAoo));
				}
				
				// endpoint specializzato
				if (properties.containsKey("contabilia.ricerca.endpoint." + idSpAoo)) {
					endpointWs = properties.get("contabilia.ricerca.endpoint." + idSpAoo);
				}
			}
		}
		result.setImpegno(impegno);
		result.setEndpoint(endpointWs);
		
		return result;
	}
	
	/*
	 * Metodo per settare endpoint specializzato all'oggetto ProxyClient
	 * se null endpoint di default impostato in file xml di configurazione
	 */
	private void setEndpointProxy(String endpointWs) {
		if (endpointWs != null) {
			logger.info("Endpoint specializzato per servizi ricerca: " + endpointWs);
			
			BindingProvider bp = (BindingProvider)proxy;
			bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointWs);
		}
	}
	
	/*
	private RicercaService getRicercaService(String endpointWs) throws IOException {
		URL url = new URL(endpointWs);
		
		QName qname = new QName("http://siac.csi.it/ricerche/svc/1.0", "RicercaService");
		Service service = Service.create(url, qname);
		RicercaService port = service.getPort(RicercaService.class);
		
		return port;
	}
	*/
}
