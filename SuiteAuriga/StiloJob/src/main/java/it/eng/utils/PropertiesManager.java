/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertiesManager {
	
	private static Logger log = Logger.getLogger(PropertiesManager.class);
	
	public Properties getProperties(String propertiesFileName) {		 		
		InputStream inputStream = null;
		try {					
			inputStream = getClass().getClassLoader().getResourceAsStream(propertiesFileName);
			if (inputStream == null) {
				log.debug("Sorry, unable to find " + propertiesFileName);
				return null;
			} 
			Properties properties = new Properties();
			properties.load(inputStream);			     
			return properties;
		} catch (FileNotFoundException e) {
			log.warn(e);
		} catch (IOException e) {
			log.warn(e);
		} finally {
			try {inputStream.close();} catch (Exception e) {}
		}
		return null;		 
	}
	
}
