/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import static it.eng.utility.ui.module.core.server.datasource.AbstractDataSource.FMT_STD_DATA;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.exception.StoreException;
import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.DocumentBean;
import it.eng.auriga.ui.module.layout.server.common.CalcolaImpronteService;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.PostaElettronicaRetriewAttachBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AllegatoProtocolloBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AltraViaProtBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AltroRifBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AssegnazioneBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AttiRichiestiBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.ClassificazioneFascicoloBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.ContribuenteBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.ControinteressatoBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.DestInvioCCBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.DestinatarioProtBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.DocCollegatoBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.FolderCustomBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.MezzoTrasmissioneDestinatarioBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.MittenteProtBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.PeriziaBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.ProtocollazioneBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.SoggEsternoProtBean;
import it.eng.auriga.ui.module.layout.shared.util.TipoRichiedente;
import it.eng.document.function.bean.AllegatiOutBean;
import it.eng.document.function.bean.AltreUbicazioniBean;
import it.eng.document.function.bean.AltriRiferimentiBean;
import it.eng.document.function.bean.AssegnatariOutBean;
import it.eng.document.function.bean.AttiRichiestiXMLBean;
import it.eng.document.function.bean.ClassFascTitolarioOutBean;
import it.eng.document.function.bean.ControinteressatiXmlBean;
import it.eng.document.function.bean.DestInvioCCOutBean;
import it.eng.document.function.bean.DestinatariOutBean;
import it.eng.document.function.bean.DestintarioUoProtocollazioneXmlBean;
import it.eng.document.function.bean.DocumentoCollegato;
import it.eng.document.function.bean.DocumentoXmlOutBean;
import it.eng.document.function.bean.EmailProvBean;
import it.eng.document.function.bean.FilePrimarioOutBean;
import it.eng.document.function.bean.Flag;
import it.eng.document.function.bean.FolderCustom;
import it.eng.document.function.bean.MittentiDocumentoOutBean;
import it.eng.document.function.bean.PeriziaXmlBean;
import it.eng.document.function.bean.RegNumPrincipale;
import it.eng.document.function.bean.SoggettoEsternoBean;
import it.eng.document.function.bean.TipoAssegnatario;
import it.eng.document.function.bean.TipoDestInvioCC;
import it.eng.document.function.bean.TipoDestinatario;
import it.eng.document.function.bean.TipoMittente;
import it.eng.document.function.bean.TipoProvenienza;
import it.eng.module.archiveUtility.ArchiveUtils;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.DocumentConfiguration;
import it.eng.utility.ManagePdfUtil;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.storageutil.exception.StorageException;
import it.eng.utility.ui.module.layout.shared.bean.IdFileBean;
import it.eng.utility.ui.servlet.bean.InfoFirmaMarca;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.utility.ui.user.ParametriDBUtil;

public class ProtocolloUtility {

	private static Logger logger = Logger.getLogger(ProtocolloUtility.class);

	public static final String _COD_ISTAT_ITALIA = "200";
	public static final String _NOME_STATO_ITALIA = "ITALIA";

	// public static final String _COD_ISTAT_MILANO = "015146";
	// public static final String _NOME_COMUNE_MILANO = "MILANO";

	private HttpSession session;
	private Date dateRif;

	public ProtocolloUtility(HttpSession pSession) {
		session = pSession;
	}

	public ProtocolloUtility(HttpSession pSession, Date pDateRif) {
		session = pSession;
		dateRif = pDateRif;
	}

	public ProtocollazioneBean getProtocolloFromDocumentoXml(DocumentoXmlOutBean lDocumentoXmlOutBean) throws Exception {
		return getProtocolloFromDocumentoXml(lDocumentoXmlOutBean, null);
	}

	public ProtocollazioneBean getProtocolloFromDocumentoXml(DocumentoXmlOutBean lDocumentoXmlOutBean, Map<String,String> extraparams) throws Exception {
		ProtocollazioneBean lBean = new ProtocollazioneBean();
		buildProtocollo(lDocumentoXmlOutBean, lBean, extraparams);
		recuperaFilesFromDoc(lDocumentoXmlOutBean, lBean, null);
		// Protocollo ricevuto
		return lBean;
	}

	public ProtocollazioneBean getProtocolloFromEmailDocumentoXml(DocumentoXmlOutBean lDocumentoXmlOutBean, Map<String, Object> lFiles) throws Exception {
		return getProtocolloFromEmailDocumentoXml(lDocumentoXmlOutBean, lFiles, null);
	}
	
	public ProtocollazioneBean getProtocolloFromEmailDocumentoXml(DocumentoXmlOutBean lDocumentoXmlOutBean, Map<String, Object> lFiles, Map<String,String> extraparams) throws Exception {
		ProtocollazioneBean lBean = new ProtocollazioneBean();
		buildProtocollo(lDocumentoXmlOutBean, lBean, extraparams);
		// if (lFiles!=null) {}; initStorageService();
		recuperaFilesFromDoc(lDocumentoXmlOutBean, lBean, lFiles);
		// Protocollo ricevuto
		return lBean;
	}

	protected void buildProtocollo(DocumentoXmlOutBean lDocumentoXmlOutBean, ProtocollazioneBean lBean, Map<String,String> extraparams) throws Exception {
		
		if(extraparams == null) {
			extraparams = new HashMap<String,String>();
		}

		boolean isPropostaAtto = extraparams.get("isPropostaAtto") != null && "true".equals(extraparams.get("isPropostaAtto"));
		boolean isPropostaOrganigramma = extraparams.get("isPropostaOrganigramma") != null && "true".equals(extraparams.get("isPropostaOrganigramma"));
		boolean isProtocollazioneBozza = extraparams.get("isProtocollazioneBozza") != null && "true".equals(extraparams.get("isProtocollazioneBozza"));
		
		// Tipo provenienza
		TipoProvenienza lTipoProvenienza = lDocumentoXmlOutBean.getFlgTipoProv();
		if (lTipoProvenienza == TipoProvenienza.ENTRATA) {
			lBean.setFlgTipoProv("E");
		} else if (lTipoProvenienza == TipoProvenienza.USCITA) {
			lBean.setFlgTipoProv("U");
		} else if (lTipoProvenienza == TipoProvenienza.INTERNA) {
			lBean.setFlgTipoProv("I");
		}
		
		lBean.setFlgVersoBozza(lDocumentoXmlOutBean.getFlgVersoBozza());

		//TODO MODIFICHE RICH. ACCESSO ATTI (MATTIA ZANIN)
		boolean isRecuperoDatiProtXNuovaRichAccessoAtti = extraparams.get("isRecuperoDatiProtXNuovaRichAccessoAtti") != null && "true".equals(extraparams.get("isRecuperoDatiProtXNuovaRichAccessoAtti"));		
		if(isRecuperoDatiProtXNuovaRichAccessoAtti) {
			if (lDocumentoXmlOutBean.getFlgRichiestaAccessoAtti() == Flag.SETTED) {
				throw new StoreException("Il protocollo selezionato è già una richiesta accesso atti in lavorazione/lavorata");
			} else {
				lBean.setFlgRichiestaAccessoAtti(true);
				if (lTipoProvenienza == TipoProvenienza.INTERNA) {
					lBean.setTipoRichiedente(TipoRichiedente.RICH_INTERNO.getValue());
				} else if (lTipoProvenienza == TipoProvenienza.ENTRATA) {
					lBean.setTipoRichiedente(TipoRichiedente.RICH_ESTERNO.getValue());
				} else if (lTipoProvenienza == TipoProvenienza.USCITA) {
					throw new StoreException("Protocollo in uscita: selezione non consentita");
				} 
			}
		} else if (lDocumentoXmlOutBean.getFlgRichiestaAccessoAtti() == Flag.SETTED) {
			lBean.setFlgRichiestaAccessoAtti(true);
			if (lDocumentoXmlOutBean.getFlgRichAccessoAttiConRichInterno() == Flag.SETTED) {
				lBean.setTipoRichiedente(TipoRichiedente.RICH_INTERNO.getValue());
			}else {
				lBean.setTipoRichiedente(TipoRichiedente.RICH_ESTERNO.getValue());
			}			 		
		}
		
		// Stato documento
		lBean.setCodStatoDett(lDocumentoXmlOutBean.getCodStatoDett());
		lBean.setCodStato(lDocumentoXmlOutBean.getCodStato());
		lBean.setStato(lDocumentoXmlOutBean.getStato());
		
		// Email arrivo/inviata
		lBean.setIdEmailArrivo(lDocumentoXmlOutBean.getIdEmailArrivo());
		lBean.setEmailArrivoInteroperabile(lDocumentoXmlOutBean.getEmailArrivoInteroperabile());
		lBean.setEmailInviataFlgPEC(lDocumentoXmlOutBean.getEmailInviataFlgPEC());
		lBean.setEmailInviataFlgPEO(lDocumentoXmlOutBean.getEmailInviataFlgPEO());
		
		lBean.setIdUdNuovoComeCopia(StringUtils.isNotBlank(lDocumentoXmlOutBean.getCopiaExIdUD()) ? new BigDecimal(lDocumentoXmlOutBean.getCopiaExIdUD()) : null);
		lBean.setDerivaDaRdA(lDocumentoXmlOutBean.getFlgDerivaDaRdA() != null && lDocumentoXmlOutBean.getFlgDerivaDaRdA());
		lBean.setFlgTipoDocDiverso(lDocumentoXmlOutBean.getFlgTipoDocDiverso() == Flag.SETTED ? "1" : null);
		lBean.setIdTipoDocDaCopiare(lDocumentoXmlOutBean.getIdTipoDocDaCopiare());
		lBean.setTimestampGetData(lDocumentoXmlOutBean.getTimestampGetData());
		
		// Tipo documento
		lBean.setTipoDocumentoSalvato(lDocumentoXmlOutBean.getTipoDocumento());
		lBean.setTipoDocumento(lDocumentoXmlOutBean.getTipoDocumento());
		lBean.setNomeTipoDocumento(lDocumentoXmlOutBean.getNomeTipoDocumento());
		lBean.setFlgRichiestaFirmaDigitale(lDocumentoXmlOutBean.getFilePrimario() != null ? lDocumentoXmlOutBean.getFilePrimario().getFlgRichiestaFirmaDigitale() : null);
		lBean.setFlgTipoDocConVie(lDocumentoXmlOutBean.getFlgTipoDocConVie() == Flag.SETTED);
		lBean.setFlgOggettoNonObblig(lDocumentoXmlOutBean.getFlgOggettoNonObblig() == Flag.SETTED);
		lBean.setFlgTipoProtModulo(lDocumentoXmlOutBean.getFlgTipoProtModulo());				
		
		// Rowid documento
		lBean.setRowidDoc(lDocumentoXmlOutBean.getRowidDoc());
		
		// Data documento
		lBean.setDataDocumento(lDocumentoXmlOutBean.getDataStesura());
		
		// Data spedizione cartaceo
		lBean.setDataSpedizioneCartaceo(lDocumentoXmlOutBean.getDataSpedizioneCartaceo());
		
		// Data di arrivo al protocollo
		lBean.setDataArrivoProtocollo(lDocumentoXmlOutBean.getDataArrivoProtocollo());
		lBean.setDataArrivoProtocolloEditabile(lDocumentoXmlOutBean.getDataArrivoProtocolloEditabile());
		if(isProtocollazioneBozza) {
			boolean hasFile = false;
			if(lDocumentoXmlOutBean.getFilePrimario() != null && StringUtils.isNotBlank(lDocumentoXmlOutBean.getFilePrimario().getUri())) {
				hasFile = true;
			} else if (lDocumentoXmlOutBean.getAllegati() != null && lDocumentoXmlOutBean.getAllegati().size() > 0) {
				for (AllegatiOutBean allegato : lDocumentoXmlOutBean.getAllegati()) {
					if(StringUtils.isNotBlank(allegato.getUri())) {
						hasFile = true;
						break;
					}
				}
			}
			if(!hasFile) {
				lBean.setDataArrivoProtocollo(new Date());		
				lBean.setDataArrivoProtocolloEditabile(true);
			}
		}
		
		// Segnatura
		lBean.setSegnatura(lDocumentoXmlOutBean.getSegnatura());		
		
		// Mezzo trasmissione (canale) e supporto originale
		lBean.setMezzoTrasmissione(lDocumentoXmlOutBean.getMessoTrasmissione());
		lBean.setNroRaccomandata(lDocumentoXmlOutBean.getNroRaccomandata());
		lBean.setDataRaccomandata(lDocumentoXmlOutBean.getDtRaccomandata());
		lBean.setNroListaRaccomandata(lDocumentoXmlOutBean.getNroListaRaccomandata());
		lBean.setSupportoOriginale(lDocumentoXmlOutBean.getSupportoOriginale());	
		lBean.setCodSupportoOrig(lDocumentoXmlOutBean.getCodSupportoOrig());
				
		// Resp. procedimento 
		lBean.setIdUserRespProc(lDocumentoXmlOutBean.getIdUserRespProc());
		lBean.setDesUserRespProc(lDocumentoXmlOutBean.getDesUserRespProc());
		
		// U.O. proponente
		lBean.setIdUoProponente(lDocumentoXmlOutBean.getIdUOProponente());
//		lBean.setDesUoProponente(lDocumentoXmlOutBean.getDesUOProponente());
//		lBean.setCodUoProponente(lDocumentoXmlOutBean.getCodUOProponente());
		lBean.setDesDirProponente(lDocumentoXmlOutBean.getDesDirProponente());
		
		// Processo collegato
		lBean.setIdProcess(lDocumentoXmlOutBean.getIdProcess());
		lBean.setEstremiProcess(lDocumentoXmlOutBean.getEstremiProcess());
		lBean.setRuoloSmistamentoAtto(lDocumentoXmlOutBean.getRuoloSmistamentoAtto());
				
		// Tipologie particolari (multa/documentazione contratti A2A)
		lBean.setFlgMulta(lDocumentoXmlOutBean.getFlgMulta() != null && lDocumentoXmlOutBean.getFlgMulta());		
		lBean.setStatoTrasferimentoBloomfleet(lDocumentoXmlOutBean.getStatoTrasferimentoBloomfleet());
		lBean.setDettaglioTrasferimentoBloomfleet(lDocumentoXmlOutBean.getDettaglioTrasferimentoBloomfleet());
		lBean.setFlgDocContratti(lDocumentoXmlOutBean.getFlgDocContratti() != null && lDocumentoXmlOutBean.getFlgDocContratti());
		lBean.setCodContratti(lDocumentoXmlOutBean.getCodContratti());
		if(StringUtils.isNotBlank(lDocumentoXmlOutBean.getFlgFirmeCompilateContratti())) {
			lBean.setFlgFirmeCompilateContratti(lDocumentoXmlOutBean.getFlgFirmeCompilateContratti());
		} else {
			lBean.setFlgFirmeCompilateContratti("N.A.");
		}
		lBean.setFlgDocContrattiBarcode(lDocumentoXmlOutBean.getFlgDocContrattiBarcode() != null && lDocumentoXmlOutBean.getFlgDocContrattiBarcode());
		
		// Oggetto
		lBean.setOggetto(lDocumentoXmlOutBean.getOggetto());

		// Livello e data riservatezza
		lBean.setLivelloRiservatezza(StringUtils.isNotBlank(lDocumentoXmlOutBean.getLivelloRiservatezza()) ? new BigDecimal(lDocumentoXmlOutBean.getLivelloRiservatezza()) : null);
		lBean.setDescrizioneRiservatezza(lDocumentoXmlOutBean.getDescrizioneRiservatezza());
		lBean.setDataRiservatezza(lDocumentoXmlOutBean.getTermineRiservatezza());
		
		// Priorita
		lBean.setPrioritaRiservatezza(lDocumentoXmlOutBean.getPriorita());
		lBean.setDescrizionePrioritaRiservatezza(lDocumentoXmlOutBean.getDescrizionePrioritaRiservatezza());
		
		// Risposta urgente
		lBean.setFlgRispostaUrgente(lDocumentoXmlOutBean.getFlgRispostaUrgente() == Flag.SETTED);
		
		// Richiesta accesso civico
		lBean.setFlgRichAccCivSemplice(lDocumentoXmlOutBean.getFlgAccessoCivicoSemplice() != null && lDocumentoXmlOutBean.getFlgAccessoCivicoSemplice());
		lBean.setFlgRichAccCivGeneralizzato(lDocumentoXmlOutBean.getFlgAccessoCivicoGeneralizzato() != null && lDocumentoXmlOutBean.getFlgAccessoCivicoGeneralizzato());

		// Note
		lBean.setNote(lDocumentoXmlOutBean.getNote());
		lBean.setNoteMancanzaFile(lDocumentoXmlOutBean.getNoteMancanzaFile());		
			
		// Collocazione fisica
		if (lDocumentoXmlOutBean.getCollocazioneFisica() != null) {
			lBean.setDescrizioneCollocazioneFisica(lDocumentoXmlOutBean.getCollocazioneFisica().getDescrizione());
			lBean.setIdToponimo(lDocumentoXmlOutBean.getCollocazioneFisica().getIdTopografico());
			lBean.setNomeCollocazioneFisica(lDocumentoXmlOutBean.getCollocazioneFisica().getDescrizione());
			lBean.setCodRapidoCollocazioneFisica(lDocumentoXmlOutBean.getCollocazioneFisica().getCodRapido());
		}
		
		// Registro emergenza
		if (lDocumentoXmlOutBean.getRegEmergenza() != null) {
			lBean.setDataRegEmergenza(lDocumentoXmlOutBean.getRegEmergenza().getTsRegistrazione());
			lBean.setNroRegEmergenza(StringUtils.isNotBlank(lDocumentoXmlOutBean.getRegEmergenza().getNro()) ? new BigDecimal(lDocumentoXmlOutBean
					.getRegEmergenza().getNro()) : null);
			lBean.setRifRegEmergenza(lDocumentoXmlOutBean.getRegEmergenza().getRegistro());
			lBean.setIdUserRegEmergenza(lDocumentoXmlOutBean.getRegEmergenza().getIdUserReg());
			lBean.setIdUoRegEmergenza(lDocumentoXmlOutBean.getRegEmergenza().getIdUoReg());
		}
		
		// Documento collegato
		if (lDocumentoXmlOutBean.getDocCollegato() != null) {
			lBean.setAnnoDocRiferimento(lDocumentoXmlOutBean.getDocCollegato().getAnno());
			lBean.setNroDocRiferimento(StringUtils.isNotBlank(lDocumentoXmlOutBean.getDocCollegato().getNro()) ? new BigDecimal(Integer
					.valueOf(lDocumentoXmlOutBean.getDocCollegato().getNro())) : null);
			lBean.setSiglaDocRiferimento(lDocumentoXmlOutBean.getDocCollegato().getTipo() != null ? lDocumentoXmlOutBean.getDocCollegato().getTipo().toString()
					: null);
		}
				
		// Scelta iter e atto di riferimento sub-impegno
		if (isPropostaAtto && ParametriDBUtil.getParametroDBAsBoolean(session, "ATTIVA_PROPOSTA_ATTO_2")) {
			lBean.setSceltaIter(lDocumentoXmlOutBean.getSceltaIterPropostaDD());
			if (lDocumentoXmlOutBean.getFlgRichParereRevisori() != null) {
				lBean.setFlgRichParereRevisori("1".equals(lDocumentoXmlOutBean.getFlgRichParereRevisori()));
			}
			lBean.setSiglaAttoRifSubImpegno(lDocumentoXmlOutBean.getSiglaAttoRifSubImpegno());
			lBean.setNumeroAttoRifSubImpegno(lDocumentoXmlOutBean.getNumeroAttoRifSubImpegno());
			lBean.setAnnoAttoRifSubImpegno(lDocumentoXmlOutBean.getAnnoAttoRifSubImpegno());
			lBean.setDataAttoRifSubImpegno(lDocumentoXmlOutBean.getDataAttoRifSubImpegno());
		}
		
		if(isPropostaOrganigramma) {
			lBean.setIdUoRevisioneOrganigramma(lDocumentoXmlOutBean.getIdUoRevisioneOrganigramma());
			lBean.setIdUoPadreRevisioneOrganigramma(lDocumentoXmlOutBean.getIdUoPadreRevisioneOrganigramma());
			lBean.setCodRapidoUoRevisioneOrganigramma(lDocumentoXmlOutBean.getCodRapidoUoRevisioneOrganigramma());
			lBean.setNomeUoRevisioneOrganigramma(lDocumentoXmlOutBean.getNomeUoRevisioneOrganigramma());
			lBean.setTipoUoRevisioneOrganigramma(lDocumentoXmlOutBean.getTipoUoRevisioneOrganigramma());
			lBean.setLivelloUoRevisioneOrganigramma(lDocumentoXmlOutBean.getLivelloUoRevisioneOrganigramma());			
		}
		
		// Mittenti
		if (lDocumentoXmlOutBean.getMittenti() != null && lDocumentoXmlOutBean.getMittenti().size() > 0) {
			List<MittenteProtBean> lListMittenti = new ArrayList<MittenteProtBean>();
			List<MittenteProtBean> lListRichiedentiInterni = new ArrayList<MittenteProtBean>();
			for (MittentiDocumentoOutBean lMittenteOutBean : lDocumentoXmlOutBean.getMittenti()) {
				MittenteProtBean lMittenteProtBean = new MittenteProtBean();
				lMittenteProtBean.setFromLoadDett(true);
				if(isProtocollazioneBozza) {					
					if(lMittenteOutBean.getFlgAssegnaAlMittente() == Flag.SETTED) {
						lMittenteProtBean.setFlgAssegnaAlMittente(true);
					} else {
						lMittenteProtBean.setFlgAssegnaAlMittente(ParametriDBUtil.getParametroDBAsBoolean(session, "ASSEGNAZIONE_MITT_DEFAULT"));
					}
				} else {
					lMittenteProtBean.setFlgAssegnaAlMittente(lMittenteOutBean.getFlgAssegnaAlMittente() == Flag.SETTED);					
				}
				if(lMittenteProtBean.getFlgAssegnaAlMittente()) {
					//TODO ASSEGNAZIONE LOAD	
					/*
					OpzioniInvioBean lOpzioniInvioBean = new OpzioniInvioBean();
					lOpzioniInvioBean.setMotivoInvio(lMittenteOutBean.getMotivoInvio());
					lOpzioniInvioBean.setLivelloPriorita(lMittenteOutBean.getLivelloPriorita());
					lOpzioniInvioBean.setMessaggioInvio(lMittenteOutBean.getMessaggioInvio());	
					lOpzioniInvioBean.setFlgPresaInCarico(lMittenteOutBean.getFeedback() == Flag.SETTED);
					lOpzioniInvioBean.setFlgMancataPresaInCarico(lMittenteOutBean.getNumeroGiorniFeedback() != null);
					lOpzioniInvioBean.setNumeroGiorniFeedback(lMittenteOutBean.getNumeroGiorniFeedback());
					lOpzioniInvioBean.setFlgInviaFascicolo(lMittenteOutBean.getFlgInviaFascicolo() == Flag.SETTED);
					lOpzioniInvioBean.setFlgInviaDocCollegati(lMittenteOutBean.getFlgInviaDocCollegati() == Flag.SETTED);
					lOpzioniInvioBean.setFlgMantieniCopiaUd(lMittenteOutBean.getFlgMantieniCopiaUd() == Flag.SETTED);
					lMittenteProtBean.setOpzioniInvio(lOpzioniInvioBean);
					*/
				}
				lMittenteProtBean.setCodRapidoMittente(lMittenteOutBean.getCodRapido());
				lMittenteProtBean.setTipoMittente(lMittenteOutBean.getTipoMittente());
				lMittenteProtBean.setIdSoggetto(lMittenteOutBean.getIdRubrica());
				if ("AOOI".equals(lMittenteOutBean.getTipoMittente())) {
					lMittenteProtBean.setAoomdgMittente(lMittenteOutBean.getIdRubrica());
					lMittenteProtBean.setDescrAoomdgMittente(lMittenteOutBean.getDenominazioneCognome());
				} else if (lMittenteOutBean.getTipo() == TipoMittente.PERSONA_FISICA) {
					lMittenteProtBean.setDenominazioneMittente(lMittenteOutBean.getDenominazioneCognome() + " " + lMittenteOutBean.getNome());
					lMittenteProtBean.setCognomeMittente(lMittenteOutBean.getDenominazioneCognome());
					lMittenteProtBean.setNomeMittente(lMittenteOutBean.getNome());					
				} else {
					lMittenteProtBean.setDenominazioneMittente(lMittenteOutBean.getDenominazioneCognome());
				}
				if (lMittenteOutBean.getTipoSoggetto() != null) {
					if ("UO".equals(lMittenteOutBean.getTipoSoggetto())) {
						lMittenteProtBean.setIdUoSoggetto(lMittenteOutBean.getIdSoggetto());
						lMittenteProtBean.setOrganigrammaMittente("UO" + lMittenteOutBean.getIdSoggetto());
						if(lMittenteOutBean.getFlgAssegnaAlMittente() == Flag.SETTED) {
							lMittenteProtBean.setIdAssegnatario("UO" + lMittenteOutBean.getIdSoggetto());	
						}
					}
					if ("SV".equals(lMittenteOutBean.getTipoSoggetto())) {
						lMittenteProtBean.setIdScrivaniaSoggetto(lMittenteOutBean.getIdSoggetto());
						lMittenteProtBean.setOrganigrammaMittente("SV" + lMittenteOutBean.getIdSoggetto());
						if(lMittenteOutBean.getFlgAssegnaAlMittente() == Flag.SETTED) {
							lMittenteProtBean.setIdAssegnatario("SV" + lMittenteOutBean.getIdSoggetto());	
						}
					}
//					if ("UT".equals(lMittenteOutBean.getTipoSoggetto())) {
//						lMittenteProtBean.setIdUserSoggetto(lMittenteOutBean.getIdSoggetto());
//						if(lMittenteOutBean.getFlgAssegnaAlMittente() == Flag.SETTED) {
//							lMittenteProtBean.setIdAssegnatario("UT" + lMittenteOutBean.getIdSoggetto());	
//						}
//					}
				}
				if (isAttivoProtocolloWizard(lBean)) {
					// dati indirizzo
					lMittenteProtBean.setStato(lMittenteOutBean.getStato());
					lMittenteProtBean.setNomeStato(lMittenteOutBean.getNomeStato());
					if (StringUtils.isBlank(lMittenteProtBean.getStato()) || _COD_ISTAT_ITALIA.equals(lMittenteProtBean.getStato())
							|| _NOME_STATO_ITALIA.equals(lMittenteProtBean.getStato())) {
						if (StringUtils.isNotBlank(lMittenteOutBean.getCodToponimo()) || StringUtils.isBlank(lMittenteOutBean.getToponimoIndirizzo())) {
							lMittenteProtBean.setFlgFuoriComune(false);
							lMittenteProtBean.setCodToponimo(lMittenteOutBean.getCodToponimo());
							lMittenteProtBean.setIndirizzo(lMittenteOutBean.getToponimoIndirizzo());
							lMittenteProtBean.setComune(getCodIstatComuneRif());
							lMittenteProtBean.setNomeComune(getNomeComuneRif());
						} else {
							lMittenteProtBean.setFlgFuoriComune(true);
							lMittenteProtBean.setTipoToponimo(lMittenteOutBean.getTipoToponimo());
							lMittenteProtBean.setToponimo(lMittenteOutBean.getToponimoIndirizzo());
							lMittenteProtBean.setComune(lMittenteOutBean.getComune());
							lMittenteProtBean.setNomeComune(lMittenteOutBean.getNomeComuneCitta());
						}
						lMittenteProtBean.setFrazione(lMittenteOutBean.getFrazione());
						lMittenteProtBean.setCap(lMittenteOutBean.getCap());
					} else {
						lMittenteProtBean.setIndirizzo(lMittenteOutBean.getToponimoIndirizzo());
						lMittenteProtBean.setCitta(lMittenteOutBean.getNomeComuneCitta());
					}
					lMittenteProtBean.setCivico(lMittenteOutBean.getCivico());
					lMittenteProtBean.setInterno(lMittenteOutBean.getInterno());
					lMittenteProtBean.setZona(lMittenteOutBean.getZona());
					lMittenteProtBean.setComplementoIndirizzo(lMittenteOutBean.getComplementoIndirizzo());
					lMittenteProtBean.setAppendici(lMittenteOutBean.getAppendici());
				}
				lMittenteProtBean.setCodfiscaleMittente(lMittenteOutBean.getCodiceFiscale());
				lMittenteProtBean.setEmailMittente(lMittenteOutBean.getEmail());
				lMittenteProtBean.setTelMittente(lMittenteOutBean.getTelefono());
				lListMittenti.add(lMittenteProtBean);
				if (isRichiestaAccessoAttiMittenteInterno(lBean) && lMittenteOutBean.getTipo() == TipoMittente.PERSONA_FISICA){
					lMittenteProtBean.setTipoMittente("UP");		
					lListRichiedentiInterni.add(lMittenteProtBean);
				}
			}
			// Se sono in una richiesta accesso atti con richiedente interno, salvo i mittenti in listaMittentiInterni
			if (isRichiestaAccessoAttiMittenteInterno(lBean)){
				lBean.setListaRichiedentiInterni(lListRichiedentiInterni);
			}else {
				lBean.setListaMittenti(lListMittenti);
			}
		}
		
		// Destinatari
		if (lDocumentoXmlOutBean.getDestinatari() != null && lDocumentoXmlOutBean.getDestinatari().size() > 0) {
			List<DestinatarioProtBean> lListDestinatari = new ArrayList<DestinatarioProtBean>();
			for (DestinatariOutBean lDestinatariOutBean : lDocumentoXmlOutBean.getDestinatari()) {
				DestinatarioProtBean lDestinatarioProtBean = new DestinatarioProtBean();
				lDestinatarioProtBean.setFromLoadDett(true);			
				
//				/*Caso in cui i destinatari sono gestiti con foglio excel salvo solo le informazioni che occorrono e esco da ciclo*/
				if("XLS".equals(lDestinatariOutBean.getTipoDestinatario())) {
					lDestinatarioProtBean.setIdFoglioExcelDestinatari(lDestinatariOutBean.getIdRubrica());
					lDestinatarioProtBean.setUriFileExcel(lDestinatariOutBean.getUriFileExcel());
					lDestinatarioProtBean.setDisplayFileNameExcel(lDestinatariOutBean.getDisplayFileNameExcel()); 
					lDestinatarioProtBean.setTipoDestinatario(lDestinatariOutBean.getTipoDestinatario());
					
					lListDestinatari.add(lDestinatarioProtBean);
					continue;			
				}
							
				if(isProtocollazioneBozza) {
					if(lDestinatariOutBean.getFlgAssegnaAlDestinatario() == Flag.SETTED) {
						lDestinatarioProtBean.setFlgAssegnaAlDestinatario(true);
					} else {
						lDestinatarioProtBean.setFlgAssegnaAlDestinatario(ParametriDBUtil.getParametroDBAsBoolean(session, "ASSEGNAZIONE_DEST_DEFAULT"));
					}
				} else {
					lDestinatarioProtBean.setFlgAssegnaAlDestinatario(lDestinatariOutBean.getFlgAssegnaAlDestinatario() == Flag.SETTED);					
				}
				if(lDestinatarioProtBean.getFlgAssegnaAlDestinatario()) {
					//TODO ASSEGNAZIONE LOAD	
					/*
					OpzioniInvioBean lOpzioniInvioBean = new OpzioniInvioBean();
					lOpzioniInvioBean.setMotivoInvio(lDestinatariOutBean.getMotivoInvio());
					lOpzioniInvioBean.setLivelloPriorita(lDestinatariOutBean.getLivelloPriorita());
					lOpzioniInvioBean.setMessaggioInvio(lDestinatariOutBean.getMessaggioInvio());	
					lOpzioniInvioBean.setFlgPresaInCarico(lDestinatariOutBean.getFeedback() == Flag.SETTED);
					lOpzioniInvioBean.setFlgMancataPresaInCarico(lDestinatariOutBean.getNumeroGiorniFeedback() != null);
					lOpzioniInvioBean.setNumeroGiorniFeedback(lDestinatariOutBean.getNumeroGiorniFeedback());
					lOpzioniInvioBean.setFlgInviaFascicolo(lDestinatariOutBean.getFlgInviaFascicolo() == Flag.SETTED);
					lOpzioniInvioBean.setFlgInviaDocCollegati(lDestinatariOutBean.getFlgInviaDocCollegati() == Flag.SETTED);
					lOpzioniInvioBean.setFlgMantieniCopiaUd(lDestinatariOutBean.getFlgMantieniCopiaUd() == Flag.SETTED);
					lDestinatarioProtBean.setOpzioniInvio(lOpzioniInvioBean);
					*/
				}
				lDestinatarioProtBean.setCodfiscaleDestinatario(lDestinatariOutBean.getCodiceFiscale());
				lDestinatarioProtBean.setIndirizzoMailDestinatario(lDestinatariOutBean.getIndirizzoMail());				
				lDestinatarioProtBean.setCodRapidoDestinatario(lDestinatariOutBean.getCodRapido());
				if(StringUtils.isNotBlank(lDestinatariOutBean.getTipoDestinatario())) {	
					if("UP".equals(lDestinatariOutBean.getTipoDestinatario()) || "UOI".equals(lDestinatariOutBean.getTipoDestinatario())) {
						lDestinatarioProtBean.setTipoDestinatario(ParametriDBUtil.getParametroDBAsBoolean(session,"DEST_INT_CON_SELECT") ? "UP_UOI" : lDestinatariOutBean.getTipoDestinatario());
					} else {
						lDestinatarioProtBean.setTipoDestinatario(lDestinatariOutBean.getTipoDestinatario());
					}
				}
				lDestinatarioProtBean.setIdSoggetto(lDestinatariOutBean.getIdRubrica());
				if ("AOOI".equals(lDestinatariOutBean.getTipoDestinatario())) {
					lDestinatarioProtBean.setAoomdgDestinatario(lDestinatariOutBean.getIdRubrica());
					lDestinatarioProtBean.setDescrAoomdgDestinatario(lDestinatariOutBean.getDenominazioneCognome());
				} else if ("LD".equals(lDestinatariOutBean.getTipoDestinatario())) {
					lDestinatarioProtBean.setGruppiDestinatario(lDestinatariOutBean.getIdRubrica());
					lDestinatarioProtBean.setIdGruppoEsterno(lDestinatariOutBean.getIdRubrica());
					lDestinatarioProtBean.setGruppoSalvato(lDestinatariOutBean.getIdRubrica());
					lDestinatarioProtBean.setDenominazioneDestinatario(lDestinatariOutBean.getDenominazioneCognome());
					lDestinatarioProtBean.setIdGruppoInterno(lDestinatariOutBean.getIdSoggetto());
					if(lDestinatariOutBean.getFlgAssegnaAlDestinatario() == Flag.SETTED) {
						lDestinatarioProtBean.setIdAssegnatario("LD" + lDestinatariOutBean.getIdSoggetto());	
					} 
					if(lDestinatariOutBean.getPerConoscenza() == Flag.SETTED) {
						lDestinatarioProtBean.setIdDestInvioCC("LD" + lDestinatariOutBean.getIdSoggetto());	
					}
				} else if (lDestinatariOutBean.getTipo() == TipoDestinatario.UNITA_DI_PERSONALE) {
					lDestinatarioProtBean.setDenominazioneDestinatario(lDestinatariOutBean.getDenominazioneCognome() + " " + lDestinatariOutBean.getNome());
					lDestinatarioProtBean.setCognomeDestinatario(lDestinatariOutBean.getDenominazioneCognome());
					lDestinatarioProtBean.setNomeDestinatario(lDestinatariOutBean.getNome());
				} else {
					lDestinatarioProtBean.setDenominazioneDestinatario(lDestinatariOutBean.getDenominazioneCognome());
				}
				if (lDestinatariOutBean.getTipoSoggetto() != null) {
					if ("UO".equals(lDestinatariOutBean.getTipoSoggetto())) {
						lDestinatarioProtBean.setIdUoSoggetto(lDestinatariOutBean.getIdSoggetto());
						lDestinatarioProtBean.setOrganigrammaDestinatario("UO" + lDestinatariOutBean.getIdSoggetto());
						if(lDestinatariOutBean.getFlgAssegnaAlDestinatario() == Flag.SETTED) {
							lDestinatarioProtBean.setIdAssegnatario("UO" + lDestinatariOutBean.getIdSoggetto());	
						} 
						if(lDestinatariOutBean.getPerConoscenza() == Flag.SETTED) {
							lDestinatarioProtBean.setIdDestInvioCC("UO" + lDestinatariOutBean.getIdSoggetto());	
						}
					}
					if ("SV".equals(lDestinatariOutBean.getTipoSoggetto())) {
						lDestinatarioProtBean.setIdScrivaniaSoggetto(lDestinatariOutBean.getIdSoggetto());
						lDestinatarioProtBean.setOrganigrammaDestinatario("SV" + lDestinatariOutBean.getIdSoggetto());
						if(lDestinatariOutBean.getFlgAssegnaAlDestinatario() == Flag.SETTED) {
							lDestinatarioProtBean.setIdAssegnatario("SV" + lDestinatariOutBean.getIdSoggetto());	
						}
						if(lDestinatariOutBean.getPerConoscenza() == Flag.SETTED) {
							lDestinatarioProtBean.setIdDestInvioCC("SV" + lDestinatariOutBean.getIdSoggetto());	
						}
					}
//					if ("UT".equals(lDestinatariOutBean.getTipoSoggetto())) {
//						lDestinatarioProtBean.setIdUserSoggetto(lDestinatariOutBean.getIdSoggetto());
//						if(lDestinatariOutBean.getFlgAssegnaAlDestinatario() == Flag.SETTED) {
//							lDestinatarioProtBean.setIdAssegnatario("UT" + lDestinatariOutBean.getIdSoggetto());	
//						} 
//						if(lDestinatariOutBean.getPerConoscenza() == Flag.SETTED) {
//							lDestinatarioProtBean.setIdDestInvioCC("UT" + lDestinatariOutBean.getIdSoggetto());	
//						}
//					}
				}				
				if (ParametriDBUtil.getParametroDBAsBoolean(session, "SHOW_CC_IN_DEST_REG")) {
					lDestinatarioProtBean.setFlgPC(lDestinatariOutBean.getPerConoscenza() == Flag.SETTED);
				}
				if (isAttivoProtocolloWizard(lBean)) {
					// dati indirizzo
					lDestinatarioProtBean.setStato(lDestinatariOutBean.getCodIstatStato());
					lDestinatarioProtBean.setNomeStato(lDestinatariOutBean.getStato());
					if (StringUtils.isBlank(lDestinatarioProtBean.getStato()) || _COD_ISTAT_ITALIA.equals(lDestinatarioProtBean.getStato())
							|| _NOME_STATO_ITALIA.equals(lDestinatarioProtBean.getStato())) {
						if (StringUtils.isNotBlank(lDestinatariOutBean.getCiToponimo()) || StringUtils.isBlank(lDestinatariOutBean.getIndirizzo())) {
							lDestinatarioProtBean.setFlgFuoriComune(false);
							lDestinatarioProtBean.setCodToponimo(lDestinatariOutBean.getCiToponimo());
							lDestinatarioProtBean.setIndirizzo(lDestinatariOutBean.getIndirizzo());
							lDestinatarioProtBean.setComune(getCodIstatComuneRif());
							lDestinatarioProtBean.setNomeComune(getNomeComuneRif());
						} else {
							lDestinatarioProtBean.setFlgFuoriComune(true);
							lDestinatarioProtBean.setTipoToponimo(lDestinatariOutBean.getTipoToponimo());
							lDestinatarioProtBean.setToponimo(lDestinatariOutBean.getIndirizzo());
							lDestinatarioProtBean.setComune(lDestinatariOutBean.getCodIstatComune());
							lDestinatarioProtBean.setNomeComune(lDestinatariOutBean.getComune());
						}
						lDestinatarioProtBean.setFrazione(lDestinatariOutBean.getFrazione());
						lDestinatarioProtBean.setCap(lDestinatariOutBean.getCap());
					} else {
						lDestinatarioProtBean.setIndirizzo(lDestinatariOutBean.getIndirizzo());
						lDestinatarioProtBean.setCitta(lDestinatariOutBean.getComune());
					}
					lDestinatarioProtBean.setCivico(lDestinatariOutBean.getCivico());
					lDestinatarioProtBean.setInterno(lDestinatariOutBean.getInterno());
					lDestinatarioProtBean.setZona(lDestinatariOutBean.getZona());
					lDestinatarioProtBean.setComplementoIndirizzo(lDestinatariOutBean.getComplementoIndirizzo());
					lDestinatarioProtBean.setAppendici(lDestinatariOutBean.getAppendici());
				} else if(ParametriDBUtil.getParametroDBAsBoolean(session, "SHOW_DESTINATARI_ESTESI")) {
					MezzoTrasmissioneDestinatarioBean lMezzoTrasmissioneDestinatarioBean = new MezzoTrasmissioneDestinatarioBean();
					lMezzoTrasmissioneDestinatarioBean.setMezzoTrasmissioneDestinatario(lDestinatariOutBean.getMezzoTrasmissioneDestinatario());
					lMezzoTrasmissioneDestinatarioBean.setDescrizioneMezzoTrasmissioneDestinatario(lDestinatariOutBean.getDescrizioneMezzoTrasmissioneDestinatario());
					lMezzoTrasmissioneDestinatarioBean.setDataRaccomandataDestinatario(lDestinatariOutBean.getDataRaccomandataDestinatario());
					lMezzoTrasmissioneDestinatarioBean.setNroRaccomandataDestinatario(lDestinatariOutBean.getNroRaccomandataDestinatario());
					lMezzoTrasmissioneDestinatarioBean.setDataNotificaDestinatario(lDestinatariOutBean.getDataNotificaDestinatario());
					lMezzoTrasmissioneDestinatarioBean.setNroNotificaDestinatario(lDestinatariOutBean.getNroNotificaDestinatario());
					if(lMezzoTrasmissioneDestinatarioBean.getMezzoTrasmissioneDestinatario() != null) {						
						if ("PEC".equals(lMezzoTrasmissioneDestinatarioBean.getMezzoTrasmissioneDestinatario())) {
							lMezzoTrasmissioneDestinatarioBean.setIndirizzoPECDestinatario(lDestinatariOutBean.getIndirizzoMail());							
						} else if ("PEO".equals(lMezzoTrasmissioneDestinatarioBean.getMezzoTrasmissioneDestinatario())) {
							lMezzoTrasmissioneDestinatarioBean.setIndirizzoPEODestinatario(lDestinatariOutBean.getIndirizzoMail());							
						}
						lMezzoTrasmissioneDestinatarioBean.setIndirizzoMailDestinatario(lDestinatariOutBean.getIndirizzoMail());
					}
					if(ParametriDBUtil.getParametroDBAsBoolean(session, "ATTIVA_INDIRIZZO_DEST_ESTESI")) {
						// dati indirizzo
						lDestinatarioProtBean.setStato(lDestinatariOutBean.getCodIstatStato());
						lDestinatarioProtBean.setNomeStato(lDestinatariOutBean.getStato());
						if (StringUtils.isBlank(lDestinatarioProtBean.getStato()) || _COD_ISTAT_ITALIA.equals(lDestinatarioProtBean.getStato())
								|| _NOME_STATO_ITALIA.equals(lDestinatarioProtBean.getStato())) {
							if (StringUtils.isNotBlank(lDestinatariOutBean.getCiToponimo()) || StringUtils.isBlank(lDestinatariOutBean.getIndirizzo())) {
								lDestinatarioProtBean.setFlgFuoriComune(false);
								lDestinatarioProtBean.setCodToponimo(lDestinatariOutBean.getCiToponimo());
								lDestinatarioProtBean.setIndirizzo(lDestinatariOutBean.getIndirizzo());
								lDestinatarioProtBean.setComune(getCodIstatComuneRif());
								lDestinatarioProtBean.setNomeComune(getNomeComuneRif());
							} else {
								lDestinatarioProtBean.setFlgFuoriComune(true);
								lDestinatarioProtBean.setTipoToponimo(lDestinatariOutBean.getTipoToponimo());
								lDestinatarioProtBean.setToponimo(lDestinatariOutBean.getIndirizzo());
								lDestinatarioProtBean.setComune(lDestinatariOutBean.getCodIstatComune());
								lDestinatarioProtBean.setNomeComune(lDestinatariOutBean.getComune());
							}
							lDestinatarioProtBean.setFrazione(lDestinatariOutBean.getFrazione());
							lDestinatarioProtBean.setCap(lDestinatariOutBean.getCap());
						} else {
							lDestinatarioProtBean.setIndirizzo(lDestinatariOutBean.getIndirizzo());
							lDestinatarioProtBean.setCitta(lDestinatariOutBean.getComune());
						}
						lDestinatarioProtBean.setCivico(lDestinatariOutBean.getCivico());
						lDestinatarioProtBean.setInterno(lDestinatariOutBean.getInterno());
						lDestinatarioProtBean.setZona(lDestinatariOutBean.getZona());
						lDestinatarioProtBean.setComplementoIndirizzo(lDestinatariOutBean.getComplementoIndirizzo());
						lDestinatarioProtBean.setAppendici(lDestinatariOutBean.getAppendici());
						// devo settare anche questi per l'invio a Postel
						lMezzoTrasmissioneDestinatarioBean.setIndirizzo(lDestinatariOutBean.getIndirizzo());
						lMezzoTrasmissioneDestinatarioBean.setFrazione(lDestinatariOutBean.getFrazione());
						lMezzoTrasmissioneDestinatarioBean.setCivico(lDestinatariOutBean.getCivico());
						lMezzoTrasmissioneDestinatarioBean.setInterno(lDestinatariOutBean.getInterno());
						lMezzoTrasmissioneDestinatarioBean.setScala(lDestinatariOutBean.getScala());
						lMezzoTrasmissioneDestinatarioBean.setPiano(lDestinatariOutBean.getPiano());
						lMezzoTrasmissioneDestinatarioBean.setCap(lDestinatariOutBean.getCap());
						lMezzoTrasmissioneDestinatarioBean.setCodIstatComune(lDestinatariOutBean.getCodIstatComune());
						lMezzoTrasmissioneDestinatarioBean.setComune(lDestinatariOutBean.getComune());
						lMezzoTrasmissioneDestinatarioBean.setCodIstatStato(lDestinatariOutBean.getCodIstatStato());
						lMezzoTrasmissioneDestinatarioBean.setStato(lDestinatariOutBean.getStato());
						lMezzoTrasmissioneDestinatarioBean.setZona(lDestinatariOutBean.getZona());
						lMezzoTrasmissioneDestinatarioBean.setComplementoIndirizzo(lDestinatariOutBean.getComplementoIndirizzo());
						lMezzoTrasmissioneDestinatarioBean.setAppendici(lDestinatariOutBean.getAppendici());
						lMezzoTrasmissioneDestinatarioBean.setTipoToponimo(lDestinatariOutBean.getTipoToponimo());
						lMezzoTrasmissioneDestinatarioBean.setCiToponimo(lDestinatariOutBean.getCiToponimo());	
						lMezzoTrasmissioneDestinatarioBean.setIndirizzoDestinatario(lDestinatariOutBean.getIndirizzoDestinatario());
						lMezzoTrasmissioneDestinatarioBean.setDescrizioneIndirizzo(lDestinatariOutBean.getDescrizioneIndirizzo());			
					} else {
						lMezzoTrasmissioneDestinatarioBean.setIndirizzo(lDestinatariOutBean.getIndirizzo());
						lMezzoTrasmissioneDestinatarioBean.setFrazione(lDestinatariOutBean.getFrazione());
						lMezzoTrasmissioneDestinatarioBean.setCivico(lDestinatariOutBean.getCivico());
						lMezzoTrasmissioneDestinatarioBean.setInterno(lDestinatariOutBean.getInterno());
						lMezzoTrasmissioneDestinatarioBean.setScala(lDestinatariOutBean.getScala());
						lMezzoTrasmissioneDestinatarioBean.setPiano(lDestinatariOutBean.getPiano());
						lMezzoTrasmissioneDestinatarioBean.setCap(lDestinatariOutBean.getCap());
						lMezzoTrasmissioneDestinatarioBean.setCodIstatComune(lDestinatariOutBean.getCodIstatComune());
						lMezzoTrasmissioneDestinatarioBean.setComune(lDestinatariOutBean.getComune());
						lMezzoTrasmissioneDestinatarioBean.setCodIstatStato(lDestinatariOutBean.getCodIstatStato());
						lMezzoTrasmissioneDestinatarioBean.setStato(lDestinatariOutBean.getStato());
						lMezzoTrasmissioneDestinatarioBean.setZona(lDestinatariOutBean.getZona());
						lMezzoTrasmissioneDestinatarioBean.setComplementoIndirizzo(lDestinatariOutBean.getComplementoIndirizzo());
						lMezzoTrasmissioneDestinatarioBean.setAppendici(lDestinatariOutBean.getAppendici());
						lMezzoTrasmissioneDestinatarioBean.setTipoToponimo(lDestinatariOutBean.getTipoToponimo());
						lMezzoTrasmissioneDestinatarioBean.setCiToponimo(lDestinatariOutBean.getCiToponimo());	
						lMezzoTrasmissioneDestinatarioBean.setIndirizzoDestinatario(lDestinatariOutBean.getIndirizzoDestinatario());
						lMezzoTrasmissioneDestinatarioBean.setDescrizioneIndirizzo(lDestinatariOutBean.getDescrizioneIndirizzo());						
					}
					lDestinatarioProtBean.setMezzoTrasmissioneDestinatario(lMezzoTrasmissioneDestinatarioBean);
				}
				lListDestinatari.add(lDestinatarioProtBean);
			}
			lBean.setListaDestinatari(lListDestinatari);
		}

		// Originale cartaceo e copia sostitutiva
		if (isAttivoProtocolloWizard(lBean)) {							
			lBean.setFlgOriginaleCartaceo(lDocumentoXmlOutBean.getFlgOriginaleCartaceo() != null && "1".equals(lDocumentoXmlOutBean.getFlgOriginaleCartaceo()));
			lBean.setFlgCopiaSostitutiva(lDocumentoXmlOutBean.getFlgCopiaSostitutiva() != null && "1".equals(lDocumentoXmlOutBean.getFlgCopiaSostitutiva()));
		}
		
		if (StringUtils.isNotBlank(ParametriDBUtil.getParametroDB(session, "DOC_PRESSO_CENTRO_POSTA"))) {
			lBean.setDocPressoCentroPosta(lDocumentoXmlOutBean.getDocPressoCentroPosta());
		}
			
		// Caricamento pregresso e identificativo di workflow PG@Web
		if (isClienteComuneMilano()) {			
//			lBean.setSiglaIdentificativoWF(lDocumentoXmlOutBean.getSezionaleOnlyOne());
			lBean.setNroIdentificativoWF(lDocumentoXmlOutBean.getNumeroOnlyOne());
			lBean.setAnnoIdentificativoWF(lDocumentoXmlOutBean.getAnnoOnlyOne());						
			lBean.setFlgCaricamentoPGPregressoDaGUI(lDocumentoXmlOutBean.getFlgCaricamentoPGPregressoDaGUI() == Flag.SETTED);			
		}
		
		// Esibenti
		if (isAttivoProtocolloWizard(lBean) || isAttivoEsibenteSenzaWizard()) {										
			if (lDocumentoXmlOutBean.getEsibenti() != null && lDocumentoXmlOutBean.getEsibenti().size() > 0) {
				List<SoggEsternoProtBean> lListEsibenti = new ArrayList<SoggEsternoProtBean>();
				for (SoggettoEsternoBean lEsibente : lDocumentoXmlOutBean.getEsibenti()) {
					SoggEsternoProtBean lEsibenteProtBean = new SoggEsternoProtBean();
					lEsibenteProtBean.setCodFiscale(lEsibente.getCodFiscale());
					lEsibenteProtBean.setCodRapido(lEsibente.getCodiceRapido());
					lEsibenteProtBean.setIdSoggetto(lEsibente.getIdSoggetto());
					lEsibenteProtBean.setTipoSoggetto(lEsibente.getTipoSoggetto());
					lEsibenteProtBean.setCognome(lEsibente.getDenominazioneCognome());
					lEsibenteProtBean.setNome(lEsibente.getNome());
					lEsibenteProtBean.setTipoDocRiconoscimento(lEsibente.getTipoDocRiconoscimento());
					lEsibenteProtBean.setEstremiDocRiconoscimento(lEsibente.getEstremiDocRiconoscimento());
					lEsibenteProtBean.setStato(lEsibente.getStato());
					lEsibenteProtBean.setNomeStato(lEsibente.getNomeStato());
					if (StringUtils.isBlank(lEsibenteProtBean.getStato()) || _COD_ISTAT_ITALIA.equals(lEsibenteProtBean.getStato())
							|| _NOME_STATO_ITALIA.equals(lEsibenteProtBean.getStato())) {
						if (StringUtils.isNotBlank(lEsibente.getCodToponimo()) || StringUtils.isBlank(lEsibente.getToponimoIndirizzo())) {
							lEsibenteProtBean.setFlgFuoriComune(false);
							lEsibenteProtBean.setCodToponimo(lEsibente.getCodToponimo());
							lEsibenteProtBean.setIndirizzo(lEsibente.getToponimoIndirizzo());
							lEsibenteProtBean.setComune(getCodIstatComuneRif());
							lEsibenteProtBean.setNomeComune(getNomeComuneRif());
						} else {
							lEsibenteProtBean.setFlgFuoriComune(true);
							lEsibenteProtBean.setTipoToponimo(lEsibente.getTipoToponimo());
							lEsibenteProtBean.setToponimo(lEsibente.getToponimoIndirizzo());
							lEsibenteProtBean.setComune(lEsibente.getComune());
							lEsibenteProtBean.setNomeComune(lEsibente.getNomeComuneCitta());
						}
						lEsibenteProtBean.setFrazione(lEsibente.getFrazione());
						lEsibenteProtBean.setCap(lEsibente.getCap());
					} else {
						lEsibenteProtBean.setIndirizzo(lEsibente.getToponimoIndirizzo());
						lEsibenteProtBean.setCitta(lEsibente.getNomeComuneCitta());
					}
					lEsibenteProtBean.setCivico(lEsibente.getCivico());
					lEsibenteProtBean.setInterno(lEsibente.getInterno());
					lEsibenteProtBean.setZona(lEsibente.getZona());
					lEsibenteProtBean.setComplementoIndirizzo(lEsibente.getComplementoIndirizzo());
					lEsibenteProtBean.setAppendici(lEsibente.getAppendici());
					lEsibenteProtBean.setFlgAncheMittente(lEsibente.getFlgAncheMittente() == Flag.SETTED);
					lListEsibenti.add(lEsibenteProtBean);
				}
				lBean.setListaEsibenti(lListEsibenti);
			}
		}
		
		// Interessati
		if (isAttivoProtocolloWizard(lBean) || isAttivoInteressatiSenzaWizard()) {													
			if (lDocumentoXmlOutBean.getInteressati() != null && lDocumentoXmlOutBean.getInteressati().size() > 0) {
				List<SoggEsternoProtBean> lListInteressati = new ArrayList<SoggEsternoProtBean>();
				for (SoggettoEsternoBean lInteressato : lDocumentoXmlOutBean.getInteressati()) {
					SoggEsternoProtBean lInteressatoProtBean = new SoggEsternoProtBean();
					lInteressatoProtBean.setCodFiscale(lInteressato.getCodFiscale());
					lInteressatoProtBean.setCodRapido(lInteressato.getCodiceRapido());
					lInteressatoProtBean.setIdSoggetto(lInteressato.getIdSoggetto());
					lInteressatoProtBean.setTipoSoggetto(lInteressato.getTipoSoggetto());
					if ("AOOI".equals(lInteressato.getTipoSoggetto())) {
						lInteressatoProtBean.setAoomdgSoggetto(lInteressato.getIdSoggetto());
						lInteressatoProtBean.setDescrAoomdgSoggetto(lInteressato.getDenominazioneCognome());
					} else if (lInteressato.getFlgPersFisica() == Flag.SETTED) {
						lInteressatoProtBean.setDenominazione(lInteressato.getDenominazioneCognome() + " " + lInteressato.getNome());
						lInteressatoProtBean.setCognome(lInteressato.getDenominazioneCognome());
						lInteressatoProtBean.setNome(lInteressato.getNome());
					} else {
						lInteressatoProtBean.setDenominazione(lInteressato.getDenominazioneCognome());
					}
					lInteressatoProtBean.setStato(lInteressato.getStato());
					lInteressatoProtBean.setNomeStato(lInteressato.getNomeStato());
					if (StringUtils.isBlank(lInteressatoProtBean.getStato()) || _COD_ISTAT_ITALIA.equals(lInteressatoProtBean.getStato())
							|| _NOME_STATO_ITALIA.equals(lInteressatoProtBean.getStato())) {
						if (StringUtils.isNotBlank(lInteressato.getCodToponimo()) || StringUtils.isBlank(lInteressato.getToponimoIndirizzo())) {
							lInteressatoProtBean.setFlgFuoriComune(false);
							lInteressatoProtBean.setCodToponimo(lInteressato.getCodToponimo());
							lInteressatoProtBean.setIndirizzo(lInteressato.getToponimoIndirizzo());
							lInteressatoProtBean.setComune(getCodIstatComuneRif());
							lInteressatoProtBean.setNomeComune(getNomeComuneRif());
						} else {
							lInteressatoProtBean.setFlgFuoriComune(true);
							lInteressatoProtBean.setTipoToponimo(lInteressato.getTipoToponimo());
							lInteressatoProtBean.setToponimo(lInteressato.getToponimoIndirizzo());
							lInteressatoProtBean.setComune(lInteressato.getComune());
							lInteressatoProtBean.setNomeComune(lInteressato.getNomeComuneCitta());
						}
						lInteressatoProtBean.setFrazione(lInteressato.getFrazione());
						lInteressatoProtBean.setCap(lInteressato.getCap());
					} else {
						lInteressatoProtBean.setIndirizzo(lInteressato.getToponimoIndirizzo());
						lInteressatoProtBean.setCitta(lInteressato.getNomeComuneCitta());
					}
					lInteressatoProtBean.setCivico(lInteressato.getCivico());
					lInteressatoProtBean.setInterno(lInteressato.getInterno());
					lInteressatoProtBean.setZona(lInteressato.getZona());
					lInteressatoProtBean.setComplementoIndirizzo(lInteressato.getComplementoIndirizzo());
					lInteressatoProtBean.setAppendici(lInteressato.getAppendici());
					lListInteressati.add(lInteressatoProtBean);
				}
				lBean.setListaInteressati(lListInteressati);
			}
		}
		
		// Delegato
		if (lDocumentoXmlOutBean.getDelegato() != null && lDocumentoXmlOutBean.getDelegato().size() > 0) {
			List<SoggEsternoProtBean> lListDelegato = new ArrayList<SoggEsternoProtBean>();
			for (SoggettoEsternoBean lDelegato : lDocumentoXmlOutBean.getDelegato()) {
				SoggEsternoProtBean lDelegatoProtBean = new SoggEsternoProtBean();
				lDelegatoProtBean.setCodFiscale(lDelegato.getCodFiscale());
				lDelegatoProtBean.setCodRapido(lDelegato.getCodiceRapido());
				lDelegatoProtBean.setIdSoggetto(lDelegato.getIdSoggetto());
				lDelegatoProtBean.setTipoSoggetto(lDelegato.getTipoSoggetto());
				if ("AOOI".equals(lDelegato.getTipoSoggetto())) {
					lDelegatoProtBean.setAoomdgSoggetto(lDelegato.getIdSoggetto());
					lDelegatoProtBean.setDescrAoomdgSoggetto(lDelegato.getDenominazioneCognome());
				} else if (lDelegato.getFlgPersFisica() == Flag.SETTED) {
					lDelegatoProtBean.setDenominazione(lDelegato.getDenominazioneCognome() + " " + lDelegato.getNome());
					lDelegatoProtBean.setCognome(lDelegato.getDenominazioneCognome());
					lDelegatoProtBean.setNome(lDelegato.getNome());
				} else {
					lDelegatoProtBean.setDenominazione(lDelegato.getDenominazioneCognome());
				}
				lDelegatoProtBean.setStato(lDelegato.getStato());
				lDelegatoProtBean.setNomeStato(lDelegato.getNomeStato());
				if (StringUtils.isBlank(lDelegatoProtBean.getStato()) || _COD_ISTAT_ITALIA.equals(lDelegatoProtBean.getStato())
						|| _NOME_STATO_ITALIA.equals(lDelegatoProtBean.getStato())) {
					if (StringUtils.isNotBlank(lDelegato.getCodToponimo()) || StringUtils.isBlank(lDelegato.getToponimoIndirizzo())) {
						lDelegatoProtBean.setFlgFuoriComune(false);
						lDelegatoProtBean.setCodToponimo(lDelegato.getCodToponimo());
						lDelegatoProtBean.setIndirizzo(lDelegato.getToponimoIndirizzo());
						lDelegatoProtBean.setComune(getCodIstatComuneRif());
						lDelegatoProtBean.setNomeComune(getNomeComuneRif());
					} else {
						lDelegatoProtBean.setFlgFuoriComune(true);
						lDelegatoProtBean.setTipoToponimo(lDelegato.getTipoToponimo());
						lDelegatoProtBean.setToponimo(lDelegato.getToponimoIndirizzo());
						lDelegatoProtBean.setComune(lDelegato.getComune());
						lDelegatoProtBean.setNomeComune(lDelegato.getNomeComuneCitta());
					}
					lDelegatoProtBean.setFrazione(lDelegato.getFrazione());
					lDelegatoProtBean.setCap(lDelegato.getCap());
				} else {
					lDelegatoProtBean.setIndirizzo(lDelegato.getToponimoIndirizzo());
					lDelegatoProtBean.setCitta(lDelegato.getNomeComuneCitta());
				}
				lDelegatoProtBean.setCivico(lDelegato.getCivico());
				lDelegatoProtBean.setInterno(lDelegato.getInterno());
				lDelegatoProtBean.setZona(lDelegato.getZona());
				lDelegatoProtBean.setComplementoIndirizzo(lDelegato.getComplementoIndirizzo());
				lDelegatoProtBean.setAppendici(lDelegato.getAppendici());
				lListDelegato.add(lDelegatoProtBean);
			}
			lBean.setListaDelegato(lListDelegato);
		}
		
		// Firmatari
		if (lDocumentoXmlOutBean.getFirmatari() != null && lDocumentoXmlOutBean.getFirmatari().size() > 0) {
			List<SoggEsternoProtBean> lListFirmatari = new ArrayList<SoggEsternoProtBean>();
			for (SoggettoEsternoBean lFirmatario : lDocumentoXmlOutBean.getFirmatari()) {
				SoggEsternoProtBean lFirmatarioProtBean = new SoggEsternoProtBean();
				lFirmatarioProtBean.setCodFiscale(lFirmatario.getCodFiscale());
				lFirmatarioProtBean.setCodRapido(lFirmatario.getCodiceRapido());
				lFirmatarioProtBean.setIdSoggetto(lFirmatario.getIdSoggetto());
				lFirmatarioProtBean.setTipoSoggetto(lFirmatario.getTipoSoggetto());
				if ("AOOI".equals(lFirmatario.getTipoSoggetto())) {
					lFirmatarioProtBean.setAoomdgSoggetto(lFirmatario.getIdSoggetto());
					lFirmatarioProtBean.setDescrAoomdgSoggetto(lFirmatario.getDenominazioneCognome());
				} else if (lFirmatario.getFlgPersFisica() == Flag.SETTED) {
					lFirmatarioProtBean.setDenominazione(lFirmatario.getDenominazioneCognome() + " " + lFirmatario.getNome());
					lFirmatarioProtBean.setCognome(lFirmatario.getDenominazioneCognome());
					lFirmatarioProtBean.setNome(lFirmatario.getNome());
				} else {
					lFirmatarioProtBean.setDenominazione(lFirmatario.getDenominazioneCognome());
				}
				lFirmatarioProtBean.setStato(lFirmatario.getStato());
				lFirmatarioProtBean.setNomeStato(lFirmatario.getNomeStato());
				if (StringUtils.isBlank(lFirmatarioProtBean.getStato()) || _COD_ISTAT_ITALIA.equals(lFirmatarioProtBean.getStato())
						|| _NOME_STATO_ITALIA.equals(lFirmatarioProtBean.getStato())) {
					if (StringUtils.isNotBlank(lFirmatario.getCodToponimo()) || StringUtils.isBlank(lFirmatario.getToponimoIndirizzo())) {
						lFirmatarioProtBean.setFlgFuoriComune(false);
						lFirmatarioProtBean.setCodToponimo(lFirmatario.getCodToponimo());
						lFirmatarioProtBean.setIndirizzo(lFirmatario.getToponimoIndirizzo());
						lFirmatarioProtBean.setComune(getCodIstatComuneRif());
						lFirmatarioProtBean.setNomeComune(getNomeComuneRif());
					} else {
						lFirmatarioProtBean.setFlgFuoriComune(true);
						lFirmatarioProtBean.setTipoToponimo(lFirmatario.getTipoToponimo());
						lFirmatarioProtBean.setToponimo(lFirmatario.getToponimoIndirizzo());
						lFirmatarioProtBean.setComune(lFirmatario.getComune());
						lFirmatarioProtBean.setNomeComune(lFirmatario.getNomeComuneCitta());
					}
					lFirmatarioProtBean.setFrazione(lFirmatario.getFrazione());
					lFirmatarioProtBean.setCap(lFirmatario.getCap());
				} else {
					lFirmatarioProtBean.setIndirizzo(lFirmatario.getToponimoIndirizzo());
					lFirmatarioProtBean.setCitta(lFirmatario.getNomeComuneCitta());
				}
				lFirmatarioProtBean.setCivico(lFirmatario.getCivico());
				lFirmatarioProtBean.setInterno(lFirmatario.getInterno());
				lFirmatarioProtBean.setZona(lFirmatario.getZona());
				lFirmatarioProtBean.setComplementoIndirizzo(lFirmatario.getComplementoIndirizzo());
				lFirmatarioProtBean.setAppendici(lFirmatario.getAppendici());
				lListFirmatari.add(lFirmatarioProtBean);
			}
			lBean.setListaFirmatari(lListFirmatari);
		}
		
		// Altre ubicazioni
		if (isAttivoProtocolloWizard(lBean) || isAttivoAltreVieSenzaWizard() || isRichiestaAccessoAtti(lBean) || isPropostaAtto(lBean)) {																	
			if (lDocumentoXmlOutBean.getAltreUbicazioni() != null && lDocumentoXmlOutBean.getAltreUbicazioni().size() > 0) {
				List<AltraViaProtBean> lListAltreVie = new ArrayList<AltraViaProtBean>();
				for (AltreUbicazioniBean lAltraVia : lDocumentoXmlOutBean.getAltreUbicazioni()) {
					AltraViaProtBean lAltraViaProtBean = new AltraViaProtBean();
					lAltraViaProtBean.setStato(lAltraVia.getStato());
					lAltraViaProtBean.setNomeStato(lAltraVia.getNomeStato());
					if (StringUtils.isBlank(lAltraViaProtBean.getStato()) || _COD_ISTAT_ITALIA.equals(lAltraViaProtBean.getStato())
							|| _NOME_STATO_ITALIA.equals(lAltraViaProtBean.getStato())) {
						if (StringUtils.isNotBlank(lAltraVia.getCodToponimo()) || StringUtils.isBlank(lAltraVia.getToponimoIndirizzo())) {
							lAltraViaProtBean.setFlgFuoriComune(false);
							lAltraViaProtBean.setCodToponimo(lAltraVia.getCodToponimo());
							lAltraViaProtBean.setIndirizzo(lAltraVia.getToponimoIndirizzo());
							lAltraViaProtBean.setComune(getCodIstatComuneRif());
							lAltraViaProtBean.setNomeComune(getNomeComuneRif());
						} else {
							lAltraViaProtBean.setFlgFuoriComune(true);
							lAltraViaProtBean.setTipoToponimo(lAltraVia.getTipoToponimo());
							lAltraViaProtBean.setToponimo(lAltraVia.getToponimoIndirizzo());
							lAltraViaProtBean.setComune(lAltraVia.getComune());
							lAltraViaProtBean.setNomeComune(lAltraVia.getNomeComuneCitta());
						}
						lAltraViaProtBean.setFrazione(lAltraVia.getFrazione());
						lAltraViaProtBean.setCap(lAltraVia.getCap());
					} else {
						lAltraViaProtBean.setIndirizzo(lAltraVia.getToponimoIndirizzo());
						lAltraViaProtBean.setCitta(lAltraVia.getNomeComuneCitta());
					}
					lAltraViaProtBean.setCivico(lAltraVia.getCivico());
					lAltraViaProtBean.setAppendici(lAltraVia.getAppendici());
					lAltraViaProtBean.setZona(lAltraVia.getZona());
					lAltraViaProtBean.setComplementoIndirizzo(lAltraVia.getComplementoIndirizzo());
					lListAltreVie.add(lAltraViaProtBean);
				}
				lBean.setListaAltreVie(lListAltreVie);
			}
		}

		// Contribuenti
		if (lDocumentoXmlOutBean.getListaContribuenti() != null && lDocumentoXmlOutBean.getListaContribuenti().size() > 0) {
			List<ContribuenteBean> lListContribuenti = new ArrayList<ContribuenteBean>();
			for (SoggettoEsternoBean lContribuente : lDocumentoXmlOutBean.getListaContribuenti()) {
				ContribuenteBean lContribuenteNewBean = new ContribuenteBean();
				lContribuenteNewBean.setCodFiscale(lContribuente.getCodFiscale());
				lContribuenteNewBean.setCodRapido(lContribuente.getCodiceRapido());
				lContribuenteNewBean.setIdSoggetto(lContribuente.getIdSoggetto());
				lContribuenteNewBean.setTipoSoggetto(lContribuente.getTipoSoggetto());

				if (lContribuente.getFlgPersFisica() == Flag.SETTED) {
					lContribuenteNewBean.setTipoSoggetto("PF");
					lContribuenteNewBean.setDenominazione(lContribuente.getDenominazioneCognome() + " " + lContribuente.getNome());
					lContribuenteNewBean.setCognome(lContribuente.getDenominazioneCognome());
					lContribuenteNewBean.setNome(lContribuente.getNome());
				} else {
					lContribuenteNewBean.setTipoSoggetto("PG");
					lContribuenteNewBean.setDenominazione(lContribuente.getDenominazioneCognome());
				}

				lContribuenteNewBean.setStato(lContribuente.getStato());
				lContribuenteNewBean.setNomeStato(lContribuente.getNomeStato());
				if (StringUtils.isBlank(lContribuenteNewBean.getStato()) || _COD_ISTAT_ITALIA.equals(lContribuenteNewBean.getStato())
						|| _NOME_STATO_ITALIA.equals(lContribuenteNewBean.getStato())) {
					if (StringUtils.isNotBlank(lContribuente.getCodToponimo()) || StringUtils.isBlank(lContribuente.getToponimoIndirizzo())) {
						lContribuenteNewBean.setFlgFuoriComune(false);
						lContribuenteNewBean.setCodToponimo(lContribuente.getCodToponimo());
						lContribuenteNewBean.setIndirizzo(lContribuente.getToponimoIndirizzo());
						lContribuenteNewBean.setComune(getCodIstatComuneRif());
						lContribuenteNewBean.setNomeComune(getNomeComuneRif());
					} else {
						lContribuenteNewBean.setFlgFuoriComune(true);
						lContribuenteNewBean.setTipoToponimo(lContribuente.getTipoToponimo());
						lContribuenteNewBean.setToponimo(lContribuente.getToponimoIndirizzo());
						lContribuenteNewBean.setComune(lContribuente.getComune());
						lContribuenteNewBean.setNomeComune(lContribuente.getNomeComuneCitta());
					}
					lContribuenteNewBean.setFrazione(lContribuente.getFrazione());
					lContribuenteNewBean.setCap(lContribuente.getCap());
				} else {
					lContribuenteNewBean.setIndirizzo(lContribuente.getToponimoIndirizzo());
					lContribuenteNewBean.setCitta(lContribuente.getNomeComuneCitta());
				}
				lContribuenteNewBean.setCivico(lContribuente.getCivico());
				lContribuenteNewBean.setInterno(lContribuente.getInterno());
				lContribuenteNewBean.setZona(lContribuente.getZona());
				lContribuenteNewBean.setComplementoIndirizzo(lContribuente.getComplementoIndirizzo());
				lContribuenteNewBean.setAppendici(lContribuente.getAppendici());

				lContribuenteNewBean.setCodiceACS(lContribuente.getProvCISogg()); // Codice ACS
				lContribuenteNewBean.setEmailContribuente(lContribuente.getEmailContribuente()); // Email contribuente
				lContribuenteNewBean.setTelContribuente(lContribuente.getTelefonoContribuente()); // Tel. contribuente

				lListContribuenti.add(lContribuenteNewBean);
			}
			lBean.setListaContribuenti(lListContribuenti);
		}

		// Leggo la data e ora ricezione delle istanze CED/AUTOTUTELA
		lBean.setDataEOraRicezione(lDocumentoXmlOutBean.getDataArrivo());

		// Documenti collegati
		if (lDocumentoXmlOutBean.getDocumentiCollegati() != null && lDocumentoXmlOutBean.getDocumentiCollegati().size() > 0) {
			List<DocCollegatoBean> lListDocumentiCollegati = new ArrayList<DocCollegatoBean>();
			for (DocumentoCollegato lDocumentoCollegato : lDocumentoXmlOutBean.getDocumentiCollegati()) {
				DocCollegatoBean lDocCollegatoBean = new DocCollegatoBean();
				lDocCollegatoBean.setIdUdCollegata(lDocumentoCollegato.getIdUd());
				String categoria = lDocumentoCollegato.getTipo() != null && lDocumentoCollegato.getTipo().getDbValue() != null ? lDocumentoCollegato.getTipo().getDbValue() : "";
	       		String sigla = lDocumentoCollegato.getSiglaRegistro() != null ? lDocumentoCollegato.getSiglaRegistro() : "";
	       		if("PG".equals(categoria)) {
	       			lDocCollegatoBean.setTipo("PG");
	       		} else if("R".equals(categoria)) {
	       			lDocCollegatoBean.setTipo("R");
	       			lDocCollegatoBean.setSiglaRegistro(sigla);
	       		} else if("PP".equals(categoria)) {
	       			lDocCollegatoBean.setTipo("PP");
	       			lDocCollegatoBean.setSiglaRegistro(sigla);
	       		} else if("P.I.".equals(sigla)) {
	       			lDocCollegatoBean.setTipo("PI");
	       		} else if("N.I.".equals(sigla)) {
	       			lDocCollegatoBean.setTipo("NI");			       			
	       		}
	       		lDocCollegatoBean.setIdTipoDoc(lDocumentoCollegato.getIdTipoDoc());
	       		lDocCollegatoBean.setNomeTipoDoc(lDocumentoCollegato.getNomeTipoDoc());
	       		lDocCollegatoBean.setAnno(lDocumentoCollegato.getAnno());
	       		lDocCollegatoBean.setNumero(lDocumentoCollegato.getNumero() != null ? String.valueOf(lDocumentoCollegato.getNumero()) : null);
	       		lDocCollegatoBean.setSub(lDocumentoCollegato.getSubNumero() != null ? String.valueOf(lDocumentoCollegato.getSubNumero()) : null);		       		
	       		lDocCollegatoBean.setMotivi(lDocumentoCollegato.getMotiviCollegamento());
	       		lDocCollegatoBean.setOggetto(lDocumentoCollegato.getOggetto());
	       		lDocCollegatoBean.setEstremiReg(lDocumentoCollegato.getEstremiReg());
	       		lDocCollegatoBean.setDatiCollegamento(lDocumentoCollegato.getDatiCollegamento());
	       		lDocCollegatoBean.setFlgLocked(lDocumentoCollegato.getFlgLocked() != null && "1".equals(lDocumentoCollegato.getFlgLocked()));
	       		lListDocumentiCollegati.add(lDocCollegatoBean);
			}
			lBean.setListaDocumentiCollegati(lListDocumentiCollegati);
		}
				
		// Altri riferimenti
		if (lDocumentoXmlOutBean.getAltriRiferimenti() != null && lDocumentoXmlOutBean.getAltriRiferimenti().size() > 0) {
			List<AltroRifBean> lListAltriRif = new ArrayList<AltroRifBean>();
			for (AltriRiferimentiBean lAltriRiferimentiBean : lDocumentoXmlOutBean.getAltriRiferimenti()) {
				AltroRifBean lAltroRifBean = new AltroRifBean();
				lAltroRifBean.setRegistroTipoRif(lAltriRiferimentiBean.getRegistroTipoRif());
				lAltroRifBean.setNumero(lAltriRiferimentiBean.getNumero());
				lAltroRifBean.setAnno(lAltriRiferimentiBean.getAnno());
				lAltroRifBean.setData(lAltriRiferimentiBean.getData());
				lAltroRifBean.setNote(lAltriRiferimentiBean.getNote());
				lListAltriRif.add(lAltroRifBean);
			}
			lBean.setListaAltriRiferimenti(lListAltriRif);
		}

		// Protocollo ricevuto
		lBean.setRifOrigineProtRicevuto(lDocumentoXmlOutBean.getRifDocRicevuto());
		lBean.setNroProtRicevuto(lDocumentoXmlOutBean.getEstremiRegDocRicevuto());
		lBean.setAnnoProtRicevuto(lDocumentoXmlOutBean.getAnnoDocRicevuto());
		lBean.setDataProtRicevuto(lDocumentoXmlOutBean.getDtDocRicevuto());
		lBean.setDataEOraArrivo(lDocumentoXmlOutBean.getDataArrivo());
		
		// Data spedizione
		lBean.setDataEOraSpedizione(lDocumentoXmlOutBean.getDataEOraSpedizione());
		
		// Assegnatari
		if (!isPropostaAtto || !ParametriDBUtil.getParametroDBAsBoolean(session, "ATTIVA_PROPOSTA_ATTO_2")) {
						
			lBean.setFlgPresentiAssPreselMitt(lDocumentoXmlOutBean.getFlgPresentiAssPreselMitt());
			
			if (lDocumentoXmlOutBean.getAssegnatari() != null && lDocumentoXmlOutBean.getAssegnatari().size() > 0) {
				List<AssegnazioneBean> lAssegnazioni = new ArrayList<AssegnazioneBean>();
				for (AssegnatariOutBean lAssegnatariOutBean : lDocumentoXmlOutBean.getAssegnatari()) {
					AssegnazioneBean lAssegnazioneBean = new AssegnazioneBean();
					if (lAssegnatariOutBean.getInternalValue().matches("[A-Z]{2}[0-9]+")) {
						String typeNodo = lAssegnatariOutBean.getInternalValue().substring(0, 2);
						if (typeNodo != null && "LD".equals(typeNodo)) {
							lAssegnazioneBean.setTipo("LD");
							lAssegnazioneBean.setTypeNodo(TipoAssegnatario.GRUPPO.toString());
							lAssegnazioneBean.setGruppo(lAssegnatariOutBean.getInternalValue().substring(2));
							lAssegnazioneBean.setGruppoSalvato(lAssegnatariOutBean.getInternalValue().substring(2));
						} else {
							lAssegnazioneBean.setTipo("SV;UO");
							lAssegnazioneBean.setTypeNodo(typeNodo);
							lAssegnazioneBean.setOrganigramma(lAssegnatariOutBean.getInternalValue());
							lAssegnazioneBean.setIdUo(lAssegnatariOutBean.getInternalValue().substring(2));
						}
					} else {
						lAssegnazioneBean.setTipo("SV;UO");
						lAssegnazioneBean.setOrganigramma(lAssegnatariOutBean.getInternalValue());
						lAssegnazioneBean.setIdUo(lAssegnatariOutBean.getInternalValue());
					}
					lAssegnazioneBean.setDescrizione(lAssegnatariOutBean.getDescr());
					lAssegnazioneBean.setDescrizioneEstesa(lAssegnatariOutBean.getDescrEstesa());
					lAssegnazioneBean.setCodRapido(lAssegnatariOutBean.getCodiceUo());
					lAssegnazioneBean.setPresaInCarico(lAssegnatariOutBean.getPresaInCarico());
					lAssegnazioneBean.setMessAltPresaInCarico(lAssegnatariOutBean.getMessAltPresaInCarico());	
					lAssegnazioneBean.setFlgDisable(lAssegnatariOutBean.getFlgDisable() == Flag.SETTED ? true : false);
					// TODO ASSEGNAZIONE LOAD
					/*
					 * OpzioniInvioBean lOpzioniInvioBean = new OpzioniInvioBean(); lOpzioniInvioBean.setMotivoInvio(lAssegnatariOutBean.getMotivoInvio());
					 * lOpzioniInvioBean.setLivelloPriorita(lAssegnatariOutBean.getLivelloPriorita());
					 * lOpzioniInvioBean.setMessaggioInvio(lAssegnatariOutBean.getMessaggioInvio());
					 * lOpzioniInvioBean.setFlgPresaInCarico(lAssegnatariOutBean.getFeedback() == Flag.SETTED);
					 * lOpzioniInvioBean.setFlgMancataPresaInCarico(lAssegnatariOutBean.getNumeroGiorniFeedback() != null);
					 * lOpzioniInvioBean.setNumeroGiorniFeedback(lAssegnatariOutBean.getNumeroGiorniFeedback());
					 * lOpzioniInvioBean.setFlgInviaFascicolo(lAssegnatariOutBean.getFlgInviaFascicolo() == Flag.SETTED);
					 * lOpzioniInvioBean.setFlgInviaDocCollegati(lAssegnatariOutBean.getFlgInviaDocCollegati() == Flag.SETTED);
					 * lOpzioniInvioBean.setFlgMantieniCopiaUd(lAssegnatariOutBean.getFlgMantieniCopiaUd() == Flag.SETTED);
					 * lAssegnazioneBean.setOpzioniInvio(lOpzioniInvioBean);
					 */
					lAssegnazioni.add(lAssegnazioneBean);
				}
				lBean.setListaAssegnazioni(lAssegnazioni);
			}
		}
		
		// Destinatari UO Protocollazione
		if (lDocumentoXmlOutBean.getListaDestinatariUoProtocollazione() != null && lDocumentoXmlOutBean.getListaDestinatariUoProtocollazione().size() > 0) {
			List<AssegnazioneBean> listaDestinatariUoProtocollazione = new ArrayList<AssegnazioneBean>();
			for (DestintarioUoProtocollazioneXmlBean item : lDocumentoXmlOutBean.getListaDestinatariUoProtocollazione()) {
				AssegnazioneBean destinatariUoProtocollazione = new AssegnazioneBean();
				destinatariUoProtocollazione.setIdUo(item.getIdUO());
				destinatariUoProtocollazione.setDescrizione(item.getDescrizione());
				
				listaDestinatariUoProtocollazione.add(destinatariUoProtocollazione);
			}
			lBean.setListaDestinatariUoProtocollazione(listaDestinatariUoProtocollazione);
		}
		
		// Invii per conoscenza
		if (!isPropostaAtto || !ParametriDBUtil.getParametroDBAsBoolean(session, "ATTIVA_PROPOSTA_ATTO_2")) {
			if (lDocumentoXmlOutBean.getDestInvioCC() != null && lDocumentoXmlOutBean.getDestInvioCC().size() > 0) {
				List<DestInvioCCBean> lDestInvioCC = new ArrayList<DestInvioCCBean>();
				for (DestInvioCCOutBean lDestInvioCCOutBean : lDocumentoXmlOutBean.getDestInvioCC()) {
					DestInvioCCBean lDestInvioCCBean = new DestInvioCCBean();
					lDestInvioCCBean.setTypeNodo(lDestInvioCCOutBean.getTipo());
					if (lDestInvioCCBean.getTypeNodo().equals(TipoDestInvioCC.GRUPPO.toString())) {
						lDestInvioCCBean.setTipo("LD");
						lDestInvioCCBean.setGruppo(lDestInvioCCOutBean.getId());
						lDestInvioCCBean.setGruppoSalvato(lDestInvioCCOutBean.getId());						
					} else {
						lDestInvioCCBean.setTipo("SV;UO");
						lDestInvioCCBean.setOrganigramma(lDestInvioCCOutBean.getTipo() + lDestInvioCCOutBean.getId());
						lDestInvioCCBean.setIdUo(lDestInvioCCOutBean.getId());
					}
					lDestInvioCCBean.setDescrizione(lDestInvioCCOutBean.getDescr());
					lDestInvioCCBean.setDescrizioneEstesa(lDestInvioCCOutBean.getDescrEstesa());
					lDestInvioCCBean.setCodRapido(lDestInvioCCOutBean.getCodRapido());
					//TODO CONDIVISIONE	LOAD
					/*
					OpzioniInvioCCBean lOpzioniInvioCCBean = new OpzioniInvioCCBean();
					lOpzioniInvioCCBean.setMotivoInvio(lDestInvioCCOutBean.getMotivoInvio());
					lOpzioniInvioCCBean.setLivelloPriorita(lDestInvioCCOutBean.getLivelloPriorita());
					lOpzioniInvioCCBean.setMessaggioInvio(lDestInvioCCOutBean.getMessaggioInvio());	
					lDestInvioCCBean.setOpzioniInvio(lOpzioniInvioCCBean);
					*/
					lDestInvioCC.add(lDestInvioCCBean);
				}
				lBean.setListaDestInvioCC(lDestInvioCC);
			}
		}
		
		// Altre U.O. coinvolte
		if (lDocumentoXmlOutBean.getAltreUoCoinvolte() != null && lDocumentoXmlOutBean.getAltreUoCoinvolte().size() > 0) {
			List<DestInvioCCBean> lUoCoinvolte = new ArrayList<DestInvioCCBean>();
			for (DestInvioCCOutBean lDestInvioCCOutBean : lDocumentoXmlOutBean.getAltreUoCoinvolte()) {
				DestInvioCCBean lDestInvioCCBean = new DestInvioCCBean();
				lDestInvioCCBean.setTypeNodo(lDestInvioCCOutBean.getTipo());
				lDestInvioCCBean.setOrganigramma(lDestInvioCCOutBean.getTipo() + lDestInvioCCOutBean.getId());
				lDestInvioCCBean.setIdUo(lDestInvioCCOutBean.getId());
				lDestInvioCCBean.setTipo("SV;UO");
				lDestInvioCCBean.setDescrizione(lDestInvioCCOutBean.getDescr());
				lDestInvioCCBean.setDescrizioneEstesa(lDestInvioCCOutBean.getDescrEstesa());
				lDestInvioCCBean.setCodRapido(lDestInvioCCOutBean.getCodRapido());
				lDestInvioCCBean.setStatoFirmaResponsabile(lDestInvioCCOutBean.getStatoFirmaResponsabile());
				lUoCoinvolte.add(lDestInvioCCBean);
			}
			lBean.setListaUoCoinvolte(lUoCoinvolte);
		}
		
		// Classifiche/fascicoli
		if (lDocumentoXmlOutBean.getClassifichefascicoli() != null && lDocumentoXmlOutBean.getClassifichefascicoli().size() > 0) {
			List<ClassificazioneFascicoloBean> lListClassificazioneFascicoloBeans = new ArrayList<ClassificazioneFascicoloBean>();
			for (ClassFascTitolarioOutBean lClassFascTitolarioOutBean : lDocumentoXmlOutBean.getClassifichefascicoli()) {
				ClassificazioneFascicoloBean lClassificazioneFascicoloBean = new ClassificazioneFascicoloBean();
				lClassificazioneFascicoloBean.setAnnoFascicolo(lClassFascTitolarioOutBean.getAnnoFascicolo());
				lClassificazioneFascicoloBean.setIdClassifica(lClassFascTitolarioOutBean.getIdClassifica());
				lClassificazioneFascicoloBean.setIdFolderFascicolo(lClassFascTitolarioOutBean.getIdFolderFascicolo());
				lClassificazioneFascicoloBean.setIndice(lClassFascTitolarioOutBean.getIndice());
				lClassificazioneFascicoloBean.setNomeFascicolo(lClassFascTitolarioOutBean.getNomeFascSottoFasc());
				lClassificazioneFascicoloBean.setNroFascicolo(lClassFascTitolarioOutBean.getNroFascicolo());
				lClassificazioneFascicoloBean.setNroSottofascicolo(lClassFascTitolarioOutBean.getNroSottofascicolo());
				lClassificazioneFascicoloBean.setNroInserto(lClassFascTitolarioOutBean.getNroInserto());				
				lClassificazioneFascicoloBean.setClassifiche(lClassFascTitolarioOutBean.getIdClassifica());
				lClassificazioneFascicoloBean.setDescrizioneClassifica(lClassFascTitolarioOutBean.getDescrizione());
				lClassificazioneFascicoloBean.setCodice(lClassFascTitolarioOutBean.getNroSecondario());
				lClassificazioneFascicoloBean.setFlgCapofila(lClassFascTitolarioOutBean.getFlgCapofila() != null
						&& "1".equals(lClassFascTitolarioOutBean.getFlgCapofila()));
				lClassificazioneFascicoloBean.setCapofila(lClassFascTitolarioOutBean.getEstremiDocCapofila());
				lClassificazioneFascicoloBean.setFlgAttiva(lClassFascTitolarioOutBean.getFlgAttiva());
				lListClassificazioneFascicoloBeans.add(lClassificazioneFascicoloBean);
			}
			lBean.setListaClassFasc(lListClassificazioneFascicoloBeans);
		}
		
		// Folder custom
		if (!isPropostaAtto || !ParametriDBUtil.getParametroDBAsBoolean(session, "ATTIVA_PROPOSTA_ATTO_2")) {
			if (lDocumentoXmlOutBean.getFolderCustom() != null && lDocumentoXmlOutBean.getFolderCustom().size() > 0) {
				List<FolderCustomBean> lListFolderCustomBeans = new ArrayList<FolderCustomBean>();
				for (FolderCustom lFolderCustom : lDocumentoXmlOutBean.getFolderCustom()) {
					FolderCustomBean lFolderCustomBean = new FolderCustomBean();
					lFolderCustomBean.setId(lFolderCustom.getId());
					lFolderCustomBean.setPath(lFolderCustom.getPath());
					lFolderCustomBean.setCodice(lFolderCustom.getNroSecondario());
					lFolderCustomBean.setFlgCapofila(lFolderCustom.getFlgCapofila() != null && "1".equals(lFolderCustom.getFlgCapofila()));
					lFolderCustomBean.setCapofila(lFolderCustom.getEstremiDocCapofila());
					lListFolderCustomBeans.add(lFolderCustomBean);
				}
				lBean.setListaFolderCustom(lListFolderCustomBeans);
			}
		}
		
		// Numerazione principale
		RegNumPrincipale lRegNumPrincipale = lDocumentoXmlOutBean.getEstremiRegistrazione();
		if (lRegNumPrincipale != null) {
			lBean.setTipoProtocollo(lRegNumPrincipale.getTipo());
			lBean.setRegistroProtocollo(lRegNumPrincipale.getRegistro());
			lBean.setCodCategoriaProtocollo(lRegNumPrincipale.getCodCategoria());
			lBean.setSiglaProtocollo(lRegNumPrincipale.getSigla());
			lBean.setAnnoProtocollo(lRegNumPrincipale.getAnno());
			lBean.setNroProtocollo(StringUtils.isNotBlank(lRegNumPrincipale.getNro()) ? new BigDecimal(lRegNumPrincipale.getNro()) : null);
			lBean.setSubProtocollo(lRegNumPrincipale.getSubNro());
			lBean.setDataProtocollo(lRegNumPrincipale.getTsRegistrazione());
			lBean.setIdUdAttoAutAnnProtocollo(lRegNumPrincipale.getIdUdAttoAutAnnReg());
			lBean.setDesUserProtocollo(lRegNumPrincipale.getDesUser());
			lBean.setUoProtocollante(StringUtils.isNotBlank(lRegNumPrincipale.getIdUO()) ? "UO" + lRegNumPrincipale.getIdUO() : null);			
			lBean.setDesUOProtocollo(lRegNumPrincipale.getDesUO());
			lBean.setAnnullata(lRegNumPrincipale.getAnnullata());
			lBean.setDatiAnnullamento(lRegNumPrincipale.getDatiAnnullamento());
			lBean.setRichAnnullamento(lRegNumPrincipale.getRichAnnullamento());
			lBean.setDatiRichAnnullamento(lRegNumPrincipale.getDatiRichAnnullamento());
			lBean.setMotiviRichAnnullamento(lRegNumPrincipale.getMotiviRichAnnullamento());
			//Numero protocollo PG@Web
			if (isClienteComuneMilano()) {
				lBean.setNroProtocolloPregresso(lRegNumPrincipale.getNro());
				lBean.setSubProtocolloPregresso(lRegNumPrincipale.getSubNro());
				lBean.setAnnoProtocolloPregresso(lRegNumPrincipale.getAnno());
				lBean.setDataProtocolloPregresso(lRegNumPrincipale.getTsRegistrazione());			
			}
		}
		
		lBean.setSocieta(lDocumentoXmlOutBean.getCodSocieta());
		
		lBean.setEstremiRepertorioPerStruttura(lDocumentoXmlOutBean.getEstremiRepertorioPerStruttura());
		
		lBean.setIdUdLiquidazione(lDocumentoXmlOutBean.getIdUdLiquidazione());
		lBean.setEstremiAttoLiquidazione(lDocumentoXmlOutBean.getEstremiAttoLiquidazione());

		// Numerazione secondaria
		lBean.setSiglaNumerazioneSecondaria(lDocumentoXmlOutBean.getSiglaRegNumerazioneSecondaria());
		lBean.setAnnoNumerazioneSecondaria(lDocumentoXmlOutBean.getAnnoRegNumerazioneSecondaria());
		lBean.setTipoNumerazioneSecondaria(lDocumentoXmlOutBean.getTipoRegNumerazioneSecondaria());
		lBean.setNumeroNumerazioneSecondaria(StringUtils.isNotBlank(lDocumentoXmlOutBean.getNumeroRegNumerazioneSecondaria()) ? new BigDecimal(
				lDocumentoXmlOutBean.getNumeroRegNumerazioneSecondaria()) : null);
		lBean.setSubNumerazioneSecondaria(lDocumentoXmlOutBean.getSubNroRegNumerazioneSecondaria());
		lBean.setDataRegistrazioneNumerazioneSecondaria(lDocumentoXmlOutBean.getDataRegistrazioneNumerazioneSecondaria());
		lBean.setIdUdAttoAutAnnRegSecondaria(lDocumentoXmlOutBean.getIdUdAttoAutAnnRegNumerazioneSecondaria());
		
		// Numerazione secondaria per richiesta accesso atti (N° e Anno della pratica sui sistemi ufficio richiedente )
		lBean.setSiglaPraticaSuSistUfficioRichiedente(lDocumentoXmlOutBean.getSiglaRegNumerazioneSecondaria());
		lBean.setNumeroPraticaSuSistUfficioRichiedente(lDocumentoXmlOutBean.getNumeroRegNumerazioneSecondaria());
		lBean.setAnnoPraticaSuSistUfficioRichiedente(lDocumentoXmlOutBean.getAnnoRegNumerazioneSecondaria());
		
		// Altre numerazioni UD
		lBean.setAltreNumerazioniUD(lDocumentoXmlOutBean.getAltreNumerazioniUD());

		// Presenza doc. collegati
		lBean.setPresenzaDocCollegati(lDocumentoXmlOutBean.getPresenzaDocCollegati());

		// Abilitazioni
		buildAbilitazioni(lDocumentoXmlOutBean, lBean);
		
		// Dati annullati
		lBean.setConDatiAnnullati(lDocumentoXmlOutBean.getConDatiAnnullati());
		lBean.setListaDatiAnnullati(lDocumentoXmlOutBean.getListaDatiAnnullati());
		
		// Email di provenienza
		if (lDocumentoXmlOutBean.getEmailProv() != null) {
			EmailProvBean lEmailProvBean = lDocumentoXmlOutBean.getEmailProv();
			lBean.setFlgCasellaIstituzionale(lEmailProvBean.getFlgCasellaIstituzionale() == Flag.SETTED ? true : false);
			lBean.setFlgDichIPA(lEmailProvBean.getFlgDichIPA() == Flag.SETTED ? true : false);
			lBean.setFlgPEC(lEmailProvBean.getFlgPEC() == Flag.SETTED ? true : false);
			lBean.setIndirizzo(lEmailProvBean.getIndirizzo());
			lBean.setGestorePEC(lEmailProvBean.getGestorePEC());
		}
		
		// Conferma assegnazione
		lBean.setIdUserConfermaAssegnazione(lDocumentoXmlOutBean.getIdUserConfermaAssegnazione());
		lBean.setDesUserConfermaAssegnazione(lDocumentoXmlOutBean.getDesUserConfermaAssegnazione());
		lBean.setUsernameConfermaAssegnazione(lDocumentoXmlOutBean.getUsernameConfermaAssegnazione());
		
		// Notifiche interoperabili
		lBean.setRicEccezioniInterop(lDocumentoXmlOutBean.getRicEccezioniInterop() != null && "true".equals(lDocumentoXmlOutBean.getRicEccezioniInterop()));
		lBean.setRicAggiornamentiInterop(lDocumentoXmlOutBean.getRicAggiornamentiInterop() != null
				&& "true".equals(lDocumentoXmlOutBean.getRicAggiornamentiInterop()));
		lBean.setRicAnnullamentiInterop(lDocumentoXmlOutBean.getRicAnnullamentiInterop() != null
				&& "true".equals(lDocumentoXmlOutBean.getRicAnnullamentiInterop()));

		// Atti
		lBean.setDataAvvioIterAtto(lDocumentoXmlOutBean.getDataAvvioIterAtto());
		lBean.setFunzionarioIstruttoreAtto(lDocumentoXmlOutBean.getFunzionarioIstruttoreAtto());
		lBean.setResponsabileAtto(lDocumentoXmlOutBean.getResponsabileAtto());
		lBean.setDataFirmaAtto(lDocumentoXmlOutBean.getDataFirmaAtto());
		lBean.setFunzionarioFirmaAtto(lDocumentoXmlOutBean.getFunzionarioFirmaAtto());
		lBean.setDataPubblicazioneAtto(lDocumentoXmlOutBean.getDataPubblicazioneAtto());
		lBean.setLogIterAtto(lDocumentoXmlOutBean.getLogIterAtto());

		// Inviata mail interoperabile
		lBean.setInviataMailInteroperabile(lDocumentoXmlOutBean.getInviataMailInteroperabile());
	
		// Attiva timbro tipologia e azione di default stampa etichetta
		lBean.setAttivaTimbroTipologia(lDocumentoXmlOutBean.getAttivaTimbroTipologia() != null && lDocumentoXmlOutBean.getAttivaTimbroTipologia().equals("1"));
		lBean.setDefaultAzioneStampaEtichetta(lDocumentoXmlOutBean.getDefaultAzioneStampaEtichetta());
	
		// Compilazione modulo
		lBean.setFlgCompilazioneModulo(lDocumentoXmlOutBean.getFlgCompilazioneModulo() == Flag.SETTED);
				
		// Proposta atto
		lBean.setFlgPropostaAtto(lDocumentoXmlOutBean.getFlgPropostaAtto() == Flag.SETTED);
		
		// Federico Cacco 13.06.2017
		// Atti richiesti (per maschera di richiesta atti)		
		lBean.setCodStatoRichAccessoAtti(lDocumentoXmlOutBean.getCodStatoRichAccessoAtti());	
		lBean.setDesStatoRichAccessoAtti(lDocumentoXmlOutBean.getDesStatoRichAccessoAtti());
		lBean.setFlgRichAttiFabbricaVisuraSue(lDocumentoXmlOutBean.getFlgRichAttiFabbricaVisuraSue() == Flag.SETTED);
		lBean.setFlgRichModificheVisuraSue(lDocumentoXmlOutBean.getFlgRichModificheVisuraSue() == Flag.SETTED);
		if (lDocumentoXmlOutBean.getAttiRichiesti() != null){
			List<AttiRichiestiBean> attiRichiestiList = new ArrayList<AttiRichiestiBean>();
			for (AttiRichiestiXMLBean attoRichiestoXMLBean : lDocumentoXmlOutBean.getAttiRichiesti()) {
				AttiRichiestiBean attoRichiestoBean = new AttiRichiestiBean();			
				attoRichiestoBean.setTipoProtocollo(StringUtils.isNotBlank(attoRichiestoXMLBean.getTipoProtocollo()) ? attoRichiestoXMLBean.getTipoProtocollo() : "PG");				
				if("PG".equals(attoRichiestoBean.getTipoProtocollo())) {
					attoRichiestoBean.setNumProtocolloGenerale(attoRichiestoXMLBean.getNumeroProtocollo());
					attoRichiestoBean.setAnnoProtocolloGenerale(attoRichiestoXMLBean.getAnnoProtocollo());
				} else if ("PS".equals(attoRichiestoBean.getTipoProtocollo())) {
					attoRichiestoBean.setSiglaProtocolloSettore(attoRichiestoXMLBean.getRegProtocolloDiSettore());
					attoRichiestoBean.setNumProtocolloSettore(attoRichiestoXMLBean.getNumeroProtocollo());
					attoRichiestoBean.setSubProtocolloSettore(attoRichiestoXMLBean.getSubProtocolloDiSettore());
					attoRichiestoBean.setAnnoProtocolloSettore(attoRichiestoXMLBean.getAnnoProtocollo());
				} else if ("WF".equals(attoRichiestoBean.getTipoProtocollo())) {
					attoRichiestoBean.setNumPraticaWorkflow(attoRichiestoXMLBean.getNumeroProtocollo());
					attoRichiestoBean.setAnnoPraticaWorkflow(attoRichiestoXMLBean.getAnnoProtocollo());
				}
				attoRichiestoBean.setClassifica(attoRichiestoXMLBean.getClassifica());
				attoRichiestoBean.setStatoScansione(attoRichiestoXMLBean.getStatoScansione());
				attoRichiestoBean.setIdFolder(attoRichiestoXMLBean.getIdFolder());
				attoRichiestoBean.setStato(attoRichiestoXMLBean.getStato());
				if (StringUtils.isNotBlank(attoRichiestoXMLBean.getStato()) && attoRichiestoXMLBean.getStato().indexOf("presente") > -1){
					// Se lo stato è presente in cittadella salvo udc
					attoRichiestoBean.setUdc(attoRichiestoXMLBean.getUdc());
				} else if (StringUtils.isNotBlank(attoRichiestoXMLBean.getStato()) && attoRichiestoXMLBean.getStato().indexOf("prelevato") > -1){
					// Se lo stato è prelevato e ancora da restituire salvo udc, ufficio di prelievo, data di prelievo e responsabile di prelievo
					// Udc
					attoRichiestoBean.setUdc(attoRichiestoXMLBean.getUdc());
					// Ufficio di prelievo
					attoRichiestoBean.setDescrizioneUfficioPrelievo(attoRichiestoXMLBean.getDenUffPrelievo());
					attoRichiestoBean.setCodRapidoUfficioPrelievo(attoRichiestoXMLBean.getCodUffPrelievo());
					attoRichiestoBean.setOrganigrammaUfficioPrelievo(attoRichiestoXMLBean.getIdUoPrelievo() != null ? "UO" + attoRichiestoXMLBean.getIdUoPrelievo().replace(".","") : null);
					attoRichiestoBean.setIdUoUfficioPrelievo(attoRichiestoXMLBean.getIdUoPrelievo());
					// Data di prelievo
					attoRichiestoBean.setDataPrelievo(attoRichiestoXMLBean.getDataPrelievo());
					// Responsabile di prelievo
					attoRichiestoBean.setIdUserResponsabilePrelievo(attoRichiestoXMLBean.getIdUserRespPrelievo());
					attoRichiestoBean.setCognomeResponsabilePrelievo(attoRichiestoXMLBean.getCognomeRespPrelievo());
					attoRichiestoBean.setNomeResponsabilePrelievo(attoRichiestoXMLBean.getNomeRespPrelievo());
					attoRichiestoBean.setUsernameResponsabilePrelievo(attoRichiestoXMLBean.getUsernameRespPrelievo());
				}
				attoRichiestoBean.setNoteUffRichiedente(attoRichiestoXMLBean.getNoteUffRichiedente());
				attoRichiestoBean.setNoteCittadella(attoRichiestoXMLBean.getNoteCittadella());
				attoRichiestoBean.setStatoAttoDaSincronizzare(attoRichiestoXMLBean.getStatoAttoDaSincronizzare());
				
				attoRichiestoBean.setCompetenzaDiUrbanistica(attoRichiestoXMLBean.getCompetenzaDiUrbanistica());
				attoRichiestoBean.setCartaceoReperibile(attoRichiestoXMLBean.getCartaceoReperibile());
				attoRichiestoBean.setInArchivio(attoRichiestoXMLBean.getInArchivio());
				attoRichiestoBean.setDesInArchivio(attoRichiestoXMLBean.getDesInArchivio());				
				attoRichiestoBean.setFlgRichiestaVisioneCartaceo("1".equalsIgnoreCase(attoRichiestoXMLBean.getFlgRichiestaVisioneCartaceo()));
				
				attoRichiestoBean.setTipoFascicolo(attoRichiestoXMLBean.getTipoFascicolo());
				attoRichiestoBean.setDesTipoFascicolo(attoRichiestoXMLBean.getDesTipoFascicolo());				
				attoRichiestoBean.setAnnoProtEdiliziaPrivata(attoRichiestoXMLBean.getAnnoProtEdiliziaPrivata());
				attoRichiestoBean.setNumeroProtEdiliziaPrivata(attoRichiestoXMLBean.getNumeroProtEdiliziaPrivata());
				attoRichiestoBean.setAnnoWorkflow(attoRichiestoXMLBean.getAnnoWorkflow());
				attoRichiestoBean.setNumeroWorkflow(attoRichiestoXMLBean.getNumeroWorkflow());
				attoRichiestoBean.setNumeroDeposito(attoRichiestoXMLBean.getNumeroDeposito());
				
				attoRichiestoBean.setTipoComunicazione(attoRichiestoXMLBean.getTipoComunicazione());
				attoRichiestoBean.setDesTipoComunicazione(attoRichiestoXMLBean.getDesTipoComunicazione());
				attoRichiestoBean.setNoteSportello(attoRichiestoXMLBean.getNoteSportello());
				attoRichiestoBean.setVisureCollegate(attoRichiestoXMLBean.getVisureCollegate());
				
				attiRichiestiList.add(attoRichiestoBean);				
			}
			lBean.setListaAttiRichiesti(attiRichiestiList);
		}
		
		lBean.setFlgAltriAttiDaRicercareVisuraSue(lDocumentoXmlOutBean.getFlgAltriAttiDaRicercareVisuraSue() == Flag.SETTED);
		
		// Deleganti
		if (lDocumentoXmlOutBean.getDeleganti() != null && lDocumentoXmlOutBean.getDeleganti().size() > 0) {
			List<SoggEsternoProtBean> listaRichiedentiDelegati = new ArrayList<SoggEsternoProtBean>();
			for (SoggettoEsternoBean lDelegato : lDocumentoXmlOutBean.getDeleganti()) {
				SoggEsternoProtBean lDelegatoProtBean = new SoggEsternoProtBean();
				lDelegatoProtBean.setCodFiscale(lDelegato.getCodFiscale());
				lDelegatoProtBean.setCodRapido(lDelegato.getCodiceRapido());
				lDelegatoProtBean.setIdSoggetto(lDelegato.getIdSoggetto());
				lDelegatoProtBean.setTipoSoggetto(lDelegato.getTipoSoggetto());
				if ("AOOI".equals(lDelegato.getTipoSoggetto())) {
					lDelegatoProtBean.setAoomdgSoggetto(lDelegato.getIdSoggetto());
					lDelegatoProtBean.setDescrAoomdgSoggetto(lDelegato.getDenominazioneCognome());
				} else if (lDelegato.getFlgPersFisica() == Flag.SETTED) {
					lDelegatoProtBean.setDenominazione(lDelegato.getDenominazioneCognome() + " " + lDelegato.getNome());
					lDelegatoProtBean.setCognome(lDelegato.getDenominazioneCognome());
					lDelegatoProtBean.setNome(lDelegato.getNome());
				} else {
					lDelegatoProtBean.setDenominazione(lDelegato.getDenominazioneCognome());
				}
				lDelegatoProtBean.setStato(lDelegato.getStato());
				lDelegatoProtBean.setNomeStato(lDelegato.getNomeStato());
				if (StringUtils.isBlank(lDelegatoProtBean.getStato()) || _COD_ISTAT_ITALIA.equals(lDelegatoProtBean.getStato())
						|| _NOME_STATO_ITALIA.equals(lDelegatoProtBean.getStato())) {
					if (StringUtils.isNotBlank(lDelegato.getCodToponimo()) || StringUtils.isBlank(lDelegato.getToponimoIndirizzo())) {
						lDelegatoProtBean.setFlgFuoriComune(false);
						lDelegatoProtBean.setCodToponimo(lDelegato.getCodToponimo());
						lDelegatoProtBean.setIndirizzo(lDelegato.getToponimoIndirizzo());
						lDelegatoProtBean.setComune(getCodIstatComuneRif());
						lDelegatoProtBean.setNomeComune(getNomeComuneRif());
					} else {
						lDelegatoProtBean.setFlgFuoriComune(true);
						lDelegatoProtBean.setTipoToponimo(lDelegato.getTipoToponimo());
						lDelegatoProtBean.setToponimo(lDelegato.getToponimoIndirizzo());
						lDelegatoProtBean.setComune(lDelegato.getComune());
						lDelegatoProtBean.setNomeComune(lDelegato.getNomeComuneCitta());
					}
					lDelegatoProtBean.setFrazione(lDelegato.getFrazione());
					lDelegatoProtBean.setCap(lDelegato.getCap());
				} else {
					lDelegatoProtBean.setIndirizzo(lDelegato.getToponimoIndirizzo());
					lDelegatoProtBean.setCitta(lDelegato.getNomeComuneCitta());
				}
				lDelegatoProtBean.setCivico(lDelegato.getCivico());
				lDelegatoProtBean.setInterno(lDelegato.getInterno());
				lDelegatoProtBean.setZona(lDelegato.getZona());
				lDelegatoProtBean.setComplementoIndirizzo(lDelegato.getComplementoIndirizzo());
				lDelegatoProtBean.setAppendici(lDelegato.getAppendici());
				lDelegatoProtBean.setEmail(lDelegato.getEmailContribuente());
				lDelegatoProtBean.setTel(lDelegato.getTelefonoContribuente());
				
				listaRichiedentiDelegati.add(lDelegatoProtBean);
			}			
			lBean.setListaRichiedentiDelegati(listaRichiedentiDelegati);
		}		
		
		// Motivo e dettagli richiesta (maschera accesso atti)
		if (StringUtils.isNotBlank(lDocumentoXmlOutBean.getMotivoRichiestaAccessoAtti())){
			lBean.setMotivoRichiestaAccessoAtti(lDocumentoXmlOutBean.getMotivoRichiestaAccessoAtti());
		}
		if (StringUtils.isNotBlank(lDocumentoXmlOutBean.getDettagliRichiestaAccessoAtti())){
			lBean.setDettagliRichiestaAccessoAtti(lDocumentoXmlOutBean.getDettagliRichiestaAccessoAtti());
		}
		
		// Incaricati del ritiro
		if (lDocumentoXmlOutBean.getIncaricatiRitiro() != null && lDocumentoXmlOutBean.getIncaricatiRitiro().size() > 0) {
			for (SoggettoEsternoBean lDelegato : lDocumentoXmlOutBean.getIncaricatiRitiro()) {
				// Verifico se è incarito del ritiro per l'ufficio richiedente o per il richiedente esterno
				if (lDelegato.getTipoRichiedente() != null && "U".equalsIgnoreCase(lDelegato.getTipoRichiedente())){
					// Ufficio richiedente
					lBean.setIdIncaricatoPrelievoPerUffRichiedente(lDelegato.getIdSoggetto());
					lBean.setCodRapidoIncaricatoPrelievoPerUffRichiedente(lDelegato.getCodiceRapido());
					lBean.setCognomeIncaricatoPrelievoPerUffRichiedente(lDelegato.getDenominazioneCognome());
					lBean.setNomeIncaricatoPrelievoPerUffRichiedente(lDelegato.getNome());				
					lBean.setTelefonoIncaricatoPrelievoPerUffRichiedente(lDelegato.getTelefonoContribuente());
				}else if (lDelegato.getTipoRichiedente() != null && "E".equalsIgnoreCase(lDelegato.getTipoRichiedente())){
					// Ufficio richiedente
					lBean.setCognomeIncaricatoPrelievoPerRichEsterno(lDelegato.getDenominazioneCognome());
					lBean.setNomeIncaricatoPrelievoPerRichEsterno(lDelegato.getNome());	
					lBean.setCodiceFiscaleIncaricatoPrelievoPerRichEsterno(lDelegato.getCodFiscale());	
					lBean.setEmailIncaricatoPrelievoPerRichEsterno(lDelegato.getEmailContribuente());	
					lBean.setTelefonoIncaricatoPrelievoPerRichEsterno(lDelegato.getTelefonoContribuente());	
				}
			}			
		}
		
		// Dettagli appuntamento (maschera accesso atti)
		lBean.setDataAppuntamento(lDocumentoXmlOutBean.getDataAppuntamento());
		lBean.setOrarioAppuntamento(lDocumentoXmlOutBean.getOrarioAppuntamento());
		lBean.setProvenienzaAppuntamento(lDocumentoXmlOutBean.getProvenienzaAppuntamento());
		try{	
			lBean.setDataPrelievo(StringUtils.isNotBlank(lDocumentoXmlOutBean.getDataPrelievo()) ? new SimpleDateFormat(FMT_STD_DATA).parse(lDocumentoXmlOutBean.getDataPrelievo()) : null);
		}catch (Exception e) {
			logger.error("Errore nella conversione della data con valore: " + lDocumentoXmlOutBean.getDataPrelievo());
			lBean.setDataPrelievo(null);
		}
		lBean.setDataRestituzionePrelievo(lDocumentoXmlOutBean.getDataRestituzionePrelievo());
		lBean.setRestituzionePrelievoAttestataDa(lDocumentoXmlOutBean.getRestituzionePrelievoAttestataDa());
		lBean.setIdNodoDefaultRicercaAtti(lDocumentoXmlOutBean.getIdNodoDefaultRicercaAtti());
		
		//Estremi UD trasmesso in uscita con
		lBean.setIdUDTrasmessaInUscitaCon(lDocumentoXmlOutBean.getIdUDTrasmessaInUscitaCon());
		lBean.setEstremiUDTrasmessoInUscitaCon(lDocumentoXmlOutBean.getEstremiUDTrasmessaInUscitaCon());
		
		// Descrizione della categoria repertorio
		lBean.setRegNumSecondariaDesGruppo(lDocumentoXmlOutBean.getRegNumSecondariaDesGruppo());
		
		buildAbilitazioniRichAccessoAtti(lDocumentoXmlOutBean, lBean);
		
		// Smistamento atti
		lBean.setIdGruppoLiquidatori(lDocumentoXmlOutBean.getIdGruppoLiquidatori());
		lBean.setNomeGruppoLiquidatori(lDocumentoXmlOutBean.getNomeGruppoLiquidatori());
		lBean.setCodGruppoLiquidatori(lDocumentoXmlOutBean.getCodGruppoLiquidatori());
		lBean.setIdAssegnatarioProcesso(lDocumentoXmlOutBean.getIdAssegnatarioProcesso());
		lBean.setDesAssegnatarioProcesso(lDocumentoXmlOutBean.getDesAssegnatarioProcesso());
		lBean.setNriLivelliAssegnatarioProcesso(lDocumentoXmlOutBean.getNriLivelliAssegnatarioProcesso());
		lBean.setIdGruppoRagioneria(lDocumentoXmlOutBean.getIdGruppoRagioneria());
		lBean.setNomeGruppoRagioneria(lDocumentoXmlOutBean.getNomeGruppoRagioneria());
		lBean.setCodGruppoRagioneria(lDocumentoXmlOutBean.getCodGruppoRagioneria());
		
		// Condivisione atti
		lBean.setDestinatariInvioCCProcesso(lDocumentoXmlOutBean.getDestinatariInvioCCProcesso());
		
		// Lista perizie ADSP
		if (lDocumentoXmlOutBean.getListaPerizie() != null && lDocumentoXmlOutBean.getListaPerizie().size() > 0) {
			List<PeriziaBean> listaPerizie = new ArrayList<PeriziaBean>();
			for (PeriziaXmlBean periziaXmlBean : lDocumentoXmlOutBean.getListaPerizie()) {
				PeriziaBean periziaBean = new PeriziaBean();				
				periziaBean.setPerizia(periziaXmlBean.getPerizia());
				periziaBean.setDescrizione(periziaXmlBean.getDescrizione());
				periziaBean.setCodiceRapido(periziaXmlBean.getPerizia());				
				listaPerizie.add(periziaBean);
			}
			lBean.setListaPerizie(listaPerizie);
		}
		
		// Avvio iter WF di risposta
		lBean.setIdProcessTypeIterWFRisposta(lDocumentoXmlOutBean.getIdProcessTypeIterWFRisposta());
		lBean.setNomeProcessTypeIterWFRisposta(lDocumentoXmlOutBean.getNomeProcessTypeIterWFRisposta());
		lBean.setNomeTipoFlussoIterWFRisposta(lDocumentoXmlOutBean.getNomeTipoFlussoIterWFRisposta());
		lBean.setIdDocTypeIterWFRisposta(lDocumentoXmlOutBean.getIdDocTypeIterWFRisposta());
		lBean.setNomeDocTypeIterWFRisposta(lDocumentoXmlOutBean.getNomeDocTypeIterWFRisposta());
		lBean.setCodCategoriaNumIniIterWFRisposta(lDocumentoXmlOutBean.getCodCategoriaNumIniIterWFRisposta());
		lBean.setSiglaNumIniIterWFRisposta(lDocumentoXmlOutBean.getSiglaNumIniIterWFRisposta());
		
		// Pubblicazione e ri-pubblicazione
		lBean.setFlgPresenzaPubblicazioni(lDocumentoXmlOutBean.getFlgPresenzaPubblicazioni());
		lBean.setIdPubblicazione(lDocumentoXmlOutBean.getIdPubblicazione());
		lBean.setNroPubblicazione(lDocumentoXmlOutBean.getNroPubblicazione());
		lBean.setDataPubblicazione(lDocumentoXmlOutBean.getDataPubblicazione());
		lBean.setDataDalPubblicazione(lDocumentoXmlOutBean.getDataDalPubblicazione());
		lBean.setDataAlPubblicazione(lDocumentoXmlOutBean.getDataAlPubblicazione());
		lBean.setRichiestaDaPubblicazione(lDocumentoXmlOutBean.getRichiestaDaPubblicazione());
		lBean.setStatoPubblicazione(lDocumentoXmlOutBean.getStatoPubblicazione());
		lBean.setFormaPubblicazione(lDocumentoXmlOutBean.getFormaPubblicazione());
		lBean.setRettificataDaPubblicazione(lDocumentoXmlOutBean.getRettificataDaPubblicazione());
		lBean.setMotivoRettificaPubblicazione(lDocumentoXmlOutBean.getMotivoRettificaPubblicazione());
		lBean.setMotivoAnnullamentoPubblicazione(lDocumentoXmlOutBean.getMotivoAnnullamentoPubblicazione());
		lBean.setProroghePubblicazione(lDocumentoXmlOutBean.getProroghePubblicazione());		
		lBean.setIdRipubblicazione(lDocumentoXmlOutBean.getIdRipubblicazione());
		lBean.setNroRipubblicazione(lDocumentoXmlOutBean.getNroRipubblicazione());
		lBean.setDataRipubblicazione(lDocumentoXmlOutBean.getDataRipubblicazione());
		lBean.setDataDalRipubblicazione(lDocumentoXmlOutBean.getDataDalRipubblicazione());
		lBean.setDataAlRipubblicazione(lDocumentoXmlOutBean.getDataAlRipubblicazione());
		lBean.setRichiestaDaRipubblicazione(lDocumentoXmlOutBean.getRichiestaDaRipubblicazione());
		lBean.setStatoRipubblicazione(lDocumentoXmlOutBean.getStatoRipubblicazione());
		lBean.setFormaRipubblicazione(lDocumentoXmlOutBean.getFormaRipubblicazione());
		lBean.setRettificataDaRipubblicazione(lDocumentoXmlOutBean.getRettificataDaRipubblicazione());	
		lBean.setMotivoRettificaRipubblicazione(lDocumentoXmlOutBean.getMotivoRettificaRipubblicazione());
		lBean.setMotivoAnnullamentoRipubblicazione(lDocumentoXmlOutBean.getMotivoAnnullamentoRipubblicazione());
		lBean.setProrogheRipubblicazione(lDocumentoXmlOutBean.getProrogheRipubblicazione());	
		
		// Richiesta accesso civico
		lBean.setFlgPresentiControinteressati(lDocumentoXmlOutBean.getFlgPresentiControinteressati());
		List<ControinteressatoBean> listaControinteressati = new ArrayList<ControinteressatoBean>();
		if (lDocumentoXmlOutBean.getListaControinteressati() != null && lDocumentoXmlOutBean.getListaControinteressati().size() > 0) {
			for (ControinteressatiXmlBean item : lDocumentoXmlOutBean.getListaControinteressati()) {
				ControinteressatoBean controinteressatoBean = new ControinteressatoBean();				
				controinteressatoBean.setTipoSoggetto(item.getTipoSoggetto());
				controinteressatoBean.setDenominazione(item.getDenominazione());
				controinteressatoBean.setCodFiscale(item.getCodFiscale());
				controinteressatoBean.setpIva(item.getpIva());
				controinteressatoBean.setNote(item.getNote());
				listaControinteressati.add(controinteressatoBean);
			}
		}
		lBean.setListaControinteressati(listaControinteressati);
		
		lBean.setInfoXAllegFlgPrimarioAtto(lDocumentoXmlOutBean.getInfoXAllegFlgPrimarioAtto() == Flag.SETTED);
		lBean.setInfoXAllegShowParteIntegrante(lDocumentoXmlOutBean.getInfoXAllegShowParteIntegrante() == Flag.SETTED);
		lBean.setInfoXAllegDefaultParteIntegrante(lDocumentoXmlOutBean.getInfoXAllegDefaultParteIntegrante() == Flag.SETTED);
		lBean.setInfoXAllegShowEscludiPubblicazione(lDocumentoXmlOutBean.getInfoXAllegShowEscludiPubblicazione() == Flag.SETTED);
		lBean.setInfoXAllegDefaultEscludiPubblicazione(lDocumentoXmlOutBean.getInfoXAllegDefaultEscludiPubblicazione() == Flag.SETTED);
		lBean.setInfoXAllegShowSeparato(lDocumentoXmlOutBean.getInfoXAllegShowSeparato() == Flag.SETTED);
		lBean.setInfoXAllegDefaultSeparato(lDocumentoXmlOutBean.getInfoXAllegDefaultSeparato() == Flag.SETTED);
		lBean.setInfoXAllegShowVersOmissis(lDocumentoXmlOutBean.getInfoXAllegShowVersOmissis() == Flag.SETTED);
		
		lBean.setFlgAttivaCtrlAllegatiXImportUnitaDoc(lDocumentoXmlOutBean.getFlgAttivaCtrlAllegatiXImportUnitaDoc() == Flag.SETTED);
	}
	
	protected void buildAbilitazioni(DocumentoXmlOutBean lDocumentoXmlOutBean, ProtocollazioneBean lBean) {
		lBean.setAbilRevocaAtto(lDocumentoXmlOutBean.getAbilRevocaAtto() != null && lDocumentoXmlOutBean.getAbilRevocaAtto());
		lBean.setAbilSmistaPropAtto(lDocumentoXmlOutBean.getAbilSmistaPropAtto() != null && lDocumentoXmlOutBean.getAbilSmistaPropAtto());
		lBean.setAbilPresaInCaricoPropAtto(lDocumentoXmlOutBean.getAbilPresaInCaricoPropAtto() != null && lDocumentoXmlOutBean.getAbilPresaInCaricoPropAtto());
		lBean.setAbilSottoscrizioneCons(lDocumentoXmlOutBean.getAbilSottoscrizioneCons() != null && lDocumentoXmlOutBean.getAbilSottoscrizioneCons());
		lBean.setAbilRimuoviSottoscrizioneCons(lDocumentoXmlOutBean.getAbilRimuoviSottoscrizioneCons() != null && lDocumentoXmlOutBean.getAbilRimuoviSottoscrizioneCons());
		lBean.setAbilPresentazionePropAtto(lDocumentoXmlOutBean.getAbilPresentazionePropAtto() != null && lDocumentoXmlOutBean.getAbilPresentazionePropAtto());		
		lBean.setAbilRitiroPropAtto(lDocumentoXmlOutBean.getAbilRitiroPropAtto() != null && lDocumentoXmlOutBean.getAbilRitiroPropAtto());		
		lBean.setAbilAvviaEmendamento(lDocumentoXmlOutBean.getAbilAvviaEmendamento() != null && lDocumentoXmlOutBean.getAbilAvviaEmendamento());
		lBean.setAbilAnnullaPropostaAtto(lDocumentoXmlOutBean.getAbilAnnullaPropostaAtto() != null && lDocumentoXmlOutBean.getAbilAnnullaPropostaAtto());
		lBean.setAbilRilascioVisto(lDocumentoXmlOutBean.getAbilRilascioVisto() != null && lDocumentoXmlOutBean.getAbilRilascioVisto());
		lBean.setAbilRifiutoVisto(lDocumentoXmlOutBean.getAbilRifiutoVisto() != null && lDocumentoXmlOutBean.getAbilRifiutoVisto());
		lBean.setAbilProtocollazioneEntrata(lDocumentoXmlOutBean.getAbilProtocollazioneEntrata() != null && lDocumentoXmlOutBean.getAbilProtocollazioneEntrata());
		lBean.setAbilProtocollazioneUscita(lDocumentoXmlOutBean.getAbilProtocollazioneUscita() != null && lDocumentoXmlOutBean.getAbilProtocollazioneUscita());
		lBean.setAbilProtocollazioneInterna(lDocumentoXmlOutBean.getAbilProtocollazioneInterna() != null && lDocumentoXmlOutBean.getAbilProtocollazioneInterna());
		lBean.setAbilAssegnazioneSmistamento(lDocumentoXmlOutBean.getAbilAssegnazioneSmistamento() != null && lDocumentoXmlOutBean.getAbilAssegnazioneSmistamento());
		lBean.setAbilModAssInviiCC(lDocumentoXmlOutBean.getAbilModAssInviiCC() != null && lDocumentoXmlOutBean.getAbilModAssInviiCC());
		lBean.setAbilSetVisionato(lDocumentoXmlOutBean.getAbilSetVisionato() != null && lDocumentoXmlOutBean.getAbilSetVisionato());
		lBean.setAbilSmista(lDocumentoXmlOutBean.getAbilSmista() != null && lDocumentoXmlOutBean.getAbilSmista());
		lBean.setAbilSmistaCC(lDocumentoXmlOutBean.getAbilSmistaCC() != null && lDocumentoXmlOutBean.getAbilSmistaCC());	
		lBean.setAbilCondivisione(lDocumentoXmlOutBean.getAbilCondivisione() != null && lDocumentoXmlOutBean.getAbilCondivisione());
		lBean.setAbilClassificazioneFascicolazione(lDocumentoXmlOutBean.getAbilClassificazioneFascicolazione() != null && lDocumentoXmlOutBean.getAbilClassificazioneFascicolazione());
		lBean.setAbilOrganizza(lDocumentoXmlOutBean.getAbilOrganizza() != null && lDocumentoXmlOutBean.getAbilOrganizza());
		lBean.setAbilModificaDatiRegistrazione(lDocumentoXmlOutBean.getAbilModificaDatiRegistrazione() != null && lDocumentoXmlOutBean.getAbilModificaDatiRegistrazione());
		lBean.setAbilModificaDati(lDocumentoXmlOutBean.getAbilModificaDati() != null && lDocumentoXmlOutBean.getAbilModificaDati());
		lBean.setAbilAvvioIterWF(lDocumentoXmlOutBean.getAbilAvvioIterWF() != null && lDocumentoXmlOutBean.getAbilAvvioIterWF());
		lBean.setAbilAggiuntaFile(lDocumentoXmlOutBean.getAbilAggiuntaFile() != null && lDocumentoXmlOutBean.getAbilAggiuntaFile());
		lBean.setAbilRegAccessoCivico(lDocumentoXmlOutBean.getAbilRegAccessoCivico() != null && lDocumentoXmlOutBean.getAbilRegAccessoCivico());
		lBean.setAbilInvioPEC(lDocumentoXmlOutBean.getAbilInvioPEC() != null && lDocumentoXmlOutBean.getAbilInvioPEC());
		lBean.setFlgInvioPECMulti(lDocumentoXmlOutBean.getFlgInvioPECMulti() != null && lDocumentoXmlOutBean.getFlgInvioPECMulti());
		lBean.setAbilInvioPEO(lDocumentoXmlOutBean.getAbilInvioPEO() != null && lDocumentoXmlOutBean.getAbilInvioPEO());
		lBean.setAbilRichAnnullamentoReg(lDocumentoXmlOutBean.getAbilRichAnnullamentoReg() != null && lDocumentoXmlOutBean.getAbilRichAnnullamentoReg());
		lBean.setAbilModificaRichAnnullamentoReg(lDocumentoXmlOutBean.getAbilModificaRichAnnullamentoReg() != null && lDocumentoXmlOutBean.getAbilModificaRichAnnullamentoReg());
		lBean.setAbilEliminaRichAnnullamentoReg(lDocumentoXmlOutBean.getAbilEliminaRichAnnullamentoReg() != null && lDocumentoXmlOutBean.getAbilEliminaRichAnnullamentoReg());
		lBean.setAbilAnnullamentoReg(lDocumentoXmlOutBean.getAbilAnnullamentoReg() != null && lDocumentoXmlOutBean.getAbilAnnullamentoReg());
		lBean.setAbilPresaInCarico(lDocumentoXmlOutBean.getAbilPresaInCarico() != null && lDocumentoXmlOutBean.getAbilPresaInCarico());
		lBean.setAbilRestituzione(lDocumentoXmlOutBean.getAbilRestituzione() != null && lDocumentoXmlOutBean.getAbilRestituzione());
		lBean.setAbilInvioConferma(lDocumentoXmlOutBean.getAbilInvioConferma() != null && lDocumentoXmlOutBean.getAbilInvioConferma());
		lBean.setAbilInvioAggiornamento(lDocumentoXmlOutBean.getAbilInvioAggiornamento() != null && lDocumentoXmlOutBean.getAbilInvioAggiornamento());
		lBean.setAbilInvioAnnullamento(lDocumentoXmlOutBean.getAbilInvioAnnullamento() != null && lDocumentoXmlOutBean.getAbilInvioAnnullamento());
		lBean.setAbilArchiviazione(lDocumentoXmlOutBean.getAbilArchiviazione() != null && lDocumentoXmlOutBean.getAbilArchiviazione());
		lBean.setAbilOsservazioniNotifiche(lDocumentoXmlOutBean.getAbilOsservazioniNotifiche() != null && lDocumentoXmlOutBean.getAbilOsservazioniNotifiche());
		lBean.setAbilFirma(lDocumentoXmlOutBean.getAbilFirma() != null && lDocumentoXmlOutBean.getAbilFirma());
		lBean.setAbilFirmaProtocolla(lDocumentoXmlOutBean.getAbilFirmaProtocolla() != null && lDocumentoXmlOutBean.getAbilFirmaProtocolla());
		lBean.setAbilVistoElettronico(lDocumentoXmlOutBean.getAbilVistoElettronico() != null && lDocumentoXmlOutBean.getAbilVistoElettronico());
		lBean.setAbilStampaCopertina(lDocumentoXmlOutBean.getAbilStampaCopertina() != null && lDocumentoXmlOutBean.getAbilStampaCopertina());
		lBean.setAbilStampaRicevuta(lDocumentoXmlOutBean.getAbilStampaRicevuta() != null && lDocumentoXmlOutBean.getAbilStampaRicevuta());
		lBean.setAbilRispondi(lDocumentoXmlOutBean.getAbilRispondi() != null && lDocumentoXmlOutBean.getAbilRispondi());
		lBean.setAbilStampaEtichetta(lDocumentoXmlOutBean.getAbilStampaEtichetta() != null && lDocumentoXmlOutBean.getAbilStampaEtichetta());
		lBean.setAbilNuovoComeCopia(lDocumentoXmlOutBean.getAbilNuovoComeCopia() != null && lDocumentoXmlOutBean.getAbilNuovoComeCopia());
		lBean.setAbilModificaTipologia(lDocumentoXmlOutBean.getAbilModificaTipologia() != null && lDocumentoXmlOutBean.getAbilModificaTipologia());
		lBean.setAbilModificaCodAttoCMTO(lDocumentoXmlOutBean.getAbilModificaCodAttoCMTO() != null && lDocumentoXmlOutBean.getAbilModificaCodAttoCMTO());
		lBean.setAbilRispondiUscita(lDocumentoXmlOutBean.getAbilRispondiUscita() != null && lDocumentoXmlOutBean.getAbilRispondiUscita());
		lBean.setAbilImpostaStatoAlVistoRegCont(lDocumentoXmlOutBean.getAbilImpostaStatoAlVistoRegCont() != null && lDocumentoXmlOutBean.getAbilImpostaStatoAlVistoRegCont());
		lBean.setAbilTogliaDaLibroFirmaVistoRegCont(lDocumentoXmlOutBean.getAbilTogliaDaLibroFirmaVistoRegCont() != null && lDocumentoXmlOutBean.getAbilTogliaDaLibroFirmaVistoRegCont());
		lBean.setAbilInviaALibroFirmaVistoRegCont(lDocumentoXmlOutBean.getAbilInviaALibroFirmaVistoRegCont() != null && lDocumentoXmlOutBean.getAbilInviaALibroFirmaVistoRegCont());
		lBean.setAbilInvioEmailRicevuta(lDocumentoXmlOutBean.getAbilInvioEmailRicevuta() != null && lDocumentoXmlOutBean.getAbilInvioEmailRicevuta());
		lBean.setAbilProrogaPubblicazione(lDocumentoXmlOutBean.getAbilProrogaPubblicazione() != null && lDocumentoXmlOutBean.getAbilProrogaPubblicazione());
		lBean.setAbilAnnullamentoPubblicazione(lDocumentoXmlOutBean.getAbilAnnullamentoPubblicazione() != null && lDocumentoXmlOutBean.getAbilAnnullamentoPubblicazione());
		lBean.setAbilRettificaPubblicazione(lDocumentoXmlOutBean.getAbilRettificaPubblicazione() != null && lDocumentoXmlOutBean.getAbilRettificaPubblicazione());
		lBean.setAbilModificaDatiExtraIter(lDocumentoXmlOutBean.getAbilModificaDatiExtraIter() != null && lDocumentoXmlOutBean.getAbilModificaDatiExtraIter());
		lBean.setAbilModificaOpereAtto(lDocumentoXmlOutBean.getAbilModificaOpereAtto() != null && lDocumentoXmlOutBean.getAbilModificaOpereAtto());
		lBean.setAbilModificaDatiPubblAtto(lDocumentoXmlOutBean.getAbilModificaDatiPubblAtto() != null && lDocumentoXmlOutBean.getAbilModificaDatiPubblAtto());
		lBean.setAbilModificaSostDirRegTecnicaPropAtto(lDocumentoXmlOutBean.getAbilModificaSostDirRegTecnicaPropAtto() != null && lDocumentoXmlOutBean.getAbilModificaSostDirRegTecnicaPropAtto());
		lBean.setAbilModificaTermineSottoscrizionePropAtto(lDocumentoXmlOutBean.getAbilModificaTermineSottoscrizionePropAtto() != null && lDocumentoXmlOutBean.getAbilModificaTermineSottoscrizionePropAtto());
		lBean.setAbilPubblicazioneTraspAmm(lDocumentoXmlOutBean.getAbilPubblicazioneTraspAmm() != null && lDocumentoXmlOutBean.getAbilPubblicazioneTraspAmm());
		lBean.setAbilGestioneCollegamentiUD(lDocumentoXmlOutBean.getAbilGestioneCollegamentiUD() != null ? lDocumentoXmlOutBean.getAbilGestioneCollegamentiUD() : false);
	}
	
	protected void buildAbilitazioniRichAccessoAtti(DocumentoXmlOutBean lDocumentoXmlOutBean, ProtocollazioneBean lBean) {

		lBean.setAbilVisualizzaDettStdProt(lDocumentoXmlOutBean.getAbilRichAccessoAttiVisualizzaDettStdProt() != null && lDocumentoXmlOutBean.getAbilRichAccessoAttiVisualizzaDettStdProt());		
		lBean.setAbilInvioInApprovazione(lDocumentoXmlOutBean.getAbilRichAccessoAttiInvioInApprovazione() != null && lDocumentoXmlOutBean.getAbilRichAccessoAttiInvioInApprovazione());		
		lBean.setAbilApprovazione(lDocumentoXmlOutBean.getAbilRichAccessoAttiApprovazione() != null && lDocumentoXmlOutBean.getAbilRichAccessoAttiApprovazione());		
		lBean.setAbilInvioEsitoVerificaArchivio(lDocumentoXmlOutBean.getAbilRichAccessoAttiInvioEsitoVerificaArchivio() != null && lDocumentoXmlOutBean.getAbilRichAccessoAttiInvioEsitoVerificaArchivio());			
		lBean.setAbilAbilitaAppuntamentoDaAgenda(lDocumentoXmlOutBean.getAbilRichAccessoAttiAbilitaAppuntamentoDaAgenda() != null && lDocumentoXmlOutBean.getAbilRichAccessoAttiAbilitaAppuntamentoDaAgenda());		
		lBean.setAbilSetAppuntamento(lDocumentoXmlOutBean.getAbilRichAccessoAttiSetAppuntamento() != null && lDocumentoXmlOutBean.getAbilRichAccessoAttiSetAppuntamento());		
		lBean.setAbilAnnullamentoAppuntamento(lDocumentoXmlOutBean.getAbilRichAccessoAttiAnnullamentoAppuntamento() != null && lDocumentoXmlOutBean.getAbilRichAccessoAttiAnnullamentoAppuntamento());		
		lBean.setAbilRegistraPrelievo(lDocumentoXmlOutBean.getAbilRichAccessoAttiRegistraPrelievo() != null && lDocumentoXmlOutBean.getAbilRichAccessoAttiRegistraPrelievo());		
		lBean.setAbilRegistraRestituzione(lDocumentoXmlOutBean.getAbilRichAccessoAttiRegistraRestituzione() != null && lDocumentoXmlOutBean.getAbilRichAccessoAttiRegistraRestituzione());		
		lBean.setAbilAnnullamento(lDocumentoXmlOutBean.getAbilRichAccessoAttiAnnullamento() != null && lDocumentoXmlOutBean.getAbilRichAccessoAttiAnnullamento());
		lBean.setAbilStampaFoglioPrelievo(lDocumentoXmlOutBean.getAbilRichAccessoAttiStampaFoglioPrelievo() != null && lDocumentoXmlOutBean.getAbilRichAccessoAttiStampaFoglioPrelievo());		
		lBean.setAbilRichAccessoAttiChiusura(lDocumentoXmlOutBean.getAbilRichAccessoAttiChiusura() != null && lDocumentoXmlOutBean.getAbilRichAccessoAttiChiusura());
		lBean.setAbilRichAccessoAttiRiapertura(lDocumentoXmlOutBean.getAbilRichAccessoAttiRiapertura() != null && lDocumentoXmlOutBean.getAbilRichAccessoAttiRiapertura());
		lBean.setAbilRichAccessoAttiRipristino(lDocumentoXmlOutBean.getAbilRichAccessoAttiRipristino() != null && lDocumentoXmlOutBean.getAbilRichAccessoAttiRipristino());	
	}

	public void recuperaFilesFromDoc(DocumentoXmlOutBean lDocumentoXmlOutBean, ProtocollazioneBean lBean, Map<String, Object> lFiles) throws Exception {
		recuperaPrimario(lDocumentoXmlOutBean, lBean, lFiles);
		// Allegati
		recuperaAllegati(lDocumentoXmlOutBean, lBean, lFiles);
		// Documenti istruttoria
		recuperaDocumentiProcFolder(lDocumentoXmlOutBean, lBean);
		// Documentazione completa per atti
		recuperaFileCompletiXAtti(lDocumentoXmlOutBean, lBean);
	}

	protected void recuperaAllegati(DocumentoXmlOutBean lDocumentoXmlOutBean, ProtocollazioneBean lBean, Map<String, Object> lFiles) throws Exception {
		if (lDocumentoXmlOutBean.getAllegati() != null && lDocumentoXmlOutBean.getAllegati().size() > 0) {
			List<AllegatoProtocolloBean> lListFilePrimarioVerPubbl = new ArrayList<AllegatoProtocolloBean>();
			List<AllegatoProtocolloBean> lListAllegati = new ArrayList<AllegatoProtocolloBean>();
			for (AllegatiOutBean allegato : lDocumentoXmlOutBean.getAllegati()) {
				String idTipoDocAllVerPubbl = ParametriDBUtil.getParametroDB(session, "ID_TIPO_DOC_ALL_VER_PUBBL");
				String attivaTimbroTipologia = lDocumentoXmlOutBean.getAttivaTimbroTipologia();
				if (allegato.getIdDocType() != null && idTipoDocAllVerPubbl != null && !"".equals(idTipoDocAllVerPubbl)
						&& allegato.getIdDocType().equals(idTipoDocAllVerPubbl)) {
					recuperaAllegato(lListFilePrimarioVerPubbl, allegato, lFiles, attivaTimbroTipologia);
				} else {
					recuperaAllegato(lListAllegati, allegato, lFiles, attivaTimbroTipologia);
				}
			}
			lBean.setListaFilePrimarioVerPubbl(lListFilePrimarioVerPubbl);
			lBean.setListaAllegati(lListAllegati);
		} else {
			if (lFiles != null && lFiles.size() > 0) {
				List<AllegatoProtocolloBean> lListAllegati = new ArrayList<AllegatoProtocolloBean>();
				Iterator<String> ite = lFiles.keySet().iterator();
				while (ite.hasNext()) {
					String key = ite.next();
					String nomeFile[] = key.split(";");
					String uriFileAllegato = (String) lFiles.get(key);
					//Nel caso in cui non mi arrivano allegati dalla store devo aggiungere tutti i file tranne il primario, il testo della mail e la segnatura
					if(!uriFileAllegato.equals(lBean.getUriFilePrimario()) && !nomeFile[0].equals("__TestoEmail.txt") && !nomeFile[0].equalsIgnoreCase("segnatura.xml")) {							
						File fileTemp = StorageImplementation.getStorage().extractFile(uriFileAllegato);	
						MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
						lMimeTypeFirmaBean = new InfoFileUtility().getInfoFromFile(fileTemp.toURI().toString(), nomeFile[0], false, null);
						if (lMimeTypeFirmaBean == null || StringUtils.isBlank(lMimeTypeFirmaBean.getImpronta())) {
							throw new Exception("Si è verificato un errore durante il controllo del file allegato " + nomeFile[0]);
						}	
						PostaElettronicaRetriewAttachBean lPostaElettronicaRetriewAttachBean = new PostaElettronicaRetriewAttachBean();
						lPostaElettronicaRetriewAttachBean.setMimeTypeFirmaBean(lMimeTypeFirmaBean);
						lPostaElettronicaRetriewAttachBean.setUriFile(uriFileAllegato);	
						recuperaAllegatoFromFOP(lListAllegati, lFiles, lPostaElettronicaRetriewAttachBean);
					}
				}
				lBean.setListaAllegati(lListAllegati);
			}
		}
	}
	
	protected void recuperaDocumentiProcFolder(DocumentoXmlOutBean lDocumentoXmlOutBean, ProtocollazioneBean lBean) throws Exception {
		if (lDocumentoXmlOutBean.getDocumentiProcFolder() != null && lDocumentoXmlOutBean.getDocumentiProcFolder().size() > 0) {
			List<AllegatoProtocolloBean> lListDocumentiProcFolder = new ArrayList<AllegatoProtocolloBean>();
			for (AllegatiOutBean docProcFolder : lDocumentoXmlOutBean.getDocumentiProcFolder()) {
				String attivaTimbroTipologia = lDocumentoXmlOutBean.getAttivaTimbroTipologia();
				recuperaAllegato(lListDocumentiProcFolder, docProcFolder, null, attivaTimbroTipologia);				
			}
			lBean.setListaDocProcFolder(lListDocumentiProcFolder);
		}
	}
	
	protected void recuperaFileCompletiXAtti(DocumentoXmlOutBean lDocumentoXmlOutBean, ProtocollazioneBean lBean) throws Exception {
		if (lDocumentoXmlOutBean.getFileCompletiXAtti() != null && lDocumentoXmlOutBean.getFileCompletiXAtti().size() > 0) {
			lBean.setListaFileCompletiXAtti(lDocumentoXmlOutBean.getFileCompletiXAtti());
		} /*else {
			List<FileCompletiXAttiBean> lListFileCompletiXAtti = new ArrayList<FileCompletiXAttiBean>();
			if (lDocumentoXmlOutBean.getFilePrimario() != null) {	
				if (StringUtils.isNotBlank(lDocumentoXmlOutBean.getFilePrimario().getUri())) {
					FileCompletiXAttiBean lFileCompletiXAttiBean = new FileCompletiXAttiBean();
					lFileCompletiXAttiBean.setUri(lDocumentoXmlOutBean.getFilePrimario().getUri());
					lFileCompletiXAttiBean.setNomeFile("File primario - " + lDocumentoXmlOutBean.getFilePrimario().getDisplayFilename());
					lFileCompletiXAttiBean.setDimensione(lDocumentoXmlOutBean.getFilePrimario().getDimensione());
					lListFileCompletiXAtti.add(lFileCompletiXAttiBean);
				}
				if (StringUtils.isNotBlank(lDocumentoXmlOutBean.getFilePrimario().getUriOmissis())) {
					FileCompletiXAttiBean lFileCompletiXAttiBean = new FileCompletiXAttiBean();
					lFileCompletiXAttiBean.setUri(lDocumentoXmlOutBean.getFilePrimario().getUriOmissis());
					lFileCompletiXAttiBean.setNomeFile("File primario (vers. con omissis) - " + lDocumentoXmlOutBean.getFilePrimario().getDisplayFilenameOmissis());
					lFileCompletiXAttiBean.setDimensione(lDocumentoXmlOutBean.getFilePrimario().getDimensioneOmissis());
					lListFileCompletiXAtti.add(lFileCompletiXAttiBean);	
				}
			}
			if (lDocumentoXmlOutBean.getAllegati() != null && lDocumentoXmlOutBean.getAllegati().size() > 0) {
				int n = 1;
				for (AllegatiOutBean allegato : lDocumentoXmlOutBean.getAllegati()) {
					if (StringUtils.isNotBlank(allegato.getUri())) {
						FileCompletiXAttiBean lFileCompletiXAttiBean = new FileCompletiXAttiBean();
						lFileCompletiXAttiBean.setUri(allegato.getUri());
						lFileCompletiXAttiBean.setNomeFile("Allegato N° "  + n + " - " + allegato.getDisplayFileName());
						lFileCompletiXAttiBean.setDimensione(allegato.getDimensione());
						lListFileCompletiXAtti.add(lFileCompletiXAttiBean);
					}
					if (StringUtils.isNotBlank(allegato.getUriOmissis())) {
						FileCompletiXAttiBean lFileCompletiXAttiBean = new FileCompletiXAttiBean();
						lFileCompletiXAttiBean.setUri(allegato.getUriOmissis());
						lFileCompletiXAttiBean.setNomeFile("Allegato N° "  + n + " (vers. con omissis) - " + allegato.getDisplayFileNameOmissis());
						lFileCompletiXAttiBean.setDimensione(allegato.getDimensioneOmissis());
						lListFileCompletiXAtti.add(lFileCompletiXAttiBean);	
					}				
					n++;
				}				
			}
			lBean.setListaFileCompletiXAtti(lListFileCompletiXAtti);
		}*/
	}

	protected void recuperaAllegato(List<AllegatoProtocolloBean> lListAllegati, AllegatiOutBean allegato,
			Map<String, Object> lFiles,String attivaTimbroTipologia) throws Exception {
		
		AllegatoProtocolloBean lAllegatoProtocolloBean = new AllegatoProtocolloBean();
		BigDecimal idDocAllegato = StringUtils.isNotBlank(allegato.getIdDoc()) ? new BigDecimal(allegato.getIdDoc()) : null;
		lAllegatoProtocolloBean.setIdUdAppartenenza(allegato.getIdUd());
		lAllegatoProtocolloBean.setIdUdAllegato(allegato.getIdUdAllegato());
		lAllegatoProtocolloBean.setIsUdSenzaFileImportata(allegato.getFlgUdSenzaFileImportata() == Flag.SETTED);		
		lAllegatoProtocolloBean.setFlgTipoProvXProt(allegato.getFlgTipoProvXProt());
		lAllegatoProtocolloBean.setEstremiProtUd(allegato.getEstremiProtUd());
		lAllegatoProtocolloBean.setNroProtocollo(allegato.getNroProtocollo());
		lAllegatoProtocolloBean.setAnnoProtocollo(allegato.getAnnoProtocollo());
		lAllegatoProtocolloBean.setDataProtocollo(allegato.getDataProtocollo());
		lAllegatoProtocolloBean.setIdDocAllegato(idDocAllegato);
		if(StringUtils.isNotBlank(allegato.getEstremiProtUd())) {
			lAllegatoProtocolloBean.setDescrizioneFileAllegato(allegato.getEstremiProtUd() + (StringUtils.isNotBlank(allegato.getDescrizioneOggetto()) ? (" - " + allegato.getDescrizioneOggetto()) : ""));
		} else {
			lAllegatoProtocolloBean.setDescrizioneFileAllegato(allegato.getDescrizioneOggetto());
		}
		lAllegatoProtocolloBean.setIdTipoFileAllegatoSalvato(allegato.getIdDocType());
		lAllegatoProtocolloBean.setIdTipoFileAllegato(allegato.getIdDocType());
		lAllegatoProtocolloBean.setDescTipoFileAllegato(allegato.getNomeDocType());
		lAllegatoProtocolloBean.setListaTipiFileAllegato(allegato.getIdDocType());
		lAllegatoProtocolloBean.setFlgRichiestaFirmaDigitale(allegato.getFlgRichiestaFirmaDigitale());
		lAllegatoProtocolloBean.setNomeFileAllegato(allegato.getDisplayFileName());
		lAllegatoProtocolloBean.setTsInsLastVerFileAllegato(allegato.getTsInsLastVerFile());
		lAllegatoProtocolloBean.setRemoteUri(false);
		lAllegatoProtocolloBean.setFlgProvEsterna(allegato.getFlgProvEsterna() == Flag.SETTED);
		lAllegatoProtocolloBean.setFlgParteDispositivo(allegato.getFlgParteDispositivo() == Flag.SETTED);
		lAllegatoProtocolloBean.setFlgParteDispositivoSalvato(allegato.getFlgParteDispositivo() == Flag.SETTED);
		lAllegatoProtocolloBean.setFlgParere(allegato.getFlgParere() == Flag.SETTED);
		lAllegatoProtocolloBean.setFlgDatiProtettiTipo1(allegato.getFlgDatiProtettiTipo1() == Flag.SETTED);
		lAllegatoProtocolloBean.setFlgDatiProtettiTipo2(allegato.getFlgDatiProtettiTipo2() == Flag.SETTED);
		lAllegatoProtocolloBean.setFlgDatiProtettiTipo3(allegato.getFlgDatiProtettiTipo3() == Flag.SETTED);
		lAllegatoProtocolloBean.setFlgDatiProtettiTipo4(allegato.getFlgDatiProtettiTipo4() == Flag.SETTED);		
		lAllegatoProtocolloBean.setFlgNoPubblAllegato(allegato.getFlgNoPubbl() == Flag.SETTED);
		lAllegatoProtocolloBean.setFlgPubblicaSeparato(allegato.getFlgPubblicaSeparato() == Flag.SETTED);
		lAllegatoProtocolloBean.setFlgPaginaFileUnione(StringUtils.isNotBlank(allegato.getNroPagFileUnione()) ? "n.ro" : "ultima");		
		lAllegatoProtocolloBean.setNroPagFileUnione(allegato.getNroPagFileUnione());
		lAllegatoProtocolloBean.setNroPagFileUnioneOmissis(allegato.getNroPagFileUnioneOmissis());
		lAllegatoProtocolloBean.setFlgDatiSensibili(allegato.getFlgDatiSensibili() == Flag.SETTED);
		// Dettaglio doc. contratti con barcode
		lAllegatoProtocolloBean.setTipoBarcodeContratto(allegato.getTipoBarcode());
		lAllegatoProtocolloBean.setBarcodeContratto(allegato.getBarcode());
		lAllegatoProtocolloBean.setEnergiaGasContratto(allegato.getEnergiaGas());
		lAllegatoProtocolloBean.setTipoSezioneContratto(allegato.getTipoSezioneContratto());
		lAllegatoProtocolloBean.setCodContratto(allegato.getCodContratto());
		lAllegatoProtocolloBean.setFlgPresentiFirmeContratto(allegato.getFlgPresentiFirmeContratto());
		lAllegatoProtocolloBean.setFlgFirmeCompleteContratto(allegato.getFlgFirmeCompleteContratto());
		lAllegatoProtocolloBean.setNroFirmePrevisteContratto(allegato.getNroFirmePrevisteContratto());
		lAllegatoProtocolloBean.setNroFirmeCompilateContratto(allegato.getNroFirmeCompilateContratto());			
		lAllegatoProtocolloBean.setFlgIncertezzaRilevazioneFirmeContratto(allegato.getFlgIncertezzaRilevazioneFirmeContratto());	
		lAllegatoProtocolloBean.setIsPubblicato(allegato.getFlgPubblicato() != null && "1".equals(allegato.getFlgPubblicato()) ? true : false);
		lAllegatoProtocolloBean.setFlgOriginaleCartaceo(allegato.getFlgOriginaleCartaceo() == Flag.SETTED);
		lAllegatoProtocolloBean.setFlgCopiaSostitutiva(allegato.getFlgCopiaSostitutiva() == Flag.SETTED);
		lAllegatoProtocolloBean.setFlgGenAutoDaModello(allegato.getFlgGenAutoDaModello() == Flag.SETTED);
		lAllegatoProtocolloBean.setFlgTimbratoFilePostReg(allegato.getFlgTimbratoPostReg() != null && allegato.getFlgTimbratoPostReg() == Flag.SETTED);
		lAllegatoProtocolloBean.setIdTask(allegato.getIdTask());
		MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
		if (lFiles != null) {
			Integer dimensioneFile = allegato.getDimensione() != null ? allegato.getDimensione().intValue() : 0;
			String fileNameToFind = StringUtils.isNotBlank(allegato.getNomeOriginale()) ? allegato.getNomeOriginale() : allegato.getDisplayFileName();
			// Provengo dalla mail
			if (allegato.getDisplayFileName().equalsIgnoreCase("__TestoEmail.txt")) {
				lAllegatoProtocolloBean.setNomeFileAllegato("TestoEmail.txt");
				lAllegatoProtocolloBean.setUriFileAllegato(recuperaUriFileAllegato(lFiles, fileNameToFind, dimensioneFile, allegato));
				File realFileAllegato = StorageImplementation.getStorage().getRealFile(lAllegatoProtocolloBean.getUriFileAllegato());
				lMimeTypeFirmaBean.setImpronta(calcolaImpronta(realFileAllegato.toURI().toString(), lAllegatoProtocolloBean.getNomeFileAllegato()));
				DocumentConfiguration lDocumentConfiguration = (DocumentConfiguration) SpringAppContext.getContext().getBean("DocumentConfiguration");
				lMimeTypeFirmaBean.setAlgoritmo(lDocumentConfiguration.getAlgoritmo().value());
				lMimeTypeFirmaBean.setEncoding(lDocumentConfiguration.getEncoding().value());							
				lMimeTypeFirmaBean.setCorrectFileName(lAllegatoProtocolloBean.getNomeFileAllegato());
				lMimeTypeFirmaBean.setFirmato(false);
				lMimeTypeFirmaBean.setConvertibile(true);
				lMimeTypeFirmaBean.setDaScansione(false);
				lMimeTypeFirmaBean.setMimetype("text/plain");
				lMimeTypeFirmaBean.setBytes(realFileAllegato.length());

			} else {
				String lStrUri = recuperaUriFileAllegato(lFiles, fileNameToFind, dimensioneFile, allegato);
				lAllegatoProtocolloBean.setUriFileAllegato(lStrUri);
				if (StringUtils.isNotBlank(allegato.getImpronta())) {
					lMimeTypeFirmaBean.setImpronta(allegato.getImpronta());
					lMimeTypeFirmaBean.setAlgoritmo(allegato.getAlgoritmoImpronta());
					lMimeTypeFirmaBean.setEncoding(allegato.getEncodingImpronta());
				}
				lMimeTypeFirmaBean.setCorrectFileName(lAllegatoProtocolloBean.getNomeFileAllegato());
				lMimeTypeFirmaBean.setFirmato(allegato.getFlgFileFirmato() == Flag.SETTED);
				lMimeTypeFirmaBean.setFirmaValida(!(allegato.getFlgFirmeNonValideBustaCrittografica() == Flag.SETTED));
				
				InfoFirmaMarca lInfoFirmaMarca = new InfoFirmaMarca();
				lInfoFirmaMarca.setBustaCrittograficaFattaDaAuriga(allegato.getFlgBustaCrittograficaAuriga() == Flag.SETTED);
				lInfoFirmaMarca.setFirmeNonValideBustaCrittografica(allegato.getFlgFirmeNonValideBustaCrittografica() == Flag.SETTED);
				lInfoFirmaMarca.setFirmeExtraAuriga(allegato.getFlgFirmeExtraAuriga() == Flag.SETTED);
				lInfoFirmaMarca.setMarcaTemporaleAppostaDaAuriga(allegato.getFlgMarcaTemporaleAuriga() == Flag.SETTED);
				lInfoFirmaMarca.setDataOraMarcaTemporale(allegato.getDataOraMarcaTemporale());
				lInfoFirmaMarca.setMarcaTemporaleNonValida(allegato.getFlgMarcaTemporaleNonValida() == Flag.SETTED);
				lMimeTypeFirmaBean.setInfoFirmaMarca(lInfoFirmaMarca);
				
				lMimeTypeFirmaBean.setPdfProtetto(allegato.getFlgPdfProtetto() == Flag.SETTED);
				lMimeTypeFirmaBean.setPdfEditabile(allegato.getFlgPdfEditabile() == Flag.SETTED);
				lMimeTypeFirmaBean.setPdfConCommenti(allegato.getFlgPdfConCommenti() == Flag.SETTED);
				
				lMimeTypeFirmaBean.setConvertibile(allegato.getFlgConvertibilePdf() == Flag.SETTED);
				lMimeTypeFirmaBean.setDaScansione(false);
				lMimeTypeFirmaBean.setMimetype(allegato.getMimetype());
				lMimeTypeFirmaBean.setBytes(allegato.getDimensione() != null ? allegato.getDimensione().longValue() : 0);
				if (lMimeTypeFirmaBean.isFirmato()) {
					lMimeTypeFirmaBean.setTipoFirma(allegato.getDisplayFileName().toUpperCase().endsWith("P7M")
							|| allegato.getDisplayFileName().toUpperCase().endsWith("TSD") ? "CAdES_BES" : "PDF");
					lMimeTypeFirmaBean.setFirmatari(StringUtils.isNotBlank(allegato.getFirmatari()) ? allegato.getFirmatari().split(",") : null);											
				}	
				
				boolean callFileOp = false;
				if((lMimeTypeFirmaBean.getMimetype() == null || "".equalsIgnoreCase(lMimeTypeFirmaBean.getMimetype())) || "sconosciuto".equalsIgnoreCase(lMimeTypeFirmaBean.getMimetype())) {
					callFileOp = true;
				} else if (lMimeTypeFirmaBean.isFirmato()) {
					if (lMimeTypeFirmaBean.getFirmatari() == null) {
						callFileOp = true;
					} else if (ParametriDBUtil.getParametroDBAsBoolean(session, "ATTIVA_CTRL_AUTO_FIRMA_ATTACH_MAIL")) {
						Long currentSize = lMimeTypeFirmaBean.getBytes();
						Long sizeDB = StringUtils.isNotBlank(ParametriDBUtil.getParametroDB(session, "MAX_SIZE_ATTCH_CTRL_FIRMA"))
								? new Long(ParametriDBUtil.getParametroDB(session, "MAX_SIZE_ATTCH_CTRL_FIRMA")) : 0;
						if(sizeDB == 0 || currentSize <= sizeDB) {
							callFileOp = true;
						}
					}
				}	
				if(callFileOp) {
					lMimeTypeFirmaBean = new InfoFileUtility().getInfoFromFile(
							StorageImplementation.getStorage().getRealFile(lAllegatoProtocolloBean.getUriFileAllegato()).toURI().toString(),
							lAllegatoProtocolloBean.getNomeFileAllegato(), false, dateRif);
				}
			}
			
			lAllegatoProtocolloBean.setInfoFile(lMimeTypeFirmaBean);
			
			if(lFiles.size() <=10) {
//				/*Controllo introdotto per gestire i pdf non leggibili da itext e quindi li trasformo in immagini per poter fare le varie operazioni*/
//				if(lMimeTypeFirmaBean.getMimetype()!= null && lMimeTypeFirmaBean.getMimetype().contains("pdf") && attivaGestPdfCorrotti() && !lMimeTypeFirmaBean.isFirmato() && !"tsd".equalsIgnoreCase(FilenameUtils.getExtension(lMimeTypeFirmaBean.getCorrectFileName()))) {
//					gestioneAllegatiCorrotti(lAllegatoProtocolloBean, false);
//				}	
				
				/*Controllo introdotto per gestire i pdf editabili individuati da fileop*/
				String realPathFile = "";
				if(attivaGestPdfEditabili() && lMimeTypeFirmaBean.getMimetype()!= null && !lMimeTypeFirmaBean.isFirmato() && lMimeTypeFirmaBean.getMimetype().contains("pdf") &&
						lMimeTypeFirmaBean.isPdfEditabile() &&!"p7m".equalsIgnoreCase(FilenameUtils.getExtension(lMimeTypeFirmaBean.getCorrectFileName()))
						&& !"tsd".equalsIgnoreCase(FilenameUtils.getExtension(lMimeTypeFirmaBean.getCorrectFileName()))) {
					realPathFile = StorageImplementation.getStorage().getRealFile(lAllegatoProtocolloBean.getUriFileAllegato()).toURI().toString();
					gestioneAllegatiEditabili(lAllegatoProtocolloBean, realPathFile);
				}	
				
				/*Controllo introdotto per gestire i pdf con commenti individuati da fileop*/
				if(attivaGestPdfConCommenti() && lMimeTypeFirmaBean.getMimetype()!= null && !lMimeTypeFirmaBean.isFirmato() &&
						lMimeTypeFirmaBean.isPdfConCommenti() && lMimeTypeFirmaBean.getMimetype().contains("pdf") && !"p7m".equalsIgnoreCase(FilenameUtils.getExtension(lMimeTypeFirmaBean.getCorrectFileName()))
						&& !"tsd".equalsIgnoreCase(FilenameUtils.getExtension(lMimeTypeFirmaBean.getCorrectFileName()))) {
					if (StringUtils.isBlank(realPathFile)) {
						realPathFile = StorageImplementation.getStorage().getRealFile(lAllegatoProtocolloBean.getUriFileAllegato()).toURI().toString();
					}
					gestioneAllegatiConCommenti(lAllegatoProtocolloBean, realPathFile);
				}
			}
			
		} else {
			lAllegatoProtocolloBean.setUriFileAllegato(allegato.getUri());
			lAllegatoProtocolloBean.setRemoteUri(true);
			if (StringUtils.isNotBlank(allegato.getImpronta())) {
				lMimeTypeFirmaBean.setImpronta(allegato.getImpronta());
				lMimeTypeFirmaBean.setAlgoritmo(allegato.getAlgoritmoImpronta());
				lMimeTypeFirmaBean.setEncoding(allegato.getEncodingImpronta());
			}
			lMimeTypeFirmaBean.setCorrectFileName(lAllegatoProtocolloBean.getNomeFileAllegato());
			lMimeTypeFirmaBean.setFirmato(allegato.getFlgFileFirmato() == Flag.SETTED);
			lMimeTypeFirmaBean.setFirmaValida(!(allegato.getFlgFirmeNonValideBustaCrittografica() == Flag.SETTED));
			
			InfoFirmaMarca lInfoFirmaMarca = new InfoFirmaMarca();
			lInfoFirmaMarca.setBustaCrittograficaFattaDaAuriga(allegato.getFlgBustaCrittograficaAuriga() == Flag.SETTED);
			lInfoFirmaMarca.setFirmeNonValideBustaCrittografica(allegato.getFlgFirmeNonValideBustaCrittografica() == Flag.SETTED);
			lInfoFirmaMarca.setFirmeExtraAuriga(allegato.getFlgFirmeExtraAuriga() == Flag.SETTED);
			lInfoFirmaMarca.setMarcaTemporaleAppostaDaAuriga(allegato.getFlgMarcaTemporaleAuriga() == Flag.SETTED);
			lInfoFirmaMarca.setDataOraMarcaTemporale(allegato.getDataOraMarcaTemporale());
			lInfoFirmaMarca.setMarcaTemporaleNonValida(allegato.getFlgMarcaTemporaleNonValida() == Flag.SETTED);
			lMimeTypeFirmaBean.setInfoFirmaMarca(lInfoFirmaMarca);
			
			lMimeTypeFirmaBean.setPdfProtetto(allegato.getFlgPdfProtetto() == Flag.SETTED);
			lMimeTypeFirmaBean.setPdfEditabile(allegato.getFlgPdfEditabile() == Flag.SETTED);
			lMimeTypeFirmaBean.setPdfConCommenti(allegato.getFlgPdfConCommenti() == Flag.SETTED);
			
			lMimeTypeFirmaBean.setConvertibile(allegato.getFlgConvertibilePdf() == Flag.SETTED);
			lMimeTypeFirmaBean.setDaScansione(false);
			lMimeTypeFirmaBean.setMimetype(allegato.getMimetype());
			lMimeTypeFirmaBean.setBytes(allegato.getDimensione() != null ? allegato.getDimensione().longValue() : 0);
			if (lMimeTypeFirmaBean.isFirmato()) {
				lMimeTypeFirmaBean.setTipoFirma(allegato.getDisplayFileName().toUpperCase().endsWith("P7M")
						|| allegato.getDisplayFileName().toUpperCase().endsWith("TSD") ? "CAdES_BES" : "PDF");
				lMimeTypeFirmaBean.setFirmatari(StringUtils.isNotBlank(allegato.getFirmatari()) ? allegato.getFirmatari().split(",") : null);
			}
			
			lAllegatoProtocolloBean.setInfoFile(lMimeTypeFirmaBean);				
		}
		
		lAllegatoProtocolloBean.setIdAttachEmail(allegato.getIdAttachEmail());
		lAllegatoProtocolloBean.setIdEmail(allegato.getIdEmail());
		lAllegatoProtocolloBean.setNroUltimaVersione(allegato.getNroUltimaVersione());
		lAllegatoProtocolloBean.setMimetypeVerPreFirma(allegato.getMimetypeVerPreFirma());
		lAllegatoProtocolloBean.setUriFileVerPreFirma(allegato.getUriVerPreFirma());
		lAllegatoProtocolloBean.setNomeFileVerPreFirma(allegato.getDisplayFilenameVerPreFirma());
		lAllegatoProtocolloBean.setFlgConvertibilePdfVerPreFirma(allegato.getFlgConvertibilePdfVerPreFirma());
		lAllegatoProtocolloBean.setImprontaVerPreFirma(allegato.getImprontaVerPreFirma());
		if(StringUtils.isNotBlank(lAllegatoProtocolloBean.getUriFileVerPreFirma())) {
			if (lMimeTypeFirmaBean.getImpronta() != null && lAllegatoProtocolloBean.getImprontaVerPreFirma() != null
					&& lMimeTypeFirmaBean.getImpronta().equals(lAllegatoProtocolloBean.getImprontaVerPreFirma())) {
				lAllegatoProtocolloBean.setInfoFileVerPreFirma(lMimeTypeFirmaBean);
			} else if(!skipRecuperoInfoFileVerPreFirmaFromFOP()) {
				try {
					MimeTypeFirmaBean lMimeTypeFirmaBeanVerPreFirma = new InfoFileUtility().getInfoFromFile(
							StorageImplementation.getStorage().getRealFile(lAllegatoProtocolloBean.getUriFileVerPreFirma()).toURI().toString(), lAllegatoProtocolloBean.getNomeFileVerPreFirma(),
							false, null);
					lAllegatoProtocolloBean.setInfoFileVerPreFirma(lMimeTypeFirmaBeanVerPreFirma);
					logger.debug("InfoFileVerPreFirma recuperato con successo: "+ lAllegatoProtocolloBean.getInfoFileVerPreFirma().getCorrectFileName());		
				} catch (Exception e) {
					logger.debug("InfoFileVerPreFirma non recuperato ", e);		
				}
			}
		}
		
		if(StringUtils.isNotBlank(allegato.getIdUDScansione())) {
			lAllegatoProtocolloBean.setIdUDScansione(allegato.getIdUDScansione());
			lAllegatoProtocolloBean.setIsChanged(true);
		}
				
		// Archiviazione in ACTA
		lAllegatoProtocolloBean.setEsitoInvioACTASerieAttiIntegrali(allegato.getEsitoInvioACTASerieAttiIntegrali());
		lAllegatoProtocolloBean.setEsitoInvioACTASeriePubbl(allegato.getEsitoInvioACTASeriePubbl());
		
		// Vers. con omissis
		BigDecimal idDocOmissis = StringUtils.isNotBlank(allegato.getIdDocOmissis()) ? new BigDecimal(allegato.getIdDocOmissis()) : null;
		lAllegatoProtocolloBean.setIdDocOmissis(idDocOmissis);
		lAllegatoProtocolloBean.setNomeFileOmissis(allegato.getDisplayFileNameOmissis());
		lAllegatoProtocolloBean.setFlgTimbratoFilePostRegOmissis(allegato.getFlgTimbratoPostRegOmissis() != null && allegato.getFlgTimbratoPostRegOmissis() == Flag.SETTED);
		MimeTypeFirmaBean lMimeTypeFirmaBeanOmissis = new MimeTypeFirmaBean();
		lAllegatoProtocolloBean.setUriFileOmissis(allegato.getUriOmissis());
		lAllegatoProtocolloBean.setRemoteUriOmissis(true);
		if (StringUtils.isNotBlank(allegato.getImprontaOmissis())) {
			lMimeTypeFirmaBeanOmissis.setImpronta(allegato.getImprontaOmissis());			
			lMimeTypeFirmaBeanOmissis.setAlgoritmo(allegato.getAlgoritmoImprontaOmissis());
			lMimeTypeFirmaBeanOmissis.setEncoding(allegato.getEncodingImprontaOmissis());	
		}
		lMimeTypeFirmaBeanOmissis.setCorrectFileName(lAllegatoProtocolloBean.getNomeFileOmissis());
		lMimeTypeFirmaBeanOmissis.setFirmato(allegato.getFlgFileFirmatoOmissis() == Flag.SETTED);
		lMimeTypeFirmaBeanOmissis.setFirmaValida(!(allegato.getFlgFirmeNonValideBustaCrittograficaOmissis() == Flag.SETTED));
		
		InfoFirmaMarca lInfoFirmaMarcaOmissis = new InfoFirmaMarca();
		lInfoFirmaMarcaOmissis.setBustaCrittograficaFattaDaAuriga(allegato.getFlgBustaCrittograficaAurigaOmissis() == Flag.SETTED);
		lInfoFirmaMarcaOmissis.setFirmeNonValideBustaCrittografica(allegato.getFlgFirmeNonValideBustaCrittograficaOmissis() == Flag.SETTED);
		lInfoFirmaMarcaOmissis.setFirmeExtraAuriga(allegato.getFlgFirmeExtraAurigaOmissis() == Flag.SETTED);
		lInfoFirmaMarcaOmissis.setMarcaTemporaleAppostaDaAuriga(allegato.getFlgMarcaTemporaleAurigaOmissis() == Flag.SETTED);
		lInfoFirmaMarcaOmissis.setDataOraMarcaTemporale(allegato.getDataOraMarcaTemporaleOmissis());
		lInfoFirmaMarcaOmissis.setMarcaTemporaleNonValida(allegato.getFlgMarcaTemporaleNonValidaOmissis() == Flag.SETTED);
		lMimeTypeFirmaBeanOmissis.setInfoFirmaMarca(lInfoFirmaMarcaOmissis);
		
		lMimeTypeFirmaBeanOmissis.setPdfProtetto(allegato.getFlgPdfProtettoOmissis() == Flag.SETTED);
		lMimeTypeFirmaBeanOmissis.setPdfEditabile(allegato.getFlgPdfEditabileOmissis() == Flag.SETTED);
		lMimeTypeFirmaBeanOmissis.setPdfConCommenti(allegato.getFlgPdfConCommentiOmissis() == Flag.SETTED);
		
		lMimeTypeFirmaBeanOmissis.setConvertibile(allegato.getFlgConvertibilePdfOmissis() == Flag.SETTED);
		lMimeTypeFirmaBeanOmissis.setDaScansione(false);
		lMimeTypeFirmaBeanOmissis.setMimetype(allegato.getMimetypeOmissis());
		lMimeTypeFirmaBeanOmissis.setBytes(allegato.getDimensioneOmissis() != null ? allegato.getDimensioneOmissis().longValue() : 0);
		if (lMimeTypeFirmaBeanOmissis.isFirmato()) {
			lMimeTypeFirmaBeanOmissis.setTipoFirma(allegato.getDisplayFileNameOmissis().toUpperCase().endsWith("P7M")
					|| allegato.getDisplayFileNameOmissis().toUpperCase().endsWith("TSD") ? "CAdES_BES" : "PDF");
			lMimeTypeFirmaBeanOmissis.setFirmatari(StringUtils.isNotBlank(allegato.getFirmatariOmissis()) ? allegato.getFirmatariOmissis().split(",") : null);
		}
		lAllegatoProtocolloBean.setInfoFileOmissis(lMimeTypeFirmaBeanOmissis);
		
		lAllegatoProtocolloBean.setNroUltimaVersioneOmissis(allegato.getNroUltimaVersioneOmissis());
		lAllegatoProtocolloBean.setMimetypeVerPreFirmaOmissis(allegato.getMimetypeVerPreFirmaOmissis());
		lAllegatoProtocolloBean.setUriFileVerPreFirmaOmissis(allegato.getUriVerPreFirmaOmissis());
		lAllegatoProtocolloBean.setNomeFileVerPreFirmaOmissis(allegato.getDisplayFilenameVerPreFirmaOmissis());
		lAllegatoProtocolloBean.setFlgConvertibilePdfVerPreFirmaOmissis(allegato.getFlgConvertibilePdfVerPreFirmaOmissis());
		lAllegatoProtocolloBean.setImprontaVerPreFirmaOmissis(allegato.getImprontaVerPreFirmaOmissis());
		if(StringUtils.isNotBlank(lAllegatoProtocolloBean.getUriFileVerPreFirmaOmissis())) {
			if (lMimeTypeFirmaBeanOmissis.getImpronta() != null && lAllegatoProtocolloBean.getImprontaVerPreFirmaOmissis() != null
					&& lMimeTypeFirmaBeanOmissis.getImpronta().equals(lAllegatoProtocolloBean.getImprontaVerPreFirmaOmissis())) {
				lAllegatoProtocolloBean.setInfoFileVerPreFirmaOmissis(lMimeTypeFirmaBeanOmissis);
			} else if(!skipRecuperoInfoFileVerPreFirmaFromFOP()) {
				try {
					MimeTypeFirmaBean lMimeTypeFirmaBeanVerPreFirmaOmissis = new InfoFileUtility().getInfoFromFile(
							StorageImplementation.getStorage().getRealFile(lAllegatoProtocolloBean.getUriFileVerPreFirmaOmissis()).toURI().toString(), lAllegatoProtocolloBean.getNomeFileVerPreFirmaOmissis(),
							false, null);
					lAllegatoProtocolloBean.setInfoFileVerPreFirmaOmissis(lMimeTypeFirmaBeanVerPreFirmaOmissis);
					logger.debug("InfoFileVerPreFirmaOmissis recuperato con successo: "+ lAllegatoProtocolloBean.getInfoFileVerPreFirmaOmissis().getCorrectFileName());		
				} catch (Exception e) {
					logger.debug("InfoFileVerPreFirmaOmissis non recuperato ", e);		
				}
			}
		}
		
		Integer nrTotAllegati = (lListAllegati != null && lListAllegati.size() > 0) ? lListAllegati.size() : 0;
		nrTotAllegati++;
		lAllegatoProtocolloBean.setNumeroProgrAllegato(nrTotAllegati.toString());
		lAllegatoProtocolloBean.setFlgAllegatoDaImportare(allegato.getFlgAllegatoDaImportare() == Flag.SETTED);
		
		lAllegatoProtocolloBean.setNroAllegato(allegato.getNroAllegato());
		
		lListAllegati.add(lAllegatoProtocolloBean);
	}

	/**
	 * @param lAllegatoProtocolloBean
	 * @param lMimeTypeFirmaBean
	 * @param omissis 
	 * @return
	 * @throws Exception
	 */
	public void gestioneAllegatiEditabili(AllegatoProtocolloBean lAllegatoProtocolloBean, String realPathFile) throws Exception {
		
		try {
			String nomeAllegato = lAllegatoProtocolloBean.getNomeFileAllegato();
			MimeTypeFirmaBean lMimeTypeFirmaBean = lAllegatoProtocolloBean.getInfoFile();
			lMimeTypeFirmaBean.setPdfEditabile(true);

			IdFileBean idFileBean = ManagePdfUtil.manageEditablePdf(realPathFile, nomeAllegato, false, dateRif, lMimeTypeFirmaBean);

			if (idFileBean.getInfoFile() != null && StringUtils.isNotBlank(idFileBean.getUri())) {
				MimeTypeFirmaBean lMimeTypeStaticizzatoBean = idFileBean.getInfoFile();
				String uriFileStaticizzato = idFileBean.getUri();
					lAllegatoProtocolloBean.setUriFileAllegato(uriFileStaticizzato);
					lAllegatoProtocolloBean.setInfoFile(lMimeTypeStaticizzatoBean);
				}

		} catch (Exception e) {
			logger.error("Errore nella gestione degli allegati editabili della mail: " + e.getMessage(), e);
		}
	}
	
	/**
	 * @param lAllegatoProtocolloBean
	 * @param lMimeTypeFirmaBean
	 * @param omissis 
	 * @return
	 * @throws Exception
	 */
	public void gestioneAllegatiConCommenti(AllegatoProtocolloBean lAllegatoProtocolloBean, String realPathFile) throws Exception {
		
		try {
			String nomeAllegato = lAllegatoProtocolloBean.getNomeFileAllegato();
			MimeTypeFirmaBean lMimeTypeFirmaBean = lAllegatoProtocolloBean.getInfoFile();
			lMimeTypeFirmaBean.setPdfConCommenti(true);

			IdFileBean idFileBean = ManagePdfUtil.managePdfConCommenti(realPathFile, nomeAllegato, false, dateRif, lMimeTypeFirmaBean);

			if (idFileBean.getInfoFile() != null && StringUtils.isNotBlank(idFileBean.getUri())) {
				MimeTypeFirmaBean lMimeTypeStaticizzatoBean = idFileBean.getInfoFile();
				String uriFileStaticizzato = idFileBean.getUri();
					lAllegatoProtocolloBean.setUriFileAllegato(uriFileStaticizzato);
					lAllegatoProtocolloBean.setInfoFile(lMimeTypeStaticizzatoBean);
				}

		} catch (Exception e) {
			logger.error("Errore nella gestione degli allegati con commenti della mail: " + e.getMessage(), e);
		}
	}
	
//	private void gestioneAllegatiCorrotti(AllegatoProtocolloBean lAllegatoProtocolloBean, boolean omissis) {
//		try {
//			String realPathFile;
//			String nomeAllegato;
//			MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
//
//			if (omissis) {
//				realPathFile = StorageImplementation.getStorage()
//						.getRealFile(lAllegatoProtocolloBean.getUriFileOmissis()).toURI().toString();
//				nomeAllegato = lAllegatoProtocolloBean.getNomeFileOmissis();
//				lMimeTypeFirmaBean = lAllegatoProtocolloBean.getInfoFileOmissis();
//			} else {
//				realPathFile = StorageImplementation.getStorage()
//						.getRealFile(lAllegatoProtocolloBean.getUriFileAllegato()).toURI().toString();
//				nomeAllegato = lAllegatoProtocolloBean.getNomeFileAllegato();
//				lMimeTypeFirmaBean = lAllegatoProtocolloBean.getInfoFile();
//			}
//
//			/*
//			 * Controllo se è un file corrotto cioè non leggibile di itext, se si lo
//			 * converto in immagine
//			 * 
//			 */
//
//			if (!ManagePdfUtil.checkPdfReadItext(realPathFile)) {
//
//				IdFileBean idFileBean = ManagePdfUtil.managePdfCorrotti(realPathFile, nomeAllegato, false, dateRif,
//						lMimeTypeFirmaBean);
//
//				if (idFileBean.getInfoFile() != null && StringUtils.isNotBlank(idFileBean.getUri())) {
//					MimeTypeFirmaBean lMimeTypeConvertitooBean = idFileBean.getInfoFile();
//					String uriFileConvertitoInImmagine = idFileBean.getUri();
//					if (omissis) {
//						lAllegatoProtocolloBean.setInfoFileOmissis(lMimeTypeConvertitooBean);
//						lAllegatoProtocolloBean.setUriFileOmissis(uriFileConvertitoInImmagine);
//					} else {
//						lAllegatoProtocolloBean.setUriFileAllegato(uriFileConvertitoInImmagine);
//						lAllegatoProtocolloBean.setInfoFile(lMimeTypeConvertitooBean);
//					}
//				}
//			}
//		} catch (Exception e) {
//			logger.error("Errore nella gestione degli allegati corrotti della mail: " + e.getMessage(), e);
//		}
//
//	}

	protected void recuperaAllegatoFromFOP(List<AllegatoProtocolloBean> lListAllegati, Map<String, Object> lFiles, PostaElettronicaRetriewAttachBean lAttachItem)
			throws Exception {

		AllegatoProtocolloBean lAllegatoProtocolloBean = new AllegatoProtocolloBean();
		BigDecimal idDocAllegato = new BigDecimal(0);

		String nomeFile = lAttachItem.getMimeTypeFirmaBean().getCorrectFileName();

		lAllegatoProtocolloBean.setIdDocAllegato(idDocAllegato);
		lAllegatoProtocolloBean.setDescrizioneFileAllegato("");
		lAllegatoProtocolloBean.setIdTipoFileAllegato(null);
		lAllegatoProtocolloBean.setListaTipiFileAllegato(null);
		lAllegatoProtocolloBean.setNomeFileAllegato(nomeFile);
		lAllegatoProtocolloBean.setRemoteUri(false);
		lAllegatoProtocolloBean.setFlgProvEsterna(false);
		lAllegatoProtocolloBean.setFlgParteDispositivo(false);
		lAllegatoProtocolloBean.setFlgParteDispositivoSalvato(false);
		lAllegatoProtocolloBean.setFlgParere(false);
		lAllegatoProtocolloBean.setFlgDatiProtettiTipo1(false);
		lAllegatoProtocolloBean.setFlgDatiProtettiTipo2(false);
		lAllegatoProtocolloBean.setFlgDatiProtettiTipo3(false);
		lAllegatoProtocolloBean.setFlgDatiProtettiTipo4(false);				
		lAllegatoProtocolloBean.setIdTask(null);
		lAllegatoProtocolloBean.setFlgNoPubblAllegato(false);
		lAllegatoProtocolloBean.setFlgPubblicaSeparato(false);
		lAllegatoProtocolloBean.setFlgDatiSensibili(false);
		lAllegatoProtocolloBean.setIsPubblicato(false);
		lAllegatoProtocolloBean.setFlgOriginaleCartaceo(false);
		lAllegatoProtocolloBean.setFlgCopiaSostitutiva(false);
		lAllegatoProtocolloBean.setFlgGenAutoDaModello(false);
		MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
		Long longDimFile = lAttachItem.getMimeTypeFirmaBean().getBytes();
		Integer dimensioneFile = longDimFile.intValue();
		if (lFiles != null) {
			// Provengo dalla mail
			if (nomeFile.equalsIgnoreCase("__TestoEmail.txt")) {
				lAllegatoProtocolloBean.setNomeFileAllegato("TestoEmail.txt");

				AllegatiOutBean allegato = new AllegatiOutBean();
				allegato.setImpronta(lAttachItem.getMimeTypeFirmaBean().getImpronta());
				allegato.setAlgoritmoImpronta(lAttachItem.getMimeTypeFirmaBean().getAlgoritmo());
				allegato.setEncodingImpronta(lAttachItem.getMimeTypeFirmaBean().getEncoding());
				
				lAllegatoProtocolloBean.setUriFileAllegato(recuperaUriFileAllegato(lFiles, nomeFile, dimensioneFile, allegato));
				File realFileAllegato = StorageImplementation.getStorage().getRealFile(lAttachItem.getUriFile());
				lMimeTypeFirmaBean.setImpronta(calcolaImpronta(realFileAllegato.toURI().toString(), nomeFile));
				DocumentConfiguration lDocumentConfiguration = (DocumentConfiguration) SpringAppContext.getContext().getBean("DocumentConfiguration");
				lMimeTypeFirmaBean.setAlgoritmo(lDocumentConfiguration.getAlgoritmo().value());
				lMimeTypeFirmaBean.setEncoding(lDocumentConfiguration.getEncoding().value());				
				lMimeTypeFirmaBean.setCorrectFileName(nomeFile);
				lMimeTypeFirmaBean.setFirmato(false);
				lMimeTypeFirmaBean.setConvertibile(true);
				lMimeTypeFirmaBean.setDaScansione(false);
				lMimeTypeFirmaBean.setMimetype("text/plain");
				lMimeTypeFirmaBean.setBytes(realFileAllegato.length());

			} else {
				AllegatiOutBean allegato = new AllegatiOutBean();
				allegato.setImpronta(lAttachItem.getMimeTypeFirmaBean().getImpronta());
				allegato.setAlgoritmoImpronta(lAttachItem.getMimeTypeFirmaBean().getAlgoritmo());
				allegato.setEncodingImpronta(lAttachItem.getMimeTypeFirmaBean().getEncoding());
				
				String lStrUri = recuperaUriFileAllegato(lFiles, nomeFile, dimensioneFile, allegato);
				lAllegatoProtocolloBean.setUriFileAllegato(lStrUri);
				if (StringUtils.isNotBlank(lAttachItem.getMimeTypeFirmaBean().getImpronta())) {
					lMimeTypeFirmaBean.setImpronta(lAttachItem.getMimeTypeFirmaBean().getImpronta());
					lMimeTypeFirmaBean.setAlgoritmo(lAttachItem.getMimeTypeFirmaBean().getAlgoritmo());
					lMimeTypeFirmaBean.setEncoding(lAttachItem.getMimeTypeFirmaBean().getEncoding());
				}
				lMimeTypeFirmaBean.setCorrectFileName(nomeFile);
				lMimeTypeFirmaBean.setFirmato(lAttachItem.getMimeTypeFirmaBean().isFirmato());
				lMimeTypeFirmaBean.setFirmaValida(lAttachItem.getMimeTypeFirmaBean().isFirmaValida());
				lMimeTypeFirmaBean.setConvertibile(lAttachItem.getMimeTypeFirmaBean().isConvertibile());
				lMimeTypeFirmaBean.setDaScansione(lAttachItem.getMimeTypeFirmaBean().isDaScansione());
				lMimeTypeFirmaBean.setMimetype(lAttachItem.getMimeTypeFirmaBean().getMimetype());
				lMimeTypeFirmaBean.setBytes(dimensioneFile != null ? dimensioneFile.longValue() : 0);
				if (lMimeTypeFirmaBean.isFirmato()) {
					lMimeTypeFirmaBean = new InfoFileUtility().getInfoFromFile(StorageImplementation.getStorage().getRealFile(lAttachItem.getUriFile()).toURI()
							.toString(), lAllegatoProtocolloBean.getNomeFileAllegato(), false, dateRif);
				}
			}
		} else {
			lAllegatoProtocolloBean.setUriFileAllegato(lAttachItem.getUriFile());
			lAllegatoProtocolloBean.setRemoteUri(true);
			if (StringUtils.isNotBlank(lAttachItem.getMimeTypeFirmaBean().getImpronta())) {
				lMimeTypeFirmaBean.setImpronta(lAttachItem.getMimeTypeFirmaBean().getImpronta());
				lMimeTypeFirmaBean.setAlgoritmo(lAttachItem.getMimeTypeFirmaBean().getAlgoritmo());
				lMimeTypeFirmaBean.setEncoding(lAttachItem.getMimeTypeFirmaBean().getEncoding());
			}
			lMimeTypeFirmaBean.setCorrectFileName(nomeFile);
			lMimeTypeFirmaBean.setFirmato(lAttachItem.getMimeTypeFirmaBean().isFirmato());
			lMimeTypeFirmaBean.setFirmaValida(lAttachItem.getMimeTypeFirmaBean().isFirmaValida());
			lMimeTypeFirmaBean.setConvertibile(lAttachItem.getMimeTypeFirmaBean().isConvertibile());
			lMimeTypeFirmaBean.setDaScansione(false);
			lMimeTypeFirmaBean.setMimetype(lAttachItem.getMimeTypeFirmaBean().getMimetype());
			lMimeTypeFirmaBean.setBytes(dimensioneFile != null ? dimensioneFile.longValue() : 0);
			if (lMimeTypeFirmaBean.isFirmato()) {
				lMimeTypeFirmaBean.setTipoFirma(nomeFile.toUpperCase().endsWith("P7M") || nomeFile.toUpperCase().endsWith("TSD") ? "CAdES_BES" : "PDF");
				lMimeTypeFirmaBean.setFirmatari(lAttachItem.getMimeTypeFirmaBean().getFirmatari() != null ? lAttachItem.getMimeTypeFirmaBean().getFirmatari()
						.toString().split(",") : null);
			}
		}
		lAllegatoProtocolloBean.setInfoFile(lMimeTypeFirmaBean);
		lAllegatoProtocolloBean.setIdAttachEmail(null);
		lAllegatoProtocolloBean.setMimetypeVerPreFirma(lAttachItem.getMimeTypeFirmaBean().getMimetype());
		lAllegatoProtocolloBean.setUriFileVerPreFirma(lAttachItem.getUriFile());
		lAllegatoProtocolloBean.setNomeFileVerPreFirma(nomeFile);
		lAllegatoProtocolloBean.setFlgConvertibilePdfVerPreFirma(lAttachItem.getMimeTypeFirmaBean().isConvertibile() ? "1" : "0");
		lAllegatoProtocolloBean.setImprontaVerPreFirma(lAttachItem.getMimeTypeFirmaBean().getImpronta());
		if(StringUtils.isNotBlank(lAllegatoProtocolloBean.getUriFileVerPreFirma())) {
			if (lMimeTypeFirmaBean.getImpronta() != null && lAllegatoProtocolloBean.getImprontaVerPreFirma() != null
					&& lMimeTypeFirmaBean.getImpronta().equals(lAllegatoProtocolloBean.getImprontaVerPreFirma())) {
				lAllegatoProtocolloBean.setInfoFileVerPreFirma(lMimeTypeFirmaBean);
			} else if(!skipRecuperoInfoFileVerPreFirmaFromFOP()) {
				try {
					MimeTypeFirmaBean lMimeTypeFirmaBeanVerPreFirma = new InfoFileUtility().getInfoFromFile(
							StorageImplementation.getStorage().getRealFile(lAllegatoProtocolloBean.getUriFileVerPreFirma()).toURI().toString(), lAllegatoProtocolloBean.getNomeFileVerPreFirma(),
							false, null);
					lAllegatoProtocolloBean.setInfoFileVerPreFirma(lMimeTypeFirmaBeanVerPreFirma);
					logger.debug("InfoFileVerPreFirma recuperato con successo: "+ lAllegatoProtocolloBean.getInfoFileVerPreFirma().getCorrectFileName());		
				} catch (Exception e) {
					logger.debug("InfoFileVerPreFirma non recuperato ", e);		
				}
			}
		}

		Integer nrTotAllegati = (lListAllegati != null && lListAllegati.size() > 0) ? lListAllegati.size() : 0;
		nrTotAllegati++;
		lAllegatoProtocolloBean.setNumeroProgrAllegato(nrTotAllegati.toString());

		lListAllegati.add(lAllegatoProtocolloBean);
	}

	protected void recuperaPrimario(DocumentoXmlOutBean doc, ProtocollazioneBean lBean, Map<String, Object> lFiles) throws Exception {
		
		BigDecimal idDocPrimario = StringUtils.isNotBlank(doc.getIdDocPrimario()) ? new BigDecimal(doc.getIdDocPrimario()) : null;
		lBean.setIdDocPrimario(idDocPrimario);
		lBean.setFlgNoPubblPrimario(doc.getFlgNoPubblPrimario() == Flag.SETTED);
		lBean.setFlgDatiSensibili(doc.getFlgDatiSensibiliPrimario() == Flag.SETTED);
		lBean.setFlgOriginaleCartaceo(doc.getFlgOriginaleCartaceo() == Flag.SETTED);
		lBean.setFlgCopiaSostitutiva(doc.getFlgCopiaSostitutiva() == Flag.SETTED);
		FilePrimarioOutBean lFilePrimarioOutBean = doc.getFilePrimario();
		if (isNotNull(lFilePrimarioOutBean)) {
			lBean.setNomeFilePrimario(lFilePrimarioOutBean.getDisplayFilename());
			lBean.setTsInsLastVerFilePrimario(lFilePrimarioOutBean.getTsInsLastVerFile());
			lBean.setRemoteUriFilePrimario(false);
			lBean.setIsFilePrimarioPubblicato(lFilePrimarioOutBean.getFlgPubblicato() != null && "1".equals(lFilePrimarioOutBean.getFlgPubblicato()) ? true : false);						
			MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
			Integer dimensioneFile = lFilePrimarioOutBean.getDimensione() != null ? lFilePrimarioOutBean.getDimensione().intValue() : 0;
			if (lFiles != null) {
				// Provengo dalla mail
				String fileNameToFind = StringUtils.isNotBlank(lFilePrimarioOutBean.getNomeOriginale()) ? lFilePrimarioOutBean.getNomeOriginale()
						: lFilePrimarioOutBean.getDisplayFilename();
				if (lFilePrimarioOutBean.getDisplayFilename().equalsIgnoreCase("__TestoEmail.txt")) {
					lBean.setUriFilePrimario(recuperaUriFilePrimario(lFiles, fileNameToFind, dimensioneFile, lFilePrimarioOutBean));
					lBean.setNomeFilePrimario("TestoEmail.txt");
					File realFilePrimario = StorageImplementation.getStorage().getRealFile(lBean.getUriFilePrimario());
					lMimeTypeFirmaBean.setImpronta(calcolaImpronta(realFilePrimario.toURI().toString(), lBean.getNomeFilePrimario()));
					DocumentConfiguration lDocumentConfiguration = (DocumentConfiguration) SpringAppContext.getContext().getBean("DocumentConfiguration");
					lMimeTypeFirmaBean.setAlgoritmo(lDocumentConfiguration.getAlgoritmo().value());
					lMimeTypeFirmaBean.setEncoding(lDocumentConfiguration.getEncoding().value());				
					lMimeTypeFirmaBean.setCorrectFileName(lBean.getNomeFilePrimario());
					lMimeTypeFirmaBean.setFirmato(false);
					lMimeTypeFirmaBean.setConvertibile(true);
					lMimeTypeFirmaBean.setDaScansione(false);
					lMimeTypeFirmaBean.setMimetype("text/plain");
					lMimeTypeFirmaBean.setBytes(realFilePrimario.length());
				} else {
					String lStrUri = recuperaUriFilePrimario(lFiles, fileNameToFind, dimensioneFile, lFilePrimarioOutBean);
					lBean.setUriFilePrimario(lStrUri);
					if (StringUtils.isNotBlank(lFilePrimarioOutBean.getImpronta())) {
						lMimeTypeFirmaBean.setImpronta(lFilePrimarioOutBean.getImpronta());
						lMimeTypeFirmaBean.setAlgoritmo(lFilePrimarioOutBean.getAlgoritmoImpronta());
						lMimeTypeFirmaBean.setEncoding(lFilePrimarioOutBean.getEncodingImpronta());
					}
					lMimeTypeFirmaBean.setCorrectFileName(lBean.getNomeFilePrimario());
					lMimeTypeFirmaBean.setFirmato(lFilePrimarioOutBean.getFlgFirmato() == Flag.SETTED);
					lMimeTypeFirmaBean.setFirmaValida(!(lFilePrimarioOutBean.getFlgFirmeNonValideBustaCrittografica() == Flag.SETTED));
					
					InfoFirmaMarca lInfoFirmaMarca = new InfoFirmaMarca();
					lInfoFirmaMarca.setBustaCrittograficaFattaDaAuriga(lFilePrimarioOutBean.getFlgBustaCrittograficaAuriga() == Flag.SETTED);
					lInfoFirmaMarca.setFirmeNonValideBustaCrittografica(lFilePrimarioOutBean.getFlgFirmeNonValideBustaCrittografica() == Flag.SETTED);
					lInfoFirmaMarca.setFirmeExtraAuriga(lFilePrimarioOutBean.getFlgFirmeExtraAuriga() == Flag.SETTED);
					lInfoFirmaMarca.setMarcaTemporaleAppostaDaAuriga(lFilePrimarioOutBean.getFlgMarcaTemporaleAuriga() == Flag.SETTED);
					lInfoFirmaMarca.setDataOraMarcaTemporale(lFilePrimarioOutBean.getDataOraMarcaTemporale());
					lInfoFirmaMarca.setMarcaTemporaleNonValida(lFilePrimarioOutBean.getFlgMarcaTemporaleNonValida() == Flag.SETTED);
					lMimeTypeFirmaBean.setInfoFirmaMarca(lInfoFirmaMarca);
					
					lMimeTypeFirmaBean.setPdfProtetto(lFilePrimarioOutBean.getFlgPdfProtetto() == Flag.SETTED);
					lMimeTypeFirmaBean.setPdfEditabile(lFilePrimarioOutBean.getFlgPdfEditabile() == Flag.SETTED);
					lMimeTypeFirmaBean.setPdfConCommenti(lFilePrimarioOutBean.getFlgPdfConCommenti() == Flag.SETTED);
					
					lMimeTypeFirmaBean.setConvertibile(lFilePrimarioOutBean.getFlgConvertibilePdf() == Flag.SETTED);
					lMimeTypeFirmaBean.setDaScansione(false);
					lMimeTypeFirmaBean.setBytes(lFilePrimarioOutBean.getDimensione() != null ? lFilePrimarioOutBean.getDimensione().longValue() : 0);
					if((lFilePrimarioOutBean.getMimetype() == null || "".equalsIgnoreCase(lFilePrimarioOutBean.getMimetype())) ||
					   (lFilePrimarioOutBean.getMimetype() != null && "sconosciuto".equalsIgnoreCase(lFilePrimarioOutBean.getMimetype())) ) {
						lMimeTypeFirmaBean = new InfoFileUtility().getInfoFromFile(
								StorageImplementation.getStorage().getRealFile(lStrUri).toURI().toString(),
								lFilePrimarioOutBean.getNomeOriginale(), false, dateRif);
					} else {
						lMimeTypeFirmaBean.setMimetype(lFilePrimarioOutBean.getMimetype());
					}
					
					if (lMimeTypeFirmaBean.isFirmato()) {
						if (ParametriDBUtil.getParametroDBAsBoolean(session, "ATTIVA_CTRL_AUTO_FIRMA_ATTACH_MAIL")) {
							Long currentSize = lMimeTypeFirmaBean.getBytes();
							Long sizeDB = StringUtils.isNotBlank(ParametriDBUtil.getParametroDB(session, "MAX_SIZE_ATTCH_CTRL_FIRMA"))
									? new Long(ParametriDBUtil.getParametroDB(session, "MAX_SIZE_ATTCH_CTRL_FIRMA")) : 0;
							if(sizeDB == 0 || currentSize <= sizeDB) {
								lMimeTypeFirmaBean = new InfoFileUtility().getInfoFromFile(StorageImplementation.getStorage().getRealFile(lBean.getUriFilePrimario())
										.toURI().toString(), lBean.getNomeFilePrimario(), false, dateRif);
							} else {
								logger.debug("Il file: "+ lBean.getNomeFilePrimario() +" con dimensione: " + String.valueOf(currentSize)
								+ " supera quella stabilita");
								lMimeTypeFirmaBean.setTipoFirma(lFilePrimarioOutBean.getDisplayFilename().toUpperCase().endsWith("P7M")
										|| lFilePrimarioOutBean.getDisplayFilename().toUpperCase().endsWith("TSD") ? "CAdES_BES" : "PDF");
								lMimeTypeFirmaBean.setFirmatari(StringUtils.isNotBlank(lFilePrimarioOutBean.getFirmatari()) ? lFilePrimarioOutBean.getFirmatari()
										.split(",") : null);
							}
						} else {
							lMimeTypeFirmaBean.setTipoFirma(lFilePrimarioOutBean.getDisplayFilename().toUpperCase().endsWith("P7M")
									|| lFilePrimarioOutBean.getDisplayFilename().toUpperCase().endsWith("TSD") ? "CAdES_BES" : "PDF");
							lMimeTypeFirmaBean.setFirmatari(StringUtils.isNotBlank(lFilePrimarioOutBean.getFirmatari()) ? lFilePrimarioOutBean.getFirmatari()
									.split(",") : null);
						}
					}
				}
			} else {
				lBean.setUriFilePrimario(lFilePrimarioOutBean.getUri());
				lBean.setRemoteUriFilePrimario(true);
				if (StringUtils.isNotBlank(lFilePrimarioOutBean.getImpronta())) {
					lMimeTypeFirmaBean.setImpronta(lFilePrimarioOutBean.getImpronta());
					lMimeTypeFirmaBean.setAlgoritmo(lFilePrimarioOutBean.getAlgoritmoImpronta());
					lMimeTypeFirmaBean.setEncoding(lFilePrimarioOutBean.getEncodingImpronta());
				}
				lMimeTypeFirmaBean.setCorrectFileName(lBean.getNomeFilePrimario());
				lMimeTypeFirmaBean.setFirmato(lFilePrimarioOutBean.getFlgFirmato() == Flag.SETTED);
				lMimeTypeFirmaBean.setFirmaValida(!(lFilePrimarioOutBean.getFlgFirmeNonValideBustaCrittografica() == Flag.SETTED));
				
				InfoFirmaMarca lInfoFirmaMarca = new InfoFirmaMarca();
				lInfoFirmaMarca.setBustaCrittograficaFattaDaAuriga(lFilePrimarioOutBean.getFlgBustaCrittograficaAuriga() == Flag.SETTED);
				lInfoFirmaMarca.setFirmeNonValideBustaCrittografica(lFilePrimarioOutBean.getFlgFirmeNonValideBustaCrittografica() == Flag.SETTED);
				lInfoFirmaMarca.setFirmeExtraAuriga(lFilePrimarioOutBean.getFlgFirmeExtraAuriga() == Flag.SETTED);
				lInfoFirmaMarca.setMarcaTemporaleAppostaDaAuriga(lFilePrimarioOutBean.getFlgMarcaTemporaleAuriga() == Flag.SETTED);
				lInfoFirmaMarca.setDataOraMarcaTemporale(lFilePrimarioOutBean.getDataOraMarcaTemporale());
				lInfoFirmaMarca.setMarcaTemporaleNonValida(lFilePrimarioOutBean.getFlgMarcaTemporaleNonValida() == Flag.SETTED);
				lMimeTypeFirmaBean.setInfoFirmaMarca(lInfoFirmaMarca);
				
				lMimeTypeFirmaBean.setPdfProtetto(lFilePrimarioOutBean.getFlgPdfProtetto() == Flag.SETTED);
				lMimeTypeFirmaBean.setPdfEditabile(lFilePrimarioOutBean.getFlgPdfEditabile() == Flag.SETTED);
				lMimeTypeFirmaBean.setPdfConCommenti(lFilePrimarioOutBean.getFlgPdfConCommenti() == Flag.SETTED);
				
				lMimeTypeFirmaBean.setConvertibile(lFilePrimarioOutBean.getFlgConvertibilePdf() == Flag.SETTED);
				lMimeTypeFirmaBean.setDaScansione(false);
				lMimeTypeFirmaBean.setMimetype(lFilePrimarioOutBean.getMimetype());
				lMimeTypeFirmaBean.setBytes(lFilePrimarioOutBean.getDimensione() != null ? lFilePrimarioOutBean.getDimensione().longValue() : 0);
				if (lMimeTypeFirmaBean.isFirmato()) {
					lMimeTypeFirmaBean.setTipoFirma(lFilePrimarioOutBean.getDisplayFilename().toUpperCase().endsWith("P7M")
							|| lFilePrimarioOutBean.getDisplayFilename().toUpperCase().endsWith("TSD") ? "CAdES_BES" : "PDF");
					lMimeTypeFirmaBean.setFirmatari(StringUtils.isNotBlank(lFilePrimarioOutBean.getFirmatari()) ? lFilePrimarioOutBean.getFirmatari()
							.split(",") : null);
				}
			}
			lBean.setInfoFile(lMimeTypeFirmaBean);
			
			if(StringUtils.isNotBlank(lFilePrimarioOutBean.getIdUDScansione())) {
				lBean.setIdUDScansione(lFilePrimarioOutBean.getIdUDScansione());
				lBean.setIsDocPrimarioChanged(true);
			}			
			
			lBean.setFlgTimbratoFilePostReg(lFilePrimarioOutBean.getFlgTimbratoPostReg() == Flag.SETTED);
			lBean.setIdAttachEmailPrimario(lFilePrimarioOutBean.getIdAttachEmail());
			lBean.setNroLastVer(lFilePrimarioOutBean.getNroLastVer() != null ? lFilePrimarioOutBean.getNroLastVer().toString() : null);
			lBean.setMimetypeVerPreFirma(lFilePrimarioOutBean.getMimetypeVerPreFirma());
			lBean.setUriFileVerPreFirma(lFilePrimarioOutBean.getUriVerPreFirma());
			lBean.setNomeFileVerPreFirma(lFilePrimarioOutBean.getDisplayFilenameVerPreFirma());
			lBean.setFlgConvertibilePdfVerPreFirma(lFilePrimarioOutBean.getFlgConvertibilePdfVerPreFirma());
			lBean.setImprontaVerPreFirma(lFilePrimarioOutBean.getImprontaVerPreFirma());
			if(StringUtils.isNotBlank(lBean.getUriFileVerPreFirma())) {
				if (lMimeTypeFirmaBean.getImpronta() != null && lBean.getImprontaVerPreFirma() != null
						&& lMimeTypeFirmaBean.getImpronta().equals(lBean.getImprontaVerPreFirma())) {
					lBean.setInfoFileVerPreFirma(lMimeTypeFirmaBean);
				} else if(!skipRecuperoInfoFileVerPreFirmaFromFOP()) {
					try {
						MimeTypeFirmaBean lMimeTypeFirmaBeanVerPreFirma = new InfoFileUtility().getInfoFromFile(
								StorageImplementation.getStorage().getRealFile(lBean.getUriFileVerPreFirma()).toURI().toString(), lBean.getNomeFileVerPreFirma(),
								false, null);
						lBean.setInfoFileVerPreFirma(lMimeTypeFirmaBeanVerPreFirma);
						logger.debug("InfoFileVerPreFirma recuperato con successo: "+ lBean.getInfoFileVerPreFirma().getCorrectFileName());		
					} catch (Exception e) {
						logger.debug("InfoFileVerPreFirma non recuperato ", e);		
					}
				}				
			}	
			// Dettaglio doc. contratti con barcode
			lBean.setTipoBarcodePrimario(lFilePrimarioOutBean.getTipoBarcode());
			lBean.setBarcodePrimario(lFilePrimarioOutBean.getBarcode());
			lBean.setEnergiaGasPrimario(lFilePrimarioOutBean.getEnergiaGas());
			lBean.setTipoSezioneContrattoPrimario(lFilePrimarioOutBean.getTipoSezioneContrattoPrimario());
			lBean.setCodContrattoPrimario(lFilePrimarioOutBean.getCodContrattoPrimario());
			lBean.setFlgPresentiFirmeContrattoPrimario(lFilePrimarioOutBean.getFlgPresentiFirmeContrattoPrimario());
			lBean.setFlgFirmeCompleteContrattoPrimario(lFilePrimarioOutBean.getFlgFirmeCompleteContrattoPrimario());
			lBean.setNroFirmePrevisteContrattoPrimario(lFilePrimarioOutBean.getNroFirmePrevisteContrattoPrimario());
			lBean.setNroFirmeCompilateContrattoPrimario(lFilePrimarioOutBean.getNroFirmeCompilateContrattoPrimario());			
			lBean.setFlgIncertezzaRilevazioneFirmePrimario(lFilePrimarioOutBean.getFlgIncertezzaRilevazioneFirmePrimario());
			// Archiviazione in ACTA
			lBean.setEsitoInvioACTASerieAttiIntegrali(lFilePrimarioOutBean.getEsitoInvioACTASerieAttiIntegrali());
			lBean.setEsitoInvioACTASeriePubbl(lFilePrimarioOutBean.getEsitoInvioACTASeriePubbl());
		}
		
		// Vers. con omissis
		lBean.setFilePrimarioOmissis(createFilePrimarioOmissisBean(lFilePrimarioOutBean));
		
		// Firmatari
		lBean.setListaNominativiFirmaOlografa(doc.getListaNominativiFirmaOlografa());
	}
	
	private DocumentBean createFilePrimarioOmissisBean(FilePrimarioOutBean lFilePrimarioOutBean) throws Exception {
		DocumentBean lFilePrimarioOmissis = new DocumentBean();		
		if (isNotNull(lFilePrimarioOutBean)) {
			lFilePrimarioOmissis.setIdDoc(lFilePrimarioOutBean.getIdDocOmissis());
			lFilePrimarioOmissis.setNomeFile(lFilePrimarioOutBean.getDisplayFilenameOmissis());
			lFilePrimarioOmissis.setUriFile(lFilePrimarioOutBean.getUriOmissis());
			lFilePrimarioOmissis.setRemoteUri(true);
			MimeTypeFirmaBean lMimeTypeFirmaBeanOmissis = new MimeTypeFirmaBean();
			if (StringUtils.isNotBlank(lFilePrimarioOutBean.getImprontaOmissis())) {
				lMimeTypeFirmaBeanOmissis.setImpronta(lFilePrimarioOutBean.getImprontaOmissis());				
				lMimeTypeFirmaBeanOmissis.setAlgoritmo(lFilePrimarioOutBean.getAlgoritmoImprontaOmissis());
				lMimeTypeFirmaBeanOmissis.setEncoding(lFilePrimarioOutBean.getEncodingImprontaOmissis());				
			}
			lMimeTypeFirmaBeanOmissis.setCorrectFileName(lFilePrimarioOmissis.getNomeFile());
			lMimeTypeFirmaBeanOmissis.setFirmato(lFilePrimarioOutBean.getFlgFirmatoOmissis() == Flag.SETTED);
			lMimeTypeFirmaBeanOmissis.setFirmaValida(lFilePrimarioOutBean.getFlgFirmatoOmissis() == Flag.SETTED);
			
			InfoFirmaMarca lInfoFirmaMarcaOmissis = new InfoFirmaMarca();
			lInfoFirmaMarcaOmissis.setBustaCrittograficaFattaDaAuriga(lFilePrimarioOutBean.getFlgBustaCrittograficaAurigaOmissis() == Flag.SETTED);
			lInfoFirmaMarcaOmissis.setFirmeNonValideBustaCrittografica(lFilePrimarioOutBean.getFlgFirmeNonValideBustaCrittograficaOmissis() == Flag.SETTED);
			lInfoFirmaMarcaOmissis.setFirmeExtraAuriga(lFilePrimarioOutBean.getFlgFirmeExtraAurigaOmissis() == Flag.SETTED);
			lInfoFirmaMarcaOmissis.setMarcaTemporaleAppostaDaAuriga(lFilePrimarioOutBean.getFlgMarcaTemporaleAurigaOmissis() == Flag.SETTED);
			lInfoFirmaMarcaOmissis.setDataOraMarcaTemporale(lFilePrimarioOutBean.getDataOraMarcaTemporaleOmissis());
			lInfoFirmaMarcaOmissis.setMarcaTemporaleNonValida(lFilePrimarioOutBean.getFlgMarcaTemporaleNonValidaOmissis() == Flag.SETTED);
			lMimeTypeFirmaBeanOmissis.setInfoFirmaMarca(lInfoFirmaMarcaOmissis);
			
			lMimeTypeFirmaBeanOmissis.setPdfProtetto(lFilePrimarioOutBean.getFlgPdfProtettoOmissis() == Flag.SETTED);
			lMimeTypeFirmaBeanOmissis.setPdfEditabile(lFilePrimarioOutBean.getFlgPdfEditabileOmissis() == Flag.SETTED);
			lMimeTypeFirmaBeanOmissis.setPdfConCommenti(lFilePrimarioOutBean.getFlgPdfConCommentiOmissis() == Flag.SETTED);
			
			lMimeTypeFirmaBeanOmissis.setConvertibile(lFilePrimarioOutBean.getFlgConvertibilePdfOmissis() == Flag.SETTED);
			lMimeTypeFirmaBeanOmissis.setDaScansione(false);
			lMimeTypeFirmaBeanOmissis.setMimetype(lFilePrimarioOutBean.getMimetypeOmissis());
			lMimeTypeFirmaBeanOmissis.setBytes(lFilePrimarioOutBean.getDimensioneOmissis() != null ? lFilePrimarioOutBean.getDimensioneOmissis().longValue() : 0);
			if (lMimeTypeFirmaBeanOmissis.isFirmato()) {
				lMimeTypeFirmaBeanOmissis.setTipoFirma(lFilePrimarioOutBean.getDisplayFilenameOmissis().toUpperCase().endsWith("P7M")
						|| lFilePrimarioOutBean.getDisplayFilenameOmissis().toUpperCase().endsWith("TSD") ? "CAdES_BES" : "PDF");
				lMimeTypeFirmaBeanOmissis.setFirmatari(StringUtils.isNotBlank(lFilePrimarioOutBean.getFirmatariOmissis()) ? lFilePrimarioOutBean.getFirmatariOmissis()
						.split(",") : null);
			}	
			lFilePrimarioOmissis.setInfoFile(lMimeTypeFirmaBeanOmissis);
			lFilePrimarioOmissis.setFlgTimbratoFilePostReg(lFilePrimarioOutBean.getFlgTimbratoPostRegOmissis() == Flag.SETTED);
			lFilePrimarioOmissis.setNroUltimaVersione(lFilePrimarioOutBean.getNroLastVerOmissis() != null ? lFilePrimarioOutBean.getNroLastVerOmissis().toString() : null);
			lFilePrimarioOmissis.setMimetypeVerPreFirma(lFilePrimarioOutBean.getMimetypeVerPreFirmaOmissis());
			lFilePrimarioOmissis.setUriFileVerPreFirma(lFilePrimarioOutBean.getUriVerPreFirmaOmissis());
			lFilePrimarioOmissis.setNomeFileVerPreFirma(lFilePrimarioOutBean.getDisplayFilenameVerPreFirmaOmissis());
			lFilePrimarioOmissis.setFlgConvertibilePdfVerPreFirma(lFilePrimarioOutBean.getFlgConvertibilePdfVerPreFirmaOmissis());
			lFilePrimarioOmissis.setImprontaVerPreFirma(lFilePrimarioOutBean.getImprontaVerPreFirmaOmissis());
			if(StringUtils.isNotBlank(lFilePrimarioOmissis.getUriFileVerPreFirma())) {
				if (lMimeTypeFirmaBeanOmissis.getImpronta() != null && lFilePrimarioOmissis.getImprontaVerPreFirma() != null
						&& lMimeTypeFirmaBeanOmissis.getImpronta().equals(lFilePrimarioOmissis.getImprontaVerPreFirma())) {
					lFilePrimarioOmissis.setInfoFileVerPreFirma(lMimeTypeFirmaBeanOmissis);				
				} else if(!skipRecuperoInfoFileVerPreFirmaFromFOP()) {
					try {
						MimeTypeFirmaBean lMimeTypeFirmaBeanVerPreFirmaOmissis = new InfoFileUtility().getInfoFromFile(
								StorageImplementation.getStorage().getRealFile(lFilePrimarioOmissis.getUriFileVerPreFirma()).toURI().toString(), lFilePrimarioOmissis.getNomeFileVerPreFirma(),
								false, null);
						lFilePrimarioOmissis.setInfoFileVerPreFirma(lMimeTypeFirmaBeanVerPreFirmaOmissis);
						logger.debug("InfoFileVerPreFirmaOmissis recuperato con successo: "+ lFilePrimarioOmissis.getInfoFileVerPreFirma().getCorrectFileName());		
					} catch (Exception e) {
						logger.debug("InfoFileVerPreFirmaOmissis non recuperato ", e);		
					}
				}
			}
		}
		return lFilePrimarioOmissis;
	}

	/**
	 * 
	 * Viene recuperato per il file primario l'uri tramite il match tra nome_file + dimensione. Se l'uri non viene recuperato dalla mappa, allora viene
	 * calcolata l'impronta del primario recuperata tramite il file,l'algoritmo ed l'encoding.Viene quindi recuperato il relativo uri valorizzato del file.
	 */
	private String recuperaUriFilePrimario(Map<String, Object> lFiles, String displayFilename, Integer dimensione, FilePrimarioOutBean lFilePrimarioOutBean)
			throws StoreException, StorageException {
		String uriFinale = null;
		CalcolaImpronteService calcolaImpronteService = new CalcolaImpronteService();
		try {
			if (!"__TestoEmail.txt".equalsIgnoreCase(displayFilename)) {
				String uriFileTemp = (String) lFiles.get(displayFilename.concat(";").concat(dimensione.toString()));
				if (uriFileTemp != null) {
					uriFinale = uriFileTemp;
				} else {
					String algoritmoAttach = lFilePrimarioOutBean.getAlgoritmoImpronta();
					String encodingAttach = lFilePrimarioOutBean.getEncodingImpronta();
					String impronta = lFilePrimarioOutBean.getImpronta();

					Map<String, String> mapImpronteFile = new HashMap<String, String>();
					Iterator<String> ite = lFiles.keySet().iterator();
					while (ite.hasNext()) {
						String key = ite.next();
						Object value = lFiles.get(key);
						File fileTemp = StorageImplementation.getStorage().extractFile((String) value);
						String improntaFile = calcolaImpronteService.calcolaImprontaWithoutFileOp(fileTemp, algoritmoAttach, encodingAttach);
						mapImpronteFile.put(improntaFile, key);
					}

					String keyMapFileUnivoci = mapImpronteFile.get(impronta);
					if (lFiles.get(keyMapFileUnivoci) != null && !"".equals(lFiles.get(keyMapFileUnivoci))) {
						uriFinale = (String) lFiles.get(keyMapFileUnivoci);
					} else {
						/**
						 * Manca il riferimento all'allegato in tabella, quindi tutte le sue info non sono presenti. Si procede al recupero delle stesse tramite
						 * l'ausilio di FOP.
						 */
						Map<String, PostaElettronicaRetriewAttachBean> mapFiles = recuperaInfoAllegatoWithFOP(lFiles);
						Iterator<String> ite2 = mapFiles.keySet().iterator();
						while (ite2.hasNext()) {
							String keyMapFiles = ite2.next();
							PostaElettronicaRetriewAttachBean postaElettronicaRetriewAttachBean = mapFiles.get(keyMapFiles);
							String improntaFileAllegTemp = postaElettronicaRetriewAttachBean.getMimeTypeFirmaBean().getImpronta();
							if (impronta.equals(improntaFileAllegTemp)) {
								uriFinale = postaElettronicaRetriewAttachBean.getUriFile();
							}
						}
					}
				}
			} else {
				uriFinale = (String) lFiles.get(displayFilename);
			}
		} catch (Exception e) {
			throw new StoreException("File non trovato tra gli attach");
		}
		return uriFinale;
	}

	/**
	 * 
	 * Viene recuperato per ogni singolo file allegato l'uri tramite il match tra nome_file + dimensione. Se l'uri non viene recuperato dalla mappa, allora
	 * viene calcolata l'impronta dell'allegato recuperata tramite il file,l'algoritmo ed l'encoding.Viene quindi recuperato il relativo uri valorizzato del
	 * singolo attachment.
	 */
	private String recuperaUriFileAllegato(Map<String, Object> lFiles, String displayFilename, Integer dimensione, AllegatiOutBean allegato)
			throws StoreException, StorageException {
		String uriFinale = null;
		CalcolaImpronteService calcolaImpronteService = new CalcolaImpronteService();
		try {
			if (!"__TestoEmail.txt".equalsIgnoreCase(displayFilename)) {
				String uriFile = (String) lFiles.get(displayFilename.concat(";").concat(dimensione.toString()));
				if (uriFile != null) {
					uriFinale = uriFile;
				} else {

					String algoritmoAttach = allegato.getAlgoritmoImpronta();
					String encodingAttach = allegato.getEncodingImpronta();
					String impronta = allegato.getImpronta();

					Map<String, String> listImpronteFile = new HashMap<String, String>();
					Iterator<String> ite = lFiles.keySet().iterator();
					while (ite.hasNext()) {
						String key = ite.next();
						Object value = lFiles.get(key);
						File fileTemp = StorageImplementation.getStorage().extractFile((String) value);
						String improntaFile = calcolaImpronteService.calcolaImprontaWithoutFileOp(fileTemp, algoritmoAttach, encodingAttach);
						listImpronteFile.put(improntaFile, key);
					}

					String keyMapFileUnivoci = listImpronteFile.get(impronta);
					if (lFiles.get(keyMapFileUnivoci) != null && !"".equals(lFiles.get(keyMapFileUnivoci))) {
						uriFinale = (String) lFiles.get(keyMapFileUnivoci);
					} else {
						/**
						 * Manca il riferimento all'allegato in tabella, quindi tutte le sue info non sono presenti. Si procede al recupero delle stesse tramite
						 * l'ausilio di FOP.
						 */
						Map<String, PostaElettronicaRetriewAttachBean> mapFiles = recuperaInfoAllegatoWithFOP(lFiles);
						Iterator<String> ite2 = mapFiles.keySet().iterator();
						while (ite2.hasNext()) {
							String keyMapFiles = ite2.next();
							PostaElettronicaRetriewAttachBean postaElettronicaRetriewAttachBean = mapFiles.get(keyMapFiles);
							String improntaFileAllegTemp = postaElettronicaRetriewAttachBean.getMimeTypeFirmaBean().getImpronta();
							if (impronta.equals(improntaFileAllegTemp)) {
								uriFinale = postaElettronicaRetriewAttachBean.getUriFile();
							}
						}
					}
				}
			} else {
				uriFinale = (String) lFiles.get(displayFilename);
			}
		} catch (Exception e) {
			throw new StoreException("File non trovato tra gli attach");
		}
		return uriFinale;
	}

	private Map<String, PostaElettronicaRetriewAttachBean> recuperaInfoAllegatoWithFOP(Map<String, Object> mapFileUnivoci) throws Exception {

		Map<String, PostaElettronicaRetriewAttachBean> mapFiles = new HashMap<String, PostaElettronicaRetriewAttachBean>();

		Iterator<String> ite = mapFileUnivoci.keySet().iterator();
		while (ite.hasNext()) {
			String key = ite.next();
			String uriFileAllegato = (String) mapFileUnivoci.get(key);
			String nomeFile[] = key.split(";");
			File fileTemp = StorageImplementation.getStorage().extractFile(uriFileAllegato);

			MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
			lMimeTypeFirmaBean = new InfoFileUtility().getInfoFromFile(fileTemp.toURI().toString(), nomeFile[0], false, null);
			if (lMimeTypeFirmaBean == null || StringUtils.isBlank(lMimeTypeFirmaBean.getImpronta())) {
				throw new Exception("Si è verificato un errore durante il controllo del file allegato " + nomeFile[0]);
			}
			PostaElettronicaRetriewAttachBean lPostaElettronicaRetriewAttachBean = new PostaElettronicaRetriewAttachBean();
			lPostaElettronicaRetriewAttachBean.setMimeTypeFirmaBean(lMimeTypeFirmaBean);
			lPostaElettronicaRetriewAttachBean.setUriFile(uriFileAllegato);

			mapFiles.put(key, lPostaElettronicaRetriewAttachBean);
		}
		return mapFiles;
	}

	private boolean isNotNull(Object beanObject) throws Exception {
		if (beanObject == null)
			return false;
		Map<String, Object> lMap = BeanUtilsBean2.getInstance().getPropertyUtils().describe(beanObject);
		lMap.remove("class");
		if (lMap.size() == 0)
			return false;
		for (String lString : lMap.keySet()) {
			if (lMap.get(lString) != null) {
				return true;
			}
		}
		return false;
	}

	public String calcolaImpronta(String fileUrl, String displayFilename) throws Exception {
		InfoFileUtility lInfoFileUtility = new InfoFileUtility();
		return lInfoFileUtility.calcolaImpronta(fileUrl, displayFilename);
	}
		
	public boolean isAttivoProtocolloWizard(ProtocollazioneBean beanDettaglio) {
		if(beanDettaglio == null || beanDettaglio.getIdUd() == null || StringUtils.isNotBlank(beanDettaglio.getSupportoOriginale())) {
			return ParametriDBUtil.getParametroDBAsBoolean(session, "ATTIVA_PROTOCOLLO_WIZARD");
		}
		return false;
	}
	
	public boolean isAttivoEsibenteSenzaWizard() {
		return ParametriDBUtil.getParametroDBAsBoolean(session, "ATTIVA_ESIBENTE_SENZA_WIZARD");
	}
	
	public boolean isAttivoInteressatiSenzaWizard() {
		return ParametriDBUtil.getParametroDBAsBoolean(session, "ATTIVA_INTERESSATI_SENZA_WIZARD");
	}
	
	public boolean isAttivoAltreVieSenzaWizard() {
		return ParametriDBUtil.getParametroDBAsBoolean(session, "ATTIVA_VIE_SENZA_WIZARD");
	}
	
	public boolean isPropostaAtto(ProtocollazioneBean beanDettaglio) {
		if(beanDettaglio != null && beanDettaglio.getFlgPropostaAtto() != null && beanDettaglio.getFlgPropostaAtto()) {
			return true;
		}
		return false;
	}
	
	public boolean isRichiestaAccessoAtti(ProtocollazioneBean beanDettaglio) {
		if(beanDettaglio != null && beanDettaglio.getFlgRichiestaAccessoAtti() != null && beanDettaglio.getFlgRichiestaAccessoAtti()) {
			return true;
		}
		return false;
	}
	
	public boolean isRichiestaAccessoAttiMittenteInterno(ProtocollazioneBean beanDettaglio) {
		return (beanDettaglio != null && beanDettaglio.getFlgRichiestaAccessoAtti() != null && beanDettaglio.getFlgRichiestaAccessoAtti() && TipoRichiedente.RICH_INTERNO.getValue().equalsIgnoreCase(beanDettaglio.getTipoRichiedente()));
	}
	
	public boolean isAttivaDecompressione(){
		return ParametriDBUtil.getParametroDBAsBoolean(session, "NO_MULTIPROT_EMAIL_IN") &&
			   ParametriDBUtil.getParametroDBAsBoolean(session, "DECOMP_ALLEGATI_MAIL_ZIP");
	}
		
	// Verifico se il nr. dei file non supera la soglia consentita
    public boolean isAttivaDecompressioneSogliaConsentita(File archive, String mimeType ) throws Exception {
		
		boolean ret = true;
		
		// Se il parametro e' valorizzato trovo il il nr. di file contenuti nel file zip
		if (StringUtils.isNotBlank(ParametriDBUtil.getParametroDB(session, "SOGLIA_CONT_DECOMP_ALLEGATI_MAIL_ZIP"))) {
			int SOGLIA_CONT_DECOMP_ALLEGATI_MAIL_ZIP = Integer.parseInt(ParametriDBUtil.getParametroDB(session, "SOGLIA_CONT_DECOMP_ALLEGATI_MAIL_ZIP")); 
			
			// Leggo il nr. di file contenuti nello zip
			int nrFileZip = ArchiveUtils.countElementsArchive(archive,mimeType);
				
			// La decompressione NON deve essere effettuata se :
			// l'archivio e' protetto (nrFileZip = -1) oppure
			// l'archivio non contiene file ( nrFileZip = 0) oppure
			// il nr. dei file supera la soglia allora non devo decomprimere
			if ((Integer.compare((nrFileZip), (-1)) == 0 )   ||
			    (Integer.compare((nrFileZip), (0))  == 0 )   ||
			    (Integer.compare((nrFileZip), (SOGLIA_CONT_DECOMP_ALLEGATI_MAIL_ZIP)) > 0 )
			    ){
				ret = false;
			}
		}
		
		return ret;
	}

	public boolean isClienteComuneMilano() {
		return ParametriDBUtil.getParametroDB(session, "CLIENTE") != null && 
			   ParametriDBUtil.getParametroDB(session, "CLIENTE").equalsIgnoreCase("CMMI");
	}

	public String getCodIstatComuneRif() {
		return ParametriDBUtil.getParametroDB(session, "ISTAT_COMUNE_RIF");
	}

	public String getNomeComuneRif() {
		return ParametriDBUtil.getParametroDB(session, "NOME_COMUNE_RIF");
	}
	
	private boolean attivaGestPdfEditabili() {
		String attivaGestPdfEditabiliInUpload = (String) session.getAttribute("ATTIVA_GEST_PDF_EDITABILI_IN_UPLOAD");
		return (attivaGestPdfEditabiliInUpload != null && "true".equalsIgnoreCase(attivaGestPdfEditabiliInUpload));
	}
	
	private boolean attivaGestPdfConCommenti() {
		String attivaGestPdfConCommenti = (String) session.getAttribute("ATTIVA_GEST_PDF_CON_COMMENTI");
		return (attivaGestPdfConCommenti != null && "true".equalsIgnoreCase(attivaGestPdfConCommenti));
	}
	
	private boolean attivaGestPdfCorrotti() {
		String attivaGestPdfEditabiliInUpload = (String) session.getAttribute("ATTIVA_CONV_IMG_PDF_CORROTTI");
		return (attivaGestPdfEditabiliInUpload != null && "true".equalsIgnoreCase(attivaGestPdfEditabiliInUpload));
	}

	/*
	 * Chiamata a FileOp per il recupero di infoFileVerPreFirma & infoFileVerPreFirmaOmissis nel caso in cui l'impronta dell'allegato vers. integrale è diversa da improntaVerPreFirma e/o l'impronta dell'allegato vers. omissis è diversa da improntaVerPreFirmaOmissis
	 * la dobbiamo lasciare SOLO nel caso di Aosta e Mauriziano, quindi solo se ATTIVA_NUOVA_PROPOSTA_ATTO_2 è false o non valorizzato.
	 */
	private boolean skipRecuperoInfoFileVerPreFirmaFromFOP() {		
		return ParametriDBUtil.getParametroDBAsBoolean(session, "SKIP_FOP_VER_PREFIRMA_IN_NUOVA_PROPOSTA_ATTO_2") && ParametriDBUtil.getParametroDBAsBoolean(session, "ATTIVA_NUOVA_PROPOSTA_ATTO_2");
	}

}