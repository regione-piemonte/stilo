/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.regex.Pattern;

import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

import it.eng.job.importdocindex.bean.ConfigurazioniDirectoryScansione;
import it.eng.job.importdocindex.bean.ConfigurazioniScansione;
import it.eng.job.importdocindex.bean.HandlerResultBean;
import it.eng.job.importdocindex.bean.VincoliFileScansione;
import it.eng.job.importdocindex.constants.ArchiveTypeEnum;
import it.eng.job.importdocindex.constants.ErrorInfoEnum;
import it.eng.job.importdocindex.constants.IndexFileTypeEnum;
import it.eng.job.importdocindex.constants.StatoElaborazione;
import it.eng.job.importdocindex.exceptions.ImportDocIndexException;
import it.eng.job.importdocindex.manager.Manager;
import it.eng.job.importdocindex.manager.ScansioneDirectoryManager;
import it.eng.job.util.DecompressionUtil;
import it.eng.utility.filemanager.FileBean;
import it.eng.utility.filemanager.FileManagerUtil;

public class ScansioneDirectoryManagerImpl extends Manager implements ScansioneDirectoryManager, Callable<HandlerResultBean<Void>> {

	private static final String EXTRACT = "_extract";
	private static final String SCANSIONE = "scansione_";
	private static final String ROUTINGKEY = "ROUTINGKEY";

	static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	private final ForkJoinPool forkJoinPool;

	/**
	 * Costruttore del manager, riceve in input la configurazione, che è già stata precedentemente validata
	 * 
	 * @param configurazioniScansione
	 * @throws ImportDocIndexException
	 */
	public ScansioneDirectoryManagerImpl(ConfigurazioniScansione configurazioniScansione, ForkJoinPool forkJoinPool) throws ImportDocIndexException {

		super(configurazioniScansione);

		// Pool dei thread da utilizzare per la scansione ricorsiva
		this.forkJoinPool = forkJoinPool;

	}

	public HandlerResultBean<Void> trovaFileIndice() throws ImportDocIndexException {

		return forkJoinPool.invoke(new ScansioneDirectoryTask(this.directoryPrincipale,
				this.configurazioniScansione.getConfigurazioniDirectoryScansione().getAbilitaScansioneSottoDirectory(),
				this.configurazioniScansione.getConfigurazioniDirectoryScansione().getLivelloMassimoScansioneSottoDirectory(), 0));

	}

	/**
	 * Classe per eseguire in parallelo la verifica dei file
	 * 
	 * @author Mattia Zanetti
	 *
	 */

	private class VerificaVincoliFileTask extends RecursiveTask<HandlerResultBean<Void>> {

		private static final long serialVersionUID = 504923471932769829L;
		private final File file;

		public VerificaVincoliFileTask(File file) {
			super();
			this.file = file;
		}

		@Override
		protected HandlerResultBean<Void> compute() {

			ThreadContext.put(ROUTINGKEY, SCANSIONE + configurazioniScansione.getId());

			try {
				return verificaVincoliFile(file);
			} catch (ImportDocIndexException e) {
				logger.error("Si è verificato un errore nella verifica del file {}", file.getAbsoluteFile(), e);
				HandlerResultBean<Void> result = new HandlerResultBean<>();
				result.setSuccessful(false);
				return result;
			}

		}

	}

	private class GestioneFileCompressoTask extends RecursiveTask<HandlerResultBean<Void>> {

		private static final long serialVersionUID = -1943261484274719724L;

		public GestioneFileCompressoTask(File fileCompresso) {
			super();
			this.fileCompresso = fileCompresso;
		}

		private final File fileCompresso;

		private HandlerResultBean<Void> gestioneFileCompresso(File fileCompresso) throws ImportDocIndexException {

			HandlerResultBean<Void> result = new HandlerResultBean<>();
			result.setSuccessful(false);

			try {

				logger.info("Avvio decompressione del file {}", fileCompresso.getAbsolutePath());

				// copia del file nella directory di backup, mantenendo l'alberatura
				HandlerResultBean<Void> handlerResultBeanBackup = backupFile(fileCompresso);
				if (!handlerResultBeanBackup.isSuccessful()) {
					throw new ImportDocIndexException(ErrorInfoEnum.ERRORE_BACKUP_FILE);
				}

				final Path pathLavoro = Paths.get(directoryLavoro.getAbsolutePath());
				final Path pathDestinazione = pathLavoro
						.resolve(FilenameUtils.getBaseName(fileCompresso.getName()) + "_" + FilenameUtils.getExtension(fileCompresso.getName()) + EXTRACT);
				creaDirectorySeNonEsiste(pathDestinazione);

				logger.info("Decompressione in {}", pathDestinazione);

				// decompressione del file in una directory con lo stesso nome, se esiste già viene sovrascritta
				DecompressionUtil.decompressArchive(fileCompresso, pathDestinazione);

				result.addAdditionalInformation(ADDITIONAL_INFORMATION_DIRECTORY_ESTRAZIONE, pathDestinazione.toFile());

				operazioniFinaliFileCompresso();

				result.setSuccessful(true);

			} catch (Exception e) {
				logger.error(ErrorInfoEnum.ERRORE_DECOMPRESSIONE_FILE.getDescription(), e);

				// operazioni finali sul file compresso in errore
				HandlerResultBean<Void> infoArchiviazioneHandlerResultPulizia = operazioniFinaliFile(fileCompresso, null,
						StatoElaborazione.ELABORATO_CON_ERRORI);
				if (!infoArchiviazioneHandlerResultPulizia.isSuccessful()) {
					logErroreOperazioniFinaliFileCompresso(fileCompresso);
				}

				throw new ImportDocIndexException(e, ErrorInfoEnum.ERRORE_DECOMPRESSIONE_FILE);
			}

			return result;

		}

		/**
		 * Operazioni finali sul file compresso
		 */

		private void operazioniFinaliFileCompresso() {
			// operazioni finali sul file compresso
			try {
				HandlerResultBean<Void> infoArchiviazioneHandlerResultPulizia = operazioniFinaliFile(this.fileCompresso, null,
						StatoElaborazione.ELABORATO_SENZA_ERRORI);
				if (!infoArchiviazioneHandlerResultPulizia.isSuccessful()) {
					logErroreOperazioniFinaliFileCompresso(this.fileCompresso);
				}
			} catch (Exception e) {
				logErroreOperazioniFinaliFileCompresso(this.fileCompresso);
			}
		}

		@Override
		protected HandlerResultBean<Void> compute() {
			ThreadContext.put(ROUTINGKEY, SCANSIONE + configurazioniScansione.getId());
			try {
				return gestioneFileCompresso(fileCompresso);
			} catch (ImportDocIndexException e) {
				logger.error("Si è verificato un errore nella decompressione del file {}", this.fileCompresso.getAbsoluteFile(), e);
				HandlerResultBean<Void> result = new HandlerResultBean<>();
				result.setSuccessful(false);
				return result;
			}

		}

	}

	/**
	 * Classe per eseguire in parallelo la verifica della directory
	 * 
	 * @author Mattia Zanetti
	 *
	 */

	private class ScansioneDirectoryTask extends RecursiveTask<HandlerResultBean<Void>> {

		private static final long serialVersionUID = 4272278979801291534L;
		private final File directory;
		private final Boolean ricorsione;
		private final Integer livelloMassimo;
		private final Integer livelloCorrente;

		public ScansioneDirectoryTask(File directory, Boolean ricorsione, Integer livelloMassimo, Integer livelloCorrente) throws ImportDocIndexException {

			super();
			this.directory = directory;
			this.ricorsione = ricorsione;
			this.livelloMassimo = livelloMassimo;
			this.livelloCorrente = livelloCorrente;

			verificaEsistenzaDirectory(directory);

		}

		/**
		 * Aggiunta dei file indice trovati
		 * 
		 * @param listaFileIndiceTrovati
		 * @param scanResult
		 * @param scansioneFileCompresso
		 */

		@SuppressWarnings("unchecked")
		private void aggiuntaFileIndice(List<File> listaFileIndiceTrovati, HandlerResultBean<Void> scanResult) {
			if (scanResult.isSuccessful() && scanResult.getAdditionalInformation(ADDITIONAL_INFORMATION_LISTA_FILE_INDICE) != null) {
				listaFileIndiceTrovati.addAll((List<File>) scanResult.getAdditionalInformation(ADDITIONAL_INFORMATION_LISTA_FILE_INDICE));
			}
		}

		@Override
		protected HandlerResultBean<Void> compute() {

			ThreadContext.put(ROUTINGKEY, SCANSIONE + configurazioniScansione.getId());

			HandlerResultBean<Void> result = new HandlerResultBean<>();
			List<File> listaFileIndiceTrovati = Collections.synchronizedList(new ArrayList<File>());
			List<File> listaDirectoryTrovate = Collections.synchronizedList(new ArrayList<File>());
			List<File> listaFileCompressiTrovati = Collections.synchronizedList(new ArrayList<File>());
			result.setSuccessful(false);

			try {

				List<RecursiveTask<HandlerResultBean<Void>>> forkScansioneFile = new LinkedList<>();

				for (File fileEntity : directory.listFiles()) {
					// ogni file è verificato da un task dedicato
					VerificaVincoliFileTask task = new VerificaVincoliFileTask(fileEntity);
					forkScansioneFile.add(task);
				}

				Collection<RecursiveTask<HandlerResultBean<Void>>> resultFork = ForkJoinTask.invokeAll(forkScansioneFile);
				List<RecursiveTask<HandlerResultBean<Void>>> forkScansioneDirectory = new LinkedList<>();

				// unione dei risultati della verifica del file
				for (RecursiveTask<HandlerResultBean<Void>> task : resultFork) {

					// recupero dell'esito del risultato del task
					HandlerResultBean<Void> scanResult = task.get();
					if (scanResult.isSuccessful()) {

						// aggiunta degli eventuali file indice trovati
						aggiuntaFileIndice(listaFileIndiceTrovati, scanResult);

						// aggiunta alla lista delle sottodirectory trovate
						aggiuntaSottoDirectory(listaDirectoryTrovate, scanResult);

						// aggiunta alla lista dei file compressi trovati
						aggiuntaFileCompresso(listaFileCompressiTrovati, scanResult);

					}

				}

				// scansione ricorsiva delle sottodirectory trovate in task paralleli
				aggiuntaFileIndiceDaSottoDirectory(forkScansioneDirectory, listaDirectoryTrovate);

				// estrazione e scansione dei file compressi
				aggiuntaFileIndiceDaFileCompresso(forkScansioneDirectory, listaFileCompressiTrovati);

				// unione dei risultati delle sottodirectory
				for (RecursiveTask<HandlerResultBean<Void>> taskDirectory : forkScansioneDirectory) {
					HandlerResultBean<Void> scanResultDirectry = taskDirectory.join();
					if (scanResultDirectry.isSuccessful()) {
						aggiuntaFileIndice(listaFileIndiceTrovati, scanResultDirectry);
					}
				}

				// indici trovati
				result.addAdditionalInformation(ADDITIONAL_INFORMATION_LISTA_FILE_INDICE, listaFileIndiceTrovati);
				result.setSuccessful(true);

			} catch (ImportDocIndexException | InterruptedException | ExecutionException e) {
				logger.error("Si è verificato un errore nel task di verifica della directory {}", directory.getAbsoluteFile(), e);
				result = new HandlerResultBean<>();
				result.setSuccessful(false);
			}

			return result;

		}

		/**
		 * @param scanResult
		 * @param forkScansioneDirectory
		 * @param listaDirectory
		 * @throws ImportDocIndexException
		 */

		private void aggiuntaFileIndiceDaSottoDirectory(final List<RecursiveTask<HandlerResultBean<Void>>> forkScansioneDirectory, List<File> listaDirectory)
				throws ImportDocIndexException {

			if (listaDirectory != null && !listaDirectory.isEmpty()) {
				logger.info("Sottodirectory da analizzare: {}", listaDirectory);
				logger.info("Livello ricorsione: {}", livelloMassimo);
				for (File subdirectory : listaDirectory) {
					// scansione sottodirectory con task ricorsivi in multithreading
					ScansioneDirectoryTask scansioneDirectoryTask = new ScansioneDirectoryTask(subdirectory, ricorsione, livelloMassimo, livelloCorrente + 1);
					forkScansioneDirectory.add(scansioneDirectoryTask);
					scansioneDirectoryTask.fork();
				}
			}
		}

		/**
		 * @param scanResult
		 * @param forkScansioneDirectory
		 * @throws ImportDocIndexException
		 */

		@SuppressWarnings("unchecked")
		private void aggiuntaSottoDirectory(List<File> listaDirectoryTrovate, final HandlerResultBean<Void> scanResult) {

			// aggiungo file indice trovati dalle sotto-directory se è attiva la scansione ricorsiva delle sottodirectory
			if (ricorsione && (livelloMassimo == ConfigurazioniDirectoryScansione.NESSUN_LIMITE_SCANSIONE_SOTTODIRECTORY || livelloCorrente <= livelloMassimo)
					&& scanResult.getAdditionalInformation(ADDITIONAL_INFORMATION_LISTA_DIRECTORY) != null) {
				listaDirectoryTrovate.addAll((List<File>) scanResult.getAdditionalInformation(ADDITIONAL_INFORMATION_LISTA_DIRECTORY));
			}
		}

		/**
		 * @param scanResult
		 * @param forkScansioneDirectory
		 */

		private void aggiuntaFileIndiceDaFileCompresso(final List<RecursiveTask<HandlerResultBean<Void>>> forkScansioneDirectory,
				List<File> listaFileCompressi) {

			if (listaFileCompressi != null && !listaFileCompressi.isEmpty()) {
				logger.info("File compressi trovati: {}", listaFileCompressi);

				List<RecursiveTask<HandlerResultBean<Void>>> listaForkEstrazione = new LinkedList<>();

				for (File fileCompresso : listaFileCompressi) {
					// elaborazione del file compresso in un thread specifico
					GestioneFileCompressoTask gestioneFileCompressoTask = new GestioneFileCompressoTask(fileCompresso);
					listaForkEstrazione.add(gestioneFileCompressoTask);
					gestioneFileCompressoTask.fork();
				}

				for (RecursiveTask<HandlerResultBean<Void>> future : listaForkEstrazione) {
					// risultato dell'estrazione
					try {
						ottieniIndiciDirectoryEstrazione(forkScansioneDirectory, future.join());
					} catch (ImportDocIndexException e) {
						logger.error("Si è verificato un errore nel task di estrazione", e);
					}
				}
			}
		}

		/**
		 * @param scanResult
		 * @param forkScansioneDirectory
		 */

		@SuppressWarnings("unchecked")
		private void aggiuntaFileCompresso(List<File> listaFileCompressiTrovati, HandlerResultBean<Void> scanResult) {

			// verifico se ci sono file compressi da processare
			if (scanResult.getAdditionalInformation(ADDITIONAL_INFORMATION_LISTA_FILE_COMPRESSI) != null) {
				// aggiungo file indice recuperati da file compressi
				listaFileCompressiTrovati.addAll((List<File>) scanResult.getAdditionalInformation(ADDITIONAL_INFORMATION_LISTA_FILE_COMPRESSI));
			}
		}

		/**
		 * @param forkScansioneDirectory
		 * @param scansioneFileCompresso
		 * @throws ImportDocIndexException
		 */

		private void ottieniIndiciDirectoryEstrazione(List<RecursiveTask<HandlerResultBean<Void>>> forkScansioneDirectory,
				HandlerResultBean<Void> scansioneFileCompresso) throws ImportDocIndexException {
			if (scansioneFileCompresso.isSuccessful() && scansioneFileCompresso.getAdditionalInformation(ADDITIONAL_INFORMATION_DIRECTORY_ESTRAZIONE) != null) {
				File directoryEstrazione = (File) scansioneFileCompresso.getAdditionalInformation(ADDITIONAL_INFORMATION_DIRECTORY_ESTRAZIONE);
				// per un file compresso devo scandire la directory stessa e almeno un altro livello anche se la scansione ricorsiva è
				// disattivata
				Integer nuovoLivelloMassimo = livelloMassimo;
				if (livelloMassimo != ConfigurazioniDirectoryScansione.NESSUN_LIMITE_SCANSIONE_SOTTODIRECTORY) {
					nuovoLivelloMassimo = Math.max(1, livelloMassimo);
				}
				ScansioneDirectoryTask scansioneDirectoryTask = new ScansioneDirectoryTask(directoryEstrazione, true, nuovoLivelloMassimo, 0);
				forkScansioneDirectory.add(scansioneDirectoryTask);
				scansioneDirectoryTask.fork();
			}
		}

	}

	/**
	 * @param fileCompresso
	 */
	protected void logErroreOperazioniFinaliFileCompresso(File fileCompresso) {
		logErroreEliminazioneFileCompressso(fileCompresso);
	}

	/**
	 * @param fileCompresso
	 */
	protected void logErroreEliminazioneFileCompressso(File fileCompresso) {
		logger.warn("Errore nelle operazioni di fine lavorazione sul file compresso {}", fileCompresso.getAbsolutePath());
	}

	@Override
	public HandlerResultBean<Void> call() throws Exception {

		final String id = this.configurazioniScansione.getId();
		ThreadContext.put(ROUTINGKEY, SCANSIONE + id);
		Thread.currentThread().setName("Scansione-" + id);
		return trovaFileIndice();
	}

	public HandlerResultBean<Void> verificaVincoliFile(File file) throws ImportDocIndexException {
		return verificaVincoliFile(file, this.configurazioniScansione.getVincoliFileScansione(),
				this.configurazioniScansione.getConfigurazioniDirectoryScansione().getAbilitaDecompressione());
	}

	/**
	 * Metodo che verifica se un file o una directory soddisfa le condizioni imposte nella configurazione e in caso lo aggiunge alla lista delle directory
	 * 
	 * @throws ImportDocIndexException
	 */

	private HandlerResultBean<Void> verificaVincoliFile(final File file, final VincoliFileScansione vincoliFileScansione, final Boolean includiCompressi)
			throws ImportDocIndexException {

		HandlerResultBean<Void> result = new HandlerResultBean<>();
		result.setSuccessful(false);
		Boolean validazione = true;

		try {

			if (!file.exists()) {
				throw new FileNotFoundException();
			}

			// Gestisco tutto tramite lista anche se al momento ho un solo file
			// Generalizzo in modo da gestire anche sviluppi futuri che potrebbero prevedere più file (esempio decompressione immediata)

			List<File> listaFileIndiceTrovati = new LinkedList<>();
			List<File> listaFileCompressi = new LinkedList<>();
			List<File> listaDirectory = new LinkedList<>();

			logger.info("Analisi del file: {}", file.getAbsolutePath());
			logger.info("Nome del file: {}", file.getName());

			logVincoliFile(vincoliFileScansione);

			// Verifica se il file ha un nome o un percorso assoluto non ammesso
			validazione = validaVincoliEsclusioni(file, vincoliFileScansione);

			// Verifica se il file soddisfa i pattern ammessi per il nome e il percorso assoluto
			validazione = validaPattern(file, validazione, vincoliFileScansione);

			// soddisfatte tutte le condizioni per una directory
			if (validazione) {

				if (file.isDirectory()) {

					listaDirectory.add(file);

				} else {

					// verifica estensione del file
					validazione = validaEstensione(file, validazione, vincoliFileScansione);

					javax.activation.MimeType mimeType = Manager.ottieniMimeType(file);
					logger.info("Mimetype rilevato per il file {} : {}", file.getAbsolutePath(), mimeType.getBaseType());
					// verifica mime type
					validazione = validaMimeType(file, validazione, vincoliFileScansione, mimeType);

					if (validazione) {

						gestioneFileValido(file, includiCompressi, listaFileIndiceTrovati, listaFileCompressi, mimeType);

					}
				}
			}

			aggiuntaListeFile(result, listaFileIndiceTrovati, listaFileCompressi, listaDirectory);

		} catch (Exception e) {
			logger.error(ErrorInfoEnum.ERRORE_SCANSIONE_FILE_INDICE.getDescription(), e);
			throw new ImportDocIndexException(e, ErrorInfoEnum.ERRORE_SCANSIONE_FILE_INDICE);
		}

		result.setSuccessful(true);
		return result;

	}

	/**
	 * Gestione file che ha soddisfatto i vincoli, ultima operazione da fare è la verifica se si tratti di un file compresso o un file indice
	 * 
	 * @param file
	 * @param includiCompressi
	 * @param listaFileIndiceTrovati
	 * @param listaFileCompressi
	 * @param mimeType
	 */

	private void gestioneFileValido(final File file, final Boolean includiCompressi, final List<File> listaFileIndiceTrovati,
			final List<File> listaFileCompressi, javax.activation.MimeType mimeType) {

		// verifico se il file è un indice compatibile con la scansione corrente
		IndexFileTypeEnum mimeTypeIndice = IndexFileTypeEnum.fromMimeType(mimeType.getBaseType());

		if (mimeTypeIndice != null) {
			// si tratta di un file indice da elaborare
			listaFileIndiceTrovati.add(file);
			logger.info("Trovato file indice {}", file.getAbsolutePath());

		} else if (includiCompressi) {

			// verifico se il file è compresso
			ArchiveTypeEnum mimeTypeCompressione = ArchiveTypeEnum.fromMimeType(mimeType.getBaseType());
			if (mimeTypeCompressione != null) {
				listaFileCompressi.add(file);
				logger.info("Trovato file compresso {}", file.getAbsolutePath());

			}
		}
	}

	/**
	 * Log (livello debug) dei vincoli da rispettare dal file
	 * 
	 * @param vincoliFileScansione
	 */
	private void logVincoliFile(final VincoliFileScansione vincoliFileScansione) {

		logger.debug("Escludo file aventi path assoluto: {}", vincoliFileScansione.getListaPercorsiFileAssolutiDaEscludere());
		logger.debug("Escludo file aventi nome: {}", vincoliFileScansione.getListaNomiFileDaEscludere());
		logger.debug("Pattern nomi file ammessi: {}", vincoliFileScansione.getListaPatternNomeFile());
		logger.debug("Pattern percorsi assoluti file ammessi: {}", vincoliFileScansione.getListaPatternPercorsiFileAssoluti());
		logger.debug("Estensioni ammesse: {}", vincoliFileScansione.getListaEstensioni());
		logger.debug("MimeType ammessi: {}", vincoliFileScansione.getListaMimeType());
	}

	/**
	 * Aggiunta delle informazioni aggiuntive con il risultato della validazione del file
	 * 
	 * @param result
	 * @param listaFileIndiceTrovati
	 * @param listaFileCompressi
	 * @param listaDirectory
	 */

	private void aggiuntaListeFile(final HandlerResultBean<Void> result, final List<File> listaFileIndiceTrovati, final List<File> listaFileCompressi,
			final List<File> listaDirectory) {

		result.addAdditionalInformation(ADDITIONAL_INFORMATION_LISTA_DIRECTORY, listaDirectory);
		result.addAdditionalInformation(ADDITIONAL_INFORMATION_LISTA_FILE_INDICE, listaFileIndiceTrovati);
		result.addAdditionalInformation(ADDITIONAL_INFORMATION_LISTA_FILE_COMPRESSI, listaFileCompressi);
	}

	/**
	 * @param file
	 * @param vincoliFileScansione
	 * @return
	 */
	private Boolean validaVincoliEsclusioni(File file, final VincoliFileScansione vincoliFileScansione) {

		Boolean validazione = !vincoliFileScansione.getListaPercorsiFileAssolutiDaEscludere().contains(file.getAbsolutePath())
				&& !vincoliFileScansione.getListaNomiFileDaEscludere().contains(file.getName());

		if (!validazione) {
			logger.info("Escludo il file: {} - il nome file o il percorso assoluto fa parte di quelli esclusi", file.getAbsolutePath());
		}

		return validazione;
	}

	/**
	 * @param file
	 * @param vincoliFileScansione
	 * @param mimeType
	 * @return
	 */
	private Boolean validaMimeType(File file, Boolean validazione, final VincoliFileScansione vincoliFileScansione, javax.activation.MimeType mimeType) {

		if (validazione) {

			validazione = vincoliFileScansione.getListaMimeType().isEmpty() || vincoliFileScansione.getListaMimeType().contains(mimeType.getBaseType());

			if (!validazione) {

				logger.info("Escludo il file: {} - mimetype {} del file non ammesso", file.getAbsolutePath(), mimeType.getBaseType());

			}

		}
		return validazione;
	}

	/**
	 * @param file
	 * @param validazione
	 * @param vincoliFileScansione
	 * @return
	 */
	private Boolean validaEstensione(File file, Boolean validazione, final VincoliFileScansione vincoliFileScansione) {

		if (validazione) {

			String estensione = FilenameUtils.getExtension(file.getName()).toLowerCase();
			validazione = vincoliFileScansione.getListaEstensioni().isEmpty() || vincoliFileScansione.getListaEstensioni().contains(estensione);

			if (!validazione) {
				logger.info("Escludo il file: {} - estensione del file non ammessa", file.getAbsolutePath());
			}

		}
		return validazione;
	}

	/**
	 * @param file
	 * @param validazione
	 * @param vincoliFileScansione
	 * @return
	 */
	private Boolean validaPattern(File file, Boolean validazione, final VincoliFileScansione vincoliFileScansione) {

		// Verifico che il nome del file sia incluso nei pattern ammessi

		if (validazione) {

			Boolean isValidPattern = vincoliFileScansione.getListaPatternNomeFile().isEmpty();

			for (int indicePattern = 0; indicePattern < vincoliFileScansione.getListaPatternNomeFile().size() && !isValidPattern; indicePattern++) {
				Pattern pattern = Pattern.compile(vincoliFileScansione.getListaPatternNomeFile().get(indicePattern));
				isValidPattern = pattern.matcher(file.getName()).find();
			}

			validazione = isValidPattern;

			if (!isValidPattern) {
				logger.info("Escludo il file: {} - nome file non soddisfa i pattern ammessi", file.getAbsolutePath());
			}

		}

		// Verifico che il percorso assoluto del file sia incluso nei pattern ammessi

		if (validazione) {

			Boolean isValidPattern = vincoliFileScansione.getListaPatternPercorsiFileAssoluti().isEmpty();

			for (int indicePattern = 0; indicePattern < vincoliFileScansione.getListaPatternPercorsiFileAssoluti().size() && !isValidPattern; indicePattern++) {
				Pattern pattern = Pattern.compile(vincoliFileScansione.getListaPatternPercorsiFileAssoluti().get(indicePattern));
				isValidPattern = pattern.matcher(file.getAbsolutePath()).find();
			}

			validazione = isValidPattern;

			if (!isValidPattern) {
				logger.info("Escludo il file: {} - percorso assoluto del file non soddisfa i pattern ammessi", file.getAbsolutePath());
			}

		}
		return validazione;
	}

	/**
	 * Verifica esistenza directory
	 * 
	 * @param directory
	 * @throws ImportDocIndexException
	 */

	protected final void verificaEsistenzaDirectory(final File directory) throws ImportDocIndexException {

		try {
			FileBean directoryBean = FileManagerUtil.createFromFile(directory);
			if (!fileManager.isDirectory(directoryBean)) {
				throw new FileNotFoundException();
			}
		} catch (Exception e) {
			logger.error("Si è verificato un errore nella verifica della directory {}", directory, e);
			throw new ImportDocIndexException(e, ErrorInfoEnum.ERRORE_SCANSIONE_FILE_INDICE);
		}
	}

}
