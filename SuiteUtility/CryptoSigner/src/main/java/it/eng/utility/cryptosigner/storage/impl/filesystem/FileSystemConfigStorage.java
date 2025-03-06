package it.eng.utility.cryptosigner.storage.impl.filesystem;

import it.eng.utility.cryptosigner.bean.ConfigBean;
import it.eng.utility.cryptosigner.exception.CryptoStorageException;
import it.eng.utility.cryptosigner.storage.IConfigStorage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

/**
 * Implmentazione di uno storage di configurazioni basato su file system
 * @author Michele Rigo
 *
 */
public class FileSystemConfigStorage implements IConfigStorage {
	
	Logger log = Logger.getLogger(FileSystemConfigStorage.class);
	
	private static final String FILE_CONFIG_DIRECTORY = "CONFIG";
	private static final String FILE_CONFIG_NAME = "Configuration";
	
	/**
	 * Directory di salvataggio dei certificati
	 */
	private String directory;
	
	/**
	 * Recupera la directory di salvataggio dei certificati
	 * @return
	 */
	public String getDirectory() {
		return directory;
	}

	/**
	 * Definisce la directory di salvataggio dei certificati
	 * @param directory
	 */
	public void setDirectory(String directory) {
		this.directory = directory;
	}

	
	public void deleteConfig(String subjectDN) {
		log.info("deleteConfig START");
		
		//Deserializzo il file per recuperare la lista delle configurazioni
		File file = new File(directory+File.separator+FILE_CONFIG_DIRECTORY+File.separator+FILE_CONFIG_NAME);
		List<ConfigBean> lista = new ArrayList<ConfigBean>();
		if(file.exists()){
			try{
				ObjectInputStream input = new ObjectInputStream(FileUtils.openInputStream(file)); 
				lista = (List<ConfigBean>)input.readObject();
				input.close();
			}catch(Exception e){
				log.warn("deleteConfig warning lettura file serializzato!",e);
				lista = new ArrayList<ConfigBean>();
			}
		}
			
		//Ciclo le configurazioni
		for(int i=0;i<lista.size();i++){
			if(lista.get(i).getSubjectDN().equals(subjectDN)){
				lista.remove(i);
				break;
			}
		}
		try{
			//Serializzo la lista
			FileOutputStream fileConfig = new FileOutputStream(file);
			ObjectOutputStream streamOut = new ObjectOutputStream(fileConfig);
			streamOut.writeObject(lista);
			streamOut.flush();
			streamOut.close();
		}catch(IOException e){
			log.warn("deleteConfig warning scrittura file serializzato!",e);
		}	
		log.info("deleteConfig END");
	}

	
	public void insertConfig(ConfigBean config){
		log.info("insertConfig START");
		//Deserializzo il file per recuperare la lista delle configurazioni
		File dir = new File(directory+File.separator+FILE_CONFIG_DIRECTORY);
		if(!dir.exists()){
			dir.mkdir();
		}
		
		File file = new File(directory+File.separator+FILE_CONFIG_DIRECTORY+File.separator+FILE_CONFIG_NAME);
		List<ConfigBean> lista = new ArrayList<ConfigBean>();
		if(file.exists()){
			try{
				ObjectInputStream input = new ObjectInputStream(FileUtils.openInputStream(file)); 
				lista = (List<ConfigBean>)input.readObject();
				input.close();
			}catch(Exception e){
				log.warn("insertConfig warning lettura file serializzato!",e);
				lista = new ArrayList<ConfigBean>();
			}
		}
		
		boolean newfile = true;
		
		//Ciclo le configurazioni
		for(int i=0;i<lista.size();i++){
			if(lista.get(i).getSubjectDN().equals(config.getSubjectDN())){
				newfile = false; 
				lista.set(i, config);
			}
		}
		if(newfile){
			lista.add(config);
		}
		try{
			//Serializzo la lista
			FileOutputStream fileConfig = new FileOutputStream(file);
			ObjectOutputStream streamOut = new ObjectOutputStream(fileConfig);
			streamOut.writeObject(lista);
			streamOut.flush();
			streamOut.close();
		}catch(IOException e){
			log.warn("insertConfig warning scrittura file serializzato!",e);
		}	
		log.info("insertConfig END");
	}

	
	public ConfigBean retriveConfig(String subjectDN) {
		log.info("retriveConfig START");
		//Deserializzo il file per recuperare la lista delle configurazioni
		File file = new File(directory+File.separator+FILE_CONFIG_DIRECTORY+File.separator+FILE_CONFIG_NAME);
		ConfigBean ret = null;
		if(file.exists()){
			try{
				List<ConfigBean> lista = new ArrayList<ConfigBean>();
				ObjectInputStream input = new ObjectInputStream(FileUtils.openInputStream(file)); 
				lista = (List<ConfigBean>)input.readObject();
				input.close();
				
				//Ciclo le configurazioni
				for(int i=0;i<lista.size();i++){
					if(lista.get(i).getSubjectDN().equals(subjectDN)){
						ret = lista.get(i);						
						break;
					}
				}
			}catch(Exception e){
				log.warn("retriveAllConfig warning lettura file serializzato!");
				ret = null;
			}
		}
		log.info("retriveConfig END");
		return ret;
	}

	
	public List<ConfigBean> retriveAllConfig() throws CryptoStorageException {
		log.info("retriveAllConfig START");
		//Deserializzo il file per recuperare la lista delle configurazioni
		File file = new File(directory+File.separator+FILE_CONFIG_DIRECTORY+File.separator+FILE_CONFIG_NAME);
		List<ConfigBean> ret = null;
		if(file.exists()){
			try{
				ObjectInputStream input = new ObjectInputStream(FileUtils.openInputStream(file)); 
				ret = (List<ConfigBean>)input.readObject();
				input.close();
			}catch(Exception e){
				log.warn("retriveAllConfig warning lettura file serializzato!");
				ret = null;
			}
		}
		log.info("retriveAllConfig END");
		return ret;
	}
}