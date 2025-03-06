package it.eng.hybrid.module.jpedal.fileOutputProvider;

import it.eng.hybrid.module.jpedal.messages.MessageConstants;
import it.eng.hybrid.module.jpedal.messages.Messages;
import it.eng.hybrid.module.jpedal.preferences.ConfigConstants;
import it.eng.hybrid.module.jpedal.preferences.PreferenceManager;
import it.eng.hybrid.module.jpedal.util.FileUtility;

import java.applet.AppletContext;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import org.apache.log4j.Logger;


public class CallbackFileOutputProvider implements FileOutputProvider {

	public final static Logger logger = Logger.getLogger(CallbackFileOutputProvider.class);
	
	private String  outputDir;
	private String  outputFileName;
	private boolean fileUploaded = false;
	private String  callBack;
	private AppletContext appletContext;
	
	@Override
	public void saveOutputFile(InputStream in, String fileInputName, PreferenceManager preferenceManager) throws Exception {
		logger.info("Metodo saveOutputFile");

		String lStrToInvoke   = "javascript:" + callBack + "('";
		String outputFileName = getOutputFileName();
		if( getOutputFileName()==null){
			outputFileName = FileUtility.getOutputFileNameToReturn(fileInputName, preferenceManager);
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

			lStrToInvoke += outputFilePath +"');";

			logger.info("Creo il file " + outputFilePath );
			try {
				OutputStream out = new FileOutputStream( outputFilePath );
		        byte[] buf = new byte[1024];
		        int len;
		        while ((len = in.read(buf)) > 0) {
		            out.write(buf, 0, len);
		        }
		        in.close();
		        out.close();
		        fileUploaded = true;
		        
		        //JSObject.getWindow(applet).eval( callBack +"('" + outputFilePath +"');");
			} catch (FileNotFoundException e) {
				logger.info("Errore nel salvataggio del file di output " + outputFilePath, e );
				fileUploaded = false;
			} catch (IOException e) {
				logger.info("Errore nel salvataggio del file di output " + outputFilePath, e );
				fileUploaded = false;
			}
		} else {
			logger.info( "Errore, " + Messages.getMessage(MessageConstants.MSG_MISSINGPARAMETERS ) );
			logger.info( "Salvataggio file non eseguito");
			fileUploaded = false;
		}

//		if( getAppletContext()!=null )
//			getAppletContext().showDocument(new URL( lStrToInvoke ) );
		
	}

	@Override
	public void saveOutputParameter(PreferenceManager preferenceManager) throws Exception {
		String outputDir = null;
		String outputFileName = null;
		try {
			outputDir = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_OUTPUTDIR );
			logger.info("Parametro  " + ConfigConstants.PROPERTY_OUTPUTDIR + ": " + outputDir);
			setOutputDir( outputDir );
			outputFileName = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_OUTPUTFILENAME );
			logger.info( "Parametro  " + ConfigConstants.PROPERTY_OUTPUTFILENAME + ": " + outputFileName);
			setOutputFileName( outputFileName );
			callBack = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_CALLBACK );
			logger.info("Parametro " + ConfigConstants.PROPERTY_CALLBACK + ": " + callBack);
			setCallBack( callBack );

//			setAppletContext( applet.getAppletContext() );
//			
//			if( applet!=null ){			
//				setApplet(applet);
//			}
			
		} catch (Exception e) {
			logger.info("Errore", e);
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

	public String getCallBack() {
		return callBack;
	}

	public void setCallBack(String callBack) {
		this.callBack = callBack;
	}

	public AppletContext getAppletContext() {
		return appletContext;
	}

	public void setAppletContext(AppletContext appletContext) {
		this.appletContext = appletContext;
	}
	
//	public JApplet getApplet() {
//		return applet;
//	}
//
//	public void setApplet(JApplet applet) {
//		this.applet = applet;
//	}

	@Override
	public boolean tryTosaveOutputFile(InputStream in, String fileInputName,
			PreferenceManager preferenceManager) throws Exception {
		saveOutputFile(in, fileInputName, preferenceManager);
		logger.info("Upload andato a buon fine: " + fileUploaded);
		return fileUploaded;
	}

}
