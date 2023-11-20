/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.math.BigDecimal;

public class LoginBean implements Serializable{

	private String codApplicazione;
	private String password;
	private String codFiscale;
	private String userid;
	private String idUtente;
	private String idApplicazione;
	private String denominazione;
	private String token;
	private String idUserLavoro;
	private String schema;
	private String dominio;
	private Boolean passwordScaduta;
	private BigDecimal idUser;
	private String useridForPrefs;
	private String delegaDenominazione;
	private String delegaCodFiscale;;
	private String[] privilegi;
	private String logoUtente;
	private String linguaApplicazione;
	
	public String getCodApplicazione() {
		return codApplicazione;
	}
	public void setCodApplicazione(String codApplicazione) {
		this.codApplicazione = codApplicazione;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCodFiscale() {
		return codFiscale;
	}
	public void setCodFiscale(String codFiscale) {
		this.codFiscale = codFiscale;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getIdUtente() {
		return idUtente;
	}
	public void setIdUtente(String idUtente) {
		this.idUtente = idUtente;
	}
	public void setIdApplicazione(String idApplicazione) {
		this.idApplicazione = idApplicazione;
	}
	public String getIdApplicazione() {
		return idApplicazione;
	}
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getIdUserLavoro() {
		return idUserLavoro;
	}
	public void setIdUserLavoro(String idUserLavoro) {
		this.idUserLavoro = idUserLavoro;
	}	
	public String getSchema() {
		return schema;
	}
	public void setSchema(String schema) {
		this.schema = schema;
	}
	public String getDominio() {
		return dominio;
	}
	public void setDominio(String dominio) {
		this.dominio = dominio;
	}
	public Boolean getPasswordScaduta() {
		return passwordScaduta;
	}
	public void setPasswordScaduta(Boolean passwordScaduta) {
		this.passwordScaduta = passwordScaduta;
	}
	public String[] getPrivilegi() {
		return privilegi;
	}
	public void setPrivilegi(String[] privilegi) {
		this.privilegi = privilegi;
	}
	public void setUseridForPrefs(String useridForPrefs) {
		this.useridForPrefs = useridForPrefs;
	}
	public String getUseridForPrefs() {
		return useridForPrefs;
	}
	public void setDelegaDenominazione(String delegaDenominazione) {
		this.delegaDenominazione = delegaDenominazione;
	}
	public String getDelegaDenominazione() {
		return delegaDenominazione;
	}
	public String getDelegaCodFiscale() {
		return delegaCodFiscale;
	}
	public void setDelegaCodFiscale(String delegaCodFiscale) {
		this.delegaCodFiscale = delegaCodFiscale;
	}
	public BigDecimal getIdUser() {
		return idUser;
	}
	public void setIdUser(BigDecimal idUser) {
		this.idUser = idUser;
	}
	public String getLogoUtente() {
		return logoUtente;
	}
	public void setLogoUtente(String logoUtente) {
		this.logoUtente = logoUtente;
	}
	public String getLinguaApplicazione() {
		return linguaApplicazione;
	}
	public void setLinguaApplicazione(String linguaApplicazione) {
		this.linguaApplicazione = linguaApplicazione;
	}
	
}
