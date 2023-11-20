/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.core.business.beans.AbstractBean;

import java.io.Serializable;
import java.util.Date;

public class VariazioneDatiUoSvBean extends AbstractBean implements Serializable {
			
		private String idUoSv;
		private String flgUoSv;
		
		private Date dataVariazione;
		private String motivoVariazione;
		private String attoVariazione;
		private Date dataIstituzioneUo;		
		private String livGerarchicoUo;
		private String tipoUo;
		private String codiceUo;
		private String denominazioneUo;
		
		// solo per la postazione
		private String utente;
		private String nomePostazione;
		private String ruoloUtenteInUo;
		private String flgIncludiUoSubordinate;
		private String flgIncludiPostazioniSubordinate;
		
		
		public String getIdUoSv() {
			return idUoSv;
		}
		public void setIdUoSv(String idUoSv) {
			this.idUoSv = idUoSv;
		}
		public String getFlgUoSv() {
			return flgUoSv;
		}
		public void setFlgUoSv(String flgUoSv) {
			this.flgUoSv = flgUoSv;
		}
		public Date getDataVariazione() {
			return dataVariazione;
		}
		public void setDataVariazione(Date dataVariazione) {
			this.dataVariazione = dataVariazione;
		}
		public String getMotivoVariazione() {
			return motivoVariazione;
		}
		public void setMotivoVariazione(String motivoVariazione) {
			this.motivoVariazione = motivoVariazione;
		}
		public String getAttoVariazione() {
			return attoVariazione;
		}
		public void setAttoVariazione(String attoVariazione) {
			this.attoVariazione = attoVariazione;
		}
		public Date getDataIstituzioneUo() {
			return dataIstituzioneUo;
		}
		public void setDataIstituzioneUo(Date dataIstituzioneUo) {
			this.dataIstituzioneUo = dataIstituzioneUo;
		}
		public String getLivGerarchicoUo() {
			return livGerarchicoUo;
		}
		public void setLivGerarchicoUo(String livGerarchicoUo) {
			this.livGerarchicoUo = livGerarchicoUo;
		}
		public String getTipoUo() {
			return tipoUo;
		}
		public void setTipoUo(String tipoUo) {
			this.tipoUo = tipoUo;
		}
		public String getCodiceUo() {
			return codiceUo;
		}
		public void setCodiceUo(String codiceUo) {
			this.codiceUo = codiceUo;
		}
		public String getDenominazioneUo() {
			return denominazioneUo;
		}
		public void setDenominazioneUo(String denominazioneUo) {
			this.denominazioneUo = denominazioneUo;
		}
		public String getUtente() {
			return utente;
		}
		public void setUtente(String utente) {
			this.utente = utente;
		}
		public String getNomePostazione() {
			return nomePostazione;
		}
		public void setNomePostazione(String nomePostazione) {
			this.nomePostazione = nomePostazione;
		}
		public String getRuoloUtenteInUo() {
			return ruoloUtenteInUo;
		}
		public void setRuoloUtenteInUo(String ruoloUtenteInUo) {
			this.ruoloUtenteInUo = ruoloUtenteInUo;
		}
		public String getFlgIncludiUoSubordinate() {
			return flgIncludiUoSubordinate;
		}
		public void setFlgIncludiUoSubordinate(String flgIncludiUoSubordinate) {
			this.flgIncludiUoSubordinate = flgIncludiUoSubordinate;
		}
		public String getFlgIncludiPostazioniSubordinate() {
			return flgIncludiPostazioniSubordinate;
		}
		public void setFlgIncludiPostazioniSubordinate(String flgIncludiPostazioniSubordinate) {
			this.flgIncludiPostazioniSubordinate = flgIncludiPostazioniSubordinate;
		}
				
		
}
