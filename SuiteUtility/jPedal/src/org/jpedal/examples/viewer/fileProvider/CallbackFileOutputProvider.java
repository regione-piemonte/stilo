package org.jpedal.examples.viewer.fileProvider;

import java.applet.AppletContext;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.swing.JApplet;

import netscape.javascript.JSObject;

import org.jpedal.examples.viewer.config.ConfigConstants;
import org.jpedal.examples.viewer.config.PreferenceManager;
import org.jpedal.examples.viewer.utils.FileUtility;
import org.jpedal.utils.LogWriter;
import org.jpedal.utils.MessageConstants;
import org.jpedal.utils.Messages;


public class CallbackFileOutputProvider implements FileOutputProvider {

	private String  outputDir;
	private String  outputFileName;
	private boolean fileUploaded = false;
	private String  callBack;
	private AppletContext appletContext;
	private JApplet applet;
	
	@Override
	public void saveOutputFile(InputStream in, String fileInputName, PreferenceManager preferenceManager) throws Exception {
		LogWriter.writeLog("Metodo saveOutputFile");

		String lStrToInvoke   = "javascript:" + callBack + "('";
		String outputFileName = getOutputFileName();
		if( getOutputFileName()==null){
			outputFileName = FileUtility.getOutputFileNameToReturn(fileInputName, preferenceManager);
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

			lStrToInvoke += outputFilePath +"');";

			LogWriter.writeLog("Creo il file " + outputFilePath, false );
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
		        
		        JSObject.getWindow(applet).eval( callBack +"('" + outputFilePath +"');");
			} catch (FileNotFoundException e) {
				LogWriter.writeLog("Errore nel salvataggio del file di output " + outputFilePath, false, e );
				fileUploaded = false;
			} catch (IOException e) {
				LogWriter.writeLog("Errore nel salvataggio del file di output " + outputFilePath, false, e );
				fileUploaded = false;
			}
		} else {
			LogWriter.writeLog( "Errore, " + Messages.getMessage(MessageConstants.MSG_MISSINGPARAMETERS ) );
			LogWriter.writeLog( "Salvataggio file non eseguito");
			fileUploaded = false;
		}

//		if( getAppletContext()!=null )
//			getAppletContext().showDocument(new URL( lStrToInvoke ) );
		
	}

	@Override
	public void saveOutputParameter(JApplet applet, PreferenceManager preferenceManager) throws Exception {
		String outputDir = null;
		String outputFileName = null;
		try {
			outputDir = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_OUTPUTDIR );
			LogWriter.writeLog("Parametro  " + ConfigConstants.PROPERTY_OUTPUTDIR + ": " + outputDir);
			setOutputDir( outputDir );
			outputFileName = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_OUTPUTFILENAME );
			LogWriter.writeLog( "Parametro  " + ConfigConstants.PROPERTY_OUTPUTFILENAME + ": " + outputFileName);
			setOutputFileName( outputFileName );
			callBack = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_CALLBACK );
			LogWriter.writeLog("Parametro " + ConfigConstants.PROPERTY_CALLBACK + ": " + callBack);
			setCallBack( callBack );

			setAppletContext( applet.getAppletContext() );
			
			if( applet!=null ){			
				setApplet(applet);
			}
			
		} catch (Exception e) {
			LogWriter.writeLog("Errore", e);
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
	
	public JApplet getApplet() {
		return applet;
	}

	public void setApplet(JApplet applet) {
		this.applet = applet;
	}

	@Override
	public boolean tryTosaveOutputFile(InputStream in, String fileInputName,
			PreferenceManager preferenceManager) throws Exception {
		saveOutputFile(in, fileInputName, preferenceManager);
		LogWriter.writeLog("Upload andato a buon fine: " + fileUploaded);
		return fileUploaded;
	}

}
