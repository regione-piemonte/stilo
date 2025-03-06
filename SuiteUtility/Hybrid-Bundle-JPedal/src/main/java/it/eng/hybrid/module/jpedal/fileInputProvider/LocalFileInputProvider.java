package it.eng.hybrid.module.jpedal.fileInputProvider;

import it.eng.hybrid.module.jpedal.messages.MessageConstants;
import it.eng.hybrid.module.jpedal.messages.Messages;
import it.eng.hybrid.module.jpedal.preferences.ConfigConstants;
import it.eng.hybrid.module.jpedal.preferences.PreferenceManager;

import java.io.File;

import org.apache.log4j.Logger;

public class LocalFileInputProvider implements FileInputProvider {

	public final static Logger logger = Logger.getLogger(LocalFileInputProvider.class);
	
	@Override
	public FileInputResponse getFile(PreferenceManager preferenceManager) throws Exception {
		FileInputResponse response = null;
		String localFile=null;
		try {
			localFile = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_LOCALFILE );
        	logger.info("Parametro " + ConfigConstants.PROPERTY_LOCALFILE + ":" + localFile );
        } catch (Exception e) {}
		
		String filename = null;
		try {
			filename = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_FILENAME );
        	logger.info("Parametro " + ConfigConstants.PROPERTY_FILENAME + ":" + filename );
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
