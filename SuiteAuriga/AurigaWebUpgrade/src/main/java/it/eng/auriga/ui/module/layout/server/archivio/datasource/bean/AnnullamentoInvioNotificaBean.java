/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.core.business.beans.AbstractBean;

import java.io.Serializable;

public class AnnullamentoInvioNotificaBean extends AbstractBean implements Serializable {
			
		private static final long serialVersionUID = 1L;
	
		private String flgUdFolder;
		private String idUdFolder;		
		private String flgInvioNotifica;
		private String idInvioNotifica;
		private String motivo;

		public String getFlgInvioNotifica() {
			return flgInvioNotifica;
		}

		public void setFlgInvioNotifica(String flgInvioNotifica) {
			this.flgInvioNotifica = flgInvioNotifica;
		}

		public String getIdInvioNotifica() {
			return idInvioNotifica;
		}

		public void setIdInvioNotifica(String idInvioNotifica) {
			this.idInvioNotifica = idInvioNotifica;
		}

		public String getFlgUdFolder() {
			return flgUdFolder;
		}

		public void setFlgUdFolder(String flgUdFolder) {
			this.flgUdFolder = flgUdFolder;
		}

		public String getIdUdFolder() {
			return idUdFolder;
		}

		public void setIdUdFolder(String idUdFolder) {
			this.idUdFolder = idUdFolder;
		}

		public String getMotivo() {
			return motivo;
		}

		public void setMotivo(String motivo) {
			this.motivo = motivo;
		}
		
}
