package it.eng.auriga.ui.module.layout.server.regoleProtocollazioneAutomaticaCaselle.datasource.bean;

import java.util.Date;

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;


public class RegoleProtocollazioneAutomaticaCaselleXmlBean {
		
		// Id. regola (PK)
		@NumeroColonna(numero = "1")
		private String idRegola;
		
		// Nome regola
		@NumeroColonna(numero = "2")
		private String nomeRegola;
		
		// Descrizione regola
		@NumeroColonna(numero = "3")
		private String descrizioneRegola;
		
		// Stato regola
		@NumeroColonna(numero = "4")
		private String statoRegola;
		
		// Timestamp di creazione della regola(nel formato dato dal parametro di conf. FMT_STD_TIMESTAMP)
		@NumeroColonna(numero = "5")	
		@TipoData(tipo = Tipo.DATA)
		private Date dataIns;

		// Descrizione dell'utente di creazione della regola
		@NumeroColonna(numero = "6")
		private String descUtenteIns;

		// Timestamp di ultima modifica della regola(nel formato dato dal parametro di conf. FMT_STD_TIMESTAMP)
		@NumeroColonna(numero = "7")
		@TipoData(tipo = Tipo.DATA)
		private Date dataUltMod;

		// Descrizione dell'utente di ultima modifica della regola
		@NumeroColonna(numero = "8")
		private String descUtenteUltMod;
	
		
		// (valori 1/0) Se 1 si tratta di una regola riservata di sistema e dunque non modificabile/cancellabile da applicativo
        private String recProtetto;

        // (valori 1/0) Se 1 si tratta di una regola annullata
		private String flgAnnullato;

		private String flgValido;

		public String getIdRegola() {
			return idRegola;
		}

		public void setIdRegola(String idRegola) {
			this.idRegola = idRegola;
		}

		public String getNomeRegola() {
			return nomeRegola;
		}

		public void setNomeRegola(String nomeRegola) {
			this.nomeRegola = nomeRegola;
		}

		

		public String getStatoRegola() {
			return statoRegola;
		}

		public void setStatoRegola(String statoRegola) {
			this.statoRegola = statoRegola;
		}

		public Date getDataIns() {
			return dataIns;
		}

		public void setDataIns(Date dataIns) {
			this.dataIns = dataIns;
		}

		public String getDescUtenteIns() {
			return descUtenteIns;
		}

		public void setDescUtenteIns(String descUtenteIns) {
			this.descUtenteIns = descUtenteIns;
		}

		public Date getDataUltMod() {
			return dataUltMod;
		}

		public void setDataUltMod(Date dataUltMod) {
			this.dataUltMod = dataUltMod;
		}

		public String getDescUtenteUltMod() {
			return descUtenteUltMod;
		}

		public void setDescUtenteUltMod(String descUtenteUltMod) {
			this.descUtenteUltMod = descUtenteUltMod;
		}

		public String getRecProtetto() {
			return recProtetto;
		}

		public void setRecProtetto(String recProtetto) {
			this.recProtetto = recProtetto;
		}

		public String getFlgAnnullato() {
			return flgAnnullato;
		}

		public void setFlgAnnullato(String flgAnnullato) {
			this.flgAnnullato = flgAnnullato;
		}

		public String getFlgValido() {
			return flgValido;
		}

		public void setFlgValido(String flgValido) {
			this.flgValido = flgValido;
		}

		public String getDescrizioneRegola() {
			return descrizioneRegola;
		}

		public void setDescrizioneRegola(String descrizioneRegola) {
			this.descrizioneRegola = descrizioneRegola;
		}

		
}
