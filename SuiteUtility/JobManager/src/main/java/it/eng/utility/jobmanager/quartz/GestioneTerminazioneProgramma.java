package it.eng.utility.jobmanager.quartz;

import java.util.concurrent.Semaphore;

public class GestioneTerminazioneProgramma {
//	private static Logger log = Logger.getLogger(GestioneTerminazioneProgramma.class);
	private static Semaphore semEndProgram = new Semaphore(0);
	private static Semaphore semThread;

//	public GestioneTerminazioneProgramma()
//	{
//		/*
//		 * Creo un semaforo con 0 accessi disponibili.
//		 * Inizialmente infatti il chiamante non potra� terminare ma dovra� aspettare
//		 * che sia terminata l'esecuzione
//		 */
//		setNumberOfResources(0);
//	}
	
	/**
	 * Imposto il numero di risorse che devono essere associate al semaforo.
	 * Queste risorse devono essere RILASCIATE dalla terminazione dei thread per fare in modo che 
	 * il chiamante possa terminare la sua esecuzione
	 * @param numberOfThreads numero di thread che sono stati generati dopo l'esecuzione del load
	 */
	public static void setNumberOfResources(int numberOfThreads)
	{
		/*
		 * Creo un semaforo con numberOfThreads accessi disponibili.
		 * Metto il valore negativo in modo tale che, l'acquire rimanga in attesa fino
		 * al momento in cui tutti i thread saranno terminati e avranno rilasciato
		 * le risorse disponibili.
		 * 
		 * Se ad esempio sono attivi 5 thread, il numero di risorse sono -4.
		 * Ogni volta che un thread termina rilascia una risorsa. Quando tutti i thread saranno terminati
		 * ci saranno 1 risorse disponibili.
		 * A questo punto, il chiamante che era in attesa di ottenere una risorsa, puo procedere
		 * e terminare l'esecuzione.
		 */
		semThread = new Semaphore(-numberOfThreads +1);
	}
	
	/**
	 * Richiamo questo metodo per sapere se e possibile terminare il programma.
	 * Nel caso in cui non sia ancora possibile si rimane in attesa sull'acquire.
	 * @return
	 */
	public static boolean endProgram()
	{
		try {
			//Provo ad acquisire la risorsa e rimango in attesa di ottenerla
			semEndProgram.acquire();
			
		} catch (InterruptedException e) {
			
			//Indico al chiamante che c'e stato un errore
			return false;
		}
		
		//Indico al chiamante che puo terminare correttamente
		return true;
	}
	
	/**
	 * Metodo che rilascia una risorsa ogni volta che un thread termina la propria esecuzione
	 */
	public static synchronized void endThreadExecution()
	{
		//Poiche il thread ha terminato rilascio una risorsa
		semThread.release();
		
		//Se tutti i thread hanno terminato l'operazione di tryAcquire restituirà true
		if(semThread.tryAcquire())
		{
			//Rilascio la risorsa che blocca il chiamante
			semEndProgram.release();
		}
	}
	
}
