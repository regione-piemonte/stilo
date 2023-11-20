/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;

public class RegistriNumerazioneXmlBean {
		
		// Identificativo del tipo di registrazione/numerazione
		@NumeroColonna(numero = "1")
		private String idTipoRegNum;

		// Descrizione del tipo di registrazione/numerazione		 
		@NumeroColonna(numero = "2")
		private String descrizione;

		// Codice che indica la categoria in cui ricade il tipo di registrazione/numerazione
		@NumeroColonna(numero = "3")
		private String codCategoria;

	    // Descrizione della categoria in cui ricade il tipo di registrazione/numerazione
		@NumeroColonna(numero = "4")
		private String desCategoria;

        // Sigla del tipo di registrazione/numerazione 		
		@NumeroColonna(numero = "5")
		private String siglaTipoRegNum;
        
		// (valori 1/0) Se 1 indica che la registrazione/numerazione viene data all'interno del sistema, se 0 che viene data da sistemi esterni
		@NumeroColonna(numero = "6")
		private Boolean flgInterna;

		// N.ro di anni ogni quanto si rinnova la numerazione (se non valorizzato significa che e' una numerazione continua che non riparte mai da 1)		
		@NumeroColonna(numero = "7")
		private Integer nrAnniRinnovaNumerazione;

        // Anno di inizio della numerazione		
		@NumeroColonna(numero = "8")
		private String annoInizioNum;

        // Data di inizio validita'  del tipo di registrazione/numerazione
		@NumeroColonna(numero = "9")
		@TipoData(tipo = Tipo.DATA_SENZA_ORA)
		private Date dtInizioValidita;
		
		// Data di fine validita'  del tipo di registrazione/numerazione	
        @NumeroColonna(numero = "10")
        @TipoData(tipo = Tipo.DATA_SENZA_ORA)
        private Date dtFineValidita;

		// Stato del registro del dato tipo di registrazione/numerazione: Aperto, Chiuso
		@NumeroColonna(numero = "11")
		private String descStatoRegistro;

        // Data e ora da cui il registro del dato tipo di registrazione/numerazione si trova nello stato attuale di Aperto o Chiuso
		@NumeroColonna(numero = "12")
        @TipoData(tipo = Tipo.DATA)
        private Date dtStatoRegNum;

        // (valori 1/0) Indica se  e'  un tipo di registrazione/numerazione attualmente valida
		@NumeroColonna(numero = "13")
		private Boolean flgValido;

        // Lista dei tipi di documenti che possono essere registrati/numerati col dato tipo di registrazione/numerazione
        @NumeroColonna(numero = "14")
        private String stringListaDocRegistrabili;

        // 15: Lista dei tipi di documenti che NON possono essere registrati/numerati col dato tipo di registrazione/numerazione 
        @NumeroColonna(numero = "15")
        private String stringListaDocNonRegistrabili;

        // Cod. identificativo del tipo di registrazione/numerazione nel sistema di provenienza
        @NumeroColonna(numero = "16")
        private String idTipoRegSistProv;

        //  Data e ora dell'ultima registrazione/numerazione nel registro		
		@NumeroColonna(numero = "17")
		@TipoData(tipo = Tipo.DATA)
		private Date dtUltimaReg;

        // Anno dell'ultima registrazione/numerazione nel registro
        @NumeroColonna(numero = "18")
        private String annoUltimaReg;

        // N.ro dell'ultima registrazione/numerazione nel registro		
        @NumeroColonna(numero = "19")
        private Integer nrUltimaReg;

        // (valori 1/0) Indicatore di tipo di registrazione/numerazione riservato dal sistema e non modificabile da applicativo		
        @NumeroColonna(numero = "20")
        private Boolean flgSistema;

        // (valori 1/0): se 1 richiesta abilitazione esplicita per visualizzare documenti registrati/numerati col dato tipo di registrazione/numerazione
		@NumeroColonna(numero = "21")
		private Boolean flgRichAbilVis;

        // (valori 1/0): se 1 richiesta abilitazione esplicita per gestire (modificare, versionare, cancellare) documenti registrati/numerati col dato tipo di registrazione/numerazione
		@NumeroColonna(numero = "22")
		private Boolean flgRichAbilXGestIn;

		// (valori 1/0): se 1 richiesta abilitazione esplicita per numerare/registrare documenti col dato tipo di registrazione/numerazione		
		@NumeroColonna(numero = "23")
		private Boolean flgRichAbilXAssegn;

        // (valori 1/0): se 1  richiesta abilitazione esplicita per firmare documenti registrati/numerati col dato tipo di registrazione/numerazione
		@NumeroColonna(numero = "24")
		private Boolean flgAbilFirma;
   
		// Timestamp di creazione del tipo di registrazione/numerazione
		@NumeroColonna(numero = "25")	
		@TipoData(tipo = Tipo.DATA)
		private Date dtCreazione;

		// Descrizione dell'utente di creazione del tipo di registrazione/numerazione
		@NumeroColonna(numero = "26")
		private String desUteCreazione;

		// Timestamp di ultima modifica dei dati del tipo di registrazione/numerazione
		@NumeroColonna(numero = "27")
		@TipoData(tipo = Tipo.DATA)
		private Date dtUltimaModifica;

		// Descrizione dell'utente di ultima modifica dei dati del tipo di registrazione/numerazione
		@NumeroColonna(numero = "28")
		private String desUteUltimaMod;
		
		// Gruppo di registri di appartenenza
		@NumeroColonna(numero = "29")
		private String gruppiRegistriAppartenenza;

		public String getIdTipoRegNum() {
			return idTipoRegNum;
		}

		public void setIdTipoRegNum(String idTipoRegNum) {
			this.idTipoRegNum = idTipoRegNum;
		}

		public String getDescrizione() {
			return descrizione;
		}

		public void setDescrizione(String descrizione) {
			this.descrizione = descrizione;
		}

		public String getCodCategoria() {
			return codCategoria;
		}

		public void setCodCategoria(String codCategoria) {
			this.codCategoria = codCategoria;
		}

		public String getDesCategoria() {
			return desCategoria;
		}

		public void setDesCategoria(String desCategoria) {
			this.desCategoria = desCategoria;
		}

		public String getSiglaTipoRegNum() {
			return siglaTipoRegNum;
		}

		public void setSiglaTipoRegNum(String siglaTipoRegNum) {
			this.siglaTipoRegNum = siglaTipoRegNum;
		}

		public Boolean getFlgInterna() {
			return flgInterna;
		}

		public void setFlgInterna(Boolean flgInterna) {
			this.flgInterna = flgInterna;
		}

		
		public String getAnnoInizioNum() {
			return annoInizioNum;
		}

		public void setAnnoInizioNum(String annoInizioNum) {
			this.annoInizioNum = annoInizioNum;
		}

		public Date getDtInizioValidita() {
			return dtInizioValidita;
		}

		public void setDtInizioValidita(Date dtInizioValidita) {
			this.dtInizioValidita = dtInizioValidita;
		}

		public Date getDtFineValidita() {
			return dtFineValidita;
		}

		public void setDtFineValidita(Date dtFineValidita) {
			this.dtFineValidita = dtFineValidita;
		}

	
		public Date getDtStatoRegNum() {
			return dtStatoRegNum;
		}

		public void setDtStatoRegNum(Date dtStatoRegNum) {
			this.dtStatoRegNum = dtStatoRegNum;
		}

		public Boolean getFlgValido() {
			return flgValido;
		}

		public void setFlgValido(Boolean flgValido) {
			this.flgValido = flgValido;
		}

		public String getStringListaDocRegistrabili() {
			return stringListaDocRegistrabili;
		}

		public void setStringListaDocRegistrabili(String stringListaDocRegistrabili) {
			this.stringListaDocRegistrabili = stringListaDocRegistrabili;
		}

		public String getStringListaDocNonRegistrabili() {
			return stringListaDocNonRegistrabili;
		}

		public void setStringListaDocNonRegistrabili(String stringListaDocNonRegistrabili) {
			this.stringListaDocNonRegistrabili = stringListaDocNonRegistrabili;
		}

		public String getIdTipoRegSistProv() {
			return idTipoRegSistProv;
		}

		public void setIdTipoRegSistProv(String idTipoRegSistProv) {
			this.idTipoRegSistProv = idTipoRegSistProv;
		}

		public Date getDtUltimaReg() {
			return dtUltimaReg;
		}

		public void setDtUltimaReg(Date dtUltimaReg) {
			this.dtUltimaReg = dtUltimaReg;
		}

		public String getAnnoUltimaReg() {
			return annoUltimaReg;
		}

		public void setAnnoUltimaReg(String annoUltimaReg) {
			this.annoUltimaReg = annoUltimaReg;
		}

		public Integer getNrUltimaReg() {
			return nrUltimaReg;
		}

		public void setNrUltimaReg(Integer nrUltimaReg) {
			this.nrUltimaReg = nrUltimaReg;
		}

		public Boolean getFlgSistema() {
			return flgSistema;
		}

		public void setFlgSistema(Boolean flgSistema) {
			this.flgSistema = flgSistema;
		}

		public Boolean getFlgRichAbilVis() {
			return flgRichAbilVis;
		}

		public void setFlgRichAbilVis(Boolean flgRichAbilVis) {
			this.flgRichAbilVis = flgRichAbilVis;
		}

		public Boolean getFlgRichAbilXGestIn() {
			return flgRichAbilXGestIn;
		}

		public void setFlgRichAbilXGestIn(Boolean flgRichAbilXGestIn) {
			this.flgRichAbilXGestIn = flgRichAbilXGestIn;
		}

		public Boolean getFlgRichAbilXAssegn() {
			return flgRichAbilXAssegn;
		}

		public void setFlgRichAbilXAssegn(Boolean flgRichAbilXAssegn) {
			this.flgRichAbilXAssegn = flgRichAbilXAssegn;
		}

		public Boolean getFlgAbilFirma() {
			return flgAbilFirma;
		}

		public void setFlgAbilFirma(Boolean flgAbilFirma) {
			this.flgAbilFirma = flgAbilFirma;
		}

		public Date getDtCreazione() {
			return dtCreazione;
		}

		public void setDtCreazione(Date dtCreazione) {
			this.dtCreazione = dtCreazione;
		}

		public String getDesUteCreazione() {
			return desUteCreazione;
		}

		public void setDesUteCreazione(String desUteCreazione) {
			this.desUteCreazione = desUteCreazione;
		}

		public Date getDtUltimaModifica() {
			return dtUltimaModifica;
		}

		public void setDtUltimaModifica(Date dtUltimaModifica) {
			this.dtUltimaModifica = dtUltimaModifica;
		}

		public String getDesUteUltimaMod() {
			return desUteUltimaMod;
		}

		public void setDesUteUltimaMod(String desUteUltimaMod) {
			this.desUteUltimaMod = desUteUltimaMod;
		}

		public String getGruppiRegistriAppartenenza() {
			return gruppiRegistriAppartenenza;
		}

		public void setGruppiRegistriAppartenenza(String gruppiRegistriAppartenenza) {
			this.gruppiRegistriAppartenenza = gruppiRegistriAppartenenza;
		}

		public String getDescStatoRegistro() {
			return descStatoRegistro;
		}

		public void setDescStatoRegistro(String descStatoRegistro) {
			this.descStatoRegistro = descStatoRegistro;
		}

		public Integer getNrAnniRinnovaNumerazione() {
			return nrAnniRinnovaNumerazione;
		}

		public void setNrAnniRinnovaNumerazione(Integer nrAnniRinnovaNumerazione) {
			this.nrAnniRinnovaNumerazione = nrAnniRinnovaNumerazione;
		}
				
		
		

}
