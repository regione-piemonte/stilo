package it.eng.areas.hybrid;

import java.awt.Desktop;
import java.awt.TrayIcon.MessageType;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;
import java.util.Date;

import org.apache.log4j.Logger;

import it.eng.areas.hybrid.server.ClientHTTPD;
import it.eng.areas.hybrid.util.TrayIconUtils;

public class ProcessThread extends Thread {

	private static final Logger logger = Logger.getLogger(ProcessThread.class);

	private boolean processWithError = false;
	private boolean processStartCompleted = false;
	private ClientHTTPD httpd;
	private File workspace;
	private String serverUrl;
	private boolean useSSL;
	
	public ProcessThread(ClientHTTPD httpd, File workspace, String serverUrl, boolean useSSL) {
		super();
		this.httpd = httpd;
		this.workspace = workspace;
		this.serverUrl = serverUrl;
		this.useSSL = useSSL;
	}

	@Override
	public void run() {
		processStartCompleted = false;
		try {
			Thread.sleep(500);
			logger.debug("starto il processo");
			httpd.start();
			
			TrayIconUtils.show();
			TrayIconUtils.displayMessage("Hybrid", "Il servizio è attivo e pronto all'uso.", MessageType.INFO);
			
			
			
		} catch (Exception e) {
			//TrayIconUtils.show();
			//TrayIconUtils.displayMessage("Hybrid", "Impossibile avviare il servizio", MessageType.ERROR);
			logger.error("Impossibile avviare il servizio");
			logger.error(e,e);
			processWithError= true;
			processStartCompleted = false;
		}


		if (useSSL && !processWithError) {
			//Verifico che non sia il primo avvio
			try {
				File sslSemaphore = new File(workspace,"ssl.txt");
				if (!sslSemaphore.exists()) {
					Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
					if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
						try {
							desktop.browse(new URI(serverUrl+"/client-info-ssl.html"));
						} catch (Exception e) {
							logger.error(e);
						}
					}									


					String sslSemapthoreContent = "Avvio in SSL " + (new Date());
					FileOutputStream out = new FileOutputStream(sslSemaphore);
					try {
						out.write(sslSemapthoreContent.getBytes());
					} finally {
						out.close();
					}
				} 
			} catch (Exception e) {
				logger.error(e,e);
				//processWithError = true;
			}
		}
		
		processStartCompleted = true;
		logger.debug("processStartCompleted " + processStartCompleted + " processWithError " +processWithError );
	}

	public boolean isProcessWithError() {
		return processWithError;
	}

	public void setProcessWithError(boolean processWithError) {
		this.processWithError = processWithError;
	}

	public boolean isProcessStartCompleted() {
		return processStartCompleted;
	}

	public void setProcessStartCompleted(boolean processStartCompleted) {
		this.processStartCompleted = processStartCompleted;
	}

	

	

	
}
