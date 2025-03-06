package it.eng.hybrid.module.jpedal.fileInputProvider;

import it.eng.hybrid.module.jpedal.preferences.ConfigConstants;
import it.eng.hybrid.module.jpedal.preferences.PreferenceManager;
import it.eng.hybrid.module.jpedal.ui.JPedalApplication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.log4j.Logger;


public class ServletUrlFileInputProvider implements FileInputProvider {

	public final static Logger logger = Logger.getLogger(ServletUrlFileInputProvider.class);
	
	@Override
	public FileInputResponse getFile(PreferenceManager preferenceManager) throws Exception {
		FileInputResponse response = null;
		String servlet = null;
		try {
			servlet = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_SERVLET );
        	logger.info("Parametro " + ConfigConstants.PROPERTY_SERVLET + " :" + servlet );
        } catch (Exception e) {}
		
		String openUrl = null;
		try {
			openUrl = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_OPENURL );
        	logger.info("Parametro " + ConfigConstants.PROPERTY_OPENURL + " :" + openUrl );
        } catch (Exception e) {}
		
		String filename = null;
		try {
			filename = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_FILENAME );
        	logger.info("Parametro " + ConfigConstants.PROPERTY_FILENAME + " :" + filename );
        } catch (Exception e) {}
		
		if( servlet==null || openUrl==null || filename==null)
			throw new Exception("Parametri incompleti");
		
		byte[] servletBytes = null;
		URL jpedalServletURL;
		try {
			jpedalServletURL = new URL( servlet );
			URLConnection urlConnection = jpedalServletURL.openConnection();
			urlConnection.setDoInput(true);
			urlConnection.setDoOutput(true);
			urlConnection.setUseCaches(false);	
			ObjectOutputStream objOut = new ObjectOutputStream (urlConnection.getOutputStream());
			objOut.writeObject(openUrl);//invio il path alla servlet
			objOut.flush();
			ObjectInputStream objIn = new ObjectInputStream(urlConnection.getInputStream());
			servletBytes = (byte[]) objIn.readObject();//leggo lo stream di bytes dalla servlet
			response = new FileInputResponse();
			response.setFileContent( servletBytes );
			response.setFileName(filename);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return response;
	}

}
