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
package it.eng.hybrid.module.jpedal.ui;

import it.eng.areas.hybrid.module.util.json.JSONArray;
import it.eng.areas.hybrid.module.util.json.JSONObject;
import it.eng.hybrid.module.jpedal.JPedalClientModule;
import it.eng.hybrid.module.jpedal.fileInputProvider.FileInputProvider;
import it.eng.hybrid.module.jpedal.fileInputProvider.FileInputResponse;
import it.eng.hybrid.module.jpedal.fileOutputProvider.FileOutputProvider;
import it.eng.hybrid.module.jpedal.fileOutputProvider.SalvaFileProvider;
import it.eng.hybrid.module.jpedal.io.ObjectStore;
import it.eng.hybrid.module.jpedal.preferences.ConfigConstants;
import it.eng.hybrid.module.jpedal.preferences.PreferenceManager;
import it.eng.hybrid.module.jpedal.viewer.Commands;
import it.eng.hybrid.module.jpedal.viewer.Values;
import it.eng.hybrid.module.jpedal.viewer.Viewer;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.Security;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.swing.JFrame;

import org.apache.log4j.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class JPedalApplication extends JFrame {

	public final static Logger logger = Logger.getLogger(JPedalApplication.class);
	
	private JPedalClientModule module;
	
	private static final long serialVersionUID = 8823940529835337414L;

	public Viewer current;

	private boolean isInitialised=false;
	private boolean destroy = false;

	private FileInputProvider fileInputProvider;
	private FileOutputProvider fileOutputProvider;
	private FileInputResponse fileInputProviderResponse;
	private PreferenceManager preferenceManager;
	private boolean externalSaveAs = false;
	private SalvaFileProvider salvaFileProvider;

	public JPedalApplication(JPedalClientModule module, JSONArray parameters) {
		//super(owner);
		this.module = module;
	
		writeSystemProperty();
		
		init(parameters);
		start();
	}
	
	/** main method to run the software */
	public void init(JSONArray parameters)	{
		if(!isInitialised){

			isInitialised=true;

			//inizializzo le preference
			preferenceManager = new PreferenceManager();
			preferenceManager.initConfig(parameters);
			
			current = new Viewer(Values.RUNNING_APPLET);
			
			current.commonValues.setPreferenceManager(preferenceManager);
			
			boolean logEnabled = false;
			try {
				logEnabled = preferenceManager.getConfiguration().getBoolean( ConfigConstants.PROPERTY_LOGENABLED);
				System.out.println("Proprieta'† " + ConfigConstants.PROPERTY_LOGENABLED + ": " + logEnabled);
			} catch (Exception e) {
				logger.info("Errore nel recupero della proprieta'† " + ConfigConstants.PROPERTY_LOGENABLED );
			}

			if( logEnabled ){
				boolean logFileEnabled = false;
				try {
					logFileEnabled = preferenceManager.getConfiguration().getBoolean( ConfigConstants.PROPERTY_LOGFILEENABLED);
					System.out.println("Proprieta'† " + ConfigConstants.PROPERTY_LOGFILEENABLED + ": " + logFileEnabled);
				} catch (Exception e) {
					logger.info("Errore nel recupero della proprieta'† " + ConfigConstants.PROPERTY_LOGFILEENABLED );
				}
				boolean logArrayEnabled = false;
				try {
					logArrayEnabled = preferenceManager.getConfiguration().getBoolean( ConfigConstants.PROPERTY_LOGARRAYENABLED);
					System.out.println("Proprieta'† " + ConfigConstants.PROPERTY_LOGARRAYENABLED + ": " + logArrayEnabled);
				} catch (Exception e) {
					logger.info("Errore nel recupero della proprieta' " + ConfigConstants.PROPERTY_LOGARRAYENABLED );
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
					logger.info("File di logging " + userLogFilePath);
				}
			}

			Security.addProvider(new BouncyCastleProvider());
			logger.info("Caricamento provider in corso!! !!!");

			//Se introduco properties specifiche uso quelle...
			String props = null;
			try {
				props = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_FILEPROPERTIES );
				logger.info("Propriet√† " + ConfigConstants.PROPERTY_FILEPROPERTIES + " " + props );
			} catch (Exception e) {
				logger.info("Errore nel recupero della propriet√† " + ConfigConstants.PROPERTY_FILEPROPERTIES );
			}
			String tipoApplet = null;
			try {
				tipoApplet = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_TIPOAPPLET );
				logger.info("proprieta'† " + ConfigConstants.PROPERTY_TIPOAPPLET + " " + tipoApplet );
			} catch (Exception e) {
				logger.info("Errore nel recupero della proprieta'† " + ConfigConstants.PROPERTY_TIPOAPPLET );
			}

			if (props != null) {
				current.loadProperties(props);
				if( props.endsWith("Extended.xml"))
					current.setExtendedApplet(true);
				else
					current.setExtendedApplet(false);
				logger.info("Carico la configurazione " + props);
			}
			//...altrimenti carico quelle di Engineering...
			else {
				//Se non definisco l'attributo "tipoApplet" o se l'attributo definito non ÔøΩ "X" lancio le property di Engineering di Base
				if(tipoApplet==null || !tipoApplet.equals("X")) {
					logger.info("Carico la configurazione it//eng//hybrid//module/jpedal/viewer/resources/EngineeringProps.xml");
					current.loadProperties(Viewer.PREFERENCES_ENGINEERING_BASE);
					current.setExtendedApplet(false);		//se ÔøΩ base setto il flag extended a false...
				}
				//Se definisco l'attributo tipoApplet = X, lancio le property Extended!
				else {
					logger.info("Carico la configurazione it//eng//hybrid//module/jpedal/viewer/resources/EngineeringPropsExtended.xml");
					current.loadProperties(Viewer.PREFERENCES_ENGINEERING_EXTENDED);
					current.setExtendedApplet(true);		//se ÔøΩ extended setto il flag extended a true...
				}
			}
			//<end-wrapper>

			Boolean readOnly = true;
			try {
				String readOnlyString = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_READONLY ) ;
				if( readOnlyString!=null )
					readOnly  = Boolean.valueOf( readOnlyString ); 
				logger.info("Visualizzazione in sola lettura? " + readOnly);
			} catch (Exception e) {
				logger.info("Errore nel recupero della propriet√† " + ConfigConstants.PROPERTY_READONLY );
			}

			if( !readOnly ) {
				try {
					String printEnabled = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_PRINTENABLED );
					logger.info("Proprieta'† " + ConfigConstants.PROPERTY_PRINTENABLED + ": " + printEnabled);
					if( printEnabled!=null ){
						current.commonValues.setPrintEnabled( Boolean.valueOf( printEnabled ) );
					}
				} catch (Exception e) {
					logger.info("Errore nel recupero della proprieta'† " + ConfigConstants.PROPERTY_PRINTENABLED );
				}
			} else {
				current.commonValues.setPrintEnabled( false );
			}

			if( !readOnly ) {
				try {
					String saveEnabled = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_SAVEENABLED);
					logger.info("Propriet√† " + ConfigConstants.PROPERTY_SAVEENABLED + ": " + saveEnabled);
					if( saveEnabled!=null ){
						current.commonValues.setSaveEnabled( Boolean.valueOf( saveEnabled ) );
					}
				} catch (Exception e) {
					logger.info("Errore nel recupero della propriet√† " + ConfigConstants.PROPERTY_SAVEENABLED );
				}
			} else {
				current.commonValues.setSaveEnabled( false );
			}

			if( !readOnly ) {
				try {
					String signEnabled = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_SIGNENABLED );
					logger.info("Propriet√† " + ConfigConstants.PROPERTY_SIGNENABLED + ": " + signEnabled);
					if( signEnabled!=null ){
						current.commonValues.setSignEnabled( Boolean.valueOf( signEnabled ) );
					}
				} catch (Exception e) {
					logger.info("Errore nel recupero della propriet√† " + ConfigConstants.PROPERTY_SIGNENABLED );
				}
			} else {
				current.commonValues.setSignEnabled( false );
			}

			if( !readOnly ) {
				try {
					String markEnabled = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_MARKENABLED );
					logger.info("Propriet√† " + ConfigConstants.PROPERTY_MARKENABLED + ": " + markEnabled);
					if( markEnabled!=null ){
						current.commonValues.setMarkEnabled( Boolean.valueOf( markEnabled ) );
					}
				} catch (Exception e) {
					logger.info("Errore nel recupero della propriet√† " + ConfigConstants.PROPERTY_MARKENABLED );
				}
			} else {
				current.commonValues.setMarkEnabled( false );
			}

			if( !readOnly ) {
				try {
					String timbroEnabled = preferenceManager.getConfiguration().getString( ConfigConstants.TIMBRO_PROPERTY_ENABLED ) ;
					logger.info("Propriet√† " + ConfigConstants.TIMBRO_PROPERTY_ENABLED + ": " + timbroEnabled);
					if( timbroEnabled!=null ){
						current.commonValues.setTimbroEnabled( Boolean.valueOf( timbroEnabled ) );
					}

				} catch (Exception e) {
					logger.info("Errore nel recupero della propriet√† " + ConfigConstants.TIMBRO_PROPERTY_ENABLED );
				}
			}else {
				current.commonValues.setTimbroEnabled( false );
			}

			current.setupViewer();

			/**
			 * pass in flag and pickup - we could extend to check and set all values
			 */
			//ANNA
			String mem = "";//getParameter("org.jpedal.memory");
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
				logger.info("Verra' utilizzato il fileInputProvider " + fileInputProviderClass);
			} catch (Exception e2) {
				logger.info("Errore - Non e' stato possibile recuperare il fileInputProvider da utilizzare");
				return;
			}

			if( fileInputProviderClass==null ){
				logger.info("Errore - fileInputProvider non configurato.");
				return;
			}

			try {
				Class cls = Class.forName(fileInputProviderClass);
				fileInputProvider = (FileInputProvider) cls.newInstance();
			} catch (InstantiationException e) {
				StringWriter lStringWriter = new StringWriter();
				PrintWriter lPrintWriter = new PrintWriter(lStringWriter); 
				e.printStackTrace(lPrintWriter);
				logger.info("Errore nel recupero del fileInputProvider: " + lStringWriter.toString());
				return;
			} catch (IllegalAccessException e) {
				StringWriter lStringWriter = new StringWriter();
				PrintWriter lPrintWriter = new PrintWriter(lStringWriter); 
				e.printStackTrace(lPrintWriter);
				logger.info("Errore nel recupero del fileInputProvider: " + lStringWriter.toString());
				return;
			}catch (ClassNotFoundException e) {
				StringWriter lStringWriter = new StringWriter();
				PrintWriter lPrintWriter = new PrintWriter(lStringWriter); 
				e.printStackTrace(lPrintWriter);
				logger.info("Errore nel recupero del fileInputProvider: " + lStringWriter.toString());
				return;
			}

			String fileOutputProviderClass = null;
			try {
				fileOutputProviderClass = preferenceManager.getConfiguration().getString( ConfigConstants.PROPERTY_FILEOUTPUT_PROVIDER );
				logger.info("Verra'† utilizzato il fileOutputProvider " + fileOutputProviderClass);
			} catch (Exception e2) {
				logger.info("Errore - Non e' stato possibile recuperare il fileOutputProvider da utilizzare");
			}
			if( fileOutputProviderClass==null ){
				logger.info("Errore - Non e' stato configurato il fileOutputProvider da utilizzare");
				//return;
			}else {
				try {
					Class cls = Class.forName(fileOutputProviderClass);
					fileOutputProvider = (FileOutputProvider) cls.newInstance();
					current.commonValues.setFileOutputProvider(fileOutputProvider);
				} catch (InstantiationException e) {
					logger.info("Errore nel recupero del fileOutputProvider: " + e.getMessage());
					return;
				} catch (IllegalAccessException e) {
					logger.info("Errore nel recupero del fileOutputProvider: " + e.getMessage());
					return;
				}catch (ClassNotFoundException e) {
					logger.info("Errore nel recupero del fileOutputProvider: " + e.getMessage());
					return;
				} catch (Exception e) {
					logger.info("Errore nel salvataggio dei parametri del fileOutputProvider: " + e.getMessage());
					return;
				}
			}

			if( fileOutputProvider!=null){
				try {
					fileOutputProvider.saveOutputParameter( preferenceManager);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			try {
				fileInputProviderResponse = fileInputProvider.getFile(preferenceManager);
			} catch (Exception e) {
				logger.info("Errore nella lettura del file: " + e.getMessage(), e);
			}
			try {
				if (preferenceManager.getConfiguration().getBoolean(ConfigConstants.PROPERTY_EXTERNAL_SAVE_AS)){
					externalSaveAs = true;
					salvaFileProvider = new SalvaFileProvider();
					salvaFileProvider.saveOutputParameter( preferenceManager);
				}
				current.commonValues.setSalvaFileProvider(salvaFileProvider);
				current.commonValues.setExternalSaveAs(externalSaveAs);
				
			} catch (Exception e) {
				logger.info("Save As interno");
			}

		}

	}

	public void start(){
		

		if( fileInputProviderResponse==null && fileInputProvider!=null ){
			try {
				fileInputProviderResponse = fileInputProvider.getFile(preferenceManager);
			} catch (Exception e) {
				logger.info("Errore nella lettura del file: " + e.getMessage(), e);
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

		if( fileContent!=null && filename!=null ){
			logger.info("fileContent!=null");
			current.executeCommand( Commands.OPENFILE,new Object[]{fileContent, filename});
		} else if( file!=null){
			logger.info("file!=null");
			current.executeCommand( Commands.OPENFILE,new Object[]{file});
		}

	}
	
	public void destroy(){
        logger.info("destroy");
		destroy = true;
		
		fileInputProvider = null;
	    fileOutputProvider = null;
	    fileInputProviderResponse = null;
	    preferenceManager = null;
	    logger.info("annullo gli oggetti");
		
        Viewer.exitOnClose=false;
        if (!current.currentCommands.uploadGestito)
        current.executeCommand(Commands.EXIT,null);
        
        
        //ensure cached items removed
        ObjectStore.flushPages();
        
        //PreferenceManager.emptyConfig();
		
        current.dispose();
        current=null;
        
       // super.destroy();
	}
	
	
	private static void writeSystemProperty(){
		Properties props=System.getProperties(); 
		Iterator<Object> itr = props.keySet().iterator();
		while(itr.hasNext()){
			String key = (String) itr.next();
			logger.info(key + " - " + props.getProperty(key));			
		}
	}
}
