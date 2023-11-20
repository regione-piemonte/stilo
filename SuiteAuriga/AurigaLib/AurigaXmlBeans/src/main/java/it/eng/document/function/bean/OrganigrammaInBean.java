/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class OrganigrammaInBean implements Serializable {

	private String idUO;
	private String tipoRelazioneUtente;
	private String idUser;
	private String dataInizioValidita;
	private String dataFineValidita;
	
	public String getDataInizioValidita() {
		return dataInizioValidita;
	}
	public void setDataInizioValidita(String dataInizioValidita) {
		this.dataInizioValidita = dataInizioValidita;
	}
	public String getDataFineValidita() {
		return dataFineValidita;
	}
	public void setDataFineValidita(String dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}
	public String getIdUO() {
		return idUO;
	}
	public void setIdUO(String idUO) {
		this.idUO = idUO;
	}
	public String getTipoRelazioneUtente() {
		return tipoRelazioneUtente;
	}
	public void setTipoRelazioneUtente(String tipoRelazioneUtente) {
		this.tipoRelazioneUtente = tipoRelazioneUtente;
	}
	public String getIdUser() {
		return idUser;
	}
	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}
	
	
	
}
