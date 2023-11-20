/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.List;

public class AzioneSuListaAttiCompletiBean extends OperazioneMassivaAttiCompletiBean implements Serializable {
	
		private static final long serialVersionUID = 1L;
	
		private String azione;
		private String motivoOsservazioni;
		private String eventoAMC;
		
		private List<ItemTagAttiCompletiBean> itemTagAttiCompleti;
		
		public String getAzione() {
			return azione;
		}

		public void setAzione(String azione) {
			this.azione = azione;
		}

		public String getMotivoOsservazioni() {
			return motivoOsservazioni;
		}

		public void setMotivoOsservazioni(String motivoOsservazioni) {
			this.motivoOsservazioni = motivoOsservazioni;
		}

		public String getEventoAMC() {
			return eventoAMC;
		}

		public void setEventoAMC(String eventoAMC) {
			this.eventoAMC = eventoAMC;
		}

		public List<ItemTagAttiCompletiBean> getItemTagAttiCompleti() {
			return itemTagAttiCompleti;
		}

		public void setItemTagAttiCompleti(List<ItemTagAttiCompletiBean> itemTagAttiCompleti) {
			this.itemTagAttiCompleti = itemTagAttiCompleti;
		}
		
}