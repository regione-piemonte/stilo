/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.DatiContabiliBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.NuovaPropostaAtto2CompletaBean;
import it.eng.client.ConsultazioneContabiliaImpl;
import it.eng.core.performance.PerformanceLogger;
import it.eng.document.function.bean.ContabiliaAccertamento;
import it.eng.document.function.bean.ContabiliaElaboraAttiAmministrativiRequest;
import it.eng.document.function.bean.ContabiliaImpegno;
import it.eng.document.function.bean.ContabiliaMovimentoGestioneStilo;
import it.eng.document.function.bean.ContabiliaOutputElaboraAttiAmministrativi;
import it.eng.document.function.bean.ContabiliaOutputRicercaAccertamento;
import it.eng.document.function.bean.ContabiliaOutputRicercaImpegno;
import it.eng.document.function.bean.ContabiliaOutputRicercaMovimentoGestione;
import it.eng.document.function.bean.ContabiliaRicercaAccertamentoRequest;
import it.eng.document.function.bean.ContabiliaRicercaImpegnoRequest;
import it.eng.document.function.bean.ContabiliaRicercaMovimentoGestioneStiloRequest;
import it.eng.document.function.bean.MovimentiContabiliaXmlBean;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.ParametriDBUtil;

@Datasource(id = "ContabiliaDataSource")
public class ContabiliaDataSource extends AbstractFetchDataSource<DatiContabiliBean> {

	private static final Logger logger = Logger.getLogger(ContabiliaDataSource.class);
	
	public boolean isAttivoContabilia() {
		String lSistAMC = ParametriDBUtil.getParametroDB(getSession(), "SIST_AMC");
		return lSistAMC != null && ("CONTABILIA".equalsIgnoreCase(lSistAMC) || "CONTABILIA2".equalsIgnoreCase(lSistAMC)); // per Regione Piemonte e Citta Metropolitana
	}
	
	private String getTipoAttoContabiliaFromSiglaReg(String sigla) {
		if(StringUtils.isNotBlank(sigla)) {
			// Se nella sigla ho il suffisso con il codice differenziato per struttura lo tolgo 
			int pos = sigla.indexOf("-");
			if(pos != -1) {
				sigla = sigla.substring(0, pos);
			}
			// Se nella sigla ho il carattere _ devo considerare solo quello che viene prima 
			pos = sigla.indexOf("_");
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
	
	public ContabiliaElaboraAttiAmministrativiRequest buildContabiliaElaboraAttiAmministrativiRequest(NuovaPropostaAtto2CompletaBean bean) {		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String idDominio = null;
		if (loginBean.getDominio() != null && !"".equals(loginBean.getDominio())) {
			if (loginBean.getDominio().split(":").length == 2) {
				idDominio = loginBean.getDominio().split(":")[1];
			}
		}
		ContabiliaElaboraAttiAmministrativiRequest lContabiliaElaboraAttiAmministrativiRequest = new ContabiliaElaboraAttiAmministrativiRequest();
		lContabiliaElaboraAttiAmministrativiRequest.setIdSpAoo(StringUtils.isNotBlank(idDominio) ? idDominio : null);		
		lContabiliaElaboraAttiAmministrativiRequest.setAnnoAttoProposta(bean.getDataRegProvvisoria() != null ? Integer.parseInt(new SimpleDateFormat("yyyy").format(bean.getDataRegProvvisoria())) : null);
		lContabiliaElaboraAttiAmministrativiRequest.setNumeroAttoProposta(StringUtils.isNotBlank(bean.getNumeroRegProvvisoria()) ? bean.getNumeroRegProvvisoria() : "");		
		lContabiliaElaboraAttiAmministrativiRequest.setTipoAttoProposta(getTipoAttoContabiliaFromSiglaReg(bean.getSiglaRegProvvisoria())); // lContabiliaElaboraAttiAmministrativiRequest.setTipoAttoProposta("PDD");	
		lContabiliaElaboraAttiAmministrativiRequest.setAnnoAttoDefinitivo(bean.getDataRegistrazione() != null ? Integer.parseInt(new SimpleDateFormat("yyyy").format(bean.getDataRegistrazione())) : null);		
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
	
	// manda i dati della proposta senza attivare blocco ("insert" per Contabilia + 0 sul flag di attivazione blocco)
	public NuovaPropostaAtto2CompletaBean invioProposta(NuovaPropostaAtto2CompletaBean bean) throws Exception {
		if(isAttivoContabilia()) {
			ConsultazioneContabiliaImpl lConsultazioneContabiliaImpl = new ConsultazioneContabiliaImpl(); 
			ContabiliaElaboraAttiAmministrativiRequest input = buildContabiliaElaboraAttiAmministrativiRequest(bean);
			ContabiliaOutputElaboraAttiAmministrativi output = null;
			try {
				PerformanceLogger lPerformanceLogger = new PerformanceLogger("ContabiliaInvioProposta " + input.getTipoAttoProposta() + " " + input.getNumeroAttoProposta() + " " + input.getAnnoAttoProposta());
				lPerformanceLogger.start();		
				output = lConsultazioneContabiliaImpl.invioproposta(getLocale(), input);
				lPerformanceLogger.end();		
			} catch(Exception e) {
				String errorMessage = "Si è verificato un'errore durante la chiamata ai servizi di Contabilia (InvioProposta)";
				logger.error(errorMessage + ": " + e.getMessage(), e);
				throw new StoreException(errorMessage);
			}
			if(output.getEntry() != null && output.getEntry().isResponseElaborazione()) {			
				bean.setEsitoEventoContabilia("OK");	
				bean.setDataEventoContabilia(new Date());
				bean.setIdPropostaAMC("-999");
			} else {				
				String errorMessage = "Si è verificato un'errore durante la chiamata ai servizi di Contabilia";
				if(output.getEntry() != null && StringUtils.isNotBlank(output.getEntry().getMessaggioElaborazione())) {									
					errorMessage += " (InvioProposta): " + output.getEntry().getMessaggioElaborazione();
				}
				logger.error(errorMessage);
				bean.setEsitoEventoContabilia("KO");
				bean.setDataEventoContabilia(new Date());
				bean.setErrMsgEventoContabilia(errorMessage);			
//				throw new StoreException(errorMessage);		
			}
		}
		return bean;
	}
	
	// aggiorna i dati di proposta già inviata (update per Contabilia, come "campi chiave" dello stringone di Contabilia mette estremi della proposta, 0 sul flag di attivazione blocco)
	public NuovaPropostaAtto2CompletaBean aggiornaProposta(NuovaPropostaAtto2CompletaBean bean) throws Exception {
		if(isAttivoContabilia()) {
			ConsultazioneContabiliaImpl lConsultazioneContabiliaImpl = new ConsultazioneContabiliaImpl(); 
			ContabiliaElaboraAttiAmministrativiRequest input = buildContabiliaElaboraAttiAmministrativiRequest(bean);
			ContabiliaOutputElaboraAttiAmministrativi output = null;
			try {
				PerformanceLogger lPerformanceLogger = new PerformanceLogger("ContabiliaAggiornaProposta " + input.getTipoAttoProposta() + " " + input.getNumeroAttoProposta() + " " + input.getAnnoAttoProposta());
				lPerformanceLogger.start();		
				output = lConsultazioneContabiliaImpl.aggiornaproposta(getLocale(), input);
				lPerformanceLogger.end();		
			} catch(Exception e) {
				String errorMessage = "Si è verificato un'errore durante la chiamata ai servizi di Contabilia (AggiornaProposta)";
				logger.error(errorMessage + ": " + e.getMessage(), e);
				throw new StoreException(errorMessage);
			}		
			if(output.getEntry() != null && output.getEntry().isResponseElaborazione()) {				
				bean.setEsitoEventoContabilia("OK");
				bean.setDataEventoContabilia(new Date());				
			} else {				
				String errorMessage = "Si è verificato un'errore durante la chiamata ai servizi di Contabilia";
				if(output.getEntry() != null && StringUtils.isNotBlank(output.getEntry().getMessaggioElaborazione())) {									
					errorMessage += " (AggiornaProposta): " + output.getEntry().getMessaggioElaborazione();
				}
				logger.error(errorMessage);			
				bean.setEsitoEventoContabilia("KO");
				bean.setDataEventoContabilia(new Date());
				bean.setErrMsgEventoContabilia(errorMessage);			
//				throw new StoreException(errorMessage);	
			}
		}
		return bean;
	}
	
	// aggiorna i dati della proposta e attiva blocco ("update" per Contabilia , come "campi chiave" dello stringone di Contabilia mette estremi della proposta, 1 sul flag di attivazione blocco)
	public NuovaPropostaAtto2CompletaBean bloccoDatiProposta(NuovaPropostaAtto2CompletaBean bean) throws Exception {
		if(isAttivoContabilia()) {
			ConsultazioneContabiliaImpl lConsultazioneContabiliaImpl = new ConsultazioneContabiliaImpl(); 
			ContabiliaElaboraAttiAmministrativiRequest input = buildContabiliaElaboraAttiAmministrativiRequest(bean);
			ContabiliaOutputElaboraAttiAmministrativi output = null;
			try {
				PerformanceLogger lPerformanceLogger = new PerformanceLogger("ContabiliaBloccoDatiProposta " + input.getTipoAttoProposta() + " " + input.getNumeroAttoProposta() + " " + input.getAnnoAttoProposta());
				lPerformanceLogger.start();		
				output = lConsultazioneContabiliaImpl.bloccodatiproposta(getLocale(), input);
				lPerformanceLogger.end();		
			} catch(Exception e) {
				String errorMessage = "Si è verificato un'errore durante la chiamata ai servizi di Contabilia (BloccoDatiProposta)";
				logger.error(errorMessage + ": " + e.getMessage(), e);
				throw new StoreException(errorMessage);
			}			
			if(output.getEntry() != null && output.getEntry().isResponseElaborazione()) {				
				bean.setEsitoEventoContabilia("OK");	
				bean.setDataEventoContabilia(new Date());
			} else {				
				String errorMessage = "Si è verificato un'errore durante la chiamata ai servizi di Contabilia";
				if(output.getEntry() != null && StringUtils.isNotBlank(output.getEntry().getMessaggioElaborazione())) {									
					errorMessage += " (BloccoDatiProposta): " + output.getEntry().getMessaggioElaborazione();
				}
				logger.error(errorMessage);
				bean.setEsitoEventoContabilia("KO");
				bean.setDataEventoContabilia(new Date());
				bean.setErrMsgEventoContabilia(errorMessage);			
//				throw new StoreException(errorMessage);	
			}
		}
		return bean;
	}
	
	// aggiorna i dati, setta stato a definitiva, e manda estremi atto definitivo ("update" per Contabilia; come "campi chiave" dello stringone di Contabilia mette estremi della proposta, negli altri campi anno, nro ecc mette i dati atto definitivo, 1 sul flag di attivazione blocco)
	public NuovaPropostaAtto2CompletaBean invioAttoDef(NuovaPropostaAtto2CompletaBean bean) throws Exception {
		if(isAttivoContabilia()) {
			ConsultazioneContabiliaImpl lConsultazioneContabiliaImpl = new ConsultazioneContabiliaImpl(); 
			ContabiliaElaboraAttiAmministrativiRequest input = buildContabiliaElaboraAttiAmministrativiRequest(bean);
			ContabiliaOutputElaboraAttiAmministrativi output = null;
			try {
				PerformanceLogger lPerformanceLogger = new PerformanceLogger("ContabiliaInvioAttoDef " + input.getTipoAttoProposta() + " " + input.getNumeroAttoProposta() + " " + input.getAnnoAttoProposta());
				lPerformanceLogger.start();		
				output = lConsultazioneContabiliaImpl.invioattodef(getLocale(), input);
				lPerformanceLogger.end();		
			} catch(Exception e) {
				String errorMessage = "Si è verificato un'errore durante la chiamata ai servizi di Contabilia (InvioAttoDef)";
				logger.error(errorMessage + ": " + e.getMessage(), e);
				throw new StoreException(errorMessage);
			}			
			if(output.getEntry() != null && output.getEntry().isResponseElaborazione()) {				
				bean.setEsitoEventoContabilia("OK");	
				bean.setDataEventoContabilia(new Date());				
			} else {				
				String errorMessage = "Si è verificato un'errore durante la chiamata ai servizi di Contabilia";
				if(output.getEntry() != null && StringUtils.isNotBlank(output.getEntry().getMessaggioElaborazione())) {									
					errorMessage += " (InvioAttoDef): " + output.getEntry().getMessaggioElaborazione();
				}
				logger.error(errorMessage);
				bean.setEsitoEventoContabilia("KO");
				bean.setDataEventoContabilia(new Date());
				bean.setErrMsgEventoContabilia(errorMessage);			
//				throw new StoreException(errorMessage);	
			}
		}
		return bean;
	}
	
	// aggiorna i dati, setta stato a esecutivo, e manda estremi atto definitivo ("update" per Contabilia; come "campi chiave" dello stringone di Contabilia mette estremi della proposta, negli altri campi anni, nro ecc mette i dati atto definitivo, 1 sul flag di attivazione blocco)
	public NuovaPropostaAtto2CompletaBean invioAttoDefEsec(NuovaPropostaAtto2CompletaBean bean) throws Exception {
		if(isAttivoContabilia()) {
			ConsultazioneContabiliaImpl lConsultazioneContabiliaImpl = new ConsultazioneContabiliaImpl(); 
			ContabiliaElaboraAttiAmministrativiRequest input = buildContabiliaElaboraAttiAmministrativiRequest(bean);
			ContabiliaOutputElaboraAttiAmministrativi output = null;
			try {
				PerformanceLogger lPerformanceLogger = new PerformanceLogger("ContabiliaInvioAttoDefEsec " + input.getTipoAttoProposta() + " " + input.getNumeroAttoProposta() + " " + input.getAnnoAttoProposta());
				lPerformanceLogger.start();		
				output = lConsultazioneContabiliaImpl.invioattodefesec(getLocale(), input);
				lPerformanceLogger.end();		
			} catch(Exception e) {
				String errorMessage = "Si è verificato un'errore durante la chiamata ai servizi di Contabilia (InvioAttoDefEsec)";
				logger.error(errorMessage + ": " + e.getMessage(), e);
				throw new StoreException(errorMessage);
			}			
			if(output.getEntry() != null && output.getEntry().isResponseElaborazione()) {				
				bean.setEsitoEventoContabilia("OK");	
				bean.setDataEventoContabilia(new Date());				
			} else {				
				String errorMessage = "Si è verificato un'errore durante la chiamata ai servizi di Contabilia";
				if(output.getEntry() != null && StringUtils.isNotBlank(output.getEntry().getMessaggioElaborazione())) {									
					errorMessage += " (InvioAttoDefEsec): " + output.getEntry().getMessaggioElaborazione();
				}
				logger.error(errorMessage);
				bean.setEsitoEventoContabilia("KO");
				bean.setDataEventoContabilia(new Date());
				bean.setErrMsgEventoContabilia(errorMessage);			
//				throw new StoreException(errorMessage);	
			}
		}
		return bean;
	}
	
	// aggiorna, setta stato a esecutivo e manda estremi atto definitivo ("update" per Contabilia; come "campi chiave" dello stringone di Contabilia mette estremi  atto definitivo perché Contabilia ha "perso" il n.ro proposta a questo punto, 1 sul flag di attivazione blocco)
	public NuovaPropostaAtto2CompletaBean invioAttoEsec(NuovaPropostaAtto2CompletaBean bean) throws Exception {
		if(isAttivoContabilia()) {
			ConsultazioneContabiliaImpl lConsultazioneContabiliaImpl = new ConsultazioneContabiliaImpl(); 
			ContabiliaElaboraAttiAmministrativiRequest input = buildContabiliaElaboraAttiAmministrativiRequest(bean);
			ContabiliaOutputElaboraAttiAmministrativi output = null;
			try {
				PerformanceLogger lPerformanceLogger = new PerformanceLogger("ContabiliaInvioAttoEsec " + input.getTipoAttoProposta() + " " + input.getNumeroAttoProposta() + " " + input.getAnnoAttoProposta());
				lPerformanceLogger.start();		
				output = lConsultazioneContabiliaImpl.invioattoesec(getLocale(), input);
				lPerformanceLogger.end();		
			} catch(Exception e) {
				String errorMessage = "Si è verificato un'errore durante la chiamata ai servizi di Contabilia (InvioAttoEsec)";
				logger.error(errorMessage + ": " + e.getMessage(), e);
				throw new StoreException(errorMessage);
			}			
			if(output.getEntry() != null && output.getEntry().isResponseElaborazione()) {				
				bean.setEsitoEventoContabilia("OK");
				bean.setDataEventoContabilia(new Date());
			} else {				
				String errorMessage = "Si è verificato un'errore durante la chiamata ai servizi di Contabilia";
				if(output.getEntry() != null && StringUtils.isNotBlank(output.getEntry().getMessaggioElaborazione())) {									
					errorMessage += " (InvioAttoEsec): " + output.getEntry().getMessaggioElaborazione();
				}
				logger.error(errorMessage);
				bean.setEsitoEventoContabilia("KO");
				bean.setDataEventoContabilia(new Date());
				bean.setErrMsgEventoContabilia(errorMessage);			
//				throw new StoreException(errorMessage);	
			}
		}
		return bean;
	}
	
	// aggiorna i dati della proposta e fa sblocco ("update" per Contabilia, come "campi chiave" dello stringone di Contabilia mette estremi della proposta, 0 sul flag di attivazione blocco)
	public NuovaPropostaAtto2CompletaBean sbloccoDatiProposta(NuovaPropostaAtto2CompletaBean bean) throws Exception {
		if(isAttivoContabilia()) {
			ConsultazioneContabiliaImpl lConsultazioneContabiliaImpl = new ConsultazioneContabiliaImpl(); 
			ContabiliaElaboraAttiAmministrativiRequest input = buildContabiliaElaboraAttiAmministrativiRequest(bean);
			ContabiliaOutputElaboraAttiAmministrativi output = null;
			try {
				PerformanceLogger lPerformanceLogger = new PerformanceLogger("ContabiliaSbloccoDatiProposta " + input.getTipoAttoProposta() + " " + input.getNumeroAttoProposta() + " " + input.getAnnoAttoProposta());
				lPerformanceLogger.start();		
				output = lConsultazioneContabiliaImpl.sbloccodatiproposta(getLocale(), input);
				lPerformanceLogger.end();		
			} catch(Exception e) {
				String errorMessage = "Si è verificato un'errore durante la chiamata ai servizi di Contabilia (SbloccoDatiProposta)";
				logger.error(errorMessage + ": " + e.getMessage(), e);
				throw new StoreException(errorMessage);
			}			
			if(output.getEntry() != null && output.getEntry().isResponseElaborazione()) {				
				bean.setEsitoEventoContabilia("OK");				
				bean.setDataEventoContabilia(new Date());
			} else {				
				String errorMessage = "Si è verificato un'errore durante la chiamata ai servizi di Contabilia";
				if(output.getEntry() != null && StringUtils.isNotBlank(output.getEntry().getMessaggioElaborazione())) {									
					errorMessage += " (SbloccoDatiProposta): " + output.getEntry().getMessaggioElaborazione();
				}
				logger.error(errorMessage);
				bean.setEsitoEventoContabilia("KO");
				bean.setDataEventoContabilia(new Date());
				bean.setErrMsgEventoContabilia(errorMessage);			
//				throw new StoreException(errorMessage);	
			}
		}
		return bean;
	}
	
	// aggiorna e setta stato a annullata ("update" per Contabilia, come "campi chiave" dello stringone di Contabilia mette estremi della proposta, 0 sul flag di attivazione blocco)
	public NuovaPropostaAtto2CompletaBean annullamentoProposta(NuovaPropostaAtto2CompletaBean bean) throws Exception {
		if(isAttivoContabilia()) {
			ConsultazioneContabiliaImpl lConsultazioneContabiliaImpl = new ConsultazioneContabiliaImpl(); 
			ContabiliaElaboraAttiAmministrativiRequest input = buildContabiliaElaboraAttiAmministrativiRequest(bean);
			ContabiliaOutputElaboraAttiAmministrativi output = null;
			try {
				PerformanceLogger lPerformanceLogger = new PerformanceLogger("ContabiliaAnnullamentoProposta " + input.getTipoAttoProposta() + " " + input.getNumeroAttoProposta() + " " + input.getAnnoAttoProposta());
				lPerformanceLogger.start();		
				output = lConsultazioneContabiliaImpl.annullamentoproposta(getLocale(), input);
				lPerformanceLogger.end();		
			} catch(Exception e) {
				String errorMessage = "Si è verificato un'errore durante la chiamata ai servizi di Contabilia (AnnullamentoProposta)";
				logger.error(errorMessage + ": " + e.getMessage(), e);
				throw new StoreException(errorMessage);
			}			
			if(output.getEntry() != null && output.getEntry().isResponseElaborazione()) {				
				bean.setEsitoEventoContabilia("OK");				
				bean.setDataEventoContabilia(new Date());
			} else {				
				String errorMessage = "Si è verificato un'errore durante la chiamata ai servizi di Contabilia";
				if(output.getEntry() != null && StringUtils.isNotBlank(output.getEntry().getMessaggioElaborazione())) {									
					errorMessage += " (AnnullamentoProposta): " + output.getEntry().getMessaggioElaborazione();
				}
				logger.error(errorMessage);
				bean.setEsitoEventoContabilia("KO");
				bean.setDataEventoContabilia(new Date());
				bean.setErrMsgEventoContabilia(errorMessage);			
//				throw new StoreException(errorMessage);	
			}
		}
		return bean;
	}
	
	// chiama i 2 metodi invioProposta ed invioAttoDef in successione (quest'ultimo viene chiamato solo se l'invio è andato a buon fine)
	public NuovaPropostaAtto2CompletaBean creaLiquidazione(NuovaPropostaAtto2CompletaBean bean) throws Exception {
		if(isAttivoContabilia()) {
			AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
			String idDominio = null;
			if (loginBean.getDominio() != null && !"".equals(loginBean.getDominio())) {
				if (loginBean.getDominio().split(":").length == 2) {
					idDominio = loginBean.getDominio().split(":")[1];
				}
			}
			ContabiliaElaboraAttiAmministrativiRequest input = new ContabiliaElaboraAttiAmministrativiRequest();
			input.setIdSpAoo(StringUtils.isNotBlank(idDominio) ? idDominio : null);		
			input.setAnnoAttoDefinitivo(StringUtils.isNotBlank(bean.getAnnoRegLiquidazione()) ? Integer.parseInt(bean.getAnnoRegLiquidazione()) : null);		
			input.setNumeroAttoDefinitivo(StringUtils.isNotBlank(bean.getNroRegLiquidazione()) ? bean.getNroRegLiquidazione() : "");		
			input.setTipoAttoDefinitivo(StringUtils.isNotBlank(bean.getCodTipoLiquidazioneXContabilia()) ? bean.getCodTipoLiquidazioneXContabilia() : ParametriDBUtil.getParametroDB(getSession(),"COD_TIPO_CONTABILIA_ATTO_LIQ"));
			input.setDataCreazione(bean.getDataAdozioneLiquidazione() != null ? new SimpleDateFormat("yyyyMMddHHmmss").format(bean.getDataAdozioneLiquidazione()) : "");
			input.setDataProposta(bean.getDataAdozioneLiquidazione() != null ? new SimpleDateFormat("yyyyMMdd").format(bean.getDataAdozioneLiquidazione()) : "");
			input.setDataApprovazione(bean.getDataAdozioneLiquidazione() != null ? new SimpleDateFormat("yyyyMMddHHmmss").format(bean.getDataAdozioneLiquidazione()) : "");
			input.setDataEsecutivita(bean.getDataAdozioneLiquidazione() != null ? new SimpleDateFormat("yyyyMMdd").format(bean.getDataAdozioneLiquidazione()) : "");
			input.setOggetto(StringUtils.isNotBlank(bean.getOggetto()) ? bean.getOggetto() : "");
			input.setNote("");
			input.setIdentificativo(StringUtils.isNotBlank(bean.getIdUdLiquidazione()) ? bean.getIdUdLiquidazione() : "");
			input.setDirigenteResponsabile(StringUtils.isNotBlank(bean.getDirigenteResponsabileContabilia()) ? bean.getDirigenteResponsabileContabilia() : "");
			input.setTrasparenza("");
			input.setCentroResponsabilita(StringUtils.isNotBlank(bean.getCentroResponsabilitaContabilia()) ? bean.getCentroResponsabilitaContabilia() : "");
			input.setCentroCosto(StringUtils.isNotBlank(bean.getCentroCostoContabilia()) ? bean.getCentroCostoContabilia() : "");
			ConsultazioneContabiliaImpl lConsultazioneContabiliaImpl = new ConsultazioneContabiliaImpl(); 
			ContabiliaOutputElaboraAttiAmministrativi output = null;
			try {
				PerformanceLogger lPerformanceLogger = new PerformanceLogger("ContabiliaCreaLiquidazione " + input.getTipoAttoDefinitivo() + " " + input.getNumeroAttoDefinitivo() + " " + input.getAnnoAttoDefinitivo());
				lPerformanceLogger.start();		
				output = lConsultazioneContabiliaImpl.crealiquidazione(getLocale(), input);
				lPerformanceLogger.end();		
			} catch(Exception e) {
				String errorMessage = "Si è verificato un'errore durante la chiamata ai servizi di Contabilia (CreaLiquidazione)";
				logger.error(errorMessage + ": " + e.getMessage(), e);
				throw new StoreException(errorMessage);
			}			
			if(output.getEntry() != null && output.getEntry().isResponseElaborazione()) {				
				bean.setEsitoEventoContabilia("OK");	
				bean.setDataEventoContabilia(new Date());
				bean.setIdPropostaAMC("-999");
			} else {				
				String errorMessage = "Si è verificato un'errore durante la chiamata ai servizi di Contabilia";
				if(output.getEntry() != null && StringUtils.isNotBlank(output.getEntry().getMessaggioElaborazione())) {									
					errorMessage += " (CreaLiquidazione): " + output.getEntry().getMessaggioElaborazione();
				}
				logger.error(errorMessage);
				bean.setEsitoEventoContabilia("KO");
				bean.setDataEventoContabilia(new Date());
				bean.setErrMsgEventoContabilia(errorMessage);			
//				throw new StoreException(errorMessage);	
			}
		}
		return bean;
	}

	public ContabiliaRicercaImpegnoRequest buildContabiliaRicercaImpegnoRequest(NuovaPropostaAtto2CompletaBean bean) {		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String idDominio = null;
		if (loginBean.getDominio() != null && !"".equals(loginBean.getDominio())) {
			if (loginBean.getDominio().split(":").length == 2) {
				idDominio = loginBean.getDominio().split(":")[1];
			}
		}
		ContabiliaRicercaImpegnoRequest lContabiliaRicercaImpegnoRequest = new ContabiliaRicercaImpegnoRequest();
		lContabiliaRicercaImpegnoRequest.setIdSpAoo(StringUtils.isNotBlank(idDominio) ? idDominio : null);	
		String annoBilancio = StringUtils.isNotBlank(bean.getAnnoContabileCompetenza()) ? bean.getAnnoContabileCompetenza() : ParametriDBUtil.getParametroDB(getSession(), "ANNO_BILANCIO");
		if(StringUtils.isNotBlank(annoBilancio)) {
			lContabiliaRicercaImpegnoRequest.setAnnoBilancio(Integer.parseInt(annoBilancio));
		} else {
//			lContabiliaRicercaImpegnoRequest.setAnnoBilancio(Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date())));
		}
		lContabiliaRicercaImpegnoRequest.setAnnoProvvedimento(bean.getDataRegProvvisoria() != null ? Integer.parseInt(new SimpleDateFormat("yyyy").format(bean.getDataRegProvvisoria())) : null);
		lContabiliaRicercaImpegnoRequest.setNumeroProvvedimento(StringUtils.isNotBlank(bean.getNumeroRegProvvisoria()) ? Integer.parseInt(bean.getNumeroRegProvvisoria()) : null);
		lContabiliaRicercaImpegnoRequest.setCodiceTipoProvvedimento(getTipoAttoContabiliaFromSiglaReg(bean.getSiglaRegProvvisoria())); // lContabiliaRicercaImpegnoRequest.setCodiceTipoProvvedimento("PDD");
		if(isAttivoClienteRP() && StringUtils.isNotBlank(bean.getCentroResponsabilitaContabilia())) {			
			lContabiliaRicercaImpegnoRequest.setCodiceTipoStruttura("CDR");
			lContabiliaRicercaImpegnoRequest.setCodiceStruttura(bean.getCentroResponsabilitaContabilia());
		} else if(isAttivoClienteCMTO() && StringUtils.isNotBlank(bean.getCentroCostoContabilia())) {			
//			lContabiliaRicercaImpegnoRequest.setCodiceTipoStruttura("CDC");
//			lContabiliaRicercaImpegnoRequest.setCodiceStruttura(bean.getCentroCostoContabilia());
		}		
		return lContabiliaRicercaImpegnoRequest;
	}
	
	public List<MovimentiContabiliaXmlBean> ricercaImpegno(NuovaPropostaAtto2CompletaBean bean) throws Exception {
		List<MovimentiContabiliaXmlBean> data = new ArrayList<MovimentiContabiliaXmlBean>();			
		if(isAttivoContabilia()) {
			ConsultazioneContabiliaImpl lConsultazioneContabiliaImpl = new ConsultazioneContabiliaImpl(); 
			ContabiliaRicercaImpegnoRequest input = buildContabiliaRicercaImpegnoRequest(bean);
			ContabiliaOutputRicercaImpegno output = null;
			try {
				PerformanceLogger lPerformanceLogger = new PerformanceLogger("ContabiliaRicercaImpegno " + input.getCodiceTipoProvvedimento() + " " + input.getNumeroProvvedimento() + " " + input.getAnnoProvvedimento());
				lPerformanceLogger.start();		
				output = lConsultazioneContabiliaImpl.ricercaimpegno(getLocale(), input);
				lPerformanceLogger.end();		
			} catch(Exception e) {
				String errorMessage = "Si è verificato un'errore durante la chiamata ai servizi di Contabilia (RicercaImpegno)";
				logger.error(errorMessage + ": " + e.getMessage(), e);
				throw new StoreException(errorMessage);
			}	
			if (output.isOk() && output.getEntry() != null && output.getEntry().getEsito() != null && StringUtils.isNotBlank(output.getEntry().getEsito().value()) && "SUCCESSO".equals(output.getEntry().getEsito().value())) {
				if(output.getEntry().getImpegni() != null) {
					for(ContabiliaImpegno lContabiliaImpegno : output.getEntry().getImpegni()) {
						MovimentiContabiliaXmlBean lMovimentiContabiliaXmlBean = new MovimentiContabiliaXmlBean();		
						lMovimentiContabiliaXmlBean.setFlgEntrataUscita("U");
						lMovimentiContabiliaXmlBean.setAnnoMovimento(lContabiliaImpegno.getAnnoImpegno() != null ? "" + lContabiliaImpegno.getAnnoImpegno().intValue() : null);
						lMovimentiContabiliaXmlBean.setNumeroMovimento(lContabiliaImpegno.getNumeroImpegno() != null ? "" + lContabiliaImpegno.getNumeroImpegno().intValue() : null);
						lMovimentiContabiliaXmlBean.setTipoMovimento(lContabiliaImpegno.getTipoImpegno());
						lMovimentiContabiliaXmlBean.setImporto(lContabiliaImpegno.getImporto() != null ? Double.toString(lContabiliaImpegno.getImporto().doubleValue()).replace(".", ",") : null);
						lMovimentiContabiliaXmlBean.setCodiceMovimento(lContabiliaImpegno.getCodice());
						lMovimentiContabiliaXmlBean.setDescrizioneMovimento(lContabiliaImpegno.getDescrizione());
						lMovimentiContabiliaXmlBean.setNumeroCapitolo(lContabiliaImpegno.getNumeroCapitolo() != null ? "" + lContabiliaImpegno.getNumeroCapitolo().intValue() : null);
						lMovimentiContabiliaXmlBean.setNumeroArticolo(lContabiliaImpegno.getNumeroArticolo() != null ? "" + lContabiliaImpegno.getNumeroArticolo().intValue() : null);
						lMovimentiContabiliaXmlBean.setNumeroUEB(lContabiliaImpegno.getNumeroUEB() != null ? "" + lContabiliaImpegno.getNumeroUEB().intValue() : null);
						lMovimentiContabiliaXmlBean.setCodiceCIG(lContabiliaImpegno.getCig());
						lMovimentiContabiliaXmlBean.setCodiceCUP(lContabiliaImpegno.getCup());
						lMovimentiContabiliaXmlBean.setCodiceSoggetto(lContabiliaImpegno.getCodiceSoggetto());
						lMovimentiContabiliaXmlBean.setParereFinanziario(lContabiliaImpegno.getParereFinanziario() ? "SI" : "NO");
						if(lContabiliaImpegno.getPdc() != null) {
							lMovimentiContabiliaXmlBean.setCodicePdC(lContabiliaImpegno.getPdc().getCodice());
							lMovimentiContabiliaXmlBean.setDescrizionePdC(lContabiliaImpegno.getPdc().getDescrizione());
						}
						if(lContabiliaImpegno.getStato() != null) {
							lMovimentiContabiliaXmlBean.setCodiceStato(lContabiliaImpegno.getStato().getCodice());
							lMovimentiContabiliaXmlBean.setDescrizioneStato(lContabiliaImpegno.getStato().getDescrizione());
						}						
						data.add(lMovimentiContabiliaXmlBean);
					}
				}				
			} else {				
				String errorMessage = "Si è verificato un'errore durante la chiamata ai servizi di Contabilia";
				if(output.getEntry() != null && output.getEntry().getErrori() != null && output.getEntry().getErrori().size() > 0) {									
					errorMessage += " (RicercaImpegno): ";
					for(int i = 0; i < output.getEntry().getErrori().size(); i++) {
						if(i > 0) {
							errorMessage += "; ";
						}
						errorMessage += output.getEntry().getErrori().get(i).getCodice() + " " + output.getEntry().getErrori().get(i).getDescrizione();
					}								
				}
				logger.error(errorMessage);
				throw new StoreException(errorMessage);
			}
		}
		return data;
	}
	
	public ContabiliaRicercaAccertamentoRequest buildContabiliaRicercaAccertamentoRequest(NuovaPropostaAtto2CompletaBean bean) {		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String idDominio = null;
		if (loginBean.getDominio() != null && !"".equals(loginBean.getDominio())) {
			if (loginBean.getDominio().split(":").length == 2) {
				idDominio = loginBean.getDominio().split(":")[1];
			}
		}
		ContabiliaRicercaAccertamentoRequest lContabiliaRicercaAccertamentoRequest = new ContabiliaRicercaAccertamentoRequest();
		lContabiliaRicercaAccertamentoRequest.setIdSpAoo(StringUtils.isNotBlank(idDominio) ? idDominio : null);		
		String annoBilancio = StringUtils.isNotBlank(bean.getAnnoContabileCompetenza()) ? bean.getAnnoContabileCompetenza() : ParametriDBUtil.getParametroDB(getSession(), "ANNO_BILANCIO");
		if(StringUtils.isNotBlank(annoBilancio)) {
			lContabiliaRicercaAccertamentoRequest.setAnnoBilancio(Integer.parseInt(annoBilancio));
		} else {
//			lContabiliaRicercaAccertamentoRequest.setAnnoBilancio(Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date())));
		}
		lContabiliaRicercaAccertamentoRequest.setAnnoProvvedimento(bean.getDataRegProvvisoria() != null ? Integer.parseInt(new SimpleDateFormat("yyyy").format(bean.getDataRegProvvisoria())) : null);
		lContabiliaRicercaAccertamentoRequest.setNumeroProvvedimento(StringUtils.isNotBlank(bean.getNumeroRegProvvisoria()) ? Integer.parseInt(bean.getNumeroRegProvvisoria()) : null);		
		lContabiliaRicercaAccertamentoRequest.setCodiceTipoProvvedimento(getTipoAttoContabiliaFromSiglaReg(bean.getSiglaRegProvvisoria())); // lContabiliaRicercaAccertamentoRequest.setCodiceTipoProvvedimento("PDD");
		if(isAttivoClienteRP() && StringUtils.isNotBlank(bean.getCentroResponsabilitaContabilia())) {			
			lContabiliaRicercaAccertamentoRequest.setCodiceTipoStruttura("CDR");
			lContabiliaRicercaAccertamentoRequest.setCodiceStruttura(bean.getCentroResponsabilitaContabilia());
		} else if(isAttivoClienteCMTO() && StringUtils.isNotBlank(bean.getCentroCostoContabilia())) {			
//			lContabiliaRicercaAccertamentoRequest.setCodiceTipoStruttura("CDC");
//			lContabiliaRicercaAccertamentoRequest.setCodiceStruttura(bean.getCentroCostoContabilia());
		}
		return lContabiliaRicercaAccertamentoRequest;
	}
	
	public List<MovimentiContabiliaXmlBean> ricercaAccertamento(NuovaPropostaAtto2CompletaBean bean) throws Exception {
		List<MovimentiContabiliaXmlBean> data = new ArrayList<MovimentiContabiliaXmlBean>();			
		if(isAttivoContabilia()) {
			ConsultazioneContabiliaImpl lConsultazioneContabiliaImpl = new ConsultazioneContabiliaImpl(); 
			ContabiliaRicercaAccertamentoRequest input = buildContabiliaRicercaAccertamentoRequest(bean);
			ContabiliaOutputRicercaAccertamento output = null;
			try {
				PerformanceLogger lPerformanceLogger = new PerformanceLogger("ContabiliaRicercaAccertamento " + input.getCodiceTipoProvvedimento() + " " + input.getNumeroProvvedimento() + " " + input.getAnnoProvvedimento());
				lPerformanceLogger.start();		
				output = lConsultazioneContabiliaImpl.ricercaaccertamento(getLocale(), input);
				lPerformanceLogger.end();		
			} catch(Exception e) {
				String errorMessage = "Si è verificato un'errore durante la chiamata ai servizi di Contabilia (RicercaAccertamento)";
				logger.error(errorMessage + ": " + e.getMessage(), e);
				throw new StoreException(errorMessage);
			}	
			if (output.isOk() && output.getEntry() != null && output.getEntry().getEsito() != null && StringUtils.isNotBlank(output.getEntry().getEsito().value()) && "SUCCESSO".equals(output.getEntry().getEsito().value())) {
				if(output.getEntry().getAccertamenti() != null) {
					for(ContabiliaAccertamento lContabiliaAccertamento : output.getEntry().getAccertamenti()) {
						MovimentiContabiliaXmlBean lMovimentiContabiliaXmlBean = new MovimentiContabiliaXmlBean();							
						lMovimentiContabiliaXmlBean.setFlgEntrataUscita("E");
						lMovimentiContabiliaXmlBean.setAnnoMovimento(lContabiliaAccertamento.getAnnoAccertamento() != null ? "" + lContabiliaAccertamento.getAnnoAccertamento().intValue() : null);
						lMovimentiContabiliaXmlBean.setNumeroMovimento(lContabiliaAccertamento.getNumeroAccertamento() != null ? "" + lContabiliaAccertamento.getNumeroAccertamento().intValue() : null);
						lMovimentiContabiliaXmlBean.setTipoMovimento(null);
						lMovimentiContabiliaXmlBean.setImporto(lContabiliaAccertamento.getImporto() != null ? Double.toString(lContabiliaAccertamento.getImporto().doubleValue()).replace(".", ",") : null);
						lMovimentiContabiliaXmlBean.setCodiceMovimento(lContabiliaAccertamento.getCodice());
						lMovimentiContabiliaXmlBean.setDescrizioneMovimento(lContabiliaAccertamento.getDescrizione());
						lMovimentiContabiliaXmlBean.setNumeroCapitolo(lContabiliaAccertamento.getNumeroCapitolo() != null ? "" + lContabiliaAccertamento.getNumeroCapitolo().intValue() : null);
						lMovimentiContabiliaXmlBean.setNumeroArticolo(lContabiliaAccertamento.getNumeroArticolo() != null ? "" + lContabiliaAccertamento.getNumeroArticolo().intValue() : null);
						lMovimentiContabiliaXmlBean.setNumeroUEB(lContabiliaAccertamento.getNumeroUEB() != null ? "" + lContabiliaAccertamento.getNumeroUEB().intValue() : null);
						lMovimentiContabiliaXmlBean.setCodiceCIG(lContabiliaAccertamento.getCig());
						lMovimentiContabiliaXmlBean.setCodiceCUP(lContabiliaAccertamento.getCup());
						lMovimentiContabiliaXmlBean.setCodiceSoggetto(lContabiliaAccertamento.getCodiceSoggetto());
						lMovimentiContabiliaXmlBean.setParereFinanziario(lContabiliaAccertamento.getParereFinanziario() ? "SI" : "NO");
						if(lContabiliaAccertamento.getPdc() != null) {
							lMovimentiContabiliaXmlBean.setCodicePdC(lContabiliaAccertamento.getPdc().getCodice());
							lMovimentiContabiliaXmlBean.setDescrizionePdC(lContabiliaAccertamento.getPdc().getDescrizione());
						}
						if(lContabiliaAccertamento.getStato() != null) {
							lMovimentiContabiliaXmlBean.setCodiceStato(lContabiliaAccertamento.getStato().getCodice());
							lMovimentiContabiliaXmlBean.setDescrizioneStato(lContabiliaAccertamento.getStato().getDescrizione());
						}						
						data.add(lMovimentiContabiliaXmlBean);
					}
				}
			} else {		
				String errorMessage = "Si è verificato un'errore durante la chiamata ai servizi di Contabilia";
				if(output.getEntry() != null && output.getEntry().getErrori() != null && output.getEntry().getErrori().size() > 0) {									
					errorMessage += " (RicercaAccertamento): ";
					for(int i = 0; i < output.getEntry().getErrori().size(); i++) {
						if(i > 0) {
							errorMessage += "; ";
						}
						errorMessage += output.getEntry().getErrori().get(i).getCodice() + " " + output.getEntry().getErrori().get(i).getDescrizione();
					}							
				}
				logger.error(errorMessage);
				throw new StoreException(errorMessage);				
			}
		}
		return data;
	}
	
	public ContabiliaRicercaMovimentoGestioneStiloRequest buildContabiliaRicercaMovimentoGestioneStiloRequest(NuovaPropostaAtto2CompletaBean bean, boolean hasNumDefinitiva) {		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String idDominio = null;
		if (loginBean.getDominio() != null && !"".equals(loginBean.getDominio())) {
			if (loginBean.getDominio().split(":").length == 2) {
				idDominio = loginBean.getDominio().split(":")[1];
			}
		}
		ContabiliaRicercaMovimentoGestioneStiloRequest lContabiliaRicercaMovimentoGestioneStiloRequest = new ContabiliaRicercaMovimentoGestioneStiloRequest();
		lContabiliaRicercaMovimentoGestioneStiloRequest.setIdSpAoo(StringUtils.isNotBlank(idDominio) ? idDominio : null);			
		String annoBilancio = StringUtils.isNotBlank(bean.getAnnoContabileCompetenza()) ? bean.getAnnoContabileCompetenza() : ParametriDBUtil.getParametroDB(getSession(), "ANNO_BILANCIO");		
		if(StringUtils.isNotBlank(annoBilancio)) {
			lContabiliaRicercaMovimentoGestioneStiloRequest.setAnnoBilancio(Integer.parseInt(annoBilancio));
		} else {
//			lContabiliaRicercaMovimentoGestioneStiloRequest.setAnnoBilancio(Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date())));	
		}
		if(hasNumDefinitiva && StringUtils.isNotBlank(bean.getNumeroRegistrazione())) {
			lContabiliaRicercaMovimentoGestioneStiloRequest.setAnnoProvvedimento(bean.getDataRegistrazione() != null ? Integer.parseInt(new SimpleDateFormat("yyyy").format(bean.getDataRegistrazione())) : null);
			lContabiliaRicercaMovimentoGestioneStiloRequest.setNumeroProvvedimento(StringUtils.isNotBlank(bean.getNumeroRegistrazione()) ? Integer.parseInt(bean.getNumeroRegistrazione()) : null);		
			lContabiliaRicercaMovimentoGestioneStiloRequest.setCodiceTipoProvvedimento(getTipoAttoContabiliaFromSiglaReg(bean.getSiglaRegistrazione())); // lContabiliaRicercaMovimentoGestioneStiloRequest.setCodiceTipoProvvedimento("DD");		
		} else {
			lContabiliaRicercaMovimentoGestioneStiloRequest.setAnnoProvvedimento(bean.getDataRegProvvisoria() != null ? Integer.parseInt(new SimpleDateFormat("yyyy").format(bean.getDataRegProvvisoria())) : null);
			lContabiliaRicercaMovimentoGestioneStiloRequest.setNumeroProvvedimento(StringUtils.isNotBlank(bean.getNumeroRegProvvisoria()) ? Integer.parseInt(bean.getNumeroRegProvvisoria()) : null);		
			lContabiliaRicercaMovimentoGestioneStiloRequest.setCodiceTipoProvvedimento(getTipoAttoContabiliaFromSiglaReg(bean.getSiglaRegProvvisoria())); // lContabiliaRicercaMovimentoGestioneStiloRequest.setCodiceTipoProvvedimento("PDD");
		}
		if(isAttivoClienteRP() && StringUtils.isNotBlank(bean.getCentroResponsabilitaContabilia())) {			
			lContabiliaRicercaMovimentoGestioneStiloRequest.setCodiceTipoStruttura("CDR");
			lContabiliaRicercaMovimentoGestioneStiloRequest.setCodiceStruttura(bean.getCentroResponsabilitaContabilia());
		} else if(isAttivoClienteCMTO() && StringUtils.isNotBlank(bean.getCentroCostoContabilia())) {			
//			lContabiliaRicercaMovimentoGestioneStiloRequest.setCodiceTipoStruttura("CDC");
//			lContabiliaRicercaMovimentoGestioneStiloRequest.setCodiceStruttura(bean.getCentroCostoContabilia());
		}
		return lContabiliaRicercaMovimentoGestioneStiloRequest;
	}
	
	public List<MovimentiContabiliaXmlBean> ricercaMovimentoGestione(NuovaPropostaAtto2CompletaBean bean) throws Exception {
		ContabiliaRicercaMovimentoGestioneStiloRequest input = buildContabiliaRicercaMovimentoGestioneStiloRequest(bean, false);
		return ricercaMovimentoGestione(bean, input);
	}


	public List<MovimentiContabiliaXmlBean> ricercaMovimentoGestioneWithNumDefinitiva(NuovaPropostaAtto2CompletaBean bean) throws Exception {
		ContabiliaRicercaMovimentoGestioneStiloRequest input = buildContabiliaRicercaMovimentoGestioneStiloRequest(bean, true);
		return ricercaMovimentoGestione(bean, input);
	}
	
	private List<MovimentiContabiliaXmlBean> ricercaMovimentoGestione(NuovaPropostaAtto2CompletaBean bean, ContabiliaRicercaMovimentoGestioneStiloRequest input) throws Exception {
		List<MovimentiContabiliaXmlBean> data = new ArrayList<MovimentiContabiliaXmlBean>();			
		if(isAttivoContabilia()) {
			ConsultazioneContabiliaImpl lConsultazioneContabiliaImpl = new ConsultazioneContabiliaImpl(); 
			ContabiliaOutputRicercaMovimentoGestione output = null;
			try {
				PerformanceLogger lPerformanceLogger = new PerformanceLogger("ContabiliaRicercaMovimentoGestione " + input.getCodiceTipoProvvedimento() + " " + input.getNumeroProvvedimento() + " " + input.getAnnoProvvedimento());
				lPerformanceLogger.start();		
				output = lConsultazioneContabiliaImpl.ricercamovimentogestione(getLocale(), input);
				lPerformanceLogger.end();		
			} catch(Exception e) {
				String errorMessage = "Si è verificato un'errore durante la chiamata ai servizi di Contabilia (RicercaMovimentoGestione)";
				logger.error(errorMessage + ": " + e.getMessage(), e);
				throw new StoreException(errorMessage);
			}	
			if (output.isOk() && output.getEntry() != null && output.getEntry().getEsito() != null && StringUtils.isNotBlank(output.getEntry().getEsito().value()) && "SUCCESSO".equals(output.getEntry().getEsito().value())) {
				if(output.getEntry().getMovimentiGestione() != null) {
					for(ContabiliaMovimentoGestioneStilo lContabiliaMovimentoGestioneStilo : output.getEntry().getMovimentiGestione()) {
						if(lContabiliaMovimentoGestioneStilo.getTipoMovimentoGestione() != null) {
							MovimentiContabiliaXmlBean lMovimentiContabiliaXmlBean = new MovimentiContabiliaXmlBean();			
							lMovimentiContabiliaXmlBean.setTipoMovimento(lContabiliaMovimentoGestioneStilo.getTipoMovimentoGestione());
							lMovimentiContabiliaXmlBean.setCodiceMovimento(null);
							if(lContabiliaMovimentoGestioneStilo.getNumeroModifica() != null) {								
								lMovimentiContabiliaXmlBean.setAnnoModifica(lContabiliaMovimentoGestioneStilo.getAnnoCompetenzaModifica() != null ? "" + lContabiliaMovimentoGestioneStilo.getAnnoCompetenzaModifica().intValue() : null);
								lMovimentiContabiliaXmlBean.setNumeroModifica(lContabiliaMovimentoGestioneStilo.getNumeroModifica() != null ? "" + lContabiliaMovimentoGestioneStilo.getNumeroModifica().intValue() : null);	
								lMovimentiContabiliaXmlBean.setDescrizioneModifica(lContabiliaMovimentoGestioneStilo.getDescrizioneModifica());
								lMovimentiContabiliaXmlBean.setImportoModifica(lContabiliaMovimentoGestioneStilo.getImportoModifica() != null ? Double.toString(lContabiliaMovimentoGestioneStilo.getImportoModifica().doubleValue()).replace(".", ",") : null);
							}
							if(lContabiliaMovimentoGestioneStilo.getNumeroImpegno() != null) {
								lMovimentiContabiliaXmlBean.setFlgEntrataUscita("U");
								lMovimentiContabiliaXmlBean.setAnnoMovimento(lContabiliaMovimentoGestioneStilo.getAnnoCompetenza() != null ? "" + lContabiliaMovimentoGestioneStilo.getAnnoCompetenza().intValue() : null);
								lMovimentiContabiliaXmlBean.setNumeroMovimento(lContabiliaMovimentoGestioneStilo.getNumeroImpegno() != null ? "" + lContabiliaMovimentoGestioneStilo.getNumeroImpegno().intValue() : null);
								lMovimentiContabiliaXmlBean.setDescrizioneMovimento(lContabiliaMovimentoGestioneStilo.getDescrizioneImpegno());
								if(lContabiliaMovimentoGestioneStilo.getNumeroSubImpegno() != null) {
									lMovimentiContabiliaXmlBean.setAnnoSub(lContabiliaMovimentoGestioneStilo.getAnnoCompetenzaSubImpegno() != null ? "" + lContabiliaMovimentoGestioneStilo.getAnnoCompetenzaSubImpegno().intValue() : null);
									lMovimentiContabiliaXmlBean.setNumeroSub(lContabiliaMovimentoGestioneStilo.getNumeroSubImpegno() != null ? "" + lContabiliaMovimentoGestioneStilo.getNumeroSubImpegno().intValue() : null);
									lMovimentiContabiliaXmlBean.setDescrizioneSub(lContabiliaMovimentoGestioneStilo.getDescrizioneSubImpegno());
									lMovimentiContabiliaXmlBean.setImporto(lContabiliaMovimentoGestioneStilo.getImportoAttualeSubImpegno() != null ? Double.toString(lContabiliaMovimentoGestioneStilo.getImportoAttualeSubImpegno().doubleValue()).replace(".", ",") : null);
									lMovimentiContabiliaXmlBean.setImportoIniziale(lContabiliaMovimentoGestioneStilo.getImportoInizialeSubImpegno() != null ? Double.toString(lContabiliaMovimentoGestioneStilo.getImportoInizialeSubImpegno().doubleValue()).replace(".", ",") : null);
								} else {
									lMovimentiContabiliaXmlBean.setImporto(lContabiliaMovimentoGestioneStilo.getImportoAttualeImpegno() != null ? Double.toString(lContabiliaMovimentoGestioneStilo.getImportoAttualeImpegno().doubleValue()).replace(".", ",") : null);
									lMovimentiContabiliaXmlBean.setImportoIniziale(lContabiliaMovimentoGestioneStilo.getImportoInizialeImpegno() != null ? Double.toString(lContabiliaMovimentoGestioneStilo.getImportoInizialeImpegno().doubleValue()).replace(".", ",") : null);								
								}
							} else if(lContabiliaMovimentoGestioneStilo.getNumeroAccertamento() != null) {
								lMovimentiContabiliaXmlBean.setFlgEntrataUscita("E");
								lMovimentiContabiliaXmlBean.setAnnoMovimento(lContabiliaMovimentoGestioneStilo.getAnnoCompetenza() != null ? "" + lContabiliaMovimentoGestioneStilo.getAnnoCompetenza().intValue() : null);
								lMovimentiContabiliaXmlBean.setNumeroMovimento(lContabiliaMovimentoGestioneStilo.getNumeroAccertamento() != null ? "" + lContabiliaMovimentoGestioneStilo.getNumeroAccertamento().intValue() : null);
								lMovimentiContabiliaXmlBean.setDescrizioneMovimento(lContabiliaMovimentoGestioneStilo.getDescrizioneAccertamento());
								if(lContabiliaMovimentoGestioneStilo.getNumeroSubAccertamento() != null) {
									lMovimentiContabiliaXmlBean.setAnnoSub(lContabiliaMovimentoGestioneStilo.getAnnoCompetenzaSubAccertamento() != null ? "" + lContabiliaMovimentoGestioneStilo.getAnnoCompetenzaSubAccertamento().intValue() : null);
									lMovimentiContabiliaXmlBean.setNumeroSub(lContabiliaMovimentoGestioneStilo.getNumeroSubAccertamento() != null ? "" + lContabiliaMovimentoGestioneStilo.getNumeroSubAccertamento().intValue() : null);
									lMovimentiContabiliaXmlBean.setDescrizioneSub(lContabiliaMovimentoGestioneStilo.getDescrizioneSubAccertamento());	
									lMovimentiContabiliaXmlBean.setImporto(lContabiliaMovimentoGestioneStilo.getImportoAttualeSubAccertamento() != null ? Double.toString(lContabiliaMovimentoGestioneStilo.getImportoAttualeSubAccertamento().doubleValue()).replace(".", ",") : null);
									lMovimentiContabiliaXmlBean.setImportoIniziale(lContabiliaMovimentoGestioneStilo.getImportoInizialeSubAccertamento() != null ? Double.toString(lContabiliaMovimentoGestioneStilo.getImportoInizialeSubAccertamento().doubleValue()).replace(".", ",") : null);
								} else {
									lMovimentiContabiliaXmlBean.setImporto(lContabiliaMovimentoGestioneStilo.getImportoAttualeAccertamento() != null ? Double.toString(lContabiliaMovimentoGestioneStilo.getImportoAttualeAccertamento().doubleValue()).replace(".", ",") : null);
									lMovimentiContabiliaXmlBean.setImportoIniziale(lContabiliaMovimentoGestioneStilo.getImportoInizialeAccertamento() != null ? Double.toString(lContabiliaMovimentoGestioneStilo.getImportoInizialeAccertamento().doubleValue()).replace(".", ",") : null);									
								}
							}  					
							lMovimentiContabiliaXmlBean.setNumeroCapitolo(lContabiliaMovimentoGestioneStilo.getCapitolo() != null && lContabiliaMovimentoGestioneStilo.getCapitolo().getNumeroCapitolo() != null ? "" + lContabiliaMovimentoGestioneStilo.getCapitolo().getNumeroCapitolo().intValue() : null);
							lMovimentiContabiliaXmlBean.setNumeroArticolo(lContabiliaMovimentoGestioneStilo.getCapitolo() != null && lContabiliaMovimentoGestioneStilo.getCapitolo().getNumeroArticolo() != null ? "" + lContabiliaMovimentoGestioneStilo.getCapitolo().getNumeroArticolo().intValue() : null);
							lMovimentiContabiliaXmlBean.setNumeroUEB(lContabiliaMovimentoGestioneStilo.getCapitolo() != null && lContabiliaMovimentoGestioneStilo.getCapitolo().getNumeroUEB() != null ? "" + lContabiliaMovimentoGestioneStilo.getCapitolo().getNumeroUEB().intValue() : null);								
							lMovimentiContabiliaXmlBean.setCodiceCIG(lContabiliaMovimentoGestioneStilo.getCig());
							lMovimentiContabiliaXmlBean.setMotivoAssenzaCIG(lContabiliaMovimentoGestioneStilo.getMotivoAssenzaCig());
							lMovimentiContabiliaXmlBean.setCodiceCUP(lContabiliaMovimentoGestioneStilo.getCup());								
							lMovimentiContabiliaXmlBean.setCodiceSoggetto(lContabiliaMovimentoGestioneStilo.getSoggetto() != null ? lContabiliaMovimentoGestioneStilo.getSoggetto().getCodice() : null);
							lMovimentiContabiliaXmlBean.setDescrizioneSoggetto(lContabiliaMovimentoGestioneStilo.getSoggetto() != null ? lContabiliaMovimentoGestioneStilo.getSoggetto().getDescrizione() : null);							
							lMovimentiContabiliaXmlBean.setCodiceClasseSoggetto(lContabiliaMovimentoGestioneStilo.getClasseSoggetto() != null ? lContabiliaMovimentoGestioneStilo.getClasseSoggetto().getCodice() : null);
							lMovimentiContabiliaXmlBean.setDescrizioneClasseSoggetto(lContabiliaMovimentoGestioneStilo.getClasseSoggetto() != null ? lContabiliaMovimentoGestioneStilo.getClasseSoggetto().getDescrizione() : null);							
							lMovimentiContabiliaXmlBean.setParereFinanziario(null);
							lMovimentiContabiliaXmlBean.setCodicePdC(lContabiliaMovimentoGestioneStilo.getPianoDeiContiFinanziario() != null ? lContabiliaMovimentoGestioneStilo.getPianoDeiContiFinanziario().getCodice() : null);
							lMovimentiContabiliaXmlBean.setDescrizionePdC(lContabiliaMovimentoGestioneStilo.getPianoDeiContiFinanziario() != null ? lContabiliaMovimentoGestioneStilo.getPianoDeiContiFinanziario().getDescrizione() : null);																
							lMovimentiContabiliaXmlBean.setCodiceStato(null);
							lMovimentiContabiliaXmlBean.setDescrizioneStato(null);		
							lMovimentiContabiliaXmlBean.setDescrizioneCapitolo(lContabiliaMovimentoGestioneStilo.getCapitolo() != null ? lContabiliaMovimentoGestioneStilo.getCapitolo().getDescrizione() : null);
							lMovimentiContabiliaXmlBean.setDescrizioneArticolo(lContabiliaMovimentoGestioneStilo.getCapitolo() != null ? lContabiliaMovimentoGestioneStilo.getCapitolo().getDescrizioneArticolo() : null);
							lMovimentiContabiliaXmlBean.setCodiceCategoria(lContabiliaMovimentoGestioneStilo.getCategoria() != null ? lContabiliaMovimentoGestioneStilo.getCategoria().getCodice() : null);
							lMovimentiContabiliaXmlBean.setDescrizioneCategoria(lContabiliaMovimentoGestioneStilo.getCategoria() != null ? lContabiliaMovimentoGestioneStilo.getCategoria().getDescrizione() : null);
							lMovimentiContabiliaXmlBean.setCodiceCodUE(lContabiliaMovimentoGestioneStilo.getCodUE() != null ? lContabiliaMovimentoGestioneStilo.getCodUE().getCodice() : null);
							lMovimentiContabiliaXmlBean.setDescrizioneCodUE(lContabiliaMovimentoGestioneStilo.getCodUE() != null ? lContabiliaMovimentoGestioneStilo.getCodUE().getDescrizione() : null);
							lMovimentiContabiliaXmlBean.setCodiceCofog(lContabiliaMovimentoGestioneStilo.getCofog() != null ? lContabiliaMovimentoGestioneStilo.getCofog().getCodice() : null);
							lMovimentiContabiliaXmlBean.setDescrizioneCofog(lContabiliaMovimentoGestioneStilo.getCofog() != null ? lContabiliaMovimentoGestioneStilo.getCofog().getDescrizione() : null);
							lMovimentiContabiliaXmlBean.setCodiceGsa(lContabiliaMovimentoGestioneStilo.getGsa() != null ? lContabiliaMovimentoGestioneStilo.getGsa().getCodice() : null);
							lMovimentiContabiliaXmlBean.setDescrizioneGsa(lContabiliaMovimentoGestioneStilo.getGsa() != null ? lContabiliaMovimentoGestioneStilo.getGsa().getDescrizione() : null);
							lMovimentiContabiliaXmlBean.setCodiceMacroaggregato(lContabiliaMovimentoGestioneStilo.getMacroaggregato() != null ? lContabiliaMovimentoGestioneStilo.getMacroaggregato().getCodice() : null);
							lMovimentiContabiliaXmlBean.setDescrizioneMacroaggregato(lContabiliaMovimentoGestioneStilo.getMacroaggregato() != null ? lContabiliaMovimentoGestioneStilo.getMacroaggregato().getDescrizione() : null);
							lMovimentiContabiliaXmlBean.setCodiceMissione(lContabiliaMovimentoGestioneStilo.getMissione() != null ? lContabiliaMovimentoGestioneStilo.getMissione().getCodice() : null);
							lMovimentiContabiliaXmlBean.setDescrizioneMissione(lContabiliaMovimentoGestioneStilo.getMissione() != null ? lContabiliaMovimentoGestioneStilo.getMissione().getDescrizione() : null);
							lMovimentiContabiliaXmlBean.setCodiceNaturaRicorrente(lContabiliaMovimentoGestioneStilo.getNaturaRicorrente() != null ? lContabiliaMovimentoGestioneStilo.getNaturaRicorrente().getCodice() : null);
							lMovimentiContabiliaXmlBean.setDescrizioneNaturaRicorrente(lContabiliaMovimentoGestioneStilo.getNaturaRicorrente() != null ? lContabiliaMovimentoGestioneStilo.getNaturaRicorrente().getDescrizione() : null);
							lMovimentiContabiliaXmlBean.setPrenotazione(lContabiliaMovimentoGestioneStilo.getPrenotazione());
							lMovimentiContabiliaXmlBean.setPrenotazioneLiquidabile(lContabiliaMovimentoGestioneStilo.getPrenotazioneLiquidabile());
							lMovimentiContabiliaXmlBean.setCodiceProgetto(lContabiliaMovimentoGestioneStilo.getProgetto() != null ? lContabiliaMovimentoGestioneStilo.getProgetto().getCodice() : null);
							lMovimentiContabiliaXmlBean.setDescrizioneProgetto(lContabiliaMovimentoGestioneStilo.getProgetto() != null ? lContabiliaMovimentoGestioneStilo.getProgetto().getDescrizione() : null);											
							lMovimentiContabiliaXmlBean.setCodiceProgramma(lContabiliaMovimentoGestioneStilo.getProgramma() != null ? lContabiliaMovimentoGestioneStilo.getProgramma().getCodice() : null);
							lMovimentiContabiliaXmlBean.setDescrizioneProgramma(lContabiliaMovimentoGestioneStilo.getProgramma() != null ? lContabiliaMovimentoGestioneStilo.getProgramma().getDescrizione() : null);
							lMovimentiContabiliaXmlBean.setCodiceTipoDebitoSiope(lContabiliaMovimentoGestioneStilo.getTipoDebitoSiope() != null ? lContabiliaMovimentoGestioneStilo.getTipoDebitoSiope().getCodice() : null);
							lMovimentiContabiliaXmlBean.setDescrizioneTipoDebitoSiope(lContabiliaMovimentoGestioneStilo.getTipoDebitoSiope() != null ? lContabiliaMovimentoGestioneStilo.getTipoDebitoSiope().getDescrizione() : null);											
							lMovimentiContabiliaXmlBean.setCodiceTipoFinanziamento(lContabiliaMovimentoGestioneStilo.getTipoFinanziamento() != null ? lContabiliaMovimentoGestioneStilo.getTipoFinanziamento().getCodice() : null);
							lMovimentiContabiliaXmlBean.setDescrizioneTipoFinanziamento(lContabiliaMovimentoGestioneStilo.getTipoFinanziamento() != null ? lContabiliaMovimentoGestioneStilo.getTipoFinanziamento().getDescrizione() : null);
							lMovimentiContabiliaXmlBean.setCodiceTipologia(lContabiliaMovimentoGestioneStilo.getTipologia() != null ? lContabiliaMovimentoGestioneStilo.getTipologia().getCodice() : null);
							lMovimentiContabiliaXmlBean.setDescrizioneTipologia(lContabiliaMovimentoGestioneStilo.getTipologia() != null ? lContabiliaMovimentoGestioneStilo.getTipologia().getDescrizione() : null);
							lMovimentiContabiliaXmlBean.setCodiceTitolo(lContabiliaMovimentoGestioneStilo.getTitolo() != null ? lContabiliaMovimentoGestioneStilo.getTitolo().getCodice() : null);
							lMovimentiContabiliaXmlBean.setDescrizioneTitolo(lContabiliaMovimentoGestioneStilo.getTitolo() != null ? lContabiliaMovimentoGestioneStilo.getTitolo().getDescrizione() : null);
							data.add(lMovimentiContabiliaXmlBean);
						}													
					}
				}
			} else {		
				String errorMessage = "Si è verificato un'errore durante la chiamata ai servizi di Contabilia";
				if(output.getEntry() != null && output.getEntry().getErrori() != null && output.getEntry().getErrori().size() > 0) {									
					errorMessage += " (RicercaMovimentoGestione): ";
					for(int i = 0; i < output.getEntry().getErrori().size(); i++) {
						if(i > 0) {
							errorMessage += "; ";
						}
						errorMessage += output.getEntry().getErrori().get(i).getCodice() + " " + output.getEntry().getErrori().get(i).getDescrizione();
					}							
				}
				logger.error(errorMessage);
				throw new StoreException(errorMessage);				
			}
		}
		return data;
	}
		
	public boolean isAttivoClienteRP() {
		String cliente = ParametriDBUtil.getParametroDB(getSession(), "CLIENTE");
		return cliente != null && !"".equals(cliente) && "RP".equals(cliente);
	}
	
	public boolean isAttivoClienteCMTO() {
		String cliente = ParametriDBUtil.getParametroDB(getSession(), "CLIENTE");
		return cliente != null && !"".equals(cliente) && "CMTO".equals(cliente);
	}
	
	@Override
	public PaginatorBean<DatiContabiliBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow,
			List<OrderByBean> orderby) throws Exception {
		return null;
	}
	
	@Override
	public DatiContabiliBean get(DatiContabiliBean bean) throws Exception {
		return null;
	}

	@Override
	public DatiContabiliBean add(DatiContabiliBean bean) throws Exception {
		return null;
	}

	@Override
	public DatiContabiliBean remove(DatiContabiliBean bean) throws Exception {
		return null;
	}

	@Override
	public DatiContabiliBean update(DatiContabiliBean bean, DatiContabiliBean oldvalue) throws Exception {
		return null;
	}

	@Override
	public Map<String, ErrorBean> validate(DatiContabiliBean bean) throws Exception {
		return null;
	}
	
}
