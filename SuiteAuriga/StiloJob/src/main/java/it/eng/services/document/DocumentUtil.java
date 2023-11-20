/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.BeansException;

import com.itextpdf.text.pdf.PdfReader;

import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreAdddocBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreAddverdocBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreExtractverdocBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreGetregnumdateudBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreTestpresenzafileBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreUpddocudBean;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreUpdverdocBean;
import it.eng.auriga.database.store.dmpk_repository_gui.bean.DmpkRepositoryGuiLoadsoggestdocBean;
import it.eng.auriga.database.store.dmpk_utility.bean.DmpkUtilityFindud_jBean;
import it.eng.auriga.database.store.dmpk_utility.bean.DmpkUtilityGetiddocprimarioallegatofromudBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.dao.beans.DownloadTicketBean;
import it.eng.client.DaoDownloadTicket;
import it.eng.client.DmpkCoreAdddoc;
import it.eng.client.DmpkCoreAddverdoc;
import it.eng.client.DmpkCoreExtractverdoc;
import it.eng.client.DmpkCoreGetregnumdateud;
import it.eng.client.DmpkCoreTestpresenzafile;
import it.eng.client.DmpkCoreUpddocud;
import it.eng.client.DmpkCoreUpdverdoc;
import it.eng.client.DmpkRepositoryGuiLoadsoggestdoc;
import it.eng.client.DmpkUtilityFindud_j;
import it.eng.client.DmpkUtilityGetiddocprimarioallegatofromud;
import it.eng.client.GestioneDocumenti;
import it.eng.client.RecuperoDocumenti;
import it.eng.constants.AurigaSpringContext;
import it.eng.database.utility.HibernateUtil;
import it.eng.document.NumeroColonna;
import it.eng.document.function.bean.AllegatiBean;
import it.eng.document.function.bean.AllegatiOutBean;
import it.eng.document.function.bean.AllegatoDocumentoInBean;
import it.eng.document.function.bean.DocumentoXmlOutBean;
import it.eng.document.function.bean.FileInfoBean;
import it.eng.document.function.bean.FilePrimarioBean;
import it.eng.document.function.bean.FileStoreBean;
import it.eng.document.function.bean.Firmatario;
import it.eng.document.function.bean.GenericFile;
import it.eng.document.function.bean.RebuildedFile;
import it.eng.document.function.bean.RecuperaDocumentoInBean;
import it.eng.document.function.bean.RecuperaDocumentoOutBean;
import it.eng.document.function.bean.RegistroEmergenza;
import it.eng.document.function.bean.SoggettoEsternoBean;
import it.eng.document.function.bean.TipoFile;
import it.eng.document.function.bean.VersionaDocumentoInBean;
import it.eng.document.function.bean.VersionaDocumentoOutBean;
import it.eng.job.AurigaJobManager;
import it.eng.job.JobDebugConfig;
import it.eng.job.SpringHelper;
import it.eng.job.util.AurigaFileUtils;
import it.eng.job.util.AurigaLoginUtil;
import it.eng.job.util.StorageImplementation;
import it.eng.services.document.bean.DocumentoRecuperatoBean;
import it.eng.services.document.bean.RecuperaFatturaFileBean;
import it.eng.services.exception.DocumentUtilException;
import it.eng.services.fileop.InfoFileUtilityJob;
import it.eng.services.fileop.bean.DocumentConfiguration;
import it.eng.services.fileop.bean.InfoFileBean;
import it.eng.utility.jobmanager.SpringAppContext;
import it.eng.utility.storageutil.StorageService;
import it.eng.utility.storageutil.exception.StorageException;
import it.eng.xml.XmlListaUtility;
import it.eng.xml.XmlUtilityDeserializer;
import it.eng.xml.XmlUtilitySerializer;

public class DocumentUtil {

	private static final String SEQ_DOWNLOAD_TICKET = "S_DOWNLOAD_TICKET";

	private static final Logger log = Logger.getLogger(DocumentUtil.class);

	public static final String MIMETYPE_TEXT = "text/plain";
	public static final String MIMETYPE_HTML = "text/html";
	public static final String MIMETYPE_PDF = "application/pdf";
	private static final String QUERY_MIMETYPE = "select mimetype from DMT_FORMATI_EL_AMMESSI where regexp_like(ESTENSIONE, :ext||'(;|$)')";

	public static final XmlUtilitySerializer XML_SERIALIZER = new XmlUtilitySerializer();
	public static final XmlUtilityDeserializer XML_DESERIALIZER = new XmlUtilityDeserializer();

	private static final String SOGG_EST_COD_NATURA_REL_CLIENTE = "CL";

	/*
	 * private static final String SQL_ISDOCCARICATO = new StringBuilder() .append("select COUNT(nt.IMPRONTA) l_Cnt ") .append(
	 * "from DMT_DOCUMENTS d, TABLE(d.VERSIONI) nt ") .append("where nt.IMPRONTA=TRIM(:ImprontaFileIn) ") .append(
	 * "and UPPER(nt.ALGORITMO_IMPRONTA)=NVL(UPPER(TRIM(:AlgoritmoImprontaIn)), UPPER(nt.ALGORITMO_IMPRONTA)) ") .append(
	 * "and UPPER(nt.ENCODING_IMPRONTA)=NVL(UPPER(TRIM(:EncodingImprontaIn)), UPPER(nt.ENCODING_IMPRONTA)) ") .append(
	 * "and d.ID_SP_AOO = NVL(:IdSPAOOIn, d.ID_SP_AOO) ") .append("and d.FLG_ANN IS NULL ") .append("and rownum=1").toString();
	 */

	/**
	 * Calcola il numero di pagine di un pdf
	 * 
	 * @param fileDoc
	 * @return {@link Integer}
	 * @throws Exception
	 */
	public static Integer getNroPaginePdf(File fileDoc) throws DocumentUtilException {
		Integer numberOfPages = null;
		PdfReader pdfReader = null;
		try {
			pdfReader = new PdfReader(fileDoc.getCanonicalPath());
			numberOfPages = pdfReader.getNumberOfPages();
			if (log.isDebugEnabled())
				log.debug(String.format("numberOfPages: %s", numberOfPages));
		} catch (Exception e) {
			throw new DocumentUtilException("Errore nel calcolo del numero pagine", e);
		} finally {
			try {
				if (pdfReader != null)
					pdfReader.close();
			} catch (Exception e) {
			}
			;
		}
		return numberOfPages;
	}

	/**
	 * Recupera informazioni sul file, chiamando FileOperation
	 * 
	 * @param fileDoc
	 *            {@link File}
	 * @return {@link InfoFileBean}
	 * @throws BeansException
	 * @throws DocumentUtilException
	 */
	public static InfoFileBean getFileInfoFO(File fileDoc) throws BeansException, DocumentUtilException {
		AurigaJobManager jobManager = SpringHelper.getJobManager();
		int businessCallRetryNum = jobManager.getBusinessCallRetryNum();
		int businessCallRetryInterval = jobManager.getBusinessCallRetryInterval();
		int numRetry = 0;

		InfoFileBean lInfoFileBean = new InfoFileBean();
		InfoFileUtilityJob lFileUtility = new InfoFileUtilityJob();
		numRetry = 0;
		while (numRetry < businessCallRetryNum) {
			try {
				numRetry++;
				lInfoFileBean = lFileUtility.getInfoFromFile(fileDoc);
				break;
			} catch (Exception e) {
				if (numRetry < businessCallRetryNum) {
					log.warn("Tentativo n." + numRetry + " di chiamata a InfoFileUtility.getInfoFromFile fallito.", e);
					log.warn("Nuovo tentativo fra " + (businessCallRetryInterval / 1000) + " secondi", e);
					try {
						Thread.sleep(businessCallRetryInterval);
					} catch (InterruptedException intex) {
					}
				} else {
					log.error("Errore nella chiamata al servizio di info file", e);
					throw new DocumentUtilException(e);
				}
			}
		}
		// lInfoFileBean.setFirmato(true);
		// lInfoFileBean.setFirmaValida(true);
		// lInfoFileBean.setConvertibile(false);
		// lInfoFileBean.setDaScansione(false);

		return lInfoFileBean;
	}

	/**
	 * Recupera informazioni sul file, con metodi locali
	 * 
	 * @param fileDoc
	 *            {@link File}
	 * @return {@link InfoFileBean}
	 * @throws BeansException
	 * @throws DocumentUtilException
	 */
	public static InfoFileBean getFileInfo(File fileDoc) throws BeansException, DocumentUtilException {
		InfoFileBean lInfoFileBean = new InfoFileBean();
		DocumentConfiguration lDocumentConfiguration = (DocumentConfiguration) SpringAppContext.getContext()
				.getBean(AurigaSpringContext.SPRINGBEAN_DOCUMENT_CONFIG);

		try {
			String algoritmo = lDocumentConfiguration.getAlgoritmo().value();
			String encoding = lDocumentConfiguration.getEncoding().value();
			FileInfoBean lFileInfoFileBean = AurigaFileUtils.createFileInfoBean(fileDoc, algoritmo, encoding);
			lInfoFileBean.setAlgoritmo(algoritmo);
			lInfoFileBean.setEncoding(encoding);
			lInfoFileBean.setImpronta(lFileInfoFileBean.getAllegatoRiferimento().getImpronta());
			lInfoFileBean.setMimetype(lFileInfoFileBean.getAllegatoRiferimento().getMimetype());
		} catch (Exception e) {
			throw new DocumentUtilException(e);
		}

		return lInfoFileBean;
	}

	/**
	 * Prepara il bean {@link AllegatoDocumentoInBean}
	 * 
	 * @param nomeFileDoc
	 *            {@link String}
	 * @param fileDoc
	 *            {@link File}
	 * @return {@link AllegatoDocumentoInBean}
	 * @throws DocumentUtilException
	 */
	public static AllegatoDocumentoInBean preparaAllegatoDocumentoInBean(String nomeFileDoc, File fileDoc) throws DocumentUtilException {
		return preparaAllegatoDocumentoInBean(nomeFileDoc, fileDoc, false);
	}
	
	public static AllegatoDocumentoInBean preparaAllegatoDocumentoInBean(String nomeFileDoc, File fileDoc, boolean flgFirmato) throws DocumentUtilException {
		InfoFileBean fileInfo = null;
		if(flgFirmato) {
			try {
				InfoFileUtilityJob infoFileUtility = new InfoFileUtilityJob();
				fileInfo = infoFileUtility.getInfoFirmaFromFile(fileDoc, true, new Date());
			} catch (Exception e) {
				log.error("Errore nella chiamata a fileOp per recuperare le info di firma del file", e);
				throw new DocumentUtilException(e);
			}
		} else {
			fileInfo = DocumentUtil.getFileInfo(fileDoc);
		}
		FileStoreBean fileStoreBean = new FileStoreBean();
		fileStoreBean.setAlgoritmo(fileInfo.getAlgoritmo());
		fileStoreBean.setEncoding(fileInfo.getEncoding());
		fileStoreBean.setDimensione(fileDoc.length());
		fileStoreBean.setImpronta(fileInfo.getImpronta());
		fileStoreBean.setMimetype(fileInfo.getMimetype());
		fileStoreBean.setDisplayFilename(nomeFileDoc);
		// fileStoreBean.setDaScansione(daScansione);
		// fileStoreBean.setDataScansione(dataScansione);
		final String[] firmatari = fileInfo.getFirmatari();
		if (firmatari != null) {
			List<Firmatario> listaFirmatari = new ArrayList<Firmatario>();
			for (int i = 0; i < firmatari.length; i++) {
				final Firmatario firmatario = new Firmatario();
				firmatario.setCommonName(firmatari[i]);
				listaFirmatari.add(firmatario);
			}
			fileStoreBean.setFirmatari(listaFirmatari);
		}
		// fileStoreBean.setFirmato(firmato);
		// fileStoreBean.setIdUserScansione(idUserScansione);

		AllegatoDocumentoInBean allegatoInBean = new AllegatoDocumentoInBean();
		// allegatoInBean.setIdUD(idUd);
		// allegatoInBean.setOggetto(allegato.getDescrizione());
		// allegatoInBean.setTipoDocumento(String.valueOf(allegato.getDocType()));
		allegatoInBean.setFileStoreBean(fileStoreBean);

		return allegatoInBean;
	}

	/**
	 * Prepara il bean {@link FilePrimarioBean}
	 * 
	 * @param nomeFileDoc
	 *            {@link String}
	 * @param fileDoc
	 *            {@link File}
	 * @return {@link FilePrimarioBean}
	 * @throws DocumentUtilException
	 */
	public static FilePrimarioBean preparaFilePrimario(String nomeFileDoc, File fileDoc) throws DocumentUtilException {
		return preparaFilePrimario(nomeFileDoc, fileDoc, null);
	}

	/**
	 * Prepara il bean {@link FilePrimarioBean}
	 * 
	 * @param nomeFileDoc
	 *            {@link String}
	 * @param fileDoc
	 *            {@link File}
	 * @param numPagine
	 *            {@link Integer}
	 * @return
	 * @throws DocumentUtilException
	 */
	public static FilePrimarioBean preparaFilePrimario(String nomeFileDoc, File fileDoc, Integer numPagine) throws DocumentUtilException {

		FilePrimarioBean filePrimarioBean = new FilePrimarioBean();
		filePrimarioBean.setFile(fileDoc);
		filePrimarioBean.setIsNewOrChanged(true);

		InfoFileBean lInfoFileBean = getFileInfo(fileDoc);

		FileInfoBean lFileInfoBean = new FileInfoBean();
		lFileInfoBean.setTipo(TipoFile.PRIMARIO);
		GenericFile lGenericFile = new GenericFile();
		lGenericFile.setMimetype(lInfoFileBean.getMimetype());
		lGenericFile.setDisplayFilename(nomeFileDoc);
		lGenericFile.setImpronta(lInfoFileBean.getImpronta());
		lGenericFile.setAlgoritmo(lInfoFileBean.getAlgoritmo());
		lGenericFile.setEncoding(lInfoFileBean.getEncoding());
		if (numPagine != null)
			lGenericFile.setNroPagineVersioneDoc(numPagine);
		lFileInfoBean.setAllegatoRiferimento(lGenericFile);

		filePrimarioBean.setInfo(lFileInfoBean);

		return filePrimarioBean;
	}

	/**
	 * Crea una nuova istanza di {@link AllegatiBean}
	 * 
	 * @return {@link AllegatiBean}
	 */
	public static AllegatiBean creaAllegatiBean() {
		AllegatiBean lAllegatiBean = new AllegatiBean();

		// List<Boolean> flgNoPubbl = new ArrayList<Boolean>();
		// List<Boolean> flgParteDispositivo = new ArrayList<Boolean>();
		// List<String> idTask = new ArrayList<String>();
		// List<Boolean> flgDaFirmare = new ArrayList<Boolean>();
		// List<String> uriFile = new ArrayList<String>();
		List<BigDecimal> idDocumento = new ArrayList<BigDecimal>();
		List<FileInfoBean> fileInfo = new ArrayList<FileInfoBean>();
		List<String> descrizione = new ArrayList<String>();
		List<Integer> docType = new ArrayList<Integer>();
		List<String> displayFilename = new ArrayList<String>();
		List<File> fileAllegati = new ArrayList<File>();
		List<Boolean> isNull = new ArrayList<Boolean>();
		List<Boolean> isNewOrChanged = new ArrayList<Boolean>();

		// lAllegatiBean.setFlgNoPubbl(flgNoPubbl);
		// lAllegatiBean.setFlgParteDispositivo(flgParteDispositivo);
		// lAllegatiBean.setIdTask(idTask);
		// lAllegatiBean.setFlgDaFirmare(flgDaFirmare);
		// lAllegatiBean.setUriFile(uriFile);
		lAllegatiBean.setIdDocumento(idDocumento);
		lAllegatiBean.setInfo(fileInfo);
		lAllegatiBean.setDescrizione(descrizione);
		lAllegatiBean.setDocType(docType);
		lAllegatiBean.setDisplayFilename(displayFilename);
		lAllegatiBean.setFileAllegati(fileAllegati);
		lAllegatiBean.setIsNull(isNull);
		lAllegatiBean.setIsNewOrChanged(isNewOrChanged);

		return lAllegatiBean;
	}

	public static AllegatoBean convertiInAllegatoBean(AllegatiOutBean allegatiOutBean) throws DocumentUtilException {
		AllegatoBean result = null;
		if (allegatiOutBean != null) {
			if (allegatiOutBean.getUri() != null) {
				StorageService storage = StorageImplementation.getStorage();
				File fileDoc = scaricaDaRepository(storage, allegatiOutBean.getUri());

				Integer nroPaginePdf = null;
				if (MIMETYPE_PDF.equalsIgnoreCase(allegatiOutBean.getMimetype()))
					nroPaginePdf = getNroPaginePdf(fileDoc);

				result = new AllegatoBean(new BigDecimal(allegatiOutBean.getIdDoc()), allegatiOutBean.getDescrizioneOggetto(),
						allegatiOutBean.getDisplayFileName(), fileDoc, nroPaginePdf);
			} else {
				result = new AllegatoBean(new BigDecimal(allegatiOutBean.getIdDoc()), allegatiOutBean.getDescrizioneOggetto());
			}

			if (allegatiOutBean.getIdDocType() != null)
				result.setDocType(Integer.valueOf(allegatiOutBean.getIdDocType()));
		}
		return result;
	}

	/**
	 * Aggiunge un allegato ad un'istanza di {@link AllegatiBean}
	 * 
	 * @param allegatiBean
	 *            {@link AllegatiBean}, se null lo crea
	 * @param nomeFileDoc
	 *            {@link String}
	 * @param fileDoc
	 *            {@link File}
	 * @param numPagine
	 *            {@link Integer}
	 * @throws DocumentUtilException
	 */
	public static void aggiungiAllegato(AllegatiBean allegatiBean, String nomeFileDoc, File fileDoc, Integer numPagine) throws DocumentUtilException {
		AllegatoBean allegatoBean = new AllegatoBean();
		allegatoBean.setDisplayFilename(nomeFileDoc);
		allegatoBean.setFile(fileDoc);
		allegatoBean.setNumPagine(numPagine);

		aggiungiAllegato(allegatiBean, allegatoBean);
	}

	/**
	 * Aggiunge un allegato ad un'istanza di {@link AllegatiBean}
	 * 
	 * @param allegatiBean
	 *            {@link AllegatiBean}, se null lo crea
	 * @param allegatoBean
	 *            {@link AllegatoBean}
	 * @throws DocumentUtilException
	 */
	public static void aggiungiAllegato(AllegatiBean allegatiBean, AllegatoBean allegatoBean) throws DocumentUtilException {
		if (allegatiBean == null)
			allegatiBean = creaAllegatiBean();
		if (allegatoBean != null) {

			FileInfoBean lFileInfoBean = new FileInfoBean();
			lFileInfoBean.setTipo(TipoFile.ALLEGATO);
			lFileInfoBean.setPosizione(allegatiBean.getInfo() != null ? allegatiBean.getInfo().size() : 0);

			if (allegatoBean.getFile() != null) {
				InfoFileBean lInfoFileBean = getFileInfo(allegatoBean.getFile());
				GenericFile lGenericFile = new GenericFile();
				lGenericFile.setMimetype(lInfoFileBean.getMimetype());
				lGenericFile.setDisplayFilename(allegatoBean.getDisplayFilename());
				lGenericFile.setImpronta(lInfoFileBean.getImpronta());
				lGenericFile.setAlgoritmo(lInfoFileBean.getAlgoritmo());
				lGenericFile.setEncoding(lInfoFileBean.getEncoding());
				if (allegatoBean.getNumPagine() != null)
					lGenericFile.setNroPagineVersioneDoc(allegatoBean.getNumPagine());
				lFileInfoBean.setAllegatoRiferimento(lGenericFile);
			}

			allegatiBean.getIdDocumento().add(allegatoBean.getIdDoc());
			allegatiBean.getDescrizione().add(allegatoBean.getDescrizione());
			allegatiBean.getDocType().add(allegatoBean.getDocType());
			allegatiBean.getDisplayFilename().add(allegatoBean.getDisplayFilename());
			allegatiBean.getFileAllegati().add(allegatoBean.getFile());
			allegatiBean.getIsNewOrChanged().add(allegatoBean.isNewOrChanged());
			allegatiBean.getIsNull().add(allegatoBean.isNull());
			allegatiBean.getInfo().add(lFileInfoBean);
		}
	}

	/**
	 * Verifica se un file è già stato caricato
	 * 
	 * @param aurigaLoginUtil
	 *            {@link AurigaLoginUtil}
	 * @param fileDoc
	 *            {@link File}
	 * @return {@link boolean} true se il documento è presente in base dati
	 * @throws DocumentUtilException
	 */
	public static boolean isDocCaricato(AurigaLoginUtil aurigaLoginUtil, File fileDoc) throws DocumentUtilException {
		InfoFileBean lInfoFileBean = getFileInfo(fileDoc);

		DmpkCoreTestpresenzafileBean bean = new DmpkCoreTestpresenzafileBean();
		bean.setIdspaooin(aurigaLoginUtil.getCliente().getId().getIdSpAoo());
		bean.setAlgoritmoimprontain(lInfoFileBean.getAlgoritmo());
		bean.setEncodingimprontain(lInfoFileBean.getEncoding());
		bean.setImprontafilein(lInfoFileBean.getImpronta());
		if (log.isDebugEnabled())
			log.debug("Controllo se il file è già stato caricato\n" + ToStringBuilder.reflectionToString(bean));

		DmpkCoreTestpresenzafile service = new DmpkCoreTestpresenzafile();
		StoreResultBean<DmpkCoreTestpresenzafileBean> result = null;
		AurigaJobManager jobManager = SpringHelper.getJobManager();
		int businessCallRetryNum = jobManager.getBusinessCallRetryNum();
		int businessCallRetryInterval = jobManager.getBusinessCallRetryInterval();
		int numRetry = 0;
		while (numRetry < businessCallRetryNum) {
			try {
				numRetry++;
				result = service.execute(aurigaLoginUtil.getLocale(), aurigaLoginUtil.getSchemaBean(), bean);
				break;
			} catch (Exception e) {
				if (numRetry < businessCallRetryNum) {
					log.warn("Tentativo n." + numRetry + " di chiamata a DmpkCoreTestpresenzafile.execute fallito.", e);
					log.warn("Nuovo tentativo fra " + (businessCallRetryInterval / 1000) + " secondi", e);
					try {
						Thread.sleep(businessCallRetryInterval);
					} catch (InterruptedException intex) {
					}
				} else {
					log.error("Errore nella chiamata al servizio di test presenza file", e);
					throw new DocumentUtilException(e);
				}
			}
		}

		if (result.isInError()) {
			log.error("Il servizio di test presenza file ha restituito l'errore: " + result.getDefaultMessage());
			throw new DocumentUtilException(result.getDefaultMessage());
		}

		DmpkCoreTestpresenzafileBean resultBean = result.getResultBean();
		log.debug("Risultato ricerca file " + fileDoc + " : " + resultBean.getFlgfileesistenteout().intValue());

		// per debug
		JobDebugConfig jobDebugConfig = SpringHelper.getJobDebugConfig();
		if (jobDebugConfig.isDebugAttivo()) {
			if (jobDebugConfig.isSaltaVerificaPresenzaFile())
				return false;
		}

		return (resultBean.getFlgfileesistenteout().intValue() >= 1);
	}

	/**
	 * Crea un bean {@link RegistroEmergenza}
	 * 
	 * @param aurigaLoginUtil
	 *            {@link AurigaLoginUtil}
	 * @param categoriaRegistro
	 *            {@link String}
	 * @param sezionale
	 *            {@link String}
	 * @param dataFattura
	 *            {@link Date}
	 * @param numeroDocumento
	 *            {@link Long}
	 * @return
	 */
	public static RegistroEmergenza ricavaRegistroNumerazione(String categoriaRegistro, String sezionale, Date dataFattura, Long numeroDocumento) {
		return ricavaRegistroNumerazione(categoriaRegistro, sezionale, dataFattura, String.valueOf(numeroDocumento));
	}

	/**
	 * Crea un bean {@link RegistroEmergenza}
	 * 
	 * @param aurigaLoginUtil
	 *            {@link AurigaLoginUtil}
	 * @param categoriaRegistro
	 *            {@link String}
	 * @param sezionale
	 *            {@link String}
	 * @param dataFattura
	 *            {@link Date}
	 * @param numeroDocumento
	 *            {@link String}
	 * @return
	 */
	public static RegistroEmergenza ricavaRegistroNumerazione(String categoriaRegistro, String sezionale, Date dataFattura, String numeroDocumento) {
		RegistroEmergenza regNum = new RegistroEmergenza();
		regNum.setFisso(categoriaRegistro);
		regNum.setRegistro(sezionale);
		if (dataFattura != null)
			regNum.setAnno(String.valueOf(DateUtils.toCalendar(dataFattura).get(Calendar.YEAR)));
		regNum.setDataRegistrazione(dataFattura);
		regNum.setNro(numeroDocumento);
		return regNum;
	}

	/**
	 * Recupera i file di una unità documentale, se presente
	 * 
	 * @param aurigaLoginUtil
	 *            {@link AurigaLoginUtil}
	 * @param regNum
	 *            {@link RegistroEmergenza}
	 * @return {@link RecuperaFatturaFileBean} il bean dell'UD con i riferimenti ai file
	 * @throws DocumentUtilException
	 */
	public static DocumentoRecuperatoBean getUDCaricato(AurigaLoginUtil aurigaLoginUtil, RegistroEmergenza regNum) throws DocumentUtilException {
		BigDecimal idUdTrovato = cercaUDFattura(aurigaLoginUtil, regNum);
		if (idUdTrovato == null)
			return null;

		DocumentoRecuperatoBean recuperaFatturaFileBean = caricaFilesUnitaDoc(aurigaLoginUtil, idUdTrovato);

		return recuperaFatturaFileBean;
	}

	/**
	 * Recupera il file di un documento, se presente
	 * 
	 * @param aurigaLoginUtil
	 *            {@link AurigaLoginUtil}
	 * @param idDoc
	 *            {@link BigDecimal}
	 * @return {@link RecuperaFatturaFileBean} il bean dell'UD con i riferimenti ai file
	 * @throws DocumentUtilException
	 */
	public static DmpkCoreExtractverdocBean getDocCaricato(AurigaLoginUtil aurigaLoginUtil, BigDecimal iddoc) throws DocumentUtilException {
		DmpkCoreExtractverdocBean bean = new DmpkCoreExtractverdocBean();
		bean.setIddocin(iddoc);

		DmpkCoreExtractverdoc service = new DmpkCoreExtractverdoc();
		StoreResultBean<DmpkCoreExtractverdocBean> result = null;
		AurigaJobManager jobManager = SpringHelper.getJobManager();
		int businessCallRetryNum = jobManager.getBusinessCallRetryNum();
		int businessCallRetryInterval = jobManager.getBusinessCallRetryInterval();
		int numRetry = 0;
		while (numRetry < businessCallRetryNum) {
			try {
				numRetry++;
				result = service.execute(aurigaLoginUtil.getLocale(), aurigaLoginUtil.getAurigaLoginBean(), bean);
				break;
			} catch (Exception e) {
				if (numRetry < businessCallRetryNum) {
					log.warn("Tentativo n." + numRetry + " di chiamata a DmpkCoreExtractverdoc.execute fallito.", e);
					log.warn("Nuovo tentativo fra " + (businessCallRetryInterval / 1000) + " secondi", e);
					try {
						Thread.sleep(businessCallRetryInterval);
					} catch (InterruptedException intex) {
					}
				} else {
					log.error("Errore nella chiamata al servizio di recupero documento", e);
					throw new DocumentUtilException(e);
				}
			}
		}

		if (result.getDefaultMessage() != null) {
			log.error("Il servizio di recupero documento ha restituito l'errore: " + result.getDefaultMessage());
			throw new DocumentUtilException(result.getDefaultMessage());
		}

		return result.getResultBean();
	}

	public static SoggettoEsternoBean getClienteDoc(AurigaLoginUtil aurigaLoginUtil, BigDecimal idDoc) throws DocumentUtilException {
		DmpkRepositoryGuiLoadsoggestdocBean bean = new DmpkRepositoryGuiLoadsoggestdocBean();
		bean.setCodidconnectiontokenin(aurigaLoginUtil.getAurigaLoginBean().getToken());
		bean.setNaturareldocvssoggin(SOGG_EST_COD_NATURA_REL_CLIENTE);
		bean.setIddocin(idDoc);

		DmpkRepositoryGuiLoadsoggestdoc service = new DmpkRepositoryGuiLoadsoggestdoc();
		StoreResultBean<DmpkRepositoryGuiLoadsoggestdocBean> result = null;
		AurigaJobManager jobManager = SpringHelper.getJobManager();
		int businessCallRetryNum = jobManager.getBusinessCallRetryNum();
		int businessCallRetryInterval = jobManager.getBusinessCallRetryInterval();
		int numRetry = 0;
		while (numRetry < businessCallRetryNum) {
			try {
				numRetry++;
				result = service.execute(aurigaLoginUtil.getLocale(), aurigaLoginUtil.getAurigaLoginBean(), bean);
				break;
			} catch (Exception e) {
				if (numRetry < businessCallRetryNum) {
					log.warn("Tentativo n." + numRetry + " di chiamata a DmpkRepositoryGuiLoadsoggestdoc.execute fallito.", e);
					log.warn("Nuovo tentativo fra " + (businessCallRetryInterval / 1000) + " secondi", e);
					try {
						Thread.sleep(businessCallRetryInterval);
					} catch (InterruptedException intex) {
					}
				} else {
					log.error("Errore nella chiamata al servizio di recupero cliente del documento", e);
					throw new DocumentUtilException(e);
				}
			}
		}

		if (result.getDefaultMessage() != null) {
			log.error("Il servizio di recupero cliente del documento ha restituito l'errore: " + result.getDefaultMessage());
			throw new DocumentUtilException(result.getDefaultMessage());
		}

		String xmlOut = result.getResultBean().getSoggettiesterniout();
		SoggettoEsternoBean clienteDoc;
		try {
			List<SoggettoEsternoBean> recuperaLista = XmlListaUtility.recuperaListaWithEnum(xmlOut, SoggettoEsternoBean.class);
			clienteDoc = recuperaLista.get(0);
		} catch (Exception e) {
			throw new DocumentUtilException("Errore nel'interpretazione della risposta dal servizio di recupero cliente del documento", e);
		}
		if (log.isDebugEnabled())
			log.debug(String.format("cliente del doc[%s]%n%s", idDoc, ToStringBuilder.reflectionToString(clienteDoc)));

		return clienteDoc;
	}

	/**
	 * 
	 * @param aurigaLoginUtil
	 * @param idUd
	 * @return {@link RecuperaFatturaFileBean}
	 * @throws DocumentUtilException
	 */
	/*
	 * public static RecuperaFatturaFileBean caricaFilesUnitaDoc(AurigaLoginUtil aurigaLoginUtil, BigDecimal idUd) throws DocumentUtilException {
	 * DmpkRepositoryGuiLoadfilefatturaBean bean = new DmpkRepositoryGuiLoadfilefatturaBean(); bean.setIdudin(idUd); //bean.setFlgricevutein(0);
	 * //bean.setFlgfilefatturain(1); bean.setFlgnoscambioallprimarioin(1);
	 * 
	 * DmpkRepositoryGuiLoadfilefattura service = new DmpkRepositoryGuiLoadfilefattura(); StoreResultBean<DmpkRepositoryGuiLoadfilefatturaBean> result = null;
	 * AurigaJobManager jobManager = SpringHelper.getJobManager(); int businessCallRetryNum = jobManager.getBusinessCallRetryNum(); int
	 * businessCallRetryInterval = jobManager.getBusinessCallRetryInterval(); int numRetry = 0; while (numRetry < businessCallRetryNum) { try { numRetry++;
	 * result = service.execute(aurigaLoginUtil.getLocale(), aurigaLoginUtil.getAurigaLoginBean(), bean); break; } catch (Exception e) { if(numRetry <
	 * businessCallRetryNum) { log.warn("Tentativo n." + numRetry + " di chiamata a DmpkRepositoryGuiLoadfilefattura.execute fallito.", e); log.warn(
	 * "Nuovo tentativo fra " + (businessCallRetryInterval / 1000) + " secondi", e); try {Thread.sleep(businessCallRetryInterval);} catch (InterruptedException
	 * intex) {} } else { log.error("Errore nella chiamata al servizio di recupero unità documentale", e); throw new DocumentUtilException(e); } } }
	 * 
	 * if (result.getDefaultMessage() != null) { log.error("Il servizio di recupero unità documentale ha restituito l'errore: " + result.getDefaultMessage());
	 * throw new DocumentUtilException(result.getDefaultMessage()); }
	 * 
	 * String xmlOut = result.getResultBean().getDatiudxmlout(); RecuperaFatturaFileBean recuperaFatturaFileBean; try { recuperaFatturaFileBean =
	 * XML_DESERIALIZER.unbindXml(xmlOut, RecuperaFatturaFileBean.class); } catch (Exception e) { throw new DocumentUtilException(
	 * "Errore nel'interpretazione della risposta dal servizio di recupero unità documentale", e); } recuperaFatturaFileBean.setIdUd(idUd); if
	 * (log.isDebugEnabled()) log.debug(String.format("file dell'UD[%s]%n%s", idUd, recuperaFatturaFileBean)); return recuperaFatturaFileBean; }
	 */

	/**
	 * 
	 * @param aurigaLoginUtil
	 * @param idUd
	 * @return {@link DocumentoXmlOutBean}
	 * @throws DocumentUtilException
	 */
	public static DocumentoRecuperatoBean caricaFilesUnitaDoc(AurigaLoginUtil aurigaLoginUtil, BigDecimal idUd) throws DocumentUtilException {
		RecuperaDocumentoInBean bean = new RecuperaDocumentoInBean();
		bean.setIdUd(idUd);

		RecuperoDocumenti service = new RecuperoDocumenti();
		RecuperaDocumentoOutBean result = null;
		AurigaJobManager jobManager = SpringHelper.getJobManager();
		int businessCallRetryNum = jobManager.getBusinessCallRetryNum();
		int businessCallRetryInterval = jobManager.getBusinessCallRetryInterval();
		int numRetry = 0;
		while (numRetry < businessCallRetryNum) {
			try {
				numRetry++;
				result = service.loaddocumento(aurigaLoginUtil.getLocale(), aurigaLoginUtil.getAurigaLoginBean(), bean);
				break;
			} catch (Exception e) {
				if (numRetry < businessCallRetryNum) {
					log.warn("Tentativo n." + numRetry + " di chiamata a RecuperoDocumenti.loaddocumento fallito.", e);
					log.warn("Nuovo tentativo fra " + (businessCallRetryInterval / 1000) + " secondi", e);
					try {
						Thread.sleep(businessCallRetryInterval);
					} catch (InterruptedException intex) {
					}
				} else {
					log.error("Errore nella chiamata al servizio di recupero unità documentale", e);
					throw new DocumentUtilException(e);
				}
			}
		}

		if (result.getDefaultMessage() != null) {
			log.error("Il servizio di recupero unità documentale ha restituito l'errore: " + result.getDefaultMessage());
			throw new DocumentUtilException(result.getDefaultMessage());
		}

		DocumentoXmlOutBean documento = result.getDocumento();
		DocumentoRecuperatoBean documentoRecuperato = new DocumentoRecuperatoBean();
		documentoRecuperato.setIdUD(idUd);
		try {
			BeanUtilsBean2.getInstance().copyProperties(documentoRecuperato, documento);
		} catch (Exception e) {
			throw new DocumentUtilException("Errore nella conversione del risultato del servizio di recupero unità documentale");
		}
		return documentoRecuperato;
	}

	/**
	 * Ricerca una unità documentale in base al registro numerazioni
	 * 
	 * @param aurigaLoginUtil
	 *            {@link AurigaLoginUtil}
	 * @param regNum
	 *            {@link RegistroEmergenza}
	 * @return {@link BigDecimal} l'id UD a cui è collegato il documento, null altrimenti
	 * @throws DocumentUtilException
	 */
	public static BigDecimal cercaUDFattura(AurigaLoginUtil aurigaLoginUtil, RegistroEmergenza regNum) throws DocumentUtilException {
		DmpkUtilityFindud_jBean bean = new DmpkUtilityFindud_jBean();
		if (aurigaLoginUtil.getCliente() != null) {
			bean.setIdspaooin(aurigaLoginUtil.getCliente().getId().getIdSpAoo());
			bean.setCodapplownerin(aurigaLoginUtil.getCliente().getId().getCiApplicazione());
			bean.setCodistapplownerin(aurigaLoginUtil.getCliente().getId().getCiIstanzaApplicazione());
		} else {
			bean.setIdspaooin(aurigaLoginUtil.getAurigaLoginBean().getSpecializzazioneBean().getIdDominio());
		}
		bean.setCodcategoriaregin(regNum.getFisso());
		bean.setSiglaregin(regNum.getRegistro());
		bean.setAnnoregin(new Integer(regNum.getAnno()));
		bean.setNumregin(new BigDecimal(regNum.getNro()));

		DmpkUtilityFindud_j service = new DmpkUtilityFindud_j();
		StoreResultBean<DmpkUtilityFindud_jBean> result = null;
		AurigaJobManager jobManager = SpringHelper.getJobManager();
		int businessCallRetryNum = jobManager.getBusinessCallRetryNum();
		int businessCallRetryInterval = jobManager.getBusinessCallRetryInterval();
		int numRetry = 0;
		while (numRetry < businessCallRetryNum) {
			try {
				numRetry++;
				result = service.execute(aurigaLoginUtil.getLocale(), aurigaLoginUtil.getSchemaBean(), bean);
				break;
			} catch (Exception e) {
				if (numRetry < businessCallRetryNum) {
					log.warn("Tentativo n." + numRetry + " di chiamata a DmpkUtilityFindud_j.execute fallito.", e);
					log.warn("Nuovo tentativo fra " + (businessCallRetryInterval / 1000) + " secondi", e);
					try {
						Thread.sleep(businessCallRetryInterval);
					} catch (InterruptedException intex) {
					}
				} else {
					log.error("Errore nella chiamata al servizio di ricerca UD documento", e);
					throw new DocumentUtilException(e);
				}
			}
		}

		if (result.isInError()) {
			log.error("Il servizio di ricerca UD documento ha restituito l'errore: " + result.getDefaultMessage());
			throw new DocumentUtilException(result.getDefaultMessage());
		}

		DmpkUtilityFindud_jBean resultBean = result.getResultBean();
		log.debug(String.format("Risultato ricerca documento idSpAoo[%1$s], codAppl[%2$s:%3$s], anno[%4$s], sezionale[%5$s], numdoc[%6$s] => idUd[%7$s]",
				resultBean.getIdspaooin(), resultBean.getCodapplownerin(), resultBean.getCodistapplownerin(), resultBean.getAnnoregin(),
				resultBean.getSiglaregin(), resultBean.getNumregin(), resultBean.getIdudout()));

		return resultBean.getIdudout();
	}

	/**
	 * Ricerca la lista di registri numerazione associati ad una idUd
	 * 
	 * @param aurigaLoginUtil
	 *            {@link AurigaLoginUtil}
	 * @param idUd
	 *            {@link BigDecimal}
	 * @return la lista di {@link RegNum}
	 * @throws DocumentUtilException
	 */
	public static List<RegistroNumerazione> ricercaRegNumUd(AurigaLoginUtil aurigaLoginUtil, BigDecimal idUd) throws DocumentUtilException {
		List<RegistroNumerazione> listaRegNum = new ArrayList<RegistroNumerazione>();
		DmpkCoreGetregnumdateudBean bean = new DmpkCoreGetregnumdateudBean();
		bean.setIdudin(idUd);

		DmpkCoreGetregnumdateud service = new DmpkCoreGetregnumdateud();
		StoreResultBean<DmpkCoreGetregnumdateudBean> result = null;
		AurigaJobManager jobManager = SpringHelper.getJobManager();
		int businessCallRetryNum = jobManager.getBusinessCallRetryNum();
		int businessCallRetryInterval = jobManager.getBusinessCallRetryInterval();
		int numRetry = 0;
		while (numRetry < businessCallRetryNum) {
			try {
				numRetry++;
				result = service.execute(aurigaLoginUtil.getLocale(), aurigaLoginUtil.getSchemaBean(), bean);
				break;
			} catch (Exception e) {
				if (numRetry < businessCallRetryNum) {
					log.warn("Tentativo n." + numRetry + " di chiamata a DmpkCoreGetregnumdateud.execute fallito.", e);
					log.warn("Nuovo tentativo fra " + (businessCallRetryInterval / 1000) + " secondi", e);
					try {
						Thread.sleep(businessCallRetryInterval);
					} catch (InterruptedException intex) {
					}
				} else {
					log.error("Errore nella chiamata al servizio di ricerca RegNum per idUd", e);
					throw new DocumentUtilException(e);
				}
			}
		}

		if (result.isInError()) {
			log.error("Il servizio di ricerca RegNum per idUd ha restituito l'errore: " + result.getDefaultMessage());
			throw new DocumentUtilException(result.getDefaultMessage());
		}

		DmpkCoreGetregnumdateudBean resultBean = result.getResultBean();
		try {
			listaRegNum = XmlListaUtility.recuperaLista(resultBean.getRegnumxmlout(), RegistroNumerazione.class);
		} catch (Exception e) {
			log.error("Errore nel'interpretazione della risposta dal servizio di ricerca RegNum per idUd", e);
			throw new DocumentUtilException(e);
		}

		log.debug(String.format("Lista Registri Numerazione per l'idUd %s: %s", resultBean.getIdudin(), listaRegNum));

		return listaRegNum;
	}

	/**
	 * Ricerca l'idDoc di un allegato in base alla sua posizione all'interno di un'unità documentale
	 * 
	 * @param aurigaLoginUtil
	 *            {@link AurigaLoginUtil}
	 * @param idUd
	 *            {@link BigDecimal}
	 * @param numPosizione
	 *            {@link int}
	 * @return {@link BigDecimal} l'id Doc a cui è collegato l'allegato, null altrimenti
	 * @throws DocumentUtilException
	 */
	public static BigDecimal cercaIdDocAllegato(AurigaLoginUtil aurigaLoginUtil, BigDecimal idUd, int numPosizione) throws DocumentUtilException {
		DmpkUtilityGetiddocprimarioallegatofromudBean bean = new DmpkUtilityGetiddocprimarioallegatofromudBean();
		bean.setIdspaooin(aurigaLoginUtil.getCliente().getId().getIdSpAoo());
		bean.setIdudin(idUd);
		bean.setNrodocvsudin(numPosizione);

		DmpkUtilityGetiddocprimarioallegatofromud service = new DmpkUtilityGetiddocprimarioallegatofromud();
		StoreResultBean<DmpkUtilityGetiddocprimarioallegatofromudBean> result = null;
		AurigaJobManager jobManager = SpringHelper.getJobManager();
		int businessCallRetryNum = jobManager.getBusinessCallRetryNum();
		int businessCallRetryInterval = jobManager.getBusinessCallRetryInterval();
		int numRetry = 0;
		while (numRetry < businessCallRetryNum) {
			try {
				numRetry++;
				result = service.execute(aurigaLoginUtil.getLocale(), aurigaLoginUtil.getSchemaBean(), bean);
				break;
			} catch (Exception e) {
				if (numRetry < businessCallRetryNum) {
					log.warn("Tentativo n." + numRetry + " di chiamata a DmpkUtilityGetiddocprimarioallegatofromud.execute fallito.", e);
					log.warn("Nuovo tentativo fra " + (businessCallRetryInterval / 1000) + " secondi", e);
					try {
						Thread.sleep(businessCallRetryInterval);
					} catch (InterruptedException intex) {
					}
				} else {
					log.error("Errore nella chiamata al servizio di ricerca idDoc allegato", e);
					throw new DocumentUtilException(e);
				}
			}
		}

		if (result.isInError()) {
			log.error("Il servizio di ricerca idDoc allegato ha restituito l'errore: " + result.getDefaultMessage());
			throw new DocumentUtilException(result.getDefaultMessage());
		}

		DmpkUtilityGetiddocprimarioallegatofromudBean resultBean = result.getResultBean();
		log.debug(String.format("Risultato ricerca allegato idSpAoo[%1$s], idUd[%2$s], numPos[%3$s] => idDoc[%4$s]", resultBean.getIdspaooin(),
				resultBean.getIdudin(), resultBean.getNrodocvsudin(), resultBean.getParametro_1()));

		final BigDecimal intIdDoc = resultBean.getParametro_1();
		if (intIdDoc != null && !new BigDecimal(-1).equals(intIdDoc)) {
			return intIdDoc;
		} else {
			return null;
		}
	}

	/**
	 * Inerisci un allegato ad una unità doc
	 * 
	 * @param aurigaLoginUtil
	 *            {@link AurigaLoginUtil}
	 * @param idUd
	 *            {@link BigDecimal}
	 * @param allegato
	 *            {@link AllegatoBean}
	 * @return {@link BigDecimal} l'idDoc del documento allegato
	 * @throws DocumentUtilException
	 */
	public static BigDecimal inserisciAllegato(AurigaLoginUtil aurigaLoginUtil, File fileDoc, AllegatoDocumentoInBean allegato) throws DocumentUtilException {
		StorageService storage = null;
		String uriStoredFile = null;
		if (fileDoc != null) {
			storage = StorageImplementation.getStorage();
			uriStoredFile = salvaInRepository(storage, fileDoc);

			allegato.getFileStoreBean().setUri(uriStoredFile);
		}

		DmpkCoreAdddocBean storeResult = null;
		try {
			storeResult = inserisciDocumento(aurigaLoginUtil, allegato);
		} catch (DocumentUtilException e) {
			if (uriStoredFile != null)
				cancellaFileInRepository(storage, uriStoredFile);
			throw (DocumentUtilException) e;
		}

		log.debug(String.format("idDoc inserito su idUd[%s]: %s%n", storeResult.getIdudout(), storeResult.getIddocout()));

		return storeResult.getIddocout();
	}

	/**
	 * Versiona un file in un documento
	 * 
	 * @param aurigaLoginBean
	 *            {@link AurigaLoginBean}
	 * @param locale
	 * @param idDoc
	 *            {@link BigDecimal}
	 * @param fileDoc
	 *            {@link File}
	 * @return true se l'operazione è andata a buon fine, false altrimenti
	 * @throws DocumentUtilException
	 * 
	 */
	public static boolean versionaDocumento(AurigaLoginBean aurigaLoginBean, Locale locale, BigDecimal idDoc, File fileDoc, boolean isPrimario)
			throws Exception {

		log.debug("Metodo di versionamento file allegato - idDoc: " + idDoc);
		RebuildedFile rebuildedFile = new RebuildedFile();
		rebuildedFile.setIdDocumento(idDoc);
		rebuildedFile.setFile(fileDoc);

		InfoFileBean infoFileBean = getFileInfo(fileDoc);

		FileInfoBean fileInfoBean = new FileInfoBean();
		if (isPrimario) {
			fileInfoBean.setTipo(TipoFile.PRIMARIO);
		} else {
			fileInfoBean.setTipo(TipoFile.ALLEGATO);
		}

		GenericFile genericFile = new GenericFile();
		genericFile.setMimetype(infoFileBean.getMimetype());
		genericFile.setDisplayFilename(fileDoc.getName());
		genericFile.setImpronta(infoFileBean.getImpronta());
		genericFile.setAlgoritmo(infoFileBean.getAlgoritmo());
		genericFile.setEncoding(infoFileBean.getEncoding());
		fileInfoBean.setAllegatoRiferimento(genericFile);

		rebuildedFile.setInfo(fileInfoBean);

		VersionaDocumentoInBean versionaDocumentoInBean = new VersionaDocumentoInBean();
		BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(versionaDocumentoInBean, rebuildedFile);

		GestioneDocumenti gestioneDocumenti = new GestioneDocumenti();
		VersionaDocumentoOutBean result = gestioneDocumenti.versionadocumento(locale, aurigaLoginBean, versionaDocumentoInBean);

		if (StringUtils.isNotBlank(result.getDefaultMessage())) {
			log.error(String.format("Il servizio di versionamento documento (idDoc[%s]) ha restituito l'errore: %s", idDoc, result.getDefaultMessage()));
			throw new DocumentUtilException(result.getDefaultMessage());
		}

		return true;
	}
	
//	/**
//	 * Versiona un file in un documento specificando il numero di pagina
//	 * 
//	 * @param aurigaLoginUtil
//	 *            {@link AurigaLoginUtil}
//	 * @param idDoc
//	 *            {@link BigDecimal}
//	 * @param fileDoc
//	 *            {@link File}
//	 * @return true se l'operazione è andata a buon fine, false altrimenti
//	 * @throws DocumentUtilException
//	 */
//	public static boolean versionaDocumentoWithNroPagina(AurigaLoginUtil aurigaLoginUtil, BigDecimal idDoc, BigDecimal idUd,  File fileDoc, boolean isPrimario, int numPagine) throws Exception {
//
//		log.debug("Metodo di versionamento file allegato - idDoc: " + idDoc);
//		RebuildedFile rebuildedFile = new RebuildedFile();
//		rebuildedFile.setIdDocumento(idDoc);
//		rebuildedFile.setFile(fileDoc);
//		InfoFileBean infoFileBean = getFileInfo(fileDoc);
//
//		FileInfoBean fileInfoBean = new FileInfoBean();
//		if (isPrimario) {
//			fileInfoBean.setTipo(TipoFile.PRIMARIO);
//		} else {
//			fileInfoBean.setTipo(TipoFile.ALLEGATO);
//		}
//
//		GenericFile genericFile = new GenericFile();
//		genericFile.setMimetype(infoFileBean.getMimetype());
//		genericFile.setDisplayFilename(fileDoc.getName());
//		genericFile.setImpronta(infoFileBean.getImpronta());
//		genericFile.setAlgoritmo(infoFileBean.getAlgoritmo());
//		genericFile.setEncoding(infoFileBean.getEncoding());
//		if (numPagine>0) {
//			//Setto il numero di pagine
//			genericFile.setNroPagineVersioneDoc(numPagine);
//		}
//		fileInfoBean.setAllegatoRiferimento(genericFile);
//		rebuildedFile.setInfo(fileInfoBean);
//		//VersionaDocumentoInBean versionaDocumentoInBean = new VersionaDocumentoInBean();
//		CreaModDocumentoInBean modificaDocumento = new CreaModDocumentoInBean();
//		BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(modificaDocumento, rebuildedFile);
//		
//		modificaDocumento.setNroPagineCartaceo(10);
//		
//		GestioneDocumenti gestioneDocumenti = new GestioneDocumenti();
//		FilePrimarioBean filePrimario = new FilePrimarioBean();
//		filePrimario.setFile(fileDoc);
//		
//		CreaModDocumentoOutBean result = gestioneDocumenti.modificadocumento(aurigaLoginUtil.getLocale(), aurigaLoginUtil.getAurigaLoginBean(),idUd, idDoc, modificaDocumento, filePrimario, null);		
//		//CreaModDocumentoOutBean result = gestioneDocumenti.modificadocumento(aurigaLoginUtil.getLocale(), aurigaLoginUtil.getAurigaLoginBean(), idUd, null, modificaDocumento, filePrimario, null);				
//		
////		VersionaDocumentoOutBean result = gestioneDocumenti.versionadocumento(aurigaLoginUtil.getLocale(), aurigaLoginUtil.getAurigaLoginBean(),
////				versionaDocumentoInBean);
//
//		if (StringUtils.isNotBlank(result.getDefaultMessage())) {
//			log.error(String.format("Il servizio di versionamento documento (idDoc[%s]) ha restituito l'errore: %s", idDoc, result.getDefaultMessage()));
//			throw new DocumentUtilException(result.getDefaultMessage());
//		}
//
//		return true;
//	}

	/**
	 * Versiona un file in un documento
	 * 
	 * @param aurigaLoginUtil
	 *            {@link AurigaLoginUtil}
	 * @param idDoc
	 *            {@link BigDecimal}
	 * @param fileDoc
	 *            {@link File}
	 * @return true se l'operazione è andata a buon fine, false altrimenti
	 * @throws DocumentUtilException
	 */
	public static boolean versionaDocumento(AurigaLoginUtil aurigaLoginUtil, BigDecimal idDoc, File fileDoc, boolean isPrimario) throws Exception {

		log.debug("Metodo di versionamento file allegato - idDoc: " + idDoc);
		RebuildedFile rebuildedFile = new RebuildedFile();
		rebuildedFile.setIdDocumento(idDoc);
		rebuildedFile.setFile(fileDoc);
		InfoFileBean infoFileBean = getFileInfo(fileDoc);

		FileInfoBean fileInfoBean = new FileInfoBean();
		if (isPrimario) {
			fileInfoBean.setTipo(TipoFile.PRIMARIO);
		} else {
			fileInfoBean.setTipo(TipoFile.ALLEGATO);
		}

		GenericFile genericFile = new GenericFile();
		genericFile.setMimetype(infoFileBean.getMimetype());
		genericFile.setDisplayFilename(fileDoc.getName());
		genericFile.setImpronta(infoFileBean.getImpronta());
		genericFile.setAlgoritmo(infoFileBean.getAlgoritmo());
		genericFile.setEncoding(infoFileBean.getEncoding());
		fileInfoBean.setAllegatoRiferimento(genericFile);
		rebuildedFile.setInfo(fileInfoBean);
		
		VersionaDocumentoInBean versionaDocumentoInBean = new VersionaDocumentoInBean();
		BeanUtilsBean2.getInstance().getPropertyUtils().copyProperties(versionaDocumentoInBean, rebuildedFile);
		GestioneDocumenti gestioneDocumenti = new GestioneDocumenti();
		VersionaDocumentoOutBean result = gestioneDocumenti.versionadocumento(aurigaLoginUtil.getLocale(), aurigaLoginUtil.getAurigaLoginBean(),
				versionaDocumentoInBean);

		if (StringUtils.isNotBlank(result.getDefaultMessage())) {
			log.error(String.format("Il servizio di versionamento documento (idDoc[%s]) ha restituito l'errore: %s", idDoc, result.getDefaultMessage()));
			throw new DocumentUtilException(result.getDefaultMessage());
		}

		return true;
	}

	/**
	 * Versiona un file in un documento
	 * <p>
	 * Va in errore se <b>{@code doUpdate}</b> è true ma non esiste ancora una versione
	 * 
	 * @param aurigaLoginUtil
	 *            {@link AurigaLoginUtil}
	 * @param doUpdate
	 *            {@link boolean} true se esiste già una versione, false altrimenti
	 * @param idDoc
	 *            {@link BigDecimal}
	 * @param fileDoc
	 *            {@link File}
	 * @param inBean
	 *            {@link AllegatoDocumentoInBean}
	 * @return true se l'operazione è andata a buon fine, false altrimenti
	 * @throws DocumentUtilException
	 */
	public static boolean versionaDocumento(AurigaLoginUtil aurigaLoginUtil, boolean doUpdate, BigDecimal idDoc, File fileDoc, AllegatoDocumentoInBean inBean)
			throws DocumentUtilException {
		StorageService storage = StorageImplementation.getStorage();
		String uriStoredFile = salvaInRepository(storage, fileDoc);

		inBean.getFileStoreBean().setUri(uriStoredFile);

		StoreResultBean<?> result = null;
		if (doUpdate) {
			DmpkCoreUpdverdocBean updverdocBean = new DmpkCoreUpdverdocBean();
			updverdocBean.setCodidconnectiontokenin(aurigaLoginUtil.getAurigaLoginBean().getToken());
			updverdocBean.setIduserlavoroin(null);
			updverdocBean.setIddocin(idDoc);
			try {
				updverdocBean.setAttributixmlin(XML_SERIALIZER.bindXml(inBean));
			} catch (Exception e) {
				throw new DocumentUtilException("Errore nella serializzazione degli attributi", e);
			}

			DmpkCoreUpdverdoc service = new DmpkCoreUpdverdoc();
			AurigaJobManager jobManager = SpringHelper.getJobManager();
			int businessCallRetryNum = jobManager.getBusinessCallRetryNum();
			int businessCallRetryInterval = jobManager.getBusinessCallRetryInterval();
			int numRetry = 0;
			while (numRetry < businessCallRetryNum) {
				try {
					numRetry++;
					result = service.execute(aurigaLoginUtil.getLocale(), aurigaLoginUtil.getAurigaLoginBean(), updverdocBean);
					break;
				} catch (Exception e) {
					if (numRetry < businessCallRetryNum) {
						log.warn("Tentativo n." + numRetry + " di chiamata a DmpkCoreUpdverdoc.execute fallito.", e);
						log.warn("Nuovo tentativo fra " + (businessCallRetryInterval / 1000) + " secondi", e);
						try {
							Thread.sleep(businessCallRetryInterval);
						} catch (InterruptedException intex) {
						}
					} else {
						log.error("Errore nella chiamata al servizio di versionamento (upd) documento", e);
						throw new DocumentUtilException(e);
					}
				}
			}
		} else {
			DmpkCoreAddverdocBean addverdocBean = new DmpkCoreAddverdocBean();
			addverdocBean.setCodidconnectiontokenin(aurigaLoginUtil.getAurigaLoginBean().getToken());
			addverdocBean.setIduserlavoroin(null);
			addverdocBean.setIddocin(idDoc);
			try {
				addverdocBean.setAttributiverxmlin(XML_SERIALIZER.bindXml(inBean));
			} catch (Exception e) {
				throw new DocumentUtilException("Errore nella serializzazione degli attributi", e);
			}

			DmpkCoreAddverdoc service = new DmpkCoreAddverdoc();
			AurigaJobManager jobManager = SpringHelper.getJobManager();
			int businessCallRetryNum = jobManager.getBusinessCallRetryNum();
			int businessCallRetryInterval = jobManager.getBusinessCallRetryInterval();
			int numRetry = 0;
			while (numRetry < businessCallRetryNum) {
				try {
					numRetry++;
					result = service.execute(aurigaLoginUtil.getLocale(), aurigaLoginUtil.getAurigaLoginBean(), addverdocBean);
					break;
				} catch (Exception e) {
					if (numRetry < businessCallRetryNum) {
						log.warn("Tentativo n." + numRetry + " di chiamata a DmpkCoreAddverdoc.execute fallito.", e);
						log.warn("Nuovo tentativo fra " + (businessCallRetryInterval / 1000) + " secondi", e);
						try {
							Thread.sleep(businessCallRetryInterval);
						} catch (InterruptedException intex) {
						}
					} else {
						log.error("Errore nella chiamata al servizio di versionamento (ins) documento", e);
						throw new DocumentUtilException(e);
					}
				}
			}
		}

		if (result.isInError()) {
			log.error(String.format("Il servizio di versionamento documento (%s idDoc[%s]) ha restituito l'errore: %s", doUpdate ? "upd" : "ins", idDoc,
					result.getDefaultMessage()));
			throw new DocumentUtilException(result.getDefaultMessage());
		}

		return true;
	}

	public static File scaricaDaRepository(StorageService storage, final String uriStoredFile) throws DocumentUtilException {
		File fileDoc = null;
		String storageError = null;
		try {
			fileDoc = storage.extractFile(uriStoredFile);
		} catch (Exception e) {
			if (e instanceof FileNotFoundException) {
				storageError = "File fattura non trovato";
			} else if (e instanceof StorageException) {
				storageError = "Errore nelle procedure di storage";
			} else if (e instanceof IOException) {
				storageError = "Errore nella lettura/scrittura del file";
			} else {
				storageError = "Errore generico nell'elaborazione del file";
			}
			throw new DocumentUtilException(storageError, e);
		}
		return fileDoc;
	}

	public static String salvaInRepository(StorageService storage, final File fileDoc) throws DocumentUtilException {
		String uriStoredFile = null;
		String storageError = null;
		try {
			uriStoredFile = storage.store(fileDoc);
		} catch (Exception e) {
			if (e instanceof FileNotFoundException) {
				storageError = "File fattura non trovato";
			} else if (e instanceof StorageException) {
				storageError = "Errore nelle procedure di storage";
			} else if (e instanceof IOException) {
				storageError = "Errore nella lettura/scrittura del file";
			} else {
				storageError = "Errore generico nell'elaborazione del file";
			}
			throw new DocumentUtilException(storageError, e);
		}
		return uriStoredFile;
	}

	public static void cancellaFileInRepository(StorageService storage, String uriStoredFile) {
		try {
			storage.delete(uriStoredFile);
		} catch (Exception e) {
			log.warn("Cancellazione file " + uriStoredFile, e);
		}
	}

	/**
	 * Inserisce un documento
	 * 
	 * @param aurigaLoginUtil
	 *            {@link AurigaLoginUtil}
	 * @param bean
	 *            {@link Serializable}
	 * @return {@link DmpkCoreAdddocBean} il bean con gli estremi del documento inserito
	 * @throws Exception
	 */
	public static DmpkCoreAdddocBean inserisciDocumento(AurigaLoginUtil aurigaLoginUtil, Serializable inBean) throws DocumentUtilException {
		DmpkCoreAdddocBean adddocBean = new DmpkCoreAdddocBean();
		adddocBean.setCodidconnectiontokenin(aurigaLoginUtil.getAurigaLoginBean().getToken());
		adddocBean.setIduserlavoroin(null);
		try {
			adddocBean.setAttributiuddocxmlin(XML_SERIALIZER.bindXml(inBean));
		} catch (Exception e) {
			throw new DocumentUtilException("Errore nella serializzazione degli attributi", e);
		}

		DmpkCoreAdddoc service = new DmpkCoreAdddoc();
		StoreResultBean<DmpkCoreAdddocBean> result = null;
		AurigaJobManager jobManager = SpringHelper.getJobManager();
		int businessCallRetryNum = jobManager.getBusinessCallRetryNum();
		int businessCallRetryInterval = jobManager.getBusinessCallRetryInterval();
		int numRetry = 0;
		while (numRetry < businessCallRetryNum) {
			try {
				numRetry++;
				result = service.execute(aurigaLoginUtil.getLocale(), aurigaLoginUtil.getAurigaLoginBean(), adddocBean);
				break;
			} catch (Exception e) {
				if (numRetry < businessCallRetryNum) {
					log.warn("Tentativo n." + numRetry + " di chiamata a DmpkCoreAdddoc.execute fallito.", e);
					log.warn("Nuovo tentativo fra " + (businessCallRetryInterval / 1000) + " secondi", e);
					try {
						Thread.sleep(businessCallRetryInterval);
					} catch (InterruptedException intex) {
					}
				} else {
					log.error("Errore nella chiamata al servizio di inserimento documento", e);
					throw new DocumentUtilException(e);
				}
			}
		}

		if (result.isInError()) {
			log.error("Il servizio di inserimento documento ha restituito l'errore: " + result.getDefaultMessage());
			throw new DocumentUtilException(result.getDefaultMessage());
		}

		return result.getResultBean();
	}

	/**
	 * Inserisce un documento
	 * 
	 * @param aurigaLoginBean
	 *            {@link AurigaLoginBean}
	 * @param locale
	 * @param bean
	 *            {@link Serializable}
	 * @return {@link DmpkCoreAdddocBean} il bean con gli estremi del documento inserito
	 * @throws Exception
	 */
	public static DmpkCoreAdddocBean inserisciDocumento(AurigaLoginBean aurigaLoginBean, Locale locale, Serializable inBean) throws DocumentUtilException {
		DmpkCoreAdddocBean adddocBean = new DmpkCoreAdddocBean();
		adddocBean.setCodidconnectiontokenin(aurigaLoginBean.getToken());
		adddocBean.setIduserlavoroin(null);
		try {
			adddocBean.setAttributiuddocxmlin(XML_SERIALIZER.bindXml(inBean));
		} catch (Exception e) {
			throw new DocumentUtilException("Errore nella serializzazione degli attributi", e);
		}

		DmpkCoreAdddoc service = new DmpkCoreAdddoc();
		StoreResultBean<DmpkCoreAdddocBean> result = null;
		AurigaJobManager jobManager = SpringHelper.getJobManager();
		int businessCallRetryNum = jobManager.getBusinessCallRetryNum();
		int businessCallRetryInterval = jobManager.getBusinessCallRetryInterval();
		int numRetry = 0;
		while (numRetry < businessCallRetryNum) {
			try {
				numRetry++;
				result = service.execute(locale, aurigaLoginBean, adddocBean);
				break;
			} catch (Exception e) {
				if (numRetry < businessCallRetryNum) {
					log.warn("Tentativo n." + numRetry + " di chiamata a DmpkCoreAdddoc.execute fallito.", e);
					log.warn("Nuovo tentativo fra " + (businessCallRetryInterval / 1000) + " secondi", e);
					try {
						Thread.sleep(businessCallRetryInterval);
					} catch (InterruptedException intex) {
					}
				} else {
					log.error("Errore nella chiamata al servizio di inserimento documento", e);
					throw new DocumentUtilException(e);
				}
			}
		}

		if (result.isInError()) {
			log.error("Il servizio di inserimento documento ha restituito l'errore: " + result.getDefaultMessage());
			throw new DocumentUtilException(result.getDefaultMessage());
		}

		return result.getResultBean();
	}

	/**
	 * Aggiorna un documento
	 * 
	 * @param aurigaLoginUtil
	 *            {@link AurigaLoginUtil}
	 * @param idUd
	 *            {@link BigDecimal}
	 * @param idDoc
	 *            {@link BigDecimal}
	 * @param bean
	 *            {@link Serializable}
	 * @throws Exception
	 */
	public static void aggiornaDocumento(AurigaLoginUtil aurigaLoginUtil, BigDecimal idUd, BigDecimal idDoc, Serializable inBean) throws DocumentUtilException {
		DmpkCoreUpddocudBean lUpddocudInput = new DmpkCoreUpddocudBean();
		lUpddocudInput.setCodidconnectiontokenin(aurigaLoginUtil.getAurigaLoginBean().getToken());
		lUpddocudInput.setIduserlavoroin(null);
		if (idDoc != null) {
			lUpddocudInput.setIduddocin(idDoc);
			lUpddocudInput.setFlgtipotargetin("D");
		} else if (idUd != null) {
			lUpddocudInput.setIduddocin(idUd);
			lUpddocudInput.setFlgtipotargetin("U");
		} else {
			throw new DocumentUtilException("Parametro mancante [idUd / idDoc]");
		}
		try {
			lUpddocudInput.setAttributiuddocxmlin(XML_SERIALIZER.bindXml(inBean));
			if (log.isTraceEnabled())
				log.trace(String.format("Attributi bean da aggiornare: %n%s", lUpddocudInput.getAttributiuddocxmlin()));
		} catch (Exception e) {
			throw new DocumentUtilException("Errore nella serializzazione degli attributi", e);
		}

		if (log.isTraceEnabled())
			log.trace(String.format("aggiornaDocumento idUd,idDoc[%s,%s]: %s", idUd, idDoc, ToStringBuilder.reflectionToString(inBean)));

		DmpkCoreUpddocud service = new DmpkCoreUpddocud();
		StoreResultBean<DmpkCoreUpddocudBean> result = null;
		AurigaJobManager jobManager = SpringHelper.getJobManager();
		int businessCallRetryNum = jobManager.getBusinessCallRetryNum();
		int businessCallRetryInterval = jobManager.getBusinessCallRetryInterval();
		int numRetry = 0;
		while (numRetry < businessCallRetryNum) {
			try {
				numRetry++;
				result = service.execute(aurigaLoginUtil.getLocale(), aurigaLoginUtil.getAurigaLoginBean(), lUpddocudInput);
				break;
			} catch (Exception e) {
				if (numRetry < businessCallRetryNum) {
					log.warn("Tentativo n." + numRetry + " di chiamata a DmpkCoreUpddocud.execute fallito.", e);
					log.warn("Nuovo tentativo fra " + (businessCallRetryInterval / 1000) + " secondi", e);
					try {
						Thread.sleep(businessCallRetryInterval);
					} catch (InterruptedException intex) {
					}
				} else {
					log.error("Errore nella chiamata al servizio di aggiornamento documento", e);
					throw new DocumentUtilException(e);
				}
			}
		}

		if (result.isInError()) {
			log.error("Il servizio di aggiornamento documento ha restituito l'errore: " + result.getDefaultMessage());
			throw new DocumentUtilException(result.getDefaultMessage());
		}
	}

	public static DownloadTicketBean getDownloadTicket(AurigaLoginUtil aurigaLoginUtil, String uriFileDoc, String nomeFileDoc, BigDecimal idUd)
			throws DocumentUtilException {
		try {
			String nrTicket = StringUtils.defaultString(String.valueOf(HibernateUtil.getNextVal(SEQ_DOWNLOAD_TICKET)));

			DownloadTicketBean downloadTicketBean = new DownloadTicketBean();
			downloadTicketBean.setTicketId(nrTicket);
			downloadTicketBean.setUri(uriFileDoc);
			downloadTicketBean.setDisplayFilename(nomeFileDoc);
			downloadTicketBean.setIdUd(idUd);

			DaoDownloadTicket daoDownloadTicket = new DaoDownloadTicket();
			DownloadTicketBean saveResult = null;
			try {
				saveResult = daoDownloadTicket.save(aurigaLoginUtil.getLocale(), aurigaLoginUtil.getAurigaLoginBean(), downloadTicketBean);
			} catch (Exception e) {
				log.error(String.format("Errore nella chiamata al servizio di salvataggio ticket di download: %s", downloadTicketBean), e);
				throw new DocumentUtilException(e);
			}

			return saveResult;
		} catch (Exception e) {
			throw new DocumentUtilException(e);
		}
	}

	/*
	 * public static boolean isDocCaricato(AurigaLoginUtil aurigaLoginUtil, File fileDoc) throws DocumentUtilException { DocumentConfiguration
	 * documentConfiguration = (DocumentConfiguration) SpringAppContext.getContext().getBean(AurigaSpringContext.SPRINGBEAN_DOCUMENT_CONFIG);
	 * 
	 * InfoFileBean lMimeTypeFirmaBean = new InfoFileBean(); InfoFileUtility lFileUtility = new InfoFileUtility(); try { lMimeTypeFirmaBean =
	 * lFileUtility.getInfoFromFile(fileDoc.toURI().toString(), fileDoc.getName(), false, null); } catch (Exception e) { log.error(
	 * "Errore nella chiamata al servizio di info file", e); throw new DocumentUtilException(e); }
	 * 
	 * Session session = null; BigDecimal result = null; try { session = HibernateUtil.begin("isDocCaricato"); SQLQuery query =
	 * session.createSQLQuery(SQL_ISDOCCARICATO); query.setBigDecimal("IdSPAOOIn", aurigaLoginUtil.getCliente().getId().getIdSpAoo());
	 * query.setString("AlgoritmoImprontaIn", documentConfiguration.getAlgoritmo().getValue()); query.setString("EncodingImprontaIn",
	 * documentConfiguration.getEncoding().getValue()); query.setString("ImprontaFileIn", lMimeTypeFirmaBean.getImpronta()); result = (BigDecimal)
	 * query.uniqueResult();
	 * 
	 * } catch (Exception e) { log.error("Errore nella chiamata al servizio di test presenza file", e); throw new DocumentUtilException(e); } finally {
	 * HibernateUtil.closeSession(session); }
	 * 
	 * return (result != null ? result.compareTo(BigDecimal.ZERO) > 0 : false); }
	 */

	public static final class AllegatoBean {

		private BigDecimal idDoc = null;
		private String descrizione = null;
		private Integer docType = null;
		private String displayFilename = null;
		private File file = null;
		private Integer numPagine = null;
		private boolean isNull = false;
		private boolean isNewOrChanged = true;

		public AllegatoBean() {
		}

		/**
		 * solo quel che serve...
		 * 
		 * @param descrizione
		 * @param displayFilename
		 * @param file
		 * @param numPagine
		 */
		public AllegatoBean(String descrizione, String displayFilename, File file, Integer numPagine) {
			this.descrizione = descrizione;
			this.displayFilename = displayFilename;
			this.file = file;
			this.numPagine = numPagine;
		}

		/**
		 * solo quel che serve...
		 * 
		 * @param idDoc
		 * @param descrizione
		 * @param displayFilename
		 * @param file
		 * @param numPagine
		 */
		public AllegatoBean(BigDecimal idDoc, String descrizione, String displayFilename, File file, Integer numPagine) {
			this.idDoc = idDoc;
			this.descrizione = descrizione;
			this.displayFilename = displayFilename;
			this.file = file;
			this.numPagine = numPagine;
		}

		/**
		 * solo quel che serve... e senza file
		 * 
		 * @param idDoc
		 * @param descrizione
		 */
		public AllegatoBean(BigDecimal idDoc, String descrizione) {
			this.idDoc = idDoc;
			this.descrizione = descrizione;
		}

		public BigDecimal getIdDoc() {
			return idDoc;
		}

		public void setIdDoc(BigDecimal idDoc) {
			this.idDoc = idDoc;
		}

		public String getDescrizione() {
			return descrizione;
		}

		public void setDescrizione(String descrizione) {
			this.descrizione = descrizione;
		}

		public Integer getDocType() {
			return docType;
		}

		public void setDocType(Integer docType) {
			this.docType = docType;
		}

		public String getDisplayFilename() {
			return displayFilename;
		}

		public void setDisplayFilename(String displayFilename) {
			this.displayFilename = displayFilename;
		}

		public File getFile() {
			return file;
		}

		public void setFile(File file) {
			this.file = file;
		}

		public Integer getNumPagine() {
			return numPagine;
		}

		public void setNumPagine(Integer numPagine) {
			this.numPagine = numPagine;
		}

		public boolean isNull() {
			return isNull;
		}

		public void setNull(boolean isNull) {
			this.isNull = isNull;
		}

		public boolean isNewOrChanged() {
			return isNewOrChanged;
		}

		public void setNewOrChanged(boolean isNewOrChanged) {
			this.isNewOrChanged = isNewOrChanged;
		}

		@Override
		public String toString() {
			return String.format("AllegatoBean [idDoc=%s, descrizione=%s, docType=%s, displayFilename=%s, file=%s, numPagine=%s, isNull=%s, isNewOrChanged=%s]",
					idDoc, descrizione, docType, displayFilename, file, numPagine, isNull, isNewOrChanged);
		}
	}

	public static boolean equalsMimetype(File fileTemplateCorpo, String mimetype) throws DocumentUtilException {
		String ext = FilenameUtils.getExtension(fileTemplateCorpo.getName());
		Session session = null;
		String result = null;
		try {
			session = HibernateUtil.begin("equalsMimetype");
			SQLQuery sqlQuery = session.createSQLQuery(QUERY_MIMETYPE);
			sqlQuery.setString("ext", ext);
			result = (String) sqlQuery.uniqueResult();
		} catch (Exception e) {
			log.error("Errore in equalsMimetype: ", e);
			throw new DocumentUtilException(e);
		} finally {
			HibernateUtil.closeSession(session);
		}
		return StringUtils.equalsIgnoreCase(mimetype, result);
	}

	@SuppressWarnings("unchecked")
	public static <T> FieldTracker<T> creaBeanConTracciaturaCampi(Class<T> beanClass) throws Exception {
		FieldTracker<T> tracker = new FieldTracker<T>();
		T bean = beanClass.newInstance();
		Advisor advisor = new DefaultPointcutAdvisor(new FieldChangeInterceptor(tracker.getCampiModificati()));
		ProxyFactory proxy = new ProxyFactory();
		proxy.setTarget(bean);
		proxy.addAdvisor(advisor);
		tracker.setTrackedBean((T) proxy.getProxy());
		tracker.setOriginalBean(bean);
		return tracker;
	}

	public static class FieldTracker<T> {

		private T trackedBean;
		private T originalBean;
		private List<String> campiModificati = new ArrayList<String>();

		public T getTrackedBean() {
			return trackedBean;
		}

		public void setTrackedBean(T bean) {
			this.trackedBean = bean;
		}

		public T getOriginalBean() {
			return originalBean;
		}

		public void setOriginalBean(T originalBean) {
			this.originalBean = originalBean;
		}

		public List<String> getCampiModificati() {
			return campiModificati;
		}
	}

	public static class RegistroNumerazione extends RegistroEmergenza {

		private static final long serialVersionUID = 1L;

		@NumeroColonna(numero = "14")
		private Integer flgAnn;

		public Integer getFlgAnn() {
			return flgAnn;
		}

		public void setFlgAnn(Integer flgAnn) {
			this.flgAnn = flgAnn;
		}

		@Override
		public String toString() {
			return String.format(
					"RegistroNumerazione [flgAnn=%s, getFisso()=%s, getRegistro()=%s, getNro()=%s, getDataRegistrazione()=%s, getIdUserReg()=%s, getIdUoReg()=%s, getAnno()=%s]",
					flgAnn, getFisso(), getRegistro(), getNro(), getDataRegistrazione(), getIdUserReg(), getIdUoReg(), getAnno());
		}
	}
}
