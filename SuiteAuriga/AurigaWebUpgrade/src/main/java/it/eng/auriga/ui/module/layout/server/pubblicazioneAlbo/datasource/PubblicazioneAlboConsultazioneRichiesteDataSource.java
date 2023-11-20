/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;

import it.eng.auriga.compiler.FreeMarkerModelliUtil;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreAdddocBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreFindudBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreUpddocudBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.function.bean.FindRepositoryObjectBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.ArchivioXmlBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.ArchivioXmlBeanDeserializationHelper;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.CriteriAvanzati;
import it.eng.auriga.ui.module.layout.server.common.NroRecordTotBean;
import it.eng.auriga.ui.module.layout.server.common.SezioneCacheAttributiDinamici;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.ProtocolloUtility;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AllegatoProtocolloBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.DocCollegatoBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.MittenteProtBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.ProtocollazioneBean;
import it.eng.auriga.ui.module.layout.server.pubblicazioneAlbo.datasource.bean.PubblicazioneAlboConsultazioneRichiesteBean;
import it.eng.client.AurigaService;
import it.eng.client.DmpkCoreFindud;
import it.eng.client.DmpkCoreUpddocud;
import it.eng.client.GestioneDocumenti;
import it.eng.client.RecuperoDocumenti;
import it.eng.client.RecuperoFile;
import it.eng.document.function.bean.AllegatiBean;
import it.eng.document.function.bean.AttachAndPosizioneBean;
import it.eng.document.function.bean.CreaModDocumentoInBean;
import it.eng.document.function.bean.CreaModDocumentoOutBean;
import it.eng.document.function.bean.DocumentoCollegato;
import it.eng.document.function.bean.DocumentoXmlOutBean;
import it.eng.document.function.bean.FileExtractedIn;
import it.eng.document.function.bean.FileExtractedOut;
import it.eng.document.function.bean.FileInfoBean;
import it.eng.document.function.bean.FilePrimarioBean;
import it.eng.document.function.bean.Flag;
import it.eng.document.function.bean.GenericFile;
import it.eng.document.function.bean.MittentiDocumentoBean;
import it.eng.document.function.bean.RecuperaDocumentoInBean;
import it.eng.document.function.bean.RecuperaDocumentoOutBean;
import it.eng.document.function.bean.RegistroEmergenza;
import it.eng.document.function.bean.TipoFile;
import it.eng.document.function.bean.TipoMittente;
import it.eng.document.function.bean.TipoProvenienza;
import it.eng.jaxb.variabili.SezioneCache;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.DocumentConfiguration;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.storageutil.exception.StorageException;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.ExportBean;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AurigaAbstractFetchDatasource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.ParametriDBUtil;
import it.eng.utility.ui.user.UserUtil;
import it.eng.xml.XmlListaUtility;
import it.eng.xml.XmlUtilitySerializer;

@Datasource(id = "PubblicazioneAlboConsultazioneRichiesteDataSource")
public class PubblicazioneAlboConsultazioneRichiesteDataSource extends AurigaAbstractFetchDatasource<PubblicazioneAlboConsultazioneRichiesteBean> {

	@Override
	public String getNomeEntita() {
		return "pubblicazione_albo_consultazione_richieste";
	}
	
	@Override
	public PaginatorBean<PubblicazioneAlboConsultazioneRichiesteBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
				
		boolean overflow = false;
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		// Inizializzo l'INPUT
		FindRepositoryObjectBean lFindRepositoryObjectBean = createFindRepositoryObjectBean(criteria, loginBean);

		// Eseguo il servizio
		List<Object> resFinder = null;
		try {
				resFinder = AurigaService.getFind().findrepositoryobject(getLocale(), loginBean, lFindRepositoryObjectBean).getList();
		} catch (Exception e) {
				throw new StoreException(e.getMessage());
		}
		
		// leggo il risultato
		String xmlResultSetOut = (String) resFinder.get(0);
		String numTotRecOut = (String) resFinder.get(1);
		String numRecInPagOut = (String) resFinder.get(2);
		String xmlPercorsiOut = null;
		String dettagliCercaInFolderOut = null;
		String errorMessageOut = null;
		if (resFinder.size() > 3) {
			xmlPercorsiOut = (String) resFinder.get(3);
		}
		if (resFinder.size() > 4) {
			dettagliCercaInFolderOut = (String) resFinder.get(4);
		}
		if (resFinder.size() > 5) {
			errorMessageOut = (String) resFinder.get(5);
		}

		overflow = manageOverflow(errorMessageOut);
		
		List<ArchivioXmlBean> data = new ArrayList<ArchivioXmlBean>();
		List<PubblicazioneAlboConsultazioneRichiesteBean> dataResult = new ArrayList<PubblicazioneAlboConsultazioneRichiesteBean>();
		
		// Conversione ListaRisultati ==> EngResultSet
		if (xmlResultSetOut != null) {
			data = XmlListaUtility.recuperaLista(xmlResultSetOut, ArchivioXmlBean.class);
		}
		
		for(ArchivioXmlBean archivioBean : data) {
			PubblicazioneAlboConsultazioneRichiesteBean pubblicazioneBean = new PubblicazioneAlboConsultazioneRichiesteBean();
			pubblicazioneBean.setIdUdFolder(new BigDecimal(archivioBean.getIdUdFolder()));
			pubblicazioneBean.setTsRichiestaPubblicazione(archivioBean.getTsRichiestaPubblicazione());
			pubblicazioneBean.setRichiedentePubblicazione(archivioBean.getRichiedentePubblicazione());
			pubblicazioneBean.setTipo(archivioBean.getTipo());								
			pubblicazioneBean.setSegnatura(archivioBean.getSegnatura());
			pubblicazioneBean.setSegnaturaXOrd(archivioBean.getSegnaturaXOrd());
			pubblicazioneBean.setTsRegistrazione(archivioBean.getTsRegistrazione());
			pubblicazioneBean.setMittenti(archivioBean.getMittenti());
			pubblicazioneBean.setOggetto(archivioBean.getOggetto());
			pubblicazioneBean.setDataInizioPubblicazione(archivioBean.getDataInizioPubblicazione());
			pubblicazioneBean.setDataFinePubblicazione(archivioBean.getDataFinePubblicazione());
			pubblicazioneBean.setGiorniPubblicazione(archivioBean.getGiorniPubblicazione());
			pubblicazioneBean.setIdRichPubbl(archivioBean.getIdRichPubbl());
			dataResult.add(pubblicazioneBean);
		}
		
		// salvo i dati in sessione per renderli disponibili l'esportazione
		getSession().setAttribute(FETCH_SESSION_KEY, data);	
		
		PaginatorBean<PubblicazioneAlboConsultazioneRichiesteBean> lPaginatorBean = new PaginatorBean<PubblicazioneAlboConsultazioneRichiesteBean>();
		lPaginatorBean.setData(dataResult);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? data.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(data.size());
		lPaginatorBean.setOverflow(overflow);
		
		return lPaginatorBean;
	}
	
	@Override
	public PubblicazioneAlboConsultazioneRichiesteBean add(PubblicazioneAlboConsultazioneRichiesteBean bean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());	
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		DmpkCoreAdddocBean lAdddocInput = new DmpkCoreAdddocBean();
		lAdddocInput.setCodidconnectiontokenin(token);
		lAdddocInput.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		
		CreaModDocumentoInBean lCreaDocumentoInBean = new CreaModDocumentoInBean();
		
		// Dati atto
		List<RegistroEmergenza> lListRegistrazioniDaDare = new ArrayList<RegistroEmergenza>();
		if (bean.getTsRegistrazione() != null && bean.getNroRegNum() != null && (StringUtils.isNotBlank(bean.getSiglaRegNum()) || StringUtils.isNotBlank(bean.getTipoRegNum()))) {
			// Registrazioni da dare
			RegistroEmergenza lRegistroEmergenza = new RegistroEmergenza();
			lRegistroEmergenza.setFisso(bean.getTipoRegNum());
			lRegistroEmergenza.setRegistro(bean.getTipoRegNum().equals("PG") ? "" : bean.getSiglaRegNum());
			lRegistroEmergenza.setAnno(bean.getAnnoRegNum());
			lRegistroEmergenza.setNro(bean.getNroRegNum());
			lRegistroEmergenza.setDataRegistrazione(bean.getTsRegistrazione());
			lListRegistrazioniDaDare.add(lRegistroEmergenza);
		}			
		lCreaDocumentoInBean.setRegistroEmergenza(lListRegistrazioniDaDare);		
		lCreaDocumentoInBean.setDataEsecutivita(bean.getDataEsecutivita());
		lCreaDocumentoInBean.setTipoDocumento(bean.getTipo());
		
		if(bean.getDataAdozione() != null) {
			lCreaDocumentoInBean.setDataAdozione(bean.getDataAdozione());
		} else {
			lCreaDocumentoInBean.setDataAdozione(bean.getTsRegistrazione());
		}
		
		lCreaDocumentoInBean.setNotePubblicazione(bean.getNotePubblicazione());
		
		// Mittente / richiedente
		salvaMittenti(bean, lCreaDocumentoInBean);

		// Oggetto
		lCreaDocumentoInBean.setOggetto(estraiTestoOmissisDaHtml(bean.getOggettoHtml())); // devo settare l'oggetto, da passare al servizio di pubblicazione all'Albo Pretorio, con la versione con omissis di oggettoHtml
//		lCreaDocumentoInBean.setOggettoOmissis(estraiTestoOmissisDaHtml(bean.getOggettoHtml()));
		SezioneCache sezioneCacheAttributiDinamici = new SezioneCache();
		sezioneCacheAttributiDinamici.getVariabile().add(SezioneCacheAttributiDinamici.createVariabileSemplice("OGGETTO_HTML_Doc", bean.getOggettoHtml()));
//		sezioneCacheAttributiDinamici.getVariabile().add(SezioneCacheAttributiDinamici.createVariabileSemplice("DES_OGG_PUBBLICATO_Doc", bean.getOggettoHtml()));
		lCreaDocumentoInBean.setSezioneCacheAttributiDinamici(sezioneCacheAttributiDinamici);
				
		// Periodo di pubblicazione
		lCreaDocumentoInBean.setDtInizioPubblAlboBK(bean.getDataInizioPubblicazione());
		lCreaDocumentoInBean.setNroGgPubblAlboBK(bean.getGiorniPubblicazione());
		
		// Forma pubblicazione
		lCreaDocumentoInBean.setFormaPubblAlboBK(bean.getFormaPubblicazione());
		
		DocumentConfiguration lDocumentConfiguration = (DocumentConfiguration) SpringAppContext.getContext().getBean("DocumentConfiguration");
		List<AttachAndPosizioneBean> list = new ArrayList<AttachAndPosizioneBean>();
		
		// File primario		
		lCreaDocumentoInBean.setFlgNoPubblPrimario(bean.getFlgNoPubblPrimario() != null && bean.getFlgNoPubblPrimario() ? "1" : null);
		lCreaDocumentoInBean.setFlgDatiSensibiliPrimario(bean.getFlgDatiSensibili() != null && bean.getFlgDatiSensibili() ? "1" : null);
		FilePrimarioBean lFilePrimarioBean = retrieveFilePrimario(bean, loginBean);
		if (lFilePrimarioBean != null) {			
			if(lFilePrimarioBean.getFile() != null) {
				MimeTypeFirmaBean lMimeTypeFirmaBean = bean.getInfoFile();
				if (lMimeTypeFirmaBean == null || StringUtils.isBlank(lMimeTypeFirmaBean.getImpronta())) {
					File lFile = StorageImplementation.getStorage().extractFile(bean.getUriFilePrimario());
					lMimeTypeFirmaBean = new InfoFileUtility().getInfoFromFile(lFile.toURI().toString(), bean.getNomeFilePrimario(), false, null);
					if (lMimeTypeFirmaBean == null || StringUtils.isBlank(lMimeTypeFirmaBean.getImpronta())) {
						throw new Exception("Si è verificato un errore durante il controllo del file primario " + bean.getNomeFilePrimario());
					}
				}	
				FileInfoBean lFileInfoBean = new FileInfoBean();
				lFileInfoBean.setTipo(TipoFile.PRIMARIO);
				GenericFile lGenericFile = new GenericFile();
				setProprietaGenericFile(lGenericFile, lMimeTypeFirmaBean);		
				lGenericFile.setMimetype(lMimeTypeFirmaBean.getMimetype());
				lGenericFile.setDisplayFilename(bean.getNomeFilePrimario());
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
				lFilePrimarioBean.setInfo(lFileInfoBean);
				if (StringUtils.isNotBlank(bean.getIdAttachEmailPrimario())) {
					AttachAndPosizioneBean lAttachAndPosizioneBean = new AttachAndPosizioneBean();
					lAttachAndPosizioneBean.setIdAttachment(bean.getIdAttachEmailPrimario());
					lAttachAndPosizioneBean.setPosizione(0);
					list.add(lAttachAndPosizioneBean);
				}
			}
			// Vers. con omissis
			if(lFilePrimarioBean.getFileOmissis() != null && bean.getFilePrimarioOmissis() != null) {
				MimeTypeFirmaBean lMimeTypeFirmaBeanOmissis = bean.getFilePrimarioOmissis().getInfoFile();
				if (lMimeTypeFirmaBeanOmissis == null || StringUtils.isBlank(lMimeTypeFirmaBeanOmissis.getImpronta())) {
					File lFileOmissis = StorageImplementation.getStorage().extractFile(bean.getFilePrimarioOmissis().getUriFile());
					lMimeTypeFirmaBeanOmissis = new InfoFileUtility().getInfoFromFile(lFileOmissis.toURI().toString(), bean.getFilePrimarioOmissis().getNomeFile(), false, null);
					if (lMimeTypeFirmaBeanOmissis == null || StringUtils.isBlank(lMimeTypeFirmaBeanOmissis.getImpronta())) {
						throw new Exception("Si è verificato un errore durante il controllo del file primario " + bean.getFilePrimarioOmissis().getNomeFile() + " (vers. con omissis)");
					}
				}
				FileInfoBean lFileInfoBeanOmissis = new FileInfoBean();
				lFileInfoBeanOmissis.setTipo(TipoFile.PRIMARIO);
				GenericFile lGenericFileOmissis = new GenericFile();
				setProprietaGenericFile(lGenericFileOmissis, lMimeTypeFirmaBeanOmissis);
				lGenericFileOmissis.setMimetype(lMimeTypeFirmaBeanOmissis.getMimetype());
				lGenericFileOmissis.setDisplayFilename(bean.getFilePrimarioOmissis().getNomeFile());
				lGenericFileOmissis.setImpronta(lMimeTypeFirmaBeanOmissis.getImpronta());
				lGenericFileOmissis.setAlgoritmo(lDocumentConfiguration.getAlgoritmo().value());
				lGenericFileOmissis.setEncoding(lDocumentConfiguration.getEncoding().value());
				if (lMimeTypeFirmaBeanOmissis.isDaScansione()) {
					lGenericFileOmissis.setDaScansione(Flag.SETTED);
					lGenericFileOmissis.setDataScansione(new Date());
					lGenericFileOmissis.setIdUserScansione(loginBean.getIdUser() + "");
				}
				lFileInfoBeanOmissis.setAllegatoRiferimento(lGenericFileOmissis);
				lFilePrimarioBean.setInfoOmissis(lFileInfoBeanOmissis);
			}
		}
				
		// Allegati
		AllegatiBean lAllegatiBean = null;
		if (bean.getListaAllegati() != null) {			
			List<String> descrizione = new ArrayList<String>();
			List<Integer> docType = new ArrayList<Integer>();
			List<File> fileAllegati = new ArrayList<File>();
			List<Boolean> isNull = new ArrayList<Boolean>();
			List<String> displayFilename = new ArrayList<String>();
			List<FileInfoBean> info = new ArrayList<FileInfoBean>();
			List<Boolean> flgParteDispositivo = new ArrayList<Boolean>();
			List<String> idTask = new ArrayList<String>();
			List<Boolean> flgNoPubbl = new ArrayList<Boolean>();
			List<Boolean> flgPubblicaSeparato = new ArrayList<Boolean>();
			List<Boolean> flgDatiSensibili = new ArrayList<Boolean>();
			List<String> idUdFrom = new ArrayList<String>();			
			// Vers. con omissis
			List<File> fileAllegatiOmissis = new ArrayList<File>();
			List<Boolean> isNullOmissis = new ArrayList<Boolean>();
			List<String> displayFilenameOmissis = new ArrayList<String>();
			List<FileInfoBean> infoOmissis = new ArrayList<FileInfoBean>();			
			int i = 1;			
			for (AllegatoProtocolloBean allegato : bean.getListaAllegati()) {
				descrizione.add(allegato.getDescrizioneFileAllegato());
				docType.add(StringUtils.isNotBlank(allegato.getListaTipiFileAllegato()) ? Integer.valueOf(allegato.getListaTipiFileAllegato()) : null);
				displayFilename.add(allegato.getNomeFileAllegato());
				flgParteDispositivo.add(allegato.getFlgParteDispositivo());
				idTask.add(getExtraparams().get("idTaskCorrente"));
				flgNoPubbl.add(allegato.getFlgNoPubblAllegato());	
				flgPubblicaSeparato.add(allegato.getFlgPubblicaSeparato());	
				flgDatiSensibili.add(allegato.getFlgDatiSensibili());
				idUdFrom.add(allegato.getIdUdAppartenenza());
				if (StringUtils.isNotBlank(allegato.getUriFileAllegato())) {
					isNull.add(false);
					File lFile = null;
					if (allegato.getRemoteUri()) {
						// Il file è esterno
						RecuperoFile lRecuperoFile = new RecuperoFile();
						FileExtractedIn lFileExtractedIn = new FileExtractedIn();
						lFileExtractedIn.setUri(allegato.getUriFileAllegato());
						FileExtractedOut out = lRecuperoFile.extractfile(UserUtil.getLocale(), loginBean, lFileExtractedIn);
						lFile = out.getExtracted();
					} else {
						// File locale
						lFile = StorageImplementation.getStorage().extractFile(allegato.getUriFileAllegato());
					}
					fileAllegati.add(lFile);
					MimeTypeFirmaBean lMimeTypeFirmaBean = allegato.getInfoFile();
					if (lMimeTypeFirmaBean == null || StringUtils.isBlank(lMimeTypeFirmaBean.getImpronta())) {
						lMimeTypeFirmaBean = new InfoFileUtility().getInfoFromFile(lFile.toURI().toString(), allegato.getNomeFileAllegato(), false, null);
						if (lMimeTypeFirmaBean == null || StringUtils.isBlank(lMimeTypeFirmaBean.getImpronta())) {
							throw new Exception("Si è verificato un errore durante il controllo del file allegato " + allegato.getNomeFileAllegato());
						}
					}
					FileInfoBean lFileInfoBean = new FileInfoBean();
					lFileInfoBean.setTipo(TipoFile.ALLEGATO);
					GenericFile lGenericFile = new GenericFile();
					setProprietaGenericFile(lGenericFile, lMimeTypeFirmaBean);
					lGenericFile.setMimetype(lMimeTypeFirmaBean.getMimetype());
					lGenericFile.setDisplayFilename(allegato.getNomeFileAllegato());
					lGenericFile.setImpronta(lMimeTypeFirmaBean.getImpronta());
					lGenericFile.setAlgoritmo(lDocumentConfiguration.getAlgoritmo().value());
					lGenericFile.setEncoding(lDocumentConfiguration.getEncoding().value());
					if (lMimeTypeFirmaBean.isDaScansione()) {
						lGenericFile.setDaScansione(Flag.SETTED);
						lGenericFile.setDataScansione(new Date());
						lGenericFile.setIdUserScansione(loginBean.getIdUser() + "");
					}
					lFileInfoBean.setPosizione(i);
					lFileInfoBean.setAllegatoRiferimento(lGenericFile);
					info.add(lFileInfoBean);
				} else {
					info.add(new FileInfoBean());
					isNull.add(true);
				}
				//TODO Vers. con omissis
				if ((allegato.getFlgDatiSensibili() != null && allegato.getFlgDatiSensibili()) && StringUtils.isNotBlank(allegato.getUriFileOmissis())) {
					isNullOmissis.add(false);
					File lFileAllegatoOmissis = null;
					if (allegato.getRemoteUriOmissis()) {
						// Il file è esterno
						RecuperoFile lRecuperoFile = new RecuperoFile();
						FileExtractedIn lFileExtractedIn = new FileExtractedIn();
						lFileExtractedIn.setUri(allegato.getUriFileOmissis());
						FileExtractedOut out = lRecuperoFile.extractfile(UserUtil.getLocale(), loginBean, lFileExtractedIn);
						lFileAllegatoOmissis = out.getExtracted();
					} else {
						// File locale
						lFileAllegatoOmissis = StorageImplementation.getStorage().extractFile(allegato.getUriFileOmissis());
					}
					fileAllegatiOmissis.add(lFileAllegatoOmissis);
					MimeTypeFirmaBean lMimeTypeFirmaBeanOmissis = allegato.getInfoFileOmissis();
					if (lMimeTypeFirmaBeanOmissis == null || StringUtils.isBlank(lMimeTypeFirmaBeanOmissis.getImpronta())) {
						lMimeTypeFirmaBeanOmissis = new InfoFileUtility().getInfoFromFile(lFileAllegatoOmissis.toURI().toString(), allegato.getNomeFileOmissis(), false, null);
						if (lMimeTypeFirmaBeanOmissis == null || StringUtils.isBlank(lMimeTypeFirmaBeanOmissis.getImpronta())) {
							throw new Exception("Si è verificato un errore durante il controllo del file allegato " + allegato.getNomeFileOmissis() + " (vers. con omissis)");
						}
					}
					FileInfoBean lFileInfoBeanOmissis = new FileInfoBean();
					lFileInfoBeanOmissis.setTipo(TipoFile.ALLEGATO);
					GenericFile lGenericFileOmissis = new GenericFile();
					setProprietaGenericFile(lGenericFileOmissis, lMimeTypeFirmaBeanOmissis);
					lGenericFileOmissis.setMimetype(lMimeTypeFirmaBeanOmissis.getMimetype());
					lGenericFileOmissis.setDisplayFilename(allegato.getNomeFileOmissis());
					lGenericFileOmissis.setImpronta(lMimeTypeFirmaBeanOmissis.getImpronta());
					lGenericFileOmissis.setAlgoritmo(lDocumentConfiguration.getAlgoritmo().value());
					lGenericFileOmissis.setEncoding(lDocumentConfiguration.getEncoding().value());
					if (lMimeTypeFirmaBeanOmissis.isDaScansione()) {
						lGenericFileOmissis.setDaScansione(Flag.SETTED);
						lGenericFileOmissis.setDataScansione(new Date());
						lGenericFileOmissis.setIdUserScansione(loginBean.getIdUser() + "");
					}
					lFileInfoBeanOmissis.setPosizione(i);
					lFileInfoBeanOmissis.setAllegatoRiferimento(lGenericFileOmissis);
					infoOmissis.add(lFileInfoBeanOmissis);
				} else {
					infoOmissis.add(new FileInfoBean());
					isNullOmissis.add(true);
				}
				if (StringUtils.isNotBlank(allegato.getIdAttachEmail())) {
					AttachAndPosizioneBean lAttachAndPosizioneBean = new AttachAndPosizioneBean();
					lAttachAndPosizioneBean.setIdAttachment(allegato.getIdAttachEmail());
					lAttachAndPosizioneBean.setPosizione(i);
					list.add(lAttachAndPosizioneBean);
				}
				i++;
			}
			lAllegatiBean = new AllegatiBean();
			lAllegatiBean.setDescrizione(descrizione);
			lAllegatiBean.setDisplayFilename(displayFilename);
			lAllegatiBean.setDocType(docType);
			lAllegatiBean.setIsNull(isNull);
			lAllegatiBean.setFileAllegati(fileAllegati);
			lAllegatiBean.setInfo(info);
			lAllegatiBean.setFlgParteDispositivo(flgParteDispositivo);
			lAllegatiBean.setIdTask(idTask);
			lAllegatiBean.setFlgNoPubbl(flgNoPubbl);		
			lAllegatiBean.setFlgPubblicaSeparato(flgPubblicaSeparato);		
			lAllegatiBean.setFlgDatiSensibili(flgDatiSensibili);
			lAllegatiBean.setIdUdFrom(idUdFrom);			
			lAllegatiBean.setDisplayFilenameOmissis(displayFilenameOmissis);
			lAllegatiBean.setIsNullOmissis(isNullOmissis);
			lAllegatiBean.setFileAllegatiOmissis(fileAllegatiOmissis);
			lAllegatiBean.setInfoOmissis(infoOmissis);
		}
	
		String idTipoDocElencoIstanze = ParametriDBUtil.getParametroDB(getSession(), "ID_DOC_TYPE_ELENCO_ISTANZE_CONC");
		if (idTipoDocElencoIstanze != null && idTipoDocElencoIstanze.equalsIgnoreCase(bean.getTipo()) && bean.getListaDocumentiCollegati() != null && bean.getListaDocumentiCollegati().size()>0) {
			List<DocumentoCollegato> listaDocumentiCollegati = new ArrayList<DocumentoCollegato>();
			for (DocCollegatoBean docCollegato : bean.getListaDocumentiCollegati()) {
				DocumentoCollegato documentoCollegato = new DocumentoCollegato();
				documentoCollegato.setIdUd(docCollegato.getIdUdCollegata());
				documentoCollegato.setcValue("A");
				documentoCollegato.setScValue("EL");
				documentoCollegato.setPrcValue("CONT");
				listaDocumentiCollegati.add(documentoCollegato);
			}
			if (listaDocumentiCollegati.size() > 0) {
				addDocumentoCollegato(lCreaDocumentoInBean, listaDocumentiCollegati);
			} 
		}
		
		GestioneDocumenti lGestioneDocumenti = new GestioneDocumenti();
		CreaModDocumentoOutBean lCreaDocumentoOutBean = null;
		
		lCreaDocumentoOutBean = lGestioneDocumenti.creadocumento(getLocale(), loginBean, lCreaDocumentoInBean, lFilePrimarioBean, lAllegatiBean);
		if (lCreaDocumentoOutBean.getDefaultMessage() != null) {
			throw new StoreException(lCreaDocumentoOutBean);
		}
		
		bean.setIdUdFolder(lCreaDocumentoOutBean.getIdUd());
		bean.setIdDocPrimario(lCreaDocumentoOutBean.getIdDoc());
		if(lCreaDocumentoInBean.getDtInizioPubblAlboBK() != null) {
			bean.setIdRichPubbl("0#" + new SimpleDateFormat("ddMMyyyy").format(lCreaDocumentoInBean.getDtInizioPubblAlboBK()));
		} 
		
		String result = "";
		String[] attributes = new String[3];
		attributes[0] = lCreaDocumentoOutBean.getNumero() != null ? lCreaDocumentoOutBean.getNumero().toString() : null;
		attributes[1] = lCreaDocumentoOutBean.getAnno() != null ? lCreaDocumentoOutBean.getAnno().toString() : null;
		
		if (lCreaDocumentoOutBean.getFileInErrors() != null && lCreaDocumentoOutBean.getFileInErrors().size() > 0) {
			StringBuffer lStringBuffer = new StringBuffer();
			lStringBuffer.append(result);
			for (String lStrFileInError : lCreaDocumentoOutBean.getFileInErrors().values()) {
				lStringBuffer.append("; " + lStrFileInError);
			}
			addMessage(lStringBuffer.toString(), "", MessageType.WARNING);
		}
		if (lCreaDocumentoOutBean.getArchiviazioneError() != null && lCreaDocumentoOutBean.getArchiviazioneError()) {
			addMessage("Non è stato possibile eseguire l'archiviazione automatica dell'e-mail", "", MessageType.WARNING);
		}
		if (lCreaDocumentoOutBean.getInvioConfermaAutomaticaError() != null && lCreaDocumentoOutBean.getInvioConfermaAutomaticaError()) {
			addMessage("Non è stato possibile eseguire l'invio conferma automatica dell'e-mail", "", MessageType.WARNING);
		}
		if (lCreaDocumentoOutBean.getSalvataggioMetadatiError() != null && lCreaDocumentoOutBean.getSalvataggioMetadatiError()) {
			addMessage("Si è verificato un errore durante il salvataggio dei metadati su SharePoint", "", MessageType.WARNING);
		}
		
		return bean;
	}
	
	@Override
	public PubblicazioneAlboConsultazioneRichiesteBean update(PubblicazioneAlboConsultazioneRichiesteBean bean,
			PubblicazioneAlboConsultazioneRichiesteBean oldvalue) throws Exception {

		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());

		CreaModDocumentoInBean lModificaDocumentoInBean = new CreaModDocumentoInBean();
		
		lModificaDocumentoInBean.setFlgSoloSetDatiRichPubbl(1);
		
		lModificaDocumentoInBean.setIdRichPubblicazione(bean.getIdRichPubbl());
		
		// Dati atto
		lModificaDocumentoInBean.setDataEsecutivita(bean.getDataEsecutivita());
		lModificaDocumentoInBean.setTipoDocumento(bean.getTipo());
		
		if(bean.getIsRettifica() != null && bean.getIsRettifica()) {
			lModificaDocumentoInBean.setIdRettificaPubblicazione(bean.getIdRichPubbl());
			lModificaDocumentoInBean.setMotivoRettificaPubblicazione(bean.getMotivoRettificaPubblicazione());			
		}
		
		if(bean.getDataAdozione() != null) {
			lModificaDocumentoInBean.setDataAdozione(bean.getDataAdozione());
		} else {
			lModificaDocumentoInBean.setDataAdozione(bean.getTsRegistrazione());
		}
		
		lModificaDocumentoInBean.setNotePubblicazione(bean.getNotePubblicazione());
		
		// Mittente / richiedente
		salvaMittenti(bean, lModificaDocumentoInBean);
 
		// Oggetto
		lModificaDocumentoInBean.setOggetto(estraiTestoOmissisDaHtml(bean.getOggettoHtml()));
//		lModificaDocumentoInBean.setOggettoOmissis(estraiTestoOmissisDaHtml(bean.getOggettoHtml()));
		SezioneCache sezioneCacheAttributiDinamici = new SezioneCache();
		sezioneCacheAttributiDinamici.getVariabile().add(SezioneCacheAttributiDinamici.createVariabileSemplice("OGGETTO_HTML_Doc", bean.getOggettoHtml()));
//		sezioneCacheAttributiDinamici.getVariabile().add(SezioneCacheAttributiDinamici.createVariabileSemplice("DES_OGG_PUBBLICATO_Doc", bean.getOggettoHtml()));
		lModificaDocumentoInBean.setSezioneCacheAttributiDinamici(sezioneCacheAttributiDinamici);
		
		// Periodo di pubblicazione
		lModificaDocumentoInBean.setDtInizioPubblAlboBK(bean.getDataInizioPubblicazione());
		lModificaDocumentoInBean.setNroGgPubblAlboBK(bean.getGiorniPubblicazione());
				
		// Forma pubblicazione
		lModificaDocumentoInBean.setFormaPubblAlboBK(bean.getFormaPubblicazione());
				
		DocumentConfiguration lDocumentConfiguration = (DocumentConfiguration) SpringAppContext.getContext().getBean("DocumentConfiguration");
		List<AttachAndPosizioneBean> list = new ArrayList<AttachAndPosizioneBean>();
		ArrayList<String> idUdDaCollegareList = new ArrayList<String>();
		
		// File primario
		lModificaDocumentoInBean.setFlgNoPubblPrimario(bean.getFlgNoPubblPrimario() != null && bean.getFlgNoPubblPrimario() ? "1" : null);
		lModificaDocumentoInBean.setFlgDatiSensibiliPrimario(bean.getFlgDatiSensibili() != null && bean.getFlgDatiSensibili() ? "1" : null);
		FilePrimarioBean lFilePrimarioBean = retrieveFilePrimario(bean, lAurigaLoginBean);
		if (lFilePrimarioBean != null) {
			if (lFilePrimarioBean.getFile() != null) {
				lFilePrimarioBean.setFlgSostituisciVerPrec(bean.getFlgSostituisciVerPrec());
				MimeTypeFirmaBean lMimeTypeFirmaBean = bean.getInfoFile();
				if (lMimeTypeFirmaBean == null || StringUtils.isBlank(lMimeTypeFirmaBean.getImpronta())) {
					File lFile = StorageImplementation.getStorage().extractFile(bean.getUriFilePrimario());
					lMimeTypeFirmaBean = new InfoFileUtility().getInfoFromFile(lFile.toURI().toString(),
							bean.getNomeFilePrimario(), false, null);
					if (lMimeTypeFirmaBean == null || StringUtils.isBlank(lMimeTypeFirmaBean.getImpronta())) {
						throw new Exception("Si è verificato un errore durante il controllo del file primario "
								+ bean.getNomeFilePrimario());
					}
				}

				FileInfoBean lFileInfoBean = new FileInfoBean();
				lFileInfoBean.setTipo(TipoFile.PRIMARIO);
				GenericFile lGenericFile = new GenericFile();
				setProprietaGenericFile(lGenericFile, lMimeTypeFirmaBean);
				lGenericFile.setMimetype(lMimeTypeFirmaBean.getMimetype());
				lGenericFile.setDisplayFilename(bean.getNomeFilePrimario());
				lGenericFile.setImpronta(lMimeTypeFirmaBean.getImpronta());
				lGenericFile.setImprontaFilePreFirma(lMimeTypeFirmaBean.getImprontaFilePreFirma());
				lGenericFile.setAlgoritmo(lDocumentConfiguration.getAlgoritmo().value());
				lGenericFile.setEncoding(lDocumentConfiguration.getEncoding().value());
				if (lMimeTypeFirmaBean.isDaScansione()) {
					lGenericFile.setDaScansione(Flag.SETTED);
					lGenericFile.setDataScansione(new Date());
					lGenericFile.setIdUserScansione(lAurigaLoginBean.getIdUser() + "");
				}
				lFileInfoBean.setAllegatoRiferimento(lGenericFile);
				lFilePrimarioBean.setIdDocumento(bean.getIdDocPrimario());
				lFilePrimarioBean.setIsNewOrChanged(bean.getIdDocPrimario() == null
						|| (bean.getIsDocPrimarioChanged() != null && bean.getIsDocPrimarioChanged()));
				lFilePrimarioBean.setInfo(lFileInfoBean);
				if (StringUtils.isNotBlank(bean.getIdAttachEmailPrimario())) {
					AttachAndPosizioneBean lAttachAndPosizioneBean = new AttachAndPosizioneBean();
					lAttachAndPosizioneBean.setIdAttachment(bean.getIdAttachEmailPrimario());
					lAttachAndPosizioneBean.setPosizione(0);
					list.add(lAttachAndPosizioneBean);
				}
			}
			if (lFilePrimarioBean.getFileOmissis() != null && bean.getFilePrimarioOmissis() != null) {
				lFilePrimarioBean
						.setFlgSostituisciVerPrecOmissis(bean.getFilePrimarioOmissis().getFlgSostituisciVerPrec());
				MimeTypeFirmaBean lMimeTypeFirmaBeanOmissis = bean.getFilePrimarioOmissis().getInfoFile();
				if (lMimeTypeFirmaBeanOmissis == null || StringUtils.isBlank(lMimeTypeFirmaBeanOmissis.getImpronta())) {
					File lFileOmissis = StorageImplementation.getStorage()
							.extractFile(bean.getFilePrimarioOmissis().getUriFile());
					lMimeTypeFirmaBeanOmissis = new InfoFileUtility().getInfoFromFile(lFileOmissis.toURI().toString(),
							bean.getFilePrimarioOmissis().getNomeFile(), false, null);
					if (lMimeTypeFirmaBeanOmissis == null
							|| StringUtils.isBlank(lMimeTypeFirmaBeanOmissis.getImpronta())) {
						throw new Exception("Si è verificato un errore durante il controllo del file primario "
								+ bean.getFilePrimarioOmissis().getNomeFile() + " (vers. con omissis)");
					}
				}
				FileInfoBean lFileInfoBeanOmissis = new FileInfoBean();
				lFileInfoBeanOmissis.setTipo(TipoFile.PRIMARIO);
				GenericFile lGenericFileOmissis = new GenericFile();
				setProprietaGenericFile(lGenericFileOmissis, lMimeTypeFirmaBeanOmissis);
				lGenericFileOmissis.setMimetype(lMimeTypeFirmaBeanOmissis.getMimetype());
				lGenericFileOmissis.setDisplayFilename(bean.getFilePrimarioOmissis().getNomeFile());
				lGenericFileOmissis.setImpronta(lMimeTypeFirmaBeanOmissis.getImpronta());
				lGenericFileOmissis.setAlgoritmo(lDocumentConfiguration.getAlgoritmo().value());
				lGenericFileOmissis.setEncoding(lDocumentConfiguration.getEncoding().value());
				if (lMimeTypeFirmaBeanOmissis.isDaScansione()) {
					lGenericFileOmissis.setDaScansione(Flag.SETTED);
					lGenericFileOmissis.setDataScansione(new Date());
					lGenericFileOmissis.setIdUserScansione(lAurigaLoginBean.getIdUser() + "");
				}
				lFileInfoBeanOmissis.setAllegatoRiferimento(lGenericFileOmissis);
				lFilePrimarioBean.setIdDocumentoOmissis(bean.getFilePrimarioOmissis().getIdDoc() != null
						? new BigDecimal(bean.getFilePrimarioOmissis().getIdDoc())
						: null);
				lFilePrimarioBean.setIsNewOrChangedOmissis(StringUtils.isBlank(bean.getFilePrimarioOmissis().getIdDoc())
						|| (bean.getFilePrimarioOmissis().getIsChanged() != null
								&& bean.getFilePrimarioOmissis().getIsChanged()));
				lFilePrimarioBean.setInfoOmissis(lFileInfoBeanOmissis);
			}
		}
		
		//Allegati
		List<AllegatoProtocolloBean> listaAllegati = bean.getListaAllegati();
		if (bean.getFlgNoPubblPrimario() != null && bean.getFlgNoPubblPrimario()) {
			AllegatoProtocolloBean filePrimarioVerPubbl = bean.getListaFilePrimarioVerPubbl() != null
					&& bean.getListaFilePrimarioVerPubbl().size() > 0 ? bean.getListaFilePrimarioVerPubbl().get(0)
							: null;
			if (filePrimarioVerPubbl != null) {
				String idTipoDocAllVerPubbl = ParametriDBUtil.getParametroDB(getSession(), "ID_TIPO_DOC_ALL_VER_PUBBL");
				if (idTipoDocAllVerPubbl != null && !"".equals(idTipoDocAllVerPubbl)) {
					filePrimarioVerPubbl.setIdTipoFileAllegato(idTipoDocAllVerPubbl);
					filePrimarioVerPubbl.setListaTipiFileAllegato(idTipoDocAllVerPubbl);
				}
				String nomeDocAllVerPubbl = ParametriDBUtil.getParametroDB(getSession(), "NOME_DOC_ALL_VER_PUBBL");
				if (nomeDocAllVerPubbl != null && !"".equals(nomeDocAllVerPubbl)) {
					filePrimarioVerPubbl.setDescrizioneFileAllegato(nomeDocAllVerPubbl);
				}
				filePrimarioVerPubbl.setFlgParteDispositivo(true);
				filePrimarioVerPubbl.setFlgNoPubblAllegato(false);
				filePrimarioVerPubbl.setFlgPubblicaSeparato(false);
				if (listaAllegati == null) {
					listaAllegati = new ArrayList<AllegatoProtocolloBean>();
				}
				listaAllegati.add(0, filePrimarioVerPubbl);
			}
		}
		AllegatiBean lAllegatiBean = null;
		if (listaAllegati != null) {
			List<BigDecimal> idDocumento = new ArrayList<BigDecimal>();
			List<String> descrizione = new ArrayList<String>();
			List<Integer> docType = new ArrayList<Integer>();
			List<File> fileAllegati = new ArrayList<File>();
			List<Boolean> isNull = new ArrayList<Boolean>();
			List<Boolean> isNewOrChanged = new ArrayList<Boolean>();
			List<String> displayFilename = new ArrayList<String>();
			List<FileInfoBean> info = new ArrayList<FileInfoBean>();
			List<Boolean> flgParteDispositivo = new ArrayList<Boolean>();
			List<String> idTask = new ArrayList<String>();
			List<Boolean> flgNoPubbl = new ArrayList<Boolean>();
			List<Boolean> flgPubblicaSeparato = new ArrayList<Boolean>();
			List<Boolean> flgDatiSensibili = new ArrayList<Boolean>();
			List<Boolean> flgSostituisciVerPrec = new ArrayList<Boolean>();
			List<Boolean> flgOriginaleCartaceo = new ArrayList<Boolean>();
			List<Boolean> flgCopiaSostitutiva = new ArrayList<Boolean>();
			List<String> idUdFrom = new ArrayList<String>();
			// Vers. con omissis
			List<BigDecimal> idDocumentoOmissis = new ArrayList<BigDecimal>();
			List<File> fileAllegatiOmissis = new ArrayList<File>();
			List<Boolean> isNullOmissis = new ArrayList<Boolean>();
			List<Boolean> isNewOrChangedOmissis = new ArrayList<Boolean>();
			List<String> displayFilenameOmissis = new ArrayList<String>();
			List<FileInfoBean> infoOmissis = new ArrayList<FileInfoBean>();
			List<Boolean> flgSostituisciVerPrecOmissis = new ArrayList<Boolean>();
			int i = 1;
			for (AllegatoProtocolloBean allegato : listaAllegati) {
				idDocumento.add(allegato.getIdDocAllegato());
				descrizione.add(allegato.getDescrizioneFileAllegato());
				docType.add(StringUtils.isNotBlank(allegato.getListaTipiFileAllegato())
						? Integer.valueOf(allegato.getListaTipiFileAllegato())
						: null);
				displayFilename.add(allegato.getNomeFileAllegato());
				flgParteDispositivo.add(allegato.getFlgParteDispositivo());
				// solo se è un allegato inserito in quella attività e se
				// l'attività è readonly salvo l'idTask
				if (allegato.getIdDocAllegato() == null) {
					idTask.add(getExtraparams().get("idTaskCorrente"));
				} else {
					idTask.add(allegato.getIdTask());
				}
				flgNoPubbl.add(allegato.getFlgNoPubblAllegato());
				flgPubblicaSeparato.add(allegato.getFlgPubblicaSeparato());
				flgDatiSensibili.add(allegato.getFlgDatiSensibili());
				flgSostituisciVerPrec.add(allegato.getFlgSostituisciVerPrec());
				flgOriginaleCartaceo.add(allegato.getFlgOriginaleCartaceo());
				flgCopiaSostitutiva.add(allegato.getFlgCopiaSostitutiva());
				idUdFrom.add(allegato.getIdUdAppartenenza());
				if (StringUtils.isNotBlank(allegato.getUriFileAllegato())
						&& StringUtils.isNotBlank(allegato.getNomeFileAllegato())) {
					isNull.add(false);
					isNewOrChanged.add(allegato.getIdDocAllegato() == null
							|| (allegato.getIsChanged() != null && allegato.getIsChanged()));
					File lFile = null;
					if (allegato.getIdDocAllegato() == null
							|| (allegato.getIsChanged() != null && allegato.getIsChanged())) {
						if (allegato.getRemoteUri()) {
							// Il file è esterno
							RecuperoFile lRecuperoFile = new RecuperoFile();
							FileExtractedIn lFileExtractedIn = new FileExtractedIn();
							lFileExtractedIn.setUri(allegato.getUriFileAllegato());
							FileExtractedOut out = lRecuperoFile.extractfile(UserUtil.getLocale(), lAurigaLoginBean,
									lFileExtractedIn);
							lFile = out.getExtracted();
						} else {
							// File locale
							lFile = StorageImplementation.getStorage().extractFile(allegato.getUriFileAllegato());
						}
						fileAllegati.add(lFile);
					}
					MimeTypeFirmaBean lMimeTypeFirmaBean = allegato.getInfoFile();
					if (lMimeTypeFirmaBean == null || StringUtils.isBlank(lMimeTypeFirmaBean.getImpronta())) {
						if (lFile == null) {
							if (allegato.getRemoteUri()) {
								// Il file è esterno
								RecuperoFile lRecuperoFile = new RecuperoFile();
								FileExtractedIn lFileExtractedIn = new FileExtractedIn();
								lFileExtractedIn.setUri(allegato.getUriFileAllegato());
								FileExtractedOut out = lRecuperoFile.extractfile(UserUtil.getLocale(), lAurigaLoginBean,
										lFileExtractedIn);
								lFile = out.getExtracted();
							} else {
								// File locale
								lFile = StorageImplementation.getStorage().extractFile(allegato.getUriFileAllegato());
							}
						}
						lMimeTypeFirmaBean = new InfoFileUtility().getInfoFromFile(lFile.toURI().toString(),
								allegato.getNomeFileAllegato(), false, null);
						if (lMimeTypeFirmaBean == null || StringUtils.isBlank(lMimeTypeFirmaBean.getImpronta())) {
							throw new Exception("Si è verificato un errore durante il controllo del file allegato "
									+ allegato.getNomeFileAllegato());
						}
					}
					FileInfoBean lFileInfoBean = new FileInfoBean();
					lFileInfoBean.setTipo(TipoFile.ALLEGATO);
					GenericFile lGenericFile = new GenericFile();
					setProprietaGenericFile(lGenericFile, lMimeTypeFirmaBean);
					lGenericFile.setMimetype(lMimeTypeFirmaBean.getMimetype());
					lGenericFile.setDisplayFilename(allegato.getNomeFileAllegato());
					lGenericFile.setImpronta(lMimeTypeFirmaBean.getImpronta());
					lGenericFile.setAlgoritmo(lDocumentConfiguration.getAlgoritmo().value());
					lGenericFile.setEncoding(lDocumentConfiguration.getEncoding().value());
					if (lMimeTypeFirmaBean.isDaScansione()) {
						lGenericFile.setDaScansione(Flag.SETTED);
						lGenericFile.setDataScansione(new Date());
						lGenericFile.setIdUserScansione(lAurigaLoginBean.getIdUser() + "");
					}
					lFileInfoBean.setPosizione(i);
					lFileInfoBean.setAllegatoRiferimento(lGenericFile);
					info.add(lFileInfoBean);
				} else {
					isNull.add(true);
					isNewOrChanged.add(null);
					info.add(new FileInfoBean());
				}
				// TODO Vers. con omissis
				idDocumentoOmissis.add(allegato.getIdDocOmissis());
				displayFilenameOmissis.add(allegato.getNomeFileOmissis());
				flgSostituisciVerPrecOmissis.add(allegato.getFlgSostituisciVerPrecOmissis());
				if ((allegato.getFlgDatiSensibili() != null && allegato.getFlgDatiSensibili())
						&& StringUtils.isNotBlank(allegato.getUriFileOmissis())
						&& StringUtils.isNotBlank(allegato.getNomeFileOmissis())) {
					isNullOmissis.add(false);
					isNewOrChangedOmissis.add(allegato.getIdDocOmissis() == null
							|| (allegato.getIsChangedOmissis() != null && allegato.getIsChangedOmissis()));
					File lFileAllegatoOmissis = null;
					if (allegato.getIdDocOmissis() == null
							|| (allegato.getIsChangedOmissis() != null && allegato.getIsChangedOmissis())) {
						if (allegato.getRemoteUriOmissis()) {
							// Il file è esterno
							RecuperoFile lRecuperoFile = new RecuperoFile();
							FileExtractedIn lFileExtractedIn = new FileExtractedIn();
							lFileExtractedIn.setUri(allegato.getUriFileOmissis());
							FileExtractedOut out = lRecuperoFile.extractfile(UserUtil.getLocale(), lAurigaLoginBean,
									lFileExtractedIn);
							lFileAllegatoOmissis = out.getExtracted();
						} else {
							// File locale
							lFileAllegatoOmissis = StorageImplementation.getStorage()
									.extractFile(allegato.getUriFileOmissis());
						}
						fileAllegatiOmissis.add(lFileAllegatoOmissis);
					}
					MimeTypeFirmaBean lMimeTypeFirmaBeanOmissis = allegato.getInfoFileOmissis();
					if (lMimeTypeFirmaBeanOmissis == null
							|| StringUtils.isBlank(lMimeTypeFirmaBeanOmissis.getImpronta())) {
						if (lFileAllegatoOmissis == null) {
							if (allegato.getRemoteUriOmissis()) {
								// Il file è esterno
								RecuperoFile lRecuperoFile = new RecuperoFile();
								FileExtractedIn lFileExtractedIn = new FileExtractedIn();
								lFileExtractedIn.setUri(allegato.getUriFileOmissis());
								FileExtractedOut out = lRecuperoFile.extractfile(UserUtil.getLocale(), lAurigaLoginBean,
										lFileExtractedIn);
								lFileAllegatoOmissis = out.getExtracted();
							} else {
								// File locale
								lFileAllegatoOmissis = StorageImplementation.getStorage()
										.extractFile(allegato.getUriFileOmissis());
							}
						}
						lMimeTypeFirmaBeanOmissis = new InfoFileUtility().getInfoFromFile(
								lFileAllegatoOmissis.toURI().toString(), allegato.getNomeFileOmissis(), false, null);
						if (lMimeTypeFirmaBeanOmissis == null
								|| StringUtils.isBlank(lMimeTypeFirmaBeanOmissis.getImpronta())) {
							throw new Exception("Si è verificato un errore durante il controllo del file allegato "
									+ allegato.getNomeFileOmissis() + " (vers. con omissis)");
						}
					}
					FileInfoBean lFileInfoBeanOmissis = new FileInfoBean();
					lFileInfoBeanOmissis.setTipo(TipoFile.ALLEGATO);
					GenericFile lGenericFileOmissis = new GenericFile();
					setProprietaGenericFile(lGenericFileOmissis, lMimeTypeFirmaBeanOmissis);
					lGenericFileOmissis.setMimetype(lMimeTypeFirmaBeanOmissis.getMimetype());
					lGenericFileOmissis.setDisplayFilename(allegato.getNomeFileOmissis());
					lGenericFileOmissis.setImpronta(lMimeTypeFirmaBeanOmissis.getImpronta());
					lGenericFileOmissis.setAlgoritmo(lDocumentConfiguration.getAlgoritmo().value());
					lGenericFileOmissis.setEncoding(lDocumentConfiguration.getEncoding().value());
					if (lMimeTypeFirmaBeanOmissis.isDaScansione()) {
						lGenericFileOmissis.setDaScansione(Flag.SETTED);
						lGenericFileOmissis.setDataScansione(new Date());
						lGenericFileOmissis.setIdUserScansione(lAurigaLoginBean.getIdUser() + "");
					}
					lFileInfoBeanOmissis.setPosizione(i);
					lFileInfoBeanOmissis.setAllegatoRiferimento(lGenericFileOmissis);
					infoOmissis.add(lFileInfoBeanOmissis);
				} else {
					isNullOmissis.add(true);
					isNewOrChangedOmissis.add(null);
					infoOmissis.add(new FileInfoBean());
				}
				if (StringUtils.isNotBlank(allegato.getIdAttachEmail())) {
					AttachAndPosizioneBean lAttachAndPosizioneBean = new AttachAndPosizioneBean();
					lAttachAndPosizioneBean.setIdAttachment(allegato.getIdAttachEmail());
					lAttachAndPosizioneBean.setPosizione(i);
					list.add(lAttachAndPosizioneBean);
				}
				// Federico Cacco 22.06.2016
				// Integro la lista dei documenti da importare
				if ((allegato.getCollegaDocumentoImportato() != null) && (allegato.getCollegaDocumentoImportato())
						&& (StringUtils.isNotBlank(allegato.getIdUdAppartenenza()))) {
					if (!idUdDaCollegareList.contains(allegato.getIdUdAppartenenza())) {
						idUdDaCollegareList.add(allegato.getIdUdAppartenenza());
					}
				}
				i++;
			}
			// Federico Cacco 22.06.2016
			// Inserisco tutti i documenti da importare
//			List<DocumentoCollegato> listaDocumentiCollegati = new ArrayList<DocumentoCollegato>();
//			for (String idUdDaCollegare : idUdDaCollegareList) {
//				DocumentoCollegato documentoCollegato = new DocumentoCollegato();
//				documentoCollegato.setIdUd(idUdDaCollegare);
//				documentoCollegato.setcValue("C");
//				documentoCollegato.setPrcValue("PRC");
//				listaDocumentiCollegati.add(documentoCollegato);
//			}
//			if (listaDocumentiCollegati.size() > 0) {
//				addDocumentoCollegato(lModificaDocumentoInBean, listaDocumentiCollegati);
//			}
			String idTipoDocElencoIstanze = ParametriDBUtil.getParametroDB(getSession(), "ID_DOC_TYPE_ELENCO_ISTANZE_CONC");
			if (idTipoDocElencoIstanze != null && idTipoDocElencoIstanze.equalsIgnoreCase(bean.getTipo()) && bean.getListaDocumentiCollegati() != null && bean.getListaDocumentiCollegati().size()>0) {
				List<DocumentoCollegato> listaDocumentiCollegati = new ArrayList<DocumentoCollegato>();
				for (DocCollegatoBean docCollegato : bean.getListaDocumentiCollegati()) {
					DocumentoCollegato documentoCollegato = new DocumentoCollegato();
					documentoCollegato.setIdUd(docCollegato.getIdUdCollegata());
					documentoCollegato.setcValue("A");
					documentoCollegato.setScValue("EL");
					documentoCollegato.setPrcValue("CONT");
					listaDocumentiCollegati.add(documentoCollegato);
				}
				if (listaDocumentiCollegati.size() > 0) {
					addDocumentoCollegato(lModificaDocumentoInBean, listaDocumentiCollegati);
				} 
			}
			lAllegatiBean = new AllegatiBean();
			lAllegatiBean.setIdDocumento(idDocumento);
			lAllegatiBean.setDescrizione(descrizione);
			lAllegatiBean.setDisplayFilename(displayFilename);
			lAllegatiBean.setDocType(docType);
			lAllegatiBean.setIsNull(isNull);
			lAllegatiBean.setIsNewOrChanged(isNewOrChanged);
			lAllegatiBean.setFileAllegati(fileAllegati);
			lAllegatiBean.setInfo(info);
			lAllegatiBean.setFlgParteDispositivo(flgParteDispositivo);
			lAllegatiBean.setIdTask(idTask);
			lAllegatiBean.setFlgNoPubbl(flgNoPubbl);
			lAllegatiBean.setFlgPubblicaSeparato(flgPubblicaSeparato);
			lAllegatiBean.setFlgDatiSensibili(flgDatiSensibili);
			lAllegatiBean.setFlgSostituisciVerPrec(flgSostituisciVerPrec);
			lAllegatiBean.setFlgOriginaleCartaceo(flgOriginaleCartaceo);
			lAllegatiBean.setFlgCopiaSostitutiva(flgCopiaSostitutiva);
			lAllegatiBean.setIdUdFrom(idUdFrom);
			lAllegatiBean.setIdDocumentoOmissis(idDocumentoOmissis);
			lAllegatiBean.setDisplayFilenameOmissis(displayFilenameOmissis);
			lAllegatiBean.setIsNullOmissis(isNullOmissis);
			lAllegatiBean.setIsNewOrChangedOmissis(isNewOrChangedOmissis);
			lAllegatiBean.setFileAllegatiOmissis(fileAllegatiOmissis);
			lAllegatiBean.setInfoOmissis(infoOmissis);
			lAllegatiBean.setFlgSostituisciVerPrecOmissis(flgSostituisciVerPrecOmissis);
		}

		CreaModDocumentoOutBean lModificaDocumentoOutBean = null;

		GestioneDocumenti lGestioneDocumenti = new GestioneDocumenti();

		lModificaDocumentoOutBean = lGestioneDocumenti.modificadocumento(getLocale(), lAurigaLoginBean, bean.getIdUdFolder(), bean.getIdDocPrimario(),
					lModificaDocumentoInBean, lFilePrimarioBean, lAllegatiBean);

		if (lModificaDocumentoOutBean.getDefaultMessage() != null) {
			throw new StoreException(lModificaDocumentoOutBean);
		}

		if(lModificaDocumentoInBean.getDtInizioPubblAlboBK() != null) {
			bean.setIdRichPubbl("0#" + new SimpleDateFormat("ddMMyyyy").format(lModificaDocumentoInBean.getDtInizioPubblAlboBK()));
		}
		
		return bean;
	}	
	
	@Override
	public PubblicazioneAlboConsultazioneRichiesteBean get(PubblicazioneAlboConsultazioneRichiesteBean bean) throws Exception {		

		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());		
		
		RecuperaDocumentoInBean lRecuperaDocumentoInBean = new RecuperaDocumentoInBean();
		lRecuperaDocumentoInBean.setIdUd(bean.getIdUdFolder());
		lRecuperaDocumentoInBean.setIdRichPubbl(bean.getIdRichPubbl());
		lRecuperaDocumentoInBean.setIdProcess(null);
		lRecuperaDocumentoInBean.setTaskName("#NONE");
		RecuperoDocumenti lRecuperoDocumenti = new RecuperoDocumenti();
		RecuperaDocumentoOutBean lRecuperaDocumentoOutBean = lRecuperoDocumenti.loaddocumento(getLocale(), lAurigaLoginBean, lRecuperaDocumentoInBean);
		if(lRecuperaDocumentoOutBean.isInError()) {
			throw new StoreException(lRecuperaDocumentoOutBean);
		}
		DocumentoXmlOutBean lDocumentoXmlOutBean = lRecuperaDocumentoOutBean.getDocumento();
		
		ProtocolloUtility lProtocolloUtility = new ProtocolloUtility(getSession());
		ProtocollazioneBean lProtocollazioneBean = lProtocolloUtility.getProtocolloFromDocumentoXml(lDocumentoXmlOutBean, getExtraparams());
		
		PubblicazioneAlboConsultazioneRichiesteBean pubblicazioneAlboBean = new PubblicazioneAlboConsultazioneRichiesteBean();
				
		// Dati atto
		pubblicazioneAlboBean.setIdUdFolder(bean.getIdUdFolder());
		pubblicazioneAlboBean.setIdRichPubbl(bean.getIdRichPubbl());
		pubblicazioneAlboBean.setSegnatura(lDocumentoXmlOutBean.getSegnatura());
		pubblicazioneAlboBean.setStatoAtto("presente");				
		if(lDocumentoXmlOutBean.getEstremiRegistrazione()!=null) {
			pubblicazioneAlboBean.setTipoRegNum(lDocumentoXmlOutBean.getEstremiRegistrazione().getTipo());
			pubblicazioneAlboBean.setSiglaRegNum(lDocumentoXmlOutBean.getEstremiRegistrazione().getSigla());
			pubblicazioneAlboBean.setAnnoRegNum(lDocumentoXmlOutBean.getEstremiRegistrazione().getAnno());
			pubblicazioneAlboBean.setTsRegistrazione(lDocumentoXmlOutBean.getEstremiRegistrazione().getTsRegistrazione());
			pubblicazioneAlboBean.setNroRegNum(lDocumentoXmlOutBean.getEstremiRegistrazione().getNro());
		}		
		pubblicazioneAlboBean.setDataEsecutivita(lDocumentoXmlOutBean.getDtEsecutivita());
		pubblicazioneAlboBean.setTipo(lDocumentoXmlOutBean.getTipoDocumento());	
		pubblicazioneAlboBean.setNomeTipo(lDocumentoXmlOutBean.getNomeTipoDocumento());
		
		// Mittente / richiedente
		if (lProtocollazioneBean.getListaMittenti() != null && lProtocollazioneBean.getListaMittenti().size() > 0) {
			MittenteProtBean lMittente = lProtocollazioneBean.getListaMittenti().get(0);
			if(lMittente.getTipoMittente().equals("UOI")) {
				pubblicazioneAlboBean.setTipoMittente("I");
				pubblicazioneAlboBean.setMittenteRichiedenteInterno(lProtocollazioneBean.getListaMittenti());
			} else {
				pubblicazioneAlboBean.setTipoMittente("E");
				pubblicazioneAlboBean.setMittenteRichiedenteEsterno(lProtocollazioneBean.getListaMittenti());
			}
		}
		
		// Oggetto
		pubblicazioneAlboBean.setOggetto(lDocumentoXmlOutBean.getOggetto());
		pubblicazioneAlboBean.setOggettoHtml(lDocumentoXmlOutBean.getOggettoHtml());
			
		// File primario
		pubblicazioneAlboBean.setIdDocPrimario(lProtocollazioneBean.getIdDocPrimario());
		pubblicazioneAlboBean.setFlgNoPubblPrimario(lProtocollazioneBean.getFlgNoPubblPrimario());
		pubblicazioneAlboBean.setFlgDatiSensibili(lProtocollazioneBean.getFlgDatiSensibili());
		pubblicazioneAlboBean.setFlgOriginaleCartaceo(lProtocollazioneBean.getFlgOriginaleCartaceo());
		pubblicazioneAlboBean.setFlgCopiaSostitutiva(lProtocollazioneBean.getFlgCopiaSostitutiva());
		pubblicazioneAlboBean.setUriFilePrimario(lProtocollazioneBean.getUriFilePrimario());
		pubblicazioneAlboBean.setRemoteUriFilePrimario(lProtocollazioneBean.getRemoteUriFilePrimario());
		pubblicazioneAlboBean.setNomeFilePrimario(lProtocollazioneBean.getNomeFilePrimario());
		pubblicazioneAlboBean.setInfoFile(lProtocollazioneBean.getInfoFile());
		pubblicazioneAlboBean.setFlgTimbratoFilePostReg(lProtocollazioneBean.getFlgTimbratoFilePostReg());
		pubblicazioneAlboBean.setIdAttachEmailPrimario(lProtocollazioneBean.getIdAttachEmailPrimario());		
		pubblicazioneAlboBean.setMimetypeVerPreFirma(lProtocollazioneBean.getMimetypeVerPreFirma());
		pubblicazioneAlboBean.setUriFileVerPreFirma(lProtocollazioneBean.getUriFileVerPreFirma());
		pubblicazioneAlboBean.setNomeFileVerPreFirma(lProtocollazioneBean.getNomeFileVerPreFirma());
		pubblicazioneAlboBean.setFlgConvertibilePdfVerPreFirma(lProtocollazioneBean.getFlgConvertibilePdfVerPreFirma());
		pubblicazioneAlboBean.setImprontaVerPreFirma(lProtocollazioneBean.getImprontaVerPreFirma());
		pubblicazioneAlboBean.setInfoFileVerPreFirma(lProtocollazioneBean.getInfoFileVerPreFirma());
		pubblicazioneAlboBean.setNroLastVer(lProtocollazioneBean.getNroLastVer());
		pubblicazioneAlboBean.setListaFilePrimarioVerPubbl(lProtocollazioneBean.getListaFilePrimarioVerPubbl());		
		pubblicazioneAlboBean.setFilePrimarioOmissis(lProtocollazioneBean.getFilePrimarioOmissis());	
		
		// Allegati
		pubblicazioneAlboBean.setListaAllegati(lProtocollazioneBean.getListaAllegati());
		
		// Periodo di pubblicazione
		pubblicazioneAlboBean.setDataInizioPubblicazione(lDocumentoXmlOutBean.getDtInizioPubblAlboBK());
		pubblicazioneAlboBean.setGiorniPubblicazione(lDocumentoXmlOutBean.getNroGgPubblAlboBK());
		if(lDocumentoXmlOutBean.getDtInizioPubblAlboBK() != null && StringUtils.isNotBlank(lDocumentoXmlOutBean.getNroGgPubblAlboBK())) {
			Date dataInizioPubbl = lDocumentoXmlOutBean.getDtInizioPubblAlboBK();
			int nroGiorniPubbl = Integer.parseInt(lDocumentoXmlOutBean.getNroGgPubblAlboBK());
			GregorianCalendar calFinePubbl = new GregorianCalendar();
			calFinePubbl.setTime(dataInizioPubbl);
			if(ParametriDBUtil.getParametroDBAsBoolean(getSession(), "CONTEGGIA_INTERO_GIORNO_CORRENTE_X_PERIODO_PUBBL")) {
				calFinePubbl.add(Calendar.DAY_OF_YEAR, nroGiorniPubbl - 1);				
			} else {
				calFinePubbl.add(Calendar.DAY_OF_YEAR, nroGiorniPubbl);							
			}
			pubblicazioneAlboBean.setDataFinePubblicazione(calFinePubbl.getTime());
		}
		
		// Forma pubblicazione
		pubblicazioneAlboBean.setFormaPubblicazione(lDocumentoXmlOutBean.getFormaPubblAlboBK());
		
		pubblicazioneAlboBean.setDataAdozione(lDocumentoXmlOutBean.getDataAdozione());
		pubblicazioneAlboBean.setNotePubblicazione(lDocumentoXmlOutBean.getNotePubblicazione());
		
		// Abilitazioni
		pubblicazioneAlboBean.setAbilModificabile(lDocumentoXmlOutBean.getAbilModificaAlboBK() != null ? lDocumentoXmlOutBean.getAbilModificaAlboBK() : false);
		pubblicazioneAlboBean.setAbilProrogaPubblicazione(lDocumentoXmlOutBean.getAbilProrogaPubblicazione() != null && lDocumentoXmlOutBean.getAbilProrogaPubblicazione());
		pubblicazioneAlboBean.setAbilAnnullamentoPubblicazione(lDocumentoXmlOutBean.getAbilAnnullamentoPubblicazione() != null && lDocumentoXmlOutBean.getAbilAnnullamentoPubblicazione());
		pubblicazioneAlboBean.setAbilRettificaPubblicazione(lDocumentoXmlOutBean.getAbilRettificaPubblicazione() != null && lDocumentoXmlOutBean.getAbilRettificaPubblicazione());
		
		// Richieste pubblicazione
		pubblicazioneAlboBean.setFlgPresenzaPubblicazioni(lDocumentoXmlOutBean.getFlgPresenzaPubblicazioni());
		
		// Istanze collegate
		if (lDocumentoXmlOutBean.getDocumentiCollegati() != null && lDocumentoXmlOutBean.getDocumentiCollegati().size() > 0) {
			List<DocCollegatoBean> lListDocumentiCollegati = new ArrayList<DocCollegatoBean>();
			for (DocumentoCollegato lDocumentoCollegato : lDocumentoXmlOutBean.getDocumentiCollegati()) {
				if (lDocumentoCollegato.getcValue()!= null && lDocumentoCollegato.getcValue().equals("A") && lDocumentoCollegato.getScValue() != null && lDocumentoCollegato.getScValue().equals("EL")) {
					DocCollegatoBean lDocCollegatoBean = new DocCollegatoBean();
					lDocCollegatoBean.setIdUdCollegata(lDocumentoCollegato.getIdUd());
					String categoria = lDocumentoCollegato.getTipo() != null
							&& lDocumentoCollegato.getTipo().getDbValue() != null
									? lDocumentoCollegato.getTipo().getDbValue()
									: "";
					String sigla = lDocumentoCollegato.getSiglaRegistro() != null
							? lDocumentoCollegato.getSiglaRegistro()
							: "";
					if ("PG".equals(categoria)) {
						lDocCollegatoBean.setTipo("PG");
					} else if ("R".equals(categoria)) {
						lDocCollegatoBean.setTipo("R");
						lDocCollegatoBean.setSiglaRegistro(sigla);
					} else if ("PP".equals(categoria)) {
						lDocCollegatoBean.setTipo("PP");
						lDocCollegatoBean.setSiglaRegistro(sigla);
					} else if ("P.I.".equals(sigla)) {
						lDocCollegatoBean.setTipo("PI");
					} else if ("N.I.".equals(sigla)) {
						lDocCollegatoBean.setTipo("NI");
					}
					lDocCollegatoBean.setIdTipoDoc(lDocumentoCollegato.getIdTipoDoc());
					lDocCollegatoBean.setNomeTipoDoc(lDocumentoCollegato.getNomeTipoDoc());
					lDocCollegatoBean.setAnno(lDocumentoCollegato.getAnno());
					lDocCollegatoBean.setNumero(
							lDocumentoCollegato.getNumero() != null ? String.valueOf(lDocumentoCollegato.getNumero())
									: null);
					lDocCollegatoBean.setSub(lDocumentoCollegato.getSubNumero() != null
							? String.valueOf(lDocumentoCollegato.getSubNumero())
							: null);
					lDocCollegatoBean.setMotivi(lDocumentoCollegato.getMotiviCollegamento());
					lDocCollegatoBean.setOggetto(lDocumentoCollegato.getOggetto());
					lDocCollegatoBean.setEstremiReg(lDocumentoCollegato.getEstremiReg());
					lDocCollegatoBean.setDatiCollegamento(lDocumentoCollegato.getDatiCollegamento());
					lDocCollegatoBean.setFlgLocked(lDocumentoCollegato.getFlgLocked() != null
							&& "1".equals(lDocumentoCollegato.getFlgLocked()));
					lListDocumentiCollegati.add(lDocCollegatoBean);
				}
			}
			pubblicazioneAlboBean.setListaDocumentiCollegati(lListDocumentiCollegati);
		}
		
		return pubblicazioneAlboBean;
	}
	
	public PubblicazioneAlboConsultazioneRichiesteBean recuperaIdUdAttoDaPubblicare(PubblicazioneAlboConsultazioneRichiesteBean bean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();

		boolean hideMessageError = getExtraparams().get("hideMessageError") != null && "true".equals(getExtraparams().get("hideMessageError"));

		DmpkCoreFindudBean input = new DmpkCoreFindudBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setCodcategoriaregin(bean.getTipoRegNum());
		input.setSiglaregin(bean.getSiglaRegNum());
		input.setNumregin(bean.getNroRegNum() != null ? Integer.parseInt(bean.getNroRegNum()) : null);
		input.setAnnoregin(bean.getAnnoRegNum() != null ? Integer.parseInt(bean.getAnnoRegNum()) : null);

		DmpkCoreFindud lDmpkCoreFindud = new DmpkCoreFindud();
		StoreResultBean<DmpkCoreFindudBean> output = lDmpkCoreFindud.execute(getLocale(), loginBean, input);
		if (output.isInError()) {
			if(StringUtils.isNotBlank(output.getDefaultMessage())) {
				if (!hideMessageError) {
					addMessage(output.getDefaultMessage(), output.getDefaultMessage(), MessageType.ERROR);
				}
			}
		}		
		bean.setIdUdFolder((output.getResultBean() != null && output.getResultBean().getIdudio() != null) ? output.getResultBean().getIdudio() : null);

		return bean;
	}
	
	public PubblicazioneAlboConsultazioneRichiesteBean recuperaIdUdIstanzaCollegata(PubblicazioneAlboConsultazioneRichiesteBean bean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		DmpkCoreFindudBean input = new DmpkCoreFindudBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setCodcategoriaregin(bean.getTipoRegNum());
		input.setSiglaregin(bean.getSiglaRegNum());
		input.setNumregin(bean.getNroRegNum() != null ? Integer.parseInt(bean.getNroRegNum()) : null);
		input.setAnnoregin(bean.getAnnoRegNum() != null ? Integer.parseInt(bean.getAnnoRegNum()) : null);

		DmpkCoreFindud lDmpkCoreFindud = new DmpkCoreFindud();
		StoreResultBean<DmpkCoreFindudBean> output = lDmpkCoreFindud.execute(getLocale(), loginBean, input);
		if (output.isInError()) {
			if(StringUtils.isNotBlank(output.getDefaultMessage())) {
					addMessage(output.getDefaultMessage(), output.getDefaultMessage(), MessageType.ERROR);
			}
		}		
		bean.setIdUdFolder((output.getResultBean() != null && output.getResultBean().getIdudio() != null) ? output.getResultBean().getIdudio() : null);

		return bean;
	}
	
	private Map<String, String> createRemapConditionsMap() {
		Map<String, String> retValue = new LinkedHashMap<String, String>();
		return retValue;
	}
	
	private String estraiTestoOmissisDaHtml(String html) {
		if (StringUtils.isNotBlank(html)) {
			html = FreeMarkerModelliUtil.replaceOmissisInHtml(html);
			return Jsoup.parse(html).text().replaceAll("\\<.*?>","");
		} else {
			return html;
		}
	}

	protected void salvaFiles(PubblicazioneAlboConsultazioneRichiesteBean bean, CreaModDocumentoInBean attributiUdDoc, AurigaLoginBean lAurigaLoginBean, FilePrimarioBean lFilePrimarioBean, AllegatiBean lAllegatiBean) throws StorageException, Exception {
		List<AttachAndPosizioneBean> list = new ArrayList<AttachAndPosizioneBean>();
		DocumentConfiguration lDocumentConfiguration = (DocumentConfiguration) SpringAppContext.getContext().getBean("DocumentConfiguration");
		lAllegatiBean = null;
		if (bean.getListaAllegati() != null) {			
			List<String> descrizione = new ArrayList<String>();
			List<Integer> docType = new ArrayList<Integer>();
			List<File> fileAllegati = new ArrayList<File>();
			List<Boolean> isNull = new ArrayList<Boolean>();
			List<String> displayFilename = new ArrayList<String>();
			List<FileInfoBean> info = new ArrayList<FileInfoBean>();
			List<Boolean> flgParteDispositivo = new ArrayList<Boolean>();
			List<String> idTask = new ArrayList<String>();
			List<Boolean> flgNoPubbl = new ArrayList<Boolean>();
			List<Boolean> flgPubblicaSeparato = new ArrayList<Boolean>();
			List<Boolean> flgDatiSensibili = new ArrayList<Boolean>();
			List<String> idUdFrom = new ArrayList<String>();			
			// Vers. con omissis
			List<File> fileAllegatiOmissis = new ArrayList<File>();
			List<Boolean> isNullOmissis = new ArrayList<Boolean>();
			List<String> displayFilenameOmissis = new ArrayList<String>();
			List<FileInfoBean> infoOmissis = new ArrayList<FileInfoBean>();			
			int i = 1;			
			for (AllegatoProtocolloBean allegato : bean.getListaAllegati()) {
				descrizione.add(allegato.getDescrizioneFileAllegato());
				docType.add(StringUtils.isNotBlank(allegato.getListaTipiFileAllegato()) ? Integer.valueOf(allegato.getListaTipiFileAllegato()) : null);
				displayFilename.add(allegato.getNomeFileAllegato());
				flgParteDispositivo.add(allegato.getFlgParteDispositivo());
				idTask.add(getExtraparams().get("idTaskCorrente"));
				flgNoPubbl.add(allegato.getFlgNoPubblAllegato());	
				flgPubblicaSeparato.add(allegato.getFlgPubblicaSeparato());	
				flgDatiSensibili.add(allegato.getFlgDatiSensibili());
				idUdFrom.add(allegato.getIdUdAppartenenza());
				if (StringUtils.isNotBlank(allegato.getUriFileAllegato())) {
					isNull.add(false);
					File lFile = null;
					if (allegato.getRemoteUri()) {
						// Il file è esterno
						RecuperoFile lRecuperoFile = new RecuperoFile();
						FileExtractedIn lFileExtractedIn = new FileExtractedIn();
						lFileExtractedIn.setUri(allegato.getUriFileAllegato());
						FileExtractedOut out = lRecuperoFile.extractfile(UserUtil.getLocale(), lAurigaLoginBean, lFileExtractedIn);
						lFile = out.getExtracted();
					} else {
						// File locale
						lFile = StorageImplementation.getStorage().extractFile(allegato.getUriFileAllegato());
					}
					fileAllegati.add(lFile);
					MimeTypeFirmaBean lMimeTypeFirmaBean = allegato.getInfoFile();
					if (lMimeTypeFirmaBean == null || StringUtils.isBlank(lMimeTypeFirmaBean.getImpronta())) {
						lMimeTypeFirmaBean = new InfoFileUtility().getInfoFromFile(lFile.toURI().toString(), allegato.getNomeFileAllegato(), false, null);
						if (lMimeTypeFirmaBean == null || StringUtils.isBlank(lMimeTypeFirmaBean.getImpronta())) {
							throw new Exception("Si è verificato un errore durante il controllo del file allegato " + allegato.getNomeFileAllegato());
						}
					}
					FileInfoBean lFileInfoBean = new FileInfoBean();
					lFileInfoBean.setTipo(TipoFile.ALLEGATO);
					GenericFile lGenericFile = new GenericFile();
					setProprietaGenericFile(lGenericFile, lMimeTypeFirmaBean);
					lGenericFile.setMimetype(lMimeTypeFirmaBean.getMimetype());
					lGenericFile.setDisplayFilename(allegato.getNomeFileAllegato());
					lGenericFile.setImpronta(lMimeTypeFirmaBean.getImpronta());
					lGenericFile.setImprontaFilePreFirma(lMimeTypeFirmaBean.getImprontaFilePreFirma());
					lGenericFile.setAlgoritmo(lDocumentConfiguration.getAlgoritmo().value());
					lGenericFile.setEncoding(lDocumentConfiguration.getEncoding().value());
					if (lMimeTypeFirmaBean.isDaScansione()) {
						lGenericFile.setDaScansione(Flag.SETTED);
						lGenericFile.setDataScansione(new Date());
						lGenericFile.setIdUserScansione(lAurigaLoginBean.getIdUser() + "");
					}
					lFileInfoBean.setPosizione(i);
					lFileInfoBean.setAllegatoRiferimento(lGenericFile);
					info.add(lFileInfoBean);
				} else {
					info.add(new FileInfoBean());
					isNull.add(true);
				}
				//TODO Vers. con omissis
				if ((allegato.getFlgDatiSensibili() != null && allegato.getFlgDatiSensibili()) && StringUtils.isNotBlank(allegato.getUriFileOmissis())) {
					isNullOmissis.add(false);
					File lFileAllegatoOmissis = null;
					if (allegato.getRemoteUriOmissis()) {
						// Il file è esterno
						RecuperoFile lRecuperoFile = new RecuperoFile();
						FileExtractedIn lFileExtractedIn = new FileExtractedIn();
						lFileExtractedIn.setUri(allegato.getUriFileOmissis());
						FileExtractedOut out = lRecuperoFile.extractfile(UserUtil.getLocale(), lAurigaLoginBean, lFileExtractedIn);
						lFileAllegatoOmissis = out.getExtracted();
					} else {
						// File locale
						lFileAllegatoOmissis = StorageImplementation.getStorage().extractFile(allegato.getUriFileOmissis());
					}
					fileAllegatiOmissis.add(lFileAllegatoOmissis);
					MimeTypeFirmaBean lMimeTypeFirmaBeanOmissis = allegato.getInfoFileOmissis();
					if (lMimeTypeFirmaBeanOmissis == null || StringUtils.isBlank(lMimeTypeFirmaBeanOmissis.getImpronta())) {
						lMimeTypeFirmaBeanOmissis = new InfoFileUtility().getInfoFromFile(lFileAllegatoOmissis.toURI().toString(), allegato.getNomeFileOmissis(), false, null);
						if (lMimeTypeFirmaBeanOmissis == null || StringUtils.isBlank(lMimeTypeFirmaBeanOmissis.getImpronta())) {
							throw new Exception("Si è verificato un errore durante il controllo del file allegato " + allegato.getNomeFileOmissis() + " (vers. con omissis)");
						}
					}
					FileInfoBean lFileInfoBeanOmissis = new FileInfoBean();
					lFileInfoBeanOmissis.setTipo(TipoFile.ALLEGATO);
					GenericFile lGenericFileOmissis = new GenericFile();
					setProprietaGenericFile(lGenericFileOmissis, lMimeTypeFirmaBeanOmissis);
					lGenericFileOmissis.setMimetype(lMimeTypeFirmaBeanOmissis.getMimetype());
					lGenericFileOmissis.setDisplayFilename(allegato.getNomeFileOmissis());
					lGenericFileOmissis.setImpronta(lMimeTypeFirmaBeanOmissis.getImpronta());
					lGenericFileOmissis.setAlgoritmo(lDocumentConfiguration.getAlgoritmo().value());
					lGenericFileOmissis.setEncoding(lDocumentConfiguration.getEncoding().value());
					if (lMimeTypeFirmaBeanOmissis.isDaScansione()) {
						lGenericFileOmissis.setDaScansione(Flag.SETTED);
						lGenericFileOmissis.setDataScansione(new Date());
						lGenericFileOmissis.setIdUserScansione(lAurigaLoginBean.getIdUser() + "");
					}
					lFileInfoBeanOmissis.setPosizione(i);
					lFileInfoBeanOmissis.setAllegatoRiferimento(lGenericFileOmissis);
					infoOmissis.add(lFileInfoBeanOmissis);
				} else {
					infoOmissis.add(new FileInfoBean());
					isNullOmissis.add(true);
				}
				if (StringUtils.isNotBlank(allegato.getIdAttachEmail())) {
					AttachAndPosizioneBean lAttachAndPosizioneBean = new AttachAndPosizioneBean();
					lAttachAndPosizioneBean.setIdAttachment(allegato.getIdAttachEmail());
					lAttachAndPosizioneBean.setPosizione(i);
					list.add(lAttachAndPosizioneBean);
				}
				i++;
			}
			lAllegatiBean = new AllegatiBean();
			lAllegatiBean.setDescrizione(descrizione);
			lAllegatiBean.setDisplayFilename(displayFilename);
			lAllegatiBean.setDocType(docType);
			lAllegatiBean.setIsNull(isNull);
			lAllegatiBean.setFileAllegati(fileAllegati);
			lAllegatiBean.setInfo(info);
			lAllegatiBean.setFlgParteDispositivo(flgParteDispositivo);
			lAllegatiBean.setIdTask(idTask);
			lAllegatiBean.setFlgNoPubbl(flgNoPubbl);		
			lAllegatiBean.setFlgPubblicaSeparato(flgPubblicaSeparato);		
			lAllegatiBean.setFlgDatiSensibili(flgDatiSensibili);
			lAllegatiBean.setIdUdFrom(idUdFrom);			
			lAllegatiBean.setDisplayFilenameOmissis(displayFilenameOmissis);
			lAllegatiBean.setIsNullOmissis(isNullOmissis);
			lAllegatiBean.setFileAllegatiOmissis(fileAllegatiOmissis);
			lAllegatiBean.setInfoOmissis(infoOmissis);
		}
		// Salvo il file PRIMARIO
		lFilePrimarioBean = retrieveFilePrimario(bean, lAurigaLoginBean);
		if (lFilePrimarioBean != null) {			
			if(lFilePrimarioBean.getFile() != null) {
				MimeTypeFirmaBean lMimeTypeFirmaBean = bean.getInfoFile();
				if (lMimeTypeFirmaBean == null || StringUtils.isBlank(lMimeTypeFirmaBean.getImpronta())) {
					File lFile = StorageImplementation.getStorage().extractFile(bean.getUriFilePrimario());
					lMimeTypeFirmaBean = new InfoFileUtility().getInfoFromFile(lFile.toURI().toString(), bean.getNomeFilePrimario(), false, null);
					if (lMimeTypeFirmaBean == null || StringUtils.isBlank(lMimeTypeFirmaBean.getImpronta())) {
						throw new Exception("Si è verificato un errore durante il controllo del file primario " + bean.getNomeFilePrimario());
					}
				}	
				FileInfoBean lFileInfoBean = new FileInfoBean();
				lFileInfoBean.setTipo(TipoFile.PRIMARIO);
				GenericFile lGenericFile = new GenericFile();
				setProprietaGenericFile(lGenericFile, lMimeTypeFirmaBean);
				lGenericFile.setMimetype(lMimeTypeFirmaBean.getMimetype());
				lGenericFile.setDisplayFilename(bean.getNomeFilePrimario());
				lGenericFile.setImpronta(lMimeTypeFirmaBean.getImpronta());
				lGenericFile.setAlgoritmo(lDocumentConfiguration.getAlgoritmo().value());
				lGenericFile.setEncoding(lDocumentConfiguration.getEncoding().value());
				if (lMimeTypeFirmaBean.isDaScansione()) {
					lGenericFile.setDaScansione(Flag.SETTED);
					lGenericFile.setDataScansione(new Date());
					lGenericFile.setIdUserScansione(lAurigaLoginBean.getIdUser() + "");
				}
				lFileInfoBean.setAllegatoRiferimento(lGenericFile);
				lFilePrimarioBean.setInfo(lFileInfoBean);
				if (StringUtils.isNotBlank(bean.getIdAttachEmailPrimario())) {
					AttachAndPosizioneBean lAttachAndPosizioneBean = new AttachAndPosizioneBean();
					lAttachAndPosizioneBean.setIdAttachment(bean.getIdAttachEmailPrimario());
					lAttachAndPosizioneBean.setPosizione(0);
					list.add(lAttachAndPosizioneBean);
				}
			}
			// Vers. con omissis
			if(lFilePrimarioBean.getFileOmissis() != null && bean.getFilePrimarioOmissis() != null) {
				MimeTypeFirmaBean lMimeTypeFirmaBeanOmissis = bean.getFilePrimarioOmissis().getInfoFile();
				if (lMimeTypeFirmaBeanOmissis == null || StringUtils.isBlank(lMimeTypeFirmaBeanOmissis.getImpronta())) {
					File lFileOmissis = StorageImplementation.getStorage().extractFile(bean.getFilePrimarioOmissis().getUriFile());
					lMimeTypeFirmaBeanOmissis = new InfoFileUtility().getInfoFromFile(lFileOmissis.toURI().toString(), bean.getFilePrimarioOmissis().getNomeFile(), false, null);
					if (lMimeTypeFirmaBeanOmissis == null || StringUtils.isBlank(lMimeTypeFirmaBeanOmissis.getImpronta())) {
						throw new Exception("Si è verificato un errore durante il controllo del file primario " + bean.getFilePrimarioOmissis().getNomeFile() + " (vers. con omissis)");
					}
				}
				FileInfoBean lFileInfoBeanOmissis = new FileInfoBean();
				lFileInfoBeanOmissis.setTipo(TipoFile.PRIMARIO);
				GenericFile lGenericFileOmissis = new GenericFile();
				setProprietaGenericFile(lGenericFileOmissis, lMimeTypeFirmaBeanOmissis);
				lGenericFileOmissis.setMimetype(lMimeTypeFirmaBeanOmissis.getMimetype());
				lGenericFileOmissis.setDisplayFilename(bean.getFilePrimarioOmissis().getNomeFile());
				lGenericFileOmissis.setImpronta(lMimeTypeFirmaBeanOmissis.getImpronta());
				lGenericFileOmissis.setAlgoritmo(lDocumentConfiguration.getAlgoritmo().value());
				lGenericFileOmissis.setEncoding(lDocumentConfiguration.getEncoding().value());
				if (lMimeTypeFirmaBeanOmissis.isDaScansione()) {
					lGenericFileOmissis.setDaScansione(Flag.SETTED);
					lGenericFileOmissis.setDataScansione(new Date());
					lGenericFileOmissis.setIdUserScansione(lAurigaLoginBean.getIdUser() + "");
				}
				lFileInfoBeanOmissis.setAllegatoRiferimento(lGenericFileOmissis);
				lFilePrimarioBean.setInfoOmissis(lFileInfoBeanOmissis);
			}
		}
	}
	
	private FilePrimarioBean retrieveFilePrimario(PubblicazioneAlboConsultazioneRichiesteBean bean, AurigaLoginBean lAurigaLoginBean) throws StorageException {
		FilePrimarioBean filePrimarioBean = new FilePrimarioBean();
		if (StringUtils.isNotBlank(bean.getUriFilePrimario()) && StringUtils.isNotBlank(bean.getNomeFilePrimario())) {
			if (bean.getRemoteUriFilePrimario()) {
				// Il file è esterno
				RecuperoFile lRecuperoFile = new RecuperoFile();
				FileExtractedIn lFileExtractedIn = new FileExtractedIn();
				lFileExtractedIn.setUri(bean.getUriFilePrimario());
				FileExtractedOut out = lRecuperoFile.extractfile(UserUtil.getLocale(), lAurigaLoginBean, lFileExtractedIn);
				filePrimarioBean.setFile(out.getExtracted());
			} else {
				// File locale
				filePrimarioBean.setFile(StorageImplementation.getStorage().extractFile(bean.getUriFilePrimario()));
			}
		}
		// Vers. con omissis
		if ((bean.getFlgDatiSensibili() != null && bean.getFlgDatiSensibili()) && bean.getFilePrimarioOmissis() != null && StringUtils.isNotBlank(bean.getFilePrimarioOmissis().getUriFile()) && StringUtils.isNotBlank(bean.getFilePrimarioOmissis().getNomeFile())) {
			if (bean.getFilePrimarioOmissis().getRemoteUri()) {
				// Il file è esterno
				RecuperoFile lRecuperoFile = new RecuperoFile();
				FileExtractedIn lFileExtractedIn = new FileExtractedIn();
				lFileExtractedIn.setUri(bean.getFilePrimarioOmissis().getUriFile());
				FileExtractedOut out = lRecuperoFile.extractfile(UserUtil.getLocale(), lAurigaLoginBean, lFileExtractedIn);
				filePrimarioBean.setFileOmissis(out.getExtracted());
			} else {
				// File locale
				filePrimarioBean.setFileOmissis(StorageImplementation.getStorage().extractFile(bean.getFilePrimarioOmissis().getUriFile()));
			}
		}
		if(filePrimarioBean.getFile() != null || filePrimarioBean.getFileOmissis() != null) {
			return filePrimarioBean;
		}
		return null;
	}

	protected void salvaMittenti(PubblicazioneAlboConsultazioneRichiesteBean bean, CreaModDocumentoInBean lCreaModDocumentoInBean) throws Exception {
		List<MittentiDocumentoBean> lListMittenti = new ArrayList<MittentiDocumentoBean>();
		List<MittenteProtBean> origListaMittenti = new ArrayList<>();
		if (bean.getTipoMittente() != null && bean.getTipoMittente().equals("I")) {
			origListaMittenti = bean.getMittenteRichiedenteInterno();
		} else {
			lCreaModDocumentoInBean.setFlgTipoProv(TipoProvenienza.ENTRATA);
			origListaMittenti = bean.getMittenteRichiedenteEsterno();
		}		
		if (origListaMittenti != null && origListaMittenti.size() > 0) {
				MittenteProtBean lMittenteProtBean = origListaMittenti.get(0);
				MittentiDocumentoBean lMittentiBean = new MittentiDocumentoBean();
				if(StringUtils.isNotBlank(lMittenteProtBean.getOrganigrammaMittente())) {
					String typeNode = lMittenteProtBean.getOrganigrammaMittente().substring(0, 2);
					String idUoSv = lMittenteProtBean.getOrganigrammaMittente().substring(2);
					lMittentiBean.setIdSoggetto(idUoSv);
					if(typeNode != null) {
						if(typeNode.equals("UO")) {
							lMittentiBean.setTipoSoggetto("UOI");					
							lMittentiBean.setDenominazioneCognome(lMittenteProtBean.getDenominazioneMittente());
							lMittentiBean.setTipo(TipoMittente.PERSONA_GIURIDICA);								
						} else if(typeNode.equals("SV")) {
							lMittentiBean.setTipoSoggetto("UP");					
							lMittentiBean.setDenominazioneCognome(lMittenteProtBean.getCognomeMittente());
							lMittentiBean.setNome(lMittenteProtBean.getNomeMittente());
							lMittentiBean.setTipo(TipoMittente.PERSONA_FISICA);							
						} 
					}
				} else if (StringUtils.isBlank(lMittenteProtBean.getTipoMittente()) || lMittenteProtBean.getTipoMittente().equals("G")
						|| lMittenteProtBean.getTipoMittente().equals("PG") || lMittenteProtBean.getTipoMittente().equals("PA")
						|| lMittenteProtBean.getTipoMittente().equals("UOI") || lMittenteProtBean.getTipoMittente().equals("AOOE")) {
					lMittentiBean.setDenominazioneCognome(lMittenteProtBean.getDenominazioneMittente());
					lMittentiBean.setTipo(TipoMittente.PERSONA_GIURIDICA);
				} else if (lMittenteProtBean.getTipoMittente().equals("F") || lMittenteProtBean.getTipoMittente().equals("PF")
						|| lMittenteProtBean.getTipoMittente().equals("UP")) {
					lMittentiBean.setDenominazioneCognome(lMittenteProtBean.getCognomeMittente());
					lMittentiBean.setNome(lMittenteProtBean.getNomeMittente());
					lMittentiBean.setTipo(TipoMittente.PERSONA_FISICA);
				}
				lMittentiBean.setCodiceFiscale(lMittenteProtBean.getCodfiscaleMittente());
				lMittentiBean.setEmail(lMittenteProtBean.getEmailMittente());
				lMittentiBean.setTelefono(lMittenteProtBean.getTelMittente());				
				if(StringUtils.isNotBlank(lMittenteProtBean.getIdSoggetto())) {
					lMittentiBean.setIdRubrica(lMittenteProtBean.getIdSoggetto());
				} else if(StringUtils.isNotBlank(lMittenteProtBean.getIdUoSoggetto())) {
					// Quando ho la preimpostazione della UO di lavoro nel mittente delle bozze e della prot. in uscita/interna 
					// mi manca l'idRubrica quindi passo l'idUo nel cod. di provenienza con prefisso [AUR.UO]
					lMittentiBean.setCodProvenienza("[AUR.UO]" + lMittenteProtBean.getIdUoSoggetto());
				}
				lListMittenti.add(lMittentiBean);				
		}
		lCreaModDocumentoInBean.setMittenti(lListMittenti);
	}
	
	private static void addDocumentoCollegato(CreaModDocumentoInBean creaModDocumentoInBean, List<DocumentoCollegato> listaDaAggiungere) {
		List<DocumentoCollegato> listaCorrente = creaModDocumentoInBean.getDocCollegato();
		if (listaCorrente == null) {
			creaModDocumentoInBean.setDocCollegato(listaDaAggiungere);
		} else {
			listaCorrente.addAll(listaDaAggiungere);
			creaModDocumentoInBean.setDocCollegato(listaCorrente);
		}
	}
	
	@Override
	public PubblicazioneAlboConsultazioneRichiesteBean remove(PubblicazioneAlboConsultazioneRichiesteBean bean) throws Exception {	
		
//		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
//
//		CreaModDocumentoInBean lModificaDocumentoInBean = new CreaModDocumentoInBean();
//		lModificaDocumentoInBean.setDaEliminare(1);
//		
//		CreaModDocumentoOutBean lModificaDocumentoOutBean = null;
//
//		GestioneDocumenti lGestioneDocumenti = new GestioneDocumenti();
//
//		lModificaDocumentoOutBean = lGestioneDocumenti.modificadocumento(getLocale(), lAurigaLoginBean, bean.getIdUdFolder(), bean.getIdDocPrimario(),
//					lModificaDocumentoInBean, null, null);
//
//		if (lModificaDocumentoOutBean.getDefaultMessage() != null) {
//			throw new StoreException(lModificaDocumentoOutBean.getDefaultMessage());
//		}
//
//		return bean;
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		DmpkCoreUpddocudBean input = new DmpkCoreUpddocudBean();
		input.setCodidconnectiontokenin(loginBean.getToken());
		input.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()): null);
		input.setFlgtipotargetin("U");
		input.setIduddocin(bean.getIdUdFolder());
		
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		
		CreaModDocumentoInBean lModificaDocumentoInBean = new CreaModDocumentoInBean();
		lModificaDocumentoInBean.setDaEliminare(1);
		
		input.setAttributiuddocxmlin(lXmlUtilitySerializer.bindXml(lModificaDocumentoInBean));
		
		DmpkCoreUpddocud dmpkCoreUpddocud = new DmpkCoreUpddocud();
		StoreResultBean<DmpkCoreUpddocudBean> output = dmpkCoreUpddocud.execute(this.getLocale(), loginBean, input);
		if(output.isInError()) {
			throw new StoreException(output);
		}
		
		return bean;
	}
	
	protected FindRepositoryObjectBean createFindRepositoryObjectBean(AdvancedCriteria criteria, AurigaLoginBean loginBean) throws Exception {

		String filtroFullText = null;
		String[] checkAttributes = null;
		String formatoEstremiReg = null;
		Integer searchAllTerms = null;
		Long idFolder = null;

		// in modalita' di esplora il valore di default e' 0
		String includiSottoCartelle = "0";
		String advancedFilters = null;
		String customFilters = null;
		String colsOrderBy = null;
		String flgDescOrderBy = null;
		Integer flgSenzaPaginazione = new Integer(1);
		Integer numPagina = null;
		Integer numRighePagina = null;
		Integer online = null;
		String percorsoRicerca = null;
		String flgTipoRicerca = null;
		String finalita = null;
		String flgUdFolder = "";
		
		boolean setNullFlgSubfolderSearch = getExtraparams().get("setNullFlgSubfolderSearch") != null ? new Boolean(getExtraparams().get("setNullFlgSubfolderSearch")) : false;
		
		/*
		 Atto N°                                        colonna 4 (è una stringa). Quando si ordina per 4 si deve ordinare per colonna 6 (quindi vanno messe entrambe tra le colonne richieste)
         Oggetto                                        colonna 18
         Tipo atto                                      colonna 32
         Origine (è il mittente/richiedente)            colonna 91
         Data atto (data senza ora)                     colonna 201 
         Inizio pubbl.                                  colonna 270
         Giorni pubbl.                                  colonna 277
         Termine pubbl.                                 colonna 278
		 Richiesta pubbl. del (data e ora)              colonna 279
         Richiesta pubbl. effettuata da                 colonna 280
         Id. rich. pubbl.								colonna 283
         */
		String colsToReturn = "4,6,18,32,91,201,270,277,278,279,280,283";

		CriteriAvanzati scCriteriAvanzati = new CriteriAvanzati();
		// StatoPubblicazione = DA_PUBBLICARE
		scCriteriAvanzati.setStatoPubblicazione("DA_PUBBLICARE");
		
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		advancedFilters = lXmlUtilitySerializer.bindXml(scCriteriAvanzati);		
		
		FindRepositoryObjectBean lFindRepositoryObjectBean = new FindRepositoryObjectBean();

		lFindRepositoryObjectBean.setFiltroFullText(filtroFullText);
		lFindRepositoryObjectBean.setCheckAttributes(checkAttributes);
		lFindRepositoryObjectBean.setFormatoEstremiReg(formatoEstremiReg);
		lFindRepositoryObjectBean.setSearchAllTerms(searchAllTerms);
		lFindRepositoryObjectBean.setFlgUdFolder(flgUdFolder);
		lFindRepositoryObjectBean.setIdFolderSearchIn(idFolder);
		if (!setNullFlgSubfolderSearch) {
			lFindRepositoryObjectBean.setFlgSubfoderSearchIn(includiSottoCartelle);
		} else {
			lFindRepositoryObjectBean.setFlgSubfoderSearchIn(null);
		}

		lFindRepositoryObjectBean.setAdvancedFilters(advancedFilters);
		lFindRepositoryObjectBean.setCustomFilters(customFilters);
		lFindRepositoryObjectBean.setColsOrderBy(colsOrderBy);
		lFindRepositoryObjectBean.setFlgDescOrderBy(flgDescOrderBy);
		lFindRepositoryObjectBean.setFlgSenzaPaginazione(flgSenzaPaginazione);
		lFindRepositoryObjectBean.setNumPagina(numPagina);
		lFindRepositoryObjectBean.setNumRighePagina(numRighePagina);
		lFindRepositoryObjectBean.setOnline(online);
		lFindRepositoryObjectBean.setColsToReturn(colsToReturn);
		lFindRepositoryObjectBean.setPercorsoRicerca(percorsoRicerca);
		lFindRepositoryObjectBean.setFlagTipoRicerca(flgTipoRicerca);
		lFindRepositoryObjectBean.setFinalita(finalita);
		lFindRepositoryObjectBean.setUserIdLavoro(loginBean.getIdUserLavoro());

		return lFindRepositoryObjectBean;
	}
	
	@Override
	protected ExportBean asyncExport(ExportBean bean) throws Exception {

		AdvancedCriteria criteria = bean.getCriteria();

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		FindRepositoryObjectBean lFindRepositoryObjectBean = createFindRepositoryObjectBean(criteria, loginBean);

		lFindRepositoryObjectBean.setOverflowlimitin(-2);

		List<Object> resFinder = null;
		try {
			resFinder = AurigaService.getFind().findrepositoryobject(getLocale(), loginBean, lFindRepositoryObjectBean).getList();
		} catch (Exception e) {
			throw new StoreException(e.getMessage());
		}

		String errorMessageOut = null;

		if (resFinder.size() > 5) {
			errorMessageOut = (String) resFinder.get(5);
		}

		if (errorMessageOut != null && !"".equals(errorMessageOut)) {
			addMessage(errorMessageOut, "", MessageType.WARNING);
		}

		// imposto l'id del job creato
		Integer jobId = Integer.valueOf((String) resFinder.get(6));
		bean.setIdAsyncJob(jobId);

		saveParameters(loginBean, bean, jobId, loginBean.getIdUserLavoro(), ArchivioXmlBean.class.getName());

		saveRemapInformations(loginBean, jobId, createRemapConditionsMap(), ArchivioXmlBeanDeserializationHelper.class);

		updateJob(loginBean, bean, jobId, loginBean.getIdUser());

		if (jobId != null && jobId > 0) {
			String mess = "Richiesta di esportazione su file registrata con Nr. " + jobId.toString()
					+ " .Per visualizzare l'export vai nella sezione 'Stampe ed esportazioni' della scrivania.";
			addMessage(mess, "", MessageType.INFO);
		}

		return null;
	}
	
	@Override
	public NroRecordTotBean getNroRecordTotali(NroRecordTotBean filterBean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		AdvancedCriteria criteria = filterBean.getCriteria();

		// creo il bean per la ricerca, ma inizializzo anche le variabili che mi servono per determinare se effettivamente posso eseguire il recupero dei dati
		FindRepositoryObjectBean lFindRepositoryObjectBean = createFindRepositoryObjectBean(criteria, loginBean);

		lFindRepositoryObjectBean.setColsToReturn("1");
		lFindRepositoryObjectBean.setOverflowlimitin(-1);

		List<Object> resFinder = null;
		try {
			resFinder = AurigaService.getFind().findrepositoryobject(getLocale(), loginBean, lFindRepositoryObjectBean).getList();
		} catch (Exception e) {
			throw new StoreException(e.getMessage());
		}

		String numTotRecOut = (String) resFinder.get(1);

		String errorMessageOut = null;

		if (resFinder.size() > 5) {
			errorMessageOut = (String) resFinder.get(5);
		}

		if (errorMessageOut != null && !"".equals(errorMessageOut)) {
			addMessage(errorMessageOut, "", MessageType.WARNING);
		}

		NroRecordTotBean retValue = new NroRecordTotBean();
		retValue.setNroRecordTot(Integer.valueOf(numTotRecOut));
		return retValue;
	}
	
	public PubblicazioneAlboConsultazioneRichiesteBean annullamentoPubblicazione(PubblicazioneAlboConsultazioneRichiesteBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		DmpkCoreUpddocudBean input = new DmpkCoreUpddocudBean();
		input.setCodidconnectiontokenin(loginBean.getToken());
		input.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()): null);
		input.setFlgtipotargetin("U");
		input.setIduddocin(bean.getIdUdFolder());
		
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		
		CreaModDocumentoInBean lModificaDocumentoInBean = new CreaModDocumentoInBean();
		lModificaDocumentoInBean.setIdAnnPubblicazione(bean.getIdRichPubbl());
		lModificaDocumentoInBean.setMotivoAnnPubblicazione(bean.getMotivoAnnPubblicazione());
		
		input.setAttributiuddocxmlin(lXmlUtilitySerializer.bindXml(lModificaDocumentoInBean));
		
		DmpkCoreUpddocud dmpkCoreUpddocud = new DmpkCoreUpddocud();
		StoreResultBean<DmpkCoreUpddocudBean> output = dmpkCoreUpddocud.execute(this.getLocale(), loginBean, input);
		if(output.isInError()) {
			throw new StoreException(output);
		}
		
		return bean;
	}
	
	public PubblicazioneAlboConsultazioneRichiesteBean prorogaTerminePubblicazione(PubblicazioneAlboConsultazioneRichiesteBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		DmpkCoreUpddocudBean input = new DmpkCoreUpddocudBean();
		input.setCodidconnectiontokenin(loginBean.getToken());
		input.setIduserlavoroin(StringUtils.isNotBlank(loginBean.getIdUserLavoro()) ? new BigDecimal(loginBean.getIdUserLavoro()): null);
		input.setFlgtipotargetin("U");
		input.setIduddocin(bean.getIdUdFolder());
		
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		
		CreaModDocumentoInBean lModificaDocumentoInBean = new CreaModDocumentoInBean();
		lModificaDocumentoInBean.setIdProrogaPubblicazione(bean.getIdRichPubbl());
		lModificaDocumentoInBean.setDataAProrogaPubblicazione(bean.getDataAProrogaPubblicazione());
		lModificaDocumentoInBean.setGiorniDurataProrogaPubblicazione(bean.getGiorniDurataProrogaPubblicazione());
		lModificaDocumentoInBean.setMotivoProrogaPubblicazione(bean.getMotivoProrogaPubblicazione());
		
		input.setAttributiuddocxmlin(lXmlUtilitySerializer.bindXml(lModificaDocumentoInBean));
		
		DmpkCoreUpddocud dmpkCoreUpddocud = new DmpkCoreUpddocud();
		StoreResultBean<DmpkCoreUpddocudBean> output = dmpkCoreUpddocud.execute(this.getLocale(), loginBean, input);
		if(output.isInError()) {
			throw new StoreException(output);
		}
		
		return bean;
		
	}
	
	public PubblicazioneAlboConsultazioneRichiesteBean getIstanzaCollegata(PubblicazioneAlboConsultazioneRichiesteBean bean) throws Exception {		
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());		

		RecuperaDocumentoInBean lRecuperaDocumentoInBean = new RecuperaDocumentoInBean();
		lRecuperaDocumentoInBean.setIdUd(bean.getIdUdFolder());
//		lRecuperaDocumentoInBean.setIdRichPubbl(bean.getIdRichPubbl());
		lRecuperaDocumentoInBean.setIdProcess(null);
		lRecuperaDocumentoInBean.setTaskName("#NONE");
		RecuperoDocumenti lRecuperoDocumenti = new RecuperoDocumenti();
		RecuperaDocumentoOutBean lRecuperaDocumentoOutBean = lRecuperoDocumenti.loaddocumento(getLocale(), lAurigaLoginBean, lRecuperaDocumentoInBean);
		if(lRecuperaDocumentoOutBean.isInError()) {
			throw new StoreException(lRecuperaDocumentoOutBean);
		}
		DocumentoXmlOutBean lDocumentoXmlOutBean = lRecuperaDocumentoOutBean.getDocumento();

//		ProtocolloUtility lProtocolloUtility = new ProtocolloUtility(getSession());
//		ProtocollazioneBean lProtocollazioneBean = lProtocolloUtility.getProtocolloFromDocumentoXml(lDocumentoXmlOutBean, getExtraparams());
		
		PubblicazioneAlboConsultazioneRichiesteBean pubblicazioneAlboBean = new PubblicazioneAlboConsultazioneRichiesteBean();
		
		pubblicazioneAlboBean.setIdUdFolder(bean.getIdUdFolder());
		if(lDocumentoXmlOutBean.getEstremiRegistrazione()!=null) {
			pubblicazioneAlboBean.setTipoRegNum(lDocumentoXmlOutBean.getEstremiRegistrazione().getTipo());
			pubblicazioneAlboBean.setSiglaRegNum(lDocumentoXmlOutBean.getEstremiRegistrazione().getSigla());
			pubblicazioneAlboBean.setAnnoRegNum(lDocumentoXmlOutBean.getEstremiRegistrazione().getAnno());
			pubblicazioneAlboBean.setTsRegistrazione(lDocumentoXmlOutBean.getEstremiRegistrazione().getTsRegistrazione());
			pubblicazioneAlboBean.setNroRegNum(lDocumentoXmlOutBean.getEstremiRegistrazione().getNro());
		}	
		pubblicazioneAlboBean.setOggetto(lDocumentoXmlOutBean.getOggetto());
		pubblicazioneAlboBean.setOggettoHtml(lDocumentoXmlOutBean.getOggettoHtml());
		
		return pubblicazioneAlboBean;
		
	}
	
}