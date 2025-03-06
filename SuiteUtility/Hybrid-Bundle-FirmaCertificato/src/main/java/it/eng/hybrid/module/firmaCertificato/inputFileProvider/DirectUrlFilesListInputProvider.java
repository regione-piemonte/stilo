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


public class DirectUrlFilesListInputProvider implements FileInputProvider {

	public final static Logger logger = Logger.getLogger(DirectUrlFilesListInputProvider.class);
	
	@Override
	public FileInputResponse getFileInputResponse() throws Exception {
		FileInputResponse response = null;
		List<FileBean> fileBeanList = new ArrayList<FileBean>() ;

		String count = PreferenceManager.getString( PreferenceKeys.PROPERTY_NUMFILES);
		logger.info("Parametro " + PreferenceKeys.PROPERTY_NUMFILES + ": " + count);
		for(int i=0;i<Integer.parseInt(count);i++){
			FileBean bean = new FileBean();
			String idFile = null;
			try {
				idFile = PreferenceManager.getString( PreferenceKeys.PROPERTY_IDFILE + i);
	        	logger.info("Parametro " + PreferenceKeys.PROPERTY_IDFILE + i + ": " + idFile );
	        } catch (Exception e) {}
			String directUrl=null;
			try {
				directUrl = PreferenceManager.getString( PreferenceKeys.PROPERTY_DIRECTURL + i);
	        	logger.info("Parametro " + PreferenceKeys.PROPERTY_DIRECTURL + i + ": " + directUrl );
	        } catch (Exception e) {}
			
			String filename = "";
			try {
				filename = PreferenceManager.getString( PreferenceKeys.PROPERTY_FILENAME + i );
	        	logger.info("Parametro " + PreferenceKeys.PROPERTY_FILENAME + i + " :" + filename );
	        } catch (Exception e) {}
			
	        String firmaPresente = PreferenceManager.getString( PreferenceKeys.PROPERTY_ISFIRMAPRESENTE+i );
			logger.info("Parametro " + PreferenceKeys.PROPERTY_ISFIRMAPRESENTE + ": " + firmaPresente);
			bean.setIsFirmaPresente( firmaPresente );
			
			String firmaValida = PreferenceManager.getString( PreferenceKeys.PROPERTY_ISFIRMAVALIDA+i );
			logger.info("Parametro " + PreferenceKeys.PROPERTY_ISFIRMAVALIDA + ": " + firmaValida);
			bean.setIsFirmaValida( firmaValida );
			
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
				File tmp=File.createTempFile(filename, "");
				
				FileOutputStream out = FileUtils.openOutputStream(tmp);
				IOUtils.copy(new ByteArrayInputStream(servletBytes), out);
				out.flush();
				out.close();
				
				logger.info( "salvando il file in:"+tmp.getAbsolutePath() );
				bean.setFile( tmp );
				bean.setFileName( filename );
				bean.setIdFile(idFile);
				fileBeanList.add( bean );
			} catch (MalformedURLException e) {
				logger.info("Errore ", e);
			} catch (IOException e) {
				logger.info("Errore ", e);
			}
		}
		response = new FileInputResponse();
		response.setFileBeanList( fileBeanList );

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
