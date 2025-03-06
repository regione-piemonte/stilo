package it.eng.areas.hybrid;

import java.awt.TrayIcon.MessageType;
import java.io.File;
import java.net.URI;
import java.security.GeneralSecurityException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.log4j.Logger;

import it.eng.areas.hybrid.gui.fx.FxManager;
import it.eng.areas.hybrid.module.IClientModuleContainer;
import it.eng.areas.hybrid.module.builtin.InitModule;
import it.eng.areas.hybrid.module.builtin.RootModule;
import it.eng.areas.hybrid.osgi.OSGiManager;
import it.eng.areas.hybrid.server.ClientHTTPD;
import it.eng.areas.hybrid.util.LoggerUtils;
import it.eng.areas.hybrid.util.TrayIconUtils;
import javafx.application.Application;

public class Main {
	
	private static final Logger logger = Logger.getLogger(Main.class);

	public static void main(String[] args) {
		System.out.println("Avvio HybridMain build "+Version.BUILD); //Messaggio per la console
		
		String context = "";
		
		final String serverUrl;
		int port = 8181;
		int portSSL = 8183;
		String serverSession = null;
		String workFolder = ".areas-hybrid";
		final boolean useSSL;
		if (args.length > 0) {
			serverUrl = args[0];
			if (args.length > 2) {
				port = Integer.parseInt(args[1]);
				portSSL = Integer.parseInt(args[2]);
				if (args.length > 3) {
					workFolder += "-" + args[3];
					if (args.length > 4) {
						serverSession = args[4];
					}
				}
			}
		} else {
			serverUrl = null;
		}
		
		TrayIconUtils.setup(serverUrl);
		
		final File workspace = new File(System.getProperty("user.home") + File.separator + workFolder);
		LoggerUtils.setup(new File(workspace, "areas-hybrid.log"));
		
		logger.info("Avvio HybridMain build " + Version.BUILD); //Messaggio per la console
		logger.info("java.class.path " + System.getProperty("java.class.path"));
		logger.info("java.library.path " + System.getProperty("java.library.path"));
		
		logger.info("serverUrl "+serverUrl);
		logger.info("port "+ port + ", portSSL" + portSSL);
		logger.info("workFolder "+workFolder);
		logger.info("serverSession "+serverSession);

		if (serverUrl != null) {
			try {
				URI serverURI = URI.create(serverUrl);
				String path = serverURI.getPath();
				if (path != null && path.startsWith("/")) {
					path = path.substring(1);
				}
				if (path.split("/").length > 0) {
					context = path.split("/")[0];
				};
			} catch (Exception e) {
				context = "";
			}
			if (serverUrl.toLowerCase().startsWith("https")) {
				useSSL = true;
				
				// Creo un TrustManager che accetta il certificato del server
				TrustManager[] trustAllCerts = new TrustManager[] { 
					    new X509TrustManager() {     
					        public java.security.cert.X509Certificate[] getAcceptedIssuers() { 
					            return new java.security.cert.X509Certificate[0];
					        } 
					        public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
					        	logger.info("checkClientTrusted");
					        } 
					        public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
					        	logger.info("checkServerTrusted");
					        }
					    } 
					}; 
				

				// Create all-trusting host name verifier
		        HostnameVerifier allHostsValid = new HostnameVerifier() {
		            public boolean verify(String hostname, SSLSession session) {
		                return true;
		            }
		        };

				// Install the all-trusting trust manager
				try {
				    SSLContext sc = SSLContext.getInstance("SSL"); 
				    sc.init(null, trustAllCerts, new java.security.SecureRandom()); 
				    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
				    
				    HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
				} catch (GeneralSecurityException e) {
					logger.error(e.getMessage());
				} 				
			} else {
				useSSL = false;
			}
		} else {
			useSSL = false;
		}
		logger.info("useSSL " + useSSL);
		
		boolean processError = false;
		ProcessThread startDelayed = null;
		OSGiManager osgiManager = null;
		try {
			//Avvio il server web embedded
			logger.info("Istanzio ClientHTTPD ");
			final ClientHTTPD httpd = new ClientHTTPD(useSSL ? portSSL : port, useSSL, serverUrl, context);
			httpd.registerModule(new RootModule());
			logger.info("Root module registrato");
			
			//Avvio l'ambiente OSGi
			try {
				osgiManager = OSGiManager.start(workspace, httpd);
			} catch(Exception e){
				logger.error("ERRORE " );
				return;
			}
			//Registro le informazioni base sull'istanza
			if (useSSL) {
				OSGiManager.getInstance().getClientModuleManager().setSharedProperty(IClientModuleContainer.PARAMETER_LOCALURL, "https://localhost:" + portSSL);
			} else {
				OSGiManager.getInstance().getClientModuleManager().setSharedProperty(IClientModuleContainer.PARAMETER_LOCALURL, "http://localhost:" + port);
			}
			
			//Registro i parametri di avvio
			if (serverUrl != null) {
				OSGiManager.getInstance().getClientModuleManager().setSharedProperty(IClientModuleContainer.PARAMETER_SERVERURL, serverUrl);
			}
			if (serverSession != null) {
				OSGiManager.getInstance().getClientModuleManager().setSharedProperty(IClientModuleContainer.PARAMETER_SERVERSESSION, serverSession);
			}
			
			httpd.registerModule(new InitModule(osgiManager));
			startDelayed = new ProcessThread(httpd, workspace, serverUrl, useSSL);
			
			logger.debug("Starto il processo");
			startDelayed.start();
			
			//aspetto che il processo di avvio sia completato, con o senza errori
			boolean avvioProcessoCompletato = false;
			while( !avvioProcessoCompletato ){
				avvioProcessoCompletato = startDelayed.isProcessStartCompleted();
				Thread.sleep(500);
			}
			logger.debug("Avvio processo processo completato");
			
			if( !startDelayed.isProcessWithError()){
				
			} else {
				processError = true;
			}
			
		} catch (Exception e) {
			TrayIconUtils.displayMessage("Hybrid", "Impossibile avviare il servizio", MessageType.ERROR);
			logger.error(e,e);
		}
		
		if (!processError) {
			logger.debug("Lancio l'applicazione");
			Application.launch(FxManager.class, args);
		} else {
			logger.error("processError vale " + processError + ". Chiuso l'applicazione");
			osgiManager.stop();
			startDelayed.interrupt();
			System.exit(0);
			return;
		}
	}
}
