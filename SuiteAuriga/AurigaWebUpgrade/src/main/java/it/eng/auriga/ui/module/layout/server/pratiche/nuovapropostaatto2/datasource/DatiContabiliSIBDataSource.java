/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.exception.StoreException;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.DatiContabiliBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.NuovaPropostaAtto2Bean;
import it.eng.client.ContabilitaSIBImpl;
import it.eng.core.performance.PerformanceLogger;
import it.eng.document.function.bean.SibEntry;
import it.eng.document.function.bean.SibInputAggiornaAtto;
import it.eng.document.function.bean.SibInputCreaProposta;
import it.eng.document.function.bean.SibInputElencoDettagliContabili;
import it.eng.document.function.bean.SibInputGetCapitolo;
import it.eng.document.function.bean.SibInputGetVociPeg;
import it.eng.document.function.bean.SibInputGetVociPegVLiv;
import it.eng.document.function.bean.SibOutputAggiornaAtto;
import it.eng.document.function.bean.SibOutputCreaProposta;
import it.eng.document.function.bean.SibOutputElencoDettagliContabili;
import it.eng.document.function.bean.SibOutputGetCapitolo;
import it.eng.document.function.bean.SibOutputGetVociPeg;
import it.eng.document.function.bean.SibRecordDettagliContabili;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.module.layout.server.StringSplitterServer;
import it.eng.utility.ui.user.ParametriDBUtil;

@Datasource(id = "DatiContabiliSIBDataSource")
public class DatiContabiliSIBDataSource extends AbstractFetchDataSource<DatiContabiliBean> {

	private static final Logger logger = Logger.getLogger(DatiContabiliSIBDataSource.class);
	
	@Override
	public PaginatorBean<DatiContabiliBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow,
			List<OrderByBean> orderby) throws Exception {
		String fieldNameCombo = getExtraparams().get("fieldNameCombo");
		String annoEsercizio = getExtraparams().get("annoEsercizio");
		String flgEntrataUscita = getExtraparams().get("flgEntrataUscita");
		List<DatiContabiliBean> data = new ArrayList<DatiContabiliBean>();		
		if(StringUtils.isNotBlank(annoEsercizio) && StringUtils.isNotBlank(flgEntrataUscita)) {
			if(StringUtils.isNotBlank(fieldNameCombo)) {
				String capitolo = getExtraparams().get("capitolo");
				String articolo = getExtraparams().get("articolo");
				String numero = getExtraparams().get("numero");
				String liv1234PdC = getExtraparams().get("liv1234PdC");
				boolean isComboCapitolo = "capitolo".equalsIgnoreCase(fieldNameCombo);
				boolean isComboArticolo = "articolo".equalsIgnoreCase(fieldNameCombo) && StringUtils.isNotBlank(capitolo);
				boolean isComboNumero = "numero".equalsIgnoreCase(fieldNameCombo) && StringUtils.isNotBlank(capitolo) && StringUtils.isNotBlank(articolo);
				boolean isComboLiv5PdC = "liv5PdC".equalsIgnoreCase(fieldNameCombo) && StringUtils.isNotBlank(capitolo) && StringUtils.isNotBlank(articolo) && StringUtils.isNotBlank(numero) && StringUtils.isNotBlank(liv1234PdC);
				boolean isComboAnnoCompetenza = "annoCompetenza".equalsIgnoreCase(fieldNameCombo) && StringUtils.isNotBlank(capitolo) && StringUtils.isNotBlank(articolo) && StringUtils.isNotBlank(numero);
				if(isComboCapitolo || isComboArticolo || isComboNumero || isComboLiv5PdC || isComboAnnoCompetenza) {
					try {
						DatiContabiliBean lDatiContabiliBean = new DatiContabiliBean();
						lDatiContabiliBean.setAnnoEsercizio(annoEsercizio);
						lDatiContabiliBean.setFlgEntrataUscita(flgEntrataUscita);
						if(StringUtils.isNotBlank(capitolo) || StringUtils.isNotBlank(articolo) || StringUtils.isNotBlank(numero)) {
							lDatiContabiliBean.setCapitolo(capitolo);		
							lDatiContabiliBean.setArticolo(articolo);
							lDatiContabiliBean.setNumero(numero);
							if(isComboLiv5PdC) {
								lDatiContabiliBean.setLiv1234PdC(liv1234PdC);									
								data = getListaVociPegVLiv(lDatiContabiliBean);
							} else {
								data = getListaVociPeg(lDatiContabiliBean, fieldNameCombo);
							}
						} else {		
							lDatiContabiliBean.setCodUnitaOrgCdR(getExtraparams().get("codUnitaOrgCdR"));
							lDatiContabiliBean.setDesUnitaOrgCdR(getExtraparams().get("desUnitaOrgCdR"));
							data = getListaCapitoli(lDatiContabiliBean, criteria);
						}
					} catch (Exception e) {}
				}			
			} else {
				try {
					DatiContabiliBean lDatiContabiliBean = new DatiContabiliBean();
					lDatiContabiliBean.setAnnoEsercizio(annoEsercizio);
					lDatiContabiliBean.setFlgEntrataUscita(flgEntrataUscita);
					data = getListaVociPeg(lDatiContabiliBean, null);
				} catch (Exception e) {}
			}
		}			
		PaginatorBean<DatiContabiliBean> paginatorBean = new PaginatorBean<DatiContabiliBean>();
		paginatorBean.setData(data);
		paginatorBean.setStartRow(0);
		paginatorBean.setEndRow(data.size());
		paginatorBean.setTotalRows(data.size());
		return paginatorBean;
	}
	
	public boolean isAttivoSIB() {
		String lSistAMC = ParametriDBUtil.getParametroDB(getSession(), "SIST_AMC");
		return lSistAMC != null && "SIB".equalsIgnoreCase(lSistAMC);
	}
	
	/*
	SIB esporrà un web service per ricevere da Auriga gli estremi di una o più proposte di determina con spesa. Tenendo conto della prossima attivazione delle delibere in Auriga si potrebbe prevedere che più in generale questo servizio serva a inviare a SIB gli estremi di proposte di provvedimento che abbiano impatto sugli impegni.
	In input il servizio riceverà una lista di proposte di provvedimento ciascuna delle quali caratterizzata dalle seguenti informazioni:
	- Sigla del registro di numerazione proposta
	- Data di proposta
	- N.ro di proposta
	- Ufficio che ha caricato la proposta
	- Ufficio proponente (il cui Dirigente adotta il provvedimento)
	- Oggetto della proposta
	- Specificità del provvedimento (ripetibile e con valori predefiniti da lista predefinita): in particolare questo potrebbe servire ad indicare se trattasi di
	  - determina a contrarre tramite procedura di gara
	  - aggiudica
	  - proposta/provvedimento relativo a rinnovo di servizi continuativi (es. contratti di manutenzione);
	  - proposta/provvedimento relativo ad accordo quadro
	Lato Auriga si prevede la gestione e l'invio a SIB di tutti i dati sopra; si rimanda ad altre analisi che esulano dal perimetro di questa la valutazione in merito all'opportunità di registrare in SIB tutti i dati ricevuti tramite questo servizio. 
	Il servizio verrà pubblicato su API Store e tramite quest'ultimo sarà richiamato da AURIGA.
	*/	
	public NuovaPropostaAtto2Bean creaProposta(NuovaPropostaAtto2Bean bean) throws Exception {
		// va chiamato il servizio creaProposta anche per le determine senza spesa
		if(isAttivoSIB()/* && bean.getFlgSpesa() != null && "SI".equals(bean.getFlgSpesa())*/) {			
			ContabilitaSIBImpl lContabilitaSIBImpl = new ContabilitaSIBImpl(); 
			SibInputCreaProposta input = new SibInputCreaProposta();		
			input.setSiglaRegistroProposta(StringUtils.isNotBlank(bean.getSiglaRegProvvisoria()) ? bean.getSiglaRegProvvisoria() : null);
			input.setNumeroProposta(StringUtils.isNotBlank(bean.getNumeroRegProvvisoria()) ? Long.parseLong(bean.getNumeroRegProvvisoria()) : null);		
			input.setDataProposta(bean.getDataRegProvvisoria());
			input.setAnnoProposta(bean.getDataRegProvvisoria() != null ? Short.parseShort(new SimpleDateFormat("yyyy").format(bean.getDataRegProvvisoria())) : null);
			input.setOggetto(bean.getOggetto());
			if(bean.getFlgDeterminaAContrarreTramiteProceduraGara() != null && bean.getFlgDeterminaAContrarreTramiteProceduraGara()) {
				input.setTipologiaProvvedimento("PROGAR"); // DETERMINA DI PROCEDURA GARA  
			} else if(bean.getFlgDeterminaAggiudicaProceduraGara() != null && bean.getFlgDeterminaAggiudicaProceduraGara()) {
				input.setTipologiaProvvedimento("AGG"); // DETERMINA DI AGGIUDICA
			} else if(bean.getFlgDeterminaRimodulazioneSpesaGaraAggiudicata() != null && bean.getFlgDeterminaRimodulazioneSpesaGaraAggiudicata()) {
				input.setTipologiaProvvedimento("RIM");	// DETERMINA DI RIMODULAZIONE		
			}		
			Long idUoProponente = StringUtils.isNotBlank(bean.getUfficioProponente()) ? Long.parseLong(bean.getUfficioProponente()) : null;
			String codiceUoProponente = bean.getCodUfficioProponente();
			String denomUoProponente = bean.getDesUfficioProponente();		
			input.setIdUoProponente(idUoProponente);		
			input.setCodiceUoProponente(codiceUoProponente);
			input.setDenomUoProponente(denomUoProponente);		
			Long idUoCaricaProposta = StringUtils.isNotBlank(bean.getUfficioDefinizioneSpesa()) ? Long.parseLong(bean.getUfficioDefinizioneSpesa()) : null;
			String codiceUoCaricaProposta = bean.getCodUfficioDefinizioneSpesa();
			String denomUoCaricaProposta = bean.getDesUfficioDefinizioneSpesa();		
			input.setIdUoCaricaProposta(idUoCaricaProposta != null ? idUoCaricaProposta : idUoProponente);
			input.setCodiceUoCaricaProposta(StringUtils.isNotBlank(codiceUoCaricaProposta) ? codiceUoCaricaProposta : codiceUoProponente);
			input.setDenomUoCaricaProposta(StringUtils.isNotBlank(denomUoCaricaProposta) ? denomUoCaricaProposta : denomUoProponente);		
			SibOutputCreaProposta output = null;
			try {
				PerformanceLogger lPerformanceLogger = new PerformanceLogger("SIBCreaProposta " + input.getSiglaRegistroProposta() + " " + input.getNumeroProposta() + " " + input.getAnnoProposta());
				lPerformanceLogger.start();		
				output = lContabilitaSIBImpl.sibcreaproposta(getLocale(), input);
				lPerformanceLogger.end();		
			} catch(Exception e) {
				String errorMessage = "Si è verificato un'errore durante la chiamata ai servizi di SIB (CreaProposta)";
				logger.error(errorMessage + ": " + e.getMessage(), e);
				throw new StoreException(errorMessage);
			}
			if (output.isOk() && output.getIdPropostaDetermina() != null) {			
				bean.setIdPropostaAMC(output.getIdPropostaDetermina() != null ? String.valueOf(output.getIdPropostaDetermina().longValue()) : null);					
			} else {
				String errorMessage = "Si è verificato un'errore durante la chiamata ai servizi di SIB";
				if(StringUtils.isNotBlank(output.getMessaggio())) {
					// Se la chiamata al servizio creaProposta va in timeout, la proposta viene comunque creata su SIB ma l'identificativo non viene salvato in DB lato Auriga. 
					// Per questo motivo la volta successiva viene ripetuta la chiamata generando il seguente errore: LA PROPOSTA DI DETERMINA/PROV. ESISTE GIA'.	
					if(output.getMessaggio().equalsIgnoreCase("LA PROPOSTA DI DETERMINA/PROV. ESISTE GIA'")) {
						// In questo caso wrappiamo l'errore e ci salviamo un id fittizio in DB in modo da evitare ulteriori chiamate.
						// Gli id -999 andranno poi sostituiti in un secondo momento con quelli reali salvati su SIB.
						bean.setIdPropostaAMC("-999");
						return bean;
					}					
					errorMessage += " (CreaProposta): " + output.getMessaggio();
				}
				logger.error(errorMessage);
				throw new StoreException(errorMessage);
			}
		}
		return bean;
	}
	
	/*
	SIB dovrà esporre un web service di "aggiornamento provvedimento" che sarà pubblicato su API Store e tramite questo richiamato da AURIGA. Tale servizio verrà richiamato da Auriga a fronte dei seguenti eventi:
	- adozione del provvedimento (firma dell'adottante)
	- rilascio del visto di regolarità contabile
	- archiviazione della proposta di provvedimento
	- caricamento del verbale di commissione di gara che sancisce l'aggiudica e adozione di una determina di aggiudica: in questo caso il servizio viene richiamato per aggiornare la determina a contrarre cui si riferisce il verbale/determina di aggiudica
	- aggiornamento dei dati della proposta/provvedimento già inviati a SIB	
	Tale servizio prevedrà in input:
	- Indicazione del tipo di numerazione del provvedimento specificata nei 3 campi successivi: di proposta o definitiva (obbligatorio)
	- Sigla registro numerazione di proposta o definitiva (stringa di max 30 caratteri, obbligatorio)
	- Anno numerazione di proposta o definitiva (obbligatorio)
	- N.ro proposta o definitivo (numerico di 7 cifre, obbligatorio)
	- Evento: scelta tra "aggiornamento", "adozione", "visto", "archiviazione" e "aggiudica" (obbligatorio)
	- Tipo di provvedimento (Determina, Delibera di Giunta ecc, sempre obbligatorio)
	- Sigla registro numerazione definitiva atto (stringa di max 30 caratteri, obbligatorio solo se evento= "adozione")
	- Anno numerazione definitiva atto (obbligatorio solo se evento= "adozione")
	- N.ro definito atto (numerico di 7 cifre, obbligatorio solo se evento= "adozione")
	- Data di adozione (data senza ora, obbligatorio solo se evento= "adozione")
	- Oggetto provvedimento (testo di 4000 caratteri, sempre obbligatorio)
	- Specificità del provvedimento (ripetibile e con valori predefiniti da lista predefinita)
	- Cognome e Nome Firmatario (ripetibile, obbligatorio solo se evento= "adozione")
	- Direzione adottante (come censita in Auriga, sempre obbligatorio)
	- Codice struttura adottante (secondo codifica di Auriga, sempre obbligatorio)
	- Denominazione struttura adottante (come censita in Auriga, sempre obbligatorio)
	- Data rilascio visto regolarità contabile ovvero di esecutività (data senza ora, obbligatorio solo se evento= "visto")
	- Cognome e Nome firmatario visto regolarità contabile (obbligatorio solo se evento= "visto")
	- Data e ora di archiviazione (data e ora, obbligatorio solo se evento= "archiviazione")
	- Estremi del verbale/determina di aggiudica: tipo di provvedimento, sigla di numerazione, data e numero di registrazione (obbligatori solo se evento= "aggiudica")	
	e in output restituirà solo OK o KO (con codice e messaggio di errore nel caso di KO) a seconda che l'aggiornamento sia stata ricevuta o meno da SIB.
	*/
	public NuovaPropostaAtto2Bean aggiornaAtto(NuovaPropostaAtto2Bean bean) throws Exception {
		// va chiamato il servizio aggiornaAtto anche per le determine senza spesa
		if(isAttivoSIB()/* && bean.getFlgSpesa() != null && "SI".equals(bean.getFlgSpesa())*/) {
			ContabilitaSIBImpl lContabilitaSIBImpl = new ContabilitaSIBImpl(); 
			SibInputAggiornaAtto input = new SibInputAggiornaAtto();		
			input.setSiglaRegistroProposta(StringUtils.isNotBlank(bean.getSiglaRegProvvisoria()) ? bean.getSiglaRegProvvisoria() : null);
			input.setAnnoProposta(bean.getDataRegProvvisoria() != null ? Short.parseShort(new SimpleDateFormat("yyyy").format(bean.getDataRegProvvisoria())) : null);		
			input.setNumeroProposta(StringUtils.isNotBlank(bean.getNumeroRegProvvisoria()) ? Long.parseLong(bean.getNumeroRegProvvisoria()) : null);		
			if(StringUtils.isNotBlank(bean.getEventoSIB())) {
				input.setEvento(bean.getEventoSIB());
				if("adozione".equalsIgnoreCase(bean.getEventoSIB())) {		
					input.setTipoProvvedimento("DETERMINA"); // Determina, Delibera di Giunta ecc		
					input.setSiglaRegistroDefinitiva(StringUtils.isNotBlank(bean.getSiglaRegistrazione()) ? bean.getSiglaRegistrazione() : null);
					input.setAnnoDefinitiva(bean.getDataRegistrazione() != null ? Short.parseShort(new SimpleDateFormat("yyyy").format(bean.getDataRegistrazione())) : null);		
					input.setNumeroDefinitiva(StringUtils.isNotBlank(bean.getNumeroRegistrazione()) ? Long.parseLong(bean.getNumeroRegistrazione()) : null);		
					input.setDataDefinitiva(bean.getDataRegistrazione());
					input.setOggetto(bean.getOggetto());
					String firmatarioFilePrimario = bean.getInfoFilePrimario() != null && bean.getInfoFilePrimario().getFirmatari() != null ? bean.getInfoFilePrimario().getFirmatari()[0] : null;
					input.setNominativoFirmatario(firmatarioFilePrimario); // da firmatari infoFile del file primario (unione dispositivo e allegati parte integrante)				
					Long idUoProponente = StringUtils.isNotBlank(bean.getUfficioProponente()) ? Long.parseLong(bean.getUfficioProponente()) : null;
					String codiceUoProponente = bean.getCodUfficioProponente();
					String denomUoProponente = bean.getDesUfficioProponente();		
					input.setIdUoAdottante(idUoProponente);
					input.setCodiceUoAdottante(codiceUoProponente);
					input.setDenomUoAdottante(denomUoProponente);					
					Long idUoDirAdottante = StringUtils.isNotBlank(bean.getIdUoDirAdottanteSIB()) ? Long.parseLong(bean.getIdUoDirAdottanteSIB()) : null;
					String codiceUoDirAdottante = bean.getCodUoDirAdottanteSIB();
					String denomUoDirAdottante = bean.getDesUoDirAdottanteSIB();
					input.setIdUoDirAdottante(idUoDirAdottante);
					input.setCodiceUoDirAdottante(codiceUoDirAdottante);
					input.setDenomUoDirAdottante(denomUoDirAdottante);		
					if(bean.getFlgDeterminaAContrarreTramiteProceduraGara() != null && bean.getFlgDeterminaAContrarreTramiteProceduraGara()) {
						input.setTipologiaProvvedimento("PROGAR"); // DETERMINA DI PROCEDURA GARA  
					} else if(bean.getFlgDeterminaAggiudicaProceduraGara() != null && bean.getFlgDeterminaAggiudicaProceduraGara()) {
						input.setTipologiaProvvedimento("AGG"); // DETERMINA DI AGGIUDICA
					} else if(bean.getFlgDeterminaRimodulazioneSpesaGaraAggiudicata() != null && bean.getFlgDeterminaRimodulazioneSpesaGaraAggiudicata()) {
						input.setTipologiaProvvedimento("RIM");	// DETERMINA DI RIMODULAZIONE		
					}								
				} else if("visto".equalsIgnoreCase(bean.getEventoSIB())) {
					input.setDataVisto(new Date()); //TODO data corrente: da salvare se va in errore la chiamata a SIB
					String firmatarioFileVisto = bean.getAllegatoVistoContabile() != null && bean.getAllegatoVistoContabile().getInfoFile() != null && bean.getAllegatoVistoContabile().getInfoFile().getFirmatari() != null ? bean.getAllegatoVistoContabile().getInfoFile().getFirmatari()[0] : null;
					input.setNominativoVisto(firmatarioFileVisto);	// da firmatari infoFile dell'allegato (visto contabile) generato da modello in quel task 
				} else if("archiviazione".equalsIgnoreCase(bean.getEventoSIB())) {
					input.setDataArchiviazione(new Date()); //TODO data corrente: da salvare se va in errore la chiamata a SIB			
				} else if("aggiudica".equalsIgnoreCase(bean.getEventoSIB())) {
					//TODO per ora non la implementiamo 
				} else if("aggiornamento".equalsIgnoreCase(bean.getEventoSIB())) {
					input.setOggetto(bean.getOggetto());
					if(bean.getFlgDeterminaAContrarreTramiteProceduraGara() != null && bean.getFlgDeterminaAContrarreTramiteProceduraGara()) {
						input.setTipologiaProvvedimento("PROGAR"); // DETERMINA DI PROCEDURA GARA  
					} else if(bean.getFlgDeterminaAggiudicaProceduraGara() != null && bean.getFlgDeterminaAggiudicaProceduraGara()) {
						input.setTipologiaProvvedimento("AGG"); // DETERMINA DI AGGIUDICA
					} else if(bean.getFlgDeterminaRimodulazioneSpesaGaraAggiudicata() != null && bean.getFlgDeterminaRimodulazioneSpesaGaraAggiudicata()) {
						input.setTipologiaProvvedimento("RIM");	// DETERMINA DI RIMODULAZIONE		
					}
				}	
			} else {
				input.setEvento("aggiornamento");
				input.setOggetto(bean.getOggetto());
				if(bean.getFlgDeterminaAContrarreTramiteProceduraGara() != null && bean.getFlgDeterminaAContrarreTramiteProceduraGara()) {
					input.setTipologiaProvvedimento("PROGAR"); // DETERMINA DI PROCEDURA GARA  
				} else if(bean.getFlgDeterminaAggiudicaProceduraGara() != null && bean.getFlgDeterminaAggiudicaProceduraGara()) {
					input.setTipologiaProvvedimento("AGG"); // DETERMINA DI AGGIUDICA
				} else if(bean.getFlgDeterminaRimodulazioneSpesaGaraAggiudicata() != null && bean.getFlgDeterminaRimodulazioneSpesaGaraAggiudicata()) {
					input.setTipologiaProvvedimento("RIM");	// DETERMINA DI RIMODULAZIONE		
				}
			}		
			SibOutputAggiornaAtto output = null;
			try {
				PerformanceLogger lPerformanceLogger = new PerformanceLogger("SIBAggiornaAtto " + input.getEvento() + " " + input.getSiglaRegistroProposta() + " " + input.getNumeroProposta() + " " + input.getAnnoProposta());
				lPerformanceLogger.start();
				output = lContabilitaSIBImpl.sibaggiornaatto(getLocale(), input);
				lPerformanceLogger.end();
			} catch(Exception e) {
				String errorMessage = "Si è verificato un'errore durante la chiamata ai servizi di SIB (AggiornaAtto " + input.getEvento() + ")";
				logger.error(errorMessage + ": " + e.getMessage(), e);
				throw new StoreException(errorMessage);
			}
			if (!output.isOk()) {			
				String errorMessage = "Si è verificato un'errore durante la chiamata ai servizi di SIB";
				if(StringUtils.isNotBlank(output.getMessaggio())) {
					errorMessage += " (AggiornaAtto " + input.getEvento() + "): " + output.getMessaggio();
				}
				logger.error(errorMessage);
				// in caso di aggiornamento se va in errore la chiamata SIB non lancio nessun errore
				bean.setEsitoEventoSIB("KO");
				bean.setDataEventoSIB(new Date());
				bean.setErrMsgEventoSIB(output.getMessaggio());			
//				throw new StoreException(errorMessage);			
			} else {
				bean.setEsitoEventoSIB("OK");
			}
		}
		return bean;
	}
	
	public List<DatiContabiliBean> getListaDatiContabiliCorrente(NuovaPropostaAtto2Bean bean) throws Exception {
		if(isAttivoSIB() && (bean.getFlgSpesa() != null && "SI".equals(bean.getFlgSpesa())) && (bean.getFlgSpesaCorrente() != null && bean.getFlgSpesaCorrente())) {
			return getListaDatiContabili(bean, "COR"); // COR se il titolo dell'impegno è una "spesa corrente"	
		}	
		return new ArrayList<DatiContabiliBean>();		
	}
		
	public List<DatiContabiliBean> getListaDatiContabiliContoCapitale(NuovaPropostaAtto2Bean bean) throws Exception {
		if(isAttivoSIB() && (bean.getFlgSpesa() != null && "SI".equals(bean.getFlgSpesa())) && (bean.getFlgSpesaContoCapitale() != null && bean.getFlgSpesaContoCapitale())) {
			return getListaDatiContabili(bean, "CAP"); // CAP se il titolo dell'impegno è una "spesa in conto capitale"
		}
		return new ArrayList<DatiContabiliBean>();
	}

	private List<DatiContabiliBean> getListaDatiContabili(NuovaPropostaAtto2Bean bean, String tipoSpesa) throws Exception {
		List<DatiContabiliBean> data = new ArrayList<DatiContabiliBean>();			
		ContabilitaSIBImpl lContabilitaSIBImpl = new ContabilitaSIBImpl(); 
		SibInputElencoDettagliContabili input = new SibInputElencoDettagliContabili();
		input.setSiglaRegistroProposta(StringUtils.isNotBlank(bean.getSiglaRegProvvisoria()) ? bean.getSiglaRegProvvisoria() : null);
		input.setNumeroProposta(StringUtils.isNotBlank(bean.getNumeroRegProvvisoria()) ? Long.parseLong(bean.getNumeroRegProvvisoria()) : null);		
		input.setAnnoProposta(bean.getDataRegProvvisoria() != null ? Short.parseShort(new SimpleDateFormat("yyyy").format(bean.getDataRegProvvisoria())) : null);		
		input.setTipoTitolo(tipoSpesa);
		SibOutputElencoDettagliContabili output = null;
		try {
			PerformanceLogger lPerformanceLogger = new PerformanceLogger("SIBElencoDettagliContabili " + tipoSpesa + " " + input.getSiglaRegistroProposta() + " " + input.getNumeroProposta() + " " + input.getAnnoProposta());
			lPerformanceLogger.start();
			output = lContabilitaSIBImpl.sibelencodettaglicontabili(getLocale(), input);
			lPerformanceLogger.end();
		} catch(Exception e) {
			String errorMessage = "Si è verificato un'errore durante la chiamata ai servizi di SIB (ElencoDettagliContabili)";
			logger.error(errorMessage + ": " + e.getMessage(), e);
			throw new StoreException(errorMessage);
		}
		if (output.isOk()) {			
			if(output.getList() != null) {
				for(SibRecordDettagliContabili lSibRecordDettagliContabili : output.getList()) {
					boolean isVariazione = lSibRecordDettagliContabili.getVarNumero() != null && lSibRecordDettagliContabili.getVarNumero().intValue() > 0;
					boolean isCrono = lSibRecordDettagliContabili.getTipoDettaglio() != null && "COP".equalsIgnoreCase(lSibRecordDettagliContabili.getTipoDettaglio());
					// le variazioni e i cronoprogramma vanno ignorate
					if(!isVariazione && !isCrono) {
						DatiContabiliBean lDatiContabiliBean = new DatiContabiliBean();		
						lDatiContabiliBean.setTipoDettaglio(lSibRecordDettagliContabili.getTipoDettaglio());					
						lDatiContabiliBean.setFlgEntrataUscita(lSibRecordDettagliContabili.getTipo()); // E per entrate e U per uscite
						if(lSibRecordDettagliContabili.getTipoDettaglio() != null && "SCP".equalsIgnoreCase(lSibRecordDettagliContabili.getTipoDettaglio())) {
							// nel caso di subcronoprogramma è sempre in uscita
							lDatiContabiliBean.setFlgEntrataUscita("U");
							lDatiContabiliBean.setAnnoCrono(lSibRecordDettagliContabili.getAnnoCompetenza() != null ? "" + lSibRecordDettagliContabili.getAnnoCompetenza().intValue() : null);
							lDatiContabiliBean.setNumeroCrono(lSibRecordDettagliContabili.getNumeroDet() != null ? "" + lSibRecordDettagliContabili.getNumeroDet().intValue() : null);							
							lDatiContabiliBean.setSubCrono(lSibRecordDettagliContabili.getSubNumero() != null ? "" + lSibRecordDettagliContabili.getSubNumero().intValue() : null);									
						} else {							
							// nel caso di impegno, subimpegno, accertamento o subaccertamento
							lDatiContabiliBean.setFlgEntrataUscita(lSibRecordDettagliContabili.getTipo()); // E per entrate e U per uscite
							lDatiContabiliBean.setAnnoCompetenza(lSibRecordDettagliContabili.getAnnoCompetenza() != null ? "" + lSibRecordDettagliContabili.getAnnoCompetenza().intValue() : null);
							lDatiContabiliBean.setNumeroDettaglio(lSibRecordDettagliContabili.getNumeroDet() != null ? "" + lSibRecordDettagliContabili.getNumeroDet().intValue() : null);	
							lDatiContabiliBean.setSubNumero(lSibRecordDettagliContabili.getSubNumero() != null ? "" + lSibRecordDettagliContabili.getSubNumero().intValue() : null);
							lDatiContabiliBean.setAnnoCrono(lSibRecordDettagliContabili.getAnnoCrono() != null ? "" + lSibRecordDettagliContabili.getAnnoCrono().intValue() : null);
							lDatiContabiliBean.setNumeroCrono(lSibRecordDettagliContabili.getNumeroCrono() != null ? "" + lSibRecordDettagliContabili.getNumeroCrono().intValue() : null);				
						}
						// l'importo va' in notazione italiana quindi devo sostituire il punto con la virgola
						lDatiContabiliBean.setImporto(lSibRecordDettagliContabili.getImporto() != null ? Double.toString(lSibRecordDettagliContabili.getImporto().doubleValue()).replace(".", ",") : null);
						lDatiContabiliBean.setOggetto(lSibRecordDettagliContabili.getDescrizione());
						lDatiContabiliBean.setCodiceCIG(lSibRecordDettagliContabili.getCig());
						lDatiContabiliBean.setCodiceCUP(lSibRecordDettagliContabili.getCup());
//						lDatiContabiliBean.setCodiceGAMIPBM(null); // Nell'offerta non era previsto.
						lDatiContabiliBean.setCodUnitaOrgCdR(lSibRecordDettagliContabili.getCentroDiResp() != null ? "" + lSibRecordDettagliContabili.getCentroDiResp().intValue() : null);					
						lDatiContabiliBean.setDesUnitaOrgCdR(lSibRecordDettagliContabili.getDescrizioneCentroDiResp());					
						lDatiContabiliBean.setCapitolo(lSibRecordDettagliContabili.getCapitolo() != null ? "" + lSibRecordDettagliContabili.getCapitolo().intValue() : null); 
						lDatiContabiliBean.setArticolo(lSibRecordDettagliContabili.getArticolo() != null ? "" + lSibRecordDettagliContabili.getArticolo().intValue() : null);
						lDatiContabiliBean.setNumero(lSibRecordDettagliContabili.getNumero() != null ? "" + lSibRecordDettagliContabili.getNumero().intValue() : null);					
						if(lSibRecordDettagliContabili.getCapitolo() != null && lSibRecordDettagliContabili.getArticolo() != null && lSibRecordDettagliContabili.getNumero() != null) {
							String descrizioneCapitolo = lSibRecordDettagliContabili.getCapitolo().intValue() + "." + lSibRecordDettagliContabili.getArticolo().intValue() + "." + lSibRecordDettagliContabili.getNumero().intValue();
							if(StringUtils.isNotBlank(lSibRecordDettagliContabili.getDescrizioneCapitolo())) {
								descrizioneCapitolo += " - " + lSibRecordDettagliContabili.getDescrizioneCapitolo();
							}
							lDatiContabiliBean.setDescrizioneCapitolo(descrizioneCapitolo);
						}
						lDatiContabiliBean.setLiv5PdC(lSibRecordDettagliContabili.getPdcLivcin() != null ? "" + lSibRecordDettagliContabili.getPdcLivcin().intValue() : null); 
						lDatiContabiliBean.setDescrizionePdC(lSibRecordDettagliContabili.getDescrizionePdc());
						if(lDatiContabiliBean.getFlgEntrataUscita() != null && "U".equalsIgnoreCase(lDatiContabiliBean.getFlgEntrataUscita())) {														
//							lDatiContabiliBean.setAnnoEsigibilitaDebito(null); // Verificare con la Ragioneria se è corretto AnnoCompetenza.
							lDatiContabiliBean.setDataEsigibilitaDa(lSibRecordDettagliContabili.getDataInizioCmp());
							lDatiContabiliBean.setDataEsigibilitaA(lSibRecordDettagliContabili.getDataFineCmp());
							lDatiContabiliBean.setDichiarazioneDL78(lSibRecordDettagliContabili.getCodiceCatalogazione()); // Viene restituito SI o NO.
						}
						if(lDatiContabiliBean.getFlgEntrataUscita() != null && "E".equalsIgnoreCase(lDatiContabiliBean.getFlgEntrataUscita())) {							
							lDatiContabiliBean.setDataScadenzaEntrata(lSibRecordDettagliContabili.getDataScadenza());
						}
						lDatiContabiliBean.setTipoFinanziamento(lSibRecordDettagliContabili.getDesFnz()); 
						lDatiContabiliBean.setCodTipoFinanziamento(lSibRecordDettagliContabili.getCodFnz() != null ? "" + lSibRecordDettagliContabili.getCodFnz().intValue() : null); 
						lDatiContabiliBean.setDenominazioneSogg(lSibRecordDettagliContabili.getDescrizioneSoggetto());
						lDatiContabiliBean.setCodFiscaleSogg(lSibRecordDettagliContabili.getCodfisSoggetto());
						lDatiContabiliBean.setCodPIVASogg(lSibRecordDettagliContabili.getCodivaSoggetto());
//						lDatiContabiliBean.setIndirizzoSogg(null); // Nell'offerta non era previsto.
//						lDatiContabiliBean.setCap(null); // Nell'offerta non era previsto.
//						lDatiContabiliBean.setLocalita(null); // Nell'offerta non era previsto.
//						lDatiContabiliBean.setProvincia(null); // Nell'offerta non era previsto.							
						lDatiContabiliBean.setFlgValidato(lSibRecordDettagliContabili.getValidato() != null && ("1".equals(lSibRecordDettagliContabili.getValidato()) || "SI".equalsIgnoreCase(lSibRecordDettagliContabili.getValidato())) ? "1" : "0");
						lDatiContabiliBean.setFlgSoggDaPubblicare(lSibRecordDettagliContabili.getSoggettoDaPubblicare() != null && ("1".equals(lSibRecordDettagliContabili.getSoggettoDaPubblicare()) || "SI".equalsIgnoreCase(lSibRecordDettagliContabili.getSoggettoDaPubblicare())) ? "1" : "0");
						lDatiContabiliBean.setSettore(lSibRecordDettagliContabili.getSettore() != null ? "" + lSibRecordDettagliContabili.getSettore().intValue() : null);					
						lDatiContabiliBean.setDescrizioneSettore(lSibRecordDettagliContabili.getDescrizioneSettore());
						data.add(lDatiContabiliBean);
					}
				}
			}			
		} else {
			String errorMessage = "Si è verificato un'errore durante la chiamata ai servizi di SIB";
			if(StringUtils.isNotBlank(output.getMessaggio())) {
				errorMessage += " (ElencoDettagliContabili): " + output.getMessaggio();
			}
			logger.error(errorMessage);
			throw new StoreException(errorMessage);
		}
		return data;
	}
	
	private List<DatiContabiliBean> getListaCapitoli(DatiContabiliBean bean, AdvancedCriteria criteria) throws Exception {
		String filterDescrizioneCapitolo = "";
		if(criteria != null && criteria.getCriteria() != null) {
			for (Criterion criterion : criteria.getCriteria()) {
				if (criterion.getFieldName().equals("descrizioneCapitolo")) {
					filterDescrizioneCapitolo = (String) criterion.getValue();
				}
			}
		}
		List<DatiContabiliBean> data = new ArrayList<DatiContabiliBean>();			
		ContabilitaSIBImpl lContabilitaSIBImpl = new ContabilitaSIBImpl(); 
		SibInputGetCapitolo input = new SibInputGetCapitolo();
		input.setEsercizio(StringUtils.isNotBlank(bean.getAnnoEsercizio()) ? new Integer(bean.getAnnoEsercizio()) : null);		
		input.setEntrateUscite(bean.getFlgEntrataUscita());
		input.setCDR(StringUtils.isNotBlank(bean.getCodUnitaOrgCdR()) ? new Integer(bean.getCodUnitaOrgCdR()) : null);		
		input.setDescrizioneCDR(bean.getDesUnitaOrgCdR() != null ? bean.getDesUnitaOrgCdR().trim().toUpperCase() : null);	
		SibOutputGetCapitolo output = null;
		try {
			PerformanceLogger lPerformanceLogger = new PerformanceLogger("SIBGetCapitolo " + input.getEntrateUscite() + " " + input.getEsercizio() + " CDR " + input.getCDR() + " DescrizioneCDR " + input.getDescrizioneCDR());
			lPerformanceLogger.start();
			output = lContabilitaSIBImpl.sibgetcapitolo(getLocale(), input);
			lPerformanceLogger.end();			
		} catch(Exception e) {
			String errorMessage = "Si è verificato un'errore durante la chiamata ai servizi di SIB (GetCapitolo)";
			logger.error(errorMessage + ": " + e.getMessage(), e);
			throw new StoreException(errorMessage);
		}
		if (output.isOk()) {			
			if(output.getEntries() != null) {
				for(SibEntry lSibEntryCapitolo : output.getEntries()) {
					DatiContabiliBean lDatiContabiliBean = new DatiContabiliBean();
					lDatiContabiliBean.setCapitolo(lSibEntryCapitolo.getCapitolo() != null ? "" + lSibEntryCapitolo.getCapitolo().intValue() : null);					
					if(lSibEntryCapitolo.getCapitolo() != null) {
						String descrizioneCapitolo = lSibEntryCapitolo.getCapitolo().intValue() + "";
						if(StringUtils.isNotBlank(lSibEntryCapitolo.getDescrizioneCapitolo())) {
							descrizioneCapitolo += " - " + lSibEntryCapitolo.getDescrizioneCapitolo();													
						}
						lDatiContabiliBean.setDescrizioneCapitolo(descrizioneCapitolo);						
						lDatiContabiliBean.setCodUnitaOrgCdR(lSibEntryCapitolo.getCDR() != null ? lSibEntryCapitolo.getCDR().intValue() + "" : null);
						lDatiContabiliBean.setDesUnitaOrgCdR(lSibEntryCapitolo.getDescrizioneCDR());																				
						boolean matchFilterDescrizioneCapitolo = StringUtils.isBlank(filterDescrizioneCapitolo) || descrizioneCapitolo.toLowerCase().contains(filterDescrizioneCapitolo.toLowerCase());
						if(matchFilterDescrizioneCapitolo) {
							data.add(lDatiContabiliBean);
						}
					}					
				}
			}			
		} else {
			String errorMessage = "Si è verificato un'errore durante la chiamata ai servizi di SIB";
			if(StringUtils.isNotBlank(output.getMessaggio())) {
				errorMessage += " (GetCapitolo): " + output.getMessaggio();
			}
			logger.error(errorMessage);
			throw new StoreException(errorMessage);
		}
		return data;
	}	
	
	private List<DatiContabiliBean> getListaVociPegVLiv(DatiContabiliBean bean) throws Exception {

		List<DatiContabiliBean> data = new ArrayList<DatiContabiliBean>();			
		ContabilitaSIBImpl lContabilitaSIBImpl = new ContabilitaSIBImpl(); 
		SibInputGetVociPegVLiv input = new SibInputGetVociPegVLiv();
		input.setEsercizio(StringUtils.isNotBlank(bean.getAnnoEsercizio()) ? new Integer(bean.getAnnoEsercizio()) : null);
		input.setEntrateUscite(bean.getFlgEntrataUscita());
		if(StringUtils.isNotBlank(bean.getLiv1234PdC())) {
			StringSplitterServer st = new StringSplitterServer(bean.getLiv1234PdC(), ".");
    		int n = 0;
    		while(st.hasMoreTokens()) {
    			if(n == 0) {
    				input.setPDCLivello1(new Integer(st.nextToken().toString()));	        						
    			} else if(n == 1) {
    				input.setPDCLivello2(new Integer(st.nextToken().toString()));				        			
    			} else if(n == 2) {
    				input.setPDCLivello3(new Integer(st.nextToken().toString()));
    			} else if(n == 3) {
    				input.setPDCLivello4(new Integer(st.nextToken().toString()));
    			} 
    			n++;
    		}			        				        			
		}
		SibOutputGetVociPeg output = null;
		try {
			PerformanceLogger lPerformanceLogger = new PerformanceLogger("SIBGetVociPEGVLiv " + input.getEntrateUscite() + " " + input.getEsercizio() + " PdC " + bean.getLiv1234PdC() + ".0");
			lPerformanceLogger.start();
			output = lContabilitaSIBImpl.sibgetvocipegvliv(getLocale(), input);
			lPerformanceLogger.end();
		} catch(Exception e) {
			String errorMessage = "Si è verificato un'errore durante la chiamata ai servizi di SIB (GetVociPEGVLiv)";
			logger.error(errorMessage + ": " + e.getMessage(), e);
			throw new StoreException(errorMessage);
		}
		if (output.isOk()) {			
			if(output.getEntries() != null) {
				for(SibEntry lSibEntryVocePeg : output.getEntries()) {
					DatiContabiliBean lDatiContabiliBean = new DatiContabiliBean();			
					String liv1PdC = lSibEntryVocePeg.getPDCLivello1() != null ? "" + lSibEntryVocePeg.getPDCLivello1().intValue() : "0";
					String liv2PdC = lSibEntryVocePeg.getPDCLivello2() != null ? "" + lSibEntryVocePeg.getPDCLivello2().intValue() : "0";
					String liv3PdC = lSibEntryVocePeg.getPDCLivello3() != null ? "" + lSibEntryVocePeg.getPDCLivello3().intValue() : "0";
					String liv4PdC = lSibEntryVocePeg.getPDCLivello4() != null ? "" + lSibEntryVocePeg.getPDCLivello4().intValue() : "0";
					String liv5PdC = lSibEntryVocePeg.getPDCLivello5() != null ? "" + lSibEntryVocePeg.getPDCLivello5().intValue() : "0";
					lDatiContabiliBean.setLiv1234PdC(liv1PdC + "." + liv2PdC + "." + liv3PdC + "." + liv4PdC);			
					lDatiContabiliBean.setLiv5PdC(liv5PdC);
					lDatiContabiliBean.setDescrizionePdC(liv5PdC + " - " + lSibEntryVocePeg.getDescrizioneCapitolo());
					data.add(lDatiContabiliBean);									
				}
				Collections.sort(data, new Comparator<DatiContabiliBean>() {

					@Override
					public int compare(DatiContabiliBean o1, DatiContabiliBean o2) {
						return Integer.compare(new Integer(o1.getLiv5PdC()), new Integer(o2.getLiv5PdC()));						
					}					
				});
			}			
		} else {
			String errorMessage = "Si è verificato un'errore durante la chiamata ai servizi di SIB";
			if(StringUtils.isNotBlank(output.getMessaggio())) {
				errorMessage += " (GetVociPegVLiv): " + output.getMessaggio();
			}
			logger.error(errorMessage);
			throw new StoreException(errorMessage);
		}
		return data;
	}
		
	private List<DatiContabiliBean> getListaVociPeg(DatiContabiliBean bean, String fieldNameCombo) throws Exception {
		
		boolean isComboArticolo = fieldNameCombo != null && "articolo".equalsIgnoreCase(fieldNameCombo);
		boolean isComboNumero = fieldNameCombo != null && "numero".equalsIgnoreCase(fieldNameCombo);
		boolean isComboAnnoCompetenza = fieldNameCombo != null && "annoCompetenza".equalsIgnoreCase(fieldNameCombo);
		
		List<DatiContabiliBean> data = new ArrayList<DatiContabiliBean>();			
		ContabilitaSIBImpl lContabilitaSIBImpl = new ContabilitaSIBImpl(); 
		SibInputGetVociPeg input = new SibInputGetVociPeg();
		input.setEsercizio(StringUtils.isNotBlank(bean.getAnnoEsercizio()) ? new Integer(bean.getAnnoEsercizio()) : null);
		if(!isComboAnnoCompetenza) {
			input.setCompetenzaPluriennale(StringUtils.isNotBlank(bean.getAnnoEsercizio()) ? new Integer(bean.getAnnoEsercizio()) : null);
		}
		input.setEntrateUscite(bean.getFlgEntrataUscita());
		input.setCapitolo(StringUtils.isNotBlank(bean.getCapitolo()) ? new Integer(bean.getCapitolo()) : null); // filtro per capitolo per trovare gli articoli		
		input.setArticolo(StringUtils.isNotBlank(bean.getArticolo()) ? new Integer(bean.getArticolo()) : null); // filtro per capitolo e articolo per trovare i numeri
		input.setNumero(StringUtils.isNotBlank(bean.getNumero()) ? new Integer(bean.getNumero()) : null); // filtro per capitolo, articolo e numero per trovare le pluriannalità
		input.setFoglia("S"); 
		SibOutputGetVociPeg output = null;
		try {
			PerformanceLogger lPerformanceLogger = new PerformanceLogger("SIBGetVociPEG " + input.getEntrateUscite() + " " + input.getEsercizio() + " Cap. " + input.getCapitolo() + " Art. " + input.getArticolo() + " Num. " + input.getNumero());
			lPerformanceLogger.start();
			output = lContabilitaSIBImpl.sibgetvocipeg(getLocale(), input);
			lPerformanceLogger.end();
		} catch(Exception e) {
			String errorMessage = "Si è verificato un'errore durante la chiamata ai servizi di SIB (GetVociPEG)";
			logger.error(errorMessage + ": " + e.getMessage(), e);
			throw new StoreException(errorMessage);
		}
		if (output.isOk()) {			
			if(output.getEntries() != null) {
				HashSet<Integer> keySet = new HashSet<Integer>();
				for(SibEntry lSibEntryVocePeg : output.getEntries()) {
					DatiContabiliBean lDatiContabiliBean = new DatiContabiliBean();							
					lDatiContabiliBean.setCapitolo(lSibEntryVocePeg.getCapitolo() != null ? "" + lSibEntryVocePeg.getCapitolo().intValue() : null);
					lDatiContabiliBean.setArticolo(lSibEntryVocePeg.getArticolo() != null ? "" + lSibEntryVocePeg.getArticolo().intValue() : null);
					lDatiContabiliBean.setNumero(lSibEntryVocePeg.getNumero() != null ? "" + lSibEntryVocePeg.getNumero().intValue() : null);			
					if(lSibEntryVocePeg.getCapitolo() != null && lSibEntryVocePeg.getArticolo() != null && lSibEntryVocePeg.getNumero() != null) {
						String descrizioneCapitolo = lSibEntryVocePeg.getCapitolo().intValue() + "." + lSibEntryVocePeg.getArticolo().intValue() + "." + lSibEntryVocePeg.getNumero().intValue();
						if(StringUtils.isNotBlank(lSibEntryVocePeg.getDescrizioneCapitolo())) {
							descrizioneCapitolo += " - " + lSibEntryVocePeg.getDescrizioneCapitolo();
						}
						lDatiContabiliBean.setDescrizioneCapitolo(descrizioneCapitolo);
					}
					lDatiContabiliBean.setTitolo(lSibEntryVocePeg.getTitolo() != null ? lSibEntryVocePeg.getTitolo().intValue() + "" : null);					
					lDatiContabiliBean.setCodUnitaOrgCdR(lSibEntryVocePeg.getCDR() != null ? lSibEntryVocePeg.getCDR().intValue() + "" : null);
					lDatiContabiliBean.setDesUnitaOrgCdR(lSibEntryVocePeg.getDescrizioneCDR());		
					lDatiContabiliBean.setLiv1234PdC(lSibEntryVocePeg.getPDCLivello1() + "." + 	lSibEntryVocePeg.getPDCLivello2() + "." + lSibEntryVocePeg.getPDCLivello3() + "." + lSibEntryVocePeg.getPDCLivello4());			
					lDatiContabiliBean.setLiv5PdC(lSibEntryVocePeg.getPDCLivello5() != null ? "" + lSibEntryVocePeg.getPDCLivello5().intValue() : null);									
					lDatiContabiliBean.setAnnoCompetenza(lSibEntryVocePeg.getCompetenzaPluriennale() != null ? "" + lSibEntryVocePeg.getCompetenzaPluriennale().intValue() : null);
					// l'importo va' in notazione italiana quindi devo sostituire il punto con la virgola
					lDatiContabiliBean.setImportoDisponibile(lSibEntryVocePeg.getDisponibile() != null ? Double.toString(lSibEntryVocePeg.getDisponibile().doubleValue()).replace(".", ",") : null);
					if(isComboArticolo || isComboNumero) {
						if(isComboArticolo && !keySet.contains(lSibEntryVocePeg.getArticolo())) {
							keySet.add(lSibEntryVocePeg.getArticolo());
							data.add(lDatiContabiliBean);
						} else if(isComboNumero && !keySet.contains(lSibEntryVocePeg.getNumero())) {
							keySet.add(lSibEntryVocePeg.getNumero());
							data.add(lDatiContabiliBean);						
						}	
					} else {								
						data.add(lDatiContabiliBean);
					}
				}
			}			
		} else {
			String errorMessage = "Si è verificato un'errore durante la chiamata ai servizi di SIB";
			if(StringUtils.isNotBlank(output.getMessaggio())) {
				errorMessage += " (GetVociPeg): " + output.getMessaggio();
			}
			logger.error(errorMessage);
			throw new StoreException(errorMessage);
		}
		return data;
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
