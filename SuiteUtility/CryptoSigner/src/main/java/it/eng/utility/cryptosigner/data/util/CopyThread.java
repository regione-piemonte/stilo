package it.eng.utility.cryptosigner.data.util;

import java.io.File;
import java.io.IOException;
import java.io.PipedOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.python.jline.internal.Log;

import it.eng.utility.FileUtil;

public class CopyThread extends Thread {

	private static Logger log = Logger.getLogger(CopyThread.class);

	private File file = null;
	private PipedOutputStream out;

	public CopyThread(File file, PipedOutputStream out) {
		this.file = file;
		this.out = out;
	}

	@Override
	public void run() {
		try {
			log.info("Operazine di copia su file " + file);
			IOUtils.copyLarge(FileUtils.openInputStream(file), out);
		} catch (Exception e) {
			Log.error("Eccezione copia su file", e);
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				Log.error("Eccezione IO copia su file", e);
			}

			FileUtil.deleteFile(file);
		}
	}

}
