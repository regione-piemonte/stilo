package it.eng.client.applet.util;

import it.eng.client.applet.i18N.MessageKeys;
import it.eng.client.applet.i18N.Messages;
import it.eng.client.applet.operation.AbstractSigner;
import it.eng.client.applet.operation.jsignpdf.HashAlgorithm;
import it.eng.common.type.SignerType;

import java.awt.Component;
import java.io.File;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JApplet;
import javax.swing.JOptionPane;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.web.AppletConfiguration;

public class PreferenceManager {

	private static Configuration config;
	private static Configuration metaConfig;
	private static JApplet applet;//leggi conf dai par dell'aplet
	private static String[] args;//leggi conf dai par del main
	private static String userPrefDirPath = System.getProperty("user.home") + File.separator + "config";
	private static String userPrefFilePath = userPrefDirPath + File.separator + "signerAppletUser.properties";
	private static String defaultPrefFilePath = "signerAppletDefault.properties";
	static Map<String, Object> lMap = new HashMap<String, Object>();
	
	private static Configuration getConfiguration(){
		if(config==null){
			//launch error
			//throw new Exception("configuration not initialized");
			JOptionPane.showMessageDialog(null,  Messages.getMessage( MessageKeys.MSG_ERROR_PREFERENCELOADING ),
					Messages.getMessage( MessageKeys.MSG_ERROR ), JOptionPane.ERROR_MESSAGE);
			throw new RuntimeException("configuration not initialized");
		}
		return config;
	}
	
	public static String getString(String pref){
		if (lMap.get(pref)!=null) return (String)lMap.get(pref);
		else return config.getString(pref);
	}
	
	public static String getString(String pref, String param2){
		if (lMap.get(pref)!=null) return (String)lMap.get(pref);
		else return config.getString(pref, param2);
	}
	
	public static int getInt(String pref){
		if (lMap.get(pref)!=null) return (Integer)lMap.get(pref);
		else return config.getInt(pref);
	}
	
	public static int getInt(String pref, int param2){
		if (lMap.get(pref)!=null) return (Integer)lMap.get(pref);
		else return config.getInt(pref, param2);
	}
	
	public static float getFloat(String paramString, float paramFloat){
		if (lMap.get(paramString)!=null) return (Float)lMap.get(paramString);
		else return config.getFloat(paramString, paramFloat);
	}
	
	public static String[] getStringArray(String paramString){
		if (lMap.get(paramString)!=null) return (String[])lMap.get(paramString);
		else return config.getStringArray(paramString);
	}
	
	public static boolean enabled(String prop){
		return config.getBoolean(prop,false);
	}
	
	public static synchronized void initConfig(JApplet japplet){
		//		CombinedConfiguration gconf=null;
		//		try{
		//			DefaultConfigurationBuilder factory = new DefaultConfigurationBuilder("it/eng/client/applet/config.xml");
		//			//factory.addConfigurationProvider("applet", new AppletConfiguration(this));
		//			//read fixed default conf
		//			gconf = (CombinedConfiguration)factory.getConfiguration();
		//			//sovrascrivi con i par dell dell'applet
		//			gconf.addConfiguration(new AppletConfiguration(this));
		//			
		//		}catch(Exception ex){
		//			ex.printStackTrace();//TODO use log
		//		}
		//		return gconf;
		applet=japplet;
		CompositeConfiguration cconf=new CompositeConfiguration();
		try{
			if(japplet!=null){
				cconf.addConfiguration(new AppletConfiguration(japplet));//ConfigurationUtils.locate(
			}
			File userPropertyDir = new File( userPrefDirPath );
			if( !userPropertyDir.exists() )
				userPropertyDir.mkdir();
			File userPropertyFile = new File( userPrefFilePath );
			if( !userPropertyFile.exists() )
				userPropertyFile.createNewFile();
			cconf.addConfiguration(new PropertiesConfiguration(new File(userPrefFilePath)) );
			cconf.addConfiguration(new PropertiesConfiguration( defaultPrefFilePath ));
			config=cconf;	
		}catch(Exception ex){
			ex.printStackTrace();
			JOptionPane.showMessageDialog((Component)japplet,Messages.getMessage( MessageKeys.MSG_ERROR_PREFERENCELOADING )+ex.getMessage(),"Errore", JOptionPane.ERROR_MESSAGE);
		}

	} 
	//inizializza la
	public static synchronized void initConfig(String[] props){
		 
		args=props;
		CompositeConfiguration cconf=new CompositeConfiguration();
		try{
			if(args!=null){
				PropertiesConfiguration pc= new PropertiesConfiguration();
				StringBuffer sb=new StringBuffer();
				
				for (int i = 0; i < props.length; i++) {
					sb.append(props[i]+"\n");
				}
				pc.load(new StringReader(sb.toString()));
				cconf.addConfiguration(pc);//ConfigurationUtils.locate(
			}
			 
			File userPropertyDir = new File( userPrefDirPath );
			if( !userPropertyDir.exists() )
				userPropertyDir.mkdir();
			File userPropertyFile = new File( userPrefFilePath );
			if( !userPropertyFile.exists() )
				userPropertyFile.createNewFile();
			cconf.addConfiguration(new PropertiesConfiguration( new File(userPrefFilePath ) ) );
			cconf.addConfiguration(new PropertiesConfiguration( defaultPrefFilePath ) );
			config=cconf;
			 
			 
		}catch(Exception ex){
			 
			ex.printStackTrace();
			throw new RuntimeException("fatal reading configuration");
			//JOptionPane.showMessageDialog((Component)japplet,"Errore Caricamento configurazioni!"+ex.getMessage(),"Errore", JOptionPane.ERROR_MESSAGE);
		}

	} 

	
	public static Configuration getMetaConfig(){
		if(metaConfig==null){
			try {
				metaConfig=new PropertiesConfiguration("meta.properties");
			} catch(Exception ex){
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
		File userPref=new File(userPrefFilePath);
		PropertiesConfiguration config = new PropertiesConfiguration(userPref);
		config.setProperty(prop, value);
		config.save();
		//config.save("it/eng/client/applet/usergui.backup.properties");
	}
	
	public static void savePropOnFly(String prop, Object value)throws Exception{
	    lMap.put(prop, value);
		//config.save("it/eng/client/applet/usergui.backup.properties");
	}
	
	public static void reinitConfig(){
		if(applet!=null)
			initConfig(applet);
		else if(args!=null)
			initConfig(args);
		else
			initConfig((JApplet)null);
	}
	//some utility
	//algoritmo di digest da usare
	public static HashAlgorithm getSignerDigestAlg(){
		String algid=getConfiguration().getString( PreferenceKeys.PROPERTY_SIGN_ALGORITHM );
		return HashAlgorithm.valueOf( algid );
	}
	//tipo di busta da fare
	public static SignerType getEnvelopeType(){
		if (lMap.get(PreferenceKeys.PROPERTY_SIGN_ENVELOPE_TYPE)!=null) return SignerType.valueOf((String)lMap.get(PreferenceKeys.PROPERTY_SIGN_ENVELOPE_TYPE));
		String evelopeType=getConfiguration().getString(PreferenceKeys.PROPERTY_SIGN_ENVELOPE_TYPE);
		return SignerType.valueOf(evelopeType);
	}
	
	public static void dumpActualConf(){
		//LogWriter.writeLog("*********"+"actual preferences" +"*********");
		Iterator<String> iterator=getConfiguration().getKeys();
		while(iterator.hasNext()) {
			String key=iterator.next();
			//LogWriter.writeLog(key+"="+getConfiguration().getString(key));
		}
		//LogWriter.writeLog("*********"+"end actual preferences" +"*********");
	}

	public static boolean getBoolean(String pref) {
		if (lMap.get(pref)!=null) return (Boolean)lMap.get(pref);
		else return config.getBoolean(pref);
	}
	
}
