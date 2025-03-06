package it.eng.hybrid.module.firmaCertificato.inputFileProvider;

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
import org.apache.log4j.Logger;

import it.eng.hybrid.module.firmaCertificato.bean.FileBean;
import it.eng.hybrid.module.firmaCertificato.messages.MessageKeys;
import it.eng.hybrid.module.firmaCertificato.messages.Messages;
import it.eng.hybrid.module.firmaCertificato.preferences.PreferenceKeys;
import it.eng.hybrid.module.firmaCertificato.preferences.PreferenceManager;

public class DirectUrlFileInputProvider implements FileInputProvider {

	public final static Logger logger = Logger.getLogger(DirectUrlFileInputProvider.class);
	
	@Override
	public FileInputResponse getFileInputResponse() throws Exception {
		FileInputResponse response = null;
		String directUrl=null;
		try {
			directUrl = PreferenceManager.getString( PreferenceKeys.PROPERTY_DIRECTURL );
        	logger.info("Parametro " + PreferenceKeys.PROPERTY_DIRECTURL + ": " + directUrl );
        } catch (Exception e) {}
		
		String filename = null;
		try {
			filename = PreferenceManager.getString( PreferenceKeys.PROPERTY_FILENAME );
        	logger.info("Parametro " + PreferenceKeys.PROPERTY_FILENAME + ": " + filename );
        } catch (Exception e) {}
		
		if( directUrl==null || filename==null)
			throw new Exception( Messages.getMessage( MessageKeys.MSG_ERROR_MISSINGPARAMETERS ) );
		
		URL jpedalServletURL;
		byte[] servletBytes = null;
		try {
			jpedalServletURL = new URL(directUrl);
			logger.info("Carico il file dalla url:"+directUrl);
			URLConnection urlConnection = jpedalServletURL.openConnection();
			urlConnection.setDoOutput(true);
			urlConnection.setUseCaches(false);	
			servletBytes =  IOUtils.toByteArray(urlConnection.getInputStream() );	//leggo lo stream di bytes dalla servlet
			File tmp=File.createTempFile(filename, ".pdf");
			
			FileOutputStream out = FileUtils.openOutputStream(tmp);
			IOUtils.copy(new ByteArrayInputStream(servletBytes), out);
			out.flush();
			out.close();
			
			logger.info( "salvando il file in:"+tmp.getAbsolutePath() );
			FileBean bean = new FileBean();
			bean.setFile( tmp );
			bean.setFileName(filename);
			
			List<FileBean> fileBeanList = new ArrayList<FileBean>() ;
			fileBeanList.add( bean );
			
			response = new FileInputResponse();
			response.setFileBeanList( fileBeanList );
		} catch (MalformedURLException e) {
			logger.info("Errore ", e);
		} catch (IOException e) {
			logger.info("Errore ", e);
		}
		return response;
	}
	
	@Override
	public String getPin() throws Exception {
		String pin =null;
		try {
			pin = PreferenceManager.getString( PreferenceKeys.PROPERTY_PIN );
//			logger.info("Parametro " + PreferenceKeys.PROPERTY_PIN + " :" + pin );
        } catch (Exception e) {}
        
        return pin;
	}

	@Override
	public String getAlias() throws Exception {
		String alias =null;
		try {
			alias = PreferenceManager.getString( PreferenceKeys.PROPERTY_ALIAS );
			logger.info("Parametro " + PreferenceKeys.PROPERTY_ALIAS + " :" + alias );
        } catch (Exception e) {}
        
        return alias;
	}

	@Override
	public String getMetodoFirma() throws Exception {
		String metodoFirma = null;
		try {
			metodoFirma = PreferenceManager.getString(PreferenceKeys.PROPERTY_METODO_FIRMA);
			logger.info("Parametro " + PreferenceKeys.PROPERTY_METODO_FIRMA + " :" + metodoFirma);
		} catch (Exception e) {
		}

		return metodoFirma;
	}

}
