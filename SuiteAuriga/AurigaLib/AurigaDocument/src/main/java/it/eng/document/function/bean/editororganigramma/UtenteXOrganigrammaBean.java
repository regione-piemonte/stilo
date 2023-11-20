/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="utenteAssociato")
@XmlAccessorType(XmlAccessType.FIELD)
public class UtenteXOrganigrammaBean {

	private String idUser;
	private String desUser;
	private String cognome;
	private String nome;
	private String cognomeNome;
	private String nroMatricola;
	private String idRuolo;
	private String descrizioneRuolo;
	private String idScrivania;
	private String desScrivania;
	private String uoAppartenenza;
	
	private String codRapido;
	private String idProfilo;
	private String nomeProfilo;
	private String attivoIn;
	private String idSoggRubrica;
	private String idMansione;
	private String nomeMansione;
	private String codiceUtenteMansione;	
	private String codiceUO;
	private String nomeAppUO;

	
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

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNroMatricola() {
		return nroMatricola;
	}

	public void setNroMatricola(String nroMatricola) {
		this.nroMatricola = nroMatricola;
	}

	public String getCodRapido() {
	 return codRapido;
	}
	
	public void setCodRapido(String codRapido) {
		this.codRapido = codRapido;
	}

	public String getIdProfilo() {
		return idProfilo;
	}

	public void setIdProfilo(String idProfilo) {
		this.idProfilo = idProfilo;
	}

	public String getNomeProfilo() {
		return nomeProfilo;
	}

	public void setNomeProfilo(String nomeProfilo) {
		this.nomeProfilo = nomeProfilo;
	}

	public String getIdSoggRubrica() {
		return idSoggRubrica;
	}

	public void setIdSoggRubrica(String idSoggRubrica) {
		this.idSoggRubrica = idSoggRubrica;
	}

	public String getCognomeNome() {
		return cognomeNome;
	}

	public void setCognomeNome(String cognomeNome) {
		this.cognomeNome = cognomeNome;
	}

	public String getAttivoIn() {
		return attivoIn;
	}

	public void setAttivoIn(String attivoIn) {
		this.attivoIn = attivoIn;
	}

	public String getIdMansione() {
		return idMansione;
	}

	public void setIdMansione(String idMansione) {
		this.idMansione = idMansione;
	}

	public String getNomeMansione() {
		return nomeMansione;
	}

	public void setNomeMansione(String nomeMansione) {
		this.nomeMansione = nomeMansione;
	}

	public String getCodiceUtenteMansione() {
		return codiceUtenteMansione;
	}

	public void setCodiceUtenteMansione(String codiceUtenteMansione) {
		this.codiceUtenteMansione = codiceUtenteMansione;
	}

	public String getIdRuolo() {
		return idRuolo;
	}

	public void setIdRuolo(String idRuolo) {
		this.idRuolo = idRuolo;
	}

	public String getDescrizioneRuolo() {
		return descrizioneRuolo;
	}

	public void setDescrizioneRuolo(String descrizioneRuolo) {
		this.descrizioneRuolo = descrizioneRuolo;
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

	public String getIdScrivania() {
		return idScrivania;
	}

	public void setIdScrivania(String idScrivania) {
		this.idScrivania = idScrivania;
	}

	public String getDesScrivania() {
		return desScrivania;
	}

	public void setDesScrivania(String desScrivania) {
		this.desScrivania = desScrivania;
	}

	public String getUoAppartenenza() {
		return uoAppartenenza;
	}

	public void setUoAppartenenza(String uoAppartenenza) {
		this.uoAppartenenza = uoAppartenenza;
	}
	
}
