/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.client.StringSplitterClient;

import java.util.HashMap;
import java.util.Map;

public class MapCreator {

	private Map<String, String> properties;
	
	public MapCreator(String keys, String values, String delim){
		properties = new HashMap<String, String>();
		String[] arrayOfKeys = new StringSplitterClient(keys, delim).getTokens();
		String[] arrayOfValues = new StringSplitterClient(values, delim).getTokens();
		for(int i = 0; i < arrayOfKeys.length; i++){
			if (i==arrayOfValues.length){
				properties.put(arrayOfKeys[i], null);	
			} else	properties.put(arrayOfKeys[i], arrayOfValues[i]);			
		}
	}

	public Map<String, String> getProperties() {
		return properties;
	}
	
}
