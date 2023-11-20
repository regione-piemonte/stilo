/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

public class RequestFieldAnnoClearoBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "RequestFieldAnnoClearoBean [value=" + value + "]";
	}
	
}
