/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

/**
 * 
 * @author DANCRIST
 *
 */

public class DestVantaggioBean {

	private String tipo;  // solo per beneficiari trasparenza
	private String tipoPersona;
	private String cognome;
	private String nome;
	private String ragioneSociale;
	private String codFiscalePIVA;
	private String importo;
	private Boolean flgPrivacy; // solo per beneficiari trasparenza
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
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
	
}