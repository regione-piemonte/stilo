/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AmcoBusinessPartnersRequest implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String codiceFiscale;
	private String partitaIva;
	private String ragioneSociale;
	private String codiceFinanziaria;
	
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
	
	public String getRagioneSociale() {
		return ragioneSociale;
	}
	
	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}
	
	public String getCodiceFinanziaria() {
		return codiceFinanziaria;
	}
	
	public void setCodiceFinanziaria(String codiceFinanziaria) {
		this.codiceFinanziaria = codiceFinanziaria;
	}

	@Override
	public String toString() {
		return "AmcoBusinessPartnersRequest [codiceFiscale=" + codiceFiscale + ", partitaIva=" + partitaIva
				+ ", ragioneSociale=" + ragioneSociale + ", codiceFinanziaria=" + codiceFinanziaria + "]";
	}
	
}
