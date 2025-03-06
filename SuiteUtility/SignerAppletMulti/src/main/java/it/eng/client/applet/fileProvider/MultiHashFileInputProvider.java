package it.eng.client.applet.fileProvider;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;

import it.eng.client.applet.i18N.MessageKeys;
import it.eng.client.applet.i18N.Messages;
import it.eng.client.applet.util.PreferenceKeys;
import it.eng.client.applet.util.PreferenceManager;
import it.eng.common.LogWriter;
import it.eng.common.bean.HashFileBean;
import it.eng.common.type.SignerType;


public class MultiHashFileInputProvider implements FileInputProvider {

	@Override
	public FileInputResponse getFileInputResponse() throws Exception {
		FileInputResponse response = null;
		List<HashFileBean> hashFileBean = new ArrayList<HashFileBean>() ;

		String count = PreferenceManager.getString( PreferenceKeys.PROPERTY_NUMFILES);
		LogWriter.writeLog("Parametro " + PreferenceKeys.PROPERTY_NUMFILES + ": " + count);
		for(int i=0;i<Integer.parseInt(count);i++){
			HashFileBean bean = new HashFileBean();
			String idFile = PreferenceManager.getString( PreferenceKeys.PROPERTY_IDFILE+i );
			LogWriter.writeLog("Parametro " + PreferenceKeys.PROPERTY_IDFILE + ": " + idFile);
			bean.setId(  idFile );
			
			if(  PreferenceManager.getString( PreferenceKeys.PROPERTY_TEMPPATHFILE+i )!=null ){
				String tempFile = new String (Base64.decodeBase64( PreferenceManager.getString( PreferenceKeys.PROPERTY_TEMPPATHFILE+i ) ) );
				LogWriter.writeLog("Parametro " + PreferenceKeys.PROPERTY_TEMPPATHFILE + ": " + tempFile);
				bean.setTempFilePath( tempFile );
			}
			
			String hashFile = PreferenceManager.getString( PreferenceKeys.PROPERTY_HASHFILE+i );
			LogWriter.writeLog("Parametro " + PreferenceKeys.PROPERTY_HASHFILE + ": " + hashFile);
			bean.setHash( Base64.decodeBase64( hashFile ) );
			
			String fileName = PreferenceManager.getString( PreferenceKeys.PROPERTY_FILENAME+i );
			LogWriter.writeLog("Parametro " + PreferenceKeys.PROPERTY_FILENAME + ": " + fileName);
			bean.setFileName(  fileName );
			
			String firmatario = PreferenceManager.getString( PreferenceKeys.PROPERTY_FIRMATARIOFILE+i );
			LogWriter.writeLog("Parametro " + PreferenceKeys.PROPERTY_FIRMATARIOFILE + ": " + firmatario);
			bean.setFirmatario( firmatario );
			
			String firmaPresente = PreferenceManager.getString( PreferenceKeys.PROPERTY_ISFIRMAPRESENTE+i );
			LogWriter.writeLog("Parametro " + PreferenceKeys.PROPERTY_ISFIRMAPRESENTE + ": " + firmaPresente);
			bean.setIsFirmaPresente( firmaPresente );
			
			String firmaValida = PreferenceManager.getString( PreferenceKeys.PROPERTY_ISFIRMAVALIDA+i );
			LogWriter.writeLog("Parametro " + PreferenceKeys.PROPERTY_ISFIRMAVALIDA + ": " + firmaValida);
			bean.setIsFirmaValida( firmaValida );
			
			
			if( firmaPresente==null || firmaValida==null)
				throw new Exception( Messages.getMessage( MessageKeys.MSG_ERROR_MISSINGPARAMETERS ) );
			
			String versione = PreferenceManager.getString( PreferenceKeys.PROPERTY_VERSIONEFILE+i );
			LogWriter.writeLog("Parametro " + PreferenceKeys.PROPERTY_VERSIONEFILE + ": " + versione);
			bean.setVersione( versione );
			
			String fileType = PreferenceManager.getString( PreferenceKeys.PROPERTY_FILETYPE+i );
			LogWriter.writeLog("Parametro " + PreferenceKeys.PROPERTY_FILETYPE + ": " + fileType);
			if( fileType!=null )
				bean.setFileType( SignerType.valueOf( fileType ) );
			
			hashFileBean.add( bean );
		}
		response = new FileInputResponse();
		response.setHashfilebean( hashFileBean );

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
//		LogWriter.writeLog("Parametro " + PreferenceKeys.PROPERTY_BREAK_ON_WRONG_PIN + ": " + breakOnWrongPin);
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
