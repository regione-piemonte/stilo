/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

/**
 * Macchina a stati che verifica lo stato di lock / unlock del documento caricato dall'applet
 * 
 * @author massimo malvestio
 *
 */
public class DocumentLockCheckerThread implements Runnable {

	// file associato al documento di cui si vuole verificare lo stato di lock
	private File documentFile;

	public void setDocumentFile(File documentFile) {
		this.documentFile = documentFile;
	}

	public void setDocLockListener(FileLockListener docLockListener) {
		this.docLockListener = docLockListener;
	}

	// listener cui notificare il cambio dello stato di lock
	private FileLockListener docLockListener;

	private Boolean completed = Boolean.FALSE;

	// inizialmente il thread verifica il lock del file specificato
	private boolean checkForLock = Boolean.TRUE;

	private static Logger logger = Logger.getLogger(DocumentLockCheckerThread.class);

	@Override
	public void run() {

		logger.debug("Inizio del check");

		// fintanto che il thread non completato, continuo nel check dello stato
		while (!completed) {

			if (checkForLock) {

				waitForLock();

			} else {

				waitForUnLock();

			}
		}
	}

	/**
	 * Verifica se il file è stato rilasciato o meno
	 */
	private void waitForUnLock() {

		logger.debug("waitforUnlock()");
		while (isDocumentLocked()) {

			try {

				logger.debug("Ciclo in waitforUnlock");
				// ogni 4 secondi verifico se il lock sul file è stato rilasciato, ho impostato un tempo più basso in quanto
				// questo stato blocca il flusso applicativo
				Thread.sleep(4000);

			} catch (InterruptedException e) {

				logger.debug("Impossibile applicare lo sleep al thread di controllo del lock del documento");

			}
		}
		// l'utente ha finito di modificare il documento, lo ha rilasciato, e quindi si può proseguire con il flusso applicativo di auriga
		completed = true;
		logger.info("L'utente ha rilasciato il documento");
		docLockListener.fileUnlocked();

	}

	/**
	 * Verifica ciclicamente se il file è stato lockato o meno
	 */
	private void waitForLock() {
		logger.debug("waitforLock()");
		long startTimer = System.currentTimeMillis();
		Boolean exit = Boolean.TRUE;
		while (!isDocumentLocked() && exit) {

			try {

				logger.debug("Ciclo in waitforLock");
				// ogni 2 secondi verifico se il lock è stato applicato posso permettermi di impostare un tempo alto in quanto
				// non blocco alcun flusso, semplicemente ritardo un pò nel cambio di stato della classe stessa
				Thread.sleep(2000);

			} catch (InterruptedException e) {

				logger.debug("Impossibile applicare lo sleep al thread di controllo del lock del documento");
				docLockListener.error(e);

			}

			if (System.currentTimeMillis() - startTimer > 10000) {
				// Sono passati pi� di n secondi in cui il sistema sta attendendo il lock
				// quindi l'editor dev'essere stato chiuso prima dei 5 secondi di attesa iniziali
				// entro il cui il file non � ancora stato lockato.

				completed = true;
				exit = false;
				logger.debug("L'utente ha chiuso il documento prima di ottenere il lock");
			}
		}

		// il documento è stato lockato, cambio modalità e passo al check dell'unlock
		checkForLock = Boolean.FALSE;

		logger.info("L'utente ha lockato il documento");

		if (!exit) {
			docLockListener.fileUnlocked();
		}

	}

	/**
	 * @return true se il file è lockato, false altrimenti
	 */
	private Boolean isDocumentLocked() {

		logger.debug("isDocumentLocked()");
		boolean retValue = true;

		try {

			File backup = backupFile(documentFile);

			// sono riuscito ad aprire uno stream sul file, vuol dire che il file non è lockato
			FileOutputStream output = new FileOutputStream(documentFile);

			restore(output, backup);

			retValue = false;
			logger.debug("Il file non è più lockato");

		} catch (IOException e) {

			// l'eccezione che a me interessa è FileNotFound, la quale indica se il file è lockato o meno.
			// l'eccezione sollevata dalla chiusura dello stream non mi da
			// alcuna informazione e non mi interessa gestirla, visto che non ho dati da salvare

			logger.debug("Il file è lockato");

		} catch (SecurityException sEx) {

			logger.debug("Il file è lockato");
		}

		logger.debug("Ritorno da isDocumentLocked con retValue" + retValue);
		return retValue;
	}

	/**
	 * Effettua il restore del file originario passato come outputstream, utilizzando i contenuti del file di back specificato
	 * 
	 * @param output
	 * @param backup
	 */
	private void restore(OutputStream output, File backup) {

		try {

			InputStream input = new FileInputStream(backup);

			IOUtils.copy(input, output);

			input.close();
			output.flush();
			output.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Effettua un backup del file specificato, ovvero viene creato un nuovo file con lo stesso nome dell'originale a cui viene appesa l'estensione .bkp
	 * 
	 * @param documentFile
	 * @return
	 */
	private File backupFile(File documentFile) {

		File outputFile = new File(documentFile.getAbsolutePath() + ".bkp");

		try {

			InputStream input = new FileInputStream(documentFile);

			OutputStream output = new FileOutputStream(outputFile);

			IOUtils.copy(input, output);
			input.close();
			output.flush();
			output.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return outputFile;
	}
}
