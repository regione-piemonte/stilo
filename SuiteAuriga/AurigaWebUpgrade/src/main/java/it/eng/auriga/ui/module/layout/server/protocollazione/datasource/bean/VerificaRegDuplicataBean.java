/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.core.business.beans.AbstractBean;

import java.io.Serializable;
import java.util.List;

public class VerificaRegDuplicataBean extends AbstractBean implements Serializable {
			
		private String datiRegXml;
		private ProtocollazioneBean datiReg;
		private String statoDuplicazione;
		private String warningMessage;
		private List<RegDuplicataBean> listaRegDuplicate;
				
		
		public ProtocollazioneBean getDatiReg() {
			return datiReg;
		}
		public void setDatiReg(ProtocollazioneBean datiReg) {
			this.datiReg = datiReg;
		}
		public List<RegDuplicataBean> getListaRegDuplicate() {
			return listaRegDuplicate;
		}
		public void setListaRegDuplicate(List<RegDuplicataBean> listaRegDuplicate) {
			this.listaRegDuplicate = listaRegDuplicate;
		}
		public String getStatoDuplicazione() {
			return statoDuplicazione;
		}
		public void setStatoDuplicazione(String statoDuplicazione) {
			this.statoDuplicazione = statoDuplicazione;
		}
		public String getWarningMessage() {
			return warningMessage;
		}
		public void setWarningMessage(String warningMessage) {
			this.warningMessage = warningMessage;
		}
		public String getDatiRegXml() {
			return datiRegXml;
		}
		public void setDatiRegXml(String datiRegXml) {
			this.datiRegXml = datiRegXml;
		}
				
}
