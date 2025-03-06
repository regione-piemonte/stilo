package it.eng.areas.hybrid.module.cryptoLight.detectors;

  

import java.io.File;
import java.io.IOException;
import java.io.PipedOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

public class CopyThread extends Thread{

	public final static Logger logger = Logger.getLogger(CopyThread.class);
	
	private File file = null;
	private PipedOutputStream out;
	
	public CopyThread(File file,PipedOutputStream out) {
		this.file = file;
		this.out = out;
	}
	
	
	@Override
	public void run() {
		try {
			IOUtils.copyLarge(FileUtils.openInputStream(file), out);
		} catch (Exception e) {	
			logger.error(e);
		} 
		 finally {
			try {
				out.close();
			} catch (IOException e) {
				logger.error(e);
			}
			
			FileUtils.deleteQuietly(file);			
		}
	}

}
