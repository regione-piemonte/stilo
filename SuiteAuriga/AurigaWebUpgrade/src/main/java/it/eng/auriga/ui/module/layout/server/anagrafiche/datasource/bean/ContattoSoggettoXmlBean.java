/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class ContattoSoggettoXmlBean {
	
	@NumeroColonna(numero = "1")
	private String rowId;
	@NumeroColonna(numero = "2")
	private String tipo;
	@NumeroColonna(numero = "3")
	private String emailTelFax;
	@NumeroColonna(numero = "4")
	private String tipoTel;
	@NumeroColonna(numero = "5")
	private String flgPec;
	@NumeroColonna(numero = "6")
	private String flgDichIpa;
	@NumeroColonna(numero = "7")
	private String flgCasellaIstituz;
	@NumeroColonna(numero = "10")
	private String flgDomicilioDigitale;
	
	
	public String getRowId() {
		return rowId;
	}
	public void setRowId(String rowId) {
		this.rowId = rowId;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getEmailTelFax() {
		return emailTelFax;
	}
	public void setEmailTelFax(String emailTelFax) {
		this.emailTelFax = emailTelFax;
	}
	public String getTipoTel() {
		return tipoTel;
	}
	public void setTipoTel(String tipoTel) {
		this.tipoTel = tipoTel;
	}
	public String getFlgPec() {
		return flgPec;
	}
	public void setFlgPec(String flgPec) {
		this.flgPec = flgPec;
	}
	public String getFlgDichIpa() {
		return flgDichIpa;
	}
	public void setFlgDichIpa(String flgDichIpa) {
		this.flgDichIpa = flgDichIpa;
	}
	public String getFlgCasellaIstituz() {
		return flgCasellaIstituz;
	}
	public void setFlgCasellaIstituz(String flgCasellaIstituz) {
		this.flgCasellaIstituz = flgCasellaIstituz;
	}
	public String getFlgDomicilioDigitale() {
		return flgDomicilioDigitale;
	}
	public void setFlgDomicilioDigitale(String flgDomicilioDigitale) {
		this.flgDomicilioDigitale = flgDomicilioDigitale;
	}
}