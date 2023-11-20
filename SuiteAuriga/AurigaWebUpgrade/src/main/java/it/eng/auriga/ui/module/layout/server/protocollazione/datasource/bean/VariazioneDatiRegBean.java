/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.core.business.beans.AbstractBean;

import java.io.Serializable;
import java.util.Date;

public class VariazioneDatiRegBean extends AbstractBean implements Serializable {
			
		private String idUd;
		private Date tsVariazione;
		private String tsVariazioneFisso;		
		private String effettuatoDa;
		private String motiviVariazione;
		private String datiAnnullabiliVariati;
				
		public Date getTsVariazione() {
			return tsVariazione;
		}
		public void setTsVariazione(Date tsVariazione) {
			this.tsVariazione = tsVariazione;
		}
		public String getEffettuatoDa() {
			return effettuatoDa;
		}
		public void setEffettuatoDa(String effettuatoDa) {
			this.effettuatoDa = effettuatoDa;
		}
		public String getMotiviVariazione() {
			return motiviVariazione;
		}
		public void setMotiviVariazione(String motiviVariazione) {
			this.motiviVariazione = motiviVariazione;
		}
		public String getDatiAnnullabiliVariati() {
			return datiAnnullabiliVariati;
		}
		public void setDatiAnnullabiliVariati(String datiAnnullabiliVariati) {
			this.datiAnnullabiliVariati = datiAnnullabiliVariati;
		}
		public String getIdUd() {
			return idUd;
		}
		public void setIdUd(String idUd) {
			this.idUd = idUd;
		}	
		public String getTsVariazioneFisso() {
			return tsVariazioneFisso;
		}
		public void setTsVariazioneFisso(String tsVariazioneFisso) {
			this.tsVariazioneFisso = tsVariazioneFisso;
		}
				
}
