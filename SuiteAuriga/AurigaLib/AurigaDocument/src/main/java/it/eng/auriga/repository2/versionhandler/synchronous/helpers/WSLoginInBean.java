/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WSLoginInBean implements Serializable {

	private String usernameIn;
	private String passwordIn;
	private String codApplicazioneIn;
	private String codIstanzaApplIn;
	private String tipoDominioIn;
	private String idDominioIn;
	private String dbSchemaIn;
	
	public String getUsernameIn() {
		return usernameIn;
	}
	public String getPasswordIn() {
		return passwordIn;
	}
	public void setUsernameIn(String usernameIn) {
		this.usernameIn = usernameIn;
	}
	public void setPasswordIn(String passwordIn) {
		this.passwordIn = passwordIn;
	}
	public String getTipoDominioIn() {
		return tipoDominioIn;
	}
	public void setTipoDominioIn(String tipoDominioIn) {
		this.tipoDominioIn = tipoDominioIn;
	}
	public String getIdDominioIn() {
		return idDominioIn;
	}
	public void setIdDominioIn(String idDominioIn) {
		this.idDominioIn = idDominioIn;
	}
	public String getCodApplicazioneIn() {
		return codApplicazioneIn;
	}
	public String getCodIstanzaApplIn() {
		return codIstanzaApplIn;
	}
	public void setCodApplicazioneIn(String codApplicazioneIn) {
		this.codApplicazioneIn = codApplicazioneIn;
	}
	public void setCodIstanzaApplIn(String codIstanzaApplIn) {
		this.codIstanzaApplIn = codIstanzaApplIn;
	}
	public String getDbSchemaIn() {
		return dbSchemaIn;
	}
	public void setDbSchemaIn(String dbSchemaIn) {
		this.dbSchemaIn = dbSchemaIn;
	}
	}