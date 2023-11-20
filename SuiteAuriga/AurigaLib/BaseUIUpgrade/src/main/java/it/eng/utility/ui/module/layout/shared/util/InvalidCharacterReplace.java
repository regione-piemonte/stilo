/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

/**
 * 
 * @author FEBUONO
 *
 */

public class InvalidCharacterReplace {	
		
	public static String replaceAllInvalidCharacters(String value) {
		value = value.replace("‘", "'");
		value = value.replace("“", "\"");
		value = value.replace("—", "-");
		value = value.replace("’", "'");
		value = value.replace("”", "\"");
		return value;
	}	
	
}