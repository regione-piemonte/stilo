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


public class LocalFilesListInputProvider implements FileInputProvider {

	public final static Logger logger = Logger.getLogger(LocalFilesListInputProvider.class);
	
	@Override
	public FileInputResponse getFileInputResponse() throws Exception {
		FileInputResponse response = null;
		List<FileBean> fileBeanList = new ArrayList<FileBean>() ;

		String count = PreferenceManager.getString( PreferenceKeys.PROPERTY_NUMFILES);
		logger.info("Parametro " + PreferenceKeys.PROPERTY_NUMFILES + ": " + count);
		for(int i=0;i<Integer.parseInt(count);i++){
			FileBean bean = new FileBean();
			
			String localFile=null;
			try {
				localFile = PreferenceManager.getString( PreferenceKeys.PROPERTY_LOCALFILE + i );
	        	logger.info("Parametro " + PreferenceKeys.PROPERTY_LOCALFILE + i + " :" + localFile );
	        } catch (Exception e) {}
			
			String filename = null;
			try {
				filename = PreferenceManager.getString( PreferenceKeys.PROPERTY_FILENAME + i );
	        	logger.info("Parametro " + PreferenceKeys.PROPERTY_FILENAME + i + " :" + filename );
	        } catch (Exception e) {}
			
			if( localFile==null || filename==null )
				throw new Exception( Messages.getMessage( MessageKeys.MSG_ERROR_MISSINGPARAMETERS ) );
			
			File file = new File( localFile );
			bean.setFile( file );
			bean.setFileName( filename );
			
			fileBeanList.add( bean );
		}
		response = new FileInputResponse();
		response.setFileBeanList( fileBeanList );

		return response;
	}

}
