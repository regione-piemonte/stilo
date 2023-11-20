/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;

public class CategorizzazioneCogitoBean implements Comparable<CategorizzazioneCogitoBean>{
	
	private String codeCategorizzazione;
	private String descrCategorizzazione;
	private String descrHierarchy;
	private String descrPath;
	private BigDecimal freq;
	
	public CategorizzazioneCogitoBean(String codeCategorizzazione, String descrCategorizzazione, BigDecimal freq, String descrHierarchy, String descrPath) {
		super();
		this.setCodeCategorizzazione(codeCategorizzazione);
		this.descrCategorizzazione = descrCategorizzazione;
		this.freq = freq;
		this.setDescrHierarchy(descrHierarchy);
		this.setDescrPath(descrPath);
	}

	public String getDescrCategorizzazione() {
		return descrCategorizzazione;
	}
	public void setDescrCategorizzazione(String descrCategorizzazione) {
		this.descrCategorizzazione = descrCategorizzazione;
	}
	public BigDecimal getFreq() {
		return freq;
	}
	public void setFreq(BigDecimal freq) {
		this.freq = freq;
	}
	public String getCodeCategorizzazione() {
		return codeCategorizzazione;
	}

	public void setCodeCategorizzazione(String codeCategorizzazione) {
		this.codeCategorizzazione = codeCategorizzazione;
	}

	// Ordino in base al valore di "freq" decrescente
	@Override
	public int compareTo(CategorizzazioneCogitoBean o) {
		if (this.getFreq().compareTo(o.getFreq()) >= 0) {
			return -1;
		} else {
			return 1;
		}
	}

	public String getDescrHierarchy() {
		return descrHierarchy;
	}

	public void setDescrHierarchy(String descrHierarchy) {
		this.descrHierarchy = descrHierarchy;
	}

	public String getDescrPath() {
		return descrPath;
	}

	public void setDescrPath(String descrPath) {
		this.descrPath = descrPath;
	}

	
}