package it.eng.areas.hybrid.module.siss.fileOutputProvider;


import it.eng.areas.hybrid.module.siss.messages.MessageKeys;
import it.eng.areas.hybrid.module.siss.messages.Messages;
import it.eng.areas.hybrid.module.siss.preferences.PreferenceKeys;
import it.eng.areas.hybrid.module.siss.preferences.PreferenceManager;
import it.eng.areas.hybrid.module.siss.util.FileUtility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.log4j.Logger; 

public class LocalFileOutputProvider implements FileOutputProvider {

	public final static Logger logger = Logger.getLogger(LocalFileOutputProvider.class);
	
	private String outputDir;
	private String outputFileName;
	
	private boolean autoClosePostSign = false;
	private String callBackAskForClose = null;
	
	@Override
	public boolean saveOutputFile(String id, InputStream in, String fileInputName, String tipoBusta, String... params) throws Exception {
		
		String outputFileName = getOutputFileName();
		if( getOutputFileName()==null){
			outputFileName = FileUtility.getOutputFileNameToReturn(fileInputName, tipoBusta);
		}
		logger.info("outputFileName " + outputFileName );
		logger.info("outputDir " + outputDir );
		
		if( getOutputDir()!=null ){
			File outputDir = new File( getOutputDir() );
			if( !outputDir.exists()){
				logger.info("Creo la directory " + outputDir );
				outputDir.mkdir();
			}
			String outputFilePath = getOutputDir() + "/" + outputFileName;
			logger.info("Creo il file " + outputFilePath );
			try {
				OutputStream out = new FileOutputStream( outputFilePath );
		        // Copy the bits from instream to outstream
		        byte[] buf = new byte[1024];
		        int len;
		        while ((len = in.read(buf)) > 0) {
		            out.write(buf, 0, len);
		        }
		        in.close();
		        out.close();
			} catch (FileNotFoundException e) {
				logger.error("Errore nel salvataggio del file di output " + outputFilePath, e );
			} catch (IOException e) {
				logger.info("Errore nel salvataggio del file di output " + outputFilePath, e );
			}
		} else {
			logger.info( "Errore, " + Messages.getMessage(MessageKeys.MSG_ERROR_MISSINGPARAMETERS ) );
			logger.info( "Salvataggio file non eseguito");
		}
		
		return true;
	}

	@Override
	public void saveOutputParameter() throws Exception {
		String outputDir = null;
		String outputFileName = null;
		try {
			outputDir = PreferenceManager.getString( PreferenceKeys.PROPERTY_OUTPUTDIR );
			logger.info("Parametro  " + PreferenceKeys.PROPERTY_OUTPUTDIR + ": " + outputDir);
			setOutputDir( outputDir );
			outputFileName = PreferenceManager.getString( PreferenceKeys.PROPERTY_OUTPUTFILENAME );
			logger.info( "Parametro  " + PreferenceKeys.PROPERTY_OUTPUTFILENAME + ": " + outputFileName);
			setOutputFileName( outputFileName );
			
			String autoClosePostSignString = PreferenceManager.getString( PreferenceKeys.PROPERTY_AUTOCLOSEPOSTSIGN);
			logger.info("Parametro " + PreferenceKeys.PROPERTY_AUTOCLOSEPOSTSIGN + ": " + autoClosePostSignString);
			if( autoClosePostSignString!=null )
				autoClosePostSign  = Boolean.valueOf( autoClosePostSignString ); 

			callBackAskForClose = PreferenceManager.getString( PreferenceKeys.PROPERTY_CALLBACKASKFORCLOSE );
			logger.info("Parametro " + PreferenceKeys.PROPERTY_CALLBACKASKFORCLOSE + ": " + callBackAskForClose);
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public String getOutputDir() {
		return outputDir;
	}

	public void setOutputDir(String outputDir) {
		this.outputDir = outputDir;
	}

	public String getOutputFileName() {
		return outputFileName;
	}

	public void setOutputFileName(String outputFileName) {
		this.outputFileName = outputFileName;
	}

	@Override
	public boolean getAutoClosePostSign() {
		return autoClosePostSign;
	}
	
	@Override
	public String getCallBackAskForClose() {
		return callBackAskForClose;
	}
}
