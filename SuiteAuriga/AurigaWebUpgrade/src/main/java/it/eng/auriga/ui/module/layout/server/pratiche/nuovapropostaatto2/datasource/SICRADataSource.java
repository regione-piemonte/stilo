/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.exception.StoreException;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.AttoRiferimentoBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.ListaAnagraficaSicraBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.MovimentiContabiliSICRABean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.NuovaPropostaAtto2CompletaBean;
import it.eng.auriga.ui.module.layout.server.pratiche.nuovapropostaatto2.datasource.bean.RicercaAnagraficaSicraBean;
import it.eng.client.ContabilitaSicraImpl;
import it.eng.core.performance.PerformanceLogger;
import it.eng.document.function.bean.sicra.SicraAnagrafica;
import it.eng.document.function.bean.sicra.SicraAttoArchiviazione;
import it.eng.document.function.bean.sicra.SicraAttoDefinitivo;
import it.eng.document.function.bean.sicra.SicraAttoDefinitivoLiquidazione;
import it.eng.document.function.bean.sicra.SicraAttoLiqContabile;
import it.eng.document.function.bean.sicra.SicraAttoLiqTecnica;
import it.eng.document.function.bean.sicra.SicraAttoRicerca;
import it.eng.document.function.bean.sicra.SicraBudget;
import it.eng.document.function.bean.sicra.SicraDettaglioImpegno;
import it.eng.document.function.bean.sicra.SicraImpegno;
import it.eng.document.function.bean.sicra.SicraImpegnoDaRendereEsecutivo;
import it.eng.document.function.bean.sicra.SicraImpegnoSintetico;
import it.eng.document.function.bean.sicra.SicraInputAggiornaRifAttoLiquidazione;
import it.eng.document.function.bean.sicra.SicraInputArchiviaAtto;
import it.eng.document.function.bean.sicra.SicraInputRicercaAnagrafica;
import it.eng.document.function.bean.sicra.SicraInputRicercaAttoLiquidazione;
import it.eng.document.function.bean.sicra.SicraInputRicercaVociBilancio;
import it.eng.document.function.bean.sicra.SicraInputSetEsecutivitaAtto;
import it.eng.document.function.bean.sicra.SicraInputSetMovimentiAtto;
import it.eng.document.function.bean.sicra.SicraOutputAggiornaRifAttoLiquidazione;
import it.eng.document.function.bean.sicra.SicraOutputArchiviaAtto;
import it.eng.document.function.bean.sicra.SicraOutputRicercaAnagrafica;
import it.eng.document.function.bean.sicra.SicraOutputRicercaAttoLiquidazione;
import it.eng.document.function.bean.sicra.SicraOutputRicercaVociBilancio;
import it.eng.document.function.bean.sicra.SicraOutputSetEsecutivitaAtto;
import it.eng.document.function.bean.sicra.SicraOutputSetMovimentiAtto;
import it.eng.document.function.bean.sicra.SicraPropostaAttoLiquidazione;
import it.eng.document.function.bean.sicra.SicraResidenza;
import it.eng.document.function.bean.sicra.SicraTestataImpegno;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.user.ParametriDBUtil;

@Datasource(id = "SICRADataSource")
public class SICRADataSource extends AbstractFetchDataSource<MovimentiContabiliSICRABean> {

	private static final Logger logger = Logger.getLogger(SICRADataSource.class);
	
	public boolean isAttivoSICRA() {
		String lSistAMC = ParametriDBUtil.getParametroDB(getSession(), "SIST_AMC");
		return lSistAMC != null && "SICRA".equalsIgnoreCase(lSistAMC); // per il Comune di Torino
	}
	
	@Override
	public PaginatorBean<MovimentiContabiliSICRABean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow,
			List<OrderByBean> orderby) throws Exception {
		String fieldNameCombo = getExtraparams().get("fieldNameCombo");
		String flgEntrataUscita = getExtraparams().get("flgEntrataUscita");
		List<MovimentiContabiliSICRABean> data = new ArrayList<MovimentiContabiliSICRABean>();		
		if(StringUtils.isNotBlank(flgEntrataUscita)) {
			if(StringUtils.isNotBlank(fieldNameCombo)) {
				String idCapitolo = getExtraparams().get("idCapitolo");
				String numeroCapitolo = getExtraparams().get("numeroCapitolo");
				boolean isComboCapitolo = "idCapitolo".equalsIgnoreCase(fieldNameCombo);
				boolean isComboAnnoCompetenza = "annoCompetenza".equalsIgnoreCase(fieldNameCombo) && StringUtils.isNotBlank(idCapitolo) && StringUtils.isNotBlank(numeroCapitolo);
				if(isComboCapitolo || isComboAnnoCompetenza) {
					try {
						MovimentiContabiliSICRABean lMovimentiContabiliSICRABean = new MovimentiContabiliSICRABean();
						lMovimentiContabiliSICRABean.setFlgEntrataUscita(flgEntrataUscita);
						if(isComboAnnoCompetenza) {							
							lMovimentiContabiliSICRABean.setIdCapitolo(idCapitolo);	
							lMovimentiContabiliSICRABean.setNumeroCapitolo(numeroCapitolo);													
						} 
						data = getListaVociBilancio(lMovimentiContabiliSICRABean, fieldNameCombo, criteria);						
					} catch (Exception e) {}
				}			
			}
		}			
		PaginatorBean<MovimentiContabiliSICRABean> paginatorBean = new PaginatorBean<MovimentiContabiliSICRABean>();
		paginatorBean.setData(data);
		paginatorBean.setStartRow(0);
		paginatorBean.setEndRow(data.size());
		paginatorBean.setTotalRows(data.size());
		return paginatorBean;
	}
	
	private List<MovimentiContabiliSICRABean> getListaVociBilancio(MovimentiContabiliSICRABean bean, String fieldNameCombo, AdvancedCriteria criteria) throws Exception {
		
		boolean isComboCapitolo = fieldNameCombo != null && "idCapitolo".equalsIgnoreCase(fieldNameCombo);
		boolean isComboAnnoCompetenza = fieldNameCombo != null && "annoCompetenza".equalsIgnoreCase(fieldNameCombo);
		boolean isEscludiFiltroCdC = getExtraparams().get("escludiFiltroCdC") != null && getExtraparams().get("escludiFiltroCdC").equalsIgnoreCase("true");
		
		String filterDescrizioneCapitolo = "";
		if(criteria != null && criteria.getCriteria() != null) {
			for (Criterion criterion : criteria.getCriteria()) {
				if (criterion.getFieldName().equals("descrizioneCapitolo")) {
					filterDescrizioneCapitolo = (String) criterion.getValue();
				}
			}
		}
		List<MovimentiContabiliSICRABean> data = new ArrayList<MovimentiContabiliSICRABean>();			
		ContabilitaSicraImpl lContabilitaSicraImpl = new ContabilitaSicraImpl(); 
		SicraInputRicercaVociBilancio input = new SicraInputRicercaVociBilancio();
		input.setParte(bean.getFlgEntrataUscita());
		if(!isEscludiFiltroCdC) {
			if(StringUtils.isNotBlank(getExtraparams().get("codCentroCosto"))) {
				// devo passarlo in centroDiCosto, che corrisponde al tag codrespservcapitolo (settore) di SICRA, e NON in codCentroCosto!!!
				input.setCentroDiCosto(getExtraparams().get("codCentroCosto")); 
			}	
		}
		String filterCodiceCapitolo = getExtraparams().get("codiceCapitolo");	
		if(StringUtils.isNotBlank(filterCodiceCapitolo)) { 
			input.setCodifica("%" + filterCodiceCapitolo + "%");
		}		
		if(isComboAnnoCompetenza && bean.getFlgEntrataUscita() != null && "U".equals(bean.getFlgEntrataUscita())) {
			boolean isCopertoDaFPV = getExtraparams().get("isCopertoDaFPV") != null && getExtraparams().get("isCopertoDaFPV").equalsIgnoreCase("true");
			input.setFlagRaggruppaFPV(true);
			input.setCopertoDaFPV(isCopertoDaFPV);
		}
		if(isComboCapitolo) {
			if(StringUtils.isNotBlank(filterDescrizioneCapitolo)) {
				input.setDescrizione("%" + filterDescrizioneCapitolo.toUpperCase() + "%");
			}
		} else if(isComboAnnoCompetenza) {
			input.setNumCapitolo(StringUtils.isNotBlank(bean.getNumeroCapitolo()) ? new BigInteger(bean.getNumeroCapitolo()) : null);
		}
		
		boolean skipRicercaCapitoliXFiltriIncompleti = false;
		if(isComboCapitolo) {
			if(isEscludiFiltroCdC) {
				if(StringUtils.isBlank(input.getCodifica()) || input.getCodifica().length() < 3) {
					skipRicercaCapitoliXFiltriIncompleti = true;
				}
			} else if(StringUtils.isBlank(input.getCentroDiCosto())) {
				skipRicercaCapitoliXFiltriIncompleti = true;
			}
		}
		
		if(!skipRicercaCapitoliXFiltriIncompleti) {
			SicraOutputRicercaVociBilancio output = null;
			try {
				PerformanceLogger lPerformanceLogger = new PerformanceLogger("SICRARicercaVociBilancio " + input.getParte() + " CdC " + input.getCodCentroCosto());
				lPerformanceLogger.start();
				output = lContabilitaSicraImpl.sicraricercavocibilancio(getLocale(), input);				
				lPerformanceLogger.end();			
			} catch(Exception e) {
				String errorMessage = "Si è verificato un'errore durante la chiamata ai servizi di SICRA (RicercaVociBilancio)";
				logger.error(errorMessage + ": " + e.getMessage(), e);
				throw new StoreException(errorMessage);
			}
			if (output.getEsitoChiamata() != null && output.getEsitoChiamata().isOk()) {			
				if(output.getBudget() != null) {
					HashSet<Long> keySet = new HashSet<Long>();
					for(SicraBudget lSicraBudget : output.getBudget()) {
						MovimentiContabiliSICRABean lMovimentiContabiliSICRABean = new MovimentiContabiliSICRABean();
						lMovimentiContabiliSICRABean.setIdCapitolo(lSicraBudget.getIdCapitolo() != null ? "" + lSicraBudget.getIdCapitolo().intValue() : null);
						lMovimentiContabiliSICRABean.setNumeroCapitolo(lSicraBudget.getNumCapitolo() != null ? "" + lSicraBudget.getNumCapitolo().intValue() : null);					
						lMovimentiContabiliSICRABean.setDescrizioneCapitolo(lSicraBudget.getDescrizione());		
						if(lSicraBudget.getNumCapitolo() != null) {
							String descrizioneCapitolo = "" + lSicraBudget.getNumCapitolo().intValue();
							if(StringUtils.isNotBlank(lSicraBudget.getDescrizione())) {
								descrizioneCapitolo += " - " + lSicraBudget.getDescrizione();
							}
							lMovimentiContabiliSICRABean.setDescrizioneCapitolo(descrizioneCapitolo);
						}
						lMovimentiContabiliSICRABean.setCodCentroCosto(lSicraBudget.getCodRespServCapitolo()); //TODO verificare se è corretto
						lMovimentiContabiliSICRABean.setCodiceCapitolo(lSicraBudget.getCodifica());
						lMovimentiContabiliSICRABean.setPianoDeiContiFinanz(lSicraBudget.getSigPianoFinCapitolo());
						lMovimentiContabiliSICRABean.setLivelliPdC(lSicraBudget.getSigCPTcapitolo());
						lMovimentiContabiliSICRABean.setDescrizionePianoDeiConti(lSicraBudget.getDesCPTcapitolo());
						lMovimentiContabiliSICRABean.setAnnoCompetenza(lSicraBudget.getAnnoCompetenza() != null ? "" + lSicraBudget.getAnnoCompetenza().intValue() : null);
						// l'importo va' in notazione italiana quindi devo sostituire il punto con la virgola
						lMovimentiContabiliSICRABean.setImportoDisponibile(lSicraBudget.getDisponibile() != null ? Double.toString(lSicraBudget.getDisponibile().doubleValue()).replace(".", ",") : null);
						int annoCompetenza = Integer.parseInt(lMovimentiContabiliSICRABean.getAnnoCompetenza());
						int annoCorrente = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
						if(annoCompetenza >= annoCorrente) {					
							if(isComboCapitolo && !keySet.contains(lSicraBudget.getIdCapitolo())) {
	//							String codiceCapitolo = lMovimentiContabiliSICRABean.getCodiceCapitolo() != null ? lMovimentiContabiliSICRABean.getCodiceCapitolo() : "";
	//							boolean matchFilterCodiceCapitolo = StringUtils.isBlank(filterCodiceCapitolo) || codiceCapitolo.contains(filterCodiceCapitolo);
	//							if(matchFilterCodiceCapitolo) {							
									keySet.add(lSicraBudget.getIdCapitolo());
									data.add(lMovimentiContabiliSICRABean);								
	//							}							
							} else if(isComboAnnoCompetenza && bean.getIdCapitolo().equals(lSicraBudget.getIdCapitolo().intValue() + "")) {
								data.add(lMovimentiContabiliSICRABean);						
							}
						}
					}
				}		
				if(isComboCapitolo) {
					Collections.sort(data, new Comparator<MovimentiContabiliSICRABean>() {
			
						@Override
						public int compare(MovimentiContabiliSICRABean o1, MovimentiContabiliSICRABean o2) {
							int compareNumCapitolo = Integer.compare(new Integer(o1.getNumeroCapitolo()), new Integer(o2.getNumeroCapitolo()));
							if(compareNumCapitolo == 0) {
								return o1.getDescrizioneCapitolo().compareTo(o2.getDescrizioneCapitolo());
							}
							return compareNumCapitolo;
						}					
					});
				} else if(isComboAnnoCompetenza) {
					Collections.sort(data, new Comparator<MovimentiContabiliSICRABean>() {
						
						@Override
						public int compare(MovimentiContabiliSICRABean o1, MovimentiContabiliSICRABean o2) {
							return Integer.compare(new Integer(o1.getAnnoCompetenza()), new Integer(o2.getAnnoCompetenza()));
						}					
					});		
					int annoCorrente = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
					if(data.size() > 0) {
						int lastAnnoCompetenza = Integer.parseInt(data.get(data.size()-1).getAnnoCompetenza());
						for(int anno = lastAnnoCompetenza + 1; anno <= (annoCorrente + 10); anno++) {
							MovimentiContabiliSICRABean lMovimentiContabiliSICRABean = new MovimentiContabiliSICRABean();
							lMovimentiContabiliSICRABean.setAnnoCompetenza("" + anno);
							lMovimentiContabiliSICRABean.setImportoDisponibile(null);
							// Nel caso di uscita (spesa) se l'anno NON è tra quelli dati da Sicra NON deve scattare il controllo sulla disponibilità (di stare entro il disponibile)
							lMovimentiContabiliSICRABean.setSkipCtrlDisp(true);
							data.add(lMovimentiContabiliSICRABean);
						}
					} else {
						for(int anno = annoCorrente; anno <= (annoCorrente + 10); anno++) {
							MovimentiContabiliSICRABean lMovimentiContabiliSICRABean = new MovimentiContabiliSICRABean();
							lMovimentiContabiliSICRABean.setAnnoCompetenza("" + anno);
							lMovimentiContabiliSICRABean.setImportoDisponibile(null);
							// Nel caso di uscita (spesa) se l'anno NON è tra quelli dati da Sicra NON deve scattare il controllo sulla disponibilità (di stare entro il disponibile)
							lMovimentiContabiliSICRABean.setSkipCtrlDisp(true);
							data.add(lMovimentiContabiliSICRABean);
						}
					}
					
				}			
			} else {
				String errorMessage = "Si è verificato un'errore durante la chiamata ai servizi di SICRA";
				if(output.getEsitoChiamata() != null && StringUtils.isNotBlank(output.getEsitoChiamata().getMessaggio())) {
					errorMessage += " (RicercaVociBilancio): " + output.getEsitoChiamata().getMessaggio();
				}
				logger.error(errorMessage);
				throw new StoreException(errorMessage);
			}
		}
		return data;
	}	
	
	public SicraImpegno createSicraImpegno(int progressivo, boolean isCancellazione, MovimentiContabiliSICRABean lMovimentiContabiliSICRABean, NuovaPropostaAtto2CompletaBean lNuovaPropostaAtto2CompletaBean) {
		SicraImpegno lSicraImpegno = new SicraImpegno();
		// Progressivo
		lSicraImpegno.setProgressivo(new BigInteger("" + progressivo));
		if(isCancellazione) {
			// Testata
			SicraTestataImpegno lSicraTestataImpegno = new SicraTestataImpegno();		
			lSicraTestataImpegno.setAzione("1");			
			lSicraTestataImpegno.setParte(lMovimentiContabiliSICRABean.getFlgEntrataUscita());
			lSicraTestataImpegno.setIdImpegno(StringUtils.isNotBlank(lMovimentiContabiliSICRABean.getIdImpAcc()) ? new Long(lMovimentiContabiliSICRABean.getIdImpAcc()) : null);
			lSicraImpegno.setTestata(lSicraTestataImpegno);			
		} else {
			// Testata
			SicraTestataImpegno lSicraTestataImpegno = new SicraTestataImpegno();		
			lSicraTestataImpegno.setAzione("0");			
			lSicraTestataImpegno.setParte(lMovimentiContabiliSICRABean.getFlgEntrataUscita());		
			lSicraTestataImpegno.setAnno(StringUtils.isNotBlank(lMovimentiContabiliSICRABean.getAnnoCompetenza()) ? new Integer(lMovimentiContabiliSICRABean.getAnnoCompetenza()) : null);
			if(StringUtils.isNotBlank(lMovimentiContabiliSICRABean.getAnnoImpAcc()) && StringUtils.isNotBlank(lMovimentiContabiliSICRABean.getAnnoCompetenza()) && lMovimentiContabiliSICRABean.getAnnoImpAcc().equals(lMovimentiContabiliSICRABean.getAnnoCompetenza())) {
				lSicraTestataImpegno.setCodAnnuale(StringUtils.isNotBlank(lMovimentiContabiliSICRABean.getNumeroImpAcc()) ? new BigInteger(lMovimentiContabiliSICRABean.getNumeroImpAcc()) : null);		
			} else {
				lSicraTestataImpegno.setCodAnnuale(null);
			}
			// il tag codice, a differenza di codiceAnnuale e anno, NON va' passato in modifica dell'impegno ma varia sempre
//			lSicraTestataImpegno.setCodice(StringUtils.isNotBlank(lMovimentiContabiliSICRABean.getCodiceImpAcc()) ? new Long(lMovimentiContabiliSICRABean.getCodiceImpAcc()) : null);
			lSicraTestataImpegno.setDescrizione(lMovimentiContabiliSICRABean.getOggetto()); // nel campo descrizione di Sicraweb metto il campo "Descrizione" (oggetto) di Stilo
			lSicraTestataImpegno.setSiglaTipoAtto("PDD");
			lSicraTestataImpegno.setCodSettore(lMovimentiContabiliSICRABean.getCodCentroCosto());
			lSicraTestataImpegno.setNumAtto(StringUtils.isNotBlank(lNuovaPropostaAtto2CompletaBean.getNumeroRegProvvisoria()) ? new BigInteger(lNuovaPropostaAtto2CompletaBean.getNumeroRegProvvisoria()) : null);
			if(lNuovaPropostaAtto2CompletaBean.getDataRegProvvisoria() != null) {
				GregorianCalendar lCalendarDataRegProvvisoria = new GregorianCalendar();
				lCalendarDataRegProvvisoria.setTime(lNuovaPropostaAtto2CompletaBean.getDataRegProvvisoria());
				lSicraTestataImpegno.setDataAtto(lCalendarDataRegProvvisoria);
				lSicraTestataImpegno.setAnnoAtto(lNuovaPropostaAtto2CompletaBean.getDataRegProvvisoria() != null ? Integer.parseInt(new SimpleDateFormat("yyyy").format(lNuovaPropostaAtto2CompletaBean.getDataRegProvvisoria())) : null);				
			} else {
				lSicraTestataImpegno.setAnnoAtto(StringUtils.isNotBlank(lNuovaPropostaAtto2CompletaBean.getAnnoRegProvvisoria()) ? new Integer(lNuovaPropostaAtto2CompletaBean.getAnnoRegProvvisoria()) : null);
			}
			lSicraTestataImpegno.setSiglaTipoAttoOri(lSicraTestataImpegno.getSiglaTipoAtto());
			lSicraTestataImpegno.setCodSettoreOri(lSicraTestataImpegno.getCodSettore());
			lSicraTestataImpegno.setNumAttoOri(lSicraTestataImpegno.getNumAtto());
			lSicraTestataImpegno.setDataAttoOri(lSicraTestataImpegno.getDataAtto());
			lSicraTestataImpegno.setAnnoAttoOri(lSicraTestataImpegno.getAnnoAtto());				
			StringBuffer lStringBuffer = new StringBuffer();
			lStringBuffer.append("OGGETTO: " + lNuovaPropostaAtto2CompletaBean.getOggetto() + "\n");
			/*
			if(StringUtils.isNotBlank(lNuovaPropostaAtto2CompletaBean.getNumeroAttoDeterminaAContrarre())) {
				String categoriaReg = lNuovaPropostaAtto2CompletaBean.getCategoriaRegAttoDeterminaAContrarre() != null ? lNuovaPropostaAtto2CompletaBean.getCategoriaRegAttoDeterminaAContrarre() : "";
				String sigla = lNuovaPropostaAtto2CompletaBean.getSiglaAttoDeterminaAContrarre() != null ? lNuovaPropostaAtto2CompletaBean.getSiglaAttoDeterminaAContrarre() : "";
				String numero = lNuovaPropostaAtto2CompletaBean.getNumeroAttoDeterminaAContrarre() != null ? lNuovaPropostaAtto2CompletaBean.getNumeroAttoDeterminaAContrarre() : "";
				String anno = lNuovaPropostaAtto2CompletaBean.getAnnoAttoDeterminaAContrarre() != null ? lNuovaPropostaAtto2CompletaBean.getAnnoAttoDeterminaAContrarre() : "";									
				if("PG".equals(categoriaReg)) {
					lStringBuffer.append("ATTI PRECEDENTI: Prot. " + numero + "/" + anno + "\n");		
				} else {					
					lStringBuffer.append("ATTI PRECEDENTI: " + sigla + " " + numero + "/" + anno + "\n");		
				}
			}
			*/
			if (lNuovaPropostaAtto2CompletaBean.getListaAttiRiferimento() != null && lNuovaPropostaAtto2CompletaBean.getListaAttiRiferimento().size() > 0) {
				String attiPrecedenti = "";
				for(int i = 0; i < lNuovaPropostaAtto2CompletaBean.getListaAttiRiferimento().size(); i++) {					
					AttoRiferimentoBean lAttoRiferimentoBean = lNuovaPropostaAtto2CompletaBean.getListaAttiRiferimento().get(i);
					if(StringUtils.isNotBlank(lAttoRiferimentoBean.getNumero())) {
						String categoriaReg = lAttoRiferimentoBean.getCategoriaReg() != null ? lAttoRiferimentoBean.getCategoriaReg() : "";
						String sigla = lAttoRiferimentoBean.getSigla() != null ? lAttoRiferimentoBean.getSigla() : "";
						String numero = lAttoRiferimentoBean.getNumero() != null ? lAttoRiferimentoBean.getNumero() : "";
						String anno = lAttoRiferimentoBean.getAnno() != null ? lAttoRiferimentoBean.getAnno() : "";									
						if("PG".equals(categoriaReg)) {
							attiPrecedenti += "Prot. " + numero + "/" + anno + "; ";	
						} else {					
							attiPrecedenti += sigla + " " + numero + "/" + anno + "; ";	
						}
					}								
				}
				if(attiPrecedenti.endsWith("; ")) {
					attiPrecedenti = attiPrecedenti.substring(0, attiPrecedenti.length() - 2);
				}	
				lStringBuffer.append("ATTI PRECEDENTI: " + attiPrecedenti + "\n");
			}
			lStringBuffer.append("PRENOTAZIONE: " + lMovimentiContabiliSICRABean.getFlgPrenotazione() + "\n");
			if (StringUtils.isNotBlank(lMovimentiContabiliSICRABean.getCodiceSoggetto()) || StringUtils.isNotBlank(lMovimentiContabiliSICRABean.getDenominazioneSogg())) {
				String nominativo = "";
				if (StringUtils.isNotBlank(lMovimentiContabiliSICRABean.getCodiceSoggetto())) {
					nominativo += "Cod. soggetto: " + lMovimentiContabiliSICRABean.getCodiceSoggetto() + " - " ;
				}
				if (StringUtils.isNotBlank(lMovimentiContabiliSICRABean.getTipoSoggetto())) {
					nominativo += "Persona " + lMovimentiContabiliSICRABean.getTipoSoggetto() + " - ";
				}
				if (lMovimentiContabiliSICRABean.getFlgSoggEstero() != null && "1".equals(lMovimentiContabiliSICRABean.getFlgSoggEstero())) {
					nominativo += "Estero - ";
				}
				if (StringUtils.isNotBlank(lMovimentiContabiliSICRABean.getCognomeSogg()) || StringUtils.isNotBlank(lMovimentiContabiliSICRABean.getNomeSogg())) {
					if (StringUtils.isNotBlank(lMovimentiContabiliSICRABean.getCognomeSogg())) {
						nominativo += "Cognome: " + lMovimentiContabiliSICRABean.getCognomeSogg() + " - ";
					}
					if (StringUtils.isNotBlank(lMovimentiContabiliSICRABean.getNomeSogg())) {
						nominativo += "Nome: " + lMovimentiContabiliSICRABean.getNomeSogg() + " - ";
					}
				} else if (StringUtils.isNotBlank(lMovimentiContabiliSICRABean.getDenominazioneSogg())) {
					nominativo += "Denominazione: " + lMovimentiContabiliSICRABean.getDenominazioneSogg() + " - ";
				}
				if (StringUtils.isNotBlank(lMovimentiContabiliSICRABean.getCodFiscaleSogg())) {
					nominativo += "C.F.: " + lMovimentiContabiliSICRABean.getCodFiscaleSogg() + " - ";
				}
				if (StringUtils.isNotBlank(lMovimentiContabiliSICRABean.getCodPIVASogg())) {
					nominativo += "P.IVA: " + lMovimentiContabiliSICRABean.getCodPIVASogg() + " - ";
				}
				if (StringUtils.isNotBlank(lMovimentiContabiliSICRABean.getIndirizzoSogg())) {
					nominativo += "Indirizzo: " + lMovimentiContabiliSICRABean.getIndirizzoSogg() + " - ";
				}
				if (StringUtils.isNotBlank(lMovimentiContabiliSICRABean.getCap())) {
					nominativo += "CAP: " + lMovimentiContabiliSICRABean.getCap() + " - ";
				}
				if (StringUtils.isNotBlank(lMovimentiContabiliSICRABean.getLocalita())) {
					nominativo += "Località: " + lMovimentiContabiliSICRABean.getLocalita() + " - ";
				}
				if (StringUtils.isNotBlank(lMovimentiContabiliSICRABean.getProvincia())) {
					nominativo += "Prov.: " + lMovimentiContabiliSICRABean.getProvincia() + " - ";
				}	
				if(nominativo.endsWith(" - ")) {
					nominativo = nominativo.substring(0, nominativo.length() - 3);
				}
				lStringBuffer.append("NOMINATIVO: " + nominativo + "\n");				
			}
			if(StringUtils.isNotBlank(lNuovaPropostaAtto2CompletaBean.getMotivoEsclusioneCIG())) {				
				lStringBuffer.append("MOTIVO ESCLUSIONE CIG: " + lNuovaPropostaAtto2CompletaBean.getMotivoEsclusioneCIG() + "\n");
			} else if(StringUtils.isNotBlank(lMovimentiContabiliSICRABean.getCodiceCIG())) {	
				lStringBuffer.append("CIG: " + lMovimentiContabiliSICRABean.getCodiceCIG() + "\n");
			}
			if(StringUtils.isNotBlank(lMovimentiContabiliSICRABean.getNote())) {
				lStringBuffer.append("NOTE: " + lMovimentiContabiliSICRABean.getNote() + "\n");
			}						
			
			lSicraTestataImpegno.setDescrizioneEstesa(lStringBuffer.toString());			
			lSicraTestataImpegno.setAutoincrementante(lMovimentiContabiliSICRABean.getFlgAutoIncrementante() != null && "1".equals(lMovimentiContabiliSICRABean.getFlgAutoIncrementante()));
			lSicraImpegno.setTestata(lSicraTestataImpegno);
			// Dettaglio
			lSicraImpegno.setDettaglio(new ArrayList<SicraDettaglioImpegno>());
			SicraDettaglioImpegno lSicraDettaglioImpegno = new SicraDettaglioImpegno();
			lSicraDettaglioImpegno.setCopertoDaFPV(lMovimentiContabiliSICRABean.getFlgCopertoDaFPV() != null && "1".equals(lMovimentiContabiliSICRABean.getFlgCopertoDaFPV()));			
			lSicraDettaglioImpegno.setDescrizione(lMovimentiContabiliSICRABean.getDescrizioneEstesa()); // nel campo dettaglio impegno/accertamento di Sicraweb metto il campo "Dettaglio descr." (descrizioneEstesa) di Stilo
			lSicraDettaglioImpegno.setIdCapitolo(StringUtils.isNotBlank(lMovimentiContabiliSICRABean.getIdCapitolo()) ? new Long(lMovimentiContabiliSICRABean.getIdCapitolo()) : null);
//			lSicraDettaglioImpegno.setSiglaPianoFin(lMovimentiContabiliSICRABean.getPianoDeiContiFinanz());
//			lSicraDettaglioImpegno.setCodificaCapitolo(lMovimentiContabiliSICRABean.getCodiceCapitolo());
			lSicraDettaglioImpegno.setSiglaCPT(lMovimentiContabiliSICRABean.getLivelliPdC());
			lSicraDettaglioImpegno.setAnnoCompetenza(StringUtils.isNotBlank(lMovimentiContabiliSICRABean.getAnnoCompetenza()) ? new Integer(lMovimentiContabiliSICRABean.getAnnoCompetenza()) : null);
			if(StringUtils.isNotBlank(lMovimentiContabiliSICRABean.getImporto())) {
				String importo = lMovimentiContabiliSICRABean.getImporto();
				importo = importo.replace(".", "");
				importo = importo.replace(",", ".");
				lSicraDettaglioImpegno.setImporto(new BigDecimal(importo));
			}
			lSicraDettaglioImpegno.setIdFornitore(StringUtils.isNotBlank(lMovimentiContabiliSICRABean.getCodiceSoggetto()) ? new Long(lMovimentiContabiliSICRABean.getCodiceSoggetto()) : null);
			lSicraDettaglioImpegno.setCig(lMovimentiContabiliSICRABean.getCodiceCIG());			
			lSicraDettaglioImpegno.setCup(lMovimentiContabiliSICRABean.getCodiceCUP());			
			lSicraImpegno.getDettaglio().add(lSicraDettaglioImpegno);
		}		
		return lSicraImpegno;
	}
	
	public NuovaPropostaAtto2CompletaBean setMovimentiAtto(NuovaPropostaAtto2CompletaBean bean) throws Exception {
		if(isAttivoSICRA()) {						
			ContabilitaSicraImpl lContabilitaSicraImpl = new ContabilitaSicraImpl(); 
			SicraInputSetMovimentiAtto input = new SicraInputSetMovimentiAtto();
			String codXSalvataggioConWarning = getExtraparams().get("codXSalvataggioConWarning");
			if(StringUtils.isNotBlank(codXSalvataggioConWarning)) {
				input.setMessaggioXml(codXSalvataggioConWarning);
			}
			input.setImpegno(new ArrayList<SicraImpegno>());			
			// Aggiungo i movimenti di SICRA da cancellare
			int progressivo = 1;
			if(bean.getListaMovimentiSICRAToDelete() != null && bean.getListaMovimentiSICRAToDelete().size() > 0) {
				for(int i = 0; i < bean.getListaMovimentiSICRAToDelete().size(); i++) {
					SicraImpegno lSicraImpegnoToDelete = createSicraImpegno(progressivo, true, bean.getListaMovimentiSICRAToDelete().get(i), bean);
					input.getImpegno().add(lSicraImpegnoToDelete);
					progressivo++;					
				}
			}		
			// Aggiungo i movimenti da inserire su SICRA
			HashSet<String> setIdListaInvioMovimentiContabiliSicra = new HashSet<String>();
			if(bean.getListaInvioMovimentiContabiliSICRA() != null) {
				for(int i = 0; i < bean.getListaInvioMovimentiContabiliSICRA().size(); i++) {
					setIdListaInvioMovimentiContabiliSicra.add(bean.getListaInvioMovimentiContabiliSICRA().get(i).getId());			
				}
			}
			HashMap<String, String> mappaIdProgressivoMovimentiToInsert = new HashMap<String, String>();
			if(bean.getListaMovimentiSICRAToInsert() != null && bean.getListaMovimentiSICRAToInsert().size() > 0) {
				for(int i = 0; i < bean.getListaMovimentiSICRAToInsert().size(); i++) {
					String id = bean.getListaMovimentiSICRAToInsert().get(i).getId();
					// verifico che l'impegno da inserire sia presente in lista, nel caso non ci fosse vorrebbe dire che è un impegno che è stato prima modificato e 
					// poi cancellato (in quel caso quindi non lo dovrò inserire su SICRA)
					if(setIdListaInvioMovimentiContabiliSicra.contains(id)) {
						SicraImpegno lSicraImpegnoToInsert = createSicraImpegno(progressivo, false, bean.getListaMovimentiSICRAToInsert().get(i), bean);
						input.getImpegno().add(lSicraImpegnoToInsert);
						mappaIdProgressivoMovimentiToInsert.put(id, "" + progressivo);
						progressivo++;
					}
				}
			}
			if(input.getImpegno().size() > 0) {
				SicraOutputSetMovimentiAtto output = null;
				try {
					PerformanceLogger lPerformanceLogger = new PerformanceLogger("SICRASetMovimentiAtto " + bean.getSiglaRegProvvisoria() + " " + bean.getNumeroRegProvvisoria() + " " + bean.getAnnoRegProvvisoria());
					lPerformanceLogger.start();		
					output = lContabilitaSicraImpl.sicrasetmovimentiatto(getLocale(), input);
					lPerformanceLogger.end();		
				} catch(Exception e) {
					String errorMessage = "Si è verificato un'errore durante la chiamata ai servizi di SICRA (SetMovimentiAtto)";
					logger.error(errorMessage + ": " + e.getMessage(), e);
					throw new StoreException(errorMessage);
				}
				if (output.getEsitoChiamata() != null && output.getEsitoChiamata().isOk()) {
					if (output.getRiscontro() != null && output.getRiscontro().getTipo() != null && "ERR".equalsIgnoreCase(output.getRiscontro().getTipo())) {						
						String errorMessage = "Si è verificato un'errore durante la chiamata ai servizi di SICRA";
						if(output.getEsitoChiamata() != null && StringUtils.isNotBlank(output.getRiscontro().getDescrizione())) {
							errorMessage += " (SetMovimentiAtto): " + output.getRiscontro().getDescrizione();
						}				
						logger.error(errorMessage);
						throw new StoreException(errorMessage);
					} else {
						if (output.getRiscontro() != null && output.getRiscontro().getTipo() != null && "WARN".equalsIgnoreCase(output.getRiscontro().getTipo()) && StringUtils.isNotBlank(output.getRiscontro().getDescrizione())) {						
							bean.setMessaggioWarning(output.getRiscontro().getDescrizione());
							bean.setCodXSalvataggioConWarning(output.getRiscontro().getMessaggioXml());
							bean.setEsitoSetMovimentiAttoSICRA("WARN");
						} else {
							HashMap<String, SicraImpegnoSintetico> mappaProgressivoImpegnoInserito = new HashMap<String, SicraImpegnoSintetico>();
							if(output.getImpegno() != null) {
								for(int i = 0; i < output.getImpegno().size(); i++) {
									String progressivoImpegno = output.getImpegno().get(i).getProgressivo() != null ? "" + output.getImpegno().get(i).getProgressivo().intValue() : null;
									mappaProgressivoImpegnoInserito.put(progressivoImpegno, output.getImpegno().get(i));
								}
							}	
							if(bean.getListaInvioMovimentiContabiliSICRA() != null) {
								for(int i = 0; i < bean.getListaInvioMovimentiContabiliSICRA().size(); i++) {
									// dopo aver inserito il movimento su SICRA mi salvo il riferimento a idImpegno
									String progressivoImpegno = mappaIdProgressivoMovimentiToInsert.get(bean.getListaInvioMovimentiContabiliSICRA().get(i).getId());
									SicraImpegnoSintetico lSicraImpegnoSintetico = mappaProgressivoImpegnoInserito.get(progressivoImpegno);
									if(lSicraImpegnoSintetico != null) {
										bean.getListaInvioMovimentiContabiliSICRA().get(i).setIdImpAcc("" + lSicraImpegnoSintetico.getIdImpegno().intValue());
										bean.getListaInvioMovimentiContabiliSICRA().get(i).setNumeroImpAcc("" + lSicraImpegnoSintetico.getCodAnnuale().intValue());
										bean.getListaInvioMovimentiContabiliSICRA().get(i).setAnnoImpAcc("" + lSicraImpegnoSintetico.getAnno().intValue());
										bean.getListaInvioMovimentiContabiliSICRA().get(i).setCodiceImpAcc("" + lSicraImpegnoSintetico.getCodice().intValue());
									}
								}
							}
							bean.setMessaggioWarning("");
							bean.setCodXSalvataggioConWarning("");
							bean.setEsitoSetMovimentiAttoSICRA("OK");
						}	
						return bean;	
					}
				} else {
					String errorMessage = "Si è verificato un'errore durante la chiamata ai servizi di SICRA";
					if(output.getEsitoChiamata() != null && StringUtils.isNotBlank(output.getEsitoChiamata().getMessaggio())) {
						errorMessage += " (SetMovimentiAtto): " + output.getEsitoChiamata().getMessaggio();
					}				
					logger.error(errorMessage);
					throw new StoreException(errorMessage);
				}				
			}
		}
		return bean;
	}
	
	public NuovaPropostaAtto2CompletaBean archiviaAtto(NuovaPropostaAtto2CompletaBean bean) throws Exception {
		if(isAttivoSICRA()) {						
			ContabilitaSicraImpl lContabilitaSicraImpl = new ContabilitaSicraImpl(); 
			SicraInputArchiviaAtto input = new SicraInputArchiviaAtto();
			if(StringUtils.isNotBlank(bean.getCodXSalvataggioConWarning())) {
				input.setMessaggioXml(bean.getCodXSalvataggioConWarning());
			}
			SicraAttoArchiviazione lSicraAttoArchiviazione = new SicraAttoArchiviazione();
			lSicraAttoArchiviazione.setSiglaTipoAtto("PDD"); //lSicraAttoArchiviazione.setSiglaTipoAtto(bean.getSiglaRegProvvisoria());
			lSicraAttoArchiviazione.setNumAtto(StringUtils.isNotBlank(bean.getNumeroRegProvvisoria()) ? new BigInteger(bean.getNumeroRegProvvisoria()) : null);
			if(bean.getDataRegProvvisoria() != null) {
				GregorianCalendar lCalendarDataRegProvvisoria = new GregorianCalendar();
				lCalendarDataRegProvvisoria.setTime(bean.getDataRegProvvisoria());
				lSicraAttoArchiviazione.setDataAtto(lCalendarDataRegProvvisoria);
				lSicraAttoArchiviazione.setAnnoAtto(bean.getDataRegProvvisoria() != null ? Integer.parseInt(new SimpleDateFormat("yyyy").format(bean.getDataRegProvvisoria())) : null);				
			} else {
				lSicraAttoArchiviazione.setAnnoAtto(StringUtils.isNotBlank(bean.getAnnoRegProvvisoria()) ? new Integer(bean.getAnnoRegProvvisoria()) : null);
			}
			input.setAtto(lSicraAttoArchiviazione);
			SicraOutputArchiviaAtto output = null;
			try {
				PerformanceLogger lPerformanceLogger = new PerformanceLogger("SICRAArchiviaAtto " + bean.getSiglaRegProvvisoria() + " " + bean.getNumeroRegProvvisoria() + " " + bean.getAnnoRegProvvisoria());
				lPerformanceLogger.start();		
				output = lContabilitaSicraImpl.sicraarchiviaatto(getLocale(), input);
				lPerformanceLogger.end();		
			} catch(Exception e) {
				String errorMessage = "Si è verificato un'errore durante la chiamata ai servizi di SICRA (ArchiviaAtto)";
				logger.error(errorMessage + ": " + e.getMessage(), e);
				throw new StoreException(errorMessage);
			}
			if (output.getEsitoChiamata() != null && output.getEsitoChiamata().isOk()) {
				if (output.getRiscontro() != null && output.getRiscontro().getTipo() != null && "ERR".equalsIgnoreCase(output.getRiscontro().getTipo())) {						
					String errorMessage = "Si è verificato un'errore durante la chiamata ai servizi di SICRA";
					if(output.getEsitoChiamata() != null && StringUtils.isNotBlank(output.getRiscontro().getDescrizione())) {
						errorMessage += " (ArchiviaAtto): " + output.getRiscontro().getDescrizione();
					}				
					logger.error(errorMessage);
					bean.setEsitoEventoSICRA("KO");
					bean.setDataEventoSICRA(new Date());
					bean.setErrMsgEventoSICRA(errorMessage);			
//					throw new StoreException(errorMessage);
				} else {
					if (output.getRiscontro() != null && output.getRiscontro().getTipo() != null && "WARN".equalsIgnoreCase(output.getRiscontro().getTipo()) && StringUtils.isNotBlank(output.getRiscontro().getDescrizione())) {
						if(StringUtils.isNotBlank(output.getRiscontro().getMessaggioXml()) && StringUtils.isBlank(input.getMessaggioXml())) {
							// solo se è la prima chiamata e mi restituisce il warning rifaccio una seconda chiamata identica passando il messageXml
							logger.warn(output.getRiscontro().getDescrizione());
							bean.setMessaggioWarning(output.getRiscontro().getDescrizione());
							bean.setCodXSalvataggioConWarning(output.getRiscontro().getMessaggioXml());
							bean.setEsitoEventoSICRA("WARN");
							bean.setDataEventoSICRA(new Date());
							return archiviaAtto(bean);
						}
					}
					bean.setMessaggioWarning("");
					bean.setCodXSalvataggioConWarning("");
					bean.setEsitoEventoSICRA("OK");
					bean.setDataEventoSICRA(new Date());
					return bean;
				}
			} else {
				String errorMessage = "Si è verificato un'errore durante la chiamata ai servizi di SICRA";
				if(output.getEsitoChiamata() != null && StringUtils.isNotBlank(output.getEsitoChiamata().getMessaggio())) {
					errorMessage += " (ArchiviaAtto): " + output.getEsitoChiamata().getMessaggio();
				}				
				logger.error(errorMessage);
				bean.setEsitoEventoSICRA("KO");
				bean.setDataEventoSICRA(new Date());
				bean.setErrMsgEventoSICRA(errorMessage);			
//				throw new StoreException(errorMessage);
			}
		}
		return bean;
	}	
	
	public NuovaPropostaAtto2CompletaBean setEsecutivitaAtto(NuovaPropostaAtto2CompletaBean bean) throws Exception {
		if(isAttivoSICRA()) {						
			ContabilitaSicraImpl lContabilitaSicraImpl = new ContabilitaSicraImpl(); 
			SicraInputSetEsecutivitaAtto input = new SicraInputSetEsecutivitaAtto();
			if(StringUtils.isNotBlank(bean.getCodXSalvataggioConWarning())) {
				input.setMessaggioXml(bean.getCodXSalvataggioConWarning());
			}
			SicraImpegnoDaRendereEsecutivo lSicraImpegnoDaRendereEsecutivo = new SicraImpegnoDaRendereEsecutivo();			
			SicraAttoRicerca lSicraAttoRicerca = new SicraAttoRicerca();
			lSicraAttoRicerca.setSiglaTipoAtto("PDD"); //lSicraAttoRicerca.setSiglaTipoAtto(lNuovaPropostaAtto2CompletaBean.getSiglaRegProvvisoria()); 
			lSicraAttoRicerca.setNumAtto(StringUtils.isNotBlank(bean.getNumeroRegProvvisoria()) ? new BigInteger(bean.getNumeroRegProvvisoria()) : null);
			if(bean.getDataRegProvvisoria() != null) {
				GregorianCalendar lCalendarDataRegProvvisoria = new GregorianCalendar();
				lCalendarDataRegProvvisoria.setTime(bean.getDataRegProvvisoria());
				lSicraAttoRicerca.setDataAtto(lCalendarDataRegProvvisoria);
				lSicraAttoRicerca.setAnnoAtto(bean.getDataRegProvvisoria() != null ? Integer.parseInt(new SimpleDateFormat("yyyy").format(bean.getDataRegProvvisoria())) : null);				
			} else {
				lSicraAttoRicerca.setAnnoAtto(StringUtils.isNotBlank(bean.getAnnoRegProvvisoria()) ? new Integer(bean.getAnnoRegProvvisoria()) : null);
			}			
			lSicraImpegnoDaRendereEsecutivo.setAttoRicerca(lSicraAttoRicerca);			
			SicraAttoDefinitivo lSicraAttoDefinitivo = new SicraAttoDefinitivo();
			lSicraAttoDefinitivo.setSiglaTipoAtto("DD"); //lSicraAttoDefinitivo.setSiglaTipoAtto(lNuovaPropostaAtto2CompletaBean.getSiglaRegistrazione()); 
			lSicraAttoDefinitivo.setNumAtto(StringUtils.isNotBlank(bean.getNumeroRegistrazione()) ? new BigInteger(bean.getNumeroRegistrazione()) : null);
			if(bean.getDataRegistrazione() != null) {
				GregorianCalendar lCalendarDataRegistrazione = new GregorianCalendar();
				lCalendarDataRegistrazione.setTime(bean.getDataRegistrazione());
				lSicraAttoDefinitivo.setDataAtto(lCalendarDataRegistrazione);
				lSicraAttoDefinitivo.setAnnoAtto(bean.getDataRegistrazione() != null ? Integer.parseInt(new SimpleDateFormat("yyyy").format(bean.getDataRegistrazione())) : null);				
			} else {
				lSicraAttoDefinitivo.setAnnoAtto(StringUtils.isNotBlank(bean.getAnnoRegistrazione()) ? new Integer(bean.getAnnoRegistrazione()) : null);
			}		
			lSicraImpegnoDaRendereEsecutivo.setAttoDefinitivo(lSicraAttoDefinitivo);
			input.setImpegno(lSicraImpegnoDaRendereEsecutivo);
			SicraOutputSetEsecutivitaAtto output = null;
			try {
				PerformanceLogger lPerformanceLogger = new PerformanceLogger("SICRASetEsecutivitaAtto " + bean.getSiglaRegProvvisoria() + " " + bean.getNumeroRegProvvisoria() + " " + bean.getAnnoRegProvvisoria());
				lPerformanceLogger.start();		
				output = lContabilitaSicraImpl.sicrasetesecutivitaatto(getLocale(), input);
				lPerformanceLogger.end();		
			} catch(Exception e) {
				String errorMessage = "Si è verificato un'errore durante la chiamata ai servizi di SICRA (SetEsecutivitaAtto)";
				logger.error(errorMessage + ": " + e.getMessage(), e);
				throw new StoreException(errorMessage);
			}
			if (output.getEsitoChiamata() != null && output.getEsitoChiamata().isOk()) {
				if (output.getRiscontro() != null && output.getRiscontro().getTipo() != null && "ERR".equalsIgnoreCase(output.getRiscontro().getTipo())) {						
					String errorMessage = "Si è verificato un'errore durante la chiamata ai servizi di SICRA";
					if(output.getEsitoChiamata() != null && StringUtils.isNotBlank(output.getRiscontro().getDescrizione())) {
						errorMessage += " (SetEsecutivitaAtto): " + output.getRiscontro().getDescrizione();
					}				
					logger.error(errorMessage);
					bean.setEsitoEventoSICRA("KO");
					bean.setDataEventoSICRA(new Date());
					bean.setErrMsgEventoSICRA(errorMessage);			
//					throw new StoreException(errorMessage);
				} else {
					if (output.getRiscontro() != null && output.getRiscontro().getTipo() != null && "WARN".equalsIgnoreCase(output.getRiscontro().getTipo()) && StringUtils.isNotBlank(output.getRiscontro().getDescrizione())) {
						if(StringUtils.isNotBlank(output.getRiscontro().getMessaggioXml()) && StringUtils.isBlank(input.getMessaggioXml())) {
							// solo se è la prima chiamata e mi restituisce il warning rifaccio una seconda chiamata identica passando il messageXml
							logger.warn(output.getRiscontro().getDescrizione());
							bean.setMessaggioWarning(output.getRiscontro().getDescrizione());
							bean.setCodXSalvataggioConWarning(output.getRiscontro().getMessaggioXml());
							bean.setEsitoEventoSICRA("WARN");
							bean.setDataEventoSICRA(new Date());
							return setEsecutivitaAtto(bean);
						}
					}
					bean.setMessaggioWarning("");
					bean.setCodXSalvataggioConWarning("");
					bean.setEsitoEventoSICRA("OK");
					bean.setDataEventoSICRA(new Date());
					return bean;
				}
			} else {
				String errorMessage = "Si è verificato un'errore durante la chiamata ai servizi di SICRA";
				if(output.getEsitoChiamata() != null && StringUtils.isNotBlank(output.getEsitoChiamata().getMessaggio())) {
					errorMessage += " (SetEsecutivitaAtto): " + output.getEsitoChiamata().getMessaggio();
				}				
				logger.error(errorMessage);
				bean.setEsitoEventoSICRA("KO");
				bean.setDataEventoSICRA(new Date());
				bean.setErrMsgEventoSICRA(errorMessage);			
//				throw new StoreException(errorMessage);
			}
		}
		return bean;
	}
	
	public ListaAnagraficaSicraBean cercaInRubricaAnagrafica(RicercaAnagraficaSicraBean bean) throws Exception {
		SicraInputRicercaAnagrafica input = new SicraInputRicercaAnagrafica();

		String tipoRicerca = getExtraparams().get("tipoRicerca");

		if ("ricercaAutomatica".equalsIgnoreCase(tipoRicerca)) {
			input.setCodice(new Long(bean.getCodiceSoggetto()));
		} 
		else if("ricercaOnDemand".equalsIgnoreCase(tipoRicerca)) {
			String stringaRicercaNominativo = null;
			if (StringUtils.isNotBlank(bean.getCognome())) {
				stringaRicercaNominativo = bean.getCognome();
			}
			if (StringUtils.isNotBlank(bean.getNome())) {
				if(StringUtils.isNotBlank(stringaRicercaNominativo)) {
					stringaRicercaNominativo = stringaRicercaNominativo + " " + bean.getNome();
				}else{
					stringaRicercaNominativo =  bean.getNome();
				}
			}
			if (StringUtils.isNotBlank(bean.getDenominazione())) {
				stringaRicercaNominativo = bean.getDenominazione();
			}
			
			input.setStringaRicerca(StringUtils.isNotBlank(stringaRicercaNominativo) ? stringaRicercaNominativo + "%" : stringaRicercaNominativo);

			if (StringUtils.isNotBlank(bean.getCodiceFiscale())) {
				input.setCodiceFiscale(bean.getCodiceFiscale());
			}
			if (StringUtils.isNotBlank(bean.getPartitaIva())) {
				input.setPartitaIva(bean.getPartitaIva());
			}
		}
		
		String escludiNominativiDaBonificare = ParametriDBUtil.getParametroDB(getSession(), "ESCLUDI_NOMINATIVI_DA_BONIFICARE_RUBRICA_SICRA");
		String escludiNominativiFalsi = ParametriDBUtil.getParametroDB(getSession(), "ESCLUDI_NOMINATIVI_FALSI_RUBRICA_SICRA");
		String escludiNominativiIrriconoscibili = ParametriDBUtil.getParametroDB(getSession(), "ESCLUDI_NOMINATIVI_IRRICONOSCIBILI_RUBRICA_SICRA");
		String numMaxRiusltati = ParametriDBUtil.getParametroDB(getSession(), "NUM_MAX_RISULTATI_RUBRICA_SICRA");
		
		if(StringUtils.isNotBlank(escludiNominativiDaBonificare) && "true".equalsIgnoreCase(escludiNominativiDaBonificare)) {
			input.setEscludiDaBonificare(new BigInteger("1"));
		}
		if(StringUtils.isNotBlank(escludiNominativiFalsi) && "true".equalsIgnoreCase(escludiNominativiFalsi)) {
			input.setEscludiNominativoFalso(new BigInteger("1"));
		}
		if(StringUtils.isNotBlank(escludiNominativiIrriconoscibili) && "true".equalsIgnoreCase(escludiNominativiIrriconoscibili)) {
			input.setEscludiSoggettoIrriconoscibile(new BigInteger("1"));
		}
		if(StringUtils.isNotBlank(numMaxRiusltati)) {
			input.setNumMaxRisultati(new BigInteger(numMaxRiusltati)); 
		}else {
			input.setNumMaxRisultati(new BigInteger("20")); 
		}
						
		List<RicercaAnagraficaSicraBean> listaAnagrafica = new ArrayList<RicercaAnagraficaSicraBean>();
		
		try {
			listaAnagrafica = chiamaServizioRicercaAnagraficaSicra(bean, input);
		} catch (Throwable e) {
			logger.error("Errore durante la chiamata ai servizi sicra: " + e.getMessage() + ", non ritorno nessun risultato dalla ricerca anagrafica");
		}
		/*PER TEST*/
//		if((StringUtils.isNotBlank(bean.getCodiceSoggetto()) && "1".equalsIgnoreCase(bean.getCodiceSoggetto()))
//				|| (StringUtils.isNotBlank(bean.getCognome()) && "Rossi".equalsIgnoreCase(bean.getCognome()))) {
//			RicercaAnagraficaSicraBean beanTest = new RicercaAnagraficaSicraBean();
//			beanTest.setNome("Mario");
//			beanTest.setCognome("Rossi");
//			beanTest.setCodiceFiscale("PPPPPP00P00P000P");
//			beanTest.setIndirizzo("TestIndirizzo");
//			beanTest.setProvincia("PD");
//			beanTest.setCodiceSoggetto("1");
//			
//			listaAnagrafica.add(beanTest);
//		}
//		else if((StringUtils.isNotBlank(bean.getCodiceSoggetto()) && "2".equalsIgnoreCase(bean.getCodiceSoggetto()))
//				|| (StringUtils.isNotBlank(bean.getCognome()) && "Verdi".equalsIgnoreCase(bean.getCognome()))) {
//			for (int i = 0; i < 10; i++) {
//				RicercaAnagraficaSicraBean beanTest = new RicercaAnagraficaSicraBean();
//				beanTest.setNome("TestNome" + i);
//				beanTest.setCognome("Verdi" + i);
//				beanTest.setCodiceFiscale("PPPPPP00P00P000P");
//				beanTest.setIndirizzo("TestIndirizzo" + i);
//				beanTest.setProvincia("PD" + i);
//				beanTest.setPartitaIva("0000000000" + i);
//				
//				listaAnagrafica.add(beanTest);
//			}
//		}
		
		ListaAnagraficaSicraBean listaAnagraficaSicraBean = new ListaAnagraficaSicraBean();
		listaAnagraficaSicraBean.setListaAnagraficaSicra(listaAnagrafica);

		return listaAnagraficaSicraBean;
	}

	/**
	 * @param beanInput
	 * @param lContabilitaSicraImpl
	 * @return 
	 * @throws StoreException
	 * @throws NumberFormatException
	 */
	public List<RicercaAnagraficaSicraBean> chiamaServizioRicercaAnagraficaSicra(RicercaAnagraficaSicraBean beanInput, SicraInputRicercaAnagrafica input) throws StoreException, NumberFormatException {
		ContabilitaSicraImpl lContabilitaSicraImpl = new ContabilitaSicraImpl(); 
		List<RicercaAnagraficaSicraBean> listaAnagrafica = new ArrayList<RicercaAnagraficaSicraBean>();
		SicraOutputRicercaAnagrafica output = null;
		try {
			PerformanceLogger lPerformanceLogger = new PerformanceLogger("SICRARicercaAnagrafica" 
					+ " codice: " + beanInput.getCodiceSoggetto() 
					+ " nome: " + beanInput.getNome()
					+ " cognome: " + beanInput.getCognome()
					+ " c.f: " + beanInput.getCodiceFiscale()
					+ " partitaIva: " + beanInput.getPartitaIva());
			lPerformanceLogger.start();
			output = lContabilitaSicraImpl.sicraricercaanagrafica(getLocale(), input);				
			lPerformanceLogger.end();			
		} catch(Exception e) {
			String errorMessage = "Si è verificato un'errore durante la chiamata ai servizi di SICRA (RicercaVociBilancio)";
			logger.error(errorMessage + ": " + e.getMessage(), e);
			throw new StoreException(errorMessage);
		}
		if (output.getEsitoChiamata() != null && output.getEsitoChiamata().isOk()) {			
			if(output.getListaAnagrafiche() != null) {
				for(SicraAnagrafica lSicraAnagrafica : output.getListaAnagrafiche()) {
					RicercaAnagraficaSicraBean bean = new RicercaAnagraficaSicraBean();
					bean.setCodiceSoggetto(String.valueOf(lSicraAnagrafica.getPkid()));
					bean.setCognome(lSicraAnagrafica.getCognome());
					bean.setNome(lSicraAnagrafica.getNome());
					bean.setPartitaIva(lSicraAnagrafica.getPiva());
					bean.setCodiceFiscale(lSicraAnagrafica.getCf());
					bean.setDenominazione(lSicraAnagrafica.getRagSociale());
					
					SicraResidenza sicraResidenza = lSicraAnagrafica.getResidenza();
					if(sicraResidenza!=null) {
						String indirizzo = sicraResidenza.getSpecie() + " " + sicraResidenza.getArea() + " " + sicraResidenza.getCivico();
						
						bean.setIndirizzo(indirizzo);
						bean.setLocalita(sicraResidenza.getComune());
						bean.setCap(sicraResidenza.getCap());
						bean.setProvincia(sicraResidenza.getSiglaProvincia());
					}
					
					listaAnagrafica.add(bean);					
				}
			}							
		} else {
			String errorMessage = "Si è verificato un'errore durante la chiamata ai servizi di SICRA";
			if(output.getEsitoChiamata() != null && StringUtils.isNotBlank(output.getEsitoChiamata().getMessaggio())) {
				errorMessage += " (RicercaAnagrafica): " + output.getEsitoChiamata().getMessaggio();
			}
			logger.error(errorMessage);
			throw new StoreException(errorMessage);
		}
		
		return listaAnagrafica;
	}	
	
	public NuovaPropostaAtto2CompletaBean aggiornaRifAttoLiquidazione(NuovaPropostaAtto2CompletaBean bean) throws Exception {
		if(isAttivoSICRA()) {						
			ContabilitaSicraImpl lContabilitaSicraImpl = new ContabilitaSicraImpl(); 
			SicraInputAggiornaRifAttoLiquidazione input = new SicraInputAggiornaRifAttoLiquidazione();	
			SicraPropostaAttoLiquidazione lSicraPropostaAttoLiquidazione = new SicraPropostaAttoLiquidazione();
			lSicraPropostaAttoLiquidazione.setSiglaTipoAtto(bean.getSiglaRegProvvisoria()); //	lSicraAttoDefinitivoLiquidazione.setSiglaTipoAtto("PDD"); 
			lSicraPropostaAttoLiquidazione.setNumeroAtto(StringUtils.isNotBlank(bean.getNumeroRegProvvisoria()) ? new BigInteger(bean.getNumeroRegProvvisoria()) : null);
			if(bean.getDataRegProvvisoria() != null) {
//				GregorianCalendar lCalendarDataRegProvvisoria = new GregorianCalendar();
//				lCalendarDataRegProvvisoria.setTime(bean.getDataRegProvvisoria());
//				lSicraPropostaAttoLiquidazione.setDataAtto(lCalendarDataRegProvvisoria);
				lSicraPropostaAttoLiquidazione.setAnnoAtto(bean.getDataRegProvvisoria() != null ? new BigInteger(new SimpleDateFormat("yyyy").format(bean.getDataRegProvvisoria())) : null);				
			} else {
				lSicraPropostaAttoLiquidazione.setAnnoAtto(StringUtils.isNotBlank(bean.getAnnoRegProvvisoria()) ? new BigInteger(bean.getAnnoRegProvvisoria()) : null);
			}				
			input.setProposta(lSicraPropostaAttoLiquidazione);			
			SicraAttoDefinitivoLiquidazione lSicraAttoDefinitivoLiquidazione = new SicraAttoDefinitivoLiquidazione();
			lSicraAttoDefinitivoLiquidazione.setSiglaTipoAtto(bean.getSiglaRegistrazione()); //	lSicraAttoDefinitivoLiquidazione.setSiglaTipoAtto("DD"); 
			lSicraAttoDefinitivoLiquidazione.setNumeroAtto(StringUtils.isNotBlank(bean.getNumeroRegistrazione()) ? new BigInteger(bean.getNumeroRegistrazione()) : null);
			if(bean.getDataRegistrazione() != null) {
//				GregorianCalendar lCalendarDataRegistrazione = new GregorianCalendar();
//				lCalendarDataRegistrazione.setTime(bean.getDataRegistrazione());
//				lSicraAttoDefinitivoLiquidazione.setDataAtto(lCalendarDataRegistrazione);
				lSicraAttoDefinitivoLiquidazione.setAnnoAtto(bean.getDataRegistrazione() != null ? new BigInteger(new SimpleDateFormat("yyyy").format(bean.getDataRegistrazione())) : null);				
			} else {
				lSicraAttoDefinitivoLiquidazione.setAnnoAtto(StringUtils.isNotBlank(bean.getAnnoRegistrazione()) ? new BigInteger(bean.getAnnoRegistrazione()) : null);
			}				
			input.setAttoDefinitivo(lSicraAttoDefinitivoLiquidazione);
			SicraOutputAggiornaRifAttoLiquidazione output = null;
			try {
				PerformanceLogger lPerformanceLogger = new PerformanceLogger("SICRAAggiornaRifAttoLiquidazione " + bean.getSiglaRegProvvisoria() + " " + bean.getNumeroRegProvvisoria() + " " + bean.getAnnoRegProvvisoria());
				lPerformanceLogger.start();		
				output = lContabilitaSicraImpl.sicraaggiornarifattoliquidazione(getLocale(), input);
				lPerformanceLogger.end();		
			} catch(Exception e) {
				String errorMessage = "Si è verificato un'errore durante la chiamata ai servizi di SICRA (AggiornaRifAttoLiquidazione)";
				logger.error(errorMessage + ": " + e.getMessage(), e);
				throw new StoreException(errorMessage);
			}
			if (output.getEsitoChiamata() != null && output.getEsitoChiamata().isOk()) {
				if(output.getTotLiquidazioniAggiornate() != null && output.getTotLiquidazioniAggiornate().intValue() > 0) {
					bean.setMessaggioWarning("");
					bean.setCodXSalvataggioConWarning("");
					bean.setEsitoEventoSICRA("OK");
					bean.setDataEventoSICRA(new Date());
					return bean;
				} else {
					String errorMessage = "Si è verificato un'errore durante la chiamata ai servizi di SICRA";
					if(output.getEsitoChiamata() != null && StringUtils.isNotBlank(output.getEsitoChiamata().getMessaggio())) {
						errorMessage += " (AggiornaRifAttoLiquidazione): Non sono state trovate liquidazioni collegate alla proposta indicata. Nessuna operazione eseguita.";
					}				
					logger.error(errorMessage);
					bean.setEsitoEventoSICRA("KO");
					bean.setDataEventoSICRA(new Date());
					bean.setErrMsgEventoSICRA(errorMessage);		
//					throw new StoreException(errorMessage);		
				}
			} else {
				String errorMessage = "Si è verificato un'errore durante la chiamata ai servizi di SICRA";
				if(output.getEsitoChiamata() != null && StringUtils.isNotBlank(output.getEsitoChiamata().getMessaggio())) {
					errorMessage += " (AggiornaRifAttoLiquidazione): " + output.getEsitoChiamata().getMessaggio();
				}				
				logger.error(errorMessage);
				bean.setEsitoEventoSICRA("KO");
				bean.setDataEventoSICRA(new Date());
				bean.setErrMsgEventoSICRA(errorMessage);			
//				throw new StoreException(errorMessage);
			}
		}
		return bean;
	}	
	
	public NuovaPropostaAtto2CompletaBean importaPdfLiquidazione(NuovaPropostaAtto2CompletaBean bean) throws Exception {		
		if(isAttivoSICRA()) {									
			ContabilitaSicraImpl lContabilitaSicraImpl = new ContabilitaSicraImpl(); 
			SicraInputRicercaAttoLiquidazione input = null;
			SicraOutputRicercaAttoLiquidazione output = null;
			SicraAttoLiqTecnica lSicraAttoLiqTecnica = null;
			SicraAttoLiqContabile lSicraAttoLiqContabile = null;			
			if(StringUtils.isNotBlank(bean.getNumeroRegistrazione())) {
				// Numerazione definitiva
				input = new SicraInputRicercaAttoLiquidazione();			
				input.setSiglaTipoAtto(bean.getSiglaRegistrazione()); // input.setSiglaTipoAtto("DD"); 
				input.setNumeroAtto(StringUtils.isNotBlank(bean.getNumeroRegistrazione()) ? new BigInteger(bean.getNumeroRegistrazione()) : null);
				if(bean.getDataRegistrazione() != null) {
//					GregorianCalendar lCalendarDataRegistrazione = new GregorianCalendar();
//					lCalendarDataRegistrazione.setTime(bean.getDataRegistrazione());
//					input.setDataAtto(lCalendarDataRegistrazione);
					input.setAnnoAtto(bean.getDataRegistrazione() != null ? new BigInteger(new SimpleDateFormat("yyyy").format(bean.getDataRegistrazione())) : null);				
				} else {
					input.setAnnoAtto(StringUtils.isNotBlank(bean.getAnnoRegistrazione()) ? new BigInteger(bean.getAnnoRegistrazione()) : null);
				}									
				try {
					PerformanceLogger lPerformanceLogger = new PerformanceLogger("SICRARicercaAttoLiquidazione " + bean.getSiglaRegistrazione() + " " + bean.getNumeroRegistrazione() + " " + bean.getAnnoRegistrazione());
					lPerformanceLogger.start();		
					output = lContabilitaSicraImpl.sicraricercaattoliquidazione(getLocale(), input);
					lPerformanceLogger.end();		
				} catch(Exception e) {
					String errorMessage = "Si è verificato un'errore durante la chiamata ai servizi di SICRA (SICRARicercaAttoLiquidazione)";
					logger.error(errorMessage + ": " + e.getMessage(), e);
					throw new StoreException(errorMessage);
				}
				if (output.getEsitoChiamata() != null && output.getEsitoChiamata().isOk()) {
					if(output.getLiquidazione() != null) {
						for(int i = 0; i < output.getLiquidazione().size(); i++) {
							lSicraAttoLiqTecnica = output.getLiquidazione().get(i).getAttoLiqTecnica();
							lSicraAttoLiqContabile = output.getLiquidazione().get(i).getAttoLiqContabile();
						}					
					}
				} else {
					String errorMessage = "Si è verificato un'errore durante la chiamata ai servizi di SICRA";
					if(output.getEsitoChiamata() != null && StringUtils.isNotBlank(output.getEsitoChiamata().getMessaggio())) {
						errorMessage += " (RicercaAttoLiquidazione): " + output.getEsitoChiamata().getMessaggio();
					}				
					logger.error(errorMessage);			
					lSicraAttoLiqTecnica = null;
					lSicraAttoLiqContabile = null;
//					throw new StoreException(errorMessage);				
				}		
			}
			if(lSicraAttoLiqTecnica == null && lSicraAttoLiqContabile == null) {		
				if(StringUtils.isNotBlank(bean.getNumeroRegProvvisoria())) {
					// Numerazione provvisoria
					input = new SicraInputRicercaAttoLiquidazione();						
					input.setSiglaTipoAtto(bean.getSiglaRegProvvisoria()); // input.setSiglaTipoAtto("PDD"); 
					input.setNumeroAtto(StringUtils.isNotBlank(bean.getNumeroRegProvvisoria()) ? new BigInteger(bean.getNumeroRegProvvisoria()) : null);
					if(bean.getDataRegProvvisoria() != null) {
//						GregorianCalendar lCalendarDataRegProvvisoria = new GregorianCalendar();
//						lCalendarDataRegProvvisoria.setTime(bean.getDataRegProvvisoria());
//						input.setDataAtto(lCalendarDataRegProvvisoria);
						input.setAnnoAtto(bean.getDataRegProvvisoria() != null ? new BigInteger(new SimpleDateFormat("yyyy").format(bean.getDataRegProvvisoria())) : null);				
					} else {
						input.setAnnoAtto(StringUtils.isNotBlank(bean.getAnnoRegProvvisoria()) ? new BigInteger(bean.getAnnoRegProvvisoria()) : null);
					}										
					try {
						PerformanceLogger lPerformanceLogger = new PerformanceLogger("SICRARicercaAttoLiquidazione " + bean.getSiglaRegProvvisoria() + " " + bean.getNumeroRegProvvisoria() + " " + bean.getAnnoRegProvvisoria());
						lPerformanceLogger.start();		
						output = lContabilitaSicraImpl.sicraricercaattoliquidazione(getLocale(), input);
						lPerformanceLogger.end();		
					} catch(Exception e) {
						String errorMessage = "Si è verificato un'errore durante la chiamata ai servizi di SICRA (SICRARicercaAttoLiquidazione)";
						logger.error(errorMessage + ": " + e.getMessage(), e);
						throw new StoreException(errorMessage);
					}
					if (output.getEsitoChiamata() != null && output.getEsitoChiamata().isOk()) {
						if(output.getLiquidazione() != null) {
							for(int i = 0; i < output.getLiquidazione().size(); i++) {
								lSicraAttoLiqTecnica = output.getLiquidazione().get(i).getAttoLiqTecnica();
								lSicraAttoLiqContabile = output.getLiquidazione().get(i).getAttoLiqContabile();
							}					
						}
					} else {
						String errorMessage = "Si è verificato un'errore durante la chiamata ai servizi di SICRA";
						if(output.getEsitoChiamata() != null && StringUtils.isNotBlank(output.getEsitoChiamata().getMessaggio())) {
							errorMessage += " (RicercaAttoLiquidazione): " + output.getEsitoChiamata().getMessaggio();
						}				
						logger.error(errorMessage);		
						lSicraAttoLiqTecnica = null;
						lSicraAttoLiqContabile = null;
						throw new StoreException(errorMessage);					
					}
				}
			}					
			String tipo = getExtraparams().get("tipo") != null ? getExtraparams().get("tipo") : "";
			if("tecnica".equalsIgnoreCase(tipo)) {
				boolean isPresenteAttoLiqTecnica = lSicraAttoLiqTecnica != null && StringUtils.isNotBlank(lSicraAttoLiqTecnica.getContenutoFileAttoLiq());
				if(isPresenteAttoLiqTecnica) {
					if(StringUtils.isNotBlank(lSicraAttoLiqTecnica.getNomeFileAttoLiq())) {
						if(lSicraAttoLiqTecnica.getNomeFileAttoLiq().toLowerCase().endsWith(".pdf")) {
							bean.setNomeFileAttoLiqTecnica(lSicraAttoLiqTecnica.getNomeFileAttoLiq());							
						} else {
							bean.setNomeFileAttoLiqTecnica(lSicraAttoLiqTecnica.getNomeFileAttoLiq() + ".pdf");
						}
					} else {
						bean.setNomeFileAttoLiqTecnica("Atto di liquidazione tecnica.pdf");
					}
					File fileAttoLiqTecnica = File.createTempFile("fileAttoLiqTecnica", ".pdf");
					byte[] bytesAttoLiqTecnica = Base64.decodeBase64(lSicraAttoLiqTecnica.getContenutoFileAttoLiq());
					FileUtils.writeByteArrayToFile(fileAttoLiqTecnica, bytesAttoLiqTecnica);
					bean.setInfoFileAttoLiqTecnica(new InfoFileUtility().getInfoFromFile(fileAttoLiqTecnica.toURI().toString(), fileAttoLiqTecnica.getName(), false, null));
					bean.setUriFileAttoLiqTecnica(StorageImplementation.getStorage().store(fileAttoLiqTecnica));		
				} else {
					throw new StoreException("Nessun pdf di liquidazione tecnica da importare");
				}
			} else if("contabile".equalsIgnoreCase(tipo)) {
				boolean isPresenteAttoLiqContabile = lSicraAttoLiqContabile != null && StringUtils.isNotBlank(lSicraAttoLiqContabile.getContenutoFileAttoLiq());			
				if(isPresenteAttoLiqContabile) {
					if(StringUtils.isNotBlank(lSicraAttoLiqContabile.getNomeFileAttoLiq())) {
						if(lSicraAttoLiqContabile.getNomeFileAttoLiq().toLowerCase().endsWith(".pdf")) {
							bean.setNomeFileAttoLiqContabile(lSicraAttoLiqContabile.getNomeFileAttoLiq());							
						} else {
							bean.setNomeFileAttoLiqContabile(lSicraAttoLiqContabile.getNomeFileAttoLiq() + ".pdf");
						}						
					} else {
						bean.setNomeFileAttoLiqContabile("Atto di liquidazione contabile.pdf");						
					}
					File fileAttoLiqContabile = File.createTempFile("fileAttoLiqContabile", ".pdf");
					byte[] bytesAttoLiqContabile = Base64.decodeBase64(lSicraAttoLiqContabile.getContenutoFileAttoLiq());
					FileUtils.writeByteArrayToFile(fileAttoLiqContabile, bytesAttoLiqContabile);
					bean.setInfoFileAttoLiqContabile(new InfoFileUtility().getInfoFromFile(fileAttoLiqContabile.toURI().toString(), fileAttoLiqContabile.getName(), false, null));
					bean.setUriFileAttoLiqContabile(StorageImplementation.getStorage().store(fileAttoLiqContabile));				
				} else {
					throw new StoreException("Nessun pdf di liquidazione contabile da importare");
				}
			}			
		}
		return bean;
	}
	
	@Override
	public MovimentiContabiliSICRABean get(MovimentiContabiliSICRABean bean) throws Exception {
		return null;
	}

	@Override
	public MovimentiContabiliSICRABean add(MovimentiContabiliSICRABean bean) throws Exception {
		return null;
	}

	@Override
	public MovimentiContabiliSICRABean remove(MovimentiContabiliSICRABean bean) throws Exception {
		return null;
	}

	@Override
	public MovimentiContabiliSICRABean update(MovimentiContabiliSICRABean bean, MovimentiContabiliSICRABean oldvalue) throws Exception {
		return null;
	}

	@Override
	public Map<String, ErrorBean> validate(MovimentiContabiliSICRABean bean) throws Exception {
		return null;
	}
	
}
