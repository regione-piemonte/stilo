/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "sbloccoLimiteRichiesteRequest")
public class SbloccoLimiteRichiesteRequest implements Serializable{

	private static final long serialVersionUID = -5210820241934764664L;
	@XmlElement(required = true)
	private String codiceFiscale;
	@XmlElement(required = true)
	private String cognome;
	@XmlElement(required = true)
	private String nome;
	@XmlElement(required = true)
	private String email;
	private String numeroTelefono;
	@XmlElement(required = true)
	private String motivoSblocco;
	
	//Parametri LogIn esterna
	private String codApplicazione;
	private String istanzaApplicazione;
	private String userNam;
	private String password;
	
	
	public String getCodApplicazione() {
		return codApplicazione;
	}

	public void setCodApplicazione(String codApplicazione) {
		this.codApplicazione = codApplicazione;
	}

	public String getIstanzaApplicazione() {
		return istanzaApplicazione;
	}

	public void setIstanzaApplicazione(String istanzaApplicazione) {
		this.istanzaApplicazione = istanzaApplicazione;
	}

	public String getUserNam() {
		return userNam;
	}

	public void setUserNam(String userNam) {
		this.userNam = userNam;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNumeroTelefono() {
		return numeroTelefono;
	}

	public void setNumeroTelefono(String numeroTelefono) {
		this.numeroTelefono = numeroTelefono;
	}

	public String getMotivoSblocco() {
		return motivoSblocco;
	}

	public void setMotivoSblocco(String motivoSblocco) {
		this.motivoSblocco = motivoSblocco;
	}

}
