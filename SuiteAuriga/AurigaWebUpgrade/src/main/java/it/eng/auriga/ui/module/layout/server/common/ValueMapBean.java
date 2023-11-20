/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.shared.bean.VisualBean;

public class ValueMapBean extends VisualBean{
	
	String key;
	String value;
	
	public ValueMapBean(String key, String value) {
		setKey(key);
		setValue(value);
	}
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}	
	
}
