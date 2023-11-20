/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SicraBeneficiario implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String nominativo;
	private String codiceFiscale;
	private String partitaIva;
	private Double importoLordo;
	private Double importoRitenute;
	private Double importoNetto;
	private List<SicraQuietanza> quietanze;
	
	public String getNominativo() {
		return nominativo;
	}
	
	public void setNominativo(String nominativo) {
		this.nominativo = nominativo;
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
	
	public Double getImportoLordo() {
		return importoLordo;
	}
	
	public void setImportoLordo(Double importoLordo) {
		this.importoLordo = importoLordo;
	}
	
	public Double getImportoRitenute() {
		return importoRitenute;
	}
	
	public void setImportoRitenute(Double importoRitenute) {
		this.importoRitenute = importoRitenute;
	}
	
	public Double getImportoNetto() {
		return importoNetto;
	}
	
	public void setImportoNetto(Double importoNetto) {
		this.importoNetto = importoNetto;
	}
	
	public List<SicraQuietanza> getQuietanze() {
		return quietanze;
	}
	
	public void setQuietanze(List<SicraQuietanza> quietanze) {
		this.quietanze = quietanze;
	}
	
}
