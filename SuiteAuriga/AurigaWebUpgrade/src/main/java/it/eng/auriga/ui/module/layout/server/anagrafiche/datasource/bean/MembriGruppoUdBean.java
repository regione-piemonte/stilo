/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

/**
 * 
 * @author DANCRIST
 *
 */

public class MembriGruppoUdBean {

	/**
	 * Id. dell'unità documentaria
	 */
	private String idUd;
	
	/**
	 * Indica se il gruppo è un destinatario (D), un assegnatario (A) o un destinataio di invio per conoscenza (C)
	 */
	private String flgAssCondDest;
	
	/**
	 * Id. del gruppo destinatario/assegnatario
	 */
	private String gruppo;
	
	/**
	 * Nome del gruppo destinatario/assegnatario
	 */
	private String nomeGruppo;
	
	/**
	 * Id. in rubrica del soggetto
	 */
	private String idRubrica;
	
	/**
	 * Tipo di soggetto: #APA = Altra PA, #IAMM = Altra AOO/UO dell'Amministrazione (esterna all'AOO), #AF = Altro (Pers. fisica), #AG = Altro (Pers. giuridica), UO;UOI = U.O./Ufficio interno, UP = Unità di personale
	 */
	private String tipoSoggetto;
	
	/**
	 * Denominazione / Cognome e Nome
	 */
	private String denominazione;
	
	/**
	 * Codice rapido/parlante
	 */
	private String codRapido;
	
	/**
	 * Codice fiscale/partita IVA
	 */
	private String codFiscalePIVA;
	
	/**
	 * Indirizzo (solo per i destinatari)
	 */
	private String indirizzo;
	
	/**
	 * (sempre 1/0) se la presa in carico è da effettuare o effettuata. 
	 */
	private String presaInCarico;

	/**
	 * Messaggio alt dell'icona di presa in carico. 
	 */
	private String messAltPresaInCarico;
	
	public String getIdUd() {
		return idUd;
	}

	public void setIdUd(String idUd) {
		this.idUd = idUd;
	}

	public String getFlgAssCondDest() {
		return flgAssCondDest;
	}

	public void setFlgAssCondDest(String flgAssCondDest) {
		this.flgAssCondDest = flgAssCondDest;
	}

	public String getGruppo() {
		return gruppo;
	}

	public void setGruppo(String gruppo) {
		this.gruppo = gruppo;
	}

	public String getNomeGruppo() {
		return nomeGruppo;
	}

	public void setNomeGruppo(String nomeGruppo) {
		this.nomeGruppo = nomeGruppo;
	}

	public String getIdRubrica() {
		return idRubrica;
	}

	public void setIdRubrica(String idRubrica) {
		this.idRubrica = idRubrica;
	}

	public String getTipoSoggetto() {
		return tipoSoggetto;
	}

	public void setTipoSoggetto(String tipoSoggetto) {
		this.tipoSoggetto = tipoSoggetto;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public String getCodRapido() {
		return codRapido;
	}

	public void setCodRapido(String codRapido) {
		this.codRapido = codRapido;
	}

	public String getCodFiscalePIVA() {
		return codFiscalePIVA;
	}

	public void setCodFiscalePIVA(String codFiscalePIVA) {
		this.codFiscalePIVA = codFiscalePIVA;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getPresaInCarico() {
		return presaInCarico;
	}

	public void setPresaInCarico(String presaInCarico) {
		this.presaInCarico = presaInCarico;
	}

	public String getMessAltPresaInCarico() {
		return messAltPresaInCarico;
	}

	public void setMessAltPresaInCarico(String messAltPresaInCarico) {
		this.messAltPresaInCarico = messAltPresaInCarico;
	}
	
}