/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.ArrayList;

public class UnlockEmailBean extends OperazioneMassivaPostaElettronicaBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
		private ArrayList<String> errorMessage;
		private String motivi;
		private boolean storeInError;
	
		public ArrayList<String> getErrorMessage() {
			return errorMessage;
		}
		public void setErrorMessage(ArrayList<String> errorMessage) {
			this.errorMessage = errorMessage;
		}
		public String getMotivi() {
			return motivi;
		}
		public void setMotivi(String motivi) {
			this.motivi = motivi;
		}
		public boolean isStoreInError() {
			return storeInError;
		}
		public void setStoreInError(boolean storeInError) {
			this.storeInError = storeInError;
		}		
}
