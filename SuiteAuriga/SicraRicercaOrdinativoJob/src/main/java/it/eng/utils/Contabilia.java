/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import it.eng.bean.NuovaPropostaAtto2CompletaBean;
import it.eng.document.function.bean.ContabiliaElaboraAttiAmministrativiRequest;
import it.eng.document.function.bean.ContabiliaOutputElaboraAttiAmministrativi;
import it.eng.job.exception.StoreException;
import it.eng.utility.client.contabilia.documenti.ClientContabiliaDocumenti;
import it.eng.utility.client.contabilia.documenti.ClientSpringContabiliaDocumenti;
import it.eng.utility.client.contabilia.documenti.data.ElaboraAttiAmministrativiRequest;
import it.eng.utility.client.contabilia.documenti.data.OutputElaboraAttiAmministrativi;

public class Contabilia {
	
	private static Logger log = Logger.getLogger(Contabilia.class);
	
	public Contabilia() {
	}

		// aggiorna e setta stato a annullata ("update" per Contabilia, come "campi chiave" dello stringone di Contabilia mette estremi della proposta, 0 sul flag di attivazione blocco)
		public NuovaPropostaAtto2CompletaBean annullamentoProposta(NuovaPropostaAtto2CompletaBean bean) throws Exception {
			
			   ContabiliaOutputElaboraAttiAmministrativi output = null;
			   OutputElaboraAttiAmministrativi response = null;
				try {
					
					ElaboraAttiAmministrativiRequest input = new ElaboraAttiAmministrativiRequest();
					
					input.setAnnoAttoProposta(bean.getAnnoRegProvvisoria() != null ? Integer.parseInt(bean.getAnnoRegProvvisoria()) : null);
					input.setNumeroAttoProposta(StringUtils.isNotBlank(bean.getNumeroRegistrazione()) ? bean.getNumeroRegistrazione(): "");
					input.setTipoAttoProposta(getTipoAttoContabiliaFromSiglaReg(bean.getSiglaRegistrazione()));
					
					input.setCentroResponsabilita(StringUtils.isNotBlank(bean.getCentroResponsabilitaContabilia()) ? bean.getCentroResponsabilitaContabilia() : "");
					input.setCentroCosto(StringUtils.isNotBlank(bean.getCentroCostoContabilia()) ? bean.getCentroCostoContabilia() : "");
					input.setDataCreazione(bean.getDataRegProvvisoria() != null ? new SimpleDateFormat("yyyyMMddHHmmss").format(bean.getDataRegProvvisoria()) : "");
					input.setDataProposta(bean.getDataRegProvvisoria() != null ? new SimpleDateFormat("yyyyMMdd").format(bean.getDataRegProvvisoria()) : "");
					input.setDataApprovazione(bean.getDataRegistrazione() != null ? new SimpleDateFormat("yyyyMMddHHmmss").format(bean.getDataRegistrazione()) : "");
					input.setDataEsecutivita(bean.getDataRegistrazione() != null ? new SimpleDateFormat("yyyyMMdd").format(bean.getDataRegistrazione()) : "");
					input.setOggetto(StringUtils.isNotBlank(bean.getOggetto()) ? bean.getOggetto() : "");
					input.setNote("");
					input.setIdSpAoo(bean.getIdDominio());
					input.setIdentificativo(StringUtils.isNotBlank(bean.getIdUd()) ? bean.getIdUd() : "");
					input.setDirigenteResponsabile(StringUtils.isNotBlank(bean.getDirigenteResponsabileContabilia()) ? bean.getDirigenteResponsabileContabilia() : "");
					input.setTrasparenza(null);
					
					ClientContabiliaDocumenti client = ClientSpringContabiliaDocumenti.getClient();
					
					response = client.annullamentoProposta(input);
					
					log.info("ContabiliaAnnullamentoProposta " + input.getTipoAttoProposta() + " " + input.getNumeroAttoProposta() + " " + input.getAnnoAttoProposta());
					log.info(response.getEntry().isResponseElaborazione() + 
							" - esito: " + response.getEntry().getEsitoElaborazione() + 
							" - codice: " + response.getEntry().getCodiceElaborazione() + 
							" - messaggio: " + response.getEntry().getMessaggioElaborazione());
					
					if(response.getEntry() != null && response.getEntry().isResponseElaborazione()) {				
						bean.setEsitoEventoContabilia("OK");				
						bean.setDataEventoContabilia(new Date());
						return bean;
					} else {				
						String errorMessage = "Si è verificato un'errore durante la chiamata ai servizi di Contabilia";
						if(response.getEntry() != null && StringUtils.isNotBlank(response.getEntry().getMessaggioElaborazione())) {									
							errorMessage += " (AnnullamentoProposta): " + response.getEntry().getMessaggioElaborazione();
						}
						log.error(errorMessage);
						bean.setEsitoEventoContabilia("KO");
						bean.setDataEventoContabilia(new Date());
						bean.setErrMsgEventoContabilia(errorMessage);			
//						throw new StoreException(errorMessage);	
					}
					
					
					input.setAnnoAttoDefinitivo(bean.getAnnoRegistrazione() != null ? Integer.parseInt(bean.getAnnoRegistrazione() ) : null);
					input.setNumeroAttoProposta(StringUtils.isNotBlank(bean.getNumeroRegProvvisoria()) ? bean.getNumeroRegProvvisoria() : "");
					input.setTipoAttoProposta(getTipoAttoContabiliaFromSiglaReg(bean.getSiglaRegProvvisoria()));
                    
					
					response = client.annullamentoProposta(input);
					
					log.info("ContabiliaAnnullamentoProposta " + input.getTipoAttoProposta() + " " + input.getNumeroAttoProposta() + " " + input.getAnnoAttoProposta());
					log.info(response.getEntry().isResponseElaborazione() + 
							" - esito: " + response.getEntry().getEsitoElaborazione() + 
							" - codice: " + response.getEntry().getCodiceElaborazione() + 
							" - messaggio: " + response.getEntry().getMessaggioElaborazione());
					
					if(response.getEntry() != null && response.getEntry().isResponseElaborazione()) {				
						bean.setEsitoEventoContabilia("OK");				
						bean.setDataEventoContabilia(new Date());
					} else {				
						String errorMessage = "Si è verificato un'errore durante la chiamata ai servizi di Contabilia";
						if(response.getEntry() != null && StringUtils.isNotBlank(response.getEntry().getMessaggioElaborazione())) {									
							errorMessage += " (AnnullamentoProposta): " + response.getEntry().getMessaggioElaborazione();
						}
						log.error(errorMessage);
						bean.setEsitoEventoContabilia("KO");
						bean.setDataEventoContabilia(new Date());
						bean.setErrMsgEventoContabilia(errorMessage);			
//						throw new StoreException(errorMessage);	
					}
					
					
				} catch(Exception e) {
					String errorMessage = "Si è verificato un'errore durante la chiamata ai servizi di Contabilia (AnnullamentoProposta)";
					log.error(errorMessage + ": " + e.getMessage(), e);
					bean.setEsitoEventoContabilia("KO");
					throw new StoreException(errorMessage);
				}			
				
			
			return bean;
		}
		
		public ContabiliaElaboraAttiAmministrativiRequest buildContabiliaElaboraAttiAmministrativiRequest(NuovaPropostaAtto2CompletaBean bean) {		
			
			ContabiliaElaboraAttiAmministrativiRequest lContabiliaElaboraAttiAmministrativiRequest = new ContabiliaElaboraAttiAmministrativiRequest();
			lContabiliaElaboraAttiAmministrativiRequest.setIdSpAoo(bean.getIdDominio());
			
			lContabiliaElaboraAttiAmministrativiRequest.setAnnoAttoProposta(bean.getAnnoRegProvvisoria() != null ? Integer.parseInt(bean.getAnnoRegProvvisoria()) : null);
			lContabiliaElaboraAttiAmministrativiRequest.setNumeroAttoProposta(StringUtils.isNotBlank(bean.getNumeroRegProvvisoria()) ? bean.getNumeroRegProvvisoria() : "");		
			lContabiliaElaboraAttiAmministrativiRequest.setTipoAttoProposta(getTipoAttoContabiliaFromSiglaReg(bean.getSiglaRegProvvisoria())); // lContabiliaElaboraAttiAmministrativiRequest.setTipoAttoProposta("PDD");	
			
			lContabiliaElaboraAttiAmministrativiRequest.setAnnoAttoDefinitivo(bean.getAnnoRegistrazione() != null ? Integer.parseInt(bean.getAnnoRegistrazione()) : null);		
			lContabiliaElaboraAttiAmministrativiRequest.setNumeroAttoDefinitivo(StringUtils.isNotBlank(bean.getNumeroRegistrazione()) ? bean.getNumeroRegistrazione() : "");		
			lContabiliaElaboraAttiAmministrativiRequest.setTipoAttoDefinitivo(getTipoAttoContabiliaFromSiglaReg(bean.getSiglaRegistrazione())); // lContabiliaElaboraAttiAmministrativiRequest.setTipoAttoDefinitivo("DD"); 
			
			lContabiliaElaboraAttiAmministrativiRequest.setDataCreazione(bean.getDataRegProvvisoria() != null ? new SimpleDateFormat("yyyyMMddHHmmss").format(bean.getDataRegProvvisoria()) : "");
			lContabiliaElaboraAttiAmministrativiRequest.setDataProposta(bean.getDataRegProvvisoria() != null ? new SimpleDateFormat("yyyyMMdd").format(bean.getDataRegProvvisoria()) : "");
			
			lContabiliaElaboraAttiAmministrativiRequest.setDataApprovazione(bean.getDataRegistrazione() != null ? new SimpleDateFormat("yyyyMMddHHmmss").format(bean.getDataRegistrazione()) : "");
			lContabiliaElaboraAttiAmministrativiRequest.setDataEsecutivita(bean.getDataRegistrazione() != null ? new SimpleDateFormat("yyyyMMdd").format(bean.getDataRegistrazione()) : "");
			
			lContabiliaElaboraAttiAmministrativiRequest.setOggetto(StringUtils.isNotBlank(bean.getOggetto()) ? bean.getOggetto() : "");
			lContabiliaElaboraAttiAmministrativiRequest.setNote("");
			lContabiliaElaboraAttiAmministrativiRequest.setIdentificativo(StringUtils.isNotBlank(bean.getIdUd()) ? bean.getIdUd() : "");
			
			lContabiliaElaboraAttiAmministrativiRequest.setDirigenteResponsabile(StringUtils.isNotBlank(bean.getDirigenteResponsabileContabilia()) ? bean.getDirigenteResponsabileContabilia() : "");
			lContabiliaElaboraAttiAmministrativiRequest.setTrasparenza("");
			lContabiliaElaboraAttiAmministrativiRequest.setCentroResponsabilita(StringUtils.isNotBlank(bean.getCentroResponsabilitaContabilia()) ? bean.getCentroResponsabilitaContabilia() : "");
			lContabiliaElaboraAttiAmministrativiRequest.setCentroCosto(StringUtils.isNotBlank(bean.getCentroCostoContabilia()) ? bean.getCentroCostoContabilia() : "");
			return lContabiliaElaboraAttiAmministrativiRequest; 
		}
		
		public String getTipoAttoContabiliaFromSiglaReg(String sigla) {
			if(StringUtils.isNotBlank(sigla)) {
				// Se nella sigla ho il suffisso con il codice differenziato per struttura lo tolgo 
				int pos = sigla.indexOf("-");
				if(pos != -1) {
					sigla = sigla.substring(0, pos);
				}
				// Il servizio elaboraAttiAmministrativi ha come parametri di input tipoAttoProposta e tipoAttoDefinitivo, ma poi il client genera uno stringone 
				// con tutti i parametri concatenati in maniera posizionale con lunghezze massime fisse. Per il codice tipo atto è previsto una lunghezza massima 
				// di 4 caratteri. Le sigle relative ai decreti sono più lunghe quindi dobbiamo troncarle.
				if(sigla.equalsIgnoreCase("PDCRC")) {
					sigla = "PDCC";
				} else if(sigla.equalsIgnoreCase("PDCRS")) {
					sigla = "PDCS";
				} else if(sigla.equalsIgnoreCase("DCRC")) {
					sigla = "DCC";
				} else if(sigla.equalsIgnoreCase("DCRS")) {
					sigla = "DCS";
				} 
			}
			return sigla;
		}
		
}
