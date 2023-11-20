/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import it.eng.job.exception.AurigaJobException;
import it.eng.utility.storageutil.Storage;
import it.eng.utility.storageutil.exception.StorageException;
import it.eng.utility.storageutil.util.StorageUtil;

public class StorageMover {

	private static Logger log = Logger.getLogger(StorageMover.class); // logger

	public StorageMover() {
	}

	/**
	 * Metodo che, dati in input il file corrente e il path di destinazione,
	 * ritorna il file contenente il path di destinazione CON alberatura.
	 * 
	 * @param currentFile
	 * @param destinationFile
	 * @return
	 * @throws Exception
	 */
	public static File getMoveDirectoryConAlberatura(File currentFile, File destinationFile) throws Exception {

		if (destinationFile != null) {

			/*
			 * Assegno il path del file di destinazione. Se il path è già
			 * risolto allora andrà in eccezione nel try e la variabile
			 * pathResolved conterrà già il valore corretto, altrimenti verrà
			 * riassegnato il valore del path risolto
			 */
			String pathResolved = destinationFile.getPath();

			try {
				/*
				 * Risolvo l'indirizzo per identificare dove si trova la root
				 * dove creare l'alberatura e spostare il file
				 */
				pathResolved = resolveStoragePath(destinationFile);
			} catch (AurigaJobException e) {
				if (log.isInfoEnabled()) {
					log.info("Non è necessario risolvere il path tramite StorageUtil");
				}
			} catch (StorageException e) {
				log.info("Eccezione nel recupero dello storage");
				throw e;
			}

			/*
			 * directoryPrimoLivello conterrà il path comprensivo della cartella
			 * in cui si trova il file da spostare directorySecondoLivello
			 * conterrà il path comprensivo della cartella in cui si trova la
			 * directory directoryPrimoLivello directoryTerzoLivello conterrà il
			 * path comprensivo della cartella in cui si trova la directory
			 * directorySecondoLivello
			 */
			File directoryPrimoLivello = currentFile.getParentFile();
			File directorySecondoLivello = directoryPrimoLivello.getParentFile();
			File directoryTerzoLivello = directorySecondoLivello.getParentFile();
			File directoryQuartoLivello = directoryTerzoLivello.getParentFile();

			// Aggiungo al path di destinazione la cartella relativa al quarto
			// livello
			pathResolved = addSeparatorToPath(pathResolved);
			pathResolved += directoryQuartoLivello.getName();

			// Aggiungo al path di destinazione la cartella relativa al terzo
			// livello
			pathResolved = addSeparatorToPath(pathResolved);
			pathResolved += directoryTerzoLivello.getName();

			// Aggiungo al path di destinazione la cartella relativa al secondo
			// livello
			pathResolved = addSeparatorToPath(pathResolved);
			pathResolved += directorySecondoLivello.getName();

			// Aggiungo al path di destinazione la cartella relativa al primo
			// livello
			pathResolved = addSeparatorToPath(pathResolved);
			pathResolved += directoryPrimoLivello.getName();

			File directory = new File(pathResolved);

			if (!directory.exists()) {
				if (!directory.mkdirs()) {
					throw new Exception(
							String.format("Non è stato possibile creare la directory %1$s per spostare il file %2$s",
									directory.getName(), currentFile.getAbsolutePath()));
				}
			}

			// Ritorno il path ora risolto
			return directory;

		} else {
			return null;
		}
	}

	/**
	 * Metodo che, dati in input il file corrente e il path di destinazione,
	 * ritorna il file contenente il path di destinazione SENZA alberatura.
	 * 
	 * @param currentFile
	 * @param destinationFile
	 * @return
	 * @throws Exception
	 */
	public static File getMoveDirectorySenzaAlberatura(File currentFile, File destinationFile) throws Exception {
		if (destinationFile != null) {

			/*
			 * Risolvo l'indirizzo per identificare dove si trova la root dove
			 * creare l'alberatura e spostare il file
			 */
			String pathResolved = destinationFile.getPath();

			try {
				/*
				 * Risolvo l'indirizzo per identificare dove si trova la root
				 * dove creare l'alberatura e spostare il file
				 */
				pathResolved = resolveStoragePath(destinationFile);
			} catch (AurigaJobException e) {
				if (log.isInfoEnabled()) {
					log.info("Non è necessario risolvere il path tramite StorageUtil");
				}
			} catch (StorageException e) {
				log.info("Eccezione nel recupero dello storage");
				throw e;
			}

			pathResolved = addSeparatorToPath(pathResolved);

			/*
			 * directoryPrimoLivello conterrà il path comprensivo della cartella
			 * in cui si trova il file da spostare directorySecondoLivello
			 * conterrà il path comprensivo della cartella in cui si trova la
			 * directory directoryPrimoLivello directoryTerzoLivello conterrà il
			 * path comprensivo della cartella in cui si trova la directory
			 * directorySecondoLivello
			 */
			File directoryPrimoLivello = currentFile.getParentFile();
			File directorySecondoLivello = directoryPrimoLivello.getParentFile();
			File directoryTerzoLivello = directorySecondoLivello.getParentFile();
			File directoryQuartoLivello = directoryTerzoLivello.getParentFile();

			// Aggiungo al path di destinazione la cartella relativa al quarto
			// livello
			pathResolved += directoryQuartoLivello.getName();

			// Aggiungo al path di destinazione la cartella relativa al terzo
			// livello
			pathResolved += directoryTerzoLivello.getName();

			// Aggiungo al path di destinazione la cartella relativa al secondo
			// livello
			pathResolved += directorySecondoLivello.getName();

			// Aggiungo al path di destinazione la cartella relativa al primo
			// livello
			pathResolved += directoryPrimoLivello.getName();

			File directory = new File(pathResolved);

			return directory;

		} else {
			return null;
		}
	}

	/**
	 * Metodo che, dato in input un file, risolve lo storage e ritorna il path
	 * fisico del file
	 * 
	 * @param destinationFile
	 *            file il cui percorso deve essere risolto
	 * @return path fisico del file che era stato inviato
	 * @throws AurigaJobException
	 * @throws StorageException
	 * 
	 */
	public static String resolveStoragePath(File destinationFile) throws AurigaJobException, StorageException {
		String[] lStrResult = StorageUtil.resolveStorageUri(destinationFile.getPath());
		if (lStrResult == null || lStrResult.length != 2 || StringUtils.isEmpty(lStrResult[0])) {
			throw new AurigaJobException("Impossibile recuperare la cartella di destinazione dallo storage");
		}
		Storage lStorage = null;
		try {

			lStorage = StorageUtil.getConfiguredStorageInDB(lStrResult[0]);
			return lStorage.retrieveFile(lStrResult[1]).getPath();

		} catch (StorageException e) {
			log.error("Eccezione nel recupero della cartella di destinazione dallo storage", e);
			throw e;
		}
	}

	/**
	 * Aggiungo il separatore al path che viene passato se non già presente
	 * 
	 * @param directoryName
	 *            percorso che deve essere analizzato
	 * @return path con carattere separatore al termine
	 */
	public static String addSeparatorToPath(String directoryName) {

		if (!directoryName.endsWith(File.separator)) {
			directoryName = directoryName + File.separator;
		}
		return directoryName;
	}

}
