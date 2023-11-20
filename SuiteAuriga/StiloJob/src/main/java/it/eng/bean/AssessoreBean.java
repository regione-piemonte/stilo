/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

/**
 * 
 * @author DANCRIST
 *
 */

public class AssessoreBean {

	private String assessore;
	private String assessoreFromLoadDett;
	private String desAssessore;
	private Boolean flgAssessoreFirmatario;
	private String cognomeNomeAssessore;

	public String getAssessore() {
		return assessore;
	}

	public void setAssessore(String assessore) {
		this.assessore = assessore;
	}

	public String getAssessoreFromLoadDett() {
		return assessoreFromLoadDett;
	}

	public void setAssessoreFromLoadDett(String assessoreFromLoadDett) {
		this.assessoreFromLoadDett = assessoreFromLoadDett;
	}

	public String getDesAssessore() {
		return desAssessore;
	}

	public void setDesAssessore(String desAssessore) {
		this.desAssessore = desAssessore;
	}
	
	public Boolean getFlgAssessoreFirmatario() {
		return flgAssessoreFirmatario;
	}
	
	public void setFlgAssessoreFirmatario(Boolean flgAssessoreFirmatario) {
		this.flgAssessoreFirmatario = flgAssessoreFirmatario;
	}
	
	public String getCognomeNomeAssessore() {
		return cognomeNomeAssessore;
	}

	public void setCognomeNomeAssessore(String cognomeNomeAssessore) {
		this.cognomeNomeAssessore = cognomeNomeAssessore;
	}

}