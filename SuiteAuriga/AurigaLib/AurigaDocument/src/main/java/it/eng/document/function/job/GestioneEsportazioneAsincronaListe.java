/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileDeleteStrategy;
import org.apache.commons.io.FileUtils;
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
import it.eng.core.business.TOrderBy;
import it.eng.core.business.TOrderBy.OrderByType;
import it.eng.core.business.TPagingList;
import it.eng.document.configuration.GestioneEsportazioneAsincronaListeBean;
import it.eng.document.function.GestioneDocumenti;
import it.eng.document.function.ResultSetExporter;
import it.eng.document.function.bean.FileInfoBean;
import it.eng.document.function.bean.GenerazioneAsincronaResultBean;
import it.eng.document.function.bean.RebuildedFile;
import it.eng.document.function.bean.VersionaDocumentoInBean;
import it.eng.document.function.bean.VersionaDocumentoOutBean;
import it.eng.spring.utility.SpringAppContext;
import it.eng.util.AurigaFileUtils;
import it.eng.utility.DocumentConfiguration;
import it.eng.utility.export.ExportMerger;
import it.eng.utility.export.ExportType;
import it.eng.utility.storageutil.exception.StorageException;
import it.eng.utility.ui.module.core.server.bean.ExportBean;

/**
 * Espone la funzionalità di generazione di un documento di esportazione dei risultati di una lista in auriga
 * 
 * @author massimo malvestio
 *
 */
@Service(name = "GenerazioneAsincronaDocumentoJob")
public class GestioneEsportazioneAsincronaListe {

	protected static Logger logger = Logger.getLogger(GestioneEsportazioneAsincronaListe.class);

	protected DaoBmtJobParameters daoBmtJobParameters = new DaoBmtJobParameters();

	protected DaoBmtJobs daoBmtJobsClient = new DaoBmtJobs();

	private DocumentConfiguration documentConfiguration;

	private SimpleDateFormat dateFormat;

	private ExecutorService executorService;

	private GestioneEsportazioneAsincronaListeBean config;

	@SuppressWarnings("unchecked")
	@Operation(name = "generaDocumentoJob")
	public GenerazioneAsincronaResultBean GeneraDocumentoJob(AurigaLoginBean loginBean, JobBean currentJob) {

		init();

		ExecutionResultBean<Map<String, JobParameterBean>> result = extractJobParameters(loginBean, currentJob);

		if (!result.isSuccessful()) {

			updateJobAsInError(currentJob, result.getMessage());

			return createReturnValue(result);
		}

		// PARAMETRI DI ESPORTAZIONE
		Map<String, JobParameterBean> parameters = result.getResult();

		// verifico la validità dei parametri in ingresso
		ExecutionResultBean<?> validateResult = validateJobParameters(parameters, currentJob);

		if (!validateResult.isSuccessful()) {

			updateJobAsInError(currentJob, result.getMessage());

			return createReturnValue(validateResult);
		}

		// RIMAPPATURE DEI CAMPI
		Map<String, String> remapConditions = (Map<String, String>) result.getAdditionalInformation("remapConditions");

		// CAMPI
		ExecutionResultBean<Field[]> classFieldsResult = extractClassFields(parameters.get("OBJECT_CLASSNAME").getParameterValue());

		if (!result.isSuccessful()) {

			updateJobAsInError(currentJob, result.getMessage());

			return createReturnValue(result);

		}

		// CAMPI DA RENDERIZZARE
		Field[] fields = classFieldsResult.getResult();

		ExportBean exportBeanTemplate = generateExportBean(currentJob, parameters);

		List<File> chunks = new ArrayList<File>();

		try {

			TFilterFetch<JobParameterBean> rowsCountFilterBean = createFilter(currentJob);

			ExecutionResultBean<Integer> rowsCountResult = daoBmtJobParameters.countRows(loginBean, rowsCountFilterBean);

			if (rowsCountResult.isSuccessful()) {

				Integer rowCount = rowsCountResult.getResult();

				List<Future<ExecutionResultBean<File>>> futures = new ArrayList<Future<ExecutionResultBean<File>>>();

				// @TODO da parametrizzare
				if (rowCount < 6) {

					TFilterFetch<JobParameterBean> resultsetsFilterBean = createFilter(currentJob);

					resultsetsFilterBean.setStartRow(0);
					resultsetsFilterBean.setEndRow(rowCount);

					TPagingList<JobParameterBean> resultSets = daoBmtJobParameters.searchWithLogin(loginBean, resultsetsFilterBean);

					if (resultSets.getData() != null && !resultSets.getData().isEmpty()) {

						futures = launchPartialExports(currentJob, parameters, remapConditions, resultSets.getData(), fields, exportBeanTemplate,
								exportBeanTemplate.showHeaders());

					}

				} else {

					for (int pageIndex = 0; pageIndex < rowCount; pageIndex++) {

						TFilterFetch<JobParameterBean> resultsetFilterExportBean = createFilter(currentJob);

						resultsetFilterExportBean.setStartRow(pageIndex);
						resultsetFilterExportBean.setEndRow(pageIndex);

						// è soltanto 1
						TPagingList<JobParameterBean> resultSet = daoBmtJobParameters.searchWithLogin(loginBean, resultsetFilterExportBean);

						// se è richiesta l'intestazione solo il primo export la
						// renderizza, tutti gli altri no
						futures.addAll(launchPartialExports(currentJob, parameters, remapConditions, resultSet.getData(), fields, exportBeanTemplate,
								pageIndex == 0 && exportBeanTemplate.showHeaders()));

					}
				}

				// recupero i risultati delle esportazioni parziali
				for (Future<ExecutionResultBean<File>> future : futures) {

					try {

						ExecutionResultBean<File> partialExport = future.get();

						if (partialExport.isSuccessful()) {

							chunks.add(partialExport.getResult());

						} else {

							String message = String.format("Durante la generazione delle esportazioni parziali, si è verificata la seguente eccezione %1$s",
									partialExport.getMessage());
							GenerazioneAsincronaResultBean errorResult = new GenerazioneAsincronaResultBean();
							errorResult.setMessage(message);
							errorResult.setSuccessful(false);

							logger.error(message);

							updateJobAsInError(currentJob, message);

							return errorResult;
						}
					} catch (Exception e) {

						String message = String.format("Durante la generazione delle esportazioni parziali, si è verificata la seguente eccezione %1$s",
								ExceptionUtils.getFullStackTrace(e));

						GenerazioneAsincronaResultBean errorResult = new GenerazioneAsincronaResultBean();
						errorResult.setMessage(message);
						errorResult.setSuccessful(false);

						logger.error(message);

						updateJobAsInError(currentJob, message);

						return errorResult;
					}
				}

				ExecutionResultBean<File> mergeResult = mergeChunks(chunks, exportBeanTemplate, currentJob, parameters);

				if (mergeResult.isSuccessful()) {

					File exportFile = mergeResult.getResult();

					logger.info(String.format("Esportazione completata del file %1$s, aggiornamento del record relativo al job", exportFile.getAbsolutePath()));

					updateJobReferenceDocumentVersion(currentJob, exportFile, loginBean);

					try {
						updateJobAsCompleted(currentJob);
					} catch (Exception e) {
						logger.error(String.format("Durante l'aggiornamento a stato completato del job %1$s, si è verificata la seguente eccezione",
								currentJob.getIdJob()), e);
					}

					// il file di esportazione non è salvato in una
					// directory di rete, procedo all'eliminazione
					if (!isRemoteSaveEnabled(parameters)) {

						try {
							FileDeleteStrategy.FORCE.delete(exportFile);
							logger.info("Rimosso il file temporaneo " + exportFile.getAbsolutePath());
						} catch (Exception e) {
							logger.info("Non è stato possibile rimuovere il file temporaneo " + exportFile.getAbsolutePath());
						}
					}

					updateJobAsCompleted(currentJob);

					GenerazioneAsincronaResultBean returnValue = new GenerazioneAsincronaResultBean();
					returnValue.setMessage("Il file è stato correttamente generato");
					returnValue.setSuccessful(true);

					return returnValue;

				} else {

					updateJobAsInError(currentJob, result.getMessage());

					GenerazioneAsincronaResultBean returnValue = new GenerazioneAsincronaResultBean();
					returnValue.setMessage(mergeResult.getMessage());
					returnValue.setSuccessful(mergeResult.isSuccessful());

					return returnValue;

				}

			} else {

				updateJobAsInError(currentJob, result.getMessage());

				GenerazioneAsincronaResultBean returnValue = new GenerazioneAsincronaResultBean();
				returnValue.setMessage(rowsCountResult.getMessage());
				returnValue.setSuccessful(rowsCountResult.isSuccessful());

				return returnValue;

			}

		} catch (Exception e) {

			GenerazioneAsincronaResultBean returnValue = new GenerazioneAsincronaResultBean();
			returnValue.setMessage("Durante la generazione del documento, si è verificata un'eccezione, verificare i log");
			returnValue.setSuccessful(false);

			logger.error(String.format("Durante la generazione del documento legato al job %1$s, si è verificata la seguente eccezione %2$s",
					currentJob.getIdJob(), ExceptionUtils.getFullStackTrace(e)));

			return returnValue;

		}
	}

	/**
	 * @param result
	 * @return
	 */
	protected GenerazioneAsincronaResultBean createReturnValue(ExecutionResultBean<?> result) {
		GenerazioneAsincronaResultBean returnValue = new GenerazioneAsincronaResultBean();
		returnValue.setMessage(result.getMessage());
		returnValue.setSuccessful(result.isSuccessful());
		return returnValue;
	}

	/**
	 * @param bmtJobs
	 * @param currentJob
	 * @throws Exception
	 */
	protected void updateJobAsCompleted(JobBean currentJob) throws Exception {
		updateJobStatus(currentJob, "R");
	}

	/**
	 * Carica il documento di esportazione lista generato in storage e lo versiona, in maniera da renderlo poi visibile in scrivania
	 * 
	 * @param currentJob
	 * @param exportFile
	 * @throws StorageException
	 * @throws BeansException
	 * @throws Exception
	 * @throws RuntimeException
	 */
	protected ExecutionResultBean<Boolean> updateJobReferenceDocumentVersion(JobBean currentJob, File exportFile, AurigaLoginBean loginBean) {

		ExecutionResultBean<Boolean> retValue = new ExecutionResultBean<Boolean>();
		retValue.setSuccessful(true);

		try {

			RebuildedFile lRebuildedFile = new RebuildedFile();
			lRebuildedFile.setIdDocumento(currentJob.getIdDoc());
			lRebuildedFile.setFile(exportFile);

			FileInfoBean lFileInfoBean = AurigaFileUtils.createFileInfoBean(exportFile, documentConfiguration.getAlgoritmo().value(),
					documentConfiguration.getEncoding().value());

			lRebuildedFile.setInfo(lFileInfoBean);

			VersionaDocumentoInBean versionaDocumenti = new VersionaDocumentoInBean();
			versionaDocumenti.setFile(exportFile);
			versionaDocumenti.setInfo(lFileInfoBean);
			versionaDocumenti.setIdDocumento(currentJob.getIdDoc());

			GestioneDocumenti lGestioneDocumenti = new GestioneDocumenti();
			VersionaDocumentoOutBean lVersionaDocumentoOutBean = lGestioneDocumenti.versionaDocumento(loginBean, versionaDocumenti);
			if (lVersionaDocumentoOutBean.getDefaultMessage() != null) {

				String message = String.format("Durante il versionamento del file %1$s, si è verificata la seguente eccezione %2$s",
						exportFile.getAbsolutePath(), lVersionaDocumentoOutBean.getDefaultMessage());

				logger.error(message);

				retValue.setSuccessful(false);
				retValue.setMessage(message);
			}

		} catch (Exception e) {

			String message = String.format("Durante il versionamento del file %1$s, si è verificata la seguente eccezione %2$s", exportFile.getAbsolutePath(),
					ExceptionUtils.getFullStackTrace(e));

			logger.error(message);

			retValue.setSuccessful(false);
			retValue.setMessage(message);

		}

		return retValue;
	}

	/**
	 * Esportazione parallela dei singoli ResultSet che sono stati trovati
	 *
	 * @param currentJob
	 * @param parameters
	 * @param remapConditions
	 * @param resultSets
	 * @param fields
	 * @param exportBean
	 * @return
	 */
	protected List<Future<ExecutionResultBean<File>>> launchPartialExports(JobBean currentJob, Map<String, JobParameterBean> parameters,
			Map<String, String> remapConditions, List<JobParameterBean> resultSets, Field[] fields, ExportBean exportBean, boolean showHeaders) {

		// lancio la generazione delle esportazioni parziali
		List<Future<ExecutionResultBean<File>>> futures = new ArrayList<Future<ExecutionResultBean<File>>>();

		if (exportBean.showHeaders()) {

			ResultSetExporter exporter = new ResultSetExporter(resultSets.get(0), parameters, remapConditions, currentJob, dateFormat, fields, true,
					exportBean);
			futures.add(executorService.submit(exporter));

			for (int i = 1; i < resultSets.size(); i++) {

				ResultSetExporter exporter2 = new ResultSetExporter(resultSets.get(i), parameters, remapConditions, currentJob, dateFormat, fields, false,
						exportBean);
				futures.add(executorService.submit(exporter2));

			}

		} else {

			for (JobParameterBean resultSet : resultSets) {

				ResultSetExporter exporter = new ResultSetExporter(resultSet, parameters, remapConditions, currentJob, dateFormat, fields, false, exportBean);
				futures.add(executorService.submit(exporter));
			}
		}
		return futures;
	}

	protected ExportBean generateExportBean(JobBean currentJob, Map<String, JobParameterBean> parameters) {

		ExportBean exportBean = new ExportBean();

		exportBean.setCsvSeparator(parameters.get("CSV_SEPARATOR").getParameterValue());

		boolean showHeaders = Boolean.TRUE;

		JobParameterBean showHeaderParameter = parameters.get("SHOW_HEADER");
		if (showHeaderParameter != null) {
			showHeaders = Boolean.valueOf(showHeaderParameter.getParameterValue());
		}
		exportBean.setShowHeaders(showHeaders);

		JobParameterBean headerFields = parameters.get("HEADER_FIELDS");
		exportBean.setFields(headerFields.getParameterValue().split("\\|\\*\\|"));

		if (showHeaders) {
			JobParameterBean headerColumns = parameters.get("HEADER_COLUMNS");
			exportBean.setHeaders(headerColumns.getParameterValue().split("\\|\\*\\|"));
		}

		exportBean.setFormatoExport(currentJob.getFormato());

		return exportBean;

	}

	/**
	 * Estrae by reflection i campi della classe specificata, li setta ad accessibili e li salva in una mappa locale alla classe che funge da cache
	 * 
	 * @param clazz
	 * @return
	 */
	private ExecutionResultBean<Field[]> extractClassFields(String className) {

		Field[] fields = null;

		ExecutionResultBean<Field[]> retValue = new ExecutionResultBean<Field[]>();

		try {

			Class<?> objectClassName = Class.forName(className);

			fields = objectClassName.getDeclaredFields();

			for (Field currentField : fields) {

				currentField.setAccessible(Boolean.TRUE);

			}

			retValue.setSuccessful(true);
			retValue.setResult(fields);

		} catch (Exception e) {

			String message = String.format("Durante l'estrazione dei campi della classe %1$s, si è verificata la seguente eccezione %2$s", className,
					ExceptionUtils.getFullStackTrace(e));
			retValue.setSuccessful(false);
			retValue.setMessage(message);
			logger.error(message);
		}

		return retValue;
	}

	/**
	 * Crea il filter per il recupero dei result set
	 * 
	 * @param currentJob
	 * @return
	 */
	private TFilterFetch<JobParameterBean> createFilter(JobBean currentJob) {

		// non sono stati implementati tutti i filtraggi, quindi se qualcosa
		// non va verificare buildCriteria in DaoBmtJobParameters

		JobParameterBean filterBean = new JobParameterBean();
		filterBean.setIdJob(currentJob.getIdJob());
		filterBean.setParameterSubtype("RESULTSET");

		HashMap<String, String> operators = new HashMap<String, String>();
		operators.put("parameterSubtype", "equal");
		filterBean.setOperators(operators);

		TFilterFetch<JobParameterBean> filter = new TFilterFetch<JobParameterBean>();
		filter.setFilter(filterBean);

		List<TOrderBy> orders = new ArrayList<TOrderBy>();
		TOrderBy orderBy = new TOrderBy();
		orderBy.setPropname("id.parameterId");
		// dal più vecchio al più recente
		orderBy.setType(OrderByType.ASCENDING);
		orders.add(orderBy);
		filter.setOrders(orders);

		return filter;
	}

	/**
	 * Valida i parametri del jo b che sono stati salvati, verificando che ci siano le condizioni per lanciare la generazione del documento
	 * 
	 * @param parameters
	 * @return
	 */
	private ExecutionResultBean<Boolean> validateJobParameters(Map<String, JobParameterBean> parameters, JobBean currentJob) {

		ExecutionResultBean<Boolean> retValue = new ExecutionResultBean<Boolean>();
		retValue.setSuccessful(true);

		completed: {

			boolean noColumnsSpecified = parameters.get("HEADER_FIELDS") == null
					|| ((JobParameterBean) parameters.get("HEADER_FIELDS")).getParameterValue() == null
					|| ((JobParameterBean) parameters.get("HEADER_FIELDS")).getParameterValue().isEmpty();

			if (noColumnsSpecified) {

				String message = String.format("Non è stata specificata alcuna colonna da visualizzare per il job %1$s", currentJob.getIdJob());
				logger.error(message);

				retValue.setSuccessful(false);
				retValue.setMessage(message);

				break completed;
			}

			boolean wrongHeaders = (parameters.get("HEADER_COLUMNS") == null
					|| ((JobParameterBean) parameters.get("HEADER_COLUMNS")).getParameterValue() == null
					|| ((JobParameterBean) parameters.get("HEADER_COLUMNS")).getParameterValue().isEmpty())
					&& (parameters.get("SHOW_HEADER") == null || (((JobParameterBean) parameters.get("SHOW_HEADER")).getParameterValue() == null
							|| Boolean.valueOf(((JobParameterBean) parameters.get("SHOW_HEADER")).getParameterValue())));

			if (wrongHeaders) {

				String message = String.format("Non sono stati specificati i nomi delle colonne per il job %1$s", currentJob.getIdJob());
				logger.error(message);

				retValue.setSuccessful(false);
				retValue.setMessage(message);

			}
		}

		if (retValue.isSuccessful()) {

			logger.info("I parametri del job risultano essere corretti");
		}

		return retValue;
	}

	/**
	 * @param currentJob
	 * @param string
	 * @throws Exception
	 */
	protected void updateJobAsInError(JobBean currentJob, String cause) {

		try {

			updateJobStatus(currentJob, "X");
			insertErrorParameterOut(currentJob, cause);

			logger.info(String.format("Posto il job %1$s in stato ERROR", currentJob.getIdJob()));

		} catch (Exception e) {

			String stackTrace = ExceptionUtils.getFullStackTrace(e);
			logger.error(String.format("Durante l'aggiornamento in stato di errore del job %1$, si è verificata la seguente eccezione %2$s",
					String.valueOf(currentJob.getIdJob().longValue()), stackTrace));

		}
	}

	protected void updateJobStatus(JobBean currentJob, String status) throws Exception {
		currentJob.setStatus(status);
		currentJob.setEndTime(new Date());
		daoBmtJobsClient.update(currentJob);
	}

	/**
	 * Inserisce un PARAMETER OUT contenente il messaggio di errore che ha impedito l'esportazione del job
	 * 
	 * @param currentJob
	 * @param cause
	 */
	private void insertErrorParameterOut(JobBean currentJob, String cause) {

		try {

			JobParameterBean errorParamOut = new JobParameterBean();

			errorParamOut.setIdJob(currentJob.getIdJob());
			errorParamOut.setParameterDir("OUT");
			errorParamOut.setParameterId(new BigDecimal(99));
			errorParamOut.setParameterSubtype("ERROR");
			errorParamOut.setParameterType("VARCHAR2");
			errorParamOut.setParameterValue(cause);

			daoBmtJobParameters.save(errorParamOut);

		} catch (Exception e) {
			logger.error("Durante il salvataggio del messaggio di errore, si è verificata la seguente eccezione", e);
		}
	}

	/**
	 * @param locale
	 * @param loginBean
	 * @param currentJob
	 * @return
	 *         <ul>
	 *         <li>"jobParameters" i parametri del job</li>
	 *         <li>"remapConditions" le condizioni di rimappatura dei campi</li>
	 *         </ul>
	 * @throws Exception
	 */
	protected ExecutionResultBean<Map<String, JobParameterBean>> extractJobParameters(AurigaLoginBean loginBean, JobBean currentJob) {

		ExecutionResultBean<Map<String, JobParameterBean>> result = new ExecutionResultBean<Map<String, JobParameterBean>>();

		logger.info(String.format("Estrazione dei parametri di lancio relativi al job %1$s", currentJob.getIdJob()));

		try {

			// non sono stati implementati tutti i filtraggi, quindi se qualcosa
			// non va verificare buildCriteria in DaoBmtJobParameters

			JobParameterBean filterBean = new JobParameterBean();
			filterBean.setIdJob(currentJob.getIdJob());
			filterBean.setParameterSubtype("RESULTSET");

			HashMap<String, String> operators = new HashMap<String, String>();
			operators.put("parameterSubtype", "notequal");
			filterBean.setOperators(operators);

			TFilterFetch<JobParameterBean> filter = new TFilterFetch<JobParameterBean>();
			filter.setFilter(filterBean);

			TPagingList<JobParameterBean> params = daoBmtJobParameters.searchWithLogin(loginBean, filter);

			logger.info("Analisi dei parametri trovati");

			Map<String, JobParameterBean> jobParameters = new LinkedHashMap<String, JobParameterBean>();

			Map<String, String> remapConditions = new LinkedHashMap<String, String>();

			for (JobParameterBean param : params.getData()) {

				if (param.getParameterSubtype().equals("REMAP_PARAMETER")) {

					String[] csvValue = param.getParameterValue().split("\\|\\*\\|");
					remapConditions.put(csvValue[0], csvValue[1]);

				} else if (param.getParameterSubtype().contains("ERROR")) {

					// elimino il parametro di uscita di errore delle precedente
					// esecuzione
					daoBmtJobParameters.delete(param);

				} else {

					jobParameters.put(param.getParameterSubtype(), param);

				}
			}

			result.setSuccessful(true);

			result.setResult(jobParameters);

			result.addAdditionalInformation("remapConditions", remapConditions);

		} catch (Exception e) {

			String message = String.format("Durante il recupero dei parametri del job %1$s si è verificata la seguente eccezione %2$s", currentJob.getIdJob(),
					ExceptionUtils.getFullStackTrace(e));

			logger.error(message);

			result.setSuccessful(false);

			result.setMessage(message);

		}

		return result;
	}

	private ExecutionResultBean<File> mergeChunks(List<File> chunks, ExportBean exportBean, JobBean currentJob, Map<String, JobParameterBean> parameters) {

		JobParameterBean remoteSaveNameParam = parameters.get("REMOTE_SAVE_NAME");

		// per questioni di performance su grossi file, si supporta solo la
		// generazione di xlsx, non xls

		if (remoteSaveNameParam != null) {

			String remoteSaveName = remoteSaveNameParam.getParameterValue();

			if (remoteSaveName != null && remoteSaveName.endsWith("xls")) {
				remoteSaveNameParam.setParameterValue(remoteSaveName + "x");
			}
		}

		if (currentJob.getFormato().toLowerCase().equals("xls")) {
			currentJob.setFormato(currentJob.getFormato().toLowerCase() + "x");
		}

		if (exportBean.getFormatoExport().toLowerCase().equals("xls")) {
			exportBean.setFormatoExport(exportBean.getFormatoExport().toLowerCase() + "x");
		}

		ExecutionResultBean<File> retValue = createExportFile(currentJob, parameters, isRemoteSaveEnabled(parameters));

		if (retValue.isSuccessful()) {

			File mergedChunks = retValue.getResult();

			String formatoExport = exportBean.getFormatoExport();

			switch (ExportType.valueOf(formatoExport.toUpperCase())) {

			case CSV:
				retValue = ExportMerger.mergeCsv(chunks, mergedChunks);
				break;

			case PDF:
				retValue = ExportMerger.mergePdfs(chunks, mergedChunks);
				break;

			case XML:

				int numTotRighe = 0;

				JobParameterBean nroTotBean = parameters.get("NRO_TOT_REC");
				String nroTotValue = nroTotBean.getParameterValue();

				if (nroTotBean != null && nroTotValue != null && !nroTotValue.isEmpty()) {
					numTotRighe = Integer.valueOf(nroTotValue);
				}

				retValue = ExportMerger.mergeDataExport(chunks, mergedChunks, numTotRighe);
				break;

			case XLS:
				retValue = ExportMerger.mergeXls(chunks, mergedChunks);
				break;

			case XLSX:
				retValue = ExportMerger.mergeXlsx(chunks, mergedChunks);
				break;

			default:

				String message = String.format("Il formato specificato non è gestito %1$s", formatoExport);
				retValue.setSuccessful(false);
				retValue.setMessage(message);

				logger.error(message);
			}
		}

		clearChunks(chunks);

		return retValue;
	}

	/**
	 * @param currentJob
	 * @param parameters
	 * @param remoteSaveEnabled
	 * @return
	 * @throws IOException
	 */
	protected ExecutionResultBean<File> createExportFile(JobBean currentJob, Map<String, JobParameterBean> parameters, boolean remoteSaveEnabled) {

		File exportFile = null;

		try {

			if (remoteSaveEnabled) {

				String path = parameters.get("REMOTE_SAVE_DIRECTORY").getParameterValue() + System.getProperty("file.separator")
						+ parameters.get("REMOTE_SAVE_NAME").getParameterValue();
				exportFile = new File(path);

			} else {
				exportFile = File.createTempFile(currentJob.getExportFilename(), "." + currentJob.getFormato());
			}

			ExecutionResultBean<File> retValue = new ExecutionResultBean<File>();
			retValue.setSuccessful(true);
			retValue.setResult(exportFile);

			return retValue;

		} catch (Exception e) {

			String message = String.format("Durante la creazione del file di esportazione, si è verificata la seguente eccezione", e);
			logger.error(message);

			ExecutionResultBean<File> retValue = new ExecutionResultBean<File>();
			retValue.setMessage(message);
			retValue.setSuccessful(false);

			return retValue;
		}

	}

	/**
	 * @param chunks
	 */
	protected void clearChunks(List<File> chunks) {

		for (File chunk : chunks) {

			try {

				FileUtils.forceDelete(chunk);
				logger.debug(String.format("Il file %1$s è stato rimosso correttamente", chunk.getAbsolutePath()));

			} catch (Exception e) {
				logger.error(
						String.format("Non è stato posssibile cancellare il file temporaneo %1$s a causa della seguente eccezione", chunk.getAbsolutePath()),
						e);
			}
		}
	}

	/**
	 * @param parameters
	 * @return
	 */
	protected boolean isRemoteSaveEnabled(Map<String, JobParameterBean> parameters) {
		boolean remoteSaveEnabled = parameters.get("REMOTE_SAVE_DIRECTORY") != null && parameters.get("REMOTE_SAVE_DIRECTORY").getParameterValue() != null
				&& !parameters.get("REMOTE_SAVE_DIRECTORY").getParameterValue().isEmpty() && parameters.get("REMOTE_SAVE_NAME") != null
				&& parameters.get("REMOTE_SAVE_NAME").getParameterValue() != null && !parameters.get("REMOTE_SAVE_NAME").getParameterValue().isEmpty();
		return remoteSaveEnabled;
	}

	/**
	 * @throws BeansException
	 */
	protected void init() throws BeansException {
		ApplicationContext context = SpringAppContext.getContext();

		config = (GestioneEsportazioneAsincronaListeBean) context.getBean("GestioneEsportazioneAsincronaListeBean");

		executorService = new ThreadPoolExecutor(config.getCorePoolSize(), config.getMaximumPoolSize(), config.getKeepAliveTime(), TimeUnit.SECONDS,
				new ArrayBlockingQueue<Runnable>(config.getWorkQueueSize()));

		dateFormat = new SimpleDateFormat(config.getDateFormat());

		documentConfiguration = (DocumentConfiguration) context.getBean("DocumentConfiguration");
	}

}
