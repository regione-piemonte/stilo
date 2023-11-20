/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class DestinatarioVantaggioBean {

	@NumeroColonna(numero = "1")
	private String tipoPersona;
	
	@NumeroColonna(numero = "2")
	private String cognome;
	
	@NumeroColonna(numero = "3")
	private String nome;
	
	@NumeroColonna(numero = "4")
	private String ragioneSociale;
	
	@NumeroColonna(numero = "5")
	private String codFiscalePIVA;
	
	@NumeroColonna(numero = "6")
	private String importo;

	@NumeroColonna(numero = "7")
	private Boolean flgPrivacy;
	
	@NumeroColonna(numero = "8")
	private String tipo;
	
	public String getTipoPersona() {
		return tipoPersona;
	}

	public void setTipoPersona(String tipoPersona) {
		this.tipoPersona = tipoPersona;
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

	public String getRagioneSociale() {
		return ragioneSociale;
	}

	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}

	public String getCodFiscalePIVA() {
		return codFiscalePIVA;
	}

	public void setCodFiscalePIVA(String codFiscalePIVA) {
		this.codFiscalePIVA = codFiscalePIVA;
	}

	public String getImporto() {
		return importo;
	}

	public void setImporto(String importo) {
		this.importo = importo;
	}
	
	public Boolean getFlgPrivacy() {
		return flgPrivacy;
	}

	public void setFlgPrivacy(Boolean flgPrivacy) {
		this.flgPrivacy = flgPrivacy;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
