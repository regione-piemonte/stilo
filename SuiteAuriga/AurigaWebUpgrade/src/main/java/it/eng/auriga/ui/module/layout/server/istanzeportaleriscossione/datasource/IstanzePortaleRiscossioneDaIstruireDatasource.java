/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileDeleteStrategy;
import org.apache.commons.lang3.StringUtils;

import it.eng.auriga.exception.StoreException;
import it.eng.auriga.function.bean.FindRepositoryObjectBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.CriteriPersonalizzatiUtil;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.CriteriPersonalizzatiUtil.TipoFiltro;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.CriteriAvanzati;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.CriteriPersonalizzati;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.MezziTrasmissioneFilterBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.Registrazione;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.Stato;
import it.eng.auriga.ui.module.layout.server.common.NroRecordTotBean;
import it.eng.auriga.ui.module.layout.server.istanzeportaleriscossione.datasource.bean.IstanzePortaleRiscossioneBean;
import it.eng.auriga.ui.module.layout.server.istanzeportaleriscossione.datasource.bean.IstanzePortaleRiscossioneXmlBean;
import it.eng.auriga.ui.module.layout.server.istanzeportaleriscossione.datasource.bean.IstanzePortaleRiscossioneXmlBeanDeserializationHelper;
import it.eng.auriga.ui.module.layout.server.istanzeportaleriscossione.datasource.bean.OperazioneMassivaIstanzePortaleRiscossioneBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.ProtocolloUtility;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.AllegatoProtocolloBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.DownloadDocsZipBean;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.ProtocollazioneBean;
import it.eng.client.AurigaService;
import it.eng.client.RecuperoDocumenti;
import it.eng.document.function.bean.DocumentoXmlOutBean;
import it.eng.document.function.bean.RecuperaDocumentoInBean;
import it.eng.document.function.bean.RecuperaDocumentoOutBean;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.utility.FileUtil;
import it.eng.utility.MessageUtil;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.storageutil.exception.StorageException;
import it.eng.utility.storageutil.util.StorageUtil;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.ExportBean;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AurigaAbstractFetchDatasource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.server.StringSplitterServer;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.utility.ui.user.ParametriDBUtil;
import it.eng.xml.XmlListaUtility;
import it.eng.xml.XmlUtilitySerializer;

@Datasource(id = "IstanzePortaleRiscossioneDaIstruireDatasource")
public class IstanzePortaleRiscossioneDaIstruireDatasource extends AurigaAbstractFetchDatasource<IstanzePortaleRiscossioneBean> {

	// 4 - N° istanza
	// 18 - Oggetto
	// 22 - Nome documento elettronico
	// 23 - N. allegati  - @FEBUONO 17/04/2019 ELIMINATO DA colsToReturn
	// 32 - Tipo di istanza
	// 93 - Nr. doc. con file
	// 101 - Tributo
	// 102 - Anno di riferimento
	// 103 - N° doc. di riferimento
	// 104 - Classificazione
	// 201 - Data presentazione (è una data e ora)
	// 220 - Contribuente - Nome
	// 221 - Contribuente - C. F.
	// 222 - Contribuente - Cod. ACS
	// 230 - Uri documento elettronico
	// 264 - Mezzo trasmissione

	protected String colsToReturn = "4,18,22,32,93,201,220,221,222,230,264,NOME_TRIBUTO,ANNO_TRIBUTO,NRO_DOC_RIF,CLASSIF_PORTALE";

	@Override
	public PaginatorBean<IstanzePortaleRiscossioneBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		List<IstanzePortaleRiscossioneBean> data = new ArrayList<IstanzePortaleRiscossioneBean>();
		boolean overflow = false;
		// creo il bean per la ricerca, ma inizializzo anche le variabili che mi servono per determinare se effettivamente posso eseguire il recupero dei dati
		FindRepositoryObjectBean lFindRepositoryObjectBean = createFindRepositoryObjectBean(criteria, loginBean);
		if (StringUtils.isNotBlank(lFindRepositoryObjectBean.getFiltroFullText())
				&& (lFindRepositoryObjectBean.getCheckAttributes() == null || lFindRepositoryObjectBean.getCheckAttributes().length == 0)) {
			addMessage("Specificare almeno un attributo su cui effettuare la ricerca full-text", "", MessageType.ERROR);
		} else {
			List<Object> resFinder = null;
			try {
				resFinder = AurigaService.getFind().findrepositoryobject(getLocale(), loginBean, lFindRepositoryObjectBean).getList();
			} catch (Exception e) {
				throw new StoreException(e.getMessage());
			}
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

			// Conversione ListaRisultati ==> EngResultSet
			if (xmlResultSetOut != null) {
				data = XmlListaUtility.recuperaLista(xmlResultSetOut, IstanzePortaleRiscossioneBean.class,
						new IstanzePortaleRiscossioneXmlBeanDeserializationHelper(createRemapConditionsMap()));
			}
		}

		// salvo i dati in sessione per renderli disponibili l'esportazione
		getSession().setAttribute(FETCH_SESSION_KEY, data);

		PaginatorBean<IstanzePortaleRiscossioneBean> lPaginatorBean = new PaginatorBean<IstanzePortaleRiscossioneBean>();
		lPaginatorBean.setData(data);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? data.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(data.size());
		lPaginatorBean.setOverflow(overflow);
		
		return lPaginatorBean;
	}

	protected FindRepositoryObjectBean createFindRepositoryObjectBean(AdvancedCriteria criteria, AurigaLoginBean loginBean) throws Exception {
		String filtroFullText = null;
		String[] checkAttributes = null;
		String formatoEstremiReg = null;
		Integer searchAllTerms = null;
		Long idFolder = null;
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
		String nrIstanzaDa = null;
		String nrIstanzaA = null;
		String nomeContribuente = null;
		String operContribuente = null;
		String cfContribuente = null;
		String codACSContribuente = null;
		Date tsIstanzaDa = null;
		Date tsIstanzaA = null;
		String oggetto = null;
		String tipoIstanza = null;
		String mezzoTrasmissioneFilter = null;

		List<CriteriPersonalizzati> listCustomFilters = new ArrayList<CriteriPersonalizzati>();
		CriteriAvanzati scCriteriAvanzati = new CriteriAvanzati();

		if (criteria != null && criteria.getCriteria() != null) {
			for (Criterion crit : criteria.getCriteria()) {
				if ("idFolder".equals(crit.getFieldName())) {
					if (StringUtils.isNotBlank((String) crit.getValue())) {
						idFolder = Long.parseLong((String) crit.getValue());
					}
				} else if ("nrIstanza".equals(crit.getFieldName())) {
					String[] estremiNrIstanza = getNumberFilterValue(crit);
					nrIstanzaDa = estremiNrIstanza[0] != null ? estremiNrIstanza[0] : null;
					nrIstanzaA = estremiNrIstanza[1] != null ? estremiNrIstanza[1] : null;
				} else if ("tsIstanza".equals(crit.getFieldName())) {
					Date[] estremiDataIstanza = getDateFilterValue(crit);
					if (tsIstanzaDa != null) {
						tsIstanzaDa = tsIstanzaDa.compareTo(estremiDataIstanza[0]) < 0 ? estremiDataIstanza[0] : tsIstanzaDa;
					} else {
						tsIstanzaDa = estremiDataIstanza[0];
					}
					if (tsIstanzaA != null) {
						tsIstanzaA = tsIstanzaA.compareTo(estremiDataIstanza[1]) > 0 ? estremiDataIstanza[1] : tsIstanzaA;
					} else {
						tsIstanzaA = estremiDataIstanza[1];
					}
				} else if ("tipoIstanza".equals(crit.getFieldName())) {
					tipoIstanza = getTextFilterValue(crit);
				} else if ("oggetto".equals(crit.getFieldName())) {
					oggetto = getTextFilterValue(crit);
				} else if ("nomeContribuente".equals(crit.getFieldName())) {
					nomeContribuente = getValueStringaFullTextMista(crit);
					operContribuente = getOperatorStringaFullTextMista(crit);
				} else if ("cfContribuente".equals(crit.getFieldName())) {
					cfContribuente = getTextFilterValue(crit);
				} else if ("codACSContribuente".equals(crit.getFieldName())) {
					codACSContribuente = getTextFilterValue(crit);
				} else if ("annoRiferimento".equals(crit.getFieldName())) {
					listCustomFilters.add(CriteriPersonalizzatiUtil.getCriterioPersonalizzato("ANNO_TRIBUTO", crit, TipoFiltro.NUMERO));
				} else if ("numeroDocRiferimento".equals(crit.getFieldName())) {
					listCustomFilters.add(CriteriPersonalizzatiUtil.getCriterioPersonalizzato("NRO_DOC_RIF", crit));
				} else if ("classificazionePortale".equals(crit.getFieldName())) {
					listCustomFilters.add(CriteriPersonalizzatiUtil.getCriterioPersonalizzato("CLASSIF_PORTALE", crit));
				} else if ("tributo".equals(crit.getFieldName())) {
					listCustomFilters.add(CriteriPersonalizzatiUtil.getCriterioPersonalizzato("NOME_TRIBUTO", crit));
				} else if ("mezzoTrasmissione".equals(crit.getFieldName())) {
					mezzoTrasmissioneFilter = getTextFilterValue(crit);
				}
			}
		}

		// N° istanza, Data Istanza
		List<Registrazione> listaRegistrazioni = new ArrayList<Registrazione>();
		if ((StringUtils.isNotBlank(nrIstanzaDa)) || (StringUtils.isNotBlank(nrIstanzaA)) || (tsIstanzaDa != null) || (tsIstanzaA != null)) {
			Registrazione registrazione = new Registrazione();
			registrazione.setCategoria(null);
			registrazione.setSigla(null);
			registrazione.setAnno(null);
			registrazione.setNumeroDa(nrIstanzaDa);
			registrazione.setNumeroA(nrIstanzaA);
			registrazione.setDataDa(tsIstanzaDa);
			registrazione.setDataA(tsIstanzaA);
			listaRegistrazioni.add(registrazione);
		}
		scCriteriAvanzati.setRegistrazioni(listaRegistrazioni);

		// Tipo Istanza
		if ((StringUtils.isNotBlank(tipoIstanza))) {
			scCriteriAvanzati.setNomeDocType(tipoIstanza);
		}

		// Oggetto
		if (StringUtils.isNotBlank(oggetto)) {
			scCriteriAvanzati.setOggettoUD(oggetto);
		}

		// Nome Contribuente
		if (StringUtils.isNotBlank(nomeContribuente)) {
			scCriteriAvanzati.setClienteUD(nomeContribuente);
		}
		if (StringUtils.isNotBlank(operContribuente)) {
			scCriteriAvanzati.setOperClienteUD(operContribuente);
		}

		// CF Contribuente
		if (StringUtils.isNotBlank(cfContribuente)) {
			scCriteriAvanzati.setpIvaCfClienteUD(cfContribuente);
		}

		// Cod. ACS Contribuente
		if (StringUtils.isNotBlank(codACSContribuente)) {
			scCriteriAvanzati.setCiProvClienteUD(codACSContribuente);
		}
		
		// Canale
		if (StringUtils.isNotBlank(mezzoTrasmissioneFilter)) {
			List<MezziTrasmissioneFilterBean> listMezzoTrasmissione = new ArrayList<MezziTrasmissioneFilterBean>();
			StringSplitterServer st = new StringSplitterServer(mezzoTrasmissioneFilter, ";");
			while (st.hasMoreTokens()) {
				MezziTrasmissioneFilterBean lMezziTrasmissioneFilter = new MezziTrasmissioneFilterBean();
				String chiave = st.nextToken();
				lMezziTrasmissioneFilter.setIdMezzoTrasmissione(chiave);
				listMezzoTrasmissione.add(lMezziTrasmissioneFilter);
			}
			scCriteriAvanzati.setMezziTrasmissione(listMezzoTrasmissione);
		}

		List<Stato> statiDettDoc = new ArrayList<Stato>();
		Stato lStatoDettDoc = new Stato();
		lStatoDettDoc.setDescrizione("istruttoria da avviare");
		statiDettDoc.add(lStatoDettDoc);
		scCriteriAvanzati.setStatiDettDoc(statiDettDoc);

		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		advancedFilters = lXmlUtilitySerializer.bindXml(scCriteriAvanzati);
		customFilters = lXmlUtilitySerializer.bindXmlList(listCustomFilters);
		FindRepositoryObjectBean lFindRepositoryObjectBean = new FindRepositoryObjectBean();
		lFindRepositoryObjectBean.setFlgUdFolder("U");
		lFindRepositoryObjectBean.setFiltroFullText(filtroFullText);
		lFindRepositoryObjectBean.setCheckAttributes(checkAttributes);
		lFindRepositoryObjectBean.setFormatoEstremiReg(formatoEstremiReg);
		lFindRepositoryObjectBean.setSearchAllTerms(searchAllTerms);
		lFindRepositoryObjectBean.setIdFolderSearchIn(idFolder);
		lFindRepositoryObjectBean.setFlgSubfoderSearchIn("1");
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
		saveParameters(loginBean, bean, jobId, loginBean.getIdUserLavoro(), IstanzePortaleRiscossioneXmlBean.class.getName());
		saveRemapInformations(loginBean, jobId, createRemapConditionsMap(), IstanzePortaleRiscossioneXmlBeanDeserializationHelper.class);
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
		filterBean.setNroRecordTot(Integer.valueOf(numTotRecOut));
		
		return filterBean;
	}

//	public DownloadDocsZipBean generateDocsZip(OperazioneMassivaIstanzePortaleRiscossioneBean bean) throws Exception {
//		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(this.getSession());
//		String limiteMaxZipErrorMessage = MessageUtil.getValue(getLocale().getLanguage(), null, "alert_archivio_list_limiteMaxZipError");
//		String errorMessage = getExtraparams().get("messageError");
//		String maxSize = ParametriDBUtil.getParametroDB(getSession(), "MAX_SIZE_ZIP");
//		int MAX_SIZE = (maxSize != null && maxSize.length() > 0 ? Integer.decode(maxSize) : 104857600);
//		long lengthZip = 0;
//		DownloadDocsZipBean retValue = new DownloadDocsZipBean();
//		
//		String tempPath = System.getProperty("java.io.tmpdir") + System.getProperty("file.separator");		
//		Date date = new Date();
//	    long timeMilli = date.getTime();
//		File zipFile = new File(tempPath + "FileSelezioneDocumenti_" +  timeMilli + ".zip");
//		
//		Map<String, Integer> zipFileNames = new HashMap<String, Integer>();
//		for (int i = 0; i < bean.getListaRecord().size(); i++) {
//			String idUd = bean.getListaRecord().get(i).getIdUdFolder();
//			String segnatura = getSegnature(bean.getListaRecord().get(i).getSegnatura());
//
//			RecuperaDocumentoInBean lRecuperaDocumentoInBean = new RecuperaDocumentoInBean();
//			lRecuperaDocumentoInBean.setIdUd(new BigDecimal(idUd));
//			RecuperoDocumenti lRecuperoDocumenti = new RecuperoDocumenti();
//
//			RecuperaDocumentoOutBean lRecuperaDocumentoOutBean = lRecuperoDocumenti.loaddocumento(getLocale(), loginBean, lRecuperaDocumentoInBean);
//
//			if (StringUtils.isNotEmpty(lRecuperaDocumentoOutBean.getDefaultMessage())) {
//				String message = lRecuperaDocumentoOutBean.getDefaultMessage();
//
//				throw new StoreException(errorMessage != null ? errorMessage : message);
//			}
//			DocumentoXmlOutBean lDocumentoXmlOutBean = lRecuperaDocumentoOutBean.getDocumento();
//			ProtocolloUtility lProtocolloUtility = new ProtocolloUtility(getSession());
//			ProtocollazioneBean documento = lProtocolloUtility.getProtocolloFromDocumentoXml(lDocumentoXmlOutBean);
//			String attachmentFileName = null;
//			lengthZip += documento.getInfoFile().getBytes();
//			if (lengthZip > MAX_SIZE) {
//				throw new StoreException(limiteMaxZipErrorMessage);
//			}
//			if (documento.getNomeFilePrimario() != null) {
//				attachmentFileName = segnatura + "_" + documento.getNomeFilePrimario();
//				attachmentFileName = checkAndRenameDuplicate(zipFileNames, attachmentFileName);
//				File currentAttachmentPrimario = File.createTempFile("temp", attachmentFileName);
//				addFileZip(documento.getInfoFile(), documento.getUriFilePrimario(), attachmentFileName, currentAttachmentPrimario, zipFile);
//			}
//			if (documento.getListaAllegati() != null) {
//				for (AllegatoProtocolloBean allegatoUdBean : documento.getListaAllegati()) {
//					lengthZip += allegatoUdBean.getInfoFile().getBytes();
//					if (lengthZip > MAX_SIZE) {
//						throw new StoreException(limiteMaxZipErrorMessage);
//					}
//					attachmentFileName = segnatura + "_" + allegatoUdBean.getNomeFileAllegato();
//					attachmentFileName = checkAndRenameDuplicate(zipFileNames, attachmentFileName);
//					File currentAttachment = File.createTempFile("temp", attachmentFileName);
//					addFileZip(allegatoUdBean.getInfoFile(), allegatoUdBean.getUriFileAllegato(), attachmentFileName, currentAttachment, zipFile);
//				}
//			}
//		}
//		try {
//			if (!zipFileNames.isEmpty()) {
//				String zipUri = StorageImplementation.getStorage().store(zipFile);
//				retValue.setStorageZipRemoteUri(zipUri);
//				retValue.setZipName(zipFile.getName());
//			} else {
//				retValue.setMessage("Le unità documentarie selezionate non hanno nessun file");
//			}
//		} finally {
//			// una volta salvato in storage lo posso eliminare localmente
//			FileDeleteStrategy.FORCE.delete(zipFile);
//		}
//		return retValue;
//	}

	private String checkAndRenameDuplicate(Map<String, Integer> zipFileNames, String attachmentFileName) {
		boolean duplicated = zipFileNames.containsKey(attachmentFileName);
		Integer index = 0;
		if (duplicated) {
			int x = zipFileNames.get(attachmentFileName);
			String attachmentFileNameoriginale = attachmentFileName;
			while (zipFileNames.containsKey(attachmentFileName)) {
				attachmentFileName = normalizeDuplicate(attachmentFileNameoriginale, x);
				x++;
			}
			zipFileNames.put(attachmentFileName, index);
		} else {
			zipFileNames.put(attachmentFileName, index);
		}
		return attachmentFileName;
	}

	public String getSegnature(String segnatura) {
		if (segnatura != null) {
			segnatura = segnatura.replace(" ", "_");
			segnatura = segnatura.replace(".", "_");
			segnatura = segnatura.replace("\\", "_");
			segnatura = segnatura.replace("/", "_");
		} else {
			Calendar currentDate = Calendar.getInstance();
			segnatura = Integer.toString(currentDate.get(Calendar.DAY_OF_WEEK)) + "_" + Integer.toString(currentDate.get(Calendar.HOUR))
					+ Integer.toString(currentDate.get(Calendar.MINUTE)) + Integer.toString(currentDate.get(Calendar.SECOND))
					+ Integer.toString(currentDate.get(Calendar.MILLISECOND));
		}
		return segnatura;
	}

	private String normalizeDuplicate(String value, Integer index) {

		String[] tokens = value.split("\\.");
		String attachmentFileName = "";
		if (tokens.length >= 2) {
			for (int x = 0; x < tokens.length - 1; x++)
				attachmentFileName += "." + tokens[x];

			attachmentFileName += "_" + index + "." + tokens[tokens.length - 1];
			// tolgo il primo punto
			attachmentFileName = attachmentFileName.substring(1, attachmentFileName.length());
		} else
			attachmentFileName = tokens[0] + "_" + index + (tokens.length > 1 ? "." + tokens[1] : "");

		return attachmentFileName;
	}

	private Map<String, String> createRemapConditionsMap() {
		Map<String, String> retValue = new LinkedHashMap<String, String>();
		String idNode = getExtraparams().get("idNode");
		retValue.put("idNode", idNode);
		return retValue;
	}

	public ProtocollazioneBean getAbilContextMenu(ProtocollazioneBean bean) throws Exception {
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		String flgSoloAbilAzioni = getExtraparams().get("flgSoloAbilAzioni");
		RecuperaDocumentoInBean lRecuperaDocumentoInBean = new RecuperaDocumentoInBean();
		lRecuperaDocumentoInBean.setIdUd(bean.getIdUd());
		lRecuperaDocumentoInBean.setFlgSoloAbilAzioni(flgSoloAbilAzioni);
		RecuperoDocumenti lRecuperoDocumenti = new RecuperoDocumenti();
		RecuperaDocumentoOutBean lRecuperaDocumentoOutBean = lRecuperoDocumenti.loaddocumento(getLocale(), lAurigaLoginBean, lRecuperaDocumentoInBean);
		if(lRecuperaDocumentoOutBean.isInError()) {
			throw new StoreException(lRecuperaDocumentoOutBean);
		}
		DocumentoXmlOutBean lDocumentoXmlOutBean = lRecuperaDocumentoOutBean.getDocumento();
		ProtocolloUtility lProtocolloUtility = new ProtocolloUtility(getSession());
		ProtocollazioneBean lProtocollazioneBean = lProtocolloUtility.getProtocolloFromDocumentoXml(lDocumentoXmlOutBean, getExtraparams());
		lProtocollazioneBean.setIdUd(bean.getIdUd());
		lProtocollazioneBean.setIdEmailArrivo(lDocumentoXmlOutBean.getIdEmailArrivo());
		lProtocollazioneBean.setEmailArrivoInteroperabile(lDocumentoXmlOutBean.getEmailArrivoInteroperabile());
		lProtocollazioneBean.setEmailInviataFlgPEC(lDocumentoXmlOutBean.getEmailInviataFlgPEC());
		lProtocollazioneBean.setEmailInviataFlgPEO(lDocumentoXmlOutBean.getEmailInviataFlgPEO());
		lProtocollazioneBean.setSegnatura(lDocumentoXmlOutBean.getSegnatura());
		return lProtocollazioneBean;
	}

	public IstanzePortaleRiscossioneBean getFile(IstanzePortaleRiscossioneBean bean) throws Exception {
		boolean isPropostaAtto = false;
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());

		RecuperaDocumentoInBean lRecuperaDocumentoInBean = new RecuperaDocumentoInBean();
		lRecuperaDocumentoInBean.setIdUd(new BigDecimal(bean.getIdUdFolder()));

		RecuperoDocumenti lRecuperoDocumenti = new RecuperoDocumenti();
		RecuperaDocumentoOutBean lRecuperaDocumentoOutBean = lRecuperoDocumenti.loaddocumento(getLocale(), lAurigaLoginBean, lRecuperaDocumentoInBean);
		if(lRecuperaDocumentoOutBean.isInError()) {
			throw new StoreException(lRecuperaDocumentoOutBean);
		}
		DocumentoXmlOutBean lDocumentoXmlOutBean = lRecuperaDocumentoOutBean.getDocumento();

		ProtocolloUtility lProtocolloUtility = new ProtocolloUtility(getSession());
		ProtocollazioneBean lProtocollazioneBean = lProtocolloUtility.getProtocolloFromDocumentoXml(lDocumentoXmlOutBean, getExtraparams());

		IstanzePortaleRiscossioneBean out = new IstanzePortaleRiscossioneBean();
		out.setIdUdFolder(bean.getIdUdFolder());
		out.setIdDocPrimario(lProtocollazioneBean.getIdDocPrimario());
		out.setNomeFilePrimario(lProtocollazioneBean.getNomeFilePrimario());
		out.setUriFilePrimario(lProtocollazioneBean.getUriFilePrimario());
		out.setRemoteUriFilePrimario(lProtocollazioneBean.getRemoteUriFilePrimario());
		out.setInfoFile(lProtocollazioneBean.getInfoFile());
		out.setListaAllegati(lProtocollazioneBean.getListaAllegati());

		return out;
	}

	@Override
	public IstanzePortaleRiscossioneBean get(IstanzePortaleRiscossioneBean bean) throws Exception {
		return bean;
	}

	@Override
	public IstanzePortaleRiscossioneBean update(IstanzePortaleRiscossioneBean bean, IstanzePortaleRiscossioneBean oldvalue) throws Exception {
		return bean;
	}

	@Override
	public IstanzePortaleRiscossioneBean add(IstanzePortaleRiscossioneBean bean) throws Exception {
		return bean;
	}

	@Override
	public IstanzePortaleRiscossioneBean remove(IstanzePortaleRiscossioneBean bean) throws Exception {
		return bean;
	}
}