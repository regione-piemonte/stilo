/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import javax.xml.bind.JAXBException;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_core_2.bean.DmpkCore2FirmasudocinlibrofirmaBean;
import it.eng.auriga.database.store.dmpk_core_2.bean.DmpkCore2TrovadocinlibrofirmaBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.common.CalcolaImpronteService;
import it.eng.auriga.ui.module.layout.server.common.NroRecordTotBean;
import it.eng.auriga.ui.module.layout.server.firmamultipla.bean.FirmaMassivaFilesBean;
import it.eng.auriga.ui.module.layout.server.librofirmamassiva.datasource.bean.DocDaFirmareBean;
import it.eng.auriga.ui.module.layout.server.librofirmamassiva.datasource.bean.FileToLibroFirma;
import it.eng.auriga.ui.module.layout.server.librofirmamassiva.datasource.bean.FiltriLibroFirmaBean;
import it.eng.auriga.ui.module.layout.server.librofirmamassiva.datasource.bean.OperazioneMassivaDocDaFirmareBean;
import it.eng.client.DmpkCore2Firmasudocinlibrofirma;
import it.eng.client.DmpkCore2Trovadocinlibrofirma;
import it.eng.client.SalvataggioFile;
import it.eng.document.function.bean.DocDaFirmareXmlBean;
import it.eng.document.function.bean.FileSavedIn;
import it.eng.document.function.bean.FileSavedOut;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.services.fileop.InfoFileUtility;
import it.eng.utility.XmlUtility;
import it.eng.utility.module.config.StorageImplementation;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.ExportBean;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AurigaAbstractFetchDatasource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.shared.bean.FileDaFirmareBean;
import it.eng.utility.ui.module.layout.shared.bean.IdFileBean;
import it.eng.utility.ui.servlet.bean.Firmatari;
import it.eng.utility.ui.servlet.bean.InfoFirmaMarca;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlListaUtility;
import it.eng.xml.XmlUtilitySerializer;

@Datasource(id="LibroFirmaMassivaDatasource")
public class LibroFirmaMassivaDatasource extends AurigaAbstractFetchDatasource<DocDaFirmareBean>{

	private static Logger mLogger = Logger.getLogger(LibroFirmaMassivaDatasource.class);
		
	@Override
	public PaginatorBean<DocDaFirmareBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {

		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		String filter = getFilterString(criteria);

		DmpkCore2TrovadocinlibrofirmaBean inputBean = new DmpkCore2TrovadocinlibrofirmaBean();
		inputBean.setCodidconnectiontokenin(lAurigaLoginBean.getToken());
		inputBean.setFiltriio(filter);
		inputBean.setFlgsenzapaginazionein(1);


		DmpkCore2Trovadocinlibrofirma libroFirmaStore = new DmpkCore2Trovadocinlibrofirma();
		StoreResultBean<DmpkCore2TrovadocinlibrofirmaBean> output = libroFirmaStore.execute(getLocale(), lAurigaLoginBean, inputBean);
		
		boolean overflow = false;
		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				mLogger.error("Errore nel recupero dell'output: " + output.getDefaultMessage());
				throw new StoreException(output);
			} else {
				overflow = manageOverflow(output.getDefaultMessage());
			}
		}
		
		
		String xmlOut = output.getResultBean().getResultout();
		List<DocDaFirmareBean> returnValue = new ArrayList<DocDaFirmareBean>();	

		if (xmlOut != null) {
			List<DocDaFirmareXmlBean> storedProcedureResults = XmlListaUtility.recuperaLista(xmlOut, DocDaFirmareXmlBean.class);
			
			
			for (DocDaFirmareXmlBean docDaFirmare : storedProcedureResults){			
				DocDaFirmareBean currentRetrievedBean = new DocDaFirmareBean();
				BeanUtilsBean2.getInstance().copyProperties(currentRetrievedBean, docDaFirmare);
				returnValue.add(currentRetrievedBean);
			}	
		}
		
		PaginatorBean<DocDaFirmareBean> lPaginatorBean = new PaginatorBean<DocDaFirmareBean>();
		lPaginatorBean.setData(returnValue);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? returnValue.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(returnValue.size());
		lPaginatorBean.setOverflow(overflow);
		return lPaginatorBean;
	}

	protected String getFilterString(AdvancedCriteria criteria) throws StoreException, Exception, JAXBException,
			IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		FiltriLibroFirmaBean lFilterBean = new FiltriLibroFirmaBean();
		
		Date dataInvioInFirmaDal = null;
		Date dataInvioInFirmaAl = null;
		
		if(criteria!=null && criteria.getCriteria()!=null){		
			for(Criterion crit : criteria.getCriteria()){
				if("firmatario".equals(crit.getFieldName())) {
					if (StringUtils.isNotBlank(getTextFilterValue(crit))) {
						lFilterBean.setFirmatario(getTextFilterValue(crit));
					} else {
						throw new StoreException("Il filtro \"firmatario\" è obbligatorio");
					}
				}
				if("dataInvioInFirma".equals(crit.getFieldName())) {
					Date[] estremiDataRaccomandata = getDateFilterValue(crit);
					if (dataInvioInFirmaDal != null) {
						dataInvioInFirmaDal = dataInvioInFirmaDal.compareTo(estremiDataRaccomandata[0]) < 0 ? estremiDataRaccomandata[0] : dataInvioInFirmaDal;
					} else {
						dataInvioInFirmaDal = estremiDataRaccomandata[0];
					}
					if (dataInvioInFirmaAl != null) {
						dataInvioInFirmaAl = dataInvioInFirmaAl.compareTo(estremiDataRaccomandata[1]) > 0 ? estremiDataRaccomandata[1] : dataInvioInFirmaAl;
					} else {
						dataInvioInFirmaAl = estremiDataRaccomandata[1];
					}
					lFilterBean.setDataInvioInFirmaAl(dataInvioInFirmaAl);
					lFilterBean.setDataInvioInFirmaDal(dataInvioInFirmaDal);
				}
				if("tipoDoc".equals(crit.getFieldName())) {
					lFilterBean.setListaIdTipiDoc(getTextFilterValue(crit));
				}
			}			
		}
		
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		String filter = lXmlUtilitySerializer.bindXml(lFilterBean);
		return filter;
	} 
	
	public OperazioneMassivaDocDaFirmareBean rifiutaFirma(OperazioneMassivaDocDaFirmareBean bean) throws Exception {

		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		DmpkCore2FirmasudocinlibrofirmaBean inputBean = new DmpkCore2FirmasudocinlibrofirmaBean();
		inputBean.setAzionein("rifiuta_firma");
		inputBean.setFirmatarioin(getExtraparams().get("firmatario"));
		
		List<FileToLibroFirma> filesToStore = new ArrayList<>();
		for (DocDaFirmareBean docBean: bean.getListaRecord()) {
			FileToLibroFirma lFile = new FileToLibroFirma();
			lFile.setIdDoc(""+ docBean.getIdDoc());
			filesToStore.add(lFile);
		}
		
		String listaDoc = new XmlUtilitySerializer().bindXmlList(filesToStore);
		inputBean.setDocinfoin(listaDoc);

		DmpkCore2Firmasudocinlibrofirma store = new DmpkCore2Firmasudocinlibrofirma();
		StoreResultBean<DmpkCore2FirmasudocinlibrofirmaBean> output = store.execute(getLocale(), lAurigaLoginBean, inputBean);

		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			throw new StoreException(output);
		}

		HashMap<String, String> errorMessages = null;		
		if (output.getResultBean().getEsitiout() != null && output.getResultBean().getEsitiout().length() > 0) {
			errorMessages = new HashMap<String, String>();
			StringReader sr = new StringReader(output.getResultBean().getEsitiout());
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
			for (int i = 0; i < lista.getRiga().size(); i++) {
				Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));
				if (v.get(3).equalsIgnoreCase("ko")) {
					errorMessages.put(v.get(2), v.get(4));
				}
			}
		}
		bean.setErrorMessages(errorMessages);

		return bean;
	}
	
	public OperazioneMassivaDocDaFirmareBean apponiFirma(FirmaMassivaFilesBean bean) throws Exception {
		
		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		DmpkCore2FirmasudocinlibrofirmaBean inputBean = new DmpkCore2FirmasudocinlibrofirmaBean();
		inputBean.setAzionein("firma");
		inputBean.setFirmatarioin(getExtraparams().get("firmatario"));
		mLogger.debug(bean.getFiles() != null ? "Sono arrivati " + bean.getFiles().size() + " file in apponiFirma" : "bean.getFiles è null");
		List<FileToLibroFirma> filesToStore = new ArrayList<>();
		for (FileDaFirmareBean docBean: bean.getFiles()) {
			// Prima di invocare la store devo salvare il file in archivio
			File lFileToSave = StorageImplementation.getStorage().extractFile(docBean.getUri());
			FileSavedIn lFileSavedIn = new FileSavedIn();
			lFileSavedIn.setSaved(lFileToSave);
			Locale local = new Locale(lAurigaLoginBean.getLinguaApplicazione());
			SalvataggioFile lSalvataggioFile = new SalvataggioFile();
			FileSavedOut out = lSalvataggioFile.savefile(local, lAurigaLoginBean, lFileSavedIn);
						
			FileToLibroFirma lFile = new FileToLibroFirma();
			lFile.setIdDoc(docBean.getIdFile());
			lFile.setUriFile(out.getUri());
			lFile.setTipoFirma(docBean.getInfoFile().getTipoFirma());
			lFile.setMimetype(docBean.getInfoFile().getMimetype());
			lFile.setDimensione(Long.toString(docBean.getInfoFile().getBytes()));
			lFile.setImpronta(docBean.getInfoFile().getImpronta());
			lFile.setAlgoritmoImpronta(docBean.getInfoFile().getAlgoritmo());
			lFile.setEncodingImpronta(docBean.getInfoFile().getEncoding());
			String firmatari = "";
			if (docBean.getInfoFile().getFirmatari() != null && docBean.getInfoFile().getFirmatari().length > 0) {
				for (String firmatario : docBean.getInfoFile().getFirmatari()) {
					firmatari += firmatario + "|*|";
				}
			}
			lFile.setFirmatari(firmatari);	
			// Il file è sicuramente firmato, verifico di avere le buste
			if (docBean.getInfoFile().getBuste() != null && docBean.getInfoFile().getBuste().length == 0) {
				// Se non ho le buste le calcolo tramite fileOp
				InfoFileUtility lInfoFileUtility = new InfoFileUtility();
				MimeTypeFirmaBean infoFile = lInfoFileUtility.getInfoFromFile(lFileToSave.toURI().toString(), lFileToSave.getName(), false, null);
				docBean.getInfoFile().setBuste(infoFile.getBuste());
			}
			// Prendo il firmatario più recente
			Firmatari firmatarioPiuRecente =  null;
			if (docBean.getInfoFile().getBuste() != null) { 
				for (Firmatari bustaFileFirmato : docBean.getInfoFile().getBuste()) {
					if (firmatarioPiuRecente == null || (bustaFileFirmato.getDataFirma() != null && firmatarioPiuRecente.getDataFirma() != null && bustaFileFirmato.getDataFirma().before(firmatarioPiuRecente.getDataFirma()))){
						firmatarioPiuRecente = bustaFileFirmato;
					}
				}
			}
			if (firmatarioPiuRecente != null) {
				lFile.setDataOraEmissioneCertificatoFirma(firmatarioPiuRecente.getDataEmissione());
				lFile.setDataOraScadenzaCertificatoFirma(firmatarioPiuRecente.getDataScadenza());
				lFile.setTipoFirmaQA(firmatarioPiuRecente.getTipoFirmaQA());
			}
			filesToStore.add(lFile);
		}
		mLogger.debug("Size filesToStore: " + filesToStore.size());
		String listaDoc = new XmlUtilitySerializer().bindXmlList(filesToStore);
		inputBean.setDocinfoin(listaDoc);
		
		mLogger.debug("listaDoc: " + listaDoc);

		DmpkCore2Firmasudocinlibrofirma store = new DmpkCore2Firmasudocinlibrofirma();
		StoreResultBean<DmpkCore2FirmasudocinlibrofirmaBean> output = store.execute(getLocale(), lAurigaLoginBean, inputBean);

		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			mLogger.error("Errore nella chiamata a DmpkCore2Firmasudocinlibrofirma: " + output.getDefaultMessage());
			throw new StoreException(output);
		}

		HashMap<String, String> errorMessages = null;		
		if (output.getResultBean().getEsitiout() != null && output.getResultBean().getEsitiout().length() > 0) {
			errorMessages = new HashMap<String, String>();
			StringReader sr = new StringReader(output.getResultBean().getEsitiout());
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);
			for (int i = 0; i < lista.getRiga().size(); i++) {
				Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));
				if (v.get(3).equalsIgnoreCase("ko")) {
					errorMessages.put(v.get(2), v.get(4));
				}
			}
		}
		
		OperazioneMassivaDocDaFirmareBean result = new OperazioneMassivaDocDaFirmareBean();
		result.setErrorMessages(errorMessages);

		return result;
		
	}
	
	public FileDaFirmareBean getFile(DocDaFirmareBean bean) throws Exception {

		FileDaFirmareBean lFileDaFirmareBean = new FileDaFirmareBean();			
		lFileDaFirmareBean.setIdFile("" + bean.getIdDoc());
		
		String nomeFile = bean.getDisplayFilename();
		String uri = bean.getUriFile();
		File lFile = StorageImplementation.getStorage().extractFile(bean.getUriFile());
//		InfoFileUtility lInfoFileUtility = new InfoFileUtility();
//		MimeTypeFirmaBean infoFile = lInfoFileUtility.getInfoFromFile(lFile.toURI().toString(), bean.getDisplayFilename(), false, null);
		
		MimeTypeFirmaBean infoFile = new MimeTypeFirmaBean();
		infoFile.setCorrectFileName(bean.getDisplayFilename());
		infoFile.setMimetype(bean.getMimetype());
		infoFile.setConvertibile(bean.getFlgPdfizzabile() != null && bean.getFlgPdfizzabile().intValue() == 1);
		infoFile.setFirmato(bean.getFlgFirmato() != null && bean.getFlgFirmato().intValue() == 1);
		infoFile.setTipoFirma(bean.getTipoBustaCrittografica());
		infoFile.setBytes(lFile.length());
		infoFile.setImpronta((new CalcolaImpronteService()).calcolaImprontaWithoutFileOp(lFile, "SHA-256", "base64"));
		
		InfoFirmaMarca lInfoFirmaMarca = new InfoFirmaMarca();
		lInfoFirmaMarca.setBustaCrittograficaFattaDaAuriga(bean.getBustaCrittograficaFattaDaAuriga() != null && "1".equalsIgnoreCase(bean.getBustaCrittograficaFattaDaAuriga()));
		lInfoFirmaMarca.setFirmeNonValideBustaCrittografica(bean.getFirmeNonValideBustaCrittografica() != null && "1".equalsIgnoreCase(bean.getFirmeNonValideBustaCrittografica()));
		lInfoFirmaMarca.setTipoBustaCrittografica(bean.getTipoBustaCrittografica());
		infoFile.setInfoFirmaMarca(lInfoFirmaMarca);
		
		lFileDaFirmareBean.setInfoFile(infoFile);
		lFileDaFirmareBean.setNomeFile(nomeFile);			
		lFileDaFirmareBean.setUri(uri);
		bean.getNroAllegato();
		boolean isAtto = bean.getFlgAtto() != null && bean.getFlgAtto().intValue() == 1;
		boolean isFilePrincipale = bean.getNroAllegato() != null && bean.getNroAllegato().intValue() == 0;
		lFileDaFirmareBean.setIsFilePrincipaleAtto(isAtto && isFilePrincipale);
				
		return lFileDaFirmareBean;
	}

	public FirmaMassivaFilesBean getListaPerFirmaMassiva(OperazioneMassivaDocDaFirmareBean bean) throws Exception {
		
		FirmaMassivaFilesBean lFirmaMassivaFilesBean = new FirmaMassivaFilesBean();
		List<FileDaFirmareBean> lListFiles = new ArrayList<FileDaFirmareBean>();
		List<IdFileBean> lListFilesError = new ArrayList<IdFileBean>();
		for(int i = 0; i < bean.getListaRecord().size(); i++) {
			
			DocDaFirmareBean lDocDaRifirmareBean = bean.getListaRecord().get(i);
			
			try {
				
				FileDaFirmareBean lFileDaFirmareBean = getFile(lDocDaRifirmareBean);
				lListFiles.add(lFileDaFirmareBean);
				
			} catch(Exception e) {
				mLogger.error(e.getMessage());
				IdFileBean errorFile = new IdFileBean();
				errorFile.setIdDoc(String.valueOf(lDocDaRifirmareBean.getIdDoc()));
				errorFile.setIdFile(""+lDocDaRifirmareBean.getIdDoc());
				errorFile.setIdUd(""+lDocDaRifirmareBean.getIdUd());
				errorFile.setNomeFile(lDocDaRifirmareBean.getDisplayFilename());
				errorFile.setUri(lDocDaRifirmareBean.getUriFile());
				lListFilesError.add(errorFile);
				if(bean.getListaRecord().size() == 1) {
					throw new Exception(e.getMessage());
				}
			}					
			
		}	
		
		if(bean.getListaRecord().size() > 1) {
			if(lListFiles.size() == 0) {
				throw new Exception("Tutti i documenti selezionati per la firma massiva sono andati in errore");
			} else if(bean.getListaRecord().size() > lListFiles.size()) {			
				addMessage("Alcuni dei documenti selezionati per la firma massiva sono andati in errore", "", MessageType.WARNING);
			}
		}
		
		lFirmaMassivaFilesBean.setDaNonTrasmettere(lListFilesError);
		lFirmaMassivaFilesBean.setFiles(lListFiles);
		
		return lFirmaMassivaFilesBean;
	}
	
	public FirmaMassivaFilesBean verificaDocumentoFirmato(FirmaMassivaFilesBean pFirmaMassivaFilesBean) throws Exception{

		FirmaMassivaFilesBean lFirmaMassivaFilesBean = new FirmaMassivaFilesBean();
		List<FileDaFirmareBean> filesOK = new ArrayList<FileDaFirmareBean>();
		List<IdFileBean> filesInError = new ArrayList<>();
		int numeroFileDaVerificare = pFirmaMassivaFilesBean != null ? (pFirmaMassivaFilesBean.getFiles() != null ? pFirmaMassivaFilesBean.getFiles().size() : -1) : -2; 
		mLogger.debug("Sono arrivati " + numeroFileDaVerificare + " file da verificare");
		for (FileDaFirmareBean lFileDaFirmareBean : pFirmaMassivaFilesBean.getFiles()){
			mLogger.debug("Analizzo il file " + lFileDaFirmareBean.getNomeFile() + ", idFile: " + lFileDaFirmareBean.getIdFile() + ", uri: " + lFileDaFirmareBean.getUri());			
			try {
				if (!lFileDaFirmareBean.getNomeFile().toLowerCase().endsWith(".p7m")) {
					lFileDaFirmareBean.setNomeFile(lFileDaFirmareBean.getNomeFile() + ".p7m");
					mLogger.debug("Cambio il nome file in " + lFileDaFirmareBean.getNomeFile());
				}				
				boolean firmaEseguita = lFileDaFirmareBean.getFirmaEseguita() != null && lFileDaFirmareBean.getFirmaEseguita();
				mLogger.debug("Nel file " + lFileDaFirmareBean.getNomeFile() + " firmaEseguita: " + lFileDaFirmareBean.getFirmaEseguita() + ", isFirmato: " + lFileDaFirmareBean.getInfoFile().isFirmato() + " isFirmaValida: " + lFileDaFirmareBean.getInfoFile().isFirmaValida());
				// Non rifaccio il controllo con FileOp perchè viene già fatto in AppletDatasource al ritorno dall'applet di firma
				if (!firmaEseguita || !lFileDaFirmareBean.getInfoFile().isFirmato() || !lFileDaFirmareBean.getInfoFile().isFirmaValida()) { 
					mLogger.error("Il file " + lFileDaFirmareBean.getNomeFile() + " non ha firma valida");
					throw new Exception("Firma non presente o non valida");
				}
				mLogger.debug("Aggiungo il file " + lFileDaFirmareBean.getNomeFile() + " in filesOK");
				filesOK.add(lFileDaFirmareBean);
			} catch (Exception e) {
				mLogger.debug("Aggiungo il file " + lFileDaFirmareBean.getNomeFile() + " in filesInError");
				mLogger.error("Errore nella veririfica del file " + lFileDaFirmareBean.getNomeFile(), e);
				filesInError.add(lFileDaFirmareBean);
			}
		}
					
		mLogger.debug("size filesOK:" + filesOK.size() + " size filesInError: " + filesInError.size());
		lFirmaMassivaFilesBean.setFiles(filesOK);
		lFirmaMassivaFilesBean.setDaNonTrasmettere(filesInError);
		return lFirmaMassivaFilesBean;
	}

	@Override
	protected ExportBean asyncExport(ExportBean bean) throws Exception {
		return null;
	}

	@Override
	public NroRecordTotBean getNroRecordTotali(NroRecordTotBean bean) throws Exception {

		AurigaLoginBean lAurigaLoginBean = AurigaUserUtil.getLoginInfo(getSession());
		
		String filter = getFilterString(bean.getCriteria());
		
		mLogger.debug("filter: " + filter);

		DmpkCore2TrovadocinlibrofirmaBean inputBean = new DmpkCore2TrovadocinlibrofirmaBean();
		inputBean.setCodidconnectiontokenin(lAurigaLoginBean.getToken());
		inputBean.setFiltriio(filter);
		inputBean.setFlgsenzapaginazionein(1);
		inputBean.setOverflowlimitin(-1);

		DmpkCore2Trovadocinlibrofirma libroFirmaStore = new DmpkCore2Trovadocinlibrofirma();
		StoreResultBean<DmpkCore2TrovadocinlibrofirmaBean> output = libroFirmaStore.execute(getLocale(), lAurigaLoginBean, inputBean);
		
		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				mLogger.error("Errore nel recupero dell'output: " + output.getDefaultMessage());
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		}

		// SETTO L'OUTPUT
		if (output.getResultBean().getResultout() != null) {
			mLogger.debug("NroRecordTot: " + output.getResultBean().getNrototrecout());
			bean.setNroRecordTot(output.getResultBean().getNrototrecout());
		}

		return bean;
	}
		
	
}
