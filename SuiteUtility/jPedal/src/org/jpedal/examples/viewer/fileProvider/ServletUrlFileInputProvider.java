package org.jpedal.examples.viewer.fileProvider;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.jpedal.examples.viewer.config.ConfigConstants;
import org.jpedal.examples.viewer.config.PreferenceManager;
import org.jpedal.utils.LogWriter;

public class ServletUrlFileInputProvider implements FileInputProvider {

	@Override
	public FileInputResponse getFile(PreferenceManager preferenceManager) throws Exception {
		FileInputResponse response = null;
		String servlet = null;
		try {
			servlet = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_SERVLET );
        	LogWriter.writeLog("Parametro " + ConfigConstants.PROPERTY_SERVLET + " :" + servlet );
        } catch (Exception e) {}
		
		String openUrl = null;
		try {
			openUrl = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_OPENURL );
        	LogWriter.writeLog("Parametro " + ConfigConstants.PROPERTY_OPENURL + " :" + openUrl );
        } catch (Exception e) {}
		
		String filename = null;
		try {
			filename = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_FILENAME );
        	LogWriter.writeLog("Parametro " + ConfigConstants.PROPERTY_FILENAME + " :" + filename );
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
