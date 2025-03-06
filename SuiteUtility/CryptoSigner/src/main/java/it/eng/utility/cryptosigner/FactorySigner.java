package it.eng.utility.cryptosigner;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;

//import it.eng.utility.cryptosigner.CryptoSingleton.ScheduleBean;
import it.eng.utility.cryptosigner.bean.ConfigBean;
import it.eng.utility.cryptosigner.ca.ICertificateAuthority;
import it.eng.utility.cryptosigner.context.CryptoSignerApplicationContextProvider;
import it.eng.utility.cryptosigner.exception.CryptoSignerException;
import it.eng.utility.cryptosigner.exception.CryptoStorageException;
import it.eng.utility.cryptosigner.storage.ICAStorage;
import it.eng.utility.cryptosigner.storage.ICRLStorage;
import it.eng.utility.cryptosigner.storage.IConfigStorage;
import it.eng.utility.cryptosigner.task.carevoke.ListenerUpdateCertificateAuthorityRevoke;
import it.eng.utility.cryptosigner.task.carevoke.TaskUpdateCertificateAuthorityRevoke;
import it.eng.utility.cryptosigner.task.caupdate.ListenerUpdateCertificateAuthorityList;
import it.eng.utility.cryptosigner.task.caupdate.TaskUpdateCertificateAuthorityList;
import it.eng.utility.cryptosigner.task.crl.ListenerUpdateCertificateRevocationList;
import it.eng.utility.cryptosigner.task.crl.TaskUpdateCertificateRevocationList;
//import it.sauronsoftware.cron4j.Scheduler;

/**
 * Classe che definisce la logica di business preposta alle operazioni di registrazione e monitoraggio dei task
 * 
 * @author Michele Rigo
 *
 */
public class FactorySigner {

	static Logger log = LogManager.getLogger(FactorySigner.class);

	/*
	 * Stringa utilizzata per sincronizzare i metodi di start stop.
	 */
	private static String synchronize = "SINC";

	/**
	 * Registra l'ApplicationContext di Spring per il recupero delle configurazioni
	 * 
	 * @param context
	 */
	public static void registerSpringContext(ApplicationContext context) {
		CryptoSingleton.getInstance().setContext(context);
	}

	/**
	 * Metodo che registra gli schedulatori sul singleton e cancella quelli precedentemente registrati Alla chiamata di questo metodo i task attivi vengono
	 * stoppati.
	 */
	/*public static void registerTask() throws CryptoSignerException {
		// Eseguo lo stop di tutti i task presenti sul singleton.
		stopTask();

		CryptoConfiguration configuration = CryptoSingleton.getInstance().getConfiguration();

		// Registro lo scheduler di aggiornamento della CA
		// Creo un'istanza del listener
		ListenerUpdateCertificateAuthorityList listenerCA = new ListenerUpdateCertificateAuthorityList();

		// Creo lo scheduler
		Scheduler schedulerCA = new Scheduler();
		schedulerCA.addSchedulerListener(listenerCA);
		String idCA = schedulerCA.schedule(configuration.getScheduleCAUpdate(), new TaskUpdateCertificateAuthorityList());

		CryptoSingleton.getInstance().registerSchedule(schedulerCA, idCA, CryptoConstants.CA_UPDATE_TASK);

		// Registro lo scheduler di aggiornamento del controllo di revoca delle Certification Authority
		// Creo un'istanza del listener
		ListenerUpdateCertificateAuthorityRevoke listenerCARevoke = new ListenerUpdateCertificateAuthorityRevoke();

		// Creo lo scheduler
		Scheduler schedulerCARevoke = new Scheduler();
		schedulerCARevoke.addSchedulerListener(listenerCARevoke);
		String idCARevoke = schedulerCARevoke.schedule(configuration.getScheduleCAUpdate(), new TaskUpdateCertificateAuthorityRevoke());

		CryptoSingleton.getInstance().registerSchedule(schedulerCARevoke, idCARevoke, CryptoConstants.CA_REVOKE_TASK);

		// Recupero tutti i distribution point del sistema
		List<ConfigBean> crlConfig = null;
		try {
			crlConfig = getInstanceConfigStorage().retriveAllConfig();
		} catch (CryptoStorageException e) {
			log.warn("Warning recupero configurazioni!", e);
			throw new CryptoSignerException(e);
		}
		log.debug("CRL CONFIG:" + crlConfig);
		if (crlConfig != null) {
			// Per ogni configurazione registro uno scheduler sul singleton
			for (int i = 0; i < crlConfig.size(); i++) {
				// Creo un nuovo task
				ConfigBean config = crlConfig.get(i);
				try {
					TaskUpdateCertificateRevocationList task = new TaskUpdateCertificateRevocationList(config.getCrlURL());

					// Creo un'istanza del listener
					ListenerUpdateCertificateRevocationList listener = new ListenerUpdateCertificateRevocationList(config.getSubjectDN());

					// Creo lo scheduler
					Scheduler scheduler = new Scheduler();
					scheduler.addSchedulerListener(listener);
					String id = scheduler.schedule(config.getSchedule(), task);

					// Registro lo scheduler sul Singleton
					CryptoSingleton.getInstance().registerSchedule(scheduler, id, config.getSubjectDN());

					log.debug("Task Registrato per SubjectDN:" + config.getSubjectDN() + ", con schedulazione:" + config.getSchedule());

				} catch (Exception e) {
					log.warn("Warning creazione task per subjectDN:" + config.getSubjectDN(), e);
				}
			}
		}
	}*/

	/**
	 * Avvia i task di aggiornamento dei certificati delle CA
	 * 
	 * @throws CryptoSignerException
	 */
	public static void initialize() throws CryptoSignerException {
		synchronized (synchronize) {
			if (CryptoSingleton.getInstance().getContext() == null) {
				ApplicationContext context = CryptoSignerApplicationContextProvider.getContext();
				registerSpringContext(context);
			}

			getInstanceCertificateAuthority().updateCertificate();
		}
	}

	public static void setup() throws CryptoSignerException {
		synchronized (synchronize) {
			log.debug("Metodo setup di FactorySigner");
			if (CryptoSingleton.getInstance().getContext() == null) {
				ApplicationContext context = CryptoSignerApplicationContextProvider.getContext();
				registerSpringContext(context);
			}
			boolean useSchedule = CryptoSingleton.getInstance().getConfiguration().isUseSchedule();
			log.debug("useSchedule " + useSchedule);
			if (useSchedule) {
				/*try {
					registerTask();
				} catch (CryptoSignerException e) {
					log.error("CryptoSignerException", e);
				}*/
			}
			try {
				boolean initCAStorage = CryptoSingleton.getInstance().getConfiguration().isInitCAStorage();
				log.debug("Inizializzazione dello storage CA attiviato? " + initCAStorage);
				if (initCAStorage) {
					initialize();
				}
			} catch (CryptoSignerException e) {
				log.error("CryptoSignerException", e);
			}
			if (useSchedule) {
				/*try {
					startTask();
				} catch (CryptoSignerException e) {
					log.error("CryptoSignerException", e);
				}*/
			}
		}
	}

	/**
	 * Avvia tutti i task di aggiornamento delle CRL presenti sul singleton Se un task risulta attivo esso viene stoppato e riavviato. Il metodo e sincornizzato
	 * con lo stopTask in modo che non e possibile fermare i task fino a quando tutti non siano stati avviati.
	 * 
	 * @throws CryptoSignerException
	 */
	/*public static void startTask() throws CryptoSignerException {
		synchronized (synchronize) {
			List<ScheduleBean> schedulers = CryptoSingleton.getInstance().getTasks();
			for (int i = 0; i < schedulers.size(); i++) {
				if (schedulers.get(i).getSchedule().isStarted()) {
					schedulers.get(i).getSchedule().stop();
				}
				schedulers.get(i).getSchedule().start();
			}
		}
	}*/

	/**
	 * Stoppa tutti i task di aggiornamento delle CRL presenti sul singleton Il metodo e sincornizzato con lo startTask in modo che non e possibile fermare i
	 * task fino a quando tutti non siano stati avviati.
	 */
	/*public static void stopTask() {
		synchronized (synchronize) {
			List<ScheduleBean> schedulers = CryptoSingleton.getInstance().getTasks();
			if (schedulers != null) {
				for (int i = 0; i < schedulers.size(); i++) {
					schedulers.get(i).getSchedule().stop();
				}
			}
		}
	}*/

	/**
	 * Ritorna il task corrispondente al subjectDN passato in ingresso
	 * 
	 * @param subjectDN
	 */
	/*public static synchronized void unregisterTask(String subjectDN) {
		List<ScheduleBean> schedulers = CryptoSingleton.getInstance().getTasks();
		Scheduler ret = null;
		if (schedulers != null) {
			for (int i = 0; i < schedulers.size(); i++) {
				if (schedulers.get(i).getSubjectDN().equals(subjectDN)) {
					schedulers.get(i).getSchedule().stop();
					schedulers.remove(i);
					try {
						getInstanceConfigStorage().deleteConfig(subjectDN);
					} catch (CryptoStorageException e) {
						log.error("CryptoStorageException", e);
					}
					break;
				}
			}
		}
	}*/

	/**
	 * Restiruisce un'istanza dello storage delle CA
	 */
	public static synchronized ICAStorage getInstanceCAStorage() {
		return (ICAStorage) CryptoSingleton.getInstance().getContext().getBean(CryptoConstants.ICASTORAGE);
	}

	/**
	 * Restituisce un'istanza dello storage delle CRL
	 */
	public static synchronized ICRLStorage getInstanceCRLStorage() {
		return (ICRLStorage) CryptoSingleton.getInstance().getContext().getBean(CryptoConstants.ICRLSTORAGE);
	}

	/**
	 * Restituisce un'istanza dello storage della CONFIG
	 */
	public static synchronized IConfigStorage getInstanceConfigStorage() {
		return (IConfigStorage) CryptoSingleton.getInstance().getContext().getBean(CryptoConstants.ICONFIGSTORAGE);
	}

	/**
	 * Restituisce un'istanza dello storage della CONFIG
	 */
	public static synchronized ICertificateAuthority getInstanceCertificateAuthority() {
		return (ICertificateAuthority) CryptoSingleton.getInstance().getContext().getBean(CryptoConstants.ICERTIFICATEAUTHORITY);
	}

}