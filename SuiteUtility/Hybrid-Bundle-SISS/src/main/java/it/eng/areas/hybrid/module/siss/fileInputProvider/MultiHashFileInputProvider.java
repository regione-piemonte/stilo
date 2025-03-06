package it.eng.areas.hybrid.module.siss.fileInputProvider;

import it.eng.areas.hybrid.module.siss.bean.HashFileBean;
import it.eng.areas.hybrid.module.siss.preferences.PreferenceKeys;
import it.eng.areas.hybrid.module.siss.preferences.PreferenceManager;
import it.eng.areas.hybrid.module.siss.util.SignerType;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;


public class MultiHashFileInputProvider implements FileInputProvider {

	public final static Logger logger = Logger.getLogger(MultiHashFileInputProvider.class);
	
	@Override
	public FileInputResponse getFileInputResponse() throws Exception {
		FileInputResponse response = null;
		List<HashFileBean> hashFileBean = new ArrayList<HashFileBean>() ;

		String count = PreferenceManager.getString( PreferenceKeys.PROPERTY_NUMFILES);
		logger.info("Parametro " + PreferenceKeys.PROPERTY_NUMFILES + ": " + count);
		for(int i=0;i<Integer.parseInt(count);i++){
			HashFileBean bean = new HashFileBean();
			String idFile = PreferenceManager.getString( PreferenceKeys.PROPERTY_IDFILE+i );
			logger.info("Parametro " + PreferenceKeys.PROPERTY_IDFILE + ": " + idFile);
			bean.setId(  idFile );
			
			if(  PreferenceManager.getString( PreferenceKeys.PROPERTY_TEMPPATHFILE+i )!=null ){
				String tempFile = new String (Base64.decodeBase64( PreferenceManager.getString( PreferenceKeys.PROPERTY_TEMPPATHFILE+i ) ) );
				logger.info("Parametro " + PreferenceKeys.PROPERTY_TEMPPATHFILE + ": " + tempFile);
				bean.setTempFilePath( tempFile );
			}
			
			String hashFile = PreferenceManager.getString( PreferenceKeys.PROPERTY_HASHFILE+i );
			logger.info("Parametro " + PreferenceKeys.PROPERTY_HASHFILE + ": " + hashFile);
			bean.setHash( Base64.decodeBase64( hashFile ) );
			
			String fileName = PreferenceManager.getString( PreferenceKeys.PROPERTY_FILENAME+i );
			logger.info("Parametro " + PreferenceKeys.PROPERTY_FILENAME + ": " + fileName);
			bean.setFileName(  fileName );
			
			String firmatario = PreferenceManager.getString( PreferenceKeys.PROPERTY_FIRMATARIOFILE+i );
			logger.info("Parametro " + PreferenceKeys.PROPERTY_FIRMATARIOFILE + ": " + firmatario);
			bean.setFirmatario( firmatario );
			
			String versione = PreferenceManager.getString( PreferenceKeys.PROPERTY_VERSIONEFILE+i );
			logger.info("Parametro " + PreferenceKeys.PROPERTY_VERSIONEFILE + ": " + versione);
			bean.setVersione( versione );
			
			String fileType = PreferenceManager.getString( PreferenceKeys.PROPERTY_FILETYPE+i );
			logger.info("Parametro " + PreferenceKeys.PROPERTY_FILETYPE + ": " + fileType);
			if( fileType!=null )
				bean.setFileType( SignerType.valueOf( fileType ) );
			
			hashFileBean.add( bean );
		}
		response = new FileInputResponse();
		response.setHashfilebean( hashFileBean );

		return response;
	}
	
}
