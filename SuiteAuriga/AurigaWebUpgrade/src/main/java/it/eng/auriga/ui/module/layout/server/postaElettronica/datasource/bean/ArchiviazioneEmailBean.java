/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.ArrayList;

public class ArchiviazioneEmailBean extends OperazioneMassivaPostaElettronicaBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
		private String classifica;
		private ArrayList<String> errorMessage;
		private Integer flgRilascia;
		
		//Campi della window dati operazione richieste
		private String motivazione;
		private String operazioneRichiesta;
		
		public String getClassifica() {
			return classifica;
		}
		public void setClassifica(String classifica) {
			this.classifica = classifica;
		}
		public ArrayList<String> getErrorMessage() {
			return errorMessage;
		}
		public void setErrorMessage(ArrayList<String> errorMessage) {
			this.errorMessage = errorMessage;
		}
		public String getMotivazione() {
			return motivazione;
		}
		public void setMotivazione(String motivazione) {
			this.motivazione = motivazione;
		}
		public String getOperazioneRichiesta() {
			return operazioneRichiesta;
		}
		public void setOperazioneRichiesta(String operazioneRichiesta) {
			this.operazioneRichiesta = operazioneRichiesta;
		}
		public Integer getFlgRilascia() {
			return flgRilascia;
		}
		public void setFlgRilascia(Integer flgRilascia) {
			this.flgRilascia = flgRilascia;
		}
		
		
		
		
}
