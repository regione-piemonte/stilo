package it.eng.client.applet.fileProvider;

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

import it.eng.client.applet.i18N.MessageKeys;
import it.eng.client.applet.i18N.Messages;
import it.eng.client.applet.util.PreferenceKeys;
import it.eng.client.applet.util.PreferenceManager;
import it.eng.common.LogWriter;
import it.eng.common.bean.FileBean;
import it.eng.server.util.Encryptor;


public class DirectUrlFilesListInputProvider implements FileInputProvider {

	@Override
	public FileInputResponse getFileInputResponse() throws Exception {
		FileInputResponse response = null;
		List<FileBean> fileBeanList = new ArrayList<FileBean>() ;

		String count = PreferenceManager.getString( PreferenceKeys.PROPERTY_NUMFILES);
		LogWriter.writeLog("Parametro " + PreferenceKeys.PROPERTY_NUMFILES + ": " + count);
		for(int i=0;i<Integer.parseInt(count);i++){
			FileBean bean = new FileBean();
			String idFile = null;
			try {
				idFile = PreferenceManager.getString( PreferenceKeys.PROPERTY_IDFILE + i);
	        	LogWriter.writeLog("Parametro " + PreferenceKeys.PROPERTY_IDFILE + i + ": " + idFile );
	        } catch (Exception e) {}
			String directUrl=null;
			try {
				directUrl = PreferenceManager.getString( PreferenceKeys.PROPERTY_DIRECTURL + i);
	        	LogWriter.writeLog("Parametro " + PreferenceKeys.PROPERTY_DIRECTURL + i + ": " + directUrl );
	        } catch (Exception e) {}
			
			String filename = "";
			try {
				filename = PreferenceManager.getString( PreferenceKeys.PROPERTY_FILENAME + i );
	        	LogWriter.writeLog("Parametro " + PreferenceKeys.PROPERTY_FILENAME + i + " :" + filename );
	        } catch (Exception e) {}
			
	        String firmaPresente = PreferenceManager.getString( PreferenceKeys.PROPERTY_ISFIRMAPRESENTE+i );
			LogWriter.writeLog("Parametro " + PreferenceKeys.PROPERTY_ISFIRMAPRESENTE + ": " + firmaPresente);
			bean.setIsFirmaPresente( firmaPresente );
			
			String firmaValida = PreferenceManager.getString( PreferenceKeys.PROPERTY_ISFIRMAVALIDA+i );
			LogWriter.writeLog("Parametro " + PreferenceKeys.PROPERTY_ISFIRMAVALIDA + ": " + firmaValida);
			bean.setIsFirmaValida( firmaValida );
			
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
				File tmp=File.createTempFile(filename, "");
				
				FileOutputStream out = FileUtils.openOutputStream(tmp);
				IOUtils.copy(new ByteArrayInputStream(servletBytes), out);
				out.flush();
				out.close();
				
				LogWriter.writeLog( "salvando il file in:"+tmp.getAbsolutePath(), false );
				bean.setFile( tmp );
				bean.setFileName( filename );
				bean.setIdFile(idFile);
				fileBeanList.add( bean );
			} catch (MalformedURLException e) {
				LogWriter.writeLog("Errore ", e);
			} catch (IOException e) {
				LogWriter.writeLog("Errore ", e);
			}
		}
		response = new FileInputResponse();
		response.setFileBeanList( fileBeanList );

		return response;
	}

	@Override
	public String getPin() throws Exception {
		String pin = null;
		try {
			pin = PreferenceManager.getString(PreferenceKeys.PROPERTY_PIN);

			//Decodifico il pin
			pin = Encryptor.decodificaConZero(pin);

//			LogWriter.writeLog("Parametro " + PreferenceKeys.PROPERTY_PIN + " :" + pin);
		} catch (Exception e) {
		}

		return pin;
	}

	@Override
	public String getAlias() throws Exception {
		String alias = null;
		try {
			alias = PreferenceManager.getString(PreferenceKeys.PROPERTY_ALIAS);
			LogWriter.writeLog("Parametro " + PreferenceKeys.PROPERTY_ALIAS + " :" + alias);
		} catch (Exception e) {
		}

		return alias;
	}

	@Override
	public String getMetodoFirma() throws Exception {
		String metodoFirma = null;
		try {
			metodoFirma = PreferenceManager.getString(PreferenceKeys.PROPERTY_METODO_FIRMA);
			LogWriter.writeLog("Parametro " + PreferenceKeys.PROPERTY_METODO_FIRMA + " :" + metodoFirma);
		} catch (Exception e) {
		}

		return metodoFirma;
	}

}
