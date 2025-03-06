package org.jpedal.examples.viewer.fileProvider;

import java.io.File;

import org.jpedal.examples.viewer.config.ConfigConstants;
import org.jpedal.examples.viewer.config.PreferenceManager;
import org.jpedal.utils.LogWriter;
import org.jpedal.utils.MessageConstants;
import org.jpedal.utils.Messages;

public class LocalFileInputProvider implements FileInputProvider {

	@Override
	public FileInputResponse getFile(PreferenceManager preferenceManager) throws Exception {
		FileInputResponse response = null;
		String localFile=null;
		try {
			localFile = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_LOCALFILE );
        	LogWriter.writeLog("Parametro " + ConfigConstants.PROPERTY_LOCALFILE + ":" + localFile );
        } catch (Exception e) {}
		
		String filename = null;
		try {
			filename = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_FILENAME );
        	LogWriter.writeLog("Parametro " + ConfigConstants.PROPERTY_FILENAME + ":" + filename );
        } catch (Exception e) {}
		
		if( localFile==null || filename==null )
			throw new Exception( Messages.getMessage( MessageConstants.MSG_MISSINGPARAMETERS ) );
		
		File file = new File(localFile);
		//FileInputStream fin;
		//byte[] fileContent = null;
		//try {
			//fin = new FileInputStream(file);
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
