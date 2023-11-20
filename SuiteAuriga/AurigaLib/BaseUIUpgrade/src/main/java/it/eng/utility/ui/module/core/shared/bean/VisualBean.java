/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.Map;

public class VisualBean {

	private String displayValue;

	public void setDisplayValue(String displayValue) {
		this.displayValue = displayValue;
	}

	public String getDisplayValue() {
		return displayValue;
	}
	
	public void setHoverValues(Map<String, String> hoverValues) {
		this.hoverValues = hoverValues;
	}

	public Map<String, String> getHoverValues() {
		return hoverValues;
	}

	public void setFormatValues(Map<String, String> formatValues) {
		this.formatValues = formatValues;
	}

	public Map<String, String> getFormatValues() {
		return formatValues;
	}
	
	public void addFormatValue(String name, String value){
		formatValues.put(name, value);
	}
	
	public void addHoverValue(String name, String value){
		hoverValues.put(name, value);
	}

	private Map<String, String> hoverValues = new HashMap<String, String>();
	
	private Map<String, String> formatValues = new HashMap<String, String>();
	
}
