/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;
import org.springframework.context.ApplicationContext;

import it.eng.dbpoolmanager.spring.FactorySpringDatasource;
import it.eng.job.luceneindexer.SpringHelper;
import it.eng.utility.jobmanager.SpringAppContext;
import it.eng.utility.jobmanager.quartz.GestioneTerminazioneProgramma;
import it.eng.utility.jobmanager.quartz.Job;
import it.eng.utility.jobmanager.quartz.LogErrori;
import it.eng.utility.jobmanager.quartz.config.JobManager;

public abstract class AbstractStarter implements Runnable {

	private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
	private JobManager manager;

	private String[] args;

	@Override
	public void run() {

		logger.info("JOB STARTER");

		// Ritorno al chiamante una volta terminata l'esecuzione del job
		startExecution();
	}

	/**
	 * Metodo che deve essere richiamato dal main e che controlla l'avvio del job e gestisce la terminazione e il ritorno del codice di errore.
	 */
	public void startExecution() {
		// Eseguo la ricerca dei job e delle rispettive annotation
		HashMap<String, Class<?>> jobclassesmap = searchAnnotation();

		if (jobclassesmap != null) {
			try {

				ApplicationContext context = SpringHelper.getMainApplicationContext();
				logger.info("TEST context " + context);
				SpringAppContext.setContext(context);
				
				// Istanzio il DBPoolManager
				FactorySpringDatasource.setAppContext(context);

				manager = executeJobManager(jobclassesmap);

				/*
				 * Da inserire per fare in modo che l'esecuzione si blocchi in questo punto fino al momento in cui tutti i thread in esecuzione non siano
				 * terminati
				 */
				endProgram();

			} catch (Exception e) {
				logger.error("Errore in fase di START del job", e);
				LogErrori.addErrorLog(LogErrori.ErrorCodeEnum.ERRORE_APPLICATIVO, e.getMessage());
			}
		} else {
			// Non ha trovato nessun job
			LogErrori.addErrorLog(LogErrori.ErrorCodeEnum.ERRORE_APPLICATIVO, "Non è stato individuato alcun job");
		}

		// Ritorno il codice di errore in base ai logger di errore che sono stati segnalati dai job
		checkErrorCode();
	}

	/**
	 * Metodo che avvia la ricerca dei job e delle rispettive annotation
	 * 
	 * @return la mappa che deve essere utilizzata nel settaggio delle configurazioni
	 */
	public HashMap<String, Class<?>> searchAnnotation() {
		HashMap<String, Class<?>> jobclassesmap;
		try {
			jobclassesmap = new HashMap<String, Class<?>>();
			Reflections reflections = new Reflections(getPackage());
			Set<Class<?>> jobclasses = reflections.getTypesAnnotatedWith(Job.class);
			for (Class<?> classe : jobclasses) {
				String jobtype = ((Job) classe.getAnnotation(Job.class)).type();
				jobclassesmap.put(jobtype, classe);
			}

		} catch (Exception e) {
			logger.error("Errore nella ricerca delle annotation java", e);
			return null;
		}

		return jobclassesmap;
	}

	/**
	 * Metodo che esegue il controllo degli errori che sono stati inseriti all'interno della classe LogErrori e calcola il valore di ritorno che deve essere
	 * restituito al chiamante
	 * 
	 * @return -2 è il codice che identifica che l'analisi è stata eseguita con schedulazione e quindi il controllo degli errori non è necessario; questo è il
	 *         caso in cui NON deve essere eseguito il System.exit, altrimenti il JobManager viene interrotto mentre è in attesa dell'orario schedulato per
	 *         avviare il job
	 * @return 0 se tutto è avvenuto correttamente e non sono stati sollevati degli errori
	 */
	public void checkErrorCode() {
		if (manager.isStartWithScheduler()) {
			logger.info("Il job ha l'avvio schedulato... Lo scheduler rimarrà attivo");
			return;
		} else {
			// Controllo se ci sono errori segnalati dal job avviato
			if (LogErrori.size() != 0) {
				// C'e almeno un errore
				logger.error("Esecuzione del job terminata con errore");
				logger.error(LogErrori.getTextError());
				if (manager.isStampaErroriInUscita()) {
					logger.error(LogErrori.getTextError());
				}
				System.exit(LogErrori.getMaximumErrorCode());
			} else {
				// Tutto e andato a buon fine, quindi ritorno il codice relativo
				logger.info("Esecuzione del job terminata correttamente");
				System.exit(LogErrori.ErrorCodeEnum.NESSUN_ERRORE.getErrorCode());
			}
			logger.info("JOB END");
		}
	}

	/**
	 * Metodo che, nel caso l'esecuzione del job sia immediata e non schedulata, si blocca in attesa che i thread termininino la loro esecuzione. In caso
	 * contrario l'elaborazione procede in modo normale senza attendere la terminazione dei thread.
	 */
	public void endProgram() {
		if (!manager.isStartWithScheduler()) {
			/*
			 * Se il job e stato avviato con il tag per essere eseguito direttamente senza aspettare la schedulazione allora il main deve rimanere in attesa
			 * della terminazione di tutti i thread e, successivamente, controllare il logger degli errori
			 */

			// Controllo se il programma puo terminare la sua esecuzione
			if (!GestioneTerminazioneProgramma.endProgram()) {
				// La gestione del semaforo ha lanciato un eccezione
				LogErrori.addErrorLog(LogErrori.ErrorCodeEnum.ERRORE_APPLICATIVO,
						"Eccezione nella gestione del semaforo in GestioneTerminazioneProgramma.endProgram()");
			} // Altrimenti si e terminata l'esecuzione senza problemi nella gestione del semaforo
		}
		/*
		 * Altrimenti se deve essere eseguito con la schedulazione normale e previsto che non debba fermarsi e attendere la terminazione dei thread ma possa
		 * terminare direttamente e lasciare il controllo del job al manager
		 */
		return;

	}

	public void setArgs(String[] args) {
		this.args = args;
	}

	public String[] getArgs() {
		return args;
	}

	/**
	 * Metodo astratto che, in base all'implementazione, deve restituire il package per la ricerca dei job con l'annotation
	 * 
	 * @return
	 */
	public abstract String getPackage();

	/**
	 * Metodo astratto che setta le configaurazioni specifiche per il JobManager
	 * 
	 * @param jobclassesmap
	 *            classi che sono state rilevate dal search precedente
	 * @return JobManager
	 */
	public abstract JobManager executeJobManager(HashMap<String, Class<?>> jobclassesmap);
}
