/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

public class DRelUOUserBean {
		
	private String idUo;
	private String nriLivelliUO;
	private String denominazioneUO;
	private String idUser;
	private String desUser;
	private String username;
	private String flgTipoRel;
	private Date dtInizioVld;
	private String idRuoloAmm;
	private String desRuoloAmm;
	private String motivi;
	
	private Integer flgIgnoreWarning;

	
	public String getIdUo() {
		return idUo;
	}
	public void setIdUo(String idUo) {
		this.idUo = idUo;
	}
	public String getNriLivelliUO() {
		return nriLivelliUO;
	}
	public void setNriLivelliUO(String nriLivelliUO) {
		this.nriLivelliUO = nriLivelliUO;
	}
	public String getDenominazioneUO() {
		return denominazioneUO;
	}
	public void setDenominazioneUO(String denominazioneUO) {
		this.denominazioneUO = denominazioneUO;
	}
	public String getIdUser() {
		return idUser;
	}
	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}
	public String getDesUser() {
		return desUser;
	}
	public void setDesUser(String desUser) {
		this.desUser = desUser;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFlgTipoRel() {
		return flgTipoRel;
	}
	public void setFlgTipoRel(String flgTipoRel) {
		this.flgTipoRel = flgTipoRel;
	}
	public Date getDtInizioVld() {
		return dtInizioVld;
	}
	public void setDtInizioVld(Date dtInizioVld) {
		this.dtInizioVld = dtInizioVld;
	}
	public String getIdRuoloAmm() {
		return idRuoloAmm;
	}
	public void setIdRuoloAmm(String idRuoloAmm) {
		this.idRuoloAmm = idRuoloAmm;
	}
	public String getDesRuoloAmm() {
		return desRuoloAmm;
	}
	public void setDesRuoloAmm(String desRuoloAmm) {
		this.desRuoloAmm = desRuoloAmm;
	}
	public String getMotivi() {
		return motivi;
	}
	public void setMotivi(String motivi) {
		this.motivi = motivi;
	}
	public Integer getFlgIgnoreWarning() {
		return flgIgnoreWarning;
	}
	public void setFlgIgnoreWarning(Integer flgIgnoreWarning) {
		this.flgIgnoreWarning = flgIgnoreWarning;
	}
	
}
