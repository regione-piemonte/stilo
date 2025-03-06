package it.eng.hybrid.module.jpedal.fileInputProvider;

import it.eng.hybrid.module.jpedal.messages.MessageConstants;
import it.eng.hybrid.module.jpedal.messages.Messages;
import it.eng.hybrid.module.jpedal.preferences.ConfigConstants;
import it.eng.hybrid.module.jpedal.preferences.PreferenceManager;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

public class DirectUrlFileInputProvider implements FileInputProvider {

	public final static Logger logger = Logger.getLogger(DirectUrlFileInputProvider.class);
	
	@Override
	public FileInputResponse getFile(PreferenceManager preferenceManager) throws Exception {
		FileInputResponse response = null;
		String directUrl=null;
		try {
			directUrl = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_DIRECTURL );
        	logger.info("Parametro " + ConfigConstants.PROPERTY_DIRECTURL + ": " + directUrl );
        } catch (Exception e) {}
		
		String filename = null;
		try {
			filename = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_FILENAME );
        	logger.info("Parametro " + ConfigConstants.PROPERTY_FILENAME + ": " + filename );
        } catch (Exception e) {}
		
		if( directUrl==null || filename==null)
			throw new Exception( Messages.getMessage( MessageConstants.MSG_MISSINGPARAMETERS ) );
		
		URL jpedalServletURL;
		byte[] servletBytes = null;
		try {
			jpedalServletURL = new URL(directUrl);
			logger.info("Carico il file dalla url:"+directUrl);
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
			
			logger.info( "salvando il file in:"+newFile.getAbsolutePath());
			response = new FileInputResponse();
			response.setFile(newFile);
			//response.setFileContent( servletBytes );
			logger.info("il nome del file è " + filename);
			response.setFileName(filename);
		} catch (MalformedURLException e) {
			logger.info("Errore ", e);
		} catch (IOException e) {
			logger.info("Errore ", e);
		}
		return response;
	}

}
