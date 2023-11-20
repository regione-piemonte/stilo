/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

public class OrganigrammaSearchSezioneCacheBean {
	
	@XmlVariabile(nome="IdOrganigramma", tipo = TipoVariabile.SEMPLICE)
	private String idOrganigramma;
	@XmlVariabile(nome="Email", tipo = TipoVariabile.SEMPLICE)
	private String email;
	@XmlVariabile(nome="FlgSoloUOAttive", tipo = TipoVariabile.SEMPLICE)
	private String flgSoloUOAttive;
	@XmlVariabile(nome="FlgSoloRelUserUOAttive", tipo = TipoVariabile.SEMPLICE)
	private String flgSoloRelUserUOAttive;
	@XmlVariabile(nome="DescrizioneRFT", tipo = TipoVariabile.SEMPLICE)
	private String descrizioneRFT;
	@XmlVariabile(nome="TipoRelUtentiConUO", tipo = TipoVariabile.SEMPLICE)
	private String tipoRelUtentiConUO;
	@XmlVariabile(nome="IdUOPP", tipo = TipoVariabile.SEMPLICE)
	private String idUOPP;
	@XmlVariabile(nome="FlgPuntoRaccoltaEmail", tipo = TipoVariabile.SEMPLICE)
	private String flgPuntoRaccoltaEmail;
	@XmlVariabile(nome="Competenze", tipo = TipoVariabile.SEMPLICE)
	private String competenze;
	
	public String getIdOrganigramma() {
		return idOrganigramma;
	}
	public void setIdOrganigramma(String idOrganigramma) {
		this.idOrganigramma = idOrganigramma;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFlgSoloUOAttive() {
		return flgSoloUOAttive;
	}
	public void setFlgSoloUOAttive(String flgSoloUOAttive) {
		this.flgSoloUOAttive = flgSoloUOAttive;
	}
	public String getFlgSoloRelUserUOAttive() {
		return flgSoloRelUserUOAttive;
	}
	public void setFlgSoloRelUserUOAttive(String flgSoloRelUserUOAttive) {
		this.flgSoloRelUserUOAttive = flgSoloRelUserUOAttive;
	}
	public String getDescrizioneRFT() {
		return descrizioneRFT;
	}
	public void setDescrizioneRFT(String descrizioneRFT) {
		this.descrizioneRFT = descrizioneRFT;
	}
	public String getTipoRelUtentiConUO() {
		return tipoRelUtentiConUO;
	}
	public void setTipoRelUtentiConUO(String tipoRelUtentiConUO) {
		this.tipoRelUtentiConUO = tipoRelUtentiConUO;
	}
	public String getIdUOPP() {
		return idUOPP;
	}
	public void setIdUOPP(String idUOPP) {
		this.idUOPP = idUOPP;
	}
	public String getFlgPuntoRaccoltaEmail() {
		return flgPuntoRaccoltaEmail;
	}
	public void setFlgPuntoRaccoltaEmail(String flgPuntoRaccoltaEmail) {
		this.flgPuntoRaccoltaEmail = flgPuntoRaccoltaEmail;
	}
	public String getCompetenze() {
		return competenze;
	}
	public void setCompetenze(String competenze) {
		this.competenze = competenze;
	}	
}