/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

public class RequestFieldOggettoClearoBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String value;
	private String format;
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getFormat() {
		return format;
	}
	
	public void setFormat(String format) {
		this.format = format;
	}
	
	@Override
	public String toString() {
		return "RequestFieldOggettoClearoBean [value=" + value + ", format=" + format + "]";
	}
	
}
