package it.eng.client.applet.fileProvider;

import it.eng.client.applet.i18N.MessageKeys;
import it.eng.client.applet.i18N.Messages;
import it.eng.client.applet.util.PreferenceKeys;
import it.eng.client.applet.util.PreferenceManager;
import it.eng.common.LogWriter;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public class DirectUrlFileInputProvider implements FileInputProvider {

	@Override
	public FileInputResponse getFile() throws Exception {
		FileInputResponse response = null;
		String directUrl=null;
		try {
			directUrl = PreferenceManager.getString( PreferenceKeys.PROPERTY_DIRECTURL );
        	LogWriter.writeLog("Parametro " + PreferenceKeys.PROPERTY_DIRECTURL + ": " + directUrl );
        } catch (Exception e) {}
		
		String filename = null;
		try {
			filename = PreferenceManager.getString( PreferenceKeys.PROPERTY_FILENAME );
        	LogWriter.writeLog("Parametro " + PreferenceKeys.PROPERTY_FILENAME + ": " + filename );
        } catch (Exception e) {}
		
		if( directUrl==null || filename==null)
			throw new Exception( Messages.getMessage( MessageKeys.MSG_ERROR_MISSINGPARAMETERS ) );
		
		URL jpedalServletURL;
		byte[] servletBytes = null;
		try {
			jpedalServletURL = new URL(directUrl);
			LogWriter.writeLog("Carico il file dalla url:"+directUrl);
			URLConnection urlConnection = jpedalServletURL.openConnection();
			urlConnection.setDoOutput(true);
			urlConnection.setUseCaches(false);	
			servletBytes =  IOUtils.toByteArray(urlConnection.getInputStream() );	//leggo lo stream di bytes dalla servlet
			File tmp=File.createTempFile(filename, ".pdf");
			
			FileOutputStream out = FileUtils.openOutputStream(tmp);
			IOUtils.copy(new ByteArrayInputStream(servletBytes), out);
			out.flush();
			out.close();
			
			LogWriter.writeLog( "salvando il file in:"+tmp.getAbsolutePath(), false );
			response = new FileInputResponse();
			response.setFile( tmp );
			//response.setFileContent( servletBytes );
			//response.setFileName(tmp.getAbsolutePath());
			response.setFileName( filename );
		} catch (MalformedURLException e) {
			LogWriter.writeLog("Errore ", e);
		} catch (IOException e) {
			LogWriter.writeLog("Errore ", e);
		}
		return response;
	}

}
