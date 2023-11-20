/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import javax.xml.bind.JAXBContext;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapperImpl;

import it.eng.auriga.module.business.dao.beans.JobBean;
import it.eng.auriga.module.business.dao.beans.JobParameterBean;
import it.eng.bean.ExecutionResultBean;
import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.jaxb.variabili.SezioneCache;
import it.eng.jaxb.variabili.Lista.Riga;
import it.eng.jaxb.variabili.Lista.Riga.Colonna;
import it.eng.utility.export.ExportManager;
import it.eng.utility.springBeanWrapper.BeanPropertyUtility;
import it.eng.utility.ui.module.core.server.bean.ExportBean;
import it.eng.xml.DeserializationHelper;
import it.eng.xml.ReflectionUtility;
import it.eng.xml.XmlListaUtility;

/**
 * Contiene la logica applicativa per l'esportazione di un singolo result set, e
 * la generazione del relativo documento di esportazione.<br/>
 * Necessario per il multithreading
 * 
 * @author massimo malvestio
 *
 */
public class ResultSetExporter implements Callable<ExecutionResultBean<File>> {

	protected Logger logger = Logger.getLogger(ResultSetExporter.class);

	protected JobParameterBean resultSet;

	protected Map<String, JobParameterBean> parameters;
	protected Map<String, String> remapConditions;
	protected JobBean currentJob;

	private SimpleDateFormat dateFormat;

	private ExportManager exportManager;

	private Field[] fields;

	private boolean showHeaders;

	private ExportBean exportBeanOriginal;

	public ResultSetExporter(JobParameterBean resultSet, Map<String, JobParameterBean> parameters,
			Map<String, String> remapConditions, JobBean currentJob, SimpleDateFormat dateFormat, Field[] fields,
			boolean showHeaders, ExportBean exportBean) {

		this.resultSet = resultSet;

		this.parameters = parameters;
		this.remapConditions = remapConditions;
		this.currentJob = currentJob;

		this.dateFormat = dateFormat;

		exportManager = new ExportManager();

		this.fields = fields;

		this.showHeaders = showHeaders;

		this.exportBeanOriginal = exportBean;
	}

	@Override
	public ExecutionResultBean<File> call() throws Exception {

		ExecutionResultBean<File> retValue = new ExecutionResultBean<File>();

		ExecutionResultBean<ExportBean> cloneResult = cloneExportBean(exportBeanOriginal, showHeaders, parameters,
				remapConditions, resultSet, fields);

		if (cloneResult.isSuccessful()) {

			ExportBean exportBeanCloned = cloneResult.getResult();

			ExecutionResultBean<File> exportFileResult = createExportFile(currentJob);

			if (exportFileResult.isSuccessful()) {

				File exportFile = exportFileResult.getResult();

				try {

					// siccome l'export manager non gestisce gli xlsx, devo
					// generare degli xls e poi mergiarli in un xlsx
					if (exportBeanCloned.getFormatoExport().equals("xlsx")) {
						exportBeanCloned.setFormatoExport("xls");
					}

					boolean successfulExport = exportManager.export(exportFile, exportBeanCloned);

					retValue.setSuccessful(successfulExport);
					retValue.setResult(exportFile);

					if (!successfulExport) {

						String message = "Il formato specificato non coincide con nessuno dei quelli previsti, sono costretto a bloccare l'esportazione del job corrente";

						retValue.setMessage(message);
						logger.error(message);

					} else {

						logger.info(String.format("Il file %1$s è stato esportato correttamente",
								exportFile.getAbsoluteFile()));
					}

				} catch (Exception e) {

					String message = String.format(
							"Durante il popolamento del file di esportazione, si è verificata la seguente eccezione %1$s",
							ExceptionUtils.getFullStackTrace(e));
					retValue.setSuccessful(false);
					retValue.setMessage(message);
					logger.error(message);

				}
			}
		} else {
			retValue.setSuccessful(false);
			retValue.setMessage(cloneResult.getMessage());
		}

		return retValue;

	}

	/**
	 * Crea l'export bean necessario per la generazione del documento. Vengono
	 * copiate tutte le proprietà del bean originale, sovrascrivendo showHeaders
	 * ed impostando i dati
	 * 
	 * @param exportBean2
	 * @param showHeaders
	 * @param fields
	 * @param resultSet
	 * @param remapConditions
	 * @param parameters
	 * @param deserializeData
	 * @return
	 */
	private ExecutionResultBean<ExportBean> cloneExportBean(ExportBean exportBeanOriginal, boolean showHeaders,
			Map<String, JobParameterBean> parameters, Map<String, String> remapConditions, JobParameterBean resultSet,
			Field[] fields) {

		ExecutionResultBean<HashMap<String, String>[]> convertResult = convertRecordsToMap(parameters, remapConditions,
				resultSet, fields);

		if (convertResult.isSuccessful()) {

			Map<String, String>[] records = convertResult.getResult();

			try {

				ExportBean exportBean = new ExportBean();
				BeanUtils.copyProperties(exportBeanOriginal, exportBean);

				exportBean.setShowHeaders(showHeaders);
				exportBean.setRecords(records);

				ExecutionResultBean<ExportBean> retValue = new ExecutionResultBean<ExportBean>();
				retValue.setSuccessful(true);
				retValue.setResult(exportBean);

				return retValue;

			} catch (Exception e) {

				String message = String.format(
						"Durante la clonazione dell'export bean si è verificata la seguente eccezione %1$s",
						ExceptionUtils.getFullStackTrace(e));

				ExecutionResultBean<ExportBean> retValue = new ExecutionResultBean<ExportBean>();
				retValue.setSuccessful(false);
				retValue.setMessage(message);

				return retValue;
			}
		} else {

			ExecutionResultBean<ExportBean> retValue = new ExecutionResultBean<ExportBean>();
			retValue.setSuccessful(false);
			retValue.setMessage(convertResult.getMessage());

			return retValue;
		}
	}

	/**
	 * @param currentJob
	 * @param parameters
	 * @param remoteSaveEnabled
	 * @return
	 * @throws IOException
	 */
	protected ExecutionResultBean<File> createExportFile(JobBean currentJob) {

		ExecutionResultBean<File> retValue = new ExecutionResultBean<File>();

		File exportFile = null;

		try {

			exportFile = File.createTempFile(currentJob.getExportFilename(), "." + currentJob.getFormato());

			retValue.setSuccessful(true);
			retValue.setResult(exportFile);

		} catch (Exception e) {

			String message = String
					.format("Durante la creazione del file di esportazione, si è verificata la seguente eccezione", e);
			logger.error(message);

			retValue.setMessage(message);
			retValue.setSuccessful(false);
		}

		return retValue;
	}

	/**
	 * Estrae il resultset in formato stringa recuperato dal job e deserializza
	 * i dati in esso contenuti
	 * 
	 * @param parameters
	 * @param remapConditions
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	protected ExecutionResultBean<List<?>> deserializeData(Map<String, JobParameterBean> parameters,
			Map<String, String> remapConditions, JobParameterBean resultSet) {

		try {
			Class<?> objectClassName = Class.forName(parameters.get("OBJECT_CLASSNAME").getParameterValue());

			JobParameterBean helperClassName = parameters.get("REMAPPER_HELPER_CLASSNAME");

			if (helperClassName == null) {

				try {
					List<?> data = null;
					if (parameters.get("OBJECT_CLASSNAME").getParameterValue().equals("it.eng.document.function.bean.GenericXmlBean")) {
					   data = recuperaListaGeneric(resultSet.getParameterValue());

					} else {
						data = XmlListaUtility.recuperaListaSynchronized(resultSet.getParameterValue(),
								objectClassName);
					}
					ExecutionResultBean<List<?>> retValue = new ExecutionResultBean<List<?>>();
					retValue.setSuccessful(true);
					retValue.setResult(data);

					return retValue;

				} catch (NullPointerException e) {

					boolean resultSetIsNull = resultSet == null;

					String parameterValue = resultSetIsNull ? "null" : resultSet.getParameterValue();

					String message = String
							.format("Si è verificato un null pointer durante la deserializzazione dei dati, "
									+ String.format("Resultset is null %1$s ", Boolean.toString(resultSetIsNull))
									+ String.format("ParameterValue %1$s, ", parameterValue)
									+ String.format("ObjectClassName %1$s", objectClassName));

					ExecutionResultBean<List<?>> retValue = new ExecutionResultBean<List<?>>();

					retValue.setSuccessful(false);
					retValue.setMessage(message);

					logger.error(message);

					return retValue;
				}

			} else {

				try {

					Class<?> helperClazz = Class.forName(helperClassName.getParameterValue());

					Class[] params = new Class[] { Map.class };

					DeserializationHelper helper = (DeserializationHelper) helperClazz.getDeclaredConstructor(params)
							.newInstance(remapConditions);

					List<?> data = XmlListaUtility.recuperaListaSynchronized(resultSet.getParameterValue(),
							objectClassName, helper);

					ExecutionResultBean<List<?>> retValue = new ExecutionResultBean<List<?>>();
					retValue.setSuccessful(true);
					retValue.setResult(data);

					return retValue;

				} catch (NullPointerException e) {

					boolean resultSetIsNull = resultSet == null;

					String parameterValue = resultSetIsNull ? "null" : resultSet.getParameterValue();

					String message = String
							.format("Si è verificato un null pointer durante la deserializzazione dei dati, "
									+ String.format("Resultset is null %1$s ", Boolean.toString(resultSetIsNull))
									+ String.format("ParameterValue %1$s, ", parameterValue)
									+ String.format("ObjectClassName %1$s", objectClassName));

					ExecutionResultBean<List<?>> retValue = new ExecutionResultBean<List<?>>();
					retValue.setSuccessful(false);
					retValue.setMessage(message);

					logger.error(message);

					return retValue;
				}
			}
		} catch (Exception e) {

			String message = String.format(
					"Durante la deserializzazione dei dati si è verificata la seguente eccezione %1$s",
					ExceptionUtils.getFullStackTrace(e));
			logger.error(message);

			ExecutionResultBean<List<?>> retValue = new ExecutionResultBean<List<?>>();
			retValue.setSuccessful(false);
			retValue.setMessage(message);

			return retValue;

		} finally {

			logger.info(String.format("Completata la deserializzazione dei dati relativi al result set %1$s",
					resultSet.getParameterId()));
		}
	}

	/**
	 * @param resultSet2
	 * @param remapConditions2
	 * @param parameters2
	 * @param data
	 * @param fields
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("unchecked")
	protected ExecutionResultBean<HashMap<String, String>[]> convertRecordsToMap(
			Map<String, JobParameterBean> parameters, Map<String, String> remapConditions, JobParameterBean resultSet,
			Field[] fields) {

		ExecutionResultBean<List<?>> deserializeResult = deserializeData(parameters, remapConditions, resultSet);

		if (!deserializeResult.isSuccessful()) {

			ExecutionResultBean<HashMap<String, String>[]> retValue = new ExecutionResultBean<HashMap<String, String>[]>();
			retValue.setSuccessful(false);
			retValue.setMessage(deserializeResult.getMessage());

			return retValue;
		}

		List<?> data = deserializeResult.getResult();

		// record in formato mappa
		HashMap<String, String>[] records = null;
		try {

			records = new HashMap[data.size()];

			// converto i bean in mappe dove k -> nome del campo, v -> valore
			if (!data.isEmpty()) {

				int i = 0;

				for (Object soggetto : data) {

					HashMap<String, String> result = new HashMap<String, String>();
                    if(parameters.get("OBJECT_CLASSNAME").getParameterValue().equals("it.eng.document.function.bean.GenericXmlBean"))
                    {
                    	JobParameterBean headerFields = parameters.get("HEADER_FIELDS");
                    	String[] fieldGen = headerFields.getParameterValue().split("\\|\\*\\|");
                    	int length = Array.getLength(soggetto);
                    	Object [] lObject = new Object[length];
                    	lObject=(Object [])soggetto;
                    	for(int j = 0; j < length; j++)
                        {
                    		logger.info("fieldGen[j]: "+fieldGen[j].toString() );
                    		logger.info("lObject[j]: "+lObject[j].toString() );
                    	    result.put(fieldGen[j].toString() , lObject[j].toString());
                        }
                    }
                    else
                    {	
					for (Field currentField : fields) {

						Object currentFieldValue = currentField.get(soggetto);

						if (currentFieldValue != null) {

							if (currentFieldValue instanceof Date) {

								try {
									TipoData lTipoData = currentField.getAnnotation(TipoData.class);
									if(lTipoData != null) {
										SimpleDateFormat lSimpleDateFormat = new SimpleDateFormat(
												lTipoData.tipo().getPattern());
										Date dateValue = (Date) currentFieldValue;
										result.put(currentField.getName(), lSimpleDateFormat.format(dateValue));
									} else {
										Date dateValue = (Date) currentFieldValue;
										result.put(currentField.getName(), dateFormat.format(dateValue));
									}
								} catch (Exception e) {
									logger.error(String.format(
											"Durante la deserializzazione di %1$s, si è verificata la seguente eccezione",
											currentFieldValue), e);
								}
							} else {
								result.put(currentField.getName(), currentFieldValue.toString());
							}
						}
					}
                    }//chiudo else

					records[i] = result;
					i++;
				}
			}

			ExecutionResultBean<HashMap<String, String>[]> retValue = new ExecutionResultBean<HashMap<String, String>[]>();
			retValue.setSuccessful(true);
			retValue.setResult(records);
			return retValue;

		} catch (Exception e) {

			String message = String.format(
					"Durante la deserializzazione dei dati si è verificata la seguente eccezione %1$s",
					ExceptionUtils.getFullStackTrace(e));

			logger.error(message);

			ExecutionResultBean<HashMap<String, String>[]> retValue = new ExecutionResultBean<HashMap<String, String>[]>();
			retValue.setSuccessful(false);
			retValue.setMessage(message);

			return retValue;
		}

	}
	public synchronized <T> List<T> recuperaListaGeneric(String xmlIn) throws Exception {
		
		JAXBContext context = JAXBContext.newInstance(new Class[]{it.eng.jaxb.variabili.esportazioni.Lista.class, it.eng.jaxb.variabili.esportazioni.Lista.Riga.class});

		it.eng.jaxb.variabili.esportazioni.Lista lLista = (it.eng.jaxb.variabili.esportazioni.Lista) context.createUnmarshaller()
				.unmarshal(new StringReader(xmlIn));
		List<T> lList = new ArrayList<T>();
		
		for (it.eng.jaxb.variabili.esportazioni.Lista.Riga lRiga : lLista.getRiga()) 
		{
			logger.info(" lRiga: "+lRiga);
			Object [] lObject = new Object[lRiga.getColonna().size()];
			for (it.eng.jaxb.variabili.esportazioni.Lista.Riga.Colonna lColonna : lRiga.getColonna()) {
			{
				String value = lColonna.getContent();
				logger.info(" lRiga: "+value);
				lObject[lColonna.getNro().intValue()-1 ]= new String();
				lObject[lColonna.getNro().intValue()-1 ]=value;
			}
			
		}
			lList.add((T) lObject);
	}
		return lList;
}

}	
