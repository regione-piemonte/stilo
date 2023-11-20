/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

public class EliminazioneDaAreaLavoroBean extends OperazioneMassivaArchivioBean implements Serializable {
			
		private static final long serialVersionUID = 1L;
	
		private String sezioneAreaLav;
		private String motivo;
				
		public String getMotivo() {
			return motivo;
		}
		public void setMotivo(String motivo) {
			this.motivo = motivo;
		}
		public String getSezioneAreaLav() {
			return sezioneAreaLav;
		}
		public void setSezioneAreaLav(String sezioneAreaLav) {
			this.sezioneAreaLav = sezioneAreaLav;
		}
		
}
