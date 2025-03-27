/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.utility.ui.module.core.shared.bean;

public class ValidationBean {

	private boolean valid;

	public ValidationBean(boolean b) {
		valid = b;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public boolean isValid() {
		return valid;
	}
	
}
