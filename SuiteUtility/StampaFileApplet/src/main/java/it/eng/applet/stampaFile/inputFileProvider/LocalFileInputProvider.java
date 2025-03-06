package it.eng.applet.stampaFile.inputFileProvider;

import it.eng.applet.stampaFile.bean.FileBean;
import it.eng.applet.stampaFile.messages.MessageKeys;
import it.eng.applet.stampaFile.messages.Messages;
import it.eng.applet.stampaFile.preferences.PreferenceKeys;
import it.eng.applet.stampaFile.preferences.PreferenceManager;
import it.eng.applet.stampaFile.util.LogWriter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class LocalFileInputProvider implements FileInputProvider {

	@Override
	public FileInputResponse getFileInputResponse() throws Exception {
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
		FileBean bean = new FileBean();
		bean.setFile(file);
		bean.setFileName(filename);
		
		List<FileBean> fileBeanList = new ArrayList<FileBean>() ;
		fileBeanList.add( bean );
		
		response = new FileInputResponse();
		response.setFileBeanList( fileBeanList );
		return response;
	}


}
