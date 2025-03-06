package it.eng.utility;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

/**
 * Thread che cancella i file presenti in coda
 * @author Administrator
 *
 */
public class ThreadDeleteFiles extends Thread {
	
	Logger logger = Logger.getLogger(ThreadDeleteFiles.class);
	
	@Override
	public void run() {
		
		File file = null;		
		while(true) {
			try {
				file = FilesToDeleteQueue.take();							
				if(file != null && file.exists()){
					//logger.debug("Riprovo a cancellare il file " + file.getAbsolutePath());				
					FileUtils.forceDelete(file);			
					logger.debug("Il file è stato eliminato.");			
				}
			} catch(Throwable e) {
				//Risalvo il file in coda
				if(file != null && file.exists()){
					//logger.debug("Il file è utilizzato da un altro processo: impossibile eliminarlo. Si riproverà in seguito.", e);
					try {
						FilesToDeleteQueue.put(file);
						//Thread.sleep(10000);
					} catch (Throwable e1) {
						file.deleteOnExit();
						logger.error("Si è verificato un errore: il file non è stato cancellato.", e1);
					}					
				}				
			}
		}
	}
}