/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.lang.invoke.MethodHandles;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

import javax.inject.Named;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.hibernate.cfg.NotYetImplementedException;

import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.module.business.beans.SpecializzazioneBean;
import it.eng.constants.AurigaSpringContext;
import it.eng.job.JobDebugConfig;
import it.eng.job.SpringHelper;
import it.eng.job.importdocindex.bean.ConfigurazioniArchiviazione;
import it.eng.job.importdocindex.bean.ConfigurazioniDirectoryScansione;
import it.eng.job.importdocindex.bean.ConfigurazioniFileIndice;
import it.eng.job.importdocindex.bean.ConfigurazioniScansione;
import it.eng.job.importdocindex.bean.ConfigurazioniScansioni;
import it.eng.job.importdocindex.bean.HandlerResultBean;
import it.eng.job.importdocindex.bean.VincoliFileScansione;
import it.eng.job.importdocindex.constants.ErrorInfoEnum;
import it.eng.job.importdocindex.exceptions.ImportDocIndexException;
import it.eng.job.importdocindex.manager.FileIndiceManager;
import it.eng.job.importdocindex.manager.ScansioneDirectoryManager;
import it.eng.job.importdocindex.manager.impl.FileIndiceManagerImpl;
import it.eng.job.importdocindex.manager.impl.ScansioneDirectoryManagerImpl;
import it.eng.utility.filemanager.FileBean;
import it.eng.utility.filemanager.FileManager;
import it.eng.utility.filemanager.FileManagerException;
import it.eng.utility.filemanager.FileManagerFactory;
import it.eng.utility.filemanager.FileManagerUtil;
import it.eng.utility.jobmanager.quartz.AbstractJob;
import it.eng.utility.jobmanager.quartz.Job;

/**
 * Elabora gli indici scansionati nelle directory configurate
 * 
 * @author mzanetti
 *
 */
@Job(type = "ImportDocIndexJob")
@Named
public class ImportDocIndexJob extends AbstractJob<String> {

	private static final String ROUTINGKEY = "ROUTINGKEY";

	private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	private String schema;
	private AurigaLoginBean aurigaLoginBean;
	private Map<String, ConfigurazioniScansione> mappaConfigurazioniScansioneValide = new HashMap<>();
	private Locale locale;

	private static final String JOBATTRKEY_SCHEMA = "schema";
	private static final String JOBATTRKEY_TIPO_DOMINIO = "tipoDominio";
	private static final String JOBATTRKEY_LOCALE = "locale";
	private static final String JOBATTRKEY_TOKEN = "token";

	private static final String JOB_CLASS_NAME = ImportDocIndexJob.class.getName();

	@Override
	protected List<String> load() {
		ArrayList<String> ret = new ArrayList<>();
		ret.add(JOB_CLASS_NAME);

		return ret;
	}

	@Override
	protected void execute(String config) {

		logger.debug("In esecuzione l'istanza {}", getFireInstanceId());

		try {
			configurazioneJob();
			// Inizio l'elaborazione solo se le configurazioni sono valide
			elabora();
		} catch (Exception e) {
			logger.error(ErrorInfoEnum.ERRORE_ELABORAZIONE_JOB.getDescription(), e);
		}

	}

	/**
	 * Configurazione comuni aurigajob
	 * 
	 * @return
	 * @throws ImportDocIndexException
	 */

	private void configurazioneJob() throws ImportDocIndexException {

		try {

			// Schema di connessione al database
			configurazioneSchema();

			// locale
			String localeValue = configurazioneLinguaLocale();

			// Specializzazione bean
			SpecializzazioneBean specializzazioneBean = configurazioneSpecializzazioneBean();

			// Bean per utilizzare i servizi di AurigaBusiness
			configurazioneAurigaLoginBean(localeValue, specializzazioneBean);

			// Verifica configurazioni scansione
			configurazioniScansione();

			// verifica che i path principali non collidano
			// si tiene conto anche della ricorsione, ma senza verificare il livello
			// alla prima collisione si interrompe
			verificaCollisioniDirectoryPrincipale();

			// verifica che i path di lavoro non collidano per evitare problemi sui file/directory creati
			verificaCollisioniDirectoryLavoro();

		} catch (Exception e) {
			logger.error(ErrorInfoEnum.VALIDAZIONE_CONFIGURAZIONI_JOB.getDescription(), e);
		}

	}

	/**
	 * Verifica delle directory principali da scandire per evitare collisioni sui file
	 * 
	 * @throws ImportDocIndexException
	 */

	private void verificaCollisioniDirectoryPrincipale() throws ImportDocIndexException {

		if (this.mappaConfigurazioniScansioneValide == null || this.mappaConfigurazioniScansioneValide.isEmpty()) {
			throw new ImportDocIndexException(ErrorInfoEnum.VALIDAZIONE_CONFIGURAZIONI_SCANSIONE_INVALIDE);
		}

		if (this.mappaConfigurazioniScansioneValide.size() > 1) {

			for (Map.Entry<String, ConfigurazioniScansione> primaEntryConfigurazioniScansione : this.mappaConfigurazioniScansioneValide.entrySet()) {
				verificaCollisioniDirectoryPrincipale(primaEntryConfigurazioniScansione);
			}
		}
	}

	/**
	 * @param primaEntryConfigurazioniScansione
	 * @throws ImportDocIndexException
	 */
	private void verificaCollisioniDirectoryPrincipale(Map.Entry<String, ConfigurazioniScansione> primaEntryConfigurazioniScansione)
			throws ImportDocIndexException {
		Path primoPath = Paths.get(primaEntryConfigurazioniScansione.getValue().getConfigurazioniDirectoryScansione().getDirectoryRadice()).toAbsolutePath();
		Boolean ricorsionePrimoPath = primaEntryConfigurazioniScansione.getValue().getConfigurazioniDirectoryScansione().getAbilitaScansioneSottoDirectory();
		for (Map.Entry<String, ConfigurazioniScansione> secondaEntryConfigurazioniScansione : this.mappaConfigurazioniScansioneValide.entrySet()) {
			if (primaEntryConfigurazioniScansione != secondaEntryConfigurazioniScansione) {
				Path secondoPath = Paths.get(secondaEntryConfigurazioniScansione.getValue().getConfigurazioniDirectoryScansione().getDirectoryRadice())
						.toAbsolutePath();
				Boolean ricorsioneSecondoPath = secondaEntryConfigurazioniScansione.getValue().getConfigurazioniDirectoryScansione()
						.getAbilitaScansioneSottoDirectory();
				if (primoPath.equals(secondoPath) || (primoPath.startsWith(secondoPath) && ricorsioneSecondoPath)
						|| (secondoPath.startsWith(primoPath) && ricorsionePrimoPath)) {
					logger.error("Le directory {} e {} potrebbero collidere - interruzione dell'esecuzione del job", primoPath, secondoPath);
					throw new ImportDocIndexException(ErrorInfoEnum.VALIDAZIONE_CONCORRENZA_DIRECTORY.getDescription(),
							ErrorInfoEnum.VALIDAZIONE_CONCORRENZA_DIRECTORY);
				}
			}

		}
	}

	/**
	 * Verifica che le directory di lavoro, backup, errori e elaborati siano univoche per evitare collisioni sui file
	 * 
	 * @throws ImportDocIndexException
	 * 
	 */

	private void verificaCollisioniDirectoryLavoro() throws ImportDocIndexException {

		if (this.mappaConfigurazioniScansioneValide.size() > 1) {
			for (Map.Entry<String, ConfigurazioniScansione> entryConfigurazioniScansione1 : this.mappaConfigurazioniScansioneValide.entrySet()) {
				for (Map.Entry<String, ConfigurazioniScansione> entryConfigurazioniScansione2 : this.mappaConfigurazioniScansioneValide.entrySet()) {
					if (entryConfigurazioniScansione1 != entryConfigurazioniScansione2) {
						final String directoryBackup1 = entryConfigurazioniScansione1.getValue().getConfigurazioniDirectoryScansione().getDirectoryBackup();
						final String directoryBackup2 = entryConfigurazioniScansione2.getValue().getConfigurazioniDirectoryScansione().getDirectoryBackup();
						verificaDirectoryLavoroDuplicate(directoryBackup1, directoryBackup2);
						final String directoryLavoro1 = entryConfigurazioniScansione1.getValue().getConfigurazioniDirectoryScansione().getDirectoryLavoro();
						final String directoryLavoro2 = entryConfigurazioniScansione2.getValue().getConfigurazioniDirectoryScansione().getDirectoryLavoro();
						verificaDirectoryLavoroDuplicate(directoryLavoro1, directoryLavoro2);
						final String directoryElaborati1 = entryConfigurazioniScansione1.getValue().getConfigurazioniDirectoryScansione()
								.getDirectoryElaborati();
						final String directoryElaborati2 = entryConfigurazioniScansione2.getValue().getConfigurazioniDirectoryScansione()
								.getDirectoryElaborati();
						verificaDirectoryLavoroDuplicate(directoryElaborati1, directoryElaborati2);
						final String directoryErrori1 = entryConfigurazioniScansione1.getValue().getConfigurazioniDirectoryScansione().getDirectoryErrori();
						final String directoryErrori2 = entryConfigurazioniScansione2.getValue().getConfigurazioniDirectoryScansione().getDirectoryErrori();
						verificaDirectoryLavoroDuplicate(directoryErrori1, directoryErrori2);
					}
				}
			}
		}
	}

	/**
	 * Verifica se i file indice ricavati dalla scansione sono univoci (a livello di path assoluto) <br>
	 * Non si verifica se i file sono duplicati a livello di contenuto, perchè potrebbe aver senso caricare più volte lo stesso file
	 * 
	 * @throws ImportDocIndexException
	 */

	private void verificaDuplicazioniFileIndiceScansione(List<File> listaFileIndice) throws ImportDocIndexException {
		for (int indice1 = 0; indice1 < listaFileIndice.size(); indice1++) {
			for (int indice2 = 0; indice2 < listaFileIndice.size(); indice2++) {
				if (indice1 != indice2) {
					Path path1 = listaFileIndice.get(indice1).toPath();
					Path path2 = listaFileIndice.get(indice2).toPath();
					if (path1.equals(path2)) {
						throw new ImportDocIndexException(ErrorInfoEnum.VALIDAZIONE_CONFIGURAZIONE_DIRECTORY_DUPLICATA);
					}
				}
			}
		}
	}

	/**
	 * Verifica l'uguaglianza dei path assoluti
	 * 
	 * @param directory1
	 * @param directory2
	 * @throws ImportDocIndexException
	 */
	private void verificaDirectoryLavoroDuplicate(final String directory1, final String directory2) throws ImportDocIndexException {
		if (StringUtils.isNotBlank(directory1) && StringUtils.isNotBlank(directory2)) {
			Path path1 = Paths.get(directory1).toAbsolutePath();
			Path path2 = Paths.get(directory2).toAbsolutePath();
			if (path1.equals(path2)) {
				throw new ImportDocIndexException(ErrorInfoEnum.VALIDAZIONE_CONFIGURAZIONE_DIRECTORY_DUPLICATA);
			}
		}
	}

	/**
	 * Verifica delle configurazioni della scansione: si verificano tutte le configurazioni della mappa e si mantengono solo quelle valide in
	 * {@link mappaConfigurazioniScansioneValide}
	 * 
	 * @throws ImportDocIndexException
	 */

	private void configurazioniScansione() throws ImportDocIndexException {

		try {
			ConfigurazioniScansioni configScansioni = SpringHelper.getSpecializedBean(AurigaSpringContext.SBRINGBEAN_CONFIG_SCANSIONI, null,
					ConfigurazioniScansioni.class);

			if (configScansioni == null || configScansioni.getMappaConfigurazioniScansione() == null
					|| configScansioni.getMappaConfigurazioniScansione().isEmpty()) {
				throw new ImportDocIndexException(ErrorInfoEnum.VALIDAZIONE_CONFIGURAZIONI_SCANSIONE_INVALIDE);
			}
			for (Map.Entry<String, ConfigurazioniScansione> entryConfigurazioniScansione : configScansioni.getMappaConfigurazioniScansione().entrySet()) {
				final String idConfigurazione = entryConfigurazioniScansione.getKey();
				final ConfigurazioniScansione configurazione = entryConfigurazioniScansione.getValue();
				verificaConfigurazioniScansione(idConfigurazione, configurazione);
			}
		} catch (Exception e) {
			logger.error(ErrorInfoEnum.VALIDAZIONE_CONFIGURAZIONE_SCANSIONE_INDICI.getDescription(), e);
			throw new ImportDocIndexException(e, ErrorInfoEnum.VALIDAZIONE_CONFIGURAZIONE_SCANSIONE_INDICI);
		}
	}

	/**
	 * @param idConfigurazione
	 * @param configurazione
	 */
	private void verificaConfigurazioniScansione(final String idConfigurazione, final ConfigurazioniScansione configurazione) {
		try {
			HandlerResultBean<Boolean> resultVerificaConfigurazioni = verificaConfigurazioniScansione(configurazione);
			if (!resultVerificaConfigurazioni.isSuccessful()) {
				throw new ImportDocIndexException(ErrorInfoEnum.VALIDAZIONE_CONFIGURAZIONE_SCANSIONE_INDICI);
			}
			this.mappaConfigurazioniScansioneValide.put(idConfigurazione, configurazione);
		} catch (ImportDocIndexException e) {
			// considero solo le configurazioni valide
			logger.warn("Errore nella verifica della configurazione avente id {} - escludo configurazione", idConfigurazione, e);
		}
	}

	/**
	 * Configurazione AurigaLoginBean
	 * 
	 * @param localeValue
	 * @param specializzazioneBean
	 */

	private void configurazioneAurigaLoginBean(String localeValue, SpecializzazioneBean specializzazioneBean) {

		aurigaLoginBean = SpringHelper.getSpecializedBean(AurigaSpringContext.SPRINGBEAN_AURIGA_LOGIN_BEAN, null, AurigaLoginBean.class);
		aurigaLoginBean.setLinguaApplicazione(localeValue);
		aurigaLoginBean.setSchema(schema);
		aurigaLoginBean.setToken(specializzazioneBean.getCodIdConnectionToken());
		aurigaLoginBean.setSpecializzazioneBean(specializzazioneBean);
	}

	/**
	 * Configurazione SpecializzazioneBean
	 * 
	 * @return
	 * @throws ImportDocIndexException
	 */

	private SpecializzazioneBean configurazioneSpecializzazioneBean() throws ImportDocIndexException {

		SpecializzazioneBean specializzazioneBean = new SpecializzazioneBean();
		// Tipo dominio applicativo
		String tipoDominioValue = (String) getAttribute(JOBATTRKEY_TIPO_DOMINIO);
		if (StringUtils.isNotBlank(tipoDominioValue)) {
			Integer tipoDominio = Integer.valueOf(tipoDominioValue);
			specializzazioneBean.setTipoDominio(tipoDominio);
		} else {
			logger.error(ErrorInfoEnum.VALIDAZIONE_CONFIGURAZIONE_TIPO_DOMINIO.getDescription());
			throw new ImportDocIndexException(ErrorInfoEnum.VALIDAZIONE_CONFIGURAZIONE_TIPO_DOMINIO);
		}

		String token = (String) getAttribute(JOBATTRKEY_TOKEN);
		if (StringUtils.isNotBlank(token)) {
			specializzazioneBean.setCodIdConnectionToken(token);
		} else {
			logger.error(ErrorInfoEnum.VALIDAZIONE_CONFIGURAZIONE_TOKEN.getDescription());
			throw new ImportDocIndexException(ErrorInfoEnum.VALIDAZIONE_CONFIGURAZIONE_TOKEN);
		}

		return specializzazioneBean;
	}

	/**
	 * Configurazione schema
	 * 
	 * @throws ImportDocIndexException
	 */

	private void configurazioneSchema() throws ImportDocIndexException {

		schema = (String) getAttribute(JOBATTRKEY_SCHEMA);
		if (StringUtils.isBlank(schema)) {
			logger.error(ErrorInfoEnum.VALIDAZIONE_CONFIGURAZIONE_SCHEMA.getDescription());
			throw new ImportDocIndexException(ErrorInfoEnum.VALIDAZIONE_CONFIGURAZIONE_SCHEMA);
		}
	}

	/**
	 * Configurazioni lingua locale
	 * 
	 * @return
	 * @throws ImportDocIndexException
	 */

	private String configurazioneLinguaLocale() throws ImportDocIndexException {

		// Lingua e paese utilizzati
		String localeValue = (String) getAttribute(JOBATTRKEY_LOCALE);
		if (StringUtils.isBlank(localeValue)) {
			logger.error(ErrorInfoEnum.VALIDAZIONE_CONFIGURAZIONE_LINGUA_LOCALE.getDescription());
			throw new ImportDocIndexException(ErrorInfoEnum.VALIDAZIONE_CONFIGURAZIONE_LINGUA_LOCALE);
		}
		this.locale = new Locale(localeValue);
		return localeValue;
	}

	private void elabora() {

		ExecutorService executorService = SpringHelper.getSpecializedBean(AurigaSpringContext.SPRINGBEAN_EXECUTOR_SERVICE, null, ExecutorService.class);

		ForkJoinPool forkJoinPool = null;
		try {
			forkJoinPool = SpringHelper.getSpecializedBean(AurigaSpringContext.SPRINGBEAN_FORK_JOIN_POOL, null, ForkJoinPool.class);
		} catch (Exception e) {
			logger.warn("Eccezione nel recupero del bean {}", AurigaSpringContext.SPRINGBEAN_FORK_JOIN_POOL);
		}

		if (forkJoinPool == null) {
			// si usa il pool comune senza istanziarne uno dedicato
			forkJoinPool = ForkJoinPool.commonPool();
		}

		for (Map.Entry<String, ConfigurazioniScansione> entryConfigurazioniScansione : this.mappaConfigurazioniScansioneValide.entrySet()) {
			final String idScansione = entryConfigurazioniScansione.getKey();
			final ConfigurazioniScansione configurazioneScansione = entryConfigurazioniScansione.getValue();
			try {
				// ogni scansione viene effettuata in parallelo
				scansione(executorService, forkJoinPool, idScansione, configurazioneScansione);
			} catch (ImportDocIndexException e) {
				logger.error("Errore scansione avente id {}", idScansione, e);
			} finally {
				try {
					puliziaDirectoryLavoro(configurazioneScansione);
				} catch (ImportDocIndexException e) {
					logger.error("Errore pulizia directory lavoro", e);
				}
			}
		}

	}

	/**
	 * Scansione dei file indice
	 * 
	 * @param executorService
	 * @param idScansione
	 * @param configurazioneScansione
	 * @throws ImportDocIndexException
	 */

	@SuppressWarnings("unchecked")
	private void scansione(ExecutorService executorService, ForkJoinPool forkJoinPool, final String idScansione,
			final ConfigurazioniScansione configurazioneScansione) throws ImportDocIndexException {

		ScansioneDirectoryManager manager = new ScansioneDirectoryManagerImpl(configurazioneScansione, forkJoinPool);
		logger.info("Iniziata scansione avente id {}", idScansione);
		ThreadContext.put(ROUTINGKEY, "scansione_" + idScansione);
		Long startTime = System.currentTimeMillis();
		Future<HandlerResultBean<Void>> futureScansione = executorService.submit((ScansioneDirectoryManagerImpl) manager);
		try {
			// recupero risultato scansione
			HandlerResultBean<Void> resultScansione = futureScansione.get();
			if (!resultScansione.isSuccessful()) {
				throw new ImportDocIndexException(ErrorInfoEnum.ERRORE_SCANSIONE_FILE_INDICE);
			}
			Long stopTime = System.currentTimeMillis();
			logger.debug("Tempo impiegato scansione avente id {}: {}", idScansione, (stopTime - startTime));
			List<File> listaFileIndice = (List<File>) resultScansione
					.getAdditionalInformation(ScansioneDirectoryManager.ADDITIONAL_INFORMATION_LISTA_FILE_INDICE);
			logger.info("Numero Indici trovati: {}", listaFileIndice.size());
			logger.info("Indici trovati: {}", listaFileIndice);

			// verifica duplicazioni file indice trovati
			verificaDuplicazioniFileIndiceScansione(listaFileIndice);

			JobDebugConfig jobDebugConfig = SpringHelper.getJobDebugConfig();
			if (jobDebugConfig.getDebugAttivo()) {
				if (jobDebugConfig.getElaboraIndici()) {
					// elabora file indice
					elaboraIndici(executorService, configurazioneScansione, listaFileIndice);
				}
			} else {
				// elabora file indice
				elaboraIndici(executorService, configurazioneScansione, listaFileIndice);
			}

		} catch (InterruptedException | ExecutionException e) {
			logger.error("Errore scansione avente id {}", idScansione, e);
		} finally {
			ThreadContext.remove(ROUTINGKEY);
		}
	}

	/**
	 * Elabora gli indici trovati per la scansione
	 * 
	 * @param executorService
	 * @param configurazioneScansione
	 * @param listaFileIndice
	 * @throws ImportDocIndexException
	 */

	protected void elaboraIndici(ExecutorService executorService, final ConfigurazioniScansione configurazioneScansione, List<File> listaFileIndice)
			throws ImportDocIndexException {
		Map<File, Future<HandlerResultBean<Void>>> mappaFuture = new HashMap<>();

		for (File file : listaFileIndice) {
			try {
				logger.info("Inizio elaborazione indice {}", file.getAbsolutePath());
				FileIndiceManager fileIndiceManager = new FileIndiceManagerImpl(configurazioneScansione, file, this.aurigaLoginBean, this.locale);
				mappaFuture.put(file, executorService.submit((FileIndiceManagerImpl) fileIndiceManager));
			} catch (Exception e) {
				logger.error("Errore elaborazione file indice {}", file.getAbsolutePath(), e);
			}
		}

		for (Map.Entry<File, Future<HandlerResultBean<Void>>> entryFuture : mappaFuture.entrySet()) {

			try {
				HandlerResultBean<Void> resultElaborazioneIndice = entryFuture.getValue().get();
				if (!resultElaborazioneIndice.isSuccessful()) {
					throw new ImportDocIndexException(ErrorInfoEnum.ERRORE_ELABORAZIONE_FILE);
				}
			} catch (InterruptedException | ExecutionException e) {
				logger.error("Errore elaborazione file indice {}", entryFuture.getKey().getAbsolutePath(), e);
			}
		}
	}

	/**
	 * Verifica delle configurazioni della scansione e impostazione dei valori di default
	 * 
	 * @return
	 * @throws ImportDocIndexException
	 */

	private HandlerResultBean<Boolean> verificaConfigurazioniScansione(final ConfigurazioniScansione configurazioniScansione) throws ImportDocIndexException {

		HandlerResultBean<Boolean> result = new HandlerResultBean<>();
		result.setSuccessful(false);

		try {

			final ConfigurazioniFileIndice configurazioniFileIndice = configurazioniScansione.getConfigurazioniFileIndice();
			final ConfigurazioniDirectoryScansione configurazioniDirectoryScansione = configurazioniScansione.getConfigurazioniDirectoryScansione();
			final VincoliFileScansione vincoliFileScansione = configurazioniScansione.getVincoliFileScansione();
			final ConfigurazioniArchiviazione configurazioniArchiviazione = configurazioniScansione.getConfigurazioniArchiviazione();

			if (configurazioniFileIndice == null || configurazioniDirectoryScansione == null || configurazioniArchiviazione == null) {
				throw new ImportDocIndexException(ErrorInfoEnum.VALIDAZIONE_CONFIGURAZIONE_SCANSIONE_INDICI);
			}

			verificaConfigurazioniDirectory(configurazioniDirectoryScansione);

			verificaConfigurazioniArchiviazione(configurazioniArchiviazione);

			verificaConfigurazioniFileIndice(configurazioniFileIndice);

			verificaVincoliScansione(configurazioniScansione, vincoliFileScansione);

		} catch (Exception e) {
			logger.error(ErrorInfoEnum.VALIDAZIONE_CONFIGURAZIONE_SCANSIONE_INDICI.getDescription(), e);
			throw new ImportDocIndexException(e, ErrorInfoEnum.VALIDAZIONE_CONFIGURAZIONE_SCANSIONE_INDICI);
		}

		result.setSuccessful(true);

		return result;

	}

	/**
	 * @param vincoliFileScansione
	 */
	private void verificaVincoliScansione(final ConfigurazioniScansione configurazioniScansione, final VincoliFileScansione vincoliFileScansione) {

		if (vincoliFileScansione == null) {

			configurazioniScansione.setVincoliFileScansione(new VincoliFileScansione());

		} else {

			if (vincoliFileScansione.getListaEstensioni() == null) {
				vincoliFileScansione.setListaEstensioni(new ArrayList<String>());
			}

			if (vincoliFileScansione.getListaMimeType() == null) {
				vincoliFileScansione.setListaMimeType(new ArrayList<String>());
			}

			if (vincoliFileScansione.getListaNomiFileDaEscludere() == null) {
				vincoliFileScansione.setListaNomiFileDaEscludere(new ArrayList<String>());
			}

			if (vincoliFileScansione.getListaPercorsiFileAssolutiDaEscludere() == null) {
				vincoliFileScansione.setListaPercorsiFileAssolutiDaEscludere(new ArrayList<String>());
			}

			if (vincoliFileScansione.getListaPatternNomeFile() == null) {
				vincoliFileScansione.setListaPatternNomeFile(new ArrayList<String>());
			}

			if (vincoliFileScansione.getListaPatternPercorsiFileAssoluti() == null) {
				vincoliFileScansione.setListaPatternPercorsiFileAssoluti(new ArrayList<String>());
			}

		}
	}

	private void verificaConfigurazioniArchiviazione(final ConfigurazioniArchiviazione configurazioniArchiviazione) throws ImportDocIndexException {

		if (BooleanUtils.isTrue(configurazioniArchiviazione.getAbilitaArchiviazioneStorageUtil()) && configurazioniArchiviazione.getStorageUtilImpl() == null) {
			throw new ImportDocIndexException(ErrorInfoEnum.VALIDAZIONE_CONFIGURAZIONE_STORAGE);
		} else {
			try {
				String test = configurazioniArchiviazione.getStorageUtilImpl().getConfigurazioniStorage();
				logger.info("Configurazione storage in uso {}", test);
			} catch (Exception e) {
				logger.error(ErrorInfoEnum.VALIDAZIONE_CONFIGURAZIONE_STORAGE.getDescription());
				throw new ImportDocIndexException(ErrorInfoEnum.VALIDAZIONE_CONFIGURAZIONE_STORAGE);
			}
		}

		if (BooleanUtils.isTrue(configurazioniArchiviazione.getAbilitaControlloMimeTypeFileReferenziati())
				&& StringUtils.isBlank(configurazioniArchiviazione.getDefaultMimeTypeFileReferenziati())) {
			throw new ImportDocIndexException(ErrorInfoEnum.VALIDAZIONE_CONFIGURAZIONE_DEFAULT_MIME_TYPE);
		}

		if (BooleanUtils.isTrue(configurazioniArchiviazione.getAbilitaCalcoloImpronta())
				&& StringUtils.isBlank(configurazioniArchiviazione.getAlgoritmoImpronta())
				&& StringUtils.isBlank(configurazioniArchiviazione.getEncodingImpronta())) {
			throw new ImportDocIndexException(ErrorInfoEnum.VALIDAZIONE_CONFIGURAZIONI_CALCOLO_IMPRONTA);
		}

	}

	/**
	 * Verifica delle configurazioni della directory di scansione e delle directory di lavoro
	 * 
	 * @param configurazioniDirectoryScansione
	 * @throws ImportDocIndexException
	 */
	private void verificaConfigurazioniDirectory(final ConfigurazioniDirectoryScansione configurazioniDirectoryScansione) throws ImportDocIndexException {

		final FileManager fileManager = ottieniFileManager(configurazioniDirectoryScansione);

		if (configurazioniDirectoryScansione.getAbilitaScansioneSottoDirectory() == null) {
			configurazioniDirectoryScansione.setAbilitaScansioneSottoDirectory(ConfigurazioniDirectoryScansione.DEFAULT_SCANSIONE_SOTTO_DIRECTORY);
		}

		if (configurazioniDirectoryScansione.getLivelloMassimoScansioneSottoDirectory() == null) {
			configurazioniDirectoryScansione
					.setLivelloMassimoScansioneSottoDirectory(ConfigurazioniDirectoryScansione.DEFAULT_LIVELLO_MASSIMO_SCANSIONE_SOTTODIRECTORY);
		}

		if (configurazioniDirectoryScansione.getAbilitaDecompressione() == null) {
			configurazioniDirectoryScansione.setAbilitaDecompressione(ConfigurazioniDirectoryScansione.DEFAULT_ABILITA_DECOMPRESSIONE);
		}

		if (configurazioniDirectoryScansione.getAzioneFile() == null) {
			configurazioniDirectoryScansione.setAzioneFile(ConfigurazioniDirectoryScansione.DEFAULT_AZIONE_FILE_ELABORATO);
		}

		if (StringUtils.isBlank(configurazioniDirectoryScansione.getDirectoryRadice())) {
			throw new ImportDocIndexException(ErrorInfoEnum.VALIDAZIONE_CONFIGURAZIONE_DIRECTORY_ROOT);
		}

		verificaDirectoryPrincipale(configurazioniDirectoryScansione, fileManager);

		/**
		 * Il backup e le directory successive non sono obbligatorie, se specificate però vanno controllate
		 */

		verificaDirectoryBackup(configurazioniDirectoryScansione, fileManager);

		verificaDirectoryLavoro(configurazioniDirectoryScansione, fileManager);

		verificaDirectoryElaborati(configurazioniDirectoryScansione, fileManager);

		verificaDirectoryErrori(configurazioniDirectoryScansione, fileManager);

	}

	/**
	 * @param configurazioniDirectoryScansione
	 * @return
	 * @throws ImportDocIndexException
	 */
	private FileManager ottieniFileManager(final ConfigurazioniDirectoryScansione configurazioniDirectoryScansione) throws ImportDocIndexException {
		final FileManager fileManager;
		try {
			fileManager = FileManagerFactory.getFileManager(configurazioniDirectoryScansione.getConfigurazioniFileManager());
		} catch (NotYetImplementedException | FileManagerException e) {
			throw new ImportDocIndexException(e, ErrorInfoEnum.VALIDAZIONE_CONFIGURAZIONE_FILE_MANAGER);
		}
		return fileManager;
	}

	/**
	 * Verifica della directory con i file in errore
	 * 
	 * @param configurazioniDirectoryScansione
	 * @param fileManager
	 * @throws ImportDocIndexException
	 */

	private void verificaDirectoryErrori(final ConfigurazioniDirectoryScansione configurazioniDirectoryScansione, final FileManager fileManager)
			throws ImportDocIndexException {
		if (StringUtils.isNotBlank(configurazioniDirectoryScansione.getDirectoryErrori())) {
			final File directoryErrori = new File(configurazioniDirectoryScansione.getDirectoryErrori());
			FileBean directoryErroriFileBean = FileManagerUtil.createFromFile(directoryErrori);
			try {
				if (!fileManager.isDirectory(directoryErroriFileBean) || !fileManager.canReadFile(directoryErroriFileBean)
						|| !fileManager.canWriteFile(directoryErroriFileBean)) {
					throw new ImportDocIndexException(ErrorInfoEnum.VALIDAZIONE_CONFIGURAZIONE_DIRECTORY_ERRORI);
				}
			} catch (FileManagerException e) {
				throw new ImportDocIndexException(e, ErrorInfoEnum.VALIDAZIONE_CONFIGURAZIONE_DIRECTORY_ERRORI);
			}
		} else {
			throw new ImportDocIndexException(ErrorInfoEnum.VALIDAZIONE_CONFIGURAZIONE_DIRECTORY_ERRORI);
		}
	}

	/**
	 * Verifica della directory con i file elaborati
	 * 
	 * @param configurazioniDirectoryScansione
	 * @param fileManager
	 * @throws ImportDocIndexException
	 */

	private void verificaDirectoryElaborati(final ConfigurazioniDirectoryScansione configurazioniDirectoryScansione, final FileManager fileManager)
			throws ImportDocIndexException {
		if (StringUtils.isNotBlank(configurazioniDirectoryScansione.getDirectoryElaborati())) {
			final File directoryElaborati = new File(configurazioniDirectoryScansione.getDirectoryElaborati());
			FileBean directoryElaboratiFileBean = FileManagerUtil.createFromFile(directoryElaborati);
			try {
				if (!fileManager.isDirectory(directoryElaboratiFileBean) || !fileManager.canReadFile(directoryElaboratiFileBean)
						|| !fileManager.canWriteFile(directoryElaboratiFileBean)) {
					throw new ImportDocIndexException(ErrorInfoEnum.VALIDAZIONE_CONFIGURAZIONE_DIRECTORY_ELABORATI);
				}
			} catch (FileManagerException e) {
				throw new ImportDocIndexException(e, ErrorInfoEnum.VALIDAZIONE_CONFIGURAZIONE_DIRECTORY_ELABORATI);
			}
		}
	}

	/**
	 * Verifica della directory di lavoro
	 * 
	 * @param configurazioniDirectoryScansione
	 * @param fileManager
	 * @throws ImportDocIndexException
	 */

	private void verificaDirectoryLavoro(final ConfigurazioniDirectoryScansione configurazioniDirectoryScansione, final FileManager fileManager)
			throws ImportDocIndexException {
		if (StringUtils.isNotBlank(configurazioniDirectoryScansione.getDirectoryLavoro())) {
			final File directoryLavoro = new File(configurazioniDirectoryScansione.getDirectoryLavoro());
			FileBean directoryLavoroFileBean = FileManagerUtil.createFromFile(directoryLavoro);
			try {
				if (!fileManager.isDirectory(directoryLavoroFileBean) || !fileManager.canReadFile(directoryLavoroFileBean)
						|| !fileManager.canWriteFile(directoryLavoroFileBean)) {
					throw new ImportDocIndexException(ErrorInfoEnum.VALIDAZIONE_CONFIGURAZIONE_DIRECTORY_LAVORO);
				}
			} catch (FileManagerException e) {
				throw new ImportDocIndexException(e, ErrorInfoEnum.VALIDAZIONE_CONFIGURAZIONE_DIRECTORY_LAVORO);
			}
		}
	}

	/**
	 * Verifica della directory di backup
	 * 
	 * @param configurazioniDirectoryScansione
	 * @param fileManager
	 * @throws ImportDocIndexException
	 */

	private void verificaDirectoryBackup(final ConfigurazioniDirectoryScansione configurazioniDirectoryScansione, final FileManager fileManager)
			throws ImportDocIndexException {
		if (StringUtils.isNotBlank(configurazioniDirectoryScansione.getDirectoryBackup())) {
			final File directoryBackup = new File(configurazioniDirectoryScansione.getDirectoryBackup());
			FileBean directoryBackupFileBean = FileManagerUtil.createFromFile(directoryBackup);
			try {
				if (!fileManager.isDirectory(directoryBackupFileBean) || !fileManager.canReadFile(directoryBackupFileBean)
						|| !fileManager.canWriteFile(directoryBackupFileBean)) {
					throw new ImportDocIndexException(ErrorInfoEnum.VALIDAZIONE_CONFIGURAZIONE_DIRECTORY_BACKUP);
				}
			} catch (FileManagerException e) {
				throw new ImportDocIndexException(e, ErrorInfoEnum.VALIDAZIONE_CONFIGURAZIONE_DIRECTORY_BACKUP);
			}
		}
	}

	/**
	 * Verifica della directory principale
	 * 
	 * @param configurazioniDirectoryScansione
	 * @param fileManager
	 * @throws ImportDocIndexException
	 */

	private void verificaDirectoryPrincipale(final ConfigurazioniDirectoryScansione configurazioniDirectoryScansione, final FileManager fileManager)
			throws ImportDocIndexException {
		try {
			final File directoryPrincipale = new File(configurazioniDirectoryScansione.getDirectoryRadice());
			FileBean directoryPrincipaleFileBean = FileManagerUtil.createFromFile(directoryPrincipale);
			// verifica solo dei permessi di lettura della directory, non è detto che sia necessario modificare file
			if (!fileManager.isDirectory(directoryPrincipaleFileBean) || !fileManager.canReadFile(directoryPrincipaleFileBean)) {
				throw new ImportDocIndexException(ErrorInfoEnum.VALIDAZIONE_CONFIGURAZIONE_DIRECTORY_ROOT);
			}
		} catch (FileManagerException e) {
			throw new ImportDocIndexException(e, ErrorInfoEnum.VALIDAZIONE_CONFIGURAZIONE_DIRECTORY_ROOT);
		}
	}

	/**
	 * Verifica della configurazione del file indice
	 * 
	 * @param configurazioniFileIndice
	 * @throws ImportDocIndexException
	 */

	private void verificaConfigurazioniFileIndice(final ConfigurazioniFileIndice configurazioniFileIndice) throws ImportDocIndexException {

		if (configurazioniFileIndice.getTipoFileIndice() == null) {
			throw new ImportDocIndexException(ErrorInfoEnum.VALIDAZIONE_CONFIGURAZIONE_FORMATO_INDICE);
		}

		if (configurazioniFileIndice.getColonnaURIFileIndice() == null) {
			configurazioniFileIndice.setColonnaURIFileIndice(ConfigurazioniFileIndice.DEFAULT_COLONNA_URI_FILE_INDICE);
		}

		if (configurazioniFileIndice.getRigaDatiFileIndice() == null) {
			configurazioniFileIndice.setRigaDatiFileIndice(ConfigurazioniFileIndice.DEFAULT_RIGA_DATI_FILE_INDICE);
		}

		if (configurazioniFileIndice.getAzioneFileIndice() == null) {
			configurazioniFileIndice.setAzioneFileIndice(ConfigurazioniFileIndice.DEFAULT_AZIONE_FILE_INDICE);
		}

		if (configurazioniFileIndice.getSeparatore() == null) {
			configurazioniFileIndice.setSeparatore(ConfigurazioniFileIndice.DEFAULT_SEPARATORE);
		}

	}

	/**
	 * Pulizia directory di lavoro, solo se non è attiva archiviazione con StorageUtill. In questo caso infatti i file potrebbero essere presenti nella
	 * directory di lavoro
	 * 
	 * @throws ImportDocIndexException
	 */

	private void puliziaDirectoryLavoro(final ConfigurazioniScansione configurazioniScansione) throws ImportDocIndexException {

		final ConfigurazioniDirectoryScansione configurazioniDirectoryScansione = configurazioniScansione.getConfigurazioniDirectoryScansione();
		final FileManager fileManager = ottieniFileManager(configurazioniDirectoryScansione);

		if (StringUtils.isNotBlank(configurazioniDirectoryScansione.getDirectoryLavoro())
				&& BooleanUtils.isTrue(configurazioniScansione.getConfigurazioniArchiviazione().getAbilitaArchiviazioneStorageUtil())) {
			final File directoryLavoro = new File(configurazioniDirectoryScansione.getDirectoryLavoro());
			FileBean directoryLavoroFileBean = FileManagerUtil.createFromFile(directoryLavoro);
			try {
				logger.info("Pulizia directory di lavoro {}", directoryLavoroFileBean.getAbsolutePath());
				// cancello la directory e la ricreo vuota
				fileManager.deleteDirectory(directoryLavoroFileBean);
				fileManager.createDirectory(directoryLavoroFileBean);
			} catch (FileManagerException e) {
				logger.warn("Errore nella pulizia della directory di lavoro {}", configurazioniDirectoryScansione.getDirectoryLavoro(), e);
			}

		}

	}

	// Fine recupero lista job
	@Override
	protected void end(String config) {
		logger.info("Elaborazione completata");
	}

}
