package it.eng.areas.hybrid.module.cryptoLight.inputFileProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import it.eng.areas.hybrid.module.cryptoLight.sign.FileBean;
import it.eng.areas.hybrid.module.cryptoLight.util.MessageKeys;
import it.eng.areas.hybrid.module.cryptoLight.util.Messages;
import it.eng.areas.hybrid.module.cryptoLight.util.PreferenceKeys;
import it.eng.areas.hybrid.module.cryptoLight.util.PreferenceManager;


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
