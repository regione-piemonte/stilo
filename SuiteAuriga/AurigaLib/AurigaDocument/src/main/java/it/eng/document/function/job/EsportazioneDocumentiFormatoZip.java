/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.dao.DaoBmtJobParameters;
import it.eng.auriga.module.business.dao.DaoBmtJobs;
import it.eng.auriga.module.business.dao.beans.JobBean;
import it.eng.auriga.module.business.dao.beans.JobParameterBean;
import it.eng.bean.ExecutionResultBean;
import it.eng.core.annotation.Operation;
import it.eng.core.annotation.Service;
import it.eng.core.business.TFilterFetch;
import it.eng.core.business.TPagingList;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;
import it.eng.document.configuration.EsportazioneDocumentiFormatoZipBean;
import it.eng.document.function.DocumentExporterManager;
import it.eng.document.function.bean.EsportazioneDocumentiZipResultBean;
import it.eng.document.storage.DocumentStorage;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.DocumentConfiguration;
/**
 * Espone l'api per l'estrazione in uno zip di documenti salvati in Auriga. La
 * richiesta viene effettuata mediante un xls che elenchi i documenti di
 * interesse
 * 
 * @author massimo malvestio
 * @author D.Bragato
 *
 */
@Service(name = "EsportazioneDocumentiFormatoZip")
public class EsportazioneDocumentiFormatoZip {


	protected static Logger log = Logger.getLogger(EsportazioneDocumentiFormatoZip.class);
	
	private static final String JOBSTATUS_IN_ELABORAZIONE = "E";
	private static final String JOBSTATUS_IN_ERRORE = "X";
	private static final String JOBSTATUS_COMPLETATO = "R";
	private static final String JOBPARAM_ID_FOGLIO = "ID_FOGLIO";
	private static final String JOBPARAM_URI_XLS_IMPORTATO = "URI_XLS_IMPORTATO";
	private static final BigDecimal JOBPARAMID_URI_XLS_ESPORTATO = new BigDecimal("150");
	private static final String JOBPARAM_URI_XLS_ESPORTATO = "URI_XLS_ESPORTATO";
	private static final BigDecimal JOBPARAMID_URI_ZIP_ESPORTATO = new BigDecimal("151");
	private static final String JOBPARAM_URI_ZIP_ESPORTATO = "URI_ZIP_ESPORTATO";
	private static final BigDecimal JOBPARAMID_NRO_RIGHE_OK = new BigDecimal("152");
	private static final String JOBPARAM_NRO_RIGHE_OK = "NRO_RIGHE_OK";
	private static final BigDecimal JOBPARAMID_NRO_RIGHE_KO = new BigDecimal("153");
	private static final String JOBPARAM_NRO_RIGHE_KO = "NRO_RIGHE_KO";
	private static final String RESULT_INFO_CESTINO = "CESTINO";

	protected DaoBmtJobParameters daoBmtJobParameters = new DaoBmtJobParameters();
	protected DaoBmtJobs daoBmtJobsClient = new DaoBmtJobs();
	private DocumentConfiguration documentConfiguration;
	private ExecutorService executorService;
	private EsportazioneDocumentiFormatoZipBean config;

	private List<File> cestino = new ArrayList<File>();

	/**
	 * In base al bean fornito, schedula un'esportazione in formato zip dei
	 * documenti specificati in un excel precedentemente caricato.
	 * <ul>
	 * <li>viene creato un record in bmt_jobs per tracciare la richiesta</li>
	 * <li>viene creato un record in dmt_fogli_x_import per il tracciamento del
	 * documento di richiesta, il quale serve anche per salvare le statistiche
	 * relative all'estrazione</li>
	 * </ul>
	 * 
	 * @param aurigaLoginBean
	 * @param bean
	 * @return
	 */
	/*@Operation(name = "scheduleJob")
	public DaoEsportazioneDocumentiZipBean scheduleJob(AurigaLoginBean aurigaLoginBean,
			DaoEsportazioneDocumentiZipBean bean) {

		ExecutionResultBean<JobBean> jobCreation = createBmtJob(aurigaLoginBean, bean);

		if (jobCreation.isSuccessful()) {

			ExecutionResultBean<DmpkBmanagerIufoglioximportBean> foglioCreation = createFoglio(bean, aurigaLoginBean);

			DaoEsportazioneDocumentiZipBean retValue = new DaoEsportazioneDocumentiZipBean();

			retValue.setErrorMessage(foglioCreation.getMessage());

			return retValue;

		} else {

			DaoEsportazioneDocumentiZipBean retValue = new DaoEsportazioneDocumentiZipBean();
			retValue.setErrorMessage(jobCreation.getMessage());
			return retValue;
		}

	}*/

	/**
	 * Crea un record su BMT_JOBS corrispondente alla richiesta di estrazione
	 * dei documenti da auriga
	 * 
	 * @param aurigaLoginBean
	 * 
	 * @param daoEsportazioneDocumentiZipBean
	 * @return
	 */
	/*private ExecutionResultBean<JobBean> createBmtJob(AurigaLoginBean aurigaLoginBean,
			DaoEsportazioneDocumentiZipBean daoEsportazioneDocumentiZipBean) {

		DaoBmtJobs daoBmtJobs = new DaoBmtJobs();

		JobBean bean = new JobBean();
		bean.setTipo("ELAB_XLS_DOC_X_ZIP");
		bean.setIdSpAoo(daoEsportazioneDocumentiZipBean.getIdSpaoo());
		bean.setIdUser(aurigaLoginBean.getIdUser().toPlainString());

		Date referenceDate = new Date();

		bean.setStartTime(referenceDate);
		bean.setScheduleTime(referenceDate);
		bean.setSubmitTime(referenceDate);

		bean.setStatus(JobBeanEnum.INIZIALE.getCode());

		try {

			JobBean createdBean = daoBmtJobs.save(bean);

			log.debug("Il job è stato creato correttamente con id " + createdBean.getIdJob());

			ExecutionResultBean<JobBean> retValue = new ExecutionResultBean<JobBean>();
			retValue.setSuccessful(true);
			retValue.setResult(createdBean);

			return retValue;

		} catch (Exception e) {

			String message = String.format("Durante la creazione del job, si è verificata la seguente eccezione %1$s",
					ExceptionUtils.getFullStackTrace(e));

			log.error(message);

			ExecutionResultBean<JobBean> retValue = new ExecutionResultBean<JobBean>();
			retValue.setSuccessful(false);
			retValue.setMessage(message);

			return retValue;
		}
	}*/

	/**
	 * Crea un recordo in dmt_fogli_x_import dell'xls di richiesta di estrazione
	 * documenti
	 * 
	 * @param bean
	 * @param aurigaLoginBean
	 * @return
	 */
	/*private ExecutionResultBean<DmpkBmanagerIufoglioximportBean> createFoglio(DaoEsportazioneDocumentiZipBean bean,
			AurigaLoginBean aurigaLoginBean) {

		Iufoglioximport documentImport = new Iufoglioximport();

		try {

			DmpkBmanagerIufoglioximportBean importBean = new DmpkBmanagerIufoglioximportBean();
			importBean.setCodidconnectiontokenin(aurigaLoginBean.getToken());

			importBean.setIduserlavoroin(bean.getIdUserLavoro());
			importBean.setStatoin("da_elaborare");
			importBean.setTipocontenutoin(bean.getCompanyId());
			importBean.setUriin(bean.getUri_xls_in());

			StoreResultBean<DmpkBmanagerIufoglioximportBean> result = documentImport.execute(aurigaLoginBean,
					importBean);

			ExecutionResultBean<DmpkBmanagerIufoglioximportBean> retValue = new ExecutionResultBean<DmpkBmanagerIufoglioximportBean>();
			retValue.setSuccessful(!result.isInError());

			if (result.isInError()) {

				String message = String.format(
						"Non è stato possbile creare il record associato al foglio %1$s, il sistema ha fornito come messaggio di errore %2$s",
						bean.getUri_xls_in(), result.getDefaultMessage());

				retValue.setMessage(result.getDefaultMessage());

				log.error(message);

			} else {

				retValue.setResult(result.getResultBean());

			}

			return retValue;

		} catch (Exception e) {

			ExecutionResultBean<DmpkBmanagerIufoglioximportBean> retValue = new ExecutionResultBean<DmpkBmanagerIufoglioximportBean>();

			String errorMessage = e.getMessage() != null ? e.getMessage() : e.getCause().getMessage();

			String baseErrorMessage = "Non è stato possbile creare il record associato al foglio %1$s, il sistema ha fornito come messaggio di errore %2$s";

			String message = String.format(baseErrorMessage, bean.getUri_xls_in(), errorMessage);
			retValue.setMessage(message);
			retValue.setSuccessful(false);

			log.error(String.format(baseErrorMessage, bean.getUri_xls_in(), ExceptionUtils.getFullStackTrace(e)));

			return retValue;
		}
	}*/
	
	/**
	 * Esegue l'export dei documenti indicati nel foglio excel precedentemente caricato in DMT_FOGLI_PER_IMPORT.
	 * Gestisce job di tipo ELAB_XLS_DOC_X_ZIP.
	 * 
	 * @author D.Bragato
	 * @param aurigaLoginBean
	 * @param currentJob
	 * @return
	 */
	@Operation(name = "generaDocumentoJob")
	public EsportazioneDocumentiZipResultBean generaDocumentoJob(AurigaLoginBean aurigaLoginBean, JobBean currentJob) {
		
		EsportazioneDocumentiZipResultBean returnValue = new EsportazioneDocumentiZipResultBean();
		
		init(aurigaLoginBean);
		
		/**
		 * Aggiorno lo stato del job in ELABORAZIONE
		 */
		try {
			
			updateJobStatus(currentJob, JOBSTATUS_IN_ELABORAZIONE);
			
		} catch (Exception e) {
	
			returnValue.setMessage("Durante l'aggiornamento dello stato del job, si è verificata un'eccezione, verificare i log");
			returnValue.setSuccessful(false);
	
			log.error(String.format(
					"Durante l'aggiornamento dello stato del job %1$s, si è verificata la seguente eccezione %2$s",
					currentJob.getIdJob(), ExceptionUtils.getFullStackTrace(e)));
			return returnValue;
		}

		/**
		 * recupero parametri del job
		*/
		ExecutionResultBean<Map<String, JobParameterBean>> parametersResult = extractJobParameters(aurigaLoginBean, currentJob);
		if (!parametersResult.isSuccessful()) {

			updateJobAsInError(currentJob, parametersResult);

			return createReturnValue(parametersResult);
		}
		Map<String, JobParameterBean> parameters = parametersResult.getResult();

		/**
		 * verifico la validità dei parametri in ingresso
		 */
		ExecutionResultBean<?> validateResult = validateJobParameters(parameters, currentJob);

		if (!validateResult.isSuccessful()) {

			updateJobAsInError(currentJob, validateResult);

			return createReturnValue(validateResult);
		}

		try {
			/**
			 * recupero foglio xlsx
			 */
			ExecutionResultBean<File> getExcelResult = getFileFoglio(parameters.get(JOBPARAM_URI_XLS_IMPORTATO).getParameterValue(), aurigaLoginBean.getSpecializzazioneBean().getIdDominio());
	
			if (!getExcelResult.isSuccessful()) {
	
				updateJobAsInError(currentJob, getExcelResult);
	
				return createReturnValue(getExcelResult);
			}
			File excelDaElab = getExcelResult.getResult();
			cestino.add(excelDaElab);
	
			/**
			 * elaboro il foglio excel
			 */
			ExecutionResultBean<File> exportResult = elaboraFoglio(aurigaLoginBean, currentJob, excelDaElab);
			
			if (!exportResult.isSuccessful()) {
				
				updateJobAsInError(currentJob, exportResult);
	
				return createReturnValue(exportResult);
			}
			File excel = exportResult.getResult();
			File exportDir = (File) exportResult.getAdditionalInformation(DocumentExporterManager.PATH_DIR_EXPORT);
			cestino.add(excel);
			cestino.add(exportDir);
	
			/**
			 * comprimo la dir
			 */
			ExecutionResultBean<File> zipResult = comprimiDirectory(currentJob, exportDir);
			
			if (!zipResult.isSuccessful()) {
				
				updateJobAsInError(currentJob, zipResult);
	
				return createReturnValue(zipResult);
			}
			File zip = zipResult.getResult();
			cestino.add(zip);
	
			/**
			 * versiono il file compresso e l'excel di risultato nei parametri di out del job
			 */
			String uriZip = null;
			String uriExcel = null;
			ExecutionResultBean<String> versionaResult = versiona(aurigaLoginBean, currentJob, zip);
			
			if (!versionaResult.isSuccessful()) {
				
				updateJobAsInError(currentJob, versionaResult);
	
				return createReturnValue(versionaResult);
			}
			uriZip = versionaResult.getResult();
	
			versionaResult = versiona(aurigaLoginBean, currentJob, excel);
			
			if (!versionaResult.isSuccessful()) {
				
				updateJobAsInError(currentJob, versionaResult);
	
				return createReturnValue(versionaResult);
			}
			uriExcel = versionaResult.getResult();
	
			/**
			 * inserisco gli uri del file compresso e dell'excel di risultato nei parametri di out del job
			 */
			ExecutionResultBean<Boolean> addParamsResult = aggiornaParametriJob(
					currentJob, uriZip, uriExcel, 
					(BigInteger) exportResult.getAdditionalInformation(DocumentExporterManager.COUNT_OK), 
					(BigInteger) exportResult.getAdditionalInformation(DocumentExporterManager.COUNT_KO));
			
			if (!addParamsResult.isSuccessful()) {
				
				updateJobAsInError(currentJob, addParamsResult);
	
				return createReturnValue(addParamsResult);
			}
	
			/**
			 * aggiorno lo stato del job
			 */
			updateJobAsCompleted(currentJob);
			
			/**
			 * Job completato con successo
			 */
			returnValue.setSuccessful(true);

		} catch (Exception e) {

			returnValue.setMessage("Durante l'aggiornamento dello stato del job, si è verificata un'eccezione, verificare i log");
			returnValue.setSuccessful(false);

			log.error(String.format(
					"Durante l'aggiornamento dello stato del job %1$s, si è verificata la seguente eccezione %2$s",
					currentJob.getIdJob(), ExceptionUtils.getFullStackTrace(e)));
		} finally {
			
			/**
			 * pulisco i temporanei
			 */
			puliziaTemporanei();

		}

		return returnValue;
	}

	/**
	 * Estrae i parametri di input del job
	 * 
	 * @param aurigaLoginBean
	 * @param currentJob
	 * @return
	 */
	private ExecutionResultBean<Map<String, JobParameterBean>> extractJobParameters(AurigaLoginBean aurigaLoginBean,
			JobBean currentJob) {

		ExecutionResultBean<Map<String, JobParameterBean>> result = new ExecutionResultBean<Map<String, JobParameterBean>>();

		log.info(String.format("Estrazione dei parametri di lancio relativi al job %1$s", currentJob.getIdJob()));

		try {
			JobParameterBean filterBean = new JobParameterBean();
			filterBean.setIdJob(currentJob.getIdJob());

			TFilterFetch<JobParameterBean> filter = new TFilterFetch<JobParameterBean>();
			filter.setFilter(filterBean);

			TPagingList<JobParameterBean> params = daoBmtJobParameters.searchWithLogin(aurigaLoginBean, filter);

			log.info("Analisi dei parametri trovati");

			Map<String, JobParameterBean> jobParameters = new LinkedHashMap<String, JobParameterBean>();
			for (JobParameterBean param : params.getData()) {
				if (JOBPARAM_ID_FOGLIO.equals(param.getParameterSubtype())) {
					jobParameters.put(JOBPARAM_ID_FOGLIO, param);
				} else if (JOBPARAM_URI_XLS_IMPORTATO.equals(param.getParameterSubtype())) {
					jobParameters.put(JOBPARAM_URI_XLS_IMPORTATO, param);
				}
			}
			result.setSuccessful(true);

			result.setResult(jobParameters);

		} catch (Exception e) {

			String message = String.format(
					"Durante il recupero dei parametri del job %1$s si è verificata la seguente eccezione",
					currentJob.getIdJob());

			log.error(message, e);
			result.setSuccessful(false);
			result.addAdditionalInformation("EXCEPTION", e);

		}

		return result;
	}

	/**
	 * Valida i parametri del jo b che sono stati salvati, verificando che ci
	 * siano le condizioni per lanciare la generazione del documento
	 * 
	 * @param parameters
	 * @return
	 */
	private ExecutionResultBean<Boolean> validateJobParameters(Map<String, JobParameterBean> parameters,
			JobBean currentJob) {

		ExecutionResultBean<Boolean> retValue = new ExecutionResultBean<Boolean>();
		retValue.setSuccessful(true);

		completed: {

			JobParameterBean jobParameterBean = parameters.get(JOBPARAM_ID_FOGLIO);
			boolean noIdFoglioSpecified = jobParameterBean == null
					|| ((JobParameterBean) jobParameterBean).getParameterValue() == null
					|| ((JobParameterBean) jobParameterBean).getParameterValue().isEmpty();

			if (noIdFoglioSpecified) {

				String message = String.format("Non è stato specificato l'identificativo del foglio Excel da elaborare per il job %1$s",
						currentJob.getIdJob());
				log.error(message);

				retValue.setSuccessful(false);
				retValue.setMessage(message);

				break completed;
			}

			 jobParameterBean = parameters.get(JOBPARAM_URI_XLS_IMPORTATO);
			boolean noUriXlsImpSpecified = jobParameterBean == null
					|| ((JobParameterBean) jobParameterBean).getParameterValue() == null
					|| ((JobParameterBean) jobParameterBean).getParameterValue().isEmpty();

			if (noUriXlsImpSpecified) {

				String message = String.format("Non è stato specificato il percorso del foglio Excel da elaborare per il job %1$s",
						currentJob.getIdJob());
				log.error(message);

				retValue.setSuccessful(false);
				retValue.setMessage(message);

				break completed;
			}
		}

		if (retValue.isSuccessful()) {

			log.info("I parametri del job risultano essere corretti");
		}

		return retValue;
	}
	
	/**
	 * Estrazione file excel da elaborare
	 * 
	 * @param uriFoglio
	 * @param idDominio
	 * @return
	 */
	private ExecutionResultBean<File> getFileFoglio(String uriFoglio, BigDecimal idDominio) {
		ExecutionResultBean<File> result = new ExecutionResultBean<File>();

		log.info(String.format("Recupero file excel %1$s", uriFoglio));
		try {
			File fileInStorage = DocumentStorage.extract(uriFoglio, idDominio);
			File destFile = new File(config.getTempPath(), fileInStorage.getName());
			FileUtils.copyFile(fileInStorage, destFile, true);
			result.setResult(destFile);
			result.setSuccessful(true);
		} catch (Exception e) {
			String message = String.format(
					"Durante il recupero del file %1$s si è verificata la seguente eccezione",
					uriFoglio);

			log.error(message, e);

			result.setSuccessful(false);
			result.setMessage(message);
			result.addAdditionalInformation("EXCEPTION", e);
		}
		return result;
	}

	/**
	 * Elabora il foglio excel di input
	 * 
	 * @param aurigaLoginBean
	 * @param currentJob
	 * @param inputFile
	 * @return
	 */
	private ExecutionResultBean<File> elaboraFoglio(AurigaLoginBean aurigaLoginBean, JobBean currentJob, File inputFile) {
		ExecutionResultBean<File> result = new ExecutionResultBean<File>();

		log.info(String.format("Elaborazione file excel per il job %1$s", currentJob.getIdJob()));

		try {
			DocumentExporterManager dem = new DocumentExporterManager(aurigaLoginBean, currentJob, this.executorService, config.getTempPath());
			
			result = dem.elaboraFoglio(aurigaLoginBean, currentJob, inputFile);
			
		} catch (Exception e) {
			String message = String.format(
					"Durante l'elaborazione del file excel per il job %1$s si è verificata la seguente eccezione",
					currentJob.getIdJob());

			log.error(message, e);

			result.setSuccessful(false);
			result.addAdditionalInformation("EXCEPTION", e);
		}
		
		return result;
	}

	/**
	 * Comprime la directory contenente i file estratti e l'excel di risultato
	 * 
	 * @param currentJob
	 * @param exportDir
	 * @return
	 */
	private ExecutionResultBean<File> comprimiDirectory(JobBean currentJob, File exportDir) {
		ExecutionResultBean<File> result = new ExecutionResultBean<File>();

		log.info(String.format("Compressione file estratti per il job %1$s", currentJob.getIdJob()));

		try {
			File zipFile = new File(exportDir.getParentFile(), exportDir.getName() + ".zip");
			FileOutputStream fos = new FileOutputStream(zipFile);
			result.addAdditionalInformation(RESULT_INFO_CESTINO, zipFile);
			ZipOutputStream zos = new ZipOutputStream(fos);

			for (File file : FileUtils.listFiles(exportDir, FileFilterUtils.fileFileFilter(), null)) {
				addToZip(exportDir, file, zos);
			}
			zos.close();
			fos.close();

			result.setSuccessful(true);
			result.getAdditionalInformations().remove(RESULT_INFO_CESTINO);
			result.setResult(zipFile);
		} catch (Exception e) {
			String message = String.format(
					"Durante la compressione dei file estratti per il job %1$s si è verificata la seguente eccezione",
					currentJob.getIdJob());

			log.error(message, e);

			result.setSuccessful(false);
			result.addAdditionalInformation("EXCEPTION", e);
		}
		
		return result;
	}

	/**
	 * Comprime un file e lo aggiunge all'archivio
	 * 
	 * @param directoryToZip
	 * @param file
	 * @param zos
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private static void addToZip(File directoryToZip, File file, ZipOutputStream zos) throws FileNotFoundException, IOException {

		FileInputStream fis = new FileInputStream(file);
		String zipFilePath = file.getCanonicalPath().substring(directoryToZip.getCanonicalPath().length() + 1,
				file.getCanonicalPath().length());
		ZipEntry zipEntry = new ZipEntry(zipFilePath);
		zos.putNextEntry(zipEntry);

		byte[] bytes = new byte[1024];
		int length;
		while ((length = fis.read(bytes)) >= 0) {
			zos.write(bytes, 0, length);
		}
		zos.closeEntry();
		fis.close();
	}
	
	
	/**
	 * Salva un file nello storage
	 * 
	 * @param currentJob
	 * @param file
	 * @return
	 */
	private ExecutionResultBean<String> versiona(AurigaLoginBean aurigaLoginBean, JobBean currentJob, File file) {
		ExecutionResultBean<String> result = new ExecutionResultBean<String>();

		log.info(String.format("Salvataggio nello storage del file %s per il job %s", file, currentJob.getIdJob()));

		try {
			String uriFile = DocumentStorage.store(file, aurigaLoginBean.getSpecializzazioneBean().getIdDominio());
			
			result.setResult(uriFile);
			result.setSuccessful(true);
		} catch (Exception e) {
			String message = String.format(
					"Durante il salvataggio del file %s per il job %s si è verificata la seguente eccezione",
					file, currentJob.getIdJob());

			log.error(message, e);

			result.setSuccessful(false);
			result.addAdditionalInformation("EXCEPTION", e);
		}
		
		return result;
	}
	
	/**
	 * 
	 * @param currentJob
	 * @param zip
	 * @param excel
	 * @param estrazioniOK
	 * @param estrazioniKO
	 * @return
	 */
	private ExecutionResultBean<Boolean> aggiornaParametriJob(
			JobBean currentJob, String uriZip, String uriExcel, BigInteger estrazioniOK, BigInteger estrazioniKO) {
		
		ExecutionResultBean<Boolean> result = new ExecutionResultBean<Boolean>();

		log.info(String.format("Aggiornamento parametri di output per il job %1$s", currentJob.getIdJob()));

		DaoBmtJobParameters daoBmtJobParameters = new DaoBmtJobParameters();
		JobParameterBean bean = null;
		try {
			// uri excel di risultato salvato nel repository
			bean = new JobParameterBean();
			bean.setIdJob(currentJob.getIdJob());
			bean.setParameterId(JOBPARAMID_URI_XLS_ESPORTATO);
			bean.setParameterDir("OUT");
			bean.setParameterType("VARCHAR2");
			bean.setParameterSubtype(JOBPARAM_URI_XLS_ESPORTATO);
			daoBmtJobParameters.forcedelete(bean);
			
			bean.setParameterValue(uriExcel);
			daoBmtJobParameters.save(bean);
			
			// uri archivio salvato nel repository
			bean = new JobParameterBean();
			bean.setIdJob(currentJob.getIdJob());
			bean.setParameterId(JOBPARAMID_URI_ZIP_ESPORTATO);
			bean.setParameterDir("OUT");
			bean.setParameterType("VARCHAR2");
			bean.setParameterSubtype(JOBPARAM_URI_ZIP_ESPORTATO);
			daoBmtJobParameters.forcedelete(bean);
			
			bean.setParameterValue(uriZip);
			daoBmtJobParameters.save(bean);
			
			// conteggio esportazioni OK
			bean = new JobParameterBean();
			bean.setIdJob(currentJob.getIdJob());
			bean.setParameterId(JOBPARAMID_NRO_RIGHE_OK);
			bean.setParameterDir("OUT");
			bean.setParameterType("INTEGER");
			bean.setParameterSubtype(JOBPARAM_NRO_RIGHE_OK);
			daoBmtJobParameters.forcedelete(bean);
			
			bean.setParameterValue(String.valueOf(estrazioniOK));
			daoBmtJobParameters.save(bean);
			
			// conteggio esportazioni KO
			bean = new JobParameterBean();
			bean.setIdJob(currentJob.getIdJob());
			bean.setParameterId(JOBPARAMID_NRO_RIGHE_KO);
			bean.setParameterDir("OUT");
			bean.setParameterType("INTEGER");
			bean.setParameterSubtype(JOBPARAM_NRO_RIGHE_KO);
			daoBmtJobParameters.forcedelete(bean);
			
			bean.setParameterValue(String.valueOf(estrazioniKO));
			daoBmtJobParameters.save(bean);
			
			result.setSuccessful(true);
			result.setResult(true);
		} catch (Exception e) {
			String message = String.format(
					"Durante l'aggiornamento dei parametri di output %s per il job %s si è verificata la seguente eccezione",
					bean != null ? bean.getParameterSubtype() : "",
					currentJob.getIdJob());

			log.error(message, e);

			result.setSuccessful(false);
			result.addAdditionalInformation("EXCEPTION", e);
		}
		
		return result;
	}

	/**
	 * 
	 * @param cestino
	 */
	private void puliziaTemporanei() {
		for (File file : cestino) {
			try {
				FileUtils.forceDelete(file);
			} catch (Exception e) {
				log.warn(e);
			}
		}
	}


	/**********************************************************************************************/
	

	/**
	 * @param aurigaLoginBean 
	 * @throws BeansException
	 */
	private void init(AurigaLoginBean aurigaLoginBean) throws BeansException {
		ApplicationContext context = SpringAppContext.getContext();

		config = (EsportazioneDocumentiFormatoZipBean) context.getBean("EsportazioneDocumentiFormatoZipBean");

		executorService = new ThreadPoolExecutor(config.getCorePoolSize(), config.getMaximumPoolSize(),
				config.getKeepAliveTime(), TimeUnit.SECONDS,
				new ArrayBlockingQueue<Runnable>(config.getWorkQueueSize()));

		documentConfiguration = (DocumentConfiguration) context.getBean("DocumentConfiguration");
		
		SubjectUtil.subject = new InheritableThreadLocal<SubjectBean>();
		SubjectBean subject = new SubjectBean();
		//subject.setIduser(aurigaLoginBean.getUserid());
		subject.setIdDominio(aurigaLoginBean.getSchema());
		subject.setTokenid(aurigaLoginBean.getToken());
		SubjectUtil.subject.set(subject);

	}

	/**
	 * @param bmtJobs
	 * @param currentJob
	 * @throws Exception
	 */
	protected void updateJobAsCompleted(JobBean currentJob) throws Exception {
		updateJobStatus(currentJob, JOBSTATUS_COMPLETATO);
	}
	
	/**
	 * @param currentJob
	 * @param cause
	 * @throws Exception
	 */
	private void updateJobAsInError(JobBean currentJob, String cause) {
		try {
			updateJobStatus(currentJob, JOBSTATUS_IN_ERRORE, cause);
			
			log.info(String.format("Posto il job %1$s in stato ERROR", currentJob.getIdJob()));
			
		} catch (Exception e) {
			String stackTrace = ExceptionUtils.getFullStackTrace(e);
			log.error(String.format(
					"Durante l'aggiornamento in stato di errore del job %1$, si è verificata la seguente eccezione %2$s",
					String.valueOf(currentJob.getIdJob().longValue()), stackTrace));
		}
	}
	
	/**
	 * 
	 * @param currentJob
	 * @param resultBean
	 */
	private void updateJobAsInError(JobBean currentJob, ExecutionResultBean<?> resultBean) {
		try {
			if (resultBean.getAdditionalInformation("EXCEPTION") != null) {
				Exception ex = (Exception) resultBean.getAdditionalInformation("EXCEPTION");
				updateJobStatus(currentJob, JOBSTATUS_IN_ERRORE, 
						resultBean.getMessage(),
						ExceptionUtils.getMessage(ex),
						ExceptionUtils.getRootCauseMessage(ex));
			} else {
				updateJobStatus(currentJob, JOBSTATUS_IN_ERRORE, resultBean.getMessage());
			}
			
			log.info(String.format("Posto il job %1$s in stato ERROR", currentJob.getIdJob()));
			
		} catch (Exception e) {
			String stackTrace = ExceptionUtils.getFullStackTrace(e);
			log.error(String.format(
					"Durante l'aggiornamento in stato di errore del job %1$, si è verificata la seguente eccezione %2$s",
					String.valueOf(currentJob.getIdJob().longValue()), stackTrace));
		}
	}

	/**
	 * 
	 * @param currentJob
	 * @param status
	 * @param message
	 * @throws Exception
	 */
	private void updateJobStatus(JobBean currentJob, String status, String...message) throws Exception {
		currentJob.setStatus(status);
		currentJob.setEndTime(new Date());
		if (message != null && message.length > 0) currentJob.setMessage(StringUtils.join(message, System.getProperty("line.separator")));
		daoBmtJobsClient.update(currentJob);
	}

	/**
	 * @param result
	 * @return
	 */
	private EsportazioneDocumentiZipResultBean createReturnValue(ExecutionResultBean<?> result) {
		EsportazioneDocumentiZipResultBean returnValue = new EsportazioneDocumentiZipResultBean();
		returnValue.setMessage(result.getMessage());
		returnValue.setSuccessful(result.isSuccessful());
		
		//tracciatura file temporanei da cancellare
		if (result.getAdditionalInformations().containsKey(RESULT_INFO_CESTINO)) {
			cestino.addAll((List<File>) result.getAdditionalInformations().get(RESULT_INFO_CESTINO));
		}
		
		return returnValue;
	}
	
	
}
