/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AmcoBusinessPartners implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String codiceFinanziaria;
	private String codiceAmco;
	private String ragioneSociale;
	private String codiceFiscale;
	private String partitaIva;
	
	public String getCodiceFinanziaria() {
		return codiceFinanziaria;
	}
	
	public void setCodiceFinanziaria(String codiceFinanziaria) {
		this.codiceFinanziaria = codiceFinanziaria;
	}
	
	public String getCodiceAmco() {
		return codiceAmco;
	}
	
	public void setCodiceAmco(String codiceAmco) {
		this.codiceAmco = codiceAmco;
	}
	
	public String getRagioneSociale() {
		return ragioneSociale;
	}
	
	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}
	
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	
	public String getPartitaIva() {
		return partitaIva;
	}
	
	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}
	
	@Override
	public String toString() {
		return "AmcoBusinessPartners [codiceFinanziaria=" + codiceFinanziaria + ", codiceAmco=" + codiceAmco
				+ ", ragioneSociale=" + ragioneSociale + ", codiceFiscale=" + codiceFiscale + ", partitaIva="
				+ partitaIva + "]";
	}
	
}
