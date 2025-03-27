/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.compiler;

import com.sun.star.beans.PropertyValue;

public class ReplacementItem {
	private String stringToReplace;
	private String stringReplacement;
	private PropertyValue[] replaceProperty;
	
	public ReplacementItem(String stringToReplace, String stringReplacement, PropertyValue[] replaceProperty) {
		super();
		this.stringToReplace = stringToReplace;
		this.stringReplacement = stringReplacement;
		this.replaceProperty = replaceProperty;
	}

	public String getStringToReplace() {
		return stringToReplace;
	}
	
	public void setStringToReplace(String stringToReplace) {
		this.stringToReplace = stringToReplace;
	}
	
	public String getStringReplacement() {
		return stringReplacement;
	}
	
	public void setStringReplacement(String stringReplacement) {
		this.stringReplacement = stringReplacement;
	}
	
	public PropertyValue[] getReplaceProperty() {
		return replaceProperty;
	}
	
	public void setReplaceProperty(PropertyValue[] replaceProperty) {
		this.replaceProperty = replaceProperty;
	}
	
}
