/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

/**
 * 
 * @author DANCRIST
 *
 */

public class ConsigliereBean {

	private String consigliere;
	private String consigliereFromLoadDett;
	private String desConsigliere;
	private Boolean flgConsigliereFirmatario;
	private String cognomeNomeConsigliere;

	public String getConsigliere() {
		return consigliere;
	}

	public void setConsigliere(String consigliere) {
		this.consigliere = consigliere;
	}

	public String getConsigliereFromLoadDett() {
		return consigliereFromLoadDett;
	}

	public void setConsigliereFromLoadDett(String consigliereFromLoadDett) {
		this.consigliereFromLoadDett = consigliereFromLoadDett;
	}

	public String getDesConsigliere() {
		return desConsigliere;
	}

	public void setDesConsigliere(String desConsigliere) {
		this.desConsigliere = desConsigliere;
	}
	
	public Boolean getFlgConsigliereFirmatario() {
		return flgConsigliereFirmatario;
	}
	
	public void setFlgConsigliereFirmatario(Boolean flgConsigliereFirmatario) {
		this.flgConsigliereFirmatario = flgConsigliereFirmatario;
	}

	public String getCognomeNomeConsigliere() {
		return cognomeNomeConsigliere;
	}

	public void setCognomeNomeConsigliere(String cognomeNomeConsigliere) {
		this.cognomeNomeConsigliere = cognomeNomeConsigliere;
	}

}