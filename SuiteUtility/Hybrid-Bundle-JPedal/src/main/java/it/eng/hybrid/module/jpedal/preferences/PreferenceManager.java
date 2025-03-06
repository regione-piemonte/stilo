package it.eng.hybrid.module.jpedal.preferences;

import it.eng.areas.hybrid.module.util.json.JSONArray;
import it.eng.areas.hybrid.module.util.json.JSONObject;

import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;

public class PreferenceManager {
	
	public final static Logger logger = Logger.getLogger( PreferenceManager.class );
	
	private Configuration config;
	private Configuration configChiamante;
	private Configuration metaConfig;
	
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
	public synchronized void initConfig(){
		CompositeConfiguration cconf=new CompositeConfiguration();
		CompositeConfiguration cconfChiamante=new CompositeConfiguration();
		try{
			//test costa
			
			String userPropertyDirPath = System.getProperty("user.home")+"/config";
			File userPropertyDir = new File(userPropertyDirPath);
			if( !userPropertyDir.exists() )
				userPropertyDir.mkdir();
			String userPropertyFilePath = System.getProperty("user.home")+"/config/jPedalUser.properties";
			File userPropertyFile = new File(userPropertyFilePath);
			if( !userPropertyFile.exists() )
				userPropertyFile.createNewFile();
			cconf.addConfiguration(new PropertiesConfiguration( userPropertyFilePath ) );
			
			cconf.addConfiguration(new PropertiesConfiguration(PreferenceManager.class.getResource("jPedalDefault.properties")));
			config=cconf;
			configChiamante=cconfChiamante;
		}catch(Exception ex){
			ex.printStackTrace();
			//JOptionPane.showMessageDialog(this,"Errore Caricamento configurazioni!"+ex.getMessage(),"Errore", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public synchronized void initConfig(JSONArray parameters){

		JSONObject options = parameters.getJSONObject(0);
		Iterator optionsItr = options.keys();
		List<String> optionNames = new ArrayList<String>();
		while( optionsItr.hasNext() ){
			optionNames.add((String) optionsItr.next());

		}
		String[] props = new String[options.length()];
		for(int i=0;i<options.length();i++){
			props[i]=optionNames.get(i)+"="+options.getString(optionNames.get(i));
			logger.info("Proprieta' " + optionNames.get(i) + "=" + props[i] );
		}

		CompositeConfiguration cconf=new CompositeConfiguration();
		try{
			if(props!=null){
				PropertiesConfiguration pc= new PropertiesConfiguration();
				StringBuffer sb=new StringBuffer();

				for (int i = 0; i < props.length; i++) {
					sb.append(props[i]+"\n");
				}
				logger.info("sb::: " + sb.toString().trim() );
				pc.load(new StringReader(sb.toString()));
				cconf.addConfiguration(pc);//ConfigurationUtils.locate(
				logger.info(cconf.getString("fileName"));
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
			
			cconf.addConfiguration(new PropertiesConfiguration(PreferenceManager.class.getResource("jPedalDefault.properties")));
			config=cconf;
		}catch(Exception ex){

			ex.printStackTrace();
			throw new RuntimeException("fatal reading configuration");
			//JOptionPane.showMessageDialog((Component)japplet,"Errore Caricamento configurazioni!"+ex.getMessage(),"Errore", JOptionPane.ERROR_MESSAGE);
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
		//LogWriter.writeLog( "Salvo " + prop + " con valore " + value );
		config.save();
	}

	public void reinitConfig(){
		initConfig();
	}
	
	public void emptyConfig(){
		config=null;
		metaConfig=null;
		configChiamante=null;
	}
}