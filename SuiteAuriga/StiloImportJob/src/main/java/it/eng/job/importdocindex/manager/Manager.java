/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.activation.MimeTypeParseException;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.cfg.NotYetImplementedException;

import it.eng.job.importdocindex.bean.ConfigurazioniDirectoryScansione;
import it.eng.job.importdocindex.bean.ConfigurazioniScansione;
import it.eng.job.importdocindex.bean.HandlerResultBean;
import it.eng.job.importdocindex.constants.ErrorInfoEnum;
import it.eng.job.importdocindex.constants.FilePostManageActionEnum;
import it.eng.job.importdocindex.constants.StatoElaborazione;
import it.eng.job.importdocindex.exceptions.ImportDocIndexException;
import it.eng.job.util.AurigaFileUtils;
import it.eng.utility.filemanager.FileManager;
import it.eng.utility.filemanager.FileManagerException;
import it.eng.utility.filemanager.FileManagerFactory;
import it.eng.utility.filemanager.FileManagerUtil;

/**
 * Classe astratta comune con metodi e proprietà comuni ai manager dedicati alla scansione dei file indice
 * 
 * @author Mattia Zanetti
 *
 */

public abstract class Manager {

	static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	protected final FileManager fileManager;
	protected final ConfigurazioniScansione configurazioniScansione;
	protected final File directoryPrincipale;
	protected final File directoryBackup;
	protected final File directoryLavoro;
	protected final File directoryErrori;
	protected final File directoryElaborati;

	public Manager(ConfigurazioniScansione configurazioniScansione) throws ImportDocIndexException {

		super();

		this.configurazioniScansione = configurazioniScansione;

		final ConfigurazioniDirectoryScansione configurazioniDirectoryScansione = this.configurazioniScansione.getConfigurazioniDirectoryScansione();
		try {
			this.fileManager = FileManagerFactory.getFileManager(configurazioniDirectoryScansione.getConfigurazioniFileManager());
		} catch (NotYetImplementedException | FileManagerException e) {
			throw new ImportDocIndexException(e, ErrorInfoEnum.VALIDAZIONE_CONFIGURAZIONE_FILE_MANAGER);
		}

		this.directoryPrincipale = new File(configurazioniDirectoryScansione.getDirectoryRadice());

		// directory di backup è opzionale
		if (StringUtils.isNotBlank(configurazioniDirectoryScansione.getDirectoryBackup())) {
			this.directoryBackup = new File(configurazioniDirectoryScansione.getDirectoryBackup());
		} else {
			this.directoryBackup = null;
		}

		// se non valorizzata, la directory di lavoro coincide con la directory di lavoro
		if (StringUtils.isNotBlank(configurazioniDirectoryScansione.getDirectoryLavoro())) {
			this.directoryLavoro = new File(configurazioniDirectoryScansione.getDirectoryLavoro());
		} else {
			this.directoryLavoro = this.directoryPrincipale;
		}

		// directory di scarto è opzionale
		if (StringUtils.isNotBlank(configurazioniDirectoryScansione.getDirectoryErrori())) {
			this.directoryErrori = new File(configurazioniDirectoryScansione.getDirectoryErrori());
		} else {
			this.directoryErrori = null;
		}

		// directory finale è opzionale
		if (StringUtils.isNotBlank(configurazioniDirectoryScansione.getDirectoryElaborati())) {
			this.directoryElaborati = new File(configurazioniDirectoryScansione.getDirectoryElaborati());
		} else {
			this.directoryElaborati = null;
		}

	}

	/**
	 * Verifica se il file in input esiste ed è leggibile
	 * 
	 * @param file
	 * @throws ImportDocIndexException
	 */

	protected static void verificaFile(final File file) throws ImportDocIndexException {

		if (!file.exists()) {
			throw new ImportDocIndexException(ErrorInfoEnum.ERRORE_FILE_NON_TROVATO);
		}

		if (!Files.isReadable(file.toPath())) {
			throw new ImportDocIndexException(ErrorInfoEnum.ERRORE_FILE_NON_ACCESSIBILE);
		}

		if (!Files.isWritable(file.toPath())) {
			throw new ImportDocIndexException(ErrorInfoEnum.ERRORE_FILE_NON_ACCESSIBILE);
		}
	}

	/**
	 * Backup del file in input, mantenendo l'alberatura rispetto alla directory principale
	 * 
	 * @param file
	 * @throws FileManagerException
	 * @throws ImportDocIndexException
	 * @throws IOException
	 */
	protected HandlerResultBean<Void> backupFile(File file) throws FileManagerException, ImportDocIndexException {

		HandlerResultBean<Void> result = new HandlerResultBean<>();
		result.setSuccessful(false);

		// verifico se il backup va effettuato
		if (this.directoryBackup != null) {

			try {
				// backup mantenendo l'alberatura
				this.backupFile(file, this.directoryBackup, this.directoryPrincipale, this.directoryLavoro);
				result.setSuccessful(true);
			} catch (Exception e) {
				logger.error(ErrorInfoEnum.ERRORE_BACKUP_FILE.getDescription(), e);
				throw new ImportDocIndexException(e, ErrorInfoEnum.ERRORE_BACKUP_FILE);
			}

		} else {
			logger.info("Backup non richiesto per il file {}", file.getAbsolutePath());
		}

		return result;

	}

	/**
	 * Azioni di fine lavorazione per il file in input
	 * 
	 * @param statoElaborazione
	 * @throws ImportDocIndexException
	 */

	protected HandlerResultBean<Void> operazioniFinaliFile(final File file, final File fileLavoro, final StatoElaborazione statoElaborazione)
			throws ImportDocIndexException {

		HandlerResultBean<Void> result = new HandlerResultBean<>();
		result.setSuccessful(false);

		try {

			final ConfigurazioniDirectoryScansione configurazioniDirectoryScansione = this.configurazioniScansione.getConfigurazioniDirectoryScansione();
			final FilePostManageActionEnum azioneFile = configurazioniDirectoryScansione.getAzioneFile();

			// in caso di errori sposto comunque nella directory di errori, che deve sempre essere configurata
			if (statoElaborazione.equals(StatoElaborazione.ELABORATO_CON_ERRORI) && !azioneFile.equals(FilePostManageActionEnum.KEEP)) {
				spostaFile(file, this.directoryErrori);
				logger.info("File {} spostato nella directory dei file in errore", file.getAbsolutePath());
			} else {
				if (azioneFile.equals(FilePostManageActionEnum.KEEP)) {
					logger.info("Il file {} va lasciato nella directory originale", file.getAbsolutePath());
				} else if (azioneFile.equals(FilePostManageActionEnum.MOVE)) {
					spostaFile(file, this.directoryElaborati);
				} else if (azioneFile.equals(FilePostManageActionEnum.DELETE)) {
					eliminaFile(file);
				} else if (azioneFile.equals(FilePostManageActionEnum.UPDATE_AND_MOVE)) {
					if (fileLavoro != null) {
						// elimino il file originale
						eliminaFile(file);
						// sposto il file con i risultati dell'elaborazione dalla directory di lavoro
						spostaFile(fileLavoro, this.directoryElaborati);
					} else {
						spostaFile(file, this.directoryElaborati);
					}
				}
			}

			result.setSuccessful(true);
		}

		catch (Exception e) {
			logger.error(ErrorInfoEnum.ERRORE_OPERAZIONI_FINALI_FILE.getDescription(), e);
			throw new ImportDocIndexException(e, ErrorInfoEnum.ERRORE_OPERAZIONI_FINALI_FILE);
		}

		return result;
	}

	/**
	 * Backup del {@link input} nella directory {@link destinazione}, mantenendo l'alberatura rispetto a {@link radice}
	 * 
	 * @param file
	 * @throws FileManagerException
	 * @throws ImportDocIndexException
	 * @throws IOException
	 */

	private final void backupFile(File file, File directoryDestinazioneBackup, File directoryRadice, File directoryLavoro)
			throws FileManagerException, ImportDocIndexException {

		logger.info("Backup del file {}", file.getAbsolutePath());

		Path pathDestinazioneBackup = restituisciDirectoryDestinazioneConAlberatura(file, directoryDestinazioneBackup, directoryRadice, directoryLavoro);
		logger.info("Destinazione backup: {}", pathDestinazioneBackup);

		if (!this.fileManager.copyFile(FileManagerUtil.createFromFile(file), FileManagerUtil.createFromFile(pathDestinazioneBackup.toFile()))) {
			throw new ImportDocIndexException(ErrorInfoEnum.ERRORE_BACKUP_FILE);
		} else {
			logger.info("Backup del file {} effettuato in {}", file.getAbsolutePath(), pathDestinazioneBackup);
		}
	}

	/**
	 * Calcola l'alberatura delle sottodirectory del file rispetto alla directory radice o alla directory di lavoro in base a dove si trova il file. <br>
	 * Crea le sottodirectory necessarie per mantenere l'alberatura anche nella directory di destinazione e ne restituisce il percorso
	 * 
	 * @param file
	 * @param destinazione
	 * @param radice
	 * @return
	 * @throws ImportDocIndexException
	 */

	protected Path restituisciDirectoryDestinazioneConAlberatura(File file, File directoryDestinazione, File directoryRadice, File directoryLavoro)
			throws ImportDocIndexException {

		final Path pathDirectoryRadice = directoryRadice.toPath();
		final Path pathDirectoryPadreFile = file.getParentFile().toPath();
		Path pathAlberatura = null;
		Path pathDirectoryDestinazione = directoryDestinazione.toPath();
		if (!pathDirectoryPadreFile.equals(pathDirectoryRadice) && pathDirectoryPadreFile.startsWith(pathDirectoryRadice)) {
			// se il file fa parte della radice allora estraggo l'alberatura
			pathAlberatura = pathDirectoryRadice.relativize(pathDirectoryPadreFile);
		} else if (directoryLavoro != null && !pathDirectoryPadreFile.equals(directoryLavoro.toPath())
				&& pathDirectoryPadreFile.startsWith(directoryLavoro.toPath())) {
			// altrimenti provo con la directory di lavoro
			pathAlberatura = directoryLavoro.toPath().relativize(pathDirectoryPadreFile);
		}
		// se il file è già nella radice non ho alberatura
		if (pathAlberatura != null) {
			pathDirectoryDestinazione = pathDirectoryDestinazione.resolve(pathAlberatura);
		}
		this.creaDirectorySeNonEsiste(pathDirectoryDestinazione);
		return pathDirectoryDestinazione;
	}

	/**
	 * Spostamento del {@link input} nella directory {@link destinazione}, mantenendo l'alberatura rispetto alla directory principale
	 * 
	 * @param file
	 * @throws FileManagerException
	 * @throws ImportDocIndexException
	 */

	protected final void spostaFile(File file, File destinazione) throws FileManagerException, ImportDocIndexException {
		this.spostaFile(file, destinazione, this.directoryPrincipale, this.directoryLavoro);
	}

	/**
	 * Spostamento del {@link input} nella directory {@link destinazione}, mantenendo l'alberatura rispetto a {@link radice}
	 * 
	 * @param file
	 * @throws FileManagerException
	 * @throws ImportDocIndexException
	 */

	protected final void spostaFile(final File file, final File directoryDestinazione, final File directoryRadice, final File directoryLavoro)
			throws FileManagerException, ImportDocIndexException {

		Path pathDestinazione = restituisciDirectoryDestinazioneConAlberatura(file, directoryDestinazione, directoryRadice, directoryLavoro);

		if (!this.fileManager.moveFile(FileManagerUtil.createFromFile(file), FileManagerUtil.createFromFile(pathDestinazione.toFile()))) {
			throw new ImportDocIndexException(ErrorInfoEnum.ERRORE_SPOSTAMENTO_FILE);
		} else {
			logger.info("File {} spostato in {}", file.getAbsolutePath(), pathDestinazione);
		}
	}

	/**
	 * Eliminazione del file in input
	 * 
	 * @param file
	 * @throws FileManagerException
	 * @throws ImportDocIndexException
	 */

	protected final void eliminaFile(File file) throws FileManagerException, ImportDocIndexException {

		if (!this.fileManager.deleteFile(FileManagerUtil.createFromFile(file))) {
			throw new ImportDocIndexException(ErrorInfoEnum.ERRORE_ELIMINAZIONE_FILE);
		} else {
			logger.info("Eliminazione del file {} effettuata", file.getAbsolutePath());
		}
	}

	/**
	 * Crea una directory nel percorso specificato, se non esiste già
	 * 
	 * @param newPath
	 * @throws IOException
	 * @throws ImportDocIndexException
	 */

	public void creaDirectorySeNonEsiste(Path newPath) throws ImportDocIndexException {
		try {
			if (!this.fileManager.createDirectory(newPath)) {
				throw new ImportDocIndexException(ErrorInfoEnum.ERRORE_CREAZIONE_DIRECTORY);
			}
		} catch (Exception e) {
			logger.error(ErrorInfoEnum.ERRORE_CREAZIONE_DIRECTORY, e);
			throw new ImportDocIndexException(ErrorInfoEnum.ERRORE_CREAZIONE_DIRECTORY);
		}
	}

	/**
	 * Restituisce il mime type del file in input
	 * 
	 * @param file
	 * @return
	 * @throws ImportDocIndexException
	 */
	public static javax.activation.MimeType ottieniMimeType(File file) throws ImportDocIndexException {
		javax.activation.MimeType mimeType;
		try {
			mimeType = AurigaFileUtils.getMimeType(file);
		} catch (IOException | MimeTypeParseException e1) {
			throw new ImportDocIndexException(e1, ErrorInfoEnum.ERRORE_RECUPERO_MIME_TYPE);
		}
		return mimeType;
	}

	protected FileManager getFileManager() {
		return fileManager;
	}

	protected ConfigurazioniScansione getConfigurazioniScansione() {
		return configurazioniScansione;
	}

	protected File getDirectoryPrincipale() {
		return directoryPrincipale;
	}

	protected File getDirectoryBackup() {
		return directoryBackup;
	}

	protected File getDirectoryLavoro() {
		return directoryLavoro;
	}

	protected File getDirectoryErrori() {
		return directoryErrori;
	}

	protected File getDirectoryElaborati() {
		return directoryElaborati;
	}

}
