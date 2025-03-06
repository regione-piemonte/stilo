package org.jpedal.examples.viewer.config;

import java.awt.Component;
import java.io.File;
import java.io.StringReader;


import javax.swing.JApplet;
import javax.swing.JOptionPane;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.web.AppletConfiguration;
import org.jpedal.utils.LogWriter;

public class PreferenceManager {
	
	private Configuration config;
	private Configuration configChiamante;
	private Configuration metaConfig;
	private JApplet applet;

	public Configuration getConfiguration()throws Exception{
		if(config==null){
			//launch error
			throw new Exception("configuration not initialized");
		}
		return config;
	}
	
	public Configuration getConfigurationChiamante()throws Exception{
		if(configChiamante==null){
			//launch error
			throw new Exception("configuration not initialized");
		}
		return configChiamante;
	}

	public boolean enabled(String prop){
		return config.getBoolean(prop,false);
	}
	public synchronized void initConfig(JApplet japplet){
		applet=japplet;
		CompositeConfiguration cconf=new CompositeConfiguration();
		CompositeConfiguration cconfChiamante=new CompositeConfiguration();
		try{
			//test costa
			if(japplet!=null){
				cconf.addConfiguration(new AppletConfiguration(japplet));
				cconfChiamante.addConfiguration(new AppletConfiguration(japplet));
			}
			
			String userPropertyDirPath = System.getProperty("user.home")+"/config";
			File userPropertyDir = new File(userPropertyDirPath);
			if( !userPropertyDir.exists() )
				userPropertyDir.mkdir();
			String userPropertyFilePath = System.getProperty("user.home")+"/config/jPedalUser.properties";
			File userPropertyFile = new File(userPropertyFilePath);
			if( !userPropertyFile.exists() )
				userPropertyFile.createNewFile();
			cconf.addConfiguration(new PropertiesConfiguration( userPropertyFilePath ) );
			
			cconf.addConfiguration(new PropertiesConfiguration("jPedalDefault.properties"));
			config=cconf;
			configChiamante=cconfChiamante;
		}catch(Exception ex){
			ex.printStackTrace();
			JOptionPane.showMessageDialog((Component)japplet,"Errore Caricamento configurazioni!"+ex.getMessage(),"Errore", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public synchronized void initConfig(String[] props){
		CompositeConfiguration cconf=new CompositeConfiguration();
		CompositeConfiguration cconfChiamante=new CompositeConfiguration();
		try{
			if(props!=null){
				PropertiesConfiguration pc= new PropertiesConfiguration();
				StringBuffer sb=new StringBuffer();

				for (int i = 0; i < props.length; i++) {
					sb.append(props[i]+"\n");
				}
				pc.load(new StringReader(sb.toString()));
				cconf.addConfiguration(pc);
				cconfChiamante.addConfiguration(pc);
			}

			String userPropertyDirPath = System.getProperty("user.home")+"/config";
			File userPropertyDir = new File(userPropertyDirPath);
			if( !userPropertyDir.exists() )
				userPropertyDir.mkdir();
			String userPropertyFilePath = System.getProperty("user.home")+"/config/jPedalUser.properties";
			File userPropertyFile = new File(userPropertyFilePath);
			if( !userPropertyFile.exists() )
				userPropertyFile.createNewFile();
			cconf.addConfiguration(new PropertiesConfiguration( userPropertyFilePath ) );
			
			cconf.addConfiguration(new PropertiesConfiguration("jPedalDefault.properties"));
			config=cconf;
			configChiamante=cconfChiamante;
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public Configuration getMetaConfig(){
		if(metaConfig==null){
			try {
				metaConfig=new PropertiesConfiguration("it/eng/client/applet/meta.properties");
			}  
			catch(Exception ex){
				ex.printStackTrace();
				JOptionPane.showMessageDialog(null,"Errore Caricamento configurazioni!"+ex.getMessage(),"Errore", JOptionPane.ERROR_MESSAGE);
			}
		}
		return metaConfig;
	}
	/**
	 * salva la prop nel file di conf come preference del client
	 * @param prop
	 * @param value
	 * @throws Exception
	 */
	public static void saveProp(String prop, Object value)throws Exception{
		PropertiesConfiguration config = new PropertiesConfiguration(System.getProperty("user.home")+"/config/jPedalUser.properties");
		config.setProperty(prop, value);
		LogWriter.writeLog( "Salvo " + prop + " con valore " + value );
		config.save();
	}

	public void reinitConfig(){
		initConfig(applet);
	}
	
	public void emptyConfig(){
		applet=null;
		config=null;
		metaConfig=null;
		configChiamante=null;
	}
}