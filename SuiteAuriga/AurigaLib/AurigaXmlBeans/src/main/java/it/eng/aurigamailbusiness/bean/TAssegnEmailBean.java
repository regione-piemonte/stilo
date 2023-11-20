/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.core.business.beans.AbstractBean;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TAssegnEmailBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -7341833444762147789L;

	private boolean flgAnn;

	private String idAssegnazioneEmail;

	private String idUteIns;

	private String idUteUltimoAgg;

	private String messaggio;

	private Date tsAssegnazione;

	private Date tsIns;

	private Date tsUltimoAgg;

	private String idEmail;

	private String idUtenteDest;

	private String idFruitoreDest;

	private String idUtenteMitt;

	public boolean getFlgAnn() {
		return flgAnn;
	}

	public void setFlgAnn(boolean flgAnn) {
		this.flgAnn = flgAnn;
	}

	public String getIdAssegnazioneEmail() {
		return idAssegnazioneEmail;
	}

	public void setIdAssegnazioneEmail(String idAssegnazioneEmail) {
		this.idAssegnazioneEmail = idAssegnazioneEmail;
	}

	public String getIdUteIns() {
		return idUteIns;
	}

	public void setIdUteIns(String idUteIns) {
		this.idUteIns = idUteIns;
	}

	public String getIdUteUltimoAgg() {
		return idUteUltimoAgg;
	}

	public void setIdUteUltimoAgg(String idUteUltimoAgg) {
		this.idUteUltimoAgg = idUteUltimoAgg;
	}

	public String getMessaggio() {
		return messaggio;
	}

	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
	}

	public Date getTsAssegnazione() {
		return tsAssegnazione;
	}

	public void setTsAssegnazione(Date tsAssegnazione) {
		this.tsAssegnazione = tsAssegnazione;
	}

	public Date getTsIns() {
		return tsIns;
	}

	public void setTsIns(Date tsIns) {
		this.tsIns = tsIns;
	}

	public Date getTsUltimoAgg() {
		return tsUltimoAgg;
	}

	public void setTsUltimoAgg(Date tsUltimoAgg) {
		this.tsUltimoAgg = tsUltimoAgg;
	}

	public String getIdEmail() {
		return idEmail;
	}

	public void setIdEmail(String idEmail) {
		this.idEmail = idEmail;
	}

	public String getIdUtenteDest() {
		return idUtenteDest;
	}

	public void setIdUtenteDest(String idUtenteDest) {
		this.idUtenteDest = idUtenteDest;
	}

	public String getIdFruitoreDest() {
		return idFruitoreDest;
	}

	public void setIdFruitoreDest(String idFruitoreDest) {
		this.idFruitoreDest = idFruitoreDest;
	}

	public String getIdUtenteMitt() {
		return idUtenteMitt;
	}

	public void setIdUtenteMitt(String idUtenteMitt) {
		this.idUtenteMitt = idUtenteMitt;
	}

}