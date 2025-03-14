package it.eng.utility.jobmanager.quartz.config;

import it.eng.utility.jobmanager.quartz.DataSource;
import it.eng.utility.jobmanager.quartz.JobScheduler;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * Manager che gestisce l'attivazione dei job
 * 
 * @author Rigo Michele
 * @author denis.bragato
 */
public class JobManager {

	Logger log = Logger.getLogger(JobManager.class);

	private String urlServer = null;

	private String nodename = null;

	private Boolean master = null;

	private Boolean cluster = null;

	private DataSource dataSource = null;

	private Map<String, JobConfigBean> jobs;
	
	private boolean startWithScheduler = true;

	private boolean stampaErroriInUscita = false;


	/**
	 * Inizializza i job configurati
	 * 
	 * @param jobclassesmap
	 */
	public void initialize(HashMap<String, Class<?>> jobclassesmap, String... args) {
		for (String key : jobs.keySet()) {
			JobConfigBean job = jobs.get(key);
			if (jobclassesmap.containsKey(job.getType())) {
				Class<?> classe = jobclassesmap.get(job.getType());
				try {
					log.debug("Avvio job " + key);
					JobScheduler.initialize(key, classe, job, this, args);
				} catch (Exception e) {
					log.error("Errore job " + key, e);
				}
			}
		}
	}

	public String getUrlServer() {
		return urlServer;
	}

	public void setUrlServer(String urlServer) {
		this.urlServer = urlServer;
	}

	public String getNodename() {
		return nodename;
	}

	public void setNodename(String nodename) {
		this.nodename = nodename;
	}

	public Boolean getMaster() {
		return master;
	}

	public void setMaster(Boolean master) {
		this.master = master;
	}

	public Boolean getCluster() {
		return cluster;
	}

	public void setCluster(Boolean cluster) {
		this.cluster = cluster;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setJobs(Map<String, JobConfigBean> jobs) {
		this.jobs = jobs;
	}

	public Map<String, JobConfigBean> getJobs() {
		return jobs;
	}
	
	/**
	 * Metodo accessorio che ritorna il valore che indica se il job deve essere avviato o meno 
	 * con l'utilizzo dello scheduler
	 * startWithScheduler = true allora verr� avviato rispettando la schedulazione indicata nel file di configurazione
	 * startWithScheduler = false allora verr� avviato immediatamente ignorando la schedulazione inserita 
	 * @return valore di startWithScheduler
	 */
	public boolean isStartWithScheduler() {
		return startWithScheduler;
	}

	/**
	 * Metodo accessorio che setta il valore che indica se il job deve essere avviato o meno 
	 * con l'utilizzo dello scheduler
	 * startWithScheduler = true allora verr� avviato rispettando la schedulazione indicata nel file di configurazione
	 * startWithScheduler = false allora verr� avviato immediatamente ignorando la schedulazione inserita 
	 * @param startWithScheduler valore di startWithScheduler
	 */
	public void setStartWithScheduler(boolean startWithScheduler) {
		this.startWithScheduler = startWithScheduler;
	}
	
	/**
	 * Ritorna lo stato di attivazione della stampa degli errori in uscita dall'applicazione per exit code diverso da zero 
	 * @return valore di stampaErroriInUscita
	 */
	public boolean isStampaErroriInUscita() {
		return stampaErroriInUscita;
	}
	/**
	 * Impostato a true, attiva la stampa degli errori in uscita dall'applicazione per exit code diverso da zero
	 * @param stampaErroriInUscita
	 */
	public void setStampaErroriInUscita(boolean stampaErroriInUscita) {
		this.stampaErroriInUscita = stampaErroriInUscita;
	}

}