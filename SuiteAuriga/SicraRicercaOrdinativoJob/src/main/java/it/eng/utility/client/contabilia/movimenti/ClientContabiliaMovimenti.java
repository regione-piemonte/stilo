/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Calendar;
import java.util.Map;
import java.util.Properties;

import javax.xml.ws.BindingProvider;

import org.apache.log4j.Logger;

import it.csi.siac.stilo.svc._1.RicercaMovimentoGestioneStilo;
import it.csi.siac.stilo.svc._1.RicercaMovimentoGestioneStiloResponse;
import it.csi.siac.stilo.svc._1_0.StiloService;
import it.eng.utility.client.contabilia.CustomPropertyPlaceholderConfigurer;
import it.eng.utility.client.contabilia.Util;
import it.eng.utility.client.contabilia.movimenti.data.OutputRicercaMovimentoGestione;
import it.eng.utility.client.contabilia.movimenti.data.PropertyStiloWS;
import it.eng.utility.client.contabilia.movimenti.data.RicercaMovimentoGestioneStiloRequest;

public class ClientContabiliaMovimenti {
	
	public static final String BEAN_ID = "clientContabiliaMovimenti";
	private static final Logger logger = Logger.getLogger(ClientContabiliaMovimenti.class);
	private StiloService proxy;
	private CustomPropertyPlaceholderConfigurer property;
	private String contabiliaFruitore;
	private String contabiliaEnte;
	
	public OutputRicercaMovimentoGestione ricercaMovimentoGestione(final RicercaMovimentoGestioneStiloRequest input) {
		OutputRicercaMovimentoGestione result = new OutputRicercaMovimentoGestione();
		RicercaMovimentoGestioneStiloResponse response = null;
		Integer totaleRisultati = 0;
		
		try {
			// parametri di input
			PropertyStiloWS prop = setParamInputRicerca(input);
			RicercaMovimentoGestioneStilo param = prop.getMovimento();
			
			logger.info("Chiamata WS ricercaMovimentoGestione parametri" + 
						" - ente: " + param.getCodiceEnte() +
						" - fruitore: " + param.getCodiceFruitore() +
						" - annoBilancio: " + param.getAnnoBilancio() +
						" - codiceTipoStruttura: " + param.getCodiceTipoProvvedimento() +
						" - codiceStruttura:" + param.getCodiceStruttura() +
						" - annoProvvedimento: " + param.getAnnoProvvedimento() +
						" - codiceTipoProvvedimento: " + param.getCodiceTipoProvvedimento() +
						" - numeroProvvedimento: " + param.getNumeroProvvedimento());
			
			// valorizzazione endpoint da file di configurazione
			setEndpointProxy(prop.getEndpoint());
			
			// chiamata al servizio ricercaMovimentoGestione
			response = proxy.ricercaMovimentoGestione(param);
			
			totaleRisultati = response.getMovimentiGestione().size();
			result.setOk(true);
			
			logger.info("Response chiamata WS ricercaMovimentoGestione" +
						" - esito: " + response.getEsito().value() +
						" - totaleRisultati: " + totaleRisultati);
		} catch (Exception e) {
			Util.aggiornaEsito(result, e);
			
			final String msg = "Fallita chiamata ricercaMovimentoGestione";
			logger.error(e.getMessage());
			
			result.setOk(false);
			result.setMessaggio(msg + " - " + e.getMessage());
		}
		
		result.setTotaleRisultati(totaleRisultati);
		result.setEntry(response);
		return result;
	}
	
	public StiloService getProxy() {
		return proxy;
	}
	
	public void setProxy(StiloService proxy) {
		this.proxy = proxy;
	}
	
	public CustomPropertyPlaceholderConfigurer getProperty() {
		return property;
	}
	
	public void setProperty(CustomPropertyPlaceholderConfigurer property) {
		this.property = property;
	}
	
	public String getContabiliaFruitore() {
		return contabiliaFruitore;
	}
	
	public void setContabiliaFruitore(String contabiliaFruitore) {
		this.contabiliaFruitore = contabiliaFruitore;
	}
	
	public String getContabiliaEnte() {
		return contabiliaEnte;
	}
	
	public void setContabiliaEnte(String contabiliaEnte) {
		this.contabiliaEnte = contabiliaEnte;
	}
	
	
	/*
	 * Metodo per restituire i parametri di input da inviare al servizio WS ricercaMovimentoGestione
	 */
	private PropertyStiloWS setParamInputRicerca(RicercaMovimentoGestioneStiloRequest input) {
		PropertyStiloWS result = new PropertyStiloWS();
		
		RicercaMovimentoGestioneStilo movimento = new RicercaMovimentoGestioneStilo();
		movimento.setAnnoProvvedimento(input.getAnnoProvvedimento());
		movimento.setCodiceStruttura(input.getCodiceStruttura());
		movimento.setCodiceTipoProvvedimento(input.getCodiceTipoProvvedimento());
		movimento.setCodiceTipoStruttura(input.getCodiceTipoStruttura());
		movimento.setNumeroProvvedimento(input.getNumeroProvvedimento());
		
		if (input.getAnnoBilancio() != null) {
			movimento.setAnnoBilancio(input.getAnnoBilancio());
		}
		else {
			movimento.setAnnoBilancio(Calendar.getInstance().get(Calendar.YEAR));
		}
		
		movimento.setCodiceFruitore(contabiliaFruitore);
		movimento.setCodiceEnte(contabiliaEnte);
		String endpointWs = null;
		
		if (input.getIdSpAoo() != null) {
			String idSpAoo = input.getIdSpAoo();
			
			// acquisizione property da file di configurazione
			Map<String, String> properties = property.getResolvedProps();
			
			if (properties != null) {
				endpointWs = properties.get("contabilia.stilo.endpoint");
				
				if (properties.containsKey("contabilia.fruitore." + idSpAoo)) {
					movimento.setCodiceFruitore(properties.get("contabilia.fruitore." + idSpAoo));
				}
				
				if (properties.containsKey("contabilia.ente." + idSpAoo)) {
					movimento.setCodiceEnte(properties.get("contabilia.ente." + idSpAoo));
				}
				
				if (properties.containsKey("contabilia.stilo.endpoint." + idSpAoo)) {
					endpointWs = properties.get("contabilia.stilo.endpoint." + idSpAoo);
				}
			}
		}
		
		result.setMovimento(movimento);
		result.setEndpoint(endpointWs);
		
		return result;
	}
	
	/*
	 * Metodo per settare endpoint specializzato all'oggetto ProxyClient
	 * se null endpoint di default impostato in file xml di configurazione
	 */
	private void setEndpointProxy(String endpointWs) {
		if (endpointWs != null) {
			logger.info("Endpoint specializzato per servizio ricercaMovimentoGestione: " + endpointWs);
			
			BindingProvider bp = (BindingProvider)proxy;
			bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointWs);
		}
	}
	
	/*
	private StiloService getRicercaService(String endpointWs) throws MalformedURLException  {
		URL url = new URL(endpointWs);
		QName qname = new QName("http://siac.csi.it/stilo/svc/1.0", "StiloService");
		Service service = Service.create(url, qname);
		StiloService result = service.getPort(StiloService.class);
		
		return result;
	}
	*/
	
}
