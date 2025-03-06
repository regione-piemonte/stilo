package it.eng.utility.amco.client;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.Comparator;

import org.apache.log4j.Logger;

import it.eng.utility.amco.utils.SortingObjectList;
import it.finmatica.gsaws.AnagraficheGSA;
import it.finmatica.gsaws.BusinessPartners;
import it.finmatica.gsaws.BusinessPartnersRequest;
import it.finmatica.gsaws.BusinessPartnersResponse;
import it.finmatica.gsaws.ContiCreditoDebitoRequest;
import it.finmatica.gsaws.ContiCreditoDebitoResponse;
import it.finmatica.gsaws.ContiImputazione;
import it.finmatica.gsaws.ContiImputazioneRequest;
import it.finmatica.gsaws.ContiImputazioneResponse;
import it.finmatica.gsaws.ContoCreditoDebito;
import it.finmatica.gsaws.TipiDocumentoRequest;
import it.finmatica.gsaws.TipiDocumentoResponse;
import it.finmatica.gsaws.TipoDocumento;

public class ClientAmco {
	
	public static final String BEAN_ID = "clientAmco";
	private static final Logger logger = Logger.getLogger(ClientAmco.class);
	private AnagraficheGSA proxy;
	
	public ContiCreditoDebitoResponse ricercaContiCreditoDebito(ContiCreditoDebitoRequest request) {
		logger.info("inizio metodo ricercaContiCreditoDebito");
		
		ContiCreditoDebitoResponse response = null;
		
		try {
			long inizio = System.currentTimeMillis();
			
			if (request != null) {
				String input = "Codice BP: " + request.getCodiceBP() + " - " +
							   "Codice capitolo: " + request.getCodiceCapitolo() + " - " +
							   "Nome tipo doc: " + request.getNomeTipoDoc();
				
				if (request.getEntrataUscita() != null) {
					input += "Entrata/uscita: " + request.getEntrataUscita();
				}
				
				logger.info("Parametri in input " + input);
			}
			else {
				logger.info("nessun parametro di input");
			}
			
			// chiamata servizio soap
			response = proxy.getContiCreditoDebito(request);
			
			if (response != null) {
				logger.info("Response: " + response.getDescrizioneErrore());
				logger.info("record trovati: " + response.getContoCreditoDebito().size());
				
				if (response.getContoCreditoDebito().size() > 1) {
					logger.info("Ordinamento lista risultati");
					
					SortingObjectList sortingObjectList = new SortingObjectList();
					
					// ordinamento per codice
					Comparator<ContoCreditoDebito> objComparator = sortingObjectList.getContoCreditoDebitoComparatorByCod();
					Collections.sort(response.getContoCreditoDebito(), objComparator);
					
					logger.info("Risultati ordinati per codice");
				}
				else {
					logger.info("Nessun ordinamneto");
				}
			}
			else {
				logger.info("Response: " + response);
			}
			
			long fine = System.currentTimeMillis();
			
			logger.info(esecuzioneServizio(inizio, fine, "ricercaContiCreditoDebito"));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
		logger.info("fine metodo ricercaContiCreditoDebito");
		
		return response;
	}
	
	public ContiImputazioneResponse ricercaContiImputazione(ContiImputazioneRequest request) {
		logger.info("inizio metodo ricercaContiImputazione");
		
		ContiImputazioneResponse response = null;
		
		try {
			long inizio = System.currentTimeMillis();
			
			if (request != null) {
				String input = "Codice capitolo: " + request.getCodiceCapitolo() + " - " +
							   "Codice conto: " + request.getCodiceConto() + " - " +
							   "Desc conto: " + request.getDescConto() + " - " +
							   "Nome tipo doc: " + request.getNomeTipoDoc();
				
				if (request.getEntrataUscita() != null) {
					input += "Entrata/uscita: " + request.getEntrataUscita();
				}
				
				logger.info("Parametri in input " + input);
			}
			else {
				logger.info("nessun parametro di input");
			}
			
			// chiamata servizio soap
			response = proxy.getContiImputazione(request);
			
			if (response != null) {
				logger.info("Response: " + response.getDescrizioneErrore());
				logger.info("record trovati: " + response.getContiImputazione().size());
				
				if (response.getContiImputazione().size() > 1) {
					logger.info("Ordinamento lista risultati");
					
					SortingObjectList sortingObjectList = new SortingObjectList();
					
					// ordinamento per codice
					Comparator<ContiImputazione> objComparator = sortingObjectList.getContoImputazioneComparatorByCod();
					Collections.sort(response.getContiImputazione(), objComparator);
					
					logger.info("Risultati ordinati per codice");
				}
				else {
					logger.info("Nessun ordinamneto");
				}
			}
			else {
				logger.info("Response: " + response);
			}
			
			long fine = System.currentTimeMillis();
			
			logger.info(esecuzioneServizio(inizio, fine, "ricercaContiImputazione"));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
		logger.info("fine metodo ricercaContiImputazione");
		
		return response;
	}
	
	public TipiDocumentoResponse ricercaTipiDocumento(TipiDocumentoRequest request) {
		logger.info("inizio metodo ricercaTipiDocumento");
		
		TipiDocumentoResponse response = null;
		
		try {
			long inizio = System.currentTimeMillis();
			
			if (request != null) {
				String input = "Desctizione: " + request.getDescrizione() + " - " +
							   "Nome: " + request.getNome();
				
				if (request.getFinanziaria() != null) {
					input += "Finanziaria: " + request.getFinanziaria();
				}
				
				logger.info("Parametri in input " + input);
			}
			else {
				logger.info("nessun parametro di input");
			}
			
			// chiamata servizio soap
			response = proxy.getTipiDocumento(request);
			
			if (response != null) {
				logger.info("Response: " + response.getDescrizioneErrore());
				logger.info("record trovati: " + response.getTipiDocumento().size());
				
				if (response.getTipiDocumento().size() > 1) {
					logger.info("Ordinamento lista risultati");
					
					SortingObjectList sortingObjectList = new SortingObjectList();
					
					// ordinamento per nome
					Comparator<TipoDocumento> objComparator = sortingObjectList.getTipoDocumentoComparatorByNome();
					Collections.sort(response.getTipiDocumento(), objComparator);
					
					logger.info("Risultati ordinati per nome");
				}
				else {
					logger.info("Nessun ordinamneto");
				}
			}
			else {
				logger.info("Response: " + response);
			}
			
			long fine = System.currentTimeMillis();
			
			logger.info(esecuzioneServizio(inizio, fine, "ricercaTipiDocumento"));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
		logger.info("fine metodo ricercaTipiDocumento");
		
		return response;
	}
	
	public BusinessPartnersResponse ricercaBusinessPartners(BusinessPartnersRequest request) {
		logger.info("inizio metodo ricercaBusinessPartners");
		
		BusinessPartnersResponse response = null;
		
		try {
			long inizio = System.currentTimeMillis();
			
			if (request != null) {
				String input = "Codice finanziaria: " + request.getCodiceFinanziaria() + " - " +
							   "Codice fiscale: " + request.getCodiceFiscale() + " - " +
							   "Partita iva: " + request.getPartitaIva() + " - " +
							   "Ragione sociale: " + request.getRagioneSociale();
				
				logger.info("Parametri in input " + input);
			}
			else {
				logger.info("nessun parametro di input");
			}
			
			// chiamata servizio soap
			response = proxy.getBusinessPartners(request);
			
			if (response != null) {
				logger.info("Response: " + response.getDescrizioneErrore());
				logger.info("record trovati: " + response.getBusinessPartners().size());
				
				if (response.getBusinessPartners().size() > 1) {
					logger.info("Ordinamento lista risultati");
					
					SortingObjectList sortingObjectList = new SortingObjectList();
					
					// ordinamento per ragione sociale
					Comparator<BusinessPartners> objComparator = sortingObjectList.getBusinessPartnersComparatorByRagSoc();
					Collections.sort(response.getBusinessPartners(), objComparator);
					
					logger.info("Risultati ordinati per ragione sociale");
				}
				else {
					logger.info("Nessun ordinamneto");
				}
			}
			else {
				logger.info("Response: " + response);
			}
			
			long fine = System.currentTimeMillis();
			
			logger.info(esecuzioneServizio(inizio, fine, "ricercaBusinessPartners"));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
		logger.info("fine metodo ricercaBusinessPartners");
		
		return response;
	}
	
	
	public AnagraficheGSA getProxy() {
		return proxy;
	}
	public void setProxy(AnagraficheGSA proxy) {
		this.proxy = proxy;
	}
	
	
	private String esecuzioneServizio(long inizio, long fine, String nomeServizio) {
		NumberFormat formatter = new DecimalFormat("#0.00000");
		
		String result = "Esecuzione servizio " + nomeServizio + " in " + formatter.format((fine - inizio) / 1000d) + " secondi";
		
		return result;
	}
	
}
