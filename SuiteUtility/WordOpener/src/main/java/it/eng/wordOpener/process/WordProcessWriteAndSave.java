package it.eng.wordOpener.process;

import it.eng.wordOpener.WriteAndSave;
import it.eng.wordOpener.exception.UnableToEditFileException;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;

public class WordProcessWriteAndSave implements WriteAndSave{

	private static Logger mLogger = Logger.getLogger(WordProcessWriteAndSave.class);

	@Override
	public void openProcess(File pFile) throws UnableToEditFileException {
		mLogger.debug("Apro il File " + pFile.getPath() + " con l'editor di default");
		try {
			if (System.getProperty("os.name").toLowerCase().contains("windows")) {
				String cmd = "rundll32 url.dll,FileProtocolHandler " + pFile.getCanonicalPath();
				Process lProcess = Runtime.getRuntime().exec(cmd);
				try {
					int closeValue = lProcess.waitFor();
					mLogger.debug("Finito " + Integer.toString(closeValue));
				} catch (InterruptedException e) {
					mLogger.debug("Finito");
				}
			} 
			else {
				Desktop.getDesktop().open(pFile);
			}
			//			if (Desktop.isDesktopSupported()) {
			//				Desktop.getDesktop().open(pFile);
			//			} else {
			//				mLogger.error("Impossibile aprire il file " + pFile.getPath() + " con l'editor di default. Controllare le impostazioni");
			//				throw new UnableToEditFileException();
			//			}
		} catch (IOException ioe) {
			mLogger.error("Impossibile aprire il file " + pFile.getPath() + " con l'editor di default. Controllare le impostazioni");
			mLogger.error("Errore " + ioe.getMessage(), ioe);
			throw new UnableToEditFileException();
		}
	}

	@Override
	public void saveFile() {
		// TODO Auto-generated method stub

	}

}
