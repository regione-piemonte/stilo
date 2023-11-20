/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ReadProperties {
	
	private static Logger log = Logger.getLogger(ReadProperties.class);
	
	public String getProperty(String key) {
		 
		Properties prop = new Properties();

		InputStream inputStream = null;
  
		try {
					
			inputStream = getClass().getClassLoader().getResourceAsStream("albopretorio.properties");
			if (inputStream == null) {
				log.debug("Sorry, unable to find albopretorio.properties");
				return "";
			}
			
			
			if (inputStream != null) {
				prop.load(inputStream);			     
	 
		
				if (prop.getProperty(key)==null){
					return "";
				}else{
					return prop.getProperty(key);
				}
			}
		} catch (FileNotFoundException e) {
			log.warn(e);
		} catch (IOException e) {
			log.warn(e);
		}

		return "";
		 
	}
}
