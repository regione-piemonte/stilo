/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class EditablePdfBean {
	
	boolean flgEditable;
	boolean flgContainsXfaForm;
	
	public boolean getFlgEditable() {
		return flgEditable;
	}
	public boolean getFlgContainsXfaForm() {
		return flgContainsXfaForm;
	}
	public void setFlgEditable(boolean flgEditable) {
		this.flgEditable = flgEditable;
	}
	public void setFlgContainsXfaForm(boolean flgContainsXfaForm) {
		this.flgContainsXfaForm = flgContainsXfaForm;
	}

}
