/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.List;

public class SpecializzazioneBean {

	private Integer tipoDominio;
	private BigDecimal idDominio;
	private String codIdConnectionToken;
	private String desUserOut;
	private String desDominioOut;
	private String parametriConfigOut;
	private List<String> privilegi;
	private String idUtenteModPec;
	private String codFiscaleUserOut;
	
	public Integer getTipoDominio() {
		return tipoDominio;
	}
	public void setTipoDominio(Integer tipoDominio) {
		this.tipoDominio = tipoDominio;
	}
	public BigDecimal getIdDominio() {
		return idDominio;
	}
	public void setIdDominio(BigDecimal bigDecimal) {
		this.idDominio = bigDecimal;
	}
	public String getCodIdConnectionToken() {
		return codIdConnectionToken;
	}
	public void setCodIdConnectionToken(String codIdConnectionToken) {
		this.codIdConnectionToken = codIdConnectionToken;
	}
	public String getDesUserOut() {
		return desUserOut;
	}
	public void setDesUserOut(String desUserOut) {
		this.desUserOut = desUserOut;
	}
	public String getDesDominioOut() {
		return desDominioOut;
	}
	public void setDesDominioOut(String desDominioOut) {
		this.desDominioOut = desDominioOut;
	}
	public String getParametriConfigOut() {
		return parametriConfigOut;
	}
	public void setParametriConfigOut(String parametriConfigOut) {
		this.parametriConfigOut = parametriConfigOut;
	}
	public void setPrivilegi(List<String> privilegi) {
		this.privilegi = privilegi;
	}
	public List<String> getPrivilegi() {
		return privilegi;
	}
	public void setIdUtenteModPec(String idUtenteModPec) {
		this.idUtenteModPec = idUtenteModPec;
	}
	public String getIdUtenteModPec() {
		return idUtenteModPec;
	}
	public String getCodFiscaleUserOut() {
		return codFiscaleUserOut;
	}
	public void setCodFiscaleUserOut(String codFiscaleUserOut) {
		this.codFiscaleUserOut = codFiscaleUserOut;
	}
	
	
}
