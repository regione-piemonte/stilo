package org.jpedal.examples.viewer.fileProvider;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.jpedal.examples.viewer.config.ConfigConstants;
import org.jpedal.examples.viewer.config.PreferenceManager;
import org.jpedal.utils.LogWriter;
import org.jpedal.utils.MessageConstants;
import org.jpedal.utils.Messages;

public class DirectUrlFileInputProvider implements FileInputProvider {

	@Override
	public FileInputResponse getFile(PreferenceManager preferenceManager) throws Exception {
		FileInputResponse response = null;
		String directUrl=null;
		try {
			directUrl = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_DIRECTURL );
        	LogWriter.writeLog("Parametro " + ConfigConstants.PROPERTY_DIRECTURL + ": " + directUrl );
        } catch (Exception e) {}
		
		String filename = null;
		try {
			filename = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_FILENAME );
        	LogWriter.writeLog("Parametro " + ConfigConstants.PROPERTY_FILENAME + ": " + filename );
        } catch (Exception e) {}
		
		if( directUrl==null || filename==null)
			throw new Exception( Messages.getMessage( MessageConstants.MSG_MISSINGPARAMETERS ) );
		
		URL jpedalServletURL;
		byte[] servletBytes = null;
		try {
			jpedalServletURL = new URL(directUrl);
			LogWriter.writeLog("Carico il file dalla url:"+directUrl);
			URLConnection urlConnection = jpedalServletURL.openConnection();
			urlConnection.setDoOutput(true);
			urlConnection.setUseCaches(false);	
			servletBytes =  IOUtils.toByteArray( urlConnection.getInputStream() );	
			File tmp=File.createTempFile("f"+filename, ".pdf"); 
			File newFile = new File(tmp.getParent() + File.separator + filename); 
			newFile.createNewFile();
			tmp.renameTo(newFile);
			FileOutputStream out = FileUtils.openOutputStream(newFile);
			IOUtils.copy(new ByteArrayInputStream(servletBytes), out);
			out.flush();
			out.close();
			
			LogWriter.writeLog( "salvando il file in:"+newFile.getAbsolutePath());
			response = new FileInputResponse();
			response.setFile(newFile);
			//response.setFileContent( servletBytes );
			LogWriter.writeLog("il nome del file è " + filename);
			response.setFileName(filename);
		} catch (MalformedURLException e) {
			LogWriter.writeLog("Errore ", e);
		} catch (IOException e) {
			LogWriter.writeLog("Errore ", e);
		}
		return response;
	}

}
