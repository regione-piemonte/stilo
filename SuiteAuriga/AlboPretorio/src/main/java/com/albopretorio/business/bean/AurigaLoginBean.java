/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Antonio Peluso
 *
 * Classe che contiene i dati di login.
 * 
 */
@XmlRootElement
public class AurigaLoginBean implements Serializable {

	private static final long serialVersionUID = -4237637497871234329L;
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
	private String loginReject;
	private String uuid;
	private String profilo;
	private String logoUtente;
	private String linguaApplicazione;
	private String specLabelGui;
	private Boolean attivaMultilingua;

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

	public void setToken(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public String getIdUserLavoro() {
		return idUserLavoro;
	}

	public void setIdUserLavoro(String idUserLavoro) {
		this.idUserLavoro = idUserLavoro;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public String getSchema() {
		return schema;
	}

	public void setDominio(String dominio) {
		this.dominio = dominio;
	}

	public String getDominio() {
		return dominio;
	}

	public void setPasswordScaduta(Boolean passwordScaduta) {
		this.passwordScaduta = passwordScaduta;
	}

	public Boolean getPasswordScaduta() {
		return passwordScaduta;
	}

	public void setIdUser(BigDecimal idUser) {
		this.idUser = idUser;
	}

	public BigDecimal getIdUser() {
		return idUser;
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

	public String getLoginReject() {
		return loginReject;
	}

	public void setLoginReject(String loginReject) {
		this.loginReject = loginReject;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getProfilo() {
		return profilo;
	}

	public void setProfilo(String profilo) {
		this.profilo = profilo;
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

	public String getSpecLabelGui() {
		return specLabelGui;
	}

	public void setSpecLabelGui(String specLabelGui) {
		this.specLabelGui = specLabelGui;
	}

	public Boolean getAttivaMultilingua() {
		return attivaMultilingua;
	}

	public void setAttivaMultilingua(Boolean attivaMultilingua) {
		this.attivaMultilingua = attivaMultilingua;
	}

}
