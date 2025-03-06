package it.eng.client.applet.fileProvider;


import it.eng.client.applet.i18N.MessageKeys;
import it.eng.client.applet.i18N.Messages;
import it.eng.client.applet.util.FileUtility;
import it.eng.client.applet.util.PreferenceKeys;
import it.eng.client.applet.util.PreferenceManager;
import it.eng.common.LogWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.JApplet;

public class LocalFileOutputProvider implements FileOutputProvider {

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
		LogWriter.writeLog("outputFileName " + outputFileName );
		LogWriter.writeLog("outputDir " + outputDir, false );
		
		if( getOutputDir()!=null ){
			File outputDir = new File( getOutputDir() );
			if( !outputDir.exists()){
				LogWriter.writeLog("Creo la directory " + outputDir, false );
				outputDir.mkdir();
			}
			String outputFilePath = getOutputDir() + "/" + outputFileName;
			LogWriter.writeLog("Creo il file " + outputFilePath, false );
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
				LogWriter.writeLog("Errore nel salvataggio del file di output " + outputFilePath, false, e );
			} catch (IOException e) {
				LogWriter.writeLog("Errore nel salvataggio del file di output " + outputFilePath, false, e );
			}
		} else {
			LogWriter.writeLog( "Errore, " + Messages.getMessage(MessageKeys.MSG_ERROR_MISSINGPARAMETERS ) );
			LogWriter.writeLog( "Salvataggio file non eseguito");
		}
		
		return true;
	}

	@Override
	public void saveOutputParameter(JApplet applet) throws Exception {
		String outputDir = null;
		String outputFileName = null;
		try {
			outputDir = PreferenceManager.getString( PreferenceKeys.PROPERTY_OUTPUTDIR );
			LogWriter.writeLog("Parametro  " + PreferenceKeys.PROPERTY_OUTPUTDIR + ": " + outputDir);
			setOutputDir( outputDir );
			outputFileName = PreferenceManager.getString( PreferenceKeys.PROPERTY_OUTPUTFILENAME );
			LogWriter.writeLog( "Parametro  " + PreferenceKeys.PROPERTY_OUTPUTFILENAME + ": " + outputFileName);
			setOutputFileName( outputFileName );
			
			String autoClosePostSignString = PreferenceManager.getString( PreferenceKeys.PROPERTY_AUTOCLOSEPOSTSIGN);
			LogWriter.writeLog("Parametro " + PreferenceKeys.PROPERTY_AUTOCLOSEPOSTSIGN + ": " + autoClosePostSignString);
			if( autoClosePostSignString!=null )
				autoClosePostSign  = Boolean.valueOf( autoClosePostSignString ); 

			callBackAskForClose = PreferenceManager.getString( PreferenceKeys.PROPERTY_CALLBACKASKFORCLOSE );
			LogWriter.writeLog("Parametro " + PreferenceKeys.PROPERTY_CALLBACKASKFORCLOSE + ": " + callBackAskForClose);
			
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
