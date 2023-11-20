/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;

public class OggettarioBean {

	// 1: Id. (PK) del modello di oggetto
	@NumeroColonna(numero = "1")
	private String  idModello;	
	
	// 2: Codice identificativo/nome del modello
	@NumeroColonna(numero = "2")
	private String  nome;	
	
	// 3: Oggetto del modello
	@NumeroColonna(numero = "3")
	private String  oggetto;
	
	// 4: Codice che identifica il modello nel sistema eventuale di provenienza
	@NumeroColonna(numero = "4")
	private String  codOrigine;
	
	// 5: (valori 1/0) Flag di modello creato dall'utente di lavoro (1) o da altri (0)
	@NumeroColonna(numero = "5")
	private Boolean flgCreatoDa;
	
	// 6: Flag di modello pubblico (PB) o valido per una UO (UO) privato di un utente (PR)
	@NumeroColonna(numero = "6")
	private String flgTipoModello;
	
	// 7: (valori 1/0) Flag di modello valido (=1) o logicamente annullato (=0)
	@NumeroColonna(numero = "7")
	private Boolean flgValido;
	
	// 8: (valori 1/0) Flag di modello riservato di sistema e non modificabile da GUI
	@NumeroColonna(numero = "8")
	private Boolean flgDiSistema;
	
	// 9: (valori 1/0) Flag di modello utilizzabile per le registrazioni in entrata
	@NumeroColonna(numero = "9")
	private Boolean flgXRegInEntrata;
	
	// 10: (valori 1/0) Flag di modello utilizzabile per le registrazioni in uscita
	@NumeroColonna(numero = "10")
	private Boolean flgXRegInUscita;
	
	// 11: (valori 1/0) Flag di modello utilizzabile per le registrazioni interne
	@NumeroColonna(numero = "11")
	private Boolean flgXRegInterne;
	
	// 12: Note del modello
	@NumeroColonna(numero = "12")
	private String  note;	
	
	// 14: Id. utente di creazione
	@NumeroColonna(numero = "14")
	private String  idUteIns;
	
	// 15: Descrizione utente di creazione
	@NumeroColonna(numero = "15")
	private String  uteIns;		
	
	// 16: Timestamp di creazione
	@NumeroColonna(numero = "16")
	@TipoData(tipo = Tipo.DATA)
	private Date tsIns;	
	
	// 17: Id. utente di ultimo aggiornamento
	@NumeroColonna(numero = "17")
	private String  idUteLastUpd;
	
	// 18: Descrizione utente di ultimo aggiornamento
	@NumeroColonna(numero = "18")
	private String  uteLastUpd;	
	
	// 19: Timestamp di ultimo aggiornamento
	@NumeroColonna(numero = "19")
	@TipoData(tipo = Tipo.DATA)
	private Date tsLastUpd;
	
	// 24: Denominazione della UO nella cui porzione di oggettario è definito il modello di oggetto rappresentato dal record
	@NumeroColonna(numero = "24")
	private String denominazioneUo;
	
	// 25: Id. della UO nella cui porzione di oggettario è definito il modello di oggetto rappresentato dal record
	@NumeroColonna(numero = "25")
	private String idUoAssociata;
	
	// 26: Cod. rapido/nri livelli della UO nella cui porzione di oggettario è definito il modello di oggetto rappresentato dal record
	@NumeroColonna(numero = "26")
	private String numeroLivelli;
	
	// 27: Flag 1/0. Se 1 indica che il modello di oggetto rappresentato dal record è visibile da tutte le sotto-UO di quella indicata in colonna 24
	@NumeroColonna(numero = "27")
	private Boolean flgVisibileDaSottoUo;
	
	// 28: Flag 1/0. Se 1 indica che il modello di oggetto rappresentato dal record è gestibile da tutte le sotto-UO di quella indicata in colonna 24
	@NumeroColonna(numero = "28")
	private Boolean flgModificabileDaSottoUo;
	
	// 29: (valori 1/0) Flag di modello modificabile dall'utente di lavoro
	@NumeroColonna(numero = "29")
	private Boolean flgModificabile;
	
	// 30: (valori 1/0) Flag di modello cancellabile dall'utente di lavoro
	@NumeroColonna(numero = "30")
	private Boolean flgCancellabile;
	
	private Boolean flgPubblicato;
	private String descrizioneUo;
	
	public String getIdModello() {
		return idModello;
	}

	public void setIdModello(String idModello) {
		this.idModello = idModello;
	}

	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getOggetto() {
		return oggetto;
	}
	
	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}
	
	public String getCodOrigine() {
		return codOrigine;
	}
	
	public void setCodOrigine(String codOrigine) {
		this.codOrigine = codOrigine;
	}
	
	public Boolean getFlgCreatoDa() {
		return flgCreatoDa;
	}
	
	public void setFlgCreatoDa(Boolean flgCreatoDa) {
		this.flgCreatoDa = flgCreatoDa;
	}
	
	public String getFlgTipoModello() {
		return flgTipoModello;
	}
	
	public void setFlgTipoModello(String flgTipoModello) {
		this.flgTipoModello = flgTipoModello;
	}

	public Boolean getFlgValido() {
		return flgValido;
	}

	public void setFlgValido(Boolean flgValido) {
		this.flgValido = flgValido;
	}

	public Boolean getFlgDiSistema() {
		return flgDiSistema;
	}

	public void setFlgDiSistema(Boolean flgDiSistema) {
		this.flgDiSistema = flgDiSistema;
	}

	public Boolean getFlgXRegInEntrata() {
		return flgXRegInEntrata;
	}

	public void setFlgXRegInEntrata(Boolean flgXRegInEntrata) {
		this.flgXRegInEntrata = flgXRegInEntrata;
	}
	
	public Boolean getFlgXRegInUscita() {
		return flgXRegInUscita;
	}
	
	public void setFlgXRegInUscita(Boolean flgXRegInUscita) {
		this.flgXRegInUscita = flgXRegInUscita;
	}

	public Boolean getFlgXRegInterne() {
		return flgXRegInterne;
	}

	public void setFlgXRegInterne(Boolean flgXRegInterne) {
		this.flgXRegInterne = flgXRegInterne;
	}
	
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getIdUteIns() {
		return idUteIns;
	}

	public void setIdUteIns(String idUteIns) {
		this.idUteIns = idUteIns;
	}
	
	public String getUteIns() {
		return uteIns;
	}

	public void setUteIns(String uteIns) {
		this.uteIns = uteIns;
	}
	
	public Date getTsIns() {
		return tsIns;
	}

	public void setTsIns(Date tsIns) {
		this.tsIns = tsIns;
	}

	public String getIdUteLastUpd() {
		return idUteLastUpd;
	}

	public void setIdUteLastUpd(String idUteLastUpd) {
		this.idUteLastUpd = idUteLastUpd;
	}

	public String getUteLastUpd() {
		return uteLastUpd;
	}

	public void setUteLastUpd(String uteLastUpd) {
		this.uteLastUpd = uteLastUpd;
	}
	
	public Date getTsLastUpd() {
		return tsLastUpd;
	}

	public void setTsLastUpd(Date tsLastUpd) {
		this.tsLastUpd = tsLastUpd;
	}

	public String getDenominazioneUo() {
		return denominazioneUo;
	}

	public void setDenominazioneUo(String denominazioneUo) {
		this.denominazioneUo = denominazioneUo;
	}

	public String getIdUoAssociata() {
		return idUoAssociata;
	}

	public void setIdUoAssociata(String idUoAssociata) {
		this.idUoAssociata = idUoAssociata;
	}

	public String getNumeroLivelli() {
		return numeroLivelli;
	}

	public void setNumeroLivelli(String numeroLivelli) {
		this.numeroLivelli = numeroLivelli;
	}

	public Boolean getFlgVisibileDaSottoUo() {
		return flgVisibileDaSottoUo;
	}

	public void setFlgVisibileDaSottoUo(Boolean flgVisibileDaSottoUo) {
		this.flgVisibileDaSottoUo = flgVisibileDaSottoUo;
	}
	
	public Boolean getFlgModificabileDaSottoUo() {
		return flgModificabileDaSottoUo;
	}
	
	public void setFlgModificabileDaSottoUo(Boolean flgModificabileDaSottoUo) {
		this.flgModificabileDaSottoUo = flgModificabileDaSottoUo;
	}
		
	public Boolean getFlgModificabile() {
		return flgModificabile;
	}
	
	public void setFlgModificabile(Boolean flgModificabile) {
		this.flgModificabile = flgModificabile;
	}

	public Boolean getFlgCancellabile() {
		return flgCancellabile;
	}

	public void setFlgCancellabile(Boolean flgCancellabile) {
		this.flgCancellabile = flgCancellabile;
	}
	
	public Boolean getFlgPubblicato() {
		return flgPubblicato;
	}
	
	public void setFlgPubblicato(Boolean flgPubblicato) {
		this.flgPubblicato = flgPubblicato;
	}

	public String getDescrizioneUo() {
		return descrizioneUo;
	}

	public void setDescrizioneUo(String descrizioneUo) {
		this.descrizioneUo = descrizioneUo;
	}

}