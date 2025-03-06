package it.eng.applet.stampaFile.inputFileProvider;

import it.eng.applet.stampaFile.bean.FileBean;
import it.eng.applet.stampaFile.messages.MessageKeys;
import it.eng.applet.stampaFile.messages.Messages;
import it.eng.applet.stampaFile.preferences.PreferenceKeys;
import it.eng.applet.stampaFile.preferences.PreferenceManager;
import it.eng.applet.stampaFile.util.LogWriter;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public class DirectUrlFileInputProvider implements FileInputProvider {

	@Override
	public FileInputResponse getFileInputResponse() throws Exception {
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
			FileBean bean = new FileBean();
			bean.setFile( tmp );
			bean.setFileName(filename);
			
			List<FileBean> fileBeanList = new ArrayList<FileBean>() ;
			fileBeanList.add( bean );
			
			response = new FileInputResponse();
			response.setFileBeanList( fileBeanList );
		} catch (MalformedURLException e) {
			LogWriter.writeLog("Errore ", e);
		} catch (IOException e) {
			LogWriter.writeLog("Errore ", e);
		}
		return response;
	}

}
