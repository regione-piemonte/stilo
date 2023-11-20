/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.dao.beans.JobBean;
import it.eng.auriga.module.business.dao.beans.JobParameterBean;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.DestInvioNotifica;
import it.eng.auriga.ui.module.layout.server.common.NroRecordTotBean;
import it.eng.client.DaoBmtJobParameters;
import it.eng.client.DaoBmtJobs;
import it.eng.core.business.TFilterFetch;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;
import it.eng.document.function.bean.Flag;
import it.eng.spring.utility.SpringAppContext;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.Criterion;
import it.eng.utility.ui.module.core.server.bean.ExportBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.server.StringSplitterServer;
import it.eng.utility.ui.module.layout.shared.bean.GenericConfigBean;

/**
 * Estende la classe originaria di baseUI per permettere di introdurre i metodi comuni per la generazione asincrona dei documenti di esportazione delle liste
 * 
 * @author massimo malvestio
 *
 * @param <T>
 */
public abstract class AurigaAbstractFetchDatasource<T> extends AbstractFetchDataSource<T> {

	protected int baseJobParameterId = 100;

	protected Logger logger = Logger.getLogger(getClass());

	protected String FETCH_SESSION_KEY = "fetch";

	protected SimpleDateFormat stdDateFormat = new SimpleDateFormat(FMT_STD_DATA);

	protected SimpleDateFormat timestampDateFormat = new SimpleDateFormat(FMT_STD_TIMESTAMP);

	/**
	 * Salvo i parametri necessari poi per la generazione del documento di esportazione
	 * 
	 * @param loginBean
	 * @param string
	 * @param colsToReturn
	 * @throws Exception
	 */
	protected void saveParameters(AurigaLoginBean loginBean, ExportBean exportBean, Integer jobId, String string, String className) throws Exception {

		BigDecimal jobIdBigDecimal = BigDecimal.valueOf(Long.valueOf(jobId));

		saveHeaderStampaParameter(loginBean, jobIdBigDecimal);

		// separatore da utilizzare quando l'esportazione è csv
		saveCsvSeparatorParameter(loginBean, exportBean, jobIdBigDecimal);

		// campi da mostrare
		saveFieldsParameter(loginBean, exportBean, jobIdBigDecimal);

		// header relativi ai campi da mostrare
		saveHeadersParameter(loginBean, exportBean, jobIdBigDecimal);

		saveClassnameParameter(loginBean, jobIdBigDecimal, className);
	}

	/**
	 * @param loginBean
	 * @param exportBean
	 * @param daoBmtJobParameters
	 * @param jobIdBigDecimal
	 * @param idUserLavoro
	 * @throws Exception
	 */
	protected void saveHeadersParameter(AurigaLoginBean loginBean, ExportBean exportBean, BigDecimal jobIdBigDecimal) throws Exception {

		JobParameterBean jobParametersInput = new JobParameterBean();
		DaoBmtJobParameters daoBmtJobParameters = new DaoBmtJobParameters();

		String[] headers = exportBean.getHeaders();
		StringBuilder headersCsv = new StringBuilder();

		for (int i = 0; i < headers.length - 1; i++) {
			headersCsv.append(headers[i]).append("|*|");
		}
		headersCsv.append(headers[headers.length - 1]);
		jobParametersInput = new JobParameterBean();
		jobParametersInput.setIdJob(jobIdBigDecimal);
		jobParametersInput.setParameterId(BigDecimal.valueOf(++baseJobParameterId));
		jobParametersInput.setParameterType("VARCHAR2");
		jobParametersInput.setParameterSubtype("HEADER_COLUMNS");
		jobParametersInput.setParameterValue(headersCsv.toString());
		jobParametersInput.setParameterDir("OUT");

		logger.debug(String.format("Salvataggio header columns con id %1$s", Integer.toString(baseJobParameterId)));

		daoBmtJobParameters.save(getLocale(), loginBean, jobParametersInput);

	}

	/**
	 * @param loginBean
	 * @param exportBean
	 * @param daoBmtJobParameters
	 * @param jobIdBigDecimal
	 * @param idUserLavoro
	 * @throws Exception
	 */
	protected void saveFieldsParameter(AurigaLoginBean loginBean, ExportBean exportBean, BigDecimal jobIdBigDecimal) throws Exception {

		JobParameterBean jobParametersInput = new JobParameterBean();
		DaoBmtJobParameters daoBmtJobParameters = new DaoBmtJobParameters();

		String[] fields = exportBean.getFields();

		StringBuilder fieldsCsv = new StringBuilder();

		for (int i = 0; i < fields.length - 1; i++) {
			fieldsCsv.append(fields[i]).append("|*|");
		}
		fieldsCsv.append(fields[fields.length - 1]);

		jobParametersInput = new JobParameterBean();
		jobParametersInput.setIdJob(jobIdBigDecimal);
		jobParametersInput.setParameterId(BigDecimal.valueOf(++baseJobParameterId));
		jobParametersInput.setParameterType("VARCHAR2");
		jobParametersInput.setParameterSubtype("HEADER_FIELDS");
		jobParametersInput.setParameterValue(fieldsCsv.toString());
		jobParametersInput.setParameterDir("OUT");
		daoBmtJobParameters.save(getLocale(), loginBean, jobParametersInput);

		logger.debug(String.format("Salvataggio header fields con id %1$s", Integer.toString(baseJobParameterId)));
	}

	/**
	 * @param loginBean
	 * @param exportBean
	 * @param daoBmtJobParameters
	 * @param jobIdBigDecimal
	 * @param idUserLavoro
	 * @throws Exception
	 */
	protected void saveCsvSeparatorParameter(AurigaLoginBean loginBean, ExportBean exportBean, BigDecimal jobIdBigDecimal) throws Exception {

		JobParameterBean jobParametersInput = new JobParameterBean();
		DaoBmtJobParameters daoBmtJobParameters = new DaoBmtJobParameters();

		jobParametersInput.setIdJob(jobIdBigDecimal);
		jobParametersInput.setParameterId(BigDecimal.valueOf(++baseJobParameterId));
		jobParametersInput.setParameterType("VARCHAR2");
		jobParametersInput.setParameterSubtype("CSV_SEPARATOR");
		jobParametersInput.setParameterValue(exportBean.getCsvSeparator());
		jobParametersInput.setParameterDir("OUT");
		daoBmtJobParameters.save(getLocale(), loginBean, jobParametersInput);

		logger.debug(String.format("Salvataggio csv separator con id %1$s", Integer.toString(baseJobParameterId)));
	}

	/**
	 * @param loginBean
	 * @param daoBmtJobParameters
	 * @param jobParametersInput
	 * @param jobIdBigDecimal
	 * @param idUserLavoro
	 * @throws Exception
	 */
	protected void saveHeaderStampaParameter(AurigaLoginBean loginBean, BigDecimal jobIdBigDecimal) throws Exception {

		JobParameterBean jobParametersInput = new JobParameterBean();
		DaoBmtJobParameters daoBmtJobParameters = new DaoBmtJobParameters();
		jobParametersInput.setIdJob(jobIdBigDecimal);
		jobParametersInput.setParameterId(BigDecimal.valueOf(++baseJobParameterId));
		jobParametersInput.setParameterType("VARCHAR2");
		jobParametersInput.setParameterSubtype("HEADER_STAMPA");
		jobParametersInput.setParameterValue("Esportazione lista clienti");
		jobParametersInput.setParameterDir("OUT");

		logger.debug(String.format("Salvataggio header con id %1$s", Integer.toString(baseJobParameterId)));

		daoBmtJobParameters.save(getLocale(), loginBean, jobParametersInput);
	}

	protected void saveClassnameParameter(AurigaLoginBean loginBean, BigDecimal jobIdBigDecimal, String className) throws Exception {

		JobParameterBean jobParametersInput = new JobParameterBean();
		DaoBmtJobParameters daoBmtJobParameters = new DaoBmtJobParameters();
		jobParametersInput.setIdJob(jobIdBigDecimal);
		jobParametersInput.setParameterId(BigDecimal.valueOf(++baseJobParameterId));
		jobParametersInput.setParameterType("VARCHAR2");
		jobParametersInput.setParameterSubtype("OBJECT_CLASSNAME");
		jobParametersInput.setParameterValue(className);
		jobParametersInput.setParameterDir("OUT");

		logger.debug(String.format("Salvataggio classname con id %1$s", Integer.toString(baseJobParameterId)));

		daoBmtJobParameters.save(getLocale(), loginBean, jobParametersInput);
	}

	/**
	 * Salva le condizioni di rimappatura per la deserializzazione dell'xml ritornato dalla store in bean
	 * 
	 * @param createRemapConditionsMap
	 *            k -> nome del parametro, v -> valore del parametro
	 */
	protected void saveRemapInformations(AurigaLoginBean loginBean, Integer jobId, Map<String, String> createRemapConditionsMap, Class<?> clazz)
			throws Exception {

		BigDecimal jobIdBigDecimal = new BigDecimal(jobId);

		for (Entry<String, String> currentEntry : createRemapConditionsMap.entrySet()) {

			JobParameterBean jobParametersInput = new JobParameterBean();
			DaoBmtJobParameters daoBmtJobParameters = new DaoBmtJobParameters();
			jobParametersInput.setIdJob(jobIdBigDecimal);
			jobParametersInput.setParameterId(BigDecimal.valueOf(++baseJobParameterId));
			jobParametersInput.setParameterType("VARCHAR2");
			jobParametersInput.setParameterSubtype("REMAP_PARAMETER");
			jobParametersInput.setParameterValue(currentEntry.getKey() + "|*|" + currentEntry.getValue());
			jobParametersInput.setParameterDir("OUT");

			logger.debug(String.format("Salvataggio %1$s con id %2$s", currentEntry.getKey(), Integer.toString(baseJobParameterId)));

			daoBmtJobParameters.save(getLocale(), loginBean, jobParametersInput);

		}

		JobParameterBean jobParametersInput = new JobParameterBean();
		DaoBmtJobParameters daoBmtJobParameters = new DaoBmtJobParameters();
		jobParametersInput.setIdJob(jobIdBigDecimal);
		jobParametersInput.setParameterId(BigDecimal.valueOf(++baseJobParameterId));
		jobParametersInput.setParameterType("VARCHAR2");
		jobParametersInput.setParameterSubtype("REMAPPER_HELPER_CLASSNAME");
		jobParametersInput.setParameterValue(clazz.getName());
		jobParametersInput.setParameterDir("OUT");

		daoBmtJobParameters.save(getLocale(), loginBean, jobParametersInput);
	}

	/**
	 * Aggiorna il job con le informazioni relative al formato
	 * 
	 * @param idUserLavoro
	 * @throws Exception
	 */
	protected void updateJob(AurigaLoginBean loginBean, ExportBean bean, Integer jobId, BigDecimal idUserLavoro) throws Exception {

		DaoBmtJobs daoBmtJobs = new DaoBmtJobs();

		TFilterFetch<JobBean> filter = new TFilterFetch<JobBean>();

		JobBean filterBean = new JobBean();
		filterBean.setIdJob(new BigDecimal(jobId));
		filter.setFilter(filterBean);
		JobBean input = daoBmtJobs.get(getLocale(), loginBean, filterBean);

		if (input != null) {
			input.setFormato(bean.getFormatoExport());
			daoBmtJobs.update(getLocale(), loginBean, input);
		}
	}

	@Override
	public ExportBean export(ExportBean bean) throws Exception {
		if (bean.isOverflow()) {
			// si è verificato un overflow allora recupero i record e invoco la generazione asincrona
			asyncExport(bean);
		} else if (bean.isPaginazioneAttiva()) {
			// ho attiva una paginazione in lista ma io devo esportare tutti i risultati e non solo una pagina
			serverExportSenzaPaginazione(bean);
		} else {
			// non si è verificato overflow, i dati mostrati sono tutti i dati effettivamente a disposizione
			super.export(bean);
		}
		return bean;
	}

	/**
	 * Estrae i bean eventualmente salvati in sessione e li converte in un array di mappe, in maniera tale da porterlo poi salvare nell'ExportBean e darlo in
	 * pasto all'ExportManager
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	protected Map[] transformSavedBeansInMaps() {

		List<?> data = (List<?>) getSession().getAttribute(FETCH_SESSION_KEY);

		Map[] retValue = new Map[data.size()];

		for (int i = 0; i < data.size(); i++) {

			try {

				Object currentBean = data.get(i);

				retValue[i] = convertToMap(currentBean, currentBean.getClass());

			} catch (Exception e) {

				logger.error("Si è verificata la seguente eccezione durante la conversione in mappa del bean", e);
			}

		}

		return retValue;
	}

	/**
	 * @param currentBean
	 * @param currentMap
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	protected Map<String, String> convertToMap(Object currentBean, Class<?> clazz) throws SecurityException, IllegalArgumentException, IllegalAccessException {

		Map<String, String> currentMap = new LinkedHashMap<String, String>();

		Field[] fields = clazz.getDeclaredFields();

		for (Field currentField : fields) {

			currentField.setAccessible(Boolean.TRUE);

			Object fieldValue = currentField.get(currentBean);

			if (fieldValue != null && fieldValue instanceof Date) {

				TipoData tipoData = currentField.getAnnotation(TipoData.class);

				if (tipoData != null && tipoData.tipo().equals(Tipo.DATA_SENZA_ORA)) {
					currentMap.put(currentField.getName(), stdDateFormat.format(fieldValue));
				} else {
					currentMap.put(currentField.getName(), timestampDateFormat.format(fieldValue));
				}

			} else {

				if (fieldValue != null) {
					currentMap.put(currentField.getName(), fieldValue.toString());
				} else {
					currentMap.put(currentField.getName(), null);
				}
			}
		}

		Class<?> superclass = clazz.getSuperclass();

		if (superclass != null) {
			currentMap.putAll(convertToMap(currentBean, superclass));
		}

		return currentMap;
	}

	/**
	 * Espone la chiamata rest per il recupero del numero totale di record in presenza di overflow
	 * 
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public abstract NroRecordTotBean getNroRecordTotali(NroRecordTotBean bean) throws Exception;

	/**
	 * Lancia la generazione asincrona della lista di oggetti visualizzati
	 * 
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	protected abstract ExportBean asyncExport(ExportBean bean) throws Exception;
	
	/**
	 * Lancia la generazione lato server della lista di oggetti visualizzati
	 * 
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	protected ExportBean serverExportSenzaPaginazione(ExportBean bean) throws Exception {		
		AdvancedCriteria criteria = bean.getCriteria();
		if(criteria != null && criteria.getCriteria() != null){		
			for(Criterion crit : criteria.getCriteria()){
				if ("nroRecordXPagina".equals(crit.getFieldName())) {
					crit.setValue(null);
				} else if ("nroPagina".equals(crit.getFieldName())) {
					crit.setValue(null);
				}
			}
		}
		PaginatorBean<T> lPaginatorBean = fetch(criteria, null, null, null);
		if(lPaginatorBean.getData() != null) {
			List<Map> listaRecords = new ArrayList<Map>();
			for (int i = 0; i < lPaginatorBean.getData().size(); i++) {
				Object currentBean = lPaginatorBean.getData().get(i);
				listaRecords.add(convertToMap(currentBean, currentBean.getClass()));			
			}			
			bean.setRecords(listaRecords.toArray(new Map[listaRecords.size()]));
		}		
		bean.setPaginazioneAttiva(false);
		return super.export(bean);
	}

	/**
	 * Gestisce la valorizzazione della variabile che indica se si è in presenza di overflow o meno. Nel datasource che estende questa classe deve essere
	 * presente in maniera separata la gestione dell'eventuale eccezione in fase di recupero dei dati
	 * 
	 * @param output
	 * @return
	 */
	protected Boolean manageOverflow(String errorMessageOut) {

		boolean overflow = false;

		if (errorMessageOut != null && !"".equals(errorMessageOut)) {

			// Se entro qua vuol dire che sono in overflow (ho troppi risultati
			// dalla store)

			GenericConfigBean config = (GenericConfigBean) SpringAppContext.getContext().getBean("GenericPropertyConfigurator");

			// Controllo se devo visualizzare il messaggio di warning
			Boolean showOverflow = config.getShowAlertMaxRecord();
			if (showOverflow) {
				addMessage(errorMessageOut, "", MessageType.WARNING);
			}
			// Sono in overflow, quindi restituisco true anche se non ho visualizzato
			// il messaggio di warning. In questo modo su BaseUI posso gestire la visualizzazione del +
			// sulla label col numero di risultati trovati
			overflow = true;
		}
		return overflow;
	}

	protected List<DestInvioNotifica> getListaSceltaOrganigrammaFilterValue(Criterion crit) {
		if (crit != null && crit.getValue() != null) {
			ArrayList lista = new ArrayList<DestInvioNotifica>();
			if (crit.getValue() instanceof Map) {
				Map map = (Map) crit.getValue();
				
				//Controllo se map è vuota o meno (Nel caso in cui la lista sia vuota map genera un nullPointerException)
				if (!map.isEmpty()) {
					for (Map val : (ArrayList<Map>) map.get("listaOrganigramma")) {
						DestInvioNotifica destInvioNotifica = new DestInvioNotifica();
						String chiave = (String) val.get("organigramma");
						destInvioNotifica.setTipo(chiave.substring(0, 2));
						destInvioNotifica.setId(chiave.substring(2));
						if (val.get("flgIncludiSottoUO") != null && ((Boolean) val.get("flgIncludiSottoUO"))) {
							destInvioNotifica.setFlgIncludiSottoUO(Flag.SETTED);
						}
						if (val.get("flgIncludiScrivanie") != null && ((Boolean) val.get("flgIncludiScrivanie"))) {
							destInvioNotifica.setFlgIncludiScrivanie(Flag.SETTED);
						}
						lista.add(destInvioNotifica);
					}
				}
			} else {
				String value = getTextFilterValue(crit);
				if (StringUtils.isNotBlank(value)) {
					StringSplitterServer st = new StringSplitterServer(value, ";");
					while (st.hasMoreTokens()) {
						DestInvioNotifica destInvioNotifica = new DestInvioNotifica();
						String chiave = st.nextToken();
						destInvioNotifica.setTipo(chiave.substring(0, 2));
						destInvioNotifica.setId(chiave.substring(2));
						lista.add(destInvioNotifica);
					}
				}
			}
			return lista;
		}
		return null;
	}

	/*
	protected static  <T> String  getColOrderBy(List<OrderByBean> orderByBeanIn, Class<T> lBeanRifIn, String listaNumColonneOrdinabiliIn) throws Exception {
		 String colOrderByOut = "";
		 String[] listaNumColonneOrdinabili = null;
		 
		 // Leggo i campi del bean di riferimento
	     Field[] lFieldsBeanRif = ReflectionUtility.getFields(lBeanRifIn);
	     
	     if(listaNumColonneOrdinabiliIn!=null && !listaNumColonneOrdinabiliIn.equalsIgnoreCase("")){
	    	 listaNumColonneOrdinabili = listaNumColonneOrdinabiliIn.split(";");
	     }
	     // Cerco tutti i nomi delle colonne ordinate nel bean di riferimento e individuo il rispettovo numero di colonna
	     if (orderByBeanIn != null && orderByBeanIn.size()>0) {
	    	 for (OrderByBean lOrderByBean : orderByBeanIn) {
					if (lOrderByBean != null) {
						String columnName = lOrderByBean.getColumnname();
						if(columnName!=null && !columnName.equalsIgnoreCase("")){
							int index;
							// Cerco il nome della colonna ordinata nel bean di riferimento
							for (Field lFieldRif : lFieldsBeanRif) {				        
							        NumeroColonna lNumeroColonna = (NumeroColonna)lFieldRif.getAnnotation(NumeroColonna.class);
						            if (lNumeroColonna != null) {		
									   // Prendo il nome della colonna del bean di riferimento 
									   String nomeColonnaBeanRif = lFieldRif.getName();						   
									   // Se il nome della colonna corrisponde con quella della colonna ordinata prendo il NR della colonna
									   if (columnName.equalsIgnoreCase(nomeColonnaBeanRif)){
											// Se il numero colonna e' nella lista dei numeri colonne ordinabili dalla store							            	
							            	if(cercaValore(lNumeroColonna.numero(), listaNumColonneOrdinabili)){
							            		colOrderByOut = colOrderByOut + lNumeroColonna.numero() + ";";
							            	}
										}
									}
							}
						}
					}
	    	 	}	
	    	 }
		return colOrderByOut;
	}
	
	public static  <T> String  getDescOrderBy(List<OrderByBean> orderByBeanIn, Class<T> lBeanRifIn, String listaNumColonneOrdinabiliIn) throws Exception {
		 String flgDescOrderByOut = "";
		 String[] listaNumColonneOrdinabili = null;
		 
		 // Leggo i campi del bean di riferimento
	     Field[] lFieldsBeanRif = ReflectionUtility.getFields(lBeanRifIn);
	     
	     if(listaNumColonneOrdinabiliIn!=null && !listaNumColonneOrdinabiliIn.equalsIgnoreCase("")){
	    	 listaNumColonneOrdinabili = listaNumColonneOrdinabiliIn.split(";");
	     }
	     
	     // Cerco tutti i nomi delle colonne ordinate nel bean di riferimento e individuo il rispettovo numero di colonna
	     if (orderByBeanIn != null && orderByBeanIn.size()>0) {
	    	 
	    	 for (OrderByBean lOrderByBean : orderByBeanIn) {
					if (lOrderByBean != null) {
						String columnName = lOrderByBean.getColumnname();
						if(columnName!=null && !columnName.equalsIgnoreCase("")){
							int index;
							// Cerco il nome della colonna ordinata nel bean di riferimento
							for (Field lFieldRif : lFieldsBeanRif) {				        
							        NumeroColonna lNumeroColonna = (NumeroColonna)lFieldRif.getAnnotation(NumeroColonna.class);
						            if (lNumeroColonna != null) {		
									   // Prendo il nome della colonna del bean di riferimento 
									   String nomeColonnaBeanRif = lFieldRif.getName();						   
									   // Se il nome della colonna corrisponde con quella della colonna ordinata prendo il NR della colonna
									   if (columnName.equalsIgnoreCase(nomeColonnaBeanRif)){
											// Se il numero colonna e' nella lista dei numeri colonne ordinabili dalla store							            	
							            	if(cercaValore(lNumeroColonna.numero(), listaNumColonneOrdinabili)){
							            		Direction direction = lOrderByBean.getDirection();
							            		
							            		if (direction!=null){
													if(direction == direction.ASC){
														flgDescOrderByOut = flgDescOrderByOut + "0;";
													}
													else if (direction == direction.DESC){
														flgDescOrderByOut = flgDescOrderByOut + "1;";
													}	
												}								      
							            	}
										}
									}
							}
						}
					}
	    	 	}	
	    	 }
		return flgDescOrderByOut;
	}
	
	private static boolean cercaValore(String valoreDaCercare, String [] arrayValori) {
	    
		for(int i = 0; i<arrayValori.length; i++) 
	    {
			if (arrayValori[i].equals(valoreDaCercare)){
				return true;
			}
		}
	    return false;
	}
	*/

}
