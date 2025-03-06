package it.eng.areas.hybrid.module.siss.fileInputProvider;

import it.eng.areas.hybrid.module.siss.bean.FileBean;
import it.eng.areas.hybrid.module.siss.messages.MessageKeys;
import it.eng.areas.hybrid.module.siss.messages.Messages;
import it.eng.areas.hybrid.module.siss.preferences.PreferenceKeys;
import it.eng.areas.hybrid.module.siss.preferences.PreferenceManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;


public class LocalFileInputProvider implements FileInputProvider {

	public final static Logger logger = Logger.getLogger(LocalFileInputProvider.class);
	
	@Override
	public FileInputResponse getFileInputResponse() throws Exception {
		FileInputResponse response = null;
		String localFile=null;
		try {
			localFile = PreferenceManager.getString( PreferenceKeys.PROPERTY_LOCALFILE );
        	logger.info("Parametro " + PreferenceKeys.PROPERTY_LOCALFILE + " :" + localFile );
        } catch (Exception e) {}
		
		String filename = null;
		try {
			filename = PreferenceManager.getString( PreferenceKeys.PROPERTY_FILENAME );
        	logger.info("Parametro " + PreferenceKeys.PROPERTY_FILENAME + " :" + filename );
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
