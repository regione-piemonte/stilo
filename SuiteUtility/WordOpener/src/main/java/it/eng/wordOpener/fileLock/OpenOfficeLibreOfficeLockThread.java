package it.eng.wordOpener.fileLock;

import java.io.File;

import org.apache.log4j.Logger;

/**
 * 
 * @deprecated utilizzare DocumentLockCheckerThread
 *
 */
@Deprecated
public class OpenOfficeLibreOfficeLockThread implements Runnable {

	private File mFile;
	private FileLockListener mFileLockListener;
	private FileState state;
	private WordLockThread mFileLockThread;
	private boolean interrupt = false;
	public enum FileState {
		LOCK_FIRST, NO_LOCK_FIRST, NO_LOCK;
	}

	private static Logger mLogger = Logger.getLogger(WordLockThread.class);

	public OpenOfficeLibreOfficeLockThread(File pFile, FileLockListener pFileLockListener){
		mFile = pFile;
		mFileLockListener = pFileLockListener;
	}

	public OpenOfficeLibreOfficeLockThread(File pFile, FileLockListener pFileLockListener, WordLockThread pFileLockThread){
		mFile = pFile;
		mFileLockListener = pFileLockListener;
		mFileLockThread = pFileLockThread;
	}

	@Override
	public void run() {
		if (!interrupt){
			File lFileLock = new File(mFile.getParentFile().getPath() + File.separator + ".~lock." + mFile.getName() + "#");
			if (!lFileLock.exists()){
				mLogger.debug("Il file non � stato ancora aperto dal sistema");
				state = FileState.NO_LOCK_FIRST;
			} else {
				mLogger.debug("Il file � stato aperto dal sistema");
				state = FileState.LOCK_FIRST;
			}	
			if (state == FileState.LOCK_FIRST){
				waitForUnLock();
			} else waitForLock();
		}
	}

	private void waitForLock() {
		while (!interrupt){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				mLogger.debug("Errore nello sleep");
				mFileLockListener.error(e);
			}

			File lFileLock = new File(mFile.getParentFile().getPath() + File.separator + ".~lock." + mFile.getName() + "#");
			if (!lFileLock.exists()){
				mLogger.debug("Il file non � stato ancora aperto dal sistema");
				state = FileState.NO_LOCK_FIRST;
			} else {
				mLogger.debug("Il file � stato aperto dal sistema");
				interrupt = true;
				waitForUnLock();
			}
		}

	}

	private void waitForUnLock() {
		if (mFileLockThread != null){
			try {
				mFileLockThread.openByOpenOffice();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		while (true){
			File lFileLock = new File(mFile.getParentFile().getPath() + File.separator + ".~lock." + mFile.getName() + "#");
			if (!lFileLock.exists()){
				mLogger.debug("Finito");
				break;
			} else {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					mLogger.debug("Errore nello sleep");
					mFileLockListener.error(e);
				}
			}
		}
		mLogger.debug("Il file non � piu in uso");
		//		ProxyDefaultHttpClient.getClientToUse();
		mFileLockListener.fileUnlocked();
	}

	public void openByWord(){
		interrupt = true;
	}

}
