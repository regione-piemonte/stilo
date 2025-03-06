package it.eng.areas.hybrid.module.cryptoLight.inputFileProvider;

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

import it.eng.areas.hybrid.module.cryptoLight.sign.FileBean;
import it.eng.areas.hybrid.module.cryptoLight.util.MessageKeys;
import it.eng.areas.hybrid.module.cryptoLight.util.Messages;
import it.eng.areas.hybrid.module.cryptoLight.util.PreferenceKeys;
import it.eng.areas.hybrid.module.cryptoLight.util.PreferenceManager;

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
	
	/**
	 * Metodo accessorio che ritorna il valore del flag breakOnWrongPin
	 * @return the breakOnWrongPin
	 */
//	@Override
//	public boolean getBreakOnWrongPin() {
//		
//		/*
//		 * Prelevo il valore del flag breakOnWrongPin che determina il comportamento
//		 * da tenere nel SignerThread
//		 */
//		String breakOnWrongPin = PreferenceManager.getString( PreferenceKeys.PROPERTY_BREAK_ON_WRONG_PIN );
//		logger.info("Parametro " + PreferenceKeys.PROPERTY_BREAK_ON_WRONG_PIN + ": " + breakOnWrongPin);
//		if( breakOnWrongPin != null )
//		{
//			if("true".equals(breakOnWrongPin))
//			{
//				return true;
//			}
//			else
//			{
//				return false;
//			} 
//		}	
//		else
//		{
//			//Se il parametro non è stato impostato allora si ritorna il valore true che non fa saltare il break
//			return false;
//		}
//	}

}
