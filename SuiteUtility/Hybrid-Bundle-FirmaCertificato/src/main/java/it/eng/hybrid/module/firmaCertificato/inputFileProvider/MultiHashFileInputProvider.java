package it.eng.hybrid.module.firmaCertificato.inputFileProvider;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import it.eng.common.type.SignerType;
import it.eng.hybrid.module.firmaCertificato.bean.HashFileBean;
import it.eng.hybrid.module.firmaCertificato.messages.MessageKeys;
import it.eng.hybrid.module.firmaCertificato.messages.Messages;
import it.eng.hybrid.module.firmaCertificato.preferences.PreferenceKeys;
import it.eng.hybrid.module.firmaCertificato.preferences.PreferenceManager;


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
			
			String firmaPresente = PreferenceManager.getString( PreferenceKeys.PROPERTY_ISFIRMAPRESENTE+i );
			logger.info("Parametro " + PreferenceKeys.PROPERTY_ISFIRMAPRESENTE + ": " + firmaPresente);
			bean.setIsFirmaPresente( firmaPresente );
			
			String firmaValida = PreferenceManager.getString( PreferenceKeys.PROPERTY_ISFIRMAVALIDA+i );
			logger.info("Parametro " + PreferenceKeys.PROPERTY_ISFIRMAVALIDA + ": " + firmaValida);
			bean.setIsFirmaValida( firmaValida );
			
			
			if( firmaPresente==null || firmaValida==null)
				throw new Exception( Messages.getMessage( MessageKeys.MSG_ERROR_MISSINGPARAMETERS ) );
			
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
