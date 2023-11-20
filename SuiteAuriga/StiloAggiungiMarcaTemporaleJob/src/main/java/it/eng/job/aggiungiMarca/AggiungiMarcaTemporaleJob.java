/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.lang.invoke.MethodHandles;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.scheduling.concurrent.ThreadPoolExecutorFactoryBean;

import it.eng.job.AurigaSpringContext;
import it.eng.job.SpringHelper;
import it.eng.job.aggiungiMarca.bean.ConfigurazioniProcessiAggiuntaMarca;
import it.eng.job.aggiungiMarca.bean.ConfigurazioniProcessoAggiuntaMarca;
import it.eng.job.aggiungiMarca.bean.ConfigurazioniSchema;
import it.eng.job.aggiungiMarca.bean.HandlerResultBean;
import it.eng.job.aggiungiMarca.constants.ErrorInfoEnum;
import it.eng.job.aggiungiMarca.dao.HibernateUtil;
import it.eng.job.aggiungiMarca.exceptions.AggiungiMarcaTemporaleException;
import it.eng.job.aggiungiMarca.manager.ElaborazioneAggiuntaMarcaManager;
import it.eng.job.aggiungiMarca.manager.impl.ElaborazioneAggiuntaMarcaManagerImpl;
import it.eng.utility.jobmanager.quartz.AbstractJob;
import it.eng.utility.jobmanager.quartz.Job;

import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.beans.SpecializzazioneBean;
import it.eng.core.business.ReflectionUtil;

import javax.inject.Named;

/**
 * Aggiunge la marca temporale a files precedentemente firmati
 * 
 *
 */
@Job(type = "AggiungiMarcaTemporaleJob")
@Named
public class AggiungiMarcaTemporaleJob extends AbstractJob<String> {

	private static final String ROUTINGKEY = "ROUTINGKEY";

	private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
	
	private static final String JOB_CLASS_NAME = AggiungiMarcaTemporaleJob.class.getName();
	
	private Map<String, ConfigurazioniProcessoAggiuntaMarca> mappaConfigurazioniProcessiAggiuntaMarca = new HashMap<String, ConfigurazioniProcessoAggiuntaMarca>();
	
	private static final String JOBATTRKEY_SCHEMA = "schema";
	private static final String JOBATTRKEY_LOCALE = "locale";
	private static final String JOBATTRKEY_TIPO_DOMINIO = "tipoDominio";
	private static final String JOBATTRKEY_TOKEN = "token";
	private static final String JOBATTRKEY_ENTITY_PACKAGE = "entityPackage";
	private static final String JOBATTRKEY_HIBERNATE_CONFIG_FILE = "hibernateConfigFile";
	
	private String schema;
	private String entityPackage;
	private String hibernateConfigFile;
	private Locale locale;
	private AurigaLoginBean aurigaLoginBean;
	
	@Override
	protected List<String> load() {
		ArrayList<String> ret = new ArrayList<>();
		ret.add(JOB_CLASS_NAME);

		return ret;
	}
	
	@Override
	protected void execute(String config) {

		logger.info("In esecuzione l'istanza {}", getFireInstanceId());

		try {
			logger.debug("--- Configurazione job ---- ");
			configuraJob();
			logger.debug("--- Fine configurazione job ---- ");
			
			// Inizio l'elaborazione solo se le configurazioni sono valide
			logger.debug("--- Elaborazione ---- ");
			elabora();
			logger.debug("--- Fine Elaborazione ---- ");
			
		} catch (Exception e) {
			logger.error(ErrorInfoEnum.ERRORE_ELABORAZIONE_JOB.getDescription(), e);
		}
	}

	/**
	 * Configurazione comuni aurigajob
	 * 
	 * @return
	 * @throws ImportDatiDepositoException
	 */

	private void configuraJob() throws AggiungiMarcaTemporaleException {
		
		ConfigurazioniSchema configSchema = SpringHelper.getSpecializedBean(AurigaSpringContext.SPRINGBEAN_CONF_SCHEMA, null,
				ConfigurazioniSchema.class);
		
		// Schema di connessione al database
		logger.debug("--- Configurazione schema ---- ");
		configurazioneSchema(configSchema);
		
		// locale
		logger.debug("--- Configurazione lingua locale ---- ");
		String localeValue = configurazioneLinguaLocale(configSchema);

		// Specializzazione bean
		logger.debug("--- Configurazione specializzazioneBean ---- ");
		SpecializzazioneBean specializzazioneBean = configurazioneSpecializzazioneBean(configSchema);

		// Bean per utilizzare i servizi di AurigaBusiness
		logger.debug("--- Configurazione aurigaLoginBean ---- ");
		configurazioneAurigaLoginBean(localeValue, specializzazioneBean);
		
		logger.debug("--- Configurazione db ---- ");
		configurazioneDb();
				
		ConfigurazioniProcessiAggiuntaMarca configProcessiAggiuntaMarca = SpringHelper.getSpecializedBean(AurigaSpringContext.SPRINGBEAN_CONF_PROCESSI_AGGIUNTAMARCA, null,
				ConfigurazioniProcessiAggiuntaMarca.class);
		
		if (configProcessiAggiuntaMarca== null || configProcessiAggiuntaMarca.getMappaConfigurazioni()== null
				|| configProcessiAggiuntaMarca.getMappaConfigurazioni().isEmpty()) {
			throw new AggiungiMarcaTemporaleException(ErrorInfoEnum.VALIDAZIONE_CONFIGURAZIONI_JOB);
		}
		
		for (Map.Entry<String, ConfigurazioniProcessoAggiuntaMarca> entryConfigurazioniAggiuntaMarca : configProcessiAggiuntaMarca.getMappaConfigurazioni().entrySet()) {
			final String idConfigurazione = entryConfigurazioniAggiuntaMarca.getKey();
			final ConfigurazioniProcessoAggiuntaMarca configurazione = entryConfigurazioniAggiuntaMarca.getValue();
			
			this.mappaConfigurazioniProcessiAggiuntaMarca.put(idConfigurazione, configurazione);
			
		}

	}

	
	
	private void elabora() {

		logger.debug("--- Metodo elabora ---- ");
		logger.debug("--- Recupero executorService ---- ");
		ExecutorService executorService = SpringHelper.getSpecializedBean(AurigaSpringContext.SPRINGBEAN_EXECUTOR_SERVICE, null, ExecutorService.class);
		if(executorService!=null){
			logger.debug("---  executorService ---- " + executorService);
			if(executorService instanceof ThreadPoolExecutorFactoryBean ){
				ThreadPoolExecutorFactoryBean tefb = ((ThreadPoolExecutorFactoryBean)executorService);
				logger.debug("---  tefb ---- " + tefb);
			}
		}
		
		ForkJoinPool forkJoinPool = null;
		try {
			forkJoinPool = SpringHelper.getSpecializedBean(AurigaSpringContext.SPRINGBEAN_FORK_JOIN_POOL, null, ForkJoinPool.class);
		} catch (Exception e) {
			logger.warn("Eccezione nel recupero del bean {}", AurigaSpringContext.SPRINGBEAN_FORK_JOIN_POOL);
		}

		if (forkJoinPool == null) {
			// si usa il pool comune senza istanziarne uno dedicato
			//forkJoinPool = ForkJoinPool.commonPool();
			forkJoinPool = new ForkJoinPool(1);
			logger.debug("---  forkJoinPool ---- " + forkJoinPool);
		}
		
		for (Map.Entry<String, ConfigurazioniProcessoAggiuntaMarca> entryConfigurazioniProcessoAggiuntaMarca : this.mappaConfigurazioniProcessiAggiuntaMarca.entrySet()) {
			final String idProcessoElaborazioneAggiuntaMarca = entryConfigurazioniProcessoAggiuntaMarca.getKey();
			logger.debug("idProcessoElaborazioneAggiuntaMarca " + idProcessoElaborazioneAggiuntaMarca);
			final ConfigurazioniProcessoAggiuntaMarca configurazioneProcessoElaborazioneAggiuntaMarca = entryConfigurazioniProcessoAggiuntaMarca.getValue();
			try {
				// ogni elaborazione viene effettuata in parallelo
				elaboraAggiuntaMarca(executorService, forkJoinPool, idProcessoElaborazioneAggiuntaMarca, configurazioneProcessoElaborazioneAggiuntaMarca);
			} catch (AggiungiMarcaTemporaleException e) {
				logger.error("Errore processo riapertura ", e);
			} finally {
				
			}
		}
			
	}


	private void elaboraAggiuntaMarca(ExecutorService executorService, ForkJoinPool forkJoinPool,  final String idProcesso, ConfigurazioniProcessoAggiuntaMarca configurazioneProcessoElaborazioneAggiuntaMarca) throws AggiungiMarcaTemporaleException {

		ElaborazioneAggiuntaMarcaManager manager = new ElaborazioneAggiuntaMarcaManagerImpl(forkJoinPool, configurazioneProcessoElaborazioneAggiuntaMarca, aurigaLoginBean, locale);
		logger.info("Iniziata elaborazione avente id {}", idProcesso);
		ThreadContext.put(ROUTINGKEY, "elaborazione_" + idProcesso);
		Long startTime = System.currentTimeMillis();
		
		Future<HandlerResultBean<Void>> futureElaborazione = executorService.submit((ElaborazioneAggiuntaMarcaManagerImpl) manager);
		try {
			// recupero risultato 
			HandlerResultBean<Void> resultElaborazione = futureElaborazione.get();
			logger.debug("resultElaborazione.isSuccessful() "+ resultElaborazione.isSuccessful());
			if (resultElaborazione!=null && !resultElaborazione.isSuccessful()) {
				logger.error("Elaborazione in errore");
			}
			if (resultElaborazione!=null && resultElaborazione.getMessage()!=null ) {
				logger.info("Message: " + resultElaborazione.getMessage());
			}
			Long stopTime = System.currentTimeMillis();
			logger.debug("Tempo impiegato elaborazione avente id {}: {}", idProcesso, (stopTime - startTime));

		} catch (InterruptedException | ExecutionException e) {
			logger.error("Errore acquisizione file ",  e);
		} finally {
			ThreadContext.remove(ROUTINGKEY);
		}
	}
	
	
	private void configurazioneSchema(ConfigurazioniSchema configSchema) throws AggiungiMarcaTemporaleException {

		schema = configSchema.getSchema();//(String) getAttribute(JOBATTRKEY_SCHEMA);
		logger.debug("schema: " + schema);
		if (StringUtils.isBlank(schema)) {
			logger.error(ErrorInfoEnum.VALIDAZIONE_CONFIGURAZIONE_SCHEMA.getDescription());
			throw new AggiungiMarcaTemporaleException(ErrorInfoEnum.VALIDAZIONE_CONFIGURAZIONE_SCHEMA);
		}
	}
	
	private void configurazioneDb() throws AggiungiMarcaTemporaleException {

		entityPackage = (String) getAttribute(JOBATTRKEY_ENTITY_PACKAGE);
		logger.debug("entityPackage: " + entityPackage);
		if (StringUtils.isBlank(entityPackage)) {
			logger.error(ErrorInfoEnum.VALIDAZIONE_CONFIGURAZIONE_DB.getDescription());
			throw new AggiungiMarcaTemporaleException(ErrorInfoEnum.VALIDAZIONE_CONFIGURAZIONE_SCHEMA);
		}
		
		hibernateConfigFile = (String) getAttribute(JOBATTRKEY_HIBERNATE_CONFIG_FILE);
		logger.debug("hibernateConfigFile: " + hibernateConfigFile);
		if (StringUtils.isBlank(hibernateConfigFile)) {
			logger.error(ErrorInfoEnum.VALIDAZIONE_CONFIGURAZIONE_DB.getDescription());
			throw new AggiungiMarcaTemporaleException(ErrorInfoEnum.VALIDAZIONE_CONFIGURAZIONE_SCHEMA);
		}
		
		HibernateUtil.setEntitypackage( entityPackage );
		HibernateUtil.buildSessionFactory( hibernateConfigFile, AggiungiMarcaTemporaleJob.class.getName() );
		
		org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration();
		configuration.configure("hibernate.cfg.xml");

		it.eng.core.business.HibernateUtil.setEntitypackage(entityPackage);
		ReflectionUtil.setEntityPackage(entityPackage);

		it.eng.core.business.HibernateUtil.addSessionFactory("OWNER_1", configuration);
		it.eng.core.business.HibernateUtil.addEnte("OWNER_1", "OWNER_1");

	}
	
	

	private String configurazioneLinguaLocale(ConfigurazioniSchema configSchema) throws AggiungiMarcaTemporaleException {

		// Lingua e paese utilizzati
		String localeValue = configSchema.getLocale();//(String) getAttribute(JOBATTRKEY_LOCALE);
		if (StringUtils.isBlank(localeValue)) {
			logger.error(ErrorInfoEnum.VALIDAZIONE_CONFIGURAZIONE_LINGUA_LOCALE.getDescription());
			throw new AggiungiMarcaTemporaleException(ErrorInfoEnum.VALIDAZIONE_CONFIGURAZIONE_LINGUA_LOCALE);
		}
		this.locale = new Locale(localeValue);
		return localeValue;
	}
	
	private SpecializzazioneBean configurazioneSpecializzazioneBean(ConfigurazioniSchema configSchema) throws AggiungiMarcaTemporaleException {

		SpecializzazioneBean specializzazioneBean = new SpecializzazioneBean();
		// Tipo dominio applicativo
		String tipoDominioValue = configSchema.getIdDominio();//(String) getAttribute(JOBATTRKEY_TIPO_DOMINIO);
		logger.debug("tipoDominio: " + tipoDominioValue);
		if (StringUtils.isNotBlank(tipoDominioValue)) {
			Integer tipoDominio = Integer.valueOf(tipoDominioValue);
			specializzazioneBean.setTipoDominio(tipoDominio);
		} else {
			logger.error(ErrorInfoEnum.VALIDAZIONE_CONFIGURAZIONE_TIPO_DOMINIO.getDescription());
			throw new AggiungiMarcaTemporaleException(ErrorInfoEnum.VALIDAZIONE_CONFIGURAZIONE_TIPO_DOMINIO);
		}

		String token = "#"+configSchema.getToken();//(String) getAttribute(JOBATTRKEY_TOKEN);
		logger.debug("token: " + token);
		if (StringUtils.isNotBlank(token)) {
			specializzazioneBean.setCodIdConnectionToken(token);
		} else {
			logger.error(ErrorInfoEnum.VALIDAZIONE_CONFIGURAZIONE_TOKEN.getDescription());
			throw new AggiungiMarcaTemporaleException(ErrorInfoEnum.VALIDAZIONE_CONFIGURAZIONE_TOKEN);
		}

		return specializzazioneBean;
	}
	
	private void configurazioneAurigaLoginBean(String localeValue, SpecializzazioneBean specializzazioneBean) {

		aurigaLoginBean = SpringHelper.getSpecializedBean(AurigaSpringContext.SPRINGBEAN_AURIGA_LOGIN, null, AurigaLoginBean.class);
		aurigaLoginBean.setLinguaApplicazione(localeValue);
		aurigaLoginBean.setSchema(schema);
		aurigaLoginBean.setToken(specializzazioneBean.getCodIdConnectionToken());
		aurigaLoginBean.setSpecializzazioneBean(specializzazioneBean);
	}

	// Fine recupero lista job
	@Override
	protected void end(String config) {
		logger.info("Elaborazione completata");
	}

}
