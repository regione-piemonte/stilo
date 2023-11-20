/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;

public class GestioneUtentiXmlBean {

	// colonna 1: Id. univoco dell'utente (ID_USER)
	@NumeroColonna(numero = "1")
	private String idUser;

	// colonna 2: Cognome e nome, descrizione (DES_USER)
	@NumeroColonna(numero = "2")
	private String desUser;

	// colonna 3: Username (interna al sistema)
	@NumeroColonna(numero = "3")
	private String username;

	// colonna 4: N° matricola
	@NumeroColonna(numero = "4")
	private String nroMatricola;

	// colonna 5: Qualifica
	@NumeroColonna(numero = "5")
	private String qualifica;

	// colonna 6: Titolo
	@NumeroColonna(numero = "6")
	private String titolo;

	// colonna 7: e-Mail
	@NumeroColonna(numero = "7")
	private String email;
	
	// colonna 8: Inizio validita' (nel formato dato dal parametro di conf. FMT_STD_DATA).
	@NumeroColonna(numero = "8")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dtIniVld;
	
	// colonna 9: Fine validita' (nel formato dato dal parametro di conf.FMT_STD_DATA)
	@NumeroColonna(numero = "9")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dtFineVld;

	// colonna 10:Id. del soggetto di rubrica corrispondente all''utente
	@NumeroColonna(numero = "10")
	private String idSoggRubrica;

	// colonna 12:
	@NumeroColonna(numero = "12")
	private String flgAccreditatiInDomIo;

	// colonna 13: Mostra i domini in cui è attivo l'utente
	@NumeroColonna(numero = "13")
	private String attivoIn;
	
	// colonna 26: flgDiSistema
	@NumeroColonna(numero = "26")
	private Integer flgDiSistema;
	
	// colonna 27: flgValido
	@NumeroColonna(numero = "27")
	private Integer flgValido;
	
	// colonna 29: Nome del profilo
	@NumeroColonna(numero = "29")
	private String nomeProfilo;
	
	// colonna 30: Elenco degli indirizzi
	@NumeroColonna(numero = "30")
	private String indirizzo;
	
	// colonna 31: Id destinatario
	@NumeroColonna(numero = "31")
	private String idDestinatario;
	
	// colonna 32: Nome destinatario
	@NumeroColonna(numero = "32")
	private String nomeDestinatario;
	
	// colonna 33: Codice mansione
	@NumeroColonna(numero = "33")
	private String codiceUtenteMansione;
	
	// colonna 34: Data ultimo accesso
	@NumeroColonna(numero = "34")
	@TipoData(tipo = Tipo.DATA)
	private Date dtUltimoAccesso;
	
	// colonna 35: Cod. UO appartenenza
	@NumeroColonna(numero = "35")
	private String codiceUO;
	
	// colonna 36: Nome UO appartenenza
	@NumeroColonna(numero = "36")
	private String nomeAppUO;
	
	// colonna 37 : Account bloccato (1/0)
	@NumeroColonna(numero = "37")
	private String flgUtenteBloccato;

	// colonna 38 : Messaggio di alt sul bottone Account bloccato
	@NumeroColonna(numero = "38")
	private String flgUtenteBloccatoMsgHover;

	private String cognomeNome;
	
	
	public String getIdUser() {
		return idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	public String getDesUser() {
		return desUser;
	}

	public void setDesUser(String desUser) {
		this.desUser = desUser;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNroMatricola() {
		return nroMatricola;
	}

	public void setNroMatricola(String nroMatricola) {
		this.nroMatricola = nroMatricola;
	}

	public String getQualifica() {
		return qualifica;
	}

	public void setQualifica(String qualifica) {
		this.qualifica = qualifica;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDtIniVld() {
		return dtIniVld;
	}

	public void setDtIniVld(Date dtIniVld) {
		this.dtIniVld = dtIniVld;
	}

	public Date getDtFineVld() {
		return dtFineVld;
	}

	public void setDtFineVld(Date dtFineVld) {
		this.dtFineVld = dtFineVld;
	}

	public String getIdSoggRubrica() {
		return idSoggRubrica;
	}

	public void setIdSoggRubrica(String idSoggRubrica) {
		this.idSoggRubrica = idSoggRubrica;
	}

	public String getFlgAccreditatiInDomIo() {
		return flgAccreditatiInDomIo;
	}

	public void setFlgAccreditatiInDomIo(String flgAccreditatiInDomIo) {
		this.flgAccreditatiInDomIo = flgAccreditatiInDomIo;
	}

	public String getAttivoIn() {
		return attivoIn;
	}

	public void setAttivoIn(String attivoIn) {
		this.attivoIn = attivoIn;
	}

	public String getNomeProfilo() {
		return nomeProfilo;
	}

	public void setNomeProfilo(String nomeProfilo) {
		this.nomeProfilo = nomeProfilo;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getIdDestinatario() {
		return idDestinatario;
	}

	public void setIdDestinatario(String idDestinatario) {
		this.idDestinatario = idDestinatario;
	}

	public String getNomeDestinatario() {
		return nomeDestinatario;
	}

	public void setNomeDestinatario(String nomeDestinatario) {
		this.nomeDestinatario = nomeDestinatario;
	}

	public String getCodiceUtenteMansione() {
		return codiceUtenteMansione;
	}

	public void setCodiceUtenteMansione(String codiceUtenteMansione) {
		this.codiceUtenteMansione = codiceUtenteMansione;
	}

	public Date getDtUltimoAccesso() {
		return dtUltimoAccesso;
	}

	public void setDtUltimoAccesso(Date dtUltimoAccesso) {
		this.dtUltimoAccesso = dtUltimoAccesso;
	}

	public String getCodiceUO() {
		return codiceUO;
	}

	public void setCodiceUO(String codiceUO) {
		this.codiceUO = codiceUO;
	}

	public String getNomeAppUO() {
		return nomeAppUO;
	}

	public void setNomeAppUO(String nomeAppUO) {
		this.nomeAppUO = nomeAppUO;
	}

	public String getFlgUtenteBloccato() {
		return flgUtenteBloccato;
	}

	public void setFlgUtenteBloccato(String flgUtenteBloccato) {
		this.flgUtenteBloccato = flgUtenteBloccato;
	}

	public String getFlgUtenteBloccatoMsgHover() {
		return flgUtenteBloccatoMsgHover;
	}

	public void setFlgUtenteBloccatoMsgHover(String flgUtenteBloccatoMsgHover) {
		this.flgUtenteBloccatoMsgHover = flgUtenteBloccatoMsgHover;
	}

	public String getCognomeNome() {
		return cognomeNome;
	}

	public void setCognomeNome(String cognomeNome) {
		this.cognomeNome = cognomeNome;
	}

	public Integer getFlgDiSistema() {
		return flgDiSistema;
	}

	public void setFlgDiSistema(Integer flgDiSistema) {
		this.flgDiSistema = flgDiSistema;
	}

	public Integer getFlgValido() {
		return flgValido;
	}

	public void setFlgValido(Integer flgValido) {
		this.flgValido = flgValido;
	}

}