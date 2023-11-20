/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.invoke.MethodHandles;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Callable;

import javax.activation.MimeType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;

import it.eng.auriga.database.store.dmpk_bmanager.bean.DmpkBmanagerCaricacontfoglioximportBean;
import it.eng.auriga.database.store.dmpk_bmanager.bean.DmpkBmanagerIufoglioximportBean;
import it.eng.auriga.database.store.dmpk_bmanager.store.impl.CaricacontfoglioximportImpl;
import it.eng.auriga.database.store.dmpk_bmanager.store.impl.IufoglioximportImpl;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
//import it.eng.client.DmpkBmanagerCaricacontfoglioximport;
//import it.eng.client.DmpkBmanagerIufoglioximport;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;
import it.eng.jaxb.variabili.Lista;
import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.jaxb.variabili.Lista.Riga.Colonna;
import it.eng.job.AurigaJobManager;
import it.eng.job.SpringHelper;
import it.eng.job.importdocindex.bean.ConfigurazioniArchiviazione;
import it.eng.job.importdocindex.bean.ConfigurazioniFileIndice;
import it.eng.job.importdocindex.bean.ConfigurazioniScansione;
import it.eng.job.importdocindex.bean.HandlerResultBean;
import it.eng.job.importdocindex.bean.InfoArchiviazione;
import it.eng.job.importdocindex.constants.ErrorInfoEnum;
import it.eng.job.importdocindex.constants.IndexFileTypeEnum;
import it.eng.job.importdocindex.constants.StatoElaborazione;
import it.eng.job.importdocindex.dao.HibernateUtil;
import it.eng.job.importdocindex.exceptions.ImportDocIndexException;
import it.eng.job.importdocindex.manager.FileIndiceManager;
import it.eng.job.importdocindex.manager.Manager;
import it.eng.job.util.AurigaFileUtils;
import it.eng.job.util.SezioneCacheUtil;
import it.eng.storeutil.AnalyzeResult;

public class FileIndiceManagerImpl extends Manager implements FileIndiceManager, Callable<HandlerResultBean<Void>> {

	private static final Integer COLONNA_STATO_ELABORAZIONE = 7; // prima colonna ha indice 0
	private static final String COLONNA_VUOTA = null;
	private static final String ESTENSIONE_FILE_ELABORATO = ".elaborato";
	private static final String STATO_ELABORAZIONE_RIGA_OK = "OK";
	private static final String STATO_ELABORAZIONE_RIGA_KO = "KO";
	private static final String NOME_COLONNA_CONF_JOB = "confJob";
	private static final String NOME_COLONNA_RELATIVE_PATH = "relativePath";

	static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	private final File fileIndice;

	private final File fileLavoro;

	private final AurigaLoginBean loginBean;

	private final Locale locale;

	private final Map<Path, StatoElaborazione> mappaFileReferenziati;

	public FileIndiceManagerImpl(ConfigurazioniScansione configurazioniScansione, File fileIndice, AurigaLoginBean loginBean, Locale locale)
			throws ImportDocIndexException {

		super(configurazioniScansione);

		Manager.verificaFile(fileIndice);

		this.fileIndice = fileIndice;
		// creo file di lavoro per registrare i record elaborati
		this.fileLavoro = creaFileLavoro(this.fileIndice, this.directoryLavoro, this.directoryPrincipale, this.directoryLavoro).toFile();
		this.loginBean = loginBean;
		this.locale = locale;
		this.mappaFileReferenziati = new HashMap<>();

	}

	private void configurazioneSubjectBean() {
		SubjectBean subject = SubjectUtil.subject.get();
		if (subject == null) {
			subject = new SubjectBean();
			SubjectUtil.subject.set(subject);
		}
	}

	/**
	 * Restituisce path del nuovo file di lavoro, partendo dal file in input e accodando .elaborato al nome
	 * 
	 * @param file
	 * @param destinazioneBackup
	 * @param radice
	 * @return
	 * @throws ImportDocIndexException
	 */
	private Path creaFileLavoro(final File file, final File directoryDestinazione, final File directoryRadice, final File directoryLavoro)
			throws ImportDocIndexException {

		Path pathDestinazionelavoro = restituisciDirectoryDestinazioneConAlberatura(file, directoryDestinazione, directoryRadice, directoryLavoro);
		this.creaDirectorySeNonEsiste(pathDestinazionelavoro);
		return pathDestinazionelavoro.resolve(file.getName() + ESTENSIONE_FILE_ELABORATO);
	}

	/**
	 * Operazioni finali sul file indice
	 * 
	 * @param statoElaborazioneFoglio
	 * @return
	 * @throws ImportDocIndexException
	 */

	public HandlerResultBean<Void> operazioniFinaliFileIndice(StatoElaborazione statoElaborazioneFoglio) throws ImportDocIndexException {

		HandlerResultBean<Void> result = new HandlerResultBean<>();
		result.setSuccessful(false);

		logger.info("Operazioni di fine lavorazione sul file indice {}", this.fileIndice.getAbsolutePath());

		try {
			operazioniFinaliFile(this.fileIndice, this.fileLavoro, statoElaborazioneFoglio);
			operazioniFinaliFileReferenziati();
			result.setSuccessful(true);
		} catch (Exception e) {
			logger.error(ErrorInfoEnum.ERRORE_OPERAZIONI_FINALI_FILE.getDescription(), e);
		}

		return result;
	}

	/**
	 * Operazioni finali sui file referenziati dal file indice
	 */

	protected void operazioniFinaliFileReferenziati() {
		for (Map.Entry<Path, StatoElaborazione> entry : mappaFileReferenziati.entrySet()) {
			try {
				operazioniFinaliFile(entry.getKey().toFile(), null, entry.getValue());
			} catch (Exception e) {
				logger.error(ErrorInfoEnum.ERRORE_OPERAZIONI_FINALI_FILE.getDescription(), e);
			}
		}
	}

	/**
	 * Archiviazione del file indice con StorageUtil e calcolo dell'impronta
	 * 
	 * @return
	 * @throws ImportDocIndexException
	 */

	public HandlerResultBean<Void> ottieniInfoFile() throws ImportDocIndexException {
		return this.ottieniInfoFile(this.fileIndice, true);
	}

	/**
	 * Archiviazione del file in input con StorageUtil, specificando se si tratta di un file indice o di un file di contenuto
	 * 
	 * @return
	 * @throws ImportDocIndexException
	 */

	private HandlerResultBean<Void> ottieniInfoFile(File file, Boolean isFileIndice) throws ImportDocIndexException {

		HandlerResultBean<Void> result = new HandlerResultBean<>();
		result.setSuccessful(false);

		try {

			InfoArchiviazione infoArchiviazione = new InfoArchiviazione();

			ConfigurazioniArchiviazione configurazioniArchiviazione = this.configurazioniScansione.getConfigurazioniArchiviazione();

			archiviazioneFileConStorageUtil(file, infoArchiviazione, configurazioniArchiviazione, isFileIndice);

			ottieniImprontaFile(file, infoArchiviazione, configurazioniArchiviazione);

			ottieniMimeType(file, infoArchiviazione, configurazioniArchiviazione, isFileIndice);

			calcoloDimensioneFile(file, infoArchiviazione);

			if (isFileIndice || BooleanUtils.isFalse(configurazioniArchiviazione.getAbilitaArchiviazioneStorageUtil())) {
				// se archivio con StorageUtil non è necessario calcolare il percorso relativo, se è il file indice salvo il nome in tutti i casi
				calcolaPercorsoRelativoFile(file, infoArchiviazione);
			}

			result.addAdditionalInformation(ADDITIONAL_INFORMATION_ARCHIVIAZIONE, infoArchiviazione);

			result.setSuccessful(true);

		} catch (Exception e) {
			logger.error(ErrorInfoEnum.ERRORE_ELABORAZIONE_FILE.getDescription(), e);
			throw new ImportDocIndexException(e, ErrorInfoEnum.ERRORE_ELABORAZIONE_FILE);
		}

		return result;

	}

	/**
	 * Calcola il percorso relativo rispetto alla directory principale, altrimenti salva il percorso assoluto
	 * 
	 * @param file
	 * @param infoArchiviazione
	 */
	protected void calcolaPercorsoRelativoFile(File file, InfoArchiviazione infoArchiviazione) {

		final Path pathDirectoryPrincipale = this.directoryPrincipale.toPath();
		final Path pathDirectoryPadreFile = file.getParentFile().toPath();
		final Path pathDirectoryLavoro = this.directoryLavoro.toPath();

		if (!pathDirectoryPadreFile.equals(pathDirectoryPrincipale) && pathDirectoryPadreFile.startsWith(pathDirectoryPrincipale)) {
			// se il file fa parte della radice allora estraggo l'alberatura
			Path alberatura = pathDirectoryPrincipale.relativize(pathDirectoryPadreFile);
			infoArchiviazione.setPercorsoRelativoRispettoARadice(alberatura.resolve(file.getName()).toString());

		} else if (pathDirectoryPadreFile.startsWith(pathDirectoryLavoro)) {
			// se è la directory di lavoro inserisco il percorso assoluto del file
			infoArchiviazione.setPercorsoRelativoRispettoARadice(file.getAbsolutePath());
		} else {
			// altrimenti imposto il percorso assoluto del file
			infoArchiviazione.setPercorsoRelativoRispettoARadice(file.getName());
		}
	}

	/**
	 * Calcolo della dimensione del file in input, impostando -1 se non calcolata
	 * 
	 * @param file
	 * @param infoArchiviazione
	 */
	private void calcoloDimensioneFile(File file, InfoArchiviazione infoArchiviazione) {

		Long dimensione = -1L;
		try {
			dimensione = FileUtils.sizeOf(file);
			logger.info("Dimensione file {}: {}", file, dimensione);
		} catch (Exception e) {
			logger.warn(ErrorInfoEnum.ERRORE_DIMENSIONE_FILE.getDescription(), e);
		}
		infoArchiviazione.setDimensione(dimensione);
	}

	/**
	 * Recupera il mime type del file
	 * 
	 * @param file
	 * @param infoArchiviazione
	 * @param configurazioniArchiviazione
	 * @throws ImportDocIndexException
	 */
	private void ottieniMimeType(File file, InfoArchiviazione infoArchiviazione, ConfigurazioniArchiviazione configurazioniArchiviazione, Boolean isFileIndice)
			throws ImportDocIndexException {

		try {
			// mime type di default vale solo per i file dei contenuti
			if (BooleanUtils.isFalse(isFileIndice) && StringUtils.isNotBlank((configurazioniArchiviazione.getDefaultMimeTypeFileReferenziati()))) {
				infoArchiviazione.setMimeType(configurazioniArchiviazione.getDefaultMimeTypeFileReferenziati());
				logger.info("Mimetype impostato di default per il file {}: {}", file, infoArchiviazione.getMimeType());
			} else {
				MimeType mimeType = ottieniMimeType(file);
				infoArchiviazione.setMimeType(mimeType.getBaseType());
				logger.info("Mimetype del file {}: {}", file, infoArchiviazione.getMimeType());
			}
		} catch (Exception e) {
			logger.error(ErrorInfoEnum.ERRORE_RECUPERO_MIME_TYPE.getDescription(), e);
			throw new ImportDocIndexException(e, ErrorInfoEnum.ERRORE_RECUPERO_MIME_TYPE);
		}
	}

	/**
	 * 
	 * Calcolo dell'impronta del file
	 * 
	 * @param file
	 * @param infoArchiviazione
	 * @param configurazioniArchiviazione
	 * @throws ImportDocIndexException
	 */
	private void ottieniImprontaFile(File file, InfoArchiviazione infoArchiviazione, ConfigurazioniArchiviazione configurazioniArchiviazione)
			throws ImportDocIndexException {

		try {
			if (BooleanUtils.isTrue(configurazioniArchiviazione.getAbilitaCalcoloImpronta())) {

				infoArchiviazione.setAlgoritmo(configurazioniArchiviazione.getAlgoritmoImpronta());
				infoArchiviazione.setEncoding(configurazioniArchiviazione.getEncodingImpronta());
				infoArchiviazione.setImpronta(AurigaFileUtils.calcolaImpronta(file, configurazioniArchiviazione.getAlgoritmoImpronta(),
						configurazioniArchiviazione.getEncodingImpronta()));
				logger.info("Impronta file {} calcolata con algoritmo {} e codifica {}: {}", file, configurazioniArchiviazione.getAlgoritmoImpronta(),
						configurazioniArchiviazione.getEncodingImpronta(), infoArchiviazione.getImpronta());

			}
		} catch (Exception e) {
			logger.error(ErrorInfoEnum.ERRORE_IMPRONTA_FILE.getDescription(), e);
			throw new ImportDocIndexException(e, ErrorInfoEnum.ERRORE_IMPRONTA_FILE);
		}
	}

	/**
	 * 
	 * @param file
	 * @param infoArchiviazione
	 * @param configurazioniArchiviazione
	 * @throws ImportDocIndexException
	 */

	public void archiviazioneFileConStorageUtil(File file, InfoArchiviazione infoArchiviazione, ConfigurazioniArchiviazione configurazioniArchiviazione,
			Boolean isFileIndice) throws ImportDocIndexException {

		try {
			// file indice va sempre archiviato
			if (isFileIndice || BooleanUtils.isTrue(configurazioniArchiviazione.getAbilitaArchiviazioneStorageUtil())) {
				infoArchiviazione.setUri(configurazioniArchiviazione.getStorageUtilImpl().store(file));
			}
			logger.info("File {} archiviato in {}", file, infoArchiviazione.getUri());
		} catch (Exception e) {
			logger.error(ErrorInfoEnum.ERRORE_ARCHIVIAZIONE_FILE.getDescription(), e);
			throw new ImportDocIndexException(e, ErrorInfoEnum.ERRORE_ARCHIVIAZIONE_FILE);
		}
	}

	/**
	 * Registrazione del file indice tramite store procedure
	 * 
	 * @param infoArchiviazione
	 * @param tipoIndice
	 * @return
	 * @throws ImportDocIndexException
	 */
	private HandlerResultBean<Void> registraFileIndice(InfoArchiviazione infoArchiviazione) throws ImportDocIndexException {

		HandlerResultBean<Void> result = new HandlerResultBean<>();
		result.setSuccessful(false);

		try {

			DmpkBmanagerIufoglioximportBean iuFoglioXImportBean = new DmpkBmanagerIufoglioximportBean();
			popolaBeanRegistrazioneFoglio(infoArchiviazione, iuFoglioXImportBean);

			servizioRegistrazioneIndice(result, iuFoglioXImportBean);

		} catch (Exception e) {
			logger.error(ErrorInfoEnum.ERRORE_ELABORAZIONE_FILE.getDescription(), e);
			throw new ImportDocIndexException(e, ErrorInfoEnum.ERRORE_ELABORAZIONE_FILE);
		}

		return result;

	}

	/**
	 * Invocazione della store per registrazione dell'indice
	 * 
	 * @param result
	 * @param iuFoglioXImportBean
	 * @throws InterruptedException
	 * @throws ImportDocIndexException
	 */
	private void servizioRegistrazioneIndice(HandlerResultBean<Void> result, DmpkBmanagerIufoglioximportBean iuFoglioXImportBean)
			throws InterruptedException, ImportDocIndexException {
		
		Session session = null;
		
		try {
			session = HibernateUtil.openSession("registrazioneIndice");
			
			final IufoglioximportImpl iufoglioximport = new IufoglioximportImpl();
			iufoglioximport.setBean(iuFoglioXImportBean);
			
			// effettuo chiamata alla store
			session.doWork(new Work() {
				@Override
				public void execute(Connection paramConnection) throws SQLException {
					paramConnection.setAutoCommit(false);
					iufoglioximport.execute(paramConnection);
				}
			});
			
			StoreResultBean<DmpkBmanagerIufoglioximportBean> resultStore = new StoreResultBean<DmpkBmanagerIufoglioximportBean>();
			AnalyzeResult.analyze(iuFoglioXImportBean, resultStore);
			resultStore.setResultBean(iuFoglioXImportBean);
			
			if (resultStore != null) {
				if (resultStore.isInError()) {
					logErroreStore(resultStore);
				}
				else {
					logger.info("Id foglio inserito: {}", resultStore.getResultBean().getIdfoglioio());
					result.addAdditionalInformation(ADDITIONAL_INFORMATION_ID_FOGLIO, resultStore.getResultBean().getIdfoglioio());
					result.setSuccessful(true);
				}
			}
			else {
				result.setSuccessful(false);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		finally {
			try {
				HibernateUtil.release(session);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
		
		/*
		DmpkBmanagerIufoglioximport service = new DmpkBmanagerIufoglioximport();
		StoreResultBean<DmpkBmanagerIufoglioximportBean> resultStoreBean = null;
		AurigaJobManager jobManager = SpringHelper.getJobManager();

		int businessCallRetryNum = jobManager.getBusinessCallRetryNum();
		int businessCallRetryInterval = jobManager.getBusinessCallRetryInterval();
		int numRetry = 0;
		while (numRetry < businessCallRetryNum) {
			try {
				numRetry++;
				configurazioneSubjectBean();
				resultStoreBean = service.execute(locale, loginBean, iuFoglioXImportBean);
				break;
			} catch (Exception e) {
				riprovaChiamataStore(businessCallRetryNum, businessCallRetryInterval, numRetry, e);
			}
		}

		if (numRetry > 0 && resultStoreBean != null) {
			if (resultStoreBean.isInError()) {
				logErroreStore(resultStoreBean);
			} else {
				logger.info("Id foglio inserito: {}", resultStoreBean.getResultBean().getIdfoglioio());
				result.addAdditionalInformation(ADDITIONAL_INFORMATION_ID_FOGLIO, resultStoreBean.getResultBean().getIdfoglioio());
				result.setSuccessful(true);
			}
		} else {
			result.setSuccessful(false);
		}
		*/
	}

	/**
	 * Log dell'errore gestito della store
	 * 
	 * @param resultStoreBean
	 * @throws ImportDocIndexException
	 */
	private void logErroreStore(StoreResultBean<?> resultStoreBean) throws ImportDocIndexException {
		logger.error(ErrorInfoEnum.ERRORE_REGISTRAZIONE_FILE.getDescription());
		logger.error("Errore nella chiamata alla store {}: {} - {}", resultStoreBean.getStoreName(), resultStoreBean.getErrorCode(),
				resultStoreBean.getDefaultMessage());

		throw new ImportDocIndexException(ErrorInfoEnum.ERRORE_REGISTRAZIONE_FILE);
	}

	/**
	 * Preparazione bean
	 * 
	 * @param infoArchiviazione
	 * @param tipoIndice
	 * @param iuFoglioXImportBean
	 * @throws ImportDocIndexException
	 */

	private void popolaBeanRegistrazioneFoglio(InfoArchiviazione infoArchiviazione, DmpkBmanagerIufoglioximportBean iuFoglioXImportBean)
			throws ImportDocIndexException {

		// XML con info di elaborazione del foglio
		// relativePath del foglio
		// tutte le informazioni del file di conf.del job con cui viene caricato il foglio

		iuFoglioXImportBean.setCodidconnectiontokenin(loginBean.getToken());
		iuFoglioXImportBean.setStatoin(StatoElaborazione.DA_ELABORARE.toString());
		iuFoglioXImportBean.setTipocontenutoin("FSUPLOAD_" + configurazioniScansione.getId());
		if (StringUtils.isNotBlank(infoArchiviazione.getUri())) {
			iuFoglioXImportBean.setUriin(infoArchiviazione.getUri());
		}
		if (StringUtils.isNotBlank(infoArchiviazione.getImpronta())) {
			iuFoglioXImportBean.setImprontain(infoArchiviazione.getImpronta());
			iuFoglioXImportBean.setAlgoritmoimprontain(infoArchiviazione.getAlgoritmo());
			iuFoglioXImportBean.setEncodingimprontain(infoArchiviazione.getEncoding());
		}

		Lista infoElaborazione = new Lista();
		Riga riga1 = new Riga();
		Colonna prima = new Colonna();
		prima.setNro(new BigInteger("1"));
		prima.setNome(NOME_COLONNA_RELATIVE_PATH);
		prima.setContent(infoArchiviazione.getPercorsoRelativoRispettoARadice());
		riga1.getColonna().add(prima);

		serializzazioneConfigScansione(riga1);

		infoElaborazione.getRiga().add(riga1);
		iuFoglioXImportBean.setInfoelaborazionein(SezioneCacheUtil.listaToString(infoElaborazione));
	}

	/**
	 * Serializzazione delle configurazioni della scansione
	 * 
	 * @param riga1
	 * @throws ImportDocIndexException
	 */

	private void serializzazioneConfigScansione(Riga riga1) throws ImportDocIndexException {
		JAXBContext jaxbContext = null;

		try {

			// creo xml con la configurazione corrente
			Colonna seconda = new Colonna();
			seconda.setNro(new BigInteger("2"));
			seconda.setNome(NOME_COLONNA_CONF_JOB);
			jaxbContext = JAXBContext.newInstance(ConfigurazioniScansione.class);
			final Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			StringWriter stringWriter = new StringWriter();
			jaxbMarshaller.marshal(this.configurazioniScansione, stringWriter);
			seconda.setContent(stringWriter.toString());
			riga1.getColonna().add(seconda);

		} catch (JAXBException e) {
			logger.error(ErrorInfoEnum.ERRORE_SERIALIZZAZIONE_CONFIGURAZIONE.getDescription(), e);
			throw new ImportDocIndexException(e, ErrorInfoEnum.ERRORE_SERIALIZZAZIONE_CONFIGURAZIONE);
		}
	}

	@Override
	public HandlerResultBean<Void> call() throws Exception {

		ThreadContext.put("ROUTINGKEY", "scansione_" + this.configurazioniScansione.getId());
		Thread.currentThread().setName("Elaborazione-Indice-" + this.fileIndice.getAbsolutePath());
		return processaFileIndice();
	}

	/**
	 * Elaborazione iniziale del file indice
	 * 
	 * @return
	 * @throws ImportDocIndexException
	 */

	public HandlerResultBean<Void> processaFileIndice() throws ImportDocIndexException {

		HandlerResultBean<Void> result = new HandlerResultBean<>();
		result.setSuccessful(false);

		logger.info("Elaboro indice {}", this.fileIndice.getAbsolutePath());

		try {
			HandlerResultBean<Void> handlerResultBeanBackup = backupFile(this.fileIndice);
			if (!handlerResultBeanBackup.isSuccessful()) {
				throw new ImportDocIndexException(ErrorInfoEnum.ERRORE_BACKUP_FILE);
			}

			HandlerResultBean<Void> infoArchiviazioneHandlerResultBean = this.ottieniInfoFile();
			InfoArchiviazione infoArchiviazione = null;
			if (infoArchiviazioneHandlerResultBean.isSuccessful()
					|| infoArchiviazioneHandlerResultBean.getAdditionalInformation(ADDITIONAL_INFORMATION_ARCHIVIAZIONE) != null) {
				infoArchiviazione = (InfoArchiviazione) infoArchiviazioneHandlerResultBean.getAdditionalInformation(ADDITIONAL_INFORMATION_ARCHIVIAZIONE);
			} else {
				throw new ImportDocIndexException(ErrorInfoEnum.ERRORE_ARCHIVIAZIONE_FILE);
			}

			IndexFileTypeEnum tipoIndice = IndexFileTypeEnum.fromMimeType(infoArchiviazione.getMimeType());

			HandlerResultBean<Void> handlerResultBeanRegistrazioneIndice = registraFileIndice(infoArchiviazione);
			if (!handlerResultBeanRegistrazioneIndice.isSuccessful()
					&& handlerResultBeanRegistrazioneIndice.getAdditionalInformation(ADDITIONAL_INFORMATION_ID_FOGLIO) == null) {
				throw new ImportDocIndexException(ErrorInfoEnum.ERRORE_REGISTRAZIONE_FILE);
			}

			HandlerResultBean<Void> handlerResultBeanElaborazioneRecordIndice = processaFileIndice(
					handlerResultBeanRegistrazioneIndice.getAdditionalInformation(ADDITIONAL_INFORMATION_ID_FOGLIO).toString(), tipoIndice);
			if (!handlerResultBeanElaborazioneRecordIndice.isSuccessful()) {
				throw new ImportDocIndexException(ErrorInfoEnum.ERRORE_ELABORAZIONE_FILE);
			}

			logger.info("Elaborazione indice terminata {}", this.fileIndice.getAbsolutePath());

			HandlerResultBean<Void> infoArchiviazioneHandlerResultPulizia = operazioniFinaliFileIndice(StatoElaborazione.ELABORATO_SENZA_ERRORI);
			if (!infoArchiviazioneHandlerResultPulizia.isSuccessful()) {
				logger.warn("Errore nelle operazioni di fine lavorazione sul file indice {}", this.fileIndice.getAbsolutePath());
			}

			// comunque l'elaborazione si è conclusa
			result.setSuccessful(true);

		} catch (Exception e) {

			operazioniFinaliFileIndice(StatoElaborazione.ELABORATO_CON_ERRORI);
			logger.error(ErrorInfoEnum.ERRORE_ELABORAZIONE_FILE.getDescription(), e);
			throw new ImportDocIndexException(e, ErrorInfoEnum.ERRORE_ELABORAZIONE_FILE);

		}

		finally {
			logger.info("Terminata elaborazione file indice {}", this.fileIndice.getAbsolutePath());
		}

		return result;
	}

	/**
	 * Elaborazione del file indice
	 * 
	 * @return
	 * @throws ImportDocIndexException
	 */

	public HandlerResultBean<Void> processaFileIndice(final String idFoglio, final IndexFileTypeEnum tipoIndice) throws ImportDocIndexException {

		HandlerResultBean<Void> result = new HandlerResultBean<>();
		result.setSuccessful(false);

		logger.info("Elaborazione foglio avente id: {}", idFoglio);

		try {

			DmpkBmanagerCaricacontfoglioximportBean caricacontfoglioximportBean = new DmpkBmanagerCaricacontfoglioximportBean();
			caricacontfoglioximportBean.setCodidconnectiontokenin(loginBean.getToken());
			caricacontfoglioximportBean.setIdfoglioin(idFoglio);

			final ConfigurazioniFileIndice configurazioniFileIndice = this.getConfigurazioniScansione().getConfigurazioniFileIndice();
			Lista listaContenuto = new Lista();

			if (tipoIndice.equals(IndexFileTypeEnum.CSV)) {

				processaFileIndiceCSV(tipoIndice, caricacontfoglioximportBean, configurazioniFileIndice, listaContenuto);

			} else if (tipoIndice.equals(IndexFileTypeEnum.XLS) || tipoIndice.equals(IndexFileTypeEnum.XLSX)) {

				processaFileIndiceXls(tipoIndice, configurazioniFileIndice, listaContenuto);

			} else {
				throw new ImportDocIndexException(ErrorInfoEnum.VALIDAZIONE_TIPOLOGIA_INDICE);
			}

			servizioCaricaContFoglio(result, caricacontfoglioximportBean);

		} catch (Exception e) {
			logger.error(ErrorInfoEnum.ERRORE_ELABORAZIONE_FILE.getDescription(), e);
			throw new ImportDocIndexException(e, ErrorInfoEnum.ERRORE_ELABORAZIONE_FILE);
		}

		logger.info("Elaborazione foglio avente id terminata: {}", idFoglio);

		return result;

	}

	/**
	 * Elaborazione del file indice di tipo CSV con aggiornamento dello stato di elaborazione delle singole righe in un file di lavoro
	 * 
	 * @param tipoIndice
	 * @param caricacontfoglioximportBean
	 * @param configurazioniFileIndice
	 * @param listaContenuto
	 * @throws ImportDocIndexException
	 */

	protected void processaFileIndiceCSV(final IndexFileTypeEnum tipoIndice, DmpkBmanagerCaricacontfoglioximportBean caricacontfoglioximportBean,
			final ConfigurazioniFileIndice configurazioniFileIndice, Lista listaContenuto) throws ImportDocIndexException {

		CSVParser parser = new CSVParserBuilder().withSeparator(configurazioniFileIndice.getSeparatore()).build();
		try (BufferedReader br = Files.newBufferedReader(Paths.get(this.fileIndice.getAbsolutePath()), StandardCharsets.UTF_8);
				CSVReader reader = new CSVReaderBuilder(br).withCSVParser(parser).build();
				Writer writer = Files.newBufferedWriter(this.fileLavoro.toPath());
				CSVWriter csvWriter = new CSVWriter(writer, configurazioniFileIndice.getSeparatore(), CSVWriter.NO_QUOTE_CHARACTER,
						CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);) {

			String[] valoriRigaCorrente;
			Integer contatoreRigheCsv = 0;
			while ((valoriRigaCorrente = reader.readNext()) != null) {

				// la riga indice non va processata
				if (contatoreRigheCsv++ >= (configurazioniFileIndice.getRigaDatiFileIndice())) {
					processaRigaIndice(csvWriter, null, configurazioniFileIndice, listaContenuto, valoriRigaCorrente, contatoreRigheCsv, tipoIndice);
				} else {
					// aggiunta riga indice nel file elaborato
					aggiungiRigaCSV(csvWriter, valoriRigaCorrente, null);
				}

			}
			caricacontfoglioximportBean.setXmlcontenutiin(SezioneCacheUtil.listaToString(listaContenuto));

		} catch (Exception e) {
			logger.error(ErrorInfoEnum.ERRORE_LETTURA_FILE_CSV.getDescription(), e);
			throw new ImportDocIndexException(e, ErrorInfoEnum.ERRORE_LETTURA_FILE_CSV);
		}
	}

	/**
	 * Elaborazione del file indice di tipo XLS/XLSX con aggiornamento dello stato di elaborazione delle singole righe in un file di lavoro
	 * 
	 * @param tipoIndice
	 * @param configurazioniFileIndice
	 * @param listaContenuto
	 * @throws ImportDocIndexException
	 */

	protected void processaFileIndiceXls(final IndexFileTypeEnum tipoIndice, final ConfigurazioniFileIndice configurazioniFileIndice, Lista listaContenuto)
			throws ImportDocIndexException {

		try (Workbook workbookFileIndice = WorkbookFactory.create(this.fileIndice);
				Workbook workbookFileLavoro = tipoIndice.equals(IndexFileTypeEnum.XLS) ? new HSSFWorkbook() : new XSSFWorkbook();
				FileOutputStream fileOutputStreamLavoro = new FileOutputStream(this.fileLavoro);) {

			CellStyle nuovoStile = workbookFileLavoro.createCellStyle();
			nuovoStile.cloneStyleFrom(workbookFileIndice.getCellStyleAt(1));

			logger.debug("Numero pagine del foglio {}", workbookFileIndice.getNumberOfSheets());
			Iterator<Sheet> iteratoreFogli = workbookFileIndice.sheetIterator();

			// iterazioni sui fogli di lavoro
			while (iteratoreFogli.hasNext()) {

				Sheet foglioIndice = iteratoreFogli.next();
				// creazione di un foglio vuoto
				Sheet nuovoFoglioIndice = workbookFileLavoro.createSheet();

				DataFormatter dataFormatter = new DataFormatter();

				Iterator<Row> rowIterator = foglioIndice.rowIterator();
				List<String> colonne = new ArrayList<>();
				Integer contatoreRighe = 0;

				while (rowIterator.hasNext()) {

					Row rigaFileIndice = rowIterator.next();
					// creazione nuova riga
					Row nuovaRiga = nuovoFoglioIndice.createRow(contatoreRighe);

					for (Integer contatoreColonne = 0; contatoreColonne < rigaFileIndice.getLastCellNum(); contatoreColonne++) {
						copiaCellaXls(dataFormatter, colonne, rigaFileIndice, nuovaRiga, contatoreColonne, nuovoStile);
					}

					String[] valoriRigaCorrente = new String[colonne.size()];
					colonne.toArray(valoriRigaCorrente);

					// righe indice non devono essere processate
					if (contatoreRighe++ >= (configurazioniFileIndice.getRigaDatiFileIndice())) {
						processaRigaIndice(null, nuovaRiga, configurazioniFileIndice, listaContenuto, valoriRigaCorrente, contatoreRighe, tipoIndice);
					}
				}
			}

			// aggiornamento del file di lavoro
			workbookFileLavoro.write(fileOutputStreamLavoro);

		} catch (Exception e) {
			logger.error(ErrorInfoEnum.ERRORE_LETTURA_FILE_XLS.getDescription(), e);
			throw new ImportDocIndexException(e, ErrorInfoEnum.ERRORE_LETTURA_FILE_XLS);
		}
	}

	/**
	 * Copia della cella nel file di lavoro
	 * 
	 * @param dataFormatter
	 * @param colonne
	 * @param rigaFileIndice
	 * @param nuovaRiga
	 * @param contatoreColonne
	 */
	protected void copiaCellaXls(DataFormatter dataFormatter, List<String> colonne, Row rigaFileIndice, Row nuovaRiga, Integer contatoreColonne,
			CellStyle nuovoStile) {

		Cell cellaOriginale = rigaFileIndice.getCell(contatoreColonne, Row.RETURN_NULL_AND_BLANK);
		final Cell nuovaCella = nuovaRiga.createCell(contatoreColonne);
		if (cellaOriginale != null) {
			// formattazione della cella per passaggio alla store procedure
			final String valoreCella = dataFormatter.formatCellValue(cellaOriginale);
			colonne.add(valoreCella);
			// copia della cella nella nuova riga mantenendo tipo e stile
			switch (cellaOriginale.getCellType()) {
			case Cell.CELL_TYPE_BOOLEAN:
				nuovaCella.setCellValue(cellaOriginale.getBooleanCellValue());
				break;
			case Cell.CELL_TYPE_ERROR:
				nuovaCella.setCellValue(cellaOriginale.getErrorCellValue());
				break;
			case Cell.CELL_TYPE_FORMULA:
				nuovaCella.setCellValue(cellaOriginale.getCellFormula());
				break;
			case Cell.CELL_TYPE_NUMERIC:
				nuovaCella.setCellValue(cellaOriginale.getNumericCellValue());
				break;
			case Cell.CELL_TYPE_STRING:
				nuovaCella.setCellValue(cellaOriginale.getRichStringCellValue());
				break;
			default:
				nuovaCella.setCellValue(valoreCella);
			}
			nuovaCella.setCellStyle(nuovoStile);
			if (cellaOriginale.getHyperlink() != null) {
				nuovaCella.setHyperlink(cellaOriginale.getHyperlink());
			}
			if (cellaOriginale.getCellComment() != null) {
				nuovaCella.setCellComment(cellaOriginale.getCellComment());
			}
			nuovaCella.setCellType(cellaOriginale.getCellType());
		} else {
			colonne.add(null);
			nuovaCella.setCellType(Cell.CELL_TYPE_BLANK);
		}
	}

	/**
	 * @param result
	 * @param caricacontfoglioximportBean
	 * @throws InterruptedException
	 * @throws ImportDocIndexException
	 */

	private void servizioCaricaContFoglio(HandlerResultBean<Void> result, DmpkBmanagerCaricacontfoglioximportBean caricacontfoglioximportBean)
			throws InterruptedException, ImportDocIndexException {
		
		Session session = null;
		
		try {
			session = HibernateUtil.openSession("caricaContFoglio");
			
			final CaricacontfoglioximportImpl lCaricacontfoglioximport = new CaricacontfoglioximportImpl();
			lCaricacontfoglioximport.setBean(caricacontfoglioximportBean);
			
			session.doWork(new Work() {
				@Override
				public void execute(Connection paramConnection) throws SQLException {
					paramConnection.setAutoCommit(false);
					lCaricacontfoglioximport.execute(paramConnection);
				}
			});
			
			StoreResultBean<DmpkBmanagerCaricacontfoglioximportBean> resultStore = new StoreResultBean<DmpkBmanagerCaricacontfoglioximportBean>();
			AnalyzeResult.analyze(caricacontfoglioximportBean, resultStore);
			resultStore.setResultBean(caricacontfoglioximportBean);
			
			if (resultStore != null) {
				if (resultStore.isInError()) {
					logErroreStore(resultStore);
				}
				else {
					result.setSuccessful(true);
				}
			}
			else {
				result.setSuccessful(false);
			}
			
		} 	catch (Exception e) {
			logger.error(e.getMessage());
		}
		finally {
			try {
				HibernateUtil.release(session);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
		
		/*
		AurigaJobManager jobManager = SpringHelper.getJobManager();
		DmpkBmanagerCaricacontfoglioximport service = new DmpkBmanagerCaricacontfoglioximport();
		StoreResultBean<DmpkBmanagerCaricacontfoglioximportBean> resultStoreBean = null;
		int businessCallRetryNum = jobManager.getBusinessCallRetryNum();
		int businessCallRetryInterval = jobManager.getBusinessCallRetryInterval();
		int numRetry = 0;
		while (numRetry < businessCallRetryNum) {
			try {
				numRetry++;
				configurazioneSubjectBean();
				resultStoreBean = service.execute(locale, loginBean, caricacontfoglioximportBean);
				break;
			} catch (Exception e) {
				riprovaChiamataStore(businessCallRetryNum, businessCallRetryInterval, numRetry, e);
			}
		}

		if (numRetry > 0 && resultStoreBean != null) {
			if (resultStoreBean.isInError()) {
				logErroreStore(resultStoreBean);
			} else {
				result.setSuccessful(true);
			}
		} else {
			result.setSuccessful(false);
		}
		*/
	}

	/**
	 * elaborazione riga file indice
	 * 
	 * @param configurazioniFileIndice
	 * @param listaContenuto
	 * @param valoriRigaCorrente
	 * @throws ImportDocIndexException
	 */

	private void processaRigaIndice(final CSVWriter writer, Row nuovaRigaXls, final ConfigurazioniFileIndice configurazioniFileIndice, Lista listaContenuto,
			String[] valoriRigaCorrente, Integer contatoreRighe, final IndexFileTypeEnum tipoIndice) throws ImportDocIndexException {

		Riga riga = new Riga();
		Colonna colonnaContenuto = new Colonna();
		Integer indiceColonnaStore = 1;

		logger.debug("Riga numero {}", contatoreRighe);

		Path directoryCorrente = Paths.get(this.fileIndice.getAbsolutePath()).getParent();

		final String uriFile = ottieniUriFileReferenziato(configurazioniFileIndice, valoriRigaCorrente);
		Path pathFileCorrente = null;

		try {

			if (StringUtils.isBlank(uriFile)) {
				throw new ImportDocIndexException(ErrorInfoEnum.ERRORE_LETTURA_COLONNA_URI_FILE);
			}

			pathFileCorrente = directoryCorrente.resolve(uriFile);
			Manager.verificaFile(pathFileCorrente.toFile());

			logger.info("File referenziato {}", pathFileCorrente);

			HandlerResultBean<Void> handlerResultBeanBackup = backupFile(pathFileCorrente.toFile());
			if (!handlerResultBeanBackup.isSuccessful()) {
				throw new ImportDocIndexException(ErrorInfoEnum.ERRORE_BACKUP_FILE);
			}

			HandlerResultBean<Void> infoArchiviazioneHandlerResultBean = ottieniInfoFile(pathFileCorrente.toFile(), false);
			InfoArchiviazione infoArchiviazione = null;
			if (infoArchiviazioneHandlerResultBean.isSuccessful()
					|| infoArchiviazioneHandlerResultBean.getAdditionalInformation(ADDITIONAL_INFORMATION_ARCHIVIAZIONE) != null) {
				infoArchiviazione = (InfoArchiviazione) infoArchiviazioneHandlerResultBean.getAdditionalInformation(ADDITIONAL_INFORMATION_ARCHIVIAZIONE);
			} else {
				throw new ImportDocIndexException(ErrorInfoEnum.ERRORE_ARCHIVIAZIONE_FILE);
			}

			colonnaContenuto.setNro(BigInteger.valueOf(indiceColonnaStore++));
			colonnaContenuto.setContent(infoArchiviazione.getUri());
			riga.getColonna().add(colonnaContenuto);
			colonnaContenuto = new Colonna();
			colonnaContenuto.setNro(BigInteger.valueOf(indiceColonnaStore++));
			colonnaContenuto.setContent(infoArchiviazione.getPercorsoRelativoRispettoARadice());
			riga.getColonna().add(colonnaContenuto);
			// Mimetype del file
			colonnaContenuto = new Colonna();
			colonnaContenuto.setNro(BigInteger.valueOf(indiceColonnaStore++));
			colonnaContenuto.setContent(infoArchiviazione.getMimeType());
			riga.getColonna().add(colonnaContenuto);
			// Impronta del file
			colonnaContenuto = new Colonna();
			colonnaContenuto.setNro(BigInteger.valueOf(indiceColonnaStore++));
			colonnaContenuto.setContent(infoArchiviazione.getImpronta());
			riga.getColonna().add(colonnaContenuto);
			// Algoritmo impronta del file
			colonnaContenuto = new Colonna();
			colonnaContenuto.setNro(BigInteger.valueOf(indiceColonnaStore++));
			colonnaContenuto.setContent(infoArchiviazione.getAlgoritmo());
			riga.getColonna().add(colonnaContenuto);
			// Encoding impronta del file
			colonnaContenuto = new Colonna();
			colonnaContenuto.setNro(BigInteger.valueOf(indiceColonnaStore++));
			colonnaContenuto.setContent(infoArchiviazione.getEncoding());
			riga.getColonna().add(colonnaContenuto);
			// Dimensione del file in bytes
			colonnaContenuto = new Colonna();
			colonnaContenuto.setNro(BigInteger.valueOf(indiceColonnaStore++));
			colonnaContenuto.setContent(infoArchiviazione.getDimensione().toString());
			riga.getColonna().add(colonnaContenuto);

			colonnaContenuto = new Colonna();
			colonnaContenuto.setNro(BigInteger.valueOf(indiceColonnaStore++));
			colonnaContenuto.setContent(STATO_ELABORAZIONE_RIGA_OK);
			riga.getColonna().add(colonnaContenuto);

			// nessun errore rilevato
			colonnaContenuto = new Colonna();
			colonnaContenuto.setNro(BigInteger.valueOf(indiceColonnaStore++));
			colonnaContenuto.setContent(COLONNA_VUOTA);
			riga.getColonna().add(colonnaContenuto);

			// aggiunta del file referenziato in modo da eseguire le operazioni al termine dell'elaborazione
			mappaFileReferenziati.put(pathFileCorrente, StatoElaborazione.ELABORATO_SENZA_ERRORI);

			// aggiornamento del file di lavoro con lo stato dell'elaborazione
			aggiornamentoStatoElaborazioneRiga(writer, nuovaRigaXls, valoriRigaCorrente, tipoIndice, STATO_ELABORAZIONE_RIGA_OK);

		} catch (Exception e) {

			logger.warn("Errore nell'elaborazione della riga {}", contatoreRighe, e);

			// si lasciano vuote le colonne mancanti fino a quelle di stato
			for (Integer indiceColonnaCsv = indiceColonnaStore; indiceColonnaCsv <= COLONNA_STATO_ELABORAZIONE; indiceColonnaCsv++) {
				colonnaContenuto = new Colonna();
				colonnaContenuto.setNro(BigInteger.valueOf(indiceColonnaStore++));
				colonnaContenuto.setContent(COLONNA_VUOTA);
				riga.getColonna().add(colonnaContenuto);
			}

			colonnaContenuto = new Colonna();
			colonnaContenuto.setNro(BigInteger.valueOf(indiceColonnaStore++));
			colonnaContenuto.setContent(STATO_ELABORAZIONE_RIGA_KO);
			riga.getColonna().add(colonnaContenuto);

			colonnaContenuto = new Colonna();
			colonnaContenuto.setNro(BigInteger.valueOf(indiceColonnaStore++));
			colonnaContenuto.setContent(e.getMessage());
			riga.getColonna().add(colonnaContenuto);

			aggiornamentoStatoElaborazioneRiga(writer, nuovaRigaXls, valoriRigaCorrente, tipoIndice, STATO_ELABORAZIONE_RIGA_KO);

			try {
				if (pathFileCorrente != null && StringUtils.isNotBlank(uriFile)) {
					verificaFile(pathFileCorrente.toFile());
					mappaFileReferenziati.put(pathFileCorrente, StatoElaborazione.ELABORATO_CON_ERRORI);
				}
			} catch (ImportDocIndexException e1) {
				logger.error("Il file referenziato {} non esiste o non è accessibile", uriFile, e1);
			}

		}

		// si passsa il contenuto della riga corrente alla store
		for (Integer indiceColonnaRiga = 0; indiceColonnaRiga < valoriRigaCorrente.length; indiceColonnaRiga++) {
			colonnaContenuto = new Colonna();
			colonnaContenuto.setNro(BigInteger.valueOf(indiceColonnaStore++));
			colonnaContenuto.setContent(valoriRigaCorrente[indiceColonnaRiga]);
			riga.getColonna().add(colonnaContenuto);
		}

		listaContenuto.getRiga().add(riga);
	}

	/**
	 * Si recupera dalla riga corrente l'uri del file referenziato
	 * 
	 * @param configurazioniFileIndice
	 * @param valoriRigaCorrente
	 * @return
	 * @throws ImportDocIndexException
	 */

	protected String ottieniUriFileReferenziato(final ConfigurazioniFileIndice configurazioniFileIndice, String[] valoriRigaCorrente)
			throws ImportDocIndexException {

		if (valoriRigaCorrente.length <= 0
				|| (!configurazioniFileIndice.getColonnaURIFileIndice().equals(ConfigurazioniFileIndice.DEFAULT_COLONNA_URI_FILE_INDICE)
						&& configurazioniFileIndice.getColonnaURIFileIndice() > valoriRigaCorrente.length)) {
			throw new ImportDocIndexException(ErrorInfoEnum.ERRORE_LETTURA_COLONNA_URI_FILE);
		}

		// se la colonna è -1 significa che si considera l'ultima colonna della riga
		Integer indiceColonna = configurazioniFileIndice.getColonnaURIFileIndice();
		if (configurazioniFileIndice.getColonnaURIFileIndice().equals(ConfigurazioniFileIndice.DEFAULT_COLONNA_URI_FILE_INDICE)) {
			indiceColonna = valoriRigaCorrente.length - 1;
		}

		return valoriRigaCorrente[indiceColonna];
	}

	/**
	 * Aggiornamento nel file indice di lavoro dello stato di elaborazione della riga corrente
	 * 
	 * @param writer
	 * @param nuovaRigaXls
	 * @param valoriRigaCorrente
	 * @param tipoIndice
	 */
	protected void aggiornamentoStatoElaborazioneRiga(final CSVWriter writer, Row nuovaRigaXls, String[] valoriRigaCorrente, final IndexFileTypeEnum tipoIndice,
			final String valore) {

		try {
			if (tipoIndice.equals(IndexFileTypeEnum.CSV)) {
				// nuova colonna nel file csv con lo stato di elaborazione della riga
				aggiungiRigaCSV(writer, valoriRigaCorrente, valore);
			} else if (tipoIndice.equals(IndexFileTypeEnum.XLS) || tipoIndice.equals(IndexFileTypeEnum.XLSX)) {
				// nuova colonna nel file xls/xlsx con lo stato di elaborazione della riga
				nuovaRigaXls.createCell(nuovaRigaXls.getLastCellNum()).setCellValue(valore);
			}
		} catch (Exception e) {
			logger.warn(ErrorInfoEnum.ERRORE_AGGIORNAMENTO_ELABORAZIONE_RIGA_INDICE.getDescription(), e);
		}

	}

	/**
	 * Aggiunta di una riga tramite il writer. Inoltre si aggiunge la colonna al termine della riga, se valorizzata
	 * 
	 * @param writer
	 * @param valoriRigaCorrente
	 */
	protected void aggiungiRigaCSV(final CSVWriter writer, String[] valoriRigaCorrente, Object contenuto) {
		ArrayList<String> list = new ArrayList<>(Arrays.asList(valoriRigaCorrente));
		if (contenuto != null) {
			list.add(contenuto.toString());
		}
		writer.writeNext(list.toArray(new String[0]));
	}

	/**
	 * Effettua una nuova chiamata alla store in caso di errore fin quando non si raggiunge il numero massimo di tentativi
	 * 
	 * @param businessCallRetryNum
	 * @param businessCallRetryInterval
	 * @param numRetry
	 * @param e
	 * @throws InterruptedException
	 * @throws ImportDocIndexException
	 */

	private void riprovaChiamataStore(int businessCallRetryNum, int businessCallRetryInterval, int numRetry, Exception e)
			throws InterruptedException, ImportDocIndexException {
		if (numRetry < businessCallRetryNum) {
			logger.warn("Tentativo numero {} di chiamata a DmpkBmanagerIufoglioximport.execute fallito", numRetry, e);
			logger.warn("Nuovo tentativo fra {} secondi", (businessCallRetryInterval / 1000), e);
			Thread.sleep(businessCallRetryInterval);
		} else {
			logger.error(ErrorInfoEnum.ERRORE_REGISTRAZIONE_FILE.getDescription(), e);
			throw new ImportDocIndexException(e, ErrorInfoEnum.ERRORE_REGISTRAZIONE_FILE);
		}
	}

}
