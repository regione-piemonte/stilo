/**
 * ===========================================
 * Java Pdf Extraction Decoding Access Library
 * ===========================================
 *
 * Project Info:  http://www.jpedal.org
 * (C) Copyright 1997-2008, IDRsolutions and Contributors.
 *
 * 	This file is part of JPedal
 *
    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA


 *
 * ---------------
 * AppletViewer.java
 * ---------------
 */
package org.jpedal.examples.viewer;

import java.awt.Graphics;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.Security;
import java.util.ArrayList;

import javax.swing.JApplet;
import javax.swing.JFrame;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jpedal.examples.viewer.config.ConfigConstants;
import org.jpedal.examples.viewer.config.PreferenceManager;
import org.jpedal.examples.viewer.fileProvider.FileInputProvider;
import org.jpedal.examples.viewer.fileProvider.FileInputResponse;
import org.jpedal.examples.viewer.fileProvider.FileOutputProvider;
import org.jpedal.examples.viewer.fileProvider.SalvaFileProvider;
import org.jpedal.io.ObjectStore;
import org.jpedal.utils.LogWriter;


/**
 * <br>Description: Demo to show JPedal being used 
 * as a GUI viewer in an applet,
 * and to demonstrate some of JPedal's capabilities
 * 
 *   See also http://www.jpedal.org/gplSrc/org/jpedal/examples/viewer/Viewer.java.html
 */
public class AppletViewer extends JApplet{

	private static final long serialVersionUID = 8823940529835337414L;

	public Viewer current = new Viewer(Values.RUNNING_APPLET);

	private boolean isInitialised=false;
	private boolean destroy = false;

	private FileInputProvider fileInputProvider;
	private FileOutputProvider fileOutputProvider;
	private FileInputResponse fileInputProviderResponse;
	private PreferenceManager preferenceManager;
	private boolean externalSaveAs = false;
	private SalvaFileProvider salvaFileProvider;

	/** main method to run the software */
	public void init()	{
		if(!isInitialised){

			isInitialised=true;

			current.commonValues.setCurrentApplet( this );

			//inizializzo le preference
			preferenceManager = new PreferenceManager();
			preferenceManager.initConfig(this);
			current.commonValues.setPreferenceManager(preferenceManager);
			System.out.println("Conf inizializzate ");
			
			boolean logEnabled = false;
			try {
				logEnabled = preferenceManager.getConfiguration().getBoolean( ConfigConstants.PROPERTY_LOGENABLED);
				System.out.println("Proprietà " + ConfigConstants.PROPERTY_LOGENABLED + ": " + logEnabled);
			} catch (Exception e) {
				LogWriter.writeLog("Errore nel recupero della proprietà " + ConfigConstants.PROPERTY_LOGENABLED );
			}

			if( logEnabled ){
				boolean logFileEnabled = false;
				try {
					logFileEnabled = preferenceManager.getConfiguration().getBoolean( ConfigConstants.PROPERTY_LOGFILEENABLED);
					System.out.println("Proprietà " + ConfigConstants.PROPERTY_LOGFILEENABLED + ": " + logFileEnabled);
				} catch (Exception e) {
					LogWriter.writeLog("Errore nel recupero della proprietà " + ConfigConstants.PROPERTY_LOGFILEENABLED );
				}
				boolean logArrayEnabled = false;
				try {
					logArrayEnabled = preferenceManager.getConfiguration().getBoolean( ConfigConstants.PROPERTY_LOGARRAYENABLED);
					System.out.println("Proprietà " + ConfigConstants.PROPERTY_LOGARRAYENABLED + ": " + logArrayEnabled);
				} catch (Exception e) {
					LogWriter.writeLog("Errore nel recupero della proprietà " + ConfigConstants.PROPERTY_LOGARRAYENABLED );
				}
				if( logFileEnabled ){
					String userLogDirPath = System.getProperty("user.home")+"/log";
					File userLogDir = new File(userLogDirPath);
					if( !userLogDir.exists() )
						userLogDir.mkdir();
					String userLogFilePath = System.getProperty("user.home")+"/log/jpedal.log";
					File fileLog = new File(userLogFilePath);
					if( fileLog.exists() ) 
						fileLog.delete();
					LogWriter.log_name = userLogFilePath;
					LogWriter.writeLog("File di logging " + userLogFilePath, false);
				}
				if( logArrayEnabled ){
					LogWriter.logArray = new ArrayList<String>();
				}
				LogWriter.initLog();
			}

			Security.addProvider(new BouncyCastleProvider());
			LogWriter.writeLog("Caricamento provider in corso!! !!!");

			//Se introduco properties specifiche uso quelle...
			String props = null;
			try {
				props = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_FILEPROPERTIES );
				LogWriter.writeLog("Proprietà " + ConfigConstants.PROPERTY_FILEPROPERTIES + " " + props );
			} catch (Exception e) {
				LogWriter.writeLog("Errore nel recupero della proprietà " + ConfigConstants.PROPERTY_FILEPROPERTIES );
			}
			String tipoApplet = null;
			try {
				tipoApplet = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_TIPOAPPLET );
				LogWriter.writeLog("proprietà " + ConfigConstants.PROPERTY_TIPOAPPLET + " " + tipoApplet );
			} catch (Exception e) {
				LogWriter.writeLog("Errore nel recupero della proprietà " + ConfigConstants.PROPERTY_TIPOAPPLET );
			}

			if (props != null) {
				current.loadProperties(props);
				if( props.endsWith("Extended.xml"))
					current.setExtendedApplet(true);
				else
					current.setExtendedApplet(false);
				LogWriter.writeLog("Carico la configurazione " + props);
			}
			//...altrimenti carico quelle di Engineering...
			else {
				//Se non definisco l'attributo "tipoApplet" o se l'attributo definito non � "X" lancio le property di Engineering di Base
				if(tipoApplet==null || !tipoApplet.equals("X")) {
					LogWriter.writeLog("Carico la configurazione org/jpedal/examples/viewer/res/preferences/EngineeringProps.xml");
					current.loadProperties(Viewer.PREFERENCES_ENGINEERING_BASE);
					current.setExtendedApplet(false);		//se � base setto il flag extended a false...
				}
				//Se definisco l'attributo tipoApplet = X, lancio le property Extended!
				else {
					LogWriter.writeLog("Carico la configurazione org/jpedal/examples/viewer/res/preferences/EngineeringPropsExtended.xml");
					current.loadProperties(Viewer.PREFERENCES_ENGINEERING_EXTENDED);
					current.setExtendedApplet(true);		//se � extended setto il flag extended a true...
				}
			}
			//<end-wrapper>

			Boolean readOnly = true;
			try {
				String readOnlyString = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_READONLY ) ;
				if( readOnlyString!=null )
					readOnly  = Boolean.valueOf( readOnlyString ); 
				LogWriter.writeLog("Visualizzazione in sola lettura? " + readOnly);
			} catch (Exception e) {
				LogWriter.writeLog("Errore nel recupero della proprietà " + ConfigConstants.PROPERTY_READONLY );
			}

			if( !readOnly ) {
				try {
					String printEnabled = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_PRINTENABLED );
					LogWriter.writeLog("Proprietà " + ConfigConstants.PROPERTY_PRINTENABLED + ": " + printEnabled);
					if( printEnabled!=null ){
						current.commonValues.setPrintEnabled( Boolean.valueOf( printEnabled ) );
					}
				} catch (Exception e) {
					LogWriter.writeLog("Errore nel recupero della proprietà " + ConfigConstants.PROPERTY_PRINTENABLED );
				}
			} else {
				current.commonValues.setPrintEnabled( false );
			}

			if( !readOnly ) {
				try {
					String saveEnabled = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_SAVEENABLED);
					LogWriter.writeLog("Proprietà " + ConfigConstants.PROPERTY_SAVEENABLED + ": " + saveEnabled);
					if( saveEnabled!=null ){
						current.commonValues.setSaveEnabled( Boolean.valueOf( saveEnabled ) );
					}
				} catch (Exception e) {
					LogWriter.writeLog("Errore nel recupero della proprietà " + ConfigConstants.PROPERTY_SAVEENABLED );
				}
			} else {
				current.commonValues.setSaveEnabled( false );
			}

			if( !readOnly ) {
				try {
					String signEnabled = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_SIGNENABLED );
					LogWriter.writeLog("Proprietà " + ConfigConstants.PROPERTY_SIGNENABLED + ": " + signEnabled);
					if( signEnabled!=null ){
						current.commonValues.setSignEnabled( Boolean.valueOf( signEnabled ) );
					}
				} catch (Exception e) {
					LogWriter.writeLog("Errore nel recupero della proprietà " + ConfigConstants.PROPERTY_SIGNENABLED );
				}
			} else {
				current.commonValues.setSignEnabled( false );
			}

			if( !readOnly ) {
				try {
					String markEnabled = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_MARKENABLED );
					LogWriter.writeLog("Proprietà " + ConfigConstants.PROPERTY_MARKENABLED + ": " + markEnabled);
					if( markEnabled!=null ){
						current.commonValues.setMarkEnabled( Boolean.valueOf( markEnabled ) );
					}
				} catch (Exception e) {
					LogWriter.writeLog("Errore nel recupero della proprietà " + ConfigConstants.PROPERTY_MARKENABLED );
				}
			} else {
				current.commonValues.setMarkEnabled( false );
			}

			if( !readOnly ) {
				try {
					String timbroEnabled = preferenceManager.getConfiguration().getString( ConfigConstants.TIMBRO_PROPERTY_ENABLED ) ;
					LogWriter.writeLog("Proprietà " + ConfigConstants.TIMBRO_PROPERTY_ENABLED + ": " + timbroEnabled);
					if( timbroEnabled!=null ){
						current.commonValues.setTimbroEnabled( Boolean.valueOf( timbroEnabled ) );
					}

				} catch (Exception e) {
					LogWriter.writeLog("Errore nel recupero della proprietà " + ConfigConstants.TIMBRO_PROPERTY_ENABLED );
				}
			}else {
				current.commonValues.setTimbroEnabled( false );
			}

			current.setupViewer();

			/**
			 * pass in flag and pickup - we could extend to check and set all values
			 */
			String mem = getParameter("org.jpedal.memory");
			if (mem!= null && mem.equals("true")) {
				System.setProperty("org.jpedal.memory", "true");
			}

			if (current.currentGUI.getFrame() instanceof JFrame){
				this.getContentPane().add(((JFrame)current.currentGUI.getFrame()).getContentPane());
			}else{
				this.getContentPane().add(current.currentGUI.getFrame());
			}

			//<start-wrap>
			/**
            //<end-wrap>
            current.openDefaultFile();
             current.executeCommand(Commands.FACING,null);
            /**/

			String fileInputProviderClass = null;
			try {
				fileInputProviderClass = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_FILEINPUT_PROVIDER );
				LogWriter.writeLog("Verrà utilizzato il fileInputProvider " + fileInputProviderClass);
			} catch (Exception e2) {
				LogWriter.writeLog("Errore - Non è stato possibile recuperare il fileInputProvider da utilizzare");
				return;
			}

			if( fileInputProviderClass==null ){
				LogWriter.writeLog("Errore - fileInputProvider non configurato.");
				return;
			}

			try {
				Class cls = Class.forName(fileInputProviderClass);
				fileInputProvider = (FileInputProvider) cls.newInstance();
			} catch (InstantiationException e) {
				StringWriter lStringWriter = new StringWriter();
				PrintWriter lPrintWriter = new PrintWriter(lStringWriter); 
				e.printStackTrace(lPrintWriter);
				LogWriter.writeLog("Errore nel recupero del fileInputProvider: " + lStringWriter.toString());
				return;
			} catch (IllegalAccessException e) {
				StringWriter lStringWriter = new StringWriter();
				PrintWriter lPrintWriter = new PrintWriter(lStringWriter); 
				e.printStackTrace(lPrintWriter);
				LogWriter.writeLog("Errore nel recupero del fileInputProvider: " + lStringWriter.toString());
				return;
			}catch (ClassNotFoundException e) {
				StringWriter lStringWriter = new StringWriter();
				PrintWriter lPrintWriter = new PrintWriter(lStringWriter); 
				e.printStackTrace(lPrintWriter);
				LogWriter.writeLog("Errore nel recupero del fileInputProvider: " + lStringWriter.toString());
				return;
			}

			String fileOutputProviderClass = null;
			try {
				fileOutputProviderClass = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_FILEOUTPUT_PROVIDER );
				LogWriter.writeLog("Verrà utilizzato il fileOutputProvider " + fileOutputProviderClass);
			} catch (Exception e2) {
				LogWriter.writeLog("Errore - Non è stato possibile recuperare il fileOutputProvider da utilizzare");
			}
			if( fileOutputProviderClass==null ){
				LogWriter.writeLog("Errore - Non è stato configurato il fileOutputProvider da utilizzare");
				//return;
			}else {
				try {
					Class cls = Class.forName(fileOutputProviderClass);
					fileOutputProvider = (FileOutputProvider) cls.newInstance();
					current.commonValues.setFileOutputProvider(fileOutputProvider);
				} catch (InstantiationException e) {
					LogWriter.writeLog("Errore nel recupero del fileOutputProvider: " + e.getMessage());
					return;
				} catch (IllegalAccessException e) {
					LogWriter.writeLog("Errore nel recupero del fileOutputProvider: " + e.getMessage());
					return;
				}catch (ClassNotFoundException e) {
					LogWriter.writeLog("Errore nel recupero del fileOutputProvider: " + e.getMessage());
					return;
				} catch (Exception e) {
					LogWriter.writeLog("Errore nel salvataggio dei parametri del fileOutputProvider: " + e.getMessage());
					return;
				}
			}

			if( fileOutputProvider!=null){
				try {
					fileOutputProvider.saveOutputParameter(this, preferenceManager);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			try {
				fileInputProviderResponse = fileInputProvider.getFile(preferenceManager);
			} catch (Exception e) {
				LogWriter.writeLog("Errore nella lettura del file: " + e.getMessage(), e);
			}
			try {
				if (preferenceManager.getConfiguration().getBoolean(ConfigConstants.PROPERTY_EXTERNAL_SAVE_AS)){
					externalSaveAs = true;
					salvaFileProvider = new SalvaFileProvider();
					salvaFileProvider.saveOutputParameter(this, preferenceManager);
				}
				current.commonValues.setSalvaFileProvider(salvaFileProvider);
				current.commonValues.setExternalSaveAs(externalSaveAs);
				
			} catch (Exception e) {
				LogWriter.writeLog("Save As interno");
			}

		}

	}

	public void start(){
		//ensure setup
		init();

		if( fileInputProviderResponse==null && fileInputProvider!=null ){
			try {
				fileInputProviderResponse = fileInputProvider.getFile(preferenceManager);
			} catch (Exception e) {
				LogWriter.writeLog("Errore nella lettura del file: " + e.getMessage(), e);
			}
		}

		byte[] fileContent = null;
		String filename = null;
		File file = null;
		if( current.commonValues.getSelectedFile()==null){
			if( fileInputProviderResponse!=null ){
				fileContent = fileInputProviderResponse.getFileContent();
				filename = fileInputProviderResponse.getFileName();
				file = fileInputProviderResponse.getFile();
			}
		} else {
			file = new File(current.commonValues.getSelectedFile());
		}
		if( file!=null )
			current.commonValues.setFileInputDir( file.getParentFile() );

		if( fileContent!=null && filename!=null )
			current.executeCommand( Commands.OPENFILE,new Object[]{fileContent, filename});
		else if( file!=null){
			current.executeCommand( Commands.OPENFILE,new Object[]{file});
		}

	}
	
	public void destroy(){
        LogWriter.writeLog("destroy");
		destroy = true;
		
		fileInputProvider = null;
	    fileOutputProvider = null;
	    fileInputProviderResponse = null;
	    preferenceManager = null;
	    LogWriter.writeLog("annullo gli oggetti");
		
        Viewer.exitOnClose=false;
        if (!current.currentCommands.uploadGestito)
        current.executeCommand(Commands.EXIT,null);
        
        
        //ensure cached items removed
        ObjectStore.flushPages();
        
        //PreferenceManager.emptyConfig();
		
        current.dispose();
        current=null;
        
        super.destroy();
	}
}
