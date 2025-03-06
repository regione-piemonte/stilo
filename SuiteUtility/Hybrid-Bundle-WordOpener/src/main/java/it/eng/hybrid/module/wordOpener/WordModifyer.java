package it.eng.hybrid.module.wordOpener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.log4j.Logger;

import it.eng.hybrid.module.wordOpener.error.ErrorManager;
import it.eng.hybrid.module.wordOpener.exception.InitException;
import it.eng.hybrid.module.wordOpener.exception.UnableToEditFileException;
import it.eng.hybrid.module.wordOpener.exception.UnableToRetrieveFileException;
import it.eng.hybrid.module.wordOpener.exception.UnableToSaveFileException;
import it.eng.hybrid.module.wordOpener.file.ComparatorFileUtils;
import it.eng.hybrid.module.wordOpener.fileLock.DocumentLockCheckerThread;
import it.eng.hybrid.module.wordOpener.fileLock.FileLockListener;
import it.eng.hybrid.module.wordOpener.fileProvider.FileProvider;
import it.eng.hybrid.module.wordOpener.process.WriteAndSave;

public class WordModifyer implements FileLockListener {

	private WriteAndSave mWriteAndSave;
	private FileProvider mFileProvider;
	private File mFile;
	private ErrorManager mErrorManager;

	private static Logger logger = Logger.getLogger(WordModifyer.class);

	public WordModifyer(WriteAndSave pWriteAndSave, FileProvider pFileProvider, ErrorManager pErrorManager) throws UnableToRetrieveFileException {
		mWriteAndSave = pWriteAndSave;
		mFileProvider = pFileProvider;
		mFile = mFileProvider.getFile();
		mErrorManager = pErrorManager;
	}

	public void openAndWait() throws UnableToEditFileException, InitException {
		mWriteAndSave.openProcess(mFile);
		waitForUnLock();
	}

	private void waitForUnLock() throws InitException {

		logger.debug("Wait for unlock");

		DocumentLockCheckerThread checkerThread = new DocumentLockCheckerThread();
		checkerThread.setDocLockListener(this);
		checkerThread.setDocumentFile(mFile);

		logger.debug("Istanziato runnable");

		Thread thread = new Thread(checkerThread);

		try {

			// al fine di minimizzare la race condition che potrebbe verificarsi tra l'apertura del file da parte dell'editor
			// Word oppure OpenOffice, e l'inizio del processo di controllo del lock, imposto un delay iniziale, tanto il check dell'avvenuto
			// lock non inficia alcun flusso applicativo
			logger.debug("Sleep del runnable");
			thread.sleep(5000);

			logger.debug("Esecuzione");
			thread.run();

		} catch (InterruptedException e) {
			logger.debug("Il thread è stato interrotto");
		}

	}

	public void fileUnlocked() {
		try {
			logger.debug("Verifico le impronte");
			if (ComparatorFileUtils.isChangedFile(new FileInputStream(mFile))) {
				logger.debug("File cambiato, salvo il file");
				mFileProvider.saveFileToServer(mFile);
			} else {
				logger.debug("File invariato, chiudo");
				mErrorManager.justClose();
			}
		} catch (UnableToSaveFileException e) {
			mErrorManager.manageExcepion(e);
		} catch (FileNotFoundException e) {
			mErrorManager.manageExcepion(e);
		} catch (InitException e) {
			mErrorManager.manageExcepion(e);
		} catch (IOException e) {
			mErrorManager.manageExcepion(e);
		}
	}

	@Override
	public void error(Exception e) {
		logger.error("Errore nel thread di verifica, il file non verr� salvato " + e.getMessage(), e);
		mErrorManager.manageExcepion(e);
	}
}
