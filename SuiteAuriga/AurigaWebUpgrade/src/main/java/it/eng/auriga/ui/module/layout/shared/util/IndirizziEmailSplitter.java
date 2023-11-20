/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Collection;

/**
 * 
 * 
 * @author MATTIA ZANIN
 *
 */

public class IndirizziEmailSplitter {	
		
	private static final String delims = ";, ";
	
	public static String[] split(String indirizziStr) {
		Collection<String> indirizziList = new ArrayList<String>();
		if(indirizziStr != null && !"".equals(indirizziStr)) {
			String indirizzo = "";
			for(int i = 0; i < indirizziStr.length(); i++)  {				
				if(!delims.contains("" + indirizziStr.charAt(i))) {
					indirizzo += indirizziStr.charAt(i);
				} else if(!"".equals(indirizzo)) {
					indirizziList.add(indirizzo);
					indirizzo = "";
				}
			}
			if(!"".equals(indirizzo)) {
				indirizziList.add(indirizzo);
			}
		}		
		return indirizziList.toArray(new String[0]);
	}
	
}
