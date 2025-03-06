package it.eng.client.applet.fileProvider;

import it.eng.client.applet.i18N.MessageKeys;
import it.eng.client.applet.i18N.Messages;
import it.eng.client.applet.util.PreferenceKeys;
import it.eng.client.applet.util.PreferenceManager;
import it.eng.common.LogWriter;

import java.io.File;


public class LocalFileInputProvider implements FileInputProvider {

	@Override
	public FileInputResponse getFile() throws Exception {
		FileInputResponse response = null;
		String localFile=null;
		try {
			localFile = PreferenceManager.getString( PreferenceKeys.PROPERTY_LOCALFILE );
        	LogWriter.writeLog("Parametro " + PreferenceKeys.PROPERTY_LOCALFILE + " :" + localFile );
        } catch (Exception e) {}
		
		String filename = null;
		try {
			filename = PreferenceManager.getString( PreferenceKeys.PROPERTY_FILENAME );
        	LogWriter.writeLog("Parametro " + PreferenceKeys.PROPERTY_FILENAME + " :" + filename );
        } catch (Exception e) {}
		
		if( localFile==null || filename==null )
			throw new Exception( Messages.getMessage( MessageKeys.MSG_ERROR_MISSINGPARAMETERS ) );
		
		File file = new File(localFile);
		//FileInputStream fin;
		//byte[] fileContent = null;
//		try {
		//	fin = new FileInputStream(file);
			//fileContent = new byte[(int)file.length()];
			//fin.read(fileContent);
			response = new FileInputResponse();
			response.setFile(file);
			//response.setFileContent( fileContent );
			response.setFileName(filename);
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		return response;
	}

}
