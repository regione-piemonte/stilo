package it.eng.utility.jobmanager.quartz;

import java.lang.management.ManagementFactory;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.management.Attribute;
import javax.management.MBeanInfo;
import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.apache.log4j.Logger;

import it.eng.utility.jobmanager.quartz.bean.JobStatusUtility;

public abstract class AbstractJob<T> {

	private Logger log = Logger.getLogger(AbstractJob.class);

	private HashMap<String, Object> attributes;

	private MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

	private String jobType;
	private String parentLoader;
	private String fireInstanceId;
	private Date scheduledFireTime;
	private Date nextFireTime;

	/**
	 * Nome assegnato nel file di configurazione come chiave della mappa degli
	 * attributi
	 */
	private String name;

	/**
	 * Carica i dati sulla cache distribuita effettuando un lock sull'operazione
	 * 
	 * @return List<T>
	 */
	protected abstract List<T> load();

	public List<T> doLoad() {

		// esegui la procedura di caricamento del job
		List<T> loadData = load();

		return loadData;

	};

	/**
	 * Crea la chiave di ricerca tramite la quale poter gestire l'mbean
	 * associato al job, utilizzando il name specificato nel file di
	 * configurazione
	 * 
	 * @param job
	 * @return
	 */
	public ObjectName createName() {

		return JobStatusUtility.createName(name);

	}

	/**
	 * Recupera l'minfo relativo all'ObjectName specificato
	 * 
	 * @param name
	 * @return
	 */
	protected MBeanInfo retrieveInfo(ObjectName name) {

		MBeanInfo info = null;

		try {

			info = mbs.getMBeanInfo(name);

		} catch (Exception e) {

			log.error("Impossibile tracciare il bean di stato di running del job a causa della seguente eccezione ", e);

		}

		return info;
	}

	/**
	 * Esegue il job
	 * 
	 * @param obj
	 */
	protected abstract void execute(T obj);

	public void doExecute(T obj) {

		log.info(String.format("EXECUTE %s, lanciato da %s", fireInstanceId, parentLoader));

		execute(obj);
	}

	/**
	 * Esegue le operazioni di chiusura del job, anche se il suo execute è
	 * terminato con errore
	 * 
	 * @param obj
	 */
	protected abstract void end(T obj);

	public void doEnd(T obj) {

		log.info("END " + fireInstanceId);

		// esegui la procedura di chiusura del job
		end(obj);

	}

	/**
	 * verifica se il job è già in esecuzione in modalità one-shot ed imposta lo
	 * shutdown
	 * 
	 * @return
	 */
	public final boolean checkOneShot() {
		return Boolean.parseBoolean((String) getAttribute("oneShot"));
	}

	public void setAttributes(HashMap<String, Object> attributes) {
		this.attributes = attributes;
	}

	public HashMap<String, Object> getAttributes() {
		return attributes;
	}

	/**
	 * Ritorna il valore di un attributo. Se non esiste, ritorna stringa vuota
	 * 
	 * @param key
	 *            la chiave dell'attributo
	 * @return l'attributo corrispondente a <code>key</code>
	 */
	protected final Object getAttribute(String key) {
		Object value = "";
		if (getAttributes() != null) {
			value = getAttributes().get(key);
		}
		return value;
	}

	/**
	 *
	 * @return l'ObjectName necessario a recuperare l'mbean di configurazione di
	 *         questo job
	 */
	public ObjectName getMBeanIdentifier() {

		ObjectName lName = null;

		try {
			lName = new ObjectName("it.eng:type=" + jobType);
		} catch (Exception e) {
			log.error(
					"Non è stato possibile creare la chiave di ricerca dell'mbean di tracciamento dello stato a causa della seguente eccezione",
					e);
		}

		return lName;

	}

	public final String getJobType() {
		return jobType;
	}

	public final void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public String getParentLoader() {
		return parentLoader;
	}

	public void setParentLoader(String parentLoader) {
		this.parentLoader = parentLoader;
	}

	public final String getFireInstanceId() {
		return fireInstanceId;
	}

	public final void setFireInstanceId(String fireInstanceId) {
		if (this.fireInstanceId != null)
			throw new IllegalAccessError("Parametro già settato");
		this.fireInstanceId = fireInstanceId;
	}

	public final Date getScheduledFireTime() {
		return scheduledFireTime;
	}

	public final void setScheduledFireTime(Date scheduledFireTime) {
		if (this.scheduledFireTime != null)
			throw new IllegalAccessError("Parametro già settato");
		this.scheduledFireTime = scheduledFireTime;
	}

	public final Date getNextFireTime() {
		return nextFireTime;
	}

	public final void setNextFireTime(Date nextFireTime) {
		if (this.nextFireTime != null)
			throw new IllegalAccessError("Parametro già settato");
		this.nextFireTime = nextFireTime;
	}

	/**
	 * Permette di richiedere lo shutdown automatico del job, una volta che
	 * tutti i thread hanno completato la loro esecuzione, ovvero tutti quanti
	 * hanno eseguito il loro doEnd
	 */
	public synchronized void scheduleShutDown() {

		try {

			boolean shutdownRequested = (Boolean) mbs.getAttribute(createName(), "ScheduleShutdown");

			if (!shutdownRequested) {

				// non è già stato richiesto lo shutdown del job, posso allora
				// procedere con l'impostazione della richiesta
				mbs.setAttribute(createName(), new Attribute("ScheduleShutdown", Boolean.TRUE));
			}

		} catch (Exception e) {
			log.error("Durante la richiesta di shutdown del job, si è verificata la seguente eccezione", e);
		}
	}

	/**
	 * 
	 * @return true se è stato richiesto lo shutdown del job, false altrimenti
	 */
	public boolean isShutDownScheduled() {

		boolean retValue = false;

		try {

			retValue = (Boolean) mbs.getAttribute(createName(), "ScheduleShutdown");

		} catch (Exception e) {
			log.error(
					"Durante la verifica se è stato richiesto lo shutdown del job, si è verificata la seguente eccezione",
					e);
		}

		return retValue;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}