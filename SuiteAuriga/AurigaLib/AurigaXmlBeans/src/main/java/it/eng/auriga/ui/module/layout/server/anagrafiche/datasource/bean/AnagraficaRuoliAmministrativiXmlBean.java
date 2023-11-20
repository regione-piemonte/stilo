/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.Date;

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;


public class AnagraficaRuoliAmministrativiXmlBean {
		
		@NumeroColonna(numero = "1")
		private BigDecimal idRuolo;

		@NumeroColonna(numero = "2")
		private String descrizioneRuolo;

		// (valori 1/0) Se 1 è un ruolo che si espleta limitatamente alla UO in cui lo si assume, se 0 è un ruolo che si espleta in tutto il soggetto produttore/AOO (es: Sindaco, Direttore Generale, Assessore ecc)
		@NumeroColonna(numero = "3")
        private String flgEspletaSoloAlleUO;
		
		// (valori 1/0) Se 1 si tratta di un ruolo riservato di sistema e dunque non modificabile/cancellabile da applicativo
        @NumeroColonna(numero = "4")
        private String recProtetto;

        // (valori 1/0) Se 1 si tratta di un ruolo annullato
		@NumeroColonna(numero = "5")
		private String flgAnnullato;
				
		// (valori 1/0) Se 1 è un ruolo che include altri ruoli
		@NumeroColonna(numero = "6")
		private String flgIncludeAltriRuoli;
		
		// Timestamp di creazione del ruolo(nel formato dato dal parametro di conf. FMT_STD_TIMESTAMP)
		@NumeroColonna(numero = "7")	
		@TipoData(tipo = Tipo.DATA)
		private Date dataIns;

		// Descrizione dell'utente di creazione del ruolo
		@NumeroColonna(numero = "8")
		private String descUtenteIns;

		// Timestamp di ultima modifica dei dati del ruolo(nel formato dato dal parametro di conf. FMT_STD_TIMESTAMP)
		@NumeroColonna(numero = "9")
		@TipoData(tipo = Tipo.DATA)
		private Date dataUltMod;

		// Descrizione dell'utente di ultima modifica dei dati del ruolo
		@NumeroColonna(numero = "10")
		private String descUtenteUltMod;

		private String flgValido;

		public BigDecimal getIdRuolo() {
			return idRuolo;
		}

		public void setIdRuolo(BigDecimal idRuolo) {
			this.idRuolo = idRuolo;
		}

		public String getDescrizioneRuolo() {
			return descrizioneRuolo;
		}

		public void setDescrizioneRuolo(String descrizioneRuolo) {
			this.descrizioneRuolo = descrizioneRuolo;
		}

		public String getFlgEspletaSoloAlleUO() {
			return flgEspletaSoloAlleUO;
		}

		public void setFlgEspletaSoloAlleUO(String flgEspletaSoloAlleUO) {
			this.flgEspletaSoloAlleUO = flgEspletaSoloAlleUO;
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

		public String getFlgValido() {
			return flgValido;
		}

		public void setFlgValido(String flgValido) {
			this.flgValido = flgValido;
		}

		public String getFlgIncludeAltriRuoli() {
			return flgIncludeAltriRuoli;
		}

		public void setFlgIncludeAltriRuoli(String flgIncludeAltriRuoli) {
			this.flgIncludeAltriRuoli = flgIncludeAltriRuoli;
		}
		
		
				
		
}
