package it.eng.hybrid.module.wordOpener.fileLock;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.log4j.Logger;

/**
 * 
 * @deprecated utilizzare DocumentLockCheckerThread
 *
 */
@Deprecated
public class WordLockThread implements Runnable {

	private File mFile;
	private FileLockListener mFileLockListener;
	private FileState state;
	private boolean interrupt = false;
	private OpenOfficeLibreOfficeLockThread mFileLockExistThread;

	public enum FileState {
		LOCK_FIRST, NO_LOCK_FIRST, NO_LOCK;
	}

	private static Logger mLogger = Logger.getLogger(WordLockThread.class);

	public WordLockThread(File pFile, FileLockListener pFileLockListener){
		mFile = pFile;
		mFileLockListener = pFileLockListener;
	}
	
	public void setFileLockExistThread(OpenOfficeLibreOfficeLockThread pFileLockExistThread){
		mFileLockExistThread = pFileLockExistThread;
	}

	@Override
	public void run() {
		if (!interrupt){
			FileOutputStream out = null;
			try {
				out = new FileOutputStream(mFile, true);
				try {

					out.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mLogger.debug("Il file non � stato ancora aperto dal sistema");
				state = FileState.NO_LOCK_FIRST;
			} catch (FileNotFoundException e2) {
				mLogger.debug("Il file � stato aperto dal sistema");
				state = FileState.LOCK_FIRST;
			} finally {
				if (out !=null){
					try {
						out.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
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
			FileOutputStream out = null;
			try {
				out = new FileOutputStream(mFile, true);
				try {
					out.close();
					out.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mLogger.debug("Il file non � ancora stato aperto dal sistema");
				state = FileState.NO_LOCK_FIRST;
			} catch (FileNotFoundException e2) {
				mLogger.debug("Il file � stato aperto dal sistema");
				interrupt = true;
				waitForUnLock();
			} finally {
				if (out !=null){
					try {
						out.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}

	}

	private void waitForUnLock() {
		while (!interrupt){
			FileOutputStream out = null;
			try{
				out = new FileOutputStream(mFile, true);
				mLogger.debug("Finito");
				break;
			} catch(FileNotFoundException e1){
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					mLogger.debug("Errore nello sleep");
					mFileLockListener.error(e);
				}
			}
			catch(Exception e){
				mLogger.debug("Errore generico");
				mFileLockListener.error(e);
			} finally {
				if (out !=null){
					try {
						out.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		if (!interrupt){
			mLogger.debug("Il file non � piu in uso");
			if (mFileLockExistThread != null){
				mFileLockExistThread.openByWord();
			}
			//		ProxyDefaultHttpClient.getClientToUse();
			mFileLockListener.fileUnlocked();
		}
	}

	public void openByOpenOffice() throws InterruptedException{
		interrupt = true;
	}

}
