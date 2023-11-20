/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

public class RestituzioneBean extends OperazioneMassivaArchivioBean implements Serializable {
			
		private static final long serialVersionUID = 1L;
	
		private Integer flgIgnoreWarning;
		private String messaggio;

		public String getMessaggio() {
			return messaggio;
		}

		public void setMessaggio(String messaggio) {
			this.messaggio = messaggio;
		}

		public Integer getFlgIgnoreWarning() {
			return flgIgnoreWarning;
		}

		public void setFlgIgnoreWarning(Integer flgIgnoreWarning) {
			this.flgIgnoreWarning = flgIgnoreWarning;
		}
		
}
