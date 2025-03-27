/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.server.pratiche.datasource;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreAdddocBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreUpddocudBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.AzioneIstruttoriaPubblicazioneBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AllegatoProtocolloBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.ProtocollazioneBean;
import it.eng.client.DmpkCoreAdddoc;
import it.eng.client.DmpkCoreUpddocud;
import it.eng.client.GestioneDocumenti;
import it.eng.client.PubblicazioniImpl;
import it.eng.client.SalvataggioFile;
import it.eng.core.performance.PerformanceLogger;
import it.eng.document.function.bean.AllegatiAddDocOutBean;
import it.eng.document.function.bean.AttachWSBean;
import it.eng.document.function.bean.CreaModDocumentoInBean;
import it.eng.document.function.bean.DocumentoCollegato;
import it.eng.document.function.bean.FileInfoBean;
import it.eng.document.function.bean.FileSavedIn;
import it.eng.document.function.bean.FileSavedOut;
import it.eng.document.function.bean.Flag;
import it.eng.document.function.bean.GenericFile;
import it.eng.document.function.bean.PubblicazioneListaAllegatiAvvio;
import it.eng.document.function.bean.PubblicazioneNotificaEsitoRequest;
import it.eng.document.function.bean.PubblicazioneResponse;
import it.eng.document.function.bean.RebuildedFile;
import it.eng.document.function.bean.TipoFile;
import it.eng.document.function.bean.VersionaDocumentoInBean;
import it.eng.document.function.bean.VersionaDocumentoOutBean;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.DocumentConfiguration;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.server.StringSplitterServer;
import it.eng.utility.ui.servlet.bean.Firmatari;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;
import it.eng.xml.XmlUtilitySerializer;


@Datasource(id = "AzioneIstruttoriaPubblicazioneDataSource")
public class AzioneIstruttoriaPubblicazioneDataSource extends AbstractServiceDataSource<AzioneIstruttoriaPubblicazioneBean, AzioneIstruttoriaPubblicazioneBean>{
	
	private static Logger logger = Logger.getLogger(AzioneIstruttoriaPubblicazioneDataSource.class);
	
	public static final String _AVVIO_COMPARATIVO_ACTION = "Avvio comparativo";
	public static final String _AVVIO_ACTION = "Avvio";
	public static final String _PROSEGUIMENTO_ISTRUTTORIA_CON_INTERRUZIONE_TERMINI_ACTION = "Proseguimento istruttoria con interruzione termini";
	public static final String _PROSEGUIMENTO_ISTRUTTORIA_SENZA_INTERRUZIONE_TERMINI_ACTION = "Proseguimento istruttoria senza interruzione termini";
	public static final String _RIPUBBLICAZIONE_ACTION = "Ripubblicazione";
	public static final String _PUBBLICAZIONE_ACTION = "Pubblicazione";

	@Override
	public AzioneIstruttoriaPubblicazioneBean call(AzioneIstruttoriaPubblicazioneBean bean) throws Exception {
		
		try {
			String azione = bean.getAzione() != null ? bean.getAzione() : "";
			// Gestisco le varie azioni
			switch (azione) {
				case _AVVIO_COMPARATIVO_ACTION:
					avvioComparativoAction(bean);
					break;
				case _AVVIO_ACTION:
					avvioAction(bean);
					break;
				case _PROSEGUIMENTO_ISTRUTTORIA_CON_INTERRUZIONE_TERMINI_ACTION:
					proseguimentoIstruttoriaConInterruzioneTerminiAction(bean);
					break;
				case _PROSEGUIMENTO_ISTRUTTORIA_SENZA_INTERRUZIONE_TERMINI_ACTION:
					proseguimentoIstruttoriaSenzaInterruzioneTerminiAction(bean);
					break;
				case _RIPUBBLICAZIONE_ACTION:
					ripubblicazioneAction(bean);
					break;
				case _PUBBLICAZIONE_ACTION:
					pubblicazioneAction(bean);
					break;
				default:
					// Dare un errore di azione non valida
					throw new StoreException("Azione non valida");
			}
		} catch(Exception e) {
			bean.setErrore(e.getMessage());
		}
		return bean;
	}
	
	protected PubblicazioneResponse callRestWSPubblicazioneSUA(PubblicazioneNotificaEsitoRequest input) throws Exception {
		
		PubblicazioneResponse output = null;
		try {
			PerformanceLogger lPerformanceLogger = new PerformanceLogger("Chiamata al servizio del SUA NotificaEsito idUd: " + input.getIdUD());
			lPerformanceLogger.start();		
			output = new PubblicazioniImpl().notificaEsito(getLocale(), input);
			lPerformanceLogger.end();		
		} catch(Exception e) {
			String errorMessage = "Si è verificato un'errore durante la chiamata al SUA";
			logger.error(errorMessage + ": " + e.getMessage(), e);
			throw new StoreException(errorMessage);
		}
		if (!output.isOk()) {
			String errorMessage = "Si è verificato un'errore durante la chiamata al SUA";
			if(StringUtils.isNotBlank(output.getErrorDescription())) {
				errorMessage += ": " + output.getErrorDescription();
			}
			logger.error(errorMessage);
			throw new StoreException(errorMessage);
		}
		
		return output;
	}
	
	protected AzioneIstruttoriaPubblicazioneBean addDocumentoAvvio(AzioneIstruttoriaPubblicazioneBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
			
		if(bean.getFileDocumento() != null && StringUtils.isNotBlank(bean.getFileDocumento().getUriFile())) {
			
			DmpkCoreAdddocBean lAdddocInput = new DmpkCoreAdddocBean();
			lAdddocInput.setCodidconnectiontokenin(token);
			lAdddocInput.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);			

			CreaModDocumentoInBean attributiUdDoc = new CreaModDocumentoInBean();
			attributiUdDoc.setOggetto(null);
			attributiUdDoc.setNomeDocType(null);
			attributiUdDoc.setDataArrivo(null);
			
//			List<FolderCustom> listaFolderCustom = new ArrayList<FolderCustom>();
//			FolderCustom folderCustom = new FolderCustom();
//			folderCustom.setId(bean.getIdFolder());
//			listaFolderCustom.add(folderCustom);
//			attributiUdDoc.setFolderCustom(listaFolderCustom);
			
//			List<DocumentoCollegato> documentiCollegati = new ArrayList<DocumentoCollegato>();
//			DocumentoCollegato docCollegato = new DocumentoCollegato();
//			docCollegato.setIdUd(bean.getIdUd());
//			docCollegato.setcValue(null);
//			docCollegato.setScValue(null);
//			docCollegato.setPrcValue(null);
//			documentiCollegati.add(docCollegato);
//			attributiDoc.setDocCollegato(documentiCollegati);				
			
			if(bean.getListaAllegati() != null) {
				List<AttachWSBean> listaAttach = new ArrayList<AttachWSBean>();
				for(AllegatoProtocolloBean allegato : bean.getListaAllegati()) {
					listaAttach.add(buildAttachWSBean(allegato));
				}
				attributiUdDoc.setListaAllegati(listaAttach);
			}
			
			XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
			lAdddocInput.setAttributiuddocxmlin(lXmlUtilitySerializer.bindXml(attributiUdDoc));	
			
			DmpkCoreAdddoc lDmpkCoreAdddoc = new DmpkCoreAdddoc();
			StoreResultBean<DmpkCoreAdddocBean> lAdddocOutput = lDmpkCoreAdddoc.execute(getLocale(), loginBean, lAdddocInput);
			if (StringUtils.isNotBlank(lAdddocOutput.getDefaultMessage())) {
				if (lAdddocOutput.isInError()) {
					throw new StoreException(lAdddocOutput);
				} else {
					addMessage(lAdddocOutput.getDefaultMessage(), "", MessageType.WARNING);
				}
			}
			
			ProtocollazioneBean lProtocollazioneBean = new ProtocollazioneBean();				
			lProtocollazioneBean.setIdDocPrimario(lAdddocOutput.getResultBean().getIddocout());
			lProtocollazioneBean.setUriFilePrimario(bean.getFileDocumento().getUriFile());
			lProtocollazioneBean.setNomeFilePrimario(bean.getFileDocumento().getNomeFile());
			
			DocumentConfiguration lDocumentConfiguration = (DocumentConfiguration) SpringAppContext.getContext().getBean("DocumentConfiguration");

			RebuildedFile lRebuildedFile = new RebuildedFile();
			lRebuildedFile.setIdDocumento(lProtocollazioneBean.getIdDocPrimario());
			lRebuildedFile.setFile(StorageImplementation.getStorage().extractFile(lProtocollazioneBean.getUriFilePrimario()));
			
			MimeTypeFirmaBean lMimeTypeFirmaBean = new MimeTypeFirmaBean();
			InfoFileUtility lFileUtility = new InfoFileUtility();
			lMimeTypeFirmaBean = lFileUtility.getInfoFromFile(lRebuildedFile.getFile().toURI().toString(), lRebuildedFile.getFile().getName(), false, null);
			
			FileInfoBean lFileInfoBean = new FileInfoBean();
			lFileInfoBean.setTipo(TipoFile.PRIMARIO);
			GenericFile lGenericFile = new GenericFile();
			setProprietaGenericFile(lGenericFile, lMimeTypeFirmaBean);
			lGenericFile.setMimetype(lMimeTypeFirmaBean.getMimetype());
			lGenericFile.setDisplayFilename(lProtocollazioneBean.getNomeFilePrimario());
			lGenericFile.setImpronta(lMimeTypeFirmaBean.getImpronta());
			lGenericFile.setImprontaFilePreFirma(lMimeTypeFirmaBean.getImprontaFilePreFirma());
			lGenericFile.setAlgoritmo(lDocumentConfiguration.getAlgoritmo().value());
			lGenericFile.setEncoding(lDocumentConfiguration.getEncoding().value());
			if (lMimeTypeFirmaBean.isDaScansione()) {
				lGenericFile.setDaScansione(Flag.SETTED);
				lGenericFile.setDataScansione(new Date());
				lGenericFile.setIdUserScansione(loginBean.getIdUser() + "");
			}
			lFileInfoBean.setAllegatoRiferimento(lGenericFile);

			lRebuildedFile.setInfo(lFileInfoBean);

			VersionaDocumentoInBean lVersionaDocumentoInBean = new VersionaDocumentoInBean();
			BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(lVersionaDocumentoInBean, lRebuildedFile);

			GestioneDocumenti lGestioneDocumenti = new GestioneDocumenti();
			VersionaDocumentoOutBean lVersionaDocumentoOutput = lGestioneDocumenti.versionadocumento(getLocale(), loginBean, lVersionaDocumentoInBean);

			if (lVersionaDocumentoOutput.getDefaultMessage() != null) {
				throw new StoreException(lVersionaDocumentoOutput);
			}
			
			bean.setIdUdAvvio(lAdddocOutput.getResultBean().getIdudout() != null ? String.valueOf(lAdddocOutput.getResultBean().getIdudout().longValue()) : null);
			bean.setIdDocAvvio(lAdddocOutput.getResultBean().getIddocout() != null ? String.valueOf(lAdddocOutput.getResultBean().getIddocout().longValue()) : null);
			bean.setNomeFileDocAvvio(bean.getFileDocumento().getNomeFile());
			
			List<AllegatiAddDocOutBean> listaAllegatiAvvio = null;
			if (lAdddocOutput.getResultBean().getUrixmlout() != null) {
				listaAllegatiAvvio = XmlListaUtility.recuperaLista(lAdddocOutput.getResultBean().getUrixmlout(), AllegatiAddDocOutBean.class);
			}
			bean.setListaAllegatiAvvio(listaAllegatiAvvio);
		}
		
		return bean;	
	}
	
	protected AttachWSBean buildAttachWSBean(AllegatoProtocolloBean allegato) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		AttachWSBean attachWSBean = new AttachWSBean();
		
		File lFile = StorageImplementation.getStorage().extractFile(allegato.getUriFileAllegato());
		
		FileSavedIn in = new FileSavedIn();
		in.setSaved(lFile);
		
		FileSavedOut out = new SalvataggioFile().savefile(getLocale(), loginBean, in);			
		
		if(out.getErrorInSaved() != null) {
			logger.error("Si è verificato un errore durante il salvataggio del file  allegato " + allegato.getNroAllegato() + " in repository: " + out.getErrorInSaved());
			throw new StoreException("Si è verificato un errore durante il salvataggio del file allegato " + allegato.getNroAllegato() + " in repository");
		}

		attachWSBean.setUri(out.getUri());
		attachWSBean.setDisplayFilename(allegato.getNomeFileAllegato());
		attachWSBean.setNumeroAttach(allegato.getNroAllegato());
		attachWSBean.setNomeTipologia(allegato.getDescTipoFileAllegato());
		attachWSBean.setDescrizione(allegato.getDescrizioneFileAllegato());
		attachWSBean.setDimensione(allegato.getInfoFile() != null ? new BigDecimal(allegato.getInfoFile().getBytes()) : null);
		attachWSBean.setImpronta(allegato.getInfoFile() != null ? allegato.getInfoFile().getImpronta() : null);
		attachWSBean.setAlgoritmo(allegato.getInfoFile() != null ? allegato.getInfoFile().getAlgoritmo() : null);
		attachWSBean.setEncodingImpronta(allegato.getInfoFile() != null ? allegato.getInfoFile().getEncoding() : null);
		attachWSBean.setMimetype(allegato.getInfoFile() != null ? allegato.getInfoFile().getMimetype() : null);
		attachWSBean.setTipoFirma(allegato.getInfoFile() != null ? allegato.getInfoFile().getTipoFirma() : null);
		if (allegato.getInfoFile() != null && allegato.getInfoFile().getFirmatari() != null) {
			String firmatari = "";
			for (String firmatarioInstance : allegato.getInfoFile().getFirmatari()) {
				firmatari = firmatari + firmatarioInstance + ";";
			}
			attachWSBean.setFirmatari(firmatari);
			attachWSBean.setFlgFirmato("1");
		} else {
			attachWSBean.setFlgFirmato("0");
		}
		attachWSBean.setInfoVerificaFirma(allegato.getInfoFile() != null ? allegato.getInfoFile().getInfoFirma() : null);
		attachWSBean.setIdSistemaProvenienza(null);
		attachWSBean.setDataFirmaBustaCrittografica(null);
		if (allegato.getInfoFile() != null && allegato.getInfoFile().getInfoFirmaMarca() != null) {
			attachWSBean.setInfoVerificaMarca(allegato.getInfoFile().getInfoFirmaMarca().getInfoMarcaTemporale());
			attachWSBean.setDataOraMarca(allegato.getInfoFile().getInfoFirmaMarca().getDataOraMarcaTemporale());
			attachWSBean.setTipoMarca(allegato.getInfoFile().getInfoFirmaMarca().getTipoMarcaTemporale());
			attachWSBean.setFlgMarcaTemporaleNonValida(allegato.getInfoFile().getInfoFirmaMarca().isMarcaTemporaleNonValida() ? Flag.SETTED : Flag.NOT_SETTED);
			attachWSBean.setFlgFirmaCrittograficaNonValida(allegato.getInfoFile().getInfoFirmaMarca().isFirmeNonValideBustaCrittografica() ? Flag.SETTED : Flag.NOT_SETTED);
		}
		// Prendo i firmatari
		String listDataOraEmissioneCertificatoFirma = "";
		String listDataOraScadenzaCertificatoFirma = "";	
		String listTipoFirmaQA = "";
		String listCfFirmatario = "";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		if (allegato.getInfoFile() != null && allegato.getInfoFile().getBuste() != null) { 
			for (Firmatari bustaFileFirmato : allegato.getInfoFile().getBuste()) {
				if (bustaFileFirmato!=null && bustaFileFirmato.getDataFirma() != null){
					// Leggo la data emissione certificato firmatario
					if (bustaFileFirmato.getDataEmissione()!=null){
						String dataEmissione = sdf.format(bustaFileFirmato.getDataEmissione());
						listDataOraEmissioneCertificatoFirma = listDataOraEmissioneCertificatoFirma + dataEmissione + ";";
					}
					// Leggo la data scadenza certificato firmatario
					if (bustaFileFirmato.getDataScadenza()!=null){
						String dataScadenza = sdf.format(bustaFileFirmato.getDataScadenza());
						listDataOraScadenzaCertificatoFirma = listDataOraScadenzaCertificatoFirma + dataScadenza + ";";
					}
					// Leggo il tipo di firma
					if (bustaFileFirmato.getTipoFirmaQA()!=null){
						String tipoFirmaQA = bustaFileFirmato.getTipoFirmaQA();
						listTipoFirmaQA = listTipoFirmaQA + tipoFirmaQA + ";";
					}
					// Leggo il cf del firmatario
					if (bustaFileFirmato.getCfFirmatario()!=null){
						String cfFirmatario = bustaFileFirmato.getCfFirmatario();
						listCfFirmatario = listCfFirmatario + cfFirmatario + ";";
					}
				}
			}
		}
		attachWSBean.setDataOraEmissioneCertificatoFirma(listDataOraEmissioneCertificatoFirma);
		attachWSBean.setDataOraScadenzaCertificatoFirma(listDataOraScadenzaCertificatoFirma);
		attachWSBean.setTipoFirmaQA(listTipoFirmaQA);
		attachWSBean.setCfFirmatario(listCfFirmatario);
		
		return attachWSBean;
	}
	
	protected void updateDocUd(String idDoc, CreaModDocumentoInBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		DmpkCoreUpddocudBean input = new DmpkCoreUpddocudBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);			
		input.setFlgtipotargetin("D");
		input.setIduddocin(StringUtils.isNotBlank(idDoc) ? new BigDecimal(idDoc) : null);
		
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		input.setAttributiuddocxmlin(lXmlUtilitySerializer.bindXml(bean));	
			
		DmpkCoreUpddocud dmpkCoreUpddocud = new DmpkCoreUpddocud();
		StoreResultBean<DmpkCoreUpddocudBean> output = dmpkCoreUpddocud.execute(getLocale(),loginBean, input);
		
		if (output.isInError()) {
			throw new StoreException(output);
		}	
	}
	
	public AzioneIstruttoriaPubblicazioneBean avvioComparativoAction(AzioneIstruttoriaPubblicazioneBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		if(StringUtils.isBlank(bean.getIdUdAvvio())) {
			try {
				addDocumentoAvvio(bean);
				bean.setFlgToReload(true);
			} catch(StoreException se) {
				throw se;
			} catch(Exception e) {
				logger.error(e.getMessage(), e);
				throw new StoreException("Si è verificato un errore in fase di creazione del documento di avvio");
			}
		}
		
		try {
			CreaModDocumentoInBean lCreaModDocumentoInBean = new CreaModDocumentoInBean();			
			lCreaModDocumentoInBean.setIdDocAvvioProcConcADSP(bean.getIdDocAvvio());
			lCreaModDocumentoInBean.setTipoAvvioProcConcADSP("AVVIO_COMPARATIVO");
			lCreaModDocumentoInBean.setSceltaGiorniTermineProcConcADSP(bean.getSceltaGiorni());
			lCreaModDocumentoInBean.setFlgTerminiModificatiProcConcADSP("1");
			updateDocUd(bean.getIdDoc(), lCreaModDocumentoInBean);
		} catch(StoreException se) {
			throw se;
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new StoreException("Si è verificato un errore in fase di aggiornamento dei metadati relativi al documento istanza");
		}
		
		try {
			PubblicazioneNotificaEsitoRequest input = new PubblicazioneNotificaEsitoRequest();	
			input.setCfOperatore(loginBean.getCodFiscale());
			input.setIdUD(bean.getIdUd());
			input.setIdUDAvvio(bean.getIdUdAvvio());
			input.setIdDocAvvio(bean.getIdDocAvvio());
			input.setNomeFileDocAvvio(bean.getNomeFileDocAvvio());
			if(bean.getListaAllegatiAvvio() != null) {
				List<PubblicazioneListaAllegatiAvvio> listaAllegatiAvvio = new ArrayList<PubblicazioneListaAllegatiAvvio>();
				for(AllegatiAddDocOutBean lAllegatiAddDocOutBean : bean.getListaAllegatiAvvio()) {
					PubblicazioneListaAllegatiAvvio lPubblicazioneListaAllegatiAvvio = new PubblicazioneListaAllegatiAvvio();
					lPubblicazioneListaAllegatiAvvio.setNumeroAllegato(lAllegatiAddDocOutBean.getNroAllegato());
					lPubblicazioneListaAllegatiAvvio.setIdDoc(lAllegatiAddDocOutBean.getIdDoc());
					lPubblicazioneListaAllegatiAvvio.setNomeFileDoc(lAllegatiAddDocOutBean.getDisplayFileName());
					lPubblicazioneListaAllegatiAvvio.setTipoDoc(lAllegatiAddDocOutBean.getNomeDocType());
					listaAllegatiAvvio.add(lPubblicazioneListaAllegatiAvvio);
				}
				input.setListaAllegatiAvvio(listaAllegatiAvvio);
			}
			input.setTipoAvvio("AVVIO_COMPARATIVO");
			input.setDataFinePubblicazione(StringUtils.isNotBlank(bean.getDataFinePubblicazione()) ? new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss").format(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(bean.getDataFinePubblicazione())) : null);
			input.setGiorniTermineProcedimento(StringUtils.isNotBlank(bean.getSceltaGiorni()) ? Long.parseLong(bean.getSceltaGiorni()) : null);
			input.setModificatiTermini(true);
			if(StringUtils.isNotBlank(bean.getCodPraticheConcorrenti())) {
				List<String> elencoPraticheConcorrenti = new ArrayList<String>();
				StringSplitterServer st = new StringSplitterServer(bean.getCodPraticheConcorrenti(), ";");
				while (st.hasMoreElements()) {
					elencoPraticheConcorrenti.add(st.nextToken());
				} 
				input.setElencoPraticheConcorrenti(elencoPraticheConcorrenti);
			}
			callRestWSPubblicazioneSUA(input);
		} catch(StoreException se) {
			throw se;
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new StoreException("Si è verificato un errore durante la chiamata al SUA per inviare avvio ed avanzare gli iter della/e pratiche coinvolte");
		}
		
		try {
			CreaModDocumentoInBean lCreaModDocumentoInBean = new CreaModDocumentoInBean();			
			lCreaModDocumentoInBean.setCodStatoDett("E9-1");
			updateDocUd(bean.getIdDoc(), lCreaModDocumentoInBean);
		} catch(StoreException se) {
			throw se;
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new StoreException("Si è verificato un errore in fase di aggiornamento dello stato dell'unità documentaria");
		}
		
		return bean;
	}
	
	public AzioneIstruttoriaPubblicazioneBean avvioAction(AzioneIstruttoriaPubblicazioneBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		if(StringUtils.isBlank(bean.getIdUdAvvio())) {
			try {
				addDocumentoAvvio(bean);
				bean.setFlgToReload(true);
			} catch(Exception e) {
				logger.error(e.getMessage(), e);
				throw new StoreException("Si è verificato un errore in fase di creazione del documento di avvio");
			}
		}
		
		try {
			CreaModDocumentoInBean lCreaModDocumentoInBean = new CreaModDocumentoInBean();			
			lCreaModDocumentoInBean.setIdDocAvvioProcConcADSP(bean.getIdDocAvvio());
			lCreaModDocumentoInBean.setTipoAvvioProcConcADSP("AVVIO_AL_TERMINE");
			lCreaModDocumentoInBean.setSceltaGiorniTermineProcConcADSP(bean.getSceltaGiorni());
			lCreaModDocumentoInBean.setFlgTerminiModificatiProcConcADSP("1");
			updateDocUd(bean.getIdDoc(), lCreaModDocumentoInBean);
		} catch(StoreException se) {
			throw se;
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new StoreException("Si è verificato un errore in fase di aggiornamento dei metadati relativi al documento istanza");
		}
		
		try {
			PubblicazioneNotificaEsitoRequest input = new PubblicazioneNotificaEsitoRequest();	
			input.setCfOperatore(loginBean.getCodFiscale());
			input.setIdUD(bean.getIdUd());
			input.setIdUDAvvio(bean.getIdUdAvvio());
			input.setIdDocAvvio(bean.getIdDocAvvio());
			input.setNomeFileDocAvvio(bean.getNomeFileDocAvvio());
			if(bean.getListaAllegatiAvvio() != null) {
				List<PubblicazioneListaAllegatiAvvio> listaAllegatiAvvio = new ArrayList<PubblicazioneListaAllegatiAvvio>();
				for(AllegatiAddDocOutBean lAllegatiAddDocOutBean : bean.getListaAllegatiAvvio()) {
					PubblicazioneListaAllegatiAvvio lPubblicazioneListaAllegatiAvvio = new PubblicazioneListaAllegatiAvvio();
					lPubblicazioneListaAllegatiAvvio.setNumeroAllegato(lAllegatiAddDocOutBean.getNroAllegato());
					lPubblicazioneListaAllegatiAvvio.setIdDoc(lAllegatiAddDocOutBean.getIdDoc());
					lPubblicazioneListaAllegatiAvvio.setNomeFileDoc(lAllegatiAddDocOutBean.getDisplayFileName());
					lPubblicazioneListaAllegatiAvvio.setTipoDoc(lAllegatiAddDocOutBean.getNomeDocType());
					listaAllegatiAvvio.add(lPubblicazioneListaAllegatiAvvio);
				}
				input.setListaAllegatiAvvio(listaAllegatiAvvio);
			}
			input.setTipoAvvio("AVVIO_AL_TERMINE");
			input.setDataFinePubblicazione(StringUtils.isNotBlank(bean.getDataFinePubblicazione()) ? new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss").format(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(bean.getDataFinePubblicazione())) : null);
			input.setGiorniTermineProcedimento(StringUtils.isNotBlank(bean.getSceltaGiorni()) ? Long.parseLong(bean.getSceltaGiorni()) : null);
			input.setModificatiTermini(true);
			if(StringUtils.isNotBlank(bean.getCodPraticheConcorrenti())) {
				List<String> elencoPraticheConcorrenti = new ArrayList<String>();
				StringSplitterServer st = new StringSplitterServer(bean.getCodPraticheConcorrenti(), ";");
				while (st.hasMoreElements()) {
					elencoPraticheConcorrenti.add(st.nextToken());
				} 
				input.setElencoPraticheConcorrenti(elencoPraticheConcorrenti);
			}
			callRestWSPubblicazioneSUA(input);
		} catch(StoreException se) {
			throw se;
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new StoreException("Si è verificato un errore durante la chiamata al SUA per inviare avvio ed avanzare gli iter della/e pratiche coinvolte");
		}
		
		try {
			CreaModDocumentoInBean lCreaModDocumentoInBean = new CreaModDocumentoInBean();			
			lCreaModDocumentoInBean.setCodStatoDett("E9");
			updateDocUd(bean.getIdDoc(), lCreaModDocumentoInBean);
		} catch(StoreException se) {
			throw se;
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new StoreException("Si è verificato un errore in fase di aggiornamento dello stato dell'unità documentaria");
		}
		
		return bean;
	}
	
	public AzioneIstruttoriaPubblicazioneBean proseguimentoIstruttoriaConInterruzioneTerminiAction(AzioneIstruttoriaPubblicazioneBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		try {
			CreaModDocumentoInBean lCreaModDocumentoInBean = new CreaModDocumentoInBean();			
			lCreaModDocumentoInBean.setTipoAvvioProcConcADSP("FINE_PUBBLICAZIONE");
			lCreaModDocumentoInBean.setSceltaGiorniTermineProcConcADSP(bean.getSceltaGiorni());
			lCreaModDocumentoInBean.setFlgTerminiModificatiProcConcADSP("1");
			updateDocUd(bean.getIdDoc(), lCreaModDocumentoInBean);
		} catch(StoreException se) {
			throw se;
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new StoreException("Si è verificato un errore in fase di aggiornamento dei metadati relativi al documento istanza");
		}
		
		try {
			PubblicazioneNotificaEsitoRequest input = new PubblicazioneNotificaEsitoRequest();	
			input.setCfOperatore(loginBean.getCodFiscale());
			input.setIdUD(bean.getIdUd());
			input.setTipoAvvio("FINE_PUBBLICAZIONE");
			input.setDataFinePubblicazione(StringUtils.isNotBlank(bean.getDataFinePubblicazione()) ? new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss").format(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(bean.getDataFinePubblicazione())) : null);
			input.setGiorniTermineProcedimento(StringUtils.isNotBlank(bean.getSceltaGiorni()) ? Long.parseLong(bean.getSceltaGiorni()) : null);
			input.setModificatiTermini(true);
			if(StringUtils.isNotBlank(bean.getCodPraticheConcorrenti())) {
				List<String> elencoPraticheConcorrenti = new ArrayList<String>();
				StringSplitterServer st = new StringSplitterServer(bean.getCodPraticheConcorrenti(), ";");
				while (st.hasMoreElements()) {
					elencoPraticheConcorrenti.add(st.nextToken());
				} 
				input.setElencoPraticheConcorrenti(elencoPraticheConcorrenti);
			}
			callRestWSPubblicazioneSUA(input);
		} catch(StoreException se) {
			throw se;
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new StoreException("Si è verificato un errore durante la chiamata al SUA");
		}
		
		try {
			CreaModDocumentoInBean lCreaModDocumentoInBean = new CreaModDocumentoInBean();			
			lCreaModDocumentoInBean.setCodStatoDett("E9");
			updateDocUd(bean.getIdDoc(), lCreaModDocumentoInBean);
		} catch(StoreException se) {
			throw se;
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new StoreException("Si è verificato un errore in fase di aggiornamento dello stato dell'unità documentaria");
		}
			
		return bean;
	}
	
	public AzioneIstruttoriaPubblicazioneBean proseguimentoIstruttoriaSenzaInterruzioneTerminiAction(AzioneIstruttoriaPubblicazioneBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		try {
			CreaModDocumentoInBean lCreaModDocumentoInBean = new CreaModDocumentoInBean();			
			lCreaModDocumentoInBean.setTipoAvvioProcConcADSP("FINE_PUBBLICAZIONE");
			lCreaModDocumentoInBean.setFlgTerminiModificatiProcConcADSP("0");
			updateDocUd(bean.getIdDoc(), lCreaModDocumentoInBean);
		} catch(StoreException se) {
			throw se;
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new StoreException("Si è verificato un errore in fase di aggiornamento dei metadati relativi al documento istanza");
		}
		
		try {
			PubblicazioneNotificaEsitoRequest input = new PubblicazioneNotificaEsitoRequest();	
			input.setCfOperatore(loginBean.getCodFiscale());
			input.setIdUD(bean.getIdUd());
			input.setTipoAvvio("FINE_PUBBLICAZIONE");
			input.setDataFinePubblicazione(StringUtils.isNotBlank(bean.getDataFinePubblicazione()) ? new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss").format(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(bean.getDataFinePubblicazione())) : null);
			input.setModificatiTermini(false);
			if(StringUtils.isNotBlank(bean.getCodPraticheConcorrenti())) {
				List<String> elencoPraticheConcorrenti = new ArrayList<String>();
				StringSplitterServer st = new StringSplitterServer(bean.getCodPraticheConcorrenti(), ";");
				while (st.hasMoreElements()) {
					elencoPraticheConcorrenti.add(st.nextToken());
				} 
				input.setElencoPraticheConcorrenti(elencoPraticheConcorrenti);
			}
			callRestWSPubblicazioneSUA(input);
		} catch(StoreException se) {
			throw se;
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new StoreException("Si è verificato un errore durante la chiamata al SUA");
		}
		
		try {
			CreaModDocumentoInBean lCreaModDocumentoInBean = new CreaModDocumentoInBean();			
			lCreaModDocumentoInBean.setCodStatoDett("E9");
			updateDocUd(bean.getIdDoc(), lCreaModDocumentoInBean);
		} catch(StoreException se) {
			throw se;
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new StoreException("Si è verificato un errore in fase di aggiornamento dello stato dell'unità documentaria");
		}
		
		return bean;
	}
	
	public AzioneIstruttoriaPubblicazioneBean ripubblicazioneAction(AzioneIstruttoriaPubblicazioneBean bean) throws Exception {
	
		try {
			CreaModDocumentoInBean lCreaModDocumentoInBean = new CreaModDocumentoInBean();			
			lCreaModDocumentoInBean.setDtInizioPubblAlboBK(bean.getDataPubblDal());
			lCreaModDocumentoInBean.setNroGgPubblAlboBK(bean.getNumGiorniPubbl());
			updateDocUd(bean.getIdDoc(), lCreaModDocumentoInBean);
		} catch(StoreException se) {
			throw se;
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new StoreException("Si è verificato un errore in fase di aggiornamento dei metadati relativi al documento istanza");
		}
			
		return bean;
	}
	
	public AzioneIstruttoriaPubblicazioneBean pubblicazioneAction(AzioneIstruttoriaPubblicazioneBean bean) throws Exception {
		
		try {
			CreaModDocumentoInBean lCreaModDocumentoInBean = new CreaModDocumentoInBean();			
			lCreaModDocumentoInBean.setDtInizioPubblAlboBK(bean.getDataPubblDal());
			lCreaModDocumentoInBean.setNroGgPubblAlboBK(bean.getNumGiorniPubbl());
			updateDocUd(bean.getIdDoc(), lCreaModDocumentoInBean);
		} catch(StoreException se) {
			throw se;
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new StoreException("Si è verificato un errore in fase di aggiornamento dei metadati relativi al documento istanza");
		}
		
		return bean;
	}
	
	public AzioneIstruttoriaPubblicazioneBean collegaComeIstanzaConcorrente(AzioneIstruttoriaPubblicazioneBean bean) throws Exception {
		
		try {
			CreaModDocumentoInBean lCreaModDocumentoInBean = new CreaModDocumentoInBean();	
			List<DocumentoCollegato> documentiCollegati = new ArrayList<DocumentoCollegato>();
			DocumentoCollegato docCollegato = new DocumentoCollegato();
			docCollegato.setIdUd(bean.getIdUdDaCollegare());
			docCollegato.setcValue("C");
			docCollegato.setScValue("ISTCONC");
			docCollegato.setPrcValue(null);
			docCollegato.setMotiviCollegamento("istanze concorrenti");
			documentiCollegati.add(docCollegato);
			lCreaModDocumentoInBean.setDocCollegato(documentiCollegati);
			lCreaModDocumentoInBean.setAppendRelazioniVsUD("1");
			updateDocUd(bean.getIdDoc(), lCreaModDocumentoInBean);
		} catch(StoreException se) {
			throw se;
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new StoreException("Si è verificato un errore in fase di collegamento ad altra istanza come concorrente");
		}
		
		return bean;
	}
	
	public AzioneIstruttoriaPubblicazioneBean scollegaIstanzaConcorrente(AzioneIstruttoriaPubblicazioneBean bean) throws Exception {
		
		try {
			CreaModDocumentoInBean lCreaModDocumentoInBean = new CreaModDocumentoInBean();	
			lCreaModDocumentoInBean.setAzioneScollegaDaIstanzaSUAPadre("true");
			updateDocUd(bean.getIdDoc(), lCreaModDocumentoInBean);
		} catch(StoreException se) {
			throw se;
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw new StoreException("Si è verificato un errore in fase di scollegamento dall'istanza concorrente");
		}
		
		return bean;
	}

}
